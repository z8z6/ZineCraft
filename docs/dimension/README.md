# 泰拉维度与星门

## 注册

`DimensionCatalog` 统一创建维度、维度类型、噪声设置和世界资源键。`ModDimensions.TERRA` 注册
`zinecraft:terra`，使用与主世界相同的地形高度和昼夜规则，但群系源仅包含 `NationBiomes` 的 19 个国家群系。

泰拉使用自定义 `zinecraft:terra` 群系源：世界方块坐标 `(0, 0)` 是固定的拉特兰中心，以该点为圆心、半径 1024 格的
全部高度都返回 `laterano_holy_fields`。其余十八国继续由多噪声最近点分区；拉特兰不会再由随机气候点重复生成，因此不同世界种子
都保持同一个国家中心。

Minecraft 1.21.1 在世界创建阶段从数据包加载 `dimension` 注册表，因此
`src/main/resources/data/zinecraft/dimension/terra.json` 是必须随 Jar 发布的稳定资源。`dimension_type`
、群系以及配置/放置地物由数据生成器导出。

主世界不再注册 `TerraNationRegion`；国家聚落与地标原本就绑定国家群系，因此会随群系一起只在泰拉生成。

## 星门

`zinecraft:stargate` 是无配置自定义地物，平均每 64 个候选区块尝试一次。Fabric 群系选择器必须同时满足：

- 群系键为 `minecraft:snowy_plains`；
- 群系能够在 `LevelStem.OVERWORLD` 生成。

由于群系生成设置可能被多个维度复用，`StarGateFeature.place` 还会硬校验当前世界必须是 `Level.OVERWORLD`。

星门由深板岩圆环、海晶灯节点和 `stargate_portal` 事件视界组成。事件视界实现原版 `Portal` 接口：

- 从主世界进入 `zinecraft:terra`；
- 首次抵达泰拉时按对应坐标创建带基座的返回门；
- 从泰拉返回时优先寻找原主世界星门，星门被破坏则回退到主世界出生点，不会在其他主世界群系补建入口；
- 传送冷却、实体和乘客跨维度迁移由原版 Portal 流程负责。

## 验证

运行：

```powershell
.\gradlew.bat runDatagen
.\gradlew.bat build
```

检查 `dimension_type/terra.json`、`configured_feature/stargate.json`、`placed_feature/stargate.json`，并在新世界中用
`/execute in zinecraft:terra run tp @s 0 120 0` 验证维度加载。自然生成测试应在雪原使用
`/locate biome minecraft:snowy_plains`，
确认其他主世界群系与泰拉维度均不会自然生成星门。

拉特兰中心与地下主机可用以下命令验证：

```text
/execute in zinecraft:terra run locate biome zinecraft:laterano_holy_fields
/execute in zinecraft:terra run locate structure zinecraft:laterano_host
/execute in zinecraft:terra run tp @s 0 -20 0
```

`laterano_host` 的结构候选区块固定为 `(-1, -1)`，33×33 模板从 `(-16, -16)` 展开，几何中心正好位于 `(0, 0)`。
