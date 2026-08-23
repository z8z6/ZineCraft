package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.city.*;

import java.util.*;

/** 对最终布局重新执行全部硬约束检查，不依赖生成过程的正确性。 */
public final class LayoutValidator {
  public Optional<String> validate(TerraCityBuilder city, CityGrid grid, MobileCityLayout layout) {
    List<UrbanPlot> plots = layout.plots();
    if (plots.size() < city.minPlotCount() || plots.size() > city.maxPlotCount()) {
      return Optional.of("移动地块总数越界：" + city.id());
    }
    if (layout.coverage() > city.maxPlotCoverage() + 1.0E-12) {
      return Optional.of("移动地块覆盖率越界：" + city.id());
    }
    HashMap<TerraCityRegionBuilder, Integer> counts = new HashMap<>();
    for (int index = 0; index < plots.size(); index++) {
      UrbanPlot plot = plots.get(index);
      if (plot.id() != index) return Optional.of("移动地块 ID 不连续：" + city.id());
      if (!grid.isUsable(plot.chunkArea())) return Optional.of("移动地块超出城市边界：" + plot.id());
      if (!plot.type().allowedSizes().contains(new PlotSize(
          plot.chunkArea().widthChunks(), plot.chunkArea().lengthChunks()
      )) && !plot.type().allowedSizes().contains(new PlotSize(
          plot.chunkArea().lengthChunks(), plot.chunkArea().widthChunks()
      ))) {
        return Optional.of("移动地块尺寸不在离散集合：" + plot.id());
      }
      counts.merge(plot.type(), 1, Integer::sum);
      for (int otherIndex = index + 1; otherIndex < plots.size(); otherIndex++) {
        UrbanPlot other = plots.get(otherIndex);
        if (plot.chunkArea().intersects(other.chunkArea())) {
          return Optional.of("移动地块重叠：" + plot.id() + "/" + other.id());
        }
        if (other.chunkArea().intersects(plot.chunkArea().expand(city.roadWidthChunks()))) {
          return Optional.of("移动地块道路间距不足：" + plot.id() + "/" + other.id());
        }
      }
    }
    for (TerraCityRegionBuilder type : city.regions()) {
      int count = counts.getOrDefault(type, 0);
      if (count < type.minCount() || count > type.maxCount()) {
        return Optional.of("Region 类型数量越界：" + type.id());
      }
    }
    if (plots.size() > 1 && layout.roads().size() < plots.size() - 1) {
      return Optional.of("道路数量不足以连接全部移动地块：" + city.id());
    }
    ArrayList<Set<Integer>> graph = new ArrayList<>(plots.size());
    for (int index = 0; index < plots.size(); index++) graph.add(new HashSet<>());
    for (UrbanRoad road : layout.roads()) {
      if (road.fromPlotId() >= plots.size() || road.toPlotId() >= plots.size()) {
        return Optional.of("道路引用未知移动地块：" + city.id());
      }
      if (!grid.isUsable(road.chunkArea())) return Optional.of("道路超出城市边界：" + city.id());
      for (UrbanPlot plot : plots) {
        if (plot.id() != road.fromPlotId() && plot.id() != road.toPlotId()
            && plot.chunkArea().intersects(road.chunkArea())) {
          return Optional.of("道路非法穿过移动地块：" + plot.id());
        }
      }
      graph.get(road.fromPlotId()).add(road.toPlotId());
      graph.get(road.toPlotId()).add(road.fromPlotId());
    }
    if (!plots.isEmpty()) {
      HashSet<Integer> reached = new HashSet<>();
      ArrayDeque<Integer> frontier = new ArrayDeque<>();
      frontier.add(0);
      while (!frontier.isEmpty()) {
        int current = frontier.removeFirst();
        if (!reached.add(current)) continue;
        frontier.addAll(graph.get(current));
      }
      if (reached.size() != plots.size()) return Optional.of("移动地块道路图不连通：" + city.id());
    }
    return Optional.empty();
  }
}
