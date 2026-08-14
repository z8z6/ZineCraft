# 实体与 Mob

## 选择入口

- 不需要属性和自然生成的投射物、载具等：`Zinecraft.ENTITIES.register`。
- `Mob` 子类：`Zinecraft.ENTITIES.mob`，让目录统一注册属性和生成限制。

```kotlin
val SCOUT = Zinecraft.ENTITIES.mob(
  path = "scout",
  zhCn = "侦察者",
  enUs = "Scout",
  factory = ::Scout,
  category = MobCategory.CREATURE,
  attributes = Scout::attributes,
  spawnRestriction = MobSpawnRestriction(
    SpawnPlacementTypes.ON_GROUND,
    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
    Scout::canSpawn
  )
) {
  sized(0.6f, 1.8f)
  clientTrackingRange(8)
}
```

目录已经调用默认属性与生成限制注册；不要再重复调用 `FabricDefaultAttributeRegistry` 或原版内部注册方法。

## 自然生成与生成蛋

```kotlin
SCOUT.naturalSpawn(
  weight = 10,
  minGroupSize = 1,
  maxGroupSize = 3,
  biomes = BiomeSelectors.includeByKey(NationBiomes.VICTORIA_MISTY_HIGHLANDS)
)

val SCOUT_EGG = SCOUT.spawnEgg(
  primaryColor = 0x3D4A57,
  secondaryColor = 0xD5C7A1,
  zhCn = "侦察者生成蛋"
)
```

省略 `biomes` 会进入全部主世界群系，因此国家特色生物必须传明确的群系选择器。生成蛋会自动生成翻译与原版模板模型，不需要 PNG。

生成谓词还要与群系地表匹配。例如 `ON_GROUND` 生物不能只得到自身不接受的岩石表层；必要时在群系地表中保留合法生态斑块，或实现适合该生物的谓词。

## 客户端渲染

在 `src/client/kotlin` 注册 renderer 和 model layer：

```kotlin
EntityRendererRegistry.register(ModEntities.SCOUT.type) { context ->
  ScoutRenderer(context)
}
```

实体类、AI、属性和自然生成属于通用端；renderer、模型、纹理装配属于客户端。专用服务器必须能在完全不加载客户端包的情况下启动。

## 默认装备

人形 Mob 的默认武器在服务端 `finalizeSpawn` 写入 `EquipmentSlot.MAINHAND`，不要由 renderer、客户端动画或生成蛋物品临时伪造。
若装备来自 TaCZ 等可选外部内容，必须提供项目内置武器回退，确保专用服务器没有枪包时仍满足实体定义；外部枪械通过
`ModTaczWeapons.gunStack` 创建，不能手写不完整的 Data Component。自然生成 Mob 的特殊装备应显式设置掉落概率，避免意外成为动态资源复制来源。

“默认持枪”只表示装备与表现。需要 Mob 实际射击时应新增服务端权威的 Mob 武器 AI，不能复用玩家 C2S 输入，也不能让客户端动画直接结算伤害。

国家居民还必须实现 `NationAffiliated`。十九国完整覆盖、关系系统连接方式和资料边界见 `references/nations.md`。

## 验证

检查属性注册、生成限制、群系筛选、生成蛋模型和客户端渲染器。构建通过后，在目标群系验证自然生成，并使用生成蛋检查尺寸、碰撞箱、纹理和动画。

参考：`src/main/kotlin/com/cxxcxx/zinecraft/api/entity/EntityCatalog.kt` 与 `docs/entity/README.md`。
