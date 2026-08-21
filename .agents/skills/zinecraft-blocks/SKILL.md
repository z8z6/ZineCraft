---
name: zinecraft-blocks
description: Add or revise Zinecraft blocks, block items, block entities, models, loot, and related recipes. Use for terrain materials, ores, decorative or interactive blocks; use the structure or city skill for assembled buildings.
---

# Zinecraft 方块

通过 `BlockCatalog` 完成方块、方块物品、翻译、模型和掉落的成套接入。

## 建立上下文

阅读根目录 `AGENTS.md`，检查工作树，然后阅读相对于 `src/main/java/com/cxxcxx/zinecraft/` 的：

- `api/registry/builder/BlockBuilder.java`、`OreBuilder.java`、`BlockEntityBuilder.java`
- `api/registry/catalog/BlockCatalog.java`、`BlockEntityCatalog.java`
- `core/registry/ModBlock.java`、`ModBlockEntity.java`、`ModRecipeProvider.java`
- `api/datagen/CatalogModelProvider.java`、`CatalogLootTableProvider.java`

## 实现

1. 核实 PRTS/官方资料中的名称、用途和视觉依据；禁止无来源推断。稳定 ID 使用 `snake_case`。
2. 普通完整立方体沿用 `ModBlock.block(...)` 或直接构造 `BlockBuilder`。正确复制物理属性，明确英文名；默认会注册方块物品、自身掉落和
   cube-all 模型。
3. 仅在真实需要时使用 `.noCubeModel()`、`.noBlockItem()`、`.noLoot()` 或 `.drop(...)`。禁用自动模型后，必须补齐手写
   blockstate、方块模型、物品模型和纹理；禁用自动掉落后要说明预期掉落。
4. 有状态或非立方渲染的方块新增专用 `Block` 类。需要持久数据时同步实现 `BlockEntity`、`ModBlockEntity`
   声明、ticker/menu/网络同步和客户端渲染，参考 `ExampleEntityBlock`、`ExampleBlockEntity` 或星门方块。
5. 矿石沿用 `OreBuilder`、`ModWorldFeature` 和配方链；世界生成属于独立关注点，不能只注册矿石方块便声称已生成。
6. 纹理放在 `src/main/resources/assets/zinecraft/textures/block/`；自动产物留在 `src/generated/resources`，不要手工编辑生成文件。

大型建筑、Jigsaw 模板和城市街区分别使用 `$zinecraft-structures` 或 `$zinecraft-cities`。

## 验证

运行 `./gradlew.bat test`、`./gradlew.bat runData`、`./gradlew.bat build`。核对
blockstate、方块/物品模型、纹理引用、双语翻译、掉落、配方、挖掘与碰撞属性、创造页和 JAR；交互方块还应在 `runClient`
与专用服务端验证保存/同步。
