---
name: terra-nation-materials
description: Design and implement nation-specific terrain blocks, architectural wall materials, original textures, or explicitly requested traceable CG-derived surface crops for Zinecraft's nineteen Terra nations. Use when revising national blocks, biome surfaces, settlement or landmark palettes, FTB Quests nation icons, texture rules, source/rights records, or visual summaries.
---

# 泰拉国家材质设计

为十九国建立可辨识、可复现且不依赖原版视觉占位符的“地貌 + 建筑主体外墙”体系，并配合 `zinecraft-content` 技能接入
Java/NeoForge 项目。

## 上下文与设计

1. 阅读 `references/nations.md`、`docs/biome/TERRA_NATIONS.md`、`docs/block/NATION_MATERIALS.md` 与当前源码，保留已发布 ID。
2. 需要资料时联网核对官网和 PRTS，区分资料原文、玩法转译和美术推断。
3. 每国至少一种自然地貌与一种建筑外墙，使用稳定 `snake_case` ID，并同步注册、贴图、模型、结构、任务图标和文档。
4. 原版方块只作为门窗、灯具、屋顶、装饰或物理属性模板；不要用现实国家刻板印象替代泰拉资料。
5. 默认输出无文字、无徽标、可平铺的原创 16×16 PNG。若仓库 `AGENTS.md`
   或用户明确要求直接使用官方/PRTS/游戏素材，则切换到“可追溯直裁”模式：保留源图、固定坐标、场景限制和权利边界，不得混入人物、文字、UI
   或徽标。

## 纹理规则

- 原创模式：使用 3—5 个主色阶，通过裂纹、砌缝、压板、波纹或盐霜表达材质；由确定性脚本固化结果。
- 可追溯直裁模式：优先从同国家、同城市的高可信背景中选择近正视、材质单一的墙面/板材/石材区域，1:1 裁切，不缩放、不减色、不 AI
  重绘、不调色。
- 直裁不能包含整栋建筑、天空、完整门窗、栏杆、植被或强透视边缘后再以 `cube_all` 每方块重复。若当前源图没有合适表面，换同国高可信画面；仍找不到则标记
  UNKNOWN，不用场景截图冒充材质。
- 两种模式都检查透明度、边缘差异、重复预览和国家间区分。直裁脚本必须验证输出像素与源矩形逐像素相同，并记录边缘误差；“来源真实”不等于“适合平铺”。

## 项目接入

1. 在 `src/main/java/com/cxxcxx/zinecraft/core/block/NationBlocks.java` 通过方块目录注册。
2. `ModSurfaceRule` 只在目标群系替换主表层；生态需要时混入少量合法表层。
3. 聚落脚本用国家地貌作 foundation、外墙作 wall；地标调色板前两槽使用对应国家材料。
4. FTB Quests 国家图标和双语说明同步改为专属方块。
5. 在 `docs/block/NATION_MATERIALS.md` 记录 ID、依据、来源和权利说明。官方衍生裁片可用于用户要求的本地预览，但公开发布前必须确认权利方规则/许可，不得表述为项目原创。

## 生成与验证

```powershell
powershell -ExecutionPolicy Bypass -File script/generate_nation_block_textures.ps1
python script/generate_nation_settlements.py
python script/generate_nation_landmarks.py
.\gradlew.bat runData
.\gradlew.bat build
```

修改任务时再运行仓库实际存在的 FTB Quests 校验脚本。确认每个方块都有符合所选模式尺寸的 PNG、状态/模型/物品模型、双语名称、挖掘标签和掉落，受影响
NBT 使用正确国家材料。原创资源不得混入第三方贴图；直裁资源必须有逐项来源与发布权利警告。
