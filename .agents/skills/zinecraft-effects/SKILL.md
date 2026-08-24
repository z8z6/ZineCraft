---
name: zinecraft-effects
description: Add or revise reusable Zinecraft client presentation effects, including VFX IDs, particles or Photon assets, sounds, animations, and weapon or skill cues. Gameplay damage and status remain server-owned.
---

# Zinecraft 特效与表现

Catalog 中登记 ID 不等于已有可见或可听实现；每个 cue 都要有明确客户端消费者。

## 当前入口

- VfxBuilder、AnimationBuilder、SoundBuilder、WeaponPresentationBuilder
- VfxCatalog、AnimationCatalog、SoundCatalog、ModWeaponPresentation
- WeaponVfxServices、VanillaWeaponVfxService、VanillaWeaponSoundService
- PhotonWeaponVfxService、PhotonWeaponEffects、sounds.json

## 修改流程

1. 用 VfxBuilder / AnimationBuilder 声明稳定 ID；Catalog 只校验和收集。每个进入普通世界播放链路的 VFX ID 都要在 vanilla 或 Photon 路径显式映射；纯资料/Ponder ID 在接入普通世界前补映射。
2. 未知 VFX/声音 ID 当前会静默无效果。Photon 单项失败只有在同一 ID 已有 vanilla 映射时才有可见降级，不能把“fallback”当作自动保证。
3. 当前 WeaponAnimationService 使用 NoopWeaponAnimationService，武器动画不播放；VanillaPlayerAnimationService 对任意动画 ID 只触发主手挥动。新增真实动画前需实现 backend 与资源。
4. 所有时间线声音先用 SoundBuilder 在合适注册类声明稳定 cue ID；VanillaWeaponSoundService 可把它映射到原版 SoundEvent。自有音频另补 OGG、sounds.json、subtitle 与播放映射，不必强制放入 ModSound。SoundEvent ID 不等于音频路径。
5. WeaponPresentationBuilder 的 duration 必须大于 0，cue 满足 0 <= tick < duration，并只绑定该武器已有动作。技能 VFX 仍需单独的普通世界消费者。
6. 所有表现类保持客户端隔离；伤害、治疗、状态和消耗由 $zinecraft-weapons 或 $zinecraft-skills 实现。

## 验证

运行 ./gradlew.bat test、./gradlew.bat runData 和 ./gradlew.bat build。逐 cue 检查开始、取消、远端实体和时间线；分别测试 Photon 成功、Photon 失败、vanilla 映射、未知 ID 静默行为和 subtitle，并启动专用服务端确认客户端类隔离。
