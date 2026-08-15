package com.cxxcxx.zinecraft.api.world.structure;

import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import com.mojang.serialization.MapCodec;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class FixedOriginStructurePlacement extends StructurePlacement {
  @NotNull
  public static final FixedOriginStructurePlacement.Companion Companion = new FixedOriginStructurePlacement.Companion(null);
  private static final int ORIGIN_CHUNK = -1;
  @NotNull
  private static final MapCodec<FixedOriginStructurePlacement> CODEC;
  private static StructurePlacementType<FixedOriginStructurePlacement> TYPE;

  static {
    MapCodec mapCodec = MapCodec.unit(() -> new FixedOriginStructurePlacement());
    CODEC = mapCodec;
  }

  private FixedOriginStructurePlacement() {
    super(new Vec3i(16, 0, 16), FrequencyReductionMethod.DEFAULT, 1.0F, 0, Optional.empty());
  }

  // $VF: synthetic method
  public FixedOriginStructurePlacement(DefaultConstructorMarker $constructor_marker) {
    this();
  }

  protected boolean isPlacementChunk(@NotNull ChunkGeneratorStructureState structureState, int chunkX, int chunkZ) {
    return chunkX == -1 && chunkZ == -1;
  }

  @NotNull
  public StructurePlacementType<?> type() {
    return java.util.Objects.requireNonNull(TYPE, "Fixed-origin structure placement is not registered");
  }

  public static final class Companion {
    private Companion() {
    }

    // $VF: synthetic method
    public Companion(DefaultConstructorMarker $constructor_marker) {
      this();
    }

    @NotNull
    public final MapCodec<FixedOriginStructurePlacement> getCODEC() {
      return FixedOriginStructurePlacement.CODEC;
    }

    public final void register$zinecraft(@NotNull ModRegistrar registrar) {
      if (FixedOriginStructurePlacement.TYPE == null) {
        FixedOriginStructurePlacement.TYPE = registrar.structurePlacement("fixed_origin", this.getCODEC());
      }
    }

    @NotNull
    public final FixedOriginStructurePlacement create$zinecraft() {
      if (FixedOriginStructurePlacement.TYPE == null) {
        int i = 0;
        String string = "固定原点结构放置器尚未注册";
        throw new IllegalStateException(string.toString());
      } else {
        return new FixedOriginStructurePlacement(null);
      }
    }
  }
}

