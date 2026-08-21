---
name: zinecraft-cities
description: Design or revise Zinecraft cities and settlements using the city-planning API, nation building sets, road layouts, and generated structure templates. Use for multi-building urban layouts; use the structure skill for a single landmark or final worldgen registration.
---

# Zinecraft 城市与聚落

先区分地图地点、现有国家聚落和尚未打通的程序化城市链路，不能把纯数据规划 API 描述成已经可生成城市。

## 建立上下文

阅读 `AGENTS.md`、工作树和：

- `api/world/city/` 的 `CityDefinition`、`CityPlanner`、`CityLayout`、地块/建筑/道路/分区类型
- `core/worldgen/city/DefaultCityPlanner.java`、`GridCityLayout.java`、`CityBuildingSelector.java`、
  `CityPlanningSeeds.java`
- `script/generate_terra_city_blockouts.py`、`generate_nation_settlements.py`
- `script/nation_settlements/` 与 `src/main/resources/data/zinecraft/structure/nation_settlements/`
- `core/registry/ModStructure.java` 中已有十九国 settlement 声明

## 实现

1. 用官方/PRTS资料确定国家、地点、城市性质、代表建筑和视觉语言；不要凭空补写设定。项目坐标、半径和街区细节属于 Minecraft
   布局适配，不能称为官方坐标。
2. 仅新增地图地点时，在 `TerraGeography` 通过 `registerPlace(...)` 声明，并满足国家前缀、最小半径、锚点归属、同国名称唯一和
   `REGION_COUNTS` 等冻结校验。
3. 新增现有国家聚落时，在 `script/nation_settlements/<nation>.py` 维护建筑集合、尺寸、连接与材料；公共规则放 `common.py`
   。center、四种 street 和功能建筑必须形成可终止的 Jigsaw 池，最终通过 `$zinecraft-structures` 注册。
4. 真正的程序化城市仍是开发工作：当前仓库没有 `CityDefinition` 实例，也没有消费 `CityPlan` 放置 NBT 的 Structure
   Piece。实现时用纯二维/2.5D `TerrainModel`，不得加载远端区块；以 `CityPlanningSeeds` 保持确定性，并校验边界、保留区、道路端口、地块重叠和建筑候选。
5. `GridCityLayout` 的道路宽度要求正偶数，而 `CityPlan.RoadPath` 要求正奇数，两者语义不同。完成规划后必须新增真实放置消费者、结构注册、模板/资产和保存边界，不能只提交
   API 对象。
6. `script/generate_terra_city_blockouts.py` 当前引用不存在的 `ModCityStructure` 和旧 `TerraGeography` API，且不写
   manifest；修复前将其视为已知失败，不作为正常生成/验收命令。

## 验证

聚落任务先确认生成器将要重写的精确目录和其中的用户改动，再运行 `python script/generate_nation_settlements.py`；程序化城市需为
planner 与实际 Structure Piece 增加确定性测试。随后运行 `./gradlew.bat runData`、`./gradlew.bat test`、
`./gradlew.bat build`，并用多个固定种子检查道路连通、边界、建筑多样性、终止性和可达入口。
