---
name: terra-nation-materials
description: Design and implement nation-specific terrain blocks, architectural wall materials, and original 16×16 textures for Zinecraft's nineteen Terra nations. Use when revising national blocks, biome surfaces, settlement or landmark palettes, FTB Quests nation icons, texture rules, or visual summaries.
---

# 泰拉国家材质设计

为十九国建立可辨识、可复现且不依赖原版视觉占位符的“地貌 + 建筑主体外墙”体系，并配合 `zinecraft-content` 技能接入
Java/NeoForge 项目。

## 上下文与设计

1. 阅读 `references/nations.md`、`docs/biome/TERRA_NATIONS.md`、`docs/block/NATION_MATERIALS.md` 与当前源码，保留已发布 ID。
2. 需要资料时联网核对官网和 PRTS，区分资料原文、玩法转译和美术推断。
3. 每国至少一种自然地貌与一种建筑外墙，使用稳定 `snake_case` ID，并同步注册、贴图、模型、结构、任务图标和文档。
4. 原版方块只作为门窗、灯具、屋顶、装饰或物理属性模板；不要用现实国家刻板印象替代泰拉资料。
5. 不复制官方关卡贴图、徽标、文字或角色素材；输出无文字、无徽标、可平铺的原创 16×16 PNG。

## 纹理规则

- 使用 3—5 个主色阶，通过裂纹、砌缝、压板、波纹或盐霜表达材质。
- 地貌降低规则性，建筑提高几何秩序；检查透明度、平铺边缘与国家间色相区分。
- 可以生成原创色板或样张，但最终资源由 `script/generate_nation_block_textures.ps1` 固化为确定性像素结果。

## 项目接入

1. 在 `src/main/java/com/cxxcxx/zinecraft/core/block/NationBlocks.java` 通过方块目录注册。
2. `ModSurfaceRule` 只在目标群系替换主表层；生态需要时混入少量合法表层。
3. 聚落脚本用国家地貌作 foundation、外墙作 wall；地标调色板前两槽使用对应国家材料。
4. FTB Quests 国家图标和双语说明同步改为专属方块。
5. 在 `docs/block/NATION_MATERIALS.md` 记录 ID、依据、来源和权利说明。

## 生成与验证

```powershell
powershell -ExecutionPolicy Bypass -File script/generate_nation_block_textures.ps1
python script/generate_nation_settlements.py
python script/generate_nation_landmarks.py
.\gradlew.bat runData
.\gradlew.bat build
```

修改任务时再运行仓库实际存在的 FTB Quests 校验脚本。确认每个方块都有 16×16 PNG、状态/模型/物品模型、双语名称和掉落，受影响
NBT 使用正确国家材料，发布资源不含第三方贴图或来源不明切片。
