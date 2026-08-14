# 添加实体与 Mob

普通实体和 Mob 都通过 `Zinecraft.ENTITIES` 注册。Mob 接口额外封装默认属性、生成限制、自然生成和生成蛋。

## 普通实体

```kotlin
val PROJECTILE = Zinecraft.ENTITIES.register(
  path = "example_projectile",
  zhCn = "示例投射物",
  enUs = "Example Projectile",
  factory = ::ExampleProjectile,
  category = MobCategory.MISC
) {
  sized(0.25f, 0.25f)
  clientTrackingRange(4)
  updateInterval(10)
}
```

返回 `EntityEntry<T>`，通过 `.type` 取得 `EntityType<T>`。实体双语名称会自动进入语言数据生成。

## Mob 类

```kotlin
class ExampleMob(type: EntityType<out PathfinderMob>, level: Level) :
  PathfinderMob(type, level) {

  companion object {
    fun attributes(): AttributeSupplier.Builder =
      createMobAttributes()
        .add(Attributes.MAX_HEALTH, 20.0)
        .add(Attributes.MOVEMENT_SPEED, 0.25)
        .add(Attributes.ATTACK_DAMAGE, 3.0)

    fun canSpawn(
      type: EntityType<ExampleMob>,
      level: ServerLevelAccessor,
      reason: MobSpawnType,
      pos: BlockPos,
      random: RandomSource
    ): Boolean = checkMobSpawnRules(type, level, reason, pos, random)
  }
}
```

## 注册属性与生成限制

```kotlin
val EXAMPLE_MOB = Zinecraft.ENTITIES.mob(
  path = "example_mob",
  zhCn = "示例生物",
  enUs = "Example Mob",
  factory = ::ExampleMob,
  category = MobCategory.CREATURE,
  attributes = ExampleMob::attributes,
  spawnRestriction = MobSpawnRestriction(
    placement = SpawnPlacementTypes.ON_GROUND,
    heightmap = Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
    predicate = ExampleMob::canSpawn
  )
) {
  sized(0.6f, 1.8f)
  clientTrackingRange(8)
}
```

属性和生成限制随实体类型一起注册，不需要另外调用 `FabricDefaultAttributeRegistry` 或原版内部注册方法。

## 自然生成与生成蛋

```kotlin
val EXAMPLE_MOB = Zinecraft.ENTITIES.mob(/* ... */)
  .naturalSpawn(
    weight = 10,
    minGroupSize = 1,
    maxGroupSize = 3,
    biomes = BiomeSelectors.foundInOverworld()
  )

val EXAMPLE_MOB_SPAWN_EGG = EXAMPLE_MOB.spawnEgg(
  primaryColor = 0x5A7652,
  secondaryColor = 0xC8D6A3,
  zhCn = "示例生物生成蛋",
  enUs = "Example Mob Spawn Egg"
)
```

生成蛋会自动注册为物品，并自动生成翻译及引用 `minecraft:item/template_spawn_egg` 的模型，不需要自定义贴图。

`naturalSpawn` 会校验权重与群体大小，并通过 Fabric biome API 加入指定群系。省略 `biomes` 时默认加入主世界。

## 客户端渲染器

实体目录不会猜测模型或渲染器。必须在 `src/client/kotlin` 注册：

```kotlin
EntityRendererRegistry.register(ModEntities.EXAMPLE_MOB.type) { context ->
  ExampleMobRenderer(context)
}
```

通用源码不能引用 renderer、model layer 或其他客户端类。

## 拉特兰人形生物的默认枪械

`LateranoCitizen.finalizeSpawn` 在服务端把枪械写入主手装备槽。`LateranoLoadout` 优先从已加载 TaCZ 枪包中稳定筛选手枪，
没有手枪时选择枪包中的其他枪械；完全没有外置枪包时回退到 `test_rifle`，所以生成结果始终满足默认持枪语义。
装备掉落率为零，避免把自然生成生物变成外置枪械复制来源。当前公民是和平生物；是否射击必须以后通过独立的服务端 Mob 武器 AI
实现，
不能复用玩家 C2S 输入或在客户端动画中结算伤害。

## 十九国居民

`ModEntities` 为每个国家群系提供对应居民。拉特兰继续使用 `LateranoCitizen`；其余十八国使用 `NationResident` 的国家专属实体类型，
仅在对应群系自然生成，并持有不会掉落的职业意象物品。所有居民都实现 `NationAffiliated`，任务、声望或外交系统应读取
`nation`，不要根据实体显示名、皮肤或所在群系反推国籍。

客户端当前复用原版宽臂玩家模型与占位皮肤；国家专属原创皮肤仍可在不改变服务端实体和关系数据的情况下逐步替换。
