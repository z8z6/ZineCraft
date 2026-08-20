package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.core.registry.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A peaceful Feline resident represented by the bundled Victorian YSM model.
 */
public final class FelineVictorianResidentEntity extends PathfinderMob {
  public FelineVictorianResidentEntity(EntityType<? extends PathfinderMob> type, net.minecraft.world.level.Level level) {
    super(type, level);
  }

  public static AttributeSupplier.Builder attributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 20.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.25D)
        .add(Attributes.FOLLOW_RANGE, 24.0D);
  }

  public static boolean canSpawn(
      EntityType<FelineVictorianResidentEntity> type,
      ServerLevelAccessor level,
      MobSpawnType spawnType,
      BlockPos pos,
      RandomSource random
  ) {
    BlockState surface = level.getBlockState(pos.below());
    boolean victoriaSurface = surface.is(ModBlock.VICTORIA_MOORLAND_SOIL.get())
        || surface.is(Blocks.GRASS_BLOCK);
    return victoriaSurface && Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
  }

  @Override
  protected void registerGoals() {
    goalSelector.addGoal(0, new FloatGoal(this));
    goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
    goalSelector.addGoal(5, new RandomStrollGoal(this, 0.8D));
    goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
    goalSelector.addGoal(7, new RandomLookAroundGoal(this));
  }
}
