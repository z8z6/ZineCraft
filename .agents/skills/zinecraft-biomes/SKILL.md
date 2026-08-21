---
name: zinecraft-biomes
description: Add or revise Zinecraft biomes and connect their climate, surface, features, spawns, TerraBlender placement, and Terra geography. Use for biome-scale terrain ecology, not a standalone structure or dimension.
---

# Zinecraft 群系

把群系作为“动态注册表数据 + 气候选取 + 地表规则 + 生成内容”的整体接入泰拉。

## 建立上下文

阅读 `AGENTS.md` 与工作树，并检查：

- `api/registry/builder/BiomeBuilder.java`、`api/registry/catalog/BiomeCatalog.java`
- `api/world/biome/BiomeSelection.java` 与 `api/world/dimension/TerraBiomeSource.java`
- `core/registry/ModBiome.java`、`ModSurfaceRule.java`、`ModTerraBlender.java`、`ModWorldFeature.java`
- `core/nation/TerraGeography.java`、`core/registry/ModDimension.java`

## 实现

1. 先用官方/PRTS资料确定国家、地区、气候、地貌与生态；没有依据时不要臆造设定。选择稳定 ID，并明确中英文名。
2. 在 `ModBiome` 用 `BiomeBuilder` 设置 `climate(...)`、降水、温度、颜色和适合的生成预设。通过 `configure(...)` 增加特色生成与
   `featuredSpawn(...)`；这些构建器只能在 bootstrap 回调内访问。
3. 检查气候点是否与已有群系可区分，并保持 `BiomeSelection`/`TerraBiomeSource` 能覆盖它。若属于泰拉国家地理，还要更新
   `TerraGeography` 的区域映射与必要的 JourneyMap/任务资料。
4. 在 `ModSurfaceRule` 增加与群系相符的地表材料；需要新地物时用 `FeatureCatalog`/`ModWorldFeature` 声明配置与放置，并从群系生成步骤引用。
5. `ZinecraftDataGenerator` 已收集 `BIOMES` 和 `FEATURES`；不要手写其动态注册 JSON，也不要编辑 `src/generated/resources`。
6. `ModBiome.ALL` 等集合是声明时快照；新增字段必须位于这些汇总字段之前，并按用途同步 `ALL_TERRA_BIOMES`、
   `MAP_SUPPORT_BIOMES` 或 `NATIONAL_BIOMES`。国家群系通常还必须符合 `<nation>_` 前缀和地图完整归属校验。
7. 若只是把现有群系加入新维度，使用 `$zinecraft-dimensions`；若只是新增建筑或刷怪实体，分别使用对应 skill，并从本群系做接入。

## 验证

运行 `./gradlew.bat test`、`./gradlew.bat runData`、`./gradlew.bat build`。检查生成的
biome/configured_feature/placed_feature 数据、翻译、气候唯一性、地表规则、生成步骤和刷怪引用；用固定种子在 `runClient`
验证边界、颜色、地表、地物和生成率，并记录种子与坐标。
