package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.accessory.CollectibleEntry;
import com.cxxcxx.zinecraft.api.accessory.CollectiblePower;
import com.cxxcxx.zinecraft.api.accessory.CollectibleSpec;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.google.gson.Gson;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
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
    Pair[] $this$map$iv = new Pair[18];
    ModCollectibles modCollectibles = INSTANCE;
    Holder holder = Attributes.ARMOR;
    $this$map$iv[0] = TuplesKt.to("rogue_1_relic_a11", attribute$default(modCollectibles, "盔甲值+3", "+3 Armor", holder, 3.0, null, 16, null));
    modCollectibles = INSTANCE;
    holder = Attributes.ARMOR;
    $this$map$iv[1] = TuplesKt.to("rogue_1_relic_a12", attribute$default(modCollectibles, "盔甲值+5", "+5 Armor", holder, 5.0, null, 16, null));
    modCollectibles = INSTANCE;
    holder = Attributes.ARMOR;
    $this$map$iv[2] = TuplesKt.to("rogue_1_relic_a13", attribute$default(modCollectibles, "盔甲值+7", "+7 Armor", holder, 7.0, null, 16, null));
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_DAMAGE;
    $this$map$iv[3] = TuplesKt.to(
        "rogue_1_relic_a14",
        modCollectibles.attribute("装备者近战攻击伤害+15%", "The wearer gains +15% melee attack damage", holder, 0.15, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_DAMAGE;
    $this$map$iv[4] = TuplesKt.to(
        "rogue_1_relic_a15",
        modCollectibles.attribute("装备者近战攻击伤害+25%", "The wearer gains +25% melee attack damage", holder, 0.25, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_DAMAGE;
    $this$map$iv[5] = TuplesKt.to(
        "rogue_1_relic_a16",
        modCollectibles.attribute("装备者近战攻击伤害+35%", "The wearer gains +35% melee attack damage", holder, 0.35, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.MAX_HEALTH;
    $this$map$iv[6] = TuplesKt.to(
        "rogue_1_relic_a20", modCollectibles.attribute("最大生命值+20%", "+20% maximum health", holder, 0.2, Operation.ADD_MULTIPLIED_BASE)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.MAX_HEALTH;
    $this$map$iv[7] = TuplesKt.to(
        "rogue_1_relic_a21", modCollectibles.attribute("最大生命值+35%", "+35% maximum health", holder, 0.35, Operation.ADD_MULTIPLIED_BASE)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.MAX_HEALTH;
    $this$map$iv[8] = TuplesKt.to(
        "rogue_1_relic_a22", modCollectibles.attribute("最大生命值+50%", "+50% maximum health", holder, 0.5, Operation.ADD_MULTIPLIED_BASE)
    );
    $this$map$iv[9] = TuplesKt.to(
        "rogue_1_relic_a31",
        new ModCollectibles.PowerOverride("每秒回复1%的最大生命值", "Recover 1% of maximum health every second", new CollectiblePower.Regeneration(0.01F, 0, 2, null))
    );
    modCollectibles = INSTANCE;
    CollectiblePower.AttributeBoost[] $i$f$map = new CollectiblePower.AttributeBoost[2];
    ModCollectibles modCollectibles1 = INSTANCE;
    Holder holder1 = Attributes.ATTACK_DAMAGE;
    $i$f$map[0] = modCollectibles1.boost(holder1, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ARMOR;
    $i$f$map[1] = boost$default(modCollectibles1, holder1, 10.0, null, 4, null);
    $this$map$iv[10] = TuplesKt.to(
        "rogue_1_relic_p05", modCollectibles.attributes("装备者近战攻击伤害+50%，盔甲值+10", "The wearer gains +50% melee attack damage and +10 Armor", $i$f$map)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_DAMAGE;
    $this$map$iv[11] = TuplesKt.to(
        "rogue_1_relic_p07",
        modCollectibles.attribute("装备者近战攻击伤害+25%", "The wearer gains +25% melee attack damage", holder, 0.25, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    $i$f$map = new CollectiblePower.AttributeBoost[3];
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ARMOR;
    $i$f$map[0] = boost$default(modCollectibles1, holder1, -8.0, null, 4, null);
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ATTACK_DAMAGE;
    $i$f$map[1] = modCollectibles1.boost(holder1, 0.4, Operation.ADD_MULTIPLIED_TOTAL);
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ATTACK_SPEED;
    $i$f$map[2] = modCollectibles1.boost(holder1, 0.3, Operation.ADD_MULTIPLIED_TOTAL);
    $this$map$iv[12] = TuplesKt.to(
        "rogue_1_relic_p10",
        modCollectibles.attributes(
            "装备者盔甲值-8，近战攻击伤害+40%，攻击速度+30%", "The wearer loses 8 Armor but gains +40% melee attack damage and +30% attack speed", $i$f$map
        )
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_DAMAGE;
    $this$map$iv[13] = TuplesKt.to(
        "rogue_1_relic_p12",
        modCollectibles.attribute("装备者近战攻击伤害+60%", "The wearer gains +60% melee attack damage", holder, 0.6, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    $i$f$map = new CollectiblePower.AttributeBoost[2];
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ARMOR;
    $i$f$map[0] = boost$default(modCollectibles1, holder1, 5.0, null, 4, null);
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.MAX_HEALTH;
    $i$f$map[1] = modCollectibles1.boost(holder1, 0.5, Operation.ADD_MULTIPLIED_BASE);
    $this$map$iv[14] = TuplesKt.to(
        "rogue_1_relic_p13", modCollectibles.attributes("装备者盔甲值+5，最大生命值+50%", "The wearer gains +5 Armor and +50% maximum health", $i$f$map)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_SPEED;
    $this$map$iv[15] = TuplesKt.to(
        "rogue_1_relic_p20", modCollectibles.attribute("装备者攻击速度+70%", "The wearer gains +70% attack speed", holder, 0.7, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    holder = Attributes.ATTACK_SPEED;
    $this$map$iv[16] = TuplesKt.to(
        "rogue_1_relic_p23", modCollectibles.attribute("装备者攻击速度+40%", "The wearer gains +40% attack speed", holder, 0.4, Operation.ADD_MULTIPLIED_TOTAL)
    );
    modCollectibles = INSTANCE;
    $i$f$map = new CollectiblePower.AttributeBoost[2];
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ATTACK_DAMAGE;
    $i$f$map[0] = modCollectibles1.boost(holder1, 0.4, Operation.ADD_MULTIPLIED_TOTAL);
    modCollectibles1 = INSTANCE;
    holder1 = Attributes.ARMOR;
    $i$f$map[1] = boost$default(modCollectibles1, holder1, 8.0, null, 4, null);
    $this$map$iv[17] = TuplesKt.to(
        "rogue_1_relic_p38", modCollectibles.attributes("装备者近战攻击伤害+40%，盔甲值+8", "The wearer gains +40% melee attack damage and +8 Armor", $i$f$map)
    );
    powerOverrides = MapsKt.mapOf($this$map$iv);
    Iterable iterable1 = INSTANCE.loadCatalog();
    int k = 0;
    Iterable $this$mapTo$iv$iv = iterable1;
    var collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable1, 10));
    int i = 0;

    for (Object object : $this$mapTo$iv$iv) {
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
                      0,
                      12288,
                      null
                  )
              )
      );
    }

    ALL = (List<CollectibleEntry>) collection;
  }

  private ModCollectibles() {
  }

  // $VF: synthetic method
  static CollectiblePower.AttributeBoost boost$default(ModCollectibles var0, Holder var1, double var2, Operation var4, int var5, Object var6) {
    if ((var5 & 4) != 0) {
      var4 = Operation.ADD_VALUE;
    }

    return var0.boost(var1, var2, var4);
  }

  // $VF: synthetic method
  static ModCollectibles.PowerOverride attribute$default(
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
    InputStream inputStream3 = ModCollectibles.class.getResourceAsStream("/zinecraft/collectibles/phantom_crimson_solitaire.json");
    if (inputStream3 == null) {
      int l = 0;
      String string = "找不到藏品目录资源：/zinecraft/collectibles/phantom_crimson_solitaire.json；请运行 script/import_prts_is2_collectibles.py";
      throw new IllegalStateException(string.toString());
    }

    InputStream inputStream = inputStream3;
    var unknownOverrides = inputStream;
    Throwable $this$mapTo$iv = null;

    Iterable list1;
    try {
      InputStream inputStream2 = unknownOverrides;
      int i = 0;
      var p0 = new InputStreamReader(inputStream2, StandardCharsets.UTF_8);
      Throwable item$iv = null;

      List list2;
      try {
        InputStreamReader inputStreamReader1 = p0;
        int j = 0;
        Object object6 = new Gson().fromJson(inputStreamReader1, ModCollectibles.ImportedCollectible[].class);
        list2 = ArraysKt.toList((Object[]) object6);
      } catch (Throwable throwable2) {
        item$iv = throwable2;
        throw throwable2;
      } finally {
        CloseableKt.closeFinally(p0, item$iv);
      }

      list1 = list2;
    } catch (Throwable throwable3) {
      $this$mapTo$iv = throwable3;
      throw throwable3;
    } finally {
      CloseableKt.closeFinally(unknownOverrides, $this$mapTo$iv);
    }

    List list = (List) list1;
    if (list.size() != 245) {
      int v = 0;
      String string5 = "《傀影与猩红孤钻》藏品目录应有 245 件，实际为 " + list.size() + " 件";
      throw new IllegalArgumentException(string5.toString());
    }

    Iterable iterable = list;
    int m = 0;
    list1 = iterable;
    var collection3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
    int y = 0;

    for (Object object2 : list1) {
      ModCollectibles.ImportedCollectible importedCollectible2 = (ModCollectibles.ImportedCollectible) object2;
      Collection collection = collection3;
      int k = 0;
      collection.add(importedCollectible2.getPath());
    }

    if (CollectionsKt.distinct((Iterable & List) collection3).size() != list.size()) {
      int u = 0;
      String string4 = "藏品目录存在重复物品 ID";
      throw new IllegalArgumentException(string4.toString());
    }

    Iterable iterable1 = list;
    int n = 0;
    list1 = iterable1;
    collection3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable1, 10));
    y = 0;

    for (Object object3 : list1) {
      ModCollectibles.ImportedCollectible importedCollectible3 = (ModCollectibles.ImportedCollectible) object3;
      Collection collection4 = collection3;
      int bb = 0;
      collection4.add(importedCollectible3.getOrderId());
    }

    if (CollectionsKt.distinct((Iterable & List) collection3).size() != list.size()) {
      int t = 0;
      String string3 = "藏品目录存在重复档案编号";
      throw new IllegalArgumentException(string3.toString());
    }

    Iterable iterable2 = list;
    int o = 0;
    list1 = iterable2;
    collection3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable2, 10));
    y = 0;

    for (Object object4 : list1) {
      ModCollectibles.ImportedCollectible importedCollectible4 = (ModCollectibles.ImportedCollectible) object4;
      Collection collection5 = collection3;
      int bc = 0;
      collection5.add(importedCollectible4.getSourceId());
    }

    if (CollectionsKt.distinct((Iterable & List) collection3).size() != list.size()) {
      int s = 0;
      String string2 = "藏品目录存在重复来源 ID";
      throw new IllegalArgumentException(string2.toString());
    }

    Iterable iterable3 = list;
    int p = 0;
    list1 = iterable3;
    collection3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable3, 10));
    y = 0;

    for (Object object5 : list1) {
      ModCollectibles.ImportedCollectible importedCollectible5 = (ModCollectibles.ImportedCollectible) object5;
      Collection collection6 = collection3;
      int bd = 0;
      collection6.add(importedCollectible5.getIconId());
    }

    if (CollectionsKt.distinct((Iterable & List) collection3).size() != list.size()) {
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

    Set set = SetsKt.minus(set1, collection2);
    if (!set.isEmpty()) {
      int w = 0;
      String string6 = "玩法覆盖引用了不存在的来源 ID：" + CollectionsKt.sorted(set);
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
      if (charSequence != null && !StringsKt.isBlank(charSequence)) {
        charSequence = imported.getPath();
        if (new Regex("[a-z0-9_]+").matches(charSequence)) {
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
      if (charSequence1 != null && !StringsKt.isBlank(charSequence1)) {
        charSequence1 = imported.getOrderId();
        if (new Regex("(?:[0-9]{3}|PCS[0-9]{2})").matches(charSequence1)) {
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
      if (charSequence2 != null && !StringsKt.isBlank(charSequence2)) {
        charSequence2 = imported.getSourceId();
        if (new Regex("rogue_1_relic_[a-z0-9_]+").matches(charSequence2)) {
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
      if (charSequence3 != null && !StringsKt.isBlank(charSequence3)) {
        charSequence3 = imported.getIconId();
        if (new Regex("rogue_1_relic_[a-z0-9_]+").matches(charSequence3)) {
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
      if (charSequence4 != null && !StringsKt.isBlank(charSequence4)) {
        charSequence4 = imported.getEnUs();
        if (charSequence4 != null && !StringsKt.isBlank(charSequence4)) {
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
      if (charSequence5 != null && !StringsKt.isBlank(charSequence5)) {
        charSequence5 = imported.getOriginalEffectEnUs();
        if (charSequence5 != null && !StringsKt.isBlank(charSequence5)) {
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
      if (charSequence6 != null && !StringsKt.isBlank(charSequence6)) {
        charSequence6 = imported.getDescriptionEnUs();
        if (charSequence6 != null && !StringsKt.isBlank(charSequence6)) {
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
    return new ModCollectibles.PowerOverride(minecraftEffectZhCn, minecraftEffectEnUs, new CollectiblePower.AttributeSet(ArraysKt.toList(boosts)));
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

    // $VF: synthetic method
    public static ModCollectibles.ImportedCollectible copy$default(
        ModCollectibles.ImportedCollectible var0,
        String var1,
        String var2,
        String var3,
        String var4,
        String var5,
        String var6,
        String var7,
        String var8,
        String var9,
        String var10,
        String var11,
        int var12,
        Object var13
    ) {
      if ((var12 & 1) != 0) {
        var1 = var0.path;
      }

      if ((var12 & 2) != 0) {
        var2 = var0.orderId;
      }

      if ((var12 & 4) != 0) {
        var3 = var0.sourceId;
      }

      if ((var12 & 8) != 0) {
        var4 = var0.iconId;
      }

      if ((var12 & 16) != 0) {
        var5 = var0.zhCn;
      }

      if ((var12 & 32) != 0) {
        var6 = var0.enUs;
      }

      if ((var12 & 64) != 0) {
        var7 = var0.originalEffectZhCn;
      }

      if ((var12 & 128) != 0) {
        var8 = var0.originalEffectEnUs;
      }

      if ((var12 & 256) != 0) {
        var9 = var0.descriptionZhCn;
      }

      if ((var12 & 512) != 0) {
        var10 = var0.descriptionEnUs;
      }

      if ((var12 & 1024) != 0) {
        var11 = var0.rarity;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
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

    @NotNull
    public final String component1() {
      return this.path;
    }

    @NotNull
    public final String component2() {
      return this.orderId;
    }

    @NotNull
    public final String component3() {
      return this.sourceId;
    }

    @NotNull
    public final String component4() {
      return this.iconId;
    }

    @NotNull
    public final String component5() {
      return this.zhCn;
    }

    @NotNull
    public final String component6() {
      return this.enUs;
    }

    @NotNull
    public final String component7() {
      return this.originalEffectZhCn;
    }

    @NotNull
    public final String component8() {
      return this.originalEffectEnUs;
    }

    @NotNull
    public final String component9() {
      return this.descriptionZhCn;
    }

    @NotNull
    public final String component10() {
      return this.descriptionEnUs;
    }

    @NotNull
    public final String component11() {
      return this.rarity;
    }

    @NotNull
    public final ModCollectibles.ImportedCollectible copy(
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
      return new ModCollectibles.ImportedCollectible(
          path, orderId, sourceId, iconId, zhCn, enUs, originalEffectZhCn, originalEffectEnUs, descriptionZhCn, descriptionEnUs, rarity
      );
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

    // $VF: synthetic method
    public static ModCollectibles.PowerOverride copy$default(
        ModCollectibles.PowerOverride var0, String var1, String var2, CollectiblePower var3, int var4, Object var5
    ) {
      if ((var4 & 1) != 0) {
        var1 = var0.minecraftEffectZhCn;
      }

      if ((var4 & 2) != 0) {
        var2 = var0.minecraftEffectEnUs;
      }

      if ((var4 & 4) != 0) {
        var3 = var0.power;
      }

      return var0.copy(var1, var2, var3);
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

    @NotNull
    public final String component1() {
      return this.minecraftEffectZhCn;
    }

    @NotNull
    public final String component2() {
      return this.minecraftEffectEnUs;
    }

    @NotNull
    public final CollectiblePower component3() {
      return this.power;
    }

    @NotNull
    public final ModCollectibles.PowerOverride copy(@NotNull String minecraftEffectZhCn, @NotNull String minecraftEffectEnUs, @NotNull CollectiblePower power) {
      return new ModCollectibles.PowerOverride(minecraftEffectZhCn, minecraftEffectEnUs, power);
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

