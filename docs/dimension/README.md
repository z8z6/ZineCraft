# 泰拉维度与星门

## 泰拉维度

`DimensionCatalog` 通过 `DimensionBuilder` 创建维度、维度类型、噪声设置和资源键。`zinecraft:terra` 使用自定义
`TerraBiomeSource`，只允许 Zinecraft 群系，不生成任何原版群系。

维度以 `(0, 0)` 为中心，世界边界为 `100000 × 100000` 格，即 X/Z 的有效范围均为
`[-50000, 50000)`。世界边界内侧最后 `1000` 格固定为泰拉外海，陆地区域位于 `[-49000, 49000)`。

国家锚点按最近点划分连续的国家范围，但不把整个国家压成单一地形。群系源先确定国家，再只从该国专属群系池按六轴气候选择；
河网只雕刻河床，不会把群系身份切换为通用河流。锚点相对位置来自泰拉地图，并以拉特兰为原点，
兼容固定原点聚落和 `laterano_host`。新玩家首次进入服务器时出生在拉特兰 `(0, 0)` 附近；该标记保存在玩家持久数据中，
之后登录不会覆盖玩家所在维度或自行设置的出生点。完整坐标表见[泰拉固定群系地图](TERRA_MAP.md)。

Minecraft 1.21.1 在世界创建阶段从数据包加载维度，因此以下资源必须随 JAR 发布：

```text
src/main/resources/data/zinecraft/dimension/terra.json
src/main/resources/data/zinecraft/dimension_type/terra.json
```

## 星门

星门由拱石框架、外置激活方块和 `stargate_portal` 事件视界组成。激活后门面完整覆盖框架内部，包括最下方一行。事件视界实现原版
`Portal` 流程，因此具有传送冷却和视场扭曲。

- 泰拉只有一座正式星门，固定在萨米约 `(-23008, -40000)`，不会在其他群系或主世界重复生成。
- 控制器只能在 `zinecraft:terra` 激活；事件视界只提供泰拉到主世界的单向传送。
- 进入事件视界后抵达主世界出生区域，主世界不会自动创建对应星门。
- 星门实现不兼容旧版框架布局，旧门需要重建。
- 完整性检查会从控制器两侧识别门框，并在轴状态不匹配时检查另一条水平轴；因此 Jigsaw
  的四种随机旋转均可激活，修复前已生成的旋转星门也会在首次激活时校正控制器轴状态。

星门以 `zinecraft:stargate` 正式结构使用固定区块放置器生成，并允许全部萨米专属群系承载结构；因此仍可被原版结构定位命令索引。

## JourneyMap 国家边界

JourneyMap 是客户端必需依赖。`ZinecraftJourneyMapPlugin` 通过 JourneyMap API 2.0.0 将十九国的边界与本地化国家名显示在
`zinecraft:terra` 地图中。边界不是手工复制的折线，而是从 `ModDimension.TERRA_MAP` 的唯一锚点计算同一组最近点
Voronoi 分区，再裁剪到 `[-49000, 49000]` 陆地区域。覆盖层直接读取 `MapSite.nation`，不再从群系 ID 猜测国家；天灾区参与边界裁剪但不作为国家标注。

城市与地区由 `TerraGeography` 提供，共 187
个地点，并分为“泰拉城市与聚落”“泰拉重要地区”两个可独立开关的图层。城市和聚落采用城市图层半径；城区与行政区统一为 `REGION`
，自然地区使用自然地貌半径；每个地点边界都再次裁剪到所属国家多边形。地点名称与国家归属来自
PRTS，坐标和边界是明确标注的游戏化布局，详见[城市与地区布局](../nation/TERRA_PLACE_LAYOUT.md)。

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
/execute in zinecraft:terra run locate structure zinecraft:stargate
```

必须在新世界或未生成的萨米区块验证星门；已有区块不会重新执行世界生成。首次出生逻辑应使用从未登录过该存档的新玩家验证。
