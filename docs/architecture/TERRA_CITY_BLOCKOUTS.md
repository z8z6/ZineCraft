# 泰拉城市与地区占位结构（已移除）

状态：`RETIRED`

旧实现曾为 `TerraGeography` 中的每个城市、聚落和地区批量注册
`blockout/terra_city/<nation>/<place_id>`、`city_asset/<place_id>/<role>` 与
`blockout/terra_region/<nation>/<place_id>` 灰盒结构。这会产生诸如 `city_13381j` 的散列占位 ID，
也会在 `ModCityStructure` 中重复描述地理目录，因此已停止注册。

现在 `TerraGeography` 只负责地点名称、国家归属和游戏化地图范围。真实建筑或地标由各自内容目录注册，
并通过 `JigsawBuilder.city(TerraPlace)` 显式保存所属城市或地区。没有资料与正式资产的地点只显示为
JourneyMap 地点，不自动生成通用城市或地区地标。

正式城市仍须遵守 [泰拉城市内部建筑生成逻辑](CITY_GENERATION_PIPELINE.md) 的证据、规划和验证门槛。
