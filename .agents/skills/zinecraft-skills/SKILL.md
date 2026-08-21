---
name: zinecraft-skills
description: Add or revise Zinecraft operator skills, skill items, combat profiles, activation metadata, executable effects, Ponder demonstrations, and reusable VFX bindings. Use for operator abilities, not generic item behavior or weapon actions alone.
---

# Zinecraft 技能

区分“资料物品/Ponder 技能”与“可执行服务端技能效果”；前者不会自动产生后者。

## 建立上下文

阅读 `AGENTS.md`、工作树和：

- `api/registry/builder/SkillBuilder.java`、`SkillEffectBuilder.java`
- `api/registry/catalog/SkillCatalog.java`、`SkillEffectCatalog.java`
- `api/skill/`、`core/skill/ModSkill.java`、`SkillRuntime.java`
- `core/registry/ModWeaponSkillEffects.java`、`core/registry/ModWeapon.java`
- `src/client/java/com/cxxcxx/zinecraft/core/client/ponder/` 与 `script/generate_skill_ponder_scene.py`

## 实现

1. 从官方/PRTS核实技能名、干员、职业、技力回复/触发、初始技力、消耗、持续时间、描述与视觉依据；Minecraft 改编与原文分开表达。
2. 在 `ModSkill` 用 `SkillBuilder` 填齐英文、干员/职业、激活、数值、描述、至少一个 VFX 和 Ponder theme。直接伤害用
   `.damage(multiplier, type)`；辅助、治疗或控制技能保持空伤害段。
3. `SkillBuilder` 只注册 `SkillItem`、tooltip、伤害资料和 Ponder 元数据；它不会自动实现 SP、施放或玩法。可用技能必须另建
   `SkillEffect`，经 `SkillEffectBuilder/SkillEffectCatalog` 登记，并由 `CastSkillAction` 或明确的服务端状态机触发。
4. 服务端验证施放者、武器/物品、资源、目标、范围、持续时间和伤害，统一走 `CombatService`。避免物品 use、武器动作和事件监听器重复结算。
5. `SkillBuilder.effect(VfxBuilder)` 当前也没有普通世界自动播放消费者；真实播放需接明确 S2C 表现时间线。具体资产使用
   `$zinecraft-effects`。Ponder 仅是教学演示，不能代替玩法验证。
6. 若 runtime 尚不支持原机制，保留资料并标记“未适配”，不要以无关药水或属性近似。

## 验证

运行 Ponder 场景生成器后，执行 `./gradlew.bat test`、`./gradlew.bat runData`、`./gradlew.bat build`。核对 tooltip、模型、创造页、特效
ID、Ponder NBT 和独立维护的任务 SNBT；可执行效果须在专用服务端和双客户端验证授权、资源、持续时间、同步与重复请求。
