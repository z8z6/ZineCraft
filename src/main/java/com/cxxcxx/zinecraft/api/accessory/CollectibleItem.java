package com.cxxcxx.zinecraft.api.accessory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

/**
 * 仅能装备到 Curios `relic` 槽的集成战略藏品。
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
    if (!(spec.getPower() instanceof CollectiblePower.Regeneration regeneration)) {
      return;
    }
    var entity = slotContext.entity();
    if (entity.level().isClientSide || entity.tickCount % regeneration.getIntervalTicks() != 0 || entity.getHealth() >= entity.getMaxHealth()) {
      return;
    }
    entity.heal(entity.getMaxHealth() * regeneration.getMaxHealthFraction());
  }

  @Override
  public boolean canEquip(SlotContext slotContext, ItemStack stack) {
    return "relic".equals(slotContext.identifier());
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
