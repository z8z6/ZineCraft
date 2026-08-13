---
name: zinecraft-content
description: Implement and extend Zinecraft through its declarative Kotlin catalogs. Use when adding or modifying items, blocks, block entities, mobs, entities, sounds, music discs, enchantments, character skill items with Ponder scenes, biomes, Jigsaw buildings, settlements, unique landmarks, translations, models, loot tables, or generated data in this Minecraft 1.21.1 Fabric project.
---

# Zinecraft 内容开发

使用项目已有的目录封装完成内容注册、资源接入、数据生成和验证。除非现有封装确实无法表达需求，不要绕过目录直接调用原版
`Registry`。

## 建立上下文

1. 阅读仓库根目录的 `AGENTS.md`，检查 `git status --short`，保留用户已有修改。
2. 确认当前项目约定：模组 ID 是 `zinecraft`，Minecraft 版本是 1.21.1，源码使用 Kotlin，通用端与客户端源码分离。
3. 搜索同类内容及其初始化入口，再修改最小必要文件。优先查看 `core` 中的真实示例，目录实现位于 `api` 包，但二者属于同一个模块。
4. 按任务读取对应参考文件，不要一次加载全部：
    - 物品、方块、方块实体：`references/items-blocks.md`
    - 实体与 Mob：`references/entities.md`
    - 群系、地表、Jigsaw 建筑、聚落、唯一地标：`references/worldgen.md`
    - 角色技能物品与 Ponder：`references/skills.md`
    - 音效、唱片、附魔、配方、创造模式页、矿物特征：`references/sound-enchantment.md`

## 实施流程

1. 为内容选择稳定的 `snake_case` ID；确认翻译、纹理、模板和声音资源采用同一个 ID。
2. 通过 `Zinecraft.ITEMS`、`BLOCKS`、`BLOCK_ENTITIES`、`ENTITIES`、`SOUNDS`、`SONGS`、`ENCHANTMENTS`、`SKILLS`、`BIOMES`、
   `FEATURES`、`STRUCTURES` 或 `RECIPES` 声明内容。
3. 将面向游戏内容的声明放入对应 `core/<domain>` 对象。只有新增目录能力时才修改 `api/<domain>`；通用端不得引用渲染器、Ponder
   场景等客户端类。
4. 新增顶层内容对象时，在 `Zinecraft.onInitialize()` 和 `ZinecraftDataGenerator.onInitializeDataGenerator()`
   中显式访问，保证正常启动与数据生成都会触发 Kotlin 对象初始化。动态注册表还要接入 `buildRegistry()` 或
   `WorldgenManager`。
5. 补齐不能自动生成的资源：PNG、OGG、结构 NBT、Ponder NBT、`sounds.json` 以及自定义模型。目录已自动收集翻译、常规模型和方块掉落时，不要手写重复
   JSON。
6. 如资料或美术来自明日方舟官网、PRTS 等外部来源，先联网核对当前页面；记录逐文件来源和权利说明。不要把第三方素材误写成项目许可证覆盖的原创资产。
7. 先运行 `./gradlew.bat runDatagen`，再单独运行 `./gradlew.bat build`。不要把两项合并为一次 Gradle 调用；当前任务图会触发隐式依赖校验。
8. 检查生成 JSON、资源路径、客户端注册和构建产物。必要时在开发世界用 `/place structure`、`/locate structure` 或实体生成蛋进行运行时验证。

## 数据与版本控制约定

- `src/main/generated/` 是可重建输出，已被 Git 忽略，不得重新加入提交。
- 发布所需且不能在运行时重建的 PNG、OGG、NBT 和手写 JSON 必须放入 `src/main/resources/`。
- 数据生成结果只用于校验和复制稳定数据；提交前用 `git status --short` 确认没有意外产物。
- 不修改无关文件，不覆盖用户正在进行的改动，不顺手重构任务范围外的旧代码。

## 质量门槛

- 新增公开封装要校验非法参数，并返回后续可组合使用的 entry 或资源键。
- 与玩法、世界观和特殊生成规则有关的代码使用中文 KDoc 或中文注释说明“为什么”。
- 群系必须同时考虑生成参数、主世界气候映射、独特地表、特色生物和建筑；地表规则必须用目标群系条件限定。
- 普通城市、村落和营地使用可重复生成的 `settlement`；每世界一次的特殊建筑使用 `uniqueLandmark`；小型单模板建筑使用
  `simpleBuilding`。
- Mob 的服务端属性/生成规则与客户端渲染分开；技能数据与 Ponder 场景分开；声音事件注册与声音文件声明分开。
- 完成前至少通过数据生成和完整构建。若失败，区分代码错误、资源错误和环境/依赖下载错误，并报告实际验证范围。

## 完成报告

说明新增了哪些声明与资源、哪些数据由目录自动生成、执行了哪些验证，以及仍需用户提供的美术或外部授权事项。引用关键文件的绝对路径和行号。

