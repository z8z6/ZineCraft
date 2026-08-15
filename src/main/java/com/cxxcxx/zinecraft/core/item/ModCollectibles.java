package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.accessory.CollectibleEntry;
import com.cxxcxx.zinecraft.api.accessory.CollectiblePower;
import com.cxxcxx.zinecraft.api.accessory.CollectibleSpec;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.google.gson.Gson;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
  private static final List<CollectibleEntry> ALL;

  static {
    Zinecraft.INSTANCE
        .getTRANSLATIONS()
        .add("item.zinecraft.collectible.series", "集成战略「傀影与猩红孤钻」 · No.%s", "Integrated Strategies: Phantom & Crimson Solitaire · No.%s");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("item.zinecraft.collectible.original_effect", "原效果：%s", "Original effect: %s");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("item.zinecraft.collectible.minecraft_effect", "装备效果：%s", "Equipped effect: %s");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("curios.identifier.relic", "藏品", "Collectible");
    Pair[] _this_map_iv = new Pair[18];
    ModCollectibles modCollectibles = INSTANCE;
    Holder holder = Attributes.ARMOR;
    _this_map_iv[0] = Pair.of("rogue_1_relic_a11", attributeWithDefaults(modCollectibles, "盔甲值+3", "+3 Armor", holder, 3.0, null, 16, null));
    modCollectibles = INSTANCE;
    holder = Attributes.ARMOR;
    _this_map_iv[1] = Pair.of("rogue_1_relic_a12", attributeWithDefaults(modCollectibles, "盔甲值+5", "+5 Armor", holder, 5.0, null, 16, null));
    modCollectibles = INSTANCE;
    holder = Attributes.ARMOR;
    _this_map_iv[2] = Pair.of("rogue_1_relic_a13", attributeWithDefaults(modCollectibles, "盔甲值+7", "+7 Armor", holder, 7.0, null, 16, null));
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_DAMAGE;
    _this_map_iv[3] = Pair.of(
        "rogue_1_relic_a14",
        modCollectibles.attribute("装备者近战攻击伤害+15%", "The wearer gains +15% melee attack damage", holder, 0.15, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_DAMAGE;
    _this_map_iv[4] = Pair.of(
        "rogue_1_relic_a15",
        modCollectibles.attribute("装备者近战攻击伤害+25%", "The wearer gains +25% melee attack damage", holder, 0.25, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_DAMAGE;
    _this_map_iv[5] = Pair.of(
        "rogue_1_relic_a16",
        modCollectibles.attribute("装备者近战攻击伤害+35%", "The wearer gains +35% melee attack damage", holder, 0.35, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.MAX_HEALTH;
    _this_map_iv[6] = Pair.of(
        "rogue_1_relic_a20", modCollectibles.attribute("最大生命值+20%", "+20% maximum health", holder, 0.2, Operation.ADD_MULTIPLIED_BASE)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.MAX_HEALTH;
    _this_map_iv[7] = Pair.of(
        "rogue_1_relic_a21", modCollectibles.attribute("最大生命值+35%", "+35% maximum health", holder, 0.35, Operation.ADD_MULTIPLIED_BASE)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.MAX_HEALTH;
    _this_map_iv[8] = Pair.of(
        "rogue_1_relic_a22", modCollectibles.attribute("最大生命值+50%", "+50% maximum health", holder, 0.5, Operation.ADD_MULTIPLIED_BASE)
    );
    _this_map_iv[9] = Pair.of(
        "rogue_1_relic_a31",
        new ModCollectibles.PowerOverride("每秒回复1%的最大生命值", "Recover 1% of maximum health every second", new CollectiblePower.Regeneration(0.01F, 20))
    );
    modCollectibles = INSTANCE;
    CollectiblePower.AttributeBoost[] _i_f_map = new CollectiblePower.AttributeBoost[2];
    ModCollectibles modCollectibles1 = INSTANCE;
    Holder holder1 = Attributes.ATTACK_DAMAGE;
    _i_f_map[0] = modCollectibles1.boost(holder1, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ARMOR;
    _i_f_map[1] = boostWithDefaults(modCollectibles1, holder1, 10.0, null, 4, null);
    _this_map_iv[10] = Pair.of(
        "rogue_1_relic_p05", modCollectibles.attributes("装备者近战攻击伤害+50%，盔甲值+10", "The wearer gains +50% melee attack damage and +10 Armor", _i_f_map)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_DAMAGE;
    _this_map_iv[11] = Pair.of(
        "rogue_1_relic_p07",
        modCollectibles.attribute("装备者近战攻击伤害+25%", "The wearer gains +25% melee attack damage", holder, 0.25, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    _i_f_map = new CollectiblePower.AttributeBoost[3];
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ARMOR;
    _i_f_map[0] = boostWithDefaults(modCollectibles1, holder1, -8.0, null, 4, null);
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ATTACK_DAMAGE;
    _i_f_map[1] = modCollectibles1.boost(holder1, 0.4, Operation.ADD_MULTIPLIED_TOTAL);
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ATTACK_SPEED;
    _i_f_map[2] = modCollectibles1.boost(holder1, 0.3, Operation.ADD_MULTIPLIED_TOTAL);
    _this_map_iv[12] = Pair.of(
        "rogue_1_relic_p10",
        modCollectibles.attributes(
            "装备者盔甲值-8，近战攻击伤害+40%，攻击速度+30%", "The wearer loses 8 Armor but gains +40% melee attack damage and +30% attack speed", _i_f_map
        )
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_DAMAGE;
    _this_map_iv[13] = Pair.of(
        "rogue_1_relic_p12",
        modCollectibles.attribute("装备者近战攻击伤害+60%", "The wearer gains +60% melee attack damage", holder, 0.6, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    _i_f_map = new CollectiblePower.AttributeBoost[2];
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ARMOR;
    _i_f_map[0] = boostWithDefaults(modCollectibles1, holder1, 5.0, null, 4, null);
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.MAX_HEALTH;
    _i_f_map[1] = modCollectibles1.boost(holder1, 0.5, Operation.ADD_MULTIPLIED_BASE);
    _this_map_iv[14] = Pair.of(
        "rogue_1_relic_p13", modCollectibles.attributes("装备者盔甲值+5，最大生命值+50%", "The wearer gains +5 Armor and +50% maximum health", _i_f_map)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_SPEED;
    _this_map_iv[15] = Pair.of(
        "rogue_1_relic_p20", modCollectibles.attribute("装备者攻击速度+70%", "The wearer gains +70% attack speed", holder, 0.7, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_SPEED;
    _this_map_iv[16] = Pair.of(
        "rogue_1_relic_p23", modCollectibles.attribute("装备者攻击速度+40%", "The wearer gains +40% attack speed", holder, 0.4, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    _i_f_map = new CollectiblePower.AttributeBoost[2];
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ATTACK_DAMAGE;
    _i_f_map[0] = modCollectibles1.boost(holder1, 0.4, Operation.ADD_MULTIPLIED_TOTAL);
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ARMOR;
    _i_f_map[1] = boostWithDefaults(modCollectibles1, holder1, 8.0, null, 4, null);
    _this_map_iv[17] = Pair.of(
        "rogue_1_relic_p38", modCollectibles.attributes("装备者近战攻击伤害+40%，盔甲值+8", "The wearer gains +40% melee attack damage and +8 Armor", _i_f_map)
    );
    powerOverrides = com.cxxcxx.zinecraft.api.util.CollectionSupport.mapOf(_this_map_iv);
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
        powerOverride1 = new ModCollectibles.PowerOverride(
            "仅保留原效果与藏品资料，暂未映射为 Minecraft 玩法",
            "Original effect and archive text preserved; no Minecraft adaptation yet",
            CollectiblePower.ArchiveOnly.INSTANCE
        );
      }

      ModCollectibles.PowerOverride powerOverride = powerOverride1;
      collection1.add(
          Zinecraft.INSTANCE
              .getCOLLECTIBLES()
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

  static CollectiblePower.AttributeBoost boostWithDefaults(ModCollectibles var0, Holder var1, double var2, Operation var4, int var5, Object var6) {
    if ((var5 & 4) != 0) {
      var4 = Operation.ADD_VALUE;
    }

    return var0.boost(var1, var2, var4);
  }

  static ModCollectibles.PowerOverride attributeWithDefaults(
      ModCollectibles var0, String var1, String var2, Holder var3, double var4, Operation var6, int var7, Object var8
  ) {
    if ((var7 & 16) != 0) {
      var6 = Operation.ADD_VALUE;
    }

    return var0.attribute(var1, var2, var3, var4, var6);
  }

  @NotNull
  public final List<CollectibleEntry> getALL() {
    return ALL;
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

  private final CollectiblePower.AttributeBoost boost(Holder<Attribute> attribute, double amount, Operation operation) {
    return new CollectiblePower.AttributeBoost(attribute, amount, operation);
  }

  private final ModCollectibles.PowerOverride attribute(
      String minecraftEffectZhCn, String minecraftEffectEnUs, Holder<Attribute> attribute, double amount, Operation operation
  ) {
    return new ModCollectibles.PowerOverride(minecraftEffectZhCn, minecraftEffectEnUs, new CollectiblePower.AttributeBoost(attribute, amount, operation));
  }

  private final ModCollectibles.PowerOverride attributes(String minecraftEffectZhCn, String minecraftEffectEnUs, CollectiblePower.AttributeBoost... boosts) {
    return new ModCollectibles.PowerOverride(minecraftEffectZhCn, minecraftEffectEnUs, new CollectiblePower.AttributeSet(java.util.List.of(boosts)));
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
