# 添加方块与方块实体

`BlockCatalog` 将方块、可选 `BlockItem`、双语翻译、简单模型和默认掉落合并为一个 Java 声明。

```java
public final BlockBuilder<Block> orirockBlock = new BlockBuilder<>(
        Zinecraft.BLOCKS, "orirock_block", "源岩块",
        () -> new Block(BlockBehaviour.Properties.of()
        .strength(3.0F, 6.0F)
        .sound(SoundType.STONE)))
    .enUs("Orirock Block")
    .build();
```

builder 默认掉落自身、生成简单立方体模型并注册 `BlockItem`。使用 `drop(item)` 指定掉落，或通过 `noLoot()`、
`noCubeModel()`、`noBlockItem()` 关闭对应默认行为。`build()` 返回同一个 `BlockBuilder<T>`，可从中取得
`block()`、`blockItem()` 和 `dropItem()`；后两者使用 `Optional` 表示声明中可能没有对应物品。`BlockBuilder` 同时实现
`Supplier<T>` 与 `ItemLike`，需要方块 supplier/物品的调用可直接使用 builder，需要原版实例时调用 `get()`。

方块贴图路径：

```text
src/main/resources/assets/zinecraft/textures/block/<path>.png
```

## 方块实体

`BlockEntityBuilder<E, B>` 同时包含方块实体类型与其对应的 `EntityBlock`。构造时直接传入一个尚未调用
`build()` 的 `BlockBuilder<B>`；方块实体 builder 会先注册方块，再将该方块绑定到实体类型。方块类继承
`BaseEntityBlock` 并实现 `newBlockEntity`。

```java
public static final BlockEntityBuilder<MachineBlockEntity, MachineBlock> MACHINE =
    new BlockEntityBuilder<>(
        Zinecraft.BLOCK_ENTITIES,
        "machine",
        MachineBlockEntity::new,
        new BlockBuilder<>(
            Zinecraft.BLOCKS,
            "machine",
            "机器",
            () -> new MachineBlock(BlockBehaviour.Properties.of().strength(4.0F))
        )
    ).build();

public final class MachineBlockEntity extends BlockEntity {
  public MachineBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntity.MACHINE.get(), pos, state);
  }
}
```

需要方块声明时使用 `MACHINE.entityBlock()`，需要延迟注册类型时使用 `MACHINE.entityType()`；Builder 自身实现
`Supplier<BlockEntityType<E>>`，需要实体类型实例时可直接调用 `get()`。保存状态时覆盖
`saveAdditional`/`loadAdditional`，修改持久化数据后调用 `setChanged()`。

方块实体渲染器只放在 `src/client/java`；通用端不能引用客户端类。

## 国家材料

`ModBlock`
集中声明普通方块、矿石、装饰头颅以及十九国地貌与建筑墙体材料。ID、群系表层用途、结构调色板与纹理规则见 [NATION_MATERIALS.md](NATION_MATERIALS.md)
。修改纹理生成规则后运行对应脚本，并确认输出仍为 16×16 PNG。
