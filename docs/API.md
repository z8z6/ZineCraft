# Zinecraft 声明式 API

项目使用按领域拆分的目录，不再由 `ContentCatalog` 或 `WorldgenCatalog` 集中承担全部职责。每个目录只保存自身注册所需的数据，
`WorldgenManager` 仅负责运行时初始化和数据生成 bootstrap 汇总。

## 目录划分

| 入口               | 类型                   | 自动处理                               |
|------------------|----------------------|------------------------------------|
| `ITEMS`          | `ItemCatalog`        | 物品注册、翻译、模型元数据、燃料和堆肥                |
| `BLOCKS`         | `BlockCatalog`       | 方块与方块物品注册、翻译、简单模型和默认掉落             |
| `BLOCK_ENTITIES` | `BlockEntityCatalog` | 方块实体类型注册与有效方块绑定                    |
| `SOUNDS`         | `SoundCatalog`       | 声音事件注册                             |
| `SONGS`          | `SongCatalog`        | 声音事件、唱片物品、Jukebox Song 和翻译         |
| `CREATIVE_TABS`  | `CreativeTabCatalog` | 标签页注册、翻译和目录内容收集                    |
| `ENTITIES`       | `EntityCatalog`      | 普通实体、Mob、默认属性、生成限制、自然生成和生成蛋        |
| `ENCHANTMENTS`   | `EnchantmentCatalog` | 1.21.1 动态附魔、翻译、等级成本、装备槽和效果 builder |
| `BIOMES`         | `BiomeCatalog`       | 群系资源键及 bootstrap                   |
| `FEATURES`       | `FeatureCatalog`     | 配置地物、放置地物和运行时群系注入                  |
| `STRUCTURES`     | `StructureCatalog`   | 处理器、模板池、Jigsaw 结构和结构集              |
| `RECIPES`        | `RecipeCatalog`      | 可组合的配方生成回调                         |

`REGISTRAR` 是底层命名空间注册器。具体内容优先使用上述领域目录；只有目录尚未覆盖的特殊注册表才直接使用它。

## 初始化

项目在 `ZinecraftCore` 中只创建一次目录：

```kotlin
val REGISTRAR = ModRegistrar(MOD_ID)
val TRANSLATIONS = TranslationCatalog()
val ITEMS = ItemCatalog(REGISTRAR, TRANSLATIONS)
val BLOCKS = BlockCatalog(REGISTRAR, TRANSLATIONS)
val BLOCK_ENTITIES = BlockEntityCatalog(REGISTRAR)
val SOUNDS = SoundCatalog(REGISTRAR)
val ENTITIES = EntityCatalog(REGISTRAR, ITEMS, TRANSLATIONS)
val ENCHANTMENTS = EnchantmentCatalog(REGISTRAR, TRANSLATIONS)

val WORLDGEN = WorldgenManager(REGISTRAR)
val BIOMES = WORLDGEN.biomes
val FEATURES = WORLDGEN.features
val STRUCTURES = WORLDGEN.structures
```

内容对象应在 `onInitialize` 和数据生成入口中被访问，确保 Kotlin `object` 的声明完成初始化。运行时只需调用一次：

```kotlin
WORLDGEN.initialize()
```

数据生成入口调用：

```kotlin
registryBuilder.add(Registries.ENCHANTMENT, ENCHANTMENTS::bootstrap)
WORLDGEN.addDataGeneration(registryBuilder)
```

## 示例

```kotlin
val MAGIC_DUST = ZinecraftCore.ITEMS.register(
  "magic_dust",
  "魔法粉尘",
  "Magic Dust"
).fuel(600).compost(0.3f)

val MACHINE = ZinecraftCore.BLOCKS.register(
  "machine",
  "机器",
  "Machine"
) {
  Block(BlockBehaviour.Properties.of().strength(4.0f))
}

val ORE = ZinecraftCore.FEATURES.ore(
  path = "machine_ore",
  block = MACHINE.block,
  veinSize = 8,
  veinsPerChunk = 4,
  maxY = 32
)
```

完整指南：

- [物品](item/README.md)
- [方块与方块实体](block/README.md)
- [实体与 Mob](entity/README.md)
- [附魔](enchantment/README.md)
- [结构](structure/README.md)
- [群系](biome/README.md)

## 设计约束

- 目录构造函数注入其真实依赖，不依赖全局单例。
- 静态注册发生在内容声明时；动态注册表通过 `BootstrapContext` 生成数据。
- 所有概率、数量和区间参数在进入注册表前校验。
- 服务端 API 不引用客户端渲染类型；实体 renderer 只放在 `src/client`。
- 自动生成只覆盖可可靠推导的数据。特殊模型、特殊掉落、实体渲染和具体附魔效果仍显式声明。
