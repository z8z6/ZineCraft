# 音效、唱片、附魔、配方与矿物

- `SoundCatalog` 注册 `SoundEvent`，并通过 `RegistryDataContributor` 生成 `JukeboxSong`；`sounds.json`、OGG 和字幕翻译必须使用一致
  ID。
- `SongCatalog` 组合声音、唱片物品、`JukeboxSong` 动态条目、模型和翻译；外部音乐必须记录来源与许可。
- `EnchantmentCatalog` 声明 Minecraft 1.21.1 动态附魔并通过 `RegistryDataContributor` 接入数据生成，显式提供支持标签、成本、槽位和
  builder 效果；相关标签必须存在。
- `RecipeCatalog` 登记由 `ModRecipeProvider` 执行的 Java `Consumer<RecipeOutput>`。
- `CreativeTabCatalog` 注册独立创造模式页；藏品、技能和 TaCZ 使用各自页面。
- `OreBuilder` 保存矿石方块、矿脉大小、每区块数量、高度、丢弃概率与可选烧炼信息；`SimpleFeatureBuilder`
  保存无配置地物的放置规则、生成阶段和群系范围。两者均由 `FeatureCatalog` 校验、登记并独立接入运行时与动态数据。
- JER 适配位于 `compat/jer/ZinecraftJerPlugin.java`，只在检测到 JER 后注册实际矿物分布。

完成后运行 `runData` 检查语言、模型、配方、附魔和 configured/placed feature，再单独运行 `build`。
