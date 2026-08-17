# 添加方块与方块实体

`BlockCatalog` 将方块、可选 `BlockItem`、双语翻译、简单模型和默认掉落合并为一个 Java 声明。

```java
public static final BlockEntry<Block> ORIROCK_BLOCK = Zinecraft.BLOCKS.register(
    "orirock_block", "源岩块", "Orirock Block",
    true, null, true, true,
    () -> new Block(BlockBehaviour.Properties.of()
        .strength(3.0F, 6.0F)
        .sound(SoundType.STONE))
);
```

参数含义依次为 `dropSelf`、`dropItem`、`cubeModel`、`registerItem` 和 factory。`dropSelf` 与 `dropItem`
不能同时启用。关闭默认模型或掉落后，必须自行提供对应资源。

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
