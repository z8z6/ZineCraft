# 泰拉维度与星门

## 泰拉维度

`DimensionCatalog` 创建维度、维度类型、噪声设置和资源键。`zinecraft:terra` 使用自定义 `TerraBiomeSource`，只允许 Zinecraft
群系，不生成任何原版群系。

世界方块坐标 `(0, 0)` 位于固定拉特兰中心区域；中心半径内全部高度返回 `laterano_holy_fields`
，其余国家按多噪声最近点分区。拉特兰中心地表绑定固定原点聚落，地下绑定 `laterano_host`。

Minecraft 1.21.1 在世界创建阶段从数据包加载维度，因此以下资源必须随 JAR 发布：

```text
src/main/resources/data/zinecraft/dimension/terra.json
src/main/resources/data/zinecraft/dimension_type/terra.json
```

## 星门

星门由拱石框架、外置激活方块和 `stargate_portal` 事件视界组成。激活后门面完整覆盖框架内部，包括最下方一行。事件视界实现原版
`Portal` 流程，因此具有传送冷却和视场扭曲。

- 从主世界进入 `zinecraft:terra`。
- 首次抵达时在目标位置创建返回门。
- 从泰拉返回时优先寻找对应主世界星门；无法使用时回退到主世界出生区域。
- 星门实现不兼容旧版框架布局，旧门需要重建。

星门以 `zinecraft:stargate` 正式结构自然生成，仅绑定主世界使用的雪原群系；因此可以被原版结构定位命令索引。旧版 Feature
生成入口已经移除，避免同一区域重复生成。

## 验证

```powershell
.\gradlew.bat runData
.\gradlew.bat build
.\gradlew.bat runClient
```

```text
/execute in zinecraft:terra run tp @s 0 120 0
/execute in zinecraft:terra run locate biome zinecraft:laterano_holy_fields
/execute in zinecraft:terra run locate structure zinecraft:laterano_host
/execute in minecraft:overworld run locate structure zinecraft:stargate
```

必须在新世界或未生成区块验证群系与结构；已有区块不会重新执行世界生成。
