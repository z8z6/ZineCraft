# Zinecraft API 便捷封装

`com.cxxcxx.zinecraft.api` 包提供两类声明目录：`ContentCatalog` 管理普通游戏内容及其数据生成元数据，`WorldgenCatalog`
管理动态世界生成内容。它与具体内容位于同一 Gradle 模块和同一个模组 JAR 中；内容代码只需要进行声明，不应直接调用 Minecraft
注册表。

分类型的完整指南：

- [物品](item/README.md)
- [方块](block/README.md)
- [实体](entity/README.md)
- [结构](structure/README.md)
- [群系](biome/README.md)

## 普通内容

每个模组创建一次目录：

```kotlin
val REGISTRAR = ModRegistrar("example-mod")
val CONTENT = ContentCatalog(REGISTRAR)
```

### 物品

```kotlin
val MAGIC_DUST = CONTENT.item("magic_dust", "魔法粉尘", "Magic Dust") {
  Item(Item.Properties().food(foodProperties))
}.fuel(600).compost(0.3f)
```

声明会完成物品注册，并记录中英文翻译和默认扁平物品模型。`fuel` 与 `compost` 可选。

### 方块

```kotlin
val EXAMPLE_BLOCK = CONTENT.block(
  "example_block",
  "示例方块",
  "Example Block"
) {
  Block(BlockBehaviour.Properties.of())
}.block
```

默认同时注册 `BlockItem`，并自动生成双语名称、简单立方体模型、方块状态、物品模型和掉落自身的战利品表。可通过 `dropSelf`、
`cubeModel`、`registerItem` 关闭对应默认行为。

### 唱片

```kotlin
val MUSIC = CONTENT.musicDisc(
  "ambient.example",
  lengthSeconds = 120f,
  description = "Artist - Example"
)
```

一次声明会注册声音事件和唱片物品，并记录 jukebox song、模型与翻译数据。音频文件和 `sounds.json` 仍需作为资源提供。

### 创造模式标签页

```kotlin
val TAB = CONTENT.creativeTab(
  "item",
  "示例模组",
  "Example Mod",
  icon = { ItemStack(MAGIC_DUST.item) }
)
```

目录中的所有物品和带物品形式的方块会自动加入标签页。

## 自动数据生成

数据生成入口应注册 API 提供的 provider：

- `ContentLanguageProvider`：生成 `zh_cn` 和 `en_us`。
- `ContentModelProvider`：生成普通物品模型、简单方块模型和方块状态。
- `ContentLootTableProvider`：生成默认方块掉落。

复杂配方仍由常规 `FabricRecipeProvider` 描述；如果其他声明封装需要追加配方，可通过 `CONTENT.recipes { output -> ... }`
注册回调，并在配方 provider 中调用 `CONTENT.generateRecipes(output)`。

## 世界生成

```kotlin
val WORLDGEN = WorldgenCatalog(REGISTRAR)
```

### 矿脉

```kotlin
val ORE = WORLDGEN.ore(
  path = "example_ore",
  block = EXAMPLE_BLOCK,
  veinSize = 8,
  veinsPerChunk = 4,
  maxY = 32
)
```

该声明会自动创建 configured feature 和 placed feature，并通过 Fabric biome modification 加入主世界。可传入其他 biome
selector。

### 群系

```kotlin
val BIOME = WORLDGEN.biome("example_biome") {
  precipitation = false
  temperature = 2.0f
  downfall = 0.0f
  defaultOverworldGeneration()
  BiomeDefaultFeatures.desertSpawns(spawns)
  BiomeDefaultFeatures.addDesertVegetation(generation)
}
```

`SimpleBiomeBuilder` 提供气候、颜色、音乐、生成设置和生物生成设置。群系如何进入噪声参数空间仍由 TerraBlender 或其他平台集成决定。

### 简易 NBT 建筑

```kotlin
val BUILDING = WORLDGEN.simpleBuilding(
  path = "portal_ruins",
  template = "portal_ruins/common",
  spacing = 36,
  separation = 30,
  salt = 958853901,
  removeVinesChance = 0.6f
)
```

该声明自动生成结构处理器、模板池、Jigsaw 结构和结构集。NBT 文件应位于 `data/<modid>/structure/<template>.nbt`
。简单建筑默认在主世界地表生成。

具有自定义结构类型、数据标记或特殊放置算法的高级结构，可使用 `WORLDGEN.structures { context -> ... }` 和
`WORLDGEN.structureSets { context -> ... }` 接入同一数据生成流程。

运行时入口调用一次：

```kotlin
WORLDGEN.initialize()
```

数据生成入口调用：

```kotlin
WORLDGEN.addDataGeneration(registryBuilder)
```
