package com.cxxcxx.zinecraft.api.skill;

import com.cxxcxx.zinecraft.api.item.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SkillEntry implements ItemLike {
  @NotNull
  private final SkillDefinition definition;
  @NotNull
  private final ItemEntry<SkillItem> itemEntry;

  public SkillEntry(@NotNull SkillDefinition definition, @NotNull ItemEntry<SkillItem> itemEntry) {
    super();
    this.definition = definition;
    this.itemEntry = itemEntry;
  }

  @NotNull
  public final SkillDefinition getDefinition() {
    return this.definition;
  }

  @NotNull
  public final ItemEntry<SkillItem> getItemEntry() {
    return this.itemEntry;
  }

  @NotNull
  public final SkillItem getItem() {
    return this.itemEntry.getItem();
  }

  @NotNull
  public Item asItem() {
    return this.getItem();
  }

  @Override
  public int hashCode() {
    int i = this.definition.hashCode();
    return i * 31 + this.itemEntry.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof SkillEntry skillEntry)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.definition, skillEntry.definition) ? false : java.util.Objects.equals(this.itemEntry, skillEntry.itemEntry);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "SkillEntry(definition=" + this.definition + ", itemEntry=" + this.itemEntry + ")";
  }
}

