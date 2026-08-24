---
name: zinecraft-collectibles
description: Add or revise Zinecraft Curios collectibles from sourced Integrated Strategies data, preserving names, effects, lore, PNGs, provenance, and server-authoritative Minecraft adaptations.
---

# Zinecraft 集成战略藏品

`ModCollectible` 是唯一运行时数据源；导入脚本产物是注册资源与审计材料，不是 JSON 运行时目录。每件藏品只有一个 `CollectiblePower`，即 `CombatStat -> CombatStat` 效果函数。

## 当前入口

- `ModCollectible`、`CollectibleBuilder`、`CollectibleCatalog`
- `CollectibleItem`、`CollectiblePower`、`CollectibleCombatStats`、`CollectibleExplorationEffects`
- `CombatStat`、`CombatService`、`CollectibleEffectRuntime`、`EnemySpawnStatService`
- `script/import_prts_is2_collectibles.py`、`script/data/prts_is2_image_sha256.json`
- `docs/item/PRTS_COLLECTIBLES.md`、`docs/item/unimplemented-collectible-effects.md`

## 修改流程

1. 保持公开 ID；`orderId` 使用三位数字或 `PCSdd`，并在系列内唯一。中文名、原效果、描述和 PNG 逐件核对 PRTS 等指定来源；项目英文翻译不要冒充上游原文。
2. 在 `ModCollectible` 用 `CollectibleBuilder` 注册，并保持 `ALL` 与 245 件 IS2 集合约束。纹理、Curios relic tag、SHA-256、来源账本和审计快照按实际改动同步。
3. 数值效果通过不可变 `CombatStat` 的字段方法组合；多项规则在同一个函数中链式返回新快照。不要恢复 `CombatStat` 枚举、`CombatStatModifier` 或第二套属性模型。
4. 每秒执行的回复或能力登记到 `perSecondEffects`；每秒重新判断并修改快照的条件登记到 `perSecondConditionalEffects`；击杀触发登记到 `killEffects`。服务端聚合所有已装备 `CollectibleItem`，不假定只有一个 relic 槽。
5. 敌方最大生命与攻击速度只通过 `enemySpawnStatEffects` 在首次生成时固化。敌方减攻适配为我方 `damageReduction`，敌方减防适配为我方 `defenseIgnore`；先聚合所有藏品，再统一限制和结算。敌方移速降低与重量忽略使用对应字段，不混入生成快照。
6. 需要寒冷、冻结、麻痹、晕眩、停顿、束缚或新战斗状态时使用 `$zinecraft-mob-effects`。通过 `CombatStatusService` 施加状态以接入敌方异常延长和我方异常减免；不要用无关原版药水近似。
7. 无法忠实映射的规则保留 `sourceRule`，并更新 `docs/item/unimplemented-collectible-effects.md` 的分类和数量。部分实现必须同时保存尚未实现的原规则；tooltip 与 `CollectibleEffectDisplay` 只展示，不参与结算。
8. 导入脚本会写文件并执行全量字段、数量、PNG 与摘要校验；图片摘要更新必须有人工确认。同步检查 Curios slot/entity 数据、relic loot consumers 和客户端属性展示。

## 验证

按改动范围选择验证，不默认运行已移除的 Gradle `check`。资料或注册变化运行 `python script/import_prts_is2_collectibles.py --skip-images` 和 `./gradlew.bat runData`；函数式战斗变化至少执行 `compileJava`，并核对 245 件、未实现审计数量、双语键、模型与 PNG 摘要。涉及服务端消费者时，在专用服务端验证多槽聚合、回复、伤害、生成快照或异常状态行为。