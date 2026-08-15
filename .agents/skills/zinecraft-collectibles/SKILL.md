---
name: zinecraft-collectibles
description: Add or revise Zinecraft Curios accessory items based on PRTS Integrated Strategies collectibles. Use when importing collectible names, original effects, lore or PNGs; extending the Curios relic slot; mapping effects to server-authoritative gameplay; or maintaining source and rights records.
---

# Zinecraft 集成战略藏品

通过 `CollectibleCatalog` 和 Curios `relic` 槽实现可装备藏品。保留 PRTS 中文原文和原始 PNG，只为 Minecraft 增加明确标注的适配效果。

## 建立上下文

1. 完整阅读根目录 `AGENTS.md` 和相邻 `../zinecraft-content/SKILL.md`。
2. 检查 `git status --short`，保留用户已有修改。
3. 阅读现有实现：
    - `src/main/java/com/cxxcxx/zinecraft/api/accessory/CollectibleCatalog.java`
    - `src/main/java/com/cxxcxx/zinecraft/core/item/ModCollectibles.java`
    - `src/main/resources/data/zinecraft/curios/`
    - `docs/item/PRTS_COLLECTIBLES.md`
    - `script/import_prts_is2_collectibles.py`

## 资料与资源

1. 联网逐件核对 PRTS 编号、中文名、原效果、原描述和图片链接；不要推断缺失资料。
2. 使用导入脚本的稳定 ID。已有公开 ID 保持兼容；批量条目使用 `collectible_is2_<档案编号>`。
3. PNG 直接来自 PRTS 原始资源链接，不生成、不重绘、不用占位图。中文原文逐字保留；英文是项目翻译，不声称为 PRTS 原文。
4. 运行导入脚本时校验数量、必填字段、ID、PNG 文件头与 SHA-256；只有人工确认上游图片变化后才更新摘要清单。

## 服务端玩法

目录 JSON 驱动全部藏品注册，不为每件藏品复制 Java 声明。普通属性使用现有 attribute helper；回复使用服务端
`CollectiblePower.Regeneration`。无法忠实映射的效果使用 `ArchiveOnly`，保留原文并明确说明尚未适配。

新增闪避、治疗增幅、减益或触发效果时，在 `CollectiblePower` 增加语义明确、参数受校验、服务端权威的能力；不要用无关属性近似。tooltip
同时显示主题/编号、PRTS 原效果、PRTS 原描述和 Minecraft 装备效果。

## 接入与验证

```powershell
python script/import_prts_is2_collectibles.py
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
```

检查模型纹理、双语文本、Curios `relic` 数据、全部 JSON、JAR 内 Java 类/PNG/模型，以及 `git diff --check`。完成报告列出玩法映射、PRTS
来源、权利说明和未进行的游戏内测试。
