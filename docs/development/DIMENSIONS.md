# 修改泰拉维度

本页讲解如何修改泰拉维度名称、固定地图、昼夜和天空环境、群系组成，以及怎样测试星门传送。教程只修改现有
`zinecraft:terra`，不建议萌新更改维度 ID。

## 需要使用的文件

| 修改内容            | 文件                                                                                      |
|-----------------|-----------------------------------------------------------------------------------------|
| 维度名称、地图边长、群系坐标  | `src/main/java/com/cxxcxx/zinecraft/core/registry/ModDimension.java`                    |
| 天空、床、坐标比例、高度等环境 | `src/main/java/com/cxxcxx/zinecraft/api/world/dimension/DimensionHelper.java`           |
| 泰拉使用的群系内容       | `src/main/java/com/cxxcxx/zinecraft/core/registry/ModBiome.java`                        |
| 首次玩家出生          | `src/main/java/com/cxxcxx/zinecraft/core/dimension/TerraPlayerSpawn.java`               |
| 星门单向目标          | `src/main/java/com/cxxcxx/zinecraft/core/structure/stargate/StarGateTeleporter.java`    |
| JourneyMap 国家边界 | `src/client/java/com/cxxcxx/zinecraft/compat/journeymap/ZinecraftJourneyMapPlugin.java` |

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

## 2. 修改固定地图

泰拉地图边长由 `ModDimension.TERRA_MAP_SIZE` 定义，正式值为 `100000`。国家锚点集中声明在
`ModDimension.TERRA_MAP`，完整现值见[泰拉固定群系地图](../dimension/TERRA_MAP.md)。

每个国家必须恰好出现一次，并直接绑定 `ModBiome.NATIONAL_BIOMES` 中的专属群系池；遗漏国家、重复国家、跨国复用群系，或国家群系
ID
不以国家 ID 开头都会在启动数据生成时失败。外海不声明国家锚点，天灾区是非国家特殊区域。锚点必须位于 `±49000` 陆地区域内部。
拉特兰保持 `(0, 0)`，否则固定原点设施与首次玩家出生点需要同步迁移。

运行 `runData` 后检查 `src/generated/resources/data/zinecraft/dimension/terra.json`，确认
`map_size` 为 `100000`、`ocean_ring_width` 为 `1000`，且 `regions` 中每个国家只有一个条目、`biomes` 只含该国群系。

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

这表示主世界和泰拉的坐标一一对应。当前星门直接前往主世界出生区域，不使用坐标缩放；仍建议保留默认值以免其他跨维度机制产生意外位置。

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

泰拉群系内容来自：

```text
src/main/java/com/cxxcxx/zinecraft/core/registry/ModBiome.java
```

群系气候、地表、地物和生物仍在 `ModBiome` 修改；国家水平位置在 `ModDimension.TERRA_MAP` 调整。新增国家群系时，ID 必须使用
`<nation_id>_<environment>` 格式，并加入该国的 `NATIONAL_BIOMES` 列表；群系源会在该列表内按六轴气候选取。

## 6. 不要随意修改维度 ID

当前 ID 是：

```text
zinecraft:terra
```

它同时被星门、命令、世界存档、结构和生成规则引用。把 `terra` 改成其他名称会让旧世界无法找到原维度，并需要同步修改多个文件。

如果只是想改玩家看到的名字，请只改翻译，不要改 ID。

## 7. 星门传送测试

新玩家从泰拉开始，萨米星门只通向主世界。修改维度后至少测试：

1. 新玩家首次登录是否出现在泰拉 `(0, 0)` 的安全地表，并获得泰拉重生点。
2. 再次登录是否保留当前维度，而不是重复执行首次传送。
3. `/locate structure zinecraft:stargate` 是否只在萨米固定点找到一座星门。
4. 使用协议源石激活后是否抵达主世界出生区域。
5. 主世界放置的控制器是否拒绝激活，且不会形成通往泰拉的入口。
6. JourneyMap 全屏地图是否显示十九国边界与本地化名称。

也可以直接使用命令：

```mcfunction
/execute in zinecraft:terra run tp @s 0 120 0
/execute in zinecraft:terra run locate biome zinecraft:laterano_holy_fields
/execute in zinecraft:terra run locate structure zinecraft:laterano_host
/execute in zinecraft:terra run worldborder get
/execute in zinecraft:terra run locate structure zinecraft:stargate
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
- 世界边界是否为 `100000` 格。
- 各国家群系是否位于坐标表对应方向，且没有重复区域。
- 天空、昼夜、床和环境光是否符合设置。
- 星门是否只能从泰拉单向传送到主世界。
- JourneyMap 是否只在泰拉显示十九国边界和名称。
- 泰拉没有自然生成原版怪物。

## 常见错误

| 现象             | 处理方法                                |
|----------------|-------------------------------------|
| 改生成 JSON 后又恢复  | 不要手改生成目录；修改 Java 后运行 `runData`      |
| 地图边长没有变化       | 检查 `TERRA_MAP_SIZE`，并使用新世界验证世界边界    |
| 其他国家群系找不到      | 检查 `NATIONAL_BIOMES` 是否归入正确国家且气候点可达 |
| 星门没有生成         | 使用新世界，并检查萨米固定区块是否已在旧版本生成过           |
| JourneyMap 无边界 | 确认客户端安装满足版本范围的 JourneyMap，并查看客户端日志  |
| 旧世界环境没变化       | 维度数据在建世界时加载，使用新世界验证                 |
| 世界创建时报群系错误     | 泰拉只允许 Zinecraft 群系，检查是否误加入原版 ID     |
| 地下结构超出世界       | 恢复默认高度，或调整结构 Y 位置                   |

## 完成检查

- [ ] 没有修改 `zinecraft:terra` ID。
- [ ] 没有手改生成的维度 JSON。
- [ ] 地图边长、锚点坐标和生成 JSON 一致。
- [ ] `test`、`runData`、`build` 成功。
- [ ] 已在新世界测试固定地图、世界边界、首次出生、萨米唯一星门和 JourneyMap 边界。
