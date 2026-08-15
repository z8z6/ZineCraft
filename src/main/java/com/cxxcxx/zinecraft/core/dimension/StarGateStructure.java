package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.core.block.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.NotNull;

public final class StarGateStructure {
  @NotNull
  public static final StarGateStructure INSTANCE = new StarGateStructure();
  @NotNull
  private static final int[] outerHalfWidths;
  private static final int OUTER_RADIUS = 12;
  private static final int CONTROLLER_DISTANCE = 5;
  private static final int FOUNDATION_HALF_WIDTH = OUTER_RADIUS + 2;
  private static final int FOUNDATION_HALF_DEPTH = CONTROLLER_DISTANCE + 1;

  static {
    outerHalfWidths = new int[OUTER_RADIUS * 2];
    for (int y = 1; y <= outerHalfWidths.length; y++) {
      int curveHeight = Math.max(y - OUTER_RADIUS, 0);
      outerHalfWidths[y - 1] = Math.max(1, (int) Math.round(Math.sqrt(OUTER_RADIUS * OUTER_RADIUS - curveHeight * curveHeight)));
    }
  }

  private StarGateStructure() {
  }

  public final boolean canPlace(@NotNull LevelAccessor level, @NotNull BlockPos base, @NotNull Axis axis) {
    int minHeight = Integer.MAX_VALUE;
    int maxHeight = Integer.MIN_VALUE;
    for (int horizontal : new int[]{-FOUNDATION_HALF_WIDTH, 0, FOUNDATION_HALF_WIDTH}) {
      BlockPos sample = local(base, axis, horizontal, 0, 0);
      int height = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, sample.getX(), sample.getZ());
      minHeight = Math.min(minHeight, height);
      maxHeight = Math.max(maxHeight, height);
    }
    if (maxHeight - minHeight > 2) {
      return false;
    }

    for (int y = 2; y <= outerHalfWidths.length + 2; y++) {
      for (int horizontal = -FOUNDATION_HALF_WIDTH; horizontal <= FOUNDATION_HALF_WIDTH; horizontal++) {
        for (int depth = -FOUNDATION_HALF_DEPTH; depth <= 3; depth++) {
          BlockState state = level.getBlockState(local(base, axis, horizontal, y, depth));
          if (!state.canBeReplaced() && !state.is(Blocks.SNOW) && !state.is(Blocks.SNOW_BLOCK)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @NotNull
  public final BlockPos place(@NotNull LevelAccessor level, @NotNull BlockPos base, @NotNull Axis axis, boolean active) {
    BlockState blockState = ModBlock.INSTANCE.getSTARGATE_ARCH().defaultBlockState();

    for (int i = -FOUNDATION_HALF_WIDTH; i <= FOUNDATION_HALF_WIDTH; i++) {
      for (int j = -3; j <= FOUNDATION_HALF_DEPTH; j++) {
        level.setBlock(this.local(base, axis, i, -1, j), blockState, 2);
      }
    }

    int[] s = new int[]{-1, 1};
    int[] q = s;
    int t = 0;

    for (int y = q.length; t < y; t++) {
      int l = q[t];

      for (int m = OUTER_RADIUS; m <= OUTER_RADIUS + 3; m++) {
        int n = 0;
        int isFrame = 6 - (m - OUTER_RADIUS);
        if (n <= isFrame) {
          while (true) {
            for (int p = -2; p < 3; p++) {
              level.setBlock(this.local(base, axis, l * m, n, p), blockState, 2);
            }

            if (n == isFrame) {
              break;
            }

            n++;
          }
        }
      }
    }

    int r = 0;

    for (int u = outerHalfWidths.length; r < u; r++) {
      int v = r + 1;
      int w = outerHalfWidths[r];
      int x = w - 2;
      int y = -w;
      if (y <= w) {
        while (true) {
          boolean bl = x < 0 || Math.abs(y) > x;
          if (bl) {
            for (int z = -1; z < 2; z++) {
              level.setBlock(this.local(base, axis, y, v, z), blockState, 2);
            }
          } else {
            BlockState blockState1 = active ? ModBlock.INSTANCE.getSTARGATE_PORTAL().defaultBlockState() : Blocks.AIR.defaultBlockState();
            level.setBlock(this.local(base, axis, y, v, 0), blockState1, 2);
          }

          if (y == w) {
            break;
          }

          y++;
        }
      }
    }

    this.setBottomPortalRow(level, base, axis, active);

    BlockPos controllerPos = this.local(base, axis, 0, 0, CONTROLLER_DISTANCE);
    level.setBlock(
        controllerPos,
        (BlockState) ((BlockState) ModBlock.INSTANCE
            .getSTARGATE_CONTROLLER()
            .defaultBlockState()
            .setValue((Property) StarGateControllerBlock.ACCESS.getAXIS(), (Comparable) axis))
            .setValue((Property) StarGateControllerBlock.ACCESS.getACTIVE(), active),
        3
    );
    return this.local(base, axis, 0, 1, 0);
  }

  public final boolean activate(@NotNull LevelAccessor level, @NotNull BlockPos controllerPos, @NotNull Axis axis) {
    BlockPos gateBase = this.gateBaseForController(controllerPos, axis);
    if (!this.isFrameIntact(level, gateBase, controllerPos, axis)) {
      return false;
    }

    this.setPortalInterior(level, gateBase, axis, true);
    BlockState blockState = level.getBlockState(controllerPos);
    if (blockState.is(ModBlock.INSTANCE.getSTARGATE_CONTROLLER())) {
      level.setBlock(controllerPos, (BlockState) blockState.setValue((Property) StarGateControllerBlock.ACCESS.getACTIVE(), true), 3);
    }

    return true;
  }

  public final void deactivate(@NotNull LevelAccessor level, @NotNull BlockPos controllerPos, @NotNull Axis axis) {
    this.setPortalInterior(level, this.gateBaseForController(controllerPos, axis), axis, false);
  }

  /**
   * 返回门洞中心，供控制器在门外触发面向整座星门的粒子冲击。
   */
  @NotNull
  public final BlockPos portalCenter(@NotNull BlockPos controllerPos, @NotNull Axis axis) {
    return this.local(this.gateBaseForController(controllerPos, axis), axis, 0, OUTER_RADIUS, 0);
  }

  private final void setPortalInterior(LevelAccessor level, BlockPos base, Axis axis, boolean active) {
    this.setBottomPortalRow(level, base, axis, active);
    int i = 0;

    for (int j = outerHalfWidths.length; i < j; i++) {
      int k = outerHalfWidths[i] - 2;
      if (k >= 0) {
        int l = i + 1;
        int m = -k;
        if (m <= k) {
          while (true) {
            BlockPos blockPos = this.local(base, axis, m, l, 0);
            if (active || level.getBlockState(blockPos).is(ModBlock.INSTANCE.getSTARGATE_PORTAL())) {
              level.setBlock(blockPos, active ? ModBlock.INSTANCE.getSTARGATE_PORTAL().defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
            }

            if (m == k) {
              break;
            }

            m++;
          }
        }
      }
    }
  }

  /**
   * 门洞从基座顶面开始，避免事件视界最下方出现一整行空气。
   */
  private void setBottomPortalRow(LevelAccessor level, BlockPos base, Axis axis, boolean active) {
    int interiorHalfWidth = outerHalfWidths[0] - 2;
    for (int horizontal = -interiorHalfWidth; horizontal <= interiorHalfWidth; horizontal++) {
      BlockPos pos = this.local(base, axis, horizontal, 0, 0);
      if (active || level.getBlockState(pos).is(ModBlock.INSTANCE.getSTARGATE_PORTAL())) {
        level.setBlock(pos, active ? ModBlock.INSTANCE.getSTARGATE_PORTAL().defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
      }
    }
  }

  private final boolean isFrameIntact(LevelAccessor level, BlockPos base, BlockPos controllerPos, Axis axis) {
    BlockState blockState = level.getBlockState(controllerPos);
    if (blockState.is(ModBlock.INSTANCE.getSTARGATE_CONTROLLER()) && blockState.getValue((Property) StarGateControllerBlock.ACCESS.getAXIS()) == axis) {
      int i = 0;

      for (int j = outerHalfWidths.length; i < j; i++) {
        int k = i + 1;
        int l = outerHalfWidths[i];
        int m = l - 2;
        int n = -l;
        if (n <= l) {
          while (true) {
            if (m < 0 || Math.abs(n) > m) {
              for (int o = -1; o < 2; o++) {
                if (!level.getBlockState(this.local(base, axis, n, k, o)).is(ModBlock.INSTANCE.getSTARGATE_ARCH())) {
                  return false;
                }
              }
            }

            if (n == l) {
              break;
            }

            n++;
          }
        }
      }

      return true;
    } else {
      return false;
    }
  }

  /**
   * 控制器固定在门外五格处，反向换算门洞基点。
   */
  private BlockPos gateBaseForController(BlockPos controllerPos, Axis axis) {
    return this.local(controllerPos, axis, 0, 0, -CONTROLLER_DISTANCE);
  }

  private final BlockPos local(BlockPos base, Axis axis, int horizontal, int vertical, int depth) {
    BlockPos blockPos2;
    if (axis == Axis.X) {
      BlockPos blockPos = base.offset(horizontal, vertical, depth);
      blockPos2 = blockPos;
    } else {
      BlockPos blockPos1 = base.offset(depth, vertical, horizontal);
      blockPos2 = blockPos1;
    }

    return blockPos2;
  }
}
