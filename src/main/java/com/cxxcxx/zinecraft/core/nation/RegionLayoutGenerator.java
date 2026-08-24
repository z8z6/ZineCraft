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
    List<ChunkPoint> stairs = coreStairs(region, center);
    RegionLayoutType[] implementedLayouts = {
        RegionLayoutType.GRID, RegionLayoutType.CONCENTRIC, RegionLayoutType.RADIAL_GRID
    };
    ArrayList<MobileLayerPlan> layers = new ArrayList<>(MobileLayer.values().length);
    for (MobileLayer layer : MobileLayer.values()) {
      List<RegionEntrance> layerEntrances = layer == MobileLayer.SURFACE ? entrances : List.of();
      Random layerRandom = new Random(mixLayerSeed(seed, layer));
      RegionLayoutType layerLayoutType = layer == MobileLayer.SURFACE
          ? type.regionLayoutType()
          : implementedLayouts[layerRandom.nextInt(implementedLayouts.length)];
      layers.add(generateLayer(region, type, layer, layerLayoutType, layerEntrances, center, stairs,
          layerRandom));
    }
    MobileLayerPlan surface = layers.stream().filter(layer -> layer.layer() == MobileLayer.SURFACE)
        .findFirst().orElseThrow();
    RegionLayout layout = new RegionLayout(
        type.regionLayoutType(), center, entrances, surface.roadGraph(), layers,
        surface.urbanBlocks(), surface.parcels(), surface.openSpaces(), surface.roadCoverage(),
        surface.buildingCoverage(), DEBUG_STAGES
    );
    RegionLayoutValidator.validate(region, layout);
    return layout;
  }

  private static MobileLayerPlan generateLayer(
      ChunkRectangle region,
      TerraCityRegionBuilder type,
      MobileLayer layer,
      RegionLayoutType layerLayoutType,
      List<RegionEntrance> entrances,
      ChunkPoint center,
      List<ChunkPoint> stairs,
      Random random
  ) {
    RoadBuilder roads = new RoadBuilder(region, type.roadConfig());
    ChunkPoint layerHub = layerHub(region, center, layer, random);
    addMandatoryRoads(roads, layerHub, entrances, random);
    roads.connect(center, layerHub, RoadClass.PRIMARY, random.nextBoolean());
    switch (layerLayoutType) {
      case GRID -> addGridRoads(roads, layerHub, random, false);
      case CONCENTRIC -> addConcentricRoads(roads, layerHub, random);
      case RADIAL_GRID -> addRadialGridRoads(roads, layerHub, entrances, random);
      case SPINE, CAMPUS, HYBRID -> throw new IllegalStateException(
          "尚未实现的 Region 内部布局进入生成器：" + layerLayoutType
      );
    }
    for (ChunkPoint stair : stairs) {
      roads.connectToRoad(stair, RoadClass.PRIMARY, random);
    }
    addAccessibilityRoads(roads, random);
    HashSet<ChunkPoint> protectedRoads = new HashSet<>(stairs);
    entrances.stream().map(RegionEntrance::point).forEach(protectedRoads::add);
    roads.removeRoadSquares(protectedRoads);
    RoadGraph graph = roads.build();
    RoadRaster raster = new RoadRaster(region, graph);
    BlockExtraction extraction = extractBlocks(region, raster);
    PartitionResult partition = partition(
        extraction.components(), raster, region, type, random, layer == MobileLayer.SURFACE
    );
    double roadCoverage = raster.roadCount() / (double) region.areaChunks();
    int buildingChunks = partition.parcels().stream().mapToInt(parcel -> parcel.area().areaChunks()).sum();
    String buildingId = switch (layer) {
      case POWER -> "mobile_plot_power_layer";
      case SUPPORT -> "mobile_plot_support_layer";
      case LIFE -> "mobile_plot_life_layer";
      case SURFACE -> "surface_buildings";
    };
    return new MobileLayerPlan(
        layer, layerLayoutType, buildingId, region, graph, extraction.blocks(), partition.parcels(),
        partition.openSpaces(), roadCoverage, buildingChunks / (double) region.areaChunks(), stairs
    );
  }

  private static List<ChunkPoint> coreStairs(ChunkRectangle region, ChunkPoint center) {
    int offsetX = Math.max(2, region.widthChunks() / 6);
    int offsetZ = Math.max(2, region.lengthChunks() / 6);
    int minX = region.minChunkX() + 1;
    int maxX = region.maxChunkXExclusive() - 2;
    int minZ = region.minChunkZ() + 1;
    int maxZ = region.maxChunkZExclusive() - 2;
    int west = clamp(center.chunkX() - offsetX, minX, maxX);
    int east = clamp(center.chunkX() + offsetX, minX, maxX);
    int north = clamp(center.chunkZ() - offsetZ, minZ, maxZ);
    int south = clamp(center.chunkZ() + offsetZ, minZ, maxZ);
    List<ChunkPoint> stairs = List.of(
        new ChunkPoint(west, north),
        new ChunkPoint(east, north),
        new ChunkPoint(west, south),
        new ChunkPoint(east, south)
    );
    if (stairs.stream().distinct().count() != 4) {
      throw new IllegalArgumentException("核心区尺寸不足，无法分散布置四个楼梯");
    }
    return stairs;
  }

  private static long mixLayerSeed(long seed, MobileLayer layer) {
    long value = seed ^ (0x9E3779B97F4A7C15L * (layer.ordinal() + 1L));
    value ^= value >>> 33;
    value *= 0xff51afd7ed558ccdl;
    return value ^ value >>> 33;
  }

  private static ChunkPoint layerHub(
      ChunkRectangle region,
      ChunkPoint stair,
      MobileLayer layer,
      Random random
  ) {
    int[][] quadrants = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
    int[] quadrant = quadrants[layer.ordinal()];
    int distanceX = Math.max(1, region.widthChunks() / 4);
    int distanceZ = Math.max(1, region.lengthChunks() / 4);
    int jitterX = random.nextInt(Math.max(1, distanceX));
    int jitterZ = random.nextInt(Math.max(1, distanceZ));
    return new ChunkPoint(
        clamp(stair.chunkX() + quadrant[0] * (distanceX + jitterX),
            region.minChunkX() + 1, region.maxChunkXExclusive() - 2),
        clamp(stair.chunkZ() + quadrant[1] * (distanceZ + jitterZ),
            region.minChunkZ() + 1, region.maxChunkZExclusive() - 2)
    );
  }

  /**
   * 只从街区最深处向既有道路补最短单宽支路。路径每一步都降低到道路的距离，
   * 因此不会沿道路平行铺设；不同层的独立随机源负责等距分支选择。
   */
  private static void addAccessibilityRoads(RoadBuilder roads, Random random) {
    for (int attempt = 0; attempt < roads.region.areaChunks(); attempt++) {
      RoadRaster raster = new RoadRaster(roads.region, roads.build());
      DistanceField field = DistanceField.from(roads.region, raster);
      if (field.maxDistance() <= 1) return;
      roads.path(field.pathToRoad(random), RoadClass.SERVICE);
    }
    throw new IllegalStateException("Region 可达性支路在限定次数内未收敛");
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
    ArrayList<RegionEntrance> ordered = new ArrayList<>(entrances);
    Collections.shuffle(ordered, random);
    roads.connect(ordered.getFirst().point(), center, RoadClass.PRIMARY, random.nextBoolean());
    for (int index = 1; index < ordered.size(); index++) {
      roads.connectToRoad(ordered.get(index).point(), RoadClass.PRIMARY, random);
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
        if (roads.hasParallelVertical(x, roads.region.minChunkZ() + 1,
            roads.region.maxChunkZExclusive() - 2)) {
          x += nextSpacing(roads.config, random);
          continue;
        }
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
        if (roads.hasParallelHorizontal(z, roads.region.minChunkX() + 1,
            roads.region.maxChunkXExclusive() - 2)) {
          z += nextSpacing(roads.config, random);
          continue;
        }
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
      ChunkPoint ringAccess = new ChunkPoint(clamp(center.chunkX(), minX, maxX), minZ);
      roads.connect(center, ringAccess, RoadClass.PRIMARY, true);
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
      Random random,
      boolean surface
  ) {
    ArrayList<BuildingParcel> parcels = new ArrayList<>();
    int remainingLandmarks = surface ? (int) type.buildings().stream()
        .filter(building -> building.unique()
            && building.building().footprintChunksX() == 2
            && building.building().footprintChunksZ() == 2)
        .count() : 0;
    boolean hasMediumShop = surface && type.buildings().stream().anyMatch(building ->
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
            ChunkRectangle candidate = new ChunkRectangle(startX, startZ, 1, 2);
            EnumSet<Direction> facings = EnumSet.of(Direction.NORTH, Direction.SOUTH);
            if (!roads.contacts(candidate, facings).isEmpty()) {
              area = candidate;
              compatibleFacings = facings;
            }
          } else if (horizontal) {
            ChunkRectangle candidate = new ChunkRectangle(startX, startZ, 2, 1);
            EnumSet<Direction> facings = EnumSet.of(Direction.WEST, Direction.EAST);
            if (!roads.contacts(candidate, facings).isEmpty()) {
              area = candidate;
              compatibleFacings = facings;
            }
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
    List<RoadContact> contacts = roads.contacts(area, compatibleFacings);
    if (contacts.isEmpty()) {
      throw new IllegalArgumentException("BuildingParcel 没有真实邻接道路：" + area);
    }
    RoadContact contact = contacts.getFirst();
    parcels.add(new BuildingParcel(
        parcels.size(), blockId, area, area,
        contact.facing(), contact.roadId(), contact.roadClass(), contacts.stream()
            .map(candidate -> new BuildingRoadConnection(
                candidate.facing(), candidate.roadId(), candidate.roadClass()
            )).toList()
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

    private List<RoadContact> contacts(
        ChunkRectangle area,
        @org.jetbrains.annotations.Nullable Set<Direction> compatibleFacings
    ) {
      ArrayList<RoadContact> contacts = new ArrayList<>();
      for (RoadEdge edge : graph.edges()) {
        for (Direction facing : Direction.Plane.HORIZONTAL) {
          if (compatibleFacings != null && !compatibleFacings.contains(facing)) continue;
          if (touches(area, edge.chunkArea(), facing)) {
            contacts.add(new RoadContact(edge.id(), edge.roadClass(), facing));
          }
        }
      }
      return contacts.stream().distinct().sorted(
          Comparator.comparingInt((RoadContact contact) -> contact.roadClass().priority()).reversed()
              .thenComparingInt(contact -> contact.facing().get2DDataValue())
              .thenComparingInt(RoadContact::roadId)
      ).toList();
    }

    private static boolean touches(
        ChunkRectangle parcel,
        ChunkRectangle road,
        Direction facing
    ) {
      return switch (facing) {
        case NORTH -> road.contains(parcel.minChunkX(), parcel.minChunkZ() - 1)
            || road.contains(parcel.maxChunkXExclusive() - 1, parcel.minChunkZ() - 1);
        case SOUTH -> road.contains(parcel.minChunkX(), parcel.maxChunkZExclusive())
            || road.contains(parcel.maxChunkXExclusive() - 1, parcel.maxChunkZExclusive());
        case WEST -> road.contains(parcel.minChunkX() - 1, parcel.minChunkZ())
            || road.contains(parcel.minChunkX() - 1, parcel.maxChunkZExclusive() - 1);
        case EAST -> road.contains(parcel.maxChunkXExclusive(), parcel.minChunkZ())
            || road.contains(parcel.maxChunkXExclusive(), parcel.maxChunkZExclusive() - 1);
        default -> false;
      };
    }
  }

  private static final class DistanceField {
    private final ChunkRectangle region;
    private final int[][] distances;
    private final int maxDistance;
    private final List<ChunkPoint> farthest;

    private DistanceField(
        ChunkRectangle region,
        int[][] distances,
        int maxDistance,
        List<ChunkPoint> farthest
    ) {
      this.region = region;
      this.distances = distances;
      this.maxDistance = maxDistance;
      this.farthest = farthest;
    }

    private static DistanceField from(ChunkRectangle region, RoadRaster roads) {
      int[][] distances = new int[region.lengthChunks()][region.widthChunks()];
      for (int[] row : distances) Arrays.fill(row, Integer.MAX_VALUE);
      ArrayDeque<ChunkPoint> queue = new ArrayDeque<>();
      for (int z = region.minChunkZ(); z < region.maxChunkZExclusive(); z++) {
        for (int x = region.minChunkX(); x < region.maxChunkXExclusive(); x++) {
          if (!roads.isRoad(x, z)) continue;
          distances[z - region.minChunkZ()][x - region.minChunkX()] = 0;
          queue.addLast(new ChunkPoint(x, z));
        }
      }
      while (!queue.isEmpty()) {
        ChunkPoint current = queue.removeFirst();
        int nextDistance = distance(region, distances, current) + 1;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
          ChunkPoint next = new ChunkPoint(
              current.chunkX() + direction.getStepX(),
              current.chunkZ() + direction.getStepZ()
          );
          if (!region.contains(next.chunkX(), next.chunkZ())
              || distance(region, distances, next) <= nextDistance) continue;
          distances[next.chunkZ() - region.minChunkZ()][next.chunkX() - region.minChunkX()] = nextDistance;
          queue.addLast(next);
        }
      }
      int maximum = 0;
      ArrayList<ChunkPoint> farthest = new ArrayList<>();
      for (int z = region.minChunkZ(); z < region.maxChunkZExclusive(); z++) {
        for (int x = region.minChunkX(); x < region.maxChunkXExclusive(); x++) {
          int value = distances[z - region.minChunkZ()][x - region.minChunkX()];
          if (value > maximum) {
            maximum = value;
            farthest.clear();
          }
          if (value == maximum) farthest.add(new ChunkPoint(x, z));
        }
      }
      return new DistanceField(region, distances, maximum, List.copyOf(farthest));
    }

    private int maxDistance() {
      return maxDistance;
    }

    private List<ChunkPoint> pathToRoad(Random random) {
      return pathFrom(farthest.get(random.nextInt(farthest.size())), random);
    }

    private List<ChunkPoint> pathFrom(ChunkPoint start, Random random) {
      ChunkPoint current = start;
      ArrayList<ChunkPoint> path = new ArrayList<>();
      path.add(current);
      while (distance(region, distances, current) > 0) {
        int targetDistance = distance(region, distances, current) - 1;
        ArrayList<ChunkPoint> candidates = new ArrayList<>(2);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
          ChunkPoint next = new ChunkPoint(
              current.chunkX() + direction.getStepX(),
              current.chunkZ() + direction.getStepZ()
          );
          if (region.contains(next.chunkX(), next.chunkZ())
              && distance(region, distances, next) == targetDistance) {
            candidates.add(next);
          }
        }
        current = candidates.get(random.nextInt(candidates.size()));
        path.add(current);
      }
      return List.copyOf(path);
    }

    private static int distance(ChunkRectangle region, int[][] distances, ChunkPoint point) {
      return distances[point.chunkZ() - region.minChunkZ()][point.chunkX() - region.minChunkX()];
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

    private void path(List<ChunkPoint> points, RoadClass roadClass) {
      if (points.size() < 2) return;
      int runStart = 0;
      int previousStepX = points.get(1).chunkX() - points.getFirst().chunkX();
      int previousStepZ = points.get(1).chunkZ() - points.getFirst().chunkZ();
      for (int index = 2; index < points.size(); index++) {
        int stepX = points.get(index).chunkX() - points.get(index - 1).chunkX();
        int stepZ = points.get(index).chunkZ() - points.get(index - 1).chunkZ();
        if (stepX == previousStepX && stepZ == previousStepZ) continue;
        segment(points.get(runStart), points.get(index - 1), roadClass);
        runStart = index - 1;
        previousStepX = stepX;
        previousStepZ = stepZ;
      }
      segment(points.get(runStart), points.getLast(), roadClass);
    }

    private void connectToRoad(ChunkPoint point, RoadClass roadClass, Random random) {
      RoadRaster raster = new RoadRaster(region, build());
      if (raster.isRoad(point.chunkX(), point.chunkZ())) return;
      path(DistanceField.from(region, raster).pathFrom(point, random), roadClass);
    }

    private void removeRoadSquares(Set<ChunkPoint> protectedCells) {
      HashSet<ChunkPoint> cells = new HashSet<>();
      HashMap<ChunkPoint, RoadClass> classes = new HashMap<>();
      for (RoadEdge edge : edges) {
        for (int z = edge.chunkArea().minChunkZ(); z < edge.chunkArea().maxChunkZExclusive(); z++) {
          for (int x = edge.chunkArea().minChunkX(); x < edge.chunkArea().maxChunkXExclusive(); x++) {
            ChunkPoint point = new ChunkPoint(x, z);
            cells.add(point);
            classes.merge(point, edge.roadClass(), (left, right) ->
                left.priority() >= right.priority() ? left : right);
          }
        }
      }
      boolean changed;
      do {
        changed = false;
        search:
        for (int z = region.minChunkZ(); z < region.maxChunkZExclusive() - 1; z++) {
          for (int x = region.minChunkX(); x < region.maxChunkXExclusive() - 1; x++) {
            List<ChunkPoint> square = List.of(
                new ChunkPoint(x, z), new ChunkPoint(x + 1, z),
                new ChunkPoint(x, z + 1), new ChunkPoint(x + 1, z + 1)
            );
            if (!cells.containsAll(square)) continue;
            ArrayList<ChunkPoint> candidates = new ArrayList<>(square);
            candidates.sort(Comparator.comparingInt(point -> roadDegree(cells, point)));
            for (ChunkPoint candidate : candidates) {
              if (protectedCells.contains(candidate)
                  || !connectedWithout(cells, candidate)
                  || !allCellsRoadAdjacentWithout(region, cells, candidate)) continue;
              cells.remove(candidate);
              changed = true;
              break search;
            }
          }
        }
      } while (changed);
      rebuildFromCells(cells, classes);
    }

    private static int roadDegree(Set<ChunkPoint> cells, ChunkPoint point) {
      int degree = 0;
      for (Direction direction : Direction.Plane.HORIZONTAL) {
        if (cells.contains(new ChunkPoint(
            point.chunkX() + direction.getStepX(), point.chunkZ() + direction.getStepZ()
        ))) degree++;
      }
      return degree;
    }

    private static boolean connectedWithout(Set<ChunkPoint> cells, ChunkPoint removed) {
      ChunkPoint start = cells.stream().filter(point -> !point.equals(removed)).findFirst().orElse(null);
      if (start == null) return true;
      HashSet<ChunkPoint> visited = new HashSet<>();
      ArrayDeque<ChunkPoint> queue = new ArrayDeque<>();
      visited.add(start);
      queue.add(start);
      while (!queue.isEmpty()) {
        ChunkPoint current = queue.removeFirst();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
          ChunkPoint next = new ChunkPoint(
              current.chunkX() + direction.getStepX(), current.chunkZ() + direction.getStepZ()
          );
          if (!next.equals(removed) && cells.contains(next) && visited.add(next)) queue.addLast(next);
        }
      }
      return visited.size() == cells.size() - 1;
    }

    private static boolean allCellsRoadAdjacentWithout(
        ChunkRectangle region,
        Set<ChunkPoint> roads,
        ChunkPoint removed
    ) {
      for (int z = region.minChunkZ(); z < region.maxChunkZExclusive(); z++) {
        for (int x = region.minChunkX(); x < region.maxChunkXExclusive(); x++) {
          ChunkPoint point = new ChunkPoint(x, z);
          if (!point.equals(removed) && roads.contains(point)) continue;
          boolean adjacent = false;
          for (Direction direction : Direction.Plane.HORIZONTAL) {
            ChunkPoint neighbor = new ChunkPoint(
                x + direction.getStepX(), z + direction.getStepZ()
            );
            if (!neighbor.equals(removed) && roads.contains(neighbor)) {
              adjacent = true;
              break;
            }
          }
          if (!adjacent) return false;
        }
      }
      return true;
    }

    private void rebuildFromCells(
        Set<ChunkPoint> cells,
        Map<ChunkPoint, RoadClass> classes
    ) {
      nodes.clear();
      edges.clear();
      nodeIds.clear();
      edgeKeys.clear();
      for (int z = region.minChunkZ(); z < region.maxChunkZExclusive(); z++) {
        int x = region.minChunkX();
        while (x < region.maxChunkXExclusive()) {
          while (x < region.maxChunkXExclusive() && !cells.contains(new ChunkPoint(x, z))) x++;
          int start = x;
          RoadClass roadClass = RoadClass.SERVICE;
          while (x < region.maxChunkXExclusive() && cells.contains(new ChunkPoint(x, z))) {
            RoadClass candidate = classes.getOrDefault(new ChunkPoint(x, z), RoadClass.SERVICE);
            if (candidate.priority() > roadClass.priority()) roadClass = candidate;
            x++;
          }
          if (x - start >= 2) segment(new ChunkPoint(start, z), new ChunkPoint(x - 1, z), roadClass);
        }
      }
      for (int x = region.minChunkX(); x < region.maxChunkXExclusive(); x++) {
        int z = region.minChunkZ();
        while (z < region.maxChunkZExclusive()) {
          while (z < region.maxChunkZExclusive() && !cells.contains(new ChunkPoint(x, z))) z++;
          int start = z;
          RoadClass roadClass = RoadClass.SERVICE;
          while (z < region.maxChunkZExclusive() && cells.contains(new ChunkPoint(x, z))) {
            RoadClass candidate = classes.getOrDefault(new ChunkPoint(x, z), RoadClass.SERVICE);
            if (candidate.priority() > roadClass.priority()) roadClass = candidate;
            z++;
          }
          if (z - start >= 2) segment(new ChunkPoint(x, start), new ChunkPoint(x, z - 1), roadClass);
        }
      }
    }

    private boolean hasParallelVertical(int x, int minZ, int maxZ) {
      return edges.stream().anyMatch(edge -> edge.chunkArea().widthChunks() == 1
          && edge.chunkArea().lengthChunks() > 1
          && Math.abs(edge.chunkArea().minChunkX() - x) <= 1
          && overlaps(minZ, maxZ + 1, edge.chunkArea().minChunkZ(), edge.chunkArea().maxChunkZExclusive()));
    }

    private boolean hasParallelHorizontal(int z, int minX, int maxX) {
      return edges.stream().anyMatch(edge -> edge.chunkArea().lengthChunks() == 1
          && edge.chunkArea().widthChunks() > 1
          && Math.abs(edge.chunkArea().minChunkZ() - z) <= 1
          && overlaps(minX, maxX + 1, edge.chunkArea().minChunkX(), edge.chunkArea().maxChunkXExclusive()));
    }

    private static boolean overlaps(int minA, int maxA, int minB, int maxB) {
      return minA < maxB && minB < maxA;
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
