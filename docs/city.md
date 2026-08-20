> 项目适配说明：本文是初始方案原稿。结合 Zinecraft 的证据、逐城资产和“禁止统一建筑生成”要求后形成的正式设计见
> [`architecture/CITY_GENERATION_PIPELINE.md`](architecture/CITY_GENERATION_PIPELINE.md)。正式方案保留共享几何规划器，但不允许用统一
`CityTheme` 自动制造十九国建筑。

可以，而且你的需求非常适合做成一套**程序化城市布局器**，而不是继续依赖单棵 Jigsaw 树。

对于 **MC 1.21.1 NeoForge**，我建议把城市分成：

```text
CityStructure
    ↓
CityPlanner                // 只负责“规划”
    ├── LandmarkPlanner    // 地标
    ├── RoadPlanner        // 道路
    ├── DistrictPlanner    // 城区/功能区
    └── LotPlanner         // 地块
            ↓
CityAssembler              // 负责“实际生成”
    ├── LandmarkPiece
    ├── RoadPiece
    ├── BuildingPiece
    └── DecorationPiece
```

其中 **Jigsaw 降级为局部建筑内部的拼装工具**，而不是负责整座城市。

NeoForge 1.21.1 本身把 `structure` / `worldgen` 等内容作为 datapack 世界生成资源加载，所以完全可以保留大量 NBT 模板，同时由
Java 代码负责更高一级的城市规划。开发环境也能直接查看原版 worldgen/structure 资源作为参考。([NeoForged 文档][1])

---

# 1. 我最推荐的城市生成流程

不要：

```text
中心 Jigsaw
   ↓
不断向外随机拼
   ↓
希望最后变成城市
```

而是：

```text
确定城市范围
      ↓
放置大型地标
      ↓
生成主干道路
      ↓
生成次级道路
      ↓
道路切割成街区
      ↓
街区切割成住宅地块
      ↓
选择 NBT 建筑填充
      ↓
路灯/植被/围墙/小物件
```

这其实很接近真正的“城市规划”。

假设你生成一个：

```text
800 × 800
```

的城市，可以先生成一份纯数据：

```java
CityPlan {
    BlockPos center;

    List<Landmark> landmarks;
    List<Road> roads;
    List<District> districts;
    List<CityBlock> blocks;
    List<BuildingLot> lots;
}
```

这里**完全不要放 Minecraft Block**。

它只是二维/2.5D 几何数据。

例如：

```java
record BuildingLot(
    BoundingBox area,
    Direction entrance,
    BuildingType type
) {}
```

这样城市规模即便做到：

```text
500 × 500
1000 × 1000
2000 × 2000
```

规划算法本身也不会受到 Jigsaw 128 格范围的约束。

---

# 2. 第一步：大型地标先占坑

这是最重要的一步。

比如你的城市拥有：

```text
        [城门]

          │
          │

[工业区]─┼────[大型中央地标]────┼─[商业区]
          │
          │
       [车站]

                [军事基地]
```

不要先造住宅。

应该：

```java
placeLandmarks();
generateMainRoads();
generateSecondaryRoads();
fillResidentialLots();
```

## 地标定义

例如：

```java
public record LandmarkDefinition(
    ResourceLocation template,
    int width,
    int depth,
    LandmarkType type,
    int clearance
) {}
```

例如一个大型行政中心：

```text
建筑：

120 × 80

实际保留：

160 × 120
```

也就是说：

```text
XXXXXXXXXXXXXXXXXXXXXXXX
X                      X
X     ███████████      X
X     █ Landmark █      X
X     ███████████      X
X                      X
XXXXXXXXXXXXXXXXXXXXXXXX
```

外面额外留出：

* 广场
* 道路
* 花园
* 台阶
* 围墙

不要让住宅紧贴大型地标。

---

# 3. 给每个地标设置“道路接口”

这个设计会让你的城市非常自然。

不要让道路规划器猜建筑入口在哪。

每个地标直接定义：

```java
LandmarkPort {
    BlockPos relativePosition;
    Direction direction;
    RoadClass roadClass;
}
```

比如：

```text
                   North Gate
                       ↓
                ┌────────────┐
                │            │
West Gate →     │   地标     │     ← East Gate
                │            │
                └────────────┘
                       ↑
                   South Gate
```

行政中心可能有：

```text
north -> MAIN_ROAD
south -> MAIN_ROAD
east  -> SECONDARY_ROAD
west  -> SECONDARY_ROAD
```

火车站：

```text
front -> MAIN_ROAD
side  -> SECONDARY
```

普通住宅：

```text
front -> LOCAL_ROAD
```

于是道路系统天然知道应该怎么连接。

---

# 4. 主干道路：不要随机走

我推荐采用：

> **Landmark Graph + A***

首先把这些东西当成 Graph Node：

```text
CityCenter
Landmark A
Landmark B
Landmark C
CityGate N
CityGate S
CityGate E
CityGate W
```

例如：

```text
Gate N
   |
   |
Landmark A
   |
   |
City Center -------- Landmark B
   |
   |
Station
   |
Gate S
```

然后给它们建立道路 Graph：

```java
class RoadGraph {
    List<RoadNode> nodes;
    List<RoadEdge> edges;
}
```

---

# 5. 地形参与道路寻路

如果你的城市不是完全平坦，那么不要简单：

```java
line(start, end)
```

而应该使用 A*。

每个候选格子的 cost：

```java
cost =
      distance
    + slopePenalty
    + waterPenalty
    + cliffPenalty
    + buildingCollisionPenalty;
```

例如：

```java
float cost(Node a, Node b) {

    int dy = Math.abs(
        terrainHeight(a.x(), a.z())
        - terrainHeight(b.x(), b.z())
    );

    return 1.0f
        + dy * 4.0f
        + waterPenalty(b)
        + obstaclePenalty(b);
}
```

那么：

```text
山

          █████
       █████████
A ─────█       █──── B
       █       █
```

道路可能自动变成：

```text
          █████
       █████████
A ────╮█       █╭── B
      ╰─────────╯
```

而不是硬穿山。

---

# 6. 但“城市道路”不要逐格 A*

这是性能和美术效果上的一个关键点。

推荐规划网格：

```text
4×4
8×8
甚至 16×16 block
```

例如：

```text
一个 RoadNode

= 8 × 8 Minecraft blocks
```

如果城市是：

```text
1024 × 1024
```

那么寻路空间从：

```text
1024²
= 1,048,576
```

下降到：

```text
128²
= 16,384
```

差别很大。

最后再把粗路径：

```text
o---o
    |
    o---o
```

拟合成：

```text
直道
弯道
十字路口
T 路口
桥梁
坡道
```

---

# 7. 主路生成后，再生成支路

这部分特别符合你说的：

> “其余住宅按道路延伸。”

我的建议是：

## 一级道路

连接：

```text
地标
城市中心
城门
车站
```

宽：

```text
11~21 blocks
```

例如：

```text
人行道  道路          道路  人行道
███ █████████████████ ███
```

---

## 二级道路

每隔：

```text
40~80 blocks
```

从主路向外产生。

例如：

```text
========================= 主路
        |          |
        |          |
        |          |
        |          |
```

然后：

```text
=========================
       |           |
-------+-----------+-------
       |           |
       |           |
-------+-----------+-------
```

形成街区。

---

# 8. 我更推荐“递归道路生长”

这是你的需求中特别值得做的部分。

从已有道路随机选择一个点：

```java
growRoad(parentRoad, direction, length);
```

规则：

```text
MAIN
 ↓
SECONDARY
 ↓
LOCAL
 ↓
DEAD_END
```

比如：

```text
========================== MAIN
      |             |
      |             |
------+-------      +-------
      |      |      |
      |      |      |
      |      |      |
```

但是**不是完全随机**。

概率可以是：

```java
if (nearLandmark)
    roadDensity *= 1.5;

if (nearCityEdge)
    roadDensity *= 0.5;

if (terrainTooSteep)
    stop();

if (hitsExistingRoad)
    makeIntersection();

if (tooCloseToParallelRoad)
    stop();
```

这样出来就很像城市。

---

# 9. 道路生成后，住宅其实非常简单

这时不用“寻找住宅位置”。

直接沿道路创建 Lot。

例如道路：

```text
================================

↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑
住宅入口

↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
住宅入口

================================
```

算法：

```java
for (RoadSegment road : roads) {

    generateLots(
        road,
        Side.LEFT
    );

    generateLots(
        road,
        Side.RIGHT
    );
}
```

---

# 10. Lot 是整个建筑系统的关键

例如：

```text
道路
================================

┌─────┐ ┌───────┐ ┌────┐
│15x20│ │ 20x24 │ │12x18
│     │ │       │ │
└─────┘ └───────┘ └────┘
```

你的 NBT 不应该定义：

```text
建筑必须位于 X=xxx Z=xxx
```

而应该声明：

```text
house_small_01

Footprint:
12 × 16

Entrance:
SOUTH

AllowedLot:
>= 14 × 18
```

规划器找到：

```text
18×24 lot
```

就可以：

```java
BuildingDefinition building =
    registry.findBestFit(lot);
```

---

# 11. 建筑模板应该做成“标签化”

例如：

```java
BuildingDefinition {
    ResourceLocation id;

    int width;
    int depth;

    BuildingCategory category;

    Set<DistrictType> districts;

    int weight;

    int minRoadLevel;
    int maxRoadLevel;
}
```

例如：

```text
victoria_house_01
victoria_house_02
victoria_house_03

victoria_shop_01
victoria_shop_02

victoria_factory_01

victoria_mansion_01
```

然后：

```text
Residential district

70% HOUSE
15% SHOP
10% APARTMENT
5% SPECIAL
```

最终城市就不会重复成：

```text
房 房 房 房 房
房 房 房 房 房
房 房 房 房 房
```

---

# 12. 地标会影响周围建筑

这是我非常建议你加入的一层。

例如：

```text
          Landmark

       ███████████
       ███████████

           plaza

============================

 shop shop shop shop shop

 apartment apartment

 house house house
```

距离地标：

```text
0~50
```

生成：

```text
广场
行政建筑
商业
大型住宅
```

距离：

```text
50~150
```

生成：

```text
公寓
商铺
普通城区
```

距离：

```text
150+
```

生成：

```text
住宅
仓库
农田
郊区
```

于是城市会产生明显的：

> **城市中心 → 城区 → 郊区**

层次感。

---

# 13. 可以引入 District

最终你的规划模型最好变成：

```text
City
│
├─ GovernmentDistrict
│
│   ├─ Landmark
│   ├─ Plaza
│   └─ Office
│
├─ CommercialDistrict
│
│   ├─ Shops
│   ├─ Apartments
│   └─ Station
│
├─ ResidentialDistrict
│
│   └─ Houses
│
└─ IndustrialDistrict
    ├─ Factory
    ├─ Warehouse
    └─ Logistics
```

然后：

```java
enum DistrictType {
    GOVERNMENT,
    COMMERCIAL,
    RESIDENTIAL,
    INDUSTRIAL,
    MILITARY,
    SLUM,
    OUTSKIRT
}
```

这对于不同国家的城市风格尤其有价值。

---

# 14. 你的情况，我会采用这种生成顺序

完整流程：

```text
                 City Seed
                     │
                     ▼
              Choose City Center
                     │
                     ▼
               Sample Terrain
                     │
                     ▼
            ┌─────────────────┐
            │ Place Landmarks │
            └─────────────────┘
                     │
                     ▼
              Landmark Graph
                     │
                     ▼
             Generate Main Roads
                     │
                     ▼
          Generate Secondary Roads
                     │
                     ▼
              Detect City Blocks
                     │
                     ▼
                Split Lots
                     │
                     ▼
             Assign Districts
                     │
                     ▼
             Select Buildings
                     │
                     ▼
          Generate Structure Pieces
                     │
             ┌───────┴────────┐
             ▼                ▼
         NBT Template       Jigsaw
             │                │
             └───────┬────────┘
                     ▼
                Minecraft
```

---

# 15. Jigsaw 应该放在哪里？

还是非常有用。

但只用于：

### 大型地标内部

例如：

```text
RhodesIsland HQ
   │
   ├── entrance
   ├── lobby
   ├── office
   ├── corridor
   ├── dormitory
   └── core
```

可以 Jigsaw。

### 大型公寓

```text
entrance
    |
corridor
 ├ room
 ├ room
 ├ stairs
 └ room
```

可以 Jigsaw。

### 工厂

```text
factory_core
├ warehouse
├ pipe_room
├ production
└ power
```

也可以。

但：

```text
整个城市
```

不要 Jigsaw。

---

# 16. 一个非常重要的实现细节：城市规划必须 deterministic

例如：

```java
long citySeed =
    worldSeed
    ^ cityCenter.asLong()
    ^ CITY_SALT;
```

然后：

```java
RandomSource random =
    RandomSource.create(citySeed);
```

这样：

```text
先生成 Chunk A
```

和：

```text
先生成 Chunk B
```

最终得到的城市必须完全一样。

**绝对不能依赖“哪个区块先被加载”。**

否则玩家从不同方向进入城市：

```text
西 → 东
```

和：

```text
东 → 西
```

可能生成两个不同的道路布局。

---

# 17. 更不要在世界生成线程强制读取远端 Chunk

例如不要为了规划整个城市直接：

```java
serverLevel.getChunk(x, z);
```

然后一路获取几百个 chunk。

NeoForge 官方仓库的一个 1.21.1 世界生成死锁案例中，维护者指出，世界生成线程等待 `ServerLevel` 获取 chunk
可能与服务器线程互相等待，并建议世界生成代码使用传入的 `WorldGenRegion` 范围，而不是强制获取额外 chunk。([GitHub][2])

所以：

```text
Planner
```

尽量只使用：

```text
seed
noise
预计算高度
city parameters
```

产生规划。

实际：

```text
NBT 放置
道路铺设
地形修改
```

再在 Minecraft 给你的生成区域内执行。

---

# 18. 最终建议的数据结构

我会设计成：

```java
public final class CityPlan {

    CityBounds bounds;

    List<LandmarkPlacement> landmarks;

    RoadGraph roadGraph;

    List<CityDistrict> districts;

    List<CityBlock> blocks;

    List<BuildingLot> lots;

}
```

道路：

```java
public record RoadSegment(
    Vec2i start,
    Vec2i end,
    RoadType type,
    int width
) {}
```

地标：

```java
public record LandmarkPlacement(
    ResourceLocation id,
    Rect2i footprint,
    Direction facing,
    List<RoadPort> ports
) {}
```

地块：

```java
public record BuildingLot(
    Rect2i bounds,
    Direction roadFacing,
    DistrictType district,
    int roadLevel
) {}
```

建筑：

```java
public record BuildingDefinition(
    ResourceLocation template,
    int width,
    int depth,
    BuildingCategory category,
    Set<DistrictType> allowedDistricts,
    int weight
) {}
```

这样以后无论是添加新国家、新城市、新住宅，**都不需要修改 `CityPlanner` 本身**。

---

## 如果是你这种大型游戏城市，我会再多做一层

不要：

```text
CityPlanner
  ↓
直接生成 Minecraft 建筑
```

而做：

```text
              CityGenerator
                    │
             ┌──────┴──────┐
             │             │
        CityTheme      CityPlanner
             │             │
             └──────┬──────┘
                    ▼
                 CityPlan
```

例如：

```java
interface CityTheme {

    LandmarkSet landmarks();

    RoadStyle roads();

    BuildingPalette buildings();

    DistrictRules districts();

    TerrainRules terrain();

}
```

这样每个国家只定义：

```text
建筑库
道路风格
城区权重
地标规则
装饰规则
```

**城市生成算法完全共用。**

例如同一个 Planner 可以得到：

```text
维多利亚
    → 方正规整
    → 主干道宽
    → 石制街区
    → 密集住宅

卡西米尔
    → 商业核心
    → 巨型赛场地标
    → 放射状道路

拉特兰
    → 中央宗教地标
    → 广场
    → 放射道路

龙门
    → 超高密度
    → 大型城市道路
    → 高层住宅
    → 巷道密集
```

而不必分别写四套生成器。

---

### 我建议你第一版先做到这个程度

```text
                 Landmark
                    ███
                     │
                     │
=====================+================ Main Road
      │              │          │
      │              │          │
------+--------------+----------+----- Secondary
      │              │          │
 H H  │ H H       H H│H H    H H│H H
 H H  │ H H       H H│H H    H H│H H
------+--------------+----------+-----
```

也就是：

**地标占位 → 地标之间生成主路 → 主路按固定/随机间距长出支路 → 支路两侧生成 Lot → Lot 选择 NBT。**

先不要一开始就做复杂的真实城市模拟、交通流量、Voronoi、复杂地价之类。这套基础架构已经能生成相当漂亮，而且**可以轻松突破
Jigsaw 的 128 格城市规模问题**。

如果继续实现，我下一步最推荐直接把它细化成一套 **NeoForge 1.21.1 Java 类架构 + `CityPlanner` / `RoadGraph` / `LotPlanner`
的核心算法伪代码**，基本可以直接作为 Codex 的开发任务说明。

[1]: https://docs.neoforged.net/docs/1.21.1/resources/?utm_source=chatgpt.com "Resources | NeoForged docs"

[2]: https://github.com/neoforged/NeoForge/discussions/2475?utm_source=chatgpt.com "World freeze 1.21.1 Neoforge · neoforged NeoForge · Discussion #2475 · GitHub"
