# Zinecraft 声明式 API

项目用按领域拆分的 Java 目录统一注册、翻译与数据生成元数据。具体内容优先调用目录，只有目录无法表达的特殊注册表才直接使用
`ModRegistrar`。

## 目录入口

| `Zinecraft` 入口               | 类型                             | 职责                      |
|------------------------------|--------------------------------|-------------------------|
| `getITEMS()`                 | `ItemCatalog`                  | 物品、翻译、模型元数据、燃料与堆肥       |
| `getBLOCKS()`                | `BlockCatalog`                 | 方块、方块物品、翻译、简单模型与默认掉落    |
| `getBLOCK_ENTITIES()`        | `BlockEntityCatalog`           | 方块实体类型与有效方块绑定           |
| `getSOUNDS()` / `getSONGS()` | `SoundCatalog` / `SongCatalog` | 声音、唱片物品与 Jukebox Song   |
| `getCREATIVE_TABS()`         | `CreativeTabCatalog`           | 创造模式页及条目收集              |
| `getENTITIES()`              | `EntityCatalog`                | 实体、Mob、属性、生成限制、生成蛋与自然生成 |
| `getENCHANTMENTS()`          | `EnchantmentCatalog`           | 1.21.1 动态附魔及数据生成        |
| `getSKILLS()`                | `SkillCatalog`                 | 技能物品、双语资料与 Ponder 元数据   |
| `getWEAPONS()`               | `WeaponRegistry`               | 服务端动作、武器定义与物品解析器        |
| `getBIOMES()`                | `BiomeCatalog`                 | 群系资源键与 bootstrap        |
| `getDIMENSIONS()`            | `DimensionCatalog`             | 维度、维度类型与群系源             |
| `getFEATURES()`              | `FeatureCatalog`               | 配置/放置地物与矿物参数            |
| `getSTRUCTURES()`            | `StructureCatalog`             | 简易建筑、Jigsaw 聚落、唯一地标与结构集 |
| `getRECIPES()`               | `RecipeCatalog`                | 配方数据生成回调                |

## Java 声明示例

```java
ItemEntry<Item> dust = Zinecraft.INSTANCE.getITEMS().register(
    "magic_dust", "魔法粉尘", "Magic Dust"
).fuel(600).compost(0.3F);

BlockEntry<Block> machine = Zinecraft.INSTANCE.getBLOCKS().register(
    "machine", "机器", "Machine",
    true, null, true, true,
    () -> new Block(BlockBehaviour.Properties.of().strength(4.0F))
);

OreEntry ore = Zinecraft.INSTANCE.getFEATURES().ore(
    "machine_ore", machine::getBlock,
    8, 4, 32, 0.0F
);
```

目录构造时注入其真实依赖；内容类通过显式静态字段和初始化入口声明内容，不依赖语言级对象初始化。`Zinecraft` 的 NeoForge
构造入口创建目录并将延迟注册器接到模组事件总线，`commonSetup` 只处理必须在注册后执行的绑定与可选兼容层。

## 动态注册表与数据生成

`WorldgenManager` 汇总群系、维度、地物与结构 bootstrap。数据生成入口把 bootstrap 添加到 `RegistrySetBuilder`，然后由
provider 导出 JSON。新增普通目录条目时通常不必改入口；新增一种动态注册表类型时才需要扩展汇总逻辑。

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
