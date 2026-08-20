# 泰拉移动城市与地区地标 Blockout

状态：`BLOCKOUT / NON-CANONICAL PLACEHOLDER`  
实现范围：112 座城市、聚落或城区；75 个重要地区或自然地貌；合计覆盖 `TerraGeography` 的 187 个地点。

## 尺寸口径

本阶段把需求中的“方块”解释为水平占地方块数，而不是单轴长度：

- 每块移动地块为 `32×32=1024` 方块，接近“地块一般 1000 方块”。
- 每个移动城市由中心、北、南、东、西五块地块组成。
- 每块地块模板为 `32×48×32`，不会超过 Minecraft 单个 Structure Template 的 48 格轴长限制。
- 地块垂直分为四层，每层高 8 格：结构层、能源/维护层、物流层和顶层城市甲板。
- 每座城市可声明多个地标；当前首批地标占地为 `15×17=255` 方块，严格位于 200—300 方块范围。
- 普通建筑是逐城候选目录，不绑定东南西北地块。默认布局在外围地块按 `12×12` 棋盘格合法用地放置建筑，并保留 4 格道路。

这些数值是 Minecraft 尺度契约，不表示官方设定提供了统一的移动地块尺寸。

## 世界生成

每个地点都使用 `TerraPlace` 的稳定地图坐标注册固定结构：

- 城市结构：`zinecraft:blockout/terra_city/<nation>/<place_id>`。
- 地区地标：`zinecraft:blockout/terra_region/<nation>/<place_id>`。
- 城市 NBT：`data/zinecraft/structure/blockout/terra_cities/...`，每城五个模板。
- 逐城建筑 NBT：`data/zinecraft/structure/blockout/terra_city_assets/<place_id>/<role>.nbt`，每个地标和普通建筑均有独立文件。
- 地区 NBT：`data/zinecraft/structure/blockout/terra_regions/...`，每地区一个模板。
- 阿戈尔城市使用海床高度图；杜林城市使用地下固定高度；其余城市使用世界表面高度图。
- 所有结构只允许在所属国家的群系中生成，不会借用别国建筑材料。

城市中心模板通过四组具名 Jigsaw 接口连接外围地块。接口目标和模板池均包含城市稳定 ID，避免两座城市交叉组装。

## 四层内容

| 层级  | 当前 Blockout 内容 | 后续正式资产要求           |
|-----|----------------|--------------------|
| L-3 | 承重底板、外壳、支柱     | 城市专属承力结构、灾害防护和检修程序 |
| L-2 | 能源/维护占位层       | 动力源、传动、冷却、源石设备及安全区 |
| L-1 | 物流占位层          | 仓储、货运通道、升降设施和人员动线  |
| L0  | 城市甲板、地标及功能建筑   | 道路、广场、逐栋建筑与当地地标    |

目前四层通过连续维护梯连通；这只满足 Blockout 可达性，不等于正式房间设计。

## 逐城功能建筑声明

112 座城市、聚落和城区均在 `ModCityStructure.java` 的 Java 代码中以稳定 `place_id`
独立声明布局类、地标列表和普通建筑候选目录。生成器要求声明键与 `TerraGeography` 一一对应；缺少、重复或额外城市都会直接失败，且不会从国家配置补全建筑。

现阶段同一国家的城市允许使用相同 Blockout 几何，但每座城市仍在 Java 中逐项声明并注册自己的 `city_center`、`residence`、
`shop`、`workshop` 和 `public_building` 结构。注册路径使用 Minecraft 要求的 ASCII 稳定
ID，中文显示名为“城市名_城市中心”“城市名_住宅”“城市名_商店”“城市名_工坊”“城市名_公共建筑”。这些结构拥有逐城 NBT
路径，可被城市规划器或 `/place structure` 引用，但不创建自然散布的 `StructureSet`。

在 Blockout 阶段允许将相同几何复制并改名为逐城 NBT；这只复用内容，不共享结构身份。替换正式资产时直接覆盖对应城市和角色的
NBT，不会影响其他城市。

## 地标证据边界

全部 187 个地点都有独立结构 ID 和独立地标占位 ID，但“存在一个城市核心体块”不代表官方确认了该体块的名称、外观或位置。逐城声明位于
`ModCityStructure.java`，未知地标统一标记为 `BLOCKOUT / UNKNOWN`。

替换某个地标前必须补齐该城市的 `SOURCE_OF_TRUTH.md`、`BUILDING.md` 和 `ROOM_PROGRAM.md`
。已有国家地标不能因为同属一个国家就复制到所有城市；只有资料明确城市归属后，才能替换对应的占位 ID。

## 生成与审查

```powershell
python script/generate_terra_city_blockouts.py
.\gradlew.bat runData
.\gradlew.bat build
```

生成器会断言：

- 地点目录恰好为 187 项，其中 112 项属于城市、聚落或城区。
- 112 座城市均有且仅有一份逐城建筑声明，不能由国家配置隐式继承。
- 每座移动城市恰好包含五块 `32×48×32` 地块。
- 每块地块包含四层，水平面积为 1024 方块。
- 每座城市可包含 1—5 个 Blockout 地标，每个地标占地为 200—300 方块。
- 普通建筑通过默认棋盘格布局放入合法用地，不按外围地块方向绑定。
- 112 个城市核心的占位形体互不重复。
- 输出恰好包含 560 个城市拼装 NBT、560 个逐城注册结构 NBT 和 75 个地区地标 NBT。

## Sources

- [泰拉十九国城市与重要地区目录](../nation/TERRA_GEOGRAPHY.md)：地点名称和国家归属。
- [泰拉城市与地区游戏化布局](../nation/TERRA_PLACE_LAYOUT.md)：游戏坐标、边界和非官方布局声明。
- [泰拉城市内部建筑生成逻辑](CITY_GENERATION_PIPELINE.md)：正式城市规划与替换门槛。
- [PRTS 泰拉大典：地理](https://prts.wiki/w/泰拉大典:地理)：地名与国家归属索引。
