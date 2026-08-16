# 添加技能与 Ponder 演示

技能当前表现为独立的不可堆叠物品。`SkillCatalog` 注册物品、校验技力数据并生成双语资料；客户端 `ZinecraftPonderPlugin` 根据
`SkillDemoTheme` 注册演示。技能物品使用独立创造模式页。

```java
SkillEntry volcano = Zinecraft.INSTANCE.getSKILLS().register(
    "skill_volcano",
    "火山", "Volcano",
    "艾雅法拉", "Eyjafjalla",
    SkillProfession.CASTER,
    "自动回复", "Auto Recovery",
    "手动触发", "Manual",
    55, 80, 15,
    "攻击范围扩大并快速向范围内至多六个敌人发射熔岩。",
    "Expands range and rapidly launches lava at up to six enemies in range.",
    SkillDemoTheme.VOLCANIC_BURST
);
```

声明会生成技能物品、名称、干员/职业、回复/触发方式、技力、持续时间、描述和普通扁平模型。`initialSp`、`spCost` 不能为负；非空持续时间必须大于零。

图标放在：

```text
src/main/resources/assets/zinecraft/textures/item/<path>.png
```

新增演示主题时，数据枚举留在通用端，Ponder 场景分支放在 `src/client/java`。演示只能表现技能机制，不能直接修改服务端伤害、状态或任务进度。

## 当前技能

| 职业 | 干员   | 技能      |
|----|------|---------|
| 先锋 | 桃金娘  | 支援号令·β型 |
| 近卫 | 银灰   | 真银斩     |
| 狙击 | 能天使  | 过载模式    |
| 狙击 | 维什戴尔 | 爆裂黎明    |
| 术师 | 艾雅法拉 | 火山      |
| 重装 | 塞雷娅  | 钙质化     |
| 医疗 | 夜莺   | 圣域      |
| 辅助 | 铃兰   | 狐火渺然    |
| 特种 | 红    | 狼群      |

## FTB Quests 技能章节

`src/main/resources/zinecraft/ftbquests/quests/chapters/operator_skills.snbt` 展示上述全部技能。每个技能节点包含一个自动检测技能物品的
Item Task，以及一个确认玩家已查看 Ponder 演示的 Checkmark。新增技能时，应同步添加技能节点和 `zh_cn.snbt`、`en_us.snbt`
中的章节说明，并运行 FTB Quests 校验脚本。

技能名称、机制和图标来自对应 PRTS 页面。逐文件来源见 [PRTS_ASSETS.md](PRTS_ASSETS.md)。第三方图标权利不受仓库代码许可证覆盖，发布前必须再次核对分发条件。

训练场模板位于 `assets/zinecraft/ponder/skill_demo/training_ground.nbt`，可用以下脚本重建：

```powershell
python script/generate_skill_ponder_scene.py
.\gradlew.bat runData
```

Ponder 是 `neoforge.mods.toml` 声明的必需依赖。
