# 添加群系

群系通过 `BiomeBuilder` 声明并交给 `Zinecraft.BIOMES`，由动态注册表 provider
导出。十九国设计依据见 [TERRA_NATIONS.md](TERRA_NATIONS.md)。

```java
ResourceKey<Biome> example = new BiomeBuilder(
    Zinecraft.BIOMES, "example_biome", "示例群系"
).enUs("Example Biome")
    .climate(0.2F, 0.5F, 0.1F, 0.0F, 0.0F, 0.3F)
    .configure(builder -> {
      builder.precipitation(false);
      builder.temperature(2.0F);
      builder.downfall(0.0F);
      BiomeDefaultFeatures.desertSpawns(builder.spawns());
      builder.defaultOverworldGeneration();
      BiomeDefaultFeatures.addDefaultOres(builder.generation());
      BiomeDefaultFeatures.addDesertVegetation(builder.generation());
    })
    .build()
    .key();
```

以 [BiomeBuilder.java](../../src/main/java/com/cxxcxx/zinecraft/api/registry/builder/BiomeBuilder.java) 的 fluent API
为准。注册资源键不代表群系会自然出现；还必须把它加入目标维度的群系源。

## 泰拉维度

`DimensionBiome` 把群系键与 `Climate.ParameterPoint` 绑定，`TerraBiomeSource` 只接受 `zinecraft`
命名空间群系。拉特兰中心由专用中心群系与半径参数保证，其余区域按多噪声最近点选择。泰拉不得混入原版群系。

新增泰拉群系时：

1. 声明群系和双语名称。
2. 添加不重复的气候点。
3. 用目标群系条件限定表层规则与特色地物。
4. 检查自然指南针翻译、结构绑定和任务节点。
5. 运行 `runData`，再在新世界验证；已有区块不会重新生成。

```powershell
.\gradlew.bat runData
.\gradlew.bat build
```
