package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;

import java.util.*;

/**
 * 注册、索引并本地化泰拉国家。
 */
public final class NationCatalog {
  private final TranslationCatalog translations;
  private final List<NationBuilder> mutableEntries = new ArrayList<>();
  public final List<NationBuilder> entries = Collections.unmodifiableList(mutableEntries);
  private final Map<String, NationBuilder> byId = new LinkedHashMap<>();

  public NationCatalog(TranslationCatalog translations) {
    this.translations = Objects.requireNonNull(translations, "翻译目录不能为空");
  }

  public NationBuilder nation(String id, String zhCn) {
    return new NationBuilder(this, id, zhCn);
  }

  public NationBuilder register(NationBuilder builder) {
    Objects.requireNonNull(builder, "国家 builder 不能为空");
    if (!builder.belongsTo(this)) throw new IllegalArgumentException("国家 builder 不属于当前目录：" + builder.id);
    if (builder.cities == null) throw new IllegalArgumentException("国家城市清单不能为空：" + builder.id);
    if (builder.relativePoints.isEmpty()) {
      throw new IllegalArgumentException("国家至少需要一个归一化坐标：" + builder.id);
    }
    if (builder.underground && (builder.size <= 0 || (builder.size & 1) != 0)) {
      throw new IllegalArgumentException("地下国家尺寸必须是正偶数：" + builder.id);
    }
    if (!builder.underground && builder.size != 0) {
      throw new IllegalArgumentException("只有地下国家可以声明固定尺寸：" + builder.id);
    }
    for (int index = 0; index < builder.relativePoints.size(); index++) {
      var point = builder.relativePoints.get(index);
      if (!Double.isFinite(point.x()) || !Double.isFinite(point.z())
          || Math.abs(point.x()) >= 1.0 || Math.abs(point.z()) >= 1.0) {
        throw new IllegalArgumentException("国家归一化坐标必须位于泰拉核心矩形内：" + builder.id);
      }
      if (index > 0 && point.equals(builder.relativePoints.get(index - 1))) {
        throw new IllegalArgumentException("国家折线不能包含连续重复顶点：" + builder.id);
      }
    }
    if (byId.putIfAbsent(builder.id(), builder) != null) {
      throw new IllegalArgumentException("泰拉国家 ID 重复：" + builder.id());
    }
    mutableEntries.add(builder);
    translations.add(builder.translationKey(), builder.zhCn(), builder.enUs());
    return builder;
  }

  public List<NationBuilder> entries() {
    return entries;
  }

  public Optional<NationBuilder> findById(String id) {
    if (id == null) return Optional.empty();
    return Optional.ofNullable(byId.get(id));
  }

  public NationBuilder requireById(String id) {
    return findById(id).orElseThrow(() -> new IllegalArgumentException("未知泰拉国家 ID：" + id));
  }
}
