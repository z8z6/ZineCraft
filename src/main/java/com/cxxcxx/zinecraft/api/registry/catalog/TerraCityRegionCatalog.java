package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.nation.TerraCityRegionBuilding;
import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;

import java.util.*;

/**
 * 注册、索引并本地化泰拉城区。
 */
public final class TerraCityRegionCatalog {
  private final NationCatalog nations;
  private final StructureCatalog structures;
  private final TranslationCatalog translations;
  private final List<TerraCityRegionBuilder> mutableEntries = new ArrayList<>();
  public final List<TerraCityRegionBuilder> entries = Collections.unmodifiableList(mutableEntries);
  private final Map<String, TerraCityRegionBuilder> byId = new LinkedHashMap<>();
  private final Map<RegionName, TerraCityRegionBuilder> byName = new HashMap<>();

  public TerraCityRegionCatalog(NationCatalog nations, StructureCatalog structures, TranslationCatalog translations) {
    this.nations = Objects.requireNonNull(nations, "国家目录不能为空");
    this.structures = Objects.requireNonNull(structures, "结构目录不能为空");
    this.translations = Objects.requireNonNull(translations, "翻译目录不能为空");
  }

  public TerraCityRegionBuilder region(NationBuilder nation, String zhCn) {
    return new TerraCityRegionBuilder(this, nation, zhCn);
  }

  public TerraCityRegionBuilder register(TerraCityRegionBuilder builder) {
    Objects.requireNonNull(builder, "城区 builder 不能为空");
    if (!builder.belongsTo(this)) throw new IllegalArgumentException("城区 builder 不属于当前目录：" + builder.zhCn);
    if (!nations.entries().contains(builder.nation))
      throw new IllegalArgumentException("城区所属国家尚未注册：" + builder.nation.id());
    LinkedHashSet<JigsawBuilder> declaredBuildings = new LinkedHashSet<>();
    ArrayList<TerraCityRegionBuilding> legalBuildings = new ArrayList<>();
    for (TerraCityRegionBuilding declaration : builder.buildings) {
      Objects.requireNonNull(declaration, "城区合法建筑清单不能包含 null：" + builder.zhCn);
      JigsawBuilder building = declaration.building();
      Objects.requireNonNull(building, "城区合法建筑清单不能包含 null：" + builder.zhCn);
      if (!structures.buildings.contains(building) || !building.cityBuilding())
        throw new IllegalArgumentException("城区引用了尚未注册的建筑：" + building.path);
      if (!declaredBuildings.add(building)) throw new IllegalArgumentException("城区重复声明合法建筑：" + building.path);
      legalBuildings.add(declaration);
    }
    if (legalBuildings.isEmpty()) throw new IllegalArgumentException("城区至少需要一个合法建筑：" + builder.zhCn);
    String id = builder.nation.id() + "/region_" + Integer.toUnsignedString(builder.zhCn.hashCode(), 36);
    builder.bindId(id);
    if (byId.putIfAbsent(id, builder) != null) throw new IllegalArgumentException("泰拉城区 ID 重复：" + id);
    RegionName name = new RegionName(builder.nation(), builder.zhCn());
    if (byName.putIfAbsent(name, builder) != null) {
      byId.remove(id);
      throw new IllegalArgumentException("同一国家存在重复城区名称：" + builder.nation().id() + "/" + builder.zhCn());
    }
    builder.buildings = List.copyOf(legalBuildings);
    mutableEntries.add(builder);
    translations.add(builder.translationKey(), builder.zhCn(), builder.enUs());
    return builder;
  }

  public List<TerraCityRegionBuilder> entries() {
    return entries;
  }

  private record RegionName(NationBuilder nation, String zhCn) {
  }
}
