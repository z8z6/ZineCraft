# Zinecraft 项目文档

本文档依据当前仓库中的 Gradle 配置、源码和资源整理，描述的是项目的实际实现状态。长期设想请另见 [开发计划](Plan.md)。

## 开发指南目录

- [添加物品](item/README.md)
- [添加方块与方块实体](block/README.md)
- [添加实体](entity/README.md)
- [添加附魔](enchantment/README.md)
- [添加技能与 Ponder 演示](skill/README.md)
- [添加结构与三段式 Jigsaw 示例](structure/README.md)
- [添加群系](biome/README.md)
- [泰拉 19 国群系与资料依据](biome/TERRA_NATIONS.md)
- [FTB Quests 泰拉远征任务指引](quest/README.md)
- [泰拉国家关系系统](nation/README.md)
- [声明式 API 总览](API.md)

## 1. 项目概览

Zinecraft 是一个以《明日方舟》及《明日方舟：终末地》内容为主题的 Minecraft 模组原型。当前版本以 Fabric 为目标平台，主要使用
Kotlin 编写，并保留少量 Java Mixin 示例。

目前仓库更接近“功能验证模板”而非可直接发布的完整模组：物品与贴图已经有一定规模，方块实体、数据生成、群系、矿物、结构、技能演示链路和
FTB Quests
远征指引也有示例实现；剧情和自定义 UI 等内容仍处于规划阶段。项目现为单模块结构，注册与数据生成封装通过
`com.cxxcxx.zinecraft.api` 包组织，但不再产生独立 API 模组。

### 技术基线

| 项目                     | 当前配置                                       |
|------------------------|--------------------------------------------|
| Minecraft              | 1.21.1                                     |
| Java / JVM             | 21                                         |
| 模组加载器                  | Fabric Loader 0.19.2 或更高                   |
| Fabric API             | 0.116.12+1.21.1                            |
| Kotlin                 | 2.3.21                                     |
| Fabric Language Kotlin | 1.13.11+kotlin.2.3.21                      |
| 构建系统                   | Gradle Wrapper + Fabric Loom 1.16-SNAPSHOT |
| 项目版本                   | 1.0.0                                      |
| Maven Group            | `com.cxxcxx.zinecraft`                     |
| 许可证                    | CC0-1.0（第三方题材和音频、图像素材仍受其原权利方约束）            |

项目还声明了 TerraBlender、Trinkets、Ponder、Cloth Config 和 FTB Quests 等依赖，并配置了 Mod Menu、JEI、Jade、AppleSkin
等本地运行时辅助模组。其中 TerraBlender 负责国家群系地表规则，Ponder 负责技能可视化演示，FTB Quests
提供泰拉远征指引；其余多数是后续开发预留或开发环境辅助。

## 2. 仓库结构

```text
zinecraft/
├─ src/main/kotlin/             注册封装、具体内容、数据生成与世界生成
├─ src/main/java/               服务端 Mixin 示例
├─ src/client/                  客户端入口与客户端 Mixin 示例
├─ src/main/resources/          模组元数据、贴图、声音、NBT 结构
├─ script/                      独立的 Pillow 图片处理脚本
├─ docs/                        项目文档、路线图和示意图
├─ build.gradle                 根项目插件与仓库配置
├─ gradle.properties            共用版本号与依赖版本
└─ settings.gradle              声明单一 Gradle 根项目
```

`docs/Plan.md` 中原先设想的 `api`、`core`、`render` 和 `main` 子模块划分已不再适用于当前仓库；现阶段统一采用单模块实现。

## 3. 构建与运行

### 环境准备

1. 安装 JDK 21，并确保 `java -version` 指向该版本。
2. 使用 IntelliJ IDEA 导入仓库根目录的 Gradle 项目。
3. 首次同步需要访问 Fabric、Maven Central、Modrinth、CurseMaven 等依赖仓库。

仓库自带 Gradle Wrapper，无需单独安装 Gradle。在 Windows PowerShell 中可使用：

```powershell
.\gradlew.bat build
```

Linux 或 macOS 使用：

```bash
./gradlew build
```

构建产物位于 `build/libs/`。Loom 会生成开发用 JAR、重映射后的发布 JAR以及源码 JAR；实际分发时应选择重映射后的模组
JAR。

### 常用任务

```powershell
# 启动开发客户端
.\gradlew.bat runClient

# 启动开发服务端
.\gradlew.bat runServer

# 生成语言、模型、配方、战利品表和世界生成数据
.\gradlew.bat runDatagen

# 查看所有可用任务
.\gradlew.bat tasks
```

数据生成由 `ZinecraftDataGenerator` 统一注册。生成内容通常写入 Loom 配置的 generated resources
目录；执行后应检查实际输出，并将需要随模组发布的数据纳入资源集或版本控制。

### 模组入口

| 模组 ID       | 环境           | 入口类                      | 状态                            |
|-------------|--------------|--------------------------|-------------------------------|
| `zinecraft` | 通用           | `Zinecraft`              | 初始化声音、物品、燃料、堆肥、方块、方块实体和世界生成注册 |
| `zinecraft` | 客户端          | `ZinecraftCoreClient`    | 空入口                           |
| `zinecraft` | 数据生成         | `ZinecraftDataGenerator` | 注册全部数据生成器和动态注册表 bootstrap     |
| `zinecraft` | TerraBlender | `ModTerraBlender`        | 注册受国家群系条件限制的地表规则              |

项目使用 Loom 的 split environment source sets，将客户端代码与通用代码分开。通用源码不应引用仅客户端可用的 Minecraft 类。

## 4. 初始化与注册架构

`Zinecraft.onInitialize()` 是核心初始化入口。多数注册器采用 Kotlin `object` 的初始化副作用：入口通过访问对象来触发其
`init` 块或属性初始化。

```text
Fabric Loader
└─ Zinecraft.onInitialize()
   ├─ ModSound                  注册 SoundEvent
   ├─ ModItem                   注册物品和创造模式标签页
   ├─ ITEMS / BLOCKS           分别注册物品与方块，并记录数据生成元数据
   ├─ ENTITIES / ENCHANTMENTS  注册实体、Mob 与动态附魔
   ├─ ModBlock                 注册示例方块及其 BlockItem
   ├─ ModBlockEntity           注册示例方块实体类型
   ├─ ModSkills               声明八职业代表技能物品与 Ponder 演示数据
   ├─ NationBiomes            声明 19 个泰拉国家动态群系键与特色生物
   ├─ ModDimensions           注册泰拉维度及其多噪声群系源
   ├─ NationLandmarks         声明 38 个绑定群系的世界唯一地标
   ├─ NationSettlements       声明 19 套可重复生成的大型 Jigsaw 聚落
   ├─ ModBuildings             声明并自动生成简单 Jigsaw 建筑数据
   └─ initBiome()              向所有主世界群系加入示例矿脉
```

`Zinecraft.REGISTRAR` 使用同一模组内的 API 封装，并集中提供以下能力：

- `id(name)`：创建 `zinecraft:<name>` 资源位置。
- `key(registry, name)`：为指定动态注册表创建资源键。
- `register` / `dynamic`：注册静态内容或向 `BootstrapContext` 注册动态内容。
- `item`、`block`、`blockEntity`、`entity`、`sound` 等：常见内容类型的快捷注册方法。

静态注册用于物品、方块、声音事件、方块实体类型和自定义结构类型；群系、配置地物、放置地物、模板池、结构、结构集及唱片歌曲等动态注册表内容，则由数据生成阶段
bootstrap 并导出为 JSON。

## 5. 模块与功能

### 5.1 API 封装包

`com.cxxcxx.zinecraft.api` 包承载公共注册封装。`ModRegistrar`
将模组命名空间与注册操作绑定，提供资源位置、动态注册表资源键、普通物品、方块及其物品、方块实体、普通实体、创造模式标签页、声音事件、结构类型、结构片段和结构池元素的统一注册方法；动态注册表内容还可通过
`dynamic` 方法写入 `BootstrapContext`。

`Zinecraft` 创建 `ModRegistrar("zinecraft")` 并通过 `REGISTRAR` 使用这些能力，不再直接调用底层
`Registry.register`。封装与内容被编译进同一个 Zinecraft JAR，部署时无需额外安装 API 模组。

### 5.2 物品与创造模式标签页

`ModItem` 使用 `ItemWrap` 封装物品 ID、中文名、英文名、物品实例和模型模板。构造包装对象时会立即注册物品，并加入共享列表；语言和模型数据生成器随后遍历该列表。

当前源码包含约 74 个 `ItemWrap` 声明，主要分为：

- 源岩、源石、研磨石、锰矿、RMA、晶体、聚酸酯、糖、切削液、装置、异铁、酮、凝胶等材料链。
- 八种职业的芯片与芯片组。
- 技巧概要、寻访凭证、龙门币等养成资源。
- `magic_dust` 示例功能物品。
- 三张音乐唱片。

所有包装物品及示例方块物品都会加入 `zinecraft:item` 创造模式标签页，标签页图标为 D32 钢。

`magic_dust` 具有以下示例行为：

- 食用恢复 6 点饥饿值，饱和度修正为 0.8，可随时快速食用。
- 食用后必定获得 30 秒的跳跃提升 III。
- 可作为 600 tick（30 秒）的燃料。
- 放入堆肥桶有 30% 概率提升堆肥层级。

三张音乐唱片分别关联 `Pictures of the Past`、`Random Gods (Theme III)` 和 `Stranger Think` 的 OGG 资源，均为稀有、不可堆叠物品，红石信号强度为
15。

### 5.3 方块与方块实体

当前仅注册 `example_entity_block`。这是一个带方块实体的草地音效方块：

- `ExampleEntityBlock` 负责交互和创建方块实体。
- `ExampleBlockEntity` 保存整数 `clicks`，每次空手交互递增并调用 `setChanged()`。
- 点击次数通过 NBT 字段 `clicks` 存盘和恢复。
- 玩家交互时会在动作栏显示累计点击次数。

该方块有自动生成的方块状态、方块模型、物品模型和“掉落自身”的战利品表。

### 5.4 配方数据生成

`ModRecipeProvider` 当前包含演示性质的配方，其中一些会修改原版物品的获得方式：

- 砂土无序合成泥土。
- 四个原木标签物品合成四个工作台。
- 羊毛与原木合成四个织布机。
- 橡木按钮通过门配方辅助器合成橡木门。
- 面包、曲奇或干草块熔炼为小麦。
- 两个魔法粉尘无序合成四个魔法粉尘。
- 魔法粉尘熔炼为自身。

后两项会无成本放大或复制物品，显然属于数据生成示例；制作正式玩法前应删除或重新平衡。

### 5.5 世界生成

世界生成由数据生成器导出动态注册表数据；Fabric 负责地物注入，TerraBlender 保留国家群系表层规则扩展。

#### 示例矿脉

`example_block_vein` 使用矿石特征，在普通石头和深板岩可替换方块中生成 `example_entity_block`：

- 矿脉大小：30。
- 每区块尝试次数：6。
- 高度：世界底部至 Y=0，使用偏向底部的高度分布。
- 范围：Fabric `BiomeModifications` 选中的所有主世界群系。

由于生成的是带方块实体的交互示例方块，这套配置主要用于验证世界生成链路，不适合作为最终矿物设定。

#### 泰拉国家群系

`NationBiomes` 为 PRTS 当前列出的 19 个现存国家各声明一个群系。`NationBiomePresets` 复用原版植被、刷怪与地下生成组合，
`NationBiomePlacements` 单独维护原版多噪声气候点，`ModDimensions.TERRA` 负责批量加入 `zinecraft:terra`。阿戈尔映射到深海，
杜林映射到地下，其余国家按地貌和气候映射到泰拉地表；主世界不再包含国家群系。

`ModSurfaceRule` 只匹配项目自己的国家群系，为每国应用一种不重复的主表层，并按需要混入草方块或雪块生态斑块，既保留视觉特色，
也满足树木与特色生物的生成条件，不会用通用兜底规则改写原版群系。每个群系还单独配置一种有辨识度的原版生物。
各国家、代表城市、设计依据与资料链接见[泰拉国家群系设计](biome/TERRA_NATIONS.md)。

#### 结构系统

项目使用统一的 Jigsaw 建筑封装：

1. `jigsaw_example` 是 `start → middle → end` 三段式 Jigsaw 示例，模板和动态注册表数据由声明与数据生成流程衔接。
2. `portal_ruins_common` 是单模板简易建筑。`ModBuildings` 只声明 NBT 模板、间距、盐值和藤蔓处理概率，API
   自动生成处理器、模板池、Jigsaw 结构及结构集。模板引用 `structure/portal_ruins/common.nbt`，处理器以 60% 概率将藤蔓替换为空气；结构间距为
   36 区块，最小间隔为 30。
3. `NationLandmarks` 为 19 个国家群系各声明两座代表建筑，共 38 座。每座建筑拥有独立的同心环结构集，`count = 1`，
   并同时在结构与放置器层绑定目标群系，因此每个世界最多自然生成一次；杜林地标使用固定地下高度，阿戈尔地标投影至海床。
4. `NationSettlements` 为每个国家注册一套可重复生成的城市、村落或营地。每套聚落由中心、四类道路和四类当地功能建筑组成，
   默认展开七层；道路使用地形贴合投影，聚落结构和随机散布结构集都精确绑定所属国家群系。

前两种通用示例结构限制在带 `minecraft:is_overworld` 标签的群系中；国家地标只在各自的国家群系中生成。

### 5.6 数据生成

`ZinecraftDataGenerator` 注册以下 provider：

| Provider                     | 输出内容                          |
|------------------------------|-------------------------------|
| `CatalogLanguageProvider`    | 从各领域目录生成中英文物品、方块、唱片描述和创造标签页翻译 |
| `CatalogModelProvider`       | 从物品、方块目录生成物品模型、简单方块模型和方块状态    |
| `CatalogLootTableProvider`   | 从方块目录生成默认方块掉落                 |
| `ModRecipeProvider`          | 示例合成与熔炼配方                     |
| `ModDynamicRegistryProvider` | 全部自定义动态注册表数据                  |

动态注册表 bootstrap 覆盖配置地物、放置地物、群系、结构处理器、模板池、结构、结构集和唱片歌曲。

### 5.7 Mixin 与客户端代码

`ExampleMixin` 注入 `MinecraftServer.loadLevel()` 开头，`ExampleClientMixin` 注入 `Minecraft.run()`
开头；两个注入方法都没有实际逻辑。通用端和客户端初始化入口也保留了 Fabric 模板注释。

这些类证明 Mixin 配置和环境拆分已经接通，但在添加真实行为前可以视作占位代码。

### 5.8 美术资源脚本

`script/` 下包含独立 Python/Pillow 脚本：

- `main.py`：缩放至 32×32、透明度二值化、颜色量化并统一边缘颜色。
- `clean.py`：缩放至 16×16 并清理半透明边缘。
- `quad.py`：缩放至 16×16、清理透明边缘并量化为 16 色。
- `cube.py`：将图片透明填充为正方形。

脚本中的输入输出目录是相对于当前工作目录的硬编码路径，运行前需检查并修改。需要先自行安装 Pillow：

```powershell
python -m pip install Pillow
```

## 6. 添加内容的推荐流程

### 添加普通物品

1. 在 `ModItem` 中调用内容目录的 `item` 方法，ID 使用小写蛇形命名。
2. 提供中文名；英文名默认从 ID 生成，也可显式传入准确翻译。
3. 将贴图放到 `src/main/resources/assets/zinecraft/textures/item/<id>.png`。
4. 执行 `runDatagen` 生成模型与语言文件。
5. 启动客户端，确认创造标签页、名称和模型。

### 添加方块及方块实体

1. 在 `ModBlock` 注册方块，并决定是否同时注册 `BlockItem`。
2. 若有状态，新增方块实体类并在 `ModBlockEntity` 注册其类型。
3. 实现 NBT 存取时始终调用父类方法，并在状态变化时调用 `setChanged()`。
4. 普通立方体方块会自动生成模型、战利品表和语言；特殊模型或掉落通过声明开关关闭默认值后自行补充。
5. 为客户端渲染需求在 `src/client` 注册 renderer，避免通用端加载客户端类。

### 添加世界生成内容

1. 在对应 `Mod*` 对象中定义稳定的 `ResourceKey`。
2. 将 bootstrap 方法加入 `ZinecraftDataGenerator.buildRegistry()`。
3. 确保 `ModDynamicRegistryProvider.configure()` 导出对应动态注册表。
4. 使用 Fabric biome modification、TerraBlender region 或结构集接入世界。
5. 运行数据生成并新建世界验证；已有区块不会重新生成内容。

### 添加声音或唱片

1. 将 `.ogg` 放入 `assets/zinecraft/sounds/`。
2. 在 `sounds.json` 声明声音事件。
3. 若作为唱片，在 `ModSound` 创建 `Song`，再在 `ModItem` 创建关联物品。
4. 通过数据生成导出 jukebox song 与翻译。

## 7. 代码与资源约定

- 主语言为 Kotlin；只在 Mixin 或互操作确有需要时使用 Java。
- 现有核心代码普遍使用 2 空格缩进，API 模板代码仍有 4 空格，应在后续统一。
- 资源 ID 使用 `zinecraft` 命名空间和小写蛇形名称。
- 客户端专用代码放在 `src/client`，通用逻辑放在 `src/main`。
- 注册名、贴图名、声音名、NBT 模板路径和数据生成键需要保持一致。
- 计划要求游戏贴图尽量保持 Minecraft 原版风格，目标尺寸为 16×16；仓库脚本也围绕像素化、透明边缘清理和色彩量化设计。
- 新内容优先通过数据生成器维护，减少手写 JSON 的重复和拼写错误。

项目已应用 Spotless 插件版本，但当前构建脚本没有配置格式规则或把它接入检查流程；不能假设 `spotlessApply`
已能统一格式。仓库目前也没有自动化测试源码或 CI 配置，验证主要依赖 Gradle 构建、数据生成和游戏内测试。

## 8. 已知限制与注意事项

- 项目仍含大量 `example_*` 示例命名、空入口和空 Mixin，发布前需要替换或删除。
- `com.cxxcxx.zinecraft.api` 目前是同一模组中的公共封装包，尚未定义稳定性或兼容性承诺。
- TerraBlender 当前只负责国家群系条件表层规则；国家群系分布由泰拉维度自身的多噪声群系源负责。
- Trinkets 和 Cloth Config 已声明为依赖但当前源码未使用；过早保留依赖会增加开发启动与分发维护成本。
- `sounds.json` 声明了 `engine`，但资源目录中没有对应的 `engine.ogg`。
- 中文语言 provider 没有生成创造模式标签页和唱片描述翻译；唱片组件当前使用的翻译键也应与 `sounds.json` 的 subtitle 键统一检查。
- 多个普通物品的英文名称由中文名自动推导，因此英文语言文件会得到中文文本，而不是可靠英文翻译。
- 示例配方会改写原版玩法并复制物品；示例矿脉会大量生成可交互方块实体，都不宜直接用于正式平衡。
- `MaterialRegistry` 是可变的 `Tier` 草稿，默认值极低，`init()` 中的 `GUIDITE` 也没有形成有效注册或常量。
- `docs/Plan.md` 是愿景文档，其中 Forge/NeoForge、`render`、`main`、生物、饰品、技能和 UI 等内容尚未在当前仓库实现。

## 9. 发布前检查清单

- 运行 `build` 与 `runDatagen`，确认没有编译错误和遗漏数据。
- 检查 `fabric.mod.json` 的作者、主页、源码地址、依赖与版本约束。
- 明确公共 API 包、TerraBlender 以及其他外部模组的稳定性和必需/可选关系。
- 删除示例配方、Mixin、日志和未使用依赖，或替换为正式实现。
- 在全新世界验证矿脉、群系、Jigsaw 结构及数据包加载。
- 在客户端验证全部物品模型、语言、唱片声音和创造标签页。
- 在专用服务端验证通用代码未引用客户端类。
- 核查所有非原创图像、音频和题材素材的授权及分发条件。

## 10. 相关资料

- [项目开发计划](Plan.md)
- [根目录说明](../README.md)
- [Fabric 官方开发文档](https://docs.fabricmc.net/develop/)
- [TerraBlender 项目](https://github.com/Glitchfiend/TerraBlender)
