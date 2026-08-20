# Zinecraft 声明式 API

项目用按领域拆分的 Java 目录统一注册、翻译与数据生成元数据。各 Catalog 持有并接入自身需要的 NeoForge 延迟注册器；动态注册表
由对应 Catalog 的 bootstrap 直接写入。

## 目录入口

| `Zinecraft` 入口       | 类型                   | 职责                                            |
|----------------------|----------------------|-----------------------------------------------|
| `TRANSLATIONS`       | `TranslationCatalog` | 双语翻译与可直接创建 `Component` 的消息条目                  |
| `VFX`                | `VfxCatalog`         | 通过 `VfxBuilder` 声明武器动作与技能复用的客户端特效 ID          |
| `ITEMS`              | `ItemCatalog`        | 物品、翻译、模型元数据、燃料与堆肥                             |
| `BLOCKS`             | `BlockCatalog`       | 方块、方块物品、翻译、简单模型与默认掉落                          |
| `BLOCK_ENTITIES`     | `BlockEntityCatalog` | 组合注册方块实体类型及其对应的 `EntityBlock`                 |
| `SOUNDS`             | `SoundCatalog`       | 声音及由 `MusicDiscBuilder` 组合的唱片物品与 Jukebox Song |
| `getCREATIVE_TABS()` | `CreativeTabCatalog` | 创造模式页及条目收集                                    |
| `getENTITIES()`      | `EntityCatalog`      | 实体、Mob、属性、生成限制、生成蛋与自然生成                       |
| `getENCHANTMENTS()`  | `EnchantmentCatalog` | 1.21.1 动态附魔及数据生成                              |
| `getSKILLS()`        | `SkillCatalog`       | 技能物品、双语资料与 Ponder 元数据                         |
| `WEAPONS`            | `WeaponCatalog`      | 通过 `WeaponBuilder` 声明服务端动作、武器定义、表现与物品解析       |
| `BIOMES`             | `BiomeCatalog`       | 通过 `BiomeBuilder` 声明群系并执行 bootstrap           |
| `DIMENSIONS`         | `DimensionCatalog`   | 维度、维度类型与群系源                                   |
| `FEATURES`           | `FeatureCatalog`     | 配置/放置地物与矿物 Builder                            |
| `STRUCTURES`         | `StructureCatalog`   | 通过 `JigsawBuilder` 声明模板池、结构与结构集               |
| `getRECIPES()`       | `RecipeCatalog`      | 配方数据生成回调                                      |

## Java 声明示例

```java
ItemBuilder<Item> dust = new ItemBuilder<>(
    Zinecraft.ITEMS, "magic_dust", "魔法粉尘", "Magic Dust",
    () -> new Item(new Item.Properties())
).build();

MessageBuilder denied = new MessageBuilder(
    Zinecraft.TRANSLATIONS, "message.zinecraft.machine.denied",
    "机器拒绝访问",
    "Machine access denied"
);
player.displayClientMessage(denied.component(), true);

BlockBuilder<Block> machine = new BlockBuilder<>(
    Zinecraft.BLOCKS, "machine", "机器", () -> new Block(BlockBehaviour.Properties.of().strength(4.0F)))
    .enUs("Machine")
    .build();

OreBuilder<Block> ore = new OreBuilder<>(Zinecraft.FEATURES, "machine_ore", machine)
    .vein(8, 4)
    .maxY(32)
    .discardChanceOnAirExposure(0.0F)
    .biomes(BiomeSelection.overworld())
    .build();

SimpleFeatureBuilder feature = new SimpleFeatureBuilder(
    Zinecraft.FEATURES, "crystal_spire", crystalSpireFeature
).placement(List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), BiomeFilter.biome()))
    .generationStep(GenerationStep.Decoration.LOCAL_MODIFICATIONS)
    .biomes(BiomeSelection.overworld())
    .build();

ResourceKey<Biome> biome = new BiomeBuilder(
    Zinecraft.BIOMES, "example_grove", "示例林地"
).climate(0.2F, 0.5F, 0.1F, 0.0F, 0.0F, 0.3F)
    .configure(builder -> {
  builder.temperature(0.7F).downfall(0.8F);
  builder.defaultOverworldGeneration();
}).build().key();

JigsawBuilder ruins = Zinecraft.STRUCTURES
    .jigsaw("example_ruins", "示例遗迹")
    .randomSpread(36, 16, 41002001)
    .layout(3, 80)
    .pool("start", pool -> pool.template("ruins/example_start"))
    .build();

DimensionBuilder dimension = Zinecraft.DIMENSIONS.dimension("example_dimension")
    .biomes(List.of(new DimensionBiome(biome, Climate.parameters(
        0.2F, 0.5F, 0.1F, 0.0F, 0.0F, 0.3F, 0.0F
    ))))
    .build();
```

Builder 构造函数接收所属 Catalog 和声明参数，`build()` 回交 Catalog 校验并注册；Catalog 不重复声明 Builder 的构造签名。

目录构造时注入其真实依赖；内容类通过显式静态字段和初始化入口声明内容，不依赖语言级对象初始化。`Zinecraft` 的 NeoForge
构造入口创建目录并将延迟注册器接到模组事件总线，`commonSetup` 只处理必须在注册后执行的绑定与可选兼容层。

## 动态注册表与数据生成

需要生成动态注册表的 Catalog 均实现 `RegistryDataContributor`，通过 `contribute(...)` 接入所管理的注册表。目前包括群系、
维度、地物、结构、附魔和 Jukebox Song；数据生成入口只遍历贡献者，不包含具体的 `Registries.*` 调用。

运行：

```powershell
.\gradlew.bat runData
.\gradlew.bat build
```

## 设计约束

- ID 使用 `zinecraft` 命名空间与稳定的 `snake_case` 路径。
- Java API 使用 `Supplier`、`Consumer`、`Function`、不可变集合或明确的领域类型，不新增默认参数掩码、`componentN`、`Companion`
  等迁移桥接。
- 公开目录在进入注册表前校验数量、概率、区间和重复 ID。
- 服务端玩法不引用客户端渲染类型；客户端表现不能直接结算伤害、弹药或技能效果。
- 自动生成只覆盖可可靠推导的数据；特殊模型、掉落、渲染和效果显式实现。
