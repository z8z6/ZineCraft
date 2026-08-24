---
name: zinecraft-structures
description: Add or revise Zinecraft Jigsaw structures, mobile-plot buildings, placements, pools, and structure NBT. Use for a standalone worldgen structure or building asset; use the city skill for urban layout.
---

# Zinecraft 结构

通过 StructureCatalog、JigsawBuilder 和移动地块结构类型完成可定位、可生成的结构。

## 当前入口

- api/registry/catalog/StructureCatalog.java、api/registry/builder/JigsawBuilder.java
- api/world/structure/ 的 Jigsaw 定义、FixedOriginStructurePlacement、MobilePlotStructure 与 placement
- core/registry/ModStructure.java
- docs/mobile-plot-structure-replacement.md

## 修改流程

1. 普通独立结构用 JigsawBuilder 声明 pool、template、目标群系、terrain adaptation、深度、距离和 placement；同时提供 structure、structure_set、template_pool 与 NBT。
2. 城市建筑使用 embeddedBuilding(...)，基础设施使用 embeddedInfrastructure(...)；实际放置由 enableMobilePlots(...) 注册的 MobilePlotStructure 消费。建筑 footprint、四层道路、至少四条贯通楼梯、下三层基础设施和 TerraCityRegionBuilder 引用必须一致。
3. 当前 NBT 均位于 src/main/resources/data/zinecraft/structure/：商店、中型商店、移动地块下三层、楼梯和六种道路构件使用单个 <id>.nbt；大型建筑使用 <id>/{foundation,core,facade,roof,annex,surrounding}.nbt。
4. 优先修改现存脚本后生成：星门用 generate_stargate_structure.py，商铺和城市 matchbox 用 generate_nation_shop_blockouts.py 与 generate_city_building_matchboxes.py，移动地块层和道路用 generate_mobile_plot_power_layer.py。运行前确认脚本覆盖的精确目录；正式 NBT 已存在时不要运行临时 matchbox 生成器覆盖它。
5. 道路模板 ID 固定覆盖 isolated、end、straight、corner、tee、cross；旋转和选择由 RegionLayout.roadTile(...) 统一计算。楼梯模板在四层每个 stair_chunks 坐标各放置一段。
6. 多建筑城市布局、楼梯选址和道路图先使用 $zinecraft-cities；这里只负责结构注册、模板和放置消费者。

## 验证

仅在确需再生时运行脚本；这些脚本会直接覆盖 NBT，运行前核对精确目标与 git status，运行后审查 diff。再执行 ./gradlew.bat test、./gradlew.bat runData 和 ./gradlew.bat build。检查 pool、模板、NBT、structure set、目标群系、移动地块 footprint、四层道路接口、楼梯连续性与 JAR；普通独立结构用 /locate，模板用 /place structure，移动地块建筑在尚未生成的泰拉区块中验证完整落地。
