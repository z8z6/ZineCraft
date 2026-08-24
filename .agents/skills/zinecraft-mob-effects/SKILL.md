---
name: zinecraft-mob-effects
description: Add or revise server-authoritative Zinecraft combat statuses using MobEffectBuilder, MobEffectCatalog, duration-aware application, and gameplay enforcement. Use for harmful or beneficial entity status mechanics, not client-only VFX.
---

# Zinecraft 战斗状态

原版 `MobEffect` 只承载状态实例和属性修饰符；伤害、控制、叠加规则与持续时间必须由服务端战斗入口明确执行。

## 当前入口

- `MobEffectBuilder`、`MobEffectCatalog`、`Zinecraft.MOB_EFFECTS`
- `ModMobEffect`：状态声明
- `CombatStatusService`：敌我持续时间换算、状态施加与叠加规则
- `CollectibleEffectRuntime`：禁止攻击等服务端事件约束
- `docs/combat/combat-stats.md`：现有状态语义

## 修改流程

1. 先从 PRTS 或项目资料核对状态名称与玩法语义；无法确认的数值或交互不要自行补全。可见粒子、声音和动画属于 `$zinecraft-effects`，不放入服务端状态实现。
2. 在 `ModMobEffect` 用 `MobEffectBuilder(Zinecraft.MOB_EFFECTS, ...)` 声明稳定 snake_case ID、双语名、类别和 RGB；所有 `attributeModifier(...)` 必须在 `build()` 前配置。不要在注册类中另建 `DeferredRegister`、`MobEffect` 子类或手工登记翻译。
3. `MobEffectCatalog` 独占延迟注册、重复 ID 校验、翻译登记和只读 entries。调用原版 API 时使用 `ModMobEffect.X.holder()`；Builder 本身不是 `Holder<MobEffect>`。
4. 一次性敌方状态通过 `CombatStatusService.applyToEnemy(...)` 施加，我方负面状态通过 `applyToFriendly(...)` 施加，使 `enemyStatusDurationBonus` 与 `friendlyStatusDurationReduction` 只作用于对应异常状态。只有持续 tick 已在上层结算时才使用 `apply(...)`。
5. 属性修饰符适合移速、攻速和项目映射到原版属性的数值。冻结、麻痹、晕眩等硬控制不能只靠 `ATTACK_SPEED`；同步更新 `CombatStatusService` 的能力判定及服务端伤害/行动入口。避免仅客户端取消或每 tick 重建效果。
6. 明确叠加、替换、解除和零持续时间语义。现有规则是第二次寒冷移除寒冷并转为冻结；扩展状态时检查所有 `hasEffect/removeEffect` 调用都使用 Builder 的 `holder()`。

## 验证

运行 `./gradlew.bat runData` 生成双语资源，并核对 `src/generated/resources/assets/zinecraft/lang/` 中的效果键。代码变更至少执行 `compileJava`；需要发布或用户明确要求时再执行对应构建。实际控制状态应在服务端验证移动、攻击、叠加、解除、敌我持续时间和重连行为。