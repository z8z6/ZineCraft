# 添加结构

独立世界结构通过 [ModStructure.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModStructure.java)、[JigsawBuilder.java](../../src/main/java/com/cxxcxx/zinecraft/api/registry/builder/JigsawBuilder.java) 和结构 NBT 共同完成。

## 1. 选择结构类型

- 野外独立建筑：普通 `JigsawBuilder`。
- 城市建筑：`embeddedBuilding(...)`。
- 城市基础设施：`embeddedInfrastructure(...)`。
- 多区域城市布局：修改城市与 Region 数据，而不是只新增一个结构。

## 2. 准备声明与模板

结构声明必须确定目标群系、起始 pool、模板 ID、生成深度、地形适配和 placement。NBT 放在：

```text
src/main/resources/data/zinecraft/structure/<id>.nbt
```

大型建筑按现有约定拆分为 `foundation`、`core`、`facade`、`roof`、`annex` 和 `surrounding`。

### JigsawBuilder 参数与常用方法

| 参数或方法 | 含义 |
| --- | --- |
| `catalog` | `Zinecraft.STRUCTURES`，项目唯一的结构目录。 |
| `path` | 结构、结构集和默认模板池共用的基础 ID。 |
| `zhCn` | 结构中文显示名；英文名可用 `.enUs(...)` 设置。 |
| `.startPool(name)` | Jigsaw 展开的入口模板池名称。 |
| `.pool(name, projection, callback)` | 定义模板池；callback 中的 `template(id, weight)` 指定 NBT 和相对权重。 |
| `.biome(key)` | 只允许在一个群系生成；多个群系使用 `.allowedBiomes(list)`。 |
| `.randomSpread(spacing, separation, salt)` | 平均区块间距、最小区块间距和稳定随机盐；spacing 必须大于 separation。 |
| `.layout(size, maxDistance)` | Jigsaw 最大展开深度和距中心的最大方块距离。 |
| `.height(heightmap, offset)` | 使用的高度图和起始高度偏移；heightmap 为 `null` 时使用固定高度。 |
| `.generation(step, adjustment)` | 生成阶段和地形适配方式。 |
| `.embedded()` | 仅供城市系统或命令放置，不创建自然生成结构集。 |
| `.footprint(x, z)` | 城市建筑默认朝南时占用的区块宽度和长度。 |
| `.build()` | 校验并生成 structure、structure_set 和 template_pool 数据。 |

`Projection.RIGID` 保持模板自身高度；`TERRAIN_MATCHING` 会随地形投影。建筑不是自然散布时不要随意填写 `randomSpread`。

## 3. 数据生成不会创造建筑内容

`runData` 可以生成 structure、structure_set 和 template_pool JSON，但不会替你搭建 NBT。仓库里的生成脚本可能覆盖目标文件，运行前必须确认路径并查看 Git 状态。

## 4. 验证

```mcfunction
/locate structure zinecraft:<id>
/place structure zinecraft:<template>
```

随后检查目标群系、旋转、地形衔接和分块边界。移动地块建筑必须在尚未生成的泰拉区块验证四层道路、楼梯和 footprint。
