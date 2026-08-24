# 移动地块分层与商店结构替换流程

本文说明如何替换移动地块的动力层、支持层、生活层、贯通楼梯，以及地表建筑 NBT。

## 1. 当前生成方式

一个 Region 的移动地块由以下内容组成：

1. 动力、支持、生活和地表四层分别计算道路与 Parcel。
2. 下三层的非道路 Chunk 放置对应层建筑，地表放置 Region 建筑。
3. 四层在同一组 `stair_chunks` 放置 16 格高楼梯段，形成至少四条连续竖井。
   楼梯在核心区四个象限内尽量分散，四层坐标严格对齐。
4. 建筑模板默认正面朝南；支持多个入口的模板用 `connectionFaces(...)` 声明。

| 内容 | NBT 尺寸/占地 | 相对 Region 地基高度 |
| --- | --- | --- |
| 动力层 | 16×16×16 blocks | 0 |
| 支持层 | 16×16×16 blocks | 16 |
| 生活层 | 16×16×16 blocks | 32 |
| 地表道路与建筑底面 | 按注册尺寸 | 48 |
| 各层楼梯段 | 16×16×16 blocks | 0 / 16 / 32 / 48 |

实际地基起点是城市 <code>ground_y + 1</code>。例如动力层使用
<code>ground_y + 1</code>，建筑底面使用 <code>ground_y + 49</code>。

分层建筑只放在本层非道路 Chunk；道路与楼梯会替代相应 Chunk。商店按地表规划阶段分配的完整 Chunk 矩形放置。

相关实现：

- [ModStructure.java](../src/main/java/com/cxxcxx/zinecraft/core/registry/ModStructure.java)
- [StructureCatalog.java](../src/main/java/com/cxxcxx/zinecraft/api/registry/catalog/StructureCatalog.java)
- [MobilePlotStructure.java](../src/main/java/com/cxxcxx/zinecraft/api/world/structure/MobilePlotStructure.java)
- [ModCityRegion.java](../src/main/java/com/cxxcxx/zinecraft/core/registry/ModCityRegion.java)

## 2. NBT 文件位置

### 2.1 三层移动地块

~~~text
src/main/resources/data/zinecraft/structure/mobile_plot_power_layer.nbt
src/main/resources/data/zinecraft/structure/mobile_plot_support_layer.nbt
src/main/resources/data/zinecraft/structure/mobile_plot_life_layer.nbt
~~~

三个文件相互独立，可以分别替换。

楼梯模板位于：

~~~text
src/main/resources/data/zinecraft/structure/mobile_plot_stair.nbt
~~~

楼梯必须保持 16×16×16，并保证顶部路径能与上一段底部继续连接。

### 2.2 商店

普通商店注册为 1×1 Chunk，模板最大水平范围为 16×16 blocks：

~~~text
src/main/resources/data/zinecraft/structure/<nation_id>_shop.nbt
~~~

中型商店注册为 1×2 Chunk，模板默认最大水平范围为
16 blocks（X）×32 blocks（Z）：

~~~text
src/main/resources/data/zinecraft/structure/<nation_id>_medium_shop.nbt
~~~

例如莱塔尼亚对应：

~~~text
src/main/resources/data/zinecraft/structure/leithanien_shop.nbt
src/main/resources/data/zinecraft/structure/leithanien_medium_shop.nbt
~~~

当前国家 ID 为：

~~~text
aegir, bolivar, higashi, durin, columbia, kazimierz, kazdel,
laterano, leithanien, rim_billiton, minos, sargon, sami,
victoria, ursus, kjerag, siracusa, siesta, yan, iberia
~~~

## 3. NBT 制作规则

### 3.1 坐标与朝向

- 模板原点为本地最小角 <code>(0, 0, 0)</code>。
- 所有方块坐标应为非负坐标，并位于 NBT 的 <code>size</code> 范围内。
- 商店默认正面必须朝南，即 +Z。
- 门、正门台阶和入口道路应放在 <code>z = sizeZ - 1</code> 一侧。
- 不要制作朝北、朝东、朝西的副本；运行时会自动旋转。

| 朝向 | Rotation | 世界占地 |
| --- | --- | --- |
| 南 | <code>NONE</code> | X×Z |
| 西 | <code>CLOCKWISE_90</code> | Z×X |
| 北 | <code>CLOCKWISE_180</code> | X×Z |
| 东 | <code>COUNTERCLOCKWISE_90</code> | Z×X |

例如，注册为 1×2 Chunk 的中型商店朝东或朝西时，会占用世界中的 2×1 Chunk。

### 3.2 分层结构

- 每个层级 NBT 必须保持 16×16×16 blocks。
- 本地 Y 范围为 0..15，不得把方块放到 y=16。
- 相邻 Chunk 会直接拼接，跨边界的地板、管线和外框必须使用一致接口。
- 三个下层建筑不会旋转；道路按四向连接掩码旋转。
- 每层均独立拥有道路；生活层之上的地表层从相对高度 48 开始。
- 如果模板包含空气，放置时可能清除目标位置已有方块。只想保留原方块的位置应使用结构空位。

如果要把层级改成非 16 高，不能只替换 NBT。还必须同步修改：

- <code>MobilePlotStructure.LAYER_HEIGHT</code>
- <code>MobilePlotStructure.MOBILE_PLOT_HEIGHT</code>
- <code>StructureCatalog.enableMobilePlots(...)</code> 中的层级 Y 偏移
- Region 分层校验和布局导出文档

通常应保留 16 高，只替换内部结构。

### 3.3 商店结构

- 普通商店不得越出其 1×1 Chunk 占地。
- 中型商店默认不得越出其 1×2 Chunk 占地。
- 高度当前不按 Chunk 数注册，但不应向下穿入生活层。
- 建筑可以小于注册占地，但入口和主体必须完全位于占地矩形中。
- 模板越界时，运行时会抛出“建筑模板越出注册 Chunk 占地”。
- 模组方块必须是运行环境中的强制依赖，否则模板中的方块无法可靠解析。

## 4. 推荐制作与导出流程

1. 启动开发客户端：

   ~~~powershell
   .\gradlew.bat runClient
   ~~~

2. 创建专门用于结构制作的超平坦测试世界。
3. 获取结构方块：

   ~~~mcfunction
   /give @s minecraft:structure_block
   ~~~

4. 使用 F3+G 显示 Chunk 边界，按目标尺寸建造。
5. 建造时保持模板正面朝南（+Z）。
6. 将结构方块切换到保存模式，填写相对位置和尺寸：

   - 分层：16×16×16。
   - 普通商店：X/Z 不超过 16×16。
   - 中型商店：X/Z 不超过 16×32。

7. 结构名称使用目标资源 ID，例如：

   ~~~text
   zinecraft:mobile_plot_power_layer
   zinecraft:leithanien_shop
   zinecraft:leithanien_medium_shop
   ~~~

8. 通常关闭“包含实体”，然后保存。

开发世界导出的文件通常位于：

~~~text
run/saves/<world>/generated/zinecraft/structures/<path>.nbt
~~~

将文件复制到第 2 节对应的
<code>src/main/resources/data/zinecraft/structure/</code> 路径。注意世界导出目录使用
<code>structures</code>，数据包资源目录使用 <code>structure</code>。

也可以使用兼容 Minecraft 1.21.1 的 NBT 编辑器或生成脚本，但文件必须是合法的 gzip 压缩结构 NBT，并使用兼容的数据版本。

## 5. 两种替换路径

### 5.1 同 ID、同尺寸替换

这是推荐路径：

1. 只替换对应的 NBT 文件。
2. 不修改 <code>ModStructure</code>。
3. 不修改 <code>ModCityRegion</code>。
4. 不手工修改生成的 template pool JSON。
5. 执行第 8 节的生成和验证命令。

模板池由数据生成自动创建。例如
<code>zinecraft:leithanien_medium_shop/start</code> 会自动引用
<code>zinecraft:leithanien_medium_shop</code>。

### 5.2 修改商店占地

只有模板水平尺寸超过现有占地，或者希望规划器分配不同大小的 Parcel 时，才修改注册尺寸。

普通商店通过以下形式注册：

~~~java
Zinecraft.STRUCTURES.embeddedBuilding(
    "leithanien_shop", "莱塔尼亚商铺",
    chunksX, chunksZ, maxDistanceFromCenter
);
~~~

中型商店当前由 <code>ModStructure.mediumShop(...)</code> 统一注册为
1×2 Chunk。修改尺寸时：

1. 在 <code>ModStructure</code> 中修改
   <code>footprintChunksX</code> 和 <code>footprintChunksZ</code>。
2. 确保 <code>maxDistanceFromCenter</code> 足以覆盖模板，且不超过 112。
3. 同步修改维护该 NBT 的生成脚本。
4. 重新生成泰拉布局，使
   <code>building_slots.chunk_area</code> 与新尺寸一致。
5. 检查新尺寸在旋转后仍能装入道路旁的 Parcel。

如果只有一个国家使用特殊尺寸，不要修改所有中型商店共用的尺寸；应把该国家改为单独的
<code>embeddedBuilding(...)</code> 注册。

### 5.3 改名或新增商店

改名或新增 ID 还必须同步：

1. <code>ModStructure</code> 中的结构声明。
2. <code>ModStructure.shopFor(...)</code> 或
   <code>mediumShopFor(...)</code> 的映射。
3. <code>ModCityRegion</code> 中引用该建筑的 Region 候选。
4. NBT 路径和维护脚本中的 ID。
5. template pool、mobile plot 结构 JSON 和泰拉压缩布局。

如果只换外观，应保留原 ID。

## 6. 生成脚本与覆盖风险

当前以下脚本生成的是临时方盒模板：

| 脚本 | 会覆盖的内容 |
| --- | --- |
| <code>script/generate_mobile_plot_power_layer.py</code> | 三个分层 NBT 和六种道路 NBT |
| <code>script/generate_city_building_matchboxes.py</code> | 全部普通商店、中型商店、地标和部分特殊结构 |
| <code>script/generate_nation_shop_blockouts.py</code> | 全部普通商店 |

这些脚本不会被 Gradle 自动执行，但手工运行会直接覆盖对应 NBT。

正式替换后应选择一种维护方式：

1. 把世界导出的 NBT 作为正式源文件，并停止运行会覆盖它的临时方盒脚本。
2. 修改生成脚本，使脚本能够可复现地生成新的正式结构。

运行批量写 NBT 的脚本前，先检查目标目录：

~~~powershell
git status --short -- src/main/resources/data/zinecraft/structure
~~~

不要在未确认改动的情况下运行批量覆盖脚本。

## 7. 不要手工编辑的生成资源

~~~text
src/generated/resources/data/zinecraft/worldgen/template_pool/
src/generated/resources/data/zinecraft/worldgen/structure/mobile_plot.json
src/generated/resources/data/zinecraft/terra_layout/
~~~

它们分别记录：

- NBT 与模板池的对应关系。
- 三层、道路、建筑列表及建筑注册占地。
- 每个国家、城市和 Region 最终使用的建筑槽位。

修改注册尺寸或建筑 ID 后必须重新生成这些资源。只替换同 ID、同尺寸的 NBT 时，布局不会变化，但仍需重新构建以把 NBT 打入 JAR。

## 8. 验证流程

### 8.1 完整构建

~~~powershell
.\gradlew.bat runData
.\gradlew.bat test
.\gradlew.bat build
~~~

也可以一次执行：

~~~powershell
.\gradlew.bat runData test build
~~~

验证点：

- 注册的模板池能够找到 NBT。
- 商店尺寸正确写入 mobile plot 结构 JSON。
- 压缩布局按新尺寸重新规划。
- 最终 JAR 包含手写 NBT。

### 8.2 游戏内直接验证

~~~mcfunction
/place structure zinecraft:mobile_plot_power_layer
/place structure zinecraft:mobile_plot_support_layer
/place structure zinecraft:mobile_plot_life_layer
/place structure zinecraft:leithanien_shop
/place structure zinecraft:leithanien_medium_shop
~~~

重点检查：

- 三层尺寸均为 16×16×16，叠放时没有缝隙或重叠。
- 商店未越出注册的 Chunk 边界。
- 模板默认门朝南。
- 建筑朝北、东、南、西生成后的旋转结果正确。
- 门与相邻道路相接。
- 方块状态、方块实体和战利品表正常。

随后使用新世界或尚未生成的 Terra Chunk 验证完整 Region。已经保存到世界中的结构不会因 NBT 更新而自动替换。

### 8.3 检查 JAR

~~~powershell
jar tf build/libs/zinecraft-1.0.0.jar |
  Select-String 'data/zinecraft/structure/(mobile_plot|leithanien.*shop)'
~~~

发布前至少确认目标 NBT 出现在 JAR 中。

## 9. 常见异常

| 异常 | 常见原因 |
| --- | --- |
| 缺少模板池或模板 | NBT 路径、结构 ID 或 template pool 不一致 |
| 建筑运行时尺寸与布局注册不一致 | 修改了注册尺寸，但没有重新生成压缩布局 |
| 建筑模板越出注册 Chunk 占地 | NBT 的 X/Z 尺寸或旋转后边界超过 footprint |
| 建筑朝向错误 | 模板没有以南（+Z）作为默认正面 |
| 新 NBT 没有生效 | 测试的是已生成 Chunk，或 JAR 中仍是旧文件 |
| 正式建筑恢复成方盒 | 再次运行了临时 matchbox 生成脚本 |
| 三层之间出现重叠 | 层级 NBT 超过本地 Y 0..15 |
| 相邻 Chunk 出现断缝 | 分层模板的 X/Z 两侧接口不能平铺 |

## 10. 最短检查清单

同尺寸替换商店：

1. 以南（+Z）为正面制作 NBT。
2. 保持普通商店不超过 16×16，中型商店不超过 16×32。
3. 覆盖 <code>src/main/resources/data/zinecraft/structure/&lt;id&gt;.nbt</code>。
4. 不运行 matchbox 脚本。
5. 执行 <code>.\gradlew.bat runData test build</code>。
6. 用 <code>/place structure zinecraft:&lt;id&gt;</code> 验证。
7. 在新生成的 Terra Region 中检查道路朝向和完整放置。

同尺寸替换分层结构：

1. 制作严格的 16×16×16 NBT。
2. 确认模板能够在 X/Z 方向无缝平铺。
3. 覆盖对应的 <code>mobile_plot_*_layer.nbt</code>。
4. 不运行 <code>generate_mobile_plot_power_layer.py</code>。
5. 执行 <code>.\gradlew.bat runData test build</code>。
6. 分别直接放置三层，并在新 Region 中检查实际叠放结果。
