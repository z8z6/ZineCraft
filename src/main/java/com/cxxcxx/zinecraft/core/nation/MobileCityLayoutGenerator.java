package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.city.*;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.*;
import java.util.random.RandomGenerator;

/** 基于 Chunk 栅格、中心评分和道路前沿的确定性正交城市生长器。 */
public final class MobileCityLayoutGenerator {
  private final CityCoreFinder coreFinder;
  private final LayoutValidator validator;

  public MobileCityLayoutGenerator() {
    this(new CityCoreFinder(), new LayoutValidator());
  }

  public MobileCityLayoutGenerator(CityCoreFinder coreFinder, LayoutValidator validator) {
    this.coreFinder = Objects.requireNonNull(coreFinder, "城市核心计算器不能为空");
    this.validator = Objects.requireNonNull(validator, "布局校验器不能为空");
  }

  public LayoutGenerationResult generate(
      TerraCityBuilder city,
      List<PlanarPoint> boundary,
      RandomGenerator random
  ) {
    return generate(city, boundary, random, LayoutDebugCollector.NONE);
  }

  public LayoutGenerationResult generate(
      TerraCityBuilder city,
      List<PlanarPoint> boundary,
      RandomGenerator random,
      LayoutDebugCollector debug
  ) {
    Objects.requireNonNull(city, "城市不能为空");
    Objects.requireNonNull(random, "城市布局随机源不能为空");
    Objects.requireNonNull(debug, "布局调试收集器不能为空");
    List<TerraCityRegionBuilder> types = city.regions();
    int mandatoryCount = types.stream().mapToInt(TerraCityRegionBuilder::minCount).sum();
    if (types.isEmpty() || city.minPlotCount() < mandatoryCount
        || city.maxPlotCount() < city.minPlotCount() || city.roadWidthChunks() <= 0) {
      return LayoutGenerationResult.failure(
          LayoutFailureReason.INVALID_CONFIGURATION,
          "城市地块数量、类型下限或道路宽度配置无效：" + city.id()
      );
    }

    CityGrid grid;
    PlanarPoint core;
    try {
      grid = new CityGrid(boundary);
      core = coreFinder.find(grid);
    } catch (IllegalArgumentException exception) {
      return LayoutGenerationResult.failure(LayoutFailureReason.CITY_TOO_SMALL, exception.getMessage());
    }
    if (grid.usableChunkArea() == 0) {
      return LayoutGenerationResult.failure(LayoutFailureReason.CITY_TOO_SMALL, "城市内没有完整 Chunk：" + city.id());
    }

    ArrayList<UrbanPlot> plots = new ArrayList<>();
    ArrayList<UrbanRoad> roads = new ArrayList<>();
    EnumMap<CandidateRejectReason, Integer> rejects = new EnumMap<>(CandidateRejectReason.class);
    LinkedHashMap<TerraCityRegionBuilder, Integer> counts = new LinkedHashMap<>();
    types.forEach(type -> counts.put(type, 0));
    int[] plotArea = {0};
    snapshot(debug, "grid_and_core", core, plots, roads, rejects, 0.0);

    List<TerraCityRegionBuilder> mandatory = types.stream()
        .sorted(Comparator.comparingInt((TerraCityRegionBuilder type) -> type.weight()).reversed()
            .thenComparing(TerraCityRegionBuilder::id))
        .flatMap(type -> java.util.stream.IntStream.range(0, type.minCount()).mapToObj(ignored -> type))
        .toList();
    for (TerraCityRegionBuilder type : mandatory) {
      Optional<Candidate> candidate = bestCandidate(
          city, type, grid, core, plots, roads, plotArea[0], random, rejects
      );
      if (candidate.isEmpty()) {
        return LayoutGenerationResult.failure(
            LayoutFailureReason.MANDATORY_PLOTS_CANNOT_FIT,
            "无法放置必选 Region：" + city.id() + "/" + type.id()
        );
      }
      accept(candidate.get(), type, grid, plots, roads, counts, plotArea);
      snapshot(debug, "mandatory_" + plots.size(), core, plots, roads, rejects,
          coverage(plotArea[0], grid.usableChunkArea()));
    }

    while (plots.size() < city.maxPlotCount()) {
      List<TerraCityRegionBuilder> eligible = types.stream()
          .filter(type -> counts.get(type) < type.maxCount())
          .toList();
      if (eligible.isEmpty()) break;
      List<TerraCityRegionBuilder> order = weightedTypeOrder(eligible, counts, random);
      Candidate selected = null;
      TerraCityRegionBuilder selectedType = null;
      for (TerraCityRegionBuilder type : order) {
        Optional<Candidate> candidate = bestCandidate(
            city, type, grid, core, plots, roads, plotArea[0], random, rejects
        );
        if (candidate.isPresent()) {
          selected = candidate.get();
          selectedType = type;
          break;
        }
      }
      if (selected == null) break;
      accept(selected, selectedType, grid, plots, roads, counts, plotArea);
      snapshot(debug, "optional_" + plots.size(), core, plots, roads, rejects,
          coverage(plotArea[0], grid.usableChunkArea()));
    }

    if (plots.size() < city.minPlotCount()) {
      return LayoutGenerationResult.failure(
          LayoutFailureReason.MINIMUM_PLOT_COUNT_CANNOT_FIT,
          "城市只能容纳 " + plots.size() + " 个地块，低于下限 " + city.minPlotCount() + "：" + city.id()
      );
    }
    MobileCityLayout layout = new MobileCityLayout(
        core, grid.usableChunkArea(), plots, roads, coverage(plotArea[0], grid.usableChunkArea())
    );
    Optional<String> validationError = validator.validate(city, grid, layout);
    if (validationError.isPresent()) {
      return LayoutGenerationResult.failure(LayoutFailureReason.INVALID_CONFIGURATION, validationError.get());
    }
    snapshot(debug, "final", core, plots, roads, rejects, layout.coverage());
    return LayoutGenerationResult.success(layout);
  }

  private Optional<Candidate> bestCandidate(
      TerraCityBuilder city,
      TerraCityRegionBuilder type,
      CityGrid grid,
      PlanarPoint core,
      List<UrbanPlot> plots,
      List<UrbanRoad> roads,
      int currentPlotArea,
      RandomGenerator random,
      EnumMap<CandidateRejectReason, Integer> rejects
  ) {
    ArrayList<Candidate> candidates = plots.isEmpty()
        ? primaryCandidates(type, core)
        : sampledFrontierCandidates(
            type, plots, city.roadWidthChunks(), city.candidateCount(), random
        );
    ArrayList<Candidate> valid = new ArrayList<>();
    filterCandidates(city, type, grid, plots, roads, currentPlotArea, candidates, valid, rejects);
    if (valid.isEmpty() && !plots.isEmpty() && plots.size() < city.minPlotCount()) {
      filterCandidates(
          city, type, grid, plots, roads, currentPlotArea,
          frontierCandidates(type, plots, city.roadWidthChunks()), valid, rejects
      );
    }
    if (valid.isEmpty()) return Optional.empty();
    Collections.shuffle(valid, new Random(random.nextLong()));
    if (valid.size() > city.candidateCount()) {
      valid.subList(city.candidateCount(), valid.size()).clear();
    }
    int minWeight = city.regions().stream().mapToInt(TerraCityRegionBuilder::weight).min().orElse(1);
    int maxWeight = city.regions().stream().mapToInt(TerraCityRegionBuilder::weight).max().orElse(1);
    return valid.stream().max(Comparator.comparingDouble(candidate ->
        score(candidate, type, minWeight, maxWeight, grid, core, plots)));
  }

  private static void filterCandidates(
      TerraCityBuilder city,
      TerraCityRegionBuilder type,
      CityGrid grid,
      List<UrbanPlot> plots,
      List<UrbanRoad> roads,
      int currentPlotArea,
      List<Candidate> candidates,
      List<Candidate> valid,
      EnumMap<CandidateRejectReason, Integer> rejects
  ) {
    for (Candidate candidate : candidates) {
      CandidateRejectReason reason = rejectReason(
          city, type, grid, plots, roads, currentPlotArea, candidate
      );
      if (reason == null) valid.add(candidate);
      else rejects.merge(reason, 1, Integer::sum);
    }
  }

  private static ArrayList<Candidate> primaryCandidates(
      TerraCityRegionBuilder type,
      PlanarPoint core
  ) {
    int coreX = (int) Math.floor(core.x() / 16.0);
    int coreZ = (int) Math.floor(core.z() / 16.0);
    ArrayList<Candidate> candidates = new ArrayList<>();
    for (PlotSize size : orientations(type.allowedSizes())) {
      for (int offsetZ = -size.lengthChunks() + 1; offsetZ <= 0; offsetZ++) {
        for (int offsetX = -size.widthChunks() + 1; offsetX <= 0; offsetX++) {
          candidates.add(new Candidate(new ChunkRectangle(
              coreX + offsetX, coreZ + offsetZ, size.widthChunks(), size.lengthChunks()
          ), -1, null, size.widthChunks() + size.lengthChunks()));
        }
      }
    }
    return candidates;
  }

  private static ArrayList<Candidate> frontierCandidates(
      TerraCityRegionBuilder type,
      List<UrbanPlot> plots,
      int roadWidth
  ) {
    ArrayList<Candidate> candidates = new ArrayList<>();
    for (UrbanPlot source : plots) {
      ChunkRectangle parent = source.chunkArea();
      for (PlotSize size : orientations(type.allowedSizes())) {
        for (int offset = -size.widthChunks() + 1; offset < parent.widthChunks(); offset++) {
          int x = parent.minChunkX() + offset;
          addNorthSouth(candidates, source.id(), parent, size, x, roadWidth, true);
          addNorthSouth(candidates, source.id(), parent, size, x, roadWidth, false);
        }
        for (int offset = -size.lengthChunks() + 1; offset < parent.lengthChunks(); offset++) {
          int z = parent.minChunkZ() + offset;
          addWestEast(candidates, source.id(), parent, size, z, roadWidth, true);
          addWestEast(candidates, source.id(), parent, size, z, roadWidth, false);
        }
      }
    }
    return candidates;
  }

  private static ArrayList<Candidate> sampledFrontierCandidates(
      TerraCityRegionBuilder type,
      List<UrbanPlot> plots,
      int roadWidth,
      int candidateCount,
      RandomGenerator random
  ) {
    List<PlotSize> sizes = orientations(type.allowedSizes());
    int target = Math.multiplyExact(candidateCount, 8);
    int maxAttempts = Math.multiplyExact(target, 16);
    LinkedHashSet<Candidate> candidates = new LinkedHashSet<>();
    for (int attempt = 0; attempt < maxAttempts && candidates.size() < target; attempt++) {
      UrbanPlot source = plots.get(random.nextInt(plots.size()));
      ChunkRectangle parent = source.chunkArea();
      PlotSize size = sizes.get(random.nextInt(sizes.size()));
      int direction = random.nextInt(4);
      ArrayList<Candidate> generated = new ArrayList<>(1);
      if (direction < 2) {
        int count = parent.widthChunks() + size.widthChunks() - 1;
        int minX = parent.minChunkX() - size.widthChunks() + 1 + random.nextInt(count);
        addNorthSouth(generated, source.id(), parent, size, minX, roadWidth, direction == 0);
      } else {
        int count = parent.lengthChunks() + size.lengthChunks() - 1;
        int minZ = parent.minChunkZ() - size.lengthChunks() + 1 + random.nextInt(count);
        addWestEast(generated, source.id(), parent, size, minZ, roadWidth, direction == 2);
      }
      candidates.addAll(generated);
    }
    return new ArrayList<>(candidates);
  }

  private static void addNorthSouth(
      List<Candidate> candidates, int parentId, ChunkRectangle parent, PlotSize size,
      int minX, int roadWidth, boolean north
  ) {
    int minZ = north
        ? parent.minChunkZ() - roadWidth - size.lengthChunks()
        : parent.maxChunkZExclusive() + roadWidth;
    ChunkRectangle area = new ChunkRectangle(minX, minZ, size.widthChunks(), size.lengthChunks());
    int overlapMin = Math.max(parent.minChunkX(), area.minChunkX());
    int overlapMax = Math.min(parent.maxChunkXExclusive(), area.maxChunkXExclusive());
    if (overlapMax <= overlapMin) return;
    ChunkRectangle road = new ChunkRectangle(
        overlapMin,
        north ? parent.minChunkZ() - roadWidth : parent.maxChunkZExclusive(),
        overlapMax - overlapMin,
        roadWidth
    );
    candidates.add(new Candidate(area, parentId, road, overlapMax - overlapMin));
  }

  private static void addWestEast(
      List<Candidate> candidates, int parentId, ChunkRectangle parent, PlotSize size,
      int minZ, int roadWidth, boolean west
  ) {
    int minX = west
        ? parent.minChunkX() - roadWidth - size.widthChunks()
        : parent.maxChunkXExclusive() + roadWidth;
    ChunkRectangle area = new ChunkRectangle(minX, minZ, size.widthChunks(), size.lengthChunks());
    int overlapMin = Math.max(parent.minChunkZ(), area.minChunkZ());
    int overlapMax = Math.min(parent.maxChunkZExclusive(), area.maxChunkZExclusive());
    if (overlapMax <= overlapMin) return;
    ChunkRectangle road = new ChunkRectangle(
        west ? parent.minChunkX() - roadWidth : parent.maxChunkXExclusive(),
        overlapMin,
        roadWidth,
        overlapMax - overlapMin
    );
    candidates.add(new Candidate(area, parentId, road, overlapMax - overlapMin));
  }

  private static CandidateRejectReason rejectReason(
      TerraCityBuilder city,
      TerraCityRegionBuilder type,
      CityGrid grid,
      List<UrbanPlot> plots,
      List<UrbanRoad> roads,
      int currentPlotArea,
      Candidate candidate
  ) {
    if (!grid.isUsable(candidate.area())) return CandidateRejectReason.OUTSIDE_CITY;
    if (!grid.isEmpty(candidate.area())) {
      for (UrbanPlot plot : plots) {
        if (plot.chunkArea().intersects(candidate.area())) return CandidateRejectReason.OVERLAPS_PLOT;
      }
      return CandidateRejectReason.OVERLAPS_ROAD;
    }
    for (UrbanPlot plot : plots) {
      if (candidate.area().intersects(plot.chunkArea().expand(city.roadWidthChunks()))) {
        return CandidateRejectReason.INVALID_ROAD_GAP;
      }
    }
    if (candidate.road() != null) {
      if (!grid.isUsable(candidate.road())) return CandidateRejectReason.OUTSIDE_CITY;
      for (UrbanPlot plot : plots) {
        if (candidate.road().intersects(plot.chunkArea()) && plot.id() != candidate.parentId()) {
          return CandidateRejectReason.OVERLAPS_PLOT;
        }
      }
      if (candidate.parentId() < 0) return CandidateRejectReason.NO_CONNECTION;
    }
    double nextCoverage = coverage(currentPlotArea + candidate.area().areaChunks(), grid.usableChunkArea());
    if (nextCoverage > city.maxPlotCoverage() + 1.0E-12) return CandidateRejectReason.COVERAGE_LIMIT;
    long count = plots.stream().filter(plot -> plot.type() == type).count();
    if (count >= type.maxCount()) return CandidateRejectReason.TYPE_MAX_COUNT;
    return null;
  }

  private static double score(
      Candidate candidate,
      TerraCityRegionBuilder type,
      int minWeight,
      int maxWeight,
      CityGrid grid,
      PlanarPoint core,
      List<UrbanPlot> plots
  ) {
    PlanarPoint center = candidate.area().centerBlocks();
    double maxRadius = Math.max(1.0, Math.hypot(
        grid.maxChunkXExclusive() - grid.minChunkX(),
        grid.maxChunkZExclusive() - grid.minChunkZ()
    ));
    double distance = Math.hypot(center.x() - core.x(), center.z() - core.z()) / 16.0;
    double normalizedWeight = maxWeight == minWeight ? 0.5
        : (type.weight() - minWeight) / (double) (maxWeight - minWeight);
    double desiredRadius = maxRadius * (1.0 - normalizedWeight) * 0.65;
    double centrality = 1.0 - Math.min(1.0, Math.abs(distance - desiredRadius) / maxRadius);
    double clearance = Math.min(1.0, grid.distanceToBoundaryBlocks(center.x(), center.z()) / (maxRadius * 16.0));
    double adjacency = candidate.interfaceLength() / (double) Math.max(
        candidate.area().widthChunks(), candidate.area().lengthChunks()
    );
    double compactness = compactness(candidate.area(), plots);
    return centrality * 4.0 + clearance * 2.0 + adjacency * 1.5 + compactness * 2.0;
  }

  private static double compactness(ChunkRectangle candidate, List<UrbanPlot> plots) {
    int minX = candidate.minChunkX();
    int minZ = candidate.minChunkZ();
    int maxX = candidate.maxChunkXExclusive();
    int maxZ = candidate.maxChunkZExclusive();
    int area = candidate.areaChunks();
    for (UrbanPlot plot : plots) {
      ChunkRectangle rectangle = plot.chunkArea();
      minX = Math.min(minX, rectangle.minChunkX());
      minZ = Math.min(minZ, rectangle.minChunkZ());
      maxX = Math.max(maxX, rectangle.maxChunkXExclusive());
      maxZ = Math.max(maxZ, rectangle.maxChunkZExclusive());
      area += rectangle.areaChunks();
    }
    return area / (double) ((maxX - minX) * (maxZ - minZ));
  }

  private static void accept(
      Candidate candidate,
      TerraCityRegionBuilder type,
      CityGrid grid,
      List<UrbanPlot> plots,
      List<UrbanRoad> roads,
      Map<TerraCityRegionBuilder, Integer> counts,
      int[] plotArea
  ) {
    int id = plots.size();
    UrbanPlot plot = new UrbanPlot(id, type, candidate.area());
    grid.occupy(candidate.area(), CityGrid.CellState.PLOT);
    plots.add(plot);
    counts.compute(type, (ignored, count) -> Objects.requireNonNull(count) + 1);
    plotArea[0] += candidate.area().areaChunks();
    if (candidate.road() != null) {
      grid.occupy(candidate.road(), CityGrid.CellState.ROAD);
      roads.add(new UrbanRoad(candidate.parentId(), id, candidate.road()));
    }
  }

  private static List<TerraCityRegionBuilder> weightedTypeOrder(
      List<TerraCityRegionBuilder> eligible,
      Map<TerraCityRegionBuilder, Integer> counts,
      RandomGenerator random
  ) {
    ArrayList<TerraCityRegionBuilder> remaining = new ArrayList<>(eligible);
    ArrayList<TerraCityRegionBuilder> order = new ArrayList<>(eligible.size());
    while (!remaining.isEmpty()) {
      long total = 0;
      for (TerraCityRegionBuilder type : remaining) {
        total += effectiveWeight(type, counts.get(type));
      }
      long cursor = random.nextLong(total);
      for (int index = 0; index < remaining.size(); index++) {
        TerraCityRegionBuilder type = remaining.get(index);
        long weight = effectiveWeight(type, counts.get(type));
        if (cursor < weight) {
          order.add(type);
          remaining.remove(index);
          break;
        }
        cursor -= weight;
      }
    }
    return order;
  }

  private static long effectiveWeight(TerraCityRegionBuilder type, int count) {
    long remaining = type.maxCount() == Integer.MAX_VALUE
        ? 1L
        : Math.max(1L, type.maxCount() - (long) count);
    return Math.max(1L, Math.min(Integer.MAX_VALUE, type.weight() * remaining));
  }

  private static List<PlotSize> orientations(List<PlotSize> sizes) {
    LinkedHashSet<PlotSize> oriented = new LinkedHashSet<>();
    for (PlotSize size : sizes) {
      oriented.add(size);
      oriented.add(size.rotated());
    }
    return List.copyOf(oriented);
  }

  private static double coverage(int plotArea, int usableArea) {
    return plotArea / (double) usableArea;
  }

  private static void snapshot(
      LayoutDebugCollector debug,
      String stage,
      PlanarPoint core,
      List<UrbanPlot> plots,
      List<UrbanRoad> roads,
      Map<CandidateRejectReason, Integer> rejects,
      double coverage
  ) {
    debug.accept(new LayoutDebugEvent(
        stage,
        core,
        plots.stream().map(UrbanPlot::chunkArea).toList(),
        roads,
        rejects,
        coverage
    ));
  }

  private record Candidate(
      ChunkRectangle area,
      int parentId,
      ChunkRectangle road,
      int interfaceLength
  ) {
  }
}
