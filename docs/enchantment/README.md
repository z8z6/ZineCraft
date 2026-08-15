# 添加附魔

Minecraft 1.21.1 的附魔属于动态注册表内容。`EnchantmentCatalog` 保存定义和双语名称，`runData` 导出
`data/zinecraft/enchantment/<path>.json`。

```java
EnchantmentEntry edge = Zinecraft.INSTANCE.getENCHANTMENTS().register(
    "originite_edge",
    "源石锋芒",
    "Originite Edge",
    ItemTags.WEAPON_ENCHANTABLE,
    null,
    null,
    5,
    3,
    Enchantment.dynamicCost(5, 8),
    Enchantment.dynamicCost(25, 8),
    4,
    new EquipmentSlotGroup[]{EquipmentSlotGroup.MAINHAND},
    builder -> builder.withEffect(
        EnchantmentEffectComponents.DAMAGE,
        new AddValue(LevelBasedValue.perLevel(1.0F)))
);
```

返回值的 `getKey()` 是 `ResourceKey<Enchantment>`。`supportedItems` 决定允许附魔的物品；`primaryItems` 和 `exclusiveWith`
可为空。物品标签与附魔互斥标签仍由 tag provider 或资源 JSON 提供。

目录不会猜测具体效果、上下文条件或数值表达式。使用原版 `Enchantment.Builder` 和 1.21.1 数据组件效果显式声明。

```powershell
.\gradlew.bat runData
.\gradlew.bat build
```

新增普通附魔无需修改数据生成入口；只有增加新的动态注册表种类时才扩展 registry builder。
