package com.cxxcxx.zinecraft.api.world.layout;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;

import java.util.List;
import java.util.Objects;

/** 在任意简单多边形内计算边界和长宽均与区块网格对齐的最大 X/Z 轴平行矩形。 */
public final class AxisAlignedRectangleCalculator {
  private static final int CHUNK_SIZE = 16;
  private static final double ALIGNMENT_EPSILON = 1.0E-9;
  private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

  public PlanarRectangle calculate(List<PlanarPoint> boundary) {
    List<PlanarPoint> polygon = List.copyOf(Objects.requireNonNull(boundary, "矩形约束边界不能为空"));
    if (polygon.size() < 3) throw new IllegalArgumentException("矩形约束边界至少需要三个点");
    Geometry region = polygon(polygon);
    if (!region.isValid()) region = region.buffer(0.0);
    if (region.isEmpty() || region.getArea() <= 0.0) {
      throw new IllegalArgumentException("Region 边界没有可用面积");
    }
    Candidate best = largestChunkRectangle(
        PreparedGeometryFactory.prepare(region), region.getEnvelopeInternal()
    );
    if (best == null) {
      throw new IllegalArgumentException("Region 内无法容纳边界对齐区块的非退化矩形");
    }
    return best.rectangle();
  }

  private static Candidate largestChunkRectangle(PreparedGeometry region, Envelope envelope) {
    int gridMinX = alignedCeiling(envelope.getMinX());
    int gridMaxX = alignedFloor(envelope.getMaxX());
    int gridMinZ = alignedCeiling(envelope.getMinY());
    int gridMaxZ = alignedFloor(envelope.getMaxY());
    int columns = (gridMaxX - gridMinX) / CHUNK_SIZE;
    int rows = (gridMaxZ - gridMinZ) / CHUNK_SIZE;
    if (columns < 1 || rows < 1) return null;

    int[] heights = new int[columns];
    int[] stack = new int[columns];
    Candidate best = null;
    long bestChunks = 0;
    for (int row = 0; row < rows; row++) {
      int minZ = gridMinZ + row * CHUNK_SIZE;
      for (int column = 0; column < columns; column++) {
        int minX = gridMinX + column * CHUNK_SIZE;
        Geometry chunk = GEOMETRY_FACTORY.toGeometry(new Envelope(
            minX, minX + CHUNK_SIZE, minZ, minZ + CHUNK_SIZE
        ));
        heights[column] = region.covers(chunk) ? heights[column] + 1 : 0;
      }

      int stackSize = 0;
      for (int column = 0; column <= columns; column++) {
        int currentHeight = column == columns ? 0 : heights[column];
        while (stackSize > 0 && heights[stack[stackSize - 1]] > currentHeight) {
          int availableHeight = heights[stack[--stackSize]];
          int left = stackSize == 0 ? 0 : stack[stackSize - 1] + 1;
          int availableWidth = column - left;
          int height = availableHeight;
          int width = availableWidth;
          long chunks = (long) width * height;
          Candidate candidate = new Candidate(
              gridMinX + left * CHUNK_SIZE,
              gridMinX + (left + width) * CHUNK_SIZE,
              gridMinZ + (row - height + 1) * CHUNK_SIZE,
              gridMinZ + (row + 1) * CHUNK_SIZE
          );
          if (chunks > bestChunks || chunks == bestChunks && better(candidate, best)) {
            bestChunks = chunks;
            best = candidate;
          }
        }
        if (column < columns) stack[stackSize++] = column;
      }
    }
    return best;
  }

  private static int alignedCeiling(double value) {
    long chunks = (long) Math.ceil(value / CHUNK_SIZE - ALIGNMENT_EPSILON);
    return Math.toIntExact(Math.multiplyExact(chunks, CHUNK_SIZE));
  }

  private static int alignedFloor(double value) {
    long chunks = (long) Math.floor(value / CHUNK_SIZE + ALIGNMENT_EPSILON);
    return Math.toIntExact(Math.multiplyExact(chunks, CHUNK_SIZE));
  }

  private static Geometry polygon(List<PlanarPoint> points) {
    Coordinate[] coordinates = new Coordinate[points.size() + 1];
    for (int index = 0; index < points.size(); index++) {
      PlanarPoint point = points.get(index);
      coordinates[index] = new Coordinate(point.x(), point.z());
    }
    coordinates[points.size()] = coordinates[0].copy();
    return GEOMETRY_FACTORY.createPolygon(coordinates);
  }

  private static boolean better(Candidate candidate, Candidate current) {
    return current == null || candidate.width() + candidate.height() > current.width() + current.height()
        || candidate.width() + candidate.height() == current.width() + current.height()
        && (candidate.minZ() < current.minZ()
        || candidate.minZ() == current.minZ() && candidate.minX() < current.minX());
  }

  private record Candidate(int minX, int maxX, int minZ, int maxZ) {
    private int width() {
      return maxX - minX;
    }

    private int height() {
      return maxZ - minZ;
    }

    private PlanarRectangle rectangle() {
      return new PlanarRectangle(
          new PlanarPoint((minX + maxX) / 2.0, (minZ + maxZ) / 2.0),
          width() / 2.0,
          height() / 2.0,
          0.0
      );
    }
  }
}
