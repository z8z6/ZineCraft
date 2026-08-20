# 泰拉城市内部建筑生成逻辑

状态：`DESIGN + COMPILED DOMAIN CONTRACTS`  
实现阶段：规划模型与建筑选择器已落地；187 个地图地点已接入固定 `BLOCKOUT` 结构。逐城正式 Structure/Piece、道路计划和证据完备资产仍待替换。

当前可运行占位实现见[泰拉移动城市与地区地标 Blockout](TERRA_CITY_BLOCKOUTS.md)。它使用五块四层移动地块表达每座城市，但不把占位体块声明为官方还原。

## 1. 对 `docs/city.md` 的项目化结论

采用以下原则：

- 整座城市不再由一棵随机 Jigsaw 树向外生长。
- Java 负责城市范围、地标保留区、道路图、城区、街区、地块和建筑选择。
- NBT/Jigsaw 继续负责单栋大型建筑内部或明确的多模块资产。
- 规划阶段只产生不可变数据，不放方块，也不读取远端区块。
- 实际放置由自定义城市 `Structure` 拆成跨区块 `StructurePiece`，让原版结构系统按当前生成区块裁切处理。

不采用 `docs/city.md` 中“用一个通用 `CityTheme`
自动生成所有国家建筑”的建议。共享层只能处理几何、寻路、碰撞、地块适配、确定性选择和序列化；每座城市必须显式声明自己的地标、道路端口、城区用途、建筑模板与基础设施。换材质或修改权重不能被视为另一座城市的设计。

## 2. 数据流

```text
TerraPlace（固定城市中心与旅行地图范围）
    ↓
CityDefinition（逐城、证据化的显式输入）
    ↓
CityPlanner（纯数据、确定性）
    ├─ 预留地标与广场
    ├─ 建立地标/城门/车站端口图
    ├─ 粗网格 A* 规划主路
    ├─ 生长次路与服务道路
    ├─ 道路切割街区
    ├─ 街区切割地块
    ├─ 分配城区与限高
    └─ CityBuildingSelector 选择建筑
    ↓
CityPlan（不可变，无方块）
    ↓
TerraCityStructure / CityStructurePiece（下一阶段）
    ├─ platform/foundation piece
    ├─ road piece
    ├─ landmark template pieces
    ├─ ordinary building template pieces
    └─ decoration/infrastructure pieces
    ↓
当前 WorldGenRegion 内的 Minecraft 方块
```

## 3. 现有 Java 契约

`api/world/city`：

- `CityDefinition`：城市范围、4/8/16 格规划网格、地形模式、独立布局策略、多个地标、道路端口、显式主路连接、城区和建筑目录。
- `CityPlan`：地标、道路路径、城区、街区、地块和最终建筑放置的不可变结果。
- `CityRect`：城市中心相对坐标中的半开矩形，统一碰撞与包含判断。
- `CityBuildingLot`：地块范围、临路方向、城区类型、道路等级和限高。
- `CityBuildingDefinition`：模板尺寸、入口朝向、退界、权重、允许城区和允许道路等级。
- `CityPlanner`：只消费纯地形模型的规划接口，明确禁止包装 `ServerLevel#getChunk` 等远端区块读取。

`core/worldgen/city`：

- `CityPlanningSeeds`：使用世界种子、城市中心、稳定地点 ID 和城市 salt 产生与区块加载顺序无关的种子。
- `CityBuildingSelector`：按城区、道路、限高、旋转后尺寸和退界筛选；把模板入口旋转到临路方向；最后执行按稳定 ID 排序的确定性权重选择。

## 4. 逐城定义门槛

只有同时满足下列条件的 `TerraPlace` 才能创建 `CityDefinition` 并进入自然生成：

1. 城市 `SOURCE_OF_TRUTH.md` 已列出官方/PRTS来源和可信等级。
2. `CITY.md` 已说明城市用途、移动/固定形态、规模和未知项。
3. 至少一个地标或城市中心模块已有 `BUILDING.md` 与 `ROOM_PROGRAM.md`。
4. 每个地标显式声明保留区、朝向和道路端口；道路规划器不猜入口。
5. 每个普通建筑模板声明真实占地、入口、限高、允许城区和道路等级。
6. 所有 NBT 已通过门、楼梯、净空、照明、容器、战利品和 Jigsaw 接口检查。
7. 城市没有资料支持时保持 JourneyMap 地点，不自动生成“国家风格通用城市”。

当前 187 个旅行地图地点均有可定位的 Blockout；其中 112 个城市、聚落和城区使用四层移动地块，75
个地区使用单独地标占位。它们仍不得绕过上述证据门槛迁移为正式 ID；研究充分的大型城市再逐一替换占位结构。

当前逐城 Blockout 输入直接定义在 `ModCityStructure.java` 中，不使用 JSON 或 Python
配置。即使同一国家暂时采用相同的建筑组合，每座城市也必须保有完整、独立的布局类、地标列表与普通建筑候选目录；生成器只解析这些
Java 声明，不会读取国家级建筑目录，也不会为漏填城市自动继承默认值。

`CityLayout` 是独立的纯数据布局策略。默认 `GridCityLayout` 按棋盘格切分普通建筑用地，避开所有地标保留区，并通过
`TerrainModel` 剔除不可建或坡度超限的位置。`DefaultCityPlanner`
先保留全部地标，再调用布局类产生地块并交给建筑选择器。建筑候选只描述尺寸、高度和权重；入口朝向在地块确定后由建筑选择器旋转到临路方向，不能再把候选建筑写死为北、东、南、西四栋。

## 5. 地标与道路

### 地标先占位

规划器首先放入 `LandmarkDefinition.reservedArea`。该矩形已经包含建筑、广场、台阶、围墙、维护通道和消防/物流净空，不只是 NBT
自身尺寸。两个地标保留区发生重叠时，定义加载直接失败，不能运行时挪动地标掩盖错误。

每个地标至少一个 `RoadPort`：

```text
port id + 城市相对坐标 + 朝向 + 道路等级
```

主路只连接 `CityDefinition.mainRoadConnections` 中显式列出的端口。共享规划器不能根据距离擅自决定宗教建筑、军事基地、车站或工业设施之间的关系。

### 道路寻路

- 在 `planningCellSize=8` 的默认粗网格执行 A*；小型密集城区可用 4，大型低密度城市可用 16。
- 代价为基础移动成本、坡度、水体、不可建造区域、地标碰撞和既有道路转向成本之和。
- `MOBILE_PLATFORM` 可忽略自然坡度，但必须避开平台分区和动力/维护区域。
- `TERRAIN_FOLLOWING` 必须限制连续坡度；超过阈值时绕行或使用逐城明确提供的桥梁/坡道模板。
- `UNDERWATER` 和 `SUBTERRANEAN` 不套用地表道路规则，必须有独立城市定义和通道资产。

端口连接完成后，再从主路向已声明城区生长 `SECONDARY`、`LOCAL` 和 `SERVICE` 道路。道路靠近城市边缘时降低分支密度，命中现有道路时形成路口，距离平行道路过近时停止。

## 6. 街区、地块与建筑

道路闭合区域被转成 `CityBlock`，然后按照城区定义切成 `CityBuildingLot`。每块地必须保存临路方向，不能让建筑模板自行猜入口。

建筑选择严格按以下顺序：

1. `allowedDistricts` 必须包含地块城区。
2. `allowedRoadClasses` 必须包含临路等级。
3. 模板高度不得超过地块与城区限高。
4. 将模板 `entranceFacing` 旋转到 `lot.roadFacing`。
5. 使用旋转后的宽深检查地块和四周退界。
6. 建筑朝道路一侧贴近规定退界，另一轴居中。
7. 候选按资源 ID 排序，使用 `citySeed + lot.id` 做确定性权重选择。

如果没有候选建筑，规划器不得跨国家借用房屋，也不得强塞超出地块的模板。该地块转为显式的 `EMPTY/PLAZA/GREEN/UTILITY`
回退类型，并在城市计划审计中报告缺失的尺寸、城区和道路组合。

## 7. 建筑模板分级

| 资产        | 放置方式                                 | 要求                                               |
|-----------|--------------------------------------|--------------------------------------------------|
| 小型单体住宅/商铺 | 单个 `StructureTemplateStructurePiece` | 模板包含完整入口、地板、顶棚和内部                                |
| 大型公寓/工厂   | 显式模块列表或受控 Jigsaw                     | 入口模块和总体包围盒预先计算                                   |
| 城市地标      | 多个明确 Piece                           | foundation/core/facade/roof/annex/surrounding 分离 |
| 道路与路口     | 道路 Piece 或国家/城市专用 NBT 模块             | 主路、支路、桥梁和坡道分池                                    |
| 路灯/绿化/设施  | Decoration Piece                     | 只能使用该城市已确认的基础设施清单                                |

Jigsaw 只在单栋大型建筑的受控范围内展开；城市道路、城区和所有建筑的位置均来自 `CityPlan`。

## 8. 世界生成接入设计

下一阶段新增一个 `TerraCityStructure`，而不是在普通 Feature 中生成整城：

1. 自定义 Structure Placement 只在已有 `TerraPlace` 城市中心区块触发。
2. `findGenerationPoint` 根据世界种子和城市定义构造一次 `CityPlan`。
3. 计划被拆成多个带真实 AABB 的 Piece；原版结构系统负责在相交区块生成对应部分。
4. Piece 的序列化必须保存城市 ID、计划版本、局部 piece ID 和模板/道路参数，不能依赖内存缓存恢复存档。
5. 规划器不得调用 `ServerLevel#getChunk`。地形输入只能来自噪声模型、结构上下文或已传入的世界生成区域。
6. 计划版本变化只影响新城市；已生成 StructureStart 不在玩家加载时偷偷重排。

单座城市 800×800 时约覆盖 2500 个区块，不应生成一个包含逐方块数据的巨大 Piece。道路按连续段切块，平台按 16/32/64 模块切分，建筑按真实
NBT 包围盒成为独立 Piece。

## 9. 确定性与并发

```text
citySeed = mix(worldSeed, place.id, place.center, citySalt)
lotSeed  = mix(citySeed, lot.id)
```

- 不使用全局随机数。
- 不使用“第几个生成的区块”作为输入。
- 定义列表先按稳定资源 ID 排序，再执行权重选择。
- 同一世界种子、地点 ID、定义版本和 salt 必须生成同一计划。
- 缓存只优化计算，不能成为结果真值；清空缓存后计划必须完全一致。

## 10. 验证

纯规划验证：

- 所有地标、城区、街区、地块和建筑均在城市边界内。
- 地标保留区互不相交；道路不穿过保留区。
- 每个道路端口存在且至少接入一条要求的道路。
- 主路图连通；孤立城区必须显式允许。
- 地块互不重叠且入口朝向相邻道路。
- 建筑旋转后占地、限高和退界有效。
- 相同输入重复规划的序列化摘要一致。

结构资产验证继续执行 `structure-checklist.md` 的房间可达性、楼梯、净空、照明、容器、战利品、旗帜和 Jigsaw 接口断言。

实机门槛：新世界中分别从不同方向接近城市，确认道路和建筑计划一致；在城市边缘、跨区块建筑、桥梁、平台底部和地标接口处检查断裂。只有通过
`/place`、`/locate`、旋转和跨区块测试的城市才可从预览 ID 迁移到正式 ID。

## 11. 迁移顺序

1. 选择资料最丰富的一座 Pilot City，不按个人喜好直接铺开十九国。
2. 建立逐城资料、城区、道路端口和建筑目录。
3. 用灰盒模板生成 `CityPlan`，先检查 500/300/150/50/10 格轮廓。
4. 接入预览 `TerraCityStructure`，不替换现有聚落正式 ID。
5. 完成 Architecture/Material/Detail Pass 和实机验证。
6. Pilot City 稳定后，再迁移下一座资料充足的城市。

## Sources

- [`docs/city.md`](../city.md)：程序化城市布局建议与确定性/区块安全原则。
- [`docs/art.md`](../art.md)：泰拉建筑资料和还原边界。
- [`TERRA_ARCHITECTURE_PIPELINE.md`](TERRA_ARCHITECTURE_PIPELINE.md)：建筑阶段、证据等级和接入门槛。
- [NeoForge 1.21.1 Resources](https://docs.neoforged.net/docs/1.21.1/resources/)：数据包世界生成资源边界。
