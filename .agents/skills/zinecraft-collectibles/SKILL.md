---
name: zinecraft-collectibles
description: Add or revise Zinecraft Curios collectibles from PRTS Integrated Strategies data, preserving sourced names, original effects, lore, PNGs, rights records, and server-authoritative Minecraft adaptations.
---

# Zinecraft 集成战略藏品

当前运行时以 Java `ModCollectible` 为唯一数据源；导入 JSON 只是审计缓存，不能把它误当作运行时目录。

## 建立上下文

阅读根目录 `AGENTS.md`，检查工作树，并阅读：

- `core/registry/ModCollectible.java`
- `api/registry/builder/CollectibleBuilder.java` 与 `api/registry/catalog/CollectibleCatalog.java`
- `api/collection/CollectibleItem.java`、`CollectiblePower.java`、`CollectibleCombatStats.java`、
  `CollectibleExplorationEffects.java`
- `script/import_prts_is2_collectibles.py`、`script/tests/test_import_prts_is2_collectibles.py`
- `script/data/prts_is2_image_sha256.json` 与现存来源账本（若有）

Java 路径均相对于 `src/main/java/com/cxxcxx/zinecraft/`。

## 资料与注册

1. 联网逐件核对 PRTS/游戏数据中的编号、中文名、原效果、描述与原始 PNG；禁止推断或改写中文原文。英文是项目翻译时不得声称为
   PRTS 原文。
2. 保持已有公开 ID；新 ID 使用稳定 `snake_case`。`orderId` 必须是三位数字或 `PCSdd`，并保持系列内唯一。
3. 在 `ModCollectible` 用 `CollectibleBuilder` 注册，更新 `ALL`/期望数量约束。PNG 放
   `assets/zinecraft/textures/item/<id>.png`；同步 `data/curios/tags/item/relic.json`、来源记录和 SHA-256 清单。模型与
   tooltip 翻译由 `runData` 生成。
4. 导入脚本负责核验固定游戏数据、数量、字段、PNG 文件头和摘要，并写审计快照、Curios 标签和来源材料；它不生成
   `ModCollectible.java`。新增其他集成战略主题时建立独立系列流程，不破坏 IS2 固定集合。

## 服务端玩法

探索资源使用 `CollectiblePower`，战斗属性使用 `CollectibleCombatStats` 与允许的 collectible modifier phase，回复使用
`CollectiblePower.Regeneration`。效果由服务端聚合所有已装备的 `CollectibleItem`；不要假定只存在一个 relic 槽。

无法忠实映射的原规则使用现有 `sourceRule(...)`/资料型能力，保留原文并明确尚未适配。不要用幸运、经验或无关药水效果近似。回复量、间隔、倍率和重复属性必须通过现有校验；客户端
tooltip 不承担结算。

## 验证

```powershell
python -m unittest script.tests.test_import_prts_is2_collectibles
python script/import_prts_is2_collectibles.py --skip-images
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
```

检查目标数量、Curios 标签、双语 tooltip、PNG SHA-256、模型、独立创造页、JAR 内容和 `git diff --check`
；在专用服务端验证装备、属性聚合与回复。只有人工确认上游图片变化后才更新摘要。
