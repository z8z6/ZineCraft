package com.cxxcxx.zinecraft.api.world.structure;

import com.cxxcxx.zinecraft.api.world.city.CityRegionBuildingSlot;
import com.cxxcxx.zinecraft.api.world.city.CityRegionCell;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout;
import com.cxxcxx.zinecraft.core.nation.TerraLayoutResource;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 消费分国压缩布局资源：逐层展开道路、建筑，并用同坐标楼梯贯通四层。
 */
public final class MobilePlotStructure extends Structure {
  public static final int LAYER_HEIGHT = 16;
  public static final int MOBILE_PLOT_HEIGHT = LAYER_HEIGHT * 3;
  public static final int FLOOR_COUNT = 4;
  @NotNull
  public static final Access ACCESS = new Access();
  @NotNull
  private static final MapCodec<MobilePlotStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
      settingsCodec(instance),
      LayerDefinition.CODEC.listOf().fieldOf("layer_tiles")
          .forGetter(structure -> structure.layerTiles),
      LayerDefinition.CODEC.fieldOf("stair_tile")
          .forGetter(structure -> structure.stairTile),
      RoadDefinition.CODEC.listOf().fieldOf("road_tiles")
          .forGetter(structure -> structure.roadTiles),
      BuildingDefinition.CODEC.listOf().fieldOf("buildings")
          .forGetter(structure -> structure.buildings)
  ).apply(instance, MobilePlotStructure::new));
  private static Supplier<StructureType<MobilePlotStructure>> type;

  private final List<LayerDefinition> layerTiles;
  private final LayerDefinition stairTile;
  private final Map<String, LayerDefinition> layerTilesById;
  private final List<RoadDefinition> roadTiles;
  private final Map<String, RoadDefinition> roadTilesById;
  private final List<BuildingDefinition> buildings;
  private final Map<String, BuildingDefinition> buildingsById;

  public MobilePlotStructure(
      StructureSettings settings,
      List<LayerDefinition> layerTiles,
      LayerDefinition stairTile,
      List<RoadDefinition> roadTiles,
      List<BuildingDefinition> buildings
  ) {
    super(settings);
    this.layerTiles = List.copyOf(Objects.requireNonNull(layerTiles, "移动地块分层模板池不能为空"));
    LinkedHashMap<String, LayerDefinition> layersById = new LinkedHashMap<>();
    for (LayerDefinition layer : this.layerTiles) {
      if (layersById.putIfAbsent(layer.id(), layer) != null) {
        throw new IllegalArgumentException("移动地块层级 ID 重复：" + layer.id());
      }
    }
    Map<String, Integer> requiredLayers = Map.of("power", 0, "support", 16, "life", 32);
    for (var required : requiredLayers.entrySet()) {
      LayerDefinition layer = layersById.get(required.getKey());
      if (layer == null || layer.yOffset() != required.getValue()) {
        throw new IllegalArgumentException("移动地块缺少层级或高度错误：" + required.getKey());
      }
    }
    this.layerTilesById = Map.copyOf(layersById);
    this.stairTile = Objects.requireNonNull(stairTile, "移动地块楼梯模板池不能为空");
    this.roadTiles = List.copyOf(Objects.requireNonNull(roadTiles, "道路构件模板池不能为空"));
    LinkedHashMap<String, RoadDefinition> roadById = new LinkedHashMap<>();
    for (RoadDefinition roadTile : roadTiles) {
      if (roadById.putIfAbsent(roadTile.id(), roadTile) != null) {
        throw new IllegalArgumentException("道路构件 ID 重复：" + roadTile.id());
      }
    }
    for (String required : List.of("isolated", "end", "straight", "corner", "tee", "cross")) {
      if (!roadById.containsKey(required)) throw new IllegalArgumentException("缺少道路构件：" + required);
    }
    this.roadTilesById = Map.copyOf(roadById);
    this.buildings = List.copyOf(Objects.requireNonNull(buildings, "候选建筑定义不能为空"));
    LinkedHashMap<String, BuildingDefinition> byId = new LinkedHashMap<>();
    for (BuildingDefinition building : buildings) {
      if (byId.putIfAbsent(building.id(), building) != null) {
        throw new IllegalArgumentException("移动地块候选建筑 ID 重复：" + building.id());
      }
    }
    this.buildingsById = Map.copyOf(byId);
  }

  @Override
  protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
    ChunkPos chunk = context.chunkPos();
    Optional<TerraLayoutResource.MobilePlotTerrain> optionalTerrain =
        TerraLayoutResource.mobilePlotTerrain(chunk.x, chunk.z);
    if (optionalTerrain.isEmpty()) return Optional.empty();

    TerraLayoutResource.MobilePlotTerrain terrain = optionalTerrain.get();
    CityRegionCell region = terrain.region();
    int baseY = terrain.profile().groundY() + 1;
    List<CityRegionBuildingSlot> chunkSlots = region.buildingSlots().stream()
        .filter(slot -> slot.chunkArea().minChunkX() == chunk.x
            && slot.chunkArea().minChunkZ() == chunk.z)
        .toList();

    return Optional.of(new GenerationStub(
        new BlockPos(chunk.getMiddleBlockX(), baseY, chunk.getMiddleBlockZ()),
        pieces -> {
          for (RegionLayout.MobileLayerPlan layer : region.regionLayout().mobileLayers()) {
            int layerY = baseY + layer.layer().ordinal() * LAYER_HEIGHT;
            if (layer.stairChunks().stream().anyMatch(stair ->
                stair.chunkX() == chunk.x && stair.chunkZ() == chunk.z)) {
              addTile(context, pieces, stairTile.pool(), chunk, layerY, Rotation.NONE);
            } else if (region.regionLayout().isRoad(layer.layer(), chunk.x, chunk.z)) {
              addRoadSurface(context, pieces, region, layer.layer(), chunk, layerY);
            } else if (layer.layer() != RegionLayout.MobileLayer.SURFACE) {
              LayerDefinition definition = Objects.requireNonNull(
                  layerTilesById.get(layer.layer().name().toLowerCase(java.util.Locale.ROOT))
              );
              addTile(context, pieces, definition.pool(), chunk, layerY, Rotation.NONE);
            }
          }
          for (CityRegionBuildingSlot slot : chunkSlots) {
            addBuilding(context, pieces, slot, baseY + MOBILE_PLOT_HEIGHT);
          }
        }
    ));
  }

  private void addRoadSurface(
      GenerationContext context,
      StructurePiecesBuilder pieces,
      CityRegionCell region,
      RegionLayout.MobileLayer layer,
      ChunkPos chunk,
      int surfaceY
  ) {
    RegionLayout.RoadTilePlan selection = region.regionLayout().roadTile(layer, chunk.x, chunk.z);
    RoadDefinition definition = Objects.requireNonNull(roadTilesById.get(selection.type().id()));
    StructurePoolElement element = definition.pool().value().getRandomTemplate(context.random());
    if (element == EmptyPoolElement.INSTANCE) return;
    BlockPos origin = rotatedOrigin(
        new com.cxxcxx.zinecraft.api.world.city.ChunkRectangle(chunk.x, chunk.z, 1, 1),
        surfaceY,
        selection.rotation()
    );
    BoundingBox box = element.getBoundingBox(context.structureTemplateManager(), origin, selection.rotation());
    pieces.addPiece(new PoolElementStructurePiece(
        context.structureTemplateManager(), element, origin, element.getGroundLevelDelta(),
        selection.rotation(), box, LiquidSettings.IGNORE_WATERLOGGING
    ));
  }

  private static void addTile(
      GenerationContext context,
      StructurePiecesBuilder pieces,
      Holder<StructureTemplatePool> pool,
      ChunkPos chunk,
      int y,
      Rotation rotation
  ) {
    StructurePoolElement element = pool.value().getRandomTemplate(context.random());
    if (element == EmptyPoolElement.INSTANCE) return;
    BlockPos origin = new BlockPos(chunk.getMinBlockX(), y, chunk.getMinBlockZ());
    BoundingBox box = element.getBoundingBox(context.structureTemplateManager(), origin, rotation);
    pieces.addPiece(new PoolElementStructurePiece(
        context.structureTemplateManager(), element, origin, element.getGroundLevelDelta(),
        rotation, box, LiquidSettings.IGNORE_WATERLOGGING
    ));
  }

  private void addBuilding(
      GenerationContext context,
      StructurePiecesBuilder pieces,
      CityRegionBuildingSlot slot,
      int buildingY
  ) {
    BuildingDefinition definition = buildingsById.get(slot.building().path);
    if (definition == null) {
      throw new IllegalStateException("移动地块缺少候选建筑定义：" + slot.building().path);
    }
    int expectedChunksX = CityRegionBuildingSlot.rotatedFootprintChunksX(
        definition.footprintChunksX(), definition.footprintChunksZ(), slot.rotation()
    );
    int expectedChunksZ = CityRegionBuildingSlot.rotatedFootprintChunksZ(
        definition.footprintChunksX(), definition.footprintChunksZ(), slot.rotation()
    );
    if (expectedChunksX != slot.chunkArea().widthChunks()
        || expectedChunksZ != slot.chunkArea().lengthChunks()) {
      throw new IllegalStateException(
          "建筑运行时尺寸与布局注册不一致：" + definition.id()
              + "，注册=" + definition.footprintChunksX() + "x" + definition.footprintChunksZ()
              + "，旋转=" + slot.rotation()
              + "，世界占地=" + slot.chunkArea().widthChunks() + "x" + slot.chunkArea().lengthChunks()
      );
    }
    BlockPos origin = rotatedOrigin(slot.chunkArea(), buildingY, slot.rotation());
    StructurePoolElement element = definition.startPool().value().getRandomTemplate(context.random());
    if (element == EmptyPoolElement.INSTANCE) return;
    BoundingBox box = element.getBoundingBox(
        context.structureTemplateManager(), origin, slot.rotation()
    );
    int maxXExclusive = slot.chunkArea().maxChunkXExclusive() * 16;
    int maxZExclusive = slot.chunkArea().maxChunkZExclusive() * 16;
    int minX = slot.chunkArea().minChunkX() * 16;
    int minZ = slot.chunkArea().minChunkZ() * 16;
    if (box.minX() < minX || box.maxX() >= maxXExclusive
        || box.minZ() < minZ || box.maxZ() >= maxZExclusive) {
      throw new IllegalStateException("建筑模板越出注册 Chunk 占地：" + definition.id());
    }
    pieces.addPiece(new PoolElementStructurePiece(
        context.structureTemplateManager(),
        element,
        origin,
        element.getGroundLevelDelta(),
        slot.rotation(),
        box,
        LiquidSettings.IGNORE_WATERLOGGING
    ));
  }

  private static BlockPos rotatedOrigin(
      com.cxxcxx.zinecraft.api.world.city.ChunkRectangle area,
      int y,
      Rotation rotation
  ) {
    int minX = area.minChunkX() * 16;
    int minZ = area.minChunkZ() * 16;
    int maxX = area.maxChunkXExclusive() * 16 - 1;
    int maxZ = area.maxChunkZExclusive() * 16 - 1;
    return switch (rotation) {
      case NONE -> new BlockPos(minX, y, minZ);
      case CLOCKWISE_90 -> new BlockPos(maxX, y, minZ);
      case CLOCKWISE_180 -> new BlockPos(maxX, y, maxZ);
      case COUNTERCLOCKWISE_90 -> new BlockPos(minX, y, maxZ);
    };
  }

  @Override
  @NotNull
  public StructureType<?> type() {
    return Objects.requireNonNull(type, "Mobile-plot structure type is not registered").get();
  }

  /** Jigsaw 候选建筑展开所需的最小运行时参数。 */
  public record LayerDefinition(String id, Holder<StructureTemplatePool> pool, int yOffset) {
    public static final Codec<LayerDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(LayerDefinition::id),
        StructureTemplatePool.CODEC.fieldOf("pool").forGetter(LayerDefinition::pool),
        Codec.intRange(0, 48).fieldOf("y_offset").forGetter(LayerDefinition::yOffset)
    ).apply(instance, LayerDefinition::new));

    public LayerDefinition {
      Objects.requireNonNull(id, "移动地块层级 ID 不能为空");
      Objects.requireNonNull(pool, "移动地块层级模板池不能为空：" + id);
    }
  }

  /** 道路模板及其连接类型。 */
  public record RoadDefinition(String id, Holder<StructureTemplatePool> pool) {
    public static final Codec<RoadDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(RoadDefinition::id),
        StructureTemplatePool.CODEC.fieldOf("pool").forGetter(RoadDefinition::pool)
    ).apply(instance, RoadDefinition::new));

    public RoadDefinition {
      Objects.requireNonNull(id, "道路构件 ID 不能为空");
      Objects.requireNonNull(pool, "道路构件模板池不能为空：" + id);
    }
  }

  /** Jigsaw 候选建筑展开所需的最小运行时参数。 */
  public record BuildingDefinition(
      String id,
      Holder<StructureTemplatePool> startPool,
      int size,
      boolean useExpansionHack,
      int maxDistanceFromCenter,
      int footprintChunksX,
      int footprintChunksZ
  ) {
    public static final Codec<BuildingDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(BuildingDefinition::id),
        StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(BuildingDefinition::startPool),
        Codec.intRange(0, 20).fieldOf("size").forGetter(BuildingDefinition::size),
        Codec.BOOL.fieldOf("use_expansion_hack").forGetter(BuildingDefinition::useExpansionHack),
        Codec.intRange(1, 112).fieldOf("max_distance_from_center")
            .forGetter(BuildingDefinition::maxDistanceFromCenter),
        Codec.intRange(1, 64).fieldOf("footprint_chunks_x")
            .forGetter(BuildingDefinition::footprintChunksX),
        Codec.intRange(1, 64).fieldOf("footprint_chunks_z")
            .forGetter(BuildingDefinition::footprintChunksZ)
    ).apply(instance, BuildingDefinition::new));

    public BuildingDefinition {
      Objects.requireNonNull(id, "候选建筑 ID 不能为空");
      Objects.requireNonNull(startPool, "候选建筑起始池不能为空：" + id);
    }
  }

  public static final class Access {
    private Access() {
    }

    @NotNull
    public MapCodec<MobilePlotStructure> getCODEC() {
      return CODEC;
    }

    public void bind(Supplier<StructureType<MobilePlotStructure>> type) {
      if (MobilePlotStructure.type == null) {
        MobilePlotStructure.type = Objects.requireNonNull(type, "type");
      }
    }
  }
}
