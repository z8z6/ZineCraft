package com.cxxcxx.zinecraft.api.skill;

import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SkillCatalog {
  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final List<SkillEntry> mutableEntries = new ArrayList<>();

  public final List<SkillEntry> entries = Collections.unmodifiableList(mutableEntries);

  public SkillCatalog(ItemCatalog items, TranslationCatalog translations) {
    this.items = Objects.requireNonNull(items, "items");
    this.translations = Objects.requireNonNull(translations, "translations");
  }

  /**
   * 注册技能定义及其对应技能物品，并集中生成技能说明与 Ponder 文本。
   *
   * @param path            技能和技能物品共用的注册路径
   * @param zhCn            技能中文名
   * @param enUs            技能英文名
   * @param operatorZhCn    干员中文名
   * @param operatorEnUs    干员英文名
   * @param profession      技能所属职业
   * @param recoveryZhCn    中文技力回复类型
   * @param recoveryEnUs    英文技力回复类型
   * @param triggerZhCn     中文触发方式
   * @param triggerEnUs     英文触发方式
   * @param initialSp       初始技力，不能为负数
   * @param spCost          技力消耗，不能为负数
   * @param durationSeconds 持续秒数；瞬时技能传入 {@code null}
   * @param descriptionZhCn 中文技能描述
   * @param descriptionEnUs 英文技能描述
   * @param theme           Ponder 演示使用的视觉主题
   * @return 同时包含技能定义和注册物品的封装条目
   */
  public SkillEntry register(
      String path,
      String zhCn,
      String enUs,
      String operatorZhCn,
      String operatorEnUs,
      SkillProfession profession,
      String recoveryZhCn,
      String recoveryEnUs,
      String triggerZhCn,
      String triggerEnUs,
      int initialSp,
      int spCost,
      @Nullable Integer durationSeconds,
      String descriptionZhCn,
      String descriptionEnUs,
      SkillDemoTheme theme
  ) {
    if (initialSp < 0) throw new IllegalArgumentException("初始技力不能为负数");
    if (spCost < 0) throw new IllegalArgumentException("技力消耗不能为负数");
    if (durationSeconds != null && durationSeconds <= 0) {
      throw new IllegalArgumentException("技能持续时间必须大于 0");
    }
    if (entries.stream().anyMatch(entry -> entry.definition().getPath().equals(path))) {
      throw new IllegalArgumentException("技能 ID 重复: " + path);
    }

    SkillDefinition definition = new SkillDefinition(
        path,
        zhCn,
        enUs,
        operatorZhCn,
        operatorEnUs,
        profession,
        recoveryZhCn,
        recoveryEnUs,
        triggerZhCn,
        triggerEnUs,
        initialSp,
        spCost,
        durationSeconds,
        descriptionZhCn,
        descriptionEnUs,
        theme
    );
    DeferredItem<SkillItem> item = items.builder(path, zhCn, () -> new SkillItem(definition))
        .enUs(enUs)
        .hideCreativeTab()
        .build();
    registerTranslations(definition);

    SkillEntry entry = new SkillEntry(definition, item);
    mutableEntries.add(entry);
    return entry;
  }

  private void registerTranslations(SkillDefinition skill) {
    String tooltipKey = "item.zinecraft." + skill.getPath() + ".tooltip";
    translations.add(
        tooltipKey + ".operator",
        "干员：" + skill.getOperatorZhCn() + " · " + skill.getProfession().getZhCn(),
        "Operator: " + skill.getOperatorEnUs() + " · " + skill.getProfession().getEnUs()
    );
    translations.add(
        tooltipKey + ".activation",
        skill.getRecoveryZhCn() + " · " + skill.getTriggerZhCn(),
        skill.getRecoveryEnUs() + " · " + skill.getTriggerEnUs()
    );

    String durationZhCn = skill.getDurationSeconds() == null ? "" : " · 持续 " + skill.getDurationSeconds() + "秒";
    String durationEnUs = skill.getDurationSeconds() == null ? "" : " · Duration " + skill.getDurationSeconds() + "s";
    String statsZhCn = "初始 " + skill.getInitialSp() + " · 消耗 " + skill.getSpCost() + durationZhCn;
    String statsEnUs = "Initial " + skill.getInitialSp() + " · Cost " + skill.getSpCost() + durationEnUs;
    translations.add(tooltipKey + ".stats", statsZhCn, statsEnUs);
    translations.add(tooltipKey + ".description", skill.getDescriptionZhCn(), skill.getDescriptionEnUs());

    String ponderKey = "zinecraft.ponder.skill_demo_" + skill.getPath();
    translations.add(
        ponderKey + ".header",
        skill.getOperatorZhCn() + "：" + skill.getZhCn(),
        skill.getOperatorEnUs() + ": " + skill.getEnUs()
    );
    translations.add(
        ponderKey + ".text_1",
        skill.getOperatorZhCn() + "的" + skill.getProfession().getZhCn() + "技能",
        "A " + skill.getProfession().getEnUs() + " skill used by " + skill.getOperatorEnUs()
    );
    translations.add(
        ponderKey + ".text_2",
        skill.getRecoveryZhCn() + " · " + skill.getTriggerZhCn(),
        skill.getRecoveryEnUs() + " · " + skill.getTriggerEnUs()
    );
    translations.add(ponderKey + ".text_3", statsZhCn, statsEnUs);
    translations.add(ponderKey + ".text_4", skill.getDescriptionZhCn(), skill.getDescriptionEnUs());
    translations.add(
        ponderKey + ".text_5",
        "演示为 Minecraft 机制化表达，技能资料取自 PRTS。",
        "This is a Minecraft interpretation based on PRTS skill data."
    );
  }
}
