---
name: zinecraft-skills
description: Add or revise Zinecraft operator skill items, sourced metadata, executable server effects, combat profiles, Ponder demonstrations, and VFX bindings. Use for operator abilities, not generic item or weapon actions alone.
---

# Zinecraft 技能

SkillBuilder 生成资料物品和 Ponder 元数据；可执行玩法必须另有服务端 SkillEffect。

## 当前入口

- SkillBuilder、SkillCatalog、SkillEffectBuilder、SkillEffectCatalog
- api/skill/SkillSpRecoveryType.java、SkillTriggerType.java、core/skill/ModSkill.java
- ModWeaponSkillEffects、CastSkillAction、ModWeapon
- client/ponder/ZinecraftPonderPlugin.java、script/generate_skill_ponder_scene.py

## 修改流程

1. 在 ModSkill 用 SkillBuilder 写入干员/职业、激活资料、数值、描述、至少一个 VFX 和 Ponder theme。activation(...) 必须使用 SkillSpRecoveryType 与 SkillTriggerType，不要恢复中英文自由文本字段；遇到新的上游类型时先扩展枚举及其双语显示。damage(...) 只提供资料与 tooltip，不会自动结算。
2. 可施放技能另建 SkillEffect，并在 ModWeaponSkillEffects 或合适注册类中通过 SkillEffectBuilder 登记；由 CastSkillAction 或新的服务端入口调用 canCast/cast。
3. 当前 SkillCastContext 只有 ServerPlayer、ItemStack 和 hand，项目没有完整 SP、目标选择、持续技能状态机或专用技能网络协议。新增机制时必须同时设计服务端授权、状态与同步。
4. 可执行伤害自行提供 damageProfiles；伤害与治疗分别通过 CombatService 结算。不要让物品 use、武器动作和事件监听器重复结算。
5. SkillBuilder.effect(...) 没有普通世界播放消费者。真实表现需增加明确的 S2C 时间线并使用 $zinecraft-effects。
6. SkillRuntime 与 TestRapidFireSkill 属于旧 TaCZ 事件 MVP，当前注入 NONE，不是现行技能/VFX 实现范例。

## 验证

先运行 python script/generate_skill_ponder_scene.py，再执行 ./gradlew.bat test、./gradlew.bat runData 和 ./gradlew.bat build。核对回复/触发枚举的双语显示、资料物品、VFX ID、Ponder NBT 和任务 SNBT；可执行效果须在专用服务端和双客户端验证授权、资源、同步与重复请求。
