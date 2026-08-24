---
name: zinecraft-entities
description: Add or revise living Zinecraft entities with server AI, attributes, spawning, loot, spawn eggs, native client models, animations, textures, and renderers. Use for mobs, residents, beasts, or enemies; not block entities.
---

# Zinecraft 生物

同时完成服务端实体规则与原生客户端表现，保证专用服务端不加载客户端类。

## 当前入口

- MobBuilder、EntityCatalog、MobSpawnRestriction、ModEntity
- core/entity/ 下最接近的实体实现
- client/entity/、ZinecraftCoreClient
- CatalogLootTableProvider、TerraMobSpawnPolicy
- script/blockbench/export_resident_native.ps1 与 assets/zinecraft/blockbench/entity/

## 修改流程

1. 在 core/entity 实现属性、AI、目标和交互；只有存在自定义数据时才增加 SynchedEntityData、NBT 保存与同步。
2. 在 ModEntity 用 MobBuilder 声明分类、属性、restriction、尺寸/追踪范围、刷怪蛋，以及 drop(...) 或 noDrops()。EntityCatalog 会验证刷怪蛋和掉落策略。
3. 实体必须在群系前 bootstrap。restriction 只控制 placement predicate；自然生成还需目标 BiomeBuilder.featuredSpawn(...)。
4. TerraMobSpawnPolicy 只取消泰拉中的 NATURAL 且非 friendly category 生成，不负责把实体加入群系生成表。
5. renderer 只在 ZinecraftCoreClient 注册。居民使用 BlockbenchResidentModel / ResidentHumanoidRenderer，兽类使用 TerraCreatureModel / Renderer / Animations；当前仓库没有旧 YSM bridge 管线。
6. 原生纹理放 assets/zinecraft/textures/entity/，Blockbench 源放现有 blockbench/entity 目录。

## 验证

运行 ./gradlew.bat runData、./gradlew.bat test 和 ./gradlew.bat build。检查翻译、刷怪蛋模型、loot、renderer、纹理、模型和动画引用；在客户端验证 AI、碰撞与自然生成，并启动专用服务端确认无客户端类加载错误。
