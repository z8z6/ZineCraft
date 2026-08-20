# 泰拉国家群系设计

本项目为 PRTS“泰拉大典”当前列出的 19 个国家各维护一个专属群系池。国家是固定地图区域，城市是国家内地点，群系则是国家内部
随气候变化的自然环境；三者是独立数据。生成器先确定国家，再只从该国群系池选择，绝不会在一个国家内生成另一国群系。

## 资料口径

- [《明日方舟》官网](https://ak.hypergryph.com/)用于确认泰拉、源石、天灾和移动城邦等官方世界观概念；官网的“WORLD /
  设定”页面是本设计的一级来源。
- [PRTS 泰拉大典地理一览](https://prts.wiki/w/泰拉大典:地理)
  用于确定当前国家清单和地名层级；完整整理见[城市与重要地区目录](../nation/TERRA_GEOGRAPHY.md)。
- PRTS 是玩家共同维护的二级来源；表中“群系设计”属于本项目根据资料做出的玩法转译，并非鹰角官方设定原文。
- 汐斯塔属于独立城邦，高卢等属于古国，因此没有计入本轮“现存国家”群系。

## 国家专属群系

每个群系 ID 和显示名均包含所属国家名称。第一项保留已有注册 ID 以兼容存档，第二项用于表现该国另一类气候地貌；两者共用该国专属
表层材料。下列自然环境是有资料依据的 Minecraft 玩法转译，不是新增的官方行政区或城市名。

| 国家   | 专属群系 ID                                                 | 独特地表      |
|------|---------------------------------------------------------|-----------|
| 阿戈尔  | `aegir_abyssal_sea`、`aegir_pelagic_depths`              | 深渊岩板      |
| 玻利瓦尔 | `bolivar_plain`、`bolivar_blackflow_forest`              | 战蚀土       |
| 东国   | `higashi_shadow_rift`、`higashi_mountain_forest`         | 裂谷暗壤      |
| 杜林   | `durin_underground_garden`、`durin_cavern_lake`          | 花园苔土      |
| 哥伦比亚 | `columbia_sandstone_wilds`、`columbia_eastern_prairie`   | 峡谷砂土      |
| 卡西米尔 | `kazimierz_knightland`、`kazimierz_forested_hills`       | 旱原草皮      |
| 卡兹戴尔 | `kazdel_scarred_wastes`、`kazdel_ruined_highlands`       | 战痕灰烬      |
| 拉特兰  | `laterano_holy_fields`、`laterano_highland_plateau`      | 冲积白垩      |
| 莱塔尼亚 | `leithanien_twilight_forest`、`leithanien_alpine_valley` | 暮林腐殖土     |
| 雷姆必拓 | `rim_billiton_mining_badlands`、`rim_billiton_arid_mesa` | 矿渣土       |
| 米诺斯  | `minos_sunlit_hills`、`minos_river_valley`               | 晒土地       |
| 萨尔贡  | `sargon_rocky_desert`、`sargon_tropical_rainforest`      | 岩漠硬壳/生态斑块 |
| 萨米   | `sami_frozen_forest`、`sami_glacial_mountains`           | 冻原苔土      |
| 维多利亚 | `victoria_misty_highlands`、`victoria_central_lowlands`  | 雾沼土       |
| 乌萨斯  | `ursus_frozen_steppe`、`ursus_eastern_highlands`         | 永冻土       |
| 谢拉格  | `kjerag_snowy_peaks`、`kjerag_alpine_forest`             | 圣雪岩       |
| 叙拉古  | `siracusa_rainy_woodland`、`siracusa_lowland_forest`     | 雨浸土       |
| 炎    | `yan_mountain_grove`、`yan_river_plains`                 | 山壤        |
| 伊比利亚 | `iberia_salt_delta`、`iberia_coastal_cliffs`             | 盐壳砾石      |

## 代码布局

- `ModBiome`：以 `BiomeBuilder` 同时声明群系气候点、颜色、刷怪规则和植被预设。
- `BiomeBuilder`：提供多噪声气候点、和平生物、地下生成及植被的 fluent API。
- `ModDimension`：保存十九个唯一国家锚点，以及国家到专属群系池的直接绑定。
- `TerraBiomeSource`：先按固定地图决定国家，再用六轴气候从该国群系池选择；拒绝跨国群系和非 `zinecraft` 群系。
- `NationBlocks`：为十九个国家各注册一种地貌方块和一种建筑外墙方块，并提供独立贴图。
- `ModSurfaceRule`：为十九个国家分配专属主表层，并按生态需要混入少量草方块斑块，不修改原版群系。
- `ModStructure`：为每个国家声明两座绑定群系的唯一建筑和一套可重复生成的大型 Jigsaw 城镇、村落或营地，并在注册时写入中文名。
- `ModBiome`：在每个国家群系声明普通友好生物的类别、权重和群体范围。
- `TerraNationRelations`：提供十九国状态以及完整的有向国家关系网。
- `generate_nation_landmarks.py`：确定性生成 38 个不同轮廓和材料的 structure NBT。
- `generate_nation_settlements.py`：确定性生成 19 套、共 171 个聚落 Jigsaw 模板。

阿戈尔使用深海气候，杜林使用地下环境，其余国家在各自固定范围内按气候生成。十九国共 38 个国家专属群系；另有外海、旧版河流
兼容项和天灾区。所有群系均由动态注册表数据生成器输出到 `data/zinecraft/worldgen/biome/`。

拉特兰国家锚点固定在泰拉 `(0, 0)`；其内部会在圣田与高原台地之间按气候选择。`laterano_dry_land` 保证中心设施附近为连续陆地。
该中心地下 `y=-32..-4` 生成唯一的
`laterano_host`：33×29×33 的银色机械山体、同心同步环和垂直 PCS 核心。它是根据 [PRTS 拉特兰资料](https://prts.wiki/w/拉特兰)
对大教堂地下、“银色山脉”和
人格与认知同步系统的资料所作的原创玩法化表达，并非官方场景模型复刻。

## 天灾区与源石晶簇

`terra_catastrophe_zone` 是泰拉外围多噪声分区中的天灾区群系，使用战痕灰烬地表，并额外高密度生成源石晶簇。晶簇复用项目已有
`originite_ore` 材质，通过 `OriginiumSpireFeature` 生成小、中、大三档随机晶簇；每组包含多根从地面斜向拔起、长度和倾角各异的
尖刺。三档晶簇也会低概率出现在泰拉其他群系，但地物会在放置时再次校验维度键，不会进入主世界或其他维度。

## 建筑唯一性

`StructureCatalog.uniqueLandmark` 使用原版 `ConcentricRingsStructurePlacement`，每个建筑拥有独立结构集，并固定
`count = 1`、`spread = 1`。结构和放置器同时绑定目标国家群系，因此每座建筑在一个世界中最多只有一个自然生成位置；它们仍可被
`/locate structure zinecraft:<id>` 定位。手动使用结构方块、命令或复制区块不属于“自然生成一次”的限制范围。

## 普通聚落

除唯一地标外，每个国家群系都有一套可以重复自然生成的聚落。每套聚落包含中心、直路、转角、十字路、道路末端和四类功能建筑，
通过 Jigsaw 随机展开为不同布局：

| 国家   | 聚落 ID                         | 代表功能建筑                |
|------|-------------------------------|-----------------------|
| 阿戈尔  | `aegir_subsea_enclave`        | 压力住宅、水培实验室、深潜码头、海流档案馆 |
| 玻利瓦尔 | `bolivar_dossoles_district`   | 运河住宅、海滩市场、赛事工坊、庆典厅    |
| 东    | `higashi_sokogawa_town`       | 町屋、锻刀铺、茶屋、奉行所         |
| 杜林   | `durin_ideal_city_block`      | 穹顶公寓、机械工坊、游戏厅、轨道站     |
| 哥伦比亚 | `columbia_frontier_town`      | 装配住宅、拓荒实验室、物流仓库、治安所   |
| 卡西米尔 | `kazimierz_knight_borough`    | 公寓、甲胄工坊、赞助商店、竞赛旅店     |
| 卡兹戴尔 | `kazdel_sarkaz_settlement`    | 帐屋、铸炉、佣兵会所、补给铺        |
| 拉特兰  | `laterano_monastery_town`     | 白石住宅、甜品店、公证所、钟楼礼拜堂    |
| 莱塔尼亚 | `leithanien_music_town`       | 暮色住宅、乐器工坊、排练厅、艺术学院    |
| 雷姆必拓 | `rim_billiton_mining_camp`    | 矿工宿舍、矿石工坊、货运站、食堂      |
| 米诺斯  | `minos_heroic_polis`          | 院落住宅、橄榄市场、训练厅、议事厅     |
| 萨尔贡  | `sargon_oasis_town`           | 土坯住宅、香料市场、商旅驿站、水井房    |
| 萨米   | `sami_snowpriest_village`     | 雪屋、猎人营地、仪式屋、补给棚       |
| 维多利亚 | `victoria_industrial_borough` | 砖砌公寓、蒸汽工坊、铁路仓库、市政厅    |
| 乌萨斯  | `ursus_northern_town`         | 保温住宅、军需仓库、矿务所、公共大厅    |
| 谢拉格  | `kjerag_mountain_village`     | 石木民居、茶坊、商队驿站、祭祀屋      |
| 叙拉古  | `siracusa_family_town`        | 家族住宅、餐馆、裁缝铺、议事厅       |
| 炎    | `yan_shangshu_town`           | 院落住宅、茶馆、百工坊、驿站        |
| 伊比利亚 | `iberia_coastal_town`         | 盐石住宅、造船坊、鱼市、审判庭办事处    |

聚落使用随机散布结构集，因此可以在新生成的对应群系区域重复出现；唯一性限制只适用于上一节的地标建筑。

十九国普通聚落使用统一的高密度安全配置：十八个外围国家聚落采用 `spacing=36`、`separation=16`，候选区密度约为旧
`52/24` 配置的 2.09 倍；阿戈尔和杜林也不再保留原先更稀疏的 `64/24`、`60/24` 特例。Jigsaw 展开深度由 `7`
提高到 `9`，使道路能够连接更多普通建筑；`maxDistanceFromCenter` 保持原版安全上限 `112`。线性随机散布的最近候选中心
仍相隔至少 `(16+1)×16=272` 格，大于两个完整 Jigsaw 半径的 `224` 格，避免相邻聚落理论包围范围重叠。拉特兰聚落继续固定在世界
原点，只应用更深的 Jigsaw 展开，不新增第二个拉特兰中心。

这些密度参数不用于地标。两座国家地标仍分别使用独立的同心环结构集、`count=1`、`spread=1`，维持每世界最多一次和
5000 格内保证生成的契约。
