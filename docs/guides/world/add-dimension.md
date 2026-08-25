# 添加维度与世界特征

只有生成规则、天空和玩法边界真正独立时才新增维度；普通地区优先做成群系或结构。当前示例入口是 [ModDimension.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModDimension.java)。

## 新维度需要同时决定

1. `DimensionBuilder` 的稳定 ID、高度范围和 DimensionType。
2. noise settings、密度函数、surface rule 与 biome source。
3. 可用群系的顺序和生成器。
4. 进入、返回、出生、重生、坐标缩放和世界边界。
5. 自然刷怪策略与维度显示名称。

### DimensionBuilder 链式参数

| 调用 | 参数含义 |
| --- | --- |
| `dimension("example")` | 创建基础 ID；会派生维度、维度类型和 LevelStem 的资源键。 |
| `heightRange(minY, height)` | 最低可生成 Y 和总高度。总高度不是最大 Y；最大 Y 为 `minY + height - 1`。 |
| `noiseSettings(key, factory)` | noise settings 的资源键和生成工厂；工厂接收动态注册上下文。 |
| `biomes(list)` | 有序 `DimensionBiome` 列表，每项包含群系键与气候点。 |
| `generator(factory)` | 根据 `DimensionBootstrapContext` 创建最终 ChunkGenerator。 |
| `build()` | 校验配置并加入 DimensionCatalog。 |

`DimensionBiome(builder.key(), builder.climate())` 的两个参数分别是群系动态注册键和多噪声气候点。Generator 工厂必须使用上下文中的 Holder，不能在静态初始化阶段直接读取尚未完成的动态注册表。

自定义密度函数在 [ModDensityFunction.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModDensityFunction.java) 注册；普通矿石或地物则优先走 [ModWorldFeature.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModWorldFeature.java)。

## 数据生成结果

`runData` 应产生匹配的 `dimension_type`、`dimension`、`noise_settings`、`density_function` 和相关 worldgen JSON。维度翻译不会自动推断，需要明确登记。

## 验证

在新世界和旧世界中分别测试创建与重进，再测试死亡重生、传送往返、边界和群系分布。专用服务端也必须能独立创建该维度。
