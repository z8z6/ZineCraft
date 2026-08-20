package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.accessory.CollectibleCatalog;
import com.cxxcxx.zinecraft.api.accessory.CollectibleItem;
import com.cxxcxx.zinecraft.api.accessory.CollectiblePower;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.google.gson.Gson;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Registers all Integrated Strategies collectibles through the collectible builder API.
 */
public final class ModCollectible {
  private static final String CATALOG_RESOURCE = "/zinecraft/collectibles/phantom_crimson_solitaire.json";
  private static final int EXPECTED_COUNT = 245;

  public static final List<DeferredItem<CollectibleItem>> ALL;

  static {
    registerCommonTranslations();
    Map<String, PowerOverride> powerOverrides = createPowerOverrides();
    List<ImportedCollectible> imported = loadCatalog();
    validateCatalog(imported, powerOverrides);
    ALL = imported.stream().map(entry -> register(entry, powerOverrides)).toList();
  }

  private static void registerCommonTranslations() {
    Zinecraft.TRANSLATIONS.add(
        "item.zinecraft.collectible.series",
        "集成战略「傀影与猩红孤钻」 · No.%s",
        "Integrated Strategies: Phantom & Crimson Solitaire · No.%s"
    );
    Zinecraft.TRANSLATIONS.add("item.zinecraft.collectible.original_effect", "原效果：%s", "Original effect: %s");
    Zinecraft.TRANSLATIONS.add("item.zinecraft.collectible.minecraft_effect", "装备效果：%s", "Equipped effect: %s");
    Zinecraft.TRANSLATIONS.add("curios.identifier.relic", "藏品", "Collectible");
    Zinecraft.TRANSLATIONS.add("menu.tabs.curios", "饰品", "Accessories");
    Zinecraft.TRANSLATIONS.add("menu.tabs.attribute", "能力", "Abilities");
  }

  private static Map<String, PowerOverride> createPowerOverrides() {
    return Map.ofEntries(
        Map.entry("rogue_1_relic_a11", statPercent("防御力+15%", "+15% DEF", CombatStat.DEFENSE, 0.15)),
        Map.entry("rogue_1_relic_a12", statPercent("防御力+25%", "+25% DEF", CombatStat.DEFENSE, 0.25)),
        Map.entry("rogue_1_relic_a13", statPercent("防御力+35%", "+35% DEF", CombatStat.DEFENSE, 0.35)),
        Map.entry("rogue_1_relic_a14", statPercent("攻击力+15%", "+15% ATK", CombatStat.ATTACK, 0.15)),
        Map.entry("rogue_1_relic_a15", statPercent("攻击力+25%", "+25% ATK", CombatStat.ATTACK, 0.25)),
        Map.entry("rogue_1_relic_a16", statPercent("攻击力+35%", "+35% ATK", CombatStat.ATTACK, 0.35)),
        Map.entry("rogue_1_relic_a20", statPercent("最大生命值+20%", "+20% maximum HP", CombatStat.MAX_HEALTH, 0.20)),
        Map.entry("rogue_1_relic_a21", statPercent("最大生命值+35%", "+35% maximum HP", CombatStat.MAX_HEALTH, 0.35)),
        Map.entry("rogue_1_relic_a22", statPercent("最大生命值+50%", "+50% maximum HP", CombatStat.MAX_HEALTH, 0.50)),
        Map.entry(
            "rogue_1_relic_a31",
            new PowerOverride(
                "每秒回复1%的最大生命值",
                "Recover 1% of maximum HP every second",
                new CollectiblePower.Regeneration(0.01F, 20)
            )
        ),
        Map.entry("rogue_1_relic_p05", stats(
            "攻击力+50%，防御力+50%", "+50% ATK and +50% DEF",
            percent(CombatStat.ATTACK, 0.50), percent(CombatStat.DEFENSE, 0.50)
        )),
        Map.entry("rogue_1_relic_p07", statPercent("攻击力+25%", "+25% ATK", CombatStat.ATTACK, 0.25)),
        Map.entry("rogue_1_relic_p10", stats(
            "防御力-40%，攻击力+40%，攻击速度+30", "-40% DEF, +40% ATK and +30 ASPD",
            percent(CombatStat.DEFENSE, -0.40), percent(CombatStat.ATTACK, 0.40), flat(CombatStat.ATTACK_SPEED, 30.0)
        )),
        Map.entry("rogue_1_relic_p12", statPercent("攻击力+60%", "+60% ATK", CombatStat.ATTACK, 0.60)),
        Map.entry("rogue_1_relic_p13", stats(
            "防御力+25%，最大生命值+50%", "+25% DEF and +50% maximum HP",
            percent(CombatStat.DEFENSE, 0.25), percent(CombatStat.MAX_HEALTH, 0.50)
        )),
        Map.entry("rogue_1_relic_p20", statFlat("攻击速度+70", "+70 ASPD", CombatStat.ATTACK_SPEED, 70.0)),
        Map.entry("rogue_1_relic_p23", statFlat("攻击速度+40", "+40 ASPD", CombatStat.ATTACK_SPEED, 40.0)),
        Map.entry("rogue_1_relic_p38", stats(
            "攻击力+40%，防御力+40%", "+40% ATK and +40% DEF",
            percent(CombatStat.ATTACK, 0.40), percent(CombatStat.DEFENSE, 0.40)
        ))
    );
  }

  private static List<ImportedCollectible> loadCatalog() {
    try (InputStream input = ModCollectible.class.getResourceAsStream(CATALOG_RESOURCE)) {
      if (input == null) {
        throw new IllegalStateException("找不到藏品目录资源：" + CATALOG_RESOURCE
            + "；请运行 script/import_prts_is2_collectibles.py");
      }
      try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
        ImportedCollectible[] imported = new Gson().fromJson(reader, ImportedCollectible[].class);
        return List.of(imported);
      }
    } catch (IOException exception) {
      throw new IllegalStateException("读取藏品目录失败：" + CATALOG_RESOURCE, exception);
    }
  }

  private static void validateUnique(
      List<ImportedCollectible> imported,
      Function<ImportedCollectible, String> key,
      String label
  ) {
    Set<String> values = new HashSet<>();
    for (ImportedCollectible entry : imported) {
      if (!values.add(key.apply(entry))) throw new IllegalArgumentException("藏品目录存在重复" + label);
    }
  }

  private static void requireText(String value, String message) {
    if (value == null || value.isBlank()) throw new IllegalArgumentException(message);
  }

  private static Rarity parseRarity(ImportedCollectible imported) {
    try {
      return Rarity.valueOf(imported.rarity());
    } catch (IllegalArgumentException | NullPointerException error) {
      throw new IllegalArgumentException(
          "藏品稀有度无效：" + imported.sourceId() + "=" + imported.rarity(), error
      );
    }
  }

  private static PowerOverride statPercent(String zhCn, String enUs, CombatStat stat, double amount) {
    return new PowerOverride(zhCn, enUs, percent(stat, amount));
  }

  private static PowerOverride statFlat(String zhCn, String enUs, CombatStat stat, double amount) {
    return new PowerOverride(zhCn, enUs, flat(stat, amount));
  }

  private static PowerOverride stats(
      String zhCn,
      String enUs,
      CollectiblePower.CombatStatBoost... boosts
  ) {
    return new PowerOverride(zhCn, enUs, new CollectiblePower.CombatStatSet(List.of(boosts)));
  }

  private static CollectiblePower.CombatStatBoost percent(CombatStat stat, double amount) {
    return new CollectiblePower.CombatStatBoost(CombatStatModifier.collectibleMultiplier(stat, amount));
  }

  private static CollectiblePower.CombatStatBoost flat(CombatStat stat, double amount) {
    return new CollectiblePower.CombatStatBoost(CombatStatModifier.collectibleAddition(stat, amount));
  }

  private ModCollectible() {
  }

  private static DeferredItem<CollectibleItem> register(
      ImportedCollectible imported,
      Map<String, PowerOverride> powerOverrides
  ) {
    PowerOverride effect = powerOverrides.get(imported.sourceId());
    if (effect == null) {
      var adaptation = CollectiblePowerAdapter.adapt(imported.originalEffectZhCn());
      effect = new PowerOverride(adaptation.zhCn(), adaptation.enUs(), adaptation.power());
    }

    return new CollectibleCatalog.CollectibleBuilder(
        Zinecraft.COLLECTIBLES, imported.path(), imported.orderId(), imported.zhCn())
        .enUs(imported.enUs())
        .originalEffect(imported.originalEffectZhCn(), imported.originalEffectEnUs())
        .description(imported.descriptionZhCn(), imported.descriptionEnUs())
        .minecraftEffect(effect.zhCn(), effect.enUs(), effect.power())
        .rarity(parseRarity(imported))
        .build();
  }

  private static void validateCatalog(
      List<ImportedCollectible> imported,
      Map<String, PowerOverride> powerOverrides
  ) {
    if (imported.size() != EXPECTED_COUNT) {
      throw new IllegalArgumentException("《傀影与猩红孤钻》藏品目录应有 " + EXPECTED_COUNT
          + " 件，实际为 " + imported.size() + " 件");
    }

    validateUnique(imported, ImportedCollectible::path, "物品 ID");
    validateUnique(imported, ImportedCollectible::orderId, "档案编号");
    validateUnique(imported, ImportedCollectible::sourceId, "来源 ID");
    validateUnique(imported, ImportedCollectible::iconId, "图片 ID");
    imported.forEach(ModCollectible::validateImported);

    Set<String> sourceIds = new HashSet<>();
    imported.forEach(entry -> sourceIds.add(entry.sourceId()));
    Set<String> missing = new HashSet<>(powerOverrides.keySet());
    missing.removeAll(sourceIds);
    if (!missing.isEmpty()) {
      throw new IllegalArgumentException("玩法覆盖引用了不存在的来源 ID：" + missing.stream().sorted().toList());
    }
  }

  private record ImportedCollectible(
      String path,
      String orderId,
      String sourceId,
      String iconId,
      String zhCn,
      String enUs,
      String originalEffectZhCn,
      String originalEffectEnUs,
      String descriptionZhCn,
      String descriptionEnUs,
      String rarity
  ) {
  }

  private record PowerOverride(String zhCn, String enUs, CollectiblePower power) {
  }

  private static void validateImported(ImportedCollectible imported) {
    if (imported.path() == null || !imported.path().matches("[a-z0-9_]+")) {
      throw new IllegalArgumentException("藏品物品 ID 格式无效：" + imported.path());
    }
    if (imported.orderId() == null || !imported.orderId().matches("(?:[0-9]{3}|PCS[0-9]{2})")) {
      throw new IllegalArgumentException("藏品档案编号格式无效：" + imported.orderId());
    }
    if (imported.sourceId() == null || !imported.sourceId().matches("rogue_1_relic_[a-z0-9_]+")) {
      throw new IllegalArgumentException("藏品来源 ID 格式无效：" + imported.sourceId());
    }
    if (imported.iconId() == null || !imported.iconId().matches("rogue_1_relic_[a-z0-9_]+")) {
      throw new IllegalArgumentException("藏品图片 ID 格式无效：" + imported.iconId());
    }
    if (!imported.sourceId().equals(imported.iconId())) {
      throw new IllegalArgumentException("藏品来源 ID 与图片 ID 不一致：" + imported.sourceId());
    }
    requireText(imported.zhCn(), "藏品中文名不能为空：" + imported.sourceId());
    requireText(imported.enUs(), "藏品英文名不能为空：" + imported.sourceId());
    requireText(imported.originalEffectZhCn(), "藏品中文原效果不能为空：" + imported.sourceId());
    requireText(imported.originalEffectEnUs(), "藏品英文原效果不能为空：" + imported.sourceId());
    requireText(imported.descriptionZhCn(), "藏品中文描述不能为空：" + imported.sourceId());
    requireText(imported.descriptionEnUs(), "藏品英文描述不能为空：" + imported.sourceId());
    parseRarity(imported);
  }

  public static void bootstrap() {
  }
}
