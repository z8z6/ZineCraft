package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.collection.CollectibleItem;
import com.cxxcxx.zinecraft.api.collection.CollectibleTooltips;
import com.cxxcxx.zinecraft.api.collection.LocalizedTooltipLine;
import com.cxxcxx.zinecraft.api.registry.builder.CollectibleBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.ItemBuilder;
import net.minecraft.data.models.model.ModelTemplates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Shared collectible registration API and data-generation metadata catalog.
 */
public final class CollectibleCatalog {
  private static final int ORIGINAL_EFFECT_FIRST_LINE_CHARACTERS = 24;
  private static final Pattern PATH_PATTERN = Pattern.compile("[a-z0-9_]+");

  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final String namespace;
  private final String originalEffectLabelTranslationKey;
  private final String minecraftEffectLabelTranslationKey;
  private final List<CollectibleBuilder> mutableEntries = new ArrayList<>();
  public final List<CollectibleBuilder> entries = Collections.unmodifiableList(mutableEntries);

  public CollectibleCatalog(ItemCatalog items, TranslationCatalog translations, String namespace) {
    this.items = Objects.requireNonNull(items, "items");
    this.translations = Objects.requireNonNull(translations, "translations");
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.originalEffectLabelTranslationKey = "item." + namespace + ".collectible.original_effect";
    this.minecraftEffectLabelTranslationKey = "item." + namespace + ".collectible.minecraft_effect";
    registerCommonTranslations();
  }

  private static void validate(CollectibleBuilder builder) {
    if (!PATH_PATTERN.matcher(builder.path).matches()) {
      throw new IllegalArgumentException("藏品 ID 必须是 snake_case：" + builder.path);
    }
    requireText(builder.zhCn, "藏品中文名不能为空：" + builder.path);
    requireText(builder.enUs, "藏品英文名不能为空：" + builder.path);
    requireText(builder.originalEffectZhCn, "藏品中文原效果不能为空：" + builder.path);
    requireText(builder.originalEffectEnUs, "藏品英文原效果不能为空：" + builder.path);
    requireText(builder.descriptionZhCn, "藏品中文描述不能为空：" + builder.path);
    requireText(builder.descriptionEnUs, "藏品英文描述不能为空：" + builder.path);
    requireText(builder.minecraftEffectZhCn, "藏品中文适配说明不能为空：" + builder.path);
    requireText(builder.minecraftEffectEnUs, "藏品英文适配说明不能为空：" + builder.path);
    Objects.requireNonNull(builder.power, "藏品效果不能为空：" + builder.path);
    Objects.requireNonNull(builder.rarity, "藏品稀有度不能为空：" + builder.path);
  }

  private static void requireText(String value, String message) {
    if (value == null || value.isBlank()) throw new IllegalArgumentException(message);
  }

  private void registerCommonTranslations() {
    translations.add(originalEffectLabelTranslationKey, "原效果：%s", "Original effect: %s");
    translations.add(minecraftEffectLabelTranslationKey, "装备效果：%s", "Equipped effect: %s");
    translations.add("curios.identifier.relic", "藏品", "Collectible");
    translations.add("menu.tabs.curios", "饰品", "Accessories");
    translations.add("menu.tabs.attribute", "能力", "Abilities");
    translations.add("menu.tabs.attribute.collectible_effects", "藏品效果", "Collectible Effects");
    translations.add("menu.tabs.attribute.collectible_effects.none", "未装备具有探索效果的藏品", "No exploration-effect collectible equipped");
    translations.add("menu.tabs.attribute.collectible_effects.hope", "希望", "Hope");
    translations.add("menu.tabs.attribute.collectible_effects.objective_life", "目标生命", "Objective Life");
    translations.add("menu.tabs.attribute.collectible_effects.temporary_objective_life", "临时目标生命", "Temporary Objective Life");
    translations.add("menu.tabs.attribute.collectible_effects.originium_ingots", "源石锭", "Originium Ingots");
    translations.add("menu.tabs.attribute.collectible_effects.action_points", "行动力", "Action Points");
    translations.add("menu.tabs.attribute.collectible_effects.anti_interference", "抗干扰指数", "Anti-Interference Index");
    translations.add("menu.tabs.attribute.collectible_effects.collapse", "坍缩值", "Collapse");
    translations.add("menu.tabs.attribute.collectible_effects.mental_burden_limit", "负荷临界点", "Mental Burden Limit");
    translations.add("menu.tabs.attribute.collectible_effects.thoughts", "思绪", "Thoughts");
    translations.add("menu.tabs.attribute.collectible_effects.candles", "烛火", "Candles");
    translations.add("menu.tabs.attribute.collectible_effects.squad_capacity", "可携带干员", "Squad Capacity");
    translations.add("menu.tabs.attribute.collectible_effects.deployment_limit", "可部署人数", "Deployment Limit");
    translations.add("menu.tabs.attribute.collectible_effects.initial_deployment_points", "初始部署费用", "Initial DP");
    translations.add("menu.tabs.attribute.collectible_effects.keys", "钥匙", "Keys");
    translations.add("menu.tabs.attribute.collectible_effects.dice", "骰子", "Dice");
    translations.add("menu.tabs.attribute.collectible_effects.light", "灯火", "Light");
    translations.add("menu.tabs.attribute.collectible_effects.command_experience", "指挥经验", "Command EXP");
    translations.add("menu.tabs.attribute.collectible_effects.hope_per_node", "每个非战斗节点希望", "Hope per Non-Combat Node");
    translations.add("menu.tabs.attribute.collectible_effects.ingots_per_node", "每个非战斗节点源石锭", "Ingots per Non-Combat Node");
    translations.add("menu.tabs.attribute.collectible_effects.battle_ingots", "战斗源石锭", "Battle Ingots");
    translations.add("menu.tabs.attribute.collectible_effects.failure_recovery", "一次失败续行目标生命", "One-Time Failure Recovery Life");
  }

  /**
   * 校验并登记藏品物品、运行时定义及提示文本翻译。
   *
   * @param builder 藏品声明
   * @return 已登记并绑定物品句柄的声明
   */
  public CollectibleBuilder register(CollectibleBuilder builder) {
    Objects.requireNonNull(builder, "藏品 builder 不能为空");
    if (builder.catalog != this) {
      throw new IllegalArgumentException("藏品 builder 不属于当前目录：" + builder.path);
    }
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("藏品 ID 重复：" + builder.path);
    }

    validate(builder);
    List<LocalizedTooltipLine> originalEffectLines = CollectibleTooltips.wrapLocalizedTooltip(
        builder.originalEffectZhCn, builder.originalEffectEnUs,
        ORIGINAL_EFFECT_FIRST_LINE_CHARACTERS
    );
    List<LocalizedTooltipLine> descriptionLines = CollectibleTooltips.wrapLocalizedTooltip(
        builder.descriptionZhCn, builder.descriptionEnUs
    );
    builder.originalEffectLineCount = originalEffectLines.size();
    builder.descriptionLineCount = descriptionLines.size();

    builder.item = new ItemBuilder<>(items,
            builder.path,
            builder.zhCn,
            builder.enUs,
            () -> new CollectibleItem(
                builder,
                namespace,
                originalEffectLabelTranslationKey,
                minecraftEffectLabelTranslationKey
            ),
            ModelTemplates.FLAT_ITEM,
            false
    ).build()
        .getItem();

    registerTranslations(builder, originalEffectLines, descriptionLines);
    mutableEntries.add(builder);
    return builder;
  }

  private void registerTranslations(
      CollectibleBuilder builder,
      List<LocalizedTooltipLine> originalEffectLines,
      List<LocalizedTooltipLine> descriptionLines
  ) {
    String key = "item." + namespace + "." + builder.path;
    translations.add(key + ".original_effect",
        literalTranslation(builder.originalEffectZhCn), literalTranslation(builder.originalEffectEnUs));
    for (int index = 0; index < originalEffectLines.size(); index++) {
      LocalizedTooltipLine line = originalEffectLines.get(index);
      translations.add(key + ".original_effect." + index,
          literalTranslation(line.getZhCn()), literalTranslation(line.getEnUs()));
    }

    translations.add(key + ".description",
        literalTranslation(builder.descriptionZhCn), literalTranslation(builder.descriptionEnUs));
    for (int index = 0; index < descriptionLines.size(); index++) {
      LocalizedTooltipLine line = descriptionLines.get(index);
      translations.add(key + ".description." + index,
          literalTranslation(line.getZhCn()), literalTranslation(line.getEnUs()));
    }
    translations.add(key + ".minecraft_effect",
        literalTranslation(builder.minecraftEffectZhCn), literalTranslation(builder.minecraftEffectEnUs));
  }

  /** Escapes percentages that are content, not Component.translatable placeholders. */
  private static String literalTranslation(String value) {
    return value.replace("%", "%%");
  }

}
