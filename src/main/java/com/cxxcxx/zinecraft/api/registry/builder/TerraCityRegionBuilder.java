package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.nation.TerraCityRegionBuilding;
import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import com.cxxcxx.zinecraft.api.registry.catalog.TerraCityRegionCatalog;
import com.cxxcxx.zinecraft.api.world.layout.GridLayout;
import com.cxxcxx.zinecraft.api.world.layout.Layout;
import com.cxxcxx.zinecraft.api.world.layout.WeightedLayoutElement;
import com.cxxcxx.zinecraft.api.world.city.PlotSize;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout.RegionLayoutType;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout.RoadConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 泰拉城区及其合法建筑声明构建器。
 */
public final class TerraCityRegionBuilder implements TerraPlace, WeightedLayoutElement {
  public static final int MIN_PLOT_AREA_CHUNKS = 80;

  public final NationBuilder nation;
  private String id;
  public final String zhCn;
  private final TerraCityRegionCatalog catalog;
  public String enUs;
  public int weight = 1;
  public boolean unique;
  public List<TerraCityRegionBuilding> buildings = List.of();
  public Layout buildingLayout = GridLayout.INSTANCE;
  private RegionLayoutType regionLayoutType;
  private RoadConfig roadConfig = RoadConfig.DEFAULT;
  public List<PlotSize> allowedSizes = List.of(
      new PlotSize(16, 12), new PlotSize(12, 8), new PlotSize(10, 8)
  );
  public int minCount = 1;
  public int maxCount = Integer.MAX_VALUE;
  private boolean built;

  public TerraCityRegionBuilder(TerraCityRegionCatalog catalog, NationBuilder nation, String zhCn) {
    this.catalog = Objects.requireNonNull(catalog, "城区目录不能为空");
    this.nation = Objects.requireNonNull(nation, "城区所属国家不能为空");
    this.zhCn = Objects.requireNonNull(zhCn, "城区中文名不能为空");
    this.enUs = zhCn;
  }

  public TerraCityRegionBuilder enUs(String enUs) {
    this.enUs = Objects.requireNonNull(enUs, "城区英文名不能为空");
    return this;
  }

  public TerraCityRegionBuilder weight(int weight) {
    if (weight <= 0) throw new IllegalArgumentException("城区权重必须为正数：" + zhCn);
    this.weight = weight;
    return this;
  }

  public TerraCityRegionBuilder buildingLayout(Layout buildingLayout) {
    this.buildingLayout = Objects.requireNonNull(buildingLayout, "城区建筑布局不能为空：" + zhCn);
    return this;
  }

  /** 注册时明确声明 Region 内部道路拓扑；生成器不得根据名称推断。 */
  public TerraCityRegionBuilder regionLayout(RegionLayoutType regionLayoutType) {
    this.regionLayoutType = Objects.requireNonNull(regionLayoutType, "Region 内部布局类型不能为空：" + zhCn);
    if (regionLayoutType == RegionLayoutType.SPINE || regionLayoutType == RegionLayoutType.CAMPUS
        || regionLayoutType == RegionLayoutType.HYBRID) {
      throw new IllegalArgumentException("该 Region 内部布局尚未实现：" + regionLayoutType + "/" + zhCn);
    }
    return this;
  }

  public TerraCityRegionBuilder roadConfig(RoadConfig roadConfig) {
    this.roadConfig = Objects.requireNonNull(roadConfig, "Region 道路配置不能为空：" + zhCn);
    return this;
  }

  public TerraCityRegionBuilder plotSizes(PlotSize... allowedSizes) {
    List<PlotSize> declared = List.copyOf(Arrays.asList(Objects.requireNonNull(
        allowedSizes, "移动地块尺寸清单不能为空"
    )));
    if (declared.isEmpty() || declared.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("移动地块至少需要一个合法离散尺寸：" + zhCn);
    }
    if (declared.stream().anyMatch(size -> size.areaChunks() < MIN_PLOT_AREA_CHUNKS)) {
      throw new IllegalArgumentException(
          "移动地块面积不得小于 " + MIN_PLOT_AREA_CHUNKS + " Chunk：" + zhCn
      );
    }
    this.allowedSizes = declared;
    return this;
  }

  public TerraCityRegionBuilder countRange(int minCount, int maxCount) {
    if (minCount < 1 || maxCount < minCount) {
      throw new IllegalArgumentException("移动地块类型数量范围无效：" + zhCn);
    }
    this.minCount = minCount;
    this.maxCount = maxCount;
    return this;
  }

  public TerraCityRegionBuilder unique() {
    return unique(true);
  }

  public TerraCityRegionBuilder unique(boolean unique) {
    this.unique = unique;
    return this;
  }

  public TerraCityRegionBuilder buildings(JigsawBuilder... buildings) {
    this.buildings = Arrays.stream(Objects.requireNonNull(buildings, "城区合法建筑清单不能为空"))
        .map(TerraCityRegionBuilding::repeatable)
        .toList();
    return this;
  }

  public TerraCityRegionBuilder building(JigsawBuilder building) {
    return building(building, 1, false);
  }

  public TerraCityRegionBuilder building(JigsawBuilder building, int weight, boolean unique) {
    ArrayList<TerraCityRegionBuilding> declared = new ArrayList<>(buildings);
    declared.add(new TerraCityRegionBuilding(building, weight, unique));
    this.buildings = List.copyOf(declared);
    return this;
  }

  public TerraCityRegionBuilder build() {
    if (built) throw new IllegalStateException("城区 builder 不能重复 build：" + zhCn);
    if (regionLayoutType == null) {
      throw new IllegalStateException("Region 注册时必须声明内部布局类型：" + zhCn);
    }
    if (allowedSizes.stream().anyMatch(size -> size.areaChunks() < MIN_PLOT_AREA_CHUNKS)) {
      throw new IllegalStateException(
          "移动地块面积不得小于 " + MIN_PLOT_AREA_CHUNKS + " Chunk：" + zhCn
      );
    }
    catalog.register(this);
    built = true;
    return this;
  }

  public boolean belongsTo(TerraCityRegionCatalog catalog) {
    return this.catalog == catalog;
  }

  public void bindId(String id) {
    if (this.id != null) throw new IllegalStateException("城区 ID 已绑定：" + this.id);
    this.id = Objects.requireNonNull(id, "城区 ID 不能为空");
  }

  @Override
  public String id() {
    return Objects.requireNonNull(id, "城区尚未 build：" + zhCn);
  }

  @Override
  public String zhCn() {
    return zhCn;
  }

  @Override
  public String enUs() {
    return enUs;
  }

  @Override
  public String translationKey() {
    return "journeymap.zinecraft.region." + id().replace('/', '.');
  }

  public NationBuilder nation() {
    return nation;
  }

  @Override
  public int weight() {
    return weight;
  }

  @Override
  public boolean isUnique() {
    return unique;
  }

  public List<TerraCityRegionBuilding> buildings() {
    return buildings;
  }

  public Layout buildingLayout() {
    return buildingLayout;
  }

  public RegionLayoutType regionLayoutType() {
    return Objects.requireNonNull(regionLayoutType, "Region 尚未声明内部布局类型：" + zhCn);
  }

  public RoadConfig roadConfig() {
    return roadConfig;
  }

  public List<PlotSize> allowedSizes() {
    return allowedSizes;
  }

  public int minCount() {
    return minCount;
  }

  public int maxCount() {
    return unique ? Math.min(1, maxCount) : maxCount;
  }
}
