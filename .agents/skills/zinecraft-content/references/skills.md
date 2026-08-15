# 角色技能物品与 Ponder

在 `src/main/java/com/cxxcxx/zinecraft/core/skill/ModSkills.java` 通过 `Zinecraft.INSTANCE.getSKILLS().register(...)`
声明技能。目录创建不可堆叠技能物品、双语名称/tooltip 和模型元数据，并校验初始技力、消耗与持续时间。

图标位于 `assets/zinecraft/textures/item/<path>.png`。名称、说明、数值和图标只从官网或 PRTS 核对；更新
`docs/skill/PRTS_ASSETS.md` 的逐文件来源与访问日期，不生成所谓官方图标。

`ZinecraftPonderPlugin` 位于 `src/client/java` 并遍历技能条目。新增主题时在通用端增加语义枚举，在客户端增加对应场景分支。Ponder
只做演示，不能结算服务端效果。

训练场 NBT 用 `python script/generate_skill_ponder_scene.py` 重建。完成后运行 `runData`、`build`，并在客户端验证场景、镜头和双语文案。
