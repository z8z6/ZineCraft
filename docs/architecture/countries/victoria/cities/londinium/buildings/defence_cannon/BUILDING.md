# Londinium Outer Defence Cannon — Blockout Brief

## Identity

Country: Victoria  
City: Londinium  
Building: Outer city wall defence cannon and one wall segment  
Canonical Status: `REGISTERED_PREVIEW / BLOCKOUT / SILHOUETTE STUDY`  
Replacement Target: `zinecraft:victoria_defence_cannon`（尚未替换）
Preview Structure: `zinecraft:victoria_defence_cannon_preview`

## Sources

[OFFICIAL]

- 《大地巡旅》PDF 50—58：移动城三层、地块、动力/支持系统。
- 《大地巡旅》PDF 123、125：伦蒂尼姆多地块、产业分区、城墙和领先机械工程。
- `F:\netdisk\明日方舟\CG、背景\背景\27_g4_giantwall.png`：长炮管、斜面炮座、连续高墙和工业背景（C 级归属）。
- `F:\netdisk\明日方舟\CG、背景\背景\27_g5_giantwall_indoor.png`：高墙内部检修平台、钢架和弹药（C 级归属）。

[PRTS]

- [伦蒂尼姆](https://prts.wiki/w/泰拉大典:地理/伦蒂尼姆)：城外高墙上的城防炮是防御核心；副炮也能击穿地面/地下结构。

## Reference Coverage

`C`：主要侧面轮廓、一个内部场景、用途和城市关系可确认；背面、俯视、传动结构、炮口细节和精确比例未知。

## Canon Information

- 城防炮位于包围伦蒂尼姆的高墙上。
- 它是伦蒂尼姆防御核心，具有极高威力。
- 伦蒂尼姆是超大型多地块移动城，具有城墙和产业分区。

## Visual Analysis

### Silhouette

- 低宽斜面装甲炮座；
- 极长、近水平炮管，前端只有小幅抬升；
- 炮管后部有粗重机匣/支撑，不是细柱炮架；
- 炮座重复排列在连续高墙平台；
- 后方由高密工业/城市体量填满，不以孤立炮台呈现。

### Mass

炮座质量集中在墙顶，底部高墙形成大尺度水平基线；炮管将视觉重心从炮座向城外拉伸。首轮不刻画内部房间，只用实心/空腔体量验证比例。

### Proportion

画面透视无法给出精确尺寸。Blockout 采用可评审比例，不声称官方比例：

- 完整段：128（炮轴）×64（沿墙方向）×48（高），L/XL 边界；
- 墙体：64×64×20；
- 炮座主体：32×32×22；
- 炮管可见长度：约 58，直径 6—8；
- 炮管轴线高度：约 34。

### Material

Blockout 只用灰/浅灰/黑/白混凝土和少量玻璃。冷灰装甲和深色炮管来自画面明暗分区，但材质纹理留到 Material Pass。

### Color

主质量冷灰，切缝/炮管灰黑，浅灰只用于分出斜面和平台。蓝色旗帜、警示标识及所有文字在 Blockout 阶段省略。

### Landmark Features

1. [目标] 超长近水平炮管；
2. [目标] 宽大斜面装甲炮座；
3. [目标] 连续高墙水平基线；
4. [目标] 炮座后部重型机匣；
5. [目标] 墙内检修/弹药空腔；
6. [目标] 可与后续相邻炮座重复拼接的 32 格接口。

## Minecraft Reconstruction

Scale: L  
Width: 128  
Length: 64  
Height: 48  
Voxel Resolution: 2—4 格表达主要斜面，1 格细节禁用。

## Modules

```text
defence_cannon/
├─ wall_front_left.nbt      32×20×32
├─ wall_front_right.nbt     32×20×32
├─ wall_rear_left.nbt       32×20×32
├─ wall_rear_right.nbt      32×20×32
├─ turret_core.nbt          32×28×32
├─ barrel_root.nbt          32×16×16
└─ barrel_muzzle.nbt        32×12×12
```

七个模块通过唯一目标名的 Jigsaw 接口确定性拼装；预览结构绑定维多利亚群系并按唯一地标方式注册。该预览注册不替换正式结构 ID。

## Block Palette

见 `../../../../MATERIALS.md` 的 Blockout Palette。

## Custom Textures

无。Blockout 禁止新增正式纹理。

## NBT Layout

- 墙体四模块组成 64×64 底座；炮座位于墙顶，炮管沿 +X 伸出。
- 室内十三房间的尺寸、用途、房门、照明、家具与战利品以同目录 `ROOM_PROGRAM.md` 为实现真值表；四段墙体均为上下两层，楼梯依次连通两层与城墙顶。
- 房间墙面使用有实体背板的维多利亚纹章挂旗，屋顶服务道设框架支撑的国别旗帜；图案为原版旗帜层的原创转译，不复制官方图标资源。
- Jigsaw 拼装后的精确原点：`wall_rear_left=(0,0,0)`、`wall_rear_right=(0,0,32)`、`wall_front_left=(32,0,0)`、
  `wall_front_right=(32,0,32)`、`turret_core=(32,20,16)`、`barrel_root=(64,30,24)`、`barrel_muzzle=(96,32,26)`。
- 32 格模块边界必须与墙板/平台分缝对齐，避免任意切断主轮廓。
- NBT 输出位于 `data/zinecraft/structure/blockout/victoria/londinium/defence_cannon/`。

预览命令：

```mcfunction
/place structure zinecraft:victoria_defence_cannon_preview
/locate structure zinecraft:victoria_defence_cannon_preview
```

## Surrounding Environment

- 前方：墙外空域/低层设施，保留炮管视线；
- 后方：后续接工业街区体块，Blockout 只预留接口；
- 两侧：可重复炮座与高墙段；
- 下方：支持层检修空间，动力层本轮不展开。

## Unknown Areas

- 炮座背面、俯视轮廓、炮管截面和后坐机构；
- 城墙底部与移动地块支持层如何连接；
- 相邻炮座间距和城墙真实高度。

## Inferred Areas

- 模块边界、内部空腔、楼梯/维护通道位置；
- 128×64×48 的 Minecraft 尺度；
- 背面封闭方式。

## Original Areas

首轮 OriginalContentRatio 预计 25—35%，主要来自不可见背面和模块拼接。因此该 Blockout 不能称为高还原资产，必须在获取更多官方视图后降低到
15% 以下，或永久保留 `Lore-Compatible Silhouette Study` 标签。

## Fidelity Checklist

| 项目              | 权重 | 当前状态         |
|-----------------|---:|--------------|
| Silhouette      | 30 | 待生成/评审       |
| Proportion      | 25 | 画面透视下的保守估算   |
| Material        | 15 | BLOCKOUT，不评分 |
| Color           | 10 | 仅明暗占位        |
| Landmark Detail | 10 | 6 项锚点待检查     |
| Environment     | 10 | 只预留接口        |

当前不计算最终 Fidelity Score，不得标 `FINAL`。
