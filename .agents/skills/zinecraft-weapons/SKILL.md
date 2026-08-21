---
name: zinecraft-weapons
description: Add or revise Zinecraft melee, firearm, or staff weapons using the server-authoritative weapon runtime, input/action mappings, combat profiles, state, networking, and client presentation. Use the effects skill for reusable visual-only assets.
---

# Zinecraft 武器

让客户端只请求动作和播放表现，命中、弹药、冷却、伤害与状态由服务端裁决。

## 建立上下文

阅读 `AGENTS.md`、工作树和：

- `api/registry/builder/WeaponBuilder.java`、`WeaponActionBuilder.java`、`WeaponPresentationBuilder.java`
- `api/registry/catalog/WeaponCatalog.java` 与 `core/registry/ModWeapon.java`
- `api/weapon/WeaponServerController.java`、`action/`、`combat/`、`state/`、`network/`
- `src/client/java/com/cxxcxx/zinecraft/core/client/weapon/`
- TaCZ 武器还要阅读 `integration/tacz/`，不要复制 TaCZ 已拥有的物品与机械动画职责

## 实现

1. 从官方/PRTS核实武器归属、名称、外观与机制；Minecraft 数值化应标为适配，不虚构原始数据。
2. 先声明承载物品；近战、枪械、施术参考 `ModWeapon` 的 test sword/rifle/staff。用 `WeaponActionBuilder` 注册可复用动作，再在
   `WeaponBuilder` 为每个 `WeaponInput` 绑定唯一动作并 `.build()`。
3. 服务端动作验证持有物、状态、时序、射程/命中和资源消耗；伤害走 `CombatService`、`CombatDamageProfile`、`MeleeHitboxService`
   或 `HitscanService`。客户端 payload 只是请求，不得提供伤害、目标、命中或弹药结论。
4. 弹药、瞄准、装填和持续动作状态使用 `WeaponStateComponents` 与现有 payload。新增组件同时提供持久与网络 codec，并接入启动注册。
5. 表现时间线只能绑定该武器已有动作。服务端 effect/duration 会受攻击速度缩放，而客户端表现 ticks
   当前不会自动缩放；必须显式核对同步。复用表现资源使用 `$zinecraft-effects`。
6. TaCZ 只经 resolver/backend/事件适配到中立边界，普通技能与公共逻辑不要直接依赖 TaCZ 类。

## 验证

运行 `./gradlew.bat test`、`./gradlew.bat runData`、`./gradlew.bat build`
。在专用服务端测试伪造/重复请求、换手/丢弃/登出取消、弹药、冷却与命中；客户端测试输入、动画、声音、VFX 和远端观察者。报告 TaCZ
与多人延迟场景是否实际验证。
