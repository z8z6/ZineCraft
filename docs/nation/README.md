# 泰拉国家关系系统

## 数据边界

`TerraNationRelations.NETWORK` 为十九国提供完整的有向关系网，共 `19 × 18 = 342` 条边。PRTS 用于确认战争、宗教、投资和技术交流等
资料事实；好感度、战争欲望、繁荣度等数值是 Zinecraft 的玩法化初始值，不是《明日方舟》官方统计。

国家自身状态包括：

- `prosperity`：经济与生产体系的繁荣度。
- `stability`：政权和社会秩序稳定度。
- `militaryStrength`：综合军事能力。
- `openness`：贸易、人员和技术交流开放度。
- `aggression`：对外使用军事手段的倾向。

有向双边关系包括 `favor`、`warDesire`、`tradeAffinity`、`tension`、`trust` 和关系标签。未被资料明确描述的国家对采用中性基线，
而不是凭空宣称同盟或敌对；显式关系可以双向不对称。

## 初始国家状态

| 国家   | 繁荣 | 稳定 | 军力 | 开放 | 进攻倾向 |
|------|---:|---:|---:|---:|-----:|
| 阿戈尔  | 72 | 50 | 80 | 10 |   15 |
| 玻利瓦尔 | 35 | 20 | 45 | 55 |   55 |
| 东国   | 62 | 40 | 60 | 45 |   40 |
| 杜林   | 82 | 80 | 35 | 55 |   15 |
| 哥伦比亚 | 88 | 72 | 85 | 90 |   65 |
| 卡西米尔 | 78 | 65 | 72 | 75 |   45 |
| 卡兹戴尔 | 30 | 30 | 88 | 35 |   70 |
| 拉特兰  | 85 | 88 | 78 | 82 |   12 |
| 莱塔尼亚 | 80 | 70 | 82 | 65 |   35 |
| 雷姆必拓 | 72 | 75 | 45 | 85 |   20 |
| 米诺斯  | 60 | 68 | 62 | 55 |   30 |
| 萨尔贡  | 65 | 60 | 70 | 70 |   40 |
| 萨米   | 38 | 70 | 75 | 25 |   15 |
| 维多利亚 | 76 | 40 | 90 | 72 |   55 |
| 乌萨斯  | 55 | 38 | 92 | 30 |   85 |
| 谢拉格  | 58 | 72 | 48 | 52 |   20 |
| 叙拉古  | 70 | 55 | 65 | 72 |   45 |
| 炎    | 88 | 88 | 90 | 60 |   20 |
| 伊比利亚 | 32 | 52 | 65 | 15 |   25 |

## 资料支持的显式关系

| 国家对                     | 关系基调            | 资料依据                                     |
|-------------------------|-----------------|------------------------------------------|
| 哥伦比亚—玻利瓦尔—莱塔尼亚          | 代理冲突、扶植政权       | [玻利瓦尔](https://prts.wiki/w/泰拉大典:地理/玻利瓦尔) |
| 乌萨斯—卡西米尔／萨米／东国          | 历史战争、边境紧张       | [乌萨斯](https://prts.wiki/w/泰拉大典:地理/乌萨斯)   |
| 维多利亚—卡兹戴尔               | 伦蒂尼姆占领与已经结束的战争  | [维多利亚](https://prts.wiki/w/泰拉大典:地理/维多利亚) |
| 阿戈尔—伊比利亚                | 历史技术转移、当前排斥与低信任 | [伊比利亚](https://prts.wiki/w/泰拉大典:地理/伊比利亚) |
| 拉特兰—伊比利亚                | 宗教联系与调解关系       | [拉特兰](https://prts.wiki/w/拉特兰)           |
| 拉特兰—卡兹戴尔                | 萨卡兹入境排斥         | [拉特兰](https://prts.wiki/w/拉特兰)           |
| 谢拉格—维多利亚／哥伦比亚／莱塔尼亚／雷姆必拓 | 改革支持、贸易与投资      | [谢拉格](https://prts.wiki/w/泰拉大典:地理/谢拉格)   |
| 哥伦比亚—维多利亚               | 历史独立、贸易和开拓区紧张   | [哥伦比亚](https://prts.wiki/w/泰拉大典:地理/哥伦比亚) |

## 使用

```java
NationRelationshipNetwork network = TerraNationRelations.NETWORK;
NationState ursus = network.state(TerraNation.URSUS);
NationRelation border = network.relation(TerraNation.URSUS, TerraNation.KAZIMIERZ);
List<NationRelation> allUrsusRelations = network.relationsFrom(TerraNation.URSUS);
```

当前网络是经过校验的初始描述数据，不负责世界存档持久化。后续战争、贸易和玩家声望玩法应复制或包装这些初始值，并由服务端
`SavedData` 保存动态变化，不能让客户端直接修改外交状态。

## API 约束

- `TerraNation.entries()` 返回声明顺序稳定的不可变十九国目录；`findById` 用于安全解析，`requireById` 用于必须存在的配置。
- `NationState` 对每项指标分别校验 `0—100`，错误信息包含国家、字段和值。
- `NationRelationKey` 和 `NationRelation` 都拒绝空国家与自关系；带事实标签的显式关系必须提供 `NationRelationEvidence`。
- `NationRelationEvidence` 只接受具有主机名的 HTTPS 来源，并对 URL 和摘要做非空规范化。
- `NationRelationshipNetwork` 在构造时拒绝重复国家状态、缺失国家状态和重复显式有向关系。构造完成后，状态索引、关系索引及查询结果均为不可变快照。
- `allRelations()` 固定返回 `19 × 18 = 342` 条有向边；`relationsFrom(nation)` 固定返回按目标国家 ID 排序的 18 条边。

旧的 `TerraNation.getEntries()`、`TerraNation.ACCESS.byId()` 和 record 的 `getXxx()` 方法暂时保留，供现有代码兼容；新代码优先使用
record 原生访问器及上述显式查询 API。
