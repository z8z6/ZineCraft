---
name: zinecraft-collectibles
description: Add or revise Zinecraft accessory items based on PRTS Integrated Strategies collectibles. Use when importing collectible names, original effects, lore or PNGs; extending the Trinkets relic slot; mapping Arknights effects to server-authoritative Minecraft gameplay; or maintaining collectible source and rights records.
---

# Zinecraft 集成战略藏品

通过现有 `CollectibleCatalog` 和 Trinkets 专用槽位实现可装备藏品。保留 PRTS 中文原文和原始 PNG，只为 Minecraft
增加明确标注的适配效果。

## 建立上下文

1. 完整阅读仓库根目录 `AGENTS.md` 和相邻的 `../zinecraft-content/SKILL.md`。
2. 检查 `git status --short`，保留用户已有修改。
3. 阅读以下现有实现，不重复建立平行系统：
    - `src/main/kotlin/com/cxxcxx/zinecraft/api/accessory/CollectibleCatalog.kt`
    - `src/main/kotlin/com/cxxcxx/zinecraft/core/item/ModCollectibles.kt`
    - `src/main/resources/data/trinkets/`
    - `docs/item/PRTS_COLLECTIBLES.md`
    - `script/import_prts_is2_collectibles.py`

## 选择和核对藏品

1. 联网打开对应主题的 PRTS 收藏品总表，逐件核对编号、中文名、原效果、原描述和图片链接。
2. 一批优先选择效果能忠实映射到 Minecraft 的同类藏品。不要为了数量把希望、部署费用、招募券等局内资源效果伪装成无关属性。
3. 使用导入脚本生成的稳定 ID。已有公开 ID 必须通过脚本的 `LEGACY_PATHS` 保持兼容；
   批量新增条目使用 `collectible_is2_<档案编号>`，避免中文转写变化。检查注册、纹理名、
   标签值和来源表完全一致。
4. 直接从 `torappu.prts.wiki` 或 PRTS 页面给出的原始资源链接下载 PNG。不得使用图像生成，不重绘，不用占位图替代。
5. 中文原效果和描述逐字保留；英文译文属于项目适配文本，不得声称是 PRTS 原文。

## 实现玩法

《傀影与猩红孤钻》的档案资料由
`src/main/resources/zinecraft/collectibles/phantom_crimson_solitaire.json` 驱动，
`ModCollectibles` 统一注册，不要再为每件藏品手写重复 Kotlin 声明：

- 普通属性藏品优先使用现有 `attribute(...)` 辅助函数。
- 防御百分比换算为以 20 点满护甲为基准的盔甲值，例如 15%/25%/35% 对应 +3/+5/+7。
- 生命百分比使用 `Attributes.MAX_HEALTH` 与 `ADD_MULTIPLIED_BASE`。
- 近战攻击百分比使用 `Attributes.ATTACK_DAMAGE` 与 `ADD_MULTIPLIED_TOTAL`；弓弩不读取该属性，符合近战限定。
- 每秒回复使用服务端 `CollectiblePower.Regeneration`，不得在客户端产生生命变化。
- 需要闪避、受治疗增幅、对敌减益或触发式效果时，先在 `CollectiblePower` 中增加语义明确、参数受校验、服务端权威的新能力；不要用速度、幸运等不相关属性近似。
- 暂时无法忠实映射的效果使用 `CollectiblePower.ArchiveOnly`，保留可装备物品、原效果和原描述，
  并在 Minecraft 效果行明确说明尚未适配。

每个 tooltip 必须同时显示：主题与编号、PRTS 原效果、PRTS 原描述、Minecraft 装备效果。Minecraft 适配与原效果存在范围差异时明确写出“装备者”。

## 接入资源

1. 运行 `python script/import_prts_is2_collectibles.py`。需要离线复核时传入
   `--game-data <roguelike_topic_table.json>`；只重建元数据时使用 `--skip-images`。
   脚本默认也会核对 `script/data/prts_is2_image_sha256.json` 中的逐图摘要；只有人工核对过
   PRTS 图片确实变更后，才使用 `--update-image-digests` 显式更新受版本控制的清单。
2. 脚本生成藏品目录、Trinkets `chest/relic` 标签和来源清单，并直接从 PRTS 下载 PNG；
   不手动复制或转换图片。
3. 脚本必须校验预期数量、必填字段、ID 唯一性、PNG 文件头和摘要；所有输出先暂存并整体发布，
   发布失败时回滚，不生成占位图或留下部分更新。
4. 新增忠实玩法适配时，只在 `ModCollectibles.powerOverrides` 中按来源 ID 增加能力。
5. 常规模型和翻译由目录数据生成，不手写重复 JSON。只有新增顶层内容对象时才改初始化入口。

## 验证

按顺序执行，不能合并为同一次 Gradle 调用：

1. `./gradlew.bat runDatagen`
2. `./gradlew.bat build`

随后检查：

- 新模型引用对应纹理；
- 中英文语言文件包含名称、原效果、描述和适配效果；
- 所有手写 JSON 可解析；
- 构建 JAR 包含 Kotlin 类、PNG、模型和 Trinkets 标签；
- `git diff --check` 与 `git status --short` 没有无关修改。

完成报告列出新增藏品、玩法映射、PRTS 来源、验证结果，以及尚未进行的游戏内手动测试。
