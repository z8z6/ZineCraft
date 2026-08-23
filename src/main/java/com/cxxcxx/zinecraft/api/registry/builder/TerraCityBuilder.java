package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import com.cxxcxx.zinecraft.api.registry.catalog.TerraCityCatalog;
import com.cxxcxx.zinecraft.api.world.layout.GridLayout;
import com.cxxcxx.zinecraft.api.world.layout.Layout;
import com.cxxcxx.zinecraft.api.world.layout.LayoutSlotCount;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 泰拉城市声明构建器。
 */
public final class TerraCityBuilder implements TerraPlace {
  public double relativeX;
  public final String zhCn;
  private final TerraCityCatalog catalog;
  public String declaredId;
  public String enUs;
  public double relativeZ;
  public int rotationDegrees;
  public Supplier<? extends Collection<TerraCityRegionBuilder>> regions;
  public Layout regionLayout = GridLayout.INSTANCE;
  public LayoutSlotCount slotCount = LayoutSlotCount.SLOTS_5;
  public int minPlotCount = 10;
  public int maxPlotCount = 100;
  public double maxPlotCoverage = 0.45;
  public int roadWidthChunks = 1;
  public int candidateCount = 16;
  private String id;
  private boolean built;

  public TerraCityBuilder(TerraCityCatalog catalog, String zhCn) {
    this.catalog = Objects.requireNonNull(catalog, "城市目录不能为空");
    this.zhCn = Objects.requireNonNull(zhCn, "城市中文名不能为空");
    this.enUs = zhCn;
  }

  public TerraCityBuilder enUs(String enUs) {
    this.enUs = Objects.requireNonNull(enUs, "城市英文名不能为空");
    return this;
  }

  /** 声明使用城市英文名转换成的稳定 snake_case ID。 */
  public TerraCityBuilder id(String id) {
    this.declaredId = Objects.requireNonNull(id, "城市 ID 不能为空");
    return this;
  }

  /**
   * 声明城市在所属国家边界内的归一化相对坐标。
   */
  public TerraCityBuilder position(double relativeX, double relativeZ) {
    this.relativeX = relativeX;
    this.relativeZ = relativeZ;
    return this;
  }

  public TerraCityBuilder rotation(int rotationDegrees) {
    this.rotationDegrees = rotationDegrees;
    return this;
  }

  public TerraCityBuilder regionLayout(Layout regionLayout) {
    this.regionLayout = Objects.requireNonNull(regionLayout, "城市子区域布局不能为空：" + zhCn);
    return this;
  }

  public TerraCityBuilder slotCount(LayoutSlotCount slotCount) {
    this.slotCount = Objects.requireNonNull(slotCount, "城市槽位数量不能为空：" + zhCn);
    return this;
  }

  /** 保留旧 API；新布局请使用 {@link #plotCountRange(int, int)}。 */
  public TerraCityBuilder maxPlotCount(LayoutSlotCount maxPlotCount) {
    this.maxPlotCount = Objects.requireNonNull(maxPlotCount, "移动地块总数不能为空").count();
    if (minPlotCount > this.maxPlotCount) minPlotCount = this.maxPlotCount;
    return this;
  }

  public TerraCityBuilder plotCountRange(int minPlotCount, int maxPlotCount) {
    if (minPlotCount < 1 || maxPlotCount < minPlotCount) {
      throw new IllegalArgumentException("城市移动地块数量范围无效：" + zhCn);
    }
    this.minPlotCount = minPlotCount;
    this.maxPlotCount = maxPlotCount;
    return this;
  }

  public TerraCityBuilder maxPlotCoverage(double maxPlotCoverage) {
    if (!Double.isFinite(maxPlotCoverage) || maxPlotCoverage <= 0.0 || maxPlotCoverage > 1.0) {
      throw new IllegalArgumentException("移动地块覆盖率必须位于 (0, 1]：" + zhCn);
    }
    this.maxPlotCoverage = maxPlotCoverage;
    return this;
  }

  public TerraCityBuilder roadWidthChunks(int roadWidthChunks) {
    if (roadWidthChunks <= 0) throw new IllegalArgumentException("道路宽度必须为正数：" + zhCn);
    this.roadWidthChunks = roadWidthChunks;
    return this;
  }

  public TerraCityBuilder candidateCount(int candidateCount) {
    if (candidateCount <= 0) throw new IllegalArgumentException("候选数量必须为正数：" + zhCn);
    this.candidateCount = candidateCount;
    return this;
  }

  public TerraCityBuilder regions(TerraCityRegionBuilder... regions) {
    List<TerraCityRegionBuilder> declared = List.copyOf(Arrays.asList(Objects.requireNonNull(regions, "城市城区清单不能为空")));
    return regions(() -> declared);
  }

  public TerraCityBuilder regions(Supplier<? extends Collection<TerraCityRegionBuilder>> regions) {
    this.regions = Objects.requireNonNull(regions, "城市城区清单不能为空：" + zhCn);
    return this;
  }

  public TerraCityBuilder build() {
    if (built) throw new IllegalStateException("城市 builder 不能重复 build：" + zhCn);
    catalog.register(this);
    built = true;
    return this;
  }

  public boolean belongsTo(TerraCityCatalog catalog) {
    return this.catalog == catalog;
  }

  public void bindId(String id) {
    if (this.id != null) throw new IllegalStateException("城市 ID 已绑定：" + this.id);
    this.id = Objects.requireNonNull(id, "城市 ID 不能为空");
  }

  @Override
  public String id() {
    return Objects.requireNonNull(id, "城市尚未 build：" + zhCn);
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
    return "journeymap.zinecraft.city." + id();
  }

  public List<TerraCityRegionBuilder> regions() {
    return List.copyOf(Objects.requireNonNull(regions.get(), "城市城区清单不能为空：" + id()));
  }

  public double relativeX() {
    return relativeX;
  }

  public double relativeZ() {
    return relativeZ;
  }

  public int rotationDegrees() {
    return Math.floorMod(rotationDegrees, 360);
  }

  public Layout regionLayout() {
    return regionLayout;
  }

  public LayoutSlotCount slotCount() {
    return slotCount;
  }

  public int minPlotCount() {
    return minPlotCount;
  }

  public int maxPlotCount() {
    return maxPlotCount;
  }

  public double maxPlotCoverage() {
    return maxPlotCoverage;
  }

  public int roadWidthChunks() {
    return roadWidthChunks;
  }

  public int candidateCount() {
    return candidateCount;
  }
}
