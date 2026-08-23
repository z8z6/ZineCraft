package com.cxxcxx.zinecraft.core.worldgen.density;

import com.cxxcxx.zinecraft.api.world.city.CityTerrainProfile;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.nation.TerraLayoutResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 对数据生成阶段冻结的泰拉布局建立 256x256 block 运行时空间索引。 */
public final class TerraTerrainLookup {
  static final int TERRAIN_BUCKET_SIZE = 256;

  private TerraTerrainLookup() {
  }

  /** 在进入世界前构建地形查询索引，避免首个泰拉区块生成时阻塞服务器线程。 */
  public static void preload() {
    long startedAt = System.nanoTime();
    Index index = Holder.INDEX;
    long elapsedMillis = (System.nanoTime() - startedAt) / 1_000_000L;
    Zinecraft.LOGGER.info(
        "已预加载泰拉地形索引：城市桶={}，Region 矩形桶={}，耗时={} ms",
        index.cityBuckets.size(), index.regionBuckets.size(), elapsedMillis
    );
  }

  static NationBlend nationBlendAt(int blockX, int blockY, int blockZ, double blendWidth) {
    return Holder.INDEX.nationBlendAt(blockX, blockY, blockZ, blendWidth);
  }

  static CityTerrainSample cityTerrainAt(
      int blockX,
      int blockY,
      int blockZ,
      int proximityNeighborRank,
      double proximitySearchRadius
  ) {
    return Holder.INDEX.cityTerrainAt(
        blockX, blockY, blockZ, proximityNeighborRank, proximitySearchRadius
    );
  }

  record CityTerrainSample(
      boolean insideCity,
      boolean insideRegion,
      double distanceToRegion,
      double neighborRegionDistance,
      CityTerrainProfile profile,
      double regionWeight,
      double cityWeight
  ) {
    static final CityTerrainSample OUTSIDE = new CityTerrainSample(
        false, false, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, null, 0.0, 0.0
    );

    CityTerrainSample {
      if (regionWeight < 0.0 || regionWeight > 1.0) {
        throw new IllegalArgumentException("Region 地表权重必须位于 [0, 1]");
      }
      if (cityWeight < 0.0 || cityWeight > 1.0) {
        throw new IllegalArgumentException("城市自然地形权重必须位于 [0, 1]");
      }
      if (profile == null && (insideRegion || regionWeight != 0.0 || cityWeight != 0.0)) {
        throw new IllegalArgumentException("没有城市地形 Profile 时不能产生城市地形影响");
      }
    }

    boolean hasTerrainInfluence() {
      return profile != null && (regionWeight > 0.0 || cityWeight > 0.0);
    }
  }

  record NationBlend(String primary, String secondary, double secondaryWeight) {
    NationBlend {
      if (secondaryWeight < 0.0 || secondaryWeight > 0.5) {
        throw new IllegalArgumentException("相邻国家混合权重必须位于 [0, 0.5]");
      }
    }
  }

  static double regionWeight(double distance, CityTerrainProfile profile) {
    if (distance <= profile.flatShoulder()) return 1.0;
    if (distance >= profile.influenceRadius()) return 0.0;
    double t = Math.clamp(
        (distance - profile.flatShoulder()) / profile.transitionWidth(),
        0.0,
        1.0
    );
    double smoother = t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
    return 1.0 - smoother;
  }

  private static final class Holder {
    private static final Index INDEX = new Index();
  }

  private static final class Index {
    private final List<NationArea> surfaceNations = new ArrayList<>();
    private final List<NationArea> undergroundNations = new ArrayList<>();
    private final Map<Long, List<CityArea>> cityBuckets = new HashMap<>();
    private final Map<Long, List<RectRegionTerrainPrimitive>> regionBuckets = new HashMap<>();
    private final ThreadLocal<Map<QueryKey, CityTerrainSample>> queryCache = ThreadLocal.withInitial(
        () -> new LinkedHashMap<>(256, 0.75F, true) {
          @Override
          protected boolean removeEldestEntry(Map.Entry<QueryKey, CityTerrainSample> eldest) {
            return size() > 512;
          }
        }
    );
    private int maxInfluenceRadius;

    private Index() {
      for (var nation : TerraLayoutResource.load().nations()) {
        boolean underground = nation.nation().isUnderground();
        NationArea area = NationArea.create(
            nation.nation().id(), underground, nation.boundary(), nation.neighboringNationIds()
        );
        (underground ? undergroundNations : surfaceNations).add(area);
        for (var city : nation.cities()) {
          CityTerrainProfile profile = city.terrainProfile();
          maxInfluenceRadius = Math.max(maxInfluenceRadius, profile.influenceRadius());
          addPolygon(
              cityBuckets,
              new CityArea(city.boundary(), underground, profile),
              city.boundary()
          );
          for (var region : city.regions()) {
            addRegion(region.mobilePlotBounds().center().x() - region.mobilePlotBounds().halfSizeX(),
                region.mobilePlotBounds().center().z() - region.mobilePlotBounds().halfSizeZ(),
                region.mobilePlotBounds().center().x() + region.mobilePlotBounds().halfSizeX(),
                region.mobilePlotBounds().center().z() + region.mobilePlotBounds().halfSizeZ(),
                underground, profile);
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

    private CityTerrainSample cityTerrainAt(
        int blockX,
        int blockY,
        int blockZ,
        int proximityNeighborRank,
        double proximitySearchRadius
    ) {
      if (proximityNeighborRank <= 0 || proximitySearchRadius <= 0.0) {
        throw new IllegalArgumentException("Region 邻近查询参数必须为正数");
      }
      boolean underground = blockY < 0;
      QueryKey key = new QueryKey(
          blockX, blockZ, underground, proximityNeighborRank,
          Double.doubleToLongBits(proximitySearchRadius)
      );
      Map<QueryKey, CityTerrainSample> cache = queryCache.get();
      CityTerrainSample cached = cache.get(key);
      if (cached != null) return cached;
      CityTerrainSample computed = computeCityTerrainAt(
          blockX, blockZ, underground, proximityNeighborRank, proximitySearchRadius
      );
      cache.put(key, computed);
      return computed;
    }

    private CityTerrainSample computeCityTerrainAt(
        int blockX,
        int blockZ,
        boolean underground,
        int proximityNeighborRank,
        double proximitySearchRadius
    ) {
      long currentBucket = bucketAt(blockX, blockZ);
      CityArea city = cityAt(cityBuckets.get(currentBucket), blockX, blockZ, underground);
      double cityWeight = city == null ? 0.0 : cityNaturalWeight(city, blockX, blockZ);

      RectRegionTerrainPrimitive nearest = null;
      double[] nearestDistances = new double[proximityNeighborRank];
      java.util.Arrays.fill(nearestDistances, Double.POSITIVE_INFINITY);
      double searchRadius = Math.max(maxInfluenceRadius, proximitySearchRadius);
      int searchBlocks = (int) Math.ceil(searchRadius);
      int minBucketX = Math.floorDiv(blockX - searchBlocks, TERRAIN_BUCKET_SIZE);
      int maxBucketX = Math.floorDiv(blockX + searchBlocks, TERRAIN_BUCKET_SIZE);
      int minBucketZ = Math.floorDiv(blockZ - searchBlocks, TERRAIN_BUCKET_SIZE);
      int maxBucketZ = Math.floorDiv(blockZ + searchBlocks, TERRAIN_BUCKET_SIZE);
      Set<RectRegionTerrainPrimitive> visited = new HashSet<>();
      for (int bucketX = minBucketX; bucketX <= maxBucketX; bucketX++) {
        for (int bucketZ = minBucketZ; bucketZ <= maxBucketZ; bucketZ++) {
          List<RectRegionTerrainPrimitive> candidates = regionBuckets.get(bucketKey(bucketX, bucketZ));
          if (candidates == null) continue;
          for (RectRegionTerrainPrimitive candidate : candidates) {
            if (candidate.underground() != underground || !visited.add(candidate)) continue;
            double distance = candidate.distanceTo(blockX, blockZ);
            if (distance > searchRadius) continue;
            insertNearest(nearestDistances, distance);
            if (nearest == null || distance < nearest.distanceTo(blockX, blockZ)) {
              nearest = candidate;
            }
          }
        }
      }
      double nearestDistance = nearestDistances[0];
      double neighborDistance = nearestDistances[proximityNeighborRank - 1];
      boolean insideRegion = nearestDistance == 0.0;
      CityTerrainProfile profile = nearest != null
          && nearestDistance <= nearest.profile().influenceRadius()
          ? nearest.profile()
          : city == null ? null : city.profile();
      double regionWeight = profile == null
          ? 0.0
          : regionWeight(nearestDistance, profile);
      if (profile == null) {
        return CityTerrainSample.OUTSIDE;
      }
      return new CityTerrainSample(
          city != null,
          insideRegion,
          nearestDistance,
          neighborDistance,
          profile,
          regionWeight,
          cityWeight
      );
    }

    private record QueryKey(
        int blockX,
        int blockZ,
        boolean underground,
        int proximityNeighborRank,
        long proximitySearchRadiusBits
    ) {
    }

    private static void insertNearest(double[] distances, double distance) {
      for (int index = 0; index < distances.length; index++) {
        if (distance >= distances[index]) continue;
        System.arraycopy(distances, index, distances, index + 1, distances.length - index - 1);
        distances[index] = distance;
        return;
      }
    }

    private void addRegion(
        double minX, double minZ, double maxX, double maxZ,
        boolean underground, CityTerrainProfile profile
    ) {
      RectRegionTerrainPrimitive area = new RectRegionTerrainPrimitive(
          minX, minZ, maxX, maxZ, underground, profile
      );
      int minBucketX = Math.floorDiv((int) Math.floor(minX), TERRAIN_BUCKET_SIZE);
      int maxBucketX = Math.floorDiv((int) Math.ceil(maxX) - 1, TERRAIN_BUCKET_SIZE);
      int minBucketZ = Math.floorDiv((int) Math.floor(minZ), TERRAIN_BUCKET_SIZE);
      int maxBucketZ = Math.floorDiv((int) Math.ceil(maxZ) - 1, TERRAIN_BUCKET_SIZE);
      for (int bucketX = minBucketX; bucketX <= maxBucketX; bucketX++) {
        for (int bucketZ = minBucketZ; bucketZ <= maxBucketZ; bucketZ++) {
          regionBuckets.computeIfAbsent(bucketKey(bucketX, bucketZ), ignored -> new ArrayList<>()).add(area);
        }
      }
    }

    private static <T extends PolygonArea> void addPolygon(
        Map<Long, List<T>> buckets, T area, List<PlanarPoint> boundary
    ) {
      int minBucketX = Math.floorDiv(
          (int) Math.floor(boundary.stream().mapToDouble(PlanarPoint::x).min().orElseThrow()),
          TERRAIN_BUCKET_SIZE
      );
      int maxBucketX = Math.floorDiv(
          (int) Math.floor(boundary.stream().mapToDouble(PlanarPoint::x).max().orElseThrow()),
          TERRAIN_BUCKET_SIZE
      );
      int minBucketZ = Math.floorDiv(
          (int) Math.floor(boundary.stream().mapToDouble(PlanarPoint::z).min().orElseThrow()),
          TERRAIN_BUCKET_SIZE
      );
      int maxBucketZ = Math.floorDiv(
          (int) Math.floor(boundary.stream().mapToDouble(PlanarPoint::z).max().orElseThrow()),
          TERRAIN_BUCKET_SIZE
      );
      for (int bucketX = minBucketX; bucketX <= maxBucketX; bucketX++) {
        for (int bucketZ = minBucketZ; bucketZ <= maxBucketZ; bucketZ++) {
          buckets.computeIfAbsent(bucketKey(bucketX, bucketZ), ignored -> new ArrayList<>()).add(area);
        }
      }
    }
  }

  private interface PolygonArea {
    List<PlanarPoint> boundary();

    boolean underground();
  }

  private record CityArea(
      List<PlanarPoint> boundary,
      boolean underground,
      CityTerrainProfile profile
  ) implements PolygonArea {
  }

  private record RectRegionTerrainPrimitive(
      double minX,
      double minZ,
      double maxX,
      double maxZ,
      boolean underground,
      CityTerrainProfile profile
  ) {
    private RectRegionTerrainPrimitive {
      if (!(minX < maxX) || !(minZ < maxZ)) {
        throw new IllegalArgumentException("城市 Region 矩形必须具有正面积");
      }
    }

    public double distanceTo(double x, double z) {
      double distanceX = Math.max(Math.max(minX - x, x - maxX), 0.0);
      double distanceZ = Math.max(Math.max(minZ - z, z - maxZ), 0.0);
      return Math.hypot(distanceX, distanceZ);
    }
  }

  private static CityArea cityAt(
      List<CityArea> candidates, double x, double z, boolean underground
  ) {
    if (candidates == null) return null;
    for (CityArea candidate : candidates) {
      if (candidate.underground() == underground && contains(candidate.boundary(), x, z)) {
        return candidate;
      }
    }
    return null;
  }

  private static double cityNaturalWeight(CityArea city, double x, double z) {
    double boundaryDistance = distanceToBoundary(city.boundary(), x, z);
    return CityTerrainMath.smootherstep(
        boundaryDistance / city.profile().transitionWidth()
    );
  }

  private static NationArea findNation(List<NationArea> nations, double x, double z) {
    for (NationArea nation : nations) if (nation.contains(x, z)) return nation;
    return null;
  }

  private static double distanceToPolygon(List<PlanarPoint> polygon, double x, double z) {
    if (contains(polygon, x, z)) return 0.0;
    return distanceToBoundary(polygon, x, z);
  }

  private static double distanceToBoundary(List<PlanarPoint> polygon, double x, double z) {
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
        ((x - start.x()) * edgeX + (z - start.z()) * edgeZ) / lengthSquared, 0.0, 1.0
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

  private static long bucketAt(int blockX, int blockZ) {
    return bucketKey(
        Math.floorDiv(blockX, TERRAIN_BUCKET_SIZE), Math.floorDiv(blockZ, TERRAIN_BUCKET_SIZE)
    );
  }

  private static long bucketKey(int bucketX, int bucketZ) {
    return ((long) bucketX << 32) ^ (bucketZ & 0xffffffffL);
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
        String id, boolean underground, List<PlanarPoint> boundary, List<String> neighboringNationIds
    ) {
      return new NationArea(
          id, underground, List.copyOf(boundary), Set.copyOf(neighboringNationIds),
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
