package com.cxxcxx.zinecraft.api.nation;

import org.jetbrains.annotations.Nullable;

import java.util.*;

public enum TerraNation {
  AEGIR("aegir", "阿戈尔", "Aegir"),
  BOLIVAR("bolivar", "玻利瓦尔", "Bolivar"),
  HIGASHI("higashi", "东国", "Higashi"),
  DURIN("durin", "杜林", "Durin"),
  COLUMBIA("columbia", "哥伦比亚", "Columbia"),
  KAZIMIERZ("kazimierz", "卡西米尔", "Kazimierz"),
  KAZDEL("kazdel", "卡兹戴尔", "Kazdel"),
  LATERANO("laterano", "拉特兰", "Laterano"),
  LEITHANIEN("leithanien", "莱塔尼亚", "Leithanien"),
  RIM_BILLITON("rim_billiton", "雷姆必拓", "Rim Billiton"),
  MINOS("minos", "米诺斯", "Minos"),
  SARGON("sargon", "萨尔贡", "Sargon"),
  SAMI("sami", "萨米", "Sami"),
  VICTORIA("victoria", "维多利亚", "Victoria"),
  URSUS("ursus", "乌萨斯", "Ursus"),
  KJERAG("kjerag", "谢拉格", "Kjerag"),
  SIRACUSA("siracusa", "叙拉古", "Siracusa"),
  YAN("yan", "炎", "Yan"),
  IBERIA("iberia", "伊比利亚", "Iberia");

  private static final List<TerraNation> ENTRIES = List.of(values());
  private static final Map<String, TerraNation> BY_ID = indexById();
  public static final Access ACCESS = new Access();
  private final String id;
  private final String zhCn;
  private final String enUs;

  TerraNation(String id, String zhCn, String enUs) {
    this.id = requireText(id, "国家 ID");
    this.zhCn = requireText(zhCn, "国家中文名");
    this.enUs = requireText(enUs, "国家英文名");
  }

  /**
   * @return 按枚举声明顺序排列的不可变十九国目录。
   */
  public static List<TerraNation> entries() {
    return ENTRIES;
  }

  /** 保留旧调用名称；新代码优先使用 {@link #entries()}。 */
  public static List<TerraNation> getEntries() {
    return entries();
  }

  public static Optional<TerraNation> findById(String id) {
    if (id == null) return Optional.empty();
    return Optional.ofNullable(BY_ID.get(id));
  }

  public static TerraNation requireById(String id) {
    return findById(id).orElseThrow(() -> new IllegalArgumentException("未知泰拉国家 ID：" + id));
  }

  public String getId() {
    return id;
  }

  public String getZhCn() {
    return zhCn;
  }

  public String getEnUs() {
    return enUs;
  }

  private static Map<String, TerraNation> indexById() {
    Map<String, TerraNation> nations = new LinkedHashMap<>();
    for (TerraNation nation : values()) {
      TerraNation previous = nations.put(nation.id, nation);
      if (previous != null) throw new IllegalStateException("泰拉国家 ID 重复：" + nation.id);
    }
    return Collections.unmodifiableMap(nations);
  }

  private static String requireText(String value, String field) {
    String text = Objects.requireNonNull(value, field + "不能为空").strip();
    if (text.isEmpty()) throw new IllegalArgumentException(field + "不能为空");
    return text;
  }

  public static final class Access {
    private Access() {
    }

    /**
     * 兼容旧 API；无法识别时返回 {@code null}。
     */
    @Nullable
    public TerraNation byId(String id) {
      return findById(id).orElse(null);
    }
  }
}
