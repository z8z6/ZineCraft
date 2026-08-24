---
name: zinecraft-release
description: Build and validate Zinecraft release JARs or the repository's PCL-compatible modpack for Minecraft 1.21.1 and NeoForge. Use for release artifacts, package diagnostics, staged runtime mods, manifests, and checksums.
---

# Zinecraft 发布

以当前工作树和 build.gradle 中的发布任务为准，不复用旧产物，也不手工重建已经自动化的打包流程。

## 发布上下文

1. 读取 AGENTS.md、gradle.properties、build.gradle、模组元数据模板和 git status --short；保留用户修改，不更改 Gradle wrapper 的腾讯镜像。
2. 版本从 gradle.properties 读取。先确认交付独立 JAR、PCL 包或两者；当前包结构与任务保证见 references/pcl-pack.md。
3. `src/client-pack/` 是受版本控制的客户端实例镜像；开发启动将其整体安装到 `run/`，PCL 打包将其整体复制到 `overrides/`。`verifyPclPackage` 当前硬性要求 options.txt 与两份 JourneyMap 6.0 配置；缺失时先报告，不从本地运行目录伪造或补取配置。

## 独立 JAR

依次单独运行：

  ./gradlew.bat runData --no-configuration-cache --console=plain
  ./gradlew.bat build --no-configuration-cache --console=plain

选择 build/libs/zinecraft-<version>.jar，排除 sources JAR；记录大小、修改时间和 SHA-256。runData 会生成动态资源并恢复压缩 Terra layout，随后 build 打入 JAR。

## PCL 整合包

1. 先单独运行 ./gradlew.bat runData --no-configuration-cache --console=plain，再运行 ./gradlew.bat verifyPclPackage --no-configuration-cache --console=plain。verifyPclPackage 依赖 packagePcl → preparePclPackage → build，但该链路本身不运行 runData；它会自动解析运行时 classpath、筛选模组 JAR、复制配置、生成 manifest/图标/说明/第三方清单、打 ZIP，并读取非空 ZIP entry。
2. 默认 `bundleRuntimeMods=true`，输出 `build/distributions/Zinecraft-PCL-<version>.zip` 并携带依赖 JAR。使用 `-PbundleRuntimeMods=false` 输出 `Zinecraft-PCL-<version>-thin.zip`，只携带 Zinecraft JAR，并在 `runtime-dependencies.json` 与 `THIRD_PARTY_MODS.md` 中列出依赖；精简包不会自动下载依赖。目标存在时任务默认拒绝覆盖，只有用户明确允许替换时才使用 `-PoverwritePcl=true`。
3. Gradle 任务会把 TaCZ 模组依赖作为运行时模组处理，但当前不会复制 run/tacz/ 下的外置枪包。用户要求枪包时，必须扩展 build.gradle 的 preparePclPackage 与 verifyPclPackage，或建立独立且完整的打包/验证流程；人工预填 staging 会被 preparePclPackage 删除。没有清晰许可证和来源时不分发。
4. 任务生成的 THIRD_PARTY_MODS.md 只对部分菜单/提示模组写出许可证，其余主要列文件名。交付前审计 staged mods 的项目、版本、许可证和允许再分发性，不把该文件视为完整许可证明。

## 补充验证

Gradle 任务通过后仍检查：

- manifest 可解析，Minecraft/NeoForge/overrides 与 gradle.properties 一致。
- ZIP 根直接包含 manifest.json，PCL 图标配置存在。
- 包内 Zinecraft JAR 与 build/libs 产物 SHA-256 相同。
- mods 中没有 sources/API/devlaunch/Minecraft/NeoForge 安装器；候选是 NeoForge mod 或明确的 library。
- 重新读取全部 ZIP entry，并记录最终 ZIP SHA-256。
- 只有实际用 PCL 导入并启动后才声称完成 GUI/客户端验证。

## 完成报告

提供产物绝对路径、版本、大小、模组数量、SHA-256、通过的 Gradle 任务、工作树状态、是否包含外置枪包，以及未执行的 PCL/客户端验证。不要声称生成了不存在的 checksum 文件。
