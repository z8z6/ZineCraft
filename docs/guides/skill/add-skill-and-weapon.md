# 添加技能与武器

技能资料使用 [ModSkill.java](../../src/main/java/com/cxxcxx/zinecraft/core/skill/ModSkill.java)，可执行技能效果使用 `SkillEffectBuilder`；武器动作集中在 [ModWeapon.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModWeapon.java)。

## 添加技能资料

```java
public static final SkillBuilder EXAMPLE_SKILL = skill("skill_example", "示例技能")
    .enUs("Example Skill")
    .operator("示例干员", "Example Operator", SkillProfession.GUARD)
    .activation(SkillSpRecoveryType.AUTO_RECOVERY, SkillTriggerType.MANUAL)
    .stats(10, 30, 20)
    .description("技能说明。", "Skill description.")
    .effect(vfx("skill/example"))
    .theme(SkillDemoTheme.AREA_SLASH)
    .build();
```

`damage(...)` 只生成资料与 tooltip，不会自动造成伤害。真正可施放的能力还需要 SkillEffect，并通过 CombatService 在服务端结算。

### SkillBuilder 参数

| 调用 | 参数含义 |
| --- | --- |
| `skill(path, zhCn)` | 技能/资料物品共用 ID 与中文名。 |
| `.enUs(name)` | 技能英文名。 |
| `.operator(zh, en, profession)` | 干员中英文名和八种 `SkillProfession` 之一。 |
| `.activation(recovery, trigger)` | 技力回复类型和触发类型，必须使用项目枚举。 |
| `.stats(initialSp, spCost, duration)` | 初始技力、消耗技力、持续秒数；瞬时或弹药技能传 `null`。这些目前主要是资料字段。 |
| `.damage(multiplier, type)` | 单个直接伤害段；`2.5` 表示攻击力 250%，type 是物理、法术等类型。可调用多次。 |
| `.description(zh, en)` | 双语技能说明。 |
| `.effect(vfx)` | 登记一个展示用 VFX ID；不自动播放。 |
| `.theme(theme)` | Ponder 演示主题。 |
| `.build()` | 校验必要字段并注册技能资料物品。 |

## 添加武器

1. 声明承载物品。
2. 用 `WeaponActionBuilder` 注册攻击、装填、瞄准或施法动作。
3. 用 `WeaponBuilder` 将每种 `WeaponInput` 绑定到唯一动作。
4. 为动作绑定动画、VFX 和声音时间线。

`WeaponActionBuilder` 的第一个参数是稳定动作 ID，第二个参数是接收最终 ResourceLocation 并创建动作实例的工厂。`WeaponBuilder.action(input, action)` 中的 `input` 是 PRIMARY、SECONDARY 或 RELOAD；同一输入只能绑定一个动作。`presentation(action, callback)` 只描述客户端时间线，不负责命中和伤害。

客户端 payload 只能请求动作，不能提交目标、伤害或命中结论。服务端会从玩家主手重新解析武器并校验时序、射程、弹药和命中。

## 验证

除常规数据生成与构建外，应在专用服务端测试重复/伪造请求、换手、丢弃、登出取消、弹药和动作互斥；双客户端验证其他玩家能看到正确表现。
