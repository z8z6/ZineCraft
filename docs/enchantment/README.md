# 添加附魔

Minecraft 1.21.1 的附魔属于动态注册表内容。`EnchantmentCatalog` 保存定义和双语名称，`runData` 导出
`data/zinecraft/enchantment/<path>.json`。

```java
EnchantmentBuilder edge = new EnchantmentBuilder(
    Zinecraft.ENCHANTMENTS, "originite_edge", "源石锋芒", ItemTags.WEAPON_ENCHANTABLE)
    .enUs("Originite Edge")
    .weight(5)
    .maxLevel(3)
    .costs(Enchantment.dynamicCost(5, 8), Enchantment.dynamicCost(25, 8))
    .anvilCost(4)
    .slots(EquipmentSlotGroup.MAINHAND)
    .configure(builder -> builder.withEffect(
        EnchantmentEffectComponents.DAMAGE,
        new AddValue(LevelBasedValue.perLevel(1.0F))))
    .build();
```

`build()` 返回 `EnchantmentBuilder`，其 `getKey()` 是 `ResourceKey<Enchantment>`。未设置的英文名会从 ID 自动生成；默认权重、
最高等级、最低/最高成本、铁砧成本和装备槽分别为 `10`、`1`、常量 `1`、常量 `1`、`1` 和 `ANY`。
`supportedItems` 决定允许附魔的物品；`primaryItems` 和 `exclusiveWith` 可不设置。物品标签与附魔互斥标签仍由 tag provider
或资源 JSON 提供。

目录不会猜测具体效果、上下文条件或数值表达式。使用原版 `Enchantment.Builder` 和 1.21.1 数据组件效果显式声明。

```powershell
.\gradlew.bat runData
.\gradlew.bat build
```

新增普通附魔无需修改数据生成入口；只有增加新的动态注册表种类时才扩展 registry builder。
