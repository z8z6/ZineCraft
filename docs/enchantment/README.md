# 添加附魔

Minecraft 1.21.1 的附魔属于动态注册表内容。项目使用 `Zinecraft.ENCHANTMENTS` 声明附魔，并由数据生成器导出
`data/zinecraft/enchantment/<path>.json`。

## 基础附魔

```kotlin
val ORIGINITE_EDGE = Zinecraft.ENCHANTMENTS.register(
  path = "originite_edge",
  zhCn = "源石锋芒",
  enUs = "Originite Edge",
  supportedItems = ItemTags.WEAPON_ENCHANTABLE,
  weight = 5,
  maxLevel = 3,
  minCost = Enchantment.dynamicCost(5, 8),
  maxCost = Enchantment.dynamicCost(25, 8),
  anvilCost = 4,
  slots = arrayOf(EquipmentSlotGroup.MAINHAND)
)
```

返回 `EnchantmentEntry`，其 `.key` 是 `ResourceKey<Enchantment>`。中英文名称会自动进入语言数据生成。

## 主物品与互斥标签

```kotlin
val PRECISE_EDGE = Zinecraft.ENCHANTMENTS.register(
  path = "precise_edge",
  zhCn = "精密锋刃",
  supportedItems = ItemTags.WEAPON_ENCHANTABLE,
  primaryItems = ItemTags.SWORD_ENCHANTABLE,
  exclusiveWith = ModEnchantmentTags.EXCLUSIVE_EDGE,
  maxLevel = 2,
  slots = arrayOf(EquipmentSlotGroup.MAINHAND)
)
```

- `supportedItems` 决定附魔可存在于哪些物品上。
- `primaryItems` 可选，用于附魔台主要选择范围。
- `exclusiveWith` 可选，引用一个附魔标签来声明互斥关系。
- 物品与附魔标签仍应由 tag provider 或资源 JSON 提供。

## 添加效果

`register` 最后的 DSL 接收原版 `Enchantment.Builder`，可组合 1.21.1 的数据组件效果：

```kotlin
val EXAMPLE = Zinecraft.ENCHANTMENTS.register(/* 定义参数 */) {
  withEffect(
    EnchantmentEffectComponents.DAMAGE,
    AddValue(LevelBasedValue.perLevel(1.0f))
  )
}
```

具体效果类型、上下文条件和数值表达式应按玩法需求显式声明；目录只负责统一构建、注册、翻译和数据导出，不会猜测效果。

## 数据生成接入

项目入口已经包含：

```kotlin
registryBuilder.add(Registries.ENCHANTMENT, Zinecraft.ENCHANTMENTS::bootstrap)
```

`ModDynamicRegistryProvider` 也会导出 `Registries.ENCHANTMENT`，新增附魔时不需要修改数据生成入口。运行：

```powershell
.\gradlew.bat runDatagen
```

然后检查生成的附魔 JSON 和语言文件。
