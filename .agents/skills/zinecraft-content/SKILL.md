---
name: zinecraft-content
description: Implement and extend Zinecraft through its declarative Java catalogs and server-authoritative Weapon Runtime. Use when adding or modifying items, blocks, block entities, mobs, national residents, nation relationships, entities, weapons, sounds, enchantments, skills, Ponder scenes, biomes, dimensions, FTB Quests guides, Jigsaw buildings, settlements, landmarks, translations, models, loot tables, or generated data in this Minecraft 1.21.1 NeoForge project.
---

# Zinecraft 内容开发

使用项目已有的 Java 目录完成内容注册、资源接入、数据生成和验证。除非现有目录确实无法表达需求，不要绕过目录直接调用底层注册表。

## 建立上下文

1. 阅读仓库根目录 `AGENTS.md`，执行 `git status --short`，保留用户已有修改。
2. 确认基线：模组 ID `zinecraft`、Minecraft 1.21.1、NeoForge 21.1.244、Java 21、单模块；通用端位于 `src/main/java`，客户端位于
   `src/client/java`。
3. 搜索同类内容及初始化入口。内容声明优先位于 `core/<domain>`，目录能力位于 `api/<domain>`，可选模组适配位于
   `compat/<modid>`。
4. 按任务读取必要参考，不一次加载全部：
    - 物品、方块、方块实体：`references/items-blocks.md`
    - 实体与 Mob：`references/entities.md`
    - 国家状态和关系：`references/nations.md`
    - 群系、地表、Jigsaw、聚落和地标：`references/worldgen.md`
    - 技能与 Ponder：`references/skills.md`
    - Weapon Runtime：`references/weapons.md`
    - TaCZ 外置枪包：`references/tacz.md`
    - FTB Quests：`references/ftbquests.md`
    - 声音、唱片、附魔、配方与矿物：`references/sound-enchantment.md`

## 实施流程

1. 使用稳定的 `snake_case` ID；注册名、翻译、纹理、模型、声音、NBT 和数据键保持一致。
2. 通过 `Zinecraft.INSTANCE` 的领域目录声明内容，包括物品、方块、实体、声音、附魔、技能、武器、群系、地物、结构、维度与配方。
3. 静态内容接入 NeoForge 延迟注册；动态注册表接入对应 bootstrap、`RegistrySetBuilder` 与 provider。不要恢复旧 Loader
   生命周期或兼容抽象。
4. 新增顶层内容类时，在 `Zinecraft` 运行时初始化和数据生成入口显式触发；不要依赖类加载顺序或语言级对象初始化副作用。
5. 客户端 renderer、按键、Ponder、粒子、声音后端和资源桥接只放在 `src/client/java`。服务端玩法不得 import
   `net.minecraft.client`。
6. 补齐无法自动生成的 PNG、OGG、结构/Ponder NBT、`sounds.json` 和特殊模型。不要手写目录已经可靠生成的重复 JSON。
7. 引用明日方舟官网或 PRTS 资料前联网核对；保留原文、链接和逐文件权利记录，不自行推断缺失设定。
8. 依次运行 `./gradlew.bat test`、`./gradlew.bat runData`、`./gradlew.bat build`。运行时改动再验证 `runClient` 或
   `runServer`；不要把 `runData` 与 `build` 合并为一次调用。

## Java API 约定

- 使用 `Supplier`、`Consumer`、`Function`、record 或明确的领域类表达 Java 语义。
- 不新增默认参数位掩码、marker 参数、`componentN`、`copy$default`、`Companion`、`*Kt` 或带 `$lambda` 的迁移命名。
- 公开封装校验重复 ID、非法数量、概率和区间，并返回可继续组合的 entry/resource key。
- 目录构造函数注入真实依赖；避免为了方便重新制造全局状态或 Loader 无关抽象。

## 数据与版本控制

- `src/generated/resources/` 是 `runData` 的可重建输出，不作为手写资源维护。
- 发布所需 PNG、OGG、NBT、任务模板和稳定手写 JSON 位于 `src/main/resources/`。
- `run/`、外置 TaCZ 枪包和客户端桥接包保持忽略。
- 不覆盖用户已有修改，不顺手重构任务范围外文件。

## 质量门槛

- 群系同时考虑气候点、表层、特色地物/生物与结构；泰拉群系源不得混入原版群系。
- 普通城市/村落/营地使用 `settlement`，每世界一次建筑使用 `uniqueLandmark`，固定中心设施使用 fixed-origin 变体，小型单模板建筑使用
  `simpleBuilding`。
- Mob 属性/生成/AI 与客户端 renderer 分开；技能数据与 Ponder 场景分开；声音注册与声音资源声明分开。
- 武器命中、伤害、弹药和技能效果由服务端 Action Runtime 决定；客户端输入和动画关键帧只表达意图与表现。
- FTB Quests 修改后运行 `scripts/validate_ftbquests.ps1`（若仓库提供），再执行常规验证。
- 完成前报告实际通过的测试、数据生成、构建和运行验证，以及未验证风险。

## 完成报告

说明新增/修改的声明与资源、目录自动生成的数据、执行的验证，以及仍需提供的原创美术或外部授权。引用关键文件绝对路径和行号。
