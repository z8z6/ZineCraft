package com.cxxcxx.zinecraft.core.structure.stargate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * 星门放置与结构校验共用的纯坐标模型。
 */
final class StarGateGeometry {
  static final int OUTER_RADIUS = 12;
  static final int CONTROLLER_DISTANCE = 5;
  static final int FOUNDATION_HALF_WIDTH = OUTER_RADIUS + 2;
  static final int FOUNDATION_HALF_DEPTH = CONTROLLER_DISTANCE + 1;

  private static final int FRAME_HALF_DEPTH = 1;
  private static final int FRAME_THICKNESS = 2;
  private static final int[] OUTER_HALF_WIDTHS = createOuterHalfWidths();

  private StarGateGeometry() {
  }

  static HorizontalAxis rotateAxis(HorizontalAxis axis, boolean quarterTurn) {
    return quarterTurn ? axis.other() : axis;
  }

  static Optional<Match> locateGate(Position controller, HorizontalAxis preferredAxis, Predicate<Position> isArch) {
    for (HorizontalAxis axis : List.of(preferredAxis, preferredAxis.other())) {
      for (Position base : gateBaseCandidates(controller, axis)) {
        if (requiredFramePositions(base, axis).stream().allMatch(isArch)) {
          return Optional.of(new Match(base, axis));
        }
      }
    }
    return Optional.empty();
  }

  static List<Position> gateBaseCandidates(Position controller, HorizontalAxis axis) {
    return List.of(
        local(controller, axis, 0, 0, -CONTROLLER_DISTANCE),
        local(controller, axis, 0, 0, CONTROLLER_DISTANCE)
    );
  }

  static List<Position> requiredFramePositions(Position base, HorizontalAxis axis) {
    List<Position> positions = new ArrayList<>();
    for (int vertical = 1; vertical <= gateHeight(); vertical++) {
      int halfWidth = outerHalfWidth(vertical);
      int interiorHalfWidth = halfWidth - FRAME_THICKNESS;
      for (int horizontal = -halfWidth; horizontal <= halfWidth; horizontal++) {
        if (interiorHalfWidth < 0 || Math.abs(horizontal) > interiorHalfWidth) {
          for (int depth = -FRAME_HALF_DEPTH; depth <= FRAME_HALF_DEPTH; depth++) {
            positions.add(local(base, axis, horizontal, vertical, depth));
          }
        }
      }
    }
    return List.copyOf(positions);
  }

  static List<Position> portalInteriorPositions(Position base, HorizontalAxis axis) {
    List<Position> positions = new ArrayList<>();
    addInteriorRow(positions, base, axis, 0, outerHalfWidth(1) - FRAME_THICKNESS);
    for (int vertical = 1; vertical <= gateHeight(); vertical++) {
      addInteriorRow(positions, base, axis, vertical, outerHalfWidth(vertical) - FRAME_THICKNESS);
    }
    return List.copyOf(positions);
  }

  static int gateHeight() {
    return OUTER_HALF_WIDTHS.length;
  }

  static int outerHalfWidth(int vertical) {
    if (vertical < 1 || vertical > gateHeight()) {
      throw new IllegalArgumentException("星门垂直层超出范围: " + vertical);
    }
    return OUTER_HALF_WIDTHS[vertical - 1];
  }

  static Position local(Position base, HorizontalAxis axis, int horizontal, int vertical, int depth) {
    return axis == HorizontalAxis.X
        ? base.offset(horizontal, vertical, depth)
        : base.offset(depth, vertical, horizontal);
  }

  private static void addInteriorRow(
      List<Position> positions,
      Position base,
      HorizontalAxis axis,
      int vertical,
      int halfWidth
  ) {
    for (int horizontal = -halfWidth; horizontal <= halfWidth; horizontal++) {
      positions.add(local(base, axis, horizontal, vertical, 0));
    }
  }

  private static int[] createOuterHalfWidths() {
    int[] widths = new int[OUTER_RADIUS * 2];
    for (int vertical = 1; vertical <= widths.length; vertical++) {
      int curveHeight = Math.max(vertical - OUTER_RADIUS, 0);
      double squaredWidth = OUTER_RADIUS * OUTER_RADIUS - curveHeight * curveHeight;
      widths[vertical - 1] = Math.max(1, (int) Math.round(Math.sqrt(squaredWidth)));
    }
    return widths;
  }

  enum HorizontalAxis {
    X,
    Z;

    HorizontalAxis other() {
      return this == X ? Z : X;
    }
  }

  record Position(int x, int y, int z) {
    Position offset(int xOffset, int yOffset, int zOffset) {
      return new Position(x + xOffset, y + yOffset, z + zOffset);
    }
  }

  record Match(Position base, HorizontalAxis axis) {
  }
}
