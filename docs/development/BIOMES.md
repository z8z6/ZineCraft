# 修改群系

本页讲解如何修改现有泰拉群系的名称、天气、颜色、植被、特色生物、地面方块和出现位置。第一次修改时建议只改一项，确认游戏中生效后再继续。

## 需要使用的文件

| 修改内容                  | 文件                                                                         |
|-----------------------|----------------------------------------------------------------------------|
| 名称、天气、温度、颜色、植被类型、特色生物 | `src/main/java/com/cxxcxx/zinecraft/core/biome/NationBiomes.java`          |
| 群系在泰拉中的分布             | `src/main/java/com/cxxcxx/zinecraft/core/biome/NationBiomePlacements.java` |
| 地面方块                  | `src/main/java/com/cxxcxx/zinecraft/core/biome/ModSurfaceRule.java`        |
| 可选的植被组合               | `src/main/java/com/cxxcxx/zinecraft/core/biome/NationBiomePresets.java`    |

不要直接修改：

```text
src/generated/resources/data/zinecraft/worldgen/biome/
```

这里是 `runData` 的输出。

## 1. 找到目标群系

以下是十九国群系 ID：

| 国家   | 群系 ID                          |
|------|--------------------------------|
| 阿戈尔  | `aegir_abyssal_sea`            |
| 玻利瓦尔 | `bolivar_plain`                |
| 东    | `higashi_shadow_rift`          |
| 杜林   | `durin_underground_garden`     |
| 哥伦比亚 | `columbia_sandstone_wilds`     |
| 卡西米尔 | `kazimierz_knightland`         |
| 卡兹戴尔 | `kazdel_scarred_wastes`        |
| 拉特兰  | `laterano_holy_fields`         |
| 莱塔尼亚 | `leithanien_twilight_forest`   |
| 雷姆必拓 | `rim_billiton_mining_badlands` |
| 米诺斯  | `minos_sunlit_hills`           |
| 萨尔贡  | `sargon_rocky_desert`          |
| 萨米   | `sami_frozen_forest`           |
| 维多利亚 | `victoria_misty_highlands`     |
| 乌萨斯  | `ursus_frozen_steppe`          |
| 谢拉格  | `kjerag_snowy_peaks`           |
| 叙拉古  | `siracusa_rainy_woodland`      |
| 炎    | `yan_mountain_grove`           |
| 伊比利亚 | `iberia_salt_delta`            |

在 `NationBiomes.java` 中搜索大写形式。例如修改维多利亚时搜索：

```text
VICTORIA_MISTY_HIGHLANDSHelper0
```

你会找到一个设置温度、雨量、颜色和预设的方法。

## 2. 修改群系名称

在 `NationBiomes.java` 中搜索：

```java
"biome.zinecraft.victoria_misty_highlands"
```

修改后面的中文和英文：

```java
Zinecraft.TRANSLATIONS.add(
    "biome.zinecraft.victoria_misty_highlands",
    "维多利亚雾雨高地",
    "Victoria Misty Highlands"
);
```

不要修改翻译键中的群系 ID。修改完成后运行 `runData`。

## 3. 修改天气和温度

在目标方法中修改：

```java
_this_register.setPrecipitation(true);
_this_register.setTemperature(0.45F);
_this_register.setDownfall(0.8F);
```

可按下表选择入门值：

| 想要的环境 | `setPrecipitation` | `setTemperature` | `setDownfall` |
|-------|--------------------|------------------|---------------|
| 寒冷多雪  | `true`             | `-0.8F`          | `0.6F`        |
| 温和多雨  | `true`             | `0.5F`           | `0.8F`        |
| 温和少雨  | `true`             | `0.7F`           | `0.25F`       |
| 炎热干燥  | `false`            | `1.2F`           | `0.0F`        |

温度和降雨量通常使用 `-1.0F` 到 `1.5F`。极端值可能带来积雪、结冰或颜色变化，修改后一定要在游戏中观察。

### 示例：让维多利亚更潮湿

找到维多利亚方法中的：

```java
_this_register.setDownfall(0.7F);
```

改为：

```java
_this_register.setDownfall(0.9F);
```

保存并运行 `runData`，然后新建世界测试。

## 4. 修改草、树叶、水和雾的颜色

颜色以十进制整数写入。最方便的做法是先准备十六进制颜色，再用 PowerShell 转换。

例如把 `#607A58` 转成十进制：

```powershell
[Convert]::ToInt32('607A58', 16)
```

输出：

```text
6322776
```

在群系方法中使用：

```java
_this_register.setGrassColor(6322776);
_this_register.setFoliageColor(5263440);
_this_register.setWaterColor(4159204);
_this_register.setWaterFogColor(329011);
_this_register.setFogColor(12638463);
```

只需要修改你希望固定的颜色。没有设置的颜色由游戏根据温度和湿度计算。

## 5. 修改植被和地貌风格

目标方法中会有一行预设，例如：

```java
NationBiomePresets.INSTANCE.rainyForest(_this_register);
```

可以替换为项目已有预设：

| 预设            | 适合场景        |
|---------------|-------------|
| `plains`      | 草原、农田、平缓地区  |
| `forest`      | 普通森林        |
| `rainyForest` | 潮湿森林、蕨类较多地区 |
| `mountain`    | 山地、草甸、裸岩地区  |
| `snowyForest` | 寒冷针叶林       |
| `desert`      | 沙漠和干旱地区     |
| `badlands`    | 荒地、矿区、干燥峡谷  |
| `jungle`      | 茂密热带地区      |
| `wetland`     | 湿地和沼泽       |
| `ocean`       | 海洋          |
| `cavern`      | 地下洞穴        |

### 示例：把普通森林改成多雨森林

修改前：

```java
NationBiomePresets.INSTANCE.forest(_this_register);
```

修改后：

```java
NationBiomePresets.INSTANCE.rainyForest(_this_register);
```

不要在同一个群系中连续调用两个完整预设，否则树木、矿物或装饰可能重复添加。

## 6. 修改特色生物

群系方法中可能有：

```java
NationBiomePresets.INSTANCE.featuredSpawn(
    _this_register,
    MobCategory.CREATURE,
    EntityType.SHEEP,
    12,
    2,
    4
);
```

最后三个数字依次是：

```text
出现权重、每群最少数量、每群最多数量
```

例如把羊换成兔子，并减少成群数量：

```java
NationBiomePresets.INSTANCE.featuredSpawn(
    _this_register,
    MobCategory.CREATURE,
    EntityType.RABBIT,
    8,
    1,
    2
);
```

规则：

- 权重必须大于 `0`。
- 最少数量必须大于 `0`。
- 最多数量不能小于最少数量。
- 泰拉自然生成只允许友好生物和国家居民；不要把怪物加入自然生成示例。

## 7. 修改地面方块

打开：

```text
src/main/java/com/cxxcxx/zinecraft/core/biome/ModSurfaceRule.java
```

搜索目标群系 getter。例如维多利亚：

```java
NationBiomes.INSTANCE.getVICTORIA_MISTY_HIGHLANDS()
```

它附近的上一行是地面规则：

```java
ruleSource1 = this.ecologicalSurface(NationBlocks.INSTANCE.getVICTORIA_MOORLAND_SOIL());
```

改成已有国家方块的示例：

```java
ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getVICTORIA_INDUSTRIAL_BRICK());
```

两种常用写法：

| 写法                       | 效果            |
|--------------------------|---------------|
| `singleBlock(...)`       | 表面全部使用指定方块    |
| `ecologicalSurface(...)` | 指定方块之间混入少量草方块 |

如果要使用新方块，先完成[修改和新增方块](BLOCKS.md)，再回来替换 getter。

## 8. 修改群系出现位置

打开：

```text
src/main/java/com/cxxcxx/zinecraft/core/biome/NationBiomePlacements.java
```

每个群系有六个主要数值：

```java
INSTANCE.placement(
    NationBiomes.INSTANCE.getVICTORIA_MISTY_HIGHLANDS(),
    -0.2F, 0.7F, 0.4F, 0.0F, 0.0F, 0.65F
)
```

按顺序可以理解为：

```text
温度、湿度、陆地程度、侵蚀程度、深度、地形变化
```

萌新修改时建议：

1. 一次只改一个值。
2. 每次改动不超过 `0.2F`。
3. 不要复制其他群系的整组数值，否则两个群系可能争夺同一位置。
4. 地表群系通常保持深度 `0.0F`；地下群系才使用明显不同的深度。

### 示例：让维多利亚更常出现在潮湿区域

把第二个值从 `0.7F` 改成 `0.85F`：

```java
INSTANCE.placement(
    NationBiomes.INSTANCE.getVICTORIA_MISTY_HIGHLANDS(),
    -0.2F, 0.85F, 0.4F, 0.0F, 0.0F, 0.65F
)
```

## 9. 生成和验证

依次运行：

```powershell
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
.\gradlew.bat runClient
```

新建世界，进入泰拉后执行：

```mcfunction
/locate biome zinecraft:victoria_misty_highlands
```

传送到返回坐标附近，检查：

- 天气和积雪是否符合预期。
- 草、树叶、水和雾的颜色是否协调。
- 树木、花草和矿物有没有异常重复。
- 特色生物数量是否过多。
- 地面方块是否出现在新生成区块。
- 相邻群系边界有没有非常突兀。

## 常见错误

| 现象                  | 处理方法                      |
|---------------------|---------------------------|
| `/locate biome` 找不到 | 检查 `runData` 是否成功，并新建世界   |
| 颜色完全没有变化            | 检查是否改对了目标方法，确认十六进制已转成十进制  |
| 群系没有树或花             | 检查是否仍调用了一个植被预设            |
| 生物完全不出现             | 检查类别、权重和数量，并确认实体适合该环境     |
| 地面还是旧方块             | 旧区块不会更新，请新建世界或前往未生成区块     |
| 泰拉出现原版怪物            | 不要在群系预设中添加怪物自然生成；检查泰拉生成规则 |

## 完成检查

- [ ] 只修改了目标群系。
- [ ] 群系 ID 没有随意改名。
- [ ] 没有手改生成目录中的群系 JSON。
- [ ] `test`、`runData`、`build` 成功。
- [ ] 已在新世界检查颜色、植被、生物、地面和边界。
