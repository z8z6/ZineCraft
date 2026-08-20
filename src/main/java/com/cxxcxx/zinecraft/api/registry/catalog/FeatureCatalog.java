package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.datagen.RegistryDataContributor;
import com.cxxcxx.zinecraft.api.registry.builder.OreBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.SimpleFeatureBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 地物注册目录，管理自定义地物类型、矿石地物和简单无配置地物的数据生成。
 */
public final class FeatureCatalog implements RegistryDataContributor {
  private final String namespace;
  private final DeferredRegister<Feature<?>> registry;
  private final List<OreBuilder<?>> mutableOres = new ArrayList<>();
  public final List<OreBuilder<?>> ores = Collections.unmodifiableList(mutableOres);
  private final List<SimpleFeatureBuilder> mutableSimpleFeatures = new ArrayList<>();
  public final List<SimpleFeatureBuilder> simpleFeatures = Collections.unmodifiableList(mutableSimpleFeatures);

  /**
   * 创建地物注册目录。
   *
   * @param namespace 模组命名空间
   */
  public FeatureCatalog(String namespace) {
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.registry = DeferredRegister.create(BuiltInRegistries.FEATURE.key(), namespace);
  }

  /**
   * 校验并登记矿石声明，为其分配配置地物和放置地物资源键。
   *
   * @param builder 矿石声明
   * @param <T> 矿石方块类型
   * @return 已绑定资源键的当前声明
   */
  public <T extends Block> OreBuilder<T> register(OreBuilder<T> builder) {
    Objects.requireNonNull(builder, "矿石 builder 不能为空");
    if (!builder.belongsTo(this)) {
      throw new IllegalArgumentException("矿石 builder 不属于当前目录：" + builder.path);
    }
    if (!ResourceLocation.isValidPath(builder.path)) {
      throw new IllegalArgumentException("矿石地物 ID 路径无效：" + builder.path);
    }
    if (builder.veinSize <= 0 || builder.veinsPerChunk <= 0) {
      throw new IllegalArgumentException("矿脉大小和每区块生成次数必须大于 0：" + builder.path);
    }
    if (builder.discardChanceOnAirExposure < 0 || builder.discardChanceOnAirExposure > 1) {
      throw new IllegalArgumentException("暴露丢弃概率必须在 0 到 1 之间：" + builder.path);
    }
    Objects.requireNonNull(builder.biomes, "矿石生成群系不能为空：" + builder.path);
    if (builder.hasCookingRecipe() && builder.cookingGroup().isBlank()) {
      throw new IllegalArgumentException("矿石烧炼分组不能为空：" + builder.path);
    }
    if (mutableOres.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("矿石地物 ID 重复：" + builder.path);
    }
    builder.bind(
        key(Registries.CONFIGURED_FEATURE, builder.path + "_vein"),
        key(Registries.PLACED_FEATURE, builder.path)
    );
    mutableOres.add(builder);
    return builder;
  }

  /**
   * 校验并登记无额外配置的地物声明，为其分配动态注册键。
   *
   * @param builder 地物声明
   * @return 已绑定资源键的当前声明
   */
  public SimpleFeatureBuilder register(SimpleFeatureBuilder builder) {
    Objects.requireNonNull(builder, "地物 builder 不能为空");
    if (!builder.belongsTo(this)) {
      throw new IllegalArgumentException("地物 builder 不属于当前目录：" + builder.path);
    }
    if (!ResourceLocation.isValidPath(builder.path)) {
      throw new IllegalArgumentException("地物 ID 路径无效：" + builder.path);
    }
    if (builder.placement.isEmpty()) {
      throw new IllegalArgumentException("地物放置规则不能为空：" + builder.path);
    }
    Objects.requireNonNull(builder.generationStep, "地物生成阶段不能为空：" + builder.path);
    Objects.requireNonNull(builder.biomes, "地物生成群系不能为空：" + builder.path);
    if (mutableSimpleFeatures.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("地物 ID 重复：" + builder.path);
    }
    builder.bind(
        key(Registries.CONFIGURED_FEATURE, builder.path),
        key(Registries.PLACED_FEATURE, builder.path)
    );
    mutableSimpleFeatures.add(builder);
    return builder;
  }

  /**
   * 将矿石和无配置地物声明写入配置地物动态注册表。
   *
   * @param context 配置地物的启动注册上下文
   */
  public void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    for (var ore : ores) {
      var targets = List.of(
          OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ore.get().defaultBlockState()),
          OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), ore.get().defaultBlockState())
      );
      context.register(ore.configuredKey(), new ConfiguredFeature<>(Feature.ORE,
          new OreConfiguration(targets, ore.veinSize, ore.discardChanceOnAirExposure)));
    }
    for (var entry : simpleFeatures) {
      context.register(entry.configuredKey(), new ConfiguredFeature<>(entry.feature, NoneFeatureConfiguration.INSTANCE));
    }
  }

  /**
   * 根据 Builder 中的放置规则写入放置地物动态注册表。
   *
   * @param context 放置地物的启动注册上下文
   */
  public void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
    var configured = context.lookup(Registries.CONFIGURED_FEATURE);
    for (var ore : ores) {
      context.register(ore.placedKey(), new PlacedFeature(configured.getOrThrow(ore.configuredKey()), List.of(
          CountPlacement.of(ore.veinsPerChunk), InSquarePlacement.spread(),
          HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(ore.maxY), 3)),
          BiomeFilter.biome()
      )));
    }
    for (var entry : simpleFeatures) {
      context.register(entry.placedKey(), new PlacedFeature(configured.getOrThrow(entry.configuredKey()), entry.placement));
    }
  }

  /**
   * 为所有矿石和无配置地物生成目标群系修改器。
   *
   * @param context 群系修改器的启动注册上下文
   */
  public void bootstrapBiomeModifiers(BootstrapContext<BiomeModifier> context) {
    var biomes = context.lookup(Registries.BIOME);
    var placed = context.lookup(Registries.PLACED_FEATURE);
    for (var ore : ores) {
      var parts = ore.biomes.resolveParts(biomes);
      for (int i = 0; i < parts.size(); i++) {
        String path = ore.placedKey().location().getPath() + (parts.size() == 1 ? "" : "_" + i);
        context.register(key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, path),
            new BiomeModifiers.AddFeaturesBiomeModifier(parts.get(i),
                HolderSet.direct(placed.getOrThrow(ore.placedKey())), GenerationStep.Decoration.UNDERGROUND_ORES));
      }
    }
    for (var entry : simpleFeatures) {
      var parts = entry.biomes.resolveParts(biomes);
      for (int i = 0; i < parts.size(); i++) {
        String path = entry.path + (parts.size() == 1 ? "" : "_" + i);
        context.register(key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, path),
            new BiomeModifiers.AddFeaturesBiomeModifier(parts.get(i),
                HolderSet.direct(placed.getOrThrow(entry.placedKey())), entry.generationStep));
      }
    }
  }

  /**
   * 将配置地物、放置地物和群系修改器接入数据生成。
   *
   * @param registryBuilder 动态注册表数据生成构建器
   */
  @Override
  public void contribute(RegistrySetBuilder registryBuilder) {
    registryBuilder.add(Registries.CONFIGURED_FEATURE, this::bootstrapConfigured);
    registryBuilder.add(Registries.PLACED_FEATURE, this::bootstrapPlaced);
    registryBuilder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, this::bootstrapBiomeModifiers);
  }

  /**
   * 登记一个自定义地物类型。
   *
   * @param path    地物类型的命名空间内路径
   * @param feature 地物类型实例
   * @param <C>     地物配置类型
   * @param <F>     地物类型
   * @return 已登记的地物类型实例
   */
  public <C extends net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration,
      F extends Feature<C>> F register(String path, F feature) {
    registry.register(path, () -> feature);
    return feature;
  }

  /**
   * 将自定义地物类型延迟注册器挂接到模组事件总线。
   *
   * @param modBus 模组事件总线
   */
  public void register(IEventBus modBus) {
    registry.register(modBus);
  }

  /**
   * 使用目录命名空间创建动态注册表资源键。
   *
   * @param registryKey 目标注册表键
   * @param path        命名空间内路径
   * @param <T>         注册表值类型
   * @return 完整资源键
   */
  private <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registryKey, String path) {
    return ResourceKey.create(registryKey, ResourceLocation.fromNamespaceAndPath(namespace, path));
  }
}
