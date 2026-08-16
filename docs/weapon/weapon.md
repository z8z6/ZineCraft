# Weapon Runtime

Zinecraft 的 Weapon Runtime 是服务端权威的 Java 动作系统，支持项目原生近战、枪械和法杖。客户端输入只发送动作意图；服务端解析手中物品、校验状态、执行时间线并广播表现。TaCZ
枪械由 TaCZ 自身运行时结算，并通过独立后端事件适配接入技能系统。

## 核心模型

- `WeaponDefinition`：武器 ID、输入到动作 ID 的映射、表现和元数据。
- `WeaponAction`：服务端动作接口；具体实现负责校验、开始、tick、结算和取消。
- `WeaponRegistry`：动作、武器定义和动态物品解析器。
- `WeaponServerController`：处理玩家请求、当前动作状态和网络广播。
- `TimedWeaponActionRuntime`：用 `TickRange` 描述 wind-up、active、recovery 等时间段。
- `WeaponPresentation`：玩家/武器动画 ID、VFX、声音与持续时间；只描述表现，不产生玩法状态。
- `WeaponContext`：服务端玩家、物品栈、手和已解析武器定义。

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
4. 在 `WeaponRegistry` 注册动作。
5. 在武器定义中绑定 `WeaponInput → actionId`。
6. 可选地添加 `WeaponPresentation`；无客户端表现时玩法仍必须正确。

近战命中使用服务端 hitbox/raycast，并避免重复叠加武器基础伤害。枪械命中使用服务端 hitscan；法杖技能调用 `SkillService`
或明确的服务端效果接口。

## 新增静态武器

```java
ResourceLocation actionId = Zinecraft.INSTANCE.getREGISTRAR().id("example_attack");
WeaponAction action = new MeleeAttackAction(actionId, 7, 20, 7.0F, 3.25, 100.0);
Zinecraft.INSTANCE.getWEAPONS().registerAction(action);

WeaponDefinition definition = new WeaponDefinition(
    Zinecraft.INSTANCE.getREGISTRAR().id("example_sword"),
    Map.of(WeaponInput.PRIMARY, actionId),
    Map.of(),
    new WeaponMetadata("item.zinecraft.example_sword")
);
```

项目原生物品必须通过静态绑定或解析器返回该定义。TaCZ 物品、枪械身份和枪包数据由 TaCZ 管理，不注册为 Zinecraft
`WeaponDefinition`。

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
