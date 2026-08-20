# 实体与 Mob

普通实体使用 `EntityCatalog.register`；`Mob` 直接构造 `MobBuilder` 并传入 `EntityCatalog`，由目录接入 NeoForge
属性事件和生成限制。

自然生成必须在目标群系注册时直接提供类别、实体类型、权重与群体范围。国家特色生物不得使用跨群系的 biome modifier 注入；生成谓词还要接受
目标表层。生成蛋由 `MobBuilder.spawnEgg` 创建并自动生成翻译与原版模板模型；`build()` 返回保存已注册实体类型和刷怪蛋的同一
builder。

renderer/model layer 在 `src/client/java` 通过 NeoForge 客户端事件注册。实体类、AI、属性、生成、国籍与默认装备属于通用端；专用服务器不得加载客户端类。

人形 Mob 在服务端 `finalizeSpawn` 设置装备并明确掉落率。外部枪械通过 `ModTaczWeapons` 构造完整 Data
Component；没有枪包时必须有内置回退。需要实际射击时实现服务端 Mob 武器 AI，不复用玩家 C2S 输入。

国家居民使用群系直接声明的普通友好生物，不创建专用居民实体或 renderer。参考
`src/main/java/com/cxxcxx/zinecraft/api/registry/catalog/EntityCatalog.java` 与 `docs/entity/README.md`。
