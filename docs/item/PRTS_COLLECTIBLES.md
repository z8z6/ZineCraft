# 集成战略藏品素材来源

本模块收录 PRTS《傀影与猩红孤钻》“长生者宝盒”的全部藏品：

已适配的攻击、防御、生命和攻击速度藏品按 PRTS 数值原样进入统一战斗属性层：百分比藏品同类相加，攻击速度 `+N`
按点数而非百分比处理。攻击力会同时作用于近战、原生枪械、法术、治疗和 TaCZ 枪械。公式及后续适配规则见
[战斗数值机制](../combat/combat-stats.md)。

全部 245 件藏品均已建立能力声明，不再以 `ArchiveOnly` 作为默认值：

- 无条件的生命、攻击、防御、法抗、攻速和每秒回复按 PRTS 数值直接进入服务端运行时；职业限定效果在 Minecraft 中明确适配为“装备者”。
- 希望、源石锭、招募、部署、关卡生命、节点和指定首领等规则保存为 `SourceRule`
  ，逐字保留原始触发条件，供对应的集成战略子系统消费；在该子系统存在前不会伪装成幸运、经验或其他无关效果。
- 目录测试逐项读取 245 条 `originalEffectZhCn`，保证每条都能生成 `CombatStatBoost`、回复能力或 `SourceRule`，且不会回退到
  `ArchiveOnly`。

## L2 Library 页面

项目要求 L2 Library 3.0.8。玩家物品栏中的 L2 `Curios` 标签显示为“饰品”；藏品只要装备在任意 Curios 饰品槽中就会生效，不再限定为
`relic` 槽。L2 属性标签显示为“能力”。能力页右侧额外显示 Zinecraft 的生命、攻击、防御、法抗、攻击速度、攻击间隔倍率、已装备饰品数量，以及明日方舟属性、物理、法术和攻速公式。

页面只读取客户端已同步的实体属性和 Curios 内容，伤害、回复与藏品触发仍由服务器结算。

## 泰拉维度战利品

泰拉维度已生成的结构箱通过 `curios:relic` 物品标签抽取全部 245 件藏品。当前维多利亚防御炮的控制、维修、弹药、规划和补给箱均有
8% 的独立藏品掉落概率；房间原有的主题藏品池保留。后续泰拉结构箱应复用同一标签池。

No.001–238 与 PCS01–PCS07，共 245 件。中文名、编号、原效果和描述来自
明日方舟游戏数据，PNG 直接下载自 PRTS 图片资源域，未重绘、未生成或替换。

- PRTS 资料页：<https://prts.wiki/w/傀影与猩红孤钻/长生者宝盒>
- PRTS 图片资源域：<https://torappu.prts.wiki/assets/roguelike_topic_itempic/>
-
游戏数据镜像：<https://raw.githubusercontent.com/Kengxxiao/ArknightsGameData/master/zh_CN/gamedata/excel/roguelike_topic_table.json>
- 中文数据固定 SHA-256：`2d3a34926fc4c71c105e5d5eb2541b81ce52e832393b476761a5001604b1b1f4`
-
英文游戏数据镜像：<https://raw.githubusercontent.com/ArknightsAssets/ArknightsGamedata/master/en/gamedata/excel/roguelike_topic_table.json>
- 英文数据固定 SHA-256：`341d50068bc3301e0c14f33e9b23b0b88bea4ad7b82e1fd075de93778c1a4e22`
- PNG SHA-256 清单：`script/data/prts_is2_image_sha256.json`
- 导入脚本：`script/import_prts_is2_collectibles.py`

## 逐文件来源

| 本地文件                                                      | PRTS 原文件                 | 藏品                       |
|-----------------------------------------------------------|--------------------------|--------------------------|
| `collectible_hot_water_kettle.png`                        | `rogue_1_relic_r01.png`  | No.001 热水壶               |
| `collectible_special_suppressor.png`                      | `rogue_1_relic_r02.png`  | No.002 特殊抑制器             |
| `collectible_stone_gargoyle.png`                          | `rogue_1_relic_r03.png`  | No.003 石像鬼塑像             |
| `collectible_vampires_bed.png`                            | `rogue_1_relic_r04.png`  | No.004 血魔的寝床             |
| `collectible_proof_of_longevity.png`                      | `rogue_1_relic_r05.png`  | No.005 长生者之证             |
| `collectible_pried_open_toolbox.png`                      | `rogue_1_relic_r06.png`  | No.006 被撬开的道具箱           |
| `collectible_antiquated_sheet_music.png`                  | `rogue_1_relic_r07.png`  | No.007 古旧乐谱残章            |
| `collectible_letter_of_termination_contract.png`          | `rogue_1_relic_r08.png`  | No.008 解约协议              |
| `collectible_universal_key.png`                           | `rogue_1_relic_r09.png`  | No.009 万能钥匙              |
| `collectible_banshees_kiss.png`                           | `rogue_1_relic_r10.png`  | No.010 女妖之吻              |
| `collectible_antique_coins.png`                           | `rogue_1_relic_r11.png`  | No.011 古旧钱币              |
| `collectible_actors_jewelry_box.png`                      | `rogue_1_relic_r12.png`  | No.012 演员的首饰盒            |
| `collectible_flawless_jadestone.png`                      | `rogue_1_relic_r13.png`  | No.013 无瑕宝玉              |
| `collectible_laughing_joker.png`                          | `rogue_1_relic_r14.png`  | No.014 尖笑鬼牌              |
| `collectible_miss_christine_petting_ticket.png`           | `rogue_1_relic_r15.png`  | No.015 Miss.Christine摸摸券 |
| `collectible_antique_casting.png`                         | `rogue_1_relic_r16.png`  | No.016 古旧铸物              |
| `collectible_blunt_claws_training.png`                    | `rogue_1_relic_r17.png`  | No.017 钝爪-典训             |
| `collectible_bend_spears_training.png`                    | `rogue_1_relic_r18.png`  | No.018 折戟-典训             |
| `collectible_iron_guard_training.png`                     | `rogue_1_relic_r19.png`  | No.019 铁卫-典训             |
| `collectible_fatal_bolts_training.png`                    | `rogue_1_relic_r20.png`  | No.020 残弩-典训             |
| `collectible_broken_wand_training.png`                    | `rogue_1_relic_r21.png`  | No.021 断杖-典训             |
| `collectible_stalwart_aid_training.png`                   | `rogue_1_relic_r22.png`  | No.022 支柱-典训             |
| `collectible_healers_path_training.png`                   | `rogue_1_relic_r23.png`  | No.023 医者-典训             |
| `collectible_rusted_blade_training.png`                   | `rogue_1_relic_r24.png`  | No.024 锈刃-典训             |
| `collectible_regional_action_plan.png`                    | `rogue_1_relic_r25.png`  | No.025 地区行动方案            |
| `collectible_comprehensive_operation_file.png`            | `rogue_1_relic_r26.png`  | No.026 全局作战文件            |
| `collectible_secret_hr_letter.png`                        | `rogue_1_relic_r27.png`  | No.027 人事部密信             |
| `collectible_draft_of_a_speech.png`                       | `rogue_1_relic_r28.png`  | No.028 一份演讲稿             |
| `collectible_crimson_troupe_ticket_stub.png`              | `rogue_1_relic_r29.png`  | No.029 猩红剧团票根            |
| `collectible_lucky_coin.png`                              | `rogue_1_relic_r30.png`  | No.030 幸运硬币              |
| `collectible_masquerade_mask.png`                         | `rogue_1_relic_r31.png`  | No.031 假面舞会面具            |
| `collectible_supreme_ring.png`                            | `rogue_1_relic_r32.png`  | No.032 至宝指环              |
| `collectible_victorian_scrap_medal.png`                   | `rogue_1_relic_r33.png`  | No.033 维多利亚“废铁”勋章        |
| `collectible_leithanian_medal_of_honor.png`               | `rogue_1_relic_r34.png`  | No.034 莱塔尼亚荣誉勋章          |
| `collectible_rusted_iron_hammer.png`                      | `rogue_1_relic_r35.png`  | No.035 锈蚀的铁锤             |
| `collectible_four_leaf_clover_fossil.png`                 | `rogue_1_relic_r36.png`  | No.036 四叶草化石             |
| `collectible_assault_co_op_expansion.png`                 | `rogue_1_relic_r37.png`  | No.037 突击协议扩充            |
| `collectible_assault_co_op_reinforcements.png`            | `rogue_1_relic_r38.png`  | No.038 突击协议增援            |
| `collectible_fortification_co_op_expansion.png`           | `rogue_1_relic_r39.png`  | No.039 堡垒协议扩充            |
| `collectible_fortification_co_op_reinforcements.png`      | `rogue_1_relic_r40.png`  | No.040 堡垒协议增援            |
| `collectible_ranged_co_op_expansion.png`                  | `rogue_1_relic_r41.png`  | No.041 远程协议扩充            |
| `collectible_ranged_co_op_reinforcements.png`             | `rogue_1_relic_r42.png`  | No.042 远程协议增援            |
| `collectible_sabotage_co_op_expansion.png`                | `rogue_1_relic_r43.png`  | No.043 破坏协议扩充            |
| `collectible_sabotage_co_op_reinforcements.png`           | `rogue_1_relic_r44.png`  | No.044 破坏协议增援            |
| `collectible_silent_squad.png`                            | `rogue_1_relic_a01.png`  | No.045 “静音小队”            |
| `collectible_fissured_restraints.png`                     | `rogue_1_relic_a02.png`  | No.046 开裂的束缚带            |
| `collectible_abyssal_wyrdmask.png`                        | `rogue_1_relic_a03.png`  | No.047 奇渊面具              |
| `collectible_godmothers_token.png`                        | `rogue_1_relic_a04.png`  | No.048 教母的信物             |
| `collectible_worn_out_group_photo.png`                    | `rogue_1_relic_a05.png`  | No.049 残破合影              |
| `collectible_writers_tongue.png`                          | `rogue_1_relic_a06.png`  | No.050 作者的喉舌             |
| `collectible_rosmontiss_embrace.png`                      | `rogue_1_relic_a07.png`  | No.051 迷迭香之拥             |
| `collectible_the_whisperer_in_darknight.png`              | `rogue_1_relic_a09.png`  | No.052 “黑夜呢喃”            |
| `collectible_gold_plated_die.png`                         | `rogue_1_relic_a08.png`  | No.053 镶金骨骰              |
| `collectible_profound_silence.png`                        | `rogue_1_relic_a10.png`  | No.054 《大静谧》             |
| `collectible_oriron_round_shield.png`                     | `rogue_1_relic_a11.png`  | No.055 异铁小圆盾             |
| `collectible_military_mirror_armor.png`                   | `rogue_1_relic_a12.png`  | No.056 军团护心镜             |
| `collectible_old_steam_armor.png`                         | `rogue_1_relic_a13.png`  | No.057 古旧的蒸汽甲胄           |
| `collectible_emperors_favor.png`                          | `rogue_1_relic_a14.png`  | No.058 皇帝的恩宠             |
| `collectible_royal_rapier.png`                            | `rogue_1_relic_a15.png`  | No.059 贵族刺剑              |
| `collectible_vieux_vanguards_blade.png`                   | `rogue_1_relic_a16.png`  | No.060 老近卫军之锋            |
| `collectible_necklace_of_the_presence.png`                | `rogue_1_relic_a17.png`  | No.061 显圣吊坠              |
| `collectible_silver_forks.png`                            | `rogue_1_relic_a18.png`  | No.062 银餐叉               |
| `collectible_damaged_revolver_cylinder.png`               | `rogue_1_relic_a19.png`  | No.063 损坏的左轮弹巢           |
| `collectible_noxious_hemostatic_agent.png`                | `rogue_1_relic_a20.png`  | No.064 难闻的止血剂            |
| `collectible_first_aid_kit.png`                           | `rogue_1_relic_a21.png`  | No.065 急救药箱              |
| `collectible_unknown_instrument.png`                      | `rogue_1_relic_a22.png`  | No.066 未知仪器              |
| `collectible_rusted_razor.png`                            | `rogue_1_relic_a23.png`  | No.067 锈蚀刀片              |
| `collectible_carriage_drivers_whip.png`                   | `rogue_1_relic_a24.png`  | No.068 赶车夫的长鞭            |
| `collectible_avenger.png`                                 | `rogue_1_relic_a25.png`  | No.069 “复仇者”             |
| `collectible_standard_anti_riot_instrument.png`           | `rogue_1_relic_a26.png`  | No.070 制式防暴用具            |
| `collectible_emperors_collection.png`                     | `rogue_1_relic_a27.png`  | No.071 皇帝的收藏             |
| `collectible_brilliant_lament.png`                        | `rogue_1_relic_a28.png`  | No.072 “璀璨悲泣”            |
| `collectible_live_rose.png`                               | `rogue_1_relic_a29.png`  | No.073 活玫瑰               |
| `collectible_white_flower_crown.png`                      | `rogue_1_relic_a30.png`  | No.074 苍白花冠              |
| `collectible_actors_perfume.png`                          | `rogue_1_relic_a31.png`  | No.075 演出用香水             |
| `collectible_designers_ruler.png`                         | `rogue_1_relic_a32.png`  | No.076 设计师量尺             |
| `collectible_arts_killer.png`                             | `rogue_1_relic_a33.png`  | No.077 “法术杀手”            |
| `collectible_dancers_bracelets.png`                       | `rogue_1_relic_a34.png`  | No.078 舞者手链              |
| `collectible_ursus_big_bread.png`                         | `rogue_1_relic_a35.png`  | No.079 乌萨斯列巴             |
| `collectible_crucible_cream_puff.png`                     | `rogue_1_relic_a36.png`  | No.080 苦行者泡芙             |
| `collectible_iron_baguette.png`                           | `rogue_1_relic_a37.png`  | No.081 铁棍面包              |
| `collectible_test_run_chocolate.png`                      | `rogue_1_relic_a38.png`  | No.082 试制巧克力             |
| `collectible_gaulish_macarons.png`                        | `rogue_1_relic_a39.png`  | No.083 高卢小圆饼             |
| `collectible_victorian_cake.png`                          | `rogue_1_relic_a40.png`  | No.084 维多利亚蛋糕            |
| `collectible_route_diagram.png`                           | `rogue_1_relic_a41.png`  | No.085 路线说明图             |
| `collectible_dim_lantern.png`                             | `rogue_1_relic_a42.png`  | No.086 昏暗的提灯             |
| `collectible_brass_compass.png`                           | `rogue_1_relic_a43.png`  | No.087 黄铜指南针             |
| `collectible_broke_mask.png`                              | `rogue_1_relic_a44.png`  | No.088 破损的面具             |
| `collectible_blank_business_card.png`                     | `rogue_1_relic_a45.png`  | No.089 空白名片              |
| `collectible_doll_house.png`                              | `rogue_1_relic_a46.png`  | No.090 人偶之家              |
| `collectible_miniature_stage_model.png`                   | `rogue_1_relic_a47.png`  | No.091 微缩舞台模型            |
| `collectible_dreambind_castle_model.png`                  | `rogue_1_relic_a48.png`  | No.092 缠梦古堡模型            |
| `collectible_blunt_claws_advancement.png`                 | `rogue_1_relic_p01.png`  | No.093 钝爪-突破             |
| `collectible_blunt_claws_burst.png`                       | `rogue_1_relic_p02.png`  | No.094 钝爪-爆发             |
| `collectible_blunt_claws_proficiency.png`                 | `rogue_1_relic_p03.png`  | No.095 钝爪-熟稔             |
| `collectible_blunt_claws_inspiration.png`                 | `rogue_1_relic_p04.png`  | No.096 钝爪-振奋             |
| `collectible_blunt_claws_mastery.png`                     | `rogue_1_relic_p05.png`  | No.097 钝爪-百战             |
| `collectible_bend_spears_advancement.png`                 | `rogue_1_relic_p06.png`  | No.098 折戟-突破             |
| `collectible_bend_spears_acuity.png`                      | `rogue_1_relic_p07.png`  | No.099 折戟-锋刃             |
| `collectible_bend_spears_bloodbath.png`                   | `rogue_1_relic_p08.png`  | No.100 折戟-浴血             |
| `collectible_bend_spears_army_of_one.png`                 | `rogue_1_relic_p09.png`  | No.101 折戟-一夫当关           |
| `collectible_bend_spears_deathmatch.png`                  | `rogue_1_relic_p10.png`  | No.102 折戟-破釜沉舟           |
| `collectible_iron_guard_advancement.png`                  | `rogue_1_relic_p11.png`  | No.103 铁卫-突破             |
| `collectible_iron_guard_invasion.png`                     | `rogue_1_relic_p12.png`  | No.104 铁卫-侵掠             |
| `collectible_iron_guard_tranquility.png`                  | `rogue_1_relic_p13.png`  | No.105 铁卫-不动             |
| `collectible_iron_guard_advance.png`                      | `rogue_1_relic_p14.png`  | No.106 铁卫-推进             |
| `collectible_iron_guard_impenetrable.png`                 | `rogue_1_relic_p15.png`  | No.107 铁卫-无锋             |
| `collectible_fatal_bolts_advancement.png`                 | `rogue_1_relic_p16.png`  | No.108 残弩-突破             |
| `collectible_fatal_bolts_precision.png`                   | `rogue_1_relic_p17.png`  | No.109 残弩-百步穿杨           |
| `collectible_fatal_bolts_synergy.png`                     | `rogue_1_relic_p18.png`  | No.110 残弩-战场依存           |
| `collectible_fatal_bolts_crossfire.png`                   | `rogue_1_relic_p19.png`  | No.111 残弩-交叉火力           |
| `collectible_fatal_bolts_divine_speed.png`                | `rogue_1_relic_p20.png`  | No.112 残弩-神速             |
| `collectible_broken_wand_advancement.png`                 | `rogue_1_relic_p21.png`  | No.113 断杖-突破             |
| `collectible_broken_wand_arts_weaving.png`                | `rogue_1_relic_p22.png`  | No.114 断杖-织法者            |
| `collectible_broken_wand_chanting.png`                    | `rogue_1_relic_p23.png`  | No.115 断杖-咏唱             |
| `collectible_broken_wand_concentration.png`               | `rogue_1_relic_p24.png`  | No.116 断杖-凝神             |
| `collectible_broken_wand_malediction.png`                 | `rogue_1_relic_p25.png`  | No.117 断杖-苦难巫咒           |
| `collectible_stalwart_aid_advancement.png`                | `rogue_1_relic_p26.png`  | No.118 支柱-突破             |
| `collectible_stalwart_aid_secondary_front.png`            | `rogue_1_relic_p27.png`  | No.119 支柱-次要战场           |
| `collectible_stalwart_aid_diligence.png`                  | `rogue_1_relic_p28.png`  | No.120 支柱-勤奋             |
| `collectible_stalwart_aid_demoralize.png`                 | `rogue_1_relic_p29.png`  | No.121 支柱-破兵             |
| `collectible_stalwart_aid_counter_arts.png`               | `rogue_1_relic_p30.png`  | No.122 支柱-枯法             |
| `collectible_healers_path_advancement.png`                | `rogue_1_relic_p31.png`  | No.123 医者-突破             |
| `collectible_healers_path_self_treating.png`              | `rogue_1_relic_p32.png`  | No.124 医者-自医             |
| `collectible_healers_path_potency.png`                    | `rogue_1_relic_p33.png`  | No.125 医者-强效试剂           |
| `collectible_healers_path_keen_hands.png`                 | `rogue_1_relic_p34.png`  | No.126 医者-妙手             |
| `collectible_healers_path_restore_sanity.png`             | `rogue_1_relic_p35.png`  | No.127 医者-理智固剂           |
| `collectible_rusted_blade_advancement.png`                | `rogue_1_relic_p36.png`  | No.128 锈刃-突破             |
| `collectible_rusted_blade_execution.png`                  | `rogue_1_relic_p37.png`  | No.129 锈刃-处决             |
| `collectible_rusted_blade_isolation.png`                  | `rogue_1_relic_p38.png`  | No.130 锈刃-单兵             |
| `collectible_rusted_blade_no_mans_land.png`               | `rogue_1_relic_p39.png`  | No.131 锈刃-无人之境           |
| `collectible_rusted_blade_overwhelm.png`                  | `rogue_1_relic_p40.png`  | No.132 锈刃-神力             |
| `collectible_hand_of_spikes.png`                          | `rogue_1_relic_p41.png`  | No.133 尖刺之手              |
| `collectible_hand_of_choker.png`                          | `rogue_1_relic_p42.png`  | No.134 扼喉之手              |
| `collectible_hand_of_buckler.png`                         | `rogue_1_relic_p43.png`  | No.135 扣挠之手              |
| `collectible_hand_of_diffusion.png`                       | `rogue_1_relic_p44.png`  | No.136 扩散之手              |
| `collectible_hand_of_shredder.png`                        | `rogue_1_relic_p45.png`  | No.137 撕扯之手              |
| `collectible_hand_of_superspeed.png`                      | `rogue_1_relic_p46.png`  | No.138 极速之手              |
| `collectible_hand_of_snatcher.png`                        | `rogue_1_relic_p47.png`  | No.139 积攒之手              |
| `collectible_blue_silk_scarf.png`                         | `rogue_1_relic_q01.png`  | No.140 蓝色丝巾              |
| `collectible_red_bow_tie.png`                             | `rogue_1_relic_q02.png`  | No.141 红色蝴蝶结             |
| `collectible_weird_flute.png`                             | `rogue_1_relic_q03.png`  | No.142 古怪的长笛             |
| `collectible_glass_bird.png`                              | `rogue_1_relic_q04.png`  | No.143 玻璃小鸟              |
| `collectible_solo_music_box.png`                          | `rogue_1_relic_q05.png`  | No.144 独奏八音盒             |
| `collectible_originium_iris.png`                          | `rogue_1_relic_q06.png`  | No.145 源石鸢尾花             |
| `collectible_pure_gold_expedition.png`                    | `rogue_1_relic_q07.png`  | No.146 赤金的远征             |
| `collectible_durin_overground.png`                        | `rogue_1_relic_q08.png`  | No.147 《杜林地上环游记》         |
| `collectible_gaulish_toponym_origins.png`                 | `rogue_1_relic_q09.png`  | No.148 《旧高卢地名源流考》        |
| `collectible_ancient_gaulish_silver_coin.png`             | `rogue_1_relic_q10.png`  | No.149 古高卢银币             |
| `collectible_elysee_purse.png`                            | `rogue_1_relic_q11.png`  | No.150 爱丽舍钱袋             |
| `collectible_bank_of_gaul_check.png`                      | `rogue_1_relic_q12.png`  | No.151 高卢银行支票            |
| `collectible_second_economic_reform_act.png`              | `rogue_1_relic_q13.png`  | No.152 《第二经济改革法》         |
| `collectible_vanilla_soda.png`                            | `rogue_1_relic_q14.png`  | No.153 香草沙士汽水            |
| `collectible_ball_juice.png`                              | `rogue_1_relic_q15.png`  | No.154 球球果汁              |
| `collectible_fowlbeast_liver_pate.png`                    | `rogue_1_relic_q16.png`  | No.155 羽兽肝酱              |
| `collectible_dreaming_essence.png`                        | `rogue_1_relic_q17.png`  | No.156 迷梦香精              |
| `collectible_barrens_tequila.png`                         | `rogue_1_relic_q18.png`  | No.157 荒地龙舌兰             |
| `collectible_captain_morgans_wine.png`                    | `rogue_1_relic_q19.png`  | No.158 摩根队长佳酿            |
| `collectible_water_of_life.png`                           | `rogue_1_relic_q20.png`  | No.159 生命之水              |
| `collectible_royal_liqueur.png`                           | `rogue_1_relic_q21.png`  | No.160 皇家利口酒             |
| `collectible_unleashings.png`                             | `rogue_1_relic_q22.png`  | No.161 “绽放”              |
| `collectible_renowned_singer.png`                         | `rogue_1_relic_q23.png`  | No.162 “当红歌手”            |
| `collectible_string_puppet.png`                           | `rogue_1_relic_q24.png`  | No.163 悬丝傀儡              |
| `collectible_childrens_puppet.png`                        | `rogue_1_relic_q25.png`  | No.164 “童趣玩偶”            |
| `collectible_coin_operated_toy.png`                       | `rogue_1_relic_q26.png`  | No.165 投币玩具              |
| `collectible_chivalric_commandments_new_edition.png`      | `rogue_1_relic_q27.png`  | No.166 骑士戒律·新编           |
| `collectible_golden_chalice.png`                          | `rogue_1_relic_q28.png`  | No.167 金酒之杯              |
| `collectible_spinach_pack.png`                            | `rogue_1_relic_q29.png`  | No.168 绿叶菜罐头             |
| `collectible_no_073_safety_reagent.png`                   | `rogue_1_relic_q30.png`  | No.169 073号安全试剂          |
| `collectible_wrath_of_siracusans.png`                     | `rogue_1_relic_q31.png`  | No.170 叙拉古人的愤怒           |
| `collectible_civilight_eterna.png`                        | `rogue_1_relic_q32.png`  | No.171 “文明的存续”           |
| `collectible_ribbon_of_honor.png`                         | `rogue_1_relic_q33.png`  | No.172 荣耀绶带              |
| `collectible_loyality.png`                                | `rogue_1_relic_q34.png`  | No.173 “忠义”              |
| `collectible_end_of_times.png`                            | `rogue_1_relic_q35.png`  | No.174 “时光之末”            |
| `collectible_royal_alliance_treaty.png`                   | `rogue_1_relic_q36.png`  | No.175 王庭盟约              |
| `collectible_condensed_suppressant.png`                   | `rogue_1_relic_q37.png`  | No.176 浓缩抑制剂             |
| `collectible_ursus_chachek.png`                           | `rogue_1_relic_c01.png`  | No.177 乌萨斯弯刀             |
| `collectible_ursus_chachek_reforged.png`                  | `rogue_1_relic_c07.png`  | No.178 乌萨斯弯刀（重铸）         |
| `collectible_victoria_crown.png`                          | `rogue_1_relic_c02.png`  | No.179 维多利亚王冠            |
| `collectible_victoria_crown_reforged.png`                 | `rogue_1_relic_c08.png`  | No.180 维多利亚王冠（重铸）        |
| `collectible_leithanien_sceptre.png`                      | `rogue_1_relic_c03.png`  | No.181 莱塔尼亚权杖            |
| `collectible_leithanien_sceptre_reforged.png`             | `rogue_1_relic_c09.png`  | No.182 莱塔尼亚权杖（重铸）        |
| `collectible_gaul_mantle.png`                             | `rogue_1_relic_c04.png`  | No.183 高卢长袍              |
| `collectible_gaul_mantle_reforged.png`                    | `rogue_1_relic_c10.png`  | No.184 高卢长袍（重铸）          |
| `collectible_half_refined_diamond.png`                    | `rogue_1_relic_c05.png`  | No.185 半洗孤钻              |
| `collectible_half_refined_diamond_reforged.png`           | `rogue_1_relic_c11.png`  | No.186 半洗孤钻（重铸）          |
| `collectible_sigil_of_tragodia.png`                       | `rogue_1_relic_c06.png`  | No.187 酒神的印记             |
| `collectible_sigil_of_tragodia_reforged.png`              | `rogue_1_relic_c12.png`  | No.188 酒神的印记（重铸）         |
| `collectible_playwrights_manuscript_victoria.png`         | `rogue_1_relic_c13.png`  | No.189 剧作家手稿：维多利亚        |
| `collectible_playwrights_manuscript_ursus.png`            | `rogue_1_relic_c14.png`  | No.190 剧作家手稿：乌萨斯         |
| `collectible_playwrights_manuscript_leithanien.png`       | `rogue_1_relic_c15.png`  | No.191 剧作家手稿：莱塔尼亚        |
| `collectible_playwrights_manuscript_gaul.png`             | `rogue_1_relic_c16.png`  | No.192 剧作家手稿：高卢          |
| `collectible_right_eye_of_the_natator.png`                | `rogue_1_relic_m01.png`  | No.193 “游禽的右眼”           |
| `collectible_left_eye_of_the_natator.png`                 | `rogue_1_relic_m02.png`  | No.194 “游禽的左眼”           |
| `collectible_magnificent_visage.png`                      | `rogue_1_relic_m03.png`  | No.195 “华美容貌”            |
| `collectible_blademace.png`                               | `rogue_1_relic_m04.png`  | No.196 “剑锤”              |
| `collectible_brokenblade.png`                             | `rogue_1_relic_m05.png`  | No.197 “断剑”              |
| `collectible_familiar_sculpture.png`                      | `rogue_1_relic_m06.png`  | No.198 眼熟的雕像             |
| `collectible_proof_of_friendship.png`                     | `rogue_1_relic_m07.png`  | No.199 友谊之证              |
| `collectible_jet_black_dance_shoes.png`                   | `rogue_1_relic_m08.png`  | No.200 漆黑的舞鞋             |
| `collectible_pure_white_dance_shoes.png`                  | `rogue_1_relic_m09.png`  | No.201 洁白的舞鞋             |
| `collectible_bladedance.png`                              | `rogue_1_relic_m10.png`  | No.202 “刀舞”              |
| `collectible_whiteflower.png`                             | `rogue_1_relic_m11.png`  | No.203 “白英花”             |
| `collectible_shadow.png`                                  | `rogue_1_relic_m12.png`  | No.204 “影子”              |
| `collectible_flowers_of_quinde.png`                       | `rogue_1_relic_m13.png`  | No.205 《坎德之花》            |
| `collectible_numbness_and_obscenity.png`                  | `rogue_1_relic_m14.png`  | No.206 《麻木与庸俗》           |
| `collectible_beauty_and_ugliness_in_the_age_of_terra.png` | `rogue_1_relic_m15.png`  | No.207 《世间的美与丑》          |
| `collectible_worn_out_puppet.png`                         | `rogue_1_relic_m16.png`  | No.208 残破的玩偶             |
| `collectible_useless_scissors.png`                        | `rogue_1_relic_m17.png`  | No.209 无用的剪刀             |
| `collectible_blank_suicide_note.png`                      | `rogue_1_relic_m18.png`  | No.210 空白遗书              |
| `collectible_mantle_of_the_wrongly_condemned.png`         | `rogue_1_relic_m19.png`  | No.211 替罪领巾              |
| `collectible_stand_in_actor.png`                          | `rogue_1_relic_m20.png`  | No.212 替补演员              |
| `collectible_abrupt_realization.png`                      | `rogue_1_relic_m21.png`  | No.213 恍悟                |
| `collectible_dance_of_the_condemned.png`                  | `rogue_1_relic_n01.png`  | No.214 死囚之舞              |
| `collectible_act_1.png`                                   | `rogue_1_relic_n02.png`  | No.215 初幕                |
| `collectible_todays_menu.png`                             | `rogue_1_relic_n03.png`  | No.216 今日菜谱              |
| `collectible_intoxicated_hymnoi.png`                      | `rogue_1_relic_n04.png`  | No.217 “迷醉荷谟伊”           |
| `collectible_empresses_wish.png`                          | `rogue_1_relic_n05.png`  | No.218 女皇之愿              |
| `collectible_victory_horn.png`                            | `rogue_1_relic_n06.png`  | No.219 凯旋号角              |
| `collectible_flash_camera.png`                            | `rogue_1_relic_n07.png`  | No.220 高闪相机              |
| `collectible_therapy_tape.png`                            | `rogue_1_relic_n08.png`  | No.221 精神治疗录像带           |
| `collectible_royal_brooch.png`                            | `rogue_1_relic_n09.png`  | No.222 皇族金胸针             |
| `collectible_tear_of_the_departed.png`                    | `rogue_1_relic_n10.png`  | No.223 “逝者垂泪”            |
| `collectible_old_fan.png`                                 | `rogue_1_relic_n11.png`  | No.224 老蒲扇               |
| `collectible_nachzehrers_cane.png`                        | `rogue_1_relic_n12.png`  | No.225 食腐者手杖             |
| `collectible_angelinas_inspiration.png`                   | `rogue_1_relic_n13.png`  | No.226 安洁莉娜的创想           |
| `collectible_scouts_scope.png`                            | `rogue_1_relic_n14.png`  | No.227 Scout的狙击镜         |
| `collectible_silence.png`                                 | `rogue_1_relic_n15.png`  | No.228 “噤声”              |
| `collectible_focus.png`                                   | `rogue_1_relic_n16.png`  | No.229 “聚焦”              |
| `collectible_rhodes_island_tactical_transceiver.png`      | `rogue_1_relic_n17.png`  | No.230 罗德岛战术电台           |
| `collectible_assault_co_op_sharp_blade.png`               | `rogue_1_relic_n18.png`  | No.231 突击协议-利刃           |
| `collectible_assault_co_op_skirmish.png`                  | `rogue_1_relic_n19.png`  | No.232 突击协议-散兵           |
| `collectible_fortification_contract_phalanx.png`          | `rogue_1_relic_n20.png`  | No.233 堡垒协议-方阵           |
| `collectible_fortification_contract_resolution.png`       | `rogue_1_relic_n21.png`  | No.234 堡垒协议-固守           |
| `collectible_long_range_contract_remote_strike.png`       | `rogue_1_relic_n22.png`  | No.235 远程协议-遥击           |
| `collectible_long_range_contract_assassination.png`       | `rogue_1_relic_n23.png`  | No.236 远程协议-克敌           |
| `collectible_sabotage_co_op_elimination.png`              | `rogue_1_relic_n24.png`  | No.237 破坏协议-消除           |
| `collectible_sabotage_co_op_suppression.png`              | `rogue_1_relic_n25.png`  | No.238 破坏协议-压制           |
| `collectible_kings_new_lance.png`                         | `rogue_1_relic_sp01.png` | No.PCS01 国王的新枪           |
| `collectible_guard_cap.png`                               | `rogue_1_relic_sp02.png` | No.PCS02 近卫军帽            |
| `collectible_painful_happiness.png`                       | `rogue_1_relic_sp03.png` | No.PCS03 “苦痛的快乐”         |
| `collectible_castles_offspring.png`                       | `rogue_1_relic_sp04.png` | No.PCS04 古堡的子嗣           |
| `collectible_surging_feast.png`                           | `rogue_1_relic_sp05.png` | No.PCS05 涌动之餐            |
| `collectible_eventide_terror.png`                         | `rogue_1_relic_sp06.png` | No.PCS06 “夜骇”            |
| `collectible_excessiveness.png`                           | `rogue_1_relic_sp07.png` | No.PCS07 “无度”            |

脚本每次运行都会核对固定输入摘要、藏品总数、字段完整性、ID 唯一性、PNG 文件头和逐图 SHA-256。

## 权利说明

这些游戏图片与文本原文的权利属于上海鹰角网络科技有限公司及其关联公司；
PRTS 用于资料整理与展示。本项目根目录许可证不应被解释为对这些第三方素材重新授权。
Minecraft 适配效果与项目代码不属于 PRTS 原文。
