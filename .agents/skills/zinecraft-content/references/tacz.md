# TaCZ 外置枪包适配

先读 `docs/weapon/tacz-adapter.md`。实现位于 `api/weapon/tacz`、`core/weapon/ModTaczWeapons.java` 与客户端
`core/client/weapon/tacz`。不要加入 TaCZ Forge 模组运行依赖，也不要提交官方或第三方枪包。

- 从 NeoForge `FMLPaths.GAMEDIR` 下的 `tacz/` 读取目录或 ZIP；拒绝绝对路径、空段与 `..`。
- 宽松 JSON、索引链和引用资源都通过同一分层虚拟视图解析，后加载包覆盖前层。
- 复用一个枪械 Item 和一个弹药 Item，用 Data Components 区分外部 ID；不能启动后动态注册 Item。
- Action 每次从当前 snapshot 读取服务端数值，客户端无权提交命中、伤害或弹药数量。
- 资源桥接只写固定可重建目录并验证目标路径；单个坏资源只记录警告，不阻止启动。
- Bedrock 枪械骨骼动画由客户端 `TaczWeaponAnimationService` 播放，动作来源必须是服务端广播的 `WeaponPresentation`
  ；基础姿态与动作层之间进行短时混合。
- Lua 动画状态机和完整第三人称人物动画当前未启用。不要在客户端执行枪包脚本或让动画状态决定伤害、命中与弹药；扩展动画必须保持服务端边界。

用被忽略的 `run/tacz` 真枪包验证数量、创造页、射击/换弹/瞄准/模式/检视/近战和专用服务器一致性。先 `runData`，再 `build`
。交付报告确认枪包资产未进入 JAR。
