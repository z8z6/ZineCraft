package com.cxxcxx.zinecraft.core.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public final class StarGateFeature extends Feature<NoneFeatureConfiguration> {
  public StarGateFeature() {
    super(NoneFeatureConfiguration.CODEC);
  }

  public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
    WorldGenLevel worldGenLevel = context.level();
    if (!java.util.Objects.equals(worldGenLevel.getLevel().dimension(), Level.OVERWORLD)) {
      return false;
    }

    BlockPos blockPos = worldGenLevel.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, context.origin());
    Axis axis = context.random().nextBoolean() ? Axis.X : Axis.Z;
    StarGateStructure starGateStructure = StarGateStructure.INSTANCE;
    LevelAccessor levelAccessor = (LevelAccessor) worldGenLevel;
    if (!starGateStructure.canPlace(levelAccessor, blockPos, axis)) {
      return false;
    }

    StarGateStructure.INSTANCE.place((LevelAccessor) worldGenLevel, blockPos, axis, false);
    return true;
  }
}

