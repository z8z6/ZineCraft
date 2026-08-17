package com.cxxcxx.zinecraft.api.world.structure;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.HolderSet.Direct;
import net.minecraft.core.HolderSet.ListBacked;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

public final class StructureCatalog {
  @NotNull
  private final ModRegistrar registrar;
  private final TranslationCatalog translations;
  @NotNull
  private final List<JigsawBuildingEntry> buildings;
  @NotNull
  private final List<Consumer<BootstrapContext<Structure>>> structureGenerators;
  @NotNull
  private final List<Consumer<BootstrapContext<StructureSet>>> structureSetGenerators;

  public StructureCatalog(@NotNull ModRegistrar registrar, TranslationCatalog translations) {
    super();
    this.registrar = registrar;
    this.translations = Objects.requireNonNull(translations, "translations");
    this.buildings = new ArrayList<>();
    this.structureGenerators = new ArrayList<>();
    this.structureSetGenerators = new ArrayList<>();
    FixedOriginStructurePlacement.ACCESS.register(this.registrar);
  }

  public static JigsawBuildingEntry simpleBuildingWithDefaults(
      StructureCatalog var0, String var1, String var2, int var3, int var4, int var5, int var6, float var7, int var8, Object var9
  ) {
    if ((var8 & 2) != 0) {
      var2 = var1;
    }

    if ((var8 & 4) != 0) {
      var3 = 32;
    }

    if ((var8 & 8) != 0) {
      var4 = 8;
    }

    if ((var8 & 32) != 0) {
      var6 = 50;
    }

    if ((var8 & 64) != 0) {
      var7 = 0.0F;
    }

    return var0.simpleBuilding(var1, var2, var3, var4, var5, var6, var7);
  }

  public static JigsawBuildingEntry uniqueLandmarkWithDefaults(
      StructureCatalog var0, String var1, String var2, ResourceKey var3, int var4, int var5, Types var6, int var7, float var8, int var9, Object var10
  ) {
    if ((var9 & 2) != 0) {
      var2 = var1;
    }

    if ((var9 & 8) != 0) {
      var4 = 32;
    }

    if ((var9 & 16) != 0) {
      var5 = 80;
    }

    if ((var9 & 32) != 0) {
      var6 = Types.WORLD_SURFACE_WG;
    }

    if ((var9 & 64) != 0) {
      var7 = 0;
    }

    if ((var9 & 128) != 0) {
      var8 = 0.0F;
    }

    return var0.uniqueLandmark(var1, var2, var3, var4, var5, var6, var7, var8);
  }

  public static JigsawBuildingEntry fixedOriginUndergroundLandmarkWithDefaults(
      StructureCatalog var0, String var1, String var2, ResourceKey var3, int var4, int var5, int var6, Object var7
  ) {
    if ((var6 & 2) != 0) {
      var2 = var1;
    }

    if ((var6 & 16) != 0) {
      var5 = 48;
    }

    return var0.fixedOriginUndergroundLandmark(var1, var2, var3, var4, var5);
  }

  public static JigsawBuildingEntry settlementWithDefaults(
      StructureCatalog var0,
      String var1,
      String var2,
      ResourceKey var3,
      int var4,
      Map var5,
      int var6,
      int var7,
      int var8,
      int var9,
      Types var10,
      int var11,
      float var12,
      int var13,
      Object var14
  ) {
    if ((var13 & 32) != 0) {
      var6 = 48;
    }

    if ((var13 & 64) != 0) {
      var7 = 24;
    }

    if ((var13 & 128) != 0) {
      var8 = 7;
    }

    if ((var13 & 256) != 0) {
      var9 = 112;
    }

    if ((var13 & 512) != 0) {
      var10 = Types.WORLD_SURFACE_WG;
    }

    if ((var13 & 1024) != 0) {
      var11 = 0;
    }

    if ((var13 & 2048) != 0) {
      var12 = 0.0F;
    }

    return var0.settlement(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
  }

  public static JigsawBuildingEntry jigsawBuildingWithDefaults(
      StructureCatalog var0,
      String var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      float var7,
      ResourceKey var8,
      boolean var9,
      int var10,
      Types var11,
      int var12,
      boolean var13,
      boolean var14,
      Decoration var15,
      TerrainAdjustment var16,
      Consumer var17,
      int var18,
      Object var19
  ) {
    if ((var18 & 2) != 0) {
      var2 = 32;
    }

    if ((var18 & 4) != 0) {
      var3 = 8;
    }

    if ((var18 & 16) != 0) {
      var5 = 3;
    }

    if ((var18 & 32) != 0) {
      var6 = 80;
    }

    if ((var18 & 64) != 0) {
      var7 = 0.0F;
    }

    if ((var18 & 128) != 0) {
      var8 = null;
    }

    if ((var18 & 256) != 0) {
      var9 = false;
    }

    if ((var18 & 512) != 0) {
      var10 = 32;
    }

    if ((var18 & 1024) != 0) {
      var11 = Types.WORLD_SURFACE_WG;
    }

    if ((var18 & 2048) != 0) {
      var12 = 0;
    }

    if ((var18 & 4096) != 0) {
      var13 = false;
    }

    if ((var18 & 8192) != 0) {
      var14 = false;
    }

    if ((var18 & 16384) != 0) {
      var15 = Decoration.SURFACE_STRUCTURES;
    }

    if ((var18 & 32768) != 0) {
      var16 = TerrainAdjustment.BEARD_THIN;
    }

    return var0.jigsawBuilding(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17);
  }

  private static void simpleBuildingHelper0$0(String _template, JigsawPoolBuilder _this_pool) {
    JigsawPoolBuilder.templateWithDefaults(_this_pool, _template, 0, 2, null);
    return;
  }

  private static void simpleBuildingHelper0(String _template, JigsawBuildingBuilder _this_jigsawBuilding) {
    JigsawBuildingBuilder.poolWithDefaults(_this_jigsawBuilding, "start", null,
        (Consumer<JigsawPoolBuilder>) (pool -> simpleBuildingHelper0$0(_template, pool)), 2, null);
    return;
  }

  private static void uniqueLandmarkHelper1$0(String _template, JigsawPoolBuilder _this_pool) {
    JigsawPoolBuilder.templateWithDefaults(_this_pool, _template, 0, 2, null);
    return;
  }

  private static void uniqueLandmarkHelper1(String _template, JigsawBuildingBuilder _this_jigsawBuilding) {
    JigsawBuildingBuilder.poolWithDefaults(_this_jigsawBuilding, "start", null,
        (Consumer<JigsawPoolBuilder>) (pool -> uniqueLandmarkHelper1$0(_template, pool)), 2, null);
    return;
  }

  private static void fixedOriginUndergroundLandmarkHelper0$0(String _template, JigsawPoolBuilder _this_pool) {
    JigsawPoolBuilder.templateWithDefaults(_this_pool, _template, 0, 2, null);
    return;
  }

  private static void fixedOriginUndergroundLandmarkHelper0(String _template, JigsawBuildingBuilder _this_jigsawBuilding) {
    JigsawBuildingBuilder.poolWithDefaults(_this_jigsawBuilding, "start", null,
        (Consumer<JigsawPoolBuilder>) (pool -> fixedOriginUndergroundLandmarkHelper0$0(_template, pool)), 2, null);
    return;
  }

  private static void settlementHelper1$0(String _templateRoot, JigsawPoolBuilder _this_pool) {
    JigsawPoolBuilder.templateWithDefaults(_this_pool, _templateRoot + "/center", 0, 2, null);
    return;
  }

  private static void settlementHelper1$1(String _templateRoot, JigsawPoolBuilder _this_pool) {
    _this_pool.template(_templateRoot + "/street_straight", 5);
    _this_pool.template(_templateRoot + "/street_corner", 3);
    _this_pool.template(_templateRoot + "/street_cross", 2);
    _this_pool.template(_templateRoot + "/street_end", 2);
    return;
  }

  private static void settlementHelper1$2(Map _buildingTemplates, String _templateRoot, JigsawPoolBuilder _this_pool) {
    Map<String, Integer> map = _buildingTemplates;
    int i = 0;

    for (Entry entry : map.entrySet()) {
      Entry entry1 = entry;
      int j = 0;
      String string = (String) entry1.getKey();
      int k = ((Number) entry1.getValue()).intValue();
      _this_pool.template(_templateRoot + "/" + string, k);
    }

    return;
  }

  private static void settlementHelper1(String _templateRoot, Map _buildingTemplates, JigsawBuildingBuilder _this_jigsawBuilding) {
    _this_jigsawBuilding.setStartPool("center");
    JigsawBuildingBuilder.poolWithDefaults(_this_jigsawBuilding, "center", null,
        (Consumer<JigsawPoolBuilder>) (pool -> settlementHelper1$0(_templateRoot, pool)), 2, null);
    _this_jigsawBuilding.pool("streets", Projection.TERRAIN_MATCHING,
        (Consumer<JigsawPoolBuilder>) (pool -> settlementHelper1$1(_templateRoot, pool)));
    JigsawBuildingBuilder.poolWithDefaults(_this_jigsawBuilding, "buildings", null,
        (Consumer<JigsawPoolBuilder>) (pool -> settlementHelper1$2(_buildingTemplates, _templateRoot, pool)), 2, null);
    return;
  }

  @NotNull
  public final JigsawBuildingEntry simpleBuilding(
      @NotNull String path, @NotNull String template, int spacing, int separation, int salt, int maxDistanceFromCenter, float removeVinesChance
  ) {
    return jigsawBuildingWithDefaults(
        this,
        path,
        spacing,
        separation,
        salt,
        1,
        maxDistanceFromCenter,
        removeVinesChance,
        null,
        false,
        0,
        null,
        0,
        false,
        false,
        null,
        null,
        (Consumer<JigsawBuildingBuilder>) (builder -> simpleBuildingHelper0(template, builder)),
        65408,
        null
    );
  }

  @NotNull
  public final JigsawBuildingEntry uniqueLandmark(
      @NotNull String path,
      @NotNull String template,
      @NotNull ResourceKey<Biome> biome,
      int ringDistance,
      int maxDistanceFromCenter,
      @Nullable Types heightmap,
      int startHeight,
      float removeVinesChance
  ) {
    if (ringDistance <= 0) {
      int i = 0;
      String string = "唯一地标与世界原点的环距离必须大于 0";
      throw new IllegalArgumentException(string.toString());
    } else {
      return jigsawBuildingWithDefaults(
          this,
          path,
          ringDistance + 1,
          ringDistance,
          path.hashCode(),
          1,
          maxDistanceFromCenter,
          removeVinesChance,
          biome,
          true,
          ringDistance,
          heightmap,
          startHeight,
          false,
          false,
          null,
          null,
          (Consumer<JigsawBuildingBuilder>) (builder -> uniqueLandmarkHelper1(template, builder)),
          61440,
          null
      );
    }
  }

  public JigsawBuildingEntry uniqueLandmark(
      String path,
      String zhCn,
      String template,
      ResourceKey<Biome> biome,
      int ringDistance,
      int maxDistanceFromCenter,
      Types heightmap,
      int startHeight,
      float removeVinesChance
  ) {
    registerTranslation(path, zhCn);
    return uniqueLandmark(
        path, template, biome, ringDistance, maxDistanceFromCenter,
        heightmap, startHeight, removeVinesChance
    );
  }

  @NotNull
  public final JigsawBuildingEntry fixedOriginUndergroundLandmark(
      @NotNull String path, @NotNull String template, @NotNull ResourceKey<Biome> biome, int startHeight, int maxDistanceFromCenter
  ) {
    int i = path.hashCode();
    Decoration decoration = Decoration.UNDERGROUND_STRUCTURES;
    TerrainAdjustment terrainAdjustment = TerrainAdjustment.ENCAPSULATE;
    return jigsawBuildingWithDefaults(
        this,
        path,
        2,
        1,
        i,
        1,
        maxDistanceFromCenter,
        0.0F,
        biome,
        false,
        0,
        null,
        startHeight,
        false,
        true,
        decoration,
        terrainAdjustment,
        (Consumer<JigsawBuildingBuilder>) (builder -> fixedOriginUndergroundLandmarkHelper0(template, builder)),
        4928,
        null
    );
  }

  public JigsawBuildingEntry fixedOriginUndergroundLandmark(
      String path,
      String zhCn,
      String template,
      ResourceKey<Biome> biome,
      int startHeight,
      int maxDistanceFromCenter
  ) {
    registerTranslation(path, zhCn);
    return fixedOriginUndergroundLandmark(path, template, biome, startHeight, maxDistanceFromCenter);
  }

  @NotNull
  public final JigsawBuildingEntry settlement(
      @NotNull String path,
      @NotNull String templateRoot,
      @NotNull ResourceKey<Biome> biome,
      int salt,
      @NotNull Map<String, Integer> buildingTemplates,
      int spacing,
      int separation,
      int size,
      int maxDistanceFromCenter,
      @Nullable Types heightmap,
      int startHeight,
      float removeVinesChance
  ) {
    return this.settlement(
        path, templateRoot, biome, salt, buildingTemplates, spacing, separation, size,
        maxDistanceFromCenter, heightmap, startHeight, removeVinesChance, false
    );
  }

  public JigsawBuildingEntry settlement(
      String path,
      String zhCn,
      String templateRoot,
      ResourceKey<Biome> biome,
      int salt,
      Map<String, Integer> buildingTemplates,
      int spacing,
      int separation,
      int size,
      int maxDistanceFromCenter,
      Types heightmap,
      int startHeight,
      float removeVinesChance
  ) {
    registerTranslation(path, zhCn);
    return settlement(
        path, templateRoot, biome, salt, buildingTemplates, spacing, separation,
        size, maxDistanceFromCenter, heightmap, startHeight, removeVinesChance
    );
  }

  /**
   * 注册固定在世界原点地表的完整聚落，用于泰拉中心城市。
   */
  @NotNull
  public final JigsawBuildingEntry fixedOriginSettlement(
      @NotNull String path,
      @NotNull String templateRoot,
      @NotNull ResourceKey<Biome> biome,
      int salt,
      @NotNull Map<String, Integer> buildingTemplates,
      int size,
      int maxDistanceFromCenter,
      @Nullable Types heightmap,
      int startHeight,
      float removeVinesChance
  ) {
    return this.settlement(
        path, templateRoot, biome, salt, buildingTemplates, 2, 1, size,
        maxDistanceFromCenter, heightmap, startHeight, removeVinesChance, true
    );
  }

  public JigsawBuildingEntry fixedOriginSettlement(
      String path,
      String zhCn,
      String templateRoot,
      ResourceKey<Biome> biome,
      int salt,
      Map<String, Integer> buildingTemplates,
      int size,
      int maxDistanceFromCenter,
      Types heightmap,
      int startHeight,
      float removeVinesChance
  ) {
    registerTranslation(path, zhCn);
    return fixedOriginSettlement(
        path, templateRoot, biome, salt, buildingTemplates, size,
        maxDistanceFromCenter, heightmap, startHeight, removeVinesChance
    );
  }

  private JigsawBuildingEntry settlement(
      String path,
      String templateRoot,
      ResourceKey<Biome> biome,
      int salt,
      Map<String, Integer> buildingTemplates,
      int spacing,
      int separation,
      int size,
      int maxDistanceFromCenter,
      Types heightmap,
      int startHeight,
      float removeVinesChance,
      boolean fixedOrigin
  ) {
    if (buildingTemplates.size() < 4) {
      int i = 0;
      String string = "大型聚落至少需要四种功能建筑模板";
      throw new IllegalArgumentException(string.toString());
    } else {
      return this.jigsawBuilding(
          path,
          spacing,
          separation,
          salt,
          size,
          maxDistanceFromCenter,
          removeVinesChance,
          biome,
          false,
          0,
          heightmap,
          startHeight,
          true,
          fixedOrigin,
          Decoration.SURFACE_STRUCTURES,
          TerrainAdjustment.BEARD_THIN,
          (Consumer<JigsawBuildingBuilder>) (builder -> settlementHelper1(templateRoot, buildingTemplates, builder))
      );
    }
  }

  @NotNull
  public final JigsawBuildingEntry jigsawBuilding(
      @NotNull String path,
      int spacing,
      int separation,
      int salt,
      int size,
      int maxDistanceFromCenter,
      float removeVinesChance,
      @Nullable ResourceKey<Biome> biome,
      boolean unique,
      int ringDistance,
      @Nullable Types heightmap,
      int startHeight,
      boolean useExpansionHack,
      boolean fixedOrigin,
      @NotNull Decoration generationStep,
      @NotNull TerrainAdjustment terrainAdjustment,
      @NotNull Consumer<? super JigsawBuildingBuilder> build
  ) {
    return this.jigsawBuilding(
        path, spacing, separation, salt, size, maxDistanceFromCenter, removeVinesChance,
        biome, unique, ringDistance, heightmap, startHeight, useExpansionHack, fixedOrigin,
        generationStep, terrainAdjustment, biome == null ? List.of() : List.of(biome), build
    );
  }

  public JigsawBuildingEntry jigsawBuilding(
      String path,
      String zhCn,
      String enUs,
      int spacing,
      int separation,
      int salt,
      int size,
      int maxDistanceFromCenter,
      float removeVinesChance,
      ResourceKey<Biome> biome,
      boolean unique,
      int ringDistance,
      Types heightmap,
      int startHeight,
      boolean useExpansionHack,
      boolean fixedOrigin,
      Decoration generationStep,
      TerrainAdjustment terrainAdjustment,
      Consumer<? super JigsawBuildingBuilder> build
  ) {
    registerTranslation(path, zhCn, enUs);
    return jigsawBuilding(
        path, spacing, separation, salt, size, maxDistanceFromCenter, removeVinesChance,
        biome, unique, ringDistance, heightmap, startHeight, useExpansionHack, fixedOrigin,
        generationStep, terrainAdjustment, build
    );
  }

  /**
   * Registers a deterministic concentric-ring landmark which prefers its national biome but
   * remains valid in another Zinecraft biome when vanilla's 112-block biome correction cannot
   * find that preference. Without this fallback a one-candidate landmark can silently be absent.
   */
  @NotNull
  public final JigsawBuildingEntry guaranteedLandmark(
      @NotNull String path,
      int ringDistance,
      int salt,
      int size,
      int maxDistanceFromCenter,
      float removeVinesChance,
      @NotNull ResourceKey<Biome> preferredBiome,
      @NotNull List<ResourceKey<Biome>> allowedBiomes,
      @Nullable Types heightmap,
      int startHeight,
      @NotNull Decoration generationStep,
      @NotNull TerrainAdjustment terrainAdjustment,
      @NotNull Consumer<? super JigsawBuildingBuilder> build
  ) {
    int maximumRadius = ConcentricRingBounds.maximumRadiusBlocks(ringDistance);
    if (maximumRadius > ConcentricRingBounds.GUARANTEED_LANDMARK_RADIUS_BLOCKS) {
      throw new IllegalArgumentException(
          "保证生成的地标超出 " + ConcentricRingBounds.GUARANTEED_LANDMARK_RADIUS_BLOCKS + " 格上界: " + path + " (" + maximumRadius + ")"
      );
    }
    if (allowedBiomes.isEmpty() || !allowedBiomes.contains(preferredBiome)) {
      throw new IllegalArgumentException("保证生成的地标允许群系必须包含其本国偏好群系: " + path);
    }
    return this.jigsawBuilding(
        path, ringDistance + 1, ringDistance, salt, size, maxDistanceFromCenter,
        removeVinesChance, preferredBiome, true, ringDistance, heightmap, startHeight,
        false, false, generationStep, terrainAdjustment, allowedBiomes, build
    );
  }

  public JigsawBuildingEntry guaranteedLandmark(
      String path,
      String zhCn,
      int ringDistance,
      int salt,
      int size,
      int maxDistanceFromCenter,
      float removeVinesChance,
      ResourceKey<Biome> preferredBiome,
      List<ResourceKey<Biome>> allowedBiomes,
      Types heightmap,
      int startHeight,
      Decoration generationStep,
      TerrainAdjustment terrainAdjustment,
      Consumer<? super JigsawBuildingBuilder> build
  ) {
    registerTranslation(path, zhCn);
    return guaranteedLandmark(
        path, ringDistance, salt, size, maxDistanceFromCenter, removeVinesChance,
        preferredBiome, allowedBiomes, heightmap, startHeight, generationStep,
        terrainAdjustment, build
    );
  }

  private void registerTranslation(String path, String zhCn) {
    registerTranslation(path, zhCn, TranslationNames.toDisplayName(path));
  }

  private void registerTranslation(String path, String zhCn, String enUs) {
    translations.add(
        "structure." + registrar.namespace + "." + path,
        zhCn,
        enUs
    );
  }

  private JigsawBuildingEntry jigsawBuilding(
      String path,
      int spacing,
      int separation,
      int salt,
      int size,
      int maxDistanceFromCenter,
      float removeVinesChance,
      ResourceKey<Biome> biome,
      boolean unique,
      int ringDistance,
      Types heightmap,
      int startHeight,
      boolean useExpansionHack,
      boolean fixedOrigin,
      Decoration generationStep,
      TerrainAdjustment terrainAdjustment,
      List<ResourceKey<Biome>> allowedBiomes,
      Consumer<? super JigsawBuildingBuilder> build
  ) {
    if (spacing <= separation) {
      int p = 0;
      String string3 = "spacing 必须大于 separation";
      throw new IllegalArgumentException(string3.toString());
    }

    if (0 <= size ? size >= 21 : true) {
      int o = 0;
      String string2 = "Jigsaw 展开深度必须在 0 到 20 之间";
      throw new IllegalArgumentException(string2.toString());
    }

    if (1 <= maxDistanceFromCenter ? maxDistanceFromCenter >= 113 : true) {
      int n = 0;
      String string1 = "结构中心最大距离必须在 1 到 112 之间，以预留地形适配边界";
      throw new IllegalArgumentException(string1.toString());
    }

    if (0.0F <= removeVinesChance ? !(removeVinesChance <= 1.0F) : true) {
      int m = 0;
      String string = "藤蔓移除概率必须在 0 到 1 之间";
      throw new IllegalArgumentException(string.toString());
    }

    JigsawBuildingBuilder poolKeys = new JigsawBuildingBuilder(path);
    build.accept(poolKeys);
    JigsawBuildingDefinition jigsawBuildingDefinition = poolKeys.build();
    Iterable iterable = jigsawBuildingDefinition.getPools();
    int i = 0;
    int j = Math.max(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(iterable, 16), 16);
    Iterable iterable1 = iterable;
    Map map = new LinkedHashMap(j);
    int k = 0;

    for (Object object : iterable1) {
      Map map1 = map;
      JigsawPoolDefinition jigsawPoolDefinition = (JigsawPoolDefinition) object;
      int l = 0;
      String string4 = jigsawPoolDefinition.getName();
      ModRegistrar modRegistrar2 = this.registrar;
      ResourceKey resourceKey2 = Registries.TEMPLATE_POOL;
      Pair pair = Pair.of(string4, modRegistrar2.key(resourceKey2, path + "/" + jigsawPoolDefinition.getName()));
      map1.put(pair.getFirst(), pair.getSecond());
    }

    Map map2 = map;
    ModRegistrar modRegistrar = this.registrar;
    ResourceKey resourceKey3 = Registries.PROCESSOR_LIST;
    ResourceKey resourceKey = modRegistrar.key(resourceKey3, path + "_processors");
    String string5 = jigsawBuildingDefinition.getStartPool();
    ModRegistrar modRegistrar3 = this.registrar;
    ResourceKey resourceKey4 = Registries.STRUCTURE;
    ResourceKey resourceKey1 = modRegistrar3.key(resourceKey4, path);
    ModRegistrar modRegistrar1 = this.registrar;
    ResourceKey resourceKey5 = Registries.STRUCTURE_SET;
    JigsawBuildingEntry jigsawBuildingEntry = new JigsawBuildingEntry(
        resourceKey,
        map2,
        string5,
        resourceKey1,
        modRegistrar1.key(resourceKey5, path),
        jigsawBuildingDefinition.getPools(),
        spacing,
        separation,
        salt,
        size,
        maxDistanceFromCenter,
        removeVinesChance,
        biome,
        allowedBiomes,
        unique,
        ringDistance,
        heightmap,
        startHeight,
        useExpansionHack,
        fixedOrigin,
        generationStep,
        terrainAdjustment
    );
    List list = this.buildings;
    JigsawBuildingEntry jigsawBuildingEntry1 = jigsawBuildingEntry;
    int q = 0;
    list.add(jigsawBuildingEntry1);
    return jigsawBuildingEntry;
  }

  public final void structures(@NotNull Consumer<? super BootstrapContext<Structure>> generate) {
    this.structureGenerators.add(context -> generate.accept(context));
  }

  public final void structureSets(@NotNull Consumer<? super BootstrapContext<StructureSet>> generate) {
    this.structureSetGenerators.add(context -> generate.accept(context));
  }

  public final void bootstrapProcessors(@NotNull BootstrapContext<StructureProcessorList> context) {
    Iterable iterable = this.buildings;
    int i = 0;

    for (Object object : iterable) {
      JigsawBuildingEntry jigsawBuildingEntry = (JigsawBuildingEntry) object;
      int j = 0;
      List list = jigsawBuildingEntry.getRemoveVinesChance() > 0.0F
          ? java.util.List.of(
          new RuleProcessor(
              java.util.List.of(
                  new ProcessorRule(
                      (RuleTest) (new RandomBlockMatchTest(Blocks.VINE, jigsawBuildingEntry.getRemoveVinesChance())),
                      (RuleTest) AlwaysTrueTest.INSTANCE,
                      Blocks.AIR.defaultBlockState()
                  )
              )
          )
      )
          : java.util.List.of();
      this.registrar.dynamic(context, jigsawBuildingEntry.getProcessorKey(), new StructureProcessorList(list));
    }
  }

  public final void bootstrapPools(@NotNull BootstrapContext<StructureTemplatePool> context) {
    Reference reference = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY);
    HolderGetter holderGetter = context.lookup(Registries.PROCESSOR_LIST);
    Iterable iterable = this.buildings;
    int i = 0;

    for (Object object : iterable) {
      JigsawBuildingEntry jigsawBuildingEntry = (JigsawBuildingEntry) object;
      int j = 0;
      Iterable iterable1 = jigsawBuildingEntry.getPools();
      int k = 0;

      for (Object object1 : iterable1) {
        JigsawPoolDefinition jigsawPoolDefinition = (JigsawPoolDefinition) object1;
        int l = 0;
        Iterable iterable2 = jigsawPoolDefinition.getTemplates();
        int m = 0;
        Iterable _this_mapTo_iv_iv = iterable2;
        var collection = new ArrayList(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(iterable2, 10));
        int n = 0;

        for (Object object2 : _this_mapTo_iv_iv) {
          JigsawTemplateElement template = (JigsawTemplateElement) object2;
          Collection collection1 = collection;
          int o = 0;
          collection1.add(
              com.mojang.datafixers.util.Pair.of(
                  StructurePoolElement.single(
                      this.registrar.id(template.getTemplate()).toString(), (Holder) holderGetter.getOrThrow(jigsawBuildingEntry.getProcessorKey())
                  ),
                  template.getWeight()
              )
          );
        }

        List list = (List) collection;
        this.registrar
            .dynamic(
                context,
                (ResourceKey<StructureTemplatePool>) com.cxxcxx.zinecraft.api.util.CollectionSupport.getRequired(jigsawBuildingEntry.getPoolKeys(), jigsawPoolDefinition.getName()),
                new StructureTemplatePool((Holder) reference, list, jigsawPoolDefinition.getProjection())
            );
      }
    }
  }

  public final void bootstrapStructures(@NotNull BootstrapContext<Structure> context) {
    HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
    HolderGetter holderGetter1 = context.lookup(Registries.TEMPLATE_POOL);
    Iterable iterable = this.buildings;
    int i = 0;

    for (Object object : iterable) {
      JigsawBuildingEntry jigsawBuildingEntry;
      ListBacked listBacked1;
      label26:
      {
        jigsawBuildingEntry = (JigsawBuildingEntry) object;
        int j = 0;
        ResourceKey resourceKey1 = jigsawBuildingEntry.getBiome();
        if (!jigsawBuildingEntry.getAllowedBiomes().isEmpty()) {
          List<Holder<Biome>> allowedBiomes = jigsawBuildingEntry.getAllowedBiomes().stream()
              .map(holderGetter::getOrThrow)
              .map(holder -> (Holder<Biome>) holder)
              .toList();
          listBacked1 = (ListBacked) HolderSet.direct(allowedBiomes);
          break label26;
        } else if (resourceKey1 != null) {
          ResourceKey resourceKey = resourceKey1;
          int k = 0;
          Holder[] holders = new Holder[]{holderGetter.getOrThrow(resourceKey)};
          Direct direct = HolderSet.direct(holders);
          if (direct != null) {
            listBacked1 = (ListBacked) direct;
            break label26;
          }
        }

        listBacked1 = (ListBacked) holderGetter.getOrThrow(BiomeTags.IS_OVERWORLD);
      }

      ListBacked listBacked = listBacked1;
      this.registrar
          .dynamic(
              context,
              jigsawBuildingEntry.getStructureKey(),
              new JigsawStructure(
                  new StructureSettings(
                      (HolderSet) listBacked,
                      java.util.Map.of(),
                      jigsawBuildingEntry.getGenerationStep(),
                      jigsawBuildingEntry.getTerrainAdjustment()
                  ),
                  (Holder) holderGetter1.getOrThrow((ResourceKey) com.cxxcxx.zinecraft.api.util.CollectionSupport.getRequired(jigsawBuildingEntry.getPoolKeys(), jigsawBuildingEntry.getStartPool())),
                  Optional.empty(),
                  jigsawBuildingEntry.getSize(),
                  (HeightProvider) ConstantHeight.of(VerticalAnchor.absolute(jigsawBuildingEntry.getStartHeight())),
                  jigsawBuildingEntry.getUseExpansionHack(),
                  Optional.ofNullable(jigsawBuildingEntry.getHeightmap()),
                  jigsawBuildingEntry.getMaxDistanceFromCenter(),
                  java.util.List.of(),
                  DimensionPadding.ZERO,
                  LiquidSettings.IGNORE_WATERLOGGING
              )
          );
    }

    iterable = this.structureGenerators;
    i = 0;

    for (Object object1 : iterable) {
      Consumer function1 = (Consumer) object1;
      int l = 0;
      function1.accept(context);
    }
  }

  public final void bootstrapSets(@NotNull BootstrapContext<StructureSet> context) {
    HolderGetter holderGetter = context.lookup(Registries.STRUCTURE);
    HolderGetter holderGetter1 = context.lookup(Registries.BIOME);
    Iterable iterable = this.buildings;
    int i = 0;

    for (Object object : iterable) {
      JigsawBuildingEntry jigsawBuildingEntry = (JigsawBuildingEntry) object;
      int j = 0;
      StructurePlacement structurePlacement1;
      if (jigsawBuildingEntry.getFixedOrigin()) {
        structurePlacement1 = FixedOriginStructurePlacement.ACCESS.create();
      } else if (jigsawBuildingEntry.getUnique()) {
        ResourceKey resourceKey1 = jigsawBuildingEntry.getBiome();
        if (resourceKey1 == null) {
          int k = 0;
          String string = "唯一地标必须绑定群系: " + jigsawBuildingEntry.getStructureKey().location();
          throw new IllegalArgumentException(string.toString());
        }

        ResourceKey resourceKey = resourceKey1;
        int m = jigsawBuildingEntry.getRingDistance();
        Holder[] holders = new Holder[]{holderGetter1.getOrThrow(resourceKey)};
        structurePlacement1 = (StructurePlacement) (new ConcentricRingsStructurePlacement(m, 1, 1, (HolderSet) HolderSet.direct(holders)));
      } else {
        structurePlacement1 = (StructurePlacement) (
            new RandomSpreadStructurePlacement(
                jigsawBuildingEntry.getSpacing(),
                jigsawBuildingEntry.getSeparation(),
                RandomSpreadType.LINEAR,
                jigsawBuildingEntry.getSalt()
            )
        );
      }

      StructurePlacement structurePlacement = structurePlacement1;
      this.registrar
          .dynamic(
              context,
              jigsawBuildingEntry.getSetKey(),
              new StructureSet((Holder) holderGetter.getOrThrow(jigsawBuildingEntry.getStructureKey()), structurePlacement)
          );
    }

    iterable = this.structureSetGenerators;
    i = 0;

    for (Object object1 : iterable) {
      Consumer function1 = (Consumer) object1;
      int l = 0;
      function1.accept(context);
    }
  }
}
