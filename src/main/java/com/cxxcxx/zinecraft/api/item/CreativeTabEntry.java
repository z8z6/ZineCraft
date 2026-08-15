package com.cxxcxx.zinecraft.api.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CreativeTabEntry {
  @NotNull
  private final ResourceKey<CreativeModeTab> key;
  @NotNull
  private final CreativeModeTab tab;

  public CreativeTabEntry(@NotNull ResourceKey<CreativeModeTab> key, @NotNull CreativeModeTab tab) {
    super();
    this.key = key;
    this.tab = tab;
  }

  @NotNull
  public final ResourceKey<CreativeModeTab> getKey() {
    return this.key;
  }

  @NotNull
  public final CreativeModeTab getTab() {
    return this.tab;
  }

  @Override
  public int hashCode() {
    int i = this.key.hashCode();
    return i * 31 + this.tab.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof CreativeTabEntry creativeTabEntry)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.key, creativeTabEntry.key) ? false : java.util.Objects.equals(this.tab, creativeTabEntry.tab);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "CreativeTabEntry(key=" + this.key + ", tab=" + this.tab + ")";
  }
}

