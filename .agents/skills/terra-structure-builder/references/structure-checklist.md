# 泰拉结构检查表

## 必读路径

- 总准则：`docs/art.md`
- 流程：`docs/architecture/TERRA_ARCHITECTURE_PIPELINE.md`
- 资产路线图：`docs/architecture/TERRA_ASSET_ROADMAP.md`
- 现状审计：`docs/architecture/EXISTING_STRUCTURE_AUDIT.md`
- 国家研究：`docs/architecture/countries/<country>/SOURCE_OF_TRUTH.md`
- 国家视觉：`docs/architecture/countries/<country>/VISUAL_BIBLE.md`
- 国家材料：`docs/architecture/countries/<country>/MATERIALS.md`
- 城市建筑：`docs/architecture/countries/<country>/cities/<city>/buildings/<building>/`
- 结构 API：`src/main/java/com/cxxcxx/zinecraft/api/world/structure/StructureCatalog.java`
- 结构资源：`src/main/resources/data/zinecraft/structure/`
- 世界生成数据：`src/generated/resources/data/zinecraft/worldgen/`

## 建筑文档最小集合

`BUILDING.md` 至少包含：证据 ID/来源/可信等级、用途与容量、轮廓锚点与尺度、总体尺寸与模块、材料及权利边界、Jigsaw 原点与接口、预览/正式
ID 和替换门槛。

`ROOM_PROGRAM.md` 每个房间至少包含：

| 字段        | 要求              |
|-----------|-----------------|
| `room_id` | 稳定 `snake_case` |
| 尺寸        | 外框、净尺寸、地板与顶棚高度  |
| 用途        | 明确的人员活动或设施功能    |
| 动线        | 门朝向、走廊、楼梯和相邻空间  |
| 表面        | 地板、墙、顶棚、门窗      |
| 照明        | 主灯位置、覆盖和物理支撑    |
| 内饰        | 与用途匹配的家具和机械     |
| 容器        | 坐标、地板高度、朝向      |
| 战利品       | 独立表或明确的共享类别     |
| 设定物品      | 注册 ID、来源和非正史声明  |

## 自动断言

- 所有坐标位于模块包围盒内。
- 顶层生成器不含跨国家的房屋、轮廓、屋顶、房间或布灯算法；独特形体按实际组装占用坐标且忽略材质后仍唯一。
- 房间地面连续；容器位于地面上一格且不被覆盖。
- 门上下半块成对，门前后两侧都有实体地板与两格通行空间；接口经过外门后不能被下一层墙体封死。
- 从结构/Jigsaw 入口开始做三维洪水搜索，到达每个房门、用途房间、楼梯、箱子站位、屋顶出口和模块出口；不得从房间内部目标反向起算以绕过封闭入口。
- 上升只允许发生在真实楼梯、梯子或明确台阶；每一级上方至少两格空气，顶部平台不覆盖末级头部空间，跨模块高度连续。
- 对入口可达且有顶的全部站立节点执行遮挡光传播，最远节点光照大于零；灯具邻接楼板、墙、梁、链或灯柱。不得只检查手工挑选的目标点。
- 家具集合满足房间用途，不用告示牌名称代替设施。
- 每个容器只有一个有效 `LootTable`，物品 ID 已注册；容器上方可开启，至少一个相邻站位有地板、两格净空且从入口可达。
- 墙旗背后存在实体墙，屋顶旗帜连接旗杆或框架。
- Jigsaw 父 `target`/子 `name` 成对、父 `pool` 指向实际子池，接缝两侧门洞高度和位置一致；应用 `final_state` 后接缝仍有连续地面与两格净空。
- 从连接器反算全部模块世界原点和实际组合 AABB；四种水平旋转都不越过 `max_distance_from_center`，手填 `assembled_scale`
  必须等于实算值。
- 修改 worldgen 后，生成 JSON 的 placement、spacing、separation、size 与 max distance 和源码契约一致。
- 不残留混凝土占位、调试方块、无来源图标或测试物品。

## 审查门槛

1. `Research`：来源索引和可信等级完成。
2. `Blockout`：轮廓、尺度、模块与环境关系可评审。
3. `Architecture Pass`：逐室用途与尺寸、内外门、真实垂直动线、全可达室内照明、家具、机械和战利品完成。
4. `Art Pass`：原创国家材质、战损、标识和细节完成。
5. `Integration`：预览结构实机通过 `/place`、`/locate`、旋转、跨模块通行和战利品测试。
6. `Canonical`：仅在证据支持且正式 ID 替换获准后使用；否则保持 `preview/blockout` 标识。
