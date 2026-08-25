# 添加集成战略藏品

所有运行时藏品都直接声明在 [ModCollectible.java](../../src/main/java/com/cxxcxx/zinecraft/core/registry/ModCollectible.java)。每件藏品对应一个公开 `CollectibleBuilder` 静态字段，不创建额外内部注册类。

## 1. 准备可靠资料

中文名、原效果、描述和图片必须来自 PRTS 等指定来源。英文名优先使用 ArkData。字段名、注册路径和 PNG 文件名分别使用一致的 `UPPER_SNAKE_CASE` 与 `lower_snake_case`。

## 2. 声明藏品

```java
public static final CollectibleBuilder EXAMPLE_RELIC = collectible(
    "example_relic",
    "示例藏品",
    "原始效果文本",
    "Original effect",
    "原始描述",
    "Original lore",
    implementedRule("原始效果文本", power -> power.attack(0.15)),
    Rarity.RARE
);
```

纹理放在 `assets/zinecraft/textures/item/example_relic.png`。同名藏品不能重复注册，也不要保存 `orderId`。

### collectible(...) 的参数

| 参数 | 含义 |
| --- | --- |
| `path` | 藏品注册 ID、纹理名和模型名的共同基础。 |
| `zhCn` | PRTS 中文名称，不能自行改写。英文名由 Builder 从 path 生成或通过 `.enUs(...)` 覆盖。 |
| `originalEffectZhCn / EnUs` | 原作效果双语文本，只用于资料保真和展示，不在运行时解析。 |
| `descriptionZhCn / EnUs` | 原作背景描述双语文本。 |
| `effect` | `PowerDefinition`，同时包含 Minecraft 适配说明、`CollectiblePower` 和未实现 source rules。 |
| `rarity` | 原版物品稀有度，只控制物品显示稀有度，不代表效果档位。 |

辅助方法内部依次调用 `originalEffect`、`description`、`minecraftEffect`、`sourceRules`、`rarity` 和 `build`。因此一件藏品只能有一个公开 Builder 字段，但 `CollectiblePower` 内可以保留多个 TierDefinition。

## 3. 正确处理效果

- 简单数值通过不可变 `CombatStat` 字段组合。
- 条件、触发时机和作用域不能从原文中删掉。
- 同一藏品的多档效果全部保留为 TierDefinition，由运行时条件选择。
- 职业只使用八种 `SkillProfession`，并且只影响对应职业技能。
- 敌方受到的物理/法术伤害使用独立最终乘区。
- 目标生命、临时目标生命和护盾复用 `maxHealth`。
- 无法忠实实现的规则保留为 `sourceRule`，并进入未实现效果审计。

## 4. 验证

先运行导入器的只读校验和脚本测试，再执行 Java 测试、编译与数据生成。最终检查公开字段数量、重名、分档、纹理文件、Curios tag、双语键与未实现效果统计。
