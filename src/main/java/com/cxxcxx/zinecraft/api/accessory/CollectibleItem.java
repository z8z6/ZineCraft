package com.cxxcxx.zinecraft.api.accessory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

/**
 * 装备在任意 Curios 饰品槽时生效的集成战略藏品。
 */
public final class CollectibleItem extends Item implements ICurioItem {
  private final CollectibleSpec spec;
  private final String namespace;
  private final String seriesTranslationKey;
  private final String originalEffectLabelTranslationKey;
  private final String minecraftEffectLabelTranslationKey;

  CollectibleItem(
      CollectibleSpec spec,
      String namespace,
      String seriesTranslationKey,
      String originalEffectLabelTranslationKey,
      String minecraftEffectLabelTranslationKey
  ) {
    super(new Item.Properties().stacksTo(1).rarity(spec.getRarity()));
    this.spec = spec;
    this.namespace = namespace;
    this.seriesTranslationKey = seriesTranslationKey;
    this.originalEffectLabelTranslationKey = originalEffectLabelTranslationKey;
    this.minecraftEffectLabelTranslationKey = minecraftEffectLabelTranslationKey;
  }

  public CollectibleSpec getSpec() {
    return spec;
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    var entity = slotContext.entity();
    if (entity.level().isClientSide || entity.getHealth() >= entity.getMaxHealth()) {
      return;
    }
    if (spec.getPower() instanceof CollectiblePower.Regeneration regeneration
        && entity.tickCount % regeneration.getIntervalTicks() == 0) {
      entity.heal(entity.getMaxHealth() * regeneration.getMaxHealthFraction());
    } else if (spec.getPower() instanceof CollectiblePower.FlatRegeneration regeneration
        && entity.tickCount % regeneration.intervalTicks() == 0) {
      entity.heal(regeneration.health());
    }
  }

  @Override
  public boolean canEquip(SlotContext slotContext, ItemStack stack) {
    return true;
  }

  @Override
  public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
      SlotContext slotContext,
      ResourceLocation slotIdentifier,
      ItemStack stack
  ) {
    var modifiers = ArrayListMultimap.<Holder<Attribute>, AttributeModifier>create();
    List<CollectiblePower.AttributeBoost> boosts;
    if (spec.getPower() instanceof CollectiblePower.AttributeBoost boost) {
      boosts = List.of(boost);
    } else if (spec.getPower() instanceof CollectiblePower.AttributeSet set) {
      boosts = set.getBoosts();
    } else {
      boosts = List.of();
    }
    for (int index = 0; index < boosts.size(); index++) {
      var boost = boosts.get(index);
      var modifierId = ResourceLocation.fromNamespaceAndPath(namespace, "collectible/" + spec.getPath() + "/" + index);
      modifiers.put(boost.getAttribute(), new AttributeModifier(modifierId, boost.getAmount(), boost.getOperation()));
    }
    List<CollectiblePower.CombatStatBoost> combatBoosts;
    if (spec.getPower() instanceof CollectiblePower.CombatStatBoost boost) {
      combatBoosts = List.of(boost);
    } else if (spec.getPower() instanceof CollectiblePower.CombatStatSet set) {
      combatBoosts = set.boosts();
    } else {
      combatBoosts = List.of();
    }
    for (int index = 0; index < combatBoosts.size(); index++) {
      var modifier = combatBoosts.get(index).modifier();
      Holder<Attribute> attribute = switch (modifier.stat()) {
        case MAX_HEALTH -> Attributes.MAX_HEALTH;
        case DEFENSE -> Attributes.ARMOR;
        case RESISTANCE -> Attributes.ARMOR_TOUGHNESS;
        case ATTACK, ATTACK_SPEED -> null;
      };
      if (attribute == null) continue;
      AttributeModifier.Operation operation = switch (modifier.phase()) {
        case COLLECTIBLE_ADDITION -> AttributeModifier.Operation.ADD_VALUE;
        case COLLECTIBLE_MULTIPLIER -> AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
        default -> throw new IllegalStateException("Unsupported collectible modifier phase: " + modifier.phase());
      };
      var modifierId = ResourceLocation.fromNamespaceAndPath(namespace, "collectible/combat/" + spec.getPath() + "/" + index);
      modifiers.put(attribute, new AttributeModifier(modifierId, modifier.amount(), operation));
    }
    return modifiers;
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
    return true;
  }

  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
    super.appendHoverText(stack, context, tooltip, flag);
    tooltip.add(Component.translatable(seriesTranslationKey, spec.getOrderId()).withStyle(ChatFormatting.DARK_AQUA));
    for (int index = 0; index < spec.getOriginalEffectLineCount(); index++) {
      var line = Component.translatable(getDescriptionId() + ".original_effect." + index);
      tooltip.add(index == 0
          ? Component.translatable(originalEffectLabelTranslationKey, line).withStyle(ChatFormatting.GOLD)
          : line.withStyle(ChatFormatting.GOLD));
    }
    for (int index = 0; index < spec.getDescriptionLineCount(); index++) {
      tooltip.add(Component.translatable(getDescriptionId() + ".description." + index).withStyle(ChatFormatting.GRAY));
    }
    tooltip.add(Component.translatable(
        minecraftEffectLabelTranslationKey,
        Component.translatable(getDescriptionId() + ".minecraft_effect")
    ).withStyle(ChatFormatting.GREEN));
  }
}
