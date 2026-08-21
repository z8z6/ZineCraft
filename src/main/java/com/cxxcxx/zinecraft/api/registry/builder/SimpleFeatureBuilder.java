package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import com.cxxcxx.zinecraft.api.registry.catalog.FeatureCatalog;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * 无额外配置地物的声明构建器，保存放置规则、生成阶段、群系范围和动态注册键。
 */
public final class SimpleFeatureBuilder {
  public final String path;
  public final Feature<NoneFeatureConfiguration> feature;
  private final FeatureCatalog catalog;
  public List<PlacementModifier> placement = List.of();
  public GenerationStep.Decoration generationStep;
  public BiomeSelection biomes;
  @Nullable
  private TerraPlace place;
  @Nullable
  private ResourceKey<ConfiguredFeature<?, ?>> configuredKey;
  @Nullable
  private ResourceKey<PlacedFeature> placedKey;

  /**
   * 创建无额外配置地物声明。
   *
   * @param catalog 接收该声明的地物目录
   * @param path    配置地物和放置地物共用的注册路径
   * @param feature 实际执行生成逻辑的地物类型
   */
  public SimpleFeatureBuilder(
      FeatureCatalog catalog,
      String path,
      Feature<NoneFeatureConfiguration> feature
  ) {
    this.catalog = Objects.requireNonNull(catalog, "地物目录不能为空");
    this.path = Objects.requireNonNull(path, "地物 ID 不能为空");
    this.feature = Objects.requireNonNull(feature, "地物类型不能为空：" + path);
  }

  /**
   * 设置放置修饰器。
   *
   * @param placement 按执行顺序排列的放置规则
   * @return 当前构建器
   */
  public SimpleFeatureBuilder placement(List<? extends PlacementModifier> placement) {
    this.placement = List.copyOf(Objects.requireNonNull(placement, "地物放置规则不能为空：" + path));
    return this;
  }

  /**
   * 设置地物注入群系生成流程的阶段。
   *
   * @param generationStep 生成阶段
   * @return 当前构建器
   */
  public SimpleFeatureBuilder generationStep(GenerationStep.Decoration generationStep) {
    this.generationStep = Objects.requireNonNull(generationStep, "地物生成阶段不能为空：" + path);
    return this;
  }

  /**
   * 设置允许生成该地物的群系范围。
   *
   * @param biomes 目标群系选择器
   * @return 当前构建器
   */
  public SimpleFeatureBuilder biomes(BiomeSelection biomes) {
    this.biomes = Objects.requireNonNull(biomes, "地物生成群系不能为空：" + path);
    return this;
  }

  /**
   * 指定该地物所属的泰拉城市或城区。
   *
   * <p>不调用本方法时 {@link #place()} 返回 {@code null}，表示不属于任何城市。</p>
   *
   * @param place {@code ModCity} 或 {@code ModCityRegion} 中声明的城市或城区
   * @return 当前构建器
   */
  public SimpleFeatureBuilder place(TerraPlace place) {
    this.place = Objects.requireNonNull(place, "地物归属地点不能为空：" + path);
    return this;
  }

  /**
   * 校验并将声明登记到所属地物目录。
   *
   * @return 当前构建器
   */
  public SimpleFeatureBuilder build() {
    if (configuredKey != null || placedKey != null) {
      throw new IllegalStateException("地物 builder 不能重复 build：" + path);
    }
    return catalog.register(this);
  }

  /**
   * 绑定目录分配的动态注册键。
   *
   * @param configuredKey 配置地物资源键
   * @param placedKey     放置地物资源键
   */
  public void bind(
      ResourceKey<ConfiguredFeature<?, ?>> configuredKey,
      ResourceKey<PlacedFeature> placedKey
  ) {
    this.configuredKey = Objects.requireNonNull(configuredKey, "configuredKey");
    this.placedKey = Objects.requireNonNull(placedKey, "placedKey");
  }

  /**
   * @return 配置地物资源键
   */
  public ResourceKey<ConfiguredFeature<?, ?>> configuredKey() {
    return Objects.requireNonNull(configuredKey, "地物尚未 build：" + path);
  }

  /**
   * @return 放置地物资源键
   */
  public ResourceKey<PlacedFeature> placedKey() {
    return Objects.requireNonNull(placedKey, "地物尚未 build：" + path);
  }

  /**
   * @return 地物所属的泰拉城市或城区；{@code null} 表示不属于任何城市
   */
  @Nullable
  public TerraPlace place() {
    return place;
  }

  /**
   * @return 当前声明是否属于指定目录
   */
  public boolean belongsTo(FeatureCatalog catalog) {
    return this.catalog == catalog;
  }
}
