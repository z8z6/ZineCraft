---
name: terra-nation-materials
description: Design and implement nation-specific terrain blocks, architectural wall materials, and original 16×16 textures for Zinecraft's nineteen Terra nations. Use when adding or revising national blocks, biome surfaces, settlement or landmark palettes, FTB Quests nation icons, texture-generation rules, or summaries of each nation's visual characteristics and reference elements.
---

# 泰拉国家材质设计

为十九国建立可辨识、可复现且不依赖原版视觉占位符的“地貌 + 建筑主体外墙”体系。配合项目的 `zinecraft-content`
技能使用；本技能负责国家视觉语义和材质一致性，后者负责注册、数据生成与构建约定。

## 建立设计上下文

1. 阅读 `references/nations.md` 中目标国家的地理、建筑、色彩和禁忌项。涉及多个国家或关系界面时阅读完整文件。
2. 核对 `docs/biome/TERRA_NATIONS.md`、`docs/block/NATION_MATERIALS.md` 与当前源码，保留已经发布的方块 ID。
3. 需要补充设定时联网核对《明日方舟》官网和对应 PRTS 页面。明确区分资料原文、项目玩法转译和美术推断。
4. 检查 `git status --short`，不要覆盖其他功能正在进行的修改。

## 设计国家材质

1. 每个国家至少定义两个独立方块：一种自然地貌和一种建筑主体外墙。
2. 使用稳定的 `snake_case` ID；保持注册、贴图、模型、结构模板、任务图标和文档完全同名。
3. 让国家的主地表、聚落地基与主体外墙使用自定义方块。原版方块只能作为门窗、灯具、屋顶、室内装饰或物理属性模板。
4. 从地理、气候、产业、城市形态和文化意象中各取一到两个要素，不要仅凭国名套用现实国家刻板印象。
5. 不复制官方关卡贴图、徽标、文字或角色素材。只借鉴战术地图的高对比分区、材料节奏和俯视可读性，输出原创纹理。

## 绘制贴图

1. 输出无文字、无徽标、可平铺的 16×16 PNG；常规立方体使用同一张 `cube_all` 纹理。
2. 每张纹理优先使用 3–5 个主色阶，并用裂纹、砌缝、压板边、波纹或盐霜等像素节奏表达材质。
3. 地貌纹理降低规则性，避免明显棋盘重复；建筑纹理提高几何秩序，使墙体在中距离可辨识。
4. 可用图像生成制作原创色板或材料样张，但最终资源应通过 `script/generate_nation_block_textures.ps1` 固化为可复现的像素结果。
5. 新增或修改纹理后，检查尺寸、透明度、平铺边缘和与相邻国家的色相区分。

## 接入项目

1. 在 `core/block/NationBlocks.kt` 通过 `Zinecraft.BLOCKS` 注册方块；`ofFullCopy` 只复用硬度和声音，不代表复用视觉。
2. 在 `ModSurfaceRule` 中使用目标群系条件替换主表层。只有树木、植被或生物生成确有需要时，才混入少量生态表层斑块。
3. 在 `generate_nation_settlements.py` 中将国家地貌放在 foundation 槽，将国家外墙放在 wall 槽。
4. 在 `generate_nation_landmarks.py` 中让地标调色板前两个槽分别使用该国地貌和外墙；其余槽可使用通用细节材料。
5. 国家节点需要视觉识别时，将 FTB Quests 图标和双语描述同步改为专属方块。
6. 向 `docs/block/NATION_MATERIALS.md` 记录 ID、设计依据、逐国来源和资源权利说明。

## 生成与验证

按顺序执行：

```powershell
powershell -ExecutionPolicy Bypass -File script/generate_nation_block_textures.ps1
python script/generate_nation_settlements.py
python script/generate_nation_landmarks.py
powershell -ExecutionPolicy Bypass -File .agents/skills/zinecraft-content/scripts/validate_ftbquests.ps1
.\gradlew.bat runDatagen
.\gradlew.bat build
```

如果未修改 FTB Quests，可以跳过任务校验。完成前确认：

- 每个新增方块都有 16×16 PNG、方块状态、方块模型、物品模型、双语名称和掉落表。
- 所有受影响的结构 NBT 都包含正确的 `zinecraft:` 国家材质。
- 群系主地表不再使用原版方块充当国家主题占位符。
- 发布资源没有第三方贴图、徽标或来源不明的美术切片。
