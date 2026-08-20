package com.cxxcxx.zinecraft.api.collection;

import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;
import com.cxxcxx.zinecraft.api.registry.builder.CollectibleBuilder;
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
 * 装备在任意 Curios 饰品槽时生效的集成战略藏品。
 */
public final class CollectibleItem extends Item implements ICurioItem {
  private final CollectibleBuilder collectible;
  private final String namespace;
  private final String seriesTranslationKey;
  private final String originalEffectLabelTranslationKey;
  private final String minecraftEffectLabelTranslationKey;

  /**
   * 创建由藏品声明驱动的 Curios 物品。
   *
   * @param collectible                        藏品的注册声明与运行时效果
   * @param namespace                          生成属性修饰器资源 ID 时使用的命名空间
   * @param seriesTranslationKey               藏品系列提示的翻译键
   * @param originalEffectLabelTranslationKey  原作效果标签的翻译键
   * @param minecraftEffectLabelTranslationKey Minecraft 效果标签的翻译键
   */
  public CollectibleItem(
      CollectibleBuilder collectible,
      String namespace,
      String seriesTranslationKey,
      String originalEffectLabelTranslationKey,
      String minecraftEffectLabelTranslationKey
  ) {
    super(new Item.Properties().stacksTo(1).rarity(collectible.rarity));
    this.collectible = collectible;
    this.namespace = namespace;
    this.seriesTranslationKey = seriesTranslationKey;
    this.originalEffectLabelTranslationKey = originalEffectLabelTranslationKey;
    this.minecraftEffectLabelTranslationKey = minecraftEffectLabelTranslationKey;
  }

  /**
   * 获取该物品关联的藏品声明。
   *
   * @return 注册时传入的藏品构建器
   */
  public CollectibleBuilder collectible() {
    return collectible;
  }

  /**
   * 在服务端按声明的间隔执行藏品生命回复效果。
   *
   * @param slotContext 当前 Curios 槽位上下文
   * @param stack 当前装备的藏品物品栈
   */
  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    var entity = slotContext.entity();
    if (entity.level().isClientSide || entity.getHealth() >= entity.getMaxHealth()) {
      return;
    }
    CollectiblePower.Regeneration regeneration = collectible.power.regeneration();
    if (regeneration != null && entity.tickCount % regeneration.intervalTicks() == 0) {
      entity.heal(regeneration.percentage()
          ? entity.getMaxHealth() * regeneration.amount()
          : regeneration.amount());
    }
  }

  /**
   * 允许藏品装备到 Curios 选择的有效槽位中。
   *
   * @return 始终为 {@code true}
   */
  @Override
  public boolean canEquip(SlotContext slotContext, ItemStack stack) {
    return true;
  }

  /**
   * 将藏品声明的原版属性和战斗属性转换为 Curios 装备修饰器。
   *
   * @param slotContext 当前 Curios 槽位上下文
   * @param slotIdentifier 当前槽位的资源标识
   * @param stack 当前装备的藏品物品栈
   * @return 以 Minecraft 属性为键的修饰器集合
   */
  @Override
  public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
      SlotContext slotContext,
      ResourceLocation slotIdentifier,
      ItemStack stack
  ) {
    var modifiers = ArrayListMultimap.<Holder<Attribute>, AttributeModifier>create();
    List<CollectiblePower.AttributeBoost> boosts = collectible.power.attributes();
    for (int index = 0; index < boosts.size(); index++) {
      var boost = boosts.get(index);
      var modifierId = ResourceLocation.fromNamespaceAndPath(namespace, "collectible/" + collectible.path + "/" + index);
      modifiers.put(boost.attribute(), new AttributeModifier(modifierId, boost.amount(), boost.operation()));
    }
    List<CombatStatModifier> combatBoosts = collectible.power.combatStats();
    for (int index = 0; index < combatBoosts.size(); index++) {
      var modifier = combatBoosts.get(index);
      Holder<Attribute> attribute = CollectibleVanillaAttributes.attribute(modifier.stat());
      AttributeModifier.Operation operation = CollectibleVanillaAttributes.operation(modifier);
      var modifierId = ResourceLocation.fromNamespaceAndPath(namespace, "collectible/combat/" + collectible.path + "/" + index);
      modifiers.put(attribute, new AttributeModifier(modifierId, CollectibleVanillaAttributes.amount(modifier), operation));
    }
    return modifiers;
  }

  /**
   * 允许玩家通过使用物品直接尝试装备藏品。
   *
   * @return 始终为 {@code true}
   */
  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
    return true;
  }

  /**
   * 按“系列、原作效果、描述、Minecraft 效果”的顺序追加本地化提示文本。
   *
   * @param stack 正在显示提示的物品栈
   * @param context 物品提示上下文
   * @param tooltip 接收新增文本的提示列表
   * @param flag 当前提示显示标志
   */
  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
    super.appendHoverText(stack, context, tooltip, flag);
    tooltip.add(Component.translatable(seriesTranslationKey, collectible.orderId).withStyle(ChatFormatting.DARK_AQUA));
    for (int index = 0; index < collectible.originalEffectLineCount; index++) {
      var line = Component.translatable(getDescriptionId() + ".original_effect." + index);
      tooltip.add(index == 0
          ? Component.translatable(originalEffectLabelTranslationKey, line).withStyle(ChatFormatting.GOLD)
          : line.withStyle(ChatFormatting.GOLD));
    }
    for (int index = 0; index < collectible.descriptionLineCount; index++) {
      tooltip.add(Component.translatable(getDescriptionId() + ".description." + index).withStyle(ChatFormatting.GRAY));
    }
    tooltip.add(Component.translatable(
        minecraftEffectLabelTranslationKey,
        Component.translatable(getDescriptionId() + ".minecraft_effect")
    ).withStyle(ChatFormatting.GREEN));
  }
}
