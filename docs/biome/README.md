# 添加群系

群系通过 `Zinecraft.BIOMES.register` 声明，并由动态注册表数据生成器自动导出。项目中的 19 个泰拉国家群系及资料依据见
[泰拉国家群系设计](TERRA_NATIONS.md)。

## 基础示例

```kotlin
val EXAMPLE_BIOME = Zinecraft.BIOMES.register("example_biome") {
  precipitation = false
  temperature = 2.0f
  downfall = 0.0f

  BiomeDefaultFeatures.desertSpawns(spawns)
  defaultOverworldGeneration()
  BiomeDefaultFeatures.addDefaultOres(generation)
  BiomeDefaultFeatures.addDesertVegetation(generation)
}
```

返回值是 `ResourceKey<Biome>`，可以用于地表规则、结构群系条件或 TerraBlender 区域映射。

## 可配置属性

`SimpleBiomeBuilder` 提供：

- `precipitation`、`temperature`、`downfall`。
- `waterColor`、`waterFogColor`、`fogColor`、`skyColor`。
- `grassColor`、`foliageColor`。
- `music`。
- `spawns`：`MobSpawnSettings.Builder`。
- `generation`：`BiomeGenerationSettings.Builder`。
- `defaultOverworldGeneration()`：加入主世界洞穴、湖泊、地下结构、泉水和冻结等基础特征。

## 加入主世界

注册群系数据不等于群系会自然出现。当前项目通过 `NationBiomePlacement` 把资源键与 TerraBlender 气候参数分离：

```kotlin
NationBiomePlacement(
  biome = NationBiomes.KAZIMIERZ_KNIGHTLAND,
  temperature = Temperature.NEUTRAL,
  humidity = Humidity.DRY,
  continentalness = Continentalness.MID_INLAND,
  erosion = Erosion.EROSION_5,
  weirdness = Weirdness.MID_SLICE_NORMAL_DESCENDING
)
```

`TerraNationRegion` 会遍历配置并加入主世界。新增群系时，应同时添加一个不与现有配置完全重复的气候点。自定义地表必须用
`SurfaceRules.isBiome(...)` 限定作用范围，不能添加影响全部原版群系的兜底规则。

## 数据生成

`Zinecraft.WORLDGEN.addDataGeneration(registryBuilder)` 已注册群系 bootstrap；`ModDynamicRegistryProvider` 会导出
`Registries.BIOME`。新增普通群系时不需要修改数据生成入口，运行：

```powershell
.\gradlew.bat runDatagen
```

生成目录已被 Git 忽略，应把需要发布的稳定数据移动到 `src/main/resources`，或在构建流程中显式包含生成目录。
