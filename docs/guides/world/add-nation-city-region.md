# 国家、城市与 Region 如何生成

Zinecraft 的城市不是在世界中随机散布若干建筑，而是先离线计算一张确定性的泰拉布局，再由世界生成读取它。完整链路如下：

![国家、城市与 Region 生成链路](./diagrams/terra-layout-generation.svg)

```text
国家定位折线
  -> 国家 Voronoi 边界
    -> 国家内的城市 Voronoi 边界
      -> 城市内按 Chunk 排列的移动地块（Region 实例）
        -> 每个 Region 的四层道路、楼梯、Parcel 与建筑槽位
          -> runData 导出的 gzip 布局资源
            -> 新区块中的道路、楼梯和建筑结构
```

主要声明入口是 [ModNation.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModNation.java)、[ModCity.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModCity.java) 和 [ModCityRegion.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModCityRegion.java)。算法入口是 [TerraLayoutCalculator.java](../../src/main/java/com/cxxcxx/zinecraft/core/nation/TerraLayoutCalculator.java)。

## 为什么采用这套设计

这条链路要同时解决四个彼此牵制的问题：

1. **国家和城市必须铺满有限世界。** Voronoi 能从少量定位点自动得到无缝、不重叠的边界，并自然产生邻国和邻城关系。
2. **移动城市要有“组装出来”的观感。** 城市内部不再继续切任意多边形，而是改用 Chunk 对齐的矩形移动地块，便于道路连接、结构 NBT 放置和区块级生成。
3. **核心区、郊区要表达空间层次。** Region 的权重既参与类型选择，也参与目标半径评分，因此同一个参数可以让重要区域更常出现并更靠近核心。
4. **世界生成不能临场求解全图。** Voronoi、候选搜索、道路连通和 Parcel 切分都在 `runData` 时完成；运行时只读取 gzip 并查询当前 Chunk，换取稳定结果和较低的进服开销。

代价也很明确：布局是全局计算的。移动一个国家站点可能改变邻国，移动一座城市可能改变同国其他城市；所以点位、ID 和随机源都应当视为需要谨慎维护的世界数据。

## 先理解 Voronoi

![Voronoi 术语、几何含义与实现](./diagrams/terra-voronoi-explained.svg)

### 基本术语

Voronoi 图不是某种 Minecraft 专用算法。它解决的是一个几何问题：给定若干“距离参照物”，空间中的每一点应该归给哪个参照物？

| 术语 | 本教程中的含义 |
| --- | --- |
| 裁剪域 `Ω` | 允许划分的外边界。国家阶段是 80,000×50,000 方块的泰拉核心矩形；城市阶段是所属国家的多边形。 |
| 站点 `site` | 距离参照物。城市使用一个点；地表国家使用由一个或多个点组成的折线。 |
| 距离函数 `d(p, site)` | 世界点 `p` 到站点的最短欧氏距离。站点类型不同，距离公式也不同。 |
| Voronoi 单元 `Vᵢ` | 在 `Ω` 内，距离站点 `i` 不大于距离任何其他站点的全部点。 |
| 等距线 `bisector` | 到两个站点距离相等的点集，也是两个单元的候选分界。点—点等距线是直线；涉及线段时可能出现抛物线段。 |
| 邻接 `adjacency` | 两个最终单元共享一段边界。项目会把它导出为邻国或邻城 ID。 |

对点站点 `sᵢ`，单元的定义是：

$$
V_i=\Omega\cap\bigcap_{j\ne i}H_{ij},
\qquad
H_{ij}=\left\{p\mid \lVert p-s_i\rVert\le\lVert p-s_j\rVert\right\}.
$$

把两边平方并展开，可以消去 `p·p`，得到一个线性半平面：

$$
(s_j-s_i)\cdot p\le
\frac{\lVert s_j\rVert^2-\lVert s_i\rVert^2}{2}.
$$

这就是 [VoronoiDiagram.java](../../src/main/java/com/cxxcxx/zinecraft/api/world/layout/VoronoiDiagram.java) 的实现基础：每个站点先拿到完整裁剪域 `Ω`，再针对其他每个站点，用上述半平面逐次裁剪当前多边形。边与半平面边界相交时，交点参数为：

$$
\begin{aligned}
t&=\frac{\operatorname{signedDistance}(\mathrm{start})}
{\operatorname{signedDistance}(\mathrm{start})-\operatorname{signedDistance}(\mathrm{end})},\\
\mathrm{intersection}&=\mathrm{start}+t(\mathrm{end}-\mathrm{start}).
\end{aligned}
$$

例如，`s₁=(0,0)`、`s₂=(10,0)` 时：

$$
\begin{aligned}
(10,0)\cdot(x,z)&\le\frac{100-0}{2},\\
10x&\le50,\\
x&\le5.
\end{aligned}
$$

所以 `s₁` 的一侧是 `x≤5`，`s₂` 的一侧是 `x≥5`；再与 `Ω` 相交，就得到两个不会越界的单元。

### 为什么国家使用折线站点

若一个狭长国家只使用单点，单点只能表达“中心”，不能表达国家的大致走向。Zinecraft 因此把国家的多次 `position(...)` 连成折线 `L`，并采用点到整条折线的最短距离：

$$
d(p,L)^2=\min_k d\!\left(p,[a_k,b_k]\right)^2.
$$

点到一条线段 `[a,b]` 的实现是先求投影参数，再限制在线段内：

$$
\begin{aligned}
t&=\operatorname{clamp}\!\left(
\frac{(p-a)\cdot(b-a)}{\lVert b-a\rVert^2},0,1
\right),\\
d\!\left(p,[a,b]\right)^2&=\left\lVert p-\bigl(a+t(b-a)\bigr)\right\rVert^2.
\end{aligned}
$$

`t=0` 表示最近点是端点 `a`，`t=1` 表示端点 `b`，`0<t<1` 表示最近点在线段内部。

[PolylineVoronoiDiagram.java](../../src/main/java/com/cxxcxx/zinecraft/api/world/layout/PolylineVoronoiDiagram.java) 的实现步骤比点站点复杂：

1. 取泰拉核心矩形的最长边作为尺度，把矩形和全部折线归一化到约 `[-0.5,0.5]`，减小 80,000×50,000 尺度带来的数值误差。
2. 把每个折线顶点插入为 point site，再把相邻顶点插入为 line site。
3. 由 jOpenVoronoi 生成 primitive face。一个国家可能拥有多个点面和线段面。
4. 将每个 face 裁剪到核心矩形；曲线边只保留端点及 X/Z 极值点，不做固定步长采样。
5. 根据 primitive 到哪条原折线距离为零判定 owner，再用 JTS `union` 合并同一国家的全部面。
6. 若合并结果只有边界点相接，则串成不增加面积的弱简单边界；最后去掉重复点和共线点，并映回方块坐标。

因此，国家边界不是把 `position(...)` 当多边形顶点连起来。真正的语义始终是“核心矩形内，到该国折线最近的全部位置”。

## 先区分三个层级

| 层级 | 声明内容 | 生成结果 |
| --- | --- | --- |
| 国家 `NationBuilder` | 国家 ID、定位折线、城市清单；地下国家还声明固定尺寸 | 泰拉核心矩形内的一块国家边界 |
| 城市 `TerraCityBuilder` | 国家内相对位置、旋转、允许出现的 Region 类型和地块约束 | 国家边界内的一块城市边界，以及其中的若干移动地块 |
| Region `TerraCityRegionBuilder` | 所属国家、权重、数量、地块尺寸、道路布局和建筑池 | 城市中的一个或多个矩形移动地块；每个实例都有四层内部布局 |

`TerraCityRegionBuilder` 声明的是一种 Region 类型，不是一个固定坐标。除非使用 `unique()` 或把 `maxCount` 限制为 1，同一种类型可以在城市中生成多个实例。

## 1. 国家边界如何生成

地表国家在 [ModNation.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModNation.java) 中声明：

```java
public static final NationBuilder EXAMPLE = Zinecraft.NATIONS
    .nation("example", "示例国")
    .position(-0.35, -0.20)
    .position(-0.10, 0.15)
    .cities(() -> List.of(ModCity.EXAMPLE_CITY))
    .build();
```

`position(x, z)` 接收相对泰拉核心矩形的归一化坐标。生成时，X、Z 分别乘以 `ModDimension.TERRA_CORE_HALF_SIZE_X` 和 `TERRA_CORE_HALF_SIZE_Z`，转换为方块坐标。多个点组成国家的定位折线；它们不是国家边界顶点。`PolylineVoronoiDiagram` 以所有地表国家的定位折线为站点，在泰拉核心矩形中计算互不重叠的国家 Voronoi 单元，单元外轮廓才是最终国家边界。

当前泰拉核心矩形宽 80,000、长 50,000，因此半边长分别是 `Hₓ=40,000`、`H_z=25,000`。国家点位的换算公式是：

$$
\begin{aligned}
\mathrm{worldX}&=\mathrm{relativeX}\times40{,}000,\\
\mathrm{worldZ}&=\mathrm{relativeZ}\times25{,}000.
\end{aligned}
$$

上例的两个点会变为：

| 归一化点 | 世界方块点 |
| --- | --- |
| `(-0.35, -0.20)` | `(-14,000, -5,000)` |
| `(-0.10, 0.15)` | `(-4,000, 3,750)` |

两点之间的线段连同两个端点共同构成该国的距离站点。`TerraLayoutCalculator` 对所有非地下国家一次性计算折线 Voronoi，然后用 `PolygonAdjacencyCalculator` 比较最终多边形边界，写出 `neighboringNationIds`。

可以把两个国家的输入与输出想成：

```text
输入：定位折线                         输出：到哪条折线更近就归哪个国家

┌────────────────────┐                ┌────────────────────┐
│ A1────A2            │                │       国家 A       │
│        ╲            │                │ A1────A2            │
│         A3   B1     │      ──>       │        ╲···········│
│              ╲      │                │         A3│ B1      │
│               B2   │                │ 国家 A    │  ╲ 国家 B│
└────────────────────┘                └────────────────────┘
                                               ↑
                                         Voronoi 分界
```

使用折线而不是单点，是为了让狭长或弯曲国家可以表达“大致走向”。算法比较的是到整条折线的距离，所以增加一个折点会拉动附近分界，但不必手写并维护一整圈边界。

因此，增加或移动一个国家的定位点可能同时改变邻国边界。坐标是 Zinecraft 的玩法布局数据，不应当作官方世界坐标；国家名称、城市归属等资料事实则应以项目指定资料为准。

地下国家不参与地表 Voronoi。它使用定位折线的中点作为中心，再生成固定正方形：

```java
public static final NationBuilder UNDERGROUND_EXAMPLE = Zinecraft.NATIONS
    .nation("underground_example", "地下示例国")
    .position(-0.60, 0.35)
    .underground()
    .size(2_000)
    .cities(() -> List.of(ModCity.UNDERGROUND_CITY))
    .build();
```

`size(...)` 的单位是方块，且整个正方形必须位于泰拉核心矩形内。`cities(...)` 使用 `Supplier` 是为了延迟读取城市字段，保持国家、Region、城市之间的静态注册依赖顺序。

地下国家中心也不是简单的顶点平均值。如果折线总长度为 `L`，`PolylineVoronoiDiagram.midpoint(...)` 沿折线累计长度，取弧长 `L/2` 所在位置。随后以 `size/2` 为半边长生成轴对齐正方形：

$$
\Omega_{\mathrm{underground}}=
\left[\mathrm{centerX}-\frac{\mathrm{size}}2,\mathrm{centerX}+\frac{\mathrm{size}}2\right]
\times
\left[\mathrm{centerZ}-\frac{\mathrm{size}}2,\mathrm{centerZ}+\frac{\mathrm{size}}2\right].
$$

这样做的意图是让地下国家拥有独立、确定的规划范围，同时不从地表国家手中“切走”面积。

## 2. 城市边界如何生成

城市在 [ModCity.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModCity.java) 中声明。项目现有辅助方法最终等价于：

```java
public static final TerraCityBuilder EXAMPLE_CITY = Zinecraft.CITIES
    .city("示例城")
    .id("example_city")
    .enUs("Example City")
    .position(0.10, -0.25)
    .rotation(90) // 当前仅作为 rotation_degrees 元数据导出
    .regions(
        ModCityRegion.EXAMPLE_CORE,
        ModCityRegion.EXAMPLE_SUBURB
    )
    .build();
```

城市的 `position(relativeX, relativeZ)` 是所属国家边界内的归一化布局坐标，不是世界方块坐标，也不是前一节的泰拉归一化坐标。`NormalizedVoronoiCalculator` 将相对坐标映射进国家多边形，再以同一国家的全部城市为站点切分城市 Voronoi 边界。

### 归一化城市坐标怎样映射进国家

[ConvexPolygonMapper.java](../../src/main/java/com/cxxcxx/zinecraft/api/world/layout/ConvexPolygonMapper.java) 使用“从国家中心向边界发射射线”的映射，而不是拿国家外接矩形做简单缩放。给定城市输入 `(u,v)∈[-1,1]²`：

$$
\begin{aligned}
f&=\max(|u|,|v|),\\
q&=(u/f,v/f) && (f>0),\\
d&=R(\theta)q && \text{（旋转后的射线方向）},\\
\lambda&=\text{射线 }\mathrm{center}+\lambda d\text{ 与国家边界的最近正向交点距离},\\
\mathrm{site}&=\mathrm{center}+f\lambda d.
\end{aligned}
$$

`f` 是输入在 L∞ 范数下离中心的比例：`f=0` 映射到国家中心，`f=1` 映射到对应方向的国家边界。这样即使国家不是矩形，`position(0.5,-0.25)` 仍表示“沿该方向走到边界距离的一半”。

例如国家暂时近似为 `[-1000,1000]×[-500,500]`、中心为 `(0,0)`，输入 `(u,v)=(0.5,-0.25)`：

$$
\begin{aligned}
f&=0.5,\\
q&=(1,-0.5),\\
\lambda&=1000 && \text{（射线先在 }x=1000\text{ 处命中边界）},\\
\mathrm{site}&=(0,0)+0.5\times1000\times(1,-0.5)\\
&=(500,-250).
\end{aligned}
$$

当前 `TerraLayoutCalculator` 调用映射器时传入的旋转角固定为 `0.0`。`TerraCityBuilder.rotation(...)` 当前只会作为 `rotation_degrees` 元数据导出，并未参与城市站点映射、Region 地块生成或建筑旋转；不要用它修正实际布局方向。

### 城市点站点怎样得到边界

映射完成后，同一国家内的每座城市得到一个点站点。`VoronoiDiagram` 对第 `i` 座城市执行：

```text
polygon ← nationBoundary
for each j ≠ i:
    polygon ← clip(polygon, Hᵢⱼ)
cityBoundaryᵢ ← polygon
```

因此只有一座城市时，它获得整个国家边界；有多座城市时，边界由所有城市点共同决定。移动 C1 不只改变 C1，也会移动它与 C2、C3 的等距线。最后再次计算共享边界，生成 `neighboringCityIds`。

例如，同一个国家有三座城市时：

```text
国家边界                         城市 Voronoi 结果

       ╭────────╮                     ╭────────╮
    ╭──╯  C1 ·  ╰──╮               ╭──╯ C1 城区╲╰──╮
   ╱          · C2  ╲             ╱──────────╲ C2  ╲
  ╰──╮  C3 ·      ╭─╯            ╰──╮ C3 城区  ╲ ╭─╯
     ╰────────────╯                  ╰────────────╯
```

这一级继续使用 Voronoi，是为了让增加城市时自动重新分配国家内部空间，并让城市边界完整覆盖国家，而不必为每座城市单独维护世界坐标范围。

常用参数如下：

| 调用 | 含义与约束 |
| --- | --- |
| `id(id)` | 稳定的 `lower_snake_case` 城市 ID。 |
| `position(x, z)` | 城市在所属国家边界内的归一化相对位置。 |
| `rotation(degrees)` | 当前仅导出为 0～359 度的元数据；尚未参与城市、Region 或建筑几何计算。 |
| `regions(...)` | 该城市允许生成的 Region 类型；Region 必须属于同一个国家。 |
| `plotCountRange(min, max)` | 城市内 Region 实例总数，默认 10～100。 |
| `maxPlotCoverage(ratio)` | Region 占城市可用 Chunk 面积的上限，默认 0.45。 |
| `roadWidthChunks(width)` | Region 之间的城市道路宽度，默认 1 Chunk。 |
| `candidateCount(count)` | 每轮保留并评分的候选地块数量，默认 16。 |

![城市 Region 与 Region 四层生成流程](./diagrams/terra-region-generation.svg)

### 第一步：把城市多边形栅格化

[CityGrid.java](../../src/main/java/com/cxxcxx/zinecraft/core/nation/CityGrid.java) 先计算多边形外接范围覆盖的 Chunk。一个 Chunk 只有以下五个采样点全部位于城市边界内，才记为 `EMPTY`：

```text
(minX,minZ)   (maxX,minZ)
      +---------+
      |    •    |  ← 中心
      +---------+
(minX,maxZ)   (maxX,maxZ)
```

点是否在多边形内使用射线奇偶规则；点恰好落在边上也算内部。只接纳完整 Chunk 的设计意图，是避免矩形 Region 或道路在斜城市边界处伸到邻城。

设可用 Chunk 集合为 `G`，城市核心不是 Voronoi 站点本身，而是在 `G` 中重新寻找的“最大净空 Chunk 中心”：

$$
\mathrm{core}=\underset{c\in G}{\operatorname{arg\,max}}\;
\operatorname{distanceToBoundary}\!\left(\operatorname{center}(c)\right).
$$

`distanceToBoundary` 是点到城市多边形所有边线段的最短欧氏距离。这使首个大型 Region 尽量放在城市最厚实的位置，而不是卡在狭角或紧贴边界。

### 第二步：先满足每种 Region 的下限

设城市允许的 Region 类型为 `T`。生成器先检查：

$$
\begin{aligned}
\mathrm{mandatoryCount}&=\sum_{t\in T}\operatorname{minCount}(t),\\
\mathrm{city.minPlotCount}&\ge\mathrm{mandatoryCount}.
\end{aligned}
$$

随后按 `weight` 降序、ID 升序展开每种类型 `minCount` 次。每一个必选实例都必须找到合法候选；任何一个放不下都会返回 `MANDATORY_PLOTS_CANNOT_FIT`，而不是跳过它。

### 第三步：为后续实例生成加权类型顺序

达到所有 `minCount` 后，类型不是简单按注册顺序尝试，而是反复做不放回加权抽取。类型 `t` 当前已放置 `nₜ` 个时：

$$
\operatorname{remaining}(t)=
\begin{cases}
1,&\operatorname{maxCount}(t)=\infty,\\
\max\!\left(1,\operatorname{maxCount}(t)-n_t\right),&\text{其他情况},
\end{cases}
$$

$$
\operatorname{effectiveWeight}(t)=
\operatorname{clamp}\!\left(
\operatorname{weight}(t)\operatorname{remaining}(t),
1,\mathrm{Integer.MAX\_VALUE}
\right).
$$

每轮以 `effectiveWeight` 为票数抽一个类型，移出临时候选集合，再抽下一个，形成本轮的尝试顺序。有限 `maxCount` 的类型在剩余名额较多时更容易靠前；`unique()` 会把有效 `maxCount` 压到 1。

### 第四步：生成 Region 矩形候选

`PlotSize(w,h)` 以 Chunk 为单位，生成器同时尝试 `w×h` 与 `h×w`。

首个 Region 必须覆盖核心 Chunk。对于每个方向后的尺寸，生成器枚举所有能覆盖核心的左上角：

$$
\begin{aligned}
\mathrm{offsetX}&\in[-w+1,0],\\
\mathrm{offsetZ}&\in[-h+1,0],\\
\mathrm{candidate.min}&=\mathrm{coreChunk}+(\mathrm{offsetX},\mathrm{offsetZ}).
\end{aligned}
$$

后续 Region 从已有地块边缘扩张。默认 `candidateCount=K` 时：

```text
目标去重候选数 = 8K
最大采样次数   = 16 × 8K = 128K
```

每次随机选择一个已有父地块、一个允许尺寸、四个方向之一和沿父地块边缘的偏移。新 Region 与父地块之间留出 `roadWidthChunks` 宽的道路矩形；二者在平行方向上的重叠长度就是 `interfaceLength`。若采样候选全部失败且城市尚未达到 `minPlotCount`，才退回到完整边缘枚举，优先保证下限可达。

### 第五步：逐条拒绝非法候选

候选按以下顺序过滤；第一条命中的原因会写入调试统计：

| 拒绝原因 | 实现判断 |
| --- | --- |
| `OUTSIDE_CITY` | Region 或连接道路包含非完整城市 Chunk。 |
| `OVERLAPS_PLOT` | Region 与已有 Region 相交，或连接道路穿过非父 Region。 |
| `OVERLAPS_ROAD` | Region 自身覆盖已有城市道路。 |
| `INVALID_ROAD_GAP` | Region 进入已有 Region 向外扩张 `roadWidthChunks` 后的保留带。 |
| `NO_CONNECTION` | 非首个 Region 没有合法父地块连接。 |
| `COVERAGE_LIMIT` | 接纳后的地块覆盖率超过 `maxPlotCoverage`。 |
| `TYPE_MAX_COUNT` | 此类型已达到有效 `maxCount`。 |

覆盖率只计算 Region 地块面积，不把城市道路算入分子：

$$
\mathrm{coverage}=
\frac{\sum_{p\in\mathrm{plots}}\operatorname{areaChunks}(p)}
{\operatorname{usableChunkArea}(\mathrm{city})}.
$$

### 第六步：打分并接纳最优候选

合法候选先用稳定随机源打乱，再最多保留 `K=candidateCount` 个评分，防止总是偏向枚举顺序。设：

$$
\begin{aligned}
R&=\max\!\left(1,\operatorname{hypot}(\mathrm{gridWidthChunks},\mathrm{gridLengthChunks})\right),\\
d&=\frac{\operatorname{distance}(\mathrm{candidateCenter},\mathrm{cityCore})}{16},\\
\widehat w&=
\begin{cases}
0.5,&w_{\max}=w_{\min},\\
\dfrac{w-w_{\min}}{w_{\max}-w_{\min}},&\text{其他情况},
\end{cases}\\
r^*&=0.65R(1-\widehat w).
\end{aligned}
$$

`r*` 是类型的目标半径。最高权重时 `ŵ=1`、`r*=0`，目标在核心；最低权重时 `ŵ=0`、`r*=0.65R`，目标偏外。四个评分分量为：

$$
\begin{aligned}
\mathrm{centrality}&=1-\min\!\left(1,\frac{|d-r^*|}{R}\right),\\
\mathrm{clearance}&=\min\!\left(1,
\frac{\operatorname{distanceToCityBoundary}(\mathrm{candidateCenter})}{16R}\right),\\
\mathrm{adjacency}&=\frac{\mathrm{interfaceLength}}
{\max(\mathrm{candidateWidth},\mathrm{candidateLength})},\\
\mathrm{compactness}&=\frac{\mathrm{totalPlotAreaAfterAccept}}
{\mathrm{boundingBoxAreaOfAllPlotsAfterAccept}},\\
\mathrm{score}&=4.0\,\mathrm{centrality}+2.0\,\mathrm{clearance}
+1.5\,\mathrm{adjacency}+2.0\,\mathrm{compactness}.
\end{aligned}
$$

这四项分别表达“符合类型的目标半径”“不要贴边”“与父地块有足够长的道路界面”“城市整体不要形成飞地”。权重不直接加到总分，而是改变目标半径。

数值示例：设 `R=50`，核心区权重是本城最高权重，候选 `d=6`，距边界 80 方块，`interfaceLength=12`，尺寸 `16×12`，接纳后紧凑度 `0.72`：

$$
\begin{aligned}
r^*&=0,\\
\mathrm{centrality}&=1-\frac6{50}=0.88,\\
\mathrm{clearance}&=\frac{80}{16\times50}=0.10,\\
\mathrm{adjacency}&=\frac{12}{16}=0.75,\\
\mathrm{compactness}&=0.72,\\
\mathrm{score}&=4\times0.88+2\times0.10+1.5\times0.75+2\times0.72\\
&=6.285.
\end{aligned}
$$

接纳后，Region 矩形标记为 `PLOT`，连接带标记为 `ROAD`，并生成一条 `UrbanRoad(parentId,newId,area)`。循环直到达到 `maxPlotCount`、没有可选类型或没有类型能找到候选。`maxPlotCount` 是上限而非目标；最终只强制 `plots.size ≥ minPlotCount`。

候选扩张可以简化为下图。`R0` 首先覆盖城市核心；`R1`、`R2` 只能贴着已有地块的边缘尝试，二者之间保留城市道路：

```text
图例：■ Region   = 城市道路   · 未占用 Chunk   ★ 城市核心

初始                     第一次扩张                 后续扩张

··········               ··········                ··■■■····
···■■■···               ···■■■=■■·                ··■■■····
···■★■···      ──>      ···■★■=■■·      ──>       ··===····
···■■■···               ···■■■=■■·                ··■■■=■■·
··········               ··········                ··■■■=■■·
   R0                      R0   R1                   R2   R0 R1
```

如果必选 Region 放不下，或最终实例数达不到城市的 `minPlotCount`，数据生成会直接失败，而不是静默少生成。

## 3. 声明 Region 类型

Region 在 [ModCityRegion.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModCityRegion.java) 中注册：

```java
public static final TerraCityRegionBuilder EXAMPLE_CORE =
    Zinecraft.CITY_REGIONS.region(ModNation.EXAMPLE, "示例城核心区")
        .weight(100)
        .regionLayout(RegionLayoutType.CONCENTRIC)
        .plotSizes(
            new PlotSize(40, 32),
            new PlotSize(32, 32),
            new PlotSize(32, 24)
        )
        .countRange(1, 1)
        .unique()
        .building(ModStructure.EXAMPLE_SHOP, 2, false)
        .building(ModStructure.EXAMPLE_LANDMARK, 1, true)
        .build();
```

| 调用 | 含义与约束 |
| --- | --- |
| `weight(value)` | 可选实例的选择权重，同时影响理想半径；权重越高越趋向城市核心。必须为正数。 |
| `regionLayout(type)` | 地表层道路布局；当前只能使用 `GRID`、`CONCENTRIC`、`RADIAL_GRID`。 |
| `plotSizes(...)` | 允许的矩形地块尺寸，单位为 Chunk；生成器也会尝试旋转尺寸。每项面积至少为 80 Chunk。 |
| `countRange(min, max)` | 该类型在一座城市中的实例数范围；默认至少 1 个。 |
| `unique()` | 把有效最大数量限制为 1，适合核心区或地标区。 |
| `roadConfig(config)` | Region 内部道路参数。三类道路宽度目前都必须为 1 Chunk；`gridSpacingChunks` 已参与网格间距，`extraEdgeRatio/maxCandidateAttempts` 当前尚未被生成器消费。 |
| `building(builder, weight, unique)` | 加入地表建筑候选；权重控制选择概率，`unique` 防止同一建筑在该 Region 重复。 |

城市建筑必须先在 [ModStructure.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModStructure.java) 中以 `embeddedBuilding(...)` 等方式声明正确的 footprint 和真实入口面。独立建筑的制作方式参见 [添加结构](add-structure.md)。

项目现有 `region(...)` 辅助方法还会自动加入国家商店，将核心区设为权重 100、郊区设为 30，并给核心区使用更大的 `PlotSize`。新增内容时优先复用这一入口；只有需求偏离默认规则时才展开 Builder 配置。

## 4. 一个 Region 内部如何生成

每个城市移动地块会交给 [RegionLayoutGenerator.java](../../src/main/java/com/cxxcxx/zinecraft/core/nation/RegionLayoutGenerator.java)，生成固定四层：

| 层 | 相对 Y | 内容 |
| --- | ---: | --- |
| `power` | 0 | 动力层道路与分层建筑 |
| `support` | 16 | 支持层道路与分层建筑 |
| `life` | 32 | 生活层道路与分层建筑 |
| `surface` | 48 | 地表道路与 Region 建筑池中的建筑 |

地表层使用注册时的 `regionLayout`。下三层分别使用独立的稳定随机源，从 `GRID`、`CONCENTRIC`、`RADIAL_GRID` 中选择布局；四层不会简单复制同一张道路图。

### 4.1 从城市道路推导 Region Entrance

城市级 `UrbanRoad` 记录相邻两个 Region 及道路矩形。`CityLayoutCalculator` 取道路矩形的方块中心，给道路两端分别增加 `CityRegionConnection(neighborId, point)`。

进入 Region 生成器后：

1. 把连接点除以 16 并向下取整，得到目标 Chunk。
2. 计算它到 Region 北、南、西、东四条边的 Chunk 距离。
3. 选择最近边；若相等，判断顺序是北 → 南 → 西 → 东。
4. 把坐标限制到该边范围内，形成宽 1 Chunk 的 `RegionEntrance`。

Entrance 的设计意图是把“Region 之间的城市道路”转换成“Region 内道路图的外部端口”。只有 `surface` 层拥有 Entrance；地下层通过楼梯与地表连通。

### 4.2 中心、楼梯与分层 seed

Region 本地中心先取矩形中心 Chunk。非 `CONCENTRIC` 类型会在 X/Z 各加入 `[-1,1]` 的稳定随机扰动，再限制在距边界至少 1 Chunk 的内部范围。

四个共享楼梯以中心为基准分布在四个象限：

$$
\begin{aligned}
\mathrm{offsetX}&=\max\!\left(2,\frac{\mathrm{regionWidth}}6\right),\\
\mathrm{offsetZ}&=\max\!\left(2,\frac{\mathrm{regionLength}}6\right),\\
\mathrm{stairs}&=\left\{
(c_x-\mathrm{offsetX},c_z-\mathrm{offsetZ}),
(c_x+\mathrm{offsetX},c_z-\mathrm{offsetZ}),\right.\\
&\hspace{5.7em}\left.
(c_x-\mathrm{offsetX},c_z+\mathrm{offsetZ}),
(c_x+\mathrm{offsetX},c_z+\mathrm{offsetZ})
\right\}.
\end{aligned}
$$

坐标会限制在 Region 内圈；若限制后无法得到四个不同 Chunk，说明 Region 太小，直接失败。这里固定四象限的意图不是装饰，而是限制最坏步行距离，并避免整个四层交通依赖单一竖井。

每个 Region 的 seed 由城市随机源、城市 ID、Region 类型 ID 和实例序号混合。每一层再混入层 ordinal：

$$
\mathrm{layerSeed}=\operatorname{mix}\!\left(
\mathrm{regionSeed}\mathbin{\mathrm{XOR}}
\bigl(\mathtt{0x9E3779B97F4A7C15}\times(\mathrm{layerOrdinal}+1)\bigr)
\right).
$$

`mix` 还执行无符号右移、乘 `0xff51afd7ed558ccd` 和再次异或。目的不是加密，而是让相邻层的随机序列充分分离。下三层先用各自随机源独立抽取一种已实现布局；`surface` 固定使用 Region 注册的布局。

### 4.3 每层 hub 为什么不同

四层按 `POWER、SUPPORT、LIFE、SURFACE` 顺序分配西北、东北、西南、东南四个象限。设：

$$
\begin{aligned}
\mathrm{distanceX}&=\max\!\left(1,\frac{\mathrm{width}}4\right),&
\mathrm{jitterX}&\in[0,\mathrm{distanceX}),\\
\mathrm{distanceZ}&=\max\!\left(1,\frac{\mathrm{length}}4\right),&
\mathrm{jitterZ}&\in[0,\mathrm{distanceZ}),\\
\mathrm{hub}&=\operatorname{clamp}\!\left(
\mathrm{center}+\mathrm{quadrantSign}(\mathrm{distance}+\mathrm{jitter}),
\mathrm{innerRegionBounds}\right).
\end{aligned}
$$

即便两层偶然抽到同一种 `layoutType`，hub 象限与随机序列也不同，因此不会复制出完全相同的道路骨架。

### 4.4 先建保证连通的强制主路

单层生成总是从强制道路开始：

1. 地表若有 Entrance，随机打乱后把第一个 Entrance 用正交折线连接到 hub；其余 Entrance 使用距离场接入最近既有道路。
2. 地下层没有 Entrance，因此从 hub 接到 Region 西边界，保证道路图不是空图。
3. 再把 Region center 与本层 hub 连接为 `PRIMARY`。

正交连接若两个点不共 X/Z，会随机选择“先走 X”或“先走 Z”的肘点。所有道路边最终都是轴对齐矩形，且当前 `PRIMARY/SECONDARY/SERVICE` 宽度都严格为 1 Chunk。

### 4.5 三种地表道路算法

| 类型 | 当前实现 | 设计意图 |
| --- | --- | --- |
| `GRID` | 从 Region 最小边开始，按 `gridSpacingChunks` 随机取间距；分别生成贯穿内圈的纵线和横线，并接到 hub。距已有平行道路 1 Chunk 内的候选线会跳过。 | 形成规则街区，同时避免相邻两条线制造无意义的双宽路。 |
| `CONCENTRIC` | 初始 inset 随机为 3 或 4 Chunk；生成正交矩形环，每轮再增加 3 或 4。环的南边有 18% 概率留缺口，每个环从北边接到中心。 | 保留核心—环路层次，又避免每一环都机械闭合。 |
| `RADIAL_GRID` | 统计 Entrance 已占方向，补主干直到至少有两个边界方向；再调用稀疏 GRID，每条候选网格线只有 72% 概率生成。 | 先建立从 hub 向外的放射骨架，再用较稀疏网格提供横向联系。 |

`SPINE`、`CAMPUS`、`HYBRID` 虽然存在于枚举中，但 Builder 会拒绝注册，生成器也会抛错；它们不是“可以尝试但效果未完成”的布局。

### 4.6 用距离场接入楼梯并补齐可达性

道路会被光栅化为 Road Chunk 集合 `R`。生成器从全部道路格同时开始四邻域 BFS，计算 Region 内每格到最近道路的曼哈顿距离：

$$
D(c)=\min_{r\in R}\left(|c_x-r_x|+|c_z-r_z|\right).
$$

接入一个楼梯或远端地块时，从起点反向行走，每一步随机选择一个满足 `D(next)=D(current)-1` 的相邻格，直到 `D=0`，再把路径中同方向连续格压成 `PRIMARY` 或 `SERVICE` 道路。

所有楼梯接路后，可达性循环反复执行：

```text
while max D(c) > 1:
    从 D 最大的格中随机选一个
    沿 D 每步减 1 的最短梯度路径接入道路
```

终止条件 `max D≤1` 等价于每个非道路 Chunk 至少有一个四邻域道路格，因此后续切出的 Parcel 一定有机会获得真实临路面。循环最多执行 Region Chunk 总数次；若仍不收敛则失败。

### 4.7 安全移除 2×2 道路块

多条正交道路叠加后可能形成 2×2 实心道路。生成器反复扫描每个 2×2 方块，优先尝试删除道路度数较低的格，但必须同时满足：

- 不是 Entrance 或楼梯保护格；
- 删除后剩余道路通过四邻域 BFS 仍整体连通；
- 删除后 Region 中每个非道路格仍至少邻接一个道路格。

每次只删一个格然后重新扫描，直到没有安全删除项。随后把连续横向/纵向 Road Chunk 重新压成 `RoadEdge`；若同一格叠加多个等级，保留优先级最高的 `RoadClass`。

### 4.8 从道路反推 UrbanBlock 与 Parcel

道路图稳定后，对全部非道路 Chunk 做四邻域 BFS。每个连通分量成为一个 `UrbanBlock`，记录真实格数和外接矩形；道路把 Region 切成几个分量，就会得到几个 UrbanBlock。

随后按稳定坐标顺序消耗每个 UrbanBlock 的剩余格：

1. 仅在地表，先统计建筑池中 footprint 为 `2×2` 的 unique 建筑，为它们查找完整 `2×2` Parcel。
2. 若建筑池存在面积为 2 Chunk 的中型商店，尝试 `1×2` 或 `2×1`；只有对应长边方向存在合法道路接触面才接受。
3. 其他剩余格退化为 `1×1` Parcel。
4. 下三层不分配地表建筑候选，因此当前 Parcel 切分为 `1×1`，运行时在每个非道路 Chunk 放相应分层模板。

每个 Parcel 都查询相邻 `RoadEdge`，并按“道路等级降序 → 方向序 → roadId”排序生成完整 `road_connections`。设 Parcel 为半开矩形 `[x₀,x₁)×[z₀,z₁)`，例如北面真实接壤要求：

```text
overlap([x₀,x₁), roadXRange)
AND road.maxZExclusive = z₀
```

南、西、东面使用对应的相等边界。不是“道路看起来很近”就算入口，必须共享至少一个 Chunk 宽的边。

### 4.9 建筑如何匹配 Parcel

`CityLayoutCalculator.buildingSlots(...)` 对地表每个 Parcel 执行：

1. 由 Parcel 主临路方向计算模板旋转。
2. 过滤已使用的 unique 建筑。
3. 旋转建筑的 `footprintChunksX/Z`，要求与 Parcel 宽、长**精确相等**，不是只要放得下。
4. 在兼容候选中按建筑权重抽取：

$$
P(\mathrm{building}=i)=\frac{w_i}{\sum_j w_j}.
$$

5. 把模板声明的本地 `connectionFaces` 按建筑旋转到世界方向，再与 Parcel 的 `road_connections` 求交；交集为空就失败。
6. 记录 `CityRegionBuildingSlot`，并把 unique 建筑加入已使用集合。

因此，为建筑新增门口时必须修改 `connectionFaces(...)`。只改 NBT 外观而不更新真实入口面，会在布局生成阶段得到“建筑模板没有朝向道路的真实入口”。

### 4.10 道路构件怎样由四向掩码决定

对道路 Chunk `c`，统一按北、东、南、西检查相邻道路；地表 Entrance 朝外的一面也算连接。方向位定义为：

```text
mask = (north ? 1 : 0)
     | (east  ? 2 : 0)
     | (south ? 4 : 0)
     | (west  ? 8 : 0)
```

| 连接形态 | 构件 | 示例 mask |
| --- | --- | ---: |
| 无连接 | `isolated` | 0 |
| 一个方向 | `end` | 1 |
| 两个相反方向 | `straight` | 5 或 10 |
| 两个相邻方向 | `corner` | 3、6、12、9 |
| 三个方向 | `tee` | 7、14、13、11 |
| 四个方向 | `cross` | 15 |

[RegionLayout.roadTile(...)](../../src/main/java/com/cxxcxx/zinecraft/api/world/city/RegionLayout.java) 同时返回构件和相对标准朝向的旋转。导出器和 `MobilePlotStructure` 都调用这一套分类，不分别维护第二份路口判断。

### 4.11 验证器不是可选的日志

[RegionLayoutValidator.java](../../src/main/java/com/cxxcxx/zinecraft/core/nation/RegionLayoutValidator.java) 会把以下条件当作硬错误：

- 恰好存在 `power/support/life/surface` 四层，范围与 `buildingId` 正确；
- 四层 `stairChunks` 列表完全相等，且每个楼梯都属于本层道路；
- 地表每个 Entrance 都属于地表道路；
- 每层道路通过四邻域 BFS 整体连通；
- Parcel 在 Region 内、不与道路重叠，每个 `road_connection` 引用真实且等级一致的相邻 RoadEdge；
- 道路与全部 Parcel 对 Region 每个 Chunk 实现不重不漏的全覆盖；
- 地表每个 Parcel 恰好对应一个建筑，建筑不重叠、尺寸正确，且覆盖全部非道路 Chunk。

这些校验解释了为什么生成器宁可在 `runData` 失败，也不会输出“差不多能用”的残缺布局。

单层大致按“确定 hub → 接入地表 Region Entrance → 建主路与布局道路 → 接入楼梯 → 补可达支路 → 清理安全可删的 2×2 道路 → 划分 Parcel”的顺序生成。四层共享至少四个分散且垂直对齐的楼梯 Chunk，每层道路必须整体连通，每个 Parcel 必须真实临路。地表建筑根据 footprint、入口方向和 Parcel 尺寸分配到 `building_slots`。

道路格最终被分类为 `isolated`、`end`、`straight`、`corner`、`tee` 或 `cross`，运行时据此选择和旋转对应 NBT。修改道路、Parcel、入口或四层模型时，必须同步检查生成器、验证器、导出器、读取器和运行时结构；不要只改 JSON 字段。

四层不是四张互不相关的地图，而是“道路独立、竖向交通共享”：

```text
       Region Entrance
              │
surface +48  ─┼───┬────────  地表建筑、对外道路
              │   │
life    +32  ─┼─┐ └────────  生活层；独立道路图
              │ │
support +16  ─┼─┴──────────  支持层；独立道路图
              │
power    +0  ─┴────────────  动力层；独立道路图
              ▲
         同一 X/Z 的楼梯竖井（至少四组）
```

这种设计让地下三层可以有不同的空间节奏，同时仍保证任何建筑都能沿本层道路到楼梯，再到地表 Entrance。至少四组分散楼梯是为了避免单点失效和过长绕行；楼梯坐标必须在四层完全一致，否则运行时无法拼成连续竖井。

## 5. 一个贯穿示例

假设“示例国”只有“示例城”，城市允许一个核心区和若干郊区：

```java
// ModNation.java
public static final NationBuilder EXAMPLE = Zinecraft.NATIONS
    .nation("example", "示例国")
    .position(0.20, -0.10)
    .cities(() -> List.of(ModCity.EXAMPLE_CITY))
    .build();

// ModCityRegion.java
public static final TerraCityRegionBuilder EXAMPLE_CORE =
    Zinecraft.CITY_REGIONS.region(ModNation.EXAMPLE, "示例城核心区")
        .weight(100)
        .regionLayout(RegionLayoutType.CONCENTRIC)
        .plotSizes(new PlotSize(32, 24))
        .unique()
        .building(ModStructure.EXAMPLE_LANDMARK, 1, true)
        .build();

public static final TerraCityRegionBuilder EXAMPLE_SUBURB =
    Zinecraft.CITY_REGIONS.region(ModNation.EXAMPLE, "示例城郊区")
        .weight(30)
        .regionLayout(RegionLayoutType.RADIAL_GRID)
        .plotSizes(new PlotSize(16, 12), new PlotSize(12, 8))
        .countRange(1, 8)
        .building(ModStructure.EXAMPLE_SHOP, 2, false)
        .build();

// ModCity.java
public static final TerraCityBuilder EXAMPLE_CITY = Zinecraft.CITIES
    .city("示例城")
    .id("example_city")
    .position(0.0, 0.0)
    .rotation(90)
    .plotCountRange(4, 7)
    .maxPlotCoverage(0.45)
    .regions(ModCityRegion.EXAMPLE_CORE, ModCityRegion.EXAMPLE_SUBURB)
    .build();
```

这组配置的推导过程是：

1. 示例国的定位点参与地表国家 Voronoi；因为只有一座城市，示例城取得整个国家边界。
2. 核心区与郊区的默认 `minCount` 都是 1，所以生成器必须先各放一个；核心区 `unique()` 后最多也只有一个。
3. 核心区权重 100，目标半径更靠近城市核心；郊区权重 30，更适合在外围候选中得分。
4. 城市至少要放 4 个地块，因此两个必选地块完成后还要继续选择郊区，直到达到 4～7 个、候选耗尽或覆盖率达到 0.45。
5. 核心区地表生成同心道路，郊区地表生成放射网格；它们的地下三层仍各自独立随机选择已实现的布局类型。
6. `runData` 固化本次结果。进入新区块后，运行时不会再次做上述候选搜索，只按资源中记录的 Chunk 布局放置结构。

再代入一组假设数值检查约束。假设示例城有 5,000 个完整可用 Chunk，核心区选中 `32×24`，三个郊区都选中 `16×12`：

$$
\begin{aligned}
A_{\mathrm{core}}&=32\times24=768\ \mathrm{Chunk},\\
A_{\mathrm{suburbs}}&=3\times16\times12=576\ \mathrm{Chunk},\\
A_{\mathrm{plots}}&=768+576=1344\ \mathrm{Chunk},\\
\mathrm{coverage}&=\frac{1344}{5000}=0.2688.
\end{aligned}
$$

`0.2688 < maxPlotCoverage(0.45)`，且地块数为 4，满足 `plotCountRange(4,7)`。若下一个候选会让覆盖率超过 0.45，它会被 `COVERAGE_LIMIT` 拒绝；若没有其他候选，城市可以停在 4～7 之间，不强求生成到 7。

对核心区候选，`weight=100` 若是本城最高权重，则目标半径 `r*=0`；郊区 `weight=30` 若是最低权重，则目标半径 `r*=0.65R`。这不保证每个郊区都在核心区外侧，但会在候选评分中形成明确偏好，最终结果还同时受边界余量、道路界面和紧凑度影响。

## 6. 数据生成与运行时放置

修改三个注册类后运行：

```powershell
.\gradlew.bat test -x generateTerraLayoutData --no-configuration-cache --console=plain
.\gradlew.bat runData --no-configuration-cache --console=plain
.\gradlew.bat build --no-configuration-cache --console=plain
```

`runData` 会调用 `TerraLayoutDataExporter`，重新计算完整布局并写入：

```text
src/generated/resources/data/zinecraft/terra_layout/index.json.gz
src/generated/resources/data/zinecraft/terra_layout/nations/<nation_id>.json.gz
```

不要手工编辑这些 gzip。游戏启动时，[TerraLayoutResource.java](../../src/main/java/com/cxxcxx/zinecraft/core/nation/TerraLayoutResource.java) 会预加载布局；`MobilePlotStructurePlacement` 只在布局中属于移动地块的 Chunk 创建结构起点，`MobilePlotStructure` 再按层放置楼梯、道路、下层构件和地表建筑。

### runData 实际执行什么

`generateTerraLayoutData` 是独立 JavaExec，入口为 [TerraLayoutDataExporter.java](../../src/main/java/com/cxxcxx/zinecraft/core/datagen/TerraLayoutDataExporter.java)：

1. 初始化 Minecraft bootstrap 环境和全部静态内容注册。
2. 调用 `TerraLayoutCalculator.calculate(ModNation.ALL, 40_000, 25_000)`。
3. 依次生成国家、城市、Region、四层道路、Parcel 与建筑槽位；任何校验异常都会使任务失败。
4. 写出 schema v16 的 `index.json.gz` 与每国一个 `nations/<id>.json.gz`。
5. 同时写验收 JSON；`generateTerraLayoutValidation` 可进一步调用脚本生成 SVG 验收地图。
6. NeoForge `runData` 可能清理不属于普通 provider 的 gzip，因此任务结束时还会由 `restoreTerraLayoutData` 重新导出压缩布局。

索引保存泰拉尺寸、国家摘要和国家文件清单；国家文件保存本国边界、邻国、城市、Region 和 `mobile_layers`。v16 以每层 `mobile_layers` 为权威数据，Region 顶层的地表视图在 Java 中由 `surface` 派生，不在 gzip 中重复保存。

### 稳定随机从哪里来

每座城市的初始随机源只依赖稳定城市 ID：

```text
citySeed = unsigned(city.id().hashCode())
cityRandom = new Random(citySeed)
```

每个 Region 再混入 `regionSeedBase`、城市 ID、Region 类型 ID 和实例序号；每层继续混入 layer ordinal。由此可得：

- 同一代码、同一注册顺序和同一 ID 会复现相同布局；
- 修改显示名但不改 ID，通常不会直接改变城市 seed；
- 修改 ID、Region 数量/顺序、候选参数，或在既有稳定随机序列中插入一次额外随机调用，都可能让后续布局整体漂移。

“确定性”不表示布局永远不变，而是表示相同输入必然得到相同输出。

### 游戏如何逐 Chunk 放置

`TerraLayoutResource.preload()` 启动时先读取 index，严格检查 index 和国家文件的 `schema_version==16`，再恢复 Builder 引用、道路图、Parcel 与建筑槽位。

`MobilePlotStructurePlacement.isPlacementChunk(x,z)` 只查询 `TerraLayoutResource.mobilePlotRegion(x,z)`；若当前 Chunk 属于任意 Region，就创建一次 `mobile_plot` 结构起点。运行时基准高度为：

$$
\begin{aligned}
\mathrm{baseY}&=\mathrm{terrainProfile.groundY}+1,\\
\mathrm{layerY}&=\mathrm{baseY}+16\times\mathrm{layerOrdinal},\\
Y_{\mathrm{POWER}}&=\mathrm{baseY},\\
Y_{\mathrm{SUPPORT}}&=\mathrm{baseY}+16,\\
Y_{\mathrm{LIFE}}&=\mathrm{baseY}+32,\\
Y_{\mathrm{SURFACE}}&=\mathrm{baseY}+48.
\end{aligned}
$$

每个 Chunk、每层按以下优先级处理：

1. 若是 `stairChunk`，放 `mobile_plot_stair`。楼梯优先于道路，因为它在布局中占用已接路的道路 Chunk。
2. 否则若是道路，调用 `roadTile(...)` 选择 `isolated/end/straight/corner/tee/cross` 模板并旋转。
3. 否则若是地下三层，放该层的 16×16×16 通用分层构件。
4. 地表非道路 Chunk 不逐格放通用模板；只在建筑 `chunkArea.minChunkX/Z` 所在锚点展开对应 Jigsaw 建筑，并验证旋转后 footprint 没有越界。

这就是“离线求解、在线查表”的边界：游戏不会在生成 Chunk 时重新跑 Voronoi、候选评分或 BFS。

布局使用城市 ID 派生的稳定随机源，因此相同代码和输入会得到可复现结果。不过，调整国家点位、城市清单、Region 参数或随机调用顺序，都可能使较大范围的布局重新计算。已有世界中已经生成的区块不会自动重建，实际验收应使用固定种子和从未生成过的泰拉区块。

## 7. 哪些 Builder 字段当前真正生效

不要仅凭 Builder 存在某个方法，就假设当前生成器已经使用它：

| 配置 | 当前状态 |
| --- | --- |
| `NationBuilder.position/cities/underground/size` | 已参与国家与城市布局。 |
| `TerraCityBuilder.position` | 已参与城市站点映射。 |
| `rotation` | 仅导出元数据；当前不旋转城市站点、Region 或建筑。 |
| `plotCountRange/maxPlotCoverage/roadWidthChunks/candidateCount` | 已参与 `MobileCityLayoutGenerator`。 |
| `TerraCityBuilder.regionLayout/slotCount` | 保留 API；当前移动地块生成器未读取。 |
| Region `weight/regionLayout/plotSizes/countRange/unique/buildings` | 已参与 Region 选择、内部布局或建筑分配。 |
| Region `buildingLayout` | 导出并在读取时核对，但当前 Parcel 切分不读取此 Layout 对象。 |
| `RoadConfig` 三种宽度 | 已校验且当前必须全部为 1 Chunk。 |
| `RoadConfig.gridSpacingChunks` | 已参与 GRID 与 RADIAL_GRID 间距。 |
| `RoadConfig.extraEdgeRatio/maxCandidateAttempts` | 当前只在 `RoadConfig` 构造时校验并保存在 Builder；生成器与布局导出器都未读取。 |

## 8. 推荐的新增顺序

由于注册类之间通过延迟 Supplier 互相引用，实际修改时按依赖内容准备即可：

1. 从 PRTS 等项目指定资料确认国家名、城市名、归属与可用原始素材；只把坐标和边界视为游戏化布局。
2. 若需要新建筑，先完成城市结构 Builder、NBT、footprint 和入口面。
3. 在 `ModNation` 增加国家基本声明，并让 `cities(...)` 保持延迟 Supplier。
4. 在 `ModCityRegion` 增加该国家的 Region 类型和建筑池。
5. 在 `ModCity` 增加城市，并引用同一国家的 Region。
6. 回到国家的 `cities(...)` 清单加入所有城市。
7. 运行测试和 `runData`，审查国家、城市与 Region 数量以及生成资源差异。

## 9. 验收清单

- 国家 ID、城市 ID 稳定且唯一，城市和 Region 的国家归属一致。
- 地表国家定位折线没有导致异常狭长或消失的 Voronoi 单元；地下国家边界未越界。
- 每座城市都能容纳必选 Region，并达到 `plotCountRange` 下限和覆盖率约束。
- 高权重核心区靠近城市核心，郊区分布和城市道路连接符合预期。
- 每个 Region 恰有 `power`、`support`、`life`、`surface` 四层。
- 四层楼梯坐标一致且至少四个；各层道路连通，地表 Entrance 接路，所有 Parcel 临路。
- 建筑 footprint、旋转后入口和 `road_connections` 一致，没有建筑重叠或悬空。
- `runData` 生成的布局 schema、gzip 体积和抽样内容正常。
- 在新世界或未生成区块中验证最终结构；不要用旧区块判断新布局是否生效。
