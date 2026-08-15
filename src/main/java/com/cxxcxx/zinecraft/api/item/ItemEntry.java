package com.cxxcxx.zinecraft.api.item;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class ItemEntry<T extends Item> implements ItemLike {
  @NotNull
  private final String path;
  @NotNull
  private final Supplier<T> item;
  @NotNull
  private final ModelTemplate model;
  private final boolean includeInCreative;

  public ItemEntry(@NotNull String path, @NotNull Supplier<T> item, @NotNull ModelTemplate model, boolean includeInCreative) {
    super();
    this.path = path;
    this.item = item;
    this.model = model;
    this.includeInCreative = includeInCreative;
  }

  @NotNull
  public final String getPath() {
    return this.path;
  }

  @NotNull
  public final T getItem() {
    return this.item.get();
  }

  @NotNull
  public final ModelTemplate getModel$zinecraft() {
    return this.model;
  }

  public final boolean getIncludeInCreative$zinecraft() {
    return this.includeInCreative;
  }

  @NotNull
  public Item asItem() {
    return this.item.get();
  }

  @NotNull
  public final ItemEntry<T> fuel(int ticks) {
    ItemEntry itemEntry = this;
    ItemEntry itemEntry1 = itemEntry;
    int i = 0;
    if (ticks <= 0) {
      int j = 0;
      String string = "燃料时间必须大于 0";
      throw new IllegalArgumentException(string.toString());
    } else {
      return itemEntry;
    }
  }

  @NotNull
  public final ItemEntry<T> compost(float chance) {
    ItemEntry itemEntry = this;
    ItemEntry itemEntry1 = itemEntry;
    int i = 0;
    if (0.0F <= chance ? !(chance <= 1.0F) : true) {
      int j = 0;
      String string = "堆肥概率必须在 0 到 1 之间";
      throw new IllegalArgumentException(string.toString());
    } else {
      return itemEntry;
    }
  }
}

