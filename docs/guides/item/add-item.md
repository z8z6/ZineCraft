# 添加普通物品

普通材料、食物和组件使用 [ModItem.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModItem.java) 与 [ItemBuilder.java](../../src/main/java/com/cxxcxx/zinecraft/api/registry/builder/ItemBuilder.java)。武器、技能资料和藏品有各自流程，不要塞进普通物品注册。

## 1. 声明物品

在 `ModItem` 的同类声明附近新增静态字段：

```java
public static final ItemBuilder<Item> EXAMPLE_MATERIAL =
    item("example_material", "示例材料", Rarity.UNCOMMON);
```

`example_material` 是注册 ID，发布后不要随意更改，否则旧存档中的物品会丢失映射。只有需要右键、耐久或特殊交互时才新建 `Item` 子类。

### item(...) 的参数

| 参数 | 示例 | 含义 |
| --- | --- | --- |
| `path` | `"example_material"` | 命名空间内 ID，最终是 `zinecraft:example_material`，只能使用小写字母、数字、下划线和合法路径字符。 |
| `zhCn` | `"示例材料"` | 简体中文显示名，由数据生成器写入语言文件。 |
| `rarity` | `Rarity.UNCOMMON` | 原版物品稀有度，影响名称颜色；不是掉率。可选值通常为 COMMON、UNCOMMON、RARE、EPIC。 |
| `model` | `ModelTemplates.FLAT_ITEM` | 可选模型模板。普通 2D 物品用默认值；传 `null` 表示不自动生成模型。 |

项目的 `item(...)` 辅助方法内部会创建 `new Item.Properties().rarity(rarity)`、加入主创造页并调用 `build()`，因此使用辅助方法后不要再次 `build()`。

### 直接使用 ItemBuilder 时

| 参数 | 含义 |
| --- | --- |
| `Zinecraft.ITEMS` | 接收该物品的唯一 ItemCatalog。 |
| `path / zhCn / enUs` | 注册 ID、中文名和英文名；省略英文名的重载会从 ID 自动生成。 |
| `factory` | 每次注册表创建物品实例时调用的工厂，例如 `() -> new Item(properties)`。 |
| `model` | 数据生成使用的模型模板。 |
| `inCreativeTab` | 是否自动加入项目的普通物品创造页。 |

## 2. 添加纹理

放置：

```text
src/main/resources/assets/zinecraft/textures/item/example_material.png
```

普通物品默认由 Catalog 生成模型。若 Builder 使用了 `model(null)` 或等价配置，就必须自行提供物品模型。

## 3. 加入配方与创造栏

配方集中维护在 [ModRecipeProvider.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModRecipeProvider.java)。Builder 默认会处理主要创造页；若关闭了创造页选项，需要确认这是有意行为。

## 4. 验证

```powershell
.\gradlew.bat runData
.\gradlew.bat test
.\gradlew.bat build
```

检查生成的中英文翻译、`models/item`、配方、纹理路径以及游戏内创造栏。物品有贴图并不代表配方、任务或战利品引用会自动出现。
