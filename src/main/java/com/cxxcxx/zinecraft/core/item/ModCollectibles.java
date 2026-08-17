package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.accessory.CollectibleEntry;
import com.cxxcxx.zinecraft.api.accessory.CollectiblePower;
import com.cxxcxx.zinecraft.api.accessory.CollectibleSpec;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.google.gson.Gson;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class ModCollectibles {
  @NotNull
  public static final ModCollectibles INSTANCE = new ModCollectibles();
  @NotNull
  private static final String CATALOG_RESOURCE = "/zinecraft/collectibles/phantom_crimson_solitaire.json";
  private static final int EXPECTED_COUNT = 245;
  @NotNull
  private static final Map<String, ModCollectibles.PowerOverride> powerOverrides;
  @NotNull
  public static final List<CollectibleEntry> ALL;

  static {
    Zinecraft.TRANSLATIONS
        .add("item.zinecraft.collectible.series", "集成战略「傀影与猩红孤钻」 · No.%s", "Integrated Strategies: Phantom & Crimson Solitaire · No.%s");
    Zinecraft.TRANSLATIONS.add("item.zinecraft.collectible.original_effect", "原效果：%s", "Original effect: %s");
    Zinecraft.TRANSLATIONS.add("item.zinecraft.collectible.minecraft_effect", "装备效果：%s", "Equipped effect: %s");
    Zinecraft.TRANSLATIONS.add("curios.identifier.relic", "藏品", "Collectible");
    Zinecraft.TRANSLATIONS.add("menu.tabs.curios", "饰品", "Accessories");
    Zinecraft.TRANSLATIONS.add("menu.tabs.attribute", "能力", "Abilities");
    powerOverrides = Map.ofEntries(
        Map.entry("rogue_1_relic_a11", INSTANCE.statPercent("防御力+15%", "+15% DEF", CombatStat.DEFENSE, 0.15)),
        Map.entry("rogue_1_relic_a12", INSTANCE.statPercent("防御力+25%", "+25% DEF", CombatStat.DEFENSE, 0.25)),
        Map.entry("rogue_1_relic_a13", INSTANCE.statPercent("防御力+35%", "+35% DEF", CombatStat.DEFENSE, 0.35)),
        Map.entry("rogue_1_relic_a14", INSTANCE.statPercent("攻击力+15%", "+15% ATK", CombatStat.ATTACK, 0.15)),
        Map.entry("rogue_1_relic_a15", INSTANCE.statPercent("攻击力+25%", "+25% ATK", CombatStat.ATTACK, 0.25)),
        Map.entry("rogue_1_relic_a16", INSTANCE.statPercent("攻击力+35%", "+35% ATK", CombatStat.ATTACK, 0.35)),
        Map.entry("rogue_1_relic_a20", INSTANCE.statPercent("最大生命值+20%", "+20% maximum HP", CombatStat.MAX_HEALTH, 0.20)),
        Map.entry("rogue_1_relic_a21", INSTANCE.statPercent("最大生命值+35%", "+35% maximum HP", CombatStat.MAX_HEALTH, 0.35)),
        Map.entry("rogue_1_relic_a22", INSTANCE.statPercent("最大生命值+50%", "+50% maximum HP", CombatStat.MAX_HEALTH, 0.50)),
        Map.entry(
            "rogue_1_relic_a31",
            new PowerOverride("每秒回复1%的最大生命值", "Recover 1% of maximum HP every second", new CollectiblePower.Regeneration(0.01F, 20))
        ),
        Map.entry("rogue_1_relic_p05", INSTANCE.stats(
            "攻击力+50%，防御力+50%", "+50% ATK and +50% DEF",
            percent(CombatStat.ATTACK, 0.50), percent(CombatStat.DEFENSE, 0.50)
        )),
        Map.entry("rogue_1_relic_p07", INSTANCE.statPercent("攻击力+25%", "+25% ATK", CombatStat.ATTACK, 0.25)),
        Map.entry("rogue_1_relic_p10", INSTANCE.stats(
            "防御力-40%，攻击力+40%，攻击速度+30", "-40% DEF, +40% ATK and +30 ASPD",
            percent(CombatStat.DEFENSE, -0.40), percent(CombatStat.ATTACK, 0.40), flat(CombatStat.ATTACK_SPEED, 30.0)
        )),
        Map.entry("rogue_1_relic_p12", INSTANCE.statPercent("攻击力+60%", "+60% ATK", CombatStat.ATTACK, 0.60)),
        Map.entry("rogue_1_relic_p13", INSTANCE.stats(
            "防御力+25%，最大生命值+50%", "+25% DEF and +50% maximum HP",
            percent(CombatStat.DEFENSE, 0.25), percent(CombatStat.MAX_HEALTH, 0.50)
        )),
        Map.entry("rogue_1_relic_p20", INSTANCE.statFlat("攻击速度+70", "+70 ASPD", CombatStat.ATTACK_SPEED, 70.0)),
        Map.entry("rogue_1_relic_p23", INSTANCE.statFlat("攻击速度+40", "+40 ASPD", CombatStat.ATTACK_SPEED, 40.0)),
        Map.entry("rogue_1_relic_p38", INSTANCE.stats(
            "攻击力+40%，防御力+40%", "+40% ATK and +40% DEF",
            percent(CombatStat.ATTACK, 0.40), percent(CombatStat.DEFENSE, 0.40)
        ))
    );
    Iterable iterable1 = INSTANCE.loadCatalog();
    int k = 0;
    Iterable _this_mapTo_iv_iv = iterable1;
    var collection = new ArrayList(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(iterable1, 10));
    int i = 0;

    for (Object object : _this_mapTo_iv_iv) {
      ModCollectibles.ImportedCollectible imported = (ModCollectibles.ImportedCollectible) object;
      Collection collection1 = collection;
      int j = 0;
      ModCollectibles.PowerOverride powerOverride1 = powerOverrides.get(imported.getSourceId());
      if (powerOverride1 == null) {
        var adaptation = CollectiblePowerAdapter.adapt(imported.getOriginalEffectZhCn());
        powerOverride1 = new ModCollectibles.PowerOverride(adaptation.zhCn(), adaptation.enUs(), adaptation.power());
      }

      ModCollectibles.PowerOverride powerOverride = powerOverride1;
      collection1.add(
          Zinecraft.COLLECTIBLES
              .register(
                  new CollectibleSpec(
                      imported.getPath(),
                      imported.getOrderId(),
                      imported.getZhCn(),
                      imported.getEnUs(),
                      imported.getOriginalEffectZhCn(),
                      imported.getOriginalEffectEnUs(),
                      imported.getDescriptionZhCn(),
                      imported.getDescriptionEnUs(),
                      powerOverride.getMinecraftEffectZhCn(),
                      powerOverride.getMinecraftEffectEnUs(),
                      powerOverride.getPower(),
                      INSTANCE.parseRarity(imported),
                      0,
                      0
                  )
              )
      );
    }

    ALL = (List<CollectibleEntry>) collection;
  }

  private ModCollectibles() {
  }

  private final List<ModCollectibles.ImportedCollectible> loadCatalog() {
    List<ImportedCollectible> list;
    try (InputStream input = ModCollectibles.class.getResourceAsStream(CATALOG_RESOURCE)) {
      if (input == null) {
        throw new IllegalStateException("找不到藏品目录资源：" + CATALOG_RESOURCE
            + "；请运行 script/import_prts_is2_collectibles.py");
      }
      try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
        ImportedCollectible[] imported = new Gson().fromJson(reader, ImportedCollectible[].class);
        list = List.of(imported);
      }
    } catch (java.io.IOException exception) {
      throw new IllegalStateException("读取藏品目录失败：" + CATALOG_RESOURCE, exception);
    }
    if (list.size() != 245) {
      int v = 0;
      String string5 = "《傀影与猩红孤钻》藏品目录应有 245 件，实际为 " + list.size() + " 件";
      throw new IllegalArgumentException(string5.toString());
    }

    Iterable iterable = list;
    int m = 0;
    Iterable list1 = iterable;
    var collection3 = new ArrayList(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(iterable, 10));
    int y = 0;

    for (Object object2 : list1) {
      ModCollectibles.ImportedCollectible importedCollectible2 = (ModCollectibles.ImportedCollectible) object2;
      Collection collection = collection3;
      int k = 0;
      collection.add(importedCollectible2.getPath());
    }

    if (com.cxxcxx.zinecraft.api.util.CollectionSupport.distinct((Iterable & List) collection3).size() != list.size()) {
      int u = 0;
      String string4 = "藏品目录存在重复物品 ID";
      throw new IllegalArgumentException(string4.toString());
    }

    Iterable iterable1 = list;
    int n = 0;
    list1 = iterable1;
    collection3 = new ArrayList(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(iterable1, 10));
    y = 0;

    for (Object object3 : list1) {
      ModCollectibles.ImportedCollectible importedCollectible3 = (ModCollectibles.ImportedCollectible) object3;
      Collection collection4 = collection3;
      int bb = 0;
      collection4.add(importedCollectible3.getOrderId());
    }

    if (com.cxxcxx.zinecraft.api.util.CollectionSupport.distinct((Iterable & List) collection3).size() != list.size()) {
      int t = 0;
      String string3 = "藏品目录存在重复档案编号";
      throw new IllegalArgumentException(string3.toString());
    }

    Iterable iterable2 = list;
    int o = 0;
    list1 = iterable2;
    collection3 = new ArrayList(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(iterable2, 10));
    y = 0;

    for (Object object4 : list1) {
      ModCollectibles.ImportedCollectible importedCollectible4 = (ModCollectibles.ImportedCollectible) object4;
      Collection collection5 = collection3;
      int bc = 0;
      collection5.add(importedCollectible4.getSourceId());
    }

    if (com.cxxcxx.zinecraft.api.util.CollectionSupport.distinct((Iterable & List) collection3).size() != list.size()) {
      int s = 0;
      String string2 = "藏品目录存在重复来源 ID";
      throw new IllegalArgumentException(string2.toString());
    }

    Iterable iterable3 = list;
    int p = 0;
    list1 = iterable3;
    collection3 = new ArrayList(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(iterable3, 10));
    y = 0;

    for (Object object5 : list1) {
      ModCollectibles.ImportedCollectible importedCollectible5 = (ModCollectibles.ImportedCollectible) object5;
      Collection collection6 = collection3;
      int bd = 0;
      collection6.add(importedCollectible5.getIconId());
    }

    if (com.cxxcxx.zinecraft.api.util.CollectionSupport.distinct((Iterable & List) collection3).size() != list.size()) {
      int r = 0;
      String string1 = "藏品目录存在重复图片 ID";
      throw new IllegalArgumentException(string1.toString());
    }

    Iterable iterable4 = list;
    int q = 0;

    for (Object object : iterable4) {
      ModCollectibles.ImportedCollectible importedCollectible = (ModCollectibles.ImportedCollectible) object;
      int z = 0;
      this.validateImported(importedCollectible);
    }

    Set set2 = powerOverrides.keySet();
    Iterable iterable5 = list;
    Collection collection2 = new HashSet();
    Set set1 = set2;
    int x = 0;

    for (Object object1 : iterable5) {
      ModCollectibles.ImportedCollectible importedCollectible1 = (ModCollectibles.ImportedCollectible) object1;
      Collection collection1 = collection2;
      int ba = 0;
      collection1.add(importedCollectible1.getSourceId());
    }

    Set set = com.cxxcxx.zinecraft.api.util.CollectionSupport.minus(set1, collection2);
    if (!set.isEmpty()) {
      int w = 0;
      String string6 = "玩法覆盖引用了不存在的来源 ID：" + com.cxxcxx.zinecraft.api.util.CollectionSupport.sorted(set);
      throw new IllegalArgumentException(string6.toString());
    } else {
      return list;
    }
  }

  private final void validateImported(ModCollectibles.ImportedCollectible imported) {
    boolean bl;
    label183:
    {
      CharSequence charSequence = imported.getPath();
      if (charSequence != null && !charSequence.toString().isBlank()) {
        charSequence = imported.getPath();
        if (charSequence.toString().matches("[a-z0-9_]+")) {
          bl = true;
          break label183;
        }
      }

      bl = false;
    }

    if (!bl) {
      int p = 0;
      String string7 = "藏品物品 ID 格式无效：" + imported.getPath();
      throw new IllegalArgumentException(string7.toString());
    }

    label172:
    {
      CharSequence charSequence1 = imported.getOrderId();
      if (charSequence1 != null && !charSequence1.toString().isBlank()) {
        charSequence1 = imported.getOrderId();
        if (charSequence1.toString().matches("(?:[0-9]{3}|PCS[0-9]{2})")) {
          bl = true;
          break label172;
        }
      }

      bl = false;
    }

    if (!bl) {
      int o = 0;
      String string6 = "藏品档案编号格式无效：" + imported.getOrderId();
      throw new IllegalArgumentException(string6.toString());
    }

    label161:
    {
      CharSequence charSequence2 = imported.getSourceId();
      if (charSequence2 != null && !charSequence2.toString().isBlank()) {
        charSequence2 = imported.getSourceId();
        if (charSequence2.toString().matches("rogue_1_relic_[a-z0-9_]+")) {
          bl = true;
          break label161;
        }
      }

      bl = false;
    }

    if (!bl) {
      int n = 0;
      String string5 = "藏品来源 ID 格式无效：" + imported.getSourceId();
      throw new IllegalArgumentException(string5.toString());
    }

    label150:
    {
      CharSequence charSequence3 = imported.getIconId();
      if (charSequence3 != null && !charSequence3.toString().isBlank()) {
        charSequence3 = imported.getIconId();
        if (charSequence3.toString().matches("rogue_1_relic_[a-z0-9_]+")) {
          bl = true;
          break label150;
        }
      }

      bl = false;
    }

    if (!bl) {
      int m = 0;
      String string4 = "藏品图片 ID 格式无效：" + imported.getIconId();
      throw new IllegalArgumentException(string4.toString());
    }

    if (!java.util.Objects.equals(imported.getSourceId(), imported.getIconId())) {
      int l = 0;
      String string3 = "藏品来源 ID 与图片 ID 不一致：" + imported.getSourceId();
      throw new IllegalArgumentException(string3.toString());
    }

    label137:
    {
      CharSequence charSequence4 = imported.getZhCn();
      if (charSequence4 != null && !charSequence4.toString().isBlank()) {
        charSequence4 = imported.getEnUs();
        if (charSequence4 != null && !charSequence4.toString().isBlank()) {
          bl = true;
          break label137;
        }
      }

      bl = false;
    }

    if (!bl) {
      int k = 0;
      String string2 = "藏品名称不能为空：" + imported.getSourceId();
      throw new IllegalArgumentException(string2.toString());
    }

    label121:
    {
      CharSequence charSequence5 = imported.getOriginalEffectZhCn();
      if (charSequence5 != null && !charSequence5.toString().isBlank()) {
        charSequence5 = imported.getOriginalEffectEnUs();
        if (charSequence5 != null && !charSequence5.toString().isBlank()) {
          bl = true;
          break label121;
        }
      }

      bl = false;
    }

    if (!bl) {
      int j = 0;
      String string1 = "藏品原效果不能为空：" + imported.getSourceId();
      throw new IllegalArgumentException(string1.toString());
    }

    label105:
    {
      CharSequence charSequence6 = imported.getDescriptionZhCn();
      if (charSequence6 != null && !charSequence6.toString().isBlank()) {
        charSequence6 = imported.getDescriptionEnUs();
        if (charSequence6 != null && !charSequence6.toString().isBlank()) {
          bl = true;
          break label105;
        }
      }

      bl = false;
    }

    if (!bl) {
      int i = 0;
      String string = "藏品原描述不能为空：" + imported.getSourceId();
      throw new IllegalArgumentException(string.toString());
    }

    this.parseRarity(imported);
  }

  private final Rarity parseRarity(ModCollectibles.ImportedCollectible imported) {
    try {
      return Rarity.valueOf(imported.getRarity());
    } catch (IllegalArgumentException error) {
      throw new IllegalArgumentException("藏品稀有度无效：" + imported.getSourceId() + "=" + imported.getRarity(), error);
    }
  }

  private static CollectiblePower.CombatStatBoost percent(CombatStat stat, double amount) {
    return new CollectiblePower.CombatStatBoost(CombatStatModifier.collectibleMultiplier(stat, amount));
  }

  private static CollectiblePower.CombatStatBoost flat(CombatStat stat, double amount) {
    return new CollectiblePower.CombatStatBoost(CombatStatModifier.collectibleAddition(stat, amount));
  }

  private PowerOverride statPercent(String zhCn, String enUs, CombatStat stat, double amount) {
    return new PowerOverride(zhCn, enUs, percent(stat, amount));
  }

  private PowerOverride statFlat(String zhCn, String enUs, CombatStat stat, double amount) {
    return new PowerOverride(zhCn, enUs, flat(stat, amount));
  }

  private PowerOverride stats(String zhCn, String enUs, CollectiblePower.CombatStatBoost... boosts) {
    return new PowerOverride(zhCn, enUs, new CollectiblePower.CombatStatSet(List.of(boosts)));
  }

  private static final class ImportedCollectible {
    @NotNull
    private final String path;
    @NotNull
    private final String orderId;
    @NotNull
    private final String sourceId;
    @NotNull
    private final String iconId;
    @NotNull
    private final String zhCn;
    @NotNull
    private final String enUs;
    @NotNull
    private final String originalEffectZhCn;
    @NotNull
    private final String originalEffectEnUs;
    @NotNull
    private final String descriptionZhCn;
    @NotNull
    private final String descriptionEnUs;
    @NotNull
    private final String rarity;

    public ImportedCollectible(
        @NotNull String path,
        @NotNull String orderId,
        @NotNull String sourceId,
        @NotNull String iconId,
        @NotNull String zhCn,
        @NotNull String enUs,
        @NotNull String originalEffectZhCn,
        @NotNull String originalEffectEnUs,
        @NotNull String descriptionZhCn,
        @NotNull String descriptionEnUs,
        @NotNull String rarity
    ) {
      super();
      this.path = path;
      this.orderId = orderId;
      this.sourceId = sourceId;
      this.iconId = iconId;
      this.zhCn = zhCn;
      this.enUs = enUs;
      this.originalEffectZhCn = originalEffectZhCn;
      this.originalEffectEnUs = originalEffectEnUs;
      this.descriptionZhCn = descriptionZhCn;
      this.descriptionEnUs = descriptionEnUs;
      this.rarity = rarity;
    }

    @NotNull
    public final String getPath() {
      return this.path;
    }

    @NotNull
    public final String getOrderId() {
      return this.orderId;
    }

    @NotNull
    public final String getSourceId() {
      return this.sourceId;
    }

    @NotNull
    public final String getIconId() {
      return this.iconId;
    }

    @NotNull
    public final String getZhCn() {
      return this.zhCn;
    }

    @NotNull
    public final String getEnUs() {
      return this.enUs;
    }

    @NotNull
    public final String getOriginalEffectZhCn() {
      return this.originalEffectZhCn;
    }

    @NotNull
    public final String getOriginalEffectEnUs() {
      return this.originalEffectEnUs;
    }

    @NotNull
    public final String getDescriptionZhCn() {
      return this.descriptionZhCn;
    }

    @NotNull
    public final String getDescriptionEnUs() {
      return this.descriptionEnUs;
    }

    @NotNull
    public final String getRarity() {
      return this.rarity;
    }

    @Override
    public int hashCode() {
      int i = this.path.hashCode();
      i = i * 31 + this.orderId.hashCode();
      i = i * 31 + this.sourceId.hashCode();
      i = i * 31 + this.iconId.hashCode();
      i = i * 31 + this.zhCn.hashCode();
      i = i * 31 + this.enUs.hashCode();
      i = i * 31 + this.originalEffectZhCn.hashCode();
      i = i * 31 + this.originalEffectEnUs.hashCode();
      i = i * 31 + this.descriptionZhCn.hashCode();
      i = i * 31 + this.descriptionEnUs.hashCode();
      return i * 31 + this.rarity.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
      if (this == other) {
        return true;
      } else if (!(other instanceof ModCollectibles.ImportedCollectible importedCollectible)) {
        return false;
      } else if (!java.util.Objects.equals(this.path, importedCollectible.path)) {
        return false;
      } else if (!java.util.Objects.equals(this.orderId, importedCollectible.orderId)) {
        return false;
      } else if (!java.util.Objects.equals(this.sourceId, importedCollectible.sourceId)) {
        return false;
      } else if (!java.util.Objects.equals(this.iconId, importedCollectible.iconId)) {
        return false;
      } else if (!java.util.Objects.equals(this.zhCn, importedCollectible.zhCn)) {
        return false;
      } else if (!java.util.Objects.equals(this.enUs, importedCollectible.enUs)) {
        return false;
      } else if (!java.util.Objects.equals(this.originalEffectZhCn, importedCollectible.originalEffectZhCn)) {
        return false;
      } else if (!java.util.Objects.equals(this.originalEffectEnUs, importedCollectible.originalEffectEnUs)) {
        return false;
      } else if (!java.util.Objects.equals(this.descriptionZhCn, importedCollectible.descriptionZhCn)) {
        return false;
      } else {
        return !java.util.Objects.equals(this.descriptionEnUs, importedCollectible.descriptionEnUs)
            ? false
            : java.util.Objects.equals(this.rarity, importedCollectible.rarity);
      }
    }

    @NotNull
    @Override
    public String toString() {
      return "ImportedCollectible(path="
          + this.path
          + ", orderId="
          + this.orderId
          + ", sourceId="
          + this.sourceId
          + ", iconId="
          + this.iconId
          + ", zhCn="
          + this.zhCn
          + ", enUs="
          + this.enUs
          + ", originalEffectZhCn="
          + this.originalEffectZhCn
          + ", originalEffectEnUs="
          + this.originalEffectEnUs
          + ", descriptionZhCn="
          + this.descriptionZhCn
          + ", descriptionEnUs="
          + this.descriptionEnUs
          + ", rarity="
          + this.rarity
          + ")";
    }
  }

  private static final class PowerOverride {
    @NotNull
    private final String minecraftEffectZhCn;
    @NotNull
    private final String minecraftEffectEnUs;
    @NotNull
    private final CollectiblePower power;

    public PowerOverride(@NotNull String minecraftEffectZhCn, @NotNull String minecraftEffectEnUs, @NotNull CollectiblePower power) {
      super();
      this.minecraftEffectZhCn = minecraftEffectZhCn;
      this.minecraftEffectEnUs = minecraftEffectEnUs;
      this.power = power;
    }

    @NotNull
    public final String getMinecraftEffectZhCn() {
      return this.minecraftEffectZhCn;
    }

    @NotNull
    public final String getMinecraftEffectEnUs() {
      return this.minecraftEffectEnUs;
    }

    @NotNull
    public final CollectiblePower getPower() {
      return this.power;
    }

    @Override
    public int hashCode() {
      int i = this.minecraftEffectZhCn.hashCode();
      i = i * 31 + this.minecraftEffectEnUs.hashCode();
      return i * 31 + this.power.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
      if (this == other) {
        return true;
      } else if (!(other instanceof ModCollectibles.PowerOverride powerOverride)) {
        return false;
      } else if (!java.util.Objects.equals(this.minecraftEffectZhCn, powerOverride.minecraftEffectZhCn)) {
        return false;
      } else {
        return !java.util.Objects.equals(this.minecraftEffectEnUs, powerOverride.minecraftEffectEnUs)
            ? false
            : java.util.Objects.equals(this.power, powerOverride.power);
      }
    }

    @NotNull
    @Override
    public String toString() {
      return "PowerOverride(minecraftEffectZhCn="
          + this.minecraftEffectZhCn
          + ", minecraftEffectEnUs="
          + this.minecraftEffectEnUs
          + ", power="
          + this.power
          + ")";
    }
  }
}
