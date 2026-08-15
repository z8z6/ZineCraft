# Weapon Runtime

先读 `docs/weapon/weapon.md`，再检查 `api/weapon`、`core/weapon/ModWeapons.java` 与 `src/client/java/.../weapon`。

调用方向：客户端输入 → C2S `WeaponInput` → 服务端解析 `WeaponDefinition` → `WeaponAction`/Runtime → 服务端结算 → S2C
started/cancelled → 客户端 `WeaponPresentation`。

- 弹药、瞄准、模式与枪机状态使用 ItemStack Data Components。
- 动作 tick/phase 属于实体侧 Runtime，不写入 ItemStack。
- C2S 不携带可信伤害、目标或弹药；服务端重新读取当前物品和玩家状态。
- 近战使用 `MeleeHitboxService`，枪械使用服务端 `HitscanService`，法杖通过 `SkillService`。
- VFX、声音和动画 cue 只属于表现，不写进 gameplay Action。
- 通用端不得 import 客户端 API；无表现资源时 gameplay 仍须正常。

新增动作测试取消、并发拒绝、服务端单次结算、弹药扣除与网络广播。依次运行 `test`、`runData`、`build`，再在客户端实测输入与表现。
