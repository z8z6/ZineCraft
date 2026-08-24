---
name: zinecraft-blocks
description: Add or revise Zinecraft blocks, block items, block entities, models, loot, recipes, mining tags, and worldgen ores. Use for terrain materials and interactive blocks, not assembled buildings.
---

# Zinecraft 方块

通过 BlockCatalog 完成方块、方块物品、翻译、模型和掉落的成套接入。

## 当前入口

- BlockBuilder、OreBuilder、BlockEntityBuilder
- BlockCatalog、BlockEntityCatalog、FeatureCatalog
- ModBlock、ModBlockEntity、ModRecipeProvider
- CatalogModelProvider、CatalogLootTableProvider、BiomeSelection

## 修改流程

1. 普通立方体沿用 ModBlock.block(...) 或 BlockBuilder；默认注册方块物品、自身掉落和 cube-all 模型。
2. 只有确有需要时使用 noCubeModel()、noBlockItem()、noLoot() 或 drop(...)。禁用自动模型后补齐手写 blockstate、方块/物品模型和纹理；禁用自动掉落后明确实际掉落。
3. 状态逻辑放专用 Block 类；需要持久数据时再增加 BlockEntity，并按实际功能实现 setChanged、loadAdditional/saveAdditional、ticker、menu、同步或 renderer，不能把这些都当作无条件要求。
4. 矿石参考 ModBlock.ore(...)：同时建立 BlockBuilder 和 OreBuilder，并登记到 FeatureCatalog。独立地物才放 ModWorldFeature。注册方块不等于已接入世界生成。
5. 手工维护 src/main/resources/data/minecraft/tags/block/mineable/ 与同级 needs_*_tool.json 采掘标签；当前 catalog 不会自动生成这些标签。
6. 纹理放 assets/zinecraft/textures/block/；runData 产物不手工维护。大型建筑使用 $zinecraft-structures 或 $zinecraft-cities。

## 验证

运行 ./gradlew.bat test、./gradlew.bat runData 和 ./gradlew.bat build。核对 blockstate、模型、纹理、翻译、掉落、配方、采掘标签、创造页、feature/biome modifier 和 JAR；交互方块按需测试保存、同步与专用服务端。
