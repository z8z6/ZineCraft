package com.cxxcxx.zinecraft.api.skill;

import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class SkillCatalog {
  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final List<SkillEntry> entries;

  public SkillCatalog(ItemCatalog items, TranslationCatalog translations) {
    super();
    this.items = items;
    this.translations = translations;
    this.entries = new ArrayList<>();
  }

  private static SkillItem createItem(SkillDefinition definition) {
    return new SkillItem(definition);
  }

  public final List<SkillEntry> getEntries() {
    return this.entries;
  }

  public final SkillEntry register(
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
    if (initialSp < 0) {
      int m = 0;
      String string3 = "初始技力不能为负数";
      throw new IllegalArgumentException(string3.toString());
    }

    if (spCost < 0) {
      int l = 0;
      String string2 = "技力消耗不能为负数";
      throw new IllegalArgumentException(string2.toString());
    }

    if (durationSeconds != null && durationSeconds <= 0) {
      int k = 0;
      String string1 = "技能持续时间必须大于 0";
      throw new IllegalArgumentException(string1.toString());
    }

    var iterable = this.entries;
    int i = 0;
    boolean bl;
    if (iterable instanceof Collection && ((Collection) iterable).isEmpty()) {
      bl = true;
    } else {
      Iterator iterator = iterable.iterator();

      while (true) {
        if (!iterator.hasNext()) {
          bl = true;
          break;
        }

        Object object = iterator.next();
        SkillEntry skillEntry = (SkillEntry) object;
        int j = 0;
        if (java.util.Objects.equals(skillEntry.definition().getPath(), path)) {
          bl = false;
          break;
        }
      }
    }

    if (!bl) {
      i = 0;
      String string = "技能 ID 重复: " + path;
      throw new IllegalArgumentException(string.toString());
    } else {
      SkillDefinition skillDefinition = new SkillDefinition(
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
      DeferredItem<SkillItem> item = this.items.builder(path, zhCn, () -> createItem(skillDefinition))
          .enUs(enUs)
          .hideCreativeTab()
          .build();
      this.registerTranslations(skillDefinition);
      SkillEntry skillEntry1 = new SkillEntry(skillDefinition, item);
      List list = this.entries;
      SkillEntry skillEntry2 = skillEntry1;
      int n = 0;
      list.add(skillEntry2);
      return skillEntry1;
    }
  }

  private final void registerTranslations(SkillDefinition skill) {
    String string = "item.zinecraft." + skill.getPath() + ".tooltip";
    this.translations
        .add(
            string + ".operator",
            "干员：" + skill.getOperatorZhCn() + " · " + skill.getProfession().getZhCn(),
            "Operator: " + skill.getOperatorEnUs() + " · " + skill.getProfession().getEnUs()
        );
    this.translations
        .add(string + ".activation", skill.getRecoveryZhCn() + " · " + skill.getTriggerZhCn(), skill.getRecoveryEnUs() + " · " + skill.getTriggerEnUs());
    Integer integer = skill.getDurationSeconds();
    String string4;
    if (integer != null) {
      int i = integer.intValue();
      int j = 0;
      string4 = " · 持续 " + i + "秒";
    } else {
      string4 = null;
    }

    if (string4 == null) {
      string4 = "";
    }

    String string1 = string4;
    integer = skill.getDurationSeconds();
    String string5;
    if (integer != null) {
      int l = integer.intValue();
      int k = 0;
      string5 = " · Duration " + l + "s";
    } else {
      string5 = null;
    }

    if (string5 == null) {
      string5 = "";
    }

    String string2 = string5;
    this.translations
        .add(
            string + ".stats",
            "初始 " + skill.getInitialSp() + " · 消耗 " + skill.getSpCost() + string1,
            "Initial " + skill.getInitialSp() + " · Cost " + skill.getSpCost() + string2
        );
    this.translations.add(string + ".description", skill.getDescriptionZhCn(), skill.getDescriptionEnUs());
    String string3 = "zinecraft.ponder.skill_demo_" + skill.getPath();
    this.translations.add(string3 + ".header", skill.getOperatorZhCn() + "：" + skill.getZhCn(), skill.getOperatorEnUs() + ": " + skill.getEnUs());
    this.translations
        .add(
            string3 + ".text_1",
            skill.getOperatorZhCn() + "的" + skill.getProfession().getZhCn() + "技能",
            "A " + skill.getProfession().getEnUs() + " skill used by " + skill.getOperatorEnUs()
        );
    this.translations
        .add(string3 + ".text_2", skill.getRecoveryZhCn() + " · " + skill.getTriggerZhCn(), skill.getRecoveryEnUs() + " · " + skill.getTriggerEnUs());
    this.translations
        .add(
            string3 + ".text_3",
            "初始 " + skill.getInitialSp() + " · 消耗 " + skill.getSpCost() + string1,
            "Initial " + skill.getInitialSp() + " · Cost " + skill.getSpCost() + string2
        );
    this.translations.add(string3 + ".text_4", skill.getDescriptionZhCn(), skill.getDescriptionEnUs());
    this.translations.add(string3 + ".text_5", "演示为 Minecraft 机制化表达，技能资料取自 PRTS。", "This is a Minecraft interpretation based on PRTS skill data.");
  }
}
