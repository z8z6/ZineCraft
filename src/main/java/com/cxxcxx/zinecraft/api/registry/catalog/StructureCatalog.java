package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.datagen.RegistryDataContributor;
import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.world.structure.ConcentricRingBounds;
import com.cxxcxx.zinecraft.api.world.structure.FixedOriginStructurePlacement;
import com.cxxcxx.zinecraft.api.world.structure.JigsawPoolDefinition;
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
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.*;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

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
  private final List<JigsawBuilder> mutableBuildings = new ArrayList<>();
  public final List<JigsawBuilder> buildings = Collections.unmodifiableList(mutableBuildings);
  private final List<Consumer<BootstrapContext<Structure>>> structureGenerators = new ArrayList<>();
  private final List<Consumer<BootstrapContext<StructureSet>>> structureSetGenerators = new ArrayList<>();

  public StructureCatalog(String namespace, TranslationCatalog translations) {
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.translations = Objects.requireNonNull(translations, "translations");
    this.structurePlacements = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PLACEMENT.key(), namespace);
    FixedOriginStructurePlacement.ACCESS.bind(registerPlacement(
        "fixed_origin", FixedOriginStructurePlacement.ACCESS.getCODEC()
    ));
  }

  /**
   * @param modBus 模组事件总线
   */
  public void register(IEventBus modBus) {
    structurePlacements.register(modBus);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private <S extends StructurePlacement> Supplier<StructurePlacementType<S>> registerPlacement(
      String path,
      MapCodec<S> codec
  ) {
    StructurePlacementType<S> type = () -> codec;
    return (Supplier) structurePlacements.register(path, () -> type);
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
   * 返回可供指定城市或地区生成的结构；未声明 {@code city} 的通用结构会自动包含在内。
   */
  public List<JigsawBuilder> structuresFor(TerraPlace city) {
    Objects.requireNonNull(city, "待查询城市或地区不能为空");
    return buildings.stream().filter(structure -> structure.isAvailableIn(city)).toList();
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
      String enUs,
      String template,
      int maxDistanceFromCenter
  ) {
    return jigsaw(path, zhCn)
        .enUs(enUs)
        .embedded()
        .layout(1, maxDistanceFromCenter)
        .pool("start", pool -> pool.template(template))
        .build();
  }

  /**
   * 注册归属于指定泰拉城市或地区的内嵌建筑。
   */
  public JigsawBuilder embeddedBuilding(
      String path,
      String zhCn,
      String enUs,
      String template,
      int maxDistanceFromCenter,
      TerraPlace place
  ) {
    return jigsaw(path, zhCn)
        .enUs(enUs)
        .city(place)
        .embedded()
        .layout(1, maxDistanceFromCenter)
        .pool("start", pool -> pool.template(template))
        .build();
  }

  /**
   * 注册在偏好群系附近按同心环放置一次的地标。
   *
   * @param path                  结构注册路径
   * @param zhCn                  结构中文名
   * @param template              起始结构模板路径
   * @param biome                 放置搜索使用的偏好群系
   * @param ringDistance          同心环距离参数（区块）
   * @param maxDistanceFromCenter Jigsaw 距起点的最大方块距离
   * @param heightmap             起始高度图；为 {@code null} 时采用固定高度
   * @param startHeight           起始高度或相对高度图偏移
   * @param removeVinesChance     模板中藤蔓被移除的概率
   * @return 已登记的结构构建器
   */
  public JigsawBuilder uniqueLandmark(
      String path, String zhCn, String template, ResourceKey<Biome> biome,
      int ringDistance, int maxDistanceFromCenter, @Nullable Types heightmap,
      int startHeight, float removeVinesChance
  ) {
    return jigsaw(path, zhCn)
        .unique(ringDistance)
        .biome(biome)
        .layout(1, maxDistanceFromCenter)
        .height(heightmap, startHeight)
        .removeVinesChance(removeVinesChance)
        .pool("start", pool -> pool.template(template))
        .build();
  }

  /**
   * 注册固定在原点区块、使用绝对地下高度的地标。
   *
   * @param path                  结构注册路径
   * @param zhCn                  结构中文名
   * @param template              起始结构模板路径
   * @param biome                 结构允许生成的群系
   * @param startHeight           绝对起始高度
   * @param maxDistanceFromCenter Jigsaw 距起点的最大方块距离
   * @return 已登记的结构构建器
   */
  public JigsawBuilder fixedOriginUndergroundLandmark(
      String path, String zhCn, String template, ResourceKey<Biome> biome,
      int startHeight, int maxDistanceFromCenter
  ) {
    return jigsaw(path, zhCn)
        .fixedOrigin()
        .biome(biome)
        .layout(1, maxDistanceFromCenter)
        .height(null, startHeight)
        .generation(Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.ENCAPSULATE)
        .pool("start", pool -> pool.template(template))
        .build();
  }

  /**
   * 注册由中心、道路和功能建筑池组成的可重复聚落。
   *
   * @param path                  聚落注册路径
   * @param zhCn                  聚落中文名
   * @param templateRoot          所有聚落模板的根路径
   * @param biome                 聚落限定群系
   * @param salt                  随机放置盐值
   * @param buildingTemplates     功能建筑模板名到权重的映射
   * @param spacing               平均区块间距
   * @param separation            最小区块间距
   * @param size                  Jigsaw 展开深度
   * @param maxDistanceFromCenter 距中心最大方块距离
   * @param heightmap             起始高度图；为 {@code null} 时采用固定高度
   * @param startHeight           起始高度或相对高度图偏移
   * @param removeVinesChance     模板中藤蔓被移除的概率
   * @return 已登记的聚落构建器
   */
  public JigsawBuilder settlement(
      String path, String zhCn, String templateRoot, ResourceKey<Biome> biome, int salt,
      Map<String, Integer> buildingTemplates, int spacing, int separation, int size,
      int maxDistanceFromCenter, @Nullable Types heightmap, int startHeight, float removeVinesChance
  ) {
    return settlementBuilder(
        path, zhCn, templateRoot, biome, salt, buildingTemplates, size,
        maxDistanceFromCenter, heightmap, startHeight, removeVinesChance
    ).randomSpread(spacing, separation, salt).build();
  }

  /**
   * 注册固定在原点区块的聚落。
   *
   * @param path                  聚落注册路径
   * @param zhCn                  聚落中文名
   * @param templateRoot          所有聚落模板的根路径
   * @param biome                 聚落限定群系
   * @param salt                  保留的结构放置盐值
   * @param buildingTemplates     功能建筑模板名到权重的映射
   * @param size                  Jigsaw 展开深度
   * @param maxDistanceFromCenter 距中心最大方块距离
   * @param heightmap             起始高度图；为 {@code null} 时采用固定高度
   * @param startHeight           起始高度或相对高度图偏移
   * @param removeVinesChance     模板中藤蔓被移除的概率
   * @return 已登记的聚落构建器
   */
  public JigsawBuilder fixedOriginSettlement(
      String path, String zhCn, String templateRoot, ResourceKey<Biome> biome, int salt,
      Map<String, Integer> buildingTemplates, int size, int maxDistanceFromCenter,
      @Nullable Types heightmap, int startHeight, float removeVinesChance
  ) {
    return settlementBuilder(
        path, zhCn, templateRoot, biome, salt, buildingTemplates, size,
        maxDistanceFromCenter, heightmap, startHeight, removeVinesChance
    ).fixedOrigin().build();
  }

  private JigsawBuilder settlementBuilder(
      String path, String zhCn, String templateRoot, ResourceKey<Biome> biome, int salt,
      Map<String, Integer> buildingTemplates, int size, int maxDistanceFromCenter,
      @Nullable Types heightmap, int startHeight, float removeVinesChance
  ) {
    if (buildingTemplates.size() < 4) throw new IllegalArgumentException("大型聚落至少需要四种功能建筑模板：" + path);
    return jigsaw(path, zhCn)
        .biome(biome)
        .layout(size, maxDistanceFromCenter)
        .height(heightmap, startHeight)
        .expansionHack(true)
        .removeVinesChance(removeVinesChance)
        .generation(Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN)
        .startPool("center")
        .pool("center", pool -> pool.template(templateRoot + "/center"))
        .pool("streets", Projection.TERRAIN_MATCHING, pool -> pool
            .template(templateRoot + "/street_straight", 5)
            .template(templateRoot + "/street_corner", 3)
            .template(templateRoot + "/street_cross", 2)
            .template(templateRoot + "/street_end", 2))
        .pool("buildings", pool -> buildingTemplates.forEach(
            (template, weight) -> pool.template(templateRoot + "/" + template, weight)
        ));
  }

  /**
   * 使用完整参数和模板池回调注册通用 Jigsaw 结构。
   *
   * @param path                  结构注册路径
   * @param zhCn                  结构中文名
   * @param enUs                  结构英文名
   * @param spacing               随机散布平均区块间距
   * @param separation            随机散布最小区块间距
   * @param salt                  随机放置盐值
   * @param size                  Jigsaw 展开深度
   * @param maxDistanceFromCenter 距中心最大方块距离
   * @param removeVinesChance     模板中藤蔓被移除的概率
   * @param biome                 限定群系；为 {@code null} 时使用主世界群系标签
   * @param unique                是否使用同心环唯一放置
   * @param ringDistance          唯一放置的同心环距离
   * @param heightmap             起始高度图；为 {@code null} 时采用固定高度
   * @param startHeight           起始高度或相对高度图偏移
   * @param useExpansionHack      是否启用原版 Jigsaw 扩展修正
   * @param fixedOrigin           是否固定在原点区块
   * @param generationStep        结构参与的群系生成阶段
   * @param terrainAdjustment     结构地形适配方式
   * @param configure             模板池配置回调
   * @return 已登记的结构构建器
   */
  public JigsawBuilder jigsawBuilding(
      String path, String zhCn, String enUs,
      int spacing, int separation, int salt, int size, int maxDistanceFromCenter,
      float removeVinesChance, @Nullable ResourceKey<Biome> biome, boolean unique,
      int ringDistance, @Nullable Types heightmap, int startHeight,
      boolean useExpansionHack, boolean fixedOrigin, Decoration generationStep,
      TerrainAdjustment terrainAdjustment, Consumer<? super JigsawBuilder> configure
  ) {
    JigsawBuilder builder = jigsaw(path, zhCn)
        .enUs(enUs)
        .randomSpread(spacing, separation, salt)
        .layout(size, maxDistanceFromCenter)
        .removeVinesChance(removeVinesChance)
        .biome(biome)
        .height(heightmap, startHeight)
        .expansionHack(useExpansionHack)
        .generation(generationStep, terrainAdjustment);
    if (unique) builder.unique(ringDistance);
    if (fixedOrigin) builder.fixedOrigin();
    configure.accept(builder);
    return builder.build();
  }

  /**
   * 注册搜索半径受约束、允许跨多个群系展开的唯一地标。
   *
   * @param path                  结构注册路径
   * @param zhCn                  结构中文名
   * @param ringDistance          同心环距离参数（区块）
   * @param salt                  保留的随机盐值
   * @param size                  Jigsaw 展开深度
   * @param maxDistanceFromCenter 距中心最大方块距离
   * @param removeVinesChance     模板中藤蔓被移除的概率
   * @param preferredBiome        同心环选址偏好群系
   * @param allowedBiomes         结构本体允许生成和展开的群系
   * @param heightmap             起始高度图；为 {@code null} 时采用固定高度
   * @param startHeight           起始高度或相对高度图偏移
   * @param generationStep        结构参与的群系生成阶段
   * @param terrainAdjustment     结构地形适配方式
   * @param configure             模板池配置回调
   * @return 已登记的唯一地标构建器
   */
  public JigsawBuilder guaranteedLandmark(
      String path, String zhCn, int ringDistance, int salt, int size,
      int maxDistanceFromCenter, float removeVinesChance,
      ResourceKey<Biome> preferredBiome, List<ResourceKey<Biome>> allowedBiomes,
      @Nullable Types heightmap, int startHeight, Decoration generationStep,
      TerrainAdjustment terrainAdjustment, Consumer<? super JigsawBuilder> configure
  ) {
    int maximumRadius = ConcentricRingBounds.maximumRadiusBlocks(ringDistance);
    if (maximumRadius > ConcentricRingBounds.GUARANTEED_LANDMARK_RADIUS_BLOCKS) {
      throw new IllegalArgumentException("保证生成的地标超出半径上界：" + path + " (" + maximumRadius + ")");
    }
    JigsawBuilder builder = jigsaw(path, zhCn)
        .unique(ringDistance)
        .biome(preferredBiome)
        .allowedBiomes(allowedBiomes)
        .layout(size, maxDistanceFromCenter)
        .removeVinesChance(removeVinesChance)
        .height(heightmap, startHeight)
        .generation(generationStep, terrainAdjustment);
    configure.accept(builder);
    return builder.build();
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
