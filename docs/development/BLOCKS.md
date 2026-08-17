# 修改和新增方块

本页适用于普通完整方块，例如石块、砖块、金属板和地面材料。楼梯、门、透明机器或带方块实体的设备不属于本教程范围。

## 示例目标

我们将新增一个名为“测试石砖”的方块：

```text
完整 ID：zinecraft:test_stone_bricks
中文名：测试石砖
英文名：Test Stone Bricks
物理手感：和原版石砖相同
破坏后：掉落自身
```

## 1. 准备贴图

制作一张 16×16 PNG，文件名必须是：

```text
test_stone_bricks.png
```

放入：

```text
src/main/resources/assets/zinecraft/textures/block/test_stone_bricks.png
```

注意：

- 文件名只能使用小写英文字母、数字和下划线。
- 不要写空格、中文或大写字母。
- 图片必须是真正的 PNG，不能只是把 JPG 后缀改成 `.png`。
- 普通方块最好完全不透明，否则可能出现透视和遮挡问题。

## 2. 打开方块文件

打开：

```text
src/main/java/com/cxxcxx/zinecraft/core/block/ModBlock.java
```

找到其他 `BlockEntry` 声明。在相同区域加入：

```java
private static final BlockEntry<Block> TEST_STONE_BRICKS =
    Zinecraft.BLOCKS.register(
        "test_stone_bricks",
        "测试石砖",
        "Test Stone Bricks",
        true,
        null,
        true,
        true,
        () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS))
    );
```

如果文件中缺少导入，编辑器通常会提示自动导入。需要的类型是：

```java
import com.cxxcxx.zinecraft.api.block.BlockEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
```

## 3. 添加获取方法

在类的其他 getter 附近加入：

```java
public Block getTEST_STONE_BRICKS() {
  return TEST_STONE_BRICKS.getBlock();
}
```

这个方法让群系、结构或其他内容能够引用新方块。

## 4. 生成模型和掉落

保存文件，然后依次运行：

```powershell
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
```

`runData` 完成后，应出现：

```text
src/generated/resources/assets/zinecraft/blockstates/test_stone_bricks.json
src/generated/resources/assets/zinecraft/models/block/test_stone_bricks.json
src/generated/resources/assets/zinecraft/models/item/test_stone_bricks.json
src/generated/resources/data/zinecraft/loot_table/blocks/test_stone_bricks.json
```

这些文件用于确认生成成功，不要直接修改它们。

## 5. 游戏内测试

启动客户端并执行：

```mcfunction
/give @s zinecraft:test_stone_bricks 64
```

依次检查：

1. 物品栏图标是否正确。
2. 方块放下后六个面是否正确。
3. 生存模式破坏后是否掉落自身。
4. 破坏声音和速度是否接近石砖。
5. 中文和英文名称是否正确。

## 修改已有方块的名称

例如要把“测试石砖”改为“测试切石砖”，只修改注册处的中英文名称：

```java
"测试切石砖",
"Test Cut Stone Bricks",
```

不要修改已经发布的 `test_stone_bricks` ID，否则旧世界中的方块可能丢失。修改后运行 `runData` 和 `build`。

## 修改硬度和声音

最安全的方法是换一个原版模板：

```java
() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS))
```

常用模板：

| 想要的手感 | 模板                    |
|-------|-----------------------|
| 普通石头  | `Blocks.STONE`        |
| 石砖    | `Blocks.STONE_BRICKS` |
| 木板    | `Blocks.OAK_PLANKS`   |
| 金属块   | `Blocks.IRON_BLOCK`   |
| 松软泥土  | `Blocks.DIRT`         |
| 砂砾    | `Blocks.GRAVEL`       |

更换模板只改变硬度、声音等交互感受，不会替换你的 PNG。

## 修改掉落

注册示例中的这两项控制掉落：

```java
true,  // 掉落自身
null,  // 不指定其他物品
```

如果方块不掉落自身，而是掉落已有物品，例如原版圆石，改为：

```java
false,
Blocks.COBBLESTONE,
```

不能同时设置“掉落自身”和“掉落指定物品”。修改后必须重新运行 `runData`。

## 常见错误

| 现象           | 处理方法                                  |
|--------------|---------------------------------------|
| 游戏中是紫黑方块     | 检查 PNG 路径和文件名是否与 ID 完全一致              |
| `/give` 不能补全 | 检查 Java 是否编译成功、ID 是否拼错                |
| 放下正常但物品图标缺失  | 确认注册示例中的最后一个布尔值为 `true`，再运行 `runData` |
| 破坏没有掉落       | 确认掉落自身为 `true`，再检查生成的 Loot Table      |
| 改名后没有变化      | 重新运行 `runData` 并重启客户端                 |
| 编译提示缺少类型     | 使用编辑器自动导入，或对照本页列出的 import             |

## 完成检查

- [ ] ID 全部使用小写下划线。
- [ ] PNG 与 ID 同名。
- [ ] 已添加 getter。
- [ ] `test`、`runData`、`build` 全部成功。
- [ ] `/give`、放置、破坏和掉落都经过游戏内测试。
