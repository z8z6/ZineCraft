# 战斗数值机制

Zinecraft 的武器、技能与藏品统一使用服务端权威的 `api/combat` 数值层。公式以 PRTS
“游戏数据基础”和“伤害分类”为依据；客户端动画、Photon 特效和 TaCZ 模型均不得结算玩法数值。

## 五项基础属性

| 属性             | 语义             | 范围/基准                      |
|----------------|----------------|----------------------------|
| `MAX_HEALTH`   | 最大生命值          | 不低于 0；同步到 Minecraft 最大生命属性 |
| `ATTACK`       | 本次攻击或治疗的攻击力基础值 | 不低于 0；对近战、枪械、法术和治疗统一生效     |
| `DEFENSE`      | 物理防御           | 不低于 0；当前由 Minecraft 盔甲值承载  |
| `RESISTANCE`   | 法术抗性           | 0–100；当前由 Minecraft 盔甲韧性承载 |
| `ATTACK_SPEED` | 攻击速度           | 基准 100，最终限制在 20–600        |

## 属性修正顺序

同一属性按以下顺序计算：

```text
藏品基础 = (基础值 + Σ藏品固定值) × max(0, 1 + Σ藏品百分比)
最终属性 = 最终倍率连乘 × ((藏品基础 + Σ直接加算) × max(0, 1 + Σ直接乘算) + Σ最终加算)
```

对应 API 阶段依次为 `COLLECTIBLE_ADDITION`、`COLLECTIBLE_MULTIPLIER`、
`DIRECT_ADDITION`、`DIRECT_MULTIPLIER`、`FINAL_ADDITION`、`FINAL_SCALER`。
藏品同类百分比相加；最终倍率逐项相乘。未来藏品不得直接修改某一种武器的伤害。

## 伤害、治疗与攻速

先计算基础伤害：

```text
A = 攻击力 × 攻击倍率 + 附加攻击力
```

- 物理：`max(5% × A, A - (1 - 百分比穿透) × max(0, 防御 - 固定穿透))`
- 法术：`max(5% × A, A × max(0, 100 - (1 - 百分比穿透) × max(0, 法抗 - 固定穿透)) / 100)`
- 真实：`A`
- 治疗：`攻击力 × 治疗倍率 + 附加攻击力`
- 攻击间隔：`理论间隔 / (clamp(攻击速度, 20, 600) / 100)`

三类 Zinecraft 伤害源都绕过原版护甲结算，因为护甲/法抗已由上述公式处理；不得再调用普通
`playerAttack` 或 `indirectMagic` 造成同一次伤害。

## 接入规则

1. 武器只声明基础攻击力、伤害类型和可选 `CombatRequest`（倍率、附加攻击、穿透、最终倍率）。
2. 服务器命中后调用 `CombatService.damage`；治疗调用 `CombatService.heal`。
   武器动作和技能声明通过 `CombatDamageProvider.damageProfiles()` 公开统一的多段 `CombatDamageProfile` 列表；固定武器伤害使用
   `FLAT`，技能百分比伤害使用 `ATTACK_MULTIPLIER`，不直接造成伤害的内容返回空列表。
   每段伤害拥有独立类型；物理伤害走防御通道，魔法、法术、火焰、冰霜、雷电和毒素伤害走法抗通道，真实伤害不减免。
   命中结算可将完整列表传给 `CombatService.damage(attacker, target, profiles)`，服务会按顺序独立结算每一段。
3. 攻击冷却通过 `CombatService.attackIntervalSeconds` 计算；整数 tick 动作通过 `CombatService.actionTiming` 缩放命中/施法
   tick 和总时长。禁止把“攻击速度+N”解释为百分比。
4. 藏品用 `CombatStatBoost` / `CombatStatSet` 声明修正。最大生命、防御和法抗会桥接到实体属性；攻击与攻速由战斗服务读取，避免重复叠加。
5. TaCZ 命中前通过其公开 `EntityHurtByGunEvent.Pre` 注入统一攻击力。射速、弹种、爆头、护甲穿透和目标减伤仍由 TaCZ
   后端及枪包数据负责，以保持兼容性；因此 Zinecraft 攻速修正只作用于原生 Weapon Runtime。

明日方舟属性层用于解释藏品原效果和统一武器计算，但最终玩家属性会桥接回
Minecraft：生命、攻击、防御、法抗和攻击速度分别落到原版最大生命、攻击伤害、盔甲、盔甲韧性和攻击速度。攻击速度点数 `+N` 写入原版时换算为
`N/100` 的基础倍率。

玩家在物品栏的 L2 Library 原有“能力”页查看这些最终原版属性；项目不再绘制第二个能力面板。“饰品”页由 L2Tabs 的 Curios
集成提供。能力页是只读表现层，不替代服务端结算。

## 依据

- PRTS：[游戏数据基础](https://prts.wiki/w/%E6%B8%B8%E6%88%8F%E6%95%B0%E6%8D%AE%E5%9F%BA%E7%A1%80)
- PRTS：[伤害分类](https://prts.wiki/w/%E4%BC%A4%E5%AE%B3%E5%88%86%E7%B1%BB)
- TaCZ 1.21.1：公开 `EntityHurtByGunEvent` API
