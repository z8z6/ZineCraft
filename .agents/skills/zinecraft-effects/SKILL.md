---
name: zinecraft-effects
description: Add or revise reusable Zinecraft client presentation effects, including VFX IDs, particles or Photon assets, animations, sounds, and timed weapon or skill cues. Use for audiovisual presentation only; gameplay damage and status remain server-owned.
---

# Zinecraft 特效与表现

创建可复用的客户端表现资源；Catalog 中登记 ID 不等于已有真实播放实现。

## 建立上下文

阅读 `AGENTS.md`、工作树和：

- `api/registry/builder/VfxBuilder.java`、`AnimationBuilder.java`、`SoundBuilder.java`、`WeaponPresentationBuilder.java`
- `api/registry/catalog/VfxCatalog.java`、`AnimationCatalog.java`、`SoundCatalog.java`
- `core/registry/ModWeaponPresentation.java`、`ModSound.java`
- `src/client/java/com/cxxcxx/zinecraft/core/client/weapon/`
- `src/client/java/com/cxxcxx/zinecraft/core/client/compat/photon/`

## 实现

1. 优先使用官方/PRTS已有图标、CG、音效或视觉资料并记录来源与权利；找不到时才制作符合现有风格的资产，不冒充原资源。
2. 用 `VfxBuilder`/`AnimationBuilder` 声明稳定 ID；这两种 Catalog 只校验和收集。新增 VFX 至少在 `VanillaWeaponVfxService`
   提供可见 fallback；可选 Photon 工厂失败或未知 ID 时仍降级。真实动画也必须有对应 backend/资源。
3. 声音用 `SoundBuilder`/`ModSound` 注册，若使用自有音频同步 OGG、`sounds.json`、subtitle 和播放服务映射。SoundEvent 注册 ID
   与音频资源路径不是一回事。
4. `WeaponPresentationBuilder` 的持续时间必须大于 0，动画 target 正确，cue 满足 `0 <= tick < duration`，且只引用该武器已绑定动作。技能引用
   VFX 仍需明确的普通世界播放消费者。
5. Photon/表现类只留在客户端，并通过现有安全选择机制加载。VFX、动画、声音只表达服务端已确认动作；伤害、治疗、状态和资源消耗由
   `$zinecraft-weapons` 或 `$zinecraft-skills` 实现。

## 验证

运行 `./gradlew.bat test`、`./gradlew.bat runData`、`./gradlew.bat build`。逐 cue 检查开始、取消、远端实体和时间线；分别测试有/无
Photon、未知 ID fallback、音频 subtitle，并启动专用服务端确认客户端类隔离。
