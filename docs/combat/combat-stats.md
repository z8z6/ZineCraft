# 藏品属性与效果模型

藏品的运行时数值统一由 `CombatStat` 表示。它不再是属性名称枚举，而是一份不可变快照，包含战斗、回复和集成战略探索字段。

## 数据流

```text
实体或基础值 -> CombatStat -> 依次应用已装备藏品的效果函数 -> 最终 CombatStat
                                   |
                                   +-> CombatStat.toVanillaModifiers -> Curios / 原版属性
```

每件藏品只有一个 `CollectiblePower`。它是 `CombatStat -> CombatStat` 的函数；复合效果在该函数内修改多个字段，多件藏品则按装备扫描顺序依次应用。`CombatStat` 不可变，修改方法都返回新快照。

## 字段

- 数值属性：最大生命、攻击、防御、法术抗性、攻击速度、伤害减免、防御无视、治疗与生命回复加成、两类技能技力回复、真实伤害加成、元素损伤加成、元素损伤减免、物理伤害闪避率、法术伤害闪避率、敌方异常状态延长、我方异常状态减免、敌方移动速度降低、敌方重量忽略，以及希望、源石锭等探索数值；原目标生命与临时目标生命统一复用 `maxHealth`。
- 每秒效果：`perSecondEffects` 函数列表，例如固定生命回复和最大生命比例回复。
- 每秒条件效果：`perSecondConditionalEffects` 函数列表，接收当次聚合快照并返回判断后的新快照，例如目标生命为 1 时增加攻击速度。
- 击杀效果：`killEffects` 函数列表，每次装备者击杀生物时依次执行。
- 敌方生成属性：`enemySpawnStatEffects` 函数列表，只在敌方第一次生成时固化生命与攻速。

尚未接入 Minecraft 运行系统的原作条件保存在藏品的 `sourceRules` 中；对应的效果函数使用恒等函数，避免伪造无关效果。来源规则是追溯信息，不是第二套数值模型。

### 回复与技力字段

- `healingAndHealthRegenerationBonus`：受到的治疗与生命回复倍率增量。多件藏品先相加，服务端在 `LivingHealEvent` 最终入口统一乘以 `1 + bonus`，因此 Zinecraft 技能治疗、藏品每秒回复、原版回复和其他模组回复共用同一结算。
- `offensiveDefensiveSkillPointRegeneration`：攻击回复和受击回复技能的额外每秒技力。
- `naturalSkillPointRegeneration`：自然回复技能的额外每秒技力。

两类技力字段均按“技力/秒”累加。目前项目尚无独立的技能技力运行时消费者；藏品已经登记到统一快照，后续技能系统应按回复类型读取对应字段，不能把它们转换成原版实体属性。

### 伤害与元素损伤字段

- `trueDamageBonus`：造成的真实伤害倍率增量。多件藏品先相加，Zinecraft 的 `CombatDamageType.TRUE` 在 `CombatService` 中统一乘以 `1 + bonus`。
- `elementalDamageBonus`：造成的元素损伤倍率增量。
- `elementalDamageReduction`：受到的元素损伤减免比例，最终限制到 `0～1`。
- `physicalDamageEvasionRate`：物理伤害闪避概率，多件藏品相加后限制到 `0～1`。
- `magicDamageEvasionRate`：法术伤害闪避概率，多件藏品相加后限制到 `0～1`。

这些字段均使用小数比例，`0.2` 表示 20%。两类闪避在服务端入伤事件中聚合后只判定一次，真实伤害不可闪避。Zinecraft 伤害类型按注册类型精确分类；其他带攻击来源的 Minecraft 伤害按“绕过护甲为法术伤害，否则为物理伤害”适配。项目目前尚无元素损伤条运行时，因此元素损伤加成与减免先完成聚合接口，后续系统应分别在最终损伤与最终受损入口读取，不能将 Minecraft 的火焰、冰霜等普通伤害类型误当作元素损伤。

### 异常状态持续时间字段

- `enemyStatusDurationBonus`：对敌方施加的一次性异常状态持续时间加成，多件藏品先相加；最终时长为 `baseTicks × (1 + bonus)`。
- `friendlyStatusDurationReduction`：我方承受的一次性异常状态持续时间减免，多件藏品先相加并限制到 `0～1`；最终时长为 `baseTicks × (1 - reduction)`。

`CombatStatusService` 是异常状态的统一施加入口，负责调用上述两个持续时间接口。现有状态为寒冷、冻结、麻痹、晕眩、停顿与束缚；重复施加寒冷会转为冻结。

| 状态 | 服务端语义 |
| --- | --- |
| 寒冷 | 攻击速度降低 30%；再次施加时移除寒冷并转为冻结 |
| 冻结 | 无法移动或造成攻击伤害，法术抗性 -15 |
| 麻痹 | 无法移动或造成攻击伤害 |
| 晕眩 | 无法移动或造成攻击伤害 |
| 停顿 | 移动速度降低 80%，仍可攻击 |
| 束缚 | 无法移动，仍可攻击 |

## 原版属性转换

`CombatStat.fromVanilla` 从实体创建战斗属性快照；`CombatStat.toVanillaModifiers` 将单件藏品的战斗效果转换为 Curios 使用的原版属性修饰器。映射集中在 `CombatStat`：

| CombatStat 字段 | Minecraft 属性 |
| --- | --- |
| `maxHealth` | `MAX_HEALTH` |
| `attack` | `ATTACK_DAMAGE` |
| `defense` | `ARMOR` |
| `resistance` | `ARMOR_TOUGHNESS` |
| `attackSpeed` | `ATTACK_SPEED`（以 100 为中性值） |

可转换的战斗效果必须是单字段仿射变换，例如固定加值或基础值倍率。非线性、依赖战斗事件或修改敌人的规则应由对应的服务端系统消费，不应伪装为原版属性修饰器。

### 敌方移动与重量字段

- `enemyMovementSpeedReduction`：所有敌方单位的移动速度降低比例，多件藏品先相加，读取时限制到 `0～1`。
- `enemyWeightIgnore`：推拉结算时忽略的敌方重量等级，多件藏品按等级相加且最低为 `0`。

这两个字段不属于生成快照：敌方生命与攻击速度仍是仅在第一次生成时固化的属性。当前项目尚无全局敌方移动更新器和推拉重量系统，后续消费者应动态聚合场上我方单位；重量按 `max(0, 敌方重量 - enemyWeightIgnore)` 读取。

## 敌方效果结算

敌方最大生命和攻击速度只在实体第一次生成时应用。效果被转换为永久 Minecraft 属性修饰器并随实体保存；磁盘重载、玩家更换藏品或同一实体重新加入世界时不重复计算。

敌方攻击降低适配为装备者的 `damageReduction`，敌方防御降低适配为装备者的 `defenseIgnore`。所有已装备藏品先把比例相加到同一个 `CombatStat`，随后在伤害事件中统一限制到 `0～1` 并只计算一次：

```text
最终伤害减免 = clamp(所有 damageReduction 之和, 0, 1)
最终防御无视 = clamp(所有 defenseIgnore 之和, 0, 1)
```

原版及其他模组的物理伤害通过 NeoForge `ARMOR` reduction modifier 应用防御无视；Zinecraft 自定义物理伤害把最终值一次性并入 `CombatRequest.percentPenetration`。敌方攻击、防御增加的负面规则不做反向适配，仍保留为未实现规则。

## 定义示例

```java
stats -> stats.multiplyAttack(0.35)

stats -> stats
    .multiplyMaxHealth(0.25)
    .addAttackSpeed(30.0)

stats -> stats.hope(2).originiumIngots(5)
```

多个效果使用 `CollectiblePower.combine(...)` 按声明顺序组合。最终结算入口读取修改后的字段，不再收集枚举修饰器并交给另一套公式解析。
