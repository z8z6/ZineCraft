package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.nation.TerraCityRegionBuilding;
import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;

import java.util.*;

/**
 * 注册、索引并本地化泰拉城市。
 */
public final class TerraCityCatalog {
  private final NationCatalog nations;
  private final TranslationCatalog translations;
  private final List<TerraCityBuilder> mutableEntries = new ArrayList<>();
  public final List<TerraCityBuilder> entries = Collections.unmodifiableList(mutableEntries);
  private final Map<String, TerraCityBuilder> byId = new LinkedHashMap<>();
  private final Map<TerraCityBuilder, NationBuilder> owners = new LinkedHashMap<>();

  public TerraCityCatalog(NationCatalog nations, TranslationCatalog translations) {
    this.nations = Objects.requireNonNull(nations, "国家目录不能为空");
    this.translations = Objects.requireNonNull(translations, "翻译目录不能为空");
  }

  public TerraCityBuilder city(String zhCn) {
    return new TerraCityBuilder(this, zhCn);
  }

  public TerraCityBuilder register(TerraCityBuilder builder) {
    Objects.requireNonNull(builder, "城市 builder 不能为空");
    if (!builder.belongsTo(this)) throw new IllegalArgumentException("城市 builder 不属于当前目录：" + builder.zhCn);
    if (builder.enUs == null || builder.enUs.isBlank())
      throw new IllegalArgumentException("城市英文名不能为空：" + builder.zhCn);
    if (builder.regions == null) throw new IllegalArgumentException("城市城区清单不能为空：" + builder.zhCn);
    if (!Double.isFinite(builder.relativeX) || !Double.isFinite(builder.relativeZ)
        || Math.abs(builder.relativeX) >= 1.0 || Math.abs(builder.relativeZ) >= 1.0) {
      throw new IllegalArgumentException("城市归一化坐标必须位于所属国家边界内：" + builder.zhCn);
    }
    String id = "city_" + Integer.toUnsignedString(builder.zhCn.hashCode(), 36);
    builder.bindId(id);
    if (byId.putIfAbsent(id, builder) != null) throw new IllegalArgumentException("泰拉城市 ID 重复：" + id);
    mutableEntries.add(builder);
    translations.add(builder.translationKey(), builder.zhCn(), builder.enUs());
    return builder;
  }

  public List<TerraCityBuilder> entries() {
    return entries;
  }

  public List<TerraCityBuilder> citiesIn(NationBuilder nation) {
    return Objects.requireNonNull(nation, "国家不能为空").cities();
  }

  public TerraCityBuilder require(NationBuilder nation, String zhCn) {
    Objects.requireNonNull(nation, "城市所属国家不能为空");
    String name = Objects.requireNonNull(zhCn, "城市名称不能为空").strip();
    return nation.cities().stream()
        .filter(city -> city.zhCn().equals(name))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("泰拉城市目录不存在城市：" + nation.id() + "/" + zhCn));
  }

  public NationBuilder nationOf(TerraCityBuilder city) {
    NationBuilder nation = owners.get(Objects.requireNonNull(city, "城市不能为空"));
    if (nation == null) throw new IllegalStateException("城市尚未由国家声明：" + city.id());
    return nation;
  }

  public void validateOwnership(TerraCityRegionCatalog regions, StructureCatalog structures) {
    owners.clear();
    LinkedHashSet<TerraCityBuilder> assignedCities = new LinkedHashSet<>();
    LinkedHashSet<TerraCityRegionBuilder> assignedRegions = new LinkedHashSet<>();
    LinkedHashSet<JigsawBuilder> legalBuildings = new LinkedHashSet<>();
    for (NationBuilder nation : nations.entries()) {
      List<TerraCityBuilder> declaredCities = nation.cities();
      if (new LinkedHashSet<>(declaredCities).size() != declaredCities.size())
        throw new IllegalStateException("国家重复声明城市：" + nation.id());
      HashSet<String> cityNames = new HashSet<>();
      for (TerraCityBuilder city : declaredCities) {
        if (!entries.contains(city)) throw new IllegalStateException("国家引用了未注册城市：" + nation.id());
        if (!assignedCities.add(city)) throw new IllegalStateException("城市被多个国家声明：" + city.id());
        if (!cityNames.add(city.zhCn())) throw new IllegalStateException("同一国家存在重复城市名称：" + nation.id());
        owners.put(city, nation);
        List<TerraCityRegionBuilder> declaredRegions = city.regions();
        if (declaredRegions.isEmpty()) throw new IllegalStateException("城市至少需要一个城区：" + city.id());
        if (new LinkedHashSet<>(declaredRegions).size() != declaredRegions.size())
          throw new IllegalStateException("城市重复声明城区：" + city.id());
        for (TerraCityRegionBuilder region : declaredRegions) {
          if (!regions.entries().contains(region))
            throw new IllegalStateException("城市引用了未注册城区：" + city.id() + "/" + region.id());
          if (!nation.equals(region.nation()))
            throw new IllegalStateException("城市与城区不属于同一国家：" + city.id() + "/" + region.id());
          if (!assignedRegions.add(region)) throw new IllegalStateException("城区被多个城市声明：" + region.id());
          for (TerraCityRegionBuilding building : region.buildings()) {
            legalBuildings.add(building.building());
          }
        }
      }
    }
    if (!assignedCities.equals(new LinkedHashSet<>(entries)))
      throw new IllegalStateException("存在未由国家声明的城市");
    if (!assignedRegions.equals(new LinkedHashSet<>(regions.entries())))
      throw new IllegalStateException("存在未由城市声明的城区");
    if (!legalBuildings.equals(new LinkedHashSet<>(structures.buildings))) {
      LinkedHashSet<JigsawBuilder> missing = new LinkedHashSet<>(structures.buildings);
      missing.removeAll(legalBuildings);
      throw new IllegalStateException("存在未由城区声明的合法建筑：" + missing.stream().map(entry -> entry.path).toList());
    }
  }
}
