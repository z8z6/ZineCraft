# 添加实体与 Mob

普通实体和 Mob 通过 `Zinecraft.INSTANCE.getENTITIES()` 注册。Mob 目录额外管理默认属性、生成限制、NeoForge biome
modifier、自然生成和生成蛋。

## 普通实体

```java
EntityEntry<ExampleProjectile> projectile = Zinecraft.INSTANCE.getENTITIES().register(
    "example_projectile", "示例投射物", "Example Projectile",
    ExampleProjectile::new, MobCategory.MISC,
    builder -> builder.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
);
```

## Mob

```java
MobSpawnRestriction<ExampleMob> restriction = new MobSpawnRestriction<>(
    SpawnPlacementTypes.ON_GROUND,
    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
    ExampleMob::canSpawn
);

MobEntry<ExampleMob> mob = Zinecraft.INSTANCE.getENTITIES().mob(
    "example_mob", "示例生物", "Example Mob",
    ExampleMob::new, MobCategory.CREATURE,
    ExampleMob::attributes, restriction,
    builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(8)
);
```

属性和生成限制在 NeoForge 注册生命周期中统一接入。`naturalSpawn` 记录生成权重、群体范围与 `BiomeSelection`，数据生成时导出
`NeoForgeRegistries.Keys.BIOME_MODIFIERS`；不要另外调用旧 Loader 的属性或群系注入 API。

生成蛋由 `MobEntry.spawnEgg(...)` 创建，并自动生成翻译与 `minecraft:item/template_spawn_egg` 模型。

## 客户端渲染

renderer 和 model layer 放在 `src/client/java`，通过 NeoForge 客户端事件注册。服务端实体、属性、生成条件与 AI 不得引用渲染器。

## 十九国居民

`ModEntities` 为每个国家群系提供居民实体。拉特兰使用 `LateranoCitizen`，其余国家使用带 `NationResidentProfile` 的
`NationResident`。所有居民实现 `NationAffiliated`；任务、声望或外交逻辑读取明确的 `TerraNation`，不要从名称、皮肤或当前位置推断国籍。

默认持有物和枪械由服务端生成逻辑设置且掉落率为零。以后增加射击 AI 时必须调用服务端 Weapon Runtime，不能复用玩家 C2S
输入或由客户端动画结算伤害。
