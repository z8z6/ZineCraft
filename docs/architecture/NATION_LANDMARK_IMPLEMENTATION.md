# 十九国地标结构实现

状态：`ARCHITECTURE PASS / IN-GAME REVIEW REQUIRED`
更新：2026-08-16

## 实现范围

十九国 38 个公开地标 ID 全部保留，旧单 NBT 小型图标结构已替换为六段式 Jigsaw：

```text
foundation → core → facade → roof → annex → surrounding
```

每座地标包含 6 个 NBT，共 228 个模块。确定性拼装包围范围约 `136×64×72`，达到 L 级；实际模块文件位于
`data/zinecraft/structure/nation_landmarks/<public_id>/`。

旧名称缺少证据时仅作为技术兼容 ID。建筑体量、房间和环境按对应国家 `REDESIGN.md` 转译，不继续复刻旧名称暗示的错误形体。

38 座地标各有一组显式 `PROGRAM_FEATURES`，每组包含三个用途特征。`public_program`
参与稳定布局计算；三个特征全部进入核心体块，并分别驱动立面入口、附属用途设施和周边环境。生成器提供机械、水利、物流、防御、公共空间与空间结构六类构造语法，同时强制
38 份最终特征签名互不重复，避免重新退化为同一建筑换材质。

## 注册

`NationLandmarks.modularLandmark` 保留原结构 key、群系、环形唯一放置与 `/locate` ID，同时为每座地标注册六个唯一模板池。Jigsaw
深度为 7，`maxDistanceFromCenter=112`；128 会因 Minecraft 1.21.1 将 `BEARD_THIN` 地形适配范围计入上限而被数据编解码拒绝。

## 可玩性

- 核心和附属空间包含可通行房间、Create 黄铜门、实体楼梯及两格头部净空。
- 核心顶部的连续背板梯跨过 Jigsaw 接口和屋顶双层板，连接带落脚平台、三格高出口通道和护栏的可用屋顶空间。
- 主照明连接顶棚、墙或结构梁；容器位于完成地板上一格。
- 容器引用 `zinecraft:chests/nation/<country>_structure`。
- surrounding 模块提供道路、广场、栈桥、场地或国家基础设施语境，而不是孤立地标落在空地上。

## 自动验证

生成器检查 38 个公开 ID、每座六模块、Jigsaw `target/name/pool`
配对、拼装范围、入口与房间连通、门、楼梯净空、灯具承托、容器高度和国家战利品路径；还检查四组水平接口两侧的脚部与两格头部净空，以及垂直接口、舱口、连续梯背板、屋顶落脚点和护栏。屋顶动线会把
core 与 roof 按实际世界高度组装，替换两个 Jigsaw 的 `final_state`
，再以两格人体三维搜索证明内部顶层能够到达屋顶平台。用途验证要求每座地标恰有三个特征、核心/立面/附属/周边四模块均落实功能，并保证
38 份生成签名唯一。数据生成进一步验证 228 个模板池与 38 个世界结构注册。

仍需在新世界逐国验证 `/place structure`、`/locate structure`、地形适配、旋转后接缝、Create 方块状态和远中近距离轮廓；完成前不标记为
Canonical Final。
