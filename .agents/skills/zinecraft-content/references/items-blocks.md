# 物品、方块与方块实体

## 入口

- 物品：构造 `ItemBuilder<T>` 时传入 `Zinecraft.ITEMS` 与声明参数，调用 `build()` 后通过 `getItem()` 取注册对象。
- 方块：构造 `BlockBuilder<T>` 时传入 `Zinecraft.BLOCKS` 与声明参数，调用 `build()` 后通过 `block()` 取注册对象。
- 方块实体：构造 `BlockEntityBuilder<E, B>` 时传入 `Zinecraft.BLOCK_ENTITIES`、实体 factory 和一个尚未
  `build()` 的 `BlockBuilder<B>`，调用 `build()` 后通过 `entityBlock()` 与 `entityType()` 取得两部分声明。

普通内容由目录登记翻译、常规模型和默认掉落。PNG 仍放在
`src/main/resources/assets/zinecraft/textures/item|block/<path>.png`。特殊状态、非立方模型或特殊掉落应关闭相应自动项并显式提供资源。

Java factory 使用 `Supplier`，不要恢复默认参数掩码或无语义包装。物品可组合 `fuel(ticks)`、`compost(chance)`；数量与概率必须先校验。

方块实体 renderer 只放在 `src/client/java`。通用端保存状态时调用父类方法，修改数据后调用 `setChanged()`。

新增独立顶层内容类时在 `Zinecraft` 运行时与数据生成入口显式触发。参考
`src/main/java/com/cxxcxx/zinecraft/api/registry/catalog/ItemCatalog.java`、
`api/registry/catalog/BlockCatalog.java`、`api/registry/catalog/BlockEntityCatalog.java` 和
`docs/item|block/README.md`。
