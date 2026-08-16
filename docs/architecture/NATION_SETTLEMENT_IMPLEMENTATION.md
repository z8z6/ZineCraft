# 十九国聚落结构实现

状态：`ARCHITECTURE PASS / IN-GAME REVIEW REQUIRED`
更新：2026-08-16

十九份 `docs/architecture/countries/<country>/REDESIGN.md` 是本轮实现合同，官方剧情背景与设定集只在各自证据边界内使用。正式
settlement 路径、`center`、四种 `street_*` 和每国四个兼容建筑文件名均保持不变，现有 Jigsaw 池与 Java 注册无需迁移。

## 实现结构

- `script/nation_settlements/common.py` 只提供确定性 NBT 序列化、单方块/长方体/清空/连接器低层原语与中立验证器。
- `script/nation_settlements/<nation>.py` 是十九个相互独立的国家 builder。每个文件显式实现本国
  `center + 4 streets + 4 buildings`，不共享房屋、房间、立面或形体算法。
- `script/generate_nation_settlements.py` 只是顶层编排器：导入十九个 `build_templates()`、核对公开 ID 与公共接口、做跨国唯一性验证，并导出正式
  NBT。顶层不生成任何建筑形体。

总量为 19 个中心、76 个街道模块、76 栋功能建筑，共 171 个 NBT。中心与街道采用 32×32 街区单元和 8 格道路断面；建筑临街宽度
17—31 格、纵深 24—46 格、高度 14—34 格。

## 兼容 ID 与语境边界

旧文件名仅是资源兼容合同，不自动继承旧语义。例如杜林 `arcade` 现在是公共图书活动空间，萨米 `ritual_house` 是部族集会棚。
`sargon_oasis_town` 也是兼容路径；当前 Sargon builder **只代表马纳特背景组**，不得推广为萨尔贡全国默认聚落。

十九国仍各自绑定本国群系。聚落生成频率、固定中心等世界生成政策由 Java 数据注册负责，不在 Python builder 内重复实现。

## 建筑与可玩性门槛

每个国家文件对自己的形体、空间和用途负责，公共验证只检查可观察的结果：

- 建筑占地有至少 10% 空隙，并具备三档以上顶部高度和非完整四角上层占用，避免实心长方盒；
- 入口、主要房间、声明楼层和楼梯落脚属于同一个三维可站立连通分量，楼梯保留两格净空；
- 房间照明使用受不透明体遮挡的六向方块光传播验证，灯具必须有实体承托；
- 每栋建筑有用途内饰、完成地板上一格的容器，以及本国结构战利品表；
- 不使用混凝土占位；Create 方块只在可解释的动力、运输、泵送、升降或生产链中使用；
- Jigsaw 的 `name / target / pool / final_state` 与 32 格道路接口一致。

## 跨国形体唯一性

顶层除公共验证外，还为 76 栋建筑计算材质无关签名：

```text
template.size + 所有非 air、非 jigsaw 方块的占用坐标集合
```

签名完全忽略国家材质 ID、方块状态和调色板，因此只换墙材或色彩不能冒充独特建筑。目前结果为 `76/76 unique`
。若以后发生碰撞，必须回到对应国家文件独立修改实际轮廓或空间，不得在顶层增加变体参数。

## 正式导出与后续审查

生成器在验证全部 171 个模板后，只清理并重建：

```text
src/main/resources/data/zinecraft/structure/nation_settlements/
```

这些资源已进入 Architecture Pass，但仍需在新世界逐国用 `/place structure` 和 `/locate structure`
检查旋转接缝、地形适配、房间动线、门交互、照明观感、容器高度和 Create 方块状态。完成实机审查前不得标记为 Canonical Final。
