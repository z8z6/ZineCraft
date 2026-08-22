package com.cxxcxx.zinecraft.core.worldgen.density;

import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
import com.cxxcxx.zinecraft.api.world.layout.PlanarRectangle;
import com.cxxcxx.zinecraft.core.nation.TerraLayoutResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 对数据生成阶段冻结的泰拉布局建立轻量运行时空间索引。 */
final class TerraTerrainLookup {
  private static final int PLOT_BUCKET_SIZE = 256;
  private static final double MAX_PLOT_TRANSITION_WIDTH = 512.0;

  private TerraTerrainLookup() {
  }

  static NationBlend nationBlendAt(int blockX, int blockY, int blockZ, double blendWidth) {
    return Holder.INDEX.nationBlendAt(blockX, blockY, blockZ, blendWidth);
  }

  static RegionTerrainBlend regionTerrainAt(
      int blockX,
      int blockY,
      int blockZ,
      double plotTransitionWidth
  ) {
    return Holder.INDEX.regionTerrainAt(blockX, blockY, blockZ, plotTransitionWidth);
  }

  enum BaseTerrain {
    NORMAL,
    HILLS
  }

  enum PlotKind {
    NONE,
    SURFACE,
    UNDERGROUND
  }

  record RegionTerrainBlend(BaseTerrain baseTerrain, PlotKind plotKind, double flatWeight) {
    RegionTerrainBlend {
      if (flatWeight < 0.0 || flatWeight > 1.0) {
        throw new IllegalArgumentException("移动地块平地混合权重必须位于 [0, 1]");
      }
      if (plotKind == PlotKind.NONE && flatWeight != 0.0) {
        throw new IllegalArgumentException("没有移动地块时平地混合权重必须为 0");
      }
    }
  }

  record NationBlend(String primary, String secondary, double secondaryWeight) {
    NationBlend {
      if (secondaryWeight < 0.0 || secondaryWeight > 0.5) {
        throw new IllegalArgumentException("相邻国家混合权重必须位于 [0, 0.5]");
      }
    }
  }

  private static final class Holder {
    private static final Index INDEX = new Index();
  }

  private static final class Index {
    private final List<NationArea> surfaceNations = new ArrayList<>();
    private final List<NationArea> undergroundNations = new ArrayList<>();
    private final Map<Long, List<RegionArea>> regionBuckets = new HashMap<>();
    private final Map<Long, List<PlotArea>> plotBuckets = new HashMap<>();

    private Index() {
      for (var nation : TerraLayoutResource.load().nations()) {
        NationArea area = NationArea.create(
            nation.nation().id(),
            nation.nation().isUnderground(),
            nation.boundary(),
            nation.neighboringNationIds()
        );
        (area.underground() ? undergroundNations : surfaceNations).add(area);
        for (var city : nation.cities()) {
          for (var region : city.regions()) {
            addRegion(region.boundary(), region.mobilePlotBounds(), area.underground());
          }
        }
      }
    }

    private NationBlend nationBlendAt(int blockX, int blockY, int blockZ, double blendWidth) {
      List<NationArea> layer = surfaceNations;
      if (blockY < 0) {
        NationArea underground = findNation(undergroundNations, blockX, blockZ);
        if (underground != null) layer = undergroundNations;
      }
      NationArea primary = findNation(layer, blockX, blockZ);
      if (primary == null) return new NationBlend(null, null, 0.0);
      if (blendWidth <= 0.0) return new NationBlend(primary.id(), null, 0.0);

      NationArea secondary = null;
      double nearestDistance = Double.POSITIVE_INFINITY;
      for (NationArea candidate : layer) {
        if (candidate == primary || !primary.neighboringNationIds().contains(candidate.id())) continue;
        double distance = candidate.distanceToArea(blockX, blockZ);
        if (distance < nearestDistance) {
          secondary = candidate;
          nearestDistance = distance;
        }
      }
      if (secondary == null || nearestDistance >= blendWidth) {
        return new NationBlend(primary.id(), null, 0.0);
      }
      double position = nearestDistance / blendWidth;
      double smoothPosition = position * position * (3.0 - 2.0 * position);
      return new NationBlend(primary.id(), secondary.id(), 0.5 * (1.0 - smoothPosition));
    }

    private RegionTerrainBlend regionTerrainAt(
        int blockX,
        int blockY,
        int blockZ,
        double plotTransitionWidth
    ) {
      if (!Double.isFinite(plotTransitionWidth)
          || plotTransitionWidth < 0.0
          || plotTransitionWidth > MAX_PLOT_TRANSITION_WIDTH) {
        throw new IllegalArgumentException(
            "移动地块过渡宽度必须位于 [0, " + MAX_PLOT_TRANSITION_WIDTH + "]"
        );
      }
      long bucket = bucketKey(
          Math.floorDiv(blockX, PLOT_BUCKET_SIZE), Math.floorDiv(blockZ, PLOT_BUCKET_SIZE)
      );
      boolean undergroundLayer = blockY < 0;
      PlanarPoint point = new PlanarPoint(blockX, blockZ);

      BaseTerrain baseTerrain = BaseTerrain.NORMAL;
      List<RegionArea> regionCandidates = regionBuckets.get(bucket);
      if (regionCandidates != null) {
        for (RegionArea candidate : regionCandidates) {
          if (candidate.underground() == undergroundLayer && candidate.contains(point)) {
            baseTerrain = BaseTerrain.HILLS;
            break;
          }
        }
      }

      PlotArea nearestPlot = null;
      double nearestDistance = Double.POSITIVE_INFINITY;
      List<PlotArea> plotCandidates = plotBuckets.get(bucket);
      if (plotCandidates != null) {
        for (PlotArea candidate : plotCandidates) {
          if (candidate.underground() != undergroundLayer) continue;
          double distance = candidate.distanceTo(point);
          if (distance < nearestDistance) {
            nearestPlot = candidate;
            nearestDistance = distance;
          }
        }
      }
      if (nearestPlot == null || nearestDistance >= plotTransitionWidth && nearestDistance > 0.0) {
        return new RegionTerrainBlend(baseTerrain, PlotKind.NONE, 0.0);
      }
      double flatWeight;
      if (nearestDistance == 0.0 || plotTransitionWidth == 0.0) {
        flatWeight = 1.0;
      } else {
        double position = nearestDistance / plotTransitionWidth;
        flatWeight = 1.0 - position * position * (3.0 - 2.0 * position);
      }
      return new RegionTerrainBlend(
          baseTerrain,
          undergroundLayer ? PlotKind.UNDERGROUND : PlotKind.SURFACE,
          flatWeight
      );
    }

    private void addRegion(List<PlanarPoint> boundary, PlanarRectangle mobilePlotBounds, boolean underground) {
      double minX = boundary.stream().mapToDouble(PlanarPoint::x).min().orElseThrow();
      double maxX = boundary.stream().mapToDouble(PlanarPoint::x).max().orElseThrow();
      double minZ = boundary.stream().mapToDouble(PlanarPoint::z).min().orElseThrow();
      double maxZ = boundary.stream().mapToDouble(PlanarPoint::z).max().orElseThrow();
      int minBucketX = Math.floorDiv((int) Math.floor(minX), PLOT_BUCKET_SIZE);
      int maxBucketX = Math.floorDiv((int) Math.floor(maxX), PLOT_BUCKET_SIZE);
      int minBucketZ = Math.floorDiv((int) Math.floor(minZ), PLOT_BUCKET_SIZE);
      int maxBucketZ = Math.floorDiv((int) Math.floor(maxZ), PLOT_BUCKET_SIZE);
      RegionArea area = new RegionArea(List.copyOf(boundary), mobilePlotBounds, underground);
      for (int bucketX = minBucketX; bucketX <= maxBucketX; bucketX++) {
        for (int bucketZ = minBucketZ; bucketZ <= maxBucketZ; bucketZ++) {
          regionBuckets.computeIfAbsent(bucketKey(bucketX, bucketZ), ignored -> new ArrayList<>()).add(area);
        }
      }

      double plotMinX = mobilePlotBounds.center().x() - mobilePlotBounds.halfSizeX()
          - MAX_PLOT_TRANSITION_WIDTH;
      double plotMaxX = mobilePlotBounds.center().x() + mobilePlotBounds.halfSizeX()
          + MAX_PLOT_TRANSITION_WIDTH;
      double plotMinZ = mobilePlotBounds.center().z() - mobilePlotBounds.halfSizeZ()
          - MAX_PLOT_TRANSITION_WIDTH;
      double plotMaxZ = mobilePlotBounds.center().z() + mobilePlotBounds.halfSizeZ()
          + MAX_PLOT_TRANSITION_WIDTH;
      int plotMinBucketX = Math.floorDiv((int) Math.floor(plotMinX), PLOT_BUCKET_SIZE);
      int plotMaxBucketX = Math.floorDiv((int) Math.floor(plotMaxX), PLOT_BUCKET_SIZE);
      int plotMinBucketZ = Math.floorDiv((int) Math.floor(plotMinZ), PLOT_BUCKET_SIZE);
      int plotMaxBucketZ = Math.floorDiv((int) Math.floor(plotMaxZ), PLOT_BUCKET_SIZE);
      PlotArea plot = new PlotArea(mobilePlotBounds, underground);
      for (int bucketX = plotMinBucketX; bucketX <= plotMaxBucketX; bucketX++) {
        for (int bucketZ = plotMinBucketZ; bucketZ <= plotMaxBucketZ; bucketZ++) {
          plotBuckets.computeIfAbsent(bucketKey(bucketX, bucketZ), ignored -> new ArrayList<>()).add(plot);
        }
      }
    }
  }

  private static NationArea findNation(List<NationArea> nations, double x, double z) {
    for (NationArea nation : nations) {
      if (nation.contains(x, z)) return nation;
    }
    return null;
  }

  private static double distanceToPolygon(List<PlanarPoint> polygon, double x, double z) {
    if (contains(polygon, x, z)) return 0.0;
    double nearestSquared = Double.POSITIVE_INFINITY;
    for (int current = 0, previous = polygon.size() - 1; current < polygon.size(); previous = current++) {
      nearestSquared = Math.min(nearestSquared, distanceSquaredToSegment(
          polygon.get(previous), polygon.get(current), x, z
      ));
    }
    return Math.sqrt(nearestSquared);
  }

  private static double distanceSquaredToSegment(PlanarPoint start, PlanarPoint end, double x, double z) {
    double edgeX = end.x() - start.x();
    double edgeZ = end.z() - start.z();
    double lengthSquared = edgeX * edgeX + edgeZ * edgeZ;
    if (lengthSquared == 0.0) return square(x - start.x()) + square(z - start.z());
    double position = Math.clamp(
        ((x - start.x()) * edgeX + (z - start.z()) * edgeZ) / lengthSquared,
        0.0,
        1.0
    );
    return square(x - (start.x() + edgeX * position))
        + square(z - (start.z() + edgeZ * position));
  }

  private static double square(double value) {
    return value * value;
  }

  private static boolean contains(List<PlanarPoint> polygon, double x, double z) {
    boolean inside = false;
    for (int current = 0, previous = polygon.size() - 1; current < polygon.size(); previous = current++) {
      PlanarPoint first = polygon.get(previous);
      PlanarPoint second = polygon.get(current);
      if (onSegment(first, second, x, z)) return true;
      if ((first.z() > z) != (second.z() > z)
          && x < (second.x() - first.x()) * (z - first.z()) / (second.z() - first.z()) + first.x()) {
        inside = !inside;
      }
    }
    return inside;
  }

  private static boolean onSegment(PlanarPoint first, PlanarPoint second, double x, double z) {
    double cross = (x - first.x()) * (second.z() - first.z())
        - (z - first.z()) * (second.x() - first.x());
    double epsilon = 1.0E-7 * Math.max(1.0, Math.max(
        Math.abs(second.x() - first.x()), Math.abs(second.z() - first.z())
    ));
    if (Math.abs(cross) > epsilon) return false;
    return x >= Math.min(first.x(), second.x()) - epsilon
        && x <= Math.max(first.x(), second.x()) + epsilon
        && z >= Math.min(first.z(), second.z()) - epsilon
        && z <= Math.max(first.z(), second.z()) + epsilon;
  }

  private static long bucketKey(int bucketX, int bucketZ) {
    return ((long) bucketX << 32) ^ (bucketZ & 0xffffffffL);
  }

  private record RegionArea(
      List<PlanarPoint> boundary,
      PlanarRectangle mobilePlotBounds,
      boolean underground
  ) {
    private boolean contains(PlanarPoint point) {
      return TerraTerrainLookup.contains(boundary, point.x(), point.z());
    }
  }

  private record PlotArea(PlanarRectangle bounds, boolean underground) {
    private double distanceTo(PlanarPoint point) {
      double distanceX = Math.max(
          Math.abs(point.x() - bounds.center().x()) - bounds.halfSizeX(),
          0.0
      );
      double distanceZ = Math.max(
          Math.abs(point.z() - bounds.center().z()) - bounds.halfSizeZ(),
          0.0
      );
      return Math.hypot(distanceX, distanceZ);
    }
  }

  private record NationArea(
      String id,
      boolean underground,
      List<PlanarPoint> boundary,
      Set<String> neighboringNationIds,
      double minX,
      double maxX,
      double minZ,
      double maxZ
  ) {
    private static NationArea create(
        String id,
        boolean underground,
        List<PlanarPoint> boundary,
        List<String> neighboringNationIds
    ) {
      return new NationArea(
          id, underground, boundary, Set.copyOf(neighboringNationIds),
          boundary.stream().mapToDouble(PlanarPoint::x).min().orElseThrow(),
          boundary.stream().mapToDouble(PlanarPoint::x).max().orElseThrow(),
          boundary.stream().mapToDouble(PlanarPoint::z).min().orElseThrow(),
          boundary.stream().mapToDouble(PlanarPoint::z).max().orElseThrow()
      );
    }

    private boolean contains(double x, double z) {
      return x >= minX && x <= maxX && z >= minZ && z <= maxZ
          && TerraTerrainLookup.contains(boundary, x, z);
    }

    private double distanceToArea(double x, double z) {
      return TerraTerrainLookup.distanceToPolygon(boundary, x, z);
    }
  }
}
