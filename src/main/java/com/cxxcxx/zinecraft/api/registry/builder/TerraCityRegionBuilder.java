package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.nation.TerraCityRegionBuilding;
import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import com.cxxcxx.zinecraft.api.registry.catalog.TerraCityRegionCatalog;
import com.cxxcxx.zinecraft.api.world.layout.LayoutSlotCount;
import com.cxxcxx.zinecraft.api.world.layout.WeightedLayoutElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 泰拉城区及其合法建筑声明构建器。
 */
public final class TerraCityRegionBuilder implements TerraPlace, WeightedLayoutElement {
  public final NationBuilder nation;
  private String id;
  public final String zhCn;
  private final TerraCityRegionCatalog catalog;
  public String enUs;
  public int weight = 1;
  public boolean unique;
  public List<TerraCityRegionBuilding> buildings = List.of();
  public LayoutSlotCount slotCount = LayoutSlotCount.SLOTS_100;
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

  public TerraCityRegionBuilder slotCount(LayoutSlotCount slotCount) {
    this.slotCount = Objects.requireNonNull(slotCount, "城区槽位数量不能为空：" + zhCn);
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

  public LayoutSlotCount slotCount() {
    return slotCount;
  }
}
