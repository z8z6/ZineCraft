package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.combat.CombatDamageBasis;
import com.cxxcxx.zinecraft.api.combat.CombatDamageProfile;
import com.cxxcxx.zinecraft.api.registry.builder.ItemBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.SkillBuilder;
import com.cxxcxx.zinecraft.api.skill.SkillItem;
import net.minecraft.data.models.model.ModelTemplates;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 统一登记技能定义、技能物品、双语提示和 Ponder 文本的数据目录。
 */
public final class SkillCatalog {
  private static final Pattern PATH_PATTERN = Pattern.compile("[a-z0-9_]+");

  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final String namespace;
  private final List<SkillBuilder> mutableEntries = new ArrayList<>();
  public final List<SkillBuilder> entries = Collections.unmodifiableList(mutableEntries);

  public SkillCatalog(ItemCatalog items, TranslationCatalog translations) {
    this.items = Objects.requireNonNull(items, "items");
    this.translations = Objects.requireNonNull(translations, "translations");
    this.namespace = items.registry.getNamespace();
  }

  private static void validate(SkillBuilder builder) {
    if (!PATH_PATTERN.matcher(builder.path).matches()) {
      throw new IllegalArgumentException("技能 ID 必须是 snake_case：" + builder.path);
    }
    requireText(builder.zhCn, "技能中文名不能为空：" + builder.path);
    requireText(builder.enUs, "技能英文名不能为空：" + builder.path);
    requireText(builder.operatorZhCn, "干员中文名不能为空：" + builder.path);
    requireText(builder.operatorEnUs, "干员英文名不能为空：" + builder.path);
    Objects.requireNonNull(builder.profession, "技能职业不能为空：" + builder.path);
    requireText(builder.recoveryZhCn, "中文技力回复类型不能为空：" + builder.path);
    requireText(builder.recoveryEnUs, "英文技力回复类型不能为空：" + builder.path);
    requireText(builder.triggerZhCn, "中文触发方式不能为空：" + builder.path);
    requireText(builder.triggerEnUs, "英文触发方式不能为空：" + builder.path);
    if (!builder.statsConfigured) throw new IllegalArgumentException("技能数值尚未设置：" + builder.path);
    if (builder.initialSp < 0) throw new IllegalArgumentException("初始技力不能为负数：" + builder.path);
    if (builder.spCost < 0) throw new IllegalArgumentException("技力消耗不能为负数：" + builder.path);
    if (builder.durationSeconds != null && builder.durationSeconds <= 0) {
      throw new IllegalArgumentException("技能持续时间必须大于 0：" + builder.path);
    }
    requireText(builder.descriptionZhCn, "技能中文描述不能为空：" + builder.path);
    requireText(builder.descriptionEnUs, "技能英文描述不能为空：" + builder.path);
    Objects.requireNonNull(builder.theme, "技能演示主题不能为空：" + builder.path);
  }

  private static void requireText(String value, String message) {
    if (value == null || value.isBlank()) throw new IllegalArgumentException(message);
  }

  private static String damageText(List<CombatDamageProfile> profiles, boolean zhCn) {
    if (profiles.isEmpty()) return zhCn ? "伤害：无直接伤害" : "Damage: No direct damage";
    return (zhCn ? "伤害：" : "Damage: ") + profiles.stream()
        .map(profile -> damageSegment(profile, zhCn))
        .collect(java.util.stream.Collectors.joining(zhCn ? "；" : "; "));
  }

  private static String damageSegment(CombatDamageProfile profile, boolean zhCn) {
    String amount = profile.basis() == CombatDamageBasis.ATTACK_MULTIPLIER
        ? formatNumber(profile.amount() * 100.0) + (zhCn ? "%攻击力" : "% ATK")
        : formatNumber(profile.amount());
    String type = switch (profile.type()) {
      case PHYSICAL -> zhCn ? "物理伤害" : "Physical";
      case MAGIC -> zhCn ? "魔法伤害" : "Magic";
      case ARTS -> zhCn ? "法术伤害" : "Arts";
      case FIRE -> zhCn ? "火焰伤害" : "Fire";
      case ICE -> zhCn ? "冰霜伤害" : "Ice";
      case LIGHTNING -> zhCn ? "雷电伤害" : "Lightning";
      case POISON -> zhCn ? "毒素伤害" : "Poison";
      case TRUE -> zhCn ? "真实伤害" : "True";
    };
    return amount + " · " + type;
  }

  private static String formatNumber(double value) {
    return value == Math.rint(value)
        ? Long.toString(Math.round(value))
        : String.format(Locale.ROOT, "%.2f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
  }

  /**
   * 校验并登记技能定义、对应技能物品及其全部翻译。
   *
   * @param builder 技能声明
   * @return 已登记并绑定运行时定义与物品句柄的声明
   */
  public SkillBuilder register(SkillBuilder builder) {
    Objects.requireNonNull(builder, "技能 builder 不能为空");
    if (builder.catalog != this) {
      throw new IllegalArgumentException("技能 builder 不属于当前目录：" + builder.path);
    }
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("技能 ID 重复：" + builder.path);
    }
    validate(builder);

    DeferredItem<SkillItem> item = new ItemBuilder<>(
        items,
        builder.path,
        builder.zhCn,
        builder.enUs,
        () -> new SkillItem(builder),
        ModelTemplates.FLAT_ITEM,
        false
    ).build().getItem();

    registerTranslations(builder);
    builder.item = item;
    mutableEntries.add(builder);
    return builder;
  }

  private void registerTranslations(SkillBuilder skill) {
    String tooltipKey = "item." + namespace + "." + skill.path + ".tooltip";
    translations.add(
        tooltipKey + ".operator",
        "干员：" + skill.operatorZhCn + " · " + skill.profession.getZhCn(),
        "Operator: " + skill.operatorEnUs + " · " + skill.profession.getEnUs()
    );
    translations.add(
        tooltipKey + ".activation",
        skill.recoveryZhCn + " · " + skill.triggerZhCn,
        skill.recoveryEnUs + " · " + skill.triggerEnUs
    );

    String durationZhCn = skill.durationSeconds == null ? "" : " · 持续 " + skill.durationSeconds + "秒";
    String durationEnUs = skill.durationSeconds == null ? "" : " · Duration " + skill.durationSeconds + "s";
    String statsZhCn = "初始 " + skill.initialSp + " · 消耗 " + skill.spCost + durationZhCn;
    String statsEnUs = "Initial " + skill.initialSp + " · Cost " + skill.spCost + durationEnUs;
    translations.add(tooltipKey + ".stats", statsZhCn, statsEnUs);
    translations.add(
        tooltipKey + ".damage",
        damageText(skill.damageProfiles(), true),
        damageText(skill.damageProfiles(), false)
    );
    translations.add(tooltipKey + ".description", skill.descriptionZhCn, skill.descriptionEnUs);

    String ponderKey = namespace + ".ponder.skill_demo_" + skill.path;
    translations.add(
        ponderKey + ".header",
        skill.operatorZhCn + "：" + skill.zhCn,
        skill.operatorEnUs + ": " + skill.enUs
    );
    translations.add(
        ponderKey + ".text_1",
        skill.operatorZhCn + "的" + skill.profession.getZhCn() + "技能",
        "A " + skill.profession.getEnUs() + " skill used by " + skill.operatorEnUs
    );
    translations.add(
        ponderKey + ".text_2",
        skill.recoveryZhCn + " · " + skill.triggerZhCn,
        skill.recoveryEnUs + " · " + skill.triggerEnUs
    );
    translations.add(ponderKey + ".text_3", statsZhCn, statsEnUs);
    translations.add(
        ponderKey + ".text_4",
        damageText(skill.damageProfiles(), true),
        damageText(skill.damageProfiles(), false)
    );
    translations.add(ponderKey + ".text_5", skill.descriptionZhCn, skill.descriptionEnUs);
    translations.add(
        ponderKey + ".text_6",
        "演示为 Minecraft 机制化表达，技能资料取自 PRTS。",
        "This is a Minecraft interpretation based on PRTS skill data."
    );
  }
}
