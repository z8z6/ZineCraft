package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.nation.NationAffiliated;
import com.cxxcxx.zinecraft.api.nation.TerraNation;
import kotlin.jvm.internal.DefaultConstructorMarker;
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

  // $VF: synthetic method
  public NationResidentProfile(TerraNation var1, Item var2, boolean var3, int var4, DefaultConstructorMarker var5) {
    this(var1, var2, (var4 & 4) != 0 ? false : var3);
  }

  // $VF: synthetic method
  public static NationResidentProfile copy$default(NationResidentProfile var0, TerraNation var1, Item var2, boolean var3, int var4, Object var5) {
    if ((var4 & 1) != 0) {
      var1 = var0.nation;
    }

    if ((var4 & 2) != 0) {
      var2 = var0.heldItem;
    }

    if ((var4 & 4) != 0) {
      var3 = var0.aquatic;
    }

    return var0.copy(var1, var2, var3);
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

  @NotNull
  public final TerraNation component1() {
    return this.nation;
  }

  @NotNull
  public final Item component2() {
    return this.heldItem;
  }

  public final boolean component3() {
    return this.aquatic;
  }

  @NotNull
  public final NationResidentProfile copy(@NotNull TerraNation nation, @NotNull Item heldItem, boolean aquatic) {
    return new NationResidentProfile(nation, heldItem, aquatic);
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
      return !java.util.Objects.equals(this.heldItem, nationResidentProfile.heldItem) ? false : this.aquatic == nationResidentProfile.aquatic;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "NationResidentProfile(nation=" + this.nation + ", heldItem=" + this.heldItem + ", aquatic=" + this.aquatic + ")";
  }
}

