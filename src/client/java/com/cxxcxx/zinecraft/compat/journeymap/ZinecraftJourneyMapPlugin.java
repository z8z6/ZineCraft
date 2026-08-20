package com.cxxcxx.zinecraft.compat.journeymap;

import com.cxxcxx.zinecraft.api.nation.TerraNation;
import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.nation.TerraGeography;
import com.cxxcxx.zinecraft.core.registry.ModDimension;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.client.display.PolygonOverlay;
import journeymap.api.v2.client.event.MappingEvent;
import journeymap.api.v2.client.model.MapPolygon;
import journeymap.api.v2.client.model.ShapeProperties;
import journeymap.api.v2.client.model.TextProperties;
import journeymap.api.v2.common.JourneyMapPlugin;
import journeymap.api.v2.common.event.ClientEventRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 在 JourneyMap 中显示与泰拉群系源一致的国家 Voronoi 边界。
 */
@JourneyMapPlugin(apiVersion = "2.0.0")
public final class ZinecraftJourneyMapPlugin implements IClientPlugin {
  private static final int[] COLORS = {
      0x3973AC, 0xC98D3A, 0x6A4C93, 0x43AA8B, 0xE76F51,
      0x577590, 0xD4A373, 0xF2C14E, 0x8E5572, 0x2A9D8F,
      0x9B5DE5, 0xF15BB5, 0x00BBF9, 0x6A994E, 0xBC4749,
      0x5E548E, 0xF9844A, 0x277DA1, 0x90BE6D
  };

  private static void onMappingEvent(IClientAPI api, MappingEvent event) {
    if (event.getStage() == MappingEvent.Stage.MAPPING_STOPPED) {
      api.removeAll(Zinecraft.MOD_ID);
      return;
    }
    showNationBorders(api);
    showPlaces(api);
  }

  private static void showNationBorders(IClientAPI api) {
    String group = Component.translatable("journeymap.zinecraft.terra_nations").getString();
    List<ModDimension.MapSite> sites = ModDimension.TERRA_MAP;
    int colorIndex = 0;
    for (ModDimension.MapSite site : sites) {
      TerraNation nation = site.nation();
      if (nation == null) {
        continue;
      }

      String name = Component.translatable("journeymap.zinecraft.nation." + nation.getId()).getString();
      ShapeProperties shape = new ShapeProperties()
          .setStrokeColor(COLORS[colorIndex % COLORS.length])
          .setStrokeOpacity(0.95F)
          .setStrokeWidth(2.0F)
          .setFillColor(COLORS[colorIndex % COLORS.length])
          .setFillOpacity(0.12F);
      TextProperties text = new TextProperties()
          .setScale(2.0F)
          .setColor(0xFFFFFF)
          .setBackgroundColor(0x111820)
          .setBackgroundOpacity(0.65F);
      PolygonOverlay overlay = new PolygonOverlay(
          Zinecraft.MOD_ID,
          ModDimension.TERRA.levelKey(),
          shape,
          new MapPolygon(countryPolygon(site, sites))
      );
      overlay.setOverlayGroupName(group)
          .setTitle(name)
          .setLabel(name)
          .setTextProperties(text)
          .setDisplayOrder(200);
      try {
        api.show(overlay);
      } catch (Exception exception) {
        Zinecraft.LOGGER.error("无法向 JourneyMap 注册泰拉国家边界：{}", nation.getId(), exception);
      }
      colorIndex++;
    }
    Zinecraft.LOGGER.info("已向 JourneyMap 注册 {} 个泰拉国家边界", colorIndex);
  }

  private static void showPlaces(IClientAPI api) {
    String cityGroup = Component.translatable("journeymap.zinecraft.terra_cities").getString();
    String regionGroup = Component.translatable("journeymap.zinecraft.terra_regions").getString();
    Map<TerraNation, ModDimension.MapSite> nationSites = new EnumMap<>(TerraNation.class);
    for (ModDimension.MapSite site : ModDimension.TERRA_MAP) {
      if (site.nation() != null) nationSites.put(site.nation(), site);
    }

    int shown = 0;
    for (TerraPlace place : TerraGeography.PLACES) {
      ModDimension.MapSite nationSite = nationSites.get(place.nation());
      if (nationSite == null) continue;
      int nationColor = COLORS[place.nation().ordinal() % COLORS.length];
      boolean urban = place.type().isUrban();
      ShapeProperties shape = new ShapeProperties()
          .setStrokeColor(urban ? nationColor : darken(nationColor))
          .setStrokeOpacity(urban ? 0.9F : 0.72F)
          .setStrokeWidth(urban ? 1.5F : 1.0F)
          .setFillColor(nationColor)
          .setFillOpacity(urban ? 0.2F : 0.08F);
      TextProperties text = new TextProperties()
          .setScale(urban ? 1.15F : 0.9F)
          .setColor(0xFFFFFF)
          .setBackgroundColor(urban ? 0x171717 : 0x25313A)
          .setBackgroundOpacity(0.62F);
      List<BlockPos> boundary = placePolygon(place, nationSite, ModDimension.TERRA_MAP);
      if (boundary.size() < 3) {
        Zinecraft.LOGGER.warn("跳过无有效边界的泰拉地点：{}", place.id());
        continue;
      }
      String name = Component.translatable(place.translationKey()).getString();
      PolygonOverlay overlay = new PolygonOverlay(
          Zinecraft.MOD_ID,
          ModDimension.TERRA.levelKey(),
          shape,
          new MapPolygon(boundary)
      );
      overlay.setOverlayGroupName(urban ? cityGroup : regionGroup)
          .setTitle(name)
          .setLabel(name)
          .setTextProperties(text)
          .setDisplayOrder(urban ? 320 : 260);
      try {
        api.show(overlay);
        shown++;
      } catch (Exception exception) {
        Zinecraft.LOGGER.error("无法向 JourneyMap 注册泰拉地点：{}", place.id(), exception);
      }
    }
    Zinecraft.LOGGER.info("已向 JourneyMap 注册 {} 个泰拉城市与地区边界", shown);
  }

  private static List<BlockPos> placePolygon(
      TerraPlace place,
      ModDimension.MapSite nationSite,
      List<ModDimension.MapSite> sites
  ) {
    List<Point> polygon = new ArrayList<>();
    int vertices = place.type().isUrban() ? 16 : 24;
    for (int index = 0; index < vertices; index++) {
      double angle = Math.PI * 2.0 * index / vertices;
      polygon.add(new Point(
          place.x() + Math.cos(angle) * place.radiusX(),
          place.z() + Math.sin(angle) * place.radiusZ()
      ));
    }
    for (ModDimension.MapSite other : sites) {
      if (other != nationSite) polygon = clipCloserToSite(polygon, nationSite, other);
    }
    return blockPolygon(polygon);
  }

  /**
   * 将陆地区域依次裁剪到所有“离当前国家锚点更近”的半平面。
   */
  private static List<BlockPos> countryPolygon(
      ModDimension.MapSite site,
      List<ModDimension.MapSite> sites
  ) {
    double edge = ModDimension.TERRA_MAP_SIZE / 2.0 - ModDimension.OUTER_OCEAN_RING_WIDTH;
    List<Point> polygon = new ArrayList<>(List.of(
        new Point(-edge, edge),
        new Point(edge, edge),
        new Point(edge, -edge),
        new Point(-edge, -edge)
    ));
    for (ModDimension.MapSite other : sites) {
      if (other == site) {
        continue;
      }
      polygon = clipCloserToSite(polygon, site, other);
    }
    return blockPolygon(polygon);
  }

  private static List<BlockPos> blockPolygon(List<Point> polygon) {
    List<BlockPos> result = new ArrayList<>();
    for (Point point : polygon) {
      BlockPos pos = new BlockPos((int) Math.round(point.x()), 64, (int) Math.round(point.z()));
      if (result.isEmpty() || !result.getLast().equals(pos)) {
        result.add(pos);
      }
    }
    if (result.size() > 1 && result.getFirst().equals(result.getLast())) {
      result.removeLast();
    }
    return result;
  }

  private static int darken(int color) {
    int red = (color >> 16 & 0xFF) * 3 / 4;
    int green = (color >> 8 & 0xFF) * 3 / 4;
    int blue = (color & 0xFF) * 3 / 4;
    return red << 16 | green << 8 | blue;
  }

  private static List<Point> clipCloserToSite(
      List<Point> input,
      ModDimension.MapSite site,
      ModDimension.MapSite other
  ) {
    if (input.isEmpty()) {
      return input;
    }
    double a = 2.0 * (other.x() - site.x());
    double b = 2.0 * (other.z() - site.z());
    double limit = (double) other.x() * other.x() + (double) other.z() * other.z()
        - (double) site.x() * site.x() - (double) site.z() * site.z();
    List<Point> output = new ArrayList<>();
    Point previous = input.getLast();
    boolean previousInside = inside(previous, a, b, limit);
    for (Point current : input) {
      boolean currentInside = inside(current, a, b, limit);
      if (currentInside != previousInside) {
        output.add(intersection(previous, current, a, b, limit));
      }
      if (currentInside) {
        output.add(current);
      }
      previous = current;
      previousInside = currentInside;
    }
    return output;
  }

  private static boolean inside(Point point, double a, double b, double limit) {
    return a * point.x() + b * point.z() <= limit + 0.0001;
  }

  private static Point intersection(Point start, Point end, double a, double b, double limit) {
    double dx = end.x() - start.x();
    double dz = end.z() - start.z();
    double denominator = a * dx + b * dz;
    double t = (limit - a * start.x() - b * start.z()) / denominator;
    return new Point(start.x() + t * dx, start.z() + t * dz);
  }

  @Override
  public void initialize(IClientAPI api) {
    ClientEventRegistry.MAPPING_EVENT.subscribe(Zinecraft.MOD_ID, event -> onMappingEvent(api, event));
  }

  @Override
  public String getModId() {
    return Zinecraft.MOD_ID;
  }

  private record Point(double x, double z) {
  }
}
