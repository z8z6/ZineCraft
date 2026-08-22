package com.cxxcxx.zinecraft.api.world.layout;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 根据多边形是否共享一段正长度边界，计算同级节点的无向邻接关系。
 *
 * <p>只有角点接触不视为相邻。计算使用相对坐标尺度容差，以吸收 Voronoi 裁剪产生的浮点误差。</p>
 */
public final class PolygonAdjacencyCalculator {
  private static final double RELATIVE_EPSILON = 1.0E-9;
  private static final double MINIMUM_EPSILON = 1.0E-7;

  public <T> List<List<Integer>> calculate(
      List<T> nodes,
      Function<T, List<PlanarPoint>> boundaryReader
  ) {
    List<T> declaredNodes = List.copyOf(Objects.requireNonNull(nodes, "邻接节点清单不能为空"));
    Objects.requireNonNull(boundaryReader, "邻接边界读取器不能为空");
    List<List<PlanarPoint>> boundaries = declaredNodes.stream()
        .map(node -> List.copyOf(Objects.requireNonNull(boundaryReader.apply(node), "邻接边界不能为空")))
        .toList();
    validateBoundaries(boundaries);

    return calculate(boundaries, allPairs(boundaries.size()));
  }

  /**
   * 利用点站点的德洛内三角剖分缩小候选集合，再以最终 Voronoi 边界复核真实邻接。
   */
  public <T> List<List<Integer>> calculateVoronoi(
      List<T> nodes,
      Function<T, List<PlanarPoint>> boundaryReader,
      Function<T, PlanarPoint> siteReader
  ) {
    List<T> declaredNodes = List.copyOf(Objects.requireNonNull(nodes, "邻接节点清单不能为空"));
    Objects.requireNonNull(boundaryReader, "邻接边界读取器不能为空");
    Objects.requireNonNull(siteReader, "德洛内站点读取器不能为空");
    List<List<PlanarPoint>> boundaries = declaredNodes.stream()
        .map(node -> List.copyOf(Objects.requireNonNull(boundaryReader.apply(node), "邻接边界不能为空")))
        .toList();
    validateBoundaries(boundaries);
    List<PlanarPoint> sites = declaredNodes.stream()
        .map(node -> Objects.requireNonNull(siteReader.apply(node), "德洛内站点不能为空"))
        .toList();
    return calculate(boundaries, delaunayPairs(sites));
  }

  private static void validateBoundaries(List<List<PlanarPoint>> boundaries) {
    for (List<PlanarPoint> boundary : boundaries) {
      if (boundary.size() < 3) throw new IllegalArgumentException("邻接边界至少需要三个点");
    }
  }

  private static List<List<Integer>> calculate(
      List<List<PlanarPoint>> boundaries,
      Set<IndexPair> candidates
  ) {
    double epsilon = epsilon(boundaries);
    ArrayList<ArrayList<Integer>> mutable = new ArrayList<>(boundaries.size());
    for (int index = 0; index < boundaries.size(); index++) mutable.add(new ArrayList<>());
    for (IndexPair candidate : candidates) {
      int first = candidate.first();
      int second = candidate.second();
      if (sharedBoundaryMidpoint(boundaries.get(first), boundaries.get(second), epsilon).isEmpty()) continue;
      mutable.get(first).add(second);
      mutable.get(second).add(first);
    }
    return mutable.stream().map(neighbors -> neighbors.stream().sorted().toList()).toList();
  }

  private static Set<IndexPair> allPairs(int size) {
    LinkedHashSet<IndexPair> pairs = new LinkedHashSet<>();
    for (int first = 0; first < size; first++) {
      for (int second = first + 1; second < size; second++) pairs.add(new IndexPair(first, second));
    }
    return pairs;
  }

  private static Set<IndexPair> delaunayPairs(List<PlanarPoint> sites) {
    if (sites.size() < 2) return Set.of();
    if (sites.size() == 2) return Set.of(new IndexPair(0, 1));
    GeometryFactory geometryFactory = new GeometryFactory();
    Coordinate[] coordinates = sites.stream()
        .map(site -> new Coordinate(site.x(), site.z()))
        .toArray(Coordinate[]::new);
    DelaunayTriangulationBuilder builder = new DelaunayTriangulationBuilder();
    builder.setSites(geometryFactory.createMultiPointFromCoords(coordinates));
    Geometry edges = builder.getEdges(geometryFactory);
    LinkedHashSet<IndexPair> pairs = new LinkedHashSet<>();
    for (int index = 0; index < edges.getNumGeometries(); index++) {
      Coordinate[] edge = edges.getGeometryN(index).getCoordinates();
      if (edge.length < 2) continue;
      int first = siteIndex(sites, edge[0]);
      int second = siteIndex(sites, edge[edge.length - 1]);
      if (first != second) pairs.add(IndexPair.sorted(first, second));
    }
    return pairs.isEmpty() ? allPairs(sites.size()) : pairs;
  }

  private static int siteIndex(List<PlanarPoint> sites, Coordinate coordinate) {
    int nearest = -1;
    double nearestDistance = Double.POSITIVE_INFINITY;
    for (int index = 0; index < sites.size(); index++) {
      PlanarPoint site = sites.get(index);
      double distance = Math.hypot(site.x() - coordinate.x, site.z() - coordinate.y);
      if (distance < nearestDistance) {
        nearest = index;
        nearestDistance = distance;
      }
    }
    if (nearest < 0) throw new IllegalStateException("德洛内边端点无法映射到布局站点");
    return nearest;
  }

  private static double epsilon(List<List<PlanarPoint>> boundaries) {
    double scale = 1.0;
    for (List<PlanarPoint> boundary : boundaries) {
      for (PlanarPoint point : boundary) {
        scale = Math.max(scale, Math.max(Math.abs(point.x()), Math.abs(point.z())));
      }
    }
    return Math.max(MINIMUM_EPSILON, scale * RELATIVE_EPSILON);
  }

  /** 返回两多边形第一段共享正长度边界的中点；仅角点接触时返回空。 */
  public Optional<PlanarPoint> sharedBoundaryMidpoint(
      List<PlanarPoint> first,
      List<PlanarPoint> second
  ) {
    List<PlanarPoint> firstBoundary = List.copyOf(Objects.requireNonNull(first, "第一个邻接边界不能为空"));
    List<PlanarPoint> secondBoundary = List.copyOf(Objects.requireNonNull(second, "第二个邻接边界不能为空"));
    validateBoundaries(List.of(firstBoundary, secondBoundary));
    return sharedBoundaryMidpoint(
        firstBoundary,
        secondBoundary,
        epsilon(List.of(firstBoundary, secondBoundary))
    );
  }

  private static Optional<PlanarPoint> sharedBoundaryMidpoint(
      List<PlanarPoint> first,
      List<PlanarPoint> second,
      double epsilon
  ) {
    PlanarPoint firstStart = first.getLast();
    for (PlanarPoint firstEnd : first) {
      PlanarPoint secondStart = second.getLast();
      for (PlanarPoint secondEnd : second) {
        Optional<PlanarPoint> midpoint = overlapMidpoint(
            firstStart,
            firstEnd,
            secondStart,
            secondEnd,
            epsilon
        );
        if (midpoint.isPresent()) return midpoint;
        secondStart = secondEnd;
      }
      firstStart = firstEnd;
    }
    return Optional.empty();
  }

  private static Optional<PlanarPoint> overlapMidpoint(
      PlanarPoint firstStart,
      PlanarPoint firstEnd,
      PlanarPoint secondStart,
      PlanarPoint secondEnd,
      double epsilon
  ) {
    double edgeX = firstEnd.x() - firstStart.x();
    double edgeZ = firstEnd.z() - firstStart.z();
    double length = Math.hypot(edgeX, edgeZ);
    if (length <= epsilon) return Optional.empty();
    if (lineDistance(firstStart, edgeX, edgeZ, length, secondStart) > epsilon
        || lineDistance(firstStart, edgeX, edgeZ, length, secondEnd) > epsilon) {
      return Optional.empty();
    }
    double unitX = edgeX / length;
    double unitZ = edgeZ / length;
    double secondStartPosition = projection(firstStart, unitX, unitZ, secondStart);
    double secondEndPosition = projection(firstStart, unitX, unitZ, secondEnd);
    double overlapStart = Math.max(0.0, Math.min(secondStartPosition, secondEndPosition));
    double overlapEnd = Math.min(length, Math.max(secondStartPosition, secondEndPosition));
    if (overlapEnd - overlapStart <= epsilon) return Optional.empty();
    double midpoint = (overlapStart + overlapEnd) / 2.0;
    return Optional.of(new PlanarPoint(
        firstStart.x() + unitX * midpoint,
        firstStart.z() + unitZ * midpoint
    ));
  }

  private static double lineDistance(
      PlanarPoint lineStart,
      double edgeX,
      double edgeZ,
      double length,
      PlanarPoint point
  ) {
    double offsetX = point.x() - lineStart.x();
    double offsetZ = point.z() - lineStart.z();
    return Math.abs(edgeX * offsetZ - edgeZ * offsetX) / length;
  }

  private static double projection(
      PlanarPoint origin,
      double unitX,
      double unitZ,
      PlanarPoint point
  ) {
    return (point.x() - origin.x()) * unitX + (point.z() - origin.z()) * unitZ;
  }

  private record IndexPair(int first, int second) {
    private static IndexPair sorted(int first, int second) {
      return first < second ? new IndexPair(first, second) : new IndexPair(second, first);
    }
  }
}
