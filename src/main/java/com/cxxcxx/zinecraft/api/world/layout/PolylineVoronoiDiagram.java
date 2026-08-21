package com.cxxcxx.zinecraft.api.world.layout;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.operation.distance.DistanceOp;
import org.locationtech.jts.operation.union.UnaryUnionOp;
import org.rogach.jopenvoronoi.geometry.Edge;
import org.rogach.jopenvoronoi.geometry.EdgeType;
import org.rogach.jopenvoronoi.geometry.Face;
import org.rogach.jopenvoronoi.site.Site;
import org.rogach.jopenvoronoi.vertex.Vertex;

import java.util.*;

/**
 * 按点到整条折线骨架的最短欧氏距离计算连续 Voronoi。
 *
 * <p>每条折线在底层拆成点与线段站点；同一元素的所有站点面随后合并，因此相邻顶点之间的
 * 线段共同构成一个 Voronoi site。曲线边界只保留端点和 X/Z 极值点，极值点之间直接连线，
 * 不进行固定栅格或弧线加密。</p>
 */
public final class PolylineVoronoiDiagram {
  private static final double EPSILON = 1.0E-9;
  private static final double FAR_RADIUS = 4.0;
  private static final int EXTREMUM_ITERATIONS = 80;
  private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

  public static double distanceSquared(List<PlanarPoint> points, double x, double z) {
    if (points.size() == 1) return squaredDistance(points.getFirst(), x, z);
    double nearest = Double.POSITIVE_INFINITY;
    for (int index = 1; index < points.size(); index++) {
      nearest = Math.min(nearest, distanceSquaredToSegment(points.get(index - 1), points.get(index), x, z));
    }
    return nearest;
  }

  public static PlanarPoint midpoint(List<PlanarPoint> points) {
    if (points.size() == 1) return points.getFirst();
    double totalLength = 0.0;
    for (int index = 1; index < points.size(); index++) {
      totalLength += Math.sqrt(squaredDistance(points.get(index - 1), points.get(index).x(), points.get(index).z()));
    }
    double cursor = totalLength / 2.0;
    for (int index = 1; index < points.size(); index++) {
      PlanarPoint start = points.get(index - 1);
      PlanarPoint end = points.get(index);
      double length = Math.sqrt(squaredDistance(start, end.x(), end.z()));
      if (cursor <= length) {
        double ratio = length <= EPSILON ? 0.0 : cursor / length;
        return new PlanarPoint(
            start.x() + (end.x() - start.x()) * ratio,
            start.z() + (end.z() - start.z()) * ratio
        );
      }
      cursor -= length;
    }
    return points.getLast();
  }

  private static double distanceSquaredToSegment(
      PlanarPoint start,
      PlanarPoint end,
      double x,
      double z
  ) {
    double edgeX = end.x() - start.x();
    double edgeZ = end.z() - start.z();
    double lengthSquared = edgeX * edgeX + edgeZ * edgeZ;
    if (lengthSquared <= EPSILON) return squaredDistance(start, x, z);
    double position = Math.clamp(
        ((x - start.x()) * edgeX + (z - start.z()) * edgeZ) / lengthSquared,
        0.0,
        1.0
    );
    double deltaX = x - start.x() - edgeX * position;
    double deltaZ = z - start.z() - edgeZ * position;
    return deltaX * deltaX + deltaZ * deltaZ;
  }

  private static double squaredDistance(PlanarPoint point, double x, double z) {
    double deltaX = x - point.x();
    double deltaZ = z - point.z();
    return deltaX * deltaX + deltaZ * deltaZ;
  }

  private static <T> int nearest(List<PolylineVoronoiSite<T>> sites, double x, double z) {
    int nearest = 0;
    double nearestDistance = distanceSquared(sites.getFirst().points(), x, z);
    for (int index = 1; index < sites.size(); index++) {
      double distance = distanceSquared(sites.get(index).points(), x, z);
      if (distance < nearestDistance) {
        nearest = index;
        nearestDistance = distance;
      }
    }
    return nearest;
  }

  private static <T> T ownerOfSite(Site primitive, List<PolylineVoronoiSite<T>> sites) {
    if (primitive == null) return null;
    double x;
    double z;
    if (primitive.isPoint()) {
      x = primitive.position().x;
      z = primitive.position().y;
    } else if (primitive.isLine()) {
      x = (primitive.start().x + primitive.end().x) / 2.0;
      z = (primitive.start().y + primitive.end().y) / 2.0;
    } else {
      return null;
    }
    int owner = nearest(sites, x, z);
    return distanceSquared(sites.get(owner).points(), x, z) <= EPSILON * EPSILON
        ? sites.get(owner).element()
        : null;
  }

  private static Coordinate coordinate(org.rogach.jopenvoronoi.geometry.Point point) {
    return new Coordinate(point.x, point.y);
  }

  private static double axis(Edge edge, double parameter, boolean xAxis) {
    org.rogach.jopenvoronoi.geometry.Point point = edge.point(parameter);
    return xAxis ? point.x : point.y;
  }

  /**
   * 黄金分割只定位解析边的坐标极值；返回边界仍只输出这个极值点。
   */
  private static double extremum(Edge edge, double start, double end, boolean xAxis, boolean maximum) {
    double left = Math.min(start, end);
    double right = Math.max(start, end);
    double ratio = (Math.sqrt(5.0) - 1.0) / 2.0;
    double first = right - ratio * (right - left);
    double second = left + ratio * (right - left);
    double firstValue = axis(edge, first, xAxis) * (maximum ? -1.0 : 1.0);
    double secondValue = axis(edge, second, xAxis) * (maximum ? -1.0 : 1.0);
    for (int iteration = 0; iteration < EXTREMUM_ITERATIONS; iteration++) {
      if (firstValue <= secondValue) {
        right = second;
        second = first;
        secondValue = firstValue;
        first = right - ratio * (right - left);
        firstValue = axis(edge, first, xAxis) * (maximum ? -1.0 : 1.0);
      } else {
        left = first;
        first = second;
        firstValue = secondValue;
        second = left + ratio * (right - left);
        secondValue = axis(edge, second, xAxis) * (maximum ? -1.0 : 1.0);
      }
    }
    return (left + right) / 2.0;
  }

  private static List<Coordinate> edgeVertices(Edge edge) {
    double start = edge.source.dist();
    double end = edge.target.dist();
    if (!Double.isFinite(start) || !Double.isFinite(end) || Math.abs(start - end) <= EPSILON) {
      return List.of(coordinate(edge.source.position), coordinate(edge.target.position));
    }
    ArrayList<Double> parameters = new ArrayList<>();
    parameters.add(start);
    parameters.add(end);
    if (edge.type != EdgeType.LINE && edge.type != EdgeType.LINELINE && edge.type != EdgeType.PARA_LINELINE) {
      parameters.add(extremum(edge, start, end, true, false));
      parameters.add(extremum(edge, start, end, true, true));
      parameters.add(extremum(edge, start, end, false, false));
      parameters.add(extremum(edge, start, end, false, true));
    }
    parameters.sort(start <= end ? Comparator.naturalOrder() : Comparator.reverseOrder());
    ArrayList<Coordinate> result = new ArrayList<>();
    for (int index = 0; index < parameters.size(); index++) {
      Coordinate point;
      if (index == 0) {
        point = coordinate(edge.source.position);
      } else if (index == parameters.size() - 1) {
        point = coordinate(edge.target.position);
      } else {
        point = coordinate(edge.point(parameters.get(index)));
      }
      if (result.isEmpty() || result.getLast().distance(point) > EPSILON) result.add(point);
    }
    return List.copyOf(result);
  }

  private static List<Coordinate> sharedEdgeVertices(
      Edge edge,
      IdentityHashMap<Edge, List<Coordinate>> edgePaths
  ) {
    List<Coordinate> existing = edgePaths.get(edge);
    if (existing != null) return existing;
    List<Coordinate> path = edgeVertices(edge);
    edgePaths.put(edge, path);
    if (edge.twin != null) {
      ArrayList<Coordinate> reversed = new ArrayList<>(path);
      java.util.Collections.reverse(reversed);
      edgePaths.put(edge.twin, List.copyOf(reversed));
    }
    return path;
  }

  private static Geometry facePolygon(
      Face face,
      Polygon clippingPolygon,
      IdentityHashMap<Edge, List<Coordinate>> edgePaths
  ) {
    ArrayList<Coordinate> boundary = new ArrayList<>();
    for (Edge edge : face.getEdges()) {
      if (!edge.valid) continue;
      for (Coordinate point : sharedEdgeVertices(edge, edgePaths)) {
        if (boundary.isEmpty() || boundary.getLast().distance(point) > EPSILON) {
          boundary.add(point.copy());
        }
      }
    }
    if (boundary.size() < 3) return GEOMETRY_FACTORY.createPolygon();
    if (boundary.getFirst().distance(boundary.getLast()) > EPSILON) {
      boundary.add(boundary.getFirst().copy());
    } else {
      boundary.set(boundary.size() - 1, boundary.getFirst().copy());
    }
    Geometry polygon = GEOMETRY_FACTORY.createPolygon(boundary.toArray(Coordinate[]::new));
    if (!polygon.isValid()) polygon = polygon.buffer(0.0);
    return polygon.intersection(clippingPolygon);
  }

  private static void appendPolygons(List<Polygon> destination, Geometry geometry) {
    for (int index = 0; index < geometry.getNumGeometries(); index++) {
      Geometry part = geometry.getGeometryN(index);
      if (part instanceof Polygon polygon && polygon.getArea() > EPSILON) destination.add(polygon);
    }
  }

  private static Polygon rectanglePolygon(Bounds bounds) {
    return GEOMETRY_FACTORY.createPolygon(new Coordinate[]{
        new Coordinate(bounds.minX(), bounds.minZ()),
        new Coordinate(bounds.maxX(), bounds.minZ()),
        new Coordinate(bounds.maxX(), bounds.maxZ()),
        new Coordinate(bounds.minX(), bounds.maxZ()),
        new Coordinate(bounds.minX(), bounds.minZ())
    });
  }

  private static List<PlanarPoint> polygonBoundary(Polygon polygon, Bounds normalized, Bounds world) {
    return coordinatesBoundary(List.of(polygon.getExteriorRing().getCoordinates()), normalized, world);
  }

  private static List<PlanarPoint> coordinatesBoundary(
      List<Coordinate> coordinates,
      Bounds normalized,
      Bounds world
  ) {
    int size = coordinates.size();
    if (size > 1 && coordinates.getFirst().distance(coordinates.getLast()) <= EPSILON) size--;
    ArrayList<PlanarPoint> boundary = new ArrayList<>(size);
    for (int index = 0; index < size; index++) {
      Coordinate point = coordinates.get(index);
      boundary.add(new PlanarPoint(
          world.minX() + (point.x - normalized.minX()) / normalized.width() * world.width(),
          world.minZ() + (point.y - normalized.minZ()) / normalized.height() * world.height()
      ));
    }
    return simplify(boundary);
  }

  private static List<Coordinate> ringAt(List<Coordinate> ring, Coordinate touch) {
    ArrayList<Coordinate> open = new ArrayList<>(ring);
    if (open.size() > 1 && open.getFirst().distance(open.getLast()) <= EPSILON) open.removeLast();
    int touchIndex = -1;
    for (int index = 0; index < open.size(); index++) {
      if (open.get(index).distance(touch) <= EPSILON) {
        touchIndex = index;
        break;
      }
    }
    if (touchIndex < 0) {
      for (int index = 0; index < open.size(); index++) {
        Coordinate start = open.get(index);
        Coordinate end = open.get((index + 1) % open.size());
        if (org.locationtech.jts.algorithm.Distance.pointToSegment(touch, start, end) <= EPSILON) {
          touchIndex = index + 1;
          open.add(touchIndex, touch.copy());
          break;
        }
      }
    }
    if (touchIndex < 0) throw new IllegalStateException("零距离组件缺少共同边界点");
    ArrayList<Coordinate> result = new ArrayList<>(open.size() + 1);
    for (int offset = 0; offset < open.size(); offset++) {
      result.add(open.get((touchIndex + offset) % open.size()).copy());
    }
    result.add(result.getFirst().copy());
    return result;
  }

  /**
   * 将仅在边界点接触的多个面串成一个不增加面积的弱简单多边形环。
   */
  private static List<PlanarPoint> touchingBoundary(MultiPolygon polygons, Bounds normalized, Bounds world) {
    ArrayList<Polygon> remaining = new ArrayList<>();
    for (int index = 0; index < polygons.getNumGeometries(); index++) {
      remaining.add((Polygon) polygons.getGeometryN(index));
    }
    Geometry connected = remaining.removeFirst();
    ArrayList<Coordinate> boundary = new ArrayList<>(
        List.of(((Polygon) connected).getExteriorRing().getCoordinates())
    );
    while (!remaining.isEmpty()) {
      int nextIndex = -1;
      Coordinate touch = null;
      for (int index = 0; index < remaining.size(); index++) {
        Polygon candidate = remaining.get(index);
        if (connected.distance(candidate) <= EPSILON) {
          nextIndex = index;
          touch = DistanceOp.nearestPoints(connected, candidate)[0];
          break;
        }
      }
      if (nextIndex < 0 || touch == null) {
        throw new IllegalStateException("国家折线 Voronoi 的多边形组件之间存在真实间隔");
      }
      Polygon next = remaining.remove(nextIndex);
      List<Coordinate> connectedRing = ringAt(boundary, touch);
      List<Coordinate> nextRing = ringAt(List.of(next.getExteriorRing().getCoordinates()), touch);
      boundary = new ArrayList<>(connectedRing.size() + nextRing.size() - 1);
      boundary.addAll(connectedRing);
      boundary.addAll(nextRing.subList(1, nextRing.size()));
      connected = connected.union(next);
    }
    return coordinatesBoundary(boundary, normalized, world);
  }

  private static List<PlanarPoint> simplify(List<PlanarPoint> polygon) {
    ArrayList<PlanarPoint> result = new ArrayList<>();
    for (PlanarPoint point : polygon) {
      while (result.size() >= 2 && collinear(result.get(result.size() - 2), result.getLast(), point)) {
        result.removeLast();
      }
      result.add(point);
    }
    while (result.size() >= 3 && collinear(result.getLast(), result.getFirst(), result.get(1))) {
      result.removeFirst();
    }
    while (result.size() >= 3 && collinear(result.get(result.size() - 2), result.getLast(), result.getFirst())) {
      result.removeLast();
    }
    return List.copyOf(result);
  }

  private static boolean collinear(PlanarPoint first, PlanarPoint second, PlanarPoint third) {
    double cross = (second.x() - first.x()) * (third.z() - second.z())
        - (second.z() - first.z()) * (third.x() - second.x());
    double scale = Math.max(1.0, Math.hypot(second.x() - first.x(), second.z() - first.z())
        * Math.hypot(third.x() - second.x(), third.z() - second.z()));
    return Math.abs(cross) <= EPSILON * scale;
  }

  public <T> List<PolylineVoronoiCell<T>> calculate(
      List<PlanarPoint> rectangularBoundary,
      List<PolylineVoronoiSite<T>> sites
  ) {
    List<PlanarPoint> boundary = List.copyOf(Objects.requireNonNull(rectangularBoundary, "边界不能为空"));
    List<PolylineVoronoiSite<T>> declaredSites = List.copyOf(Objects.requireNonNull(sites, "站点不能为空"));
    if (declaredSites.isEmpty()) throw new IllegalArgumentException("折线 Voronoi 至少需要一个站点");
    Bounds worldBounds = Bounds.of(boundary);
    double scale = Math.max(worldBounds.width(), worldBounds.height());
    Bounds normalizedBounds = new Bounds(
        -worldBounds.width() / scale / 2.0,
        -worldBounds.height() / scale / 2.0,
        worldBounds.width() / scale / 2.0,
        worldBounds.height() / scale / 2.0
    );
    List<PolylineVoronoiSite<T>> normalizedSites = declaredSites.stream()
        .map(site -> new PolylineVoronoiSite<>(site.element(), site.points().stream()
            .map(point -> new PlanarPoint(
                normalizedBounds.minX() + (point.x() - worldBounds.minX()) / worldBounds.width()
                    * normalizedBounds.width(),
                normalizedBounds.minZ() + (point.z() - worldBounds.minZ()) / worldBounds.height()
                    * normalizedBounds.height()
            ))
            .toList()))
        .toList();

    org.rogach.jopenvoronoi.VoronoiDiagram diagram =
        new org.rogach.jopenvoronoi.VoronoiDiagram(FAR_RADIUS);
    ArrayList<org.rogach.jopenvoronoi.geometry.Point> primitivePoints = new ArrayList<>();
    for (PolylineVoronoiSite<T> site : normalizedSites) {
      for (PlanarPoint point : site.points()) {
        primitivePoints.add(new org.rogach.jopenvoronoi.geometry.Point(point.x(), point.z()));
      }
    }
    List<Vertex> vertices = diagram.insertPointSites(primitivePoints);
    int offset = 0;
    for (PolylineVoronoiSite<T> site : normalizedSites) {
      for (int index = 1; index < site.points().size(); index++) {
        diagram.insertLineSite(vertices.get(offset + index - 1), vertices.get(offset + index));
      }
      offset += site.points().size();
    }

    Polygon clippingPolygon = rectanglePolygon(normalizedBounds);
    Map<T, List<Polygon>> ownedPolygons = new LinkedHashMap<>();
    for (PolylineVoronoiSite<T> site : normalizedSites) ownedPolygons.put(site.element(), new ArrayList<>());
    IdentityHashMap<Edge, List<Coordinate>> edgePaths = new IdentityHashMap<>();
    for (Face face : diagram.getFaces()) {
      T owner = ownerOfSite(face.getSite(), normalizedSites);
      if (owner == null) continue;
      appendPolygons(ownedPolygons.get(owner), facePolygon(face, clippingPolygon, edgePaths));
    }

    ArrayList<PolylineVoronoiCell<T>> cells = new ArrayList<>(declaredSites.size());
    for (int index = 0; index < declaredSites.size(); index++) {
      PolylineVoronoiSite<T> declaredSite = declaredSites.get(index);
      List<Polygon> parts = ownedPolygons.get(declaredSite.element());
      if (parts == null || parts.isEmpty()) {
        throw new IllegalStateException("折线 Voronoi 单元边界退化：" + index);
      }
      Geometry merged = UnaryUnionOp.union(parts);
      List<PlanarPoint> nationBoundary;
      if (merged instanceof Polygon polygon) {
        nationBoundary = polygonBoundary(polygon, normalizedBounds, worldBounds);
      } else if (merged instanceof MultiPolygon polygons) {
        nationBoundary = touchingBoundary(polygons, normalizedBounds, worldBounds);
      } else {
        throw new IllegalStateException("国家折线 Voronoi 边界类型无效：" + index + "/" + merged.getGeometryType());
      }
      if (nationBoundary.size() < 3) throw new IllegalStateException("折线 Voronoi 单元边界退化：" + index);
      cells.add(new PolylineVoronoiCell<>(
          new VoronoiSite<>(declaredSite.element(), midpoint(declaredSite.points())),
          nationBoundary
      ));
    }
    return List.copyOf(cells);
  }

  private record Bounds(double minX, double minZ, double maxX, double maxZ) {
    private static Bounds of(List<PlanarPoint> boundary) {
      if (boundary.size() != 4) throw new IllegalArgumentException("折线 Voronoi 目前要求矩形边界");
      double minX = boundary.stream().mapToDouble(PlanarPoint::x).min().orElseThrow();
      double minZ = boundary.stream().mapToDouble(PlanarPoint::z).min().orElseThrow();
      double maxX = boundary.stream().mapToDouble(PlanarPoint::x).max().orElseThrow();
      double maxZ = boundary.stream().mapToDouble(PlanarPoint::z).max().orElseThrow();
      return new Bounds(minX, minZ, maxX, maxZ);
    }

    private double width() {
      return maxX - minX;
    }

    private double height() {
      return maxZ - minZ;
    }
  }
}
