# Zinecraft 项目文档

本文档描述当前仓库的实际实现。项目已完成从 Fabric/Kotlin 到 Minecraft 1.21.1、NeoForge 21.1.244 和 Java 21
的迁移；历史迁移要求保留在 [neoforge.md](neoforge.md)，不再代表待办状态。

## 开发指南

- [声明式 API 总览](API.md)
- [物品与国家食物](item/README.md)
- [方块、方块实体与国家材料](block/README.md)
- [实体与十九国居民](entity/README.md)
- [附魔](enchantment/README.md)
- [技能与 Ponder](skill/README.md)
- [Weapon Runtime](weapon/weapon.md)
- [TaCZ 枪包适配](weapon/tacz-adapter.md)
- [群系与泰拉十九国](biome/README.md)
- [泰拉维度与星门](dimension/README.md)
- [Jigsaw 聚落与唯一地标](structure/README.md)
- [泰拉建筑资产流程](architecture/TERRA_ARCHITECTURE_PIPELINE.md)
- [十九国与建筑资料总结](architecture/TERRA_COUNTRIES_AND_ARCHITECTURE.md)
- [《大地巡旅》建筑阅读笔记](architecture/EARTH_GUIDE_ARCHITECTURE_NOTES.md)
- [国家建筑资产路线图](architecture/TERRA_ASSET_ROADMAP.md)
- [现有国家结构审计](architecture/EXISTING_STRUCTURE_AUDIT.md)
- [FTB Quests 指引](quest/README.md)
- [国家关系系统](nation/README.md)

## 当前架构

| 项目        | 当前值                                 |
|-----------|-------------------------------------|
| Minecraft | 1.21.1                              |
| NeoForge  | 21.1.244                            |
| Java      | 21                                  |
| 构建插件      | ModDevGradle 2.0.143                |
| 映射        | Parchment 2024.11.17                |
| 模组 ID     | `zinecraft`                         |
| 源码布局      | `src/main/java` + `src/client/java` |

项目是单模块 NeoForge 模组。`com.cxxcxx.zinecraft.api` 提供按领域拆分的声明目录，`core` 声明实际内容，`compat`
保存可选模组适配，客户端渲染与输入放在 `src/client/java`。通用端不得引用 `net.minecraft.client`。

主要运行依赖包括 TerraBlender、Create/Ponder、FTB Quests、Cloth Config 和
Curios。JEI、JER、AppleSkin、Jade、拼音搜索、JourneyMap、自然指南针和探险家罗盘只用于开发运行配置；它们不是 Zinecraft 发布 JAR
的嵌入依赖。

## 常用任务

```powershell
.\gradlew.bat test       # Java 单元测试
.\gradlew.bat runData    # 数据生成
.\gradlew.bat runClient  # 开发客户端
.\gradlew.bat runServer  # 开发服务端
.\gradlew.bat build      # 完整构建与资源校验
```

推荐验证顺序是 `test` → `runData` → `build` → 按风险选择 `runClient` 或 `runServer`。不要把 `runData` 与 `build` 合并为同一个
Gradle 调用。

## 数据与资源

- `src/main/resources/`：必须随 JAR 发布的手写资源、PNG、OGG、结构 NBT、任务模板和稳定动态注册表 JSON。
- `src/generated/resources/`：`runData` 的可重建输出；用于验证目录生成结果。
- `run/`：开发客户端/服务端工作目录、外置 TaCZ 枪包与本地配置，不进入版本控制。
- `script/`：确定性生成国家纹理、结构模板、Ponder 场景或导入 PRTS 资料的脚本。

新增内容应优先通过目录声明并让数据生成器派生语言、常规模型、掉落和动态注册表数据。无法可靠推导的模型、声音、PNG、NBT 与特殊
JSON 必须显式提供。

## 内容与资料约束

《明日方舟》相关名称、说明与图片只使用明日方舟官网或 PRTS 中的现有资料，不自行补写世界观事实。引入外部素材时记录逐文件来源与权利说明；仓库许可证不覆盖第三方素材。

## 发布前检查

1. `rg --files -g '*.kt' -g '*.kts'` 不应返回源码文件。
2. 运行 `test`、`runData` 和 `build`。
3. 在新世界验证动态注册表、泰拉群系、星门和结构；已有区块不会重新生成内容。
4. 在专用服务端确认通用端未加载客户端类。
5. 检查 `build/libs/` 的发布 JAR、外部依赖声明和第三方素材授权。
