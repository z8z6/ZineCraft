# 添加实体与 Mob

普通实体和 Mob 通过 `Zinecraft.ENTITIES` 注册。Mob 目录管理默认属性、生成限制、生成蛋和掉落；自然生成由目标群系在注册时直接描述。

## 普通实体

```java
Supplier<EntityType<ExampleProjectile>> projectile = Zinecraft.ENTITIES.register(
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

MobBuilder<ExampleMob> mob = new MobBuilder<>(
    Zinecraft.ENTITIES, "example_mob", "示例生物", "Example Mob",
    ExampleMob::new, MobCategory.CREATURE,
    ExampleMob::attributes, restriction,
    builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(8)
).spawnEgg(0x6B7A58, 0xD8C7A1, "示例生物刷怪蛋", "Example Mob Spawn Egg")
 .drop(Items.EMERALD)
 .build();
```

属性和生成限制在 NeoForge 注册生命周期中统一接入。需要自然生成时，在群系声明中直接指定类别、类型、权重和群体范围：

```java
builder.featuredSpawn(MobCategory.CREATURE, mob.get(), 8, 1, 3);
```

这样生成规则与群系生态位于同一声明，不再由 `MobBuilder` 生成额外 biome modifier。

`MobBuilder` 同时保存注册声明和构建后的实体类型、刷怪蛋，`build()` 返回自身；通过 `type()` 与 `spawnEgg()` 可分别取得实体类型和刷怪蛋，
builder 本身也实现 `Supplier<EntityType<T>>`。`spawnEgg(...)` 会同步注册刷怪蛋，并自动生成翻译与
`minecraft:item/template_spawn_egg` 模型；`drop(...)`
声明实体战利品，数据生成时自动导出对应的 entity loot table。实体手持装备是否掉落仍由实体自身的装备掉落率控制，和这里声明的战利品相互独立。
不应掉落物品的和平居民使用 `noDrops()` 显式声明，数据生成器会为其输出空实体战利品表。

## 客户端渲染

renderer 和 model layer 放在 `src/client/java`，通过 NeoForge 客户端事件注册。服务端实体、属性、生成条件与 AI 不得引用渲染器。

内置 YSM 居民使用 `NoopRenderer` 占位，并在客户端实体加入世界时通过独立桥接类绑定模型包。萨科塔礼服居民对应
`sankta_formal_resident` 模型，只在 `laterano_holy_fields` 的拉特兰冲积白垩或草方块上自然生成，生成权重为 8，群体数量为
10–20。

## 国家群系生物

各国常规居民优先使用群系生态中直接声明的普通友好生物。只有明确要求接入定制 YSM 外观时才注册专用和平实体；国家关系仍由国家、任务和
服务端状态系统表达，不把国籍状态附加到自然生成的生物实例上。旧居民皮肤只作为未启用的历史素材保留，其来源记录见
[NATION_RESIDENT_TEXTURES.md](NATION_RESIDENT_TEXTURES.md)。

# 泰拉维度生成规则

泰拉维度的自然生成只允许群系声明的友好 Mob。各国家群系的数据声明不包含 `monster` 生成项；服务端的
`TerraMobSpawnPolicy` 仅拦截 `MobSpawnType.NATURAL` 的非友好 Mob。命令、刷怪蛋、刷怪笼、结构生成和跨维度进入均可绕过该规则。
