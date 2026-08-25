# 添加生物

生物包含服务端 AI、注册数据和客户端模型三部分。参考 [ModEntity.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModEntity.java) 以及 `core/entity`、`client/entity` 中最接近的现有实现。

## 1. 实现服务端实体

先在 `core/entity` 创建实体类，定义属性、AI goal、目标选择和交互。不要在这个类里引用客户端 renderer。

## 2. 使用 MobBuilder 注册

```java
public static final MobBuilder<MyEntity> MY_ENTITY =
    new MobBuilder<>(
        Zinecraft.ENTITIES,
        "my_entity",
        "示例生物",
        MyEntity::new,
        MobCategory.CREATURE,
        MyEntity::attributes,
        spawnRestriction,
        builder -> builder.sized(0.8F, 1.2F)
    )
    .spawnEgg(0x554433, 0xCCAA66, "示例生物刷怪蛋", "Example Mob Spawn Egg")
    .noDrops()
    .build();
```

### MobBuilder 构造参数

| 参数 | 含义 |
| --- | --- |
| `Zinecraft.ENTITIES` | 项目的 EntityCatalog，负责实体类型、属性、翻译和刷怪蛋注册。 |
| `"my_entity"` | 实体 ID，最终为 `zinecraft:my_entity`。 |
| `"示例生物"` | 实体中文名；英文名默认从 ID 生成，可用 `.enUs(...)` 覆盖。 |
| `MyEntity::new` | `EntityType.EntityFactory`，必须匹配实体构造器。 |
| `MobCategory.CREATURE` | 原版生成类别，影响生成上限、和平模式和刷怪策略。 |
| `MyEntity::attributes` | 默认属性 Builder 的供应器，通常包含生命、移速、攻击等。 |
| `spawnRestriction` | 自然生成位置、Heightmap 和 predicate；不需要自然生成时可为 `null`。 |
| `configure` | 原版 `EntityType.Builder` 回调，常用于 `sized(width, height)` 和追踪距离。 |

### spawnEgg(...) 的参数

`primary` 和 `secondary` 是 `0xRRGGBB` 格式的主色、斑点色；后两个参数分别是刷怪蛋中英文名。`.drop(item)` 可重复调用声明简单掉落，`.noDrops()` 表示明确无掉落，两者不能混用。

必须明确选择 `drop(...)` 或 `noDrops()`。刷怪蛋不会自动让生物自然生成。

## 3. 接入自然生成

在目标 [ModBiome.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModBiome.java) 中调用 `featuredSpawn(...)`。SpawnRestriction 只判断位置是否合格，群系生成表才决定它是否会被自然选中。

## 4. 添加客户端表现

把纹理放入 `assets/zinecraft/textures/entity`，模型和动画按现有原生管线维护，并只在 `ZinecraftCoreClient` 注册 renderer。这样专用服务端不会错误加载客户端类。

## 5. 验证

除 `runData`、`test`、`build` 外，还要在客户端检查 AI、碰撞、动画和生成，并启动专用服务端确认没有 client-only 类加载错误。
