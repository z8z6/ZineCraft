# 十九国建筑主体材质：游戏背景直裁来源

## 适用范围

本清单追踪 `script/generate_nation_cg_wall_textures.ps1` 生成的十九张 128×128
建筑主体材质。用户明确要求直接采用本地保存的《明日方舟》游戏背景/CG，故本批资源是官方画面的**裁切衍生物**，不是项目原创像素纹理。

生成过程仅执行固定坐标的 1:1 裁切与无透明 RGB PNG 编码：不缩小到 16×16、不减色、不锐化、不调色、不重绘、不添加文字/徽标，也不使用生成式
AI。裁切区域已经逐张同时检查原图和输出，避开天空、完整门窗、整栋轮廓、植被实体、角色、文字、招牌、徽标与明显 UI。受 128×128
固定尺寸和场景透视限制，少数裁片仍带有板缝、梁框、积雪或投影；这些限制在下表中明确记录。

## Source → asset 清单

共同源目录：`F:\netdisk\明日方舟\CG、背景\背景`。坐标原点为原图左上角；全部源图为 1024×576，裁片均为 128×128。

边缘连续度使用输出裁片相对边缘逐像素 RGB 平均绝对差（MAD，范围 0–255，越低越接近；`LR` 为左/右边，`TB`
为上/下边）。该数值只量化接缝色差，不是“无缝”认证，也不能检测透视方向或构件语义。

| 国家   | 目标材质 ID                         | 官方背景文件                               | Crop `x,y,w,h`    | 边缘 MAD `LR/TB`  | 画面语境与限制                                          |
|------|---------------------------------|--------------------------------------|-------------------|-----------------|--------------------------------------------------|
| 阿戈尔  | `aegir_pressure_tile`           | `51_g4_aegirstreet_1.png`            | `0,120,128,128`   | `14.80 / 6.23`  | 街道近景深青竖向结构板；有竖向明暗分带，是场景表面而非无缝耐压砖。                |
| 玻利瓦尔 | `bolivar_dossoles_stucco`       | `48_g7_galleriesstaircase.png`       | `0,250,128,128`   | `12.99 / 12.45` | 多索雷斯展馆楼梯间的近正视粗灰泥墙；只证明该公共建筑内墙。                    |
| 东    | `higashi_machiya_plaster`       | `64_g8_tessaihome.png`               | `575,75,128,128`  | `26.43 / 83.72` | 室内木框间的素灰抹墙；上下边含梁框，故纵向重复接缝明显，旧 ID 不能推广为全东町屋。      |
| 杜林   | `durin_ideal_city_panel`        | `30_g7_durinhall.png`                | `430,0,128,128`   | `39.03 / 74.02` | 理想城厅堂的蓝灰模块板与板缝；下缘转入压暗结构层，不是真正无缝。                 |
| 哥伦比亚 | `columbia_frontier_panel`       | `38_g2_colombiaoffice.png`           | `280,85,128,128`  | `14.34 / 19.27` | 科研办公空间的浅色水平墙板；不能推广为全部拓荒城镇。                       |
| 卡西米尔 | `kazimierz_arena_masonry`       | `bg_nearllivingroom.png`             | `290,80,128,128`  | `25.63 / 19.80` | 临光居室的浅灰压纹墙面；左缘残留栏构件，且旧“竞技场石砌”ID 仅为兼容名。           |
| 卡兹戴尔 | `kazdel_fortress_plate`         | `49_g1_kazdelroom.png`               | `450,448,128,128` | `22.48 / 32.91` | 战后室内近景拼接板材；板缝和材质切换可见，只支持临时/工业构件语境。               |
| 拉特兰  | `laterano_basilica_marble`      | `26_g1_laterano_cathedralfront.png`  | `640,190,128,128` | `42.36 / 33.47` | 圣堂侧翼大块浅色石面；含立面分带与采光渐变，只适用于宗教/公共建筑语境。             |
| 莱塔尼亚 | `leithanien_resonant_brick`     | `28_g6_whitehome.png`                | `350,80,128,128`  | `3.39 / 24.95`  | 白屋近正视浅色内墙饰面；“共振砖”是兼容玩法名，画面不证明声学砖属性。              |
| 雷姆必拓 | `rim_billiton_corrugated_steel` | `46_g1_transporter.png`              | `896,260,128,128` | `88.11 / 38.03` | 运输载具的锈蚀浅色护板；左缘深色框造成强横向接缝，旧 ID 不代表裁片真为波纹钢。        |
| 米诺斯  | `minos_heroic_masonry`          | `69_g12_generalroom.png`             | `0,60,128,128`    | `10.68 / 13.16` | 米诺斯室内的近正视浅色矿物抹墙；不推广为全国“英雄式”石砌。                   |
| 萨尔贡  | `sargon_oasis_adobe`            | `53_g1_menatmainstreet_d.png`        | `170,105,128,128` | `50.24 / 82.62` | 马纳特主街巨型暖色矿物墙面；日照和树影造成强渐变，裁片内没有植被实体但并非无缝土坯。       |
| 萨米   | `sami_tribal_timber`            | `40_g5_samitribe.png`                | `350,448,128,128` | `12.60 / 5.92`  | 部族聚落前景的连续木板面；仍保留原画板缝和透视，不覆盖 `sami_ritual_stone`。 |
| 维多利亚 | `victoria_industrial_brick`     | `37_g5_blockadewall.png`             | `0,200,128,128`   | `49.44 / 29.15` | 封锁区近景风化墙面；右缘构造柱造成接缝，旧“工业砖”ID 不等于画面中的标准红砖。        |
| 乌萨斯  | `ursus_imperial_masonry`        | `66_g12_deitygrypherburgmeeting.png` | `780,85,128,128`  | `18.16 / 62.97` | 帝国议事厅近正视浅色墙板和线脚；上下明暗/线脚使纵向重复明显。                  |
| 谢拉格  | `kjerag_monastery_stone`        | `45_g9_underkjerastastue.png`        | `390,210,128,128` | `40.15 / 17.29` | 谢拉格聚落台地的水平层石与积雪；可见雪带，不可宣称为蔓珠院无缝石墙。               |
| 叙拉古  | `siracusa_family_masonry`       | `33_g1_srcstreet.png`                | `0,320,128,128`   | `7.59 / 5.54`   | 雨中旧城近景暗色抹墙；不证明特定家族专属材质。                          |
| 炎    | `yan_courtyard_brick`           | `35_g3_yumenobservationtower_d.png`  | `150,335,128,128` | `31.53 / 62.58` | 玉门观景台近正视装饰墙板；上下边含完整的边框节奏，旧“院墙青砖”ID 仅为兼容名。        |
| 伊比利亚 | `iberia_coastal_masonry`        | `57_g13_ibtown_d.png`                | `0,210,128,128`   | `14.39 / 11.91` | 沿海城镇近景盐风侵蚀抹墙；仅适用于该场景。                            |

### 仍不可完全平铺的裁片

这 19 张图都是叙事场景中的表面采样，**没有一张可据此宣称为真正无缝纹理**。其中 `higashi_machiya_plaster`、
`durin_ideal_city_panel`、`rim_billiton_corrugated_steel`、`sargon_oasis_adobe`、`ursus_imperial_masonry`、
`yan_courtyard_brick` 的单轴边缘 MAD 超过 60，重复时最容易看到接缝；`kazimierz_arena_masonry`、`kazdel_fortress_plate`、
`laterano_basilica_marble`、`victoria_industrial_brick`、`kjerag_monastery_stone`
仍带构件边缘、板缝、投影或积雪。继续消除这些接缝将需要缩放、透视校正、拼接、修补或重绘，均会违反本批“固定 128×128、1:1 直接裁切”的约束。

## 权利与发布边界

- 原始画面及裁切衍生物的著作权归《明日方舟》相关权利方所有；本项目不能将本批文件表述为原创纹理或据此主张独立美术权利。
- 本地素材由用户指定用于同人模组实现。本清单只提供可审计的来源追踪，不构成任何再分发授权。
- 在公开发布 JAR、资源包或商店页面前，应由项目负责人确认官方同人内容规则与所需许可；不应把这些裁片进入通用可自由复用的素材包。
- 背景画面只证明具体场景可见的局部表面。Minecraft 六面重复贴图属于玩法转译，不能反向声称为官方设定的无缝材质。

## 复现与检查

```powershell
powershell -ExecutionPolicy Bypass -File script/generate_nation_cg_wall_textures.ps1
```

脚本会验证固定条目数、源图存在、裁切边界、128×128 尺寸、无 Alpha 通道、每个输出像素与源矩形 RGB 完全相等、十九张 SHA-256
全部唯一，并输出两轴边缘 MAD。原创地貌/专用材质生成器 `generate_nation_block_textures.ps1` 已显式跳过十八张既有 CG 外墙
ID，不会再覆盖它们；新增的 `sami_tribal_timber` 只由本裁切脚本生成。
