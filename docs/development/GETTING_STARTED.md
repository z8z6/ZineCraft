# 开始之前

本页只需要完成一次。后续修改任何内容，都可以重复使用这里的命令。

## 1. 确认软件

项目使用：

- Windows PowerShell
- Java 21
- Python 3（只在生成结构或导入藏品时需要）
- Minecraft 1.21.1
- NeoForge 21.1.244

在仓库根目录打开 PowerShell，然后执行：

```powershell
java -version
python --version
```

Java 输出中应包含 `21`。如果只改 Java 或 PNG，不运行 Python 脚本，也可以暂时不安装 Python。

## 2. 确认当前目录

PowerShell 中执行：

```powershell
Get-Location
```

应显示项目根目录，例如：

```text
E:\project\zinecraft-template-1.21.1
```

本文所有命令都假设你位于这个目录。

## 3. 查看已有改动

开始前执行：

```powershell
git status --short
```

输出中的文件可能是其他人尚未完成的工作。不要删除、还原或覆盖与本次任务无关的文件。修改前可以把自己要编辑的文件复制一份到项目外作为备份。

## 4. 认识三个资源目录

| 目录                         | 放什么                     | 能否直接修改        |
|----------------------------|-------------------------|---------------|
| `src/main/java/`           | 名称、数值和内容声明              | 可以            |
| `src/main/resources/`      | PNG、NBT、手写 JSON、藏品目录    | 可以，但先看是否由脚本生成 |
| `src/generated/resources/` | `runData` 生成的模型、语言、世界数据 | 不可以手改         |

如果你修改了 `src/generated/resources/`，下一次运行 `runData` 时改动会消失。

## 5. 常用命令

一次只运行一个命令，看到成功后再运行下一个：

```powershell
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
.\gradlew.bat runClient
```

它们分别用于：

| 命令          | 用途                 | 成功标志               |
|-------------|--------------------|--------------------|
| `test`      | 检查数值和工具代码          | `BUILD SUCCESSFUL` |
| `runData`   | 重建模型、语言、群系和维度 JSON | `BUILD SUCCESSFUL` |
| `build`     | 检查并打包模组            | `BUILD SUCCESSFUL` |
| `runClient` | 启动开发客户端            | 出现 Minecraft 主菜单   |

不要把 `runData` 和 `build` 写在同一个 Gradle 命令里。推荐依次运行。

## 6. 第一次启动测试世界

运行：

```powershell
.\gradlew.bat runClient
```

进入游戏后新建一个允许作弊的创造模式世界。建议名称使用：

```text
Zinecraft Dev
```

常用命令：

```mcfunction
/give @s minecraft:structure_block
/give @s zinecraft:<物品或方块 ID>
/locate biome zinecraft:<群系 ID>
/locate structure zinecraft:<结构 ID>
/place structure zinecraft:<结构 ID>
```

输入 ID 时按 `Tab` 可以自动补全。不能补全通常表示 ID 写错，或资源没有成功加载。

## 7. 怎样判断改动是否生效

- 贴图修改：在游戏中按 `F3+T` 重新加载资源。
- 名称和模型修改：运行 `runData` 后重新启动客户端最可靠。
- 群系和维度修改：新建世界验证。
- 自然生成结构修改：前往未生成区块，或新建世界。
- 藏品效果修改：重新装备藏品，再查看 L2 原有“能力”面板。

## 8. 最小提交前检查

```powershell
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
git diff --check
git status --short
```

最后两个命令用于检查多余空格和确认实际修改了哪些文件。不要顺手提交 `run/`、缓存目录或外部枪包。
