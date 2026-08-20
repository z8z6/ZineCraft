package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.core.registry.ModBlock;
import com.cxxcxx.zinecraft.core.registry.ModEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Shared server-authoritative implementation for Terra's four reference animals.
 * Species-specific shape and presentation remain entirely client-side.
 */
public final class TerraBeastEntity extends PathfinderMob {
  public final AnimationState idleAnimationState = new AnimationState();
  public final AnimationState attackAnimationState = new AnimationState();
  public final AnimationState hurtAnimationState = new AnimationState();
  private int idleAnimationTimeout;

  public TerraBeastEntity(EntityType<? extends PathfinderMob> type, Level level) {
    super(type, level);
  }

  public static AttributeSupplier.Builder sandbeastAttributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 18.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.24D)
        .add(Attributes.ARMOR, 4.0D)
        .add(Attributes.FOLLOW_RANGE, 18.0D);
  }

  public static AttributeSupplier.Builder rivenbeastAttributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 30.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.30D)
        .add(Attributes.ATTACK_DAMAGE, 6.0D)
        .add(Attributes.ARMOR, 3.0D)
        .add(Attributes.FOLLOW_RANGE, 28.0D);
  }

  public static AttributeSupplier.Builder clampbeastAttributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 24.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.23D)
        .add(Attributes.ATTACK_DAMAGE, 5.0D)
        .add(Attributes.ARMOR, 8.0D)
        .add(Attributes.FOLLOW_RANGE, 22.0D);
  }

  public static AttributeSupplier.Builder packbeastAttributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 36.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.20D)
        .add(Attributes.ARMOR, 6.0D)
        .add(Attributes.KNOCKBACK_RESISTANCE, 0.35D)
        .add(Attributes.FOLLOW_RANGE, 18.0D);
  }

  public static boolean canSpawnInDesert(
      EntityType<TerraBeastEntity> type,
      ServerLevelAccessor level,
      MobSpawnType spawnType,
      BlockPos pos,
      RandomSource random
  ) {
    BlockState surface = level.getBlockState(pos.below());
    return (surface.is(Blocks.SAND) || surface.is(Blocks.RED_SAND) || surface.is(Blocks.GRASS_BLOCK)
        || surface.is(ModBlock.SARGON_DESERT_CRUST.get()))
        && Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
  }

  public static boolean canSpawnInColdLand(
      EntityType<TerraBeastEntity> type,
      ServerLevelAccessor level,
      MobSpawnType spawnType,
      BlockPos pos,
      RandomSource random
  ) {
    BlockState surface = level.getBlockState(pos.below());
    return (surface.is(Blocks.SNOW_BLOCK) || surface.is(Blocks.GRASS_BLOCK) || surface.is(Blocks.PODZOL)
        || surface.is(ModBlock.URSUS_PERMAFROST.get()))
        && Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
  }

  public static boolean canSpawnInWetLand(
      EntityType<TerraBeastEntity> type,
      ServerLevelAccessor level,
      MobSpawnType spawnType,
      BlockPos pos,
      RandomSource random
  ) {
    BlockState surface = level.getBlockState(pos.below());
    return (surface.is(Blocks.GRASS_BLOCK) || surface.is(Blocks.MUD) || surface.is(Blocks.CLAY)
        || surface.is(ModBlock.KAZIMIERZ_STEPPE_TURF.get()))
        && Mob.checkMobSpawnRules(type, level, spawnType, pos, random);
  }

  @Override
  protected void registerGoals() {
    goalSelector.addGoal(0, new FloatGoal(this));
    if (isHostileSpecies()) {
      goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15D, true));
      targetSelector.addGoal(1, new HurtByTargetGoal(this));
      targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    } else {
      goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
    }
    goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
    goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
    goalSelector.addGoal(7, new RandomLookAroundGoal(this));
  }

  @Override
  public void tick() {
    super.tick();
    if (level().isClientSide()) updateAnimationStates();
  }

  private boolean isHostileSpecies() {
    return getType() == ModEntity.RIVENBEAST.get() || getType() == ModEntity.CLAMPBEAST.get();
  }

  private void updateAnimationStates() {
    if (idleAnimationTimeout <= 0) {
      idleAnimationTimeout = random.nextInt(40) + 80;
      idleAnimationState.start(tickCount);
    } else {
      idleAnimationTimeout--;
    }
    if (swinging) attackAnimationState.startIfStopped(tickCount);
    else attackAnimationState.stop();
    if (hurtTime > 0) hurtAnimationState.startIfStopped(tickCount);
    else hurtAnimationState.stop();
  }
}
