---
name: zinecraft-dimensions
description: Add or revise Zinecraft dimensions, dimension types, biome sources, noise settings, portals, spawn policies, and world boundaries. Use for a complete world dimension; use the biome skill for ecology inside an existing dimension.
---

# Zinecraft 维度

通过 DimensionCatalog 生成 dimension type、level stem 与 codec，并显式实现进入、出生、边界和刷怪策略。

## 当前入口

- DimensionBuilder、DimensionCatalog
- DimensionBootstrapContext、DimensionBiome、TerraBiomeSource、OverworldNoiseSettingsFactory
- ModDimension、ModDensityFunction、core/worldgen/density/、ModBiome、ModSurfaceRule
- TerraPlayerSpawn、TerraWorldBoundary、TerraMobSpawnPolicy
- TerraLayoutResource 与 core/structure/stargate/

## 修改流程

1. 只有玩法与生成规则真正独立时才新增维度；新地区通常属于群系或结构。
2. 在 ModDimension 用 DimensionBuilder 声明稳定 ID、heightRange、DimensionType、noiseSettings、有序 DimensionBiome 和 generator。自定义 biome source 通过 DimensionCatalog.biomeSource(...) 注册 codec。
3. 自定义密度函数在 ModDensityFunction 注册，并保持噪声高度、surface rule、biome source 和动态注册引用一致。若复用泰拉城市地形，必须显式接入 TerraLayoutResource 与 density lookup。
4. 维度名称不会自动进入翻译目录，需显式写入 TranslationCatalog。动态注册数据由 runData 生成。
5. 分别决定出生、重生、传送往返、坐标缩放、边界与自然刷怪。泰拉现有策略不是新维度的默认行为。
6. 入口方块/结构分别使用 $zinecraft-blocks 与 $zinecraft-structures，并保持维度 key、传送目标和生成数据一致。

## 验证

运行 ./gradlew.bat test、./gradlew.bat runData 和 ./gradlew.bat build。核对 dimension_type、level stem、noise_settings、density_function、codec 和注册表引用；在新旧世界及专用服务端测试创建、重进、重生、往返、边界与群系分布。
