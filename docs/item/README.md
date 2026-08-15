# 添加物品

物品通过 `Zinecraft.INSTANCE.getITEMS()` 声明。目录负责注册、双语翻译、模型元数据、创造模式收集、燃料和堆肥信息。

## Java 示例

```java
private static final ItemEntry<Item> ORIROCK = Zinecraft.INSTANCE.getITEMS().register(
    "orirock",
    "源岩",
    "Orirock",
    ModelTemplates.FLAT_ITEM,
    new Item.Properties(),
    true
);
```

自定义物品通过 factory 保留具体类型：

```java
private static final ItemEntry<ScannerItem> SCANNER = Zinecraft.INSTANCE.getITEMS().register(
    "scanner", "扫描器", "Scanner",
    ModelTemplates.FLAT_ITEM, true,
    () -> new ScannerItem(new Item.Properties().stacksTo(1))
);
```

返回值是 `ItemEntry<T>`；通过 `getItem()` 取得物品。`ItemEntry` 实现 `ItemLike`，原版接受 `ItemLike` 的 API 可直接使用条目。

```java
ItemStack stack = new ItemStack(SCANNER.getItem());
```

可组合元数据：

- `fuel(ticks)`：燃烧时间，20 tick 为 1 秒。
- `compost(chance)`：堆肥成功概率，必须位于 0—1。
- `includeInCreative=false`：不进入目录自动收集的创造模式页。

## 模型与贴图

普通物品默认使用 `ModelTemplates.FLAT_ITEM`；唱片使用 `ModelTemplates.MUSIC_DISC`。贴图路径为：

```text
src/main/resources/assets/zinecraft/textures/item/<path>.png
```

复杂手持、动态或多层模型不能由目录推断，应在资源目录提供 JSON，或扩展 `CatalogModelProvider`。运行 `runData` 后检查生成模型和双语语言文件。

## 创造模式页

创造模式页通过 `Zinecraft.INSTANCE.getCREATIVE_TABS()` 注册。应在需要收集的物品声明完成后创建页面；藏品、技能和 TaCZ
动态物品分别使用独立页面。

## 国家食物与藏品

- 十九国食物的资料、参数和配方见 [NATION_FOODS.md](NATION_FOODS.md)。
- PRTS 藏品导入、效果与权利记录见 [PRTS_COLLECTIBLES.md](PRTS_COLLECTIBLES.md)。
