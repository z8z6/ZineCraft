# 添加结构

## 大型 Jigsaw 聚落

城市、村落、营地等可重复生成的建筑群使用 `settlement`：

```kotlin
val MINING_CAMP = Zinecraft.STRUCTURES.settlement(
  path = "mining_camp",
  templateRoot = "settlements/mining_camp",
  biome = ModBiomes.MINING_BADLANDS,
  salt = 41002001,
  buildingTemplates = linkedMapOf(
    "bunkhouse" to 4,
    "ore_workshop" to 3,
    "freight_depot" to 2,
    "canteen" to 2
  ),
  spacing = 52,
  separation = 24,
  size = 7
)
```

封装自动建立三个模板池：

- `center`：聚落中心，使用 `RIGID` 投影。
- `streets`：直路、转角、十字路和道路末端，使用 `TERRAIN_MATCHING` 贴合地形。
- `buildings`：至少四种带权重的功能建筑，使用 `RIGID` 投影。

模板目录必须提供 `center.nbt`、`street_straight.nbt`、`street_corner.nbt`、`street_cross.nbt`、
`street_end.nbt` 以及 `buildingTemplates` 中声明的建筑。`size` 控制道路网络的最大展开层数；建议大型聚落使用 6—8，
并将 `maxDistanceFromCenter` 设为 96—112；封装将上限保留在 112，为原版地形适配边界预留空间。

`NationSettlements` 已为十九个国家分别注册一套聚落，每套包含中心、四种道路和四种当地功能建筑。运行以下脚本可确定性重建
全部 171 个模板：

```powershell
python script/generate_nation_settlements.py
```

普通聚落使用随机散布结构集，可以在不同区域重复出现；这与下方每世界一次的唯一地标是两种独立机制。

## 每世界唯一建筑

绑定指定群系、每个世界只自然生成一次的建筑使用：

```kotlin
val UNIQUE_TOWER = Zinecraft.STRUCTURES.uniqueLandmark(
  path = "unique_tower",
  template = "landmarks/unique_tower",
  biome = ModBiomes.TARGET_BIOME,
  ringDistance = 32,
  heightmap = Heightmap.Types.WORLD_SURFACE_WG
)
```

该封装生成独立的 Jigsaw 结构、模板池、处理器和结构集。结构集使用原版同心环放置器并设置 `count = 1`，结构与放置器均绑定
目标群系，因此保持原版区块生成、存档及 `/locate structure` 兼容性。`ringDistance` 是以区块为单位的首环距离。
地表、海床建筑可分别传入 `WORLD_SURFACE_WG`、`OCEAN_FLOOR_WG`；地下建筑应传入 `heightmap = null` 与明确的
`startHeight`，避免被高度图投影到地面。

### 固定原点地下地标

必须与世界坐标对齐、不能受种子和同心环选址影响的核心设施使用：

```kotlin
val HOST = Zinecraft.STRUCTURES.fixedOriginUndergroundLandmark(
  path = "laterano_host",
  template = "laterano_host/core",
  biome = NationBiomes.LATERANO_HOLY_FIELDS,
  startHeight = -32
)
```

`zinecraft:fixed_origin` 放置器只接受区块 `(-1, -1)`。33×33 模板会覆盖 `x/z = -16..16`，所以结构几何中心严格位于
`(0, 0)`，并使用 `underground_structures` 阶段和 `encapsulate` 地形适配。拉特兰主机模板由
`script/generate_laterano_host.py` 确定性生成。

结构分为两类：

- 简易 Jigsaw 建筑：使用 `StructureCatalog.jigsawBuilding`，自动生成模板池、结构和结构集。
- 高级自定义结构：自行实现 `Structure` / `StructurePiece`，通过目录回调接入统一动态注册表。

## 三段式 Jigsaw 示例

仓库内置一个可运行的 `start → middle → end` 示例：

```kotlin
val THREE_PIECE_JIGSAW = Zinecraft.STRUCTURES.jigsawBuilding(
  path = "jigsaw_example",
  spacing = 40,
  separation = 20,
  salt = 31579842,
  size = 2
) {
  pool("start") {
    template("jigsaw_example/start")
  }
  pool("middle") {
    template("jigsaw_example/middle")
  }
  pool("end") {
    template("jigsaw_example/end")
  }
}
```

三个结构模板位于：

```text
src/main/resources/data/zinecraft/structure/jigsaw_example/
├─ start.nbt
├─ middle.nbt
└─ end.nbt
```

可以运行以下脚本重新生成最小模板：

```powershell
python script/generate_jigsaw_example.py
```

### 连接关系

| 片段        | Jigsaw name                            | target                               | pool                              |
|-----------|----------------------------------------|--------------------------------------|-----------------------------------|
| start 出口  | `zinecraft:jigsaw_example/start_exit`  | `zinecraft:jigsaw_example/middle_in` | `zinecraft:jigsaw_example/middle` |
| middle 入口 | `zinecraft:jigsaw_example/middle_in`   | `minecraft:empty`                    | `minecraft:empty`                 |
| middle 出口 | `zinecraft:jigsaw_example/middle_exit` | `zinecraft:jigsaw_example/end_in`    | `zinecraft:jigsaw_example/end`    |
| end 入口    | `zinecraft:jigsaw_example/end_in`      | `minecraft:empty`                    | `minecraft:empty`                 |

这是三个建筑片段，但需要四个 Jigsaw 方块完成两次连接。连接时父片段的 `target` 必须等于候选子片段的 `name`，两个 Jigsaw
的朝向必须相对。

`size = 2` 表示从起始片段最多继续展开两层，因此可生成中段和终段。`spacing` 必须大于 `separation`。

### 多模板权重

同一个池可声明多个随机候选：

```kotlin
pool("middle") {
  template("village/house_small", weight = 3)
  template("village/house_large", weight = 1)
}
```

模板路径会自动补上当前模组命名空间。

## 单模板建筑

不需要继续拼接时可以使用快捷方法：

```kotlin
val RUINS = Zinecraft.STRUCTURES.simpleBuilding(
  path = "ruins",
  template = "ruins/common",
  spacing = 36,
  separation = 30,
  salt = 958853901,
  removeVinesChance = 0.6f
)
```

它等价于只有 `start` 池的 Jigsaw 建筑。API 会自动生成：

- Structure processor list。
- Structure template pool。
- `JigsawStructure`。
- 使用随机散布的 `StructureSet`。

默认在带 `minecraft:is_overworld` 标签的群系地表生成。

## 用结构方块制作模板

1. `/give @s minecraft:structure_block`。
2. 在创造模式搭建片段，每个连接处放置 Jigsaw 方块。
3. 配置 `name`、`target`、`pool` 和 `final_state`。
4. 用结构方块保存模板。
5. 将世界目录下生成的 NBT 复制到 `src/main/resources/data/zinecraft/structure/<path>.nbt`。

开发时可用 `/place structure zinecraft:jigsaw_example` 验证结构数据。

## 高级扩展

若 Jigsaw 建筑仍不能满足特殊生成逻辑，可注册额外的结构 bootstrap：

```kotlin
init {
  Zinecraft.STRUCTURES.structures(::configureStructures)
  Zinecraft.STRUCTURES.structureSets(::configureStructureSets)
}
```

只有确实需要自定义序列化和结构片段行为时，才使用 `REGISTRAR.structureType` 和 `REGISTRAR.structurePiece`
；项目不再保留无实际用途的自定义结构示例类。
