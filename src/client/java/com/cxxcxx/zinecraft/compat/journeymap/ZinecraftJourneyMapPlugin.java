package com.cxxcxx.zinecraft.compat.journeymap;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.NationLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.TerraLayoutPlan;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.nation.TerraLayoutResource;
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
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 在 JourneyMap 中显示统一计算的 Nation、City 边界和名称，以及 Region 移动地块边界。
 */
@JourneyMapPlugin(apiVersion = "2.0.0")
public final class ZinecraftJourneyMapPlugin implements IClientPlugin {
  private static final int CITY_LABEL_MIN_ZOOM = 64;
  private static final int[] COLORS = {
      0x3973AC, 0xC98D3A, 0x6A4C93, 0x43AA8B, 0xE76F51,
      0x577590, 0xD4A373, 0xF2C14E, 0x8E5572, 0x2A9D8F,
      0x9B5DE5, 0xF15BB5, 0x00BBF9, 0x6A994E, 0xBC4749,
      0x5E548E, 0xF9844A, 0x277DA1, 0x90BE6D
  };

  private static void onMappingEvent(IClientAPI api, MappingEvent event) {
    if (event.getStage() == MappingEvent.Stage.MAPPING_STOPPED || !hasTerraDimension()) {
      api.removeAll(Zinecraft.MOD_ID);
      return;
    }
    try {
      TerraLayoutPlan plan = TerraLayoutResource.load();
      showNationBorders(api, plan);
      showCityBorders(api, plan);
      showRegionBorders(api, plan);
    } catch (RuntimeException exception) {
      Zinecraft.LOGGER.error("无法读取 JourneyMap 泰拉边界", exception);
    }
  }

  private static boolean hasTerraDimension() {
    var connection = Minecraft.getInstance().getConnection();
    return connection != null && connection.levels().contains(ModDimension.TERRA.levelKey());
  }

  private static void showNationBorders(IClientAPI api, TerraLayoutPlan plan) {
    String group = Component.translatable("journeymap.zinecraft.terra_nations").getString();
    int shown = 0;
    for (NationLayoutPlan nationPlan : plan.nations()) {
      NationBuilder nation = nationPlan.nation();
      String name = Component.translatable(nation.translationKey()).getString();
      int color = nationColor(nation);
      PolygonOverlay overlay = labeledOverlay(
          nationPlan.boundary(),
          shape(color, 2.0F, 0.95F, 0.0F),
          text(2.0F),
          group,
          name,
          200
      );
      if (show(api, overlay, "国家", nation.id())) shown++;
    }
    Zinecraft.LOGGER.info("已向 JourneyMap 注册 {} 个泰拉国家边界", shown);
  }

  private static void showCityBorders(IClientAPI api, TerraLayoutPlan plan) {
    String group = Component.translatable("journeymap.zinecraft.terra_cities").getString();
    int shown = 0;
    for (NationLayoutPlan nationPlan : plan.nations()) {
      int color = nationColor(nationPlan.nation());
      for (CityLayoutPlan cityPlan : nationPlan.cities()) {
        String name = Component.translatable(cityPlan.city().translationKey()).getString();
        PolygonOverlay overlay = labeledOverlay(
            cityPlan.boundary(),
            shape(color, 1.5F, 0.95F, 0.16F),
            text(1.15F).setMinZoom(CITY_LABEL_MIN_ZOOM),
            group,
            name,
            320
        );
        if (show(api, overlay, "城市", cityPlan.city().id())) shown++;
      }
    }
    Zinecraft.LOGGER.info("已向 JourneyMap 注册 {} 个泰拉城市边界", shown);
  }

  private static void showRegionBorders(IClientAPI api, TerraLayoutPlan plan) {
    String group = Component.translatable("journeymap.zinecraft.terra_regions").getString();
    int shown = 0;
    for (NationLayoutPlan nationPlan : plan.nations()) {
      int color = nationColor(nationPlan.nation());
      for (CityLayoutPlan cityPlan : nationPlan.cities()) {
        for (var regionCell : cityPlan.regions()) {
          String name = Component.translatable(regionCell.region().translationKey()).getString();
          PolygonOverlay overlay = overlay(
              regionCell.mobilePlotBounds().corners(),
              shape(color, 1.0F, 0.8F, 0.0F),
              group,
              name,
              420
          );
          if (show(api, overlay, "城区", regionCell.region().id() + "/" + regionCell.slot().index())) shown++;
        }
      }
    }
    Zinecraft.LOGGER.info("已向 JourneyMap 注册 {} 个泰拉城区矩形边界", shown);
  }

  private static PolygonOverlay labeledOverlay(
      List<PlanarPoint> boundary,
      ShapeProperties shape,
      TextProperties text,
      String group,
      String name,
      int displayOrder
  ) {
    PolygonOverlay overlay = overlay(boundary, shape, group, name, displayOrder);
    overlay.setLabel(name).setTextProperties(text);
    return overlay;
  }

  private static PolygonOverlay overlay(
      List<PlanarPoint> boundary,
      ShapeProperties shape,
      String group,
      String name,
      int displayOrder
  ) {
    PolygonOverlay overlay = new PolygonOverlay(
        Zinecraft.MOD_ID,
        ModDimension.TERRA.levelKey(),
        shape,
        new MapPolygon(blockPolygon(boundary))
    );
    overlay.setOverlayGroupName(group)
        .setTitle(name)
        .setDisplayOrder(displayOrder);
    return overlay;
  }

  private static ShapeProperties shape(
      int color,
      float strokeWidth,
      float strokeOpacity,
      float fillOpacity
  ) {
    return new ShapeProperties()
        .setStrokeColor(color)
        .setStrokeOpacity(strokeOpacity)
        .setStrokeWidth(strokeWidth)
        .setFillColor(color)
        .setFillOpacity(fillOpacity);
  }

  private static TextProperties text(float scale) {
    return new TextProperties()
        .setScale(scale)
        .setColor(0xFFFFFF)
        .setBackgroundColor(0x111820)
        .setBackgroundOpacity(0.65F);
  }

  private static int nationColor(NationBuilder nation) {
    int index = Zinecraft.NATIONS.entries().indexOf(nation);
    return COLORS[Math.floorMod(index, COLORS.length)];
  }

  private static List<BlockPos> blockPolygon(List<PlanarPoint> polygon) {
    ArrayList<BlockPos> result = new ArrayList<>();
    for (PlanarPoint point : polygon) {
      BlockPos position = new BlockPos((int) Math.round(point.x()), 64, (int) Math.round(point.z()));
      if (result.isEmpty() || !result.getLast().equals(position)) result.add(position);
    }
    if (result.size() > 1 && result.getFirst().equals(result.getLast())) result.removeLast();
    if (result.size() < 3) throw new IllegalStateException("JourneyMap 多边形边界少于三个方块点");
    return List.copyOf(result);
  }

  private static boolean show(IClientAPI api, PolygonOverlay overlay, String category, String id) {
    try {
      api.show(overlay);
      return true;
    } catch (Exception exception) {
      Zinecraft.LOGGER.error("无法向 JourneyMap 注册泰拉{}边界：{}", category, id, exception);
      return false;
    }
  }

  @Override
  public void initialize(IClientAPI api) {
    ClientEventRegistry.MAPPING_EVENT.subscribe(Zinecraft.MOD_ID, event -> onMappingEvent(api, event));
  }

  @Override
  public String getModId() {
    return Zinecraft.MOD_ID;
  }
}
