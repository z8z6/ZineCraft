package com.cxxcxx.zinecraft.core.worldgen.city;

import com.cxxcxx.zinecraft.api.world.city.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认城市规划器：先保留全部地标，再由城市自己的布局策略生成普通建筑地块。
 */
public final class DefaultCityPlanner implements CityPlanner {
  private final CityBuildingSelector buildingSelector = new CityBuildingSelector();

  @Override
  public CityPlan plan(Context context) {
    var definition = context.definition();
    long citySeed = CityPlanningSeeds.citySeed(context.worldSeed(), definition.place(), definition.salt());
    List<CityBuildingLot> lots = definition.layout().createBuildingLots(new CityLayout.Context(
        definition.planningBounds(),
        definition.landmarks().stream().map(landmark -> landmark.reservedArea()).toList(),
        definition.districts(),
        CityRoadClass.LOCAL,
        context.terrain()
    ));
    List<CityBuildingPlacement> buildings = new ArrayList<>();
    for (CityBuildingLot lot : lots) {
      buildingSelector.select(citySeed, lot, definition.buildings()).ifPresent(buildings::add);
    }
    List<CityPlan.CityBlock> blocks = definition.districts().stream()
        .map(district -> new CityPlan.CityBlock(district.id(), district.bounds(), district.type()))
        .toList();
    return new CityPlan(
        definition.id(),
        citySeed,
        definition.landmarks().stream().map(CityPlan.LandmarkPlacement::new).toList(),
        List.of(),
        definition.districts().stream().map(CityPlan.PlannedDistrict::new).toList(),
        blocks,
        lots,
        buildings
    );
  }
}
