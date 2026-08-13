# 音效、唱片与附魔

## 普通音效

声音事件和声音文件声明是两件事：

```kotlin
val ENGINE = Zinecraft.SOUNDS.register("engine")
```

同时在 `src/main/resources/assets/zinecraft/sounds.json` 添加事件，把 OGG 放在 `assets/zinecraft/sounds/`：

```json
{
  "engine": {
    "subtitle": "sound.zinecraft.engine",
    "sounds": ["zinecraft:engine"]
  }
}
```

再通过 `Zinecraft.TRANSLATIONS.add("sound.zinecraft.engine", "引擎轰鸣", "Engine Roar")` 登记字幕翻译。事件 ID、JSON
键和文件相对路径必须一致。使用合法 OGG 编码并核对音量、循环行为及空间衰减。

## 音乐唱片

```kotlin
val THEME = Zinecraft.SONGS.register(
  path = "music.theme",
  lengthSeconds = 120f,
  description = "Composer - Theme",
  zhCn = "音乐唱片",
  enUs = "Music Disc",
  signal = 12
)
```

`SongCatalog` 会一起注册 `SoundEvent`、唱片物品、`JukeboxSong` 动态条目、物品或描述翻译和唱片模型。仍需手写 `sounds.json`
并提供 OGG 与物品纹理。新增唱片声明所在对象必须在普通初始化和数据生成中被访问；项目现有 `ModSound.configure` 已接入
`Registries.JUKEBOX_SONG`。

外部音乐通常有版权，不因可下载而允许随模组分发。记录作曲者、来源和许可；权利不清楚时只保留注册代码与占位资源说明，不提交音频。

## 附魔

附魔是动态注册表内容：

```kotlin
val EXAMPLE = Zinecraft.ENCHANTMENTS.register(
  path = "example",
  zhCn = "示例附魔",
  enUs = "Example Enchantment",
  supportedItems = ModItemTags.EXAMPLE_ENCHANTABLE,
  weight = 10,
  maxLevel = 3,
  minCost = Enchantment.dynamicCost(1, 10),
  maxCost = Enchantment.dynamicCost(20, 10),
  anvilCost = 2,
  slots = arrayOf(EquipmentSlotGroup.MAINHAND)
) {
  // 使用原版或项目已有 enchantment effects API 增加效果组件。
}
```

`supportedItems` 对应的 item tag 必须存在；可选 `primaryItems` 和 `exclusiveWith` 也必须指向有效标签。目录校验权重、等级、成本和装备槽并自动生成名称。附魔目录的
`bootstrap` 已在 `ZinecraftDataGenerator.buildRegistry()` 中注册；若创建新的内容对象，仍需保证普通启动和 datagen 都触发其初始化。

运行数据生成后检查 `data/zinecraft/enchantment/<path>.json`、相关 tags 和翻译，再执行完整构建。参考
`docs/enchantment/README.md`、`SoundCatalog.kt`、`SongCatalog.kt` 与 `EnchantmentCatalog.kt`。

## 配方、创造模式页与矿物

- 用 `Zinecraft.RECIPES.add { output -> ... }` 登记数据生成配方；生成逻辑由现有 `ModRecipeProvider` 统一执行。
- 用 `Zinecraft.CREATIVE_TABS.register(path, zhCn, enUs, icon)` 新建创造模式页。目录默认收集全部已登记物品和拥有方块物品的方块；除非确实要排除方块，不要关闭
  `includeBlocks`。
- 用 `Zinecraft.FEATURES.ore(...)` 注册常规矿物，传入矿石方块、矿脉大小、每区块数量、高度和明确的群系选择器。
  `WorldgenManager` 已负责 configured/placed feature 的动态数据生成和普通启动时的群系注入。

这些入口同样遵守“双初始化”规则：声明对象必须在普通启动和 datagen 时被访问，动态世界生成内容还必须由 `WorldgenManager`
接入注册表构建。
