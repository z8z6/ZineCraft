---
name: zinecraft-cities
description: Add or revise Zinecraft Terra cities, city regions, mobile-plot layouts, roads, and their structure-backed buildings. Use for multi-region urban layout; use the structure skill for a standalone building.
---

# Zinecraft 城市

城市使用“国家边界 → 城市 → 城区 → 移动地块 → 世界结构”的确定性生成链路。

## 当前入口

- 注册：core/registry/ModNation.java、ModCity.java、ModCityRegion.java
- 构建器：TerraCityBuilder、TerraCityRegionBuilder
- 布局：api/world/layout/、api/world/city/ 与 core/nation/
- 落地：MobilePlotStructure、MobilePlotStructurePlacement、ModStructure.enableMobilePlots(...)
- 数据：TerraLayoutDataExporter、TerraLayoutResource
- 设计约束：docs/layout.md、docs/terra-layout-schema-v14.md、docs/mobile-plot-structure-replacement.md、docs/road.md

## 修改流程

1. 城市名、所属国家和相对位置以资料为依据；position(...) 是项目归一化布局坐标，不是官方世界坐标。
2. 在 ModCity 用 TerraCityBuilder 声明 ID、位置、旋转并绑定现有 ModCityRegion；只有需求偏离默认值时才配置 region layout、地块数量、覆盖率、道路宽度和候选数。
3. 在 ModCityRegion 用 TerraCityRegionBuilder 配置权重、region/building layout、RoadConfig、允许的 PlotSize、数量/唯一性和 JigsawBuilder 建筑。当前 builder 只接受已实现的 region layout 类型。
4. 布局总链路是 TerraLayoutCalculator → CityLayoutCalculator。后者分别调用 MobileCityLayoutGenerator 生成城市内移动地块，并调用 RegionLayoutGenerator 生成城区入口、道路、parcel 与 open space；两条结果都需通过对应 validator。
5. 当前城市结构 NBT 均位于 src/main/resources/data/zinecraft/structure/：商店、中型商店、三层和道路使用单个 <id>.nbt，大型建筑使用 <id>/{foundation,core,facade,roof,annex,surrounding}.nbt。仅在确需再生时运行对应脚本，先确认其覆盖范围和工作树状态。
6. 不手改 src/generated/resources/data/zinecraft/terra_layout/ 下的 index.json.gz 与 nations/*.json.gz。它们由 generateTerraLayoutData 生成，runData 结束后会自动恢复。

## 验证

运行 ./gradlew.bat test、./gradlew.bat runData、./gradlew.bat generateTerraLayoutValidation 和 ./gradlew.bat build。检查 SVG/JSON 报告、schema v14 压缩资源、布局确定性、地块/道路约束、结构引用和 JAR 内容；再用固定种子验证实际落地。
