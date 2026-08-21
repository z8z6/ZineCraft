---
name: zinecraft-items
description: Add or revise ordinary Zinecraft items through the project catalogs and data generation. Use for materials, food, components, music discs, or other standalone items; use the dedicated weapon, skill, or collectible skill for those systems.
---

# Zinecraft 普通物品

通过项目的声明式目录新增普通物品，并让翻译、模型、创造模式页和配方保持一致。

## 开始前

完整阅读根目录 `AGENTS.md`，检查 `git status --short` 并保留用户改动。以当前源码为准，重点阅读：

- `api/registry/builder/ItemBuilder.java` 与 `api/registry/catalog/ItemCatalog.java`
- `core/registry/ModItem.java`、`ModCreativeTab.java`、`ModRecipeProvider.java`
- `core/Zinecraft.java` 与 `core/ZinecraftDataGenerator.java`

以上 Java 路径均相对于 `src/main/java/com/cxxcxx/zinecraft/`。

## 实现

1. 先从明日方舟官网、PRTS 或 ArknightsGameData 核实名称、说明和图像；不要补写未经来源支持的设定。只有找不到合适资源时才制作符合现有风格的资产，并记录来源状态。
2. 在 `ModItem` 用 `ItemBuilder` 声明稳定的 `snake_case` ID、中文名、必要的明确英文名、工厂、模型模板与创造页选项。普通材料沿用现有
   `item(...)`，食物沿用 `food(...)`；真正需要行为时才新增 `Item` 子类。
3. 默认模型由 `CatalogModelProvider` 生成，纹理放在 `src/main/resources/assets/zinecraft/textures/item/<id>.png`
   。自定义或手持模型应显式选择模板或维护手写模型，不要同时维护可由 `runData` 生成的副本。
4. 配方在 `ModRecipeProvider` 声明。若物品属于标签、任务、战利品或兼容层，同时更新对应数据；不要只做到创造模式可取。
5. 保持 `Zinecraft.bootstrapContent()` 的显式加载方式；新增独立注册类时才把其 `bootstrap()` 放在依赖已就绪、消费者之前。

武器物品、技能物品、Curios 藏品分别交给 `$zinecraft-weapons`、`$zinecraft-skills`、`$zinecraft-collectibles`。

## 验证

运行 `./gradlew.bat test`、`./gradlew.bat runData` 和 `./gradlew.bat build`。检查双语翻译、生成模型、纹理路径、配方/标签、创造页可见性、JAR
内容及 `git diff --check`；若行为依赖客户端，补做 `runClient` 手测并如实报告未执行项。
