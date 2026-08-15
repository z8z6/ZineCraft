package com.cxxcxx.zinecraft.api.enchantment;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Builder;
import net.minecraft.world.item.enchantment.Enchantment.Cost;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public final class EnchantmentEntry {
  @NotNull
  private final ResourceKey<Enchantment> key;
  @NotNull
  private final TagKey<Item> supportedItems;
  @Nullable
  private final TagKey<Item> primaryItems;
  @Nullable
  private final TagKey<Enchantment> exclusiveWith;
  private final int weight;
  private final int maxLevel;
  @NotNull
  private final Cost minCost;
  @NotNull
  private final Cost maxCost;
  private final int anvilCost;
  @NotNull
  private final List<EquipmentSlotGroup> slots;
  @NotNull
  private final Consumer<Builder> configure;

  public EnchantmentEntry(
      @NotNull ResourceKey<Enchantment> key,
      @NotNull TagKey<Item> supportedItems,
      @Nullable TagKey<Item> primaryItems,
      @Nullable TagKey<Enchantment> exclusiveWith,
      int weight,
      int maxLevel,
      @NotNull Cost minCost,
      @NotNull Cost maxCost,
      int anvilCost,
      @NotNull List<? extends EquipmentSlotGroup> slots,
      @NotNull Consumer<? super Builder> configure
  ) {
    super();
    this.key = key;
    this.supportedItems = supportedItems;
    this.primaryItems = primaryItems;
    this.exclusiveWith = exclusiveWith;
    this.weight = weight;
    this.maxLevel = maxLevel;
    this.minCost = minCost;
    this.maxCost = maxCost;
    this.anvilCost = anvilCost;
    this.slots = new java.util.ArrayList<>(slots);
    this.configure = builder -> configure.accept(builder);
  }

  @NotNull
  public final ResourceKey<Enchantment> getKey() {
    return this.key;
  }

  @NotNull
  public final TagKey<Item> getSupportedItems() {
    return this.supportedItems;
  }

  @Nullable
  public final TagKey<Item> getPrimaryItems() {
    return this.primaryItems;
  }

  @Nullable
  public final TagKey<Enchantment> getExclusiveWith() {
    return this.exclusiveWith;
  }

  public final int getWeight() {
    return this.weight;
  }

  public final int getMaxLevel() {
    return this.maxLevel;
  }

  @NotNull
  public final Cost getMinCost() {
    return this.minCost;
  }

  @NotNull
  public final Cost getMaxCost() {
    return this.maxCost;
  }

  public final int getAnvilCost() {
    return this.anvilCost;
  }

  @NotNull
  public final List<EquipmentSlotGroup> getSlots() {
    return this.slots;
  }

  @NotNull
  public final Consumer<Builder> getConfigure() {
    return this.configure;
  }
}

