# 物品、方块与方块实体

## 入口

- 物品：`Zinecraft.INSTANCE.getITEMS()`，返回 `ItemEntry<T>`，通过 `getItem()` 取实例。
- 方块：`Zinecraft.INSTANCE.getBLOCKS()`，返回 `BlockEntry<T>`，通过 `getBlock()` 取实例。
- 方块实体：`Zinecraft.INSTANCE.getBLOCK_ENTITIES()`，至少绑定一个有效方块。

普通内容由目录登记翻译、常规模型和默认掉落。PNG 仍放在
`src/main/resources/assets/zinecraft/textures/item|block/<path>.png`。特殊状态、非立方模型或特殊掉落应关闭相应自动项并显式提供资源。

Java factory 使用 `Supplier`，不要恢复默认参数掩码或无语义包装。物品可组合 `fuel(ticks)`、`compost(chance)`；数量与概率必须先校验。

方块实体 renderer 只放在 `src/client/java`。通用端保存状态时调用父类方法，修改数据后调用 `setChanged()`。

新增独立顶层内容类时在 `Zinecraft` 运行时与数据生成入口显式触发。参考
`src/main/java/com/cxxcxx/zinecraft/api/item/ItemCatalog.java`、`api/block/BlockCatalog.java`、
`api/block/BlockEntityCatalog.java` 和 `docs/item|block/README.md`。
