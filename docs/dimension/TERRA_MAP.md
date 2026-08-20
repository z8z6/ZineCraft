# 泰拉固定国家地图

## 三层数据模型

1. **国家**：固定地图上的唯一连续区域，由国家锚点的最近点分区决定；一个国家只出现一次。
2. **城市与重要地区**：属于国家的地点，目录见[泰拉十九国城市与重要地区](../nation/TERRA_GEOGRAPHY.md)
   。没有可靠地图坐标的地点暂不生成，不能用群系代替城市。
3. **群系**：国家内部的自然环境。每个群系 ID 均以所属国家 ID 开头；生成器先确定国家，再只从该国群系池按温度、湿度、大陆性、侵蚀、深度和奇异度选择。

最外圈外海和天灾区是显式的非国家特殊区域，不用于表达第二个国家。

## 地图规则

- 泰拉维度边长为 `100000 × 100000` 格，中心为 `(0, 0)`，四边位于 `±50000`。
- 四边内侧各 `1000` 格固定为 `zinecraft:terra_outer_ocean`；国家陆地区域截止到 `±49000`。
- X 轴向东为正，Z 轴向南为正；拉特兰国家锚点是坐标原点。
- 每个国家只有一个锚点；最近锚点分区产生一个连续国家范围。
- 国家内部由原版六轴气候噪声选择该国专属群系，地形高度仍由 `minecraft:overworld` 噪声塑造。
- 河网作为地形雕刻生成，不再切换成通用 `terra_river`，因此河道仍保留其国家群系名称。
- 外海从浅滩向世界边界逐步加深，最大约低于海平面 `28` 格。

## 国家锚点与群系池

| 国家/特殊区域  |      X |      Z | 允许生成的群系                                                 |
|----------|-------:|-------:|---------------------------------------------------------|
| 玻利瓦尔     | -45000 | -18000 | `bolivar_plain`、`bolivar_blackflow_forest`              |
| 哥伦比亚     | -33000 | -19000 | `columbia_sandstone_wilds`、`columbia_eastern_prairie`   |
| 萨米       | -23000 | -40000 | `sami_frozen_forest`、`sami_glacial_mountains`           |
| 卡西米尔     | -18000 | -25000 | `kazimierz_knightland`、`kazimierz_forested_hills`       |
| 谢拉格      | -19000 | -10000 | `kjerag_snowy_peaks`、`kjerag_alpine_forest`             |
| 莱塔尼亚     |  -7000 | -10000 | `leithanien_twilight_forest`、`leithanien_alpine_valley` |
| 维多利亚     | -12000 |  -1000 | `victoria_misty_highlands`、`victoria_central_lowlands`  |
| 米诺斯      | -30000 |   3000 | `minos_sunlit_hills`、`minos_river_valley`               |
| 萨尔贡      | -39000 |  18000 | `sargon_rocky_desert`、`sargon_tropical_rainforest`      |
| 阿戈尔      | -10000 |  32000 | `aegir_abyssal_sea`、`aegir_pelagic_depths`              |
| 伊比利亚     |  -4000 |  16000 | `iberia_salt_delta`、`iberia_coastal_cliffs`             |
| 拉特兰      |      0 |      0 | `laterano_holy_fields`、`laterano_highland_plateau`      |
| 叙拉古      |   6000 |  -5000 | `siracusa_rainy_woodland`、`siracusa_lowland_forest`     |
| 雷姆必拓     |  16000 |      0 | `rim_billiton_mining_badlands`、`rim_billiton_arid_mesa` |
| 卡兹戴尔     |  14000 | -18000 | `kazdel_scarred_wastes`、`kazdel_ruined_highlands`       |
| 乌萨斯      |   9000 | -36000 | `ursus_frozen_steppe`、`ursus_eastern_highlands`         |
| 东国       |  26000 | -37000 | `higashi_shadow_rift`、`higashi_mountain_forest`         |
| 炎        |  26000 | -23000 | `yan_mountain_grove`、`yan_river_plains`                 |
| 杜林       |  34000 | -31000 | `durin_underground_garden`、`durin_cavern_lake`          |
| 天灾区（非国家） |  35000 |  14000 | `terra_catastrophe_zone`                                |

群系英文 ID 和中文显示名均包含国家名称。代码启动校验会拒绝缺少国家、国家重复、群系跨国归属，以及国家群系 ID 不以国家 ID
开头的配置。

## 验证命令

必须使用新世界或尚未生成的区块验证：

```mcfunction
/execute in zinecraft:terra run tp @s 0 120 0
/execute in zinecraft:terra run locate biome zinecraft:laterano_holy_fields
/execute in zinecraft:terra run locate biome zinecraft:laterano_highland_plateau
/execute in zinecraft:terra run locate biome zinecraft:sami_frozen_forest
/execute in zinecraft:terra run worldborder get
```
