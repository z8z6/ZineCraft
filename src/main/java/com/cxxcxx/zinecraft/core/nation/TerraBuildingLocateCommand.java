package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.CityRegionBuildingSlot;
import com.cxxcxx.zinecraft.api.world.city.CityRegionCell;
import com.cxxcxx.zinecraft.api.world.city.NationLayoutPlan;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Comparator;
import java.util.Optional;

/** 定位 schema v9 中作为 mobile_plot 内部 Piece 生成的建筑 slot。 */
public final class TerraBuildingLocateCommand {
  private TerraBuildingLocateCommand() {
  }

  public static void register(RegisterCommandsEvent event) {
    event.getDispatcher().register(Commands.literal("zinecraft")
        .requires(source -> source.hasPermission(2))
        .then(Commands.literal("locate_building")
            .then(Commands.argument("building_id", StringArgumentType.word())
                .suggests((context, suggestions) -> SharedSuggestionProvider.suggest(
                    Zinecraft.STRUCTURES.buildings.stream()
                        .filter(building -> building.cityBuilding())
                        .map(building -> building.path),
                    suggestions
                ))
                .executes(context -> locate(
                    context.getSource(),
                    StringArgumentType.getString(context, "building_id")
                )))));
  }

  private static int locate(net.minecraft.commands.CommandSourceStack source, String rawBuildingId) {
    String buildingId = rawBuildingId.startsWith(Zinecraft.MOD_ID + ":")
        ? rawBuildingId.substring(Zinecraft.MOD_ID.length() + 1)
        : rawBuildingId;
    Vec3 origin = source.getPosition();
    Optional<LocatedSlot> nearest = TerraLayoutResource.load().nations().stream()
        .flatMap(nation -> nation.cities().stream()
            .flatMap(city -> city.regions().stream()
                .flatMap(region -> region.buildingSlots().stream()
                    .filter(slot -> slot.building().path.equals(buildingId))
                    .map(slot -> located(nation, city, region, slot, origin)))))
        .min(Comparator.comparingDouble(LocatedSlot::distanceSquared));
    if (nearest.isEmpty()) {
      source.sendFailure(Component.literal("泰拉布局中没有建筑 slot：" + buildingId));
      return 0;
    }

    LocatedSlot result = nearest.get();
    int x = Mth.floor(result.slot().center().x());
    int z = Mth.floor(result.slot().center().z());
    int distance = Mth.floor(Math.sqrt(result.distanceSquared()));
    source.sendSuccess(() -> Component.literal(
        "最近的 " + buildingId + " slot 位于 [" + x + ", " + z + "]"
            + "，距离 " + distance + " 方块；国家 " + result.nation().nation().id()
            + "，城市 " + result.city().city().id()
            + "，Region " + result.region().region().id()
            + "，slot " + result.slot().slot().index()
    ), false);
    return distance;
  }

  private static LocatedSlot located(
      NationLayoutPlan nation,
      CityLayoutPlan city,
      CityRegionCell region,
      CityRegionBuildingSlot slot,
      Vec3 origin
  ) {
    double dx = slot.center().x() - origin.x;
    double dz = slot.center().z() - origin.z;
    return new LocatedSlot(nation, city, region, slot, dx * dx + dz * dz);
  }

  private record LocatedSlot(
      NationLayoutPlan nation,
      CityLayoutPlan city,
      CityRegionCell region,
      CityRegionBuildingSlot slot,
      double distanceSquared
  ) {
  }
}
