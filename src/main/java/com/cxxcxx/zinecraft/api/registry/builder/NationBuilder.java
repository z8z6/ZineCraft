package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import com.cxxcxx.zinecraft.api.registry.catalog.NationCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 泰拉国家声明构建器。
 */
public final class NationBuilder implements TerraPlace {
  public final String id;
  public final String zhCn;
  private final NationCatalog catalog;
  public final List<PlanarPoint> relativePoints = new ArrayList<>();
  public Supplier<? extends Collection<TerraCityBuilder>> cities;
  public boolean underground;
  public int size;
  private boolean built;

  public NationBuilder(NationCatalog catalog, String id, String zhCn) {
    this.catalog = Objects.requireNonNull(catalog, "国家目录不能为空");
    this.id = Objects.requireNonNull(id, "国家 ID 不能为空");
    this.zhCn = Objects.requireNonNull(zhCn, "国家中文名不能为空");
  }

  /**
   * 声明国家折线相对泰拉核心矩形的归一化顶点，参数按 x、z 成对排列。
   */
  public NationBuilder position(double x, double z) {
    this.relativePoints.add(new PlanarPoint(x, z));
    return this;
  }

  public NationBuilder cities(Supplier<? extends Collection<TerraCityBuilder>> cities) {
    this.cities = Objects.requireNonNull(cities, "国家城市清单不能为空：" + id);
    return this;
  }

  /**
   * 将国家标记为地下国家。
   */
  public NationBuilder underground() {
    this.underground = true;
    return this;
  }

  /**
   * 声明地下国家水平规划正方形的边长，单位为方块。
   */
  public NationBuilder size(int size) {
    this.size = size;
    return this;
  }

  public NationBuilder build() {
    if (built) throw new IllegalStateException("国家 builder 不能重复 build：" + id);
    catalog.register(this);
    built = true;
    return this;
  }

  public boolean belongsTo(NationCatalog catalog) {
    return this.catalog == catalog;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public String zhCn() {
    return zhCn;
  }

  @Override
  public String enUs() {
    return TranslationCatalog.toDisplayName(id);
  }

  @Override
  public String translationKey() {
    return "journeymap.zinecraft.nation." + id;
  }

  public String getId() {
    return id;
  }

  public List<PlanarPoint> relativePoints() {
    return relativePoints;
  }

  public boolean isUnderground() {
    return underground;
  }

  public int size() {
    return size;
  }

  public java.util.List<TerraCityBuilder> cities() {
    return java.util.List.copyOf(Objects.requireNonNull(cities.get(), "国家城市清单不能为空：" + id));
  }
}
