# Terra 布局资源 Schema v16

运行时资源仍按国家拆分为 `index.json.gz` 与 `nations/<nation_id>.json.gz`，两者的
`schema_version` 必须严格等于 16。v16 不兼容只保存一个楼梯的 v15 数据。

## 四层 Region 布局

`region_layout.mobile_layers` 固定且唯一包含 `power`、`support`、`life`、`surface`。
每层均保存自己的 `layout_type`、`road_graph`、`urban_blocks`、`parcels`、`open_spaces`、
`road_coverage`、`building_coverage` 与 `stair_chunks`。四层道路分别使用稳定的分层
seed 计算；至少四个 `stair_chunks` 必须在四层保持相同 X/Z，且属于各层连通道路栅格。
动力、支持、生活三层分别使用独立的稳定随机源，从 GRID、CONCENTRIC、RADIAL_GRID
中随机选择布局；不同层允许偶然选中相同类型，但道路核心、道路图和后续随机序列仍
相互独立。地表保留 Region 注册的布局类型。各层只共享楼梯位置；楼梯分别位于核心区
四个象限，在保持核心区边距的前提下尽量拉开。

v16 不再在 `region_layout` 顶层重复保存地表的 `road_graph`、`urban_blocks`、
`parcels`、`open_spaces` 和覆盖率；运行时兼容视图直接由 `mobile_layers.surface`
派生。

动力、支持、生活、地表的 Y 偏移依次为 0、16、32、48。下三层的非道路 Chunk
分别放置对应层建筑；地表非道路 Parcel 使用 Region 建筑池。道路在每一层都按
isolated、end、straight、corner、tee、cross 四向掩码选择模板。
所有 Region 内部道路固定为单 Chunk 宽；可达性支路使用到既有道路的最短距离梯度，
禁止用相邻并排道路表达同一条路线。最终道路栅格会迭代清理所有能够安全删除的
2×2 道路块；仅当删除会破坏楼梯、道路连通性或某个建筑的唯一临路入口时保留。

每层的 `road_junctions` 只记录需要显式注明的拐角、T 形和十字路口；直道、端点和
孤立道路可由 `road_graph` 推导，不再逐 Chunk 重复写入：

~~~json
{
  "chunk_x": 12,
  "chunk_z": -4,
  "type": "tee",
  "rotation": "clockwise_90",
  "connection_mask": 7,
  "stair": false
}
~~~

`type` 只能是 `corner`、`tee`、`cross`。`connection_mask` 使用四位方向掩码：
NORTH=1、EAST=2、SOUTH=4、WEST=8；示例中的 7 表示北、东、南三面连通。
`road_tile_counts` 同时记录每层六类构件数量。运行时道路放置和 JSON 导出调用同一个
分类器，不允许分别推断。

## 建筑连通面

`BuildingParcel` 与 `building_slots` 使用有序的 `road_connections` 数组：

~~~json
{
  "face": "south",
  "road_id": 12,
  "road_class": "service"
}
~~~

数组第一项是兼容字段 `road_facing` / `adjacent_road_id` 表示的主入口。每一项都必须
引用本层存在的道路边，并且 Parcel 在对应面与该道路矩形真实面邻接。建筑模板通过
`connectionFaces(...)` 声明本地真实入口；旋转后只有模板支持的世界方向会进入
`building_slots.road_connections`。

## 可达性约束

- 每层道路栅格必须整体连通。
- 地表 Entrance 必须落在地表道路上。
- 每个 Parcel 至少有一个真实道路连通面。
- 四层楼梯必须垂直对齐并接入各层道路；因此任意下层建筑可经本层道路、楼梯和地表
  道路到达 Region Entrance。
- 每层每个 Chunk 必须且只能属于道路或一个 Parcel；楼梯在运行时替代其道路 Chunk。

`RegionLayout` Java 对象中的顶层 `roadGraph`、`parcels` 等兼容视图由 surface 层
派生；它们不再作为重复字段写入 v16 JSON。新消费者应读取 `mobile_layers` 内的
权威分层数据。
