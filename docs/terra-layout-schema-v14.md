# Terra 布局资源 Schema v14

运行时资源按国家拆分并使用 gzip 压缩：

- `data/zinecraft/terra_layout/index.json.gz`
- `data/zinecraft/terra_layout/nations/<nation_id>.json.gz`

索引和国家文件的 `schema_version` 均严格等于 14。未压缩的单体 JSON 只作为构建目录中的人工验收报告，不进入模组包。

顶层 `building_types` 按建筑 ID 列出所有 Region 建筑类型，包含：

- `id`
- `zh_cn_name`
- `en_us_name`
- `footprint_chunks_x`
- `footprint_chunks_z`

每个 `building_slots` 项同时记录 `building_name` 与 `building_name_en_us`，便于不查询目录时直接识别建筑。

Region 内所有 Chunk 必须且只能属于道路或一个建筑占地；`open_spaces` 当前为空。普通商店为 1×1 Chunk，中型商店为 1×2 Chunk，地标默认采用 2×2 Chunk。

每个 `region_layout.mobile_layers` 固定包含 `power`、`support`、`life` 三层。每层记录当前唯一建筑 ID 和覆盖整个 Region 的 `chunk_area`；对应模板均为 16×16×16，世界生成高度偏移依次为 0、16、32。
Nation 与 City 外边界仍沿用既有 Voronoi 地理规划；City 内部的 Region 已改为
docs/layout.md 定义的 Chunk 网格正交生长布局。

## City 新增字段

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| city_core | Point | 距 City 边界最远的完整 Chunk 单元中心 |
| usable_chunk_area | integer | 完全位于 City Polygon 内的 Chunk 数 |
| terrain_profile | CityTerrainProfile | 此城市唯一的地面、承重层和平地过渡参数 |
| min_plot_count | integer | Region 地块数量下限，默认 10 |
| max_plot_count | integer | Region 地块数量上限，默认 100 |
| max_plot_coverage | number | 地块面积覆盖率硬上限，默认 0.45 |
| plot_coverage | number | 实际地块 Chunk 面积 / 可用 Chunk 面积 |
| road_width_chunks | integer | 道路宽度，默认 1 Chunk |
| roads | UrbanRoad[] | 生长时同步产生的道路边 |

regions 不再表示互相拼接并全覆盖 City 的 Voronoi 单元。每项 Region 就是一块
轴对齐移动地块，其 boundary 与 mobile_plot.corners 相同，边界和长宽均对齐
16 blocks。未被地块或道路使用的 City 区域保留给后续自然地形规划。

terrain_profile 冻结 ground_y、foundation_depth、foundation_blend_depth、
surface_lock_depth、flat_shoulder、transition_width、plane_slope 与
plane_amplitude。同一城市不得按
Region 改写这些值。地表城市当前统一使用 ground_y=80；移动地块、City 道路、
Region 道路和广场共同构成一个 UrbanFlatArea 矩形 Union，只有 Union 外围产生
smootherstep 过渡。

## UrbanRoad

~~~json
{
  "from_plot_id": 0,
  "to_plot_id": 1,
  "chunk_area": {
    "min_chunk_x": 12,
    "min_chunk_z": -8,
    "width_chunks": 6,
    "length_chunks": 1
  }
}
~~~

chunk_area 使用左闭右开范围。道路只沿 X/Z 轴延伸，并连接 from_plot_id 与
to_plot_id；全部 Region 必须从 plot 0 经道路图可达。

## 建筑槽位

每个 building_slots 项新增以下字段：

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| chunk_area | ChunkRectangle | 建筑在 Region 内实际占用的完整 Chunk 矩形 |
| parcel_id | integer | 建筑所属的最终 BuildingParcel |
| adjacent_road_id | integer | 建筑朝向的相邻 Region 内道路 |
| facing | string | 建筑正面朝向 |
| rotation | string | 从模板默认正面 +Z（南）旋转到 facing 的变换 |

建筑注册必须通过 footprint(chunksX, chunksZ) 声明 X/Z 方向的 Chunk 数。
模板宽度和长度分别不得超过 chunksX * 16 与 chunksZ * 16 blocks；建筑槽位之间
不得重叠，并且必须完整位于所属 Region 内。当前内置城市建筑统一注册为 1x1
Chunk 的带门火柴盒，后续建筑可以按实际模板扩大声明。

## RegionLayout

每个 Region 注册时必须明确传入 GRID、CONCENTRIC 或 RADIAL_GRID，生成器不得根据
名称推断。region_layout 保存 local_center、entrances、road_graph、urban_blocks、
parcels、open_spaces、road_coverage、building_coverage 和 debug_stages。

生成顺序固定为：入口 → Primary RoadGraph → 布局专属 Secondary Road → 道路栅格化
→ UrbanBlock → 矩形 Parcel → 建筑评分与朝向。所有入口和道路必须连通，建筑不得与
道路重叠且必须邻接道路。

道路 Structure 使用 isolated、end、straight、corner、tee、cross 六类 16x1x16 NBT，
运行时按道路 Chunk 的四向连接掩码选择并旋转；其中包含独立的拐角模板。

## 生成约束

- 每种 Region 类型先生成 minCount 个，unique() 类型最多 1 个。
- 建筑数量不固定。Region 注册声明数量范围和目标覆盖率，生成器依据面积、可用临路
  Parcel、目标覆盖率及确定性扰动决定实际数量；当前默认范围为 4..24。
- 城市总地块数必须位于 [min_plot_count, max_plot_count]。
- 地块覆盖率不得超过 max_plot_coverage，道路默认不计入覆盖率。
- 地块尺寸只能来自类型声明的离散 PlotSize，允许长宽交换。
- 所有 Region 的面积不得小于 80 Chunk；普通 Region 默认尺寸为 16x12、12x8
  或 10x8 Chunk。
- 核心区使用 40x32、32x32 或 32x24 Chunk；最小面积 768 Chunk，是普通
  Region 最大面积 192 Chunk 的 4 倍。布列洁诺伊的“中心矿区”作为其唯一核心
  地块，应用相同尺寸规则。
- 建筑模板南侧（+Z）是默认正面，最终按临路方向旋转到北、东、南或西。
- 任意两个地块之间至少保留 road_width_chunks 个 Chunk。
- 同一 City ID 使用固定随机种子；相同输入必须产生相同布局。
- 生成失败必须给出 CITY_TOO_SMALL、MANDATORY_PLOTS_CANNOT_FIT、
  MINIMUM_PLOT_COUNT_CANNOT_FIT 或 INVALID_CONFIGURATION。

v11 及更早版本不包含 RegionLayout、RoadGraph、Parcel 与动态建筑数量，仅用于历史说明。

## JourneyMap 显示

- 国家仅绘制边界线，不再填充背景色。
- 城市名称从 zoom 256 开始显示。
- Region 名称从 zoom 512 开始显示；Region 边界与填充均不绘制。

## 城市地形断面调试

TerraTerrainTransectExporter.export 可对指定 X/Z 线段按 1 或 4 blocks 采样并输出
CSV。字段包含 urbanWeight、distanceToFlatArea、Dnatural、DforcedFlat、Dfinal
和 estimatedSurfaceY；其 Y 扫描只在显式调试导出时运行，不进入 DensityFunction
compute 热路径。
