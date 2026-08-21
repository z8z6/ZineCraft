---
name: zinecraft-structures
description: Add or revise generated Zinecraft structures, Jigsaw pools, placements, fixed landmarks, and structure NBT. Use for worldgen structures or unique landmarks; use the city skill for settlement planning and building-set design.
---

# Zinecraft 结构

通过项目 `StructureCatalog`、Jigsaw API 和结构 NBT 完成可定位、可生成的世界结构。

## 建立上下文

阅读 `AGENTS.md`、工作树和以下当前实现：

- `api/registry/catalog/StructureCatalog.java`、`api/registry/builder/JigsawBuilder.java`
- `api/world/structure/` 下的 pool、template、固定坐标与环形边界类型
- `core/registry/ModStructure.java`、`core/structure/LateranoHostStructure.java`
- `script/generate_jigsaw_example.py`、`generate_stargate_structure.py`、`generate_nation_landmarks.py`
- `src/main/resources/data/zinecraft/structure/` 与 `src/generated/resources/data/zinecraft/worldgen/`

## 实现

1. 从官方/PRTS核实地点、建筑用途、命名和外观；优先使用项目已有方块与原始素材。无来源设计必须标为 Minecraft 适配，不冒充原设。
2. 根据需求选择模式：普通/聚落 Jigsaw 使用 `JigsawBuilder`；唯一坐标建筑参考 `LateranoHostStructure` 和
   `FixedOriginStructurePlacement`；星门等专用逻辑参考 `core/structure/stargate/`。
3. 在 `ModStructure` 声明结构键、目标群系、地形适配、Jigsaw 深度、最大距离和 placement。同步维护 template
   pool、structure、structure_set 与双语名称；不要只提交 NBT。
4. NBT 放在 `src/main/resources/data/zinecraft/structure/<id>/`。优先修改可复现的生成脚本再生成 NBT；连接块的
   name/target/pool、朝向、边界和投影必须与 pool 定义一致。
5. `generate_nation_settlements.py` 与 `generate_nation_landmarks.py`
   会清空并重写各自整个输出目录。运行前解析并确认精确目标目录，检查其中用户改动；不要对不明确或有未保留改动的目录执行重写。
6. 若结构对应泰拉地标，更新 `TerraGeography` 地点关联及固定/唯一约束。聚落或建筑集合的规划先使用 `$zinecraft-cities`，再由本
   skill 接入 worldgen。

## 验证

先运行相关结构脚本的校验/生成模式，再运行 `./gradlew.bat runData`、`./gradlew.bat test`、`./gradlew.bat build`。检查所有
pool 引用和 NBT 均存在、structure set 参数合法、目标群系正确、JAR 包含手写 NBT；用固定种子和 `/place structure`
验证拼接、地形适配、边界与唯一性。
