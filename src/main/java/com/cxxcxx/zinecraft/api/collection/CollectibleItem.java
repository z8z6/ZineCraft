package com.cxxcxx.zinecraft.api.collection;

import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.registry.builder.CollectibleBuilder;
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

/** 装备在 Curios 藏品槽时生效的集成战略藏品。 */
public final class CollectibleItem extends Item implements ICurioItem {
  private final CollectibleBuilder collectible;
  private final String namespace;
  private final String originalEffectLabelTranslationKey;
  private final String minecraftEffectLabelTranslationKey;

  public CollectibleItem(
      CollectibleBuilder collectible,
      String namespace,
      String originalEffectLabelTranslationKey,
      String minecraftEffectLabelTranslationKey
  ) {
    super(new Item.Properties().stacksTo(1).rarity(collectible.rarity));
    this.collectible = collectible;
    this.namespace = namespace;
    this.originalEffectLabelTranslationKey = originalEffectLabelTranslationKey;
    this.minecraftEffectLabelTranslationKey = minecraftEffectLabelTranslationKey;
  }

  public CollectibleBuilder collectible() {
    return collectible;
  }

  /** 服务端每秒执行该藏品注册的周期能力函数。 */
  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    var entity = slotContext.entity();
    if (entity.level().isClientSide || entity.tickCount % 20 != 0) {
      return;
    }
    collectible.power.apply(CombatStat.EMPTY.withCollectibleEffectTier(
        CollectibleSpecialCondition.tier(entity)
    )).triggerPerSecondEffects(entity);
  }

  @Override
  public boolean canEquip(SlotContext slotContext, ItemStack stack) {
    return true;
  }

  /** 由 CombatStat 集中把该藏品效果转换为 Minecraft 原版属性修饰器。 */
  @Override
  public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
      SlotContext slotContext,
      ResourceLocation slotIdentifier,
      ItemStack stack
  ) {
    var entity = slotContext.entity();
    ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath(
        namespace, "collectible/" + collectible.path
    );
    return CombatStat.toVanillaModifiers(
        collectible.power,
        modifierId,
        entity == null ? 0 : CollectibleSpecialCondition.tier(entity)
    );
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
    return true;
  }

  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
    super.appendHoverText(stack, context, tooltip, flag);
    for (int index = 0; index < collectible.originalEffectLineCount; index++) {
      var line = Component.translatable(getDescriptionId() + ".original_effect." + index);
      if (isTooltipPadding(line)) continue;
      tooltip.add(index == 0
          ? Component.translatable(originalEffectLabelTranslationKey, line).withStyle(ChatFormatting.GOLD)
          : line.withStyle(ChatFormatting.GOLD));
    }
    for (int index = 0; index < collectible.descriptionLineCount; index++) {
      var line = Component.translatable(getDescriptionId() + ".description." + index);
      if (!isTooltipPadding(line)) tooltip.add(line.withStyle(ChatFormatting.GRAY));
    }
    tooltip.add(Component.translatable(
        minecraftEffectLabelTranslationKey,
        Component.translatable(getDescriptionId() + ".minecraft_effect")
    ).withStyle(ChatFormatting.GREEN));
  }

  private static boolean isTooltipPadding(Component line) {
    String text = line.getString();
    return text.isEmpty() || text.codePoints().allMatch(codePoint -> codePoint == 0x200B);
  }
}
