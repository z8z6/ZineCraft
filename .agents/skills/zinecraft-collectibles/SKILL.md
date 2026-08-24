---
name: zinecraft-collectibles
description: Add or revise Zinecraft Curios collectibles from sourced Integrated Strategies data, preserving names, effects, lore, PNGs, provenance, and server-authoritative Minecraft adaptations.
---

# Zinecraft 集成战略藏品

ModCollectible 是唯一运行时数据源；导入脚本产物是注册资源与审计材料，不是 JSON 运行时目录。

## 当前入口

- ModCollectible、CollectibleBuilder、CollectibleCatalog
- CollectibleItem、CollectiblePower、CollectibleCombatStats、CollectibleExplorationEffects
- script/import_prts_is2_collectibles.py 与对应 unittest
- script/data/prts_is2_image_sha256.json
- 固定来源账本 docs/item/PRTS_COLLECTIBLES.md

## 修改流程

1. 保持公开 ID；orderId 使用三位数字或 PCSdd，并在系列内唯一。中文名、原效果、描述和 PNG 逐件核对来源；项目英文翻译不要冒充上游原文。
2. 在 ModCollectible 用 CollectibleBuilder 注册，并保持 ALL 与 245 件 IS2 集合约束。纹理、Curios relic tag、SHA-256、来源账本和审计快照必须同步。
3. import 脚本会写文件并执行全量字段、数量、PNG 与摘要校验；unittest 只覆盖局部规范化/回滚行为，不能替代实际导入。更新图片摘要必须有人工确认。
4. 探索资源用 CollectiblePower，战斗属性用 CollectibleCombatStats，回复用 CollectiblePower.Regeneration。服务端聚合所有已装备 CollectibleItem，不假定只有一个 relic 槽。
5. 无法忠实映射的规则保留 sourceRule/资料能力并标记未适配；不使用无关幸运、经验或药水效果近似。tooltip 与 CollectibleEffectDisplay 只展示，不参与结算。
6. 同步检查 Curios slot/entity 数据、relic loot consumers 和客户端属性展示。

## 验证

依次运行 python -m unittest script.tests.test_import_prts_is2_collectibles、python script/import_prts_is2_collectibles.py --skip-images、./gradlew.bat test、./gradlew.bat runData 和 ./gradlew.bat build。build 的 check 已包含 verifyCollectibleJarResources。核对 245 件、账本、tag、双语键、PNG 摘要、模型和 JAR；在专用服务端验证装备聚合与回复。
