# 实体与 Mob

普通实体使用 `EntityCatalog.register`；`Mob` 使用 `EntityCatalog.mob`，由目录接入 NeoForge 属性事件、生成限制和动态 biome
modifier。

自然生成必须提供权重、群体范围与明确的 `BiomeSelection`。国家特色生物不得使用“全部主世界”默认选择；生成谓词还要接受目标表层。生成蛋由
`MobEntry.spawnEgg` 创建并自动生成翻译与原版模板模型。

renderer/model layer 在 `src/client/java` 通过 NeoForge 客户端事件注册。实体类、AI、属性、生成、国籍与默认装备属于通用端；专用服务器不得加载客户端类。

人形 Mob 在服务端 `finalizeSpawn` 设置装备并明确掉落率。外部枪械通过 `ModTaczWeapons` 构造完整 Data
Component；没有枪包时必须有内置回退。需要实际射击时实现服务端 Mob 武器 AI，不复用玩家 C2S 输入。

国家居民实现 `NationAffiliated`，并保持十九国实体与 renderer 覆盖。参考
`src/main/java/com/cxxcxx/zinecraft/api/entity/EntityCatalog.java` 与 `docs/entity/README.md`。
