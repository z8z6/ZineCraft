# 添加战斗状态

状态声明使用 [ModMobEffect.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModMobEffect.java)，实际施加与硬控制规则位于 [CombatStatusService.java](../../src/main/java/com/cxxcxx/zinecraft/api/combat/CombatStatusService.java)。

## 1. 声明状态

```java
public static final MobEffectBuilder EXAMPLE = effect(
    "example",
    "示例状态",
    "Example",
    0x7A5A44
)
    .attributeModifier(
        Attributes.MOVEMENT_SPEED,
        "movement_speed",
        -0.25,
        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    )
    .build();
```

所有 modifier 都要在 `build()` 前配置。调用原版效果 API 时使用 `ModMobEffect.EXAMPLE.holder()`，Builder 自身不是 Holder。

### effect(...) 与 attributeModifier(...) 参数

| 参数 | 含义 |
| --- | --- |
| `path` | 状态 ID，例如 `cold`。 |
| `zhCn / enUs` | 状态中英文显示名。 |
| `color` | `0xRRGGBB` 粒子/界面颜色，范围必须是 `0x000000` 到 `0xFFFFFF`。 |
| `category` | 项目 helper 当前使用 `HARMFUL`；直接构造 Builder 时也可明确 BENEFICIAL 或 NEUTRAL。 |
| `attribute` | 要修改的原版属性 Holder，例如 `Attributes.MOVEMENT_SPEED`。 |
| `modifierPath` | 当前状态内部唯一的小写修饰符 ID，不要与同一状态的其他 modifier 重复。 |
| `amount` | 修饰数值；`-0.25` 在乘法总量操作下表示降低 25%。 |
| `operation` | ADD_VALUE 为固定值，ADD_MULTIPLIED_BASE 乘基础值，ADD_MULTIPLIED_TOTAL 乘最终总量。 |

## 2. 在服务端施加

对敌方使用 `CombatStatusService.applyToEnemy(...)`，对我方负面状态使用 `applyToFriendly(...)`，这样藏品提供的敌我持续时间修正才会进入正确乘区。

## 3. 硬控制需要额外规则

把攻击速度降到零不等于真正禁止攻击。冻结、麻痹、晕眩等状态还要更新服务端能力判定、攻击入口和叠加/替换逻辑。

## 4. 验证

验证持续时间、重复施加、解除、移动、攻击、重连以及敌我持续时间加成。粒子、声音和动画属于表现层，不能代替服务端状态。
