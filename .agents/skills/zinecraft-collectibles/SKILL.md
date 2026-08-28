---
name: zinecraft-collectibles
description: Add or revise Zinecraft Curios collectibles from sourced Integrated Strategies data, preserving names, tiered effects, lore, PNGs, provenance, and server-authoritative Minecraft adaptations.
---

# Zinecraft 集成战略藏品

`ModCollectible` 是唯一运行时数据源。每件藏品只有一个公开的 `CollectibleBuilder` 静态字段和一个 `CollectiblePower`（`CombatStat -> CombatStat`）；导入脚本产物只用于生成注册代码、资源和审计材料，不是第二套 JSON 运行时。

## 当前入口

- 注册与展示：`ModCollectible`、`CollectibleBuilder`、`CollectibleCatalog`、`CollectibleItem`
- 运行时：`CollectiblePower`、`CollectibleCombatStats`、`CollectibleExplorationEffects`、`CombatStat`
- 服务端消费者：`CombatService`、`CollectibleEffectRuntime`、`EnemySpawnStatService`
- 导入：`script/import_prts_is2_collectibles.py`、`script/import_prts_all_collectibles.py`
- 来源与审计：`docs/item/PRTS_COLLECTIBLES.md`、`docs/item/PRTS_ADDITIONAL_COLLECTIBLES.md`、`docs/item/unimplemented-collectible-effects.md`

## 数据与注册约束

1. 中文名、原效果、描述和 PNG 以 PRTS 等指定来源为准，不自行改写或推断。英文名优先使用 ArkData；上游缺失时允许暂用中文名人工翻译，但必须在来源账本中标明。图片仅在确实找不到原资源时才生成。
2. 同名藏品只保留已有声明，不重复注册；灰蕈秘境藏品不加入追加批次。当前总约束为 742 个公开 `CollectibleBuilder` 静态字段，其中追加导入器维护 497 件。
3. 每件藏品直接声明在 `ModCollectible`；不要创建 `AdditionalCollectiblesXX` 等内部类。字段名、注册路径与 PNG 文件名使用一致的英文 `UPPER_SNAKE_CASE` / `lower_snake_case` 名称，不用编号占位。
4. 不保存或传递 `orderId`。来源编号只能留在导入缓存、账本或审计数据中，不进入 `CollectibleBuilder` 和运行时定义。
5. 同一藏品的多档效果全部保留为独立 `TierDefinition`，由 `collectibleEffectTier` 或对应特殊条件在运行时选择；不要只保留最强档。
6. 纹理、Curios relic tag、模型、双语键、SHA-256、来源账本和审计快照随注册变化同步。图片摘要变化必须人工确认。
7. 原效果与描述的分行属于来源文本，不按字符数、空格、标点或界面宽度自行折行，也不合并来源中明确存在的换行。没有显式换行的原文只生成一个 Tooltip 文本行；显示宽度问题应在渲染层处理，不能通过改写来源文本解决。

## 效果实现约束

1. 数值效果通过不可变 `CombatStat` 字段方法显式组合。不要在运行时解析 PRTS 文本，不要恢复 `prtsRule`、`CombatStat` 枚举、`CombatStatModifier` 或第二套属性模型。
2. 导入期将效果分类为：
   - `implementedRule`：已有服务端消费者，能够忠实执行；
   - `registeredRule`：已登记为实际 `CombatStat` 效果，属于完全实现；
   - `partialRule`：只执行可忠实分离的部分，并保留完整原规则；
   - `sourceRule`：尚无忠实实现，只登记原规则。
   对外只显示三种实现状态：`implementedRule` 与 `registeredRule` 均为“完全实现”，`partialRule` 为“部分实现”，`sourceRule` 为“未实现”。
3. 每件藏品必须设置 `CollectibleImplementationStatus`。完全实现的 Tooltip“装备效果”直接复用来源中的中英文原效果，不显示“已登记”或通用实现文案；部分实现和未实现才显示对应适配说明，并以 `sourceRules` 保留完整原规则。实现状态只描述运行时忠实度，不参与效果结算。
4. 导入器不得生成或恢复 `explorationRule`。能通过现有 `CombatStat` 消费者实际生效的资源字段使用 `registeredRule`，并在导入审计中归入完全实现；只有缺少消费者或原条件未完整覆盖时才使用 `partialRule` / `sourceRule`。
5. 条件、触发时机和作用域是效果语义的一部分。不能因为句子中出现希望、源石锭或属性数值，就把条件效果无条件应用；无法安全拆分时保守使用 `sourceRule`。
6. 目标生命、临时目标生命和护盾值统一复用 `maxHealth` / `addMaxHealth`，不要为它们新增独立字段。行动力、抗干扰指数、坍缩值、负荷临界点、思绪、烛火等主题资源可使用独立探索字段。
7. `oneTimeFailureRecoveryObjectiveLife` 近似不死图腾：服务端在非 `BYPASSES_INVULNERABILITY` 的致命伤害确认阶段，从已装备藏品中选取恢复值最高的一件，取消死亡，将当前生命设为不超过最大生命的登记恢复值，向对应玩家发送实际藏品堆栈用于第一人称触发展示，并销毁触发藏品。原规则若还包含“非区域最终战斗”“继续探索”等未落地语义，仍标记为部分实现并保留完整原规则。
8. “职业”只指八种 `SkillProfession`。职业藏品分别登记到先锋、近卫、狙击、术师、重装、医疗、辅助、特种字段，并且只在对应职业的技能计算中生效。近战、远程、高台、地面和分支职业不能冒充 `SkillProfession`。
9. 敌方最大生命、攻击、防御和攻击速度只通过 `enemySpawnStatEffects` 在首次生成时固化；敌方减攻映射为我方 `damageReduction`，敌方减防映射为我方 `defenseIgnore`。敌方移速降低和重量忽略使用专用字段。
10. 敌方受到的物理与法术伤害分别使用 `enemyPhysicalDamageTakenBonus`、`enemyMagicDamageTakenBonus`，作为防御/法抗结算后的独立最终乘区，不混入攻击或防御属性。
11. 投币玩具等按源石锭数量变化的效果读取当前源石锭总数；探索运行时通过 `withOriginiumIngots` 写入当前值，不把“获得源石锭”的增量误当作当前总数。
12. 每秒执行的回复或能力登记到 `perSecondEffects`；每秒重新判断的条件登记到 `perSecondConditionalEffects`；击杀触发登记到 `killEffects`。服务端聚合全部已装备藏品，不假定只有一个 relic 槽。
13. 需要寒冷、冻结、麻痹、晕眩、停顿、束缚或新战斗状态时使用 `$zinecraft-mob-effects`，通过 `CombatStatusService` 接入状态时长修正，不用无关原版药水近似。

## 未实现效果

无法忠实映射的规则保留在 `sourceRules`，并同步更新 `docs/item/unimplemented-collectible-effects.md` 的分类和数量。部分实现也必须保存完整原规则。Tooltip 和 `CollectibleEffectDisplay` 只负责展示，不参与结算。

`script/import_prts_all_collectibles.py` 是追加藏品显式效果和自动审计的生成入口。修改分类器时至少覆盖以下回归风险：条件资源不得无条件生效、多档不得丢失、职业连续属性必须落到同一个 `SkillProfession` 字段、目标生命/临时目标生命/护盾必须复用 `maxHealth`。

## 验证

按改动范围执行：

- 导入器：`python -m unittest discover -s script/tests`
- IS2 数据约束：`python script/import_prts_is2_collectibles.py --skip-images`
- 追加藏品只读校验：`python script/import_prts_all_collectibles.py --skip-images`
- Java 编译：`./gradlew.bat compileJava`
- 函数式变化：`./gradlew.bat test`
- 注册、语言、模型或标签变化：`./gradlew.bat runData`
- Tooltip 文本变化：确认无显式换行的原文只生成一个可见文本行，含显式换行的原文逐行原样保留；中英文为对齐翻译键补充的不可见占位行不计入来源分行
- 实现状态变化：抽查生成的双语 `.minecraft_effect`；完全实现项必须与对应原效果一致，部分实现和未实现项必须显示各自适配说明

最终核对 742 个公开字段、497 个追加工厂、全部藏品均有实现状态、无 `explorationRule`、无同名重复、无编号路径、无 `orderId`、无追加内部类，并确认 PNG 与英文路径、审计数量和生成资源一致。不默认运行已移除的 Gradle `check`。
