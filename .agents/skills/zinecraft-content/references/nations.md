# 国家居民与关系

- `TerraNation` 是十九国稳定 ID 的唯一来源；实体、群系、结构、任务和关系共用该枚举。
- 每个国家群系拥有明确居民实体；普通居民使用 `NationResidentProfile`，拉特兰使用 `LateranoCitizen`。
- 所有居民实现 `NationAffiliated`，玩法读取明确国籍，不从名称、皮肤或位置推断。
- `NationState` 数值范围 0—100；`NationRelation` 是有向边。没有资料的国家对保持中性，不虚构同盟或敌对。
- PRTS 只支持战争、投资、宗教等事实；玩法数值标记为项目初始值，并附 `NationRelationEvidence`。
- 动态外交由服务端 `SavedData` 包装初始网络，客户端只接收同步结果。

修改后检查十九国覆盖断言、客户端 renderer、任务节点和 `docs/nation/README.md`。实现位于
`src/main/java/com/cxxcxx/zinecraft/core/nation/TerraNationRelations.java`。
