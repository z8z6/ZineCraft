# 群系、维度与结构

群系在 `core/biome` 用 `BiomeBuilder` 声明，并交给 `BiomeCatalog`
注册。完整群系同时检查颜色/气候、生成步骤、生物、唯一多噪声气候点、目标群系限定表层、特色地物、聚落和地标。资料实现必须标记为玩法化表达。

维度使用 `DimensionBuilder` 声明群系气候点、维度类型、噪声设置与区块生成器，并交给 `DimensionCatalog` 注册。泰拉使用专用
`TerraBiomeSource`，不得包含原版群系。拉特兰中心固定在 `(0,0)` 区域，中心地表使用 fixed-origin 聚落，地下设施使用
fixed-origin landmark。维度 JSON 是发布资源，不依赖仅生成目录。

地物的 biome modifier 与 `Feature.place` 都要限制维度。传送门使用原版 `Portal`/`DimensionTransition`，由服务端处理跨维度、冷却和安全出口。

结构统一由 `JigsawBuilder` 声明。结构选择：小建筑 `simpleBuilding`；自定义池 `jigsawBuilding`；可重复城市/村落 `settlement`
；每世界一次建筑 `uniqueLandmark`
；固定中心使用对应 fixed-origin 变体。

NBT 位于 `src/main/resources/data/zinecraft/structure/`。父 Jigsaw 的 `target` 必须匹配子 `name`，连接方向相对。运行
`runData`、`build`，并在新世界使用 `/place structure`、`/locate structure` 和维度传送命令验证。
