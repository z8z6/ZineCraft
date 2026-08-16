# 泰拉十九国建筑重设计总纲

状态：`DESIGN PACK IMPLEMENTED / IN-GAME REVIEW REQUIRED`
更新：2026-08-16

## 目标

以设定集、官方剧情背景、官网、PRTS 与游戏数据为证据，替换旧版“同一盒体算法 +
国家调色板”的十九国建筑原型。重设计覆盖每国现有四栋聚落功能建筑与两座地标，但不将局部城市视觉推广为全国风格。

十九国设计合同已经用于兼容替换自然生成 NBT：公开 ID 保持不变，聚落进入 Architecture Pass，地标进入六段式多模块 Architecture
Pass。逐国 `REDESIGN.md` 仍是证据、语境、尺度、模块和房间审查依据；实机评审前不得称为 Canonical Final。

## 共用硬约束

1. 每国至少拆分两种不可混用的建筑语境，例如移动城/固定聚落、首都/地方、工业区/生活区或灾前/灾后。
2. 每个现有结构 ID 必须明确 `保留`、`改名`、`替换` 或 `冻结`，不能默认旧名称正确。
3. 普通建筑至少 S（16—32 格）；国家地标优先 L/XL/XXL。低矮建筑通过宽度、广场、道路和组团获得尺度，不强行拔高。
4. 地标拆分为 foundation、core、facade、roof、annex、interior、surrounding；大型建筑不得继续使用单 NBT 图标模型。
5. 房间按“划分→动线→完成面→照明→用途内饰→容器→战利品/设定物品”顺序设计。
6. 技术建筑必须呈现可理解的动力、操作、物流和维护关系；Create 只用于有功能逻辑的机械细节。
7. 灯具必须有承托，楼梯必须有两格头部净空，容器必须位于完成地板上一格，Jigsaw 接缝必须可通行。
8. 官方图像只用于研究；材质、旗帜和标志均为原创体素转译，不复制官方素材。

## 国家设计包

| 国家   | ID             | 独立设计                                           |
|------|----------------|------------------------------------------------|
| 阿戈尔  | `aegir`        | [REDESIGN](countries/aegir/REDESIGN.md)        |
| 玻利瓦尔 | `bolivar`      | [REDESIGN](countries/bolivar/REDESIGN.md)      |
| 东    | `higashi`      | [REDESIGN](countries/higashi/REDESIGN.md)      |
| 杜林   | `durin`        | [REDESIGN](countries/durin/REDESIGN.md)        |
| 哥伦比亚 | `columbia`     | [REDESIGN](countries/columbia/REDESIGN.md)     |
| 卡西米尔 | `kazimierz`    | [REDESIGN](countries/kazimierz/REDESIGN.md)    |
| 卡兹戴尔 | `kazdel`       | [REDESIGN](countries/kazdel/REDESIGN.md)       |
| 拉特兰  | `laterano`     | [REDESIGN](countries/laterano/REDESIGN.md)     |
| 莱塔尼亚 | `leithanien`   | [REDESIGN](countries/leithanien/REDESIGN.md)   |
| 雷姆必拓 | `rim_billiton` | [REDESIGN](countries/rim_billiton/REDESIGN.md) |
| 米诺斯  | `minos`        | [REDESIGN](countries/minos/REDESIGN.md)        |
| 萨尔贡  | `sargon`       | [REDESIGN](countries/sargon/REDESIGN.md)       |
| 萨米   | `sami`         | [REDESIGN](countries/sami/REDESIGN.md)         |
| 维多利亚 | `victoria`     | [REDESIGN](countries/victoria/REDESIGN.md)     |
| 乌萨斯  | `ursus`        | [REDESIGN](countries/ursus/REDESIGN.md)        |
| 谢拉格  | `kjerag`       | [REDESIGN](countries/kjerag/REDESIGN.md)       |
| 叙拉古  | `siracusa`     | [REDESIGN](countries/siracusa/REDESIGN.md)     |
| 炎    | `yan`          | [REDESIGN](countries/yan/REDESIGN.md)          |
| 伊比利亚 | `iberia`       | [REDESIGN](countries/iberia/REDESIGN.md)       |

## 实施顺序

1. 维多利亚 Pilot 完成实机评审和正式替换门槛。
2. 炎、拉特兰、阿戈尔、卡西米尔建立 L/XL 多模块 Blockout。
3. 雷姆必拓、谢拉格、伊比利亚、卡兹戴尔、乌萨斯验证工业、山地、海岸与战损模块。
4. 莱塔尼亚、叙拉古、萨尔贡、哥伦比亚、米诺斯、玻利瓦尔、东先补城市级证据再制作地标。
5. 萨米优先实现轻型可迁移聚落网络；杜林先补文字来源，再实现理想城公共设施。

旧统一盒体与单模板地标已经被替换。当前实现见 `NATION_SETTLEMENT_IMPLEMENTATION.md`、`NATION_LANDMARK_IMPLEMENTATION.md` 和
`NATION_STRUCTURE_LOOT.md`；游戏内旋转、地形、照明、动线和远景 Fidelity 尚待逐国复核。
