package com.cxxcxx.zinecraft.api.accessory;

import com.cxxcxx.zinecraft.api.item.ItemEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CollectibleEntry {
  @NotNull
  private final CollectibleSpec spec;
  @NotNull
  private final ItemEntry<CollectibleItem> itemEntry;

  public CollectibleEntry(@NotNull CollectibleSpec spec, @NotNull ItemEntry<CollectibleItem> itemEntry) {
    super();
    this.spec = spec;
    this.itemEntry = itemEntry;
  }

  @NotNull
  public final CollectibleSpec getSpec() {
    return this.spec;
  }

  @NotNull
  public final ItemEntry<CollectibleItem> getItemEntry() {
    return this.itemEntry;
  }

  @NotNull
  public final CollectibleItem getItem() {
    return (CollectibleItem) this.itemEntry.getItem();
  }

  @Override
  public int hashCode() {
    int i = this.spec.hashCode();
    return i * 31 + this.itemEntry.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof CollectibleEntry collectibleEntry)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.spec, collectibleEntry.spec) ? false : java.util.Objects.equals(this.itemEntry, collectibleEntry.itemEntry);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "CollectibleEntry(spec=" + this.spec + ", itemEntry=" + this.itemEntry + ")";
  }
}

