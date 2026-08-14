# TaCZ 枪械资源适配

Zinecraft 在 Fabric 1.21.1 中直接读取用户安装的 TaCZ 1.1.x 枪包，不要求把 TaCZ 本体作为运行依赖，也不把第三方资产打包进
Zinecraft。官方 TaCZ 当前面向 Forge 1.20.1，因此这里实现的是枪包格式兼容层，不是加载 TaCZ 模组 jar。

## 安装

将枪包目录或 `.zip` 放到游戏目录的 `tacz/` 中：

```text
<gameDir>/tacz/example_pack/gunpack.meta.json
<gameDir>/tacz/example_pack/assets/...
<gameDir>/tacz/example_pack/data/...
```

压缩包的根目录必须直接包含 `gunpack.meta.json`。单人游戏只需安装一次；专用服务器和每个客户端应安装相同版本的枪包。修改枪包后重启游戏，避免客户端表现与服务端数值不一致。

加载成功后，每个外部枪械和弹药会以带 TaCZ ID Data Component 的 `zinecraft:tacz_gun` / `zinecraft:tacz_ammunition`
物品栈出现在 Zinecraft 创造模式页。系统不会为每把枪动态注册新 Item，因而不会破坏客户端与服务端的注册表同步。

## 已适配内容

- 目录与 ZIP 枪包、`gunpack.meta.json`、`gunpack_info.json`，以及带注释的宽松 JSON。
- `index/guns`、`data/guns` 与 `index/ammo`，包括弹容、弹药 ID、枪机类型、伤害、弹丸数、RPM、连发参数、射程、瞄准、近战和换弹时间。
- 服务端权威的射击、命中、伤害、弹药扣除与装填；枪械实例状态保存在 ItemStack Data Component。
- `display/guns` / `display/ammo` 中的模型、UV/槽位纹理、动画和声音引用。
- Bedrock `minecraft:geometry` 骨骼、方块、逐面 UV、关键帧位移/旋转/缩放、线性与 Catmull-Rom 插值、动画声音关键帧。
- TaCZ 客户端 Lua 状态机：安全标准库、`require` 模块、多轨覆盖/叠加、循环/停帧/暂停、进度调整、过渡，以及官方脚本所需的枪械和移动上下文。
- PlayerAnimator 格式第三人称动作：上下半身持枪、行走、奔跑、蹲行、瞄准、射击、换弹、近战、骑乘和匍匐；不要求安装 Forge TaCZ 或旧
  PlayerAnimator。
- `lang/*.json` 与全部 `tacz_sounds/*.ogg`。客户端会在 `resourcepacks/zinecraft_tacz_bridge/` 生成可重建桥接包，原始资产内容不被修改，并自动启用。
- 输入：左键射击、右键瞄准、R 换弹、B 切换开火模式、X 检视、V 枪械近战；手动枪机在下一次左键时执行拉栓。

“完整支持”在这里指 TaCZ 1.1.x 枪包的枪械显示资源、客户端动画状态机和人物动作格式，不表示 Fabric 端重现整个 TaCZ Forge
API。当前服务端仍使用 Zinecraft 的权威 hitscan Runtime；TaCZ 的服务端 gun-logic
Lua、配件工作台/改模、真实弹道实体、爆炸/燃烧、热量与蓄力数值逻辑仍是后续独立兼容层。枪包中的这些字段不会被客户端脚本授予伤害权限。

## 覆盖与安全规则

- 枪包按文件名排序，后加载的包覆盖相同虚拟路径；同一枪械 ID 的最终索引使用覆盖后的资源。
- 所有目录和 ZIP 条目都经过相对路径校验，`..` 和绝对路径不会被读取或写入桥接目录。
- 服务端不信任客户端的伤害、命中或弹药数；客户端只播放服务端广播的表现时间线。
- Lua 环境不加载 IO、OS、协程或 Java 反射库；脚本只能调用显式动画上下文。
- 桥接包只复制语言和 OGG 到本机可重建缓存；模型、纹理、动画、脚本和人物动作直接从原枪包读取。

## 验证基线

官方默认枪包的开发校验结果：1 个包、54 把枪、24 种弹药、73 个模型、54 个枪械动画文件、3 个人物动画文件、54 个 Lua 状态机均成功加载，失败数为
0；声音桥接生成 1344 个 OGG 和 1344 个声音定义。该枪包仅位于被忽略的 `run/tacz`，没有进入发布 jar。

## 授权

TaCZ 代码采用 GPL-3.0，官方内置资产标注为 CC BY-NC-ND 4.0。Zinecraft 只实现独立的格式读取与运行时桥接，不复制 TaCZ
代码，仓库也不提交官方枪包资产。使用第三方枪包时，使用者仍须遵守该枪包自己的作者、署名、非商业和禁止演绎等条款；桥接不会改变或替代其许可证。

官方资料：<https://github.com/MCModderAnchor/TACZ>
