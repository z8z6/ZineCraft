# 当前 PCL 包契约

本参考描述 build.gradle 的实际输出；任务实现变化时先更新代码，再同步本文件。

## 主任务

- preparePclPackage：构建 staging，解析 runtimeClasspath、clientRuntimeRuntimeClasspath 与 clientLegacyClasspath，筛选可分发 JAR，并按 bundleRuntimeMods 选择携带 JAR 或只写依赖清单。
- build：组装模组产物；发布流程不包含 check。
- packagePcl：把 staging 压成版本化 ZIP；默认拒绝覆盖，显式属性为 -PoverwritePcl=true。
- verifyPclPackage：依赖前两项，检查关键 entry，读取所有非空 entry，并打印 ZIP SHA-256。

## 目录

  Zinecraft-PCL-<version>[-thin].zip
  ├── manifest.json
  ├── runtime-dependencies.json
  ├── LICENSE-Zinecraft.txt
  ├── THIRD_PARTY_MODS.md
  └── overrides/
      ├── README-Zinecraft.txt
      ├── options.txt
      ├── config/
      ├── journeymap/config/
      ├── mods/
      │   └── zinecraft-<version>.jar
      └── PCL/
          ├── Logo.png
          └── Setup.ini

manifest 的 Minecraft、NeoForge、模组版本和作者由 build.gradle / gradle.properties 生成，不手抄示例值。实例使用 Java 21，并在说明中建议至少 6 GB 内存。

`src/client-pack/` 直接镜像 `overrides/` 与开发实例根目录；发布配置只从该受版本控制目录取得，不从 `run/` 快照。

## 当前自动保证

verifyPclPackage 要求：

- manifest.json
- runtime-dependencies.json 与打包模式、mods 内容一致
- overrides/options.txt
- JourneyMap 6.0 fullmap 与 minimap 配置
- overrides/mods/zinecraft-<version>.jar

它还会读取所有非空 ZIP entry 并输出 SHA-256。它目前不会解析 manifest、比较内外 Zinecraft JAR 摘要、检查 PCL 图标、逐个复验 staged mod 类型或写 checksum 文件；这些属于 skill 中的补充只读验证。

## 运行时模组与许可

不要维护静态依赖列表。以 build.gradle 的依赖配置、Gradle 实际解析结果和 staging 内容为准。preparePclPackage 接受带 NeoForge mods metadata 的 JAR，以及 Manifest 声明 GAMELIBRARY/LIBRARY 的库，并排除已知加载器、Minecraft、DevLaunch 和 client-extra 产物。

THIRD_PARTY_MODS.md 当前只完整声明部分客户端菜单/提示依赖，其余文件仍需发布前许可审计。

默认模式将依赖 JAR 放入 `overrides/mods/`；`-PbundleRuntimeMods=false` 生成 `-thin.zip`，仅保留 Zinecraft JAR。两种模式都写出含组件坐标、文件名和 bundled 状态的 `runtime-dependencies.json`。

## TaCZ

TaCZ 模组本身来自 runtime classpath；外置 gun pack 不在当前 staging 逻辑中，ZIP 默认没有 overrides/tacz。只有用户要求、来源与许可证允许且打包任务已增加相应复制/校验时，才把枪包列为发布内容。
