# 物品、方块与方块实体

## 物品

在 `core/item` 的内容对象中通过 `Zinecraft.ITEMS` 声明：

```kotlin
val ORIROCK = Zinecraft.ITEMS.register(
  path = "orirock",
  zhCn = "源岩",
  enUs = "Orirock"
)

val MAGIC_DUST = Zinecraft.ITEMS.register(
  path = "magic_dust",
  zhCn = "魔法粉尘"
) { Item(Item.Properties().stacksTo(16)) }
  .fuel(600)
  .compost(0.3f)
```

`register` 返回 `ItemEntry<T>`；用 `.item` 获取实例，entry 本身也实现了 `ItemLike`。可传 `ModelTemplate`；默认使用
`FLAT_ITEM`。目录自动登记中英文名称和物品模型，PNG 仍需放在
`src/main/resources/assets/zinecraft/textures/item/<path>.png`。

优先直接使用 `ItemEntry<T>`，不要再引入只改名、不增加语义的包装类型。若多个内容确实共享创建规则，可在对应 `core`
对象内增加小型私有辅助函数。

## 方块

```kotlin
val ORIGINIUM_BLOCK = Zinecraft.BLOCKS.register(
  path = "originium_block",
  zhCn = "源石块",
  enUs = "Originium Block"
) {
  Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK))
}
```

`BlockCatalog.register` 的开关：

- `dropSelf = true`：数据生成器生成自身掉落表。
- `cubeModel = true`：生成普通 cube-all 方块状态与模型。
- `registerItem = true`：同时注册对应方块物品。

特殊方块状态、非立方模型或特殊掉落应关闭对应自动项，并提供明确的资源/数据生成实现。返回的 `BlockEntry<T>` 用 `.block` 获取实例。

## 方块实体

先注册方块，再绑定方块实体类型：

```kotlin
val TERMINAL = Zinecraft.BLOCK_ENTITIES.register(
  "terminal",
  ::TerminalBlockEntity,
  ModBlock.TERMINAL.block
)
```

至少绑定一个方块。若需要 renderer，把注册放到 `src/client/kotlin`，不要从 `src/main/kotlin` 引用客户端类。

## 创造模式页与初始化

物品和方块目录会由 `CreativeTabCatalog` 汇总。新增独立内容对象时，在 `core/Zinecraft.kt` 和数据生成入口显式访问；在现有
`ModItem`、`ModBlock` 中追加声明则不需要新增入口。

参考实现：

- `src/main/kotlin/com/cxxcxx/zinecraft/api/item/ItemCatalog.kt`
- `src/main/kotlin/com/cxxcxx/zinecraft/api/block/BlockCatalog.kt`
- `src/main/kotlin/com/cxxcxx/zinecraft/api/block/BlockEntityCatalog.kt`
- `docs/item/README.md`、`docs/block/README.md`
