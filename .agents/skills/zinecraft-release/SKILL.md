---
name: zinecraft-release
description: Build, validate, and package Zinecraft releases and PCL-compatible modpacks for Minecraft 1.21.1 and NeoForge. Use when producing release JARs, preparing distributable ZIPs, including runtime helper mods or TaCZ gun packs, generating PCL-readable CurseForge manifests, calculating checksums, or diagnosing packaging failures.
---

# Zinecraft 发布与 PCL 整合包

从当前 Git 工作树构建可发布的 Zinecraft，并生成可由 PCL 直接导入的整合包。以仓库配置和本次实际解析结果为准，不复用无法证明来自当前源码的旧产物。

## 建立发布上下文

1. 阅读根目录 `AGENTS.md`、`gradle.properties`、`build.gradle`、模组元数据模板和 `git status --short`。
2. 保留用户修改，不更改 `gradle/wrapper/gradle-wrapper.properties` 的腾讯镜像地址。
3. 从 `gradle.properties` 读取 Minecraft、NeoForge 和模组版本；不要在发布命令中重复维护版本。
4. 确认用户需要独立 JAR、PCL 整合包或两者。PCL 格式细节和目录示例见 `references/pcl-pack.md`。

## 构建发布 JAR

1. 若仓库提供 FTB Quests 校验脚本，先运行 `.agents/skills/zinecraft-content/scripts/validate_ftbquests.ps1`。
2. 依次单独执行：

   ```powershell
   ./gradlew.bat test --no-configuration-cache --console=plain
   ./gradlew.bat runData --no-configuration-cache --console=plain
   ./gradlew.bat build --no-configuration-cache --console=plain
   ```

3. 不把 `runData` 和 `build` 合并为一次 Gradle 调用。`runData` 生成 `src/generated/resources/`，随后 `build` 将其打入 JAR。
4. 选择 `build/libs/zinecraft-<version>.jar`；排除 `-sources.jar`。运行时改动在可行时再验证 `runClient`。
5. 记录 JAR 大小、修改时间和 SHA-256。

## 收集整合包依赖

1. 用 `./gradlew.bat dependencies --configuration runtimeClasspath` 获取当前依赖树；以实际解析版本为准。
2. 包含 Zinecraft 的必需前置，以及用户要求随包提供的 `runtimeOnly` 辅助模组。
3. 不把 Minecraft、NeoForge、DevLaunch、普通 Maven 库、`-sources.jar` 或 `-api.jar` 放入 `mods/`。Minecraft 与 NeoForge 由
   PCL 根据清单安装。
4. 优先使用正式运行时 JAR。若 Create 当前只解析到已验证的 `slim` 产物，同时加入 Ponder、Flywheel 和 Registrate。
5. 打开每个候选 JAR，要求存在 `META-INF/neoforge.mods.toml`；允许 Manifest 中声明 `FMLModType: GAMELIBRARY` 的游戏库，例如
   Registrate。
6. 给从 Maven 缓存取得的 Modrinth 文件恢复可读的原始文件名，并在 `THIRD_PARTY_MODS.md` 记录项目、版本和许可证归属。

## 包含 TaCZ 枪包

仅在用户要求时将外置枪包加入发布物：

1. 从 `run/tacz/<pack>/` 读取 `gunpack.meta.json`、`assets/tacz/gunpack_info.json` 和说明文件。
2. 不修改枪包素材；原样复制到 `overrides/tacz/<pack>/`。
3. 在第三方声明中记录版本、作者、许可证和来源 URL。许可证不清楚时停止分发并请求确认。
4. 比较源目录和包内目录的文件数；必要时追加逐文件哈希校验。

## 生成 PCL 整合包

1. 使用 PCL 可识别的 CurseForge 结构：根目录 `manifest.json`，实例内容位于 `overrides/`。
2. 在清单中声明精确的 Minecraft 与 `neoforge-<version>`，`files` 可为空；本地构建和已获准分发的依赖放入 `overrides/mods/`。
3. 可把项目图标复制为 `overrides/PCL/Logo.png`，并通过 `overrides/PCL/Setup.ini` 设置自定义图标。
4. 在实例根目录放置简短的安装说明；提醒使用 Java 21，并建议至少 6 GB 游戏内存。
5. 输出到版本化路径 `build/distributions/Zinecraft-PCL-<version>.zip`。目标已存在时不要静默覆盖。

## 验证发布物

完成前全部检查：

- `manifest.json` 能解析，Minecraft、NeoForge、`overrides` 字段正确。
- 包内 Zinecraft JAR 与 `build/libs` 产物 SHA-256 相同。
- `mods/` 中没有源码/API JAR，所有条目都是 NeoForge 模组或明确的 `GAMELIBRARY`。
- ZIP 根目录直接包含 `manifest.json`，没有多套一层目录。
- ZIP 包含 Zinecraft JAR、PCL 图标配置，以及用户要求的 TaCZ 元数据。
- 打开 ZIP 并读取每个非空条目，让 CRC 或损坏问题在交付前失败。
- 生成整合包 SHA-256；若无法实际启动 PCL，明确说明只完成了格式和内容校验。

## 完成报告

提供独立 JAR、PCL ZIP 和校验文件的绝对路径，报告版本、大小、模组数量、TaCZ 文件数量、SHA-256、实际通过的 Gradle 任务及未执行的
GUI 启动验证。
