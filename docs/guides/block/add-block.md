# 添加方块

普通方块通过 [ModBlock.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModBlock.java) 和 [BlockBuilder.java](../../src/main/java/com/cxxcxx/zinecraft/api/registry/builder/BlockBuilder.java) 注册。

## 1. 从普通立方体开始

```java
public static final BlockBuilder<Block> EXAMPLE_STONE = block(
    "example_stone",
    "示例石材",
    Blocks.STONE
);
```

第三个参数是行为和属性参考方块。默认流程会同时注册方块物品、cube-all 模型和自身掉落。

### block(...) 的参数

| 参数 | 示例 | 含义 |
| --- | --- | --- |
| `path` | `"example_stone"` | 方块注册 ID，同时决定默认纹理、模型和 blockstate 文件名。 |
| `zhCn` | `"示例石材"` | 方块及默认方块物品的中文显示名。 |
| `physicalTemplate` | `Blocks.STONE` | 通过 `Properties.ofFullCopy(...)` 复制硬度、爆炸抗性、声音、摩擦等物理属性；不会复制纹理。 |

需要自定义行为时，第三个参数改用 `Supplier<T>`，例如 `() -> new MyBlock(Properties.of().strength(3.0F))`。

### 常用链式方法

| 方法 | 含义 |
| --- | --- |
| `.enUs(name)` | 覆盖从 ID 自动生成的英文名。 |
| `.drop(item)` | 正常采集时掉落指定物品，不再掉落自身。 |
| `.noLoot()` | 不生成自动战利品表；并不等于以后不能手写 loot table。 |
| `.noCubeModel()` | 关闭自动 cube-all blockstate/模型，必须手工补资源。 |
| `.noBlockItem()` | 不注册对应方块物品，玩家不能通过普通物品形式持有它。 |
| `.itemProperties(properties)` | 设置自动生成的 BlockItem 属性，例如稀有度。 |
| `.build()` | 执行校验并交给 BlockCatalog 注册；只能调用一次。 |

## 2. 添加方块纹理

```text
src/main/resources/assets/zinecraft/textures/block/example_stone.png
```

需要朝向、多个状态或非立方模型时，才关闭自动 cube 模型并手工提供 blockstate、方块模型和物品模型。

## 3. 交互方块与方块实体

简单交互放入专用 `Block` 子类。只有确实需要保存数据时才增加 BlockEntity，并按功能实现 NBT 保存、同步、ticker 或菜单。注册入口见 [ModBlockEntity.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModBlockEntity.java)。

## 4. 矿石不是只有一个方块

矿石还要通过 `OreBuilder` 接入 FeatureCatalog 和目标群系；采掘标签放在 `data/minecraft/tags/block/mineable`，工具等级标签也需要显式维护。

## 5. 验证

运行 `test`、`runData` 和 `build`，检查 blockstate、方块/物品模型、掉落、采掘工具、创造栏和世界生成。交互方块还要测试退出世界后数据是否保留。
