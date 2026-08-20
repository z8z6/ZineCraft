# 修改结构和替换 NBT

本页讲解如何找到一座结构使用的 NBT、用游戏内结构方块制作替换模板、处理多模块建筑，以及验证自然生成。

## 先判断结构类型

| 类型    | 常见例子                              | NBT 位置                                         | 是否由脚本重建 |
|-------|-----------------------------------|------------------------------------------------|---------|
| 单模板结构 | 星门、拉特兰地下主机                        | `src/main/resources/data/zinecraft/structure/` | 通常否     |
| 国家聚落  | `victoria_industrial_borough`     | `structure/nation_settlements/`                | 是       |
| 国家地标  | `victoria_steam_station`          | `structure/nation_landmarks/`                  | 是       |
| 预览结构  | `victoria_defence_cannon_preview` | 目标预览目录                                         | 看对应脚本   |

如果 NBT 位于 `nation_settlements` 或 `nation_landmarks`，不要只手改最终 NBT。运行对应 Python 脚本时，它会被覆盖。

## 1. 从结构 ID 找到 NBT

### 聚落

公开 ID 在：

```text
src/main/java/com/cxxcxx/zinecraft/core/registry/ModStructure.java
```

例如搜索：

```text
victoria_industrial_borough
```

对应模块目录：

```text
src/main/resources/data/zinecraft/structure/nation_settlements/victoria_industrial_borough/
```

聚落通常包含：

```text
center.nbt
street_straight.nbt
street_corner.nbt
street_cross.nbt
street_end.nbt
四种功能建筑.nbt
```

### 国家地标

公开 ID 在：

```text
src/main/java/com/cxxcxx/zinecraft/core/registry/ModStructure.java
```

例如：

```text
victoria_steam_station
```

对应目录：

```text
src/main/resources/data/zinecraft/structure/nation_landmarks/victoria_steam_station/
```

标准地标通常包含六个模块：

```text
foundation.nbt
core.nbt
facade.nbt
roof.nbt
annex.nbt
surrounding.nbt
```

只替换 `core.nbt` 不会改变地基、立面、屋顶、附属和周边模块。

### 单模板结构

拉特兰地下主机：

```text
src/main/resources/data/zinecraft/structure/laterano_host/core.nbt
```

萨米固定星门：

```text
src/main/resources/data/zinecraft/structure/stargate.nbt
```

## 2. 查看模板池实际引用

运行一次：

```powershell
.\gradlew.bat runData
```

然后打开：

```text
src/generated/resources/data/zinecraft/worldgen/template_pool/
```

以维多利亚工业区为例：

```text
src/generated/resources/data/zinecraft/worldgen/template_pool/victoria_industrial_borough/buildings.json
```

文件中的 `location` 会写出实际 NBT 路径，例如：

```json
"location": "zinecraft:nation_settlements/victoria_industrial_borough/brick_tenement"
```

对应：

```text
src/main/resources/data/zinecraft/structure/nation_settlements/victoria_industrial_borough/brick_tenement.nbt
```

模板池 JSON 只能查看，不能手改。

## 3. 用结构方块制作 NBT

### 3.1 建立测试世界

运行客户端并新建一个允许作弊的平坦创造世界。执行：

```mcfunction
/give @s minecraft:structure_block
```

找一块空地建造模板。建议先用羊毛标出边界，再开始建造。

### 3.2 放置保存结构方块

在建筑一个角落放置结构方块，打开后切换到“保存”模式。

填写名称，例如：

```text
zinecraft:my_test_house
```

填写相对位置和尺寸。萌新建议先做不超过 32×32×32 的模板，确认流程后再扩大。

点击“检测”只会寻找同名角落方块；如果没有使用角落模式，可以直接手工填写偏移和尺寸。

确认白色边框完整包住建筑，然后点击“保存”。

### 3.3 找到游戏保存的 NBT

开发客户端的世界通常位于：

```text
run/saves/<世界名称>/generated/zinecraft/structures/my_test_house.nbt
```

把这个文件复制到项目资源目录。若要临时替换星门，可以复制为：

```text
src/main/resources/data/zinecraft/structure/stargate.nbt
```

替换前先在项目外备份原文件。

### 3.4 验证手写 NBT

重新启动客户端或重新进入世界，然后执行：

```mcfunction
/place structure zinecraft:stargate
```

检查尺寸、方向、方块实体和容器。

## 4. 替换聚落模块

以替换维多利亚工业区的砖砌公寓为例。

目标文件：

```text
src/main/resources/data/zinecraft/structure/nation_settlements/victoria_industrial_borough/brick_tenement.nbt
```

操作步骤：

1. 在测试世界放出当前聚落：

   ```mcfunction
   /place structure zinecraft:victoria_industrial_borough
   ```

2. 记录道路高度、门口方向和模块占地。
3. 在空地重建公寓，门口位置必须与旧模块一致。
4. 保留负责连接的 Jigsaw 方块，不要把它们当成多余方块删除。
5. 用结构方块保存为：

   ```text
   zinecraft:nation_settlements/victoria_industrial_borough/brick_tenement
   ```

6. 从测试世界的 `generated/zinecraft/structures/` 找到同路径 NBT。
7. 将 NBT 复制到项目中的相同路径。
8. 立即用 `/place structure` 做临时验证。

重要：这个目录由 `script/generate_nation_settlements.py` 管理。手工替换只能作为测试样板。正式保留改动时，需要把尺寸、材料、房间和连接点同步写入生成脚本中的目标建筑计划，再运行脚本重建。

## 5. 修改脚本生成的聚落

生成脚本：

```text
script/generate_nation_settlements.py
```

部分国家还有便于维护的分文件：

```text
script/nation_settlements/<国家>.py
```

操作步骤：

1. 在脚本中搜索公开聚落 ID，例如 `victoria_industrial_borough`。
2. 再搜索模块名，例如 `brick_tenement`。
3. 修改该模块的尺寸、材料、门、房间或家具计划。
4. 不要修改其他国家的共享参数，除非希望所有聚落一起变化。
5. 运行：

   ```powershell
   python script/generate_nation_settlements.py
   ```

6. 看到生成和校验成功后，检查目标 NBT 的修改时间。
7. 再运行 `test`、`runData`、`build`。

如果你只会用结构方块而不会修改 Python，请保留导出的测试 NBT，并把模块名、尺寸、门口位置和截图一起交给维护生成脚本的开发者。不要直接声称手改
NBT 已完成正式接入。

## 6. 修改脚本生成的国家地标

生成脚本：

```text
script/generate_nation_landmarks.py
```

部分国家的专用模块位于：

```text
script/nation_landmarks/<国家>.py
```

操作步骤：

1. 搜索地标 ID，例如 `victoria_steam_station`。
2. 确认六个模块都受到哪些修改影响。
3. 先改 `foundation` 和 `core`，验证总体尺寸。
4. 再改 `facade`、`roof`、`annex` 和 `surrounding`。
5. 运行：

   ```powershell
   python script/generate_nation_landmarks.py
   ```

6. 执行：

   ```mcfunction
   /place structure zinecraft:victoria_steam_station
   ```

7. 从四个方向进入结构，检查模块是否正确拼接。

## 7. Jigsaw 方块检查

编辑多模块结构时，每个接口至少检查：

- 父模块出口对着子模块入口。
- 两边地板高度相同。
- 门洞至少两格高。
- 接口前后没有实心方块堵路。
- `target` 与另一侧的 `name` 匹配。
- `pool` 指向正确模板池。
- `final_state` 是拼接后希望留下的方块，通常为空气或道路方块。

如果结构只生成第一个模块，优先检查 Jigsaw 接口，而不是盲目增加结构大小。

## 8. 战利品箱

结构中的箱子不要直接塞入固定物品。应给箱子设置 Loot Table，例如：

```text
zinecraft:chests/nation/victoria_defence_cannon_supply
```

在结构方块保存模板前，确认箱子的战利品表 ID 拼写正确。放置结构后第一次打开箱子，才会生成物品。

测试时至少打开三个不同箱子，确认：

- 没有空表报错。
- 物品主题符合房间用途。
- 藏品概率没有高到每箱必出。
- 同一个箱子不会重复初始化。

## 9. 完整验证

依次运行：

```powershell
python script/generate_nation_settlements.py   # 只在修改聚落时
python script/generate_nation_landmarks.py     # 只在修改地标时
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
.\gradlew.bat runClient
```

游戏内执行：

```mcfunction
/place structure zinecraft:<结构 ID>
/locate structure zinecraft:<结构 ID>
```

检查清单：

1. 朝北、南、东、西观察轮廓。
2. 走遍入口、走廊、楼梯和屋顶出口。
3. 检查每处门洞上方有两格净空。
4. 检查灯是否有墙、梁、链或灯柱支撑。
5. 打开容器检查战利品。
6. 检查居民是否能在房间和道路中移动。
7. 新建世界使用 `/locate structure` 验证自然生成。

## 常见错误

| 现象                      | 处理方法                               |
|-------------------------|------------------------------------|
| 只出现起始模块                 | 检查 Jigsaw 的 `name`、`target`、方向和模板池 |
| 建筑埋进地下或悬空               | 检查地基高度、保存偏移和结构使用的高度图               |
| 手改 NBT 又变回去了            | 该目录由 Python 脚本生成，应修改脚本来源           |
| `/place structure` 不能补全 | 检查公开结构 ID，而不是模块 NBT 名称             |
| 模块重叠                    | 检查模块尺寸、接口方向和拼接距离                   |
| 箱子为空且有报错                | 检查 Loot Table ID 和对应 JSON 是否存在     |
| 旧世界还是旧建筑                | 已生成结构不会更新，请在新位置重新放置或新建世界           |

## 完成检查

- [ ] 已确认目标是手写 NBT 还是脚本生成 NBT。
- [ ] 没有手改模板池生成 JSON。
- [ ] 多模块结构保留并验证了 Jigsaw 接口。
- [ ] 聚落或地标生成脚本运行成功。
- [ ] `test`、`runData`、`build` 成功。
- [ ] 已完成 `/place`、`/locate`、动线和战利品检查。
