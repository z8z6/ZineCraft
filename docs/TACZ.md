目标：在现有 Minecraft NeoForge 1.21.1 Java 项目中集成 MUKSC/TACZ-1.21.1，但 TaCZ 只能作为枪械后端，不得成为项目武器系统的核心抽象。

## 参考源码

优先阅读并以以下源码为唯一 TaCZ API 事实来源：

* MUKSC/TACZ-1.21.1
* branch: `neoforge/1.21.1`

禁止根据 Forge 1.20.1 TaCZ API 猜测接口。
禁止因为旧 Wiki 示例而使用当前源码中不存在的方法。

在编码前重点检查：

* `com.tacz.guns.api`
* `com.tacz.guns.api.item`
* `com.tacz.guns.api.item.gun`
* `com.tacz.guns.api.event.common`
* `com.tacz.guns.api.client.animation`
* `com.tacz.guns.resource`
* `com.tacz.guns.item`

确认当前版本真实存在的 API、Event 和数据结构后再实现。

## 架构目标

建立独立模块：

```text
integration/tacz/
```

TaCZ 类型不得泄漏到：

```text
skill/
combat/
operator/
vfx/
```

业务层只能依赖项目自身定义的武器抽象。

推荐结构：

```text
weapon/
├── api/
│   ├── WeaponBackend
│   ├── RangedWeaponBackend
│   ├── WeaponContext
│   └── WeaponShotContext
└── event/
    └── WeaponShotEvent

integration/tacz/
├── TaczIntegration
├── TaczWeaponBackend
├── TaczGunResolver
├── TaczGunEvents
├── TaczAnimationBridge
└── TaczVfxBridge
```

## 第一阶段仅实现 MVP

### 1. TaCZ 枪械识别

实现可靠的 TaCZ gun detection。

不要硬编码具体枪械 ID。

不要为每把枪创建 Java subclass。

### 2. Event Adapter

研究当前源码中的：

```text
GunShootEvent
GunFireEvent
```

确认二者真实语义后，选择正确事件作为射击事件来源。

将其转换为项目内部：

```java
WeaponShotEvent
```

业务层不得直接订阅 TaCZ event。

数据转换至少考虑：

```text
shooter
weapon ItemStack
position
direction
gun identity
```

只有当前 TaCZ API 可以可靠获取的数据才能进入 context。

不要为了补齐字段访问 private implementation detail。

### 3. WeaponBackend

建立：

```java
interface RangedWeaponBackend
```

TaCZ 实现：

```java
final class TaczWeaponBackend
        implements RangedWeaponBackend
```

接口保持小型。

不要试图在第一阶段抽象 TaCZ 所有功能。

### 4. Skill Integration

Skill System 只能监听：

```text
WeaponShotEvent
```

而不能监听：

```text
GunShootEvent
```

实现一个测试 Skill：

```text
TestRapidFireSkill
```

用于验证：

```text
TaCZ shot
→ adapter
→ WeaponShotEvent
→ SkillRuntime
```

### 5. VFX Hook

为未来 Photon 集成保留：

```java
WeaponVfxService
```

当前只需要：

```text
onShot(...)
```

事件触发时可以调用测试实现。

禁止把 Photon 类直接写入 Skill。

最终目标：

```text
Skill
 ↓
WeaponVfxService
 ↓
Photon implementation
```

### 6. Client / Server Boundary

明确标记：

```text
server authoritative gameplay
client presentation
```

不得因为播放 muzzle flash 或技能 VFX 修改服务端战斗逻辑。

不要每个粒子发送一个 packet。

未来 VFX 应同步高层事件：

```text
effect id
entity
position
direction
seed
```

客户端自行播放效果。

## 暂时不要实现

第一阶段禁止实现：

* 自定义 ADS
* 自定义 recoil
* 自定义 gun renderer
* 自定义 reload animation
* 大型 attachment framework
* Photon 完整接入
* GeckoLib 与 TaCZ 枪械动画混合
* 修改 TaCZ 源码
* Mixin TaCZ internal classes

除非发现没有 public API 可以完成 MVP，否则不要使用 Mixin。

如果 public API 缺失：

1. 记录缺失能力；
2. 指出需要访问的 internal implementation；
3. 暂停该功能；
4. 不要自行创建复杂 workaround。

## TaCZ 动画

阅读：

```text
api/client/animation/
├── gltf
├── statemachine
├── AnimationController
├── AnimationListener
├── AnimationPlan
└── ObjectAnimation
```

第一阶段只整理动画扩展点，不实现复杂技能动画。

枪械机械动画优先继续由 TaCZ 控制。

未来：

```text
Player body animation
```

与：

```text
TaCZ gun animation
```

应保持职责分离。

## 数据驱动

研究：

```text
GunPackLoader
CommonAssetsManager
resource/manager
resource/pojo
resource/serialize
```

枪械定义、模型、材质、动画尽可能使用 TaCZ gun pack/data-driven workflow。

禁止：

```java
ExusiaiGun extends ...
AshGun extends ...
FiammettaGun extends ...
```

这种每个内容创建 Java 类型的设计。

## 代码质量要求

保持实现最小化。

不要为了“未来扩展”创建十几个空接口。

只抽象目前存在明确边界的部分：

```text
WeaponBackend
WeaponShotEvent
TaCZ Adapter
VFX Service
```

不要修改无关系统。

不要重构整个项目。

每完成一个阶段立即编译。

如果 TaCZ API 与预期不同，以源码为准，并更新设计。

## 最终验收

需要证明以下流程可以工作：

```text
玩家使用 TaCZ 枪械射击
        ↓
TaCZ event
        ↓
TaczGunEvents
        ↓
WeaponShotEvent
        ↓
SkillRuntime
        ↓
TestRapidFireSkill
        ↓
WeaponVfxService
```

同时保证：

```text
SkillRuntime
```

完全不知道 TaCZ 的存在。

最后输出：

1. 实际使用到的 TaCZ public APIs；
2. 实际订阅的 TaCZ events；
3. 新增文件列表；
4. 模块依赖关系；
5. 当前发现的 TaCZ 1.21.1 Port API 风险；
6. 下一阶段建议，但不要直接实施下一阶段。
