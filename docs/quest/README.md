# FTB Quests 泰拉远征指引

## 内容范围

项目内置 `Zinecraft：泰拉远征` 与 `泰拉国家关系网` 两个章节。远征章节包含三条相互衔接的内容：

1. 在主世界雪原寻找星门，并通过 `zinecraft:terra` 维度访问任务验证传送成功。
2. 分别访问泰拉维度中的 19 个国家群系；所有群系任务完成后可提交地理考察。
3. 领取测试剑、测试步枪、24 发测试弹药和测试法杖，按说明完成基础动作训练；TaCZ 枪械训练为不阻塞主线的可选任务。

关系网章节把 19 个国家作为可点击节点，以各国对应的特色方块作为稳定可渲染的标志，并用 FTB Quests 原生依赖线连接具有 PRTS
明确依据的重要关系。节点详情展示国家繁荣度、稳定度、军力、开放度、战争倾向，以及相关国家间的好感、战争欲望、贸易、紧张和信任。未连线的国家采用系统的中立默认关系；依赖线颜色只表示任务查看进度，不表示外交态度。

FTB Quests 原生只能自动检测维度、群系与物品状态。武器动作目前使用物品检测加手动确认，避免让客户端动画或按键直接修改服务端任务进度。

## 安装方式

FTB Quests 将任务书保存在运行目录的 `config/ftbquests/quests/`，而不是 Minecraft 数据包中。Zinecraft 因此把稳定模板放在
`src/main/resources/zinecraft/ftbquests/quests/`，并在 FTB Quests 已加载时由 `FtbQuestGuideInstaller` 安装章节、合并语言键：

```text
config/ftbquests/quests/
├─ chapters/zinecraft_guide.snbt
├─ chapters/terra_relations.snbt
└─ lang/
   ├─ en_us.snbt
   └─ zh_cn.snbt
```

章节文件已存在时不会被覆盖；集中语言表已存在时只补入缺失的 Zinecraft
键，现有键值不会被替换。因此整合包作者可以在游戏编辑模式中调整内容而不被下次启动还原。若要恢复项目最新版章节，先备份并删除对应章节文件；若要恢复某条内置译文，删除对应语言表中的
Zinecraft 键后再启动游戏。

## 开发验证

1. 启动开发客户端并创建新世界，确认 `run/config/ftbquests/quests/chapters/` 下两个内置章节均已生成。
2. 使用任务书界面打开 `Zinecraft：泰拉远征`，检查中文或英文文本、依赖线和奖励物品；再打开 `泰拉国家关系网`，检查 19 个国家标志、15
   条重要关系线和节点详情。
3. 在主世界执行 `/locate biome minecraft:snowy_plains` 验证雪原任务，然后通过星门验证 `zinecraft:terra` 维度任务。
4. 使用 `/locate biome zinecraft:<国家群系>` 或自然探索验证各群系访问任务。
5. 修改 SNBT 后使用 `/ftbquests reload quests` 重载任务定义；已有任务进度可用 `/ftbquests change_progress` 调试。

FTB Quests 是外部必需依赖，其 JAR 不会被打包进 Zinecraft。发布整合包时需要按照 FTB 的分发条款单独声明并下载该模组及其传递依赖。
