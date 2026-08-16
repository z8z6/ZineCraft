# Londinium Defence Cannon — Blockout Review 01

日期：2026-08-16  
状态：`REGISTERED_PREVIEW / NOT APPROVED FOR FINAL INTEGRATION`

## 输出

生成脚本：`script/generate_victoria_defence_cannon_blockout.py`

| 模块                 |       尺寸 | 结构条目数（含清场空气） |
|--------------------|---------:|-------------:|
| `wall_rear_left`   | 32×20×32 |       17,876 |
| `wall_rear_right`  | 32×20×32 |       17,875 |
| `wall_front_left`  | 32×20×32 |       18,004 |
| `wall_front_right` | 32×20×32 |       18,002 |
| `turret_core`      | 32×28×32 |       17,344 |
| `barrel_root`      | 32×16×16 |        3,745 |
| `barrel_muzzle`    | 32×12×12 |        1,952 |

Jigsaw 组合包围盒为 128×64×48，共 94,798 个结构条目。七个 NBT 使用唯一目标接口确定性拼接，并注册为
`zinecraft:victoria_defence_cannon_preview`；正式 `zinecraft:victoria_defence_cannon` 保持不变。

## Architecture Pass 02

2026-08-16 已加入五种原创维多利亚城防材质，完全替换炮体混凝土占位；内部由显式空气体积形成可通行空间，并划分为火控室、源石军械储存区、维修间、乘员补给间和后膛检修舱。Create
6.0.10 的传动轴、大小齿轮、金属梁、工业铁块、流体管道和机械泵用于机械细节。

四类宝箱分别绑定 `control`、`ordnance`、`maintenance`、`supply`
战利品表。补给箱含维多利亚中央谷地烤肉；其余按房间投放源石工业材料、机械零件和低概率军械藏品。由于项目尚无正式源石炸弹物品，不在容器中伪造同名物品。

## Reference vs Minecraft

| 检查项       | 结果              | 说明                           |
|-----------|-----------------|------------------------------|
| 超长炮管      | PASS            | 由两个独立模块形成 64 格炮管，明显超出炮座      |
| 低宽斜面炮座    | PASS WITH LIMIT | 以五级退台表达大斜面；当前阶梯仍偏粗，需要游戏内远景检查 |
| 连续高墙基线    | PASS            | 四个 32 格模块形成 64×64、20 格高墙段    |
| 重型后膛/机匣   | PASS            | 炮座与 `barrel_root` 重叠形成粗重后部体量 |
| 墙内检修/弹药空间 | PASS WITH LIMIT | 已有五类功能空间、照明和垂直交通；待游戏内核对门洞与动线 |
| 重复炮座与城市背景 | MISSING         | 只有接口和空间预留，尚未制作相邻组团           |
| 官方精确比例    | UNKNOWN         | 单张透视画面不足，当前数值是 C 级保守转译       |

## 距离层级预审

- **500/300 格**：可读为“高墙上的超长炮”，主轮廓成立。
- **150 格**：炮座退台、后膛与墙顶平台可分辨。
- **50 格**：模块切缝、工作平台和专用材质开始可见。
- **10 格**：可评审火控台、机械传动、弹药架、维修台、照明与宝箱分布。

## 问题

1. 炮管当前完全水平，官方画面存在轻微透视/仰角差异；需要游戏内相机对照后决定是否做 1:8 或 1:16 抬升。
2. 炮座五级退台是体素近似，不足以证明官方装甲分板。
3. 内部已进入 Architecture Pass，但仍需在游戏内确认跨 Jigsaw 接缝的动线是否连贯。
4. 缺少第二炮座、后方城市体块和墙外空间，Environment 项暂为 0。
5. 画面国别归属仍是 C 级；在找到该背景对应剧情/关卡索引前不能升级为 Canonical Reconstruction。

## 初步评分

Blockout 阶段只对可评审项计分：

| 项目              | 权重 |    得分 | 说明                |
|-----------------|---:|------:|-------------------|
| Silhouette      | 30 |    23 | 三个主要轮廓锚点成立，缺重复组团  |
| Proportion      | 25 |    16 | 大比例关系成立，精确比例未知    |
| Material        | 15 | 待实机评分 | 已替换为五种原创专用材质      |
| Color           | 10 | 待实机评分 | 冷灰、煤黑、黄铜点与冷青仪表已落地 |
| Landmark Detail | 10 | 待实机评分 | 已加入后膛、机械传动和五类功能空间 |
| Environment     | 10 |     0 | 尚未制作              |

当前不能计算正式 Fidelity Score，也不能替换 `victoria_defence_cannon`。预览注册只用于游戏内评审。

## 下一门槛

下一轮游戏内评审需要：

- 在新世界执行 `/place structure zinecraft:victoria_defence_cannon_preview` 并从五档距离截图；
- 核对 `27_g4/27_g5` 的剧情地点归属；
- 决定炮管仰角和炮座高宽比；
- 至少制作一个重复墙段和一个后方城市体块 Blockout，完成 Environment 评审。
