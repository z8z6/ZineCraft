---
name: zinecraft-weapons
description: Add or revise Zinecraft melee, firearm, or staff weapons using the server-authoritative action runtime, combat profiles, state, networking, and client presentation. Use the effects skill for reusable visual-only assets.
---

# Zinecraft 武器

客户端只请求动作和播放表现；命中、弹药、伤害与动作状态由服务端裁决。当前自研武器输入只支持主手。

## 当前入口

- WeaponBuilder、WeaponActionBuilder、WeaponPresentationBuilder、WeaponCatalog
- ModWeapon、WeaponServerController、WeaponPayloadTypes
- api/weapon/action/、combat/、state/、network/
- WeaponClientInput、WeaponAttackMixin 与 client/weapon/
- TaCZ 任务才读取 integration/tacz/

## 修改流程

1. 先声明承载物品，再用 WeaponActionBuilder 注册动作，并在 WeaponBuilder 为每个 WeaponInput 绑定唯一动作与可选表现时间线。
2. 服务端从玩家当前主手重新解析武器，验证 active action、时序、射程、命中和资源；伤害走 CombatService、CombatDamageProfile、MeleeHitboxService 或 HitscanService。payload 不接收伤害、目标、命中或弹药结论。
3. 当前并发/动作锁由 WeaponServerController 的 active actions 与 runtime 控制；若需要跨动作冷却，必须新增明确的服务端状态，不能假定已有通用冷却组件。
4. 已落地的持久状态示例是 AMMO 与 AIMING。FIRE_MODE、NEEDS_BOLT 目前只是预留，使用前先实现消费者；新增组件需同时提供持久与网络 codec 并注册。
5. 只有调用 CombatService.actionTiming 的动作会受攻击速度缩放；近战、射击和施法需核对，装填与瞄准当前使用原始 ticks。客户端表现 ticks 也不会自动随服务端缩放。
6. TaCZ 只经 resolver/backend/event 适配到中立边界，不复制其物品、输入、渲染和机械动画职责。

## 验证

运行 ./gradlew.bat test、./gradlew.bat runData 和 ./gradlew.bat build。在专用服务端测试伪造/重复请求、非主手请求、换手/丢弃/登出取消、弹药、动作互斥与命中；客户端测试输入、时间线和远端观察者，并如实报告 TaCZ 与延迟场景是否验证。
