package com.cxxcxx.zinecraft.api.world.dimension;

import java.util.OptionalLong;

import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.DimensionType.MonsterSettings;
import org.jetbrains.annotations.NotNull;

public final class DimensionHelper {
  @NotNull
  public static final DimensionHelper INSTANCE = new DimensionHelper();

  private DimensionHelper() {
  }

  @NotNull
  public final DimensionType overworldLikeType() {
    return new DimensionType(
        OptionalLong.empty(),
        true,
        false,
        false,
        true,
        1.0,
        true,
        false,
        -64,
        384,
        384,
        BlockTags.INFINIBURN_OVERWORLD,
        BuiltinDimensionTypes.OVERWORLD_EFFECTS,
        0.0F,
        new MonsterSettings(false, true, (IntProvider) UniformInt.of(0, 7), 0)
    );
  }
}
