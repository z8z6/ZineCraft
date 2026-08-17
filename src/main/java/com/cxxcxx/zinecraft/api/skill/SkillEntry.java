package com.cxxcxx.zinecraft.api.skill;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record SkillEntry(SkillDefinition definition, DeferredItem<SkillItem> item) implements ItemLike {

  public final SkillItem getItem() {
    return this.item.get();
  }

  public @NotNull Item asItem() {
    return this.getItem();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof SkillEntry skillEntry)) {
      return false;
    } else {
      return !Objects.equals(this.definition, skillEntry.definition) ? false : Objects.equals(this.item, skillEntry.item);
    }
  }

  @Override
  public String toString() {
    return "SkillEntry(definition=" + this.definition + ", item=" + this.item + ")";
  }
}
