package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.nation.NationAffiliated;
import com.cxxcxx.zinecraft.api.nation.TerraNation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NationResidentProfile implements NationAffiliated {
  @NotNull
  private final TerraNation nation;
  @NotNull
  private final Item heldItem;
  private final boolean aquatic;

  public NationResidentProfile(@NotNull TerraNation nation, @NotNull Item heldItem, boolean aquatic) {
    super();
    this.nation = nation;
    this.heldItem = heldItem;
    this.aquatic = aquatic;
  }

  @NotNull
  @Override
  public TerraNation getNation() {
    return this.nation;
  }

  @NotNull
  public final Item getHeldItem() {
    return this.heldItem;
  }

  public final boolean getAquatic() {
    return this.aquatic;
  }

  @Override
  public int hashCode() {
    int i = this.nation.hashCode();
    i = i * 31 + this.heldItem.hashCode();
    return i * 31 + Boolean.hashCode(this.aquatic);
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof NationResidentProfile nationResidentProfile)) {
      return false;
    } else if (this.nation != nationResidentProfile.nation) {
      return false;
    } else {
      return java.util.Objects.equals(this.heldItem, nationResidentProfile.heldItem) && this.aquatic == nationResidentProfile.aquatic;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "NationResidentProfile(nation=" + this.nation + ", heldItem=" + this.heldItem + ", aquatic=" + this.aquatic + ")";
  }
}
