# 添加方块与方块实体

`BlockCatalog` 将方块、可选 `BlockItem`、双语翻译、简单模型和默认掉落合并为一个 Java 声明。

```java
public final DeferredBlock<Block> orirockBlock = Zinecraft.BLOCKS
    .builder("orirock_block", "源岩块",
        () -> new Block(BlockBehaviour.Properties.of()
        .strength(3.0F, 6.0F)
        .sound(SoundType.STONE)))
    .enUs("Orirock Block")
    .build();
```

builder 默认掉落自身、生成简单立方体模型并注册 `BlockItem`。使用 `drop(item)` 指定掉落，或通过 `noLoot()`、
`noCubeModel()`、`noBlockItem()` 关闭对应默认行为。`build()` 返回 NeoForge 原生 `DeferredBlock<T>`，factory 保持懒加载；
声明和数据生成元数据由 `BlockBuilder` 自身保存，不需要额外的 entry 包装。需要原版实例时调用 `get()`。

方块贴图路径：

```text
src/main/resources/assets/zinecraft/textures/block/<path>.png
```

## 方块实体

先注册方块，再用 `BLOCK_ENTITIES` 将 factory 与一个或多个有效方块绑定。方块类继承 `BaseEntityBlock` 并实现
`newBlockEntity`。保存状态时覆盖 `saveAdditional`/`loadAdditional`，修改持久化数据后调用 `setChanged()`。

```java
public final class MachineBlockEntity extends BlockEntity {
  public MachineBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.MACHINE.get(), pos, state);
  }
}
```

方块实体渲染器只放在 `src/client/java`；通用端不能引用客户端类。

## 国家材料

`NationBlocks`
为十九国分别声明地貌与建筑墙体材料。ID、群系表层用途、结构调色板与纹理规则见 [NATION_MATERIALS.md](NATION_MATERIALS.md)
。修改纹理生成规则后运行对应脚本，并确认输出仍为 16×16 PNG。
