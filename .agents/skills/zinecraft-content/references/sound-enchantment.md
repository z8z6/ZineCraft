# 音效、唱片、附魔、配方与矿物

- `SoundCatalog` 注册 `SoundEvent`；`sounds.json`、OGG 和字幕翻译必须使用一致 ID。
- `SongCatalog` 组合声音、唱片物品、`JukeboxSong` 动态条目、模型和翻译；外部音乐必须记录来源与许可。
- `EnchantmentCatalog` 声明 Minecraft 1.21.1 动态附魔，显式提供支持标签、成本、槽位和 builder 效果；相关标签必须存在。
- `RecipeCatalog` 登记由 `ModRecipeProvider` 执行的 Java `Consumer<RecipeOutput>`。
- `CreativeTabCatalog` 注册独立创造模式页；藏品、技能和 TaCZ 使用各自页面。
- `FeatureCatalog.ore` 保存矿脉大小、每区块数量、高度和丢弃概率；运行时注入与动态数据由 `WorldgenManager` 汇总。
- JER 适配位于 `compat/jer/ZinecraftJerPlugin.java`，只在检测到 JER 后注册实际矿物分布。

完成后运行 `runData` 检查语言、模型、配方、附魔和 configured/placed feature，再单独运行 `build`。
