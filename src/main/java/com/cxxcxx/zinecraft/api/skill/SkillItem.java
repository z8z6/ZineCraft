package com.cxxcxx.zinecraft.api.skill;

import kotlin.jvm.internal.DefaultConstructorMarker;
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
  private final SkillDefinition skill;

  public SkillItem(@NotNull SkillDefinition skill, @NotNull Properties properties) {
    super(properties);
    this.skill = skill;
  }

  // $VF: synthetic method
  public SkillItem(SkillDefinition var1, Properties var2, int var3, DefaultConstructorMarker var4) {
    this(var1, (var3 & 2) != 0 ? new Properties().stacksTo(1).rarity(Rarity.RARE) : var2);
  }

  @NotNull
  public final SkillDefinition getSkill() {
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
    tooltipComponents.add(Component.translatable(string + ".description").withStyle(ChatFormatting.GRAY));
  }
}

