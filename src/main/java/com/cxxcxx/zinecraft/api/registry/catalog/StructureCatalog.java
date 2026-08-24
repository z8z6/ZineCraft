package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.datagen.RegistryDataContributor;
import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.world.structure.FixedOriginStructurePlacement;
import com.cxxcxx.zinecraft.api.world.structure.JigsawPoolDefinition;
import com.cxxcxx.zinecraft.api.world.structure.MobilePlotStructure;
import com.cxxcxx.zinecraft.api.world.structure.MobilePlotStructurePlacement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.placement.*;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 结构注册目录，统一校验 Jigsaw Builder 并生成处理器、模板池、结构与结构集。
 */
public final class StructureCatalog implements RegistryDataContributor {
  private final String namespace;
  private final TranslationCatalog translations;
  private final DeferredRegister<StructurePlacementType<?>> structurePlacements;
  private final DeferredRegister<StructureType<?>> structureTypes;
  private final List<JigsawBuilder> mutableBuildings = new ArrayList<>();
  public final List<JigsawBuilder> buildings = Collections.unmodifiableList(mutableBuildings);
  private final List<Consumer<BootstrapContext<Structure>>> structureGenerators = new ArrayList<>();
  private final List<Consumer<BootstrapContext<StructureSet>>> structureSetGenerators = new ArrayList<>();
  private boolean mobilePlotsEnabled;

  public StructureCatalog(String namespace, TranslationCatalog translations) {
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.translations = Objects.requireNonNull(translations, "translations");
    this.structurePlacements = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PLACEMENT.key(), namespace);
    this.structureTypes = DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE.key(), namespace);
    FixedOriginStructurePlacement.ACCESS.bind(registerPlacement(
        "fixed_origin", FixedOriginStructurePlacement.ACCESS.getCODEC()
    ));
    MobilePlotStructurePlacement.ACCESS.bind(registerPlacement(
        "mobile_plot", MobilePlotStructurePlacement.ACCESS.getCODEC()
    ));
    MobilePlotStructure.ACCESS.bind(registerStructureType(
        "mobile_plot", MobilePlotStructure.ACCESS.getCODEC()
    ));
  }

  /**
   * @param modBus 模组事件总线
   */
  public void register(IEventBus modBus) {
    structurePlacements.register(modBus);
    structureTypes.register(modBus);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private <S extends StructurePlacement> Supplier<StructurePlacementType<S>> registerPlacement(
      String path,
      MapCodec<S> codec
  ) {
    StructurePlacementType<S> type = () -> codec;
    return (Supplier) structurePlacements.register(path, () -> type);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private <S extends Structure> Supplier<StructureType<S>> registerStructureType(
      String path,
      MapCodec<S> codec
  ) {
    StructureType<S> type = () -> codec;
    return (Supplier) structureTypes.register(path, () -> type);
  }

  /**
   * 校验并登记一个完整的 Jigsaw 结构声明。
   *
   * @param builder 隶属于当前目录且已完成配置的结构构建器
   * @return 已绑定处理器、模板池、结构和结构集资源键的构建器
   */
  public JigsawBuilder register(JigsawBuilder builder) {
    Objects.requireNonNull(builder, "Jigsaw builder 不能为空");
    if (!builder.belongsTo(this)) throw new IllegalArgumentException("Jigsaw builder 不属于当前目录：" + builder.path);
    if (!ResourceLocation.isValidPath(builder.path))
      throw new IllegalArgumentException("结构 ID 路径无效：" + builder.path);
    if (builder.zhCn.isBlank() || builder.enUs.isBlank())
      throw new IllegalArgumentException("结构名称不能为空：" + builder.path);
    if (builder.pools.isEmpty()) throw new IllegalArgumentException("Jigsaw 建筑至少需要一个模板池：" + builder.path);
    if (builder.pools.stream().noneMatch(pool -> pool.name().equals(builder.startPool()))) {
      throw new IllegalArgumentException("找不到起始模板池：" + builder.path + "/" + builder.startPool());
    }
    if (builder.spacing() <= builder.separation())
      throw new IllegalArgumentException("spacing 必须大于 separation：" + builder.path);
    if (builder.size() < 0 || builder.size() > 20)
      throw new IllegalArgumentException("Jigsaw 展开深度必须在 0 到 20 之间：" + builder.path);
    if (builder.maxDistanceFromCenter() < 1 || builder.maxDistanceFromCenter() > 112) {
      throw new IllegalArgumentException("结构中心最大距离必须在 1 到 112 之间：" + builder.path);
    }
    if (builder.removeVinesChance() < 0 || builder.removeVinesChance() > 1) {
      throw new IllegalArgumentException("藤蔓移除概率必须在 0 到 1 之间：" + builder.path);
    }
    if (builder.cityBuilding()
        && (builder.footprintChunksX() <= 0 || builder.footprintChunksZ() <= 0)) {
      throw new IllegalArgumentException("城市建筑必须声明 X/Z Chunk 占地：" + builder.path);
    }
    if (builder.unique() && (builder.ringDistance() <= 0 || builder.biome() == null)) {
      throw new IllegalArgumentException("唯一地标必须声明正环距离和偏好群系：" + builder.path);
    }
    if (!builder.allowedBiomes().isEmpty() && builder.biome() != null
        && !builder.allowedBiomes().contains(builder.biome())) {
      throw new IllegalArgumentException("允许群系必须包含偏好群系：" + builder.path);
    }
    if (mutableBuildings.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("结构 ID 重复：" + builder.path);
    }

    Map<String, ResourceKey<StructureTemplatePool>> poolKeys = new LinkedHashMap<>();
    for (JigsawPoolDefinition pool : builder.pools) {
      poolKeys.put(pool.name(), key(Registries.TEMPLATE_POOL, builder.path + "/" + pool.name()));
    }
    builder.bind(
        key(Registries.PROCESSOR_LIST, builder.path + "_processors"),
        poolKeys,
        key(Registries.STRUCTURE, builder.path),
        key(Registries.STRUCTURE_SET, builder.path)
    );
    mutableBuildings.add(builder);
    translations.add("structure." + namespace + "." + builder.path, builder.zhCn, builder.enUs);
    return builder;
  }

  /**
   * @param path 结构注册路径 @param zhCn 结构中文名 @return 尚未登记的通用 Jigsaw Builder
   */
  public JigsawBuilder jigsaw(String path, String zhCn) {
    return new JigsawBuilder(this, path, zhCn);
  }

  /**
   * 注册仅含一个起始模板的可重复建筑。
   *
   * @param path                  结构注册路径
   * @param zhCn                  结构中文名
   * @param template              结构模板路径
   * @param spacing               随机散布平均区块间距
   * @param separation            随机散布最小区块间距
   * @param salt                  结构放置随机盐值
   * @param maxDistanceFromCenter Jigsaw 距起点的最大方块距离
   * @param removeVinesChance     模板中藤蔓被移除的概率
   * @return 已登记的结构构建器
   */
  public JigsawBuilder simpleBuilding(
      String path, String zhCn, String template,
      int spacing, int separation, int salt, int maxDistanceFromCenter, float removeVinesChance
  ) {
    return jigsaw(path, zhCn)
        .randomSpread(spacing, separation, salt)
        .layout(1, maxDistanceFromCenter)
        .removeVinesChance(removeVinesChance)
        .pool("start", pool -> pool.template(template))
        .build();
  }

  /**
   * 注册供城市内部引用的独立结构，不为它创建自然生成结构集。
   */
  public JigsawBuilder embeddedBuilding(
      String path,
      String zhCn,
      int footprintChunksX,
      int footprintChunksZ,
      int maxDistanceFromCenter
  ) {
    return jigsaw(path, zhCn)
        .embedded()
        .footprint(footprintChunksX, footprintChunksZ)
        .layout(1, maxDistanceFromCenter)
        .pool("start", pool -> pool.template(path))
        .build();
  }

  /** 注册供程序化城市消费者引用、但不参与 Region 建筑候选分配的基础设施结构。 */
  public JigsawBuilder embeddedInfrastructure(
      String path,
      String zhCn,
      int maxDistanceFromCenter
  ) {
    return jigsaw(path, zhCn)
        .embedded()
        .infrastructure()
        .footprint(1, 1)
        .layout(1, maxDistanceFromCenter)
        .pool("start", pool -> pool.template(path))
        .build();
  }

  /**
   * 注册移动地块运行时结构：动力、支持、生活三层在矩形内逐区块铺设，Region 的道路与建筑在顶面展开。
   */
  public void enableMobilePlots(
      List<JigsawBuilder> layerTiles,
      JigsawBuilder stairTile,
      List<JigsawBuilder> roadTiles,
      Collection<ResourceKey<Biome>> allowedBiomes
  ) {
    List<JigsawBuilder> declaredLayers = List.copyOf(Objects.requireNonNull(
        layerTiles, "移动地块分层构件不能为空"
    ));
    List<JigsawBuilder> declaredRoadTiles = List.copyOf(Objects.requireNonNull(
        roadTiles, "移动地块道路构件不能为空"
    ));
    Objects.requireNonNull(stairTile, "移动地块楼梯构件不能为空");
    List<ResourceKey<Biome>> mobilePlotBiomes = List.copyOf(
        Objects.requireNonNull(allowedBiomes, "移动地块允许群系不能为空")
    );
    if (declaredLayers.size() != 3 || declaredLayers.stream().anyMatch(layer -> !layer.belongsTo(this))) {
      throw new IllegalArgumentException("移动地块必须声明属于当前目录的三层构件");
    }
    if (!stairTile.belongsTo(this)) throw new IllegalArgumentException("移动地块楼梯必须属于当前目录");
    if (declaredRoadTiles.size() != 6 || declaredRoadTiles.stream().anyMatch(tile -> !tile.belongsTo(this))) {
      throw new IllegalArgumentException("移动地块必须声明属于当前目录的六类道路构件");
    }
    if (mobilePlotBiomes.isEmpty()) throw new IllegalArgumentException("移动地块至少需要一个允许群系");
    if (mobilePlotsEnabled) return;
    mobilePlotsEnabled = true;
    ResourceKey<Structure> structureKey = key(Registries.STRUCTURE, "mobile_plot");
    ResourceKey<StructureSet> setKey = key(Registries.STRUCTURE_SET, "mobile_plot");

    structures(context -> {
      HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
      HolderGetter<StructureTemplatePool> pools = context.lookup(Registries.TEMPLATE_POOL);
      List<MobilePlotStructure.BuildingDefinition> candidates = buildings.stream()
          .filter(JigsawBuilder::cityBuilding)
          .map(building -> new MobilePlotStructure.BuildingDefinition(
              building.path,
              pools.getOrThrow(requiredPoolKey(building, building.startPool())),
              building.size(),
              building.useExpansionHack(),
              building.maxDistanceFromCenter(),
              building.footprintChunksX(),
              building.footprintChunksZ()
          ))
          .toList();
      context.register(structureKey, new MobilePlotStructure(
          new StructureSettings(
              HolderSet.direct(mobilePlotBiomes.stream().map(biomes::getOrThrow).toList()),
              Map.of(),
              net.minecraft.world.level.levelgen.GenerationStep.Decoration.SURFACE_STRUCTURES,
              net.minecraft.world.level.levelgen.structure.TerrainAdjustment.NONE
          ),
          java.util.stream.IntStream.range(0, declaredLayers.size()).mapToObj(index -> {
            JigsawBuilder layer = declaredLayers.get(index);
            String id = List.of("power", "support", "life").get(index);
            return new MobilePlotStructure.LayerDefinition(
                id, pools.getOrThrow(requiredPoolKey(layer, layer.startPool())), index * 16
            );
          }).toList(),
          new MobilePlotStructure.LayerDefinition(
              "stair", pools.getOrThrow(requiredPoolKey(stairTile, stairTile.startPool())), 0
          ),
          declaredRoadTiles.stream().map(tile -> new MobilePlotStructure.RoadDefinition(
              tile.path.substring(tile.path.lastIndexOf('/') + 1),
              pools.getOrThrow(requiredPoolKey(tile, tile.startPool()))
          )).toList(),
          candidates
      ));
    });
    structureSets(context -> context.register(
        setKey,
        new StructureSet(
            context.lookup(Registries.STRUCTURE).getOrThrow(structureKey),
            MobilePlotStructurePlacement.ACCESS.create()
        )
    ));
  }

  /**
   * @param generate 自定义结构动态注册回调
   */
  public void structures(Consumer<? super BootstrapContext<Structure>> generate) {
    structureGenerators.add(context -> generate.accept(context));
  }

  /**
   * @param generate 自定义结构集动态注册回调
   */
  public void structureSets(Consumer<? super BootstrapContext<StructureSet>> generate) {
    structureSetGenerators.add(context -> generate.accept(context));
  }

  /**
   * 将处理器、模板池、结构和结构集接入数据生成。
   *
   * @param registryBuilder 动态注册表数据生成构建器
   */
  @Override
  public void contribute(RegistrySetBuilder registryBuilder) {
    registryBuilder.add(Registries.PROCESSOR_LIST, this::bootstrapProcessors);
    registryBuilder.add(Registries.TEMPLATE_POOL, this::bootstrapPools);
    registryBuilder.add(Registries.STRUCTURE, this::bootstrapStructures);
    registryBuilder.add(Registries.STRUCTURE_SET, this::bootstrapSets);
  }

  /**
   * @param context 结构处理器列表动态注册上下文
   */
  public void bootstrapProcessors(BootstrapContext<StructureProcessorList> context) {
    for (JigsawBuilder building : buildings) {
      List<net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor> processors =
          building.removeVinesChance() > 0
              ? List.of(new RuleProcessor(List.of(new ProcessorRule(
              new RandomBlockMatchTest(Blocks.VINE, building.removeVinesChance()),
              AlwaysTrueTest.INSTANCE,
              Blocks.AIR.defaultBlockState()
          ))))
              : List.of();
      context.register(building.processorKey(), new StructureProcessorList(processors));
    }
  }

  /**
   * @param context Jigsaw 模板池动态注册上下文
   */
  public void bootstrapPools(BootstrapContext<StructureTemplatePool> context) {
    Holder<StructureTemplatePool> empty = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);
    HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
    for (JigsawBuilder building : buildings) {
      for (JigsawPoolDefinition pool : building.pools) {
        List<Pair<Function<Projection, ? extends StructurePoolElement>, Integer>> elements = pool.templates().stream()
            .map(template -> Pair.<Function<Projection, ? extends StructurePoolElement>, Integer>of(
                StructurePoolElement.single(id(template.template()).toString(), processors.getOrThrow(building.processorKey())),
                template.weight()
            ))
            .toList();
        context.register(
            requiredPoolKey(building, pool.name()),
            new StructureTemplatePool(empty, elements, pool.projection())
        );
      }
    }
  }

  /**
   * @param context 结构动态注册上下文
   */
  public void bootstrapStructures(BootstrapContext<Structure> context) {
    HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
    HolderGetter<StructureTemplatePool> pools = context.lookup(Registries.TEMPLATE_POOL);
    for (JigsawBuilder building : buildings) {
      HolderSet<Biome> allowed = allowedBiomes(building, biomes);
      context.register(building.structureKey(), new JigsawStructure(
          new StructureSettings(allowed, Map.of(), building.generationStep(), building.terrainAdjustment()),
          pools.getOrThrow(requiredPoolKey(building, building.startPool())),
          Optional.empty(),
          building.size(),
          ConstantHeight.of(VerticalAnchor.absolute(building.startHeight())),
          building.useExpansionHack(),
          Optional.ofNullable(building.heightmap()),
          building.maxDistanceFromCenter(),
          List.of(),
          DimensionPadding.ZERO,
          LiquidSettings.IGNORE_WATERLOGGING
      ));
    }
    structureGenerators.forEach(generate -> generate.accept(context));
  }

  /**
   * @param context 结构集动态注册上下文
   */
  public void bootstrapSets(BootstrapContext<StructureSet> context) {
    HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
    HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
    for (JigsawBuilder building : buildings) {
      if (!building.naturalPlacement()) continue;
      StructurePlacement placement;
      if (building.fixedOriginPlacement()) {
        placement = FixedOriginStructurePlacement.ACCESS.create(
            building.fixedChunkX(), building.fixedChunkZ()
        );
      } else if (building.unique()) {
        placement = new ConcentricRingsStructurePlacement(
            building.ringDistance(), 1, 1, HolderSet.direct(biomes.getOrThrow(building.biome()))
        );
      } else {
        placement = new RandomSpreadStructurePlacement(
            building.spacing(), building.separation(), RandomSpreadType.LINEAR, building.salt()
        );
      }
      context.register(
          building.setKey(),
          new StructureSet(structures.getOrThrow(building.structureKey()), placement)
      );
    }
    structureSetGenerators.forEach(generate -> generate.accept(context));
  }

  private HolderSet<Biome> allowedBiomes(JigsawBuilder building, HolderGetter<Biome> biomes) {
    if (!building.allowedBiomes().isEmpty()) {
      return HolderSet.direct(building.allowedBiomes().stream().map(biomes::getOrThrow).toList());
    }
    if (building.biome() != null) return HolderSet.direct(biomes.getOrThrow(building.biome()));
    return biomes.getOrThrow(BiomeTags.IS_OVERWORLD);
  }

  private ResourceKey<StructureTemplatePool> requiredPoolKey(JigsawBuilder building, String name) {
    ResourceKey<StructureTemplatePool> key = building.poolKeys().get(name);
    if (key == null) throw new IllegalStateException("找不到模板池资源键：" + building.path + "/" + name);
    return key;
  }

  private ResourceLocation id(String path) {
    return ResourceLocation.fromNamespaceAndPath(namespace, path);
  }

  private <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
    return ResourceKey.create(registry, id(path));
  }
}
