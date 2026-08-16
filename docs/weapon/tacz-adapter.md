# TaCZ 1.21.1 后端集成

Zinecraft 将 MUKSC/TACZ-1.21.1 作为必需的枪械后端。TaCZ 负责枪械物品、枪包加载、弹道、弹药、输入、渲染、ADS、后坐力与机械动画；Zinecraft
不复制这些能力，也不为每把枪创建 Java 类型。

唯一 API 事实来源是 `MUKSC/TACZ-1.21.1` 的 `neoforge/1.21.1` 分支。当前开发基线为 `1.1.8-hotfix-r6`，不得用 Forge 1.20.1
Wiki 或旧源码推断接口。

## 模块边界

```text
TaCZ GunFireEvent (server)
        ↓
integration/tacz/TaczGunEvents
        ↓
api/weapon/event/WeaponShotEvent
        ↓
core/skill/SkillRuntime
        ↓
core/skill/TestRapidFireSkill
        ↓
api/weapon/vfx/WeaponVfxService
```

项目内部的 `WeaponBackend`、`RangedWeaponBackend` 与 `WeaponShotContext` 不引用 TaCZ 类型。只有 `integration/tacz` 可以
import `com.tacz.*`；`skill`、`combat`、`operator` 和 `vfx` 必须只依赖 Zinecraft 自身抽象。

## 当前公共 API

- `IGun.getIGunOrNull(ItemStack)`：可靠识别 TaCZ 枪械。
- `IGun#getGunId(ItemStack)`：取得数据驱动的枪械身份，不硬编码具体枪 ID。
- `GunFireEvent`：一次实际击发触发一次；Burst 会触发多次。
- `GunShootEvent`：一次扳机动作触发一次；MVP 不订阅该事件。

适配器只处理 `LogicalSide.SERVER` 的、未取消的 `GunFireEvent`，并转换 TaCZ 公开提供或 Minecraft 实体公开提供的
shooter、ItemStack、眼部位置、视线方向和 gun ID。它不读取 private 字段，不修改 TaCZ 射击结算。

## 客户端与 VFX

`WeaponShotEvent` 是服务端权威的高层业务事件。当前测试技能只把 `onShot` 传入 `WeaponVfxService` 的 no-op 实现，用来证明调用链和保留
Photon 边界；客户端 muzzle flash、枪声与枪械动画仍由 TaCZ 自己播放。

未来同步技能 VFX 时只发送高层效果信息（effect id、entity、position、direction、seed），客户端自行展开效果。粒子和动画不得反向修改伤害、弹药或技能结算。

## 第一阶段限制

当前不实现自定义 ADS、后坐力、枪械渲染、换弹动画、配件框架、TaCZ/GeckoLib 混合动画、Mixin 或 TaCZ 源码修改。动画 API
仅作为后续扩展点调查，枪械机械动画继续由 TaCZ 控制。

TaCZ 1.21.1 是非官方移植版，其 API 和数据格式可能在 hotfix 间变化；升级依赖时必须重新核对事件触发位置、`IGun`
接口和枪包兼容性，并运行客户端与专用服务器烟测。
