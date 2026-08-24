---
name: zinecraft-biomes
description: Add or revise Zinecraft biomes and connect their climate, surfaces, features, spawns, Terra surface-rule integration, and nation geography. Use for biome-scale terrain ecology, not a standalone structure or dimension.
---

# Zinecraft 群系

把群系作为动态注册数据、气候采样、国家归属、地表规则和生成内容一起接入泰拉。

## 当前入口

- BiomeBuilder、BiomeCatalog、BiomeSelection
- ModBiome、ModNation、ModDimension、TerraBiomeSource
- ModSurfaceRule、ModTerraBlender、ModWorldFeature
- core/datagen/ZinecraftDataGenerator.java

## 修改流程

1. 在 ModBiome 用 BiomeBuilder 设置稳定 ID、名称、climate(...)、降水、温度、颜色和生成预设；用 configure(...) 添加特色生成与 featuredSpawn(...)。
2. 新声明必须位于 ALL、ALL_TERRA_BIOMES、MAP_SUPPORT_BIOMES、NATIONAL_BIOMES 等快照字段之前，并加入正确集合。国家群系使用 <nation>_ 前缀，且只能归属一个 ModNation。
3. 国家区域来自 ModNation 的相对点，ModDimension.createTerraGenerator 组装各国群系池。城市群系应按现有 configurationFrom(...) 模式继承对应生态，外海保留 TERRA_OUTER_OCEAN。
4. 在 ModSurfaceRule 补齐地表。新地物通过 FeatureCatalog、SimpleFeatureBuilder 或 OreBuilder 注册，并用 BiomeSelection 选择目标群系。泰拉河流由 TerraHydrologyFeature 雕刻，不是河流群系。
5. 实体先于群系 bootstrap；spawn restriction 只限制位置，自然生成仍需 featuredSpawn。
6. 动态注册 JSON 由 runData 生成，不直接修改 src/generated/resources。

## 验证

运行 ./gradlew.bat test、./gradlew.bat runData 和 ./gradlew.bat build。核对群系/feature 数据、国家群系池、城市群系继承、气候点、外海、水文、地表、刷怪引用和 JAR；用固定种子检查边界与生成率。
