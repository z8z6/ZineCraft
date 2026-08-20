# Weapon Runtime

Zinecraft 的 Weapon Runtime 是服务端权威的 Java 动作系统，支持项目原生近战、枪械和法杖。客户端输入只发送动作意图；服务端解析手中物品、校验状态、执行时间线并广播表现。TaCZ
枪械由 TaCZ 自身运行时结算，并通过独立后端事件适配接入技能系统。

## 核心模型

- `WeaponAction`：服务端动作接口；具体实现负责校验、开始、tick、结算和取消。
- `WeaponActionBuilder`：通过稳定路径和 factory 构建、校验并登记服务端动作。
- `AnimationCatalog` / `AnimationBuilder`：区分玩家与武器动画并登记稳定的客户端动画 ID。
- `VfxCatalog` / `VfxBuilder`：登记可由武器动作和技能复用的客户端特效 ID。
- `SoundCatalog` / `SoundBuilder`：登记声音事件、字幕翻译和武器声音 cue 使用的稳定 ID。
- `WeaponCatalog`：动作、构建后的武器和物品解析器。
- `WeaponBuilder`：武器的唯一声明和运行时对象，包含 ID、物品、翻译键、输入、服务端动作和表现映射。
- `WeaponPresentationBuilder`：构建后直接作为只读表现时间线，包含玩家/武器动画、VFX、声音与持续时间。
- `WeaponServerController`：处理玩家请求、当前动作状态和网络广播。
- `TimedWeaponActionRuntime`：用 `TickRange` 描述 wind-up、active、recovery 等时间段。
- `WeaponContext`：服务端玩家、物品栈、手和已解析的 `WeaponBuilder`。

持久化弹药、瞄准、开火模式和枪机状态使用 Minecraft 1.21.1 Data Components 存在 `ItemStack` 上，不依赖客户端单例或仅内存状态。

## 输入和网络

客户端把主攻击、副攻击、换弹、开火模式、检视、近战和枪机输入映射到 `WeaponInput`。请求 payload
只携带输入和必要上下文。服务端必须重新读取玩家、手中物品和当前动作，不信任客户端伤害、目标、弹药或冷却。

动作开始、取消和表现时间线由服务端 payload 广播。客户端 `WeaponPresentationController` 调用动画、声音和 VFX
服务；任何客户端关键帧都不得直接扣弹、造成伤害或推进任务。

## 新增动作

1. 为动作选择稳定的 `ResourceLocation`。
2. 实现 `WeaponAction`，在服务端校验输入与物品状态。
3. 明确定义动作持续时间和每个结算 tick/range。
4. 使用 `WeaponActionBuilder` 构建并向 `WeaponCatalog` 登记动作。
5. 使用 `WeaponBuilder.action` 绑定 `WeaponInput → WeaponActionBuilder`。
6. 可选地使用 `WeaponBuilder.presentation` 配置表现时间线，并通过 `VfxBuilder` 引用特效；无客户端表现时玩法仍必须正确。

近战命中使用服务端 hitbox/raycast，并避免重复叠加武器基础伤害。枪械命中使用服务端 hitscan；法杖技能通过
`SkillEffectBuilder` 引用 `SkillEffectCatalog` 中已登记的服务端效果。

所有原生武器伤害和治疗必须进入 `CombatService`，由统一攻击力、物理/法术/真实伤害、穿透和治疗公式结算；不得直接调用实体
`hurt` 绕开藏品修正。详细公式与新增武器约束见
[战斗数值机制](../combat/combat-stats.md)。TaCZ 保留自己的弹种、爆头与防具结算，但命中前会通过公开事件应用相同的攻击力藏品增益。

## 新增静态武器

```java
AnimationBuilder playerAttack = new AnimationBuilder(
    Zinecraft.ANIMATIONS, AnimationBuilder.Target.PLAYER, "example_attack"
).build();
VfxBuilder slash = new VfxBuilder(Zinecraft.VFX, "weapon/example_slash").build();
SoundBuilder swing = new SoundBuilder(
    Zinecraft.SOUNDS, "weapon/example_swing", "示例武器挥动"
).enUs("Example Weapon Swing").build();
WeaponActionBuilder<MeleeAttackAction> action = new WeaponActionBuilder<>(
    Zinecraft.WEAPONS,
    "example_attack",
    id -> new MeleeAttackAction(
        id, 7, 20,
        CombatDamageProfile.flat(7.0, CombatDamageType.PHYSICAL),
        3.25, 100.0
    )
).build();
WeaponBuilder weapon = new WeaponBuilder(
    Zinecraft.WEAPONS, "example_sword", EXAMPLE_SWORD_ITEM
).action(WeaponInput.PRIMARY, action)
    .presentation(action, presentation -> presentation
        .duration(20)
        .playerAnimation(playerAttack)
        .vfx(slash, 7)
        .sound(swing, 7))
    .build();
```

`WeaponPresentationBuilder` 只接受已构建的 `AnimationBuilder`、`VfxBuilder` 和 `SoundBuilder`，不接受裸
`ResourceLocation`。项目内置武器的这些声明集中在 `ModWeaponPresentation`，武器和客户端播放后端共同引用同一注册项。

`WeaponCatalog` 会从构建后的条目解析物品，不需要在 common setup 二次绑定；特殊外部物品使用
`registerResolver` 返回一个已构建的 `WeaponBuilder`。TaCZ 物品、枪械身份和枪包数据由 TaCZ 管理，不注册为 Zinecraft
武器。

## 客户端边界

- 通用端不得 import `net.minecraft.client`。
- renderer、按键、Ponder、声音后端、粒子后端和资源桥接放在 `src/client/java`。
- 没有表现资源时使用 no-op 或原版后端，不能阻止服务端玩法执行。
- TaCZ 的渲染、ADS、后坐力与枪械机械动画由 TaCZ 控制；Zinecraft 不注入自定义枪械
  renderer。详见 [tacz-adapter.md](tacz-adapter.md)。

### Photon 武器特效

静态武器通过三个稳定 cue 使用 Photon 2.2 Java FX：

- `zinecraft:weapon/sword_slash`：测试剑轻击，在第 4 tick 播放前向青色剑气。
- `zinecraft:weapon/explosion`：测试步枪命中，在第 3 tick 播放暖色爆炸核心与烟尘。
- `zinecraft:weapon/healing`：测试法杖副攻击，在第 10 tick 播放环绕上升的治疗粒子。

效果定义集中在客户端 `compat/photon/PhotonWeaponEffects`，由 Photon 的 `FX`、`ParticleEmitter`
和 `EntityEffectExecutor` 创建与播放，不参与伤害、命中或治疗结算。Photon 不是发布时的强制依赖：
表现控制器通过反射边界加载 Photon 后端，缺少 Photon、后端链接失败或单次播放异常时均回退到原版粒子。
若后续改用 Photon 编辑器制作资源，官方 2.2 格式应导出到
`assets/zinecraft/fx/<name>.fx`，并以 `zinecraft:<name>` 传给 `FXHelper.getFX`；`.fx` 是压缩 NBT，
不应手写或改成 JSON。

## 验证

```powershell
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
.\gradlew.bat runClient
```

至少测试：非法请求被拒绝、动作不能并发覆盖、切换物品会取消动作、弹药只扣一次、服务端命中一致、取消 payload 正确，以及无客户端表现资源时不崩溃。
