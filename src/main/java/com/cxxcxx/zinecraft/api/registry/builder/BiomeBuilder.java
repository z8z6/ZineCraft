package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.BiomeCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 群系声明构建器，统一保存注册元数据、气候与颜色设置、生成步骤和生物生成规则。
 */
public final class BiomeBuilder {
  public final String path;
  public final String zhCn;
  private final BiomeCatalog catalog;
  public String enUs;
  private Consumer<BiomeBuilder> configure = builder -> {
  };
  @Nullable
  private ResourceKey<Biome> key;
  @Nullable
  private ParameterPoint climate;
  @Nullable
  private MobSpawnSettings.Builder spawns;
  @Nullable
  private BiomeGenerationSettings.Builder generation;
  private boolean precipitation;
  private float temperature;
  private float downfall;
  private int waterColor;
  private int waterFogColor;
  private int fogColor;
  @Nullable
  private Integer skyColor;
  @Nullable
  private Integer grassColor;
  @Nullable
  private Integer foliageColor;
  @Nullable
  private Music music;

  /**
   * 创建使用自动英文名和默认气候参数的群系声明。
   *
   * @param catalog 接收该群系的目录
   * @param path    群系的命名空间内路径
   * @param zhCn    群系的简体中文名称
   */
  public BiomeBuilder(BiomeCatalog catalog, String path, String zhCn) {
    this.catalog = Objects.requireNonNull(catalog, "群系目录不能为空");
    this.path = Objects.requireNonNull(path, "群系 ID 不能为空");
    this.zhCn = Objects.requireNonNull(zhCn, "群系中文名不能为空：" + path);
    this.enUs = TranslationCatalog.toDisplayName(path);
    resetSettings();
  }

  /**
   * 设置群系的英文名称。
   *
   * @param enUs 英文名称
   * @return 当前构建器
   */
  public BiomeBuilder enUs(String enUs) {
    this.enUs = Objects.requireNonNull(enUs, "群系英文名不能为空：" + path);
    return this;
  }

  /**
   * 追加在数据生成 bootstrap 阶段执行的群系配置。
   *
   * @param configure 配置气候、颜色、生物与生成步骤的回调
   * @return 当前构建器
   */
  public BiomeBuilder configure(Consumer<? super BiomeBuilder> configure) {
    Objects.requireNonNull(configure, "群系配置不能为空：" + path);
    Consumer<BiomeBuilder> previous = this.configure;
    this.configure = builder -> {
      previous.accept(builder);
      configure.accept(builder);
    };
    return this;
  }

  /**
   * 设置群系在多噪声群系源中的气候点。
   *
   * @param temperature     气候温度轴
   * @param humidity        气候湿度轴
   * @param continentalness 大陆性轴
   * @param erosion         侵蚀轴
   * @param depth           深度轴
   * @param weirdness       奇异度轴
   * @return 当前构建器
   */
  public BiomeBuilder climate(
      float temperature,
      float humidity,
      float continentalness,
      float erosion,
      float depth,
      float weirdness
  ) {
    float[] coordinates = {temperature, humidity, continentalness, erosion, depth, weirdness};
    for (float coordinate : coordinates) {
      if (!Float.isFinite(coordinate)) {
        throw new IllegalArgumentException("群系气候坐标必须是有限值：" + path);
      }
    }
    this.climate = Climate.parameters(
        temperature, humidity, continentalness, erosion, depth, weirdness, 0.0F
    );
    return this;
  }

  /**
   * 设置是否产生降水。
   *
   * @param precipitation 是否产生降水
   * @return 当前构建器
   */
  public BiomeBuilder precipitation(boolean precipitation) {
    this.precipitation = precipitation;
    return this;
  }

  /**
   * 设置群系基础温度。
   *
   * @param temperature 温度值
   * @return 当前构建器
   */
  public BiomeBuilder temperature(float temperature) {
    if (!Float.isFinite(temperature)) {
      throw new IllegalArgumentException("群系温度必须是有限值：" + path);
    }
    this.temperature = temperature;
    return this;
  }

  /**
   * 设置群系降水量。
   *
   * @param downfall 0 到 1 的降水量
   * @return 当前构建器
   */
  public BiomeBuilder downfall(float downfall) {
    if (!Float.isFinite(downfall) || downfall < 0 || downfall > 1) {
      throw new IllegalArgumentException("群系降水量必须在 0 到 1 之间：" + path);
    }
    this.downfall = downfall;
    return this;
  }

  /**
   * @param color 水体 RGB 颜色值 @return 当前构建器
   */
  public BiomeBuilder waterColor(int color) {
    this.waterColor = validatedColor(color, "水体");
    return this;
  }

  /**
   * @param color 水下雾 RGB 颜色值 @return 当前构建器
   */
  public BiomeBuilder waterFogColor(int color) {
    this.waterFogColor = validatedColor(color, "水下雾");
    return this;
  }

  /**
   * @param color 雾 RGB 颜色值 @return 当前构建器
   */
  public BiomeBuilder fogColor(int color) {
    this.fogColor = validatedColor(color, "雾");
    return this;
  }

  /**
   * @param color 天空 RGB 颜色覆盖值；为 {@code null} 时根据温度计算 @return 当前构建器
   */
  public BiomeBuilder skyColor(@Nullable Integer color) {
    this.skyColor = color == null ? null : validatedColor(color, "天空");
    return this;
  }

  /**
   * @param color 草地 RGB 颜色覆盖值；为 {@code null} 时使用原版颜色 @return 当前构建器
   */
  public BiomeBuilder grassColor(@Nullable Integer color) {
    this.grassColor = color == null ? null : validatedColor(color, "草地");
    return this;
  }

  /**
   * @param color 树叶 RGB 颜色覆盖值；为 {@code null} 时使用原版颜色 @return 当前构建器
   */
  public BiomeBuilder foliageColor(@Nullable Integer color) {
    this.foliageColor = color == null ? null : validatedColor(color, "树叶");
    return this;
  }

  /**
   * @param music 群系背景音乐；为 {@code null} 时不指定 @return 当前构建器
   */
  public BiomeBuilder music(@Nullable Music music) {
    this.music = music;
    return this;
  }

  /**
   * @return 当前 bootstrap 中的生物生成设置构建器
   */
  public MobSpawnSettings.Builder spawns() {
    return Objects.requireNonNull(spawns, "群系生物配置只能在 bootstrap 回调中访问：" + path);
  }

  /**
   * @return 当前 bootstrap 中的地物与地形生成设置构建器
   */
  public BiomeGenerationSettings.Builder generation() {
    return Objects.requireNonNull(generation, "群系生成配置只能在 bootstrap 回调中访问：" + path);
  }

  /**
   * 添加原版主世界的基础洞穴、湖泊、地下结构、泉水和结冰步骤。
   *
   * @return 当前构建器
   */
  public BiomeBuilder defaultOverworldGeneration() {
    BiomeDefaultFeatures.addDefaultCarversAndLakes(generation());
    BiomeDefaultFeatures.addDefaultCrystalFormations(generation());
    BiomeDefaultFeatures.addDefaultMonsterRoom(generation());
    BiomeDefaultFeatures.addDefaultUndergroundVariety(generation());
    BiomeDefaultFeatures.addDefaultSprings(generation());
    BiomeDefaultFeatures.addSurfaceFreezing(generation());
    return this;
  }

  /**
   * 添加一组经过数量校验的特色生物生成规则。
   *
   * @param category 生物生成分类
   * @param type     生物类型
   * @param weight   生成权重，必须大于 0
   * @param minCount 每群最少数量
   * @param maxCount 每群最多数量
   * @return 当前构建器
   */
  public BiomeBuilder featuredSpawn(
      MobCategory category,
      EntityType<?> type,
      int weight,
      int minCount,
      int maxCount
  ) {
    if (weight <= 0) throw new IllegalArgumentException("特色生物生成权重必须大于 0：" + path);
    if (minCount <= 0 || maxCount < minCount) {
      throw new IllegalArgumentException("特色生物群体数量无效：" + path);
    }
    spawns().addSpawn(
        Objects.requireNonNull(category, "生物生成分类不能为空：" + path),
        new SpawnerData(Objects.requireNonNull(type, "生物类型不能为空：" + path), weight, minCount, maxCount)
    );
    return this;
  }

  /**
   * @return 添加平原生物与植被预设后的当前构建器
   */
  public BiomeBuilder plains() {
    peacefulPlainsSpawns();
    generationBase(true);
    BiomeDefaultFeatures.addPlainVegetation(generation());
    BiomeDefaultFeatures.addPlainGrass(generation());
    return this;
  }

  /**
   * @return 添加温带森林生物与植被预设后的当前构建器
   */
  public BiomeBuilder forest() {
    peacefulPlainsSpawns();
    generationBase(true);
    BiomeDefaultFeatures.addBirchTrees(generation());
    BiomeDefaultFeatures.addForestFlowers(generation());
    BiomeDefaultFeatures.addForestGrass(generation());
    return this;
  }

  /**
   * @return 添加多雨森林生物与植被预设后的当前构建器
   */
  public BiomeBuilder rainyForest() {
    peacefulPlainsSpawns();
    generationBase(true);
    BiomeDefaultFeatures.addTallBirchTrees(generation());
    BiomeDefaultFeatures.addForestFlowers(generation());
    BiomeDefaultFeatures.addFerns(generation());
    BiomeDefaultFeatures.addForestGrass(generation());
    return this;
  }

  /**
   * @return 添加山地生物、植被与额外绿宝石预设后的当前构建器
   */
  public BiomeBuilder mountain() {
    peacefulPlainsSpawns();
    generationBase(true);
    BiomeDefaultFeatures.addMountainTrees(generation());
    BiomeDefaultFeatures.addMeadowVegetation(generation());
    BiomeDefaultFeatures.addExtraEmeralds(generation());
    return this;
  }

  /**
   * @return 添加和平雪林生物与植被预设后的当前构建器
   */
  public BiomeBuilder snowyForest() {
    featuredSpawn(MobCategory.CREATURE, EntityType.RABBIT, 10, 2, 3);
    featuredSpawn(MobCategory.CREATURE, EntityType.POLAR_BEAR, 1, 1, 2);
    BiomeDefaultFeatures.caveSpawns(spawns());
    generationBase(true);
    BiomeDefaultFeatures.addSnowyTrees(generation());
    BiomeDefaultFeatures.addTaigaGrass(generation());
    BiomeDefaultFeatures.addCommonBerryBushes(generation());
    return this;
  }

  /**
   * @return 添加和平沙漠生物、植被与化石预设后的当前构建器
   */
  public BiomeBuilder desert() {
    peacefulDesertSpawns();
    generationBase(false);
    BiomeDefaultFeatures.addDesertVegetation(generation());
    BiomeDefaultFeatures.addDesertExtraVegetation(generation());
    BiomeDefaultFeatures.addDesertExtraDecoration(generation());
    BiomeDefaultFeatures.addFossilDecoration(generation());
    return this;
  }

  /**
   * @return 添加和平荒地生物、植被与额外金矿预设后的当前构建器
   */
  public BiomeBuilder badlands() {
    peacefulDesertSpawns();
    generationBase(false);
    BiomeDefaultFeatures.addBadlandsTrees(generation());
    BiomeDefaultFeatures.addBadlandGrass(generation());
    BiomeDefaultFeatures.addBadlandExtraVegetation(generation());
    BiomeDefaultFeatures.addExtraGold(generation());
    return this;
  }

  /**
   * @return 添加和平丛林生物与植被预设后的当前构建器
   */
  public BiomeBuilder jungle() {
    BiomeDefaultFeatures.farmAnimals(spawns());
    featuredSpawn(MobCategory.CREATURE, EntityType.CHICKEN, 10, 4, 4);
    BiomeDefaultFeatures.caveSpawns(spawns());
    generationBase(true);
    BiomeDefaultFeatures.addJungleTrees(generation());
    BiomeDefaultFeatures.addJungleGrass(generation());
    BiomeDefaultFeatures.addJungleVines(generation());
    BiomeDefaultFeatures.addJungleMelons(generation());
    return this;
  }

  /**
   * @return 添加湿地生物与植被预设后的当前构建器
   */
  public BiomeBuilder wetland() {
    BiomeDefaultFeatures.caveSpawns(spawns());
    generationBase(false);
    BiomeDefaultFeatures.addSwampVegetation(generation());
    BiomeDefaultFeatures.addSwampExtraVegetation(generation());
    return this;
  }

  /**
   * @return 添加海洋生物与水下植被预设后的当前构建器
   */
  public BiomeBuilder ocean() {
    featuredSpawn(MobCategory.WATER_CREATURE, EntityType.SQUID, 3, 1, 4);
    featuredSpawn(MobCategory.WATER_AMBIENT, EntityType.COD, 15, 3, 6);
    BiomeDefaultFeatures.caveSpawns(spawns());
    generationBase(true);
    BiomeDefaultFeatures.addDefaultSeagrass(generation());
    BiomeDefaultFeatures.addColdOceanExtraVegetation(generation());
    return this;
  }

  /**
   * @return 添加洞穴生物、滴水石与繁茂洞穴植被预设后的当前构建器
   */
  public BiomeBuilder cavern() {
    BiomeDefaultFeatures.caveSpawns(spawns());
    generationBase(true);
    BiomeDefaultFeatures.addDripstone(generation());
    BiomeDefaultFeatures.addLushCavesVegetationFeatures(generation());
    BiomeDefaultFeatures.addLushCavesSpecialOres(generation());
    return this;
  }

  /**
   * 校验并将群系声明登记到所属目录。
   *
   * @return 当前构建器
   */
  public BiomeBuilder build() {
    if (key != null) {
      throw new IllegalStateException("群系 builder 不能重复 build：" + path);
    }
    return catalog.register(this);
  }

  /**
   * 使用动态注册表查询器执行配置并创建群系值。
   *
   * @param placedFeatures 放置地物查询器
   * @param carvers        配置地形雕刻器查询器
   * @return 构建完成的群系
   */
  public Biome create(
      HolderGetter<PlacedFeature> placedFeatures,
      HolderGetter<ConfiguredWorldCarver<?>> carvers
  ) {
    resetSettings();
    this.spawns = new MobSpawnSettings.Builder();
    this.generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
    configure.accept(this);

    BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
        .waterColor(waterColor)
        .waterFogColor(waterFogColor)
        .fogColor(fogColor)
        .skyColor(skyColor != null ? skyColor : defaultSkyColor())
        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
        .backgroundMusic(music);
    if (grassColor != null) effects.grassColorOverride(grassColor);
    if (foliageColor != null) effects.foliageColorOverride(foliageColor);

    Biome biome = new Biome.BiomeBuilder()
        .hasPrecipitation(precipitation)
        .temperature(temperature)
        .downfall(downfall)
        .specialEffects(effects.build())
        .mobSpawnSettings(spawns().build())
        .generationSettings(generation().build())
        .build();
    this.spawns = null;
    this.generation = null;
    return biome;
  }

  /**
   * @return 动态群系注册表中的资源键
   */
  public ResourceKey<Biome> key() {
    return Objects.requireNonNull(key, "群系尚未 build：" + path);
  }

  /**
   * @return 为多噪声群系源配置的气候点
   */
  public ParameterPoint climate() {
    return Objects.requireNonNull(climate, "群系尚未配置气候点：" + path);
  }

  /**
   * @param key 目录分配的群系资源键
   */
  public void bind(ResourceKey<Biome> key) {
    this.key = Objects.requireNonNull(key, "key");
  }

  /**
   * @return 当前声明是否属于指定群系目录
   */
  public boolean belongsTo(BiomeCatalog catalog) {
    return this.catalog == catalog;
  }

  private void resetSettings() {
    precipitation = true;
    temperature = 0.8F;
    downfall = 0.4F;
    waterColor = 4159204;
    waterFogColor = 329011;
    fogColor = 12638463;
    skyColor = null;
    grassColor = null;
    foliageColor = null;
    music = null;
  }

  private int defaultSkyColor() {
    float adjustedTemperature = Mth.clamp(temperature / 3.0F, -1.0F, 1.0F);
    return Mth.hsvToRgb(
        0.62222224F - adjustedTemperature * 0.05F,
        0.5F + adjustedTemperature * 0.1F,
        1.0F
    );
  }

  private int validatedColor(int color, String name) {
    if (color < 0 || color > 0xFFFFFF) {
      throw new IllegalArgumentException("群系" + name + "颜色必须是 0x000000 到 0xFFFFFF：" + path);
    }
    return color;
  }

  private void peacefulPlainsSpawns() {
    BiomeDefaultFeatures.farmAnimals(spawns());
    featuredSpawn(MobCategory.CREATURE, EntityType.HORSE, 5, 2, 6);
    featuredSpawn(MobCategory.CREATURE, EntityType.DONKEY, 1, 1, 3);
    BiomeDefaultFeatures.caveSpawns(spawns());
  }

  private void peacefulDesertSpawns() {
    featuredSpawn(MobCategory.CREATURE, EntityType.RABBIT, 4, 2, 3);
    BiomeDefaultFeatures.caveSpawns(spawns());
  }

  private void generationBase(boolean addDefaultExtraVegetation) {
    defaultOverworldGeneration();
    BiomeDefaultFeatures.addDefaultOres(generation());
    BiomeDefaultFeatures.addDefaultSoftDisks(generation());
    BiomeDefaultFeatures.addDefaultMushrooms(generation());
    if (addDefaultExtraVegetation) {
      BiomeDefaultFeatures.addDefaultExtraVegetation(generation());
    }
  }
}
