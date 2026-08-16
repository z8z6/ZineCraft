# 现有国家结构审计

状态：`LEGACY AUDIT / SUPERSEDED BY ARCHITECTURE PASS`
审计日期：2026-08-16

## 结论

仓库已有 19 套聚落、171 个聚落 NBT（每国中心、四条道路、四栋功能建筑）和 38 座唯一地标。但它们是在 Source of Truth、Visual
Bible 和逐建筑证据表建立前生成的，只能作为注册、Jigsaw 与国家材质接入原型。

上述结论描述的是替换前基线。2026-08-16 后续实施已保留公开 ID 并替换全部旧 NBT：聚落为 171 个新版模块，38 座地标为 228 个多模块
NBT。新版状态为 `ARCHITECTURE_PASS / IN-GAME REVIEW REQUIRED`，不再是本节审计的统一盒体或单模板地标，但仍不能标为 Canonical
Final。

## 关键问题

| 优先级 | 问题      | 证据                                               | 处理                                             |
|-----|---------|--------------------------------------------------|------------------------------------------------|
| P0  | 尺度不足    | 地标最大轴仅 9—17 格，高 8—16 格；国家代表建筑规范要求 64+            | Pilot 先重做 L 级 Blockout                         |
| P0  | 流程倒置    | 缺少逐国 Source of Truth/Visual Bible，却已生成 209 个 NBT | 冻结旧原型，先补资料链                                    |
| P0  | 无逐资产来源  | 名称、功能、轮廓和后三槽调色板没有 `source -> asset` 记录           | 未核实项标 U/ORIGINAL                               |
| P1  | 十九国同质化  | 76 栋功能建筑全部调用同一个 `building()` 盒体算法                | 每国建立独立模块语法                                     |
| P1  | 地标不可扩展  | 38 地标均为单模板、单池                                    | 重做为 foundation/core/facade/roof/surrounding 模块 |
| P1  | 城市语境缺失  | 没有 plaza、road、adjacent block、skyline 或移动城市基座     | 地标与环境一起设计                                      |
| P2  | 调色板部分合规 | 前两槽已使用国家地貌和外墙方块                                  | 保留稳定 ID，重新审查其余材料                               |

## 聚落生成器

`script/generate_nation_settlements.py` 为每国生成相同规格：

- 中心：15×5×15；
- 道路：9×3×9；
- 四栋建筑仅有 9×7×11、11×8×9、9×9×11、11×10×9 四种盒体；
- 变化只来自名称和 `foundation/wall/roof/accent/road` 五槽调色板；
- Jigsaw 深度为 7、最大中心距离为 112，但展开范围不等于单栋建筑尺度。

因此“实验室、码头、歌剧院、神殿、仓库”等目前只是名称差异，不是功能或轮廓差异。

## 唯一地标尺寸

| 国家   | 现有地标                                          | NBT 尺寸              |
|------|-----------------------------------------------|---------------------|
| 阿戈尔  | `volcanic_beacon` / `abyssal_observatory`     | 11×14×11 / 13×9×13  |
| 玻利瓦尔 | `dossoles_yacht` / `race_checkpoint`          | 15×9×7 / 15×8×7     |
| 东    | `rift_shrine` / `sokogawa_watchtower`         | 11×10×9 / 9×14×9    |
| 杜林   | `dome_station` / `water_park`                 | 13×9×9 / 15×8×13    |
| 哥伦比亚 | `frontier_lab` / `prison_outpost`             | 13×9×11 / 13×10×13  |
| 卡西米尔 | `arena_gate` / `knight_monument`              | 15×11×7 / 11×13×11  |
| 卡兹戴尔 | `babel_ruins` / `sarkaz_camp`                 | 11×14×11 / 15×8×13  |
| 拉特兰  | `revelation_tower` / `ambrosius_chapel`       | 9×15×9 / 13×12×11   |
| 莱塔尼亚 | `twin_spires` / `concert_hall`                | 13×15×9 / 15×11×13  |
| 雷姆必拓 | `mining_derrick` / `rail_depot`               | 13×13×11 / 17×8×9   |
| 米诺斯  | `heroes_temple` / `heroes_plaza`              | 15×10×11 / 15×8×15  |
| 萨尔贡  | `golden_bazaar` / `long_spring_well`          | 15×10×13 / 11×9×11  |
| 萨米   | `cyclops_altar` / `snowpriest_lodge`          | 11×11×11 / 13×10×11 |
| 维多利亚 | `defence_cannon` / `steam_station`            | 15×10×9 / 17×10×11  |
| 乌萨斯  | `sarcophagus_station` / `northern_mine_tower` | 13×9×11 / 11×14×11  |
| 谢拉格  | `karlan_monastery` / `sacred_plaza`           | 15×11×13 / 15×9×15  |
| 叙拉古  | `family_court` / `family_theatre`             | 15×11×11 / 15×12×13 |
| 炎    | `yumen_beacon` / `shangshu_pavilion`          | 11×15×11 / 13×11×13 |
| 伊比利亚 | `eye_lighthouse` / `saltwind_chapel`          | 11×16×11 / 13×13×11 |

## 高风险快捷映射

以下不是断言单项一定错误，而是表示在补齐官方/PRTS逐项证据前不能继续深化：

- 东的町屋、锻刀铺、茶屋、神社组合；
- 米诺斯的城邦、橄榄市场、神殿组合；
- 萨尔贡的绿洲、土坯、香料、驿站组合，且把多样国土压成单一荒漠；
- 维多利亚的红砖加蒸汽组合；
- 哥伦比亚的拓荒镇、治安官、监狱组合；
- 炎的院落、茶馆、亭台组合；
- 叙拉古的餐馆、家族剧院组合；
- 拉特兰的修道院、圣堂、礼拜堂组合。

这些元素只有在对应官方画面/文字中出现时才能成为 CANON；现实文化相似性本身不是依据。

## 兼容策略

- 暂不删除旧 NBT、注册 ID 或已发布国家方块。
- 新 Blockout 使用独立目录，不覆盖自然生成模板。
- Pilot 资产通过评审后，用旧 ID 指向新模块化结构；必要时保留旧技术别名。
- 其余国家按路线图分批替换，不做一次性机械放大。

## 重设计合同

十九国现有 76 栋功能建筑与 38 座地标均已建立逐项重设计合同，入口见 `docs/architecture/ALL_NATIONS_REDESIGN.md`。合同明确旧
ID 的保留、改名、替换或冻结策略，并给出目标尺度、轮廓、模块、房间、机械与战利品需求。

用户批准后已按这些合同实施兼容替换。公开结构 ID、聚落路径和 `/locate` 键保持不变；未批准的仍是 Canonical/Final
状态，而不是资源接入本身。实现见 `NATION_SETTLEMENT_IMPLEMENTATION.md`、`NATION_LANDMARK_IMPLEMENTATION.md` 与
`NATION_STRUCTURE_LOOT.md`。

## 关键实现

- `script/generate_nation_landmarks.py`
- `script/generate_nation_settlements.py`
- `src/main/java/com/cxxcxx/zinecraft/core/structure/NationLandmarks.java`
- `src/main/java/com/cxxcxx/zinecraft/core/structure/NationSettlements.java`
- `src/main/java/com/cxxcxx/zinecraft/api/world/structure/StructureCatalog.java`
- `src/main/resources/data/zinecraft/structure/nation_landmarks/`
- `src/main/resources/data/zinecraft/structure/nation_settlements/`
