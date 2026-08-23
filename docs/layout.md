# 任务：重构移动城市内部地块生成逻辑

当前项目为 Minecraft 1.21.1 的程序化世界/城市生成系统。

现在已经能够得到每个城市的二维 `Polygon` 边界。需要重新设计城市内部的“移动地块（Mobile Plot）”生成算法。

不要采用 Voronoi、BSP 全覆盖或纯随机矩形装箱。

目标是实现一个：

**基于 Chunk 网格、中心聚集、道路连通、带面积限制和类型约束的正交城市生长算法。**

重点是布局质量、确定性、可扩展性和后续世界生成兼容性。

---

# 1. 基本坐标约束

Minecraft 一个 Chunk 为：

```text
16 × 16 blocks
```

所有移动地块必须完全 Chunk 对齐。

要求：

```text
minX % 16 == 0
minZ % 16 == 0

width  % 16 == 0
length % 16 == 0
```

内部规划算法尽量直接使用：

```text
Chunk Coordinate
```

而不是 Block Coordinate。

例如：

```text
Grid Cell = 1 Chunk = 16×16 blocks
```

最后生成世界内容时再转换：

```java
blockX = chunkX * 16;
blockZ = chunkZ * 16;
```

---

# 2. 城市输入

每个城市至少具有：

```java
CityLayoutConfig {
    Polygon2D boundary;

    long seed;

    int maxPlotCount;

    double maxPlotCoverage;

    List<PlotTypeConfig> plotTypes;
}
```

其中：

```text
maxPlotCount
```

限制该城市最多生成多少个移动地块。

例如：

```text
maxPlotCount = 12
```

---

```text
maxPlotCoverage
```

限制移动地块占整个城市 Polygon 可用面积的比例。

例如：

```text
0.45
```

表示：

> 所有移动地块面积之和不得超过城市内部可用面积的 45%。

默认 coverage **只计算移动地块本身面积，不计算道路面积**。

实现时将此行为封装，方便未来支持：

```java
includeRoadAreaInCoverage
```

之类配置。

---

# 3. Plot Type

每一种移动地块定义为：

```java
PlotTypeConfig {
    String id;

    int weight;

    List<PlotSize> allowedSizes;

    int minCount;
    int maxCount;
}
```

其中：

```text
weight
```

既影响生成优先级，也影响空间位置。

**权重越高的地块越应该靠近城市中心。**

例如：

```text
CORE            weight = 100
COMMAND         weight = 90
COMMERCIAL      weight = 70
INDUSTRIAL      weight = 50
RESIDENTIAL     weight = 40
LOGISTICS       weight = 30
```

不要把 `weight` 单纯作为随机概率。

它至少参与：

1. 初始生成顺序；
2. 候选位置评分；
3. 与城市中心距离的期望值。

---

# 4. 每种地块至少存在一个

这是硬约束。

对于：

```text
plotTypes
```

中的每一个类型：

```text
至少生成 1 个
```

或者：

```java
count >= minCount
```

因此生成必须分成两个阶段：

```text
Phase A: Mandatory Placement
Phase B: Optional Growth
```

---

## Phase A

首先确保：

```text
每一种 PlotType 都生成 minCount 个
```

优先按照：

```text
weight 从高到低
```

生成。

原因：

> 高权重类型应该优先获得城市核心附近的优质空间。

如果连所有 mandatory plot 都无法放下：

```text
不要静默跳过
```

而应该返回明确失败信息：

```java
LayoutGenerationResult {
    boolean success;
    FailureReason reason;
}
```

例如：

```text
CITY_TOO_SMALL
MANDATORY_PLOTS_CANNOT_FIT
INVALID_CONFIGURATION
```

---

# 5. 城市 Polygon 栅格化

首先把城市 Polygon 转换为 Chunk Grid。

例如：

```text
Polygon
    ↓
Chunk rasterization
```

一个 Chunk Cell 只有在满足规定条件时才属于城市可用区域。

建议支持：

```text
CELL_CENTER_INSIDE
```

或者更严格：

```text
CELL_FULLY_INSIDE
```

优先使用后者生成大型移动地块，避免结构越过 Polygon 边界。

生成：

```java
CityGrid
```

每个 Cell 至少包含：

```java
CityCell {
    int chunkX;
    int chunkZ;

    boolean usable;

    double boundaryDistance;
}
```

---

# 6. 城市中心不能简单使用 Polygon Centroid

计算：

```text
Distance To Boundary Field
```

寻找距离 Polygon 边界最远的 Chunk Cell：

```java
cityCore =
    argmax(distanceToBoundary);
```

它近似于：

```text
Pole of Inaccessibility
```

将其作为：

```text
City Core / Urban Core
```

而不是直接使用几何 centroid。

这样对于凹多边形和细长 Polygon 更稳定。

---

# 7. 所有移动地块必须是轴对齐矩形

定义：

```java
UrbanPlot {
    PlotType type;

    int minChunkX;
    int minChunkZ;

    int widthChunks;
    int lengthChunks;
}
```

禁止旋转到非 X/Z 轴方向。

只允许：

```text
X axis
Z axis
```

方向。

允许长宽互换：

```text
4 × 8 chunks
8 × 4 chunks
```

但仍然必须轴对齐。

---

# 8. 地块尺寸必须来自离散集合

不要随机产生：

```text
237 × 183 blocks
```

这种尺寸。

所有尺寸直接按 Chunk 数定义：

```java
PlotSize {
    int widthChunks;
    int lengthChunks;
}
```

例如：

```text
4×4
6×4
8×6
8×8
12×8
16×12
```

对应：

```text
64×64
96×64
128×96
128×128
192×128
256×192 blocks
```

这样保证：

* Minecraft Chunk 对齐；
* NBT 分片简单；
* 世界生成简单；
* 道路生成简单；
* 调试地图简单。

---

# 9. 地块之间必须保留恰好至少一个 Chunk 的道路空间

移动地块不能直接接触。

两个逻辑相邻的 Plot 之间至少存在：

```text
1 Chunk = 16 blocks
```

的道路带。

例如：

```text
┌───────────────┐
│    Plot A     │
└───────────────┘

█████████████████  ← 1 Chunk Road

┌───────────────┐
│    Plot B     │
└───────────────┘
```

在 Grid 上即：

```text
AAAA
AAAA
....

RRRR
RRRR

BBBB
BBBB
....
```

其中：

```text
A/B = Plot
R   = Road
```

推荐配置：

```java
roadWidthChunks = 1;
```

暂时固定为：

```text
1
```

但架构上不要写死，方便以后改为 2 Chunk。

---

# 10. 地块连通性由“生长过程”保证

不要：

```text
随机生成所有矩形
→ 最后检测是否连通
```

而应该使用：

```text
Connected Growth
```

每一个新 Plot 必须通过道路连接到：

```text
至少一个已有 Plot / Road Network
```

因此布局从一开始就是连通的。

基本过程：

```text
Primary Plot
      ↓
Road
      ↓
Plot
      ↓
Road
      ↓
Plot
```

维护：

```java
UrbanGraph
```

其中：

```java
PlotNode
RoadEdge
```

保证最终：

```text
UrbanGraph is connected
```

---

# 11. 使用 Frontier Growth，而不是随机撒点

维护当前城市可以继续扩张的位置：

```java
UrbanFrontier
```

例如：

```java
UrbanFrontier {
    UrbanPlot source;

    Direction direction;

    Segment availableEdge;
}
```

方向只能：

```text
NORTH
SOUTH
WEST
EAST
```

生成新的 Plot 时：

```text
Existing Plot
     ↓
1 Chunk Road
     ↓
Candidate Plot
```

候选地块必须从已有城市边缘向外生长。

这样天然形成：

```text
集中
连续
逐渐扩张
```

的城市形态。

---

# 12. 高权重 Plot 优先靠近中心

这是重要规则。

对候选 Plot 计算：

```text
distanceToCityCore
```

并结合 PlotType.weight 形成：

```text
centralityScore
```

例如将 weight 归一化：

```java
normalizedWeight ∈ [0,1]
```

权重越高：

```text
期望距离中心越小
```

概念上：

```java
desiredRadius =
    maxUrbanRadius * (1.0 - normalizedWeight);
```

候选位置距离该期望 radius 越接近，得分越高。

或者简化为：

```java
centralityScore =
    normalizedWeight
    * proximityToCenter;
```

但避免所有类型都强行堆在同一个点。

应该允许：

```text
高权重 → 核心
中权重 → 内城
低权重 → 外围
```

形成明显的城市层级。

---

# 13. Candidate Scoring

每次生成新地块：

```text
不要只生成一个候选位置
```

建议：

```text
每次生成 N 个 Candidate
```

例如：

```java
candidateCount = 16;
```

然后评分，选择最高分。

评分可以设计为：

```java
score =
      W_CENTER      * centralityScore
    + W_ADJACENCY   * adjacencyScore
    + W_COMPACTNESS * compactnessScore
    + W_BOUNDARY    * boundaryClearanceScore
    + W_ROAD        * roadQualityScore
    + W_TYPE        * typeSuitabilityScore

    - W_ELONGATION  * elongationPenalty
    - W_EDGE        * edgePenalty
    - W_FRAGMENT    * fragmentationPenalty;
```

其中：

### centralityScore

根据：

```text
Plot Type weight
+
距离 City Core
```

计算。

---

### adjacencyScore

新 Plot 与现有城市的有效连接越好越高。

例如：

```text
更长的道路接口
```

优于：

```text
只有一个 Chunk 宽的很小接口
```

---

### compactnessScore

鼓励城市保持集中。

避免：

```text
████
   █
   █
   ███████████
```

这种细长布局。

可以根据：

```text
新布局 bounding box
area / perimeter²
```

或其他 compactness 指标进行近似评分。

---

### boundaryClearanceScore

距离 Polygon 边界越远越优先。

核心城市应该倾向城市内部。

外围自然地形留给：

```text
山地
森林
河流
自然环境
```

---

### roadQualityScore

道路连接尽量：

```text
短
直
轴对齐
避免死胡同过多
```

---

# 14. 必须限制城市面积覆盖率

定义：

```java
plotCoverage =
    totalPlotChunkArea
    / totalUsableCityChunkArea;
```

要求：

```java
plotCoverage <= maxPlotCoverage
```

例如：

```text
maxPlotCoverage = 0.45
```

则最多：

```text
45%
```

城市 Polygon 被移动地块覆盖。

剩余区域必须保留给：

```text
道路
平原
山脉
河流
森林
城市绿地
自然地形
```

不要尝试使用矩形填满整个 Polygon。

---

# 15. maxPlotCount 与 Coverage 双重终止

Optional Growth 阶段继续生成直到满足任一条件：

```text
plotCount >= maxPlotCount
```

或者：

```text
继续加入任何合法 Plot 都会超过 maxPlotCoverage
```

或者：

```text
没有合法 Candidate
```

然后停止。

因此：

```text
maxPlotCount
```

和：

```text
maxPlotCoverage
```

都是硬上限。

---

# 16. Optional Plot Type 选择

完成每种类型的 Mandatory Placement 后，再进行普通生长。

此时类型选择可以使用：

```text
weight
```

作为概率因子之一。

但是需要考虑：

```text
maxCount
```

以及：

```text
当前该类型已经生成多少
```

避免某一种高 weight 类型占据整个城市。

建议计算：

```java
effectiveWeight =
    config.weight
    * remainingCapacityFactor
    * cityZoneSuitability;
```

---

# 17. 保留自然区域

非常重要：

移动城市 Polygon 并不等于：

```text
全部铺移动设施
```

最终应该类似：

```text
^^^^^^^^^^^^^^^^^^^^^^^^
^^^^              ^^^^^^
^^                  ^^^^

       ┌───────┐
       │ PlotA │
       └───┬───┘
           │ road
   ┌───────┴───────┐
   │     PlotB     │
   └───────┬───────┘
           │
      ┌────┴─────┐
      │  PlotC   │
      └──────────┘

^^^                 ^^^^
^^^^^^          ^^^^^^^^
```

外围及内部未利用的大块区域之后交给：

```text
Terrain Planner
```

生成：

```text
Mountain
Foothill
River
Natural Plain
Forest
```

---

# 18. 道路网络

布局生成器同时输出：

```java
List<UrbanRoad>
```

不要等后面再从 Plot 猜道路。

每次：

```text
Parent Plot
→ Candidate Plot
```

被接受时，同时创建：

```java
RoadEdge
```

道路宽度：

```text
1 Chunk
```

道路必须轴对齐。

如果两个已有 Road 可以非常容易连成环：

```text
允许添加少量额外 Road Edge
```

以避免整个城市道路网络完全是一棵树。

推荐：

```text
MST-like connected backbone
+
small number of loops
```

但是不要生成规则棋盘。

---

# 19. 城市整体应具有中心密集、外围稀疏的趋势

通过以下因素自然实现：

```text
高 weight 类型靠中心
+
距离边界 penalty
+
compactness score
+
frontier growth
+
coverage limit
```

不要强制做成完美同心圆。

需要保留：

```text
随机性
不对称性
局部空地
```

例如不同 seed 下：

```text
East 更发达
West 留山地
```

或者：

```text
North 是工业区
South 是住宅区
```

都应该可能发生。

---

# 20. 随机性必须 deterministic

所有布局生成必须只依赖：

```text
World Seed
+
City ID / City Seed
```

同一 seed：

```text
永远生成相同布局
```

禁止使用：

```java
new Random()
Math.random()
System.currentTimeMillis()
```

之类非确定随机源。

统一通过项目已有随机系统。

---

# 21. 推荐数据结构

设计类似：

```java
class MobileCityLayout {
    Polygon2D boundary;

    ChunkPos cityCore;

    List<UrbanPlot> plots;

    List<UrbanRoad> roads;

    UrbanGraph graph;

    double coverage;
}
```

---

```java
record UrbanPlot(
    int id,

    PlotType type,

    int minChunkX,
    int minChunkZ,

    int widthChunks,
    int lengthChunks
) {}
```

---

```java
record UrbanRoad(
    int fromPlotId,
    int toPlotId,

    Rect2i chunkArea
) {}
```

---

```java
record PlotSize(
    int widthChunks,
    int lengthChunks
) {}
```

---

```java
class PlotTypeConfig {
    String id;

    int weight;

    List<PlotSize> allowedSizes;

    int minCount;
    int maxCount;
}
```

---

# 22. 推荐实现模块

不要把算法全部塞进一个类。

拆分为：

```text
MobileCityLayoutGenerator
    │
    ├── CityGrid
    │
    ├── CityCoreFinder
    │
    ├── PlotCandidateGenerator
    │
    ├── PlotCandidateScorer
    │
    ├── UrbanGrowthPlanner
    │
    ├── RoadPlanner
    │
    └── LayoutValidator
```

职责：

### MobileCityLayoutGenerator

负责整个生成流程。

### CityGrid

负责：

```text
Polygon → Chunk Grid
```

以及：

```text
空间占用查询
```

### CityCoreFinder

负责：

```text
distance field
city core
```

### PlotCandidateGenerator

根据：

```text
Frontier
PlotType
AllowedSize
```

生成候选矩形。

### PlotCandidateScorer

只负责评分。

不要在 scorer 内直接修改布局。

### UrbanGrowthPlanner

管理：

```text
mandatory phase
optional phase
frontiers
```

### RoadPlanner

创建和维护道路网络。

### LayoutValidator

最终检查所有硬约束。

---

# 23. 空间查询不要 O(N²) 暴力扫描

由于所有东西已经 Chunk Grid 化：

直接维护：

```text
OccupancyGrid
```

Cell 状态例如：

```java
enum CellState {
    OUTSIDE,
    EMPTY,
    PLOT,
    ROAD,
    RESERVED
}
```

候选矩形是否合法可以通过 Grid 快速判断。

如果地图未来非常大，再优化：

```text
prefix sum
spatial index
```

但第一版优先保证架构清晰。

---

# 24. 最终 LayoutValidator 必须检查

生成结束后运行完整验证。

要求：

```text
1. 所有 Plot 位于 City Polygon 内

2. 所有 Plot 坐标 Chunk 对齐

3. 所有 Plot 长宽均为 Chunk 倍数

4. Plot 之间不存在重叠

5. Plot 之间满足至少 1 Chunk 道路间隔

6. Road 不与 Plot 非法重叠

7. 所有 Plot 通过 RoadGraph 连通

8. 每种 PlotType：
   count >= minCount

9. 每种 PlotType：
   count <= maxCount

10. totalPlotCount <= maxPlotCount

11. plotCoverage <= maxPlotCoverage

12. 高 weight Plot 整体上应比低 weight Plot 更靠近中心
    此项作为统计质量检测，而不是绝对硬约束
```

---

# 25. 输出调试信息

项目之后需要在地图调试器中观察每一步布局变化。

因此生成器不要只输出最终结果。

增加可选：

```java
LayoutDebugCollector
```

记录：

```text
Stage 0
City Polygon / Chunk Grid

Stage 1
Distance Field / City Core

Stage 2
Mandatory Plot 1

Stage 3
Mandatory Plot 2

...

Stage N
Optional Growth

Final
Plots + Roads + Remaining Terrain
```

每一步至少能够导出：

```text
Plot rectangles
Road rectangles
City core
Candidate score
Rejected candidate reason
Current coverage
Current plot count
```

不要让 Debug 系统和核心算法强耦合。

使用：

```java
DebugEvent / Snapshot
```

形式暴露。

---

# 26. Candidate Reject Reason

为了之后方便调算法，候选失败必须区分原因，例如：

```java
enum CandidateRejectReason {
    OUTSIDE_CITY,
    OVERLAPS_PLOT,
    OVERLAPS_ROAD,
    INVALID_ROAD_GAP,
    COVERAGE_LIMIT,
    TYPE_MAX_COUNT,
    INVALID_SIZE,
    NO_CONNECTION,
    RESERVED_TERRAIN
}
```

Debug Viewer 可以统计：

```text
为什么某个城市最后只有 8 个 Plot？
```

而不是只得到：

```text
placement failed
```

---

# 27. 算法整体流程

最终实现流程：

```text
City Polygon
        ↓
Rasterize to Chunk Grid
        ↓
Calculate Distance To Boundary
        ↓
Find City Core
        ↓
Initialize Occupancy Grid
        ↓

──────────────────────────
Mandatory Placement
──────────────────────────

PlotTypes sort by weight DESC
        ↓
Generate minCount for every type
        ↓
Candidate Generation
        ↓
Candidate Scoring
        ↓
Place Plot
        ↓
Create 1-Chunk Road
        ↓
Update Frontier

──────────────────────────
Optional Growth
──────────────────────────

while:
    plotCount < maxPlotCount
    &&
    coverage < maxPlotCoverage

        ↓

Select PlotType
        ↓
Select Frontier
        ↓
Generate N Candidates
        ↓
Filter hard constraints
        ↓
Score candidates
        ↓
Choose best valid candidate
        ↓
Place Plot + Road
        ↓
Update Occupancy / Graph / Frontier

──────────────────────────
Finalize
──────────────────────────

Optionally Add Road Loops
        ↓
Validate Layout
        ↓
Output MobileCityLayout
```

---

# 28. 一个必须注意的问题

不要把：

```text
weight
```

实现成：

```java
random.nextInt(totalWeight)
```

然后认为任务完成。

本项目中的 weight 具有明显空间意义：

> weight 越高，越应该优先位于中心。

因此应该同时用于：

```text
Placement Order
+
Spatial Scoring
+
Optional Generation Probability
```

三个地方。

---

# 29. 不要过度设计

本次先实现：

```text
轴对齐矩形
Chunk Grid
中心聚集
1 Chunk Road
Connected Growth
Type Weight
Mandatory Type
Plot Count Limit
Coverage Limit
Deterministic Seed
Debug Snapshot
```

暂时不要实现：

```text
复杂交通模拟
人口模拟
经济模拟
真实城市土地价格模型
道路交通流
复杂 GIS
建筑内部布局
山脉生成
河流生成
```

自然地形只需要保留：

```text
RemainingArea / TerrainReservedArea
```

接口，供后续 Terrain Generator 使用。

---

# 30. 修改现有代码要求

先阅读当前项目中：

```text
City
MobileCity
Plot
Region
Polygon
World Generation
Structure
Random/Seed
```

相关实现。

优先复用已有：

```text
geometry
random
serialization
registry
worldgen
```

基础设施。

不要为了实现本功能大规模重构无关代码。

如果当前已有地块生成器：

```text
保留 public API 尽可能兼容
```

内部替换为新的：

```text
Connected Orthogonal Urban Growth
```

模型。

首先给出：

```text
1. 当前相关代码结构分析
2. 推荐修改范围
3. 数据结构设计
4. 生成算法设计
5. 再开始编码
```

但分析应围绕当前项目实际代码，不要进行脱离项目的架构发散。

---

# 最终目标

最终生成结果应满足：

```text
            自然地形
      ^^^^^^^^^^^^^^^^^

             ┌──────┐
             │高权重│
             │ Plot │
             └───┬──┘
                 │
             1 Chunk Road
                 │
       ┌─────────┴─────────┐
       │                   │
   ┌───┴────┐          ┌───┴─────┐
   │ Plot B │          │ Plot C  │
   └───┬────┘          └─────────┘
       │
   1 Chunk Road
       │
   ┌───┴──────┐
   │低权重Plot│
   └──────────┘


^^^^^^                     ^^^^^^^
          自然地形
```

整体必须体现：

* 高权重地块集中于城市核心；
* 低权重地块逐渐向外围分布；
* 所有地块均为 Chunk 对齐矩形；
* 所有地块之间通过至少 1 Chunk 宽道路连接；
* 城市道路网络整体连通；
* 每种地块至少生成一个；
* 地块总数不超过城市配置上限；
* 地块总面积不超过覆盖率上限；
* 不填满 Polygon；
* 保留足够大且连续的自然地形区域；
* 不生成规则棋盘；
* 不随机散落矩形；
* 同一个 Seed 必须完全可复现。
