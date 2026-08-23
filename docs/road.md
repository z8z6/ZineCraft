# 任务：实现每个 Region 内部的道路与建筑布局生成

当前项目为 Minecraft 1.21.1 的程序化城市生成系统。

上一阶段已经完成：

```text
City Polygon
    ↓
Connected Rectangular Region Generator
    ↓
多个互相连接的矩形 Region
```

每个 Region：

* 是轴对齐矩形；
* 长宽均为 Minecraft Chunk 的整数倍；
* Region 之间通过 1 Chunk 宽的城市道路连接；
* 整个城市的 Region Graph 保证连通；
* Region 外部可能存在山地、河流、自然地形。

现在需要实现下一层：

```text
Region
    ↓
Internal Road Layout
    ↓
Urban Block
    ↓
Building Parcel
    ↓
Building Placement
    ↓
Building NBT
```

目标是让每个 Region 内部形成具有明确布局风格的道路和建筑群，而不是随机摆放建筑。

第一版重点实现：

```text
GRID
CONCENTRIC
RADIAL_GRID
```

并为后续：

```text
SPINE
CAMPUS
HYBRID
```

保留扩展接口。

---

# 1. 基础规划单位

继续使用 Minecraft Chunk 作为规划基本单位：

```text
1 Grid Cell = 1 Chunk = 16 × 16 blocks
```

Region 本身已经 Chunk 对齐。

例如：

```text
Region:
12 × 16 chunks

=

192 × 256 blocks
```

Region 内的：

* 道路；
* Building Parcel；
* Building Footprint；

原则上全部 Chunk 对齐。

使用 Chunk 坐标进行规划：

```java
chunkX
chunkZ
```

最后真正生成 Minecraft 内容时再转换：

```java
blockX = chunkX * 16;
blockZ = chunkZ * 16;
```

不要在布局阶段大量使用 Block 坐标。

---

# 2. Region 输入

每个 Region 至少提供：

```java
RegionLayoutContext {
    Region region;

    long seed;

    RegionType regionType;

    List<RegionEntrance> entrances;

    List<BuildingTypeConfig> availableBuildings;
}
```

Region 已经是：

```java
Rect2i
```

形式。

例如：

```java
record Region(
    int id,

    int minChunkX,
    int minChunkZ,

    int widthChunks,
    int lengthChunks,

    RegionType type
) {}
```

---

# 3. Region Entrance

Region 不能完全独立生成。

上一阶段的 Region 道路网络已经决定：

```text
哪些 Region 相邻
```

以及：

```text
道路从 Region 哪一侧进入
```

因此每个 Region 必须接收：

```java
record RegionEntrance(
    Direction side,

    int offsetChunks,

    int widthChunks,

    int connectedRegionId
) {}
```

例如：

```text
            North Entrance
                  │
                  │
┌─────────────────┼────────────────┐
│                 │                │
│                                  │
│             Region               │
│                                  │
│                                  │
└─────┼────────────────────────────┘
      │
 West Entrance
```

Region 内部道路生成器必须首先处理这些 Entrance。

禁止出现：

```text
外部道路
    ↓
████████████
████建筑████
████████████
```

Region Entrance 属于硬约束。

---

# 4. 总体生成原则

严格采用：

```text
道路优先
↓
道路划分街区
↓
街区划分 Building Parcel
↓
建筑生成
```

不要：

```text
先随机摆建筑
↓
再从建筑之间找道路
```

正确流程：

```text
Region Boundary
       ↓
Entrances
       ↓
Primary Road Graph
       ↓
Layout-specific Roads
       ↓
Road Rasterization
       ↓
Urban Blocks
       ↓
Building Parcels
       ↓
Building Placement
```

---

# 5. LayoutType

增加：

```java
enum RegionLayoutType {

    GRID,

    CONCENTRIC,

    RADIAL_GRID,

    SPINE,

    CAMPUS
}
```

第一版必须完整实现：

```text
GRID
CONCENTRIC
RADIAL_GRID
```

其他类型暂时只保留接口。

RegionType 可以配置不同 LayoutType 权重。

例如：

```text
RESIDENTIAL
    GRID            60
    RADIAL_GRID     30
    CONCENTRIC      10

COMMERCIAL
    GRID            30
    RADIAL_GRID     40
    CONCENTRIC      30

ADMINISTRATIVE
    CONCENTRIC      60
    RADIAL_GRID     40

INDUSTRIAL
    GRID            60
    SPINE           40

LOGISTICS
    SPINE           70
    GRID            30
```

Layout 选择必须 deterministic。

---

# 6. Region Local Center

每个 Region 需要计算：

```java
ChunkPos localCenter;
```

由于 Region 已经是矩形，第一版可以使用：

```text
Region Rectangle Center
```

但是允许在一定范围内做 Seed 控制的小偏移：

```text
中心 ± 1~2 chunks
```

用于减少所有 Region 完全对称。

重要 Region：

```text
CORE
COMMAND
ADMINISTRATIVE
```

应尽量保持中心稳定。

---

# 7. 道路统一抽象为 RoadGraph

所有布局算法最终都输出：

```java
RoadGraph
```

定义：

```java
class RoadGraph {
    List<RoadNode> nodes;
    List<RoadEdge> edges;
}
```

RoadNode：

```java
record RoadNode(
    int id,
    int chunkX,
    int chunkZ,
    RoadNodeType type
) {}
```

类型例如：

```java
enum RoadNodeType {
    ENTRANCE,
    INTERSECTION,
    CENTER,
    PLAZA,
    LANDMARK
}
```

RoadEdge：

```java
record RoadEdge(
    int from,
    int to,

    RoadClass roadClass,

    int widthChunks
) {}
```

---

# 8. RoadClass

定义道路等级：

```java
enum RoadClass {

    PRIMARY,

    SECONDARY,

    SERVICE
}
```

推荐：

```text
PRIMARY
2 chunks = 32 blocks

SECONDARY
1 chunk = 16 blocks

SERVICE
1 chunk = 16 blocks
```

具体宽度必须可配置：

```java
RoadConfig {
    int primaryWidthChunks;
    int secondaryWidthChunks;
    int serviceWidthChunks;
}
```

不要把所有道路宽度硬编码到生成器。

---

# 9. 第一阶段：Mandatory Primary Roads

无论采用哪种 LayoutType：

```text
所有 Entrance 必须首先连接。
```

例如：

```text
West Entrance ───────── East Entrance
                         │
                         │
                         South Entrance
```

首先生成：

```java
PrimaryRoadGraph
```

要求：

```text
所有 RegionEntrance
```

位于同一个 connected component。

可以使用：

```text
Local Center
```

作为主要连接节点：

```text
Entrance
   ↓
Center
   ↓
Entrance
```

但不要强制每个 Region 都形成完美十字。

根据 Entrance 分布选择更短、更合理的 Manhattan Route。

---

# 10. 道路必须正交

第一版所有内部道路只能：

```text
平行 X
或
平行 Z
```

禁止任意角度。

即：

```text
horizontal
vertical
```

这样：

* 道路 Chunk 对齐；
* 建筑 Parcel 容易生成；
* 建筑 NBT 易于旋转；
* 不产生锯齿地块。

---

# 11. GRID Layout

GRID 是棋盘式布局。

不要生成绝对均匀棋盘。

基本过程：

```text
Mandatory Primary Road
        ↓
生成若干平行 Secondary Road
        ↓
生成垂直 Secondary Road
        ↓
形成候选 Grid Graph
        ↓
删除部分非必要道路
        ↓
保持道路整体连通
```

例如：

```text
BBBB│BBBB│BBBB
BBBB│BBBB│BBBB
────┼────┼────
BBBB│BBBB│BBBB
BBBB│BBBB│BBBB
────┼────┼────
BBBB│BBBB│BBBB
```

但最终允许：

```text
BBBBBBBB│BBBB
BBBBBBBB│BBBB
────────┼────
BBBB│BBB│BBBB
BBBB│BBBBBBBB
────┘BBBBBBBB
BBBBBBBBBBBBB
```

即：

```text
局部道路可以缺失
```

避免完美棋盘。

---

# 12. GRID 道路间距

道路间距必须是离散 Chunk 数。

例如：

```java
gridSpacingChunks = {
    3,
    4,
    5,
    6
};
```

使用 deterministic random 进行小范围变化。

不要：

```text
每隔4 Chunk绝对生成道路
```

而应该：

```text
4
5
3
4
6
```

形成自然变化。

---

# 13. GRID 道路裁剪

建议先构建候选 Grid Graph：

```text
Full Grid
```

然后：

```text
保留所有 Primary Roads
+
构造 Secondary Road MST
+
额外保留一定比例 Secondary Edge
```

例如：

```java
extraEdgeRatio = 0.25 ~ 0.50;
```

目标：

```text
Connected
+
存在少量环路
+
不是规则棋盘
```

---

# 14. CONCENTRIC Layout

Minecraft 不适合真正圆形城市。

因此这里实现：

```text
Orthogonal Concentric Layout
```

即：

```text
矩形环 / 方形环
```

例如：

```text
┌────────────────────────────┐
│                            │
│   ┌────────────────────┐   │
│   │                    │   │
│   │    ┌──────────┐    │   │
│   │    │   CORE   │    │   │
│   │    └──────────┘    │   │
│   │                    │   │
│   └────────────────────┘   │
│                            │
└────────────────────────────┘
```

每个环之间留：

```text
1~2 Chunk Road
```

---

# 15. CONCENTRIC 必须增加径向连接

不能只生成多个互不连接的 Ring。

必须添加：

```text
Cross Connector
```

例如：

```text
             │
┌────────────┼────────────┐
│            │            │
│   ┌────────┼────────┐   │
│   │        │        │   │
────┼────── CENTER ───┼────
│   │        │        │   │
│   └────────┼────────┘   │
│            │            │
└────────────┼────────────┘
             │
```

Region Entrance 应优先成为这些连接轴的一部分。

---

# 16. CONCENTRIC 不应每个环都完整

允许：

```text
部分环被广场占据
部分环中断
部分区域变成大型建筑
```

例如：

```text
┌─────────────────────┐
│                     │
│   ┌────────────┐    │
│   │            │    │
│   │   CORE     │    │
│   └───────     │    │
│          │     │    │
└──────────┴─────┘
```

不要生成机械式完美套娃。

---

# 17. RADIAL_GRID Layout

这是推荐作为默认城市布局之一的模式。

不是生成真正斜向道路。

而是：

```text
Orthogonal Radial Skeleton
+
Grid Subdivision
```

即：

```text
入口
 ↓
Local Center
 ↓
主干道路向四周辐射
 ↓
主路之间剩余空间
 ↓
局部使用 Grid 划分
```

例如：

```text
            │
      BBBB  │  BBBB
      BBBB  │  BBBB
────────────●────────────
      BBBB  │  BBBB
───────┐    │    ┌───────
       │    │    │
```

拓扑上：

```text
Center → Branches
```

视觉上具有放射感。

几何上仍然完全 Chunk 对齐。

---

# 18. RADIAL_GRID 的主干道路

根据：

```text
Region Entrances
+
Local Center
```

生成：

```text
Primary Radial Skeleton
```

例如：

```text
         Entrance
             │
             │
Entrance ─── CENTER ─── Entrance
             │
             │
          Branch
```

如果 Entrance 只有两个：

允许额外向没有 Entrance 的方向生成：

```text
1~2条城市发展轴
```

但长度必须受 Region 尺寸控制。

不要固定四方向全部生成。

---

# 19. RADIAL_GRID 的 Grid Subdivision

Primary Roads 形成多个剩余区域：

```text
Quadrant / Sector
```

每个 Sector 独立使用简化 GRID Generator：

```text
Secondary Roads
```

但必须遵循主干道路。

因此：

```text
RADIAL_GRID
```

不是独立实现一整套建筑算法。

应该复用：

```text
GridSubdivisionGenerator
```

---

# 20. 道路生成完成后 Rasterize

RoadGraph 只是逻辑图。

下一步：

```text
RoadGraph
    ↓
RoadRasterizer
    ↓
Chunk Occupancy Grid
```

例如：

```java
enum RegionCellType {
    EMPTY,
    ROAD_PRIMARY,
    ROAD_SECONDARY,
    ROAD_SERVICE,
    BUILDING,
    PLAZA,
    GREEN,
    RESERVED
}
```

生成：

```java
RegionGrid
```

---

# 21. 从道路划分 UrbanBlock

Road Rasterization 后：

```text
Region - RoadCells
```

得到剩余可用空间。

对：

```text
EMPTY Cell
```

运行 Connected Components。

每个由道路包围的连续空间称为：

```java
UrbanBlock
```

例如：

```text
BBBB│BBBB
BBBB│BBBB
────┼────
BBBB│BBBB
BBBB│BBBB
```

形成四个 UrbanBlock。

定义：

```java
record UrbanBlock(
    int id,
    Set<ChunkPos> cells,
    BoundingBox2i bounds
) {}
```

---

# 22. UrbanBlock 再划分 BuildingParcel

UrbanBlock 不等于一栋建筑。

它可以进一步划分：

```text
UrbanBlock
    ↓
BuildingParcel[]
```

例如：

```text
8 × 6 chunks
```

可以：

```text
┌───────┬───────┐
│   A   │   B   │
│       │       │
├───────┴───────┤
│       C       │
└───────────────┘
```

也可以：

```text
┌───────────────┐
│               │
│       A       │
│               │
└───────────────┘
```

具体取决于：

```text
Building Type
Region Type
Urban Density
Distance To Local Center
```

---

# 23. Building Parcel 必须为矩形

最终用于建筑生成的 Parcel 必须：

```text
Chunk aligned
+
axis aligned
+
rectangular
```

如果一个 UrbanBlock 是不规则形状：

不要直接作为建筑地块。

应使用：

```text
Rectangle Partition
```

拆成若干矩形 Parcel。

允许留下：

```text
小面积无法利用区域
```

作为：

```text
GREEN
PLAZA
SERVICE
EMPTY
```

不要强行填满。

---

# 24. 建筑不应覆盖整个 Parcel

引入：

```text
Building Setback
```

例如 Parcel：

```text
6 × 6 chunks
```

建筑可以：

```text
4 × 5
```

剩余空间用于：

```text
入口
广场
绿化
设备区
停车
装饰
```

定义：

```java
BuildingParcel {
    Rect2i area;

    Rect2i buildableArea;

    Direction roadFacing;

    int adjacentRoadId;
}
```

---

# 25. 建筑必须朝向道路

每个 BuildingParcel：

计算相邻道路。

选择：

```text
等级最高
```

或者：

```text
共享边最长
```

的道路作为建筑正面。

优先：

```text
PRIMARY
>
SECONDARY
>
SERVICE
```

得到：

```java
Direction facing;
```

建筑模板统一采用一个标准朝向，例如：

```text
NORTH
```

最终通过：

```java
Rotation
```

转成：

```text
NORTH
SOUTH
EAST
WEST
```

---

# 26. BuildingType

增加：

```java
BuildingTypeConfig {
    String id;

    int weight;

    List<BuildingFootprint> footprints;

    int minHeight;
    int maxHeight;

    Set<RegionType> allowedRegions;
}
```

建筑类型可以例如：

```text
RESIDENTIAL
COMMERCIAL
OFFICE
INDUSTRIAL
WAREHOUSE
ADMINISTRATION
UTILITY
LANDMARK
```

---

# 27. 建筑 Weight 与中心关系

和上一阶段 Region 权重类似：

```text
建筑 weight 越高
```

越倾向：

```text
Local Center
+
Primary Road
```

附近。

例如：

```text
重要商业建筑
行政建筑
大型公共建筑
```

位于中心。

而：

```text
Warehouse
Utility
普通住宅
```

更允许出现在外围。

不要单纯把 weight 当：

```text
随机出现概率
```

它同时参与：

```text
Building Type Probability
+
Centrality Score
+
Road Preference
```

---

# 28. 建筑高度也应形成层级

可以根据：

```text
distanceToLocalCenter
```

计算高度倾向。

例如：

```text
CONCENTRIC / COMMERCIAL

中心：
80~150 blocks

中部：
40~100

外围：
20~60
```

形成城市天际线：

```text
             ████
          ██ ████ ██
       ██ ██ ████ ██ ██
    ██ ██ ██ ████ ██ ██ ██
```

不要让每栋建筑高度完全随机。

---

# 29. 不同 Layout 应影响建筑分布

GRID：

```text
建筑密度相对均匀
```

CONCENTRIC：

```text
中心大型建筑
+
向外逐渐降低密度/高度
```

RADIAL_GRID：

```text
Primary Road 沿线建筑更重要、更密集
+
内部 Secondary Grid 相对普通
```

实现时不要把这些规则散落在大量 if 中。

定义：

```java
LayoutBuildingPolicy
```

统一控制。

---

# 30. Plaza / OpenSpace

不是所有可用区域都必须生成建筑。

保留：

```java
OpenSpace
```

例如：

```java
enum OpenSpaceType {
    PLAZA,
    GREEN,
    SERVICE,
    EMPTY
}
```

特别是：

```text
Region Center
Primary Road Intersection
大型建筑前方
```

可以生成广场。

例如：

```text
        ROAD
=========│=========
         │
    ┌────┴────┐
    │  PLAZA  │
    └────┬────┘
         │
    ┌────┴────┐
    │LANDMARK │
    └─────────┘
```

这会明显改善城市真实感。

---

# 31. Landmark 支持

允许 Region 配置：

```text
0 或 1 个主要 Landmark
```

Landmark 应在道路生成之前作为：

```text
Reserved Anchor
```

加入规划。

例如：

```java
LandmarkReservation {
    Rect2i desiredArea;
    int priority;
}
```

然后 Primary Road 应主动连接：

```text
Landmark Entrance
```

不要道路生成结束后再随机寻找位置塞大型建筑。

---

# 32. Building NBT 与布局系统解耦

布局生成器禁止直接加载 NBT。

正确职责：

```text
RegionLayoutGenerator
       ↓
BuildingParcel

BuildingPlanner
       ↓
BuildingPlacement

BuildingTemplateResolver
       ↓
NBT ResourceLocation

StructureTemplatePlacer
       ↓
Minecraft World
```

例如：

```java
record BuildingPlacement(
    BuildingType type,

    Rect2i footprint,

    Direction facing,

    int baseY,

    ResourceLocation template
) {}
```

`ResourceLocation template` 甚至可以由后续步骤再解析。

---

# 33. 推荐模块结构

不要把所有逻辑写在一个类中。

建议：

```text
RegionLayoutGenerator
│
├── RegionEntrancePlanner
│
├── RegionLayoutSelector
│
├── PrimaryRoadPlanner
│
├── GridRoadGenerator
│
├── ConcentricRoadGenerator
│
├── RadialGridRoadGenerator
│
├── RoadRasterizer
│
├── UrbanBlockExtractor
│
├── ParcelPartitioner
│
├── BuildingPlanner
│
├── BuildingCandidateScorer
│
└── RegionLayoutValidator
```

---

# 34. Road Layout Generator 统一接口

定义：

```java
interface RoadLayoutGenerator {

    RoadGraph generate(
        Region region,
        RegionLayoutContext context
    );
}
```

实现：

```java
GridRoadGenerator

ConcentricRoadGenerator

RadialGridRoadGenerator
```

---

# 35. ParcelPartitioner 独立

不要：

```text
GRID 有一套 Parcel 算法
CONCENTRIC 又写一套
RADIAL 再写一套
```

统一：

```text
RoadGraph
↓
RoadRasterizer
↓
UrbanBlock
↓
ParcelPartitioner
```

Layout Generator 只负责道路。

建筑区划逻辑尽量复用。

---

# 36. Building Candidate Scoring

对于每个 BuildingParcel：

生成多个候选：

```text
BuildingType
+
Footprint
+
Facing
```

计算：

```java
score =
      W_CENTER * centralityScore
    + W_ROAD * roadScore
    + W_SIZE * footprintFitScore
    + W_REGION * regionTypeScore
    + W_LAYOUT * layoutSuitabilityScore

    - W_WASTE * unusedParcelPenalty
    - W_REPEAT * repetitionPenalty;
```

选择最高分候选。

不要：

```text
随机选一个能放进去的 NBT
```

---

# 37. 避免重复建筑

维护：

```java
BuildingUsageTracker
```

如果同一个 Region 内：

```text
同一模板连续大量出现
```

增加 penalty。

例如：

```text
最近两栋都是 residential_a
```

则：

```text
residential_a score -= repetitionPenalty
```

避免明显复制粘贴感。

---

# 38. Density / Coverage

每个 Region 允许：

```java
double buildingCoverage;
```

例如：

```text
住宅区：
0.55~0.75

商业核心：
0.65~0.85

工业：
0.40~0.65

行政：
0.30~0.55
```

注意：

```text
Region Building Coverage
```

与上一阶段：

```text
City Region Coverage
```

是两个不同概念。

不要混用。

剩余空间用于：

```text
Road
Plaza
Green
Utility
Empty
```

---

# 39. Deterministic Random

所有结果必须由：

```text
World Seed
+
City ID
+
Region ID
```

确定。

例如：

```java
regionSeed =
    hash(worldSeed, cityId, regionId);
```

同一个 Region：

```text
输入相同
→ 永远得到相同布局
```

禁止：

```java
Math.random()
new Random()
System.currentTimeMillis()
```

---

# 40. Debug Snapshot

与上一阶段一样，Region 生成器必须支持调试输出。

建议记录：

```text
Stage 0
Region Boundary + Entrance

Stage 1
Local Center

Stage 2
Mandatory Primary Roads

Stage 3
Layout Candidate Roads

Stage 4
Final RoadGraph

Stage 5
Rasterized Roads

Stage 6
UrbanBlocks

Stage 7
Building Parcels

Stage 8
Building Placements

Final
Complete Region
```

每个 Snapshot 支持后续地图查看器展示。

---

# 41. Debug 信息

每个 Region 输出：

```java
RegionLayoutDebugInfo {
    int regionId;

    RegionLayoutType layoutType;

    ChunkPos localCenter;

    List<RoadEdge> roads;

    List<UrbanBlock> blocks;

    List<BuildingParcel> parcels;

    List<BuildingPlacement> buildings;

    double roadCoverage;

    double buildingCoverage;
}
```

---

# 42. Validator

生成结束必须运行：

```java
RegionLayoutValidator
```

检查：

```text
1. 所有道路位于 Region 内

2. 所有建筑位于 Region 内

3. 所有建筑 Chunk 对齐

4. Building Footprint 均为矩形

5. Building 不重叠

6. Building 不与 Road 非法重叠

7. 所有 Region Entrance 均连接到 RoadGraph

8. RoadGraph 整体连通

9. 每栋建筑至少邻接一条可访问道路

10. Road width 合法

11. Building coverage 不超过 Region 配置

12. Landmark Reservation 没有被普通建筑侵占
```

---

# 43. 失败处理

不要无限：

```text
retry
retry
retry
```

必须设置：

```java
maxCandidateAttempts
```

例如：

```text
32 / 64
```

如果某块 UrbanBlock 无法生成合适建筑：

```text
保留为 OpenSpace
```

而不是让整个 Region 生成失败。

道路连通失败则属于真正生成失败。

---

# 44. 第一版不要过度实现

本次重点只完成：

```text
Region Entrance
Local Center

GRID
CONCENTRIC
RADIAL_GRID

Primary / Secondary Roads

Road Graph
Road Rasterization

UrbanBlock

Rectangular Building Parcel

Building Facing

Building Placement Descriptor

Deterministic Seed

Debug Snapshot

Validator
```

暂时不要实现：

```text
真实交通模拟
行人系统
停车位模拟
人口模型
经济模型
复杂曲线道路
非轴对齐建筑
建筑内部房间
NBT 实际建筑内容生成
装饰细节
```

NBT Placement 可以先建立接口。

---

# 45. 当前阶段建议的核心接口

最终应形成：

```java
RegionLayout generateRegionLayout(
    Region region,
    RegionLayoutContext context
);
```

返回：

```java
class RegionLayout {

    Region region;

    RegionLayoutType layoutType;

    ChunkPos localCenter;

    RoadGraph roadGraph;

    List<UrbanBlock> urbanBlocks;

    List<BuildingParcel> parcels;

    List<BuildingPlacement> buildings;

    List<OpenSpace> openSpaces;
}
```

---

# 46. 与上一级城市生成器的关系

保持明确层级：

```text
CityLayoutGenerator
    │
    ├── Region A
    ├── Region B
    └── Region C

         ↓

RegionLayoutGenerator

         ↓

RoadGraph
    ↓
UrbanBlock
    ↓
BuildingParcel
    ↓
BuildingPlacement
```

上一级：

```text
负责 Region 如何排列
```

本模块：

```text
负责一个 Region 内部如何布局
```

不要让 RegionLayoutGenerator 修改：

```text
Region 的位置
Region 的尺寸
Region Graph
城市级道路连接关系
```

这些已经由上一层决定。

---

# 47. 最终视觉目标

GRID：

```text
┌─────────────────────────┐
│ BBB│BBBB│BBBB           │
│ BBB│BBBB│BBBB           │
│────┼────┼─────────────  │
│ BBB│BBBBBBBB│BBBB       │
│ BBB│BBBBBBBB│BBBB       │
│────┘        │           │
│ BBBBBBBBBBBB│BBBB       │
└─────────────────────────┘
```

CONCENTRIC：

```text
┌──────────────────────────┐
│ BBBBBBBBBBBBBBBBBBBBBBBB │
│ B ┌──────────────────┐ B │
│ B │BBBBBBBBBBBBBBBBBB│ B │
│ B │B ┌────────────┐ B│ B │
│───┼──┤    CORE    ├──┼───│
│ B │B └────────────┘ B│ B │
│ B │BBBBBBBBBBBBBBBBBB│ B │
│ B └──────────────────┘ B │
└──────────────────────────┘
```

RADIAL_GRID：

```text
┌───────────────────────────┐
│ BBBB│BBBBBB│BBBBBBBB      │
│ BBBB│BBBBBB│BBBBBBBB      │
│─────┼──────┼──────────    │
│ BBBB│      │BBBBBBBB      │
│═════╪═ CORE ╪══════════   │
│ BBBB│      │BBBBBBBB      │
│─────┼──────┼──────────    │
│ BBBB│BBBBBB│BBBBBBBB      │
└───────────────────────────┘
```

三个 Layout 都必须保证：

```text
建筑 Chunk 对齐
道路 Chunk 对齐
Entrance 连通
RoadGraph 连通
建筑朝向道路
存在空地
避免完全填满
同 Seed 可复现
```

---

# 最终设计原则

请始终遵守以下原则：

## 1. Road First

```text
先道路
后建筑
```

---

## 2. Graph First

道路首先是：

```text
RoadGraph
```

然后才 Rasterize 为实际 Chunk。

---

## 3. Layout Only Controls Roads

```text
GRID
CONCENTRIC
RADIAL_GRID
```

主要负责：

```text
道路拓扑结构
```

而不是分别实现三套建筑生成器。

---

## 4. Parcels Are Layout Independent

统一流程：

```text
Road
↓
UrbanBlock
↓
Parcel
↓
Building
```

---

## 5. Connectivity Is a Hard Constraint

```text
所有 Entrance
所有 Primary Roads
整个 RoadGraph
```

必须连通。

---

## 6. Randomness Only Produces Variation

随机只能影响：

```text
道路间距
支路选择
建筑类型
建筑尺寸
局部偏移
是否保留某条次级道路
```

不能决定：

```text
基本连通性
坐标对齐
道路合法性
建筑是否越界
```

---

## 7. Minecraft Constraints First

所有设计首先考虑：

```text
Chunk Alignment
Axis Alignment
NBT Placement
Structure Generation
Chunk Streaming
```

不要为了理论上的城市规划复杂度破坏 Minecraft 的生成约束。

---

# 最终目标

最终系统应形成清晰的三级城市规划架构：

```text
City
│
├── Region Layout
│      │
│      ├── GRID
│      ├── CONCENTRIC
│      └── RADIAL_GRID
│
├── Road Graph
│
├── Urban Blocks
│
├── Building Parcels
│
└── Building Placements
```

其中：

```text
City Generator
```

负责：

> 哪些 Region 存在以及 Region 如何连接。

```text
Region Layout Generator
```

负责：

> Region 内部的道路与街区。

```text
Building Planner
```

负责：

> 每个 Building Parcel 上应该放什么建筑。

```text
Building Template Resolver
```

负责：

> 最终具体使用哪个 NBT。

优先保持这几个层级之间的职责边界清晰，避免将道路规划、建筑选择和 NBT 放置耦合到同一个生成类中。

开始编码前，先阅读项目当前 Region、城市道路、地块、随机种子、Geometry 和 StructureTemplate 相关实现；基于现有代码给出最小修改方案，然后再实现。不要为了该功能重写无关架构。
