package com.cxxcxx.zinecraft.api.world.structure;

import com.cxxcxx.zinecraft.core.nation.TerraLayoutResource;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/** 在任意移动地块覆盖的每个区块创建一次结构起点。 */
public final class MobilePlotStructurePlacement extends StructurePlacement {
  @NotNull
  public static final Access ACCESS = new Access();
  @NotNull
  private static final MapCodec<MobilePlotStructurePlacement> CODEC =
      MapCodec.unit(MobilePlotStructurePlacement::new);
  private static Supplier<StructurePlacementType<MobilePlotStructurePlacement>> type;

  private MobilePlotStructurePlacement() {
    super(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1.0F, 0, Optional.empty());
  }

  @Override
  protected boolean isPlacementChunk(
      @NotNull ChunkGeneratorStructureState structureState,
      int chunkX,
      int chunkZ
  ) {
    return TerraLayoutResource.mobilePlotRegion(chunkX, chunkZ).isPresent();
  }

  @Override
  @NotNull
  public StructurePlacementType<?> type() {
    return Objects.requireNonNull(type, "Mobile-plot structure placement is not registered").get();
  }

  public static final class Access {
    private Access() {
    }

    @NotNull
    public MapCodec<MobilePlotStructurePlacement> getCODEC() {
      return CODEC;
    }

    public void bind(Supplier<StructurePlacementType<MobilePlotStructurePlacement>> type) {
      if (MobilePlotStructurePlacement.type == null) {
        MobilePlotStructurePlacement.type = Objects.requireNonNull(type, "type");
      }
    }

    @NotNull
    public MobilePlotStructurePlacement create() {
      if (MobilePlotStructurePlacement.type == null) {
        throw new IllegalStateException("移动地块结构放置器尚未注册");
      }
      return new MobilePlotStructurePlacement();
    }
  }
}
