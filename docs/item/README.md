# 添加物品

物品通过 `Zinecraft.ITEMS` 声明。目录负责注册、双语翻译、模型元数据、创造模式收集、燃料和堆肥信息。

## Java 示例

```java
public final DeferredItem<Item> orirock = Zinecraft.ITEMS
    .builder("orirock", "源岩")
    .enUs("Orirock")
    .build();
```

自定义物品通过 factory 保留具体类型：

```java
public final DeferredItem<ScannerItem> scanner = Zinecraft.ITEMS
    .builder("scanner", "扫描器",
        () -> new ScannerItem(new Item.Properties().stacksTo(1)))
    .enUs("Scanner")
    .build();
```

`build()` 返回 NeoForge 原生 `DeferredItem<T>`。模型、创造栏、燃料与堆肥等数据保存在 `ItemBuilder` 中，不再使用额外的 entry
包装。`DeferredItem` 可直接作为 `ItemLike` 传递；只在注册完成后需要具体物品方法时调用 `.get()`。

```java
ItemStack stack = new ItemStack(scanner);
```

可组合元数据：

- `fuel(ticks)`：燃烧时间，20 tick 为 1 秒。
- `compost(chance)`：堆肥成功概率，必须位于 0—1。
- `hideCreativeTab()`：不进入目录自动收集的创造模式页。

## 合成材料稀有度

泰拉合成材料在 `ModItem` 的注册声明中显式传入 `Rarity`，不再根据物品 ID 隐式推导：

- 1 级基础材料：`COMMON`。
- 2 级初级加工材料：`UNCOMMON`。
- 3 级中级加工、稀有矿物产物：`RARE`。
- 4—5 级高级加工站产物：`EPIC`。

由于 Minecraft 只有四档物品稀有度，配方 4 级与 5 级合并为 `EPIC`。这套稀有度只控制合成材料的 Minecraft 物品颜色，不覆盖
PRTS 藏品自身的稀有度。

## 模型与贴图

普通物品默认使用 `ModelTemplates.FLAT_ITEM`；唱片使用 `ModelTemplates.MUSIC_DISC`。贴图路径为：

```text
src/main/resources/assets/zinecraft/textures/item/<path>.png
```

复杂手持、动态或多层模型不能由目录推断，应在资源目录提供 JSON，或扩展 `CatalogModelProvider`。运行 `runData` 后检查生成模型和双语语言文件。

## 创造模式页

创造模式页通过 `Zinecraft.CREATIVE_TABS` 注册。应在需要收集的物品声明完成后创建页面；藏品、技能和 TaCZ
动态物品分别使用独立页面。

## 国家食物与藏品

- 藏品通过 `Zinecraft.COLLECTIBLES.builder(path, orderId, zhCn)` 链式声明名称、原效果、描述、Minecraft 效果和稀有度；
  `build()` 直接返回 `DeferredItem<CollectibleItem>`。
- 十九国食物的资料、参数和配方见 [NATION_FOODS.md](NATION_FOODS.md)。
- PRTS 藏品导入、效果与权利记录见 [PRTS_COLLECTIBLES.md](PRTS_COLLECTIBLES.md)。
