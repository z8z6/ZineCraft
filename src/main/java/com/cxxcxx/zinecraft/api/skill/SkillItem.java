package com.cxxcxx.zinecraft.api.skill;

import com.cxxcxx.zinecraft.api.registry.builder.SkillBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SkillItem extends Item {
  @NotNull
  private final SkillBuilder skill;

  public SkillItem(@NotNull SkillBuilder skill, @NotNull Properties properties) {
    super(properties);
    this.skill = skill;
  }

  public SkillItem(SkillBuilder skill) {
    this(skill, new Properties().stacksTo(1).rarity(Rarity.RARE));
  }

  @NotNull
  public SkillBuilder getSkill() {
    return this.skill;
  }

  public void appendHoverText(
      @NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag
  ) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    String string = this.getDescriptionId() + ".tooltip";
    tooltipComponents.add(Component.translatable(string + ".operator").withStyle(ChatFormatting.GOLD));
    tooltipComponents.add(Component.translatable(string + ".activation").withStyle(ChatFormatting.AQUA));
    tooltipComponents.add(Component.translatable(string + ".stats").withStyle(ChatFormatting.YELLOW));
    tooltipComponents.add(Component.translatable(string + ".damage").withStyle(ChatFormatting.RED));
    tooltipComponents.add(Component.translatable(string + ".description").withStyle(ChatFormatting.GRAY));
  }
}
