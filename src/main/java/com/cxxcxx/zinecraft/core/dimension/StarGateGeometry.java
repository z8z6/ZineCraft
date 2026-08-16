package com.cxxcxx.zinecraft.core.dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Pure coordinate model shared by runtime stargate checks and unit tests.
 */
final class StarGateGeometry {
  private static final int OUTER_RADIUS = 12;
  private static final int CONTROLLER_DISTANCE = 5;
  private static final int[] OUTER_HALF_WIDTHS = createOuterHalfWidths();

  private StarGateGeometry() {
  }

  static HorizontalAxis rotateAxis(HorizontalAxis axis, boolean quarterTurn) {
    return quarterTurn ? axis.other() : axis;
  }

  static Optional<Match> locateGate(Position controller, HorizontalAxis preferredAxis, Predicate<Position> isArch) {
    for (HorizontalAxis candidateAxis : List.of(preferredAxis, preferredAxis.other())) {
      for (Position base : gateBaseCandidates(controller, candidateAxis)) {
        if (requiredFramePositions(base, candidateAxis).stream().allMatch(isArch)) {
          return Optional.of(new Match(base, candidateAxis));
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
    ArrayList<Position> positions = new ArrayList<>();
    for (int vertical = 1; vertical <= OUTER_HALF_WIDTHS.length; vertical++) {
      int halfWidth = OUTER_HALF_WIDTHS[vertical - 1];
      int interiorHalfWidth = halfWidth - 2;
      for (int horizontal = -halfWidth; horizontal <= halfWidth; horizontal++) {
        if (interiorHalfWidth < 0 || Math.abs(horizontal) > interiorHalfWidth) {
          for (int depth = -1; depth <= 1; depth++) {
            positions.add(local(base, axis, horizontal, vertical, depth));
          }
        }
      }
    }
    return List.copyOf(positions);
  }

  private static Position local(Position base, HorizontalAxis axis, int horizontal, int vertical, int depth) {
    return axis == HorizontalAxis.X
        ? base.offset(horizontal, vertical, depth)
        : base.offset(depth, vertical, horizontal);
  }

  private static int[] createOuterHalfWidths() {
    int[] widths = new int[OUTER_RADIUS * 2];
    for (int vertical = 1; vertical <= widths.length; vertical++) {
      int curveHeight = Math.max(vertical - OUTER_RADIUS, 0);
      widths[vertical - 1] = Math.max(1, (int) Math.round(Math.sqrt(OUTER_RADIUS * OUTER_RADIUS - curveHeight * curveHeight)));
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
