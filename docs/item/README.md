# 添加物品

物品通过 `Zinecraft.ITEMS` 声明。目录负责注册、双语翻译、模型元数据、创造模式收集、燃料和堆肥信息。

## Java 示例

```java
public final ItemBuilder<Item> orirock = new ItemBuilder<>(
    Zinecraft.ITEMS, "orirock", "源岩", "Orirock", () -> new Item(new Item.Properties())
).build();
```

自定义物品通过 factory 保留具体类型：

```java
public final ItemBuilder<ScannerItem> scanner = new ItemBuilder<>(
    Zinecraft.ITEMS, "scanner", "扫描器", "Scanner",
    () -> new ScannerItem(new Item.Properties().stacksTo(1))
).build();
```

构造 `ItemBuilder` 时传入目录和声明参数，再调用 `build()` 登记物品；Catalog 不重复提供 Builder 构造入口。物品属性全部在
factory
的 `Item.Properties` 中声明。需要 NeoForge 注册对象时调用 `getItem()`；`ItemBuilder` 本身也可直接作为 `ItemLike` 传递。

```java
ItemStack stack = new ItemStack(scanner);
```

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

特殊模型模板与是否加入主创造栏通过 `ItemBuilder` 构造参数传入。复杂手持、动态或多层模型不能由目录推断，应在资源目录提供
JSON，或扩展 `CatalogModelProvider`。运行 `runData` 后检查生成模型和双语语言文件。

## 创造模式页

创造模式页通过 `Zinecraft.CREATIVE_TABS` 注册。应在需要收集的物品声明完成后创建页面；藏品、技能和 TaCZ
动态物品分别使用独立页面。

## 国家食物与藏品

- 藏品通过 `new CollectibleCatalog.CollectibleBuilder(Zinecraft.COLLECTIBLES, path, orderId, zhCn)`
  链式声明名称、原效果、描述、Minecraft 效果和稀有度；
  `build()` 直接返回 `DeferredItem<CollectibleItem>`。
- 十九国食物的资料、参数和配方见 [NATION_FOODS.md](NATION_FOODS.md)。
- PRTS 藏品导入、效果与权利记录见 [PRTS_COLLECTIBLES.md](PRTS_COLLECTIBLES.md)。
