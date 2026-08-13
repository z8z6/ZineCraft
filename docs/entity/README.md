# 添加实体

普通实体通过 `ModRegistrar.entity` 注册。API 负责 ID 和实体类型注册，实体属性、默认属性、渲染器和生成规则仍需分别配置，因为它们依赖具体实体类型。

## 实体类

```kotlin
class ExampleMob(type: EntityType<out PathfinderMob>, level: Level) :
  PathfinderMob(type, level) {

  companion object {
    fun attributes(): AttributeSupplier.Builder =
      createMobAttributes()
        .add(Attributes.MAX_HEALTH, 20.0)
        .add(Attributes.MOVEMENT_SPEED, 0.25)
  }
}
```

## 注册实体类型

```kotlin
object ModEntities {
  val EXAMPLE_MOB = ZinecraftCore.REGISTRAR.entity(
    path = "example_mob",
    factory = ::ExampleMob,
    category = MobCategory.CREATURE
  ) {
    sized(0.6f, 1.8f)
    clientTrackingRange(8)
  }

  fun initialize() {
    FabricDefaultAttributeRegistry.register(
      EXAMPLE_MOB,
      ExampleMob.attributes()
    )
  }
}
```

在模组通用入口调用：

```kotlin
ModEntities.initialize()
```

## 客户端渲染器

在 `src/client/kotlin` 中注册，避免服务端加载客户端类：

```kotlin
EntityRendererRegistry.register(ModEntities.EXAMPLE_MOB) { context ->
  ExampleMobRenderer(context)
}
```

## 生成蛋与自然生成

生成蛋可以作为普通物品声明：

```kotlin
val EXAMPLE_MOB_SPAWN_EGG = CONTENT.item(
  "example_mob_spawn_egg",
  "示例生物生成蛋",
  "Example Mob Spawn Egg"
) {
  SpawnEggItem(
    ModEntities.EXAMPLE_MOB,
    primaryColor,
    secondaryColor,
    Item.Properties()
  )
}
```

自然生成需要另外调用 Fabric biome API 添加 spawn，并为实体类型注册 spawn restriction。当前内容目录不会自动猜测这些行为。
