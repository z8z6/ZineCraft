package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.datagen.RegistryDataContributor;
import com.cxxcxx.zinecraft.api.registry.builder.DimensionBuilder;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionBiome;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionBootstrapContext;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;

/**
 * 维度注册目录，负责群系源类型、维度 Builder 校验以及维度类型和 LevelStem 数据生成。
 */
public final class DimensionCatalog implements RegistryDataContributor {
  private final String namespace;
  private final DeferredRegister<MapCodec<? extends BiomeSource>> biomeSources;
  private final List<DimensionBuilder> mutableEntries = new ArrayList<>();
  public final List<DimensionBuilder> entries = Collections.unmodifiableList(mutableEntries);

  /**
   * @param namespace 模组命名空间
   */
  public DimensionCatalog(String namespace) {
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.biomeSources = DeferredRegister.create(BuiltInRegistries.BIOME_SOURCE.key(), namespace);
  }

  /**
   * 登记一个自定义群系源序列化器。
   *
   * @param path  群系源类型注册路径
   * @param codec 群系源 MapCodec
   * @param <S>   群系源类型
   * @return 已登记的 MapCodec
   */
  public <S extends BiomeSource> MapCodec<S> biomeSource(String path, MapCodec<S> codec) {
    biomeSources.register(path, () -> codec);
    return codec;
  }

  /**
   * @param modBus 模组事件总线
   */
  public void register(IEventBus modBus) {
    biomeSources.register(modBus);
  }

  /**
   * @param path 维度注册路径 @return 尚未登记的维度构建器
   */
  public DimensionBuilder dimension(String path) {
    return new DimensionBuilder(this, path);
  }

  /**
   * 校验维度声明、分配资源键并登记 Builder。
   *
   * @param builder 完成配置且隶属于当前目录的维度构建器
   * @return 已绑定资源键的当前构建器
   */
  public DimensionBuilder register(DimensionBuilder builder) {
    Objects.requireNonNull(builder, "维度 builder 不能为空");
    if (!builder.belongsTo(this)) throw new IllegalArgumentException("维度 builder 不属于当前目录：" + builder.path);
    if (!ResourceLocation.isValidPath(builder.path))
      throw new IllegalArgumentException("维度 ID 路径无效：" + builder.path);
    if (builder.biomes.isEmpty()) throw new IllegalArgumentException("维度至少需要一个群系：" + builder.path);
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("维度 ID 重复：" + builder.path);
    }

    var biomeKeys = new HashSet<ResourceKey<Biome>>();
    var climatePoints = new HashSet<ParameterPoint>();
    for (DimensionBiome biome : builder.biomes) {
      if (!biomeKeys.add(biome.biome())) {
        throw new IllegalArgumentException("维度群系资源键重复：" + builder.path);
      }
      if (!climatePoints.add(biome.parameters())) {
        throw new IllegalArgumentException("维度气候点重复：" + builder.path);
      }
    }

    ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace, builder.path);
    builder.bind(
        ResourceKey.create(Registries.DIMENSION, id),
        ResourceKey.create(Registries.LEVEL_STEM, id),
        ResourceKey.create(Registries.DIMENSION_TYPE, id)
    );
    mutableEntries.add(builder);
    return builder;
  }

  /**
   * @param registryBuilder 动态注册表数据生成构建器
   */
  @Override
  public void contribute(RegistrySetBuilder registryBuilder) {
    registryBuilder.add(Registries.DIMENSION_TYPE, this::bootstrapDimensionTypes);
    registryBuilder.add(Registries.LEVEL_STEM, this::bootstrapLevelStems);
  }

  /**
   * @param context 维度类型动态注册上下文
   */
  public void bootstrapDimensionTypes(BootstrapContext<DimensionType> context) {
    for (DimensionBuilder builder : entries) {
      context.register(builder.typeKey(), builder.createDimensionType());
    }
  }

  /**
   * @param context LevelStem 动态注册上下文
   */
  public void bootstrapLevelStems(BootstrapContext<LevelStem> context) {
    var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
    var noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
    var biomes = context.lookup(Registries.BIOME);
    for (DimensionBuilder builder : entries) {
      List<Pair<ParameterPoint, Holder<Biome>>> parameters = builder.biomes.stream()
          .map(biome -> Pair.<ParameterPoint, Holder<Biome>>of(
              biome.parameters(), biomes.getOrThrow(biome.biome())
          ))
          .toList();
      ParameterList<Holder<Biome>> parameterList = new ParameterList<>(parameters);
      MultiNoiseBiomeSource biomeSource = MultiNoiseBiomeSource.createFromList(parameterList);
      Holder<NoiseGeneratorSettings> settings = noiseSettings.getOrThrow(builder.noiseSettingsKey());
      DimensionBootstrapContext bootstrapContext = new DimensionBootstrapContext(
          builder, biomeSource, parameterList, biomes, settings
      );
      var createGenerator = builder.generator();
      ChunkGenerator generator = createGenerator == null
          ? new NoiseBasedChunkGenerator(biomeSource, settings)
          : Objects.requireNonNull(createGenerator.apply(bootstrapContext), "区块生成器不能为空：" + builder.path);
      context.register(
          builder.stemKey(),
          new LevelStem(dimensionTypes.getOrThrow(builder.typeKey()), generator)
      );
    }
  }
}
