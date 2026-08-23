package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.city.ChunkRectangle;
import com.cxxcxx.zinecraft.api.world.city.CityRegionConnection;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout.*;
import net.minecraft.core.Direction;

import java.util.*;

/** 按 docs/road.md 执行道路优先的 Region 内部 Chunk 规划。 */
public final class RegionLayoutGenerator {
  private static final List<String> DEBUG_STAGES = List.of(
      "boundary_and_entrances", "local_center", "mandatory_primary_roads",
      "layout_candidate_roads", "final_road_graph", "rasterized_roads",
      "urban_blocks", "building_parcels", "building_placements", "complete_region"
  );

  public RegionLayout generate(
      ChunkRectangle region,
      TerraCityRegionBuilder type,
      List<CityRegionConnection> connections,
      long seed
  ) {
    Objects.requireNonNull(region, "Region Chunk 范围不能为空");
    Objects.requireNonNull(type, "Region 类型不能为空");
    Objects.requireNonNull(connections, "Region connection 不能为空");
    Random random = new Random(seed);
    List<RegionEntrance> entrances = entrances(region, connections);
    ChunkPoint center = localCenter(region, type, random);
    RoadBuilder roads = new RoadBuilder(region, type.roadConfig());
    addMandatoryRoads(roads, center, entrances, random);
    switch (type.regionLayoutType()) {
      case GRID -> addGridRoads(roads, center, random, false);
      case CONCENTRIC -> addConcentricRoads(roads, center, random);
      case RADIAL_GRID -> addRadialGridRoads(roads, center, entrances, random);
      case SPINE, CAMPUS, HYBRID -> throw new IllegalStateException(
          "尚未实现的 Region 内部布局进入生成器：" + type.regionLayoutType()
      );
    }
    RoadGraph graph = roads.build();
    RoadRaster raster = new RoadRaster(region, graph);
    BlockExtraction extraction = extractBlocks(region, raster);
    PartitionResult partition = partition(extraction.components(), raster, region, type, random);
    double roadCoverage = raster.roadCount() / (double) region.areaChunks();
    double buildingCoverage = 0.0;
    RegionLayout layout = new RegionLayout(
        type.regionLayoutType(), center, entrances, graph, List.of(
            new MobileLayerPlan(MobileLayer.POWER, "mobile_plot_power_layer", region),
            new MobileLayerPlan(MobileLayer.SUPPORT, "mobile_plot_support_layer", region),
            new MobileLayerPlan(MobileLayer.LIFE, "mobile_plot_life_layer", region)
        ), extraction.blocks(),
        partition.parcels(), partition.openSpaces(), roadCoverage, buildingCoverage, DEBUG_STAGES
    );
    RegionLayoutValidator.validate(region, layout);
    return layout;
  }

  private static List<RegionEntrance> entrances(
      ChunkRectangle region,
      List<CityRegionConnection> connections
  ) {
    ArrayList<RegionEntrance> result = new ArrayList<>();
    for (CityRegionConnection connection : connections) {
      int targetX = (int) Math.floor(connection.point().x() / 16.0);
      int targetZ = (int) Math.floor(connection.point().z() / 16.0);
      int west = Math.abs(targetX - region.minChunkX());
      int east = Math.abs(targetX - (region.maxChunkXExclusive() - 1));
      int north = Math.abs(targetZ - region.minChunkZ());
      int south = Math.abs(targetZ - (region.maxChunkZExclusive() - 1));
      int nearest = Math.min(Math.min(west, east), Math.min(north, south));
      Direction side;
      int x;
      int z;
      int offset;
      if (nearest == north) {
        side = Direction.NORTH;
        x = clamp(targetX, region.minChunkX(), region.maxChunkXExclusive() - 1);
        z = region.minChunkZ();
        offset = x - region.minChunkX();
      } else if (nearest == south) {
        side = Direction.SOUTH;
        x = clamp(targetX, region.minChunkX(), region.maxChunkXExclusive() - 1);
        z = region.maxChunkZExclusive() - 1;
        offset = x - region.minChunkX();
      } else if (nearest == west) {
        side = Direction.WEST;
        x = region.minChunkX();
        z = clamp(targetZ, region.minChunkZ(), region.maxChunkZExclusive() - 1);
        offset = z - region.minChunkZ();
      } else {
        side = Direction.EAST;
        x = region.maxChunkXExclusive() - 1;
        z = clamp(targetZ, region.minChunkZ(), region.maxChunkZExclusive() - 1);
        offset = z - region.minChunkZ();
      }
      result.add(new RegionEntrance(side, offset, 1, connection.neighboringSlotIndex(), new ChunkPoint(x, z)));
    }
    return List.copyOf(result);
  }

  private static ChunkPoint localCenter(
      ChunkRectangle region,
      TerraCityRegionBuilder type,
      Random random
  ) {
    int x = region.minChunkX() + region.widthChunks() / 2;
    int z = region.minChunkZ() + region.lengthChunks() / 2;
    if (type.regionLayoutType() != RegionLayoutType.CONCENTRIC) {
      x += random.nextInt(3) - 1;
      z += random.nextInt(3) - 1;
    }
    return new ChunkPoint(
        clamp(x, region.minChunkX() + 1, region.maxChunkXExclusive() - 2),
        clamp(z, region.minChunkZ() + 1, region.maxChunkZExclusive() - 2)
    );
  }

  private static void addMandatoryRoads(
      RoadBuilder roads,
      ChunkPoint center,
      List<RegionEntrance> entrances,
      Random random
  ) {
    if (entrances.isEmpty()) {
      roads.connect(center, new ChunkPoint(roads.region.minChunkX(), center.chunkZ()), RoadClass.PRIMARY, true);
      return;
    }
    for (RegionEntrance entrance : entrances) {
      roads.connect(entrance.point(), center, RoadClass.PRIMARY, random.nextBoolean());
    }
  }

  private static void addGridRoads(
      RoadBuilder roads,
      ChunkPoint center,
      Random random,
      boolean sparse
  ) {
    int x = roads.region.minChunkX() + nextSpacing(roads.config, random);
    while (x < roads.region.maxChunkXExclusive() - 2) {
      if (!sparse || random.nextDouble() < 0.72) {
        ChunkPoint junction = new ChunkPoint(x, center.chunkZ());
        roads.connect(center, junction, RoadClass.SECONDARY, true);
        roads.segment(new ChunkPoint(x, roads.region.minChunkZ() + 1), junction, RoadClass.SECONDARY);
        roads.segment(junction, new ChunkPoint(x, roads.region.maxChunkZExclusive() - 2), RoadClass.SECONDARY);
      }
      x += nextSpacing(roads.config, random);
    }
    int z = roads.region.minChunkZ() + nextSpacing(roads.config, random);
    while (z < roads.region.maxChunkZExclusive() - 2) {
      if (!sparse || random.nextDouble() < 0.72) {
        ChunkPoint junction = new ChunkPoint(center.chunkX(), z);
        roads.connect(center, junction, RoadClass.SECONDARY, false);
        roads.segment(new ChunkPoint(roads.region.minChunkX() + 1, z), junction, RoadClass.SECONDARY);
        roads.segment(junction, new ChunkPoint(roads.region.maxChunkXExclusive() - 2, z), RoadClass.SECONDARY);
      }
      z += nextSpacing(roads.config, random);
    }
  }

  private static void addConcentricRoads(RoadBuilder roads, ChunkPoint center, Random random) {
    int inset = 3 + random.nextInt(2);
    while (roads.region.widthChunks() - inset * 2 >= 5
        && roads.region.lengthChunks() - inset * 2 >= 5) {
      int minX = roads.region.minChunkX() + inset;
      int maxX = roads.region.maxChunkXExclusive() - inset - 1;
      int minZ = roads.region.minChunkZ() + inset;
      int maxZ = roads.region.maxChunkZExclusive() - inset - 1;
      ChunkPoint nw = new ChunkPoint(minX, minZ);
      ChunkPoint ne = new ChunkPoint(maxX, minZ);
      ChunkPoint se = new ChunkPoint(maxX, maxZ);
      ChunkPoint sw = new ChunkPoint(minX, maxZ);
      roads.segment(nw, ne, RoadClass.SECONDARY);
      roads.segment(ne, se, RoadClass.SECONDARY);
      if (random.nextDouble() >= 0.18) roads.segment(se, sw, RoadClass.SECONDARY);
      roads.segment(sw, nw, RoadClass.SECONDARY);
      roads.connect(center, new ChunkPoint(center.chunkX(), minZ), RoadClass.PRIMARY, true);
      inset += 3 + random.nextInt(2);
    }
  }

  private static void addRadialGridRoads(
      RoadBuilder roads,
      ChunkPoint center,
      List<RegionEntrance> entrances,
      Random random
  ) {
    EnumSet<Direction> occupied = EnumSet.noneOf(Direction.class);
    entrances.forEach(entrance -> occupied.add(entrance.side()));
    ArrayList<Direction> candidates = new ArrayList<>(List.of(
        Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST
    ));
    Collections.shuffle(candidates, random);
    int extras = Math.max(0, 2 - entrances.size());
    for (Direction direction : candidates) {
      if (extras == 0) break;
      if (!occupied.add(direction)) continue;
      ChunkPoint target = switch (direction) {
        case NORTH -> new ChunkPoint(center.chunkX(), roads.region.minChunkZ());
        case SOUTH -> new ChunkPoint(center.chunkX(), roads.region.maxChunkZExclusive() - 1);
        case WEST -> new ChunkPoint(roads.region.minChunkX(), center.chunkZ());
        case EAST -> new ChunkPoint(roads.region.maxChunkXExclusive() - 1, center.chunkZ());
        default -> throw new IllegalStateException("非水平道路方向");
      };
      roads.segment(center, target, RoadClass.PRIMARY);
      extras--;
    }
    addGridRoads(roads, center, random, true);
  }

  private static int nextSpacing(RoadConfig config, Random random) {
    return config.gridSpacingChunks().get(random.nextInt(config.gridSpacingChunks().size()));
  }

  private static BlockExtraction extractBlocks(ChunkRectangle region, RoadRaster roads) {
    HashSet<Long> visited = new HashSet<>();
    ArrayList<UrbanBlock> blocks = new ArrayList<>();
    ArrayList<BlockComponent> components = new ArrayList<>();
    for (int z = region.minChunkZ(); z < region.maxChunkZExclusive(); z++) {
      for (int x = region.minChunkX(); x < region.maxChunkXExclusive(); x++) {
        long key = key(x, z);
        if (roads.isRoad(x, z) || !visited.add(key)) continue;
        ArrayDeque<ChunkPoint> queue = new ArrayDeque<>();
        ArrayList<ChunkPoint> cells = new ArrayList<>();
        queue.add(new ChunkPoint(x, z));
        int minX = x;
        int maxX = x;
        int minZ = z;
        int maxZ = z;
        while (!queue.isEmpty()) {
          ChunkPoint point = queue.removeFirst();
          cells.add(point);
          minX = Math.min(minX, point.chunkX());
          maxX = Math.max(maxX, point.chunkX());
          minZ = Math.min(minZ, point.chunkZ());
          maxZ = Math.max(maxZ, point.chunkZ());
          for (Direction direction : Direction.Plane.HORIZONTAL) {
            int nextX = point.chunkX() + direction.getStepX();
            int nextZ = point.chunkZ() + direction.getStepZ();
            if (!region.contains(nextX, nextZ) || roads.isRoad(nextX, nextZ)) continue;
            if (visited.add(key(nextX, nextZ))) queue.addLast(new ChunkPoint(nextX, nextZ));
          }
        }
        ChunkRectangle bounds = new ChunkRectangle(minX, minZ, maxX - minX + 1, maxZ - minZ + 1);
        int id = blocks.size();
        blocks.add(new UrbanBlock(id, cells.size(), bounds));
        components.add(new BlockComponent(id, List.copyOf(cells), bounds));
      }
    }
    return new BlockExtraction(List.copyOf(blocks), List.copyOf(components));
  }

  private static PartitionResult partition(
      List<BlockComponent> blocks,
      RoadRaster roads,
      ChunkRectangle region,
      TerraCityRegionBuilder type,
      Random random
  ) {
    ArrayList<BuildingParcel> parcels = new ArrayList<>();
    int remainingLandmarks = (int) type.buildings().stream()
        .filter(building -> building.unique()
            && building.building().footprintChunksX() == 2
            && building.building().footprintChunksZ() == 2)
        .count();
    boolean hasMediumShop = type.buildings().stream().anyMatch(building ->
        building.building().footprintChunksX() * building.building().footprintChunksZ() == 2);
    for (BlockComponent block : blocks) {
      HashSet<Long> remaining = new HashSet<>();
      block.cells().forEach(cell -> remaining.add(key(cell.chunkX(), cell.chunkZ())));
      while (remainingLandmarks > 0) {
        ChunkRectangle landmark = firstRectangle(remaining, 2, 2);
        if (landmark == null) break;
        addParcel(parcels, block.id(), landmark, roads, null);
        remove(remaining, landmark);
        remainingLandmarks--;
      }
      while (!remaining.isEmpty()) {
        long first = remaining.stream().min(Long::compare).orElseThrow();
        int startX = keyX(first);
        int startZ = keyZ(first);
        ChunkRectangle area = null;
        EnumSet<Direction> compatibleFacings = null;
        if (hasMediumShop) {
          boolean vertical = remaining.contains(key(startX, startZ + 1));
          boolean horizontal = remaining.contains(key(startX + 1, startZ));
          if (vertical && horizontal) {
            vertical = random.nextBoolean();
            horizontal = !vertical;
          }
          if (vertical) {
            area = new ChunkRectangle(startX, startZ, 1, 2);
            compatibleFacings = EnumSet.of(Direction.NORTH, Direction.SOUTH);
          } else if (horizontal) {
            area = new ChunkRectangle(startX, startZ, 2, 1);
            compatibleFacings = EnumSet.of(Direction.WEST, Direction.EAST);
          }
        }
        if (area == null) {
          area = new ChunkRectangle(startX, startZ, 1, 1);
        }
        addParcel(parcels, block.id(), area, roads, compatibleFacings);
        remove(remaining, area);
      }
    }
    return new PartitionResult(List.copyOf(parcels), List.of());
  }

  private static ChunkRectangle firstRectangle(Set<Long> cells, int width, int length) {
    for (long cell : cells.stream().sorted().toList()) {
      int startX = keyX(cell);
      int startZ = keyZ(cell);
      boolean fits = true;
      for (int z = startZ; z < startZ + length && fits; z++) {
        for (int x = startX; x < startX + width; x++) {
          if (!cells.contains(key(x, z))) {
            fits = false;
            break;
          }
        }
      }
      if (fits) return new ChunkRectangle(startX, startZ, width, length);
    }
    return null;
  }

  private static void addParcel(
      List<BuildingParcel> parcels,
      int blockId,
      ChunkRectangle area,
      RoadRaster roads,
      @org.jetbrains.annotations.Nullable Set<Direction> compatibleFacings
  ) {
    RoadContact contact = roads.nearestContact(area, compatibleFacings);
    parcels.add(new BuildingParcel(
        parcels.size(), blockId, area, area,
        contact.facing(), contact.roadId(), contact.roadClass()
    ));
  }

  private static void remove(Set<Long> cells, ChunkRectangle area) {
    for (int z = area.minChunkZ(); z < area.maxChunkZExclusive(); z++) {
      for (int x = area.minChunkX(); x < area.maxChunkXExclusive(); x++) cells.remove(key(x, z));
    }
  }

  private static int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }

  private static long key(int x, int z) {
    return ((long) x << 32) ^ (z & 0xffffffffL);
  }

  private static int keyX(long key) {
    return (int) (key >> 32);
  }

  private static int keyZ(long key) {
    return (int) key;
  }

  private record PartitionResult(List<BuildingParcel> parcels, List<OpenSpace> openSpaces) {
  }

  private record BlockComponent(int id, List<ChunkPoint> cells, ChunkRectangle bounds) {
  }

  private record BlockExtraction(List<UrbanBlock> blocks, List<BlockComponent> components) {
  }

  private record RoadContact(int roadId, RoadClass roadClass, Direction facing) {
  }

  private static final class RoadRaster {
    private final ChunkRectangle region;
    private final RoadGraph graph;
    private final HashSet<Long> cells = new HashSet<>();

    private RoadRaster(ChunkRectangle region, RoadGraph graph) {
      this.region = region;
      this.graph = graph;
      for (RoadEdge edge : graph.edges()) {
        for (int z = edge.chunkArea().minChunkZ(); z < edge.chunkArea().maxChunkZExclusive(); z++) {
          for (int x = edge.chunkArea().minChunkX(); x < edge.chunkArea().maxChunkXExclusive(); x++) {
            if (region.contains(x, z)) cells.add(key(x, z));
          }
        }
      }
    }

    private boolean isRoad(int x, int z) {
      return cells.contains(key(x, z));
    }

    private int roadCount() {
      return cells.size();
    }

    private RoadContact nearestContact(
        ChunkRectangle area,
        @org.jetbrains.annotations.Nullable Set<Direction> compatibleFacings
    ) {
      RoadContact best = null;
      int bestDistance = Integer.MAX_VALUE;
      for (RoadEdge edge : graph.edges()) {
        for (Direction facing : Direction.Plane.HORIZONTAL) {
          if (compatibleFacings != null && !compatibleFacings.contains(facing)) continue;
          int distance = directionalDistance(area, edge.chunkArea(), facing);
          RoadContact candidate = new RoadContact(edge.id(), edge.roadClass(), facing);
          if (best == null || distance < bestDistance
              || distance == bestDistance
              && candidate.roadClass().priority() > best.roadClass().priority()) {
            best = candidate;
            bestDistance = distance;
          }
        }
      }
      return Objects.requireNonNull(best, "Region 必须至少包含一条道路");
    }

    private static int directionalDistance(
        ChunkRectangle parcel,
        ChunkRectangle road,
        Direction facing
    ) {
      int parcelCenterX2 = parcel.minChunkX() + parcel.maxChunkXExclusive();
      int parcelCenterZ2 = parcel.minChunkZ() + parcel.maxChunkZExclusive();
      int roadCenterX2 = road.minChunkX() + road.maxChunkXExclusive();
      int roadCenterZ2 = road.minChunkZ() + road.maxChunkZExclusive();
      int dx = Math.abs(parcelCenterX2 - roadCenterX2);
      int dz = Math.abs(parcelCenterZ2 - roadCenterZ2);
      boolean correctHalfPlane = switch (facing) {
        case NORTH -> roadCenterZ2 < parcelCenterZ2;
        case SOUTH -> roadCenterZ2 > parcelCenterZ2;
        case WEST -> roadCenterX2 < parcelCenterX2;
        case EAST -> roadCenterX2 > parcelCenterX2;
        default -> false;
      };
      return dx + dz + (correctHalfPlane ? 0 : 1_000_000);
    }
  }

  private static final class RoadBuilder {
    private final ChunkRectangle region;
    private final RoadConfig config;
    private final ArrayList<RoadNode> nodes = new ArrayList<>();
    private final ArrayList<RoadEdge> edges = new ArrayList<>();
    private final HashMap<ChunkPoint, Integer> nodeIds = new HashMap<>();
    private final HashSet<String> edgeKeys = new HashSet<>();

    private RoadBuilder(ChunkRectangle region, RoadConfig config) {
      this.region = region;
      this.config = config;
    }

    private void connect(ChunkPoint from, ChunkPoint to, RoadClass roadClass, boolean xFirst) {
      if (from.chunkX() == to.chunkX() || from.chunkZ() == to.chunkZ()) {
        segment(from, to, roadClass);
        return;
      }
      ChunkPoint elbow = xFirst
          ? new ChunkPoint(to.chunkX(), from.chunkZ())
          : new ChunkPoint(from.chunkX(), to.chunkZ());
      segment(from, elbow, roadClass);
      segment(elbow, to, roadClass);
    }

    private void segment(ChunkPoint from, ChunkPoint to, RoadClass roadClass) {
      if (from.equals(to)) return;
      if (from.chunkX() != to.chunkX() && from.chunkZ() != to.chunkZ()) {
        throw new IllegalArgumentException("Region 道路边必须正交");
      }
      int width = config.width(roadClass);
      int minX;
      int minZ;
      int areaWidth;
      int areaLength;
      if (from.chunkZ() == to.chunkZ()) {
        minX = Math.min(from.chunkX(), to.chunkX());
        areaWidth = Math.abs(from.chunkX() - to.chunkX()) + 1;
        minZ = clamp(from.chunkZ() - (width - 1) / 2,
            region.minChunkZ(), region.maxChunkZExclusive() - width);
        areaLength = width;
      } else {
        minZ = Math.min(from.chunkZ(), to.chunkZ());
        areaLength = Math.abs(from.chunkZ() - to.chunkZ()) + 1;
        minX = clamp(from.chunkX() - (width - 1) / 2,
            region.minChunkX(), region.maxChunkXExclusive() - width);
        areaWidth = width;
      }
      ChunkRectangle area = new ChunkRectangle(minX, minZ, areaWidth, areaLength);
      String edgeKey = roadClass + ":" + area;
      if (!edgeKeys.add(edgeKey)) return;
      int fromId = node(from, RoadNodeType.INTERSECTION);
      int toId = node(to, RoadNodeType.INTERSECTION);
      edges.add(new RoadEdge(edges.size(), fromId, toId, roadClass, width, area));
    }

    private int node(ChunkPoint point, RoadNodeType type) {
      return nodeIds.computeIfAbsent(point, ignored -> {
        int id = nodes.size();
        nodes.add(new RoadNode(id, point, type));
        return id;
      });
    }

    private RoadGraph build() {
      return new RoadGraph(nodes, edges);
    }
  }
}
