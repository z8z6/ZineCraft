# 泰拉国家建筑资产流程

状态：`ACTIVE`  
更新：2026-08-16

本文落实 [`docs/art.md`](../art.md) 的第一阶段要求。项目中的国家建筑必须沿着“资料—证据—视觉规则—体块—评审—材质—细节—NBT”推进，不能先生成结构再用设定解释。

## 三个开工问题

每个资产开始前必须回答：

1. 官方画面或文字明确展示了什么？
2. 哪些立面、空间、比例或功能仍然未知？
3. Minecraft 转译最应保留的轮廓、体量和环境关系是什么？

无法回答第 1 项时，不进入 Canonical Reconstruction；无法回答第 2 项时，不进入细节设计。

## 来源与证据等级

来源优先级：最新游戏内容 > 官方设定集/官网 > 官方活动、PV、KV 与游戏背景 > PRTS 整理 > 保守推断。

| 等级 | 含义              | 可用于                |
|----|-----------------|--------------------|
| S  | 官方画面直接可见        | 轮廓、比例、材质分区、环境关系    |
| A  | 官方文字明确描述        | 名称、地点、用途、历史和技术性质   |
| B  | PRTS 对官方资料的可靠整理 | 城市/地点索引与交叉核对       |
| C  | 多张官方画面的最小必要推断   | 不可见面、连接构件和玩法尺度补全   |
| U  | 未知              | 记录 UNKNOWN，不进入最终设计 |

每条证据还必须标注 `CANON`、`INFERRED` 或 `ORIGINAL`。官方图只用于研究和体素转译，不复制进发布资源。

## 资料数据库

本轮资料位于：

- 官方设定集：`F:\netdisk\明日方舟\明日方舟世界观设定集：大地巡旅.pdf`，456 页扫描版；国家章节为书内第 107—346 页。
- 官方游戏背景：`F:\netdisk\明日方舟\CG、背景\背景`，共 1406 张 PNG；建筑研究以根目录 1029 张剧情背景为主，排除 `特效` 子目录。
- 官方勘误：[《大地巡旅》勘误页](https://ak.hypergryph.com/corrigendum/terra-a-journey?source_from=book)。
- 二级索引：[PRTS 泰拉大典地理](https://prts.wiki/w/泰拉大典:地理)。

文档布局：

```text
docs/architecture/
├─ TERRA_COUNTRIES_AND_ARCHITECTURE.md
├─ TERRA_ASSET_ROADMAP.md
├─ EXISTING_STRUCTURE_AUDIT.md
└─ countries/<country>/
   ├─ SOURCE_OF_TRUTH.md
   ├─ VISUAL_BIBLE.md
   ├─ MATERIALS.md
   ├─ CITY_LIST.md
   ├─ BUILDING_LIST.md
   └─ cities/<city>/buildings/<asset>/BUILDING.md
```

## 资产状态

| 状态                        | 含义                          | 是否可自然生成         |
|---------------------------|-----------------------------|-----------------|
| `RESEARCH`                | 仅有资料记录                      | 否               |
| `BLOCKOUT`                | 只验证尺度、轮廓和体量                 | 否，默认不接注册        |
| `ARCHITECTURE_PASS`       | 立面节奏和模块完成                   | 仅测试世界           |
| `MATERIAL_PASS`           | 已使用可追踪材质                    | 仅测试世界           |
| `REVIEW`                  | 等待 Canonical Review         | 否               |
| `FINAL`                   | Fidelity Score ≥ 90 且权利记录完整 | 是               |
| `PROTOTYPE_NON_CANONICAL` | 旧原型或玩法占位                    | 可保留兼容，但不得称为官方还原 |

## 分阶段流程

1. **Research**：登记文件路径、网页链接、页码、地点和可信等级。
2. **Evidence**：提取 silhouette、mass、proportion、roof、facade、material、color、lighting、environment。
3. **Visual Bible**：形成国家形体、建筑、材质、色彩、照明和基础设施规则。
4. **City Concept**：同时规划地标、高层/中层/街道层、道路、广场和移动城市基座。
5. **Building Selection**：按 Reference Coverage 选择 A/B 级资产；D 级只能做轮廓重建；E/F 级不做官方还原。
6. **Blockout**：仅使用 stone/concrete/glass/wool 等占位块，优先 16/32/64 模块；不写小装饰。
7. **Blockout Review**：在 500/300/150/50/10 格距离检查轮廓、体量与地标锚点。
8. **Architecture Pass**：加入 facade、window、roof、pillar、bridge、tower、industrial structure。
9. **Material Pass**：替换为有 `reference_id` 的 Canonical Material；沿用稳定国家方块 ID。
10. **Detail Pass**：最后加入灯、管线、标识、装饰和道具。
11. **Canonical Review**：按轮廓 30%、比例 25%、材质 15%、色彩 10%、地标细节 10%、环境 10% 评分。
12. **Integration**：只有 `FINAL` 资产才接入 `ModLandmark`/`ModSettlement` 并运行数据生成、构建和游戏内验证。

## NBT 与模块规则

- 国家代表建筑优先 L/XL/XXL；S 16—32、M 32—64、L 64—128、XL 128—256、XXL 256+。
- 大型资产不得用一个 NBT 保存整栋建筑，至少拆为 `foundation`、`core`、`facade`、`roof`、`tower/annex`、`surrounding`。
- 重复窗列、拱廊、工业框架和楼层使用可重复模块。
- 地标必须同时有 plaza、road、adjacent block、background 和 skyline 设计。
- 移动城市设计必须在资料允许时表达地块边缘、结构层级、动力/工业设施、交通和基座，而不是现实城市平铺。

## 原型冻结线与当前状态

初次审计时，19 套聚落和 38 座唯一地标统一归类为 `PROTOTYPE_NON_CANONICAL`，并采用以下冻结规则：

- 保留已发布 ID 以免破坏存档和数据引用；
- 不继续为旧盒体补细节或贴图；
- 不把现有名称、轮廓、调色板后三槽视为官方设定；
- 新设计先以未注册 Blockout 并行存在，通过评审后再替换旧模板。

2026-08-16 用户批准实际替换后，旧公开 ID 已指向新版 Architecture Pass：19 套聚落包含 171 个模块，38 座地标包含 228 个六段式
Jigsaw 模块。它们可以继续自然生成和 `/locate`，但在完成游戏内旋转、地形、光照、动线和 Fidelity Review 前仍不得标记为 `FINAL`
或 `Canonical Reconstruction`。

## 验证门槛

结构进入注册前依次执行：

```powershell
python script/generate_nation_settlements.py
python script/generate_nation_landmarks.py
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
```

然后在新世界用 `/place structure`、`/locate structure` 检查拼接方向、地形适配、自然生成唯一性和客户端表现。Blockout
阶段不运行材质生成，也不宣称 `FINAL`。
