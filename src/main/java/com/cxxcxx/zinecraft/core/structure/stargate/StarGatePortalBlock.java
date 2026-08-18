package com.cxxcxx.zinecraft.core.structure.stargate;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import org.jetbrains.annotations.Nullable;

public final class StarGatePortalBlock extends Block implements Portal {
  public static final MapCodec<StarGatePortalBlock> CODEC = Block.simpleCodec(StarGatePortalBlock::new);
  private static final int AMBIENT_SOUND_CHANCE = 80;
  private static final int PARTICLES_PER_TICK = 2;
  private static final double PARTICLE_SPEED = 0.04;
  private static final int PLAYER_TRANSITION_TICKS = 20;

  public StarGatePortalBlock(Properties properties) {
    super(properties);
  }

  private static double randomVelocity(RandomSource random) {
    return (random.nextDouble() - 0.5) * PARTICLE_SPEED;
  }

  @Override
  protected MapCodec<? extends StarGatePortalBlock> codec() {
    return CODEC;
  }

  @Override
  public void animateTick(
      BlockState state,
      Level level,
      BlockPos pos,
      RandomSource random
  ) {
    if (random.nextInt(AMBIENT_SOUND_CHANCE) == 0) {
      level.playLocalSound(pos, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 0.35F, 1.45F, false);
    }

    for (int i = 0; i < PARTICLES_PER_TICK; i++) {
      level.addParticle(
          ParticleTypes.ELECTRIC_SPARK,
          pos.getX() + random.nextDouble(),
          pos.getY() + random.nextDouble(),
          pos.getZ() + random.nextDouble(),
          randomVelocity(random),
          randomVelocity(random),
          randomVelocity(random)
      );
    }
  }

  @Override
  public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
    return ItemStack.EMPTY;
  }

  @Override
  protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
    if (entity.canUsePortal(false)) {
      entity.setAsInsidePortal(this, pos);
    }
  }

  @Override
  public int getPortalTransitionTime(ServerLevel level, Entity entity) {
    return entity instanceof Player ? PLAYER_TRANSITION_TICKS : 0;
  }

  @Override
  @Nullable
  public DimensionTransition getPortalDestination(
      ServerLevel level,
      Entity entity,
      BlockPos pos
  ) {
    return StarGateTeleporter.INSTANCE.destination(level, entity, pos);
  }

  /**
   * 复用原版下界传送门的屏幕遮罩、视角旋转和触发音效。
   */
  @Override
  public Portal.Transition getLocalTransition() {
    return Portal.Transition.CONFUSION;
  }

}
