# 添加方块

`BlockCatalog.register` 将方块注册、`BlockItem`、双语名称、简单模型和默认掉落合并为一次声明。

## 普通立方体方块

```kotlin
val ORIROCK_BLOCK = ZinecraftCore.BLOCKS.register(
  path = "orirock_block",
  zhCn = "源岩块",
  enUs = "Orirock Block"
) {
  Block(
    BlockBehaviour.Properties.of()
      .strength(3.0f, 6.0f)
      .sound(SoundType.STONE)
  )
}.block
```

默认行为包括：

- 注册方块及同 ID 的 `BlockItem`。
- 生成中英文名称。
- 生成简单立方体模型、方块状态和物品模型。
- 生成“掉落自身”的战利品表。
- 创建创造标签页时自动加入标签页。

方块贴图放在：

```text
src/main/resources/assets/zinecraft/textures/block/orirock_block.png
```

## 关闭默认生成

复杂方块可以逐项关闭默认行为：

```kotlin
val MACHINE = ZinecraftCore.BLOCKS.register(
  path = "machine",
  zhCn = "机器",
  enUs = "Machine",
  dropSelf = false,
  cubeModel = false,
  registerItem = true
) {
  MachineBlock(BlockBehaviour.Properties.of().strength(4.0f))
}.block
```

关闭后需要自行提供对应的战利品表、模型或方块状态数据。

## 方块实体

先声明方块，再通过 `BLOCK_ENTITIES` 注册方块实体类型：

```kotlin
class MachineBlockEntity(pos: BlockPos, state: BlockState) :
  BlockEntity(ModBlockEntities.MACHINE, pos, state)

object ModBlockEntities {
  val MACHINE = ZinecraftCore.BLOCK_ENTITIES.register(
    "machine",
    ::MachineBlockEntity,
    ModBlocks.MACHINE
  )
}
```

方块需要继承 `BaseEntityBlock` 并实现：

```kotlin
override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
  MachineBlockEntity(pos, state)
```

保存状态时覆盖 `saveAdditional` / `loadAdditional`，修改数据后调用 `setChanged()`。仅客户端渲染器必须放在 `src/client`。
