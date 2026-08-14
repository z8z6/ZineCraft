# 任务：为 Fabric 1.21.1 Kotlin 模组设计并实现 Weapon Framework 第一阶段

你正在一个 Minecraft Fabric 1.21.1 模组项目中工作。

项目主要使用 Kotlin，目标运行环境为 Minecraft 1.21.1 / Fabric / Java 21。

本项目未来需要支持：

* 剑 / 大剑等近战武器
* 枪械
* 法杖
* 混合武器，例如枪剑
* 武器攻击动画
* 玩家攻击/施法/瞄准动画
* 武器自身骨骼动画
* Particle / Trail / Muzzle Flash / Impact 等特效
* 技能系统
* 联机游戏

当前阶段不要实现完整系统。

我们的目标是先建立一个结构稳定、规模克制的 Weapon Runtime，并通过“一把测试剑”完成纵向验证。

---

# 1. 首先分析现有项目

在修改代码之前：

1. 阅读当前项目目录。
2. 阅读 `build.gradle(.kts)`、`gradle.properties`、`fabric.mod.json`。
3. 确认：

    * Minecraft 版本
    * Fabric Loader
    * Fabric API
    * Yarn/Mojmap mappings
    * Kotlin 版本
    * 是否已有 GeckoLib
    * 是否已有 Player Animation Library / PAL 或其他玩家动画库
4. 搜索已有：

    * Item 注册
    * Networking
    * Client initializer
    * Data Components
    * animation
    * particle
    * combat
    * mixin
5. 尽可能复用现有代码，不要重新建立一套重复框架。

不要假设 API 名称。

必须以当前项目实际依赖和 Minecraft 1.21.1 API 为准。

如果需要增加动画库，先确认存在 Fabric 1.21.1 可用版本，不要猜测依赖版本。

---

# 2. 架构原则

不要建立：

* ECS
* DI Framework
* 自定义 EventBus
* scripting engine
* plugin framework
* 跨 Forge/NeoForge/Fabric 的完整兼容层
* 通用 Minecraft API wrapper
* 巨大的 JSON 数据系统
* Visual Effect Graph
* Gameplay Ability System 克隆
* 大量只有一个实现的 interface
* 为“未来可能需要”创建的抽象

代码应解决当前确定存在的问题。

优先采用：

* composition
* 小型 interface
* immutable definition
* runtime state
* server authoritative gameplay
* client presentation

不要让 Sword / Gun / Staff 形成三套完全独立的系统。

不要依靠复杂继承树：

`Weapon -> Sword -> GreatSword -> MagicGreatSword -> FireMagicGreatSword`

武器能力应通过 Action 组合。

---

# 3. Weapon 核心模型

设计：

`WeaponDefinition`

它表示一种武器的静态定义。

至少包含：

* weapon id
* action bindings
* animation references
* 基础 weapon metadata

不要在 WeaponDefinition 中保存运行状态。

示意：

```kotlin
data class WeaponDefinition(
  val id: Identifier,
  val actions: Map<WeaponInput, Identifier>
)
```

具体结构可以根据现有项目调整。

WeaponDefinition 第一阶段可以由 Kotlin 注册。

暂时不要实现完整 JSON datapack loader。

但是设计时不要阻止未来使用 Codec/JSON 加载 Definition。

---

# 4. WeaponInstanceState

区分：

STATIC DEFINITION

和：

ITEM INSTANCE STATE。

以后枪械需要：

* ammo
* selected fire mode
* attachments
* weapon variant

这些状态属于具体 ItemStack。

Minecraft 1.21.1 下优先使用 Data Components 保存需要持久化的 ItemStack 数据。

不要把状态放入 Item singleton，例如禁止：

```kotlin
class GunItem {
  var ammo: Int
}
```

第一阶段测试剑如果没有 instance state，则不要为了它制造没有用途的数据结构。

---

# 5. WeaponInput

建立少量语义输入：

```text
PRIMARY
SECONDARY
RELOAD
SPECIAL
```

如果当前项目已有 input abstraction，优先复用。

WeaponInput 不直接表示具体动作。

例如：

Sword:
PRIMARY -> light_attack
SECONDARY -> block
SPECIAL -> heavy_attack

Gun:
PRIMARY -> fire
SECONDARY -> aim
RELOAD -> reload

Staff:
PRIMARY -> cast_primary
SECONDARY -> cast_secondary
SPECIAL -> charge

````

因此 WeaponController 的职责是：

```text
Input
  ↓
current weapon
  ↓
WeaponDefinition
  ↓
resolve Action
  ↓
request action
````

---

# 6. WeaponAction

建立 WeaponAction 概念。

目标：

Sword、Gun、Staff 最终共享同一 Action Runtime。

建议核心接口保持非常小，例如：

```kotlin
interface WeaponAction {
  val id: Identifier

  fun canStart(context: WeaponContext): Boolean

  fun createRuntime(context: WeaponContext): WeaponActionRuntime
}
```

如果有更符合现有项目的设计，可以调整。

不要给 interface 添加十几个尚未使用的方法。

---

# 7. WeaponContext

WeaponAction 不应该从大量 singleton/global object 获取上下文。

建立一个小型 WeaponContext，包含当前 Action 真正需要的信息，例如：

```text
LivingEntity / Player
World
ItemStack
Hand
WeaponDefinition
```

根据 Server / Client 实际需求决定字段。

不要把所有 Minecraft 系统都塞进去。

---

# 8. ActionRuntime

WeaponAction 是 Definition / Factory。

WeaponActionRuntime 表示一次正在执行的动作。

例如：

```text
player presses attack
        ↓
MeleeAttackAction
        ↓
MeleeAttackRuntime
```

Runtime 至少能够描述：

```text
current tick
phase
finished
```

统一基础 Phase：

```text
STARTUP
ACTIVE
RECOVERY
FINISHED
```

但不要强迫所有 Action 使用复杂 phase graph。

如果简单 tick timeline 足够，就保持简单。

Runtime 不应持久化在 ItemStack。

Runtime 属于正在执行动作的实体。

---

# 9. Action Timeline

系统必须允许一个 Action 在特定 tick 产生 Gameplay Event 和 Presentation Event。

例如测试剑：

```text
tick 0:
    start player attack animation

tick 4:
    start sword trail

tick 7:
    server hitbox

tick 8:
    impact presentation

tick 15:
    combo window

tick 20:
    action complete
```

第一阶段不需要设计通用可视化 Timeline Editor。

可以使用简单代码实现。

但请将：

GAMEPLAY EVENT

与：

PRESENTATION EVENT

概念分开。

---

# 10. Server authoritative

这是强制要求。

Damage / Hitbox / Projectile / Ammo / Mana 等 Gameplay State 必须由 Server 决定。

禁止：

```text
client animation keyframe
    ↓
send "deal damage"
    ↓
server trusts client
```

正确模型：

```text
Client input
    ↓
WeaponActionRequest
    ↓
Server validates
    ↓
Server creates WeaponActionRuntime
    ↓
Server timeline reaches hit tick
    ↓
Server performs hit detection
    ↓
Server applies damage
```

Client animation 只是同一个 Action Timeline 的表现。

---

# 11. Networking

第一阶段只设计最少的数据包。

优先考虑：

```text
C2S:
WeaponActionRequest

S2C:
WeaponActionStarted
WeaponActionCancelled
```

必要时增加 Action Finished，但只有真正需要同步时才增加。

ActionStarted 至少应允许客户端知道：

```text
entity
action id
start game tick / synchronization information
```

如随机 VFX 或 recoil 需要确定性，可以增加 seed。

不要：

* 每 tick 发送 Action 状态
* 每 tick发送动画位置
* 将 WeaponDefinition 整个通过 packet 重复发送
* 客户端决定 damage

目标是：

Server 通知：

“entity X 在时间 T 开始 action Y”

客户端自行播放对应表现。

---

# 12. Animation architecture

Gameplay 代码不能直接依赖 GeckoLib 或具体 Player Animation API。

建立非常薄的 presentation abstraction。

例如：

```kotlin
interface PlayerAnimationService {
  fun play(entityId: Int, animation: Identifier)
  fun stop(entityId: Int, animation: Identifier)
}
```

以及：

```kotlin
interface WeaponAnimationService {
  fun play(
    entityId: Int,
    stack: ItemStack,
    animation: Identifier
  )
}
```

具体 API 根据实际动画库调整。

不要为了抽象而包装整个 GeckoLib/PAL API。

目标只是在 Gameplay 与动画库之间建立边界。

---

# 13. Player animation

玩家身体动画与武器自身动画必须分离。

Player Animation：

```text
attack pose
reload pose
aim pose
casting pose
body / arms / legs
```

Weapon Animation：

```text
magazine
bolt
slide
trigger
staff crystal
weapon bones
```

如果项目采用 PAL，则：

```text
PlayerAnimationService
       ↓
PAL backend
```

如果最终使用其他库，也只能替换 backend。

Weapon Runtime 不允许直接调用 PAL API。

---

# 14. Weapon animation

如果项目已有 GeckoLib，则可以使用它处理：

```text
3D weapon
weapon bones
reload animation
bolt
magazine
staff parts
```

如果没有 GeckoLib，不要未经分析直接加入。

先判断测试剑是否真的需要 GeckoLib。

不要把：

```text
currentAnimation
animationTick
```

保存在 Item singleton。

具体 ItemStack / player animation state 必须能够彼此区分。

---

# 15. VFX

建立一个非常轻量的：

```text
WeaponVfxService
```

第一阶段只需要支持测试剑：

```text
Trail
Impact
```

未来可能支持：

```text
Particle
Trail
MuzzleFlash
Impact
Beam
Decal
CameraShake
```

但不要现在全部实现。

Gameplay Runtime 只发：

```text
play VFX id at context
```

不要让 MeleeAttackAction 直接拥有几十行 particle spawning 代码。

---

# 16. Melee 第一阶段

实现一个：

`MeleeAttackAction`

以及必要的 Runtime。

测试剑攻击过程：

```text
PRIMARY INPUT
      ↓
WeaponController
      ↓
resolve test_sword/light_attack
      ↓
C2S request
      ↓
Server validates
      ↓
ActionRuntime starts
      ↓
S2C ActionStarted
      ↓
Client plays animation
      ↓
Server active tick
      ↓
hitbox query
      ↓
damage
      ↓
recovery
      ↓
finished
```

Hitbox 第一阶段保持简单。

可以使用：

* Box
  或
* Vanilla entity query + distance / facing test

不要现在实现：

* skeletal hitbox
* OBB system
* lag compensation
* rollback netcode

但 Hitbox 代码应从 WeaponAction 中适当分离，避免以后每个 Action 复制 entity query。

---

# 17. Combo

第一阶段只预留最小能力。

例如：

```text
Attack1
   ↓
combo window
   ↓
Attack2
```

可以通过：

```text
nextAction
comboStartTick
comboEndTick
```

或其他简单设计完成。

不要现在实现完整 Combo Graph Editor。

如果第一阶段实现 combo 会显著扩大范围，可以暂时只设计接口边界，不实现第二段攻击。

---

# 18. 未来枪械必须能够建立在同一框架上

当前不实现完整枪械，但设计完成后应能够自然加入：

```text
FireAction
ReloadAction
AimAction
SwitchFireModeAction
```

以及状态：

```text
ammo
fireMode
```

一次 FireAction 应可以产生：

```text
server:
ammo consume
hitscan/projectile
damage
spread

client:
weapon animation
player animation
muzzle flash
sound
recoil
shell effect
```

如果当前 WeaponAction 设计无法自然表达这些行为，说明抽象需要调整。

但不要现在实现完整枪械。

> 实现状态（2026-08）：后续需求已经在同一 Weapon Runtime 上加入 TaCZ 1.1.x 外置枪包、Lua 客户端动画状态机和人物动作兼容层。具体安装、支持边界与验证结果见
`docs/weapon/tacz-adapter.md`；服务端玩法仍保持本章规定的权威 Action 边界。

---

# 19. 未来 Staff 必须能够复用 SkillSystem

未来法杖：

```text
CastAction
    ↓
SkillService.cast(skillId)
```

StaffItem 不应该自己实现：

```text
fireball damage
mana
particle
cooldown
```

Weapon Framework 只负责：

```text
weapon input
action state
animation
presentation timing
```

技能实际效果未来交给 SkillSystem。

不要现在实现完整 SkillSystem。

只确保架构不会阻止：

```text
WeaponAction
    ↓
SkillService
```

---

# 20. Package 建议

不要强制照搬，如果现有项目已有良好 package，请适配。

可以参考：

```text
weapon/
    WeaponDefinition
    WeaponRegistry
    WeaponController
    WeaponContext

weapon/action/
    WeaponAction
    WeaponActionRuntime
    ActionPhase

weapon/action/melee/
    MeleeAttackAction
    MeleeAttackRuntime

weapon/state/
    ...

combat/
    HitboxService
    DamageService

network/
    WeaponActionRequestPayload
    WeaponActionStartedPayload

client/weapon/
    WeaponPresentationController

client/animation/
    PlayerAnimationService
    WeaponAnimationService

client/vfx/
    WeaponVfxService
```

不要为了目录对称制造空类。

---

# 21. 当前必须完成的 MVP

最终实现一个测试 Weapon：

```text
Test Sword
```

支持：

```text
PRIMARY
    ↓
Light Attack
```

Light Attack 至少具备：

1. Client 请求 Attack。
2. Server 验证玩家当前可以攻击。
3. Server 创建 Runtime。
4. Server 广播 ActionStarted。
5. Client 播放攻击 animation。
6. Client 在对应 timeline 播放简单 trail/VFX。
7. Server 在 active tick 做一次 melee hit detection。
8. Server 对目标造成伤害。
9. 进入 recovery。
10. Runtime 正确结束。
11. 攻击期间不能无限重复开始同一攻击。
12. dedicated server 环境不能加载 client-only class。

如果当前没有实际动画资源：

创建 animation/VFX backend 和调用路径即可。

不要为了完成任务伪造大型动画资产。

---

# 22. 代码质量要求

优先：

* 简单
* 清晰
* 可调试
* server/client 边界明确
* Kotlin idiomatic，但不要过度函数式
* 热路径避免无意义 allocation
* 少用 global mutable state
* 不使用 reflection
* 不使用 runtime class scanning
* 不使用复杂 DI

避免：

```text
ManagerFactory
ProviderFactory
AbstractBaseWeaponActionFactory
GenericActionPipelineProcessor
```

之类没有实际价值的结构。

如果一个 class 只有一个调用者且没有真正抽象意义，可以直接合并。

---

# 23. Mixin 原则

优先 Fabric Event / 原生 API。

Mixin 只有在 Fabric / Minecraft 没有合适 hook 时使用。

如果必须使用 Mixin：

Mixin 只做 bridge。

例如：

```text
Mixin
 ↓
WeaponController / Service
```

禁止将 Weapon gameplay logic 大量写进 Mixin。

---

# 24. 实施方式

不要一次修改整个项目。

按照以下方式工作：

第一步：

输出你对当前项目 Weapon/Item/Network/Client 架构的分析。

第二步：

给出你准备新增/修改的最小文件集合，并解释每个文件存在的必要性。

第三步：

检查当前方案是否存在：

* 过度抽象
* client/server 混用
* runtime state 放入 Item singleton
* Sword/Gun/Staff 强耦合
* animation 决定 gameplay
* packet 过度同步

如果存在，先简化设计。

第四步：

开始实现 Weapon Framework foundation。

第五步：

实现 Test Sword 的完整纵向链路。

第六步：

编译项目并修复实际编译错误。

不要通过猜测 API 来规避编译。

第七步：

总结：

* 实际创建/修改的文件
* Weapon Action 的执行流程
* Client/Server 边界
* 如何增加第二把 Sword
* 未来如何增加 Firearm
* 未来如何接入 Staff + SkillSystem
* 当前刻意没有实现的功能

---

# 最重要的设计约束

最终请始终保持下面这一关系：

```text
Item
 ↓
WeaponDefinition
 ↓
Action Binding
 ↓
WeaponAction
 ↓
WeaponActionRuntime
 ↓
────────────────────────────
SERVER            CLIENT
Gameplay          Presentation
↓                 ↓
Hitbox             Player Animation
Damage             Weapon Animation
Projectile         VFX
Skill              Sound
```

Sword / Gun / Staff 不是三套 Framework。

它们只是不同 WeaponAction 的组合。

不要提前建设完整框架。

先让：

```text
Test Sword
→ Attack
→ Animation
→ Trail
→ Server Hit
→ Damage
```

这一条链路以最小、清晰、可扩展的代码真正运行起来。
