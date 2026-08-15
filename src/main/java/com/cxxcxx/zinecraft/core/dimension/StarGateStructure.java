package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.core.block.ModBlock;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.ranges.IntRange;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class StarGateStructure {
  @NotNull
  public static final StarGateStructure INSTANCE = new StarGateStructure();
  @NotNull
  private static final int[] outerHalfWidths;
  private static final int FRAME_THICKNESS = 2;

  static {
    int[] i = new int[]{8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 7, 6, 5, 3, 1};
    outerHalfWidths = i;
  }

  private StarGateStructure() {
  }

  public final boolean canPlace(@NotNull LevelAccessor level, @NotNull BlockPos base, @NotNull Axis axis) {
    Integer[] $this$all$iv = new Integer[]{-9, 0, 9};
    Iterable iterable2 = CollectionsKt.listOf($this$all$iv);
    int i = 0;
    Iterable $this$mapTo$iv$iv = iterable2;
    var collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable2, 10));
    int j = 0;

    for (Object object : $this$mapTo$iv$iv) {
      int $i$f$all = ((Number) object).intValue();
      Collection collection1 = collection;
      int l = 0;
      BlockPos blockPos = INSTANCE.local(base, axis, $i$f$all, 0, 0);
      collection1.add(level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, blockPos.getX(), blockPos.getZ()));
    }

    List list = (List) collection;
    int maxHeight = list.stream().mapToInt(value -> ((Number) value).intValue()).max().orElseThrow();
    int minHeight = list.stream().mapToInt(value -> ((Number) value).intValue()).min().orElseThrow();
    if (maxHeight - minHeight > 2) {
      return false;
    }

    Iterable iterable3 = (Iterable) (new IntRange(2, 19));
    i = 0;
    boolean bl;
    if (iterable3 instanceof Collection && ((Collection) iterable3).isEmpty()) {
      bl = true;
    } else {
      Iterator iterator1 = iterable3.iterator();

      while (true) {
        if (!iterator1.hasNext()) {
          bl = true;
          break;
        }

        int s = ((IntIterator) iterator1).nextInt();
        j = s;
        int t = 0;
        Iterable iterable4 = (Iterable) (new IntRange(-10, 10));
        int u = 0;
        if (iterable4 instanceof Collection && ((Collection) iterable4).isEmpty()) {
          bl = true;
        } else {
          Iterator iterator2 = iterable4.iterator();

          while (true) {
            if (!iterator2.hasNext()) {
              bl = true;
              break;
            }

            int v = ((IntIterator) iterator2).nextInt();
            int m = v;
            int n = 0;
            Iterable iterable1 = (Iterable) (new IntRange(-3, 3));
            int o = 0;
            if (iterable1 instanceof Collection && ((Collection) iterable1).isEmpty()) {
              bl = true;
            } else {
              Iterator iterator = iterable1.iterator();

              while (true) {
                if (!iterator.hasNext()) {
                  bl = true;
                  break;
                }

                int p = ((IntIterator) iterator).nextInt();
                int q = p;
                int r = 0;
                BlockState blockState = level.getBlockState(INSTANCE.local(base, axis, m, j, q));
                if (!blockState.canBeReplaced() && !blockState.is(Blocks.SNOW) && !blockState.is(Blocks.SNOW_BLOCK)) {
                  bl = false;
                  break;
                }
              }
            }

            if (!bl) {
              bl = false;
              break;
            }
          }
        }

        if (!bl) {
          bl = false;
          break;
        }
      }
    }

    return bl;
  }

  @NotNull
  public final BlockPos place(@NotNull LevelAccessor level, @NotNull BlockPos base, @NotNull Axis axis, boolean active) {
    BlockState blockState = ModBlock.INSTANCE.getSTARGATE_ARCH().defaultBlockState();

    for (int i = -10; i < 11; i++) {
      for (int j = -3; j < 4; j++) {
        level.setBlock(this.local(base, axis, i, -1, j), blockState, 2);
      }
    }

    int[] s = new int[]{-1, 1};
    int[] q = s;
    int t = 0;

    for (int y = q.length; t < y; t++) {
      int l = q[t];

      for (int m = 8; m < 11; m++) {
        int n = 0;
        int isFrame = 5 - (m - 8);
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

    level.setBlock(
        base,
        (BlockState) ((BlockState) ModBlock.INSTANCE
            .getSTARGATE_CONTROLLER()
            .defaultBlockState()
            .setValue((Property) StarGateControllerBlock.Companion.getAXIS(), (Comparable) axis))
            .setValue((Property) StarGateControllerBlock.Companion.getACTIVE(), active),
        3
    );
    return this.local(base, axis, 0, 1, 0);
  }

  public final boolean activate(@NotNull LevelAccessor level, @NotNull BlockPos controllerPos, @NotNull Axis axis) {
    if (!this.isFrameIntact(level, controllerPos, axis)) {
      return false;
    }

    this.setPortalInterior(level, controllerPos, axis, true);
    BlockState blockState = level.getBlockState(controllerPos);
    if (blockState.is(ModBlock.INSTANCE.getSTARGATE_CONTROLLER())) {
      level.setBlock(controllerPos, (BlockState) blockState.setValue((Property) StarGateControllerBlock.Companion.getACTIVE(), true), 3);
    }

    return true;
  }

  public final void deactivate(@NotNull LevelAccessor level, @NotNull BlockPos controllerPos, @NotNull Axis axis) {
    this.setPortalInterior(level, controllerPos, axis, false);
  }

  private final void setPortalInterior(LevelAccessor level, BlockPos base, Axis axis, boolean active) {
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

  private final boolean isFrameIntact(LevelAccessor level, BlockPos base, Axis axis) {
    BlockState blockState = level.getBlockState(base);
    if (blockState.is(ModBlock.INSTANCE.getSTARGATE_CONTROLLER()) && blockState.getValue((Property) StarGateControllerBlock.Companion.getAXIS()) == axis) {
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

