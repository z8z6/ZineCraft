# 添加技能与 Ponder 演示

技能暂时表现为不可堆叠的稀有物品。通用端的 `SkillCatalog` 负责注册物品、校验技力数据、生成名称和多行说明；客户端的
`ZinecraftPonderPlugin` 为每件技能物品注册可视化演示。玩家将鼠标移到技能物品上并按住 Ponder 所显示的按键即可观看。

## 声明技能

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

声明后会自动得到：

- `zinecraft:<path>` 技能物品；
- 中英文名称、干员、职业、回复方式、触发方式、技力和描述；
- 普通扁平物品模型；
- 可供 Ponder 插件遍历的 `SkillDefinition`。

将图标放在 `assets/zinecraft/textures/item/<path>.png` 后运行 `runDatagen`。新增一种演示机制时，在客户端
`SkillDemoTheme` 对应分支中使用 Ponder 的世界、覆盖层、粒子和动画指令实现；通用端不得引用 Ponder 客户端类。

## 首批技能

| 职业 | 干员   | 技能      | 演示主题          |
|----|------|---------|---------------|
| 先锋 | 桃金娘  | 支援号令·β型 | 部署费用逐步回复      |
| 近卫 | 银灰   | 真银斩     | 扩大范围并同时攻击多个目标 |
| 狙击 | 能天使  | 过载模式    | 五连射与攻击加速      |
| 术师 | 艾雅法拉 | 火山      | 大范围熔岩爆发       |
| 重装 | 塞雷娅  | 钙质化     | 范围治疗、减速和增伤    |
| 医疗 | 夜莺   | 圣域      | 范围治疗与法术防护     |
| 辅助 | 铃兰   | 狐火渺然    | 停顿领域与持续恢复     |
| 特种 | 红    | 狼群      | 部署时范围伤害与晕眩    |

## 资料与图标来源

技能机制、名称和专精三数值参考对应的 PRTS 干员页面：

- [桃金娘](https://prts.wiki/w/桃金娘)、[银灰](https://prts.wiki/w/银灰)、[能天使](https://prts.wiki/w/能天使)、[艾雅法拉](https://prts.wiki/w/艾雅法拉)
- [塞雷娅](https://prts.wiki/w/塞雷娅)、[夜莺](https://prts.wiki/w/夜莺)、[铃兰](https://prts.wiki/w/铃兰)、[红](https://prts.wiki/w/红)

八张 128×128 技能图标取自 PRTS 对应的“文件:技能 … .png”页面，仓库中保留原始分辨率。PRTS 是玩家维护的二级资料来源；
技能图标及明日方舟相关素材的权利属于其原权利方，不因本项目许可证而转为 CC0。发布或再分发前应再次核对权利方政策。
逐文件来源见 [PRTS 技能图标来源](PRTS_ASSETS.md)。

## Ponder 资源

八个技能共用 `assets/zinecraft/ponder/skill_demo/training_ground.nbt` 训练台，但根据 `SkillDemoTheme` 播放不同动画。
可运行以下脚本确定性重建训练台：

```powershell
python script/generate_skill_ponder_scene.py
```

Ponder 目前是必需依赖，`fabric.mod.json` 声明版本不低于 1.0.69。
