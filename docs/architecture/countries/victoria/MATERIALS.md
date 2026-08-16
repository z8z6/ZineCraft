# Victoria Materials

状态：`RESEARCH / BLOCKOUT`

## 已发布稳定材料

| ID                                    | 当前用途 | 证据状态                              | 决策                    |
|---------------------------------------|------|-----------------------------------|-----------------------|
| `zinecraft:victoria_moorland_soil`    | 国家地貌 | INFERRED，设定集支持区域湿润/高地差异但不支持全国单一沼土 | 保留 ID；只在有地貌依据的群系使用    |
| `zinecraft:victoria_industrial_brick` | 主体外墙 | S/C，`21_G3` 有深红褐砖石街墙，但不是全部维多利亚建筑  | 保留 ID；限定城市街区，不用于炮座或乡村 |

## 城防炮 Architecture Pass 材料

| material_id                  | reference_id               | 画面特征            | 状态                 |
|------------------------------|----------------------------|-----------------|--------------------|
| `victoria_city_dark_masonry` | `bg_victoria_street_21g3`  | 深红褐、烟灰接缝、规整水平层带 | 待原创纹理；不从背景切片       |
| `victoria_civic_paving`      | `bg_victoria_street_21g3`  | 大块浅灰石铺地、低对比边缝   | 待设计                |
| `victoria_wall_armor`        | `bg_giantwall_27g4`        | 冷灰大块装甲、斜面、粗大分板  | 已实现；C 级形态参考，原创铆接纹理 |
| `victoria_cannon_casing`     | `bg_giantwall_27g4`        | 浅灰炮身分板、黄铜检修点    | 已实现；玩法转译           |
| `victoria_structural_frame`  | `bg_giantwall_inside_27g5` | 灰黑钢架、纵向肋条与锈蚀    | 已实现；C 级形态参考        |
| `victoria_reinforced_floor`  | `bg_giantwall_inside_27g5` | 深色维护走道、防滑凸纹     | 已实现；玩法转译           |
| `victoria_control_panel`     | `bg_giantwall_inside_27g5` | 煤黑仪表面、琥珀与冷青状态点  | 已实现；玩法转译，不含文字/徽标   |
| `victoria_village_limestone` | `bg_victoria_village_34g1` | 浅暖石墙、不均匀维护痕迹    | 待设计；仅地方乡村          |
| `victoria_dark_roof`         | `bg_victoria_village_34g1` | 低饱和深灰坡屋面        | 待设计                |

## Architecture Pass 规则

城墙、炮座、炮身、骨架、地面和操作台分别使用上述五种专用材质，不再使用混凝土占位。Create 6.0.10
的工业铁块、金属梁、传动轴、齿轮、流体管道与机械泵只用于后膛和维修区的机械细节；门窗、灯具、铁栏与容器可继续使用原版方块。

内部按火控室、源石军械储存区、维修间、乘员补给间和后膛检修舱分区。项目目前没有“源石炸弹”正式物品
ID，因此储存区只用隔爆墙、桶架与源石工业材料表达，不把 TNT 冒充设定物品。

## 权利说明

官方背景用于分析材料面积、明暗和分板尺度，不直接取样或像素化。任何新增 16×16 PNG 必须由
`script/generate_nation_block_textures.ps1` 的确定性规则原创生成并记录 `reference_id`。
