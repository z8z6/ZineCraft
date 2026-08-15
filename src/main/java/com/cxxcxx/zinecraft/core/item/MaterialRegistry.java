package com.cxxcxx.zinecraft.core.item;

import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MaterialRegistry implements Tier {
  @NotNull
  public static final MaterialRegistry.MaterialCreate MaterialCreate = new MaterialRegistry.MaterialCreate(null);
  @JvmField
  public int uses = 1;
  @JvmField
  public float speed = 1.0F;
  @JvmField
  public float attackDamageBonus = 1.0F;
  @JvmField
  @NotNull
  public TagKey<Block> incorrectBlocksForDrops;
  @JvmField
  public int enchantmentValue;
  @JvmField
  @Nullable
  public Ingredient repairIngredient;

  public MaterialRegistry() {
    TagKey tagKey = BlockTags.INCORRECT_FOR_WOODEN_TOOL;
    this.incorrectBlocksForDrops = tagKey;
    this.enchantmentValue = 1;
    this.repairIngredient = Ingredient.EMPTY;
  }

  public int getUses() {
    return this.uses;
  }

  @NotNull
  public final MaterialRegistry setUses(int uses) {
    this.uses = uses;
    return this;
  }

  public float getSpeed() {
    return this.speed;
  }

  @NotNull
  public final MaterialRegistry setSpeed(float speed) {
    this.speed = speed;
    return this;
  }

  public float getAttackDamageBonus() {
    return this.attackDamageBonus;
  }

  @NotNull
  public final MaterialRegistry setAttackDamageBonus(float attackDamageBonus) {
    this.attackDamageBonus = attackDamageBonus;
    return this;
  }

  @NotNull
  public TagKey<Block> getIncorrectBlocksForDrops() {
    return this.incorrectBlocksForDrops;
  }

  @NotNull
  public final MaterialRegistry setIncorrectBlocksForDrops(@NotNull TagKey<Block> incorrectBlocksForDrops) {
    this.incorrectBlocksForDrops = incorrectBlocksForDrops;
    return this;
  }

  public int getEnchantmentValue() {
    return this.enchantmentValue;
  }

  @NotNull
  public final MaterialRegistry setEnchantmentValue(int enchantmentValue) {
    this.enchantmentValue = enchantmentValue;
    return this;
  }

  @Nullable
  public Ingredient getRepairIngredient() {
    return this.repairIngredient;
  }

  @NotNull
  public final MaterialRegistry setRepairIngredient(@Nullable Ingredient repairIngredient) {
    this.repairIngredient = repairIngredient;
    return this;
  }

  public static final class MaterialCreate {
    private MaterialCreate() {
    }

    // $VF: synthetic method
    public MaterialCreate(DefaultConstructorMarker $constructor_marker) {
      this();
    }

    public final void init() {
      MaterialRegistry.MaterialCreate materialCreate = MaterialRegistry.MaterialCreate;
    }
  }
}

