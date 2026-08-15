# 任务：将 Minecraft 1.21.1 Fabric + Kotlin 模组迁移到 NeoForge + Java

你正在维护一个 Minecraft 1.21.1 模组。

当前项目技术栈：

* Minecraft 1.21.1
* Fabric Loader
* Fabric API
* Kotlin
* Gradle

目标技术栈：

* Minecraft 1.21.1
* NeoForge
* Java 21
* Gradle
* Mojang mappings / NeoForge 1.21.1 官方推荐开发方式

本次任务的目标是：

> 在尽可能保持现有游戏行为、资源结构和功能不变的前提下，将项目从 Fabric + Kotlin 迁移到 NeoForge + Java。

这首先是一次 **平台迁移和语言迁移**，而不是一次全面架构重写。

---

# 1. 最重要的原则

## 1.1 不要过度设计

禁止为了“未来可能需要”引入大量：

* Adapter
* Bridge
* Factory
* Manager
* Service
* Context
* Provider
* Facade
* Loader abstraction

除非现有代码确实存在对应需求。

项目迁移到 NeoForge 后，暂时只支持 NeoForge。

因此：

**不要为了 Fabric/NeoForge 双端兼容而创建一整套 Loader Compatibility Layer。**

如果某个 NeoForge API 只在极少数位置出现，直接使用 NeoForge API。

只有当 Loader API 大量侵入核心 gameplay logic 时，才考虑创建非常薄的边界层。

遵循：

> Simple first, abstraction when justified.

---

# 2. 不要同时重写 Gameplay 系统

项目未来会包含：

* Sword / melee weapon
* Staff / magic weapon
* Gun / ranged weapon
* Weapon animation
* Skill system
* VFX
* Particle
* Projectile
* Entity
* Mob
* Structure
* Biome
* Player skill state
* Client animation state
* Network synchronization

但：

**本次迁移不要重新设计这些系统。**

如果现有 Fabric/Kotlin 实现可以直接转换：

优先：

Kotlin implementation
↓
Java equivalent
↓
替换 Fabric API
↓
NeoForge API

而不是：

Kotlin implementation
↓
重新设计架构
↓
实现一套全新框架

只有现有代码明显依赖 Fabric 生命周期/API，导致无法直接迁移时，才进行局部重构。

---

# 3. 第一阶段：分析现有工程

在修改任何代码之前，先扫描整个 repository。

分析：

1. `build.gradle` / `build.gradle.kts`
2. `gradle.properties`
3. `settings.gradle`
4. `fabric.mod.json`
5. Fabric entrypoint
6. Kotlin source
7. Java source
8. Mixin
9. Access Widener
10. Registry
11. Event
12. Networking
13. Client initialization
14. Rendering
15. Item / Block
16. Entity
17. Worldgen
18. Datagen
19. Resource
20. Config
21. 第三方依赖

然后生成：

`MIGRATION_PLAN.md`

内容只需要包括：

### Current Architecture

当前主要 package 和模块。

### Fabric Dependencies

列出所有 Fabric-specific API。

例如：

* FabricLoader
* Fabric API registry
* Fabric lifecycle events
* Fabric networking
* Fabric key binding
* Fabric renderer API
* Fabric event API
* Fabric data generation
* Fabric transfer API
* Fabric attachment API
* Fabric mixin/access widener

### Kotlin Dependencies

找出 Kotlin-specific 写法，例如：

* object
* data class
* sealed class
* extension function
* delegated property
* companion object
* lateinit
* nullable type
* coroutine
* Kotlin collection API
* Kotlin serialization

### Migration Risk

按照：

* LOW
* MEDIUM
* HIGH

标记。

不要写长篇架构论文。

控制在能够直接指导实施的范围。

完成分析后直接开始迁移，不需要等待人工确认。

---

# 4. 第二阶段：迁移构建系统

将项目迁移为标准 NeoForge 1.21.1 Java 项目。

要求：

* Java 21
* NeoForge 1.21.1
* 移除 Fabric Loader
* 移除 Fabric API
* 移除 Fabric Loom
* 移除 Fabric Language Kotlin
* 移除不再需要的 Kotlin Gradle plugin
* 移除 Kotlin runtime
* 配置 NeoForge ModDevGradle / 当前 NeoForge 1.21.1 推荐 Gradle 配置

注意：

**必须使用 Minecraft 1.21.1 对应 NeoForge API。**

不要参考：

* 1.20.x Forge
* 老 Forge API
* NeoForge 1.21.4+
* NeoForge 1.21.8+
* NeoForge 26.1

中的 API 写法，除非确认该 API 在 NeoForge 1.21.1 中完全一致。

遇到 API 不确定时：

优先查询：

NeoForge 官方 `1.21 - 1.21.1` 文档。

---

# 5. Mod Entry Point

将 Fabric：

```text
ModInitializer
ClientModInitializer
```

迁移到 NeoForge：

```text
@Mod
```

体系。

保持入口类尽量简单。

推荐职责：

```text
MyMod
 ├─ register content
 ├─ register networking
 ├─ register config
 └─ hook lifecycle
```

不要把实际 gameplay logic 塞进入口类。

客户端初始化必须和 common/server gameplay 逻辑分离。

---

# 6. Registry 迁移

将 Fabric Registry API 迁移到 NeoForge 1.21.1 推荐注册方式。

优先使用：

```java
DeferredRegister
```

以及对应 helper。

整理注册代码，例如：

```text
registry/
    ModItems.java
    ModBlocks.java
    ModEntities.java
    ModSounds.java
    ModParticles.java
    ModEffects.java
    ModMenus.java
    ModDataComponents.java
```

不要创建一个巨大的：

```text
ModRegistries.java
```

注册所有东西。

但也不要给每个 Item 创建一个 registry 类。

按 Minecraft registry 类型划分即可。

---

# 7. Kotlin → Java

将所有 Kotlin 源码迁移为 Java 21。

要求：

不要机械逐行翻译。

应该翻译 Kotlin 的语义。

例如：

```kotlin
object ModItems
```

可转换为：

```java
public final class ModItems {
  private ModItems() {
  }
}
```

或者根据实际情况改为静态注册容器。

---

对于：

```kotlin
data class
```

优先考虑 Java：

```java
record
```

但只有数据结构真正不可变且语义适合时才使用。

---

对于：

```kotlin
sealed class
```

可根据实际需要使用：

```java
sealed interface
sealed class
```

不要为了对应 Kotlin 语法强行使用 sealed。

---

对于 extension function：

优先判断它实际上属于：

* Utility
* Domain object
* Helper

哪一种。

不要自动生成：

```text
XXXExtensions.java
```

作为垃圾桶。

---

避免 Java 中出现 Kotlin 翻译遗留风格，例如：

```java
Something.INSTANCE
```

大量 static helper nesting

或者不必要的：

```java
Objects.requireNonNull(...)

Optional
    AtomicReference
Supplier
```

---

# 8. Client / Server Boundary

严格区分：

```text
logical client
logical server
physical client
dedicated server
```

Gameplay authoritative state 应由 server 管理。

例如：

* Damage
* Skill cooldown
* Mana
* Weapon state
* Ammo
* Projectile spawn
* Entity state
* Combat calculation

原则上由服务器决定。

客户端主要负责：

* Rendering
* Animation
* Input
* HUD
* Particle presentation
* Sound presentation
* Prediction（未来确有需要时）

不要因为单人游戏可以运行，就假设代码能够在 Dedicated Server 正确运行。

客户端代码建议放：

```text
client/
    render/
    animation/
    input/
    hud/
    particle/
```

common gameplay code 不允许依赖：

```text
Minecraft.getInstance()
```

或其他明显 client-only 类型。

---

# 9. Event 系统迁移

将 Fabric Event API 转换为 NeoForge Event Bus。

迁移时区分：

```text
Mod Event Bus
NeoForge Game Event Bus
```

不要创建自己的 EventBus abstraction 去包装 NeoForge。

只有游戏内部确实存在独立领域事件需求，例如：

```text
SkillCastEvent
WeaponAttackEvent
```

并且多个 subsystem 真正需要订阅时，才考虑自己的 domain event。

否则直接使用普通方法调用。

---

# 10. Networking

将 Fabric networking 迁移到 NeoForge 1.21.1 networking。

使用该版本推荐的：

```text
RegisterPayloadHandlersEvent
CustomPacketPayload
StreamCodec
```

等机制。

网络代码统一放在：

```text
network/
```

例如：

```text
network/
    ModNetworking.java

    payload/
        CastSkillPayload.java
        WeaponAttackPayload.java
        ReloadWeaponPayload.java
        SyncPlayerDataPayload.java
```

Payload 本身尽量只是：

```text
data + serialization
```

不要把复杂 gameplay logic 放进 packet 类。

推荐：

```text
Payload
    ↓
Handler
    ↓
Gameplay System
```

例如：

```text
CastSkillPayload
        ↓
SkillNetworkHandler
        ↓
SkillSystem.cast(...)
```

如果 handler 很简单，可以直接调用 domain system，不要为了架构美观强行创建 handler class。

服务器必须校验客户端请求。

禁止直接信任客户端发送：

* damage
* cooldown
* mana
* position
* target
* ammo
* skill state

等关键 gameplay state。

---

# 11. ItemStack 自定义数据

如果原 Fabric 项目使用：

```text
NBT
custom tags
Fabric attachments
```

保存武器状态，应逐个判断。

对于 ItemStack 的结构化自定义状态：

优先考虑 Minecraft 1.21.1 Data Components。

例如未来可能出现：

```text
ammo
weapon mode
charge
spell
weapon level
custom state
```

可以根据实际需要定义 Data Component。

不要创建：

```text
Map<String, Object>
```

或一个通用万能 NBT 容器。

但本次迁移只迁移现有数据。

不要提前实现完整武器 Data Component Framework。

---

# 12. Player / Entity 数据

如果存在：

```text
player skill
mana
cooldown
progression
entity custom state
```

研究 NeoForge 1.21.1 Data Attachments 是否适合。

例如：

```text
Player
 └─ SkillData
      ├─ mana
      ├─ unlocked skills
      └─ cooldown state
```

但不要把所有数据塞进一个：

```text
PlayerData
```

God Object。

应该根据真正存在的数据组织。

同时注意：

Data Attachment 并不自动意味着所有数据都应该 Attachment 化。

已有 Vanilla 机制可以解决时优先使用 Vanilla。

---

# 13. Weapon Architecture

当前或未来项目存在：

```text
Sword
Staff
Gun
```

迁移时保留扩展空间，但不要提前实现大型 Weapon Framework。

推荐的最低限度结构：

```text
item/
    weapon/
        WeaponItem
        Sword...
        Staff...
        Gun...
```

只有当 Sword / Staff / Gun 确实共享：

* attack lifecycle
* cooldown
* animation trigger
* targeting
* resource consumption

等行为时，才抽象共同接口或基类。

不要先设计：

```text
IWeapon
WeaponContext
WeaponBehavior
WeaponStrategy
WeaponExecutor
WeaponAction
WeaponActionContext
WeaponComponent
WeaponComponentRegistry
```

然后才开始写第一把武器。

抽象必须来自重复代码，而不是未来想象。

---

# 14. Skill System

Skill 系统不是本次迁移的主要目标。

如果现有技能系统已经存在：

保持当前行为，完成 NeoForge/Java 转换。

如果还不存在：

只保留未来适合放置代码的位置：

```text
skill/
```

不要在迁移期间实现完整技能框架。

未来技能系统应尽量保持：

```text
Skill definition
        ↓
Skill execution
        ↓
Server gameplay
        ↓
Network sync
        ↓
Client presentation
```

Gameplay 和 rendering 不应耦合。

---

# 15. Animation

动画属于 Client Presentation。

因此不要让：

```text
animation
```

成为 gameplay source of truth。

例如：

错误：

```text
动画播放到第 14 帧
→ 对敌人造成伤害
```

更合理：

```text
Server attack timeline
→ attack event
→ damage
→ sync
→ client animation
```

动画可以与 gameplay timeline 对齐，但客户端动画状态不能决定 authoritative gameplay。

迁移期间只保证现有动画功能可以工作。

不要顺便开发新的动画引擎。

---

# 16. Mixin

扫描所有 Fabric Mixin。

逐个判断：

### A. NeoForge Event 可以替代

使用 NeoForge Event。

### B. NeoForge hook/API 可以替代

使用 NeoForge API。

### C. Vanilla override 可以解决

使用正常继承/override。

### D. 没有合适 API

保留 Mixin。

不要为了“NeoForge 应该不用 Mixin”而强行消灭所有 Mixin。

同样：

不要为了避免一个非常简单的 Mixin，写几十行 Event/Reflection workaround。

目标是：

```text
最小侵入
+
可维护
```

而不是：

```text
0 Mixins
```

---

# 17. Access Widener

扫描 Fabric：

```text
*.accesswidener
```

逐项检查用途。

迁移到：

* public/protected NeoForge API
* Access Transformer
* Mixin Accessor
* Mixin Invoker

等合适方案。

不要机械生成大量 Access Transformer。

如果原 Access Widener 已经不再需要，应直接删除。

---

# 18. Resources / Datagen

尽可能保留：

```text
assets/<modid>/
data/<modid>/
```

已有资源结构。

检查：

* models
* blockstates
* textures
* sounds
* lang
* recipes
* loot tables
* tags
* advancements
* worldgen

迁移 Fabric Datagen 到 NeoForge Datagen。

不要因为迁移 Loader 而重新生成所有 JSON，除非格式确实变化。

---

# 19. 推荐 package 结构

根据现有项目调整，不要求完全一致：

```text
<mod package>/
│
├── MyMod.java
│
├── registry/
│   ├── ModItems.java
│   ├── ModBlocks.java
│   ├── ModEntities.java
│   ├── ModParticles.java
│   ├── ModSounds.java
│   └── ModDataComponents.java
│
├── item/
│   └── weapon/
│
├── entity/
│
├── combat/
│
├── skill/
│
├── network/
│   └── payload/
│
├── world/
│
├── client/
│   ├── animation/
│   ├── render/
│   ├── hud/
│   ├── input/
│   └── particle/
│
└── mixin/
```

这是指导结构，不是硬性要求。

如果某个 package 只有一个极小 class，不要为了匹配结构强制拆分。

---

# 20. 禁止 God Manager

避免出现：

```text
ModManager
GameManager
WeaponManager
SkillManager
ClientManager
RegistryManager
NetworkManager
```

除非它真的管理某种生命周期资源。

尤其不要出现：

```java
public class ModManager {
  registerItems();

  registerEntities();

  registerSkills();

  handleNetwork();

  updateWeapons();

  updateAnimations();

  tick();
}
```

不同职责应自然分离。

---

# 21. 不要滥用 Singleton

Java migration 不应该把 Kotlin：

```kotlin
object
```

全部翻译成传统 Singleton。

对于无状态 registry / utility：

优先：

```java
public final class Xxx {
  private Xxx() {
  }

  public static ...
}
```

对于有生命周期/state 的对象：

由 Minecraft / NeoForge 生命周期管理。

---

# 22. Java 风格

目标代码应该是现代、清晰的 Java 21。

允许合理使用：

```text
record
sealed
switch expression
pattern matching
var
Stream
Optional
```

但：

不要为了“Modern Java”炫技。

简单逻辑优先简单 Java。

例如：

优先：

```java
for(var entity :entities){
    entity.

doSomething();
}
```

而不是为了函数式风格写复杂 Stream pipeline。

---

# 23. Nullability

Kotlin → Java 后要重点检查 null safety。

不能简单删除：

```text
?
!!
?.
```

必须理解原始语义。

对于 Minecraft API 可能返回 null 的位置：

明确检查。

不要通过到处使用：

```java
Objects.requireNonNull()
```

掩盖逻辑问题。

也不要将所有可能为空的东西都包装成：

```java
Optional
```

Optional 主要适合 API return value，而不是到处作为 field/property。

---

# 24. 每阶段必须可以编译

不要一次修改整个项目之后才运行 Gradle。

采用增量迁移：

```text
Step 1
Build system
→ compile

Step 2
Mod entry
→ compile

Step 3
Registry
→ compile

Step 4
Basic content
→ compile

Step 5
Networking
→ compile

Step 6
Gameplay
→ compile

Step 7
Client
→ compile

Step 8
Datagen/resources
→ compile
```

每个阶段都修复编译错误。

不要留下数百个 error 再统一处理。

---

# 25. Migration Order

建议迁移顺序：

```text
1. Gradle / NeoForge environment

2. Mod metadata

3. Main @Mod entry

4. Registry

5. Items / Blocks

6. Entity

7. Events

8. Networking

9. Persistent data

10. Gameplay systems

11. Client initialization

12. Rendering

13. Animation

14. Particle / VFX

15. Worldgen

16. Datagen

17. Mixins

18. Tests / dedicated server
```

如果依赖关系要求调整顺序，可以调整。

---

# 26. Dedicated Server 必须验证

最终不仅运行：

```text
runClient
```

还必须运行：

```text
runServer
```

确保：

客户端专用 class 没有被 dedicated server classload。

重点检查：

```text
Minecraft
Screen
GuiGraphics
PoseStack
EntityRenderer
ItemRenderer
KeyMapping
Particle
client model
animation renderer
```

等代码是否泄露进入 common initialization。

---

# 27. 行为兼容

迁移完成后应尽量满足：

```text
Before Fabric
≈
After NeoForge
```

包括：

* registry ID
* resource location
* item ID
* entity ID
* recipe ID
* tag ID
* translation key
* network-visible behavior
* save data
* player state

如果必须破坏兼容性：

明确记录到：

```text
MIGRATION_NOTES.md
```

---

# 28. 不要主动扩大 Scope

本任务禁止顺手：

* 重写 combat system
* 重写 animation system
* 创建 ECS
* 创建 dependency injection framework
* 创建自己的 event framework
* 创建自己的 registry framework
* 创建自己的 networking framework
* 创建自己的 serialization framework
* 创建 Fabric/NeoForge abstraction
* 全面重构 package
* 全面修改命名
* 优化所有旧代码
* 添加大量未来功能

除非这是完成迁移必须进行的工作。

---

# 29. 对坏代码的处理

如果发现明显设计问题：

不要立即大规模重构。

使用以下规则：

### 小问题

直接修复。

### 会阻碍迁移的问题

进行最小必要重构。

### 与迁移无关的大问题

记录：

```text
TODO / MIGRATION_NOTES.md
```

以后处理。

---

# 30. Codex 工作方式

你是负责实际迁移的 senior Minecraft mod engineer，而不是 architecture consultant。

因此不要输出大量：

```text
Option A
Option B
Option C
```

讨论。

除非真的存在重大不可逆架构选择。

默认：

1. 阅读代码
2. 判断最简单合理方案
3. 实现
4. 编译
5. 修复
6. 继续

如果存在多个方案：

选择：

> 最符合 NeoForge 1.21.1 官方惯例、最简单、侵入最小的方案。

---

# 31. 修改代码前

先输出一个简洁计划：

```text
Migration status

[ ] Build system
[ ] NeoForge entrypoint
[ ] Registries
[ ] Kotlin → Java
[ ] Events
[ ] Networking
[ ] Data storage
[ ] Gameplay
[ ] Client
[ ] Resources
[ ] Mixins
[ ] Dedicated server
```

不要展开成长篇设计文档。

然后直接执行。

---

# 32. 修改过程中

每完成一个阶段：

运行合适的 Gradle task。

如果出现错误：

先分析错误根因。

不要使用：

* suppress warning
* unchecked cast
* reflection
* fake adapter
* empty implementation
* TODO implementation
* commented-out code

来让项目“看似编译通过”。

---

# 33. 最终验收

迁移完成后检查：

### Build

```text
Gradle build succeeds
```

### Client

```text
Minecraft client starts
Mod loads
World can be entered
```

### Server

```text
Dedicated server starts
Mod loads
No client class loading crash
```

### Registry

确认所有已有：

```text
items
blocks
entities
particles
sounds
effects
```

注册成功。

### Gameplay

检查已有：

```text
weapons
skills
combat
entities
```

行为。

### Network

验证：

```text
client → server
server → client
```

必要数据同步。

### Persistence

退出世界重新进入后：

自定义数据保持正确。

---

# 34. 最终输出

完成工作后只需要给出：

## Migration Summary

完成了哪些主要迁移。

## Important Architectural Changes

只记录真正重要的变化。

## Compatibility Notes

是否存在存档/API/resource 兼容性变化。

## Remaining Issues

仍需要人工处理的问题。

## Validation

列出实际执行过的：

```text
build
runClient
runServer
tests
```

及结果。

不要生成长篇架构论文。

---

# 最终目标

最终代码应该：

```text
Minecraft 1.21.1
        +
NeoForge
        +
Java 21
```

能够稳定运行。

同时为未来：

```text
Weapons
Combat
Skills
Animations
Entities
VFX
Worldgen
```

保留合理扩展空间。

但这种扩展空间应该来自：

```text
清晰职责
低耦合
client/server 分离
合理 package
稳定 domain boundary
```

而不是来自：

```text
大量 interface
大量 abstraction layer
大量 manager
大量 factory
预测未来需求
```

优先级始终是：

```text
Correctness
    ↓
Successful migration
    ↓
Maintainability
    ↓
Extensibility
    ↓
Abstraction
```

先得到一个干净、可靠、符合 NeoForge 1.21.1 惯例的 Java 项目，再进行后续 gameplay architecture 演进。
