package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.core.dimension.StarGateGeometry.HorizontalAxis;
import com.cxxcxx.zinecraft.core.dimension.StarGateGeometry.Position;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StarGateGeometryTest {
  private static List<Position> controllersFor(Position base, HorizontalAxis axis) {
    return StarGateGeometry.gateBaseCandidates(base, axis);
  }

  @Test
  void controllerAxisFollowsQuarterTurnStructureRotations() {
    assertEquals(HorizontalAxis.X, StarGateGeometry.rotateAxis(HorizontalAxis.X, false));
    assertEquals(HorizontalAxis.Z, StarGateGeometry.rotateAxis(HorizontalAxis.X, true));
    assertEquals(HorizontalAxis.X, StarGateGeometry.rotateAxis(HorizontalAxis.Z, true));
  }

  @Test
  void locatesGateOnEitherSideOfController() {
    Position base = new Position(40, 80, -20);
    for (HorizontalAxis axis : HorizontalAxis.values()) {
      Set<Position> frame = new HashSet<>(StarGateGeometry.requiredFramePositions(base, axis));
      for (Position controller : controllersFor(base, axis)) {
        StarGateGeometry.Match match = StarGateGeometry.locateGate(controller, axis, frame::contains).orElseThrow();
        assertEquals(base, match.base());
        assertEquals(axis, match.axis());
      }
    }
  }

  @Test
  void repairsAxisFromPreviouslyGeneratedRotatedGate() {
    Position base = new Position(-12, 72, 91);
    Set<Position> zAxisFrame = new HashSet<>(StarGateGeometry.requiredFramePositions(base, HorizontalAxis.Z));
    Position controller = controllersFor(base, HorizontalAxis.Z).get(0);

    StarGateGeometry.Match match = StarGateGeometry
        .locateGate(controller, HorizontalAxis.X, zAxisFrame::contains)
        .orElseThrow();

    assertEquals(base, match.base());
    assertEquals(HorizontalAxis.Z, match.axis());
  }

  @Test
  void rejectsFrameWithOneMissingArchstone() {
    Position base = new Position(0, 0, 0);
    Set<Position> frame = new HashSet<>(StarGateGeometry.requiredFramePositions(base, HorizontalAxis.X));
    assertTrue(frame.remove(frame.iterator().next()));

    for (Position controller : controllersFor(base, HorizontalAxis.X)) {
      assertTrue(StarGateGeometry.locateGate(controller, HorizontalAxis.X, frame::contains).isEmpty());
    }
  }
}
