# FTB Quests 泰拉远征指引

## 内容范围

项目内置二十五个章节，并使用两个章节组：

- `泰拉国家档案`：包含“十九国与特色建筑”总览，以及十九个独立国家章节。每章先列出国家设定、城市与聚落、重要地区、建筑与探索四个档案节点，再为该国每座正式结构提供一个服务端自动判定的访问任务。
- `开发与模组`：包含“开发环境模组说明”，区分发布必需依赖、传递依赖与仅供 `runClient` 使用的辅助模组。
- 未分组章节：`Zinecraft：泰拉远征`、`泰拉国家关系网`、`藏品图鉴` 与 `干员技能`，保留世界探索、武器教学、关系网络、藏品说明和技能训练。

`藏品图鉴`展示“傀影与猩红孤钻”的全部 245 件藏品，按攻击、治疗、防御、生存、综合战斗、探索、编队与招募、资源经营和特殊规则九类排布。每个节点使用真实藏品图标，介绍档案编号、PRTS
原效果、原描述和 Minecraft 装备方式；玩家持有对应藏品时，物品任务会自动完成。

`干员技能`展示当前注册的九种技能物品。每个节点列出干员、职业、技力回复和触发方式、初始技力、消耗、持续时间与技能说明；物品任务自动检查技能物品，Checkmark
用于确认玩家已查看对应 Ponder 演示。

远征章节包含三条相互衔接的内容：

1. 新玩家从泰拉开始，前往萨米唯一星门，并通过 `minecraft:overworld` 维度访问任务验证单向传送成功。
2. 分别访问泰拉维度中的 19 个国家群系；所有群系任务完成后可提交地理考察。
3. 领取测试剑、测试步枪、24 发测试弹药和测试法杖，按说明完成基础动作训练；TaCZ 枪械训练为不阻塞主线的可选任务。

关系网章节把 19 个国家作为可点击节点，以各国对应的特色方块作为稳定可渲染的标志，并用 FTB Quests 原生依赖线连接具有 PRTS
明确依据的重要关系。节点详情展示国家繁荣度、稳定度、军力、开放度、战争倾向，以及相关国家间的好感、战争欲望、贸易、紧张和信任。未连线的国家采用系统的中立默认关系；依赖线颜色只表示任务查看进度，不表示外交态度。

FTB Quests 原生自动检测维度、群系、结构与物品状态。结构任务使用 `type: "structure"` 和完整结构
ID，玩家进入有效结构边界后自动完成。武器动作目前仍使用物品检测加手动确认，避免让客户端动画或按键直接修改服务端任务进度。

十九个国家章节共包含 76 个档案节点，并覆盖 58 座正式结构：19 个聚落、38 个国家地标和拉特兰地下特殊结构 `laterano_host`
。国家设定来自 PRTS 国家条目；城市、聚落、城区与重要地区采用 `docs/nation/TERRA_GEOGRAPHY.md` 整理的 PRTS
地理目录。没有核实到英文名的地点在英文任务书中保留中文原名，不自行创造译名。

建筑节点明确区分资料与实现：正式结构是依据资料制作的 Minecraft 表达；每座城市独立注册的城市中心、住宅、商店、工坊和公共建筑仍属于
Blockout，未知外观不作为官方复原。萨米固定出口 `stargate`
不属于国家建筑访问任务；`victoria_defence_cannon_preview` 是评审预览结构，二者均不进入国家访问任务。

## 安装方式

FTB Quests 将任务书保存在运行目录的 `config/ftbquests/quests/`，而不是 Minecraft 数据包中。Zinecraft 因此把稳定模板放在
`src/main/resources/zinecraft/ftbquests/quests/`，并在 FTB Quests 已加载时由 `FtbQuestGuideInstaller` 安装章节、合并语言键：

```text
config/ftbquests/quests/
├─ chapter_groups.snbt
├─ chapters/development_mods.snbt
├─ chapters/collectibles.snbt
├─ chapters/operator_skills.snbt
├─ chapters/terra_nations.snbt
├─ chapters/nation_<country>.snbt（十九个国家独立章节）
├─ chapters/zinecraft_guide.snbt
├─ chapters/terra_relations.snbt
└─ lang/
   ├─ en_us.snbt
   └─ zh_cn.snbt
```

章节文件已存在时不会被覆盖；章节组文件只追加缺失的 Zinecraft 章节组；集中语言表已存在时只补入缺失的 Zinecraft
键，现有键值不会被替换。因此整合包作者可以在游戏编辑模式中调整内容而不被下次启动还原。若要恢复项目最新版章节，先备份并删除对应章节文件；若要恢复某条内置译文，删除对应语言表中的
Zinecraft 键后再启动游戏。

## 开发验证

1. 启动开发客户端并创建新世界，确认 `run/config/ftbquests/quests/chapters/` 下二十五个内置章节及两个章节组均已生成。
2. 使用任务书界面打开 `Zinecraft：泰拉远征`，检查中文或英文文本、依赖线和奖励物品；再检查十九个国家章节中的 76
   个国家档案节点与 58 个建筑访问节点、开发章节中的依赖分类，以及 `泰拉国家关系网` 的 19 个国家标志、15
   条重要关系线和节点详情；最后确认藏品图鉴包含九个分类和 245 个藏品节点，干员技能章节包含九个技能节点。
3. 使用新玩家验证首次登录位于泰拉；前往 `zinecraft:sami_frozen_forest` 完成萨米任务，再通过固定星门验证
   `minecraft:overworld` 维度任务。
4. 使用 `/locate biome zinecraft:<国家群系>` 或自然探索验证各群系访问任务；使用 `/locate structure zinecraft:<结构ID>`
   定位结构，进入其有效边界后确认对应国家章节任务自动完成。
5. 修改国家资料或结构目录后运行 `python script/generate_nation_quest_chapters.py`，再执行 FTB Quests 校验，确保十九章、76
   个档案节点与 58
   个结构目标保持同步；不要直接修改生成章节后再跳过生成器。
6. 修改藏品目录后运行 `python script/generate_collectible_quest_chapter.py`，同步刷新藏品章节与中英文说明。
7. 修改 SNBT 后使用 `/ftbquests reload quests` 重载任务定义；已有任务进度可用 `/ftbquests change_progress` 调试。

FTB Quests 是外部必需依赖，其 JAR 不会被打包进 Zinecraft。发布整合包时需要按照 FTB 的分发条款单独声明并下载该模组及其传递依赖。
