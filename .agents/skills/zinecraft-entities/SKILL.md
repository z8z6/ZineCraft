---
name: zinecraft-entities
description: Add or revise living Zinecraft entities with server AI, attributes, spawning, loot, spawn eggs, client models, animations, textures, and renderers. Use for mobs, residents, beasts, or enemies; not block entities.
---

# Zinecraft 生物

同时完成服务端实体规则与客户端表现，保证专用服务端不加载客户端类。

## 建立上下文

阅读 `AGENTS.md`、工作树和：

- `api/registry/builder/MobBuilder.java`、`api/registry/catalog/EntityCatalog.java`
- `api/entity/MobSpawnRestriction.java`、`core/registry/ModEntity.java`
- `core/entity/` 下最接近的实体实现
- `src/client/java/com/cxxcxx/zinecraft/core/client/entity/` 与
  `src/client/java/com/cxxcxx/zinecraft/core/client/ZinecraftCoreClient.java`
- `api/datagen/CatalogLootTableProvider.java`、`core/dimension/TerraMobSpawnPolicy.java`

除显式客户端路径外，Java 路径均相对于 `src/main/java/com/cxxcxx/zinecraft/`。

## 实现

1. 从官方/PRTS核实物种、名称、外观和行为；不要把猜测写成设定。稳定 ID、双语名和纹理来源应可追踪。
2. 在 `core/entity` 实现实体的属性、目标/行为、交互、保存与同步。逻辑、伤害、掉落和生成判定放通用端；渲染、模型、动画桥接只放
   `src/client/java`。
3. 在 `ModEntity` 用 `MobBuilder` 声明分类、属性、`MobSpawnRestriction`、尺寸/追踪范围、必需的刷怪蛋，以及 `.drop(...)` 或
   `.noDrops()`。构建顺序保持实体先于引用其类型的群系。
4. 在 `ZinecraftCoreClient` 注册 renderer。原生模型参考 `TerraCreatureModel/Renderer/Animations`；YSM 角色参考现有 bridge
   与 `assets/yes_steve_model/builtin/`，明确选择一种管线，不要混用。
5. 原生纹理放 `assets/zinecraft/textures/entity/`，Blockbench 源放现有目录。若需要自然生成，除 spawn restriction 外，还要在目标
   `BiomeBuilder.configure(...)` 中加入 `featuredSpawn(...)`，并核对泰拉自然生成策略。

方块实体使用 `$zinecraft-blocks`；群系生成表同时遵循 `$zinecraft-biomes`。

## 验证

运行 `./gradlew.bat runData`、`./gradlew.bat test`、`./gradlew.bat build`。检查实体/刷怪蛋翻译、刷怪蛋模型、loot
table、renderer、纹理和动画引用；在客户端验证 AI、碰撞、动画与自然生成，并启动专用服务端确认无客户端类加载错误。
