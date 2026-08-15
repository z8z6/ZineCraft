package com.cxxcxx.zinecraft.core.dimension;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StarGatePortalBlock extends Block implements Portal {
  @NotNull
  public static final StarGatePortalBlock.Access ACCESS = new StarGatePortalBlock.Access();
  @NotNull
  private static final MapCodec<StarGatePortalBlock> CODEC;

  static {
    MapCodec mapCodec = Block.simpleCodec(StarGatePortalBlock::new);
    CODEC = mapCodec;
  }

  public StarGatePortalBlock(@NotNull Properties properties) {
    super(properties);
  }

  @NotNull
  protected MapCodec<? extends StarGatePortalBlock> codec() {
    return CODEC;
  }

  public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
    if (random.nextInt(80) == 0) {
      level.playLocalSound(pos, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 0.35F, 1.45F, false);
    }

    byte b = 2;

    for (int i = 0; i < b; i++) {
      int j = 0;
      level.addParticle(
          (ParticleOptions) ParticleTypes.ELECTRIC_SPARK,
          pos.getX() + random.nextDouble(),
          pos.getY() + random.nextDouble(),
          pos.getZ() + random.nextDouble(),
          (random.nextDouble() - 0.5) * 0.04,
          (random.nextDouble() - 0.5) * 0.04,
          (random.nextDouble() - 0.5) * 0.04
      );
    }
  }

  @NotNull
  public ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
    ItemStack itemStack = ItemStack.EMPTY;
    return itemStack;
  }

  protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
    if (entity.canUsePortal(false)) {
      entity.setAsInsidePortal(this, pos);
    }
  }

  public int getPortalTransitionTime(@NotNull ServerLevel level, @NotNull Entity entity) {
    return entity instanceof Player ? 20 : 0;
  }

  /**
   * 复用下界传送门的原版视场旋转、屏幕叠加和触发音效。
   */
  @NotNull
  public Portal.Transition getLocalTransition() {
    return Portal.Transition.CONFUSION;
  }

  @Nullable
  public DimensionTransition getPortalDestination(@NotNull ServerLevel level, @NotNull Entity entity, @NotNull BlockPos pos) {
    return StarGateTeleporter.INSTANCE.destination(level, entity, pos);
  }

  public static final class Access {
    private Access() {
    }

    @NotNull
    public final MapCodec<StarGatePortalBlock> getCODEC() {
      return StarGatePortalBlock.CODEC;
    }
  }
}
