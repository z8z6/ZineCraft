# Region 结构与道路生成总结

本文总结 Terra 城市中一个 Region 从布局计算到世界结构落地的当前实现。数据格式以
[terra-layout-schema-v16.md](terra-layout-schema-v16.md) 为准，NBT 制作与替换参见
[mobile-plot-structure-replacement.md](mobile-plot-structure-replacement.md)。

## 1. 生成链路

```text
TerraLayoutCalculator
  -> CityLayoutCalculator
    -> MobileCityLayoutGenerator：划分城市内移动地块
    -> RegionLayoutGenerator：为每个 Region 生成四层布局
      -> RegionLayoutValidator：校验道路、楼梯、Parcel 和入口
  -> TerraLayoutDataExporter：写出 schema v16 国家级 gzip
  -> TerraLayoutResource：运行时读取
  -> MobilePlotStructure：按 Chunk 放置道路、楼梯和建筑
```

Region 规划使用 Chunk 坐标。相同输入和 seed 必须得到相同结果；随机调整必须使用已有
稳定随机源，避免插入无关随机调用后令全部布局漂移。

## 2. Region 的四层结构

`RegionLayout.mobileLayers` 固定包含：

| 层 | 相对 Y | 非道路区域 |
| --- | ---: | --- |
| `power` | 0 | 动力层建筑 |
| `support` | 16 | 支持层建筑 |
| `life` | 32 | 生活层建筑 |
| `surface` | 48 | Region 建筑池分配的建筑 |

每层拥有独立的 `layout_type`、`road_graph`、`urban_blocks`、`parcels`、
`open_spaces` 和覆盖率。地表使用 Region 注册的布局类型；下三层用各自的稳定 seed
从 GRID、CONCENTRIC、RADIAL_GRID 中随机选择，因此四层可以完全不同。

四层只共享一组 `stair_chunks`。生成器以 Region 核心为中心，在四个象限各选择一个
内部点，形成至少四个尽量分散且互不重叠的楼梯坐标。每个楼梯点会分别接入四层道路；
运行时在每个楼梯坐标的四个高度各放置一段 16 格高楼梯模板，组成贯通竖井。

## 3. 单层道路生成

每层按以下顺序生成：

1. 计算本层 hub。不同层使用不同象限和随机扰动，避免复制相同骨架。
2. 地表把 Region Entrance 接入主路；首个入口连接 hub，其余入口汇入最近的已有道路，
   避免为每个入口重复铺设平行主路。下层没有外部 Entrance。
3. 连接 Region 中心与本层 hub。
4. 按布局类型增加候选道路：
   - GRID：生成横纵网格，并跳过紧邻既有同向道路的候选线。
   - CONCENTRIC：生成围绕 hub 的正交环路与连接线。
   - RADIAL_GRID：生成径向骨架，再叠加较稀疏的网格。
5. 将全部楼梯点通过距离场最短梯度接入现有道路。
6. 从距离道路最远的可建造格反复补充单格宽 service 支路，直到所有待分配格能够形成
   真实临路 Parcel。
7. 对最终栅格迭代清理 2×2 道路块。只有在删除某格后道路仍整体连通、楼梯/Entrance
   不受影响、所有非道路格仍至少邻接道路时才删除。若某段是建筑唯一入口则保留，
   可达性优先于纯视觉去重。
8. 将道路栅格重新整理为 RoadGraph，提取非道路连通块并划分 Parcel。

所有 Region 内道路宽度固定为一个 Chunk。`RoadConfig` 的 primary、secondary、
service 宽度都必须为 1；道路等级仅表达优先级和建筑邻接信息，不再表达多车道宽度。

## 4. 道路构件分类

`RegionLayout.roadTile(layer, x, z)` 检查北、东、南、西四个相邻道路格，并统一返回：

| 连通数/形态 | 构件 |
| --- | --- |
| 0 | `isolated` |
| 1 | `end` |
| 2，方向相反 | `straight` |
| 2，方向相邻 | `corner` |
| 3 | `tee` |
| 4 | `cross` |

该结果同时供 `MobilePlotStructure` 选择并旋转 NBT，以及
`TerraLayoutDataExporter` 导出 JSON，禁止两处分别推断。

schema v16 的 `road_junctions` 只显式保存 `corner`、`tee`、`cross`。每项包含 Chunk
坐标、类型、旋转、`stair` 标记和四位 `connection_mask`：

| 方向 | 掩码 |
| --- | ---: |
| NORTH | 1 |
| EAST | 2 |
| SOUTH | 4 |
| WEST | 8 |

直道、端点和孤立道路可由 `road_graph` 推导；每层的 `road_tile_counts` 仍记录六类
构件总数。

## 5. Parcel、建筑连通面与可达性

道路栅格确定后，非道路连通块被提取为 `UrbanBlock`，再切分为
`BuildingParcel`。每个 Parcel 必须至少与一条本层 RoadEdge 真实面邻接。

`road_connections` 是完整入口列表，每项包含：

- `face`：Parcel 面向道路的世界方向。
- `road_id`：实际相邻的 RoadEdge。
- `road_class`：该道路等级。

`road_facing`、`adjacent_road_id` 和 `adjacent_road_class` 只是第一入口的兼容视图。
建筑模板通过 `connectionFaces(...)` 声明本地真实入口面；模板旋转后，只有兼容的
世界方向会写入 `building_slots.road_connections`。

验证器保证：

- 每层道路栅格整体连通。
- 地表所有 Entrance 落在地表道路上。
- 四层拥有相同且至少四个楼梯坐标，每个楼梯属于各层道路。
- 每个 Parcel 的全部声明入口都引用真实相邻道路。
- 每层 Chunk 不重叠、不遗漏，并属于道路或一个 Parcel。

因此下层建筑可以经本层道路到达任一楼梯，再通过竖井到达地表道路和 Region Entrance。

## 6. 运行时结构落地

`MobilePlotStructure` 以城市 `ground_y + 1` 为基准，按当前 Chunk 执行：

1. 如果 Chunk 是本层楼梯坐标，放置 `mobile_plot_stair`。
2. 否则如果是道路，调用 `roadTile(...)` 选择六种道路模板之一并按返回值旋转。
3. 否则在下三层放置对应的 16×16×16 分层建筑。
4. 地表按照 `building_slots` 的锚点、占地、朝向和候选池放置建筑。

楼梯优先于道路放置，因为楼梯在布局中占用一个已接路的道路 Chunk。结构只会在尚未
生成的区块中按新数据落地；修改 NBT 或布局不会重写已有世界区块。

## 7. schema v16 与体积控制

运行时资源按国家拆分为：

```text
data/zinecraft/terra_layout/index.json.gz
data/zinecraft/terra_layout/nations/<nation_id>.json.gz
```

v16 的体积控制包括：

- 仅保存需要显式注明的 `road_junctions`，不逐格保存全部道路构件。
- 用整数 `connection_mask` 代替方向字符串数组。
- 地表兼容视图直接从 `mobile_layers.surface` 派生，不在 Region 顶层重复保存
  road graph、Parcel、open space 和覆盖率。
- 保留四层完整 RoadGraph 与 Parcel，因为运行时放置和可达性需要这些权威数据。

不要手工编辑 gzip。修改模型、生成器或注册信息后运行 `runData` 重新生成。

## 8. 关键实现文件

- `src/main/java/com/cxxcxx/zinecraft/api/world/city/RegionLayout.java`
- `src/main/java/com/cxxcxx/zinecraft/core/nation/RegionLayoutGenerator.java`
- `src/main/java/com/cxxcxx/zinecraft/core/nation/RegionLayoutValidator.java`
- `src/main/java/com/cxxcxx/zinecraft/core/nation/CityLayoutCalculator.java`
- `src/main/java/com/cxxcxx/zinecraft/core/datagen/TerraLayoutDataExporter.java`
- `src/main/java/com/cxxcxx/zinecraft/core/nation/TerraLayoutResource.java`
- `src/main/java/com/cxxcxx/zinecraft/api/world/structure/MobilePlotStructure.java`
- `src/main/java/com/cxxcxx/zinecraft/api/registry/catalog/StructureCatalog.java`

## 9. 验证流程

```powershell
./gradlew.bat test -x generateTerraLayoutData --no-configuration-cache --console=plain
./gradlew.bat runData --no-configuration-cache --console=plain
./gradlew.bat build --no-configuration-cache --console=plain
```

除了构建成功，还应抽查生成 gzip：

- `schema_version` 为 16。
- 每个 Region 恰有四层，每层至少四个 `stair_chunks`，四层列表相同。
- `road_junctions.type` 只包含 corner、tee、cross。
- 所有 RoadEdge 宽度为 1。
- 每个 Parcel 至少有一项 `road_connections`。
- gzip 总体积没有因重复兼容字段或逐格道路明细异常增长。
