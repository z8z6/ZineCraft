package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.DimensionCatalog;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionBiome;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionBootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 维度声明构建器，统一保存群系气候点、维度类型、噪声设置、区块生成器和动态注册键。
 */
public final class DimensionBuilder {
  public final String path;
  private final DimensionCatalog catalog;
  private final List<DimensionBiome> mutableBiomes = new ArrayList<>();
  public final List<DimensionBiome> biomes = Collections.unmodifiableList(mutableBiomes);
  private ResourceKey<NoiseGeneratorSettings> noiseSettingsKey = NoiseGeneratorSettings.OVERWORLD;
  private Supplier<DimensionType> createDimensionType = DimensionBuilder::createDefaultDimensionType;
  @Nullable
  private Function<DimensionBootstrapContext, ChunkGenerator> createGenerator;
  @Nullable
  private ResourceKey<Level> levelKey;
  @Nullable
  private ResourceKey<LevelStem> stemKey;
  @Nullable
  private ResourceKey<DimensionType> typeKey;

  /**
   * 创建尚未登记的维度声明。
   *
   * @param catalog 接收该声明的维度目录
   * @param path    维度、维度类型与 LevelStem 使用的注册路径
   */
  public DimensionBuilder(DimensionCatalog catalog, String path) {
    this.catalog = Objects.requireNonNull(catalog, "维度目录不能为空");
    this.path = Objects.requireNonNull(path, "维度 ID 不能为空");
  }

  /**
   * @param biomes 群系资源键与多噪声气候点列表 @return 当前构建器
   */
  public DimensionBuilder biomes(List<DimensionBiome> biomes) {
    mutableBiomes.clear();
    mutableBiomes.addAll(Objects.requireNonNull(biomes, "维度群系列表不能为空：" + path));
    return this;
  }

  /**
   * @param biome 群系资源键与气候点 @return 当前构建器
   */
  public DimensionBuilder biome(DimensionBiome biome) {
    mutableBiomes.add(Objects.requireNonNull(biome, "维度群系不能为空：" + path));
    return this;
  }

  /**
   * @param noiseSettingsKey 区块生成使用的噪声设置资源键 @return 当前构建器
   */
  public DimensionBuilder noiseSettings(ResourceKey<NoiseGeneratorSettings> noiseSettingsKey) {
    this.noiseSettingsKey = Objects.requireNonNull(noiseSettingsKey, "噪声设置不能为空：" + path);
    return this;
  }

  /**
   * @param createDimensionType 维度类型工厂 @return 当前构建器
   */
  public DimensionBuilder dimensionType(Supplier<DimensionType> createDimensionType) {
    this.createDimensionType = Objects.requireNonNull(createDimensionType, "维度类型工厂不能为空：" + path);
    return this;
  }

  /**
   * @param createGenerator 自定义区块生成器工厂 @return 当前构建器
   */
  public DimensionBuilder generator(
      Function<? super DimensionBootstrapContext, ? extends ChunkGenerator> createGenerator
  ) {
    Objects.requireNonNull(createGenerator, "区块生成器工厂不能为空：" + path);
    this.createGenerator = context -> createGenerator.apply(context);
    return this;
  }

  /**
   * @return 校验并登记后的当前构建器
   */
  public DimensionBuilder build() {
    if (levelKey != null) throw new IllegalStateException("Dimension builder 不能重复 build：" + path);
    return catalog.register(this);
  }

  /**
   * 绑定目录为维度分配的动态注册键。
   *
   * @param levelKey 运行时维度资源键
   * @param stemKey  LevelStem 动态注册键
   * @param typeKey  维度类型动态注册键
   */
  public void bind(
      ResourceKey<Level> levelKey,
      ResourceKey<LevelStem> stemKey,
      ResourceKey<DimensionType> typeKey
  ) {
    this.levelKey = Objects.requireNonNull(levelKey, "levelKey");
    this.stemKey = Objects.requireNonNull(stemKey, "stemKey");
    this.typeKey = Objects.requireNonNull(typeKey, "typeKey");
  }

  /**
   * @return 运行时维度资源键
   */
  public ResourceKey<Level> levelKey() {
    return Objects.requireNonNull(levelKey, "维度尚未 build：" + path);
  }

  /**
   * @return LevelStem 动态注册键
   */
  public ResourceKey<LevelStem> stemKey() {
    return Objects.requireNonNull(stemKey, "维度尚未 build：" + path);
  }

  /**
   * @return 维度类型动态注册键
   */
  public ResourceKey<DimensionType> typeKey() {
    return Objects.requireNonNull(typeKey, "维度尚未 build：" + path);
  }

  /**
   * @return 区块生成使用的噪声设置资源键
   */
  public ResourceKey<NoiseGeneratorSettings> noiseSettingsKey() {
    return noiseSettingsKey;
  }

  /**
   * @return 新建维度类型值
   */
  public DimensionType createDimensionType() {
    return createDimensionType.get();
  }

  /**
   * @return 自定义区块生成器工厂；未设置时为 {@code null}
   */
  @Nullable
  public Function<DimensionBootstrapContext, ChunkGenerator> generator() {
    return createGenerator;
  }

  /**
   * @param catalog 待比较的维度目录 @return 当前声明是否归该目录所有
   */
  public boolean belongsTo(DimensionCatalog catalog) {
    return this.catalog == catalog;
  }

  private static DimensionType createDefaultDimensionType() {
    return new DimensionType(
        OptionalLong.empty(),
        true,
        false,
        false,
        true,
        1.0,
        true,
        false,
        -64,
        384,
        384,
        BlockTags.INFINIBURN_OVERWORLD,
        BuiltinDimensionTypes.OVERWORLD_EFFECTS,
        0.0F,
        new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0)
    );
  }
}
