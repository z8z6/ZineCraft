# 修改泰拉维度

本页讲解如何修改泰拉维度名称、拉特兰中心范围、昼夜和天空环境、群系组成，以及怎样测试星门传送。教程只修改现有
`zinecraft:terra`，不建议萌新更改维度 ID。

## 需要使用的文件

| 修改内容             | 文件                                                                            |
|------------------|-------------------------------------------------------------------------------|
| 维度名称、中心半径、泰拉维度声明 | `src/main/java/com/cxxcxx/zinecraft/core/dimension/ModDimension.java`         |
| 天空、床、坐标比例、高度等环境  | `src/main/java/com/cxxcxx/zinecraft/api/world/dimension/DimensionHelper.java` |
| 泰拉使用的群系和分布       | `src/main/java/com/cxxcxx/zinecraft/core/biome/ModBiome.java`                 |
| 星门往返目标           | `src/main/java/com/cxxcxx/zinecraft/core/dimension/StarGateTeleporter.java`   |

以下文件由 `runData` 生成，只用于查看结果：

```text
src/generated/resources/data/zinecraft/dimension/terra.json
src/generated/resources/data/zinecraft/dimension_type/terra.json
```

不要直接编辑它们。

## 1. 修改维度显示名称

打开 `ModDimension.java`，搜索：

```java
"dimension.zinecraft.terra"
```

会看到：

```java
Zinecraft.TRANSLATIONS.add(
    "dimension.zinecraft.terra",
    "泰拉",
    "Terra"
);
```

例如改成：

```java
Zinecraft.TRANSLATIONS.add(
    "dimension.zinecraft.terra",
    "泰拉大陆",
    "Terra"
);
```

不要修改翻译键中的 `terra`。保存后运行 `runData`。

## 2. 修改拉特兰中心范围

泰拉 `(0, 0)` 周围固定为拉特兰圣田。打开 `ModDimension.java`，找到：

```java
public static final int LATERANO_CENTER_RADIUS = 1024;
```

同时检查创建泰拉群系源的位置。它应使用同一个半径常量。如果仍看到直接写出的 `1024`，改为：

```java
new TerraBiomeSource(
    parameterList,
    reference,
    LATERANO_CENTER_RADIUS
)
```

### 示例：把中心半径改成 1536 格

```java
public static final int LATERANO_CENTER_RADIUS = 1536;
```

建议值：

| 目标        | 建议半径            |
|-----------|-----------------|
| 小型测试中心    | `256`           |
| 默认正式中心    | `1024`          |
| 更宽广的拉特兰区域 | `1536` 或 `2048` |

半径不能小于 `64`。数值越大，玩家需要走得越远才能看到其他国家群系。

运行 `runData` 后，打开：

```text
src/generated/resources/data/zinecraft/dimension/terra.json
```

确认其中出现：

```json
"center_radius": 1536
```

## 3. 修改维度环境

打开：

```text
src/main/java/com/cxxcxx/zinecraft/api/world/dimension/DimensionHelper.java
```

找到 `overworldLikeType()` 中的 `new DimensionType(...)`。当前泰拉使用这一组环境设置。

常见修改示例：

### 固定时间

当前第一项是：

```java
OptionalLong.empty()
```

表示正常昼夜循环。固定为正午：

```java
OptionalLong.of(6000L)
```

固定为午夜：

```java
OptionalLong.of(18000L)
```

恢复昼夜循环时改回 `OptionalLong.empty()`。

### 禁止使用床

构造参数中当前 `bed_works` 对应值为 `true`。将相应位置改为 `false` 后，床不能用于睡觉或设置出生点。

这组参数位置不直观，修改前先运行 `runData` 并对照生成的 `dimension_type/terra.json`。每次只改一个值，然后再次运行 `runData`
，确认改动出现在预期字段。

### 修改环境光

当前环境光为：

```java
0.0F
```

例如改成略亮：

```java
0.1F
```

建议保持在 `0.0F` 到 `1.0F`。过高会让洞穴和夜晚几乎不需要照明。

### 修改坐标比例

当前比例为：

```java
1.0
```

这表示主世界和泰拉的坐标一一对应。星门传送也会读取这个比例。萌新不建议修改，否则返回门的位置会发生大范围变化。

## 4. 修改维度高度

当前泰拉使用：

```text
最低 Y：-64
总高度：384
逻辑高度：384
```

这些值在 `DimensionHelper.overworldLikeType()` 中连续出现。

如果确实要调整：

1. 保持最低 Y 和高度符合 Minecraft 的区块高度要求。
2. 同时确认结构、星门和地下主机没有超出新范围。
3. 运行 `runData`，检查 `dimension_type/terra.json` 中的 `min_y`、`height` 和 `logical_height`。
4. 必须新建世界测试。

对于普通内容修改，建议保留默认高度。

## 5. 修改泰拉包含的群系

泰拉外围群系来自：

```text
src/main/java/com/cxxcxx/zinecraft/core/biome/ModBiome.java
```

要改变群系位置或权重感，应修改这里的气候点。具体步骤见[修改群系](BIOMES.md)。

拉特兰圣田不会出现在外围列表中，它固定由中心区域提供。不要为了让它成为中心而复制一条相同的外围气候点。

### 暂时停用一个外围群系

例如测试时暂时停用天灾区，可在 `ModDimension` 构建外围群系列表时临时过滤该 Builder：

```java
builder -> builder != ModBiome.TERRA_CATASTROPHE_ZONE
```

运行 `runData` 后，确认 `dimension/terra.json` 的 `biomes` 数组中不再有该 ID。

正式版本不应无说明地删除已发布国家群系。恢复时把完整条目放回，并确保气候点不与其他群系完全相同。

## 6. 不要随意修改维度 ID

当前 ID 是：

```text
zinecraft:terra
```

它同时被星门、命令、世界存档、结构和生成规则引用。把 `terra` 改成其他名称会让旧世界无法找到原维度，并需要同步修改多个文件。

如果只是想改玩家看到的名字，请只改翻译，不要改 ID。

## 7. 星门传送测试

星门会在主世界和泰拉之间往返。修改维度后至少测试：

1. 从主世界星门进入泰拉。
2. 泰拉目标位置是否安全。
3. 首次进入时是否创建返回门。
4. 从泰拉返回主世界。
5. 再次进入时是否找到已有门，而不是重复生成。

也可以直接使用命令：

```mcfunction
/execute in zinecraft:terra run tp @s 0 120 0
/execute in zinecraft:terra run locate biome zinecraft:laterano_holy_fields
/execute in zinecraft:terra run locate structure zinecraft:laterano_host
/execute in minecraft:overworld run locate structure zinecraft:stargate
```

## 8. 生成和验证

依次运行：

```powershell
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
.\gradlew.bat runClient
```

检查生成文件：

```text
src/generated/resources/data/zinecraft/dimension/terra.json
src/generated/resources/data/zinecraft/dimension_type/terra.json
```

然后新建世界，检查：

- `(0, 0)` 是否仍为拉特兰圣田。
- 中心边界是否接近设置的半径。
- 外围能否找到其他国家群系。
- 天空、昼夜、床和环境光是否符合设置。
- 星门是否可以双向传送。
- 泰拉没有自然生成原版怪物。

## 常见错误

| 现象            | 处理方法                              |
|---------------|-----------------------------------|
| 改生成 JSON 后又恢复 | 不要手改生成目录；修改 Java 后运行 `runData`    |
| 中心半径没有变化      | 检查创建 `TerraBiomeSource` 时是否使用半径常量 |
| 其他国家群系找不到     | 中心半径可能太大，或气候点被删除/重复               |
| 星门传到错误坐标      | 检查坐标比例，默认应为 `1.0`                 |
| 旧世界环境没变化      | 维度数据在建世界时加载，使用新世界验证               |
| 世界创建时报群系错误    | 泰拉只允许 Zinecraft 群系，检查是否误加入原版 ID   |
| 地下结构超出世界      | 恢复默认高度，或调整结构 Y 位置                 |

## 完成检查

- [ ] 没有修改 `zinecraft:terra` ID。
- [ ] 没有手改生成的维度 JSON。
- [ ] 中心半径和生成 JSON 一致。
- [ ] `test`、`runData`、`build` 成功。
- [ ] 已在新世界测试中心、外围、环境和星门往返。
