# 角色技能物品与 Ponder 演示

当前阶段把角色技能建模为不可堆叠的稀有物品。通用端声明数据与提示文本，客户端 Ponder 插件负责动画演示。

## 新增技能

在 `core/skill/ModSkills.kt` 中注册：

```kotlin
val VOLCANO = Zinecraft.SKILLS.register(
  path = "skill_volcano",
  zhCn = "火山",
  enUs = "Volcano",
  operatorZhCn = "艾雅法拉",
  operatorEnUs = "Eyjafjalla",
  profession = SkillProfession.CASTER,
  recoveryZhCn = "自动回复",
  recoveryEnUs = "Auto Recovery",
  triggerZhCn = "手动触发",
  triggerEnUs = "Manual",
  initialSp = 55,
  spCost = 80,
  durationSeconds = 15,
  descriptionZhCn = "攻击范围扩大并快速向范围内至多六个敌人发射熔岩。",
  descriptionEnUs = "Expands range and rapidly launches lava at up to six enemies in range.",
  theme = SkillDemoTheme.VOLCANIC_BURST
)
```

目录自动创建技能物品、名称、多行 tooltip、Ponder 文案和普通扁平物品模型。把图标放在
`src/main/resources/assets/zinecraft/textures/item/<path>.png`。

## Ponder 场景

`ZinecraftPonderPlugin` 遍历 `Zinecraft.SKILLS.entries`，所以使用已有 `SkillDemoTheme` 时无需逐技能注册场景。若现有主题不能准确表达技能：

1. 在通用端 `SkillDemoTheme` 增加语义明确的枚举值。
2. 在客户端 `ZinecraftPonderPlugin.animateTheme` 增加对应分支。
3. 使用世界方块变化、轮廓、粒子、物品实体和镜头动作体现核心机制，不只播放同一套装饰动画。
4. 保持 Ponder 类型只出现在 `src/client/kotlin`。

技能共用 `assets/zinecraft/ponder/skill_demo/training_ground.nbt`。需要调整场地时运行
`python script/generate_skill_ponder_scene.py`，并把稳定 NBT 保留在 resources。

## 资料与版权

技能名、描述、技力、持续时间和图标需要通过当前 PRTS 干员或技能文件页核对。优先使用明日方舟官网确认官方世界观信息，PRTS
用于具体数值与文件来源。更新 `docs/skill/PRTS_ASSETS.md`，逐文件记录页面和访问日期。

不得把 PRTS 或明日方舟素材描述为项目许可证覆盖的原创资产。发布、打包或再分发前提示用户核对权利方政策；不要在缺少明确来源时猜测或生成“官方图标”。

## 验证

运行数据生成，检查中英文物品和 tooltip 键、物品模型与 PNG 路径；运行构建检查 client/main 分包；在客户端用 Ponder
快捷键检查场景可打开、镜头不越界、文案可读且主题行为符合描述。

参考：`docs/skill/README.md`、`docs/skill/PRTS_ASSETS.md`、`SkillCatalog.kt`、`ModSkills.kt` 和 `ZinecraftPonderPlugin.kt`。
