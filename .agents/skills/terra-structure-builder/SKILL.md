---
name: terra-structure-builder
description: Research, design, generate, register, and validate evidence-backed Terra architecture for Zinecraft. Use when adding or revising Arknights nation buildings, rooms, interiors, mobile-city components, Jigsaw modules, settlements, landmarks, structure NBT, structure loot, preview registrations, or in-game architecture reviews.
---

# 泰拉结构建造

将明日方舟资料转译为可游玩、可维护、可验证的 Minecraft 结构。先建立证据与建筑程序，再生成 NBT；不得用国家刻板印象、建筑名称或统一盒体代替设计。

## 建立上下文

1. 阅读仓库根目录 `AGENTS.md`、`docs/art.md`、`docs/architecture/TERRA_ARCHITECTURE_PIPELINE.md` 和目标国家现有文档。
2. 执行 `git status --short`；保留用户和并行任务的修改。
3. 同时使用 `terra-nation-materials` 处理国家色彩、材料和权利边界，使用 `zinecraft-content` 处理结构目录、注册和数据生成。
4.
创建或审查房间、Jigsaw、战利品、预览结构时，完整读取 [references/structure-checklist.md](references/structure-checklist.md)。

## 证据先行

1. 依次检索设定集、游戏内 CG/背景、官网、PRTS、ArknightsGameData；记录文件、页码或 URL。
2. 为关键结论标记 `S/A/B/C/U`。间接画面只支持保守转译，未知项不得伪装为还原。
3. 区分国家、城市、机构和单一剧情地点；不得把局部视觉推广为全国统一建筑语言。
4. 先补齐目标国家的 `SOURCE_OF_TRUTH.md`、`VISUAL_BIBLE.md`、`MATERIALS.md`、建筑清单和目标建筑 `BUILDING.md`。证据不足时只做标明假设的
   Blockout。
5. 按仓库 `AGENTS.md` 的资源策略优先使用可核对的官方/PRTS/GameData 资料，不得在已有合适资料时自行臆造。若用户明确要求把官方
   CG/背景直裁为本地纹理，必须记录源图与坐标、避开人物/文字/UI/徽标，并标明衍生资源与公开发布授权边界；旗帜和标志仍采用原创体素转译，除非用户提供了可直接使用且权利明确的资源。

## 从建筑程序开始

按顺序设计，不得跳步：

1. 定义用途、使用者、容量、危险区和维护需求。
2. 确定总体尺寸、轮廓锚点、远中近三档可读性，以及道路、广场、相邻建筑与天际线关系。
3. 列出基础、主体、立面、屋顶、附属、内部和环境模块；大型地标不得只用一个 NBT。
4. 建立 `ROOM_PROGRAM.md`：逐室记录尺寸、楼层、用途、入口、家具、照明、容器和战利品。
5. 先验证门、走廊、楼梯、屋顶出口、跨 Jigsaw 接口与至少两格玩家头部净空。
6. 再铺地板、墙、顶棚与门窗；最后按用途布置机械、桌椅、床、档案、标语、盆栽、旗帜和容器。
7. 每盏灯必须有物理承托。用主照明保证空间覆盖，灯笼只作气氛和路径提示。
8. 战利品按房间拆表；国家特产、耗材和剧情物品必须是已注册 ID，并记录证据边界。不得为填箱伪造设定物品。

## 禁止统一建筑生成

1. 每个国家、每座具名地标和每类功能建筑必须在所属国家文件中显式定义形体、房间和基础设施。不得用跨国家的
   `profile/kind/silhouette/roof/feature` 分支、统一盒体、换色或稳定 seed 代替独立设计。
2. 顶层生成器只允许导入、编排、验证和写出；共享模块只允许 NBT 序列化、单方块/长方体等低层原语和不改变建筑的验证器。
3. 共享层不得自动挖门、补房间、生成楼梯、布灯或修正轮廓。发现验证失败时回到对应国家/建筑函数显式修复。
4. 唯一性必须按实际组装后的占用坐标检查，并忽略国家材质 ID；调色板变化、状态槽变化或手填尺寸不算独特形体。

## Minecraft 实现

1. 小型单体用 `simpleBuilding`，可重复聚落用 `settlement`，多模块建筑用 `jigsawBuilding`，每世界一次地标用 `uniqueLandmark`
   ；固定中心使用 fixed-origin 变体。
2. 大型建筑使用确定性生成脚本和稳定 `snake_case` ID。NBT 放在 `src/main/resources/data/zinecraft/structure/`，不手工维护可由目录生成的
   JSON。
3. Jigsaw 父 `target` 必须匹配子 `name`，父 `pool` 必须引用实际子池，方向相对，接口处必须真正可通行。校验时应用双方
   `final_state`，按连接器坐标反算每个模块的世界原点；不能把允许生成距离或手填尺度当成实际建筑尺寸。
4. 国家主材优先使用项目原创方块；原版和 Create 方块用于门窗、照明、家具、传动、管线和机械细节。技术设施必须体现操作、动力、维护和物流关系。
5. 注册独立预览 ID，保留正式 ID，直到游戏内评审通过。已发布 ID 优先保持兼容。
6. 在生成脚本中断言实际组装包围盒、模块数、旋转后的生成半径、门与楼梯、净空、房间连通、灯具支撑、家具、容器高度、战利品、旗帜背板和
   Jigsaw 接口。
7. 聚落街道的建筑父接口必须引用本国 `buildings` 池，并从道路有连续地面到接口；建筑子接口必须有地板，且从 `final_state=air`
   的接口可穿过外门到首个房间。
8. 房间可达性从结构入口开始做三维玩家洪水搜索。上升只能通过真实楼梯、梯子或明确台阶；检查末级头部、顶部落脚和跨模块连续性。装饰塔冠不得被误报为可用楼层。
9. 对入口可达且有顶的全部站立节点执行遮挡光传播，不只检查手工标注点。箱子必须可开启，并至少有一个从入口可达、带地板和两格净空的相邻站位。

## 验证与交付

1. 运行结构生成器和语法/资源校验。
2. 分别运行 `./gradlew.bat test`、`./gradlew.bat runData`、`./gradlew.bat build`。修改放置参数后必须读取
   `src/generated/resources`，确认 JSON 中的 spacing、separation、size、max distance、placement type 已更新；只检查 Java
   源不算通过。
3. 在新位置执行 `/place structure <preview_id>`；使用 `/locate structure <preview_id>` 验证世界生成注册。已放置旧结构不会自动更新。
4. 从 500/300、150、50、10 格距离检查轮廓、比例、材质、颜色、细节与环境，并记录到 `BLOCKOUT_REVIEW.md`。
5. 报告结构 ID、模块、房间、战利品、验证结果、未完成的实机检查和仍属推断的部分。证据与实机评审均通过后才能替换正式结构。
