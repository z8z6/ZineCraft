# 十九国结构施工状态

更新日期：2026-08-16

本表区分“资源文件已存在”和“建筑已完成”。旧 NBT、能够生成、或通过旧版计数断言，均不等于完成。

## 状态定义

- `WIP-S`：聚落建筑正在逐栋施工；必须通过道路→Jigsaw→外门→内部门/用途分区、真实楼梯、入口可达有顶空间全照明、箱旁站位和战利品校验。
- `WIP-LR`：地标已有独立形体和六模块实现，正在按真实组装 AABB、112 格旋转边界、真实楼梯和全可达空间照明重新验收。
- `TODO-L`：地标已有独立设计骨架，但尚未完成上述严格可玩性施工，不得标为完成。
- `DONE`：生成器、严格结构校验、NBT 回读、runData、测试与构建全部通过；仍需另行记录游戏内 `/place` 和 `/locate` 视觉验收。

## 逐国建筑

| 国家   | 聚落建筑（全部 `WIP-S`）                                                            | 地标                                                   |
|------|-----------------------------------------------------------------------------|------------------------------------------------------|
| 阿戈尔  | `pressure_residence`、`hydroponics_lab`、`bathysphere_dock`、`current_archive` | `volcanic_beacon`、`abyssal_observatory`：`WIP-LR`     |
| 玻利瓦尔 | `canal_house`、`beach_market`、`race_workshop`、`festival_hall`                | `dossoles_yacht`、`race_checkpoint`：`WIP-LR`          |
| 东    | `machiya`、`swordsmith`、`tea_house`、`magistrate_house`                       | `rift_shrine`、`sokogawa_watchtower`：`TODO-L`         |
| 杜林   | `dome_apartment`、`machine_shop`、`arcade`、`transit_station`                  | `dome_station`、`water_park`：`TODO-L`                 |
| 哥伦比亚 | `prefab_house`、`pioneer_lab`、`logistics_depot`、`sheriff_office`             | `frontier_lab`、`prison_outpost`：`TODO-L`             |
| 卡西米尔 | `tenement`、`armor_workshop`、`sponsor_shop`、`tournament_inn`                 | `arena_gate`、`knight_monument`：`TODO-L`              |
| 卡兹戴尔 | `canvas_house`、`forge`、`mercenary_lodge`、`provision_store`                  | `babel_ruins`、`sarkaz_camp`：`TODO-L`                 |
| 拉特兰  | `white_residence`、`confectionery`、`notary_office`、`bell_chapel`             | `revelation_tower`、`ambrosius_chapel`：`TODO-L`       |
| 莱塔尼亚 | `twilight_house`、`instrument_workshop`、`rehearsal_hall`、`arts_academy`      | `twin_spires`、`concert_hall`：`TODO-L`                |
| 雷姆必拓 | `miner_bunkhouse`、`ore_workshop`、`freight_depot`、`canteen`                  | `mining_derrick`、`rail_depot`：`TODO-L`               |
| 米诺斯  | `courtyard_house`、`olive_market`、`training_hall`、`council_house`            | `heroes_temple`、`heroes_plaza`：`TODO-L`              |
| 萨尔贡  | `adobe_house`、`spice_market`、`caravanserai`、`well_house`                    | `golden_bazaar`、`long_spring_well`：`WIP-LR`          |
| 萨米   | `snow_lodge`、`hunter_camp`、`ritual_house`、`supply_shed`                     | `cyclops_altar`、`snowpriest_lodge`：`WIP-LR`          |
| 维多利亚 | `brick_tenement`、`steam_workshop`、`rail_warehouse`、`council_hall`           | `defence_cannon`、`steam_station`：`WIP-LR`            |
| 乌萨斯  | `heated_house`、`military_storehouse`、`mine_office`、`communal_hall`          | `sarcophagus_station`、`northern_mine_tower`：`WIP-LR` |
| 谢拉格  | `stone_chalet`、`tea_workshop`、`caravan_post`、`shrine_house`                 | `karlan_monastery`、`sacred_plaza`：`WIP-LR`           |
| 叙拉古  | `family_house`、`trattoria`、`tailor_shop`、`meeting_hall`                     | `family_court`、`family_theatre`：`WIP-LR`             |
| 炎    | `courtyard_residence`、`tea_house`、`artisan_workshop`、`relay_office`         | `yumen_beacon`、`shangshu_pavilion`：`WIP-LR`          |
| 伊比利亚 | `saltstone_house`、`shipwright`、`fish_market`、`inquisitor_office`            | `eye_lighthouse`、`saltwind_chapel`：`WIP-LR`          |

## 集成状态

| 项目            | 状态                     | 完成条件                                                                                  |
|---------------|------------------------|---------------------------------------------------------------------------------------|
| 19 国 CG 主体材质  | 已完成可追溯直裁；发布前权利复核       | 19 张逐像素源矩形一致、来源坐标与非无缝限制已记录                                                            |
| 聚落密度          | `DONE`（源码与生成 JSON 已同步） | 18 个随机聚落为 `spacing=36`、`separation=16`；19 个结构 `size=9`、`max_distance_from_center=112` |
| 171 个聚落 NBT   | 待严格施工完成后重生成            | 76 栋建筑全部通过 `WIP-S` 门槛                                                                 |
| 228 个地标模块 NBT | 待 38 座全部严格通过后重生成       | 每座六模块、实际 AABB/半径/门/梯/光/箱均通过                                                           |
| 游戏内预览         | 未完成                    | `/place structure`、`/locate structure`、四向旋转和战利品实测                                     |
| 项目构建          | `DONE`                 | `runData`、完整 `test`、`build` 均成功；正式 JAR 已生成                                            |
