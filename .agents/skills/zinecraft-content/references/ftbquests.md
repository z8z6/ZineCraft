# FTB Quests 任务指引

## 存储与安装边界

- FTB Quests 1.21.1 的任务定义位于 `config/ftbquests/quests/`，不是数据包注册表资源。
- 发布模板放在 `src/main/resources/zinecraft/ftbquests/quests/`，通过 `FtbQuestGuideInstaller` 安装到配置目录。
- 安装器只能创建缺失的 Zinecraft 专属章节；FTB Quests 2101.1.x 的语言表是 `lang/<locale>.snbt`，必须只合并缺失键，不得替换已有译文。
- 新增章节应使用独立的 `chapters/<name>.snbt`；不要覆盖任务书的 `data.snbt`、`chapter_groups.snbt` 或整份集中语言表。

## 任务格式

每个章节、任务、Task 和 Reward 都需要稳定且全局唯一的 16 位十六进制 ID。Minecraft 1.21.1 的物品栈使用组件格式：

```snbt
{
	filename: "example"
	id: "5000000000000001"
	quests: [{
		id: "5000000000000002"
		tasks: [{ dimension: "zinecraft:terra", id: "5000000000000003", type: "dimension" }]
		x: 0.0d
		y: 0.0d
	}]
}
```

常用任务：

- 维度访问：`{ dimension: "zinecraft:terra", type: "dimension" }`
- 群系访问：`{ biome: "zinecraft:laterano_holy_fields", type: "biome" }`
- 获得物品：`{ item: { count: 1, id: "zinecraft:test_rifle" }, type: "item" }`
- 教学确认：`{ type: "checkmark" }`

文本放在对应语言 SNBT 中，键格式为 `chapter.<ID>.title`、`quest.<ID>.title`、`quest.<ID>.quest_subtitle`、
`quest.<ID>.quest_desc` 和 `task.<ID>.title`。至少提供 `zh_cn` 与 `en_us`。

## 创建流程

1. 先列出章节目标、自动验证条件、手动教学动作、依赖线和奖励，避免直接在 SNBT 中边写边设计。
2. 为 Chapter、Quest、Task 和 Reward 分配稳定的 16 位大写十六进制 ID；已发布 ID 不得因排版调整而改变。
3. 在 `quests/chapters/<name>.snbt` 创建独立章节。优先使用 FTB 原生维度、群系和物品 Task。
4. 同步向 `quests/lang/en_us.snbt` 与 `quests/lang/zh_cn.snbt` 添加相同键集合。2101.1.x 不扫描按章节嵌套的语言目录。
5. 将新章节和语言片段加入 `FtbQuestGuideInstaller`：章节只在缺失时创建，语言表只合并缺失键。
6. 若仓库提供 `.agents/skills/zinecraft-content/scripts/validate_ftbquests.ps1`，运行它检查
   ID、依赖与双语键；若脚本不存在，使用仓库现有验证脚本或显式检查 SNBT。
7. 依次运行 `runData` 和 `build`，再启动开发世界验证任务进度与奖励。

## 设计规则

- 能由 FTB Quests 原生验证的维度、群系和物品任务使用自动任务，不用手动勾选替代。
- 武器射击、装填和施法尚无原生 Task；使用物品 Task 加 Checkmark 教学确认，不得让客户端按键或动画直接写任务进度。
- 依赖外部 TaCZ 枪包、服务器配置或其他可选资源的任务必须设为 `optional: true`，不能阻塞基础主线。
- 奖励物品必须确实存在且能构造有效默认 ItemStack；动态 TaCZ 枪械不能用缺少枪械数据组件的普通物品奖励冒充。
- 关系图使用 Quest 节点和 `dependencies` 绘制连线；`quest_links` 是跨章节快捷入口，不是图的边。FTB
  依赖线颜色表示任务进度，不能用来编码外交正负，关系含义与数值必须写入节点详情。
- 关系网只绘制有明确资料依据的重要关系，不要把系统补齐的全部中立默认边画出；无连线的含义应在图例中说明。

## 验证

1. 先运行 `scripts/validate_ftbquests.ps1`，再运行数据生成和完整构建，确认内置 SNBT 被收入发布 JAR。
2. 启动带 FTB Quests 的开发客户端，让安装器写入 `run/config/ftbquests/quests/`。
3. 检查日志不存在 SNBT 解析、未知 Task 类型、未知物品、维度或群系错误。
4. 使用 `/ftbquests reload quests` 验证重载，并在新世界实际完成一次维度、群系和物品任务。

项目说明见 `docs/quest/README.md`，当前真实模板为 `zinecraft_guide.snbt` 与 `terra_relations.snbt`。
