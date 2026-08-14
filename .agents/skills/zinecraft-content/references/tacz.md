# TaCZ 外置枪包适配

处理“复用 TaCZ 枪械资源”时，先阅读 `docs/weapon/tacz-adapter.md`，再检查 `api/weapon/tacz`、
`core/weapon/ModTaczWeapons.kt` 与客户端 `core/client/weapon/tacz`。不要把 TaCZ Forge 模组加入 Fabric
运行依赖，也不要把官方或第三方枪包复制进 `src/main/resources`。

## 格式与加载边界

- 从 `FabricLoader.gameDir/tacz` 发现根目录含 `gunpack.meta.json` 的目录或 ZIP。
- 使用虚拟路径读取包，拒绝绝对路径、空段和 `..`；ZIP InputStream 关闭时必须同时关闭 ZipFile。
- TaCZ JSON 允许 `//` 注释，使用 lenient `JsonReader`，不要用严格序列化器直接绑定。
- 索引链是 `data/<ns>/index/guns` → `data/<ns>/data/guns` + `assets/<ns>/display/guns`；显示文件再引用
  `geo_models`、`textures`、`animations` 和 `tacz_sounds`。
- 多包按稳定文件名排序并构造分层资源视图，后层覆盖前层。索引路径和被引用资源都必须通过同一分层视图解析。

## 注册表与状态

Minecraft 启动后不能按资源重载动态增删 Item。继续复用一个 `TaczGunItem` 与一个 `TaczAmmoItem`，用
`TACZ_GUN_ID` / `TACZ_AMMO_ID` Data Component 区分外部定义；创造栏添加带 ID 的 ItemStack 变体。

`WeaponRegistry.replaceDynamic` 只替换指定来源的 Definition，`registerResolver` 根据枪械 ID 解析物品栈。所有外部枪械复用稳定的
fire/reload/aim/fire-select/inspect/melee/bolt Action；Action 在执行时从当前 `TaczGunPacks.snapshot`
读取服务端数值。不要为每把外部枪注册新的网络载荷或 Item。开火模式和手动枪机状态是网络同步的 ItemStack Data Component。

## 客户端资源

- GUI 优先使用 `slot` PNG；手持、掉落和展示框解析 Bedrock geometry。纹理用 `NativeImage` + `DynamicTexture` 从枪包惰性注册，不提交缓存。
- 使用 `TaczLuaAnimationRuntime` 运行 `assets/<ns>/scripts`。Lua 环境只装 Base/Package/Bit32/Table/String/Math 和编译器，不装
  IO、OS、Coroutine、Luajava；按 `<namespace>_<path>` 预载模块以兼容枪包 `require`。
- 状态机上下文的方法名属于外部格式 ABI；修改 `getAmmoCount`、`runAnimation`、轨道、移动/瞄准等 Java
  可见方法前，先统计真实枪包脚本调用。三类离散轨道要维持覆盖与 additive blending 的更新顺序。
- `TaczBedrockParser` 同时读取枪模和 PlayerAnimator JSON，保留 Catmull-Rom 插值与 `sound_effects`。
  `TaczHumanoidAnimationMixin` 只在持有 TaCZ 枪时覆盖人物骨骼；无人物动画文件时使用简单原版持枪姿势。
- 原版 SoundManager 不认识 `tacz_sounds`。在本地 `resourcepacks/zinecraft_tacz_bridge` 生成可重建资源包：原样复制语言和
  OGG，生成
  `sounds.json`，通过资源包仓库启用后再 reload。必须扫描全部 `tacz_sounds/*.ogg`，不能只复制 display 顶层
  cue，因为动画关键帧会引用分段换弹音效。所有写入必须验证目标仍位于固定桥接目录。
- 模型/纹理解析失败只记录带虚拟路径的警告，不能让一个坏枪包阻止客户端启动。

## 验证

1. 用未提交、位于 `run/tacz` 的真实枪包启动客户端；日志应报告包、枪与弹药数量，并启用 `file/zinecraft_tacz_bridge`。
2. 确认数据生成产生 `models/item/tacz_gun.json` 与 `tacz_ammunition.json`，父模型是 `minecraft:builtin/entity`。
3. 日志中的模型、枪械动画、人物动画、Lua 状态机 failure 均应为 0；桥接 OGG 数应等于虚拟资源视图中的 OGG 数。
4. 在创造栏检查步枪、手枪、霰弹枪和手动枪机武器；实测射击、R 换弹、右键瞄准、B 模式、X 检视、V 近战及第一/第三人称动作。
5. 专用服务器与客户端使用相同枪包，确认仅服务端结算命中与伤害。
6. 先单独 `runDatagen`，再 `build`；`run/` 与生成的桥接包必须保持 Git 忽略。

## 授权

先核对枪包元数据和上游许可证。官方 TaCZ 代码是 GPL-3.0，内置资产是 CC BY-NC-ND 4.0；本项目仅保留独立格式解析代码。不要复制上游
GPL 实现，也不要提交、转换或二次发布 ND 资产。交付报告说明外部资产未进入 jar，并链接上游来源。
