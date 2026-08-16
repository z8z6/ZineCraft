# 十九国通用结构战利品表

状态：`IMPLEMENTED / COMMON FALLBACK`
更新：2026-08-16

## 使用约定

新版聚落与地标的通用容器可引用 `zinecraft:chests/nation/<country>_structure`
。这些表只表达在普通住宅、公共设施、仓储和维护空间中可能出现的日常物资，不代替危险品库、军械库、档案室、实验室等按房间划分的专用战利品表。

每表包含 3—5 次普通抽取，以及一次 `1%` 的非唯一已注册材料抽取。稀有池不含钥匙、测试物品、唯一剧情物或会暗示特定剧情归属的藏品。

## 国家清单

| 国家   | 战利品表                     | 有证据的注册食品                           | 结构通用物资方向            | 1% 非唯一材料                  |
|------|--------------------------|------------------------------------|---------------------|---------------------------|
| 阿戈尔  | `aegir_structure`        | 无；使用中性补给                           | 密封容器、铜铁、镜片/航行与维修物资  | `crystal_element`         |
| 玻利瓦尔 | `bolivar_structure`      | `bolivar_smoked_capsule`           | 食品、布料、绳索、纸张、修理物资    | `refined_solvent`         |
| 哥伦比亚 | `columbia_structure`     | `columbia_originium_roasted_fowl`  | 工业材料、红石、纸张、地图/调度物资  | `optimized_device`        |
| 杜林   | `durin_structure`        | 无；使用中性补给                           | 食品、园艺、玻璃、红石与维护材料    | `crystal_element`         |
| 东    | `higashi_structure`      | `higashi_nano_kappo`               | 食品、纸木、布线与日常工具       | `skill_summary_2`         |
| 伊比利亚 | `iberia_structure`       | `iberia_chitin_cluster`            | 海上食品、木材、金属、绳索与航行物资  | `manganese_trihydrate`    |
| 卡兹戴尔 | `kazdel_structure`       | 无；使用中性补给                           | 普通口粮、燃料、布皮与低阶维修零件   | `oriron_group`            |
| 卡西米尔 | `kazimierz_structure`    | `kazimierz_knight_supplement`      | 食品/补剂、皮革、铁、箭矢与纸张    | `manganese_trihydrate`    |
| 谢拉格  | `kjerag_structure`       | 无；使用中性补给                           | 旅行食品、防寒替代物、燃料、地图与工具 | `grindstone_pentahydrate` |
| 拉特兰  | `laterano_structure`     | `laterano_sacred_tone_soup`        | 甜食、食品、书纸与少量日常贵重物    | `skill_summary_2`         |
| 莱塔尼亚 | `leithanien_structure`   | `leithanien_musical_roast_extract` | 食品、书纸、线材、红石与晶体材料    | `crystal_group`           |
| 米诺斯  | `minos_structure`        | `minos_poetry_gel`                 | 食品、纸书、皮革、蜂蜜与建材      | `grindstone_pentahydrate` |
| 雷姆必拓 | `rim_billiton_structure` | 无；使用中性补给                           | 口粮、煤、矿物、铁轨与采掘工具     | `optimized_device`        |
| 萨米   | `sami_structure`         | 无；使用中性补给                           | 食品、皮革、绳索、箭矢、燃料与地图   | `grindstone_pentahydrate` |
| 萨尔贡  | `sargon_structure`       | `sargon_grass_cheese_gel`          | 食品、布皮、染料、容器与普通贸易品   | `polymer_agent`           |
| 叙拉古  | `siracusa_structure`     | 无；使用中性补给                           | 食品、书纸、皮革、铁与维护红石     | `refined_solvent`         |
| 乌萨斯  | `ursus_structure`        | `ursus_ham_supplement`             | 高热量食品、燃料、铁、皮革与箭矢    | `oriron_group`            |
| 维多利亚 | `victoria_structure`     | `victoria_central_valley_roast`    | 食品、煤铁、纸张与低阶维修装置     | `optimized_device`        |
| 炎    | `yan_structure`          | `yan_wasteland_meat_stir_fry`      | 食品、纸竹、铜铁与地图/旅行物资    | `crystal_group`           |

食品证据边界来自 [`docs/item/NATION_FOODS.md`](../item/NATION_FOODS.md)：只有标记为“PRTS 直接参考”的 12
项进入国家通用表。它们表示具有明确国家指向的食品条目，不表示官方唯一国菜。

## 明确排除

- 未加入 `aegir_fresh_shellcrab_sashimi`、`durin_honey_slugpudding`、`kazdel_cartilage_tack`、`rim_billiton_mining_ration`、
  `sami_instant_bone_soup`、`kjerag_valley_pie`、`siracusa_stew_gathering`：这 7 项在项目文档中标记为国家化/终末地概念改编，证据不足以作为国家特产自然掉落。
- 未加入 `example_item`、测试武器、测试钥匙、调试方块或协议物品。
- 未加入 `originite`、`protocol_originium`、虚构源石样本、海嗣样本、王室物件、宗教核心、赛事奖杯、家族证物等可能制造错误剧情断言的物品。
- 未加入 Curios 藏品。藏品虽已注册，但其原始叙事与普通国家建筑没有稳定的一一对应关系；如后续进入专用房间表，必须逐件记录来源和语境。
- 未引用不存在的票据、品牌纪念品、燃料、乐器、医疗或剧情物品 ID；相应功能仅用原版中性材料表达。

## 验证

- 19 个 JSON 均可解析，类型为 `minecraft:chest`。
- 普通池只使用 `minecraft:item`，稀有池只使用 `minecraft:item` 与 `minecraft:empty`。
- 24 个不同的 `zinecraft` 物品路径均可在 `src/main/java` 注册源码中找到；原版物品按约定豁免源码注册检查。
- 路径与国家设计包 ID 一致：`aegir`、`bolivar`、`columbia`、`durin`、`higashi`、`iberia`、`kazdel`、`kazimierz`、`kjerag`、
  `laterano`、`leithanien`、`minos`、`rim_billiton`、`sami`、`sargon`、`siracusa`、`ursus`、`victoria`、`yan`。
