package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.world.city.ChunkRectangle;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout;
import net.minecraft.core.Direction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class RegionLayoutValidatorTest {
  private static final ChunkRectangle REGION = new ChunkRectangle(0, 0, 3, 3);

  @Test
  void acceptsFourAlignedLayersAndMultipleRoadFaces() {
    RegionLayout layout = layout(false);
    assertDoesNotThrow(() -> RegionLayoutValidator.validate(REGION, layout));
    assertEquals(4, layout.mobileLayers().size());
    assertEquals(4, layout.mobileLayers().getFirst().stairChunks().size());
    assertEquals(2, layout.parcels().getFirst().roadConnections().size());
  }

  @Test
  void rejectsDeclaredConnectionThatDoesNotTouchParcel() {
    RegionLayout layout = layout(true);
    assertThrows(IllegalArgumentException.class, () -> RegionLayoutValidator.validate(REGION, layout));
  }

  @Test
  void regionRoadsMustRemainOneChunkWide() {
    assertEquals(1, RegionLayout.RoadConfig.DEFAULT.primaryWidthChunks());
    assertThrows(IllegalArgumentException.class, () -> new RegionLayout.RoadConfig(
        2, 1, 1, List.of(3, 4), 0.2, 16
    ));
  }

  @Test
  void resolvesRoadJunctionTypeAndConnections() {
    RegionLayout layout = layout(false);
    RegionLayout.RoadTilePlan center = layout.roadTile(RegionLayout.MobileLayer.SURFACE, 1, 1);
    assertEquals(RegionLayout.RoadTileType.CROSS, center.type());
    assertEquals(4, center.connections().size());

    RegionLayout.RoadTilePlan end = layout.roadTile(RegionLayout.MobileLayer.SURFACE, 1, 0);
    assertEquals(RegionLayout.RoadTileType.END, end.type());
    assertEquals(List.of(Direction.SOUTH), end.connections());
  }

  private static RegionLayout layout(boolean invalidConnection) {
    RegionLayout.MobileLayerPlan surface = layer(RegionLayout.MobileLayer.SURFACE, invalidConnection);
    List<RegionLayout.MobileLayerPlan> layers = List.of(
        layer(RegionLayout.MobileLayer.POWER, false),
        layer(RegionLayout.MobileLayer.SUPPORT, false),
        layer(RegionLayout.MobileLayer.LIFE, false),
        surface
    );
    return new RegionLayout(
        RegionLayout.RegionLayoutType.GRID, new RegionLayout.ChunkPoint(1, 1), List.of(),
        surface.roadGraph(), layers, surface.urbanBlocks(), surface.parcels(), List.of(),
        5.0 / 9.0, 4.0 / 9.0, List.of("test")
    );
  }

  private static RegionLayout.MobileLayerPlan layer(
      RegionLayout.MobileLayer layer,
      boolean invalidConnection
  ) {
    RegionLayout.RoadGraph graph = graph();
    ArrayList<RegionLayout.UrbanBlock> blocks = new ArrayList<>();
    ArrayList<RegionLayout.BuildingParcel> parcels = new ArrayList<>();
    add(blocks, parcels, 0, 0, Direction.EAST, 0, Direction.SOUTH, 1);
    add(blocks, parcels, 2, 0, Direction.WEST, 0, Direction.SOUTH, 1);
    add(blocks, parcels, 0, 2, Direction.EAST, 0, Direction.NORTH, 1);
    add(blocks, parcels, 2, 2, Direction.WEST, 0, Direction.NORTH, 1);
    if (invalidConnection) {
      RegionLayout.BuildingParcel original = parcels.getFirst();
      parcels.set(0, new RegionLayout.BuildingParcel(
          original.id(), original.urbanBlockId(), original.area(), original.buildableArea(),
          Direction.NORTH, 0, RegionLayout.RoadClass.SERVICE,
          List.of(new RegionLayout.BuildingRoadConnection(
              Direction.NORTH, 0, RegionLayout.RoadClass.SERVICE
          ))
      ));
    }
    String buildingId = switch (layer) {
      case POWER -> "mobile_plot_power_layer";
      case SUPPORT -> "mobile_plot_support_layer";
      case LIFE -> "mobile_plot_life_layer";
      case SURFACE -> "surface_buildings";
    };
    RegionLayout.RegionLayoutType layoutType = switch (layer) {
      case POWER, SURFACE -> RegionLayout.RegionLayoutType.GRID;
      case SUPPORT -> RegionLayout.RegionLayoutType.CONCENTRIC;
      case LIFE -> RegionLayout.RegionLayoutType.RADIAL_GRID;
    };
    return new RegionLayout.MobileLayerPlan(
        layer, layoutType, buildingId, REGION, graph, blocks, parcels, List.of(),
        5.0 / 9.0, 4.0 / 9.0, List.of(
            new RegionLayout.ChunkPoint(1, 0),
            new RegionLayout.ChunkPoint(0, 1),
            new RegionLayout.ChunkPoint(2, 1),
            new RegionLayout.ChunkPoint(1, 2)
        )
    );
  }

  private static RegionLayout.RoadGraph graph() {
    return new RegionLayout.RoadGraph(
        List.of(
            new RegionLayout.RoadNode(0, new RegionLayout.ChunkPoint(1, 0), RegionLayout.RoadNodeType.INTERSECTION),
            new RegionLayout.RoadNode(1, new RegionLayout.ChunkPoint(1, 2), RegionLayout.RoadNodeType.INTERSECTION),
            new RegionLayout.RoadNode(2, new RegionLayout.ChunkPoint(0, 1), RegionLayout.RoadNodeType.INTERSECTION),
            new RegionLayout.RoadNode(3, new RegionLayout.ChunkPoint(2, 1), RegionLayout.RoadNodeType.INTERSECTION)
        ),
        List.of(
            new RegionLayout.RoadEdge(0, 0, 1, RegionLayout.RoadClass.SERVICE, 1,
                new ChunkRectangle(1, 0, 1, 3)),
            new RegionLayout.RoadEdge(1, 2, 3, RegionLayout.RoadClass.SERVICE, 1,
                new ChunkRectangle(0, 1, 3, 1))
        )
    );
  }

  private static void add(
      List<RegionLayout.UrbanBlock> blocks,
      List<RegionLayout.BuildingParcel> parcels,
      int x,
      int z,
      Direction primaryFace,
      int primaryRoad,
      Direction secondaryFace,
      int secondaryRoad
  ) {
    int id = parcels.size();
    ChunkRectangle area = new ChunkRectangle(x, z, 1, 1);
    blocks.add(new RegionLayout.UrbanBlock(id, 1, area));
    parcels.add(new RegionLayout.BuildingParcel(
        id, id, area, area, primaryFace, primaryRoad, RegionLayout.RoadClass.SERVICE,
        List.of(
            new RegionLayout.BuildingRoadConnection(primaryFace, primaryRoad, RegionLayout.RoadClass.SERVICE),
            new RegionLayout.BuildingRoadConnection(secondaryFace, secondaryRoad, RegionLayout.RoadClass.SERVICE)
        )
    ));
  }
}
