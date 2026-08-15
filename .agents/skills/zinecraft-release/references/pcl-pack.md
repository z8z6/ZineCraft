# PCL 整合包格式

## 官方行为依据

PCL 的 `ModModpack.vb` 会在 ZIP 根目录或一级目录寻找 `manifest.json`，没有 `addons` 字段时按 CurseForge 整合包处理。它读取
`minecraft.version` 和 `minecraft.modLoaders`，识别 `neoforge-` 前缀，并把清单指定的 `overrides` 目录复制到新实例。

官方实现：<https://github.com/Meloong-Git/PCL/blob/main/Plain%20Craft%20Launcher%202/Modules/Minecraft/ModModpack.vb>

## 最小清单

```json
{
  "minecraft": {
    "version": "1.21.1",
    "modLoaders": [
      {
        "id": "neoforge-21.1.244",
        "primary": true
      }
    ]
  },
  "manifestType": "minecraftModpack",
  "manifestVersion": 1,
  "name": "Zinecraft 1.0.0",
  "version": "1.0.0",
  "author": "z8z6, YeXingChenAWA",
  "files": [],
  "overrides": "overrides"
}
```

始终用 `gradle.properties` 的当前值替换示例版本。

## 目录结构

```text
Zinecraft-PCL-<version>.zip
├── manifest.json
├── LICENSE-Zinecraft.txt
├── THIRD_PARTY_MODS.md
└── overrides/
    ├── README-Zinecraft.txt
    ├── mods/
    │   ├── zinecraft-<version>.jar
    │   └── <runtime dependencies>.jar
    ├── PCL/
    │   ├── Logo.png
    │   └── Setup.ini
    └── tacz/
        └── <gun pack>/
```

`Setup.ini` 的最小图标配置：

```ini
Logo=PCL\Logo.png
LogoCustom=True
```

## 当前依赖分类

实际版本读取 `gradle.properties` 和 Gradle 解析结果。当前发布通常包括：

- 必需：TerraBlender、Create、Ponder、Flywheel、Registrate、FTB Quests、FTB Library、FTB Teams、Architectury、Cloth Config、Curios。
- 辅助：JEI、AppleSkin、Just Enough Resources、Jade、Just Enough Characters、JourneyMap、Nature's Compass、Explorer's Compass。
- 不放入 `mods/`：Minecraft、NeoForge、DevLaunch、JUnit、sources/API 分类器。

如果依赖树发生变化，更新发布列表，不把本表当作锁文件。

## TaCZ 分发检查

当前默认枪包的 `gunpack_info.json` 声明作者、版本、许可证和 URL。复制前再次读取这些字段，不根据历史发布结果推断。保持目录和文件内容不变，确保包内路径是
`overrides/tacz/<namespace>/`。

## 压缩包检查

使用 `System.IO.Compression.ZipFile.CreateFromDirectory(staging, output, Optimal, false)` 可保证暂存目录本身不会多套一层。创建后重新打开
ZIP：

1. 查找 `manifest.json`。
2. 查找 `overrides/mods/zinecraft-<version>.jar`。
3. 要求枪包发布时存在 `overrides/tacz/<pack>/gunpack.meta.json`。
4. 读取每个非空 Entry 到 `Stream.Null`，触发解压和 CRC 校验。
5. 对最终 ZIP 运行 `Get-FileHash -Algorithm SHA256`。

PCL GUI 不可用时，不声称完成了实际导入或客户端启动；报告已完成格式、内容和压缩完整性校验。
