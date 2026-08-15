# TaCZ 枪包格式适配

Zinecraft 在 NeoForge 1.21.1 中读取 TaCZ 1.1.x 外置枪包，不加载 TaCZ 模组 JAR，也不把第三方枪包资产打包进 Zinecraft。服务端玩法由
Zinecraft Weapon Runtime 实现。

## 安装

将枪包目录或 ZIP 放在游戏目录的 `tacz/`：

```text
<gameDir>/tacz/example_pack/gunpack.meta.json
<gameDir>/tacz/example_pack/assets/...
<gameDir>/tacz/example_pack/data/...
```

ZIP 根目录必须直接包含 `gunpack.meta.json`。专用服务器和每个客户端应安装相同版本的枪包。

枪械和弹药使用动态 Data Component 标识的 `zinecraft:tacz_gun` 与 `zinecraft:tacz_ammunition` 物品栈，不为每把枪注册新
Item，因而不破坏客户端/服务端注册表同步。它们位于独立 TaCZ 创造模式页。

## 当前支持

- 目录和 ZIP 枪包、宽松 JSON、枪包元数据、枪械与弹药索引。
- 弹容、弹药类型、枪机、伤害、弹丸数、RPM、连发、射程、瞄准、近战和换弹参数。
- 服务端权威的射击、hitscan 命中、伤害、弹药扣除、装填、开火模式与物品状态。
- Bedrock geometry、纹理槽、语言与 OGG 声音资源读取。
- 客户端可重建资源桥接包 `resourcepacks/zinecraft_tacz_bridge/`。
- 左键射击、右键瞄准、R 换弹、B 开火模式、X 检视、V 近战和手动枪机动作。

## 暂未启用

TaCZ Lua 动画状态机、完整第一人称骨骼时间线和第三人称人物动画模块目前已从运行路径移除。枪包中的动画与脚本路径可以被索引，但不会执行脚本，也不会授予任何客户端伤害权限。当前客户端以静态/基础
Bedrock 模型和服务端广播的表现状态为准。

未实现的 TaCZ 模组功能还包括配件工作台、Forge API 互操作、真实弹道实体、复杂爆炸/燃烧、热量和蓄力脚本逻辑。

## 安全与覆盖

- 枪包按稳定顺序加载，后加载资源覆盖同虚拟路径。
- 所有目录和 ZIP 条目经过相对路径校验，拒绝 `..` 与绝对路径。
- 服务端不信任客户端提交的伤害、命中或弹药数量。
- 桥接包只写入可重建的本地资源缓存，不修改原枪包。

## 验证与授权

启动日志会报告枪包、枪械和弹药数量。默认开发基线为 1 个包、54 把枪和 24 种弹药；枪包位于被忽略的 `run/tacz`，不进入发布 JAR。

TaCZ 代码和官方资产分别受其上游许可证约束。Zinecraft
只实现独立格式读取与运行时桥接；使用者仍需遵守每个枪包的作者、署名和分发条款。官方项目：<https://github.com/MCModderAnchor/TACZ>
