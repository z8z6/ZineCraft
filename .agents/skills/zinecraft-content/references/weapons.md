# 武器 Runtime：剑、枪械与法杖

先读 `docs/weapon/weapon.md`，再检查 `api/weapon`、`core/weapon/ModWeapons.kt` 和客户端 `core/client/weapon` 的当前实现。复用已有
Definition、Action、Controller 和三个网络载荷；不要为一种武器复制控制器或网络层。

## 架构边界

保持以下调用方向：

```text
Item -> WeaponDefinition -> WeaponInput binding -> WeaponAction -> WeaponActionRuntime
                                                     | server gameplay
WeaponActionStarted -> WeaponPresentation             | client animation/VFX/sound
```

- `WeaponDefinition` 只保存 ID、输入绑定、表现引用和静态元数据。
- 一次动作的 tick、phase 和 finished 属于实体侧 Runtime，不写入 ItemStack。
- 弹药、瞄准、模式和附件等物品实例状态使用 `WeaponStateComponents`，不得放在 Item singleton 字段。
- C2S 只提交 `WeaponInput`；服务端重新读取当前物品、解析 Definition、校验 Action 并执行 gameplay tick。
- S2C 只同步开始/取消和服务端开始时间；客户端从 `WeaponPresentation` 重放定时 cue，不逐 tick 同步。
- 通用端不得 import `net.minecraft.client` 或 Fabric client API。动画、粒子和声音后端只放在 `src/client`。

## 声明武器

在 `core/weapon/ModWeapons.kt` 中通过 `Zinecraft.ITEMS` 注册物品，通过 `Zinecraft.WEAPONS` 注册 Action 与 Definition：

```kotlin
val FIRE = FirearmFireAction(FIRE_ID, fireTick = 2, durationTicks = 10, damage = 6f, range = 48.0)

val RIFLE = WeaponDefinition(
  id = Zinecraft.REGISTRAR.id("test_rifle"),
  actions = mapOf(WeaponInput.PRIMARY to FIRE_ID),
  presentations = mapOf(FIRE_ID to WeaponPresentation(/* animation 与定时 cue */)),
  metadata = WeaponMetadata(RIFLE_ITEM.item.descriptionId)
)

init {
  Zinecraft.WEAPONS.registerAction(FIRE)
  Zinecraft.WEAPONS.register(RIFLE_ITEM.item, RIFLE)
}
```

新增顶层武器对象时，仍需在主初始化和数据生成入口触发；追加到现有 `ModWeapons` 不需要新入口。普通名称和模型由物品目录生成。没有正式美术时可用无纹理槽的
`ModelTemplate` 继承原版模型，并在交付说明中标为占位资源。

## 实现 Action

- 单段启动/生效/后摇动作继承 `TimedWeaponActionRuntime`，只在约定 gameplay tick 执行效果。
- 近战通过 `MeleeHitboxService`；枪械通过服务端 `HitscanService` 或以后新增的服务端 Projectile Action。
- 枪械射击先检查并扣除 `WeaponStateComponents.AMMO`；装填只在服务端补充弹药并消耗弹药物品。
- 右键语义由 `ActionWeaponItem` 转发 `SECONDARY`；左键与装填键只发送语义请求。保留方块破坏等原版行为。
- 枪口火焰、轨迹、命中特效和声音写成 `TimedWeaponVfx` / `TimedWeaponSound`，不要写进 gameplay Action。

## 法杖与技能

具体法术实现注册到 `Zinecraft.SKILL_SERVICE`。法杖绑定 `CastSkillAction`，由 Action 控制施法时间，由 `SkillEffect` 执行服务端效果：

```kotlin
Zinecraft.SKILL_SERVICE.register(SKILL_ID, object : SkillEffect {
  override fun canCast(context: SkillCastContext) = context.player.isAlive
  override fun cast(context: SkillCastContext) { /* 服务端技能效果 */ }
})

val CAST = CastSkillAction(ACTION_ID, SKILL_ID, Zinecraft.SKILL_SERVICE, castTick = 5, durationTicks = 18)
```

不要在 Staff Item 内实现伤害、治疗、mana、粒子或冷却。若技能需要持久 mana，再新增受校验且网络同步的 Data
Component；没有实际需求时不要预建。

## 检查与验证

1. 切换物品、死亡或掉线是否取消 Runtime；动作期间是否拒绝重复开始。
2. 命中、伤害、弹药消耗、装填消耗和技能效果是否只在服务端发生。
3. 客户端是否仅播放 started timeline；通用源码是否不存在 client import。
4. `runDatagen` 后检查武器名称和模型；再单独运行 `build`，确认 `spotlessCheck`、client/main 分包和 remap jar。
5. 枪械至少实测空仓拒绝射击、部分装填、无弹药拒绝装填；法杖至少实测技能条件与命中/治疗结果。

当前真实示例：`MeleeAttackAction`、`FirearmActions`、`CastSkillAction`、`ModWeaponSkillEffects`、
`WeaponPresentationController`。
