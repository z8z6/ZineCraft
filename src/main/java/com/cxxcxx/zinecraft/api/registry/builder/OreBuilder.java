package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import com.cxxcxx.zinecraft.api.registry.catalog.FeatureCatalog;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 矿石地物声明构建器，统一保存矿石方块、矿脉生成参数、烧炼信息和动态注册键。
 *
 * @param <T> 矿石方块类型
 */
public final class OreBuilder<T extends Block> implements Supplier<T>, ItemLike {
  public final String path;
  public final Supplier<? extends T> block;
  private final FeatureCatalog catalog;
  public int veinSize;
  public int veinsPerChunk;
  public int maxY;
  public float discardChanceOnAirExposure;
  public BiomeSelection biomes;
  @Nullable
  private TerraPlace place;
  @Nullable
  public ItemLike cookingResult;
  @Nullable
  public String cookingGroup;
  @Nullable
  private ResourceKey<ConfiguredFeature<?, ?>> configuredKey;
  @Nullable
  private ResourceKey<PlacedFeature> placedKey;

  /**
   * 创建矿石地物声明。
   *
   * @param catalog 接收该矿石的地物目录
   * @param path    放置地物的注册路径；配置地物会使用 {@code path + "_vein"}
   * @param block   矿脉实际放置的方块供应器
   */
  public OreBuilder(FeatureCatalog catalog, String path, Supplier<? extends T> block) {
    this.catalog = Objects.requireNonNull(catalog, "地物目录不能为空");
    this.path = Objects.requireNonNull(path, "矿石地物 ID 不能为空");
    this.block = Objects.requireNonNull(block, "矿石方块不能为空：" + path);
  }

  /**
   * 设置矿脉大小和每区块生成次数。
   *
   * @param veinSize      单条矿脉最多生成的方块数
   * @param veinsPerChunk 每区块尝试放置矿脉的次数
   * @return 当前构建器
   */
  public OreBuilder<T> vein(int veinSize, int veinsPerChunk) {
    this.veinSize = veinSize;
    this.veinsPerChunk = veinsPerChunk;
    return this;
  }

  /**
   * 设置偏向世界底部的最高生成高度。
   *
   * @param maxY 高度分布的最高端点
   * @return 当前构建器
   */
  public OreBuilder<T> maxY(int maxY) {
    this.maxY = maxY;
    return this;
  }

  /**
   * 设置矿石暴露于空气时被丢弃的概率。
   *
   * @param chance 0 到 1 的丢弃概率
   * @return 当前构建器
   */
  public OreBuilder<T> discardChanceOnAirExposure(float chance) {
    this.discardChanceOnAirExposure = chance;
    return this;
  }

  /**
   * 设置允许生成该矿石的群系范围。
   *
   * @param biomes 目标群系选择器
   * @return 当前构建器
   */
  public OreBuilder<T> biomes(BiomeSelection biomes) {
    this.biomes = Objects.requireNonNull(biomes, "矿石生成群系不能为空：" + path);
    return this;
  }

  /**
   * 指定该矿石地物所属的泰拉城市或城区。
   *
   * <p>不调用本方法时 {@link #place()} 返回 {@code null}，表示不属于任何城市。</p>
   *
   * @param place {@code ModCity} 或 {@code ModCityRegion} 中声明的城市或城区
   * @return 当前构建器
   */
  public OreBuilder<T> place(TerraPlace place) {
    this.place = Objects.requireNonNull(place, "矿石地物归属地点不能为空：" + path);
    return this;
  }

  /**
   * 声明矿石的熔炉和高炉产物。
   *
   * @param result 烧炼产物
   * @param group  熔炼与高炉配方共用的分组名
   * @return 当前构建器
   */
  public OreBuilder<T> cooking(ItemLike result, String group) {
    this.cookingResult = Objects.requireNonNull(result, "矿石烧炼产物不能为空：" + path);
    this.cookingGroup = Objects.requireNonNull(group, "矿石烧炼分组不能为空：" + path);
    return this;
  }

  /**
   * 校验并将矿石登记到所属地物目录。
   *
   * @return 当前构建器
   */
  public OreBuilder<T> build() {
    if (configuredKey != null || placedKey != null) {
      throw new IllegalStateException("矿石 builder 不能重复 build：" + path);
    }
    return catalog.register(this);
  }

  /**
   * 绑定目录分配的配置地物和放置地物资源键。
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
   * @return 当前声明是否包含烧炼配方信息
   */
  public boolean hasCookingRecipe() {
    return cookingResult != null;
  }

  /**
   * @return 烧炼产物；未调用 {@link #cooking(ItemLike, String)} 时抛出异常
   */
  public ItemLike cookingResult() {
    return Objects.requireNonNull(cookingResult, "矿石未声明烧炼产物：" + path);
  }

  /**
   * @return 烧炼配方分组；未调用 {@link #cooking(ItemLike, String)} 时抛出异常
   */
  public String cookingGroup() {
    return Objects.requireNonNull(cookingGroup, "矿石未声明烧炼分组：" + path);
  }

  /**
   * @return 配置地物资源键
   */
  public ResourceKey<ConfiguredFeature<?, ?>> configuredKey() {
    return Objects.requireNonNull(configuredKey, "矿石尚未 build：" + path);
  }

  /**
   * @return 放置地物资源键
   */
  public ResourceKey<PlacedFeature> placedKey() {
    return Objects.requireNonNull(placedKey, "矿石尚未 build：" + path);
  }

  /**
   * @return 矿石地物所属的泰拉城市或城区；{@code null} 表示不属于任何城市
   */
  @Nullable
  public TerraPlace place() {
    return place;
  }

  /**
   * @return 矿石方块实例
   */
  @Override
  public T get() {
    return block.get();
  }

  /**
   * @return 矿石方块对应的物品实例
   */
  @Override
  public net.minecraft.world.item.Item asItem() {
    return get().asItem();
  }

  /**
   * @return 当前声明是否属于指定地物目录
   */
  public boolean belongsTo(FeatureCatalog catalog) {
    return this.catalog == catalog;
  }
}
