package com.cxxcxx.zinecraft.api.world.structure;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public final class FixedOriginStructurePlacement extends StructurePlacement {
  @NotNull
  public static final FixedOriginStructurePlacement.Access ACCESS = new FixedOriginStructurePlacement.Access();
  @NotNull
  private static final MapCodec<FixedOriginStructurePlacement> CODEC;
  private static Supplier<StructurePlacementType<FixedOriginStructurePlacement>> type;

  static {
    CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        com.mojang.serialization.Codec.INT.optionalFieldOf("chunk_x", -1)
            .forGetter(placement -> placement.chunkX),
        com.mojang.serialization.Codec.INT.optionalFieldOf("chunk_z", -1)
            .forGetter(placement -> placement.chunkZ)
    ).apply(instance, FixedOriginStructurePlacement::new));
  }

  private final int chunkX;
  private final int chunkZ;

  private FixedOriginStructurePlacement(int chunkX, int chunkZ) {
    super(new Vec3i(16, 0, 16), FrequencyReductionMethod.DEFAULT, 1.0F, 0, Optional.empty());
    this.chunkX = chunkX;
    this.chunkZ = chunkZ;
  }

  protected boolean isPlacementChunk(@NotNull ChunkGeneratorStructureState structureState, int chunkX, int chunkZ) {
    return chunkX == this.chunkX && chunkZ == this.chunkZ;
  }

  @NotNull
  public StructurePlacementType<?> type() {
    return Objects.requireNonNull(type, "Fixed-origin structure placement is not registered").get();
  }

  public static final class Access {
    private Access() {
    }

    @NotNull
    public final MapCodec<FixedOriginStructurePlacement> getCODEC() {
      return FixedOriginStructurePlacement.CODEC;
    }

    public void bind(Supplier<StructurePlacementType<FixedOriginStructurePlacement>> type) {
      if (FixedOriginStructurePlacement.type == null) {
        FixedOriginStructurePlacement.type = Objects.requireNonNull(type, "type");
      }
    }

    @NotNull
    public final FixedOriginStructurePlacement create() {
      return create(-1, -1);
    }

    /**
     * @param chunkX 唯一生成区块 X
     * @param chunkZ 唯一生成区块 Z
     * @return 固定在指定区块的结构放置器
     */
    @NotNull
    public final FixedOriginStructurePlacement create(int chunkX, int chunkZ) {
      if (FixedOriginStructurePlacement.type == null) {
        throw new IllegalStateException("固定坐标结构放置器尚未注册");
      } else {
        return new FixedOriginStructurePlacement(chunkX, chunkZ);
      }
    }
  }
}
