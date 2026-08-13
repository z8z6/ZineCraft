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
