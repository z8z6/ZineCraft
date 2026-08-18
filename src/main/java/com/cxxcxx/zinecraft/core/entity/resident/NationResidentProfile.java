package com.cxxcxx.zinecraft.core.entity.resident;

import com.cxxcxx.zinecraft.api.nation.NationAffiliated;
import com.cxxcxx.zinecraft.api.nation.TerraNation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record NationResidentProfile(TerraNation nation, Item heldItem,
                                    boolean aquatic) implements NationAffiliated {

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
      return Objects.equals(this.heldItem, nationResidentProfile.heldItem) && this.aquatic == nationResidentProfile.aquatic;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "NationResidentProfile(nation=" + this.nation + ", heldItem=" + this.heldItem + ", aquatic=" + this.aquatic + ")";
  }
}
