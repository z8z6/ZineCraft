---
name: zinecraft-attribute-panel
description: Add or revise Zinecraft rows, pagination, hover formulas, Shift contribution details, and localized Curios modifier sources inside the L2Tabs attribute panel. Use for client attribute-panel presentation, not server combat or collectible-effect implementation.
---

# Zinecraft 属性面板

将 Zinecraft 自定义属性作为 L2Tabs 原属性列表的一部分展示，并保持原面板的分页、坐标、颜色、悬浮乘区和 Shift 明细行为。显示层只能解释当前权威快照，不能参与或改写服务端结算。

## 当前入口

- 面板注入：src/client/java/com/cxxcxx/zinecraft/core/client/mixin/AttributeScreenMixin.java
- L2Tabs 布局访问：src/client/java/com/cxxcxx/zinecraft/core/client/mixin/BaseTextScreenAccessor.java
- 藏品属性行、乘区与来源追踪：src/client/java/com/cxxcxx/zinecraft/core/client/collection/CollectibleEffectDisplay.java
- 客户端 Mixin 清单：src/client/resources/zinecraft.client.mixins.json
- 双语属性名：CollectibleCatalog.registerCommonTranslations
- Curios 原版属性修饰器：CollectibleItem.getAttributeModifiers、CombatStat.toVanillaModifiers

L2Tabs 由 L2Library 的嵌套 JAR 在运行时加载，不暴露在项目 Java 编译类路径。面向 BaseAttributeScreen 和 BaseTextScreen 的 Mixin 必须继续使用字符串目标、Accessor、Invoker 和标准注入描述符；不要直接导入 dev.xkmc.l2tabs.*。升级 L2Library/L2Tabs 后，先只读检查实际类签名和语言键，再调整注入点。

## 合并进原属性列表

1. 自定义行必须计入 BaseAttributeScreen.init 的总行数，复用 L2Tabs 原左右分页按钮；不能只在 render 尾部绘制而不扩展页数。
2. 组合索引为“原属性数量 + 自定义行索引”。当前布局沿用原面板的 leftPos + 8、topPos + 6 和 10 像素行高；翻页时用当前 page 与 getSize() 裁剪。
3. 普通行保持原属性格式：数值 属性名，颜色参数为黑色 0，即使数值为 0 也显示。倍率行展示相对基线的变化，例如内部倍率 1.2 在主列表显示为 20%。
4. 不再创建右侧浮动框或彩色分组标题。增加或删除属性时，同步更新显示条目、分页行数和双语属性名。

当前藏品探索面板固定覆盖 CombatStat 的 19 项探索字段：希望、源石锭、行动力、抗干扰指数、坍缩值、负荷临界点、思绪、烛火、编队容量、部署上限、初始部署费用、钥匙、骰子、灯火、指挥经验倍率、每非战斗节点希望、每非战斗节点源石锭、战斗源石锭倍率和失败续行目标生命。字段集合变化时以 CombatStat 当前记录组件和实际消费者为准，不从 PRTS 文本推断新属性。

## 悬浮乘区与 Shift 明细

悬浮范围使用该行文本的实际字体宽度和 10 像素行高。提示复用 L2Tabs 已有翻译键：

- menu.tabs.attribute.base
- menu.tabs.attribute.add
- menu.tabs.attribute.mult_base
- menu.tabs.attribute.mult_all
- menu.tabs.attribute.format
- menu.tabs.attribute.detail

未按 Shift 时显示基础值、加法乘区、乘法乘区、独立乘法乘区、最终公式和“按 Shift 显示细节”；按 Shift 后去掉提示，并把逐藏品贡献放在对应乘区摘要之后。数值和颜色沿用 L2Tabs：正值绿色、负值红色、乘区摘要蓝色、属性标题金色、来源名称深灰色。

逐藏品来源不能从最终聚合值平均拆分，也不能解析说明文字。按 Curios 实际装备迭代顺序，从 CombatStat.EMPTY.withCollectibleEffectTier(...) 开始依次应用每件 CollectiblePower，保存每一步的 before/after：

- 加法字段贡献为 after - before。
- 连乘字段贡献为 after / before，最终倍率必须等于逐步连乘结果。
- 只在 Shift 明细中列出非零或非单位倍率的贡献。
- 来源名称取装备 ItemStack 的本地化悬浮名；中文语言环境应显示藏品中文名，不显示注册路径。

该追踪只构造客户端展示数据。不要为它增加第二套服务端属性模型、持久化数据或独立结算逻辑。

## Curios 原版属性来源名称

藏品提供的生命、攻击、护甲、韧性和攻速等原版属性仍由 Curios 属性修饰器承载。其 ID 形如：

    <namespace>:collectible/<collectible_path>/<attribute>/<operation>

L2Tabs 默认在 Shift 详情中直接显示该 ID。只对 collectible/ 前缀进行替换：从 ID 提取 collectible_path，在 BuiltInRegistries.ITEM 中查找同命名空间物品，并返回该物品 descriptionId 的本地化名称。非藏品修饰器、无法解析的路径和缺失物品必须保留 L2Tabs 原结果，不能误改其他模组来源。

## 翻译与验证

属性名在 CollectibleCatalog 的翻译目录中维护，生成的 src/generated/resources/**/lang/*.json 不能手工修改。按改动范围执行：

- Java 或 Mixin 修改：./gradlew.bat compileJava
- 显示计算、逐件追踪或行为变化：./gradlew.bat test
- 属性名或其他翻译变化：./gradlew.bat runData

最终还应在中文客户端打开能力页进行冒烟检查：零值行存在、翻页不丢行、普通行黑色、悬浮公式正确、Shift 仅展开非零来源、加法与连乘总值和主行一致、原版属性的藏品来源显示本地化名称。数据运行不会保证目标屏幕一定被类加载，因此编译和 runData 不能完全替代这项客户端检查。
