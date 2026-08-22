package com.cxxcxx.zinecraft.api.world.structure;

import com.cxxcxx.zinecraft.api.world.city.CityRegionBuildingSlot;
import com.cxxcxx.zinecraft.api.world.city.CityRegionCell;
import com.cxxcxx.zinecraft.core.nation.TerraLayoutResource;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
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
 * 消费 {@code terra_layout.json}：每区块铺设一份动力层，并在本区块持有的 slot 上展开建筑。
 */
public final class MobilePlotStructure extends Structure {
  public static final int POWER_LAYER_HEIGHT = 31;
  @NotNull
  public static final Access ACCESS = new Access();
  @NotNull
  private static final MapCodec<MobilePlotStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
      settingsCodec(instance),
      StructureTemplatePool.CODEC.fieldOf("power_layer_pool")
          .forGetter(structure -> structure.powerLayerPool),
      BuildingDefinition.CODEC.listOf().fieldOf("buildings")
          .forGetter(structure -> structure.buildings)
  ).apply(instance, MobilePlotStructure::new));
  private static Supplier<StructureType<MobilePlotStructure>> type;

  private final Holder<StructureTemplatePool> powerLayerPool;
  private final List<BuildingDefinition> buildings;
  private final Map<String, BuildingDefinition> buildingsById;

  public MobilePlotStructure(
      StructureSettings settings,
      Holder<StructureTemplatePool> powerLayerPool,
      List<BuildingDefinition> buildings
  ) {
    super(settings);
    this.powerLayerPool = Objects.requireNonNull(powerLayerPool, "动力层模板池不能为空");
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
    Optional<CityRegionCell> optionalRegion = TerraLayoutResource.mobilePlotRegion(chunk.x, chunk.z);
    if (optionalRegion.isEmpty()) return Optional.empty();

    CityRegionCell region = optionalRegion.get();
    int centerX = chunk.getMiddleBlockX();
    int centerZ = chunk.getMiddleBlockZ();
    int baseY = context.chunkGenerator().getFirstFreeHeight(
        centerX,
        centerZ,
        Heightmap.Types.WORLD_SURFACE_WG,
        context.heightAccessor(),
        context.randomState()
    );
    BlockPos powerOrigin = new BlockPos(chunk.getMinBlockX(), baseY, chunk.getMinBlockZ());
    StructurePoolElement powerElement = powerLayerPool.value().getRandomTemplate(context.random());
    if (powerElement == EmptyPoolElement.INSTANCE) return Optional.empty();

    BoundingBox powerBox = powerElement.getBoundingBox(
        context.structureTemplateManager(), powerOrigin, Rotation.NONE
    );
    PoolElementStructurePiece powerPiece = new PoolElementStructurePiece(
        context.structureTemplateManager(),
        powerElement,
        powerOrigin,
        powerElement.getGroundLevelDelta(),
        Rotation.NONE,
        powerBox,
        LiquidSettings.IGNORE_WATERLOGGING
    );
    List<CityRegionBuildingSlot> chunkSlots = region.buildingSlots().stream()
        .filter(slot -> SectionPos.blockToSectionCoord(Mth.floor(slot.center().x())) == chunk.x
            && SectionPos.blockToSectionCoord(Mth.floor(slot.center().z())) == chunk.z)
        .toList();

    return Optional.of(new GenerationStub(
        new BlockPos(chunk.getMiddleBlockX(), baseY, chunk.getMiddleBlockZ()),
        pieces -> {
          pieces.addPiece(powerPiece);
          for (CityRegionBuildingSlot slot : chunkSlots) {
            addBuilding(context, pieces, slot, baseY + POWER_LAYER_HEIGHT);
          }
        }
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
    BlockPos origin = new BlockPos(
        Mth.floor(slot.center().x()),
        buildingY,
        Mth.floor(slot.center().z())
    );
    JigsawPlacement.addPieces(
        context,
        definition.startPool(),
        Optional.empty(),
        definition.size(),
        origin,
        definition.useExpansionHack(),
        Optional.empty(),
        definition.maxDistanceFromCenter(),
        PoolAliasLookup.EMPTY,
        DimensionPadding.ZERO,
        LiquidSettings.IGNORE_WATERLOGGING
    ).ifPresent(stub -> stub.getPiecesBuilder().build().pieces().forEach(pieces::addPiece));
  }

  @Override
  @NotNull
  public StructureType<?> type() {
    return Objects.requireNonNull(type, "Mobile-plot structure type is not registered").get();
  }

  /** Jigsaw 候选建筑展开所需的最小运行时参数。 */
  public record BuildingDefinition(
      String id,
      Holder<StructureTemplatePool> startPool,
      int size,
      boolean useExpansionHack,
      int maxDistanceFromCenter
  ) {
    public static final Codec<BuildingDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(BuildingDefinition::id),
        StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(BuildingDefinition::startPool),
        Codec.intRange(0, 20).fieldOf("size").forGetter(BuildingDefinition::size),
        Codec.BOOL.fieldOf("use_expansion_hack").forGetter(BuildingDefinition::useExpansionHack),
        Codec.intRange(1, 112).fieldOf("max_distance_from_center")
            .forGetter(BuildingDefinition::maxDistanceFromCenter)
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
