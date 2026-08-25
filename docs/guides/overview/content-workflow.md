# 新增内容的通用流程

这份工程已经把 NeoForge 的注册、翻译和数据生成包装成 Catalog + Builder。萌新通常不需要自己创建 `DeferredRegister`，而是找到相应的 `ModXxx` 注册类，照同类内容增加一个静态字段。

## 先认识四层结构

1. **声明**：在 `core/registry` 或对应内容目录中声明 Builder。
2. **行为**：只有内容确实需要逻辑时，才在 `core` 下增加 Java 类。
3. **资源**：纹理、声音、结构 NBT 等手工资源放在 `src/main/resources`。
4. **生成数据**：翻译、模型、战利品表和世界生成 JSON 由 `runData` 写入 `src/generated/resources`。

项目总入口是 [Zinecraft.java](../../src/main/java/com/cxxcxx/zinecraft/core/Zinecraft.java)，数据生成入口是 [ZinecraftDataGenerator.java](../../src/main/java/com/cxxcxx/zinecraft/core/datagen/ZinecraftDataGenerator.java)。

## 推荐操作顺序

1. 复制同一注册类里最接近的现有声明。
2. 给内容确定不会再变化的 `lower_snake_case` ID。
3. 同时填写中文名和英文名，不要用显示名充当注册 ID。
4. 添加必需的 PNG、NBT、OGG 或手写 JSON。
5. 运行 `runData`，检查生成差异。
6. 运行测试与构建，再进入客户端验证实际行为。

## 看懂所有 Builder 的共同参数

| 常见名称 | 统一含义 |
| --- | --- |
| `catalog` | 内容所属的唯一目录，负责注册、重复 ID 校验和数据生成；不要另建 DeferredRegister。 |
| `path` | 不带 `zinecraft:` 的稳定资源路径，通常使用 `lower_snake_case`。 |
| `zhCn / enUs` | 数据生成写入的中英文名，不参与注册表寻址。 |
| `factory` | 延迟创建实际 Minecraft 对象的函数；静态初始化时不要提前调用 `.get()`。 |
| `configure` | 对原版 Builder 或项目 Builder 做额外设置的回调。 |
| `build()` | 完成校验并登记；不是 Gradle build，同一 Builder 只能调用一次。 |

看到 `Supplier<T>` 时，可以把它理解为“现在先保存创建方法，等 NeoForge 注册阶段再创建对象”；看到 `ResourceKey<T>` 时，它只是注册表地址，不是已经创建好的对象。

```powershell
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
```

## 不要直接修改什么

- 不要手改 `src/generated/resources` 中可由数据生成器重建的文件。
- 不要为每个内容类型重复创建注册表。
- 不要只看到物品出现在创造栏就认为功能完成；掉落、配方、生成和服务端行为需要分别验证。
- 不要把客户端动画或粒子当成伤害、控制等服务端规则。

## 如何使用图鉴反查源码

打开“图鉴”，每张卡片都会显示注册 ID 和主要源码入口。先搜索一个与你想做的内容最接近的现有条目，再打开对应 `ModXxx` 文件模仿，通常比从空白开始可靠。
