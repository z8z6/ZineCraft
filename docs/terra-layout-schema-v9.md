# Terra Layout JSON Schema v9

本文说明泰拉 Nation、City、Region 三级布局 JSON 的 v9 数据格式。

运行时资源位于：

```text
src/generated/resources/data/zinecraft/terra_layout.json
```

数据由 `TerraLayoutDataExporter` 生成，由 `TerraLayoutResource` 读取。运行时读取器要求
`schema_version` **严格等于 9**，不会自动兼容其他版本。

这份 JSON 是二维城市规划数据，不表示建筑已经在世界中生成。当前没有 Y 坐标、建筑实际占地、建筑朝向或最终
Structure Piece。

## 坐标和通用约定

- 使用 Minecraft 世界的 X/Z 水平坐标。
- `coordinate_unit` 为 `minecraft_block`，绝对坐标和长度的单位均为方块。
- 坐标和长度使用 JSON number，通常由 Java `double` 导出。
- 所有 `boundary` 和 `corners` 都是不重复首点的开放点数组；消费端需要自行将最后一点连接回第一点。
- 多边形点按边界环顺序排列，但顺/逆时针方向不应作为 schema API 使用。
- `boundary` 表示多边形边界，不等同于生成站点、声明折线或移动地块范围。
- `normalized_slot` 是布局算法的归一化坐标，不是世界坐标。
- v9 只包含 X/Z 平面信息，不包含高度、地表采样或地下深度。

### Point

所有点都使用相同结构：

```json
{
  "x": 123.5,
  "z": -456.25
}
```

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| `x` | number | X 坐标。绝对点时单位为方块，归一化点时为无量纲值。 |
| `z` | number | Z 坐标。绝对点时单位为方块，归一化点时为无量纲值。 |

### 通用地点字段

Nation、City 和 Region 都包含以下字段，但三者的 `center` 含义略有区别。

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| `id` | string | 当前节点的注册 ID。 |
| `zh_cn_name` | string | 简体中文显示名。 |
| `center` | Point | 当前节点的生成站点。它不保证是 `boundary` 的面积质心。 |
| `boundary` | Point[] | 当前节点的世界坐标多边形边界，至少包含 3 个点。 |

具体来说：

- Nation `center` 是国家声明折线的中点站点；地下国家同样以该点作为固定边界中心。
- City `center` 是城市归一化声明位置映射进 Nation 后得到的 Voronoi 站点。
- Region `center` 是 Region 布局 slot 映射进 City 后得到的 Voronoi 站点。
- `mobile_plot.center` 是最大移动地块矩形的中心，与 Region `center`、连接点平均值均无必然关系。
- Building Slot `center` 是该建筑候选 slot 的世界坐标。

## 根对象

```json
{
  "schema_version": 9,
  "coordinate_unit": "minecraft_block",
  "core_size_x": 80000,
  "core_size_z": 50000,
  "boundary": [],
  "nations": []
}
```

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| `schema_version` | integer | Schema 版本。v9 固定为 `9`。 |
| `coordinate_unit` | string | 坐标单位。v9 固定为 `minecraft_block`。 |
| `core_size_x` | integer | 泰拉核心规划矩形在 X 方向的完整长度。当前数据为 `80000`。 |
| `core_size_z` | integer | 泰拉核心规划矩形在 Z 方向的完整长度。当前数据为 `50000`。 |
| `boundary` | Point[] | 泰拉核心规划矩形的世界边界。当前为 4 个角点。 |
| `nations` | Nation[] | 所有地表和地下国家的布局计划。 |

核心矩形以世界原点为中心，因此当前范围是：

```text
X: [-core_size_x / 2, core_size_x / 2]
Z: [-core_size_z / 2, core_size_z / 2]
```

## Nation

Nation 在通用地点字段之外包含：

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| `neighboring_nation_ids` | string[] | 与本国共享有效边界的同级 Nation ID。关系必须双向，不包含自身。 |
| `underground` | boolean | 是否为地下国家。 |
| `size` | integer | 地下国家固定正方形边界的边长，单位为方块。地表国家当前通常为 `0`，该值对其边界无效。 |
| `normalized_polyline` | Point[] | 国家注册时声明的归一化站点折线，X/Z 分别相对于泰拉核心矩形半边长。 |
| `polyline` | Point[] | `normalized_polyline` 缩放到世界坐标后的站点折线。它不是 Nation `boundary`。 |
| `cities` | City[] | 国家内部的 City 布局计划。 |

地表国家使用声明折线参与折线 Voronoi 切分。地下国家不参与地表 Nation Voronoi，而是以 `center` 为中心、
以 `size` 为边长建立固定正方形边界。

缩放关系为：

```text
polyline.x = normalized_polyline.x * core_size_x / 2
polyline.z = normalized_polyline.z * core_size_z / 2
```

## City

City 在通用地点字段之外包含：

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| `neighboring_city_ids` | string[] | 在同一 Nation 内与本 City 共享有效边界的 City ID。关系必须双向。 |
| `rotation_degrees` | number | Region 布局 slot 围绕 City 站点的正角规划旋转值，按 `[0, 360)` 归一化。具体方向由 X/Z 变换定义。 |
| `regions` | Region[] | City 内部的 Region Voronoi 单元。 |

`rotation_degrees` 只影响 Region 站点的布局方向，不代表 City 边界、移动地块或建筑 NBT 可以按任意角度旋转。
v9 的移动地块固定与世界 X/Z 轴平行。

City ID 使用英文 snake_case，例如 `dewville`、`lungmen`。

## Region

Region 在通用地点字段之外包含：

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| `slot_index` | integer | Region 在所属 City 布局中的 slot 索引，只要求在该 City 内唯一。 |
| `connections` | RegionConnection[] | 与相邻 Region 的连通关系和连通点。 |
| `mobile_plot` | MobilePlot | Region 内由完整区块组成的最大轴对齐移动地块矩形。 |
| `building_layout` | string | Region 使用的建筑候选 slot 布局 ID。当前声明默认是 `grid`。 |
| `building_slots` | BuildingSlot[] | 已分配建筑类型的候选 slot。它们不是最终建筑放置结果。 |
| `normalized_slot` | Point | Region 在 City 布局中的原始归一化 slot 坐标。 |

当前 Region ID 形如 `kazimierz/region_y2a0v2`。它是注册 ID，消费端应将其作为不透明字符串使用，不应解析
`region_` 后的内容。

`normalized_slot` 经过 City `rotation_degrees` 和父多边形映射后形成 Region `center`，不能通过简单乘以 City
边界宽高还原世界坐标。

### RegionConnection

```json
{
  "neighboring_slot_index": 1,
  "point": {
    "x": 100.0,
    "z": 200.0
  }
}
```

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| `neighboring_slot_index` | integer | 相邻 Region 在同一 City 内的 `slot_index`。 |
| `point` | Point | 两个 Region 共享边界中一条有效邻边的中点，使用世界坐标。 |

连接关系必须成对出现。如果 Region A 指向 Region B，则 B 必须指向 A，并且两条记录必须使用完全相同的
`point`。连接点位于 Region 共享边界上，不保证位于 `mobile_plot` 内。

### MobilePlot

```json
{
  "center": { "x": 104.0, "z": 200.0 },
  "half_size_x": 40.0,
  "half_size_z": 56.0,
  "rotation_degrees": 0.0,
  "area": 8960.0,
  "corners": []
}
```

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| `center` | Point | 最大移动地块矩形自身的世界中心。它不再由 Region 连通点计算，也不要求是 16 的倍数。 |
| `half_size_x` | number | 矩形沿世界 X 轴的半边长，单位为方块。因为完整宽度按区块对齐，所以该值是 8 的倍数。 |
| `half_size_z` | number | 矩形沿世界 Z 轴的半边长，单位为方块。因为完整深度按区块对齐，所以该值是 8 的倍数。 |
| `rotation_degrees` | number | 矩形旋转角。v9 当前固定为 `0.0`。 |
| `area` | number | 矩形面积，等于 `4 * half_size_x * half_size_z`。 |
| `corners` | Point[4] | 矩形四角的世界坐标。 |

移动地块将 Region 覆盖的完整 16×16 区块作为可用单元，再通过最大矩形直方图算法求得，因此是在区块网格
约束下由完整区块组成的确定性最大矩形，并满足：

- 完整矩形被 Region `boundary` 覆盖。
- 矩形平行于世界 X/Z 轴。
- `minX`、`maxX`、`minZ`、`maxZ` 均为 16 的倍数。
- 完整宽度 `2 * half_size_x` 和完整深度 `2 * half_size_z` 均为 16 的倍数。
- `half_size_x` 和 `half_size_z` 均大于 0。
- `center` 不要求等于 Region `center` 或连接点中心。
- 当某方向包含奇数个区块时，该方向的 `center` 坐标是 8 的奇数倍，因此不要求中心坐标是 16 的倍数。

对于 v9 当前固定的零旋转矩形：

```text
minX = center.x - half_size_x
maxX = center.x + half_size_x
minZ = center.z - half_size_z
maxZ = center.z + half_size_z
```

### BuildingSlot

```json
{
  "slot_index": 0,
  "building_id": "kazimierz_shop",
  "center": { "x": 100.0, "z": 200.0 },
  "normalized_slot": { "x": -0.81, "z": -0.81 }
}
```

| 字段 | 类型 | 含义 |
| --- | --- | --- |
| `slot_index` | integer | 建筑候选 slot 在当前 Region 内的索引。 |
| `building_id` | string | 通过 Region 权重规则分配到该 slot 的 `JigsawBuilder.path`。 |
| `center` | Point | 候选建筑锚点的世界 X/Z 坐标。 |
| `normalized_slot` | Point | 相对于 `mobile_plot` 的有效归一化坐标。 |

建筑 slot 由 `building_layout` 生成，再按 0.9 的内缩系数映射进 `mobile_plot`，从而避免锚点落在矩形边界上。
当前 Grid 默认 100 个 slot 时，坐标绝对值最大为 `0.81`。

对于 v9 的轴对齐移动地块，映射关系为：

```text
center.x = mobile_plot.center.x + normalized_slot.x * mobile_plot.half_size_x
center.z = mobile_plot.center.z + normalized_slot.z * mobile_plot.half_size_z
```

需要注意：

- `building_slots` 是候选锚点；当前移动地块世界生成器会尝试展开每一个 slot，尚未执行建筑间碰撞淘汰。
- Building Slot `center` 不要求对齐区块；区块对齐约束只作用于 `mobile_plot` 的边界和完整长宽。
- v9 没有记录建筑宽度、深度、高度、入口、朝向、镜像或最终 BoundingBox。
- 多个候选建筑的实际占地可能重叠。
- 后续放置器必须结合建筑 footprint、道路、入口和碰撞检测生成最终 `BuildingPlacementPlan`。
- `building_id` 是项目结构路径；需要资源位置时由消费端按项目 namespace 解析。

## 移动地块世界生成

`zinecraft:mobile_plot` StructureSet 是 v9 布局的运行时消费者：

1. `mobile_plot` 的四边都已对齐区块边界，放置器会让矩形完整覆盖的每个区块各生成一次结构起点。
2. 每个结构起点在区块最小 X/Z 坐标放置一份 `mobile_plot_power_layer`，固定为 `Rotation.NONE`，因此 16×16 模板恰好覆盖该区块。
3. 每个动力层区块独立以当前区块中心 X/Z 处 `WORLD_SURFACE_WG` 的首个可用方块高度作为 `baseY`，也就是动力层底板所在的局部地表高度。相邻区块地形高度不同时，动力层允许随地形产生高度差。
4. 动力层模板高度为 31，封顶板位于局部 Y=30；候选建筑的 Jigsaw 起点位于顶板上方一格 `baseY + 31`。
5. 一个建筑 slot 只由包含其 `center` 的区块消费。Jigsaw 四向旋转由世界种子和结构区块确定，因此相同世界种子下结果稳定。
6. 当前会尝试展开全部候选 slot，但不会在不同 slot 之间做 footprint 碰撞检测；发生重叠时需要后续 `BuildingPlacementPlan` 阶段解决。

世界生成器只查询噪声高度和当前布局资源，不会为了确定相邻动力层高度而加载远端区块。

`mobile_plot` 的允许群系显式使用 `ModBiome.ALL_TERRA_BIOMES`。不能使用
`#minecraft:is_overworld` 代替，因为泰拉自定义群系当前没有加入该原版标签，使用该标签会使整个移动地块结构在泰拉维度被群系校验拒绝。

建筑候选作为 `zinecraft:mobile_plot` StructureStart 内部的 Jigsaw Piece 保存，不是独立 StructureStart。
因此原版 `/locate structure zinecraft:<building_id>` 无法定位 embedded building。应使用布局定位命令：

```text
/zinecraft locate_building <building_id>
```

命令返回距离执行位置最近的匹配 slot，以及所属 Nation、City、Region 和 slot 索引；它定位的是布局锚点，
目标区块仍须使用当前数据版本重新生成后才会出现建筑。

## v9 数据不变量

v9 生成结果必须满足以下关系。其中一部分由运行时 record/资源读取器再次校验，几何包含关系主要由生成算法保证：

1. `schema_version == 9`。
2. Nation、City 的邻接关系双向且不包含未知 ID。
3. Region 连接关系双向，并共享完全相同的连接点。
4. 同一 City 内 Region `slot_index` 唯一。
5. `mobile_plot` 非退化、轴对齐、完整位于对应 Region 内，四条边及完整长宽均为 16 的倍数。
6. Region 的建筑 slot 数量等于该 Region 声明的 `slotCount`。
7. 同一 Region 内 Building Slot `slot_index` 唯一。
8. Building Slot 引用的建筑必须属于该 Region 的合法建筑清单。
9. 所有 Building Slot `center` 必须位于 `mobile_plot` 内。
10. JSON 的 `building_layout` 必须与运行时 Region 声明一致。

消费端比较浮点坐标或执行包含判断时应使用合理容差，不应依赖十进制文本完全相等；Region 双向连接点除外，
生成器会向两侧写入同一个点值。

## 生成和兼容性

重新生成布局数据：

```powershell
.\gradlew.bat generateTerraLayoutData
```

修改字段、字段含义或必需约束时，应同步：

1. 提升 `schema_version`。
2. 修改 `TerraLayoutDataExporter`。
3. 修改 `TerraLayoutResource`。
4. 更新本文档。
5. 重新生成 `terra_layout.json` 并运行 `gradlew build`。
