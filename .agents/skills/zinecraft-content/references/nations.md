# 国家居民与关系

## 居民覆盖

- `TerraNation` 是十九国稳定 ID 的唯一来源；实体、关系、任务和文档使用同一枚举。
- 每个 `NationBiomes` 群系必须同时保留一种独特特色生物和一种所属国家明确的居民实体。
- 普通国家居民使用 `NationResident`，通过国家专属实体类型绑定 `NationResidentProfile`，并只在对应群系生成。
- 拉特兰使用 `LateranoCitizen`，继续在服务端 `finalizeSpawn` 配置默认枪械。
- 所有居民实现 `NationAffiliated`。玩法代码读取 `nation`，不得根据翻译名、皮肤或当前位置猜测国籍。
- 客户端只负责模型与皮肤；实体类型、自然生成、国籍和装备全部在通用端定义。

新增国家群系或重构居民时，检查 `ModEntities.RESIDENT_TYPES_BY_NATION` 的覆盖断言，并同步注册客户端 renderer。

## 关系建模

- `NationState` 描述单个国家的繁荣、稳定、军力、开放与进攻倾向，范围均为 0—100。
- `NationRelation` 是有向边：好感和信任为 -100—100；战争欲望、贸易倾向和紧张度为 0—100。
- `NationRelationshipNetwork` 为没有明确资料的国家对建立中性基线，所以任意两个不同国家都可安全查询。
- PRTS 只支撑战争、投资、宗教联系等事实；每条显式边附 `NationRelationEvidence`。具体数值必须在文档中标明为玩法推断。
- 不要为补满关系网而虚构同盟或敌对。没有来源时保留中性边。
- 双向态度可能不同；有不对称事实时分别声明两条有向边。
- 初始网络只读。需要动态外交时，在服务端以 `SavedData` 包装并持久化变化，客户端只能接收同步结果。

实现与资料表见 `core/nation/TerraNationRelations.kt` 和 `docs/nation/README.md`。
