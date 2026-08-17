# 修改藏品

本页讲解如何修改集成战略藏品的名称、说明、稀有度、Minecraft 效果和图片，以及怎样验证饰品栏和能力面板。

## 先了解藏品文件

| 内容            | 文件                                                                          |
|---------------|-----------------------------------------------------------------------------|
| 245 件藏品目录     | `src/main/resources/zinecraft/collectibles/phantom_crimson_solitaire.json`  |
| PRTS/游戏数据导入脚本 | `script/import_prts_is2_collectibles.py`                                    |
| 特殊效果覆盖        | `src/main/java/com/cxxcxx/zinecraft/core/item/ModCollectibles.java`         |
| 自动识别普通效果      | `src/main/java/com/cxxcxx/zinecraft/core/item/CollectiblePowerAdapter.java` |
| 藏品图片          | `src/main/resources/assets/zinecraft/textures/item/`                        |
| 图片摘要清单        | `script/data/prts_is2_image_sha256.json`                                    |
| Curios 藏品标签   | `src/main/resources/data/curios/tags/item/relic.json`                       |
| 来源记录          | `docs/item/PRTS_COLLECTIBLES.md`                                            |

藏品只要装备在任意 Curios 饰品槽中就会生效，不需要限定在名为 `relic` 的槽位。

## 1. 找到目标藏品

打开：

```text
src/main/resources/zinecraft/collectibles/phantom_crimson_solitaire.json
```

可以按编号、中文名或来源 ID 搜索。例如 No.097“钝爪-百战”：

```text
097
钝爪-百战
rogue_1_relic_p05
```

每条数据类似：

```json
{
  "path": "collectible_blunt_claws_mastery",
  "orderId": "097",
  "sourceId": "rogue_1_relic_p05",
  "iconId": "rogue_1_relic_p05",
  "zhCn": "钝爪-百战",
  "enUs": "钝爪-百战",
  "originalEffectZhCn": "……",
  "originalEffectEnUs": "……",
  "descriptionZhCn": "……",
  "descriptionEnUs": "……",
  "rarity": "RARE"
}
```

字段含义：

| 字段                | 用途                              | 是否可以随意改          |
|-------------------|---------------------------------|------------------|
| `path`            | Minecraft 物品 ID，由官方英文名转成小写下划线格式 | 一般不要手改；重新导入时自动生成 |
| `orderId`         | PRTS 档案编号                       | 不可以              |
| `sourceId`        | 游戏数据 ID                         | 不可以              |
| `iconId`          | PRTS 图片文件 ID                    | 不可以，除非上游资料改变     |
| `zhCn`            | 中文名                             | 只按资料原文修正         |
| `enUs`            | 英文服官方显示名                        | 由英文游戏数据导入，不要自行翻译 |
| `originalEffect*` | 明日方舟原效果                         | 只按资料原文修正         |
| `description*`    | 明日方舟描述                          | 只按资料原文修正         |
| `rarity`          | Minecraft 稀有度                   | 应与导入映射保持一致       |

目录由导入脚本生成。直接修改 JSON 适合临时验证；正式修正应该修改上游输入或导入规则，再重新运行脚本。

## 2. 重新导入全部藏品

最安全的离线检查命令：

```powershell
python script/import_prts_is2_collectibles.py --skip-images
```

它会：

- 检查游戏数据摘要。
- 检查 245 件藏品是否齐全。
- 检查 ID 和必填字段。
- 检查现有 PNG 与 SHA-256 清单。
- 重建藏品目录、Curios 标签和来源记录。

如果你有经过核对的本地游戏数据文件：

```powershell
python script/import_prts_is2_collectibles.py `
  --game-data "D:\ArknightsGameData\roguelike_topic_table.json" `
  --english-game-data "D:\ArknightsGameData\roguelike_topic_table_en.json" `
  --skip-images
```

如果摘要不一致，脚本会停止。不要为了通过检查直接改摘要；先人工确认上游数据为什么变化。

## 3. 修改 Minecraft 适配效果

原效果文字不能为了 Minecraft 玩法而改写。Minecraft 适配效果单独定义在：

```text
src/main/java/com/cxxcxx/zinecraft/core/item/ModCollectibles.java
```

搜索：

```text
createPowerOverrides()
```

覆盖表使用 `sourceId`，不是 Minecraft `path`。

### 示例：攻击力增加 15%

```java
Map.entry(
    "rogue_1_relic_a14",
    statPercent(
        "攻击力+15%",
        "+15% ATK",
        CombatStat.ATTACK,
        0.15
    )
)
```

百分比要写成小数：

| 原效果    | Java 数值 |
|--------|---------|
| `+10%` | `0.10`  |
| `+25%` | `0.25`  |
| `-40%` | `-0.40` |

### 示例：攻击速度增加 30 点

```java
Map.entry(
    "rogue_1_relic_example",
    statFlat(
        "攻击速度+30",
        "+30 ASPD",
        CombatStat.ATTACK_SPEED,
        30.0
    )
)
```

攻击速度 `+30` 是 30 点，不要写成 `0.30`。项目会在最终写入原版攻击速度时换算成基础倍率。

### 示例：同时增加攻击和防御

```java
Map.entry(
    "rogue_1_relic_example",
    stats(
        "攻击力+20%，防御力+10%",
        "+20% ATK and +10% DEF",
        percent(CombatStat.ATTACK, 0.20),
        percent(CombatStat.DEFENSE, 0.10)
    )
)
```

### 示例：每秒回复最大生命值 1%

```java
Map.entry(
    "rogue_1_relic_example",
    new PowerOverride(
        "每秒回复1%的最大生命值",
        "Recover 1% of maximum HP every second",
        new CollectiblePower.Regeneration(0.01F, 20)
    )
)
```

`20` 表示 20 tick，也就是约 1 秒。

### 可以使用的基础属性

| 写法                        | 最终原版属性 |
|---------------------------|--------|
| `CombatStat.MAX_HEALTH`   | 最大生命值  |
| `CombatStat.ATTACK`       | 攻击伤害   |
| `CombatStat.DEFENSE`      | 盔甲值    |
| `CombatStat.RESISTANCE`   | 盔甲韧性   |
| `CombatStat.ATTACK_SPEED` | 攻击速度   |

明日方舟机制只用于解释和换算原效果，最终值会回到原版属性，因此 L2 原有“能力”面板和其他模组能够看到。

## 4. 让普通文字效果自动识别

没有专用覆盖的藏品会经过：

```text
src/main/java/com/cxxcxx/zinecraft/core/item/CollectiblePowerAdapter.java
```

它能够识别常见无条件效果，例如：

```text
攻击力+15%
防御力+25%
最大生命值+20%
法术抗性+10
攻击速度+30
每秒回复1%的最大生命值
```

带有“攻击后”“技能开启时”“每有一名干员”等条件的效果不会被强行近似为原版属性，而会保留为等待对应系统触发的来源规则。

如果新增一种无法自动识别的效果，优先在 `powerOverrides` 为具体藏品添加明确适配。不要为了省事把闪避、部署费用或招募规则伪装成幸运、经验或移动速度。

## 5. 修改稀有度

目录中的可用值：

```text
UNCOMMON
RARE
EPIC
```

导入脚本当前把游戏数据映射为：

| 游戏数据         | Minecraft 稀有度 |
|--------------|---------------|
| `NORMAL`     | `UNCOMMON`    |
| `RARE`       | `RARE`        |
| `SUPER_RARE` | `EPIC`        |

如果只是临时测试，可以修改单条 JSON 的 `rarity`。正式修改必须调整导入脚本中的 `RARITY_MAP`，然后重新导入；否则下次运行脚本会恢复。

不要根据效果强弱自行改变 PRTS 档案稀有度。若项目确实需要独立的 Minecraft 稀有度规则，应在文档中明确它是玩法适配。

## 6. 修改藏品图片

藏品 PNG 位于：

```text
src/main/resources/assets/zinecraft/textures/item/<path>.png
```

例如：

```text
src/main/resources/assets/zinecraft/textures/item/collectible_blunt_claws_mastery.png
```

图片来自 PRTS 原始资源，不生成、不重绘、不使用占位图。

### 正常重新检查图片

```powershell
python script/import_prts_is2_collectibles.py --skip-images
```

### 上游图片确认发生变化时

只有人工对比并确认 PRTS 图片确实更新后，才执行：

```powershell
python script/import_prts_is2_collectibles.py `
  --refresh `
  --update-image-digests
```

这会更新 PNG 和摘要清单。不要只为消除 SHA-256 报错而使用 `--update-image-digests`。

## 7. 验证饰品栏生效

1. 启动客户端。
2. 使用 `/give` 获得目标藏品：

   ```mcfunction
   /give @s zinecraft:collectible_blunt_claws_mastery
   ```

3. 打开物品栏，进入 L2/Curios“饰品”页。
4. 把藏品放入任意饰品槽。
5. 打开 L2 原有“能力”页。
6. 记录装备前后的最终原版属性。
7. 把藏品移出饰品栏，确认属性恢复。

### 示例检查

如果藏品是攻击力 `+15%`：

1. 装备前记录原版攻击伤害。
2. 装备藏品。
3. 新数值应反映该百分比修正。
4. 手持武器时再次查看，确认武器属性和藏品属性共同显示。
5. 移除藏品后不应残留永久修正。

回复类藏品还要测试：

- 满血时不重复治疗。
- 受伤后按设定间隔回复。
- 移出饰品栏后立即停止。
- 死亡、重生和切换维度后不会重复叠加。

## 8. 生成和验证

修改目录、图片或效果后依次运行：

```powershell
python script/import_prts_is2_collectibles.py --skip-images
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
.\gradlew.bat runClient
```

如果只是修改 `powerOverrides`，可以不重新导入目录，但仍要运行 `test` 和 `build`。

## 常见错误

| 现象           | 处理方法                                |
|--------------|-------------------------------------|
| 修改 JSON 后又恢复 | 目录由导入脚本生成，应修改上游数据或导入规则              |
| 藏品无法放入饰品栏    | 检查 `curios:relic` 标签是否包含该物品         |
| 装备后属性面板没变化   | 确认使用 `CombatStatBoost`，重新装备并检查原版能力页 |
| 攻速增长异常       | `+30` 应写 `30.0`，不是 `0.30`           |
| 回复在客户端看起来跳动  | 确认效果由服务端结算，没有同时添加客户端治疗              |
| 图片摘要不匹配      | 对比 PRTS 原图；未确认上游变化时不要更新摘要           |
| 条件效果被错误常驻    | 删除错误属性适配，改为具体触发或 `SourceRule`       |
| 移除藏品后属性不恢复   | 检查修改是否通过 Curios 属性修正，而不是永久写入玩家数据    |

## 完成检查

- [ ] 中文原文已和官网、游戏数据或 PRTS 核对。
- [ ] `path` 与官方英文名生成的路径一致，编号与来源 ID 没有被手工修改。
- [ ] Minecraft 适配文字与原效果分开。
- [ ] 攻速点数和百分比没有混淆。
- [ ] 图片来源和 SHA-256 记录完整。
- [ ] `test`、`runData`、`build` 成功。
- [ ] 已测试任意饰品槽、能力面板、移除恢复和服务端效果。
