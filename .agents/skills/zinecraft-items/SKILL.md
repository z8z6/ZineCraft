---
name: zinecraft-items
description: Add or revise ordinary Zinecraft catalog-backed items and their generated resources. Use for materials, food, and components; use the weapon, skill, collectible, or sound workflow for specialized items and music discs.
---

# Zinecraft 普通物品

通过 ItemCatalog 新增普通物品，并保持翻译、模型、创造页、配方与手工数据一致。

## 当前入口

- ItemBuilder、ItemCatalog
- ModItem、ModCreativeTab、ModRecipeProvider
- core/Zinecraft.java、core/datagen/ZinecraftDataGenerator.java

## 修改流程

1. 在 ModItem 用 ItemBuilder 声明稳定 snake_case ID、双语名、factory、模型模板与创造页选项；普通材料参考 item(...)，食物参考 food(...)，真正需要行为时才新增 Item 子类。
2. 默认模型由 CatalogModelProvider 生成，纹理放 assets/zinecraft/textures/item/<id>.png。model 为 null 表示不生成模型，此时必须维护手写模型；inCreativeTab=false 会从主物品页排除。
3. 当前普通配方在 ModRecipeProvider 声明。需要标签、任务、战利品或兼容层时维护对应 src/main/resources/data/...；runData 不会自动推断这些接入。
4. 保持 Zinecraft.bootstrapContent() 的显式加载顺序；只有新增独立注册类时才补 bootstrap。
5. 音乐唱片不走 ModItem 普通流程，使用 MusicDiscBuilder、ModSound 与 SoundCatalog；手工补齐 OGG 与 sounds.json，并确认 runData 生成 jukebox song 和唱片模型。其他专用物品分别路由到对应 skill。

## 验证

运行 ./gradlew.bat test、./gradlew.bat runData 和 ./gradlew.bat build。检查双语翻译、模型、纹理、配方/标签、创造页、任务/loot 引用和 JAR；唱片还应通过 verifyMusicDiscJarResources 并在客户端试听。
