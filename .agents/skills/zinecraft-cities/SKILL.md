---
name: zinecraft-cities
description: Add or revise Zinecraft Terra cities, city regions, four-layer mobile-plot layouts, roads, stairs, parcels, and structure-backed urban buildings. Use for multi-region urban layout; use the structure skill for a standalone building asset.
---

# Zinecraft 城市

城市使用“国家边界 → 城市 → Region → 四层移动地块 → 世界结构”的确定性生成链路。

## 入口与文档

- 注册：`core/registry/ModNation.java`、`ModCity.java`、`ModCityRegion.java`
- 构建器：`TerraCityBuilder`、`TerraCityRegionBuilder`
- 计算与校验：`TerraLayoutCalculator`、`CityLayoutCalculator`、`RegionLayoutGenerator`、`RegionLayoutValidator`
- 数据模型：`RegionLayout`、`CityRegionBuildingSlot`
- 运行时：`MobilePlotStructure`、`MobilePlotStructurePlacement`
- 导出与读取：`TerraLayoutDataExporter`、`TerraLayoutResource`
- 当前算法总览：`docs/region-structure-road-generation.md`
- 数据格式：`docs/terra-layout-schema-v16.md`
- NBT 维护：`docs/mobile-plot-structure-replacement.md`

修改 Region 结构、道路、楼梯、Parcel、建筑入口或压缩布局时，先读算法总览和 v16
schema；只替换结构 NBT 时改用 `$zinecraft-structures` 并读 NBT 维护文档。

## 关键不变量

- `mobile_layers` 必须完整且唯一包含 `power`、`support`、`life`、`surface`。
- 下三层使用各自的稳定随机源独立选择 GRID、CONCENTRIC 或 RADIAL_GRID；地表使用
  Region 注册的布局类型。不要复用一层道路图生成其他层。
- 核心区至少有四个分散楼梯。四层必须共享同一组 X/Z，且每个楼梯都属于各层连通道路。
- Region 道路固定为单 Chunk 宽。新增道路应汇入最近既有道路，安全移除重复的 2×2
  道路块，但不得因此破坏道路连通、楼梯或建筑唯一入口。
- 每层道路必须整体连通；地表 Entrance 必须接路；每个 Parcel 至少有一个真实邻路面。
- `road_connections` 可包含多个入口面。每项必须引用本层真实存在且与 Parcel 对应面
  接壤的道路；不要只更新兼容字段 `road_facing`。
- 道路类型统一由 `RegionLayout.roadTile(...)` 的四向连接掩码解析。运行时与 JSON
  导出不得各自实现另一套 corner/tee/cross 判断。
- schema v16 的权威 Region 数据位于 `mobile_layers`。顶层地表兼容视图由 surface
  层派生，不要重新写回重复字段。

## 修改流程

1. 城市名、所属国家和相对位置以项目指定资料为依据；`position(...)` 是归一化布局
   坐标，不是官方世界坐标。
2. 在 `ModCity` 声明城市 ID、位置、旋转与 Region 组合；仅在需求偏离默认值时覆盖
   地块数量、覆盖率或候选参数。
3. 在 `ModCityRegion` 配置 Region 权重、布局类型、`RoadConfig`、允许的 `PlotSize`、
   建筑数量/唯一性和候选 `JigsawBuilder`。只选择生成器已经实现的布局类型。
4. 修改分层规划时同步维护模型、生成器、验证器、导出器、读取器和
   `MobilePlotStructure`；字段不兼容时提升 schema 版本。
5. 建筑新增入口时用 `connectionFaces(...)` 声明模板本地真实入口，并验证旋转后的
   世界方向、占地和 `building_slots.road_connections`。
6. 不直接编辑 `src/generated/resources/data/zinecraft/terra_layout/**/*.json.gz`；
   修改源代码后用 `runData` 重新生成并审查体积、schema 和抽样内容。

## 验证

至少运行：

```powershell
./gradlew.bat test -x generateTerraLayoutData --no-configuration-cache --console=plain
./gradlew.bat runData --no-configuration-cache --console=plain
./gradlew.bat build --no-configuration-cache --console=plain
```

核对四层数量与随机布局、楼梯数量/分散度/垂直对齐、各层道路连通、所有 Parcel
临路、多入口引用、路口分类、schema 版本、gzip 体积和生成资源读取。最后用固定种子
在尚未生成的 Terra Chunk 中验证实际结构落地；已有世界区块不会自动重建。
