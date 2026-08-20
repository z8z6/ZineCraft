• 目前武器与技能采用“Catalog 管理、Builder 声明、Runtime 执行”的结构。最重要的是：技能现在分为“技能资料/物品”和“真正可执行的技能效果”两层，它们暂时没有自动绑定。

## 总体调用关系

客户端输入
↓
WeaponInput + C2S Payload
↓
WeaponServerController
↓
WeaponCatalog → WeaponBuilder
↓
WeaponActionBuilder → WeaponAction → WeaponActionRuntime
├─ 近战 / 枪械：直接调用 CombatService
└─ 法杖：CastSkillAction → SkillEffectBuilder → SkillEffect
↓
S2C started/cancelled Payload
↓
WeaponPresentationController
↓
动画 / VFX / 声音

## 一、武器结构

### WeaponCatalog

src/main/java/com/cxxcxx/zinecraft/api/registry/catalog/WeaponCatalog.java:22

武器系统的总目录，负责：

- 注册所有 WeaponBuilder
- 注册所有 WeaponActionBuilder
- 检查重复武器 ID、重复动作 ID
- 检查武器是否引用未注册动作
- 根据 ResourceLocation 查找武器或动作
- 根据玩家手中的 ItemStack 查找对应武器

全局实例是：

Zinecraft.WEAPONS

### WeaponBuilder

src/main/java/com/cxxcxx/zinecraft/api/registry/builder/WeaponBuilder.java:18

现在它既是武器声明 Builder，也是构建后的武器运行时对象，替代了原来的 WeaponDefinition。

它保存：

- 武器 ID
- 对应物品
- 翻译键
- WeaponInput → WeaponActionBuilder
- Action ID → WeaponPresentationBuilder

例如：

new WeaponBuilder(Zinecraft.WEAPONS, "test_sword", item)
.action(WeaponInput.PRIMARY, LIGHT_ATTACK)
.presentation(LIGHT_ATTACK, presentation -> ...)
.build();

构建以后可以通过：

weapon.action(WeaponInput.PRIMARY);
weapon.presentation(actionId);
weapon.asItem();

获取运行时数据。

### WeaponActionBuilder

src/main/java/com/cxxcxx/zinecraft/api/registry/builder/WeaponActionBuilder.java:11

负责创建并注册服务端动作。

new WeaponActionBuilder<>(
Zinecraft.WEAPONS,
"light_attack",
id -> new MeleeAttackAction(...)
).build();

它主要保证：

- 动作拥有稳定 ID
- factory 生成的动作 ID 正确
- 动作不会重复注册
- 构建后才能被武器引用

### WeaponAction

src/main/java/com/cxxcxx/zinecraft/api/weapon/action/WeaponAction.java

描述一种服务端动作，主要有三个职责：

ResourceLocation getId();
boolean canStart(WeaponContext context);
WeaponActionRuntime createRuntime(WeaponContext context);

目前的具体动作包括：

- MeleeAttackAction：服务端近战判定
- FirearmFireAction：服务端射击
- FirearmReloadAction：换弹
- ToggleAimAction：切换瞄准状态
- CastSkillAction：执行技能效果

### WeaponActionRuntime

src/main/java/com/cxxcxx/zinecraft/api/weapon/action/WeaponActionRuntime.java

表示某次正在执行的动作实例。

WeaponAction 是动作定义；WeaponActionRuntime 是某个玩家本次攻击、换弹或施法的运行状态。

它保存或提供：

- 当前 tick
- 当前阶段
- 是否结束
- 每 tick 执行
- 是否允许被新输入中断

### TimedWeaponActionRuntime

src/main/java/com/cxxcxx/zinecraft/api/weapon/action/TimedWeaponActionRuntime.java

标准的定时动作实现，把动作分成：

STARTUP → ACTIVE → RECOVERY → FINISHED

近战命中、开火、施法等实际结算只能发生在服务端指定 tick，而不是由客户端动画关键帧决定。

### WeaponContext

src/main/java/com/cxxcxx/zinecraft/api/weapon/action/WeaponContext.java:11

一次动作的服务端上下文，包含：

- ServerPlayer
- 当前 ItemStack
- 使用的手
- 当前 WeaponBuilder
- ServerLevel

动作通过它读取可信的服务端状态。

### WeaponServerController

src/main/java/com/cxxcxx/zinecraft/api/weapon/WeaponServerController.java:29

武器运行时的总调度器，负责：

1. 接收客户端输入请求。
2. 重新读取玩家手中物品。
3. 从 WeaponCatalog 找到 WeaponBuilder。
4. 根据 WeaponInput 找到动作。
5. 调用 canStart。
6. 创建并保存 WeaponActionRuntime。
7. 每个服务端 tick 推进动作。
8. 切换物品、死亡或退出时取消动作。
9. 广播动作开始与取消 payload。

因此客户端不能直接决定伤害、弹药或命中结果。

## 二、武器表现结构

### WeaponPresentationBuilder

src/main/java/com/cxxcxx/zinecraft/api/registry/builder/WeaponPresentationBuilder.java:13

描述一个动作对应的客户端表现时间线：

- 持续时间
- 玩家动画
- 武器动画
- 指定 tick 播放的 VFX
- 指定 tick 播放的声音

它只接受已注册资源：

.playerAnimation(AnimationBuilder)
.weaponAnimation(AnimationBuilder)
.vfx(VfxBuilder, tick)
.sound(SoundBuilder, tick)

不能传裸 ResourceLocation。

它不处理伤害、治疗、弹药或技能结算。

### 表现资源 Builder/Catalog

分别是：

- AnimationCatalog / AnimationBuilder
- VfxCatalog / VfxBuilder
- SoundCatalog / SoundBuilder

全局目录位于：

Zinecraft.ANIMATIONS
Zinecraft.VFX
Zinecraft.SOUNDS

所有内置武器表现资源集中声明在：

src/main/java/com/cxxcxx/zinecraft/core/registry/ModWeaponPresentation.java:9

这样 ModWeapon、Vanilla 回退后端和 Photon 后端引用的是同一注册项，不重复拼路径。

### WeaponPresentationController

src/client/java/com/cxxcxx/zinecraft/core/client/weapon/WeaponPresentationController.java:23

仅存在于客户端。

收到服务端的动作开始 payload 后，它：

- 找到对应 WeaponBuilder
- 找到动作的 WeaponPresentationBuilder
- 按时间线播放动画
- 在指定 tick 播放声音和 VFX
- 动作取消或结束时停止动画

## 三、技能结构

当前技能分成两套概念。

### 1. SkillBuilder：技能资料与物品

src/main/java/com/cxxcxx/zinecraft/api/registry/builder/SkillBuilder.java:24

用于声明明日方舟技能资料，包括：

- 技能名称
- 干员与职业
- 技力回复类型
- 触发方式
- 初始技力和消耗
- 持续时间
- 技能描述
- 伤害倍率和伤害类型
- Ponder 演示主题
- 客户端 VFX

其中：

SkillBuilder.effect(VfxBuilder)

这里的 effect 指客户端特效，不是服务端玩法效果。

### SkillCatalog

src/main/java/com/cxxcxx/zinecraft/api/registry/catalog/SkillCatalog.java:17

负责：

- 校验技能资料完整性
- 注册对应 SkillItem
- 生成技能名称和 tooltip 翻译
- 提供 Ponder 展示数据
- 校验技能至少有一个 VFX

全局实例：

Zinecraft.SKILLS

### SkillItem

src/main/java/com/cxxcxx/zinecraft/api/skill/SkillItem.java

是技能资料对应的 Minecraft 物品。

目前主要用于：

- 展示技能资料
- tooltip
- 创造模式内容
- Ponder 教程入口

它目前不会自动执行 SkillEffect。

### ModSkill

src/main/java/com/cxxcxx/zinecraft/core/skill/ModSkill.java:10

集中声明正式技能资料，例如：

- 真银斩
- 火山
- 钙质化
- 圣域
- 狐火渺然
- 狼群

这些当前属于资料、物品和演示层。

## 四、可执行技能效果

### SkillEffect

src/main/java/com/cxxcxx/zinecraft/api/skill/SkillEffect.java:9

服务端玩法效果接口：

boolean canCast(SkillCastContext context);
void cast(SkillCastContext context);
List<CombatDamageProfile> damageProfiles();

它负责真正的：

- 施法条件
- 伤害
- 治疗
- 命中查询
- 服务端状态修改

### SkillEffectBuilder

src/main/java/com/cxxcxx/zinecraft/api/registry/builder/SkillEffectBuilder.java:15

负责创建和注册 SkillEffect。

它自动生成：

zinecraft:skill/<path>

并提供：

effect.getId();
effect.canCast(context);
effect.cast(context);
effect.damageProfiles();

cast 会再次调用 canCast，所以动作开始和真正结算时都会校验服务端状态。

### SkillEffectCatalog

src/main/java/com/cxxcxx/zinecraft/api/registry/catalog/SkillEffectCatalog.java:17

负责：

- 校验技能效果 ID
- 防止重复注册
- 保存构建后的效果
- 根据 ID 查询效果

全局实例：

Zinecraft.SKILL_EFFECTS

### ModWeaponSkillEffects

src/main/java/com/cxxcxx/zinecraft/core/registry/ModWeaponSkillEffects.java:19

集中声明武器使用的可执行技能效果：

- ARCANE_BOLT：服务端 hitscan，造成 8 点法术伤害
- MENDING_LIGHT：为施法者恢复生命

这里保存的是 SkillEffectBuilder，不再保存裸 ID。

### CastSkillAction

src/main/java/com/cxxcxx/zinecraft/api/weapon/action/staff/CastSkillAction.java:15

连接武器动作和技能效果：

法杖输入
→ CastSkillAction
→ 到达施法 tick
→ SkillEffectBuilder.cast
→ SkillEffect
→ CombatService

它让技能效果遵循武器动作的前摇、结算 tick 和后摇。

## 五、目前需要特别注意的边界

SkillBuilder 和 SkillEffectBuilder 不是同一个东西：

类型 用途 是否执行玩法                                                                                           
━━━━━━━━━━━━━━━━━━━━━━━━━━━ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ━━━━━━━━━━━━━━
SkillBuilder 技能资料、物品、翻译、Ponder、VFX 否
─────────────────────────── ─────────────────────────────────── ──────────────
SkillEffectBuilder 服务端伤害、治疗、控制等实际效果 是
─────────────────────────── ─────────────────────────────────── ──────────────
WeaponPresentationBuilder 动画、声音、VFX 时间线 否
─────────────────────────── ─────────────────────────────────── ──────────────
WeaponActionBuilder 服务端武器动作 是

此外，src/main/java/com/cxxcxx/zinecraft/core/skill/SkillRuntime.java 和 TestRapidFireSkill 是一条独立的射击事件实验路径，目前没有接入
SkillCatalog 或
SkillEffectCatalog。

─ Worked for 1m 03s
────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────

› Ask Codex to do anything
 
