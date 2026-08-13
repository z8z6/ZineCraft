# 添加物品

物品通过 `Zinecraft.ITEMS` 声明。声明时会立即注册物品，并记录语言和模型元数据；数据生成器随后自动生成 `zh_cn`、`en_us`
和普通物品模型。

## 普通物品

在 `ModItem` 中添加：

```kotlin
val ORIROCK = Zinecraft.ITEMS.register(
  path = "orirock",
  zhCn = "源岩",
  enUs = "Orirock"
)
```

也可以在 `ModItem` 内定义一个局部辅助函数，让声明更短：

```kotlin
private fun item(path: String, zhCn: String, enUs: String) =
  Zinecraft.ITEMS.register(path, zhCn, enUs)

val ORIROCK = item("orirock", "源岩", "Orirock")
```

返回值是 `ItemEntry<Item>`，实际 Minecraft 物品通过 `.item` 取得：

```kotlin
ItemStack(ModItem.ORIROCK.item)
```

`ItemEntry` 也实现了 `ItemLike`，接受 `ItemLike` 的 API 可直接传入条目。

## 自定义属性物品

通过 factory 创建具体物品：

```kotlin
val MAGIC_DUST = Zinecraft.ITEMS.register(
  path = "magic_dust",
  zhCn = "魔法粉尘",
  enUs = "Magic Dust"
) {
  Item(
    Item.Properties().food(
      FoodProperties.Builder()
        .nutrition(6)
        .saturationModifier(0.8f)
        .alwaysEdible()
        .fast()
        .build()
    )
  )
}.fuel(600).compost(0.3f)
```

- `fuel(ticks)`：注册燃烧时间，20 tick 为 1 秒。
- `compost(chance)`：注册堆肥成功概率，范围为 0～1。

## 自定义物品类

```kotlin
class ScannerItem(properties: Properties) : Item(properties)

val SCANNER = Zinecraft.ITEMS.register(
  "scanner",
  "扫描器",
  "Scanner"
) {
  ScannerItem(Item.Properties().stacksTo(1))
}
```

返回值会保留具体泛型类型 `ItemEntry<ScannerItem>`。

## 模型与贴图

默认模型为 `ModelTemplates.FLAT_ITEM`。贴图放在：

```text
src/main/resources/assets/zinecraft/textures/item/<path>.png
```

唱片等特殊模板可传入 `model`：

```kotlin
Zinecraft.ITEMS.register(
  "example_disc",
  "示例唱片",
  "Example Disc",
  model = ModelTemplates.MUSIC_DISC
)
```

运行 `./gradlew runDatagen` 后会生成模型 JSON。复杂的手持模型、动态模型或多层模型不能由普通声明推断，应自行提供模型
JSON，或扩展 `CatalogModelProvider`。

## 创造模式标签页

目录中的物品可自动加入标签页：

```kotlin
val TAB = Zinecraft.CREATIVE_TABS.register(
  path = "item",
  zhCn = "Zinecraft",
  enUs = "Zinecraft",
  icon = { ItemStack(ORIROCK.item) }
)
```

应在所有需要加入标签页的物品声明完成后创建标签页。
