# Zinecraft

[中文](#中文) · [English](#english)

## 中文

Zinecraft 是一个面向 Minecraft 1.21.1 / NeoForge 的《明日方舟》主题内容模组。项目使用 Java
21，围绕泰拉维度、十九国群系与建筑、藏品、技能、武器运行时、TaCZ 枪包格式适配和 FTB Quests 指引构建。

> 本项目是非官方同人项目，与鹰角网络、Hypergryph 或 Minecraft/Mojang Studios 无隶属关系。

### 当前内容

- 泰拉独立维度、十九国群系、国家地表材料、聚落与唯一地标。
- 首次出生于泰拉、萨米唯一星门单向前往主世界、拉特兰中心区域与固定坐标建筑。
- Curios 藏品栏、PRTS 集成战略藏品资料及服务端权威效果。
- 技能物品、Ponder 演示和服务端 Weapon Runtime。
- TaCZ 1.1.x 外置枪包读取、枪械/弹药状态、资源桥接与服务端命中结算。
- FTB Quests 泰拉远征、国家档案、关系网和开发模组说明。
- JourneyMap 十九国边界与名称叠加，以及 JEI、JER、Jade、AppleSkin、拼音搜索、自然指南针和探险家罗盘开发环境适配。

### 技术基线

| 项目        | 版本或说明                                              |
|-----------|----------------------------------------------------|
| Minecraft | 1.21.1                                             |
| NeoForge  | 21.1.244                                           |
| Java      | 21                                                 |
| 构建        | Gradle Wrapper + ModDevGradle                      |
| 源码        | Java；通用端位于 `src/main/java`，客户端位于 `src/client/java` |
| 模组 ID     | `zinecraft`                                        |

### 开发

安装 JDK 21 后，在仓库根目录运行：

```powershell
# 生成语言、模型、配方、战利品表和动态注册表数据
.\gradlew.bat runData

# 启动开发客户端
.\gradlew.bat runClient

# 完整发布构建
.\gradlew.bat build
```

Linux/macOS 将 `.\gradlew.bat` 替换为 `./gradlew`。构建产物位于 `build/libs/`。数据生成输出位于 `src/generated/resources/`
，该目录是可重建产物，不应作为手写资源维护。

### 目录

```text
src/main/java/       通用注册、玩法、世界生成和兼容层
src/client/java/     渲染、输入、Ponder 与客户端资源桥接
src/main/resources/  发布资源、结构 NBT、任务模板和手写数据
docs/                开发和内容文档
.agents/skills/      面向仓库维护代理的领域工作流
```

详细开发入口见 [项目文档](docs/README.md) 与 [声明式 API](docs/API.md)
。迁移背景见 [NeoForge 迁移说明](MIGRATION_NOTES.md)。

### 资料与授权

项目中的《明日方舟》名称、说明和图像资料按仓库约定取自[明日方舟官网](https://ak.hypergryph.com/)
与 [PRTS Wiki](https://prts.wiki/)，相关内容及素材权利属于各自权利方。仓库代码许可证见 [LICENSE](LICENSE)
；项目许可证不会覆盖第三方题材、图像、音频、枪包或其他外部资产。

## English

Zinecraft is an Arknights-themed content mod for Minecraft 1.21.1 on NeoForge. It is written in Java 21 and centers on
the Terra dimension, nineteen nation biomes and settlements, collectibles, operator skills, a server-authoritative
weapon runtime, TaCZ gun-pack format compatibility, and an FTB Quests guide.

> This is an unofficial fan project and is not affiliated with Hypergryph, Minecraft, or Mojang Studios.

### Current features

- A dedicated Terra dimension with nineteen nation biomes, national terrain materials, settlements, and unique
  landmarks.
- First spawn in Terra, one-way travel through the sole Sami stargate, a fixed Laterano center, and fixed-coordinate
  structures.
- Curios collectible slots, Integrated Strategies collectible records sourced from PRTS, and server-authoritative
  effects.
- Skill items, Ponder demonstrations, and a server-authoritative Weapon Runtime.
- TaCZ 1.1.x external gun-pack loading, firearm/ammunition state, resource bridging, and server-side hit resolution.
- FTB Quests expedition, nation archive, relationship network, and development-mod chapters.
- JourneyMap overlays for all nineteen national borders and names, plus development integrations for JEI, JER, Jade,
  AppleSkin, search helpers, Nature's Compass, and Explorer's Compass.

### Technical baseline

| Component | Version or role                                                        |
|-----------|------------------------------------------------------------------------|
| Minecraft | 1.21.1                                                                 |
| NeoForge  | 21.1.244                                                               |
| Java      | 21                                                                     |
| Build     | Gradle Wrapper + ModDevGradle                                          |
| Sources   | Java; common code in `src/main/java`, client code in `src/client/java` |
| Mod ID    | `zinecraft`                                                            |

### Development

Install JDK 21, then run from the repository root:

```powershell
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat runClient
.\gradlew.bat build
```

On Linux or macOS, replace `.\gradlew.bat` with `./gradlew`. Artifacts are written to `build/libs/`. Generated data is
written to `src/generated/resources/` and should remain reproducible rather than being maintained as handwritten
content.

See the [documentation index](docs/README.md) and [declarative API guide](docs/API.md) for development details.
Migration context is recorded in [NeoForge migration notes](MIGRATION_NOTES.md).

### Sources and licensing

Arknights names, descriptions, and referenced images follow the repository policy of using
the [official Arknights website](https://ak.hypergryph.com/) and [PRTS Wiki](https://prts.wiki/). Their rights remain
with their respective owners. The repository code license is provided in [LICENSE](LICENSE); it does not relicense
third-party settings, images, audio, gun packs, or other external assets.
