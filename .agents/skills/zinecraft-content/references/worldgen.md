# 群系与建筑生成

## 新增群系

在 `core/biome` 通过目录注册，注释应使用中文说明国家或区域特色：

```kotlin
/** 维多利亚雾原：潮湿高地与工业城镇之间的过渡地带。 */
val VICTORIA_MISTY_HIGHLANDS = Zinecraft.BIOMES.register("victoria_misty_highlands") {
  precipitation = true
  temperature = 0.6f
  downfall = 0.9f
  defaultOverworldGeneration()
  BiomeDefaultFeatures.addDefaultOres(generation)
  // 继续添加该群系的植被、地物与生物配置。
}
```

一个可自然探索的完整群系至少检查：

1. `NationBiomes` 中的气候、颜色、生成步骤和生物。
2. `NationBiomePlacements.ALL` 中唯一且合理的原版多噪声气候点，并同步 `terra.json`；国家群系只允许出现在泰拉维度。
3. `ModSurfaceRule` 中受 `SurfaceRules.isBiome(...)` 限定的独特表层。禁止添加影响所有原版群系的兜底规则。
4. 特色生物的群系选择器与合法生成地面。
5. 一个可重复聚落和按需求设置的特色建筑或唯一地标。

若内容依据官网或 PRTS，联网核对名称、地理、文化和建筑描述，并把 Minecraft 实现写成“基于资料的玩法化表达”，不要把推断冒充原文事实。

## 维度与星门

- 通过 `Zinecraft.DIMENSIONS.register` 声明维度；`DimensionCatalog` 负责资源键、维度类型和多噪声群系源 bootstrap。
- Minecraft 1.21.1 的 `dimension` 注册表由世界创建数据包层加载，因此发布资源必须包含
  `src/main/resources/data/<mod>/dimension/<id>.json`；不能把 `Registries.LEVEL_STEM` 交给
  `FabricDynamicRegistryProvider.addAll`。
- 维度类型可以由 `DimensionHelper.overworldLikeType()` 构造，避免在同一注册表 bootstrap 中读取尚未绑定的原版 Holder。
- 限定某维度的地物应同时用群系键和 `BiomeSelectionContext.canGenerateIn(LevelStem.<key>)` 缩小注入范围，并在
  `Feature.place` 用当前 `ServerLevel` 维度键做硬校验。Biome 实例会被复用，单靠选择器无法阻止其他维度复用同一群系后执行该地物。
- 传送门实现原版 `Portal`，返回 `DimensionTransition`；跨维度实体复制、冷却和乘客处理交给原版流程，出口创建必须提供安全基座。
- 泰拉国家群系不得重新注册 TerraBlender 主世界 Region。星门自然入口仅匹配主世界 `minecraft:snowy_plains`
  ；泰拉侧星门只在首次抵达时创建，不参与自然生成。

## 选择建筑封装

| 需求         | 方法               | 生成语义               |
|------------|------------------|--------------------|
| 单个小建筑      | `simpleBuilding` | 单模板、随机散布           |
| 自定义拼接建筑    | `jigsawBuilding` | 多模板池、随机散布          |
| 城市、村落、营地   | `settlement`     | 大型 Jigsaw 路网，可重复生成 |
| 每世界一次的特殊地标 | `uniqueLandmark` | 同心环放置，候选数为 1       |

### 普通大型聚落

```kotlin
val VICTORIA_TOWN = Zinecraft.STRUCTURES.settlement(
  path = "victoria_town",
  templateRoot = "settlements/victoria_town",
  biome = NationBiomes.VICTORIA_MISTY_HIGHLANDS,
  salt = 41002001,
  buildingTemplates = linkedMapOf(
    "row_house" to 4,
    "workshop" to 3,
    "station" to 2,
    "clinic" to 2
  ),
  spacing = 52,
  separation = 24,
  size = 7
)
```

模板根目录必须提供 `center.nbt`、四种 `street_*.nbt` 和至少四种功能建筑。道路池自动使用 `TERRAIN_MATCHING`，中心与建筑使用
`RIGID`。大型聚落建议 `size` 6—8，`maxDistanceFromCenter` 不超过封装校验的 112。

### 三段 Jigsaw 建筑

```kotlin
val THREE_PIECE = Zinecraft.STRUCTURES.jigsawBuilding(
  path = "jigsaw_example",
  spacing = 40,
  separation = 20,
  salt = 31579842,
  size = 2
) {
  pool("start") { template("jigsaw_example/start") }
  pool("middle") { template("jigsaw_example/middle") }
  pool("end") { template("jigsaw_example/end") }
}
```

`size = 2` 允许从起始片段再展开两层。父 Jigsaw 的 `target` 必须等于子模板连接块的 `name`，父连接块的 `pool`
指向子池，连接方向相对。NBT 放在 `src/main/resources/data/zinecraft/structure/<path>/`。

### 唯一地标

```kotlin
val CAPITAL = Zinecraft.STRUCTURES.uniqueLandmark(
  path = "victoria_capital",
  template = "landmarks/victoria_capital",
  biome = NationBiomes.VICTORIA_MISTY_HIGHLANDS,
  ringDistance = 32,
  heightmap = Heightmap.Types.WORLD_SURFACE_WG
)
```

“唯一”是每个结构条目的结构集候选数为 1，不表示多个不同地标共享一个全局配额。海床使用 `OCEAN_FLOOR_WG`；地下结构传
`heightmap = null` 并显式设置 `startHeight`。

## 资源与验证

- NBT 是发布资源，保存在 `src/main/resources/data/zinecraft/structure/`，不要依赖被忽略的 generated 目录。
- 可用仓库的 `script/generate_jigsaw_example.py`、`script/generate_nation_settlements.py` 重建既有示例；修改脚本后也要重建并验证输出。
- 运行数据生成与构建，再用 `/place structure zinecraft:<id>` 检查拼接，用 `/locate structure zinecraft:<id>` 检查世界生成。

详细约定见 `docs/biome/README.md`、`docs/structure/README.md`，实现见 `BiomeCatalog.kt`、`StructureCatalog.kt`。
