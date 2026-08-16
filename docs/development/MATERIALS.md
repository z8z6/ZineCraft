# 修改方块材质和贴图

本页讲解如何找到方块使用的 PNG、直接替换普通贴图、修改十九国原创材质，以及修改由游戏背景裁切的国家建筑材质。

## 先判断贴图属于哪一类

| 类型          | 例子                          | 正确修改位置                                           |
|-------------|-----------------------------|--------------------------------------------------|
| 普通手写贴图      | 测试方块、矿物、设备                  | 直接替换 `textures/block/<id>.png`                   |
| 脚本生成的原创国家材质 | `victoria_moorland_soil`    | 修改 `generate_nation_block_textures.ps1`          |
| 游戏背景裁切材质    | `victoria_industrial_brick` | 修改 `generate_nation_cg_wall_textures.ps1` 的来源与坐标 |

如果不确定，先在两个脚本中搜索方块 ID。能搜到的贴图不能只改最终 PNG，否则下次运行脚本会覆盖。

## 1. 找到方块实际使用的贴图

以 `victoria_moorland_soil` 为例，先查看生成模型：

```text
src/generated/resources/assets/zinecraft/models/block/victoria_moorland_soil.json
```

内容类似：

```json
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "zinecraft:block/victoria_moorland_soil"
  }
}
```

`zinecraft:block/victoria_moorland_soil` 对应：

```text
src/main/resources/assets/zinecraft/textures/block/victoria_moorland_soil.png
```

资源引用中不写 `.png`，但磁盘文件必须带 `.png`。

## 2. 直接替换普通贴图

假设要替换：

```text
src/main/resources/assets/zinecraft/textures/block/test_stone_bricks.png
```

操作步骤：

1. 确认新图片是 PNG。
2. 普通像素方块建议使用 16×16。
3. 将新图片改成与旧文件完全相同的文件名。
4. 覆盖旧文件。
5. 在运行中的客户端按 `F3+T`。
6. 重新拿取或放置方块检查六个面。
7. 运行 `build` 确认图片会进入 JAR。

### 六面使用不同贴图时

如果模型不是 `cube_all`，可能会引用：

```json
"top": "zinecraft:block/example_top",
"bottom": "zinecraft:block/example_bottom",
"side": "zinecraft:block/example_side"
```

这时需要分别修改：

```text
example_top.png
example_bottom.png
example_side.png
```

不要只替换与方块 ID 同名的 PNG。

## 3. 修改原创国家材质

原创国家地貌和专用材质由以下脚本生成：

```text
script/generate_nation_block_textures.ps1
```

### 示例：修改维多利亚雾沼土颜色

在脚本中搜索：

```text
victoria_moorland_soil
```

会看到：

```powershell
@{
  id='victoria_moorland_soil'
  kind='terrain'
  colors=@('#292825','#3F4035','#555B43','#6D7353','#787264')
}
```

把色板改成新的五个颜色，例如略微偏冷：

```powershell
@{
  id='victoria_moorland_soil'
  kind='terrain'
  colors=@('#24282A','#363E3E','#4D5953','#657064','#7C8270')
}
```

然后执行：

```powershell
powershell -ExecutionPolicy Bypass -File script/generate_nation_block_textures.ps1
```

成功后脚本会报告生成了多少张非 CG 国家贴图。

### `kind` 怎么选

不要随意发明值。优先复制项目中已有的类型：

| `kind`       | 适合材质      |
|--------------|-----------|
| `terrain`    | 泥土、砂砾、苔地  |
| `roots`      | 潮湿土壤、根系地面 |
| `cracked`    | 龟裂土、灰烬    |
| `brick`      | 砖墙        |
| `masonry`    | 石砌墙       |
| `panel`      | 平整装配板     |
| `plate`      | 金属装甲板     |
| `corrugated` | 波纹钢板      |
| `marble`     | 大理石或规则石材  |

改变 `kind` 会改变纹理节奏。修改后要检查平铺边缘，不能只看单张 PNG。

## 4. 修改游戏背景裁切材质

十九张国家建筑主体材质来自用户指定的游戏背景固定裁片，脚本是：

```text
script/generate_nation_cg_wall_textures.ps1
```

这些贴图是官方画面的裁切衍生物，不是原创材质。公开发布前必须确认使用权限，并保留来源记录：

```text
docs/block/CG_MATERIAL_SOURCES.md
```

### 示例：调整维多利亚工业砖的裁切区域

在脚本的 `$Crops` 中搜索：

```text
victoria_industrial_brick
```

当前条目类似：

```powershell
[pscustomobject]@{
  Nation = 'victoria'
  Target = 'victoria_industrial_brick'
  Source = '21_G3_victoria_street_d.png'
  X = 235
  Y = 115
  Width = 128
  Height = 128
}
```

如果要把裁切区域向右移动 16 像素，只修改 `X`：

```powershell
X = 251
```

不要改变 `Target`，否则模型仍会寻找旧文件名。

### 运行脚本

如果原始背景不在脚本默认目录，可以指定：

```powershell
powershell -ExecutionPolicy Bypass -File script/generate_nation_cg_wall_textures.ps1 `
  -SourceRoot "D:\Arknights\Backgrounds"
```

脚本会检查：

- 19 个条目是否齐全。
- 原图是否存在。
- 裁切区域是否超出原图。
- 输出是否为 128×128。
- 输出是否没有 Alpha 通道。

修改来源文件或坐标后，同步更新：

```text
docs/block/CG_MATERIAL_SOURCES.md
```

记录新的文件名、坐标、场景限制和权利风险。

## 5. 哪些国家贴图由哪个脚本负责

简化判断方法：

- 各国地貌方块通常由 `generate_nation_block_textures.ps1` 生成。
- 各国主要建筑外墙通常由 `generate_nation_cg_wall_textures.ps1` 裁切。
- `sami_tribal_timber` 只由 CG 裁切脚本生成。
- `sami_ritual_stone` 仍是原创脚本材质。
- 维多利亚城防炮的装甲、壳板、骨架、地板和面板属于原创脚本材质。

最可靠的方法仍然是在两个脚本中搜索完整 ID。

## 6. 新增一张国家材质

先按照[修改和新增方块](BLOCKS.md)注册方块，然后选择来源类型。

### 新增原创材质

在 `generate_nation_block_textures.ps1` 的 `$specs` 中加入：

```powershell
@{
  id='victoria_wet_cobblestone'
  kind='masonry'
  colors=@('#252B2B','#3D4643','#56615A','#707A70','#899087')
}
```

运行脚本后，应生成：

```text
src/main/resources/assets/zinecraft/textures/block/victoria_wet_cobblestone.png
```

### 新增 CG 裁片

只有在来源和再分发边界已经确认时，才向 `$Crops` 添加条目。必须同时更新 `CG_MATERIAL_SOURCES.md`，并保证裁片不包含人物、文字、徽标或
UI。

## 7. 游戏内检查方法

拿到方块后搭一面至少 8×8 的墙，再搭一块至少 8×8 的地面。分别在白天、夜晚和雨天观察：

1. 拼接边缘是否形成明显十字线。
2. 是否有一个像素过亮，平铺后变成规则斑点。
3. 墙面在远处是否闪烁或过于细碎。
4. 地貌方块是否过于规则，像人造瓷砖。
5. 建筑外墙是否缺少方向感或出现不自然重复。
6. 贴图颜色是否和邻近门窗、屋顶、道路协调。

运行中的客户端按：

```text
F3+T
```

即可重新加载 PNG。脚本、模型或 Java 改动仍建议重启客户端。

## 常见错误

| 现象          | 处理方法                                   |
|-------------|----------------------------------------|
| 紫黑缺失纹理      | 检查 PNG 路径、文件名大小写和模型引用                  |
| 改完又被覆盖      | 搜索 ID，确认应该修改哪个生成脚本                     |
| CG 脚本提示缺少原图 | 使用 `-SourceRoot` 指向包含背景 PNG 的目录        |
| CG 脚本提示越界   | 检查 `X + Width` 和 `Y + Height` 是否超过原图尺寸 |
| 透明区域显示异常    | 普通实心方块不应直接使用透明 PNG                     |
| 单张好看但墙面难看   | 用 8×8 平铺墙检查边缘和重复图案                     |
| 物品图标仍是旧贴图   | 按 `F3+T`，必要时重启客户端                      |

## 完成检查

- [ ] 已确认贴图是手写、原创脚本生成还是 CG 裁切。
- [ ] 没有手改生成模型 JSON。
- [ ] PNG 文件名与资源 ID 一致。
- [ ] CG 裁片同步更新了来源记录。
- [ ] 已检查 8×8 墙面和地面平铺效果。
- [ ] `build` 成功，发布 JAR 中包含目标 PNG。
