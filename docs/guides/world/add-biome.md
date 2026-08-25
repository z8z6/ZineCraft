# 添加群系

群系不只是颜色配置，还要进入泰拉的气候采样、国家池、地表规则和生成内容。主要入口是 [ModBiome.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModBiome.java)。

## 1. 声明 BiomeBuilder

复制最接近的平原、山地、海洋或洞穴群系，修改稳定 ID、双语名和气候点：

```java
public static final BiomeBuilder EXAMPLE_PLAIN = biome(
    "example_plain",
    "示例平原",
    0.45F, 0.0F, 0.1F, 0.35F, 0.0F, 0.0F,
    builder -> {
      builder.temperature(0.8F);
      builder.downfall(0.4F);
      builder.plains();
    }
);
```

六个气候值会影响 `TerraBiomeSource` 的选择，不应随意复制后不调整。

### biome(...) 的参数

| 参数 | 含义 |
| --- | --- |
| `path` | 群系 ID；国家群系必须采用 `<nation>_<name>` 前缀。 |
| `zhCn` | 群系中文显示名。 |
| `climateTemperature` | 多噪声气候的温度坐标，不是游戏内实际温度值。 |
| `humidity` | 湿度坐标，决定它与其他气候点的相对距离。 |
| `continentalness` | 大陆性坐标，常用于区分海洋、海岸和内陆。 |
| `erosion` | 侵蚀坐标，参与平坦/崎岖地形选择。 |
| `depth` | 深度坐标，洞穴或特殊垂直区域会使用。 |
| `weirdness` | 奇异度坐标，参与山谷、山峰等地形变化。 |
| `configure` | 在数据生成阶段设置实际生态、颜色、地物和刷怪的回调。 |

### configure 回调中的常用设置

| 调用 | 含义 |
| --- | --- |
| `temperature(value)` | 游戏内群系温度，会影响降雪和部分视觉行为。 |
| `downfall(value)` | 降水强度。 |
| `precipitation(boolean)` | 是否允许降水。 |
| `waterColor / fogColor / grassColor` | 十进制 RGB 颜色；不要把十六进制字符串直接传入。 |
| `plains() / mountain() / ocean() / cavern()` | 添加一组原版生态生成预设。 |
| `featuredSpawn(category, type, weight, min, max)` | 加入自然生成表；weight 是相对权重，min/max 是每组数量。 |

## 2. 加入集合与国家

新字段必须写在 `ALL`、`ALL_TERRA_BIOMES` 等快照字段之前，并加入正确集合。国家群系使用 `<nation>_` 前缀，并在 [ModNation.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModNation.java) 中只归属一个国家。

## 3. 补地表、地物和生物

- 地表材料：修改 [ModSurfaceRule.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModSurfaceRule.java)。
- 地物：通过 FeatureCatalog 注册，并用 BiomeSelection 选择群系。
- 生物：在 Builder 中调用 `featuredSpawn(...)`。

## 4. 验证

运行数据生成和测试后，用固定种子查看群系边界、颜色、地表、地物与刷怪。城市群系通常应继承相应自然群系配置，而不是从零重复生态参数。
