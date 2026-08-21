---
name: zinecraft-dimensions
description: Add or revise Zinecraft dimensions, dimension types, level stems, biome sources, noise settings, portals, spawn policies, and world boundaries. Use for a complete world dimension; use the biome skill for ecology inside an existing dimension.
---

# Zinecraft 维度

通过 `DimensionCatalog` 生成 dimension type、level stem 与相关 codec，并显式处理进入、出生和边界策略。

## 建立上下文

阅读 `AGENTS.md`、工作树和：

- `api/registry/builder/DimensionBuilder.java`、`api/registry/catalog/DimensionCatalog.java`
- `api/world/dimension/DimensionBootstrapContext.java`、`DimensionBiome.java`、`TerraBiomeSource.java`
- `core/registry/ModDimension.java`、`ModBiome.java`、`ModSurfaceRule.java`
- `core/dimension/TerraPlayerSpawn.java`、`TerraWorldBoundary.java`、`TerraMobSpawnPolicy.java`
- `core/structure/stargate/StarGateTeleporter.java`

Java 路径均相对于 `src/main/java/com/cxxcxx/zinecraft/`。

## 实现

1. 先确认新维度在设定与玩法上确实独立；若只是新地区，应新增群系/结构而非复制维度。名称、天空和规则依据来自官方/PRTS，适配内容需明确标注。
2. 在 `ModDimension` 用 `DimensionBuilder` 声明稳定 ID、`DimensionType` 参数、noise settings 和有序 `DimensionBiome`
   列表。自定义 biome source 或 chunk generator 需要 codec，并在 `DimensionCatalog` 的注册生命周期内接入。
3. 保证所有引用群系先由 `BiomeCatalog` 声明，气候点有效且列表非空；噪声/地表规则与 biome source 一致。动态注册数据由
   `runData` 生成，不编辑 `src/generated/resources`。
4. 自定义 biome source codec 必须通过 `DimensionCatalog.biomeSource(...)` 注册到 mod bus。维度名称不会由
   `DimensionCatalog` 自动翻译，需像泰拉一样显式加入 `TranslationCatalog`。
5. 分别决定出生、传送、重生、返回路径、坐标缩放、边界和自然刷怪策略。不要把泰拉专属的首次出生、星门单向传送或 Mob 限制自动套到别的维度。
6. 新维度的入口方块/结构分别使用 `$zinecraft-blocks` 和 `$zinecraft-structures`，传送目标、维度 key 与生成数据必须一致。

## 验证

运行 `./gradlew.bat runData`、`./gradlew.bat test`、`./gradlew.bat build`。核对 dimension_type、level stem、codec
与全部注册表引用；使用新旧世界测试创建、重进、死亡重生、往返传送、边界、群系分布和专用服务端加载。
