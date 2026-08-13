# 添加结构

结构分为两类：

- 简易 Jigsaw 建筑：使用 `WorldgenCatalog.jigsawBuilding`，自动生成模板池、结构和结构集。
- 高级自定义结构：自行实现 `Structure` / `StructurePiece`，通过目录回调接入统一动态注册表。

## 三段式 Jigsaw 示例

仓库内置一个可运行的 `start → middle → end` 示例：

```kotlin
val THREE_PIECE_JIGSAW = ZinecraftCore.WORLDGEN.jigsawBuilding(
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
src/main/resources/data/zinecraft-core/structure/jigsaw_example/
├─ start.nbt
├─ middle.nbt
└─ end.nbt
```

可以运行以下脚本重新生成最小模板：

```powershell
python script/generate_jigsaw_example.py
```

### 连接关系

| 片段        | Jigsaw name                                 | target                                    | pool                                   |
|-----------|---------------------------------------------|-------------------------------------------|----------------------------------------|
| start 出口  | `zinecraft-core:jigsaw_example/start_exit`  | `zinecraft-core:jigsaw_example/middle_in` | `zinecraft-core:jigsaw_example/middle` |
| middle 入口 | `zinecraft-core:jigsaw_example/middle_in`   | `minecraft:empty`                         | `minecraft:empty`                      |
| middle 出口 | `zinecraft-core:jigsaw_example/middle_exit` | `zinecraft-core:jigsaw_example/end_in`    | `zinecraft-core:jigsaw_example/end`    |
| end 入口    | `zinecraft-core:jigsaw_example/end_in`      | `minecraft:empty`                         | `minecraft:empty`                      |

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
val RUINS = ZinecraftCore.WORLDGEN.simpleBuilding(
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
5. 将世界目录下生成的 NBT 复制到 `src/main/resources/data/zinecraft-core/structure/<path>.nbt`。

开发时可用 `/place structure zinecraft-core:jigsaw_example` 验证结构数据。

## 高级自定义结构

需要数据标记、战利品箱、自定义高度算法或特殊序列化时，实现自己的结构类型，然后接入目录：

```kotlin
init {
  ZinecraftCore.WORLDGEN.structures(::configureStructures)
  ZinecraftCore.WORLDGEN.structureSets(::configureStructureSets)
}
```

自定义结构仍可使用 `REGISTRAR.structureType` 和 `REGISTRAR.structurePiece` 注册静态类型。仓库中的 `ExampleStructure` /
`ExampleStructurePieces` 展示了模板片段、旋转、NBT 保存及数据标记处理。
