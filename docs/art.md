# 明日方舟泰拉国家大型建筑 / 城市 / NBT / 贴图资产工程

## 0. 项目身份

你正在参与一个 **Minecraft 1.21.1 NeoForge《明日方舟》同人模组**的世界与建筑资产开发。

本项目需要根据《明日方舟》泰拉世界观，为不同**泰拉国家、移动城市、地区与组织**制作：

* 超大型城市地标
* 国家代表性建筑
* 移动城市建筑群
* 城市街区
* 基础设施
* 工业设施
* 军事设施
* 商业设施
* 宗教 / 行政 / 学术 / 文化建筑
* 城市公共空间
* 道路、桥梁、广场等城市基础设施
* Minecraft Structure NBT
* 建筑专属方块及贴图
* 国家级共享材质库

本项目最重要的目标依次为：

**官方还原度 > 世界观正确性 > 国家视觉辨识度 > 城市宏大感 > Minecraft 可玩性 > 开发效率**

任何情况下，都不能为了“好看”“宏大”“方便生成”牺牲官方设定。

---

# 1. “国家”的定义

本项目中的“国家”**不是现实世界国家**。

这里的国家特指《明日方舟》泰拉世界观中的国家与文明体系，例如：

* 维多利亚
* 炎
* 乌萨斯
* 哥伦比亚
* 莱塔尼亚
* 卡西米尔
* 伊比利亚
* 拉特兰
* 叙拉古
* 萨尔贡
* 卡兹戴尔
* 萨米
* 谢拉格
* 雷姆必拓
* 米诺斯
* 玻利瓦尔
* 阿戈尔
* 东
* 杜林
* 以及官方后续公布的国家或地区

具体国家列表必须以当前官方资料和 PRTS.wiki 为准。

---

# 2. 最重要规则：禁止把泰拉国家直接等同现实国家

严禁使用：

```text
维多利亚 = 英国
乌萨斯 = 俄罗斯
炎 = 中国
莱塔尼亚 = 奥地利/德国
叙拉古 = 意大利
拉特兰 = 梵蒂冈
哥伦比亚 = 美国
```

这样的简化逻辑直接生成建筑。

现实世界文化只能作为理解鹰角设计语言时的背景知识。

## 禁止

例如不得因为：

```text
维多利亚类似英国
```

于是未经官方依据直接加入：

* Big Ben
* Westminster
* Buckingham Palace
* 伦敦经典街道
* 英式红砖建筑复制品

同样：

```text
炎
```

不能直接变成：

* 故宫复制品
* 北京城
* 长城
* 唐宋古建筑合集

除非《明日方舟》官方视觉资料明确表现了对应设计元素。

---

# 3. 唯一允许使用的世界观资料来源

## 第一优先级：鹰角官方资料

允许：

```text
https://ak.hypergryph.com/
```

包括但不限于：

* 明日方舟官网 WORLD / 世界观页面
* 官方活动页面
* 官方主线宣传页面
* SideStory 官方页面
* 官方 PV
* 官方概念图
* 官方角色宣传图
* 官方活动 KV
* 官方背景图
* 官方家具设计
* 官方场景图
* 官方设定图
* 官方美术设定
* 《大地巡旅：明日方舟官方世界观设定集》
* 官方美术设定集

如果仓库中保存了上述官方截图或扫描参考：

```text
reference/official/
```

这些文件同样属于一级参考。

---

## 第二优先级：PRTS.wiki

允许：

```text
https://prts.wiki/
```

主要用于：

* 国家信息
* 城市信息
* 地点信息
* 活动资料
* 剧情出处
* 场景出处
* 官方设定整理
* 图片出处定位
* 国家、城市、组织关系确认

PRTS 是**资料整理与索引来源**。

如果 PRTS 内容能够追溯到游戏文本或官方资料：

优先继续寻找原始官方来源。

---

# 4. 禁止资料来源

未经我明确允许，禁止以以下内容作为建筑设计依据：

* 百度百科
* 萌娘百科
* Fandom Wiki
* Reddit
* NGA
* Bilibili 二创视频
* YouTube 二创视频
* Pinterest
* DeviantArt
* Pixiv 二创
* AI 生成图
* 玩家同人设定
* 玩家建筑
* 玩家地图
* 现实城市照片
* 现实建筑照片
* Google 图片搜索中的未知来源图片
* 搜索引擎摘要
* 其他游戏的建筑

这些内容即使视觉效果很好，也不能进入 Canonical Reference。

---

# 5. Source of Truth 规则

所有建筑设计前必须建立：

```text
SOURCE_OF_TRUTH.md
```

禁止先开始建筑再寻找资料解释设计。

---

## 每一项信息必须标记可信等级

使用：

```text
S — 官方直接展示
A — 官方文字明确描述
B — PRTS 对官方资料的可靠整理
C — 根据多个官方画面的保守推断
U — 未知
```

其中：

### S

官方画面直接可以看到：

```text
伦蒂尼姆某建筑具有巨大烟囱
```

可以直接还原。

### A

官方文字说明：

```text
某设施属于军事用途
```

可以按照文字确定功能，但视觉设计仍需要官方视觉依据。

### B

PRTS 引用或整理官方资料。

可用于补充信息。

### C

只能做**最小必要推断**。

不得扩展成大量原创设计。

### U

官方没有资料。

必须记录：

```text
UNKNOWN
```

不得假装存在官方设定。

---

# 6. 官方资料冲突处理

资料优先级：

```text
最新官方游戏内容
        >
官方设定集 / 官网
        >
官方活动 / PV / KV
        >
PRTS 对官方资料的整理
        >
基于官方资料的保守推断
```

如果出现冲突：

**官方资料永远覆盖 PRTS。**

必须记录：

```text
SOURCE_CONFLICT
```

并说明采用哪个版本以及理由。

---

# 7. 禁止 AI 幻觉

这是整个项目最重要的开发约束之一。

如果官方只展示：

```text
建筑正面
```

不得自行声称：

```text
建筑背面就是……
```

如果官方只展示：

```text
城市天际线
```

不得声称：

```text
官方设定城市道路布局为……
```

---

## 必须明确区分

```text
CANON
官方明确内容

INFERRED
根据官方视觉合理推断

ORIGINAL
为了 Minecraft 游戏性不得不补充的原创内容
```

其中：

```text
ORIGINAL
```

必须尽可能少。

---

# 8. 设计哲学

项目不是：

> 根据现实世界国家设计“明日方舟风建筑”。

项目应该是：

> 将鹰角已经建立的泰拉视觉语言，以 Minecraft 的体素与建筑系统重新表达。

---

# 9. 建筑设计第一原则：Silhouette First

对于超大型建筑：

**远景轮廓比局部贴图更加重要。**

分析顺序：

```text
Silhouette
    ↓
Mass / Volume
    ↓
Proportion
    ↓
Landmark Features
    ↓
Facade Rhythm
    ↓
Material
    ↓
Small Details
```

禁止从：

```text
窗户
门
砖块
装饰
```

开始设计。

---

# 10. 宏大感

城市建筑必须让玩家产生明显的尺度感。

允许大量采用：

```text
80 blocks
120 blocks
200 blocks
300 blocks
甚至更大的建筑尺度
```

不要因为 Minecraft 默认村庄尺度而缩小建筑。

---

## 建筑尺度等级

统一定义：

```text
S
普通建筑
16~32 blocks

M
大型建筑
32~64 blocks

L
城市建筑
64~128 blocks

XL
地标建筑
128~256 blocks

XXL
城市核心 / 巨构
256+ blocks
```

国家代表建筑优先：

```text
L / XL / XXL
```

---

# 11. 但是“宏大”不能覆盖“还原”

如果官方建筑视觉表现为低矮、宽阔：

不得为了宏大感把它改成摩天楼。

正确方法是通过：

* 超宽尺度
* 巨型广场
* 前景空间
* 建筑群
* 强烈轴线
* 巨型基础设施
* 城市背景

制造宏大感。

---

# 12. 每个国家必须建立 Visual Bible

创建：

```text
assets/design/countries/<country>/VISUAL_BIBLE.md
```

必须分析：

## Shape Language

例如：

```text
Vertical
Horizontal
Monumental
Industrial
Organic
Geometric
Dense
Sparse
```

但所有判断必须有来源。

---

## Architecture Language

分析：

* 建筑总体比例
* 塔楼比例
* 屋顶
* 建筑基座
* 立面分区
* 窗户
* 拱
* 柱
* 金属结构
* 管线
* 工业设施
* 广告系统
* 标识系统
* 灯光

---

## Material Language

例如：

```text
stone
steel
painted metal
copper
glass
concrete
wood
originium technology
```

同样必须依据官方画面。

---

## Color Language

提取：

```text
Primary
Secondary
Accent
Neutral
Emissive
```

禁止凭印象生成国家色板。

---

# 13. Reference Board

每个国家必须建立：

```text
reference/
└── terra/
    └── <country>/
        ├── official/
        │   ├── city/
        │   ├── architecture/
        │   ├── street/
        │   ├── interior/
        │   ├── infrastructure/
        │   ├── vehicle/
        │   ├── signage/
        │   └── material/
        │
        ├── prts/
        │
        └── SOURCE_INDEX.md
```

---

# 14. SOURCE_INDEX

每张参考图必须记录：

```text
ID:
Source:
URL:
Source Type:
Country:
City:
Event/Chapter:
Scene:
Canonical Level:
Relevant Elements:
Notes:
```

例如：

```text
ID: victoria_londinium_017

Source Type:
OFFICIAL

Country:
Victoria

City:
Londinium

Canonical Level:
S

Relevant Elements:
- skyline
- tower proportion
- industrial chimney
- facade
- bridge

Notes:
用于城市远景和主要建筑比例分析。
```

---

# 15. 每个建筑必须有 Reference Coverage

生成建筑之前计算：

```text
ReferenceCoverage
```

分为：

```text
A
官方资料非常充分

B
主要结构可见

C
只有部分结构

D
只有远景

E
只有文字

F
几乎没有资料
```

---

## 对应处理

### A/B

允许高还原复刻。

### C

允许：

```text
官方主体 + 保守补全
```

### D

优先：

```text
Silhouette Reconstruction
```

而不是虚构细节。

### E/F

禁止声称：

```text
Canonical Reconstruction
```

只能：

```text
Lore-Compatible Original
```

并显著标注。

---

# 16. 单建筑分析流程

每个大型建筑必须按照以下顺序工作：

### STEP 1 — Reference Collection

搜索：

```text
官网
PRTS
仓库 official reference
```

---

### STEP 2 — Evidence Extraction

整理：

```text
Silhouette
Height
Width
Mass
Roof
Facade
Material
Color
Lighting
Environment
Adjacent Buildings
```

---

### STEP 3 — Landmark Features

选出：

```text
3~10
```

个最重要视觉锚点。

没有这些特征：

建筑就算失败。

---

### STEP 4 — Minecraft Reconstruction

确定：

```text
MinecraftScale
BlockDimensions
VoxelResolution
Modules
Palette
TextureSet
```

---

### STEP 5 — NBT Generation

最后才进入结构生成。

---

# 17. 不要单独生成一栋建筑

这是城市项目。

每个大型建筑同时必须分析：

```text
BUILDING
+
PLAZA
+
ROAD
+
ADJACENT BLOCK
+
BACKGROUND
+
SKYLINE
```

城市环境本身属于资产。

---

# 18. 城市 Concept 必须来自官方资料

禁止：

> “我觉得维多利亚应该是一座蒸汽朋克伦敦。”

应该：

> 分析官方伦蒂尼姆、维多利亚活动背景、剧情场景、PV、设定资料后，总结鹰角实际使用的城市视觉语言。

---

# 19. 城市分层

每个城市按照：

```text
CITY
│
├─ Mega Structure
│
├─ Landmark
│
├─ Major Building
│
├─ City Block
│
├─ Street
│
├─ Infrastructure
│
└─ Detail Props
```

设计。

---

# 20. 城市天际线

必须设计：

```text
Primary Landmark

Secondary Landmark

High-rise Cluster

Mid-rise Layer

Street Layer
```

玩家在：

```text
500 blocks
300 blocks
150 blocks
50 blocks
10 blocks
```

距离下，都应该得到不同层级的视觉信息。

---

# 21. 移动城市

泰拉建筑设计必须特别考虑：

```text
Mobile City
```

不能把城市简单理解为现实城市。

当官方资料允许时，需要考虑：

* 移动城市地块
* 城市结构层级
* 巨型工业设施
* 城市边缘
* 动力设施
* 大型机械结构
* 交通系统
* 城市基座
* 城市垂直层级

---

# 22. NBT 架构

禁止：

```text
one_building.nbt
```

保存整座超大建筑。

采用：

```text
city
└── district
    └── building
        ├── foundation
        ├── core
        ├── facade
        ├── roof
        ├── tower
        ├── decoration
        └── surrounding
```

---

# 23. Module Grid

大型建筑优先采用：

```text
16x16
32x32
64x64
```

模块网格。

---

# 24. 示例

```text
victoria/
└── londinium/
    └── landmark_x/
        ├── foundation_00.nbt
        ├── foundation_01.nbt
        ├── core_00.nbt
        ├── facade_north_00.nbt
        ├── facade_repeat_a.nbt
        ├── tower_00.nbt
        ├── roof_00.nbt
        └── plaza_00.nbt
```

---

# 25. 重复建筑结构

对于：

* 窗列
* 柱列
* 拱廊
* 工业框架
* 高层楼面
* 城市住宅

必须识别：

```text
Repeatable Module
```

避免逐方块重复生成。

---

# 26. 建筑纹理原则

贴图绝对不能由 AI 随机生成。

必须先从官方资料建立：

```text
Material Library
```

---

# 27. 国家材质库

目录：

```text
textures/block/terra/<country>/
```

例如：

```text
wall/
metal/
stone/
glass/
roof/
floor/
industrial/
ornament/
sign/
emissive/
```

---

# 28. Canonical Material

定义：

```text
CanonicalMaterial
```

一个材质必须保存：

```text
material_id
reference_id
base_color
brightness_range
roughness_visual
pattern
edge_style
variants
```

---

# 29. 贴图不是照片转换

严禁：

```text
现实建筑照片
↓
直接 pixelate
↓
Minecraft texture
```

必须重新建立符合项目整体视觉语言的：

```text
Pixel Material
```

---

# 30. 国家之间必须明显区分

远看城市时，玩家应该不需要 HUD 就能够大致判断：

> “这里属于哪个泰拉国家。”

这种区别必须来源于：

```text
Shape
Scale
Material
Color
Technology
Urban Density
Lighting
Signage
Infrastructure
```

而不是简单换旗帜。

---

# 31. 国家 Asset Pack

每个国家至少规划：

```text
1~2 XXL Mega Structures

3~5 XL Landmarks

5~10 L Buildings

10~20 M Buildings

若干 Modular City Blocks

Street Kit

Infrastructure Kit

Material Library
```

不要求第一次全部实现。

先建立规划。

---

# 32. 城市不允许成为“建筑博物馆”

禁止：

```text
地标
空地
地标
空地
地标
```

城市必须有：

```text
Urban Fabric
```

包括：

* 普通建筑
* 住宅
* 商业
* 工业
* 巷道
* 后勤设施
* 道路
* 公共交通
* 城市设施

才能衬托地标规模。

---

# 33. Detail Density

采用三级细节：

```text
MACRO
城市轮廓

MESO
建筑体块与立面

MICRO
门窗、标识、设备
```

优先顺序：

```text
MACRO > MESO > MICRO
```

禁止在城市轮廓错误的情况下花大量时间制作门把手。

---

# 34. 高还原检查

每个建筑完成后进行：

```text
Canonical Review
```

至少比较：

### Silhouette

官方：

```text
████████
   ███
   ███
```

NBT：

```text
████████
   ███
   ███
```

整体轮廓必须接近。

---

### Proportion

比较：

```text
Height / Width

Tower / MainBody

Roof / Body

Window / Wall
```

---

### Color Distribution

不是比较单个像素。

比较：

```text
Dark Area
Light Area
Accent
Emissive
```

比例。

---

### Landmark Features

逐项确认：

```text
[PASS]
[MISSING]
[WRONG]
```

---

# 35. 必须输出还原度评分

```text
Fidelity Score
```

由：

```text
Silhouette        30%
Proportion        25%
Material          15%
Color             10%
Landmark Detail   10%
Environment       10%
```

组成。

---

目标：

```text
>= 90
```

如果不足：

禁止标记资产为：

```text
FINAL
```

---

# 36. Original Content Budget

为了避免 AI 过度设计：

为每个资产计算：

```text
OriginalContentRatio
```

高还原建筑应：

```text
< 15%
```

如果：

```text
> 30%
```

必须说明：

> 当前官方资料不足，本建筑不能视为高还原资产。

---

# 37. 数据结构

建议：

```text
assets/design/
└── countries/
    ├── victoria/
    │   ├── COUNTRY.md
    │   ├── VISUAL_BIBLE.md
    │   ├── SOURCE_OF_TRUTH.md
    │   ├── MATERIALS.md
    │   │
    │   ├── cities/
    │   │   └── londinium/
    │   │       ├── CITY.md
    │   │       ├── SKYLINE.md
    │   │       └── buildings/
    │   │
    │   └── assets/
    │
    ├── yan/
    ├── ursus/
    └── ...
```

---

# 38. 建筑 Design Document

每栋建筑创建：

```text
BUILDING.md
```

包含：

```text
# Identity

Country:
City:
Building:
Canonical Status:

# Sources

Official:
PRTS:

# Reference Coverage

# Canon Information

# Visual Analysis

## Silhouette

## Mass

## Proportion

## Material

## Color

## Landmark Features

# Minecraft Reconstruction

Scale:
Width:
Length:
Height:

# Modules

# Block Palette

# Custom Textures

# NBT Layout

# Surrounding Environment

# Unknown Areas

# Inferred Areas

# Original Areas

# Fidelity Checklist
```

---

# 39. Codex 工作规则

你不能：

> 一收到“制作维多利亚城市”就开始写 NBT。

必须：

```text
Research
↓
Evidence
↓
Visual Bible
↓
City Concept
↓
Building Selection
↓
Reference Analysis
↓
Blockout
↓
Review
↓
Material
↓
Detail
↓
NBT
```

---

# 40. 首先制作 Blockout

第一轮 NBT：

只允许：

```text
stone
concrete
glass
wool
```

等临时方块。

目标只验证：

```text
Scale
Silhouette
Mass
Skyline
```

不要做细节。

---

# 41. Blockout Review

Blockout 必须经过：

```text
REFERENCE
vs
MINECRAFT
```

比较。

如果轮廓错误：

直接重建。

禁止进入贴图阶段。

---

# 42. 第二轮：Architecture Pass

加入：

```text
facade
window
roof
pillar
bridge
tower
industrial structure
```

---

# 43. 第三轮：Material Pass

替换：

```text
placeholder
```

为：

```text
Canonical Material
```

---

# 44. 第四轮：Detail Pass

最后加入：

```text
sign
light
pipe
ornament
prop
```

---

# 45. 不允许一次性生成完整城市

必须分阶段。

例如：

```text
Victoria / Londinium

Phase 1
Reference

Phase 2
Skyline

Phase 3
Landmark Blockout

Phase 4
Urban Block

Phase 5
Material

Phase 6
Infrastructure

Phase 7
Details
```

---

# 46. 每次工作前先回答三个问题

```text
1. 官方到底展示了什么？

2. 哪些东西我们不知道？

3. Minecraft 中最应该保留什么？
```

然后才开始实现。

---

# 47. 当资料不足时

不要询问：

> “要不要让我自由设计？”

默认行为：

```text
降低原创程度
↓
保留已知轮廓
↓
使用同国家已确认视觉元素
↓
最小化不可见区域
↓
标记 INFERRED
```

不要凭空创造一座“看起来很明日方舟”的城市。

---

# 48. 每次输出都必须附 Sources

格式：

```text
## Sources

[OFFICIAL]
Title:
URL:
Used For:

[PRTS]
Page:
URL:
Used For:
```

同时建立：

```text
source -> asset
```

追踪关系。

---

# 49. Codex 不负责创造美术设定

Codex 的职责是：

```text
Official Visual
        ↓
Analyze
        ↓
Voxel Reconstruction
        ↓
Minecraft Asset
```

而不是：

```text
Text Prompt
        ↓
Imagine Arknights
        ↓
Generate Random Art
```

---

# 50. 当前第一阶段任务

现在不要生成最终 NBT，也不要直接生成贴图。

首先建立：

```text
TERRA_ARCHITECTURE_PIPELINE.md
```

并完成以下内容：

### 1

建立泰拉国家资产数据库结构。

### 2

从：

```text
ak.hypergryph.com
prts.wiki
```

整理当前可以确认的国家列表。

### 3

针对每个国家建立：

```text
Known Cities
Known Locations
Known Buildings
Known Infrastructure
Available Visual References
```

### 4

计算：

```text
ReferenceRichness
```

范围：

```text
0~100
```

### 5

根据：

```text
ReferenceRichness
+
Visual Uniqueness
+
City Scale
+
Available Architecture
```

评估哪些国家最适合优先制作。

### 6

不要因为个人喜好决定国家顺序。

### 7

输出：

```text
TERRA_ASSET_ROADMAP.md
```

包含建议开发顺序。

### 8

选择资料最丰富的一个国家作为：

```text
Pilot Country
```

### 9

只为 Pilot Country 建立：

```text
SOURCE_OF_TRUTH.md
VISUAL_BIBLE.md
MATERIALS.md
CITY_LIST.md
BUILDING_LIST.md
```

### 10

完成这些内容以后，再开始第一个大型建筑的 Blockout。

---

# 最终原则

整个项目始终牢记：

> **我们不是在创造“像明日方舟”的 Minecraft 城市。**

> **我们是在尽可能忠实地把泰拉已经存在的城市与建筑视觉语言转换成 Minecraft 世界。**

因此：

```text
有官方资料 → 还原

有部分资料 → 保守重建

没有资料 → 标记未知

资料不足 → 宁可不做

绝不让 AI 幻觉替代官方设定
```

任何建筑、纹理、城市规划或材质，只要无法说明：

> “这个设计依据来自哪里？”

就不能进入最终资产。
