package com.cxxcxx.zinecraft.core.structure.stargate;

import com.cxxcxx.zinecraft.core.registry.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class StarGateStructure {
  public static final StarGateStructure INSTANCE = new StarGateStructure();

  private static final int MAX_FOUNDATION_HEIGHT_DIFFERENCE = 2;
  private static final int CLEARANCE_BOTTOM_OFFSET = 2;
  private static final int CLEARANCE_TOP_OFFSET = 2;
  private static final int CLEARANCE_BACK_DEPTH = 3;
  private static final int FOUNDATION_FRONT_DEPTH = 3;
  private static final int BUTTRESS_HALF_DEPTH = 2;
  private static final int BUTTRESS_EXTENSION = 3;
  private static final int BUTTRESS_BASE_HEIGHT = 6;

  private StarGateStructure() {
  }

  static Optional<GateMatch> locateGate(
      BlockPos controllerPos,
      Axis preferredAxis,
      Predicate<BlockPos> isArch
  ) {
    return StarGateGeometry.locateGate(
        position(controllerPos),
        horizontalAxis(preferredAxis),
        candidate -> isArch.test(blockPos(candidate))
    ).map(match -> new GateMatch(blockPos(match.base()), minecraftAxis(match.axis())));
  }

  static List<BlockPos> gateBaseCandidates(BlockPos controllerPos, Axis axis) {
    return StarGateGeometry.gateBaseCandidates(position(controllerPos), horizontalAxis(axis)).stream()
        .map(StarGateStructure::blockPos)
        .toList();
  }

  private static Optional<GateMatch> findGate(LevelAccessor level, BlockPos controllerPos, Axis axis) {
    return locateGate(
        controllerPos,
        axis,
        pos -> level.getBlockState(pos).is(ModBlock.STARGATE_ARCH.get())
    );
  }

  private static void placeFoundation(LevelAccessor level, BlockPos base, Axis axis, BlockState arch) {
    for (int horizontal = -StarGateGeometry.FOUNDATION_HALF_WIDTH;
         horizontal <= StarGateGeometry.FOUNDATION_HALF_WIDTH;
         horizontal++) {
      for (int depth = -FOUNDATION_FRONT_DEPTH; depth <= StarGateGeometry.FOUNDATION_HALF_DEPTH; depth++) {
        level.setBlock(local(base, axis, horizontal, -1, depth), arch, 2);
      }
    }
  }

  private static void placeButtresses(LevelAccessor level, BlockPos base, Axis axis, BlockState arch) {
    for (int side : new int[]{-1, 1}) {
      for (int distance = StarGateGeometry.OUTER_RADIUS;
           distance <= StarGateGeometry.OUTER_RADIUS + BUTTRESS_EXTENSION;
           distance++) {
        int height = BUTTRESS_BASE_HEIGHT - (distance - StarGateGeometry.OUTER_RADIUS);
        for (int vertical = 0; vertical <= height; vertical++) {
          for (int depth = -BUTTRESS_HALF_DEPTH; depth <= BUTTRESS_HALF_DEPTH; depth++) {
            level.setBlock(local(base, axis, side * distance, vertical, depth), arch, 2);
          }
        }
      }
    }
  }

  private static void placePortalInterior(LevelAccessor level, BlockPos base, Axis axis, boolean active) {
    BlockState portalState = portalState(active);
    for (StarGateGeometry.Position position : portalPositions(base, axis)) {
      BlockPos pos = blockPos(position);
      boolean isBottomRow = pos.getY() == base.getY();
      if (active || !isBottomRow || level.getBlockState(pos).is(ModBlock.STARGATE_PORTAL.get())) {
        level.setBlock(pos, portalState, isBottomRow ? 3 : 2);
      }
    }
  }

  private static void setPortalInterior(LevelAccessor level, BlockPos base, Axis axis, boolean active) {
    BlockState portalState = portalState(active);
    for (StarGateGeometry.Position position : portalPositions(base, axis)) {
      BlockPos pos = blockPos(position);
      if (active || level.getBlockState(pos).is(ModBlock.STARGATE_PORTAL.get())) {
        level.setBlock(pos, portalState, 3);
      }
    }
  }

  private static List<StarGateGeometry.Position> portalPositions(BlockPos base, Axis axis) {
    return StarGateGeometry.portalInteriorPositions(position(base), horizontalAxis(axis));
  }

  private static BlockState portalState(boolean active) {
    return active
        ? ModBlock.STARGATE_PORTAL.get().defaultBlockState()
        : Blocks.AIR.defaultBlockState();
  }

  private static List<Axis> horizontalAxes(Axis preferredAxis) {
    Axis alternate = preferredAxis == Axis.X ? Axis.Z : Axis.X;
    return List.of(preferredAxis, alternate);
  }

  private static StarGateGeometry.Position position(BlockPos pos) {
    return new StarGateGeometry.Position(pos.getX(), pos.getY(), pos.getZ());
  }

  private static BlockPos blockPos(StarGateGeometry.Position pos) {
    return new BlockPos(pos.x(), pos.y(), pos.z());
  }

  private static StarGateGeometry.HorizontalAxis horizontalAxis(Axis axis) {
    return axis == Axis.X ? StarGateGeometry.HorizontalAxis.X : StarGateGeometry.HorizontalAxis.Z;
  }

  private static Axis minecraftAxis(StarGateGeometry.HorizontalAxis axis) {
    return axis == StarGateGeometry.HorizontalAxis.X ? Axis.X : Axis.Z;
  }

  private static BlockPos local(BlockPos base, Axis axis, int horizontal, int vertical, int depth) {
    return blockPos(StarGateGeometry.local(position(base), horizontalAxis(axis), horizontal, vertical, depth));
  }

  public boolean canPlace(LevelAccessor level, BlockPos base, Axis axis) {
    int minHeight = Integer.MAX_VALUE;
    int maxHeight = Integer.MIN_VALUE;
    for (int horizontal : new int[]{-StarGateGeometry.FOUNDATION_HALF_WIDTH, 0, StarGateGeometry.FOUNDATION_HALF_WIDTH}) {
      BlockPos sample = local(base, axis, horizontal, 0, 0);
      int height = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, sample.getX(), sample.getZ());
      minHeight = Math.min(minHeight, height);
      maxHeight = Math.max(maxHeight, height);
    }
    if (maxHeight - minHeight > MAX_FOUNDATION_HEIGHT_DIFFERENCE) {
      return false;
    }

    int clearanceTop = StarGateGeometry.gateHeight() + CLEARANCE_TOP_OFFSET;
    for (int vertical = CLEARANCE_BOTTOM_OFFSET; vertical <= clearanceTop; vertical++) {
      for (int horizontal = -StarGateGeometry.FOUNDATION_HALF_WIDTH;
           horizontal <= StarGateGeometry.FOUNDATION_HALF_WIDTH;
           horizontal++) {
        for (int depth = -StarGateGeometry.FOUNDATION_HALF_DEPTH; depth <= CLEARANCE_BACK_DEPTH; depth++) {
          BlockState state = level.getBlockState(local(base, axis, horizontal, vertical, depth));
          if (!state.canBeReplaced() && !state.is(Blocks.SNOW) && !state.is(Blocks.SNOW_BLOCK)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public BlockPos place(LevelAccessor level, BlockPos base, Axis axis, boolean active) {
    BlockState arch = ModBlock.STARGATE_ARCH.get().defaultBlockState();
    placeFoundation(level, base, axis, arch);
    placeButtresses(level, base, axis, arch);

    StarGateGeometry.Position geometryBase = position(base);
    for (StarGateGeometry.Position framePosition : StarGateGeometry.requiredFramePositions(geometryBase, horizontalAxis(axis))) {
      level.setBlock(blockPos(framePosition), arch, 2);
    }
    placePortalInterior(level, base, axis, active);

    BlockPos controllerPos = local(base, axis, 0, 0, StarGateGeometry.CONTROLLER_DISTANCE);
    BlockState controller = ModBlock.STARGATE_CONTROLLER.get().defaultBlockState()
        .setValue(StarGateControllerBlock.AXIS, axis)
        .setValue(StarGateControllerBlock.ACTIVE, active);
    level.setBlock(controllerPos, controller, 3);

    return local(base, axis, 0, 1, 0);
  }

  public boolean activate(LevelAccessor level, BlockPos controllerPos, Axis axis) {
    BlockState controllerState = level.getBlockState(controllerPos);
    if (!controllerState.is(ModBlock.STARGATE_CONTROLLER.get())) {
      return false;
    }

    Optional<GateMatch> match = findGate(level, controllerPos, axis);
    if (match.isEmpty()) {
      return false;
    }

    GateMatch gate = match.get();
    setPortalInterior(level, gate.base(), gate.axis(), true);
    level.setBlock(
        controllerPos,
        controllerState
            .setValue(StarGateControllerBlock.AXIS, gate.axis())
            .setValue(StarGateControllerBlock.ACTIVE, true),
        3
    );
    return true;
  }

  public void deactivate(LevelAccessor level, BlockPos controllerPos, Axis axis) {
    // 控制器被替换后才会调用 onRemove，因此需要检查两侧和两个水平轴，
    // 确保旧版旋转模板中轴属性错误的传送门也能被清除。
    for (Axis candidateAxis : horizontalAxes(axis)) {
      for (BlockPos base : gateBaseCandidates(controllerPos, candidateAxis)) {
        setPortalInterior(level, base, candidateAxis, false);
      }
    }
  }

  /**
   * 返回星门中心，作为激活特效的原点。
   */
  public BlockPos portalCenter(LevelAccessor level, BlockPos controllerPos, Axis axis) {
    GateMatch gate = findGate(level, controllerPos, axis)
        .orElseGet(() -> new GateMatch(gateBaseCandidates(controllerPos, axis).getFirst(), axis));
    return local(gate.base(), gate.axis(), 0, StarGateGeometry.OUTER_RADIUS, 0);
  }

  record GateMatch(BlockPos base, Axis axis) {
  }
}
