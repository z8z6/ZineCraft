# 添加群系

群系通过 `WorldgenCatalog.biome` 声明，并由动态注册表数据生成器自动导出。

## 基础示例

```kotlin
val EXAMPLE_BIOME = ZinecraftCore.WORLDGEN.biome("example_biome") {
  precipitation = false
  temperature = 2.0f
  downfall = 0.0f
  music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DESERT)

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

注册群系数据不等于群系会自然出现。当前项目使用 TerraBlender，在 `Region.addBiomes` 中把资源键映射到气候参数：

```kotlin
ParameterPointListBuilder()
  .temperature(Temperature.COOL)
  .humidity(Humidity.DRY)
  .continentalness(Continentalness.INLAND)
  .build()
  .forEach { point -> builder.add(point, ModBiome.EXAMPLE_BIOME) }
```

还应在 TerraBlender 入口注册区域。自定义地表可通过 `SurfaceRuleManager` 添加，并使用 `SurfaceRules.isBiome(EXAMPLE_BIOME)`
限定群系。

## 数据生成

`ZinecraftCore.WORLDGEN.addDataGeneration(registryBuilder)` 会注册群系 bootstrap；`ModWorldProvider` 会将
`Registries.BIOME` 导出。新增普通群系时不需要再修改数据生成入口。
