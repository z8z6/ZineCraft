# 添加结构

结构目录提供三种稳定抽象：可重复聚落、每世界一次的唯一地标和单模板建筑。需要固定坐标时使用固定原点变体；只有目录无法表达特殊序列化时才实现自定义
`Structure`/`StructurePiece`。

## 大型 Jigsaw 聚落

```java
JigsawBuildingEntry camp = Zinecraft.INSTANCE.getSTRUCTURES().settlement(
    "mining_camp",
    "settlements/mining_camp",
    ModBiomes.MINING_BADLANDS,
    41002001,
    CollectionSupport.linkedMapOf(
        Pair.of("bunkhouse", 4),
        Pair.of("ore_workshop", 3),
        Pair.of("freight_depot", 2),
        Pair.of("canteen", 2)),
    36, 16, 9, 112,
    Heightmap.Types.WORLD_SURFACE_WG,
    0, 0.0F
);
```

目录建立 `center`、`streets` 和 `buildings` 池。模板根目录必须提供 `center.nbt`、四种道路模板及至少四种功能建筑。`spacing`
必须大于 `separation`，Jigsaw 深度范围为 0—20，最大中心距离不能超过原版允许范围。

国家普通聚落当前统一使用 `spacing=36`、`separation=16`、Jigsaw 深度 `9` 和最大中心距离 `112`。线性随机散布的最近候选
中心间隔至少 272 格，仍大于最大 224 格结构直径；该配置只影响可重复聚落，不得复用于 `uniqueLandmark` 或
`guaranteedLandmark`。

拉特兰中心聚落使用 `fixedOriginSettlement`，确保地面建筑群和中心群系共同覆盖世界坐标 `(0, 0)`。

## 唯一地标

```java
JigsawBuildingEntry tower = Zinecraft.INSTANCE.getSTRUCTURES().uniqueLandmark(
    "unique_tower", "landmarks/unique_tower",
    ModBiomes.TARGET_BIOME,
    32, 96,
    Heightmap.Types.WORLD_SURFACE_WG,
    0, 0.0F
);
```

唯一地标生成独立模板池、Jigsaw 结构和同心环结构集，并在结构和放置层同时绑定目标群系。地下固定设施使用：

```java
JigsawBuildingEntry host = Zinecraft.INSTANCE.getSTRUCTURES().fixedOriginUndergroundLandmark(
    "laterano_host", "laterano_host/core",
    NationBiomes.INSTANCE.getLATERANO_HOLY_FIELDS(),
    -32, 112
);
```

`zinecraft:fixed_origin` 只接受区块 `(-1, -1)`；33×33 模板覆盖 `-16..16`，几何中心位于 `(0, 0)`。

## 单模板建筑

```java
JigsawBuildingEntry ruins = Zinecraft.INSTANCE.getSTRUCTURES().simpleBuilding(
    "ruins", "ruins/common",
    36, 30, 958853901,
    80, 0.6F
);
```

目录自动派生 processor list、template pool、`JigsawStructure` 和 `StructureSet`。NBT 位于：

```text
src/main/resources/data/zinecraft/structure/<path>.nbt
```

## 验证

```powershell
.\gradlew.bat runData
.\gradlew.bat build
```

在新世界使用 `/place structure zinecraft:<id>` 和 `/locate structure zinecraft:<id>`。检查 Jigsaw 父片段的 `target`
是否等于子片段的 `name`，以及两个连接方块方向是否相对。
