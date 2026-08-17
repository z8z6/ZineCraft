package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.api.world.dimension.*;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.NationBiomePlacements;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class ModDimensions {
  @NotNull
  public static final ModDimensions INSTANCE = new ModDimensions();
  public static final int LATERANO_CENTER_RADIUS = 1024;
  @NotNull
  public static final DimensionEntry TERRA;
  @NotNull
  private static final MapCodec<TerraBiomeSource> TERRA_BIOME_SOURCE = Zinecraft.REGISTRAR
      .biomeSource("terra", TerraBiomeSource.ACCESS.getCODEC());

  static {
    DimensionCatalog dimensionCatalog1 = Zinecraft.WORLDGEN.getDimensions();
    Iterable _this_filterNot_iv = NationBiomePlacements.ALL;
    String string = "terra";
    DimensionCatalog dimensionCatalog = dimensionCatalog1;
    int i = 0;
    Iterable _this_filterNotTo_iv_iv = _this_filterNot_iv;
    var collection = new ArrayList();
    int j = 0;

    for (Object object : _this_filterNotTo_iv_iv) {
      DimensionBiome dimensionBiome = (DimensionBiome) object;
      int k = 0;
      if (!java.util.Objects.equals(dimensionBiome.getBiome(), NationBiomes.LATERANO_HOLY_FIELDS)) {
        collection.add(object);
      }
    }

    List list = (List) collection;
    TERRA = dimensionCatalog.register(string, list, ModDimensions::TERRAHelper1);
    Zinecraft.TRANSLATIONS.add("dimension.zinecraft.terra", "泰拉", "Terra");
  }

  private ModDimensions() {
  }

  private static final ChunkGenerator TERRAHelper1(DimensionBootstrapContext context) {
    Reference reference = context.getBiomes().getOrThrow(NationBiomes.LATERANO_HOLY_FIELDS);
    ParameterList parameterList = context.getBiomeParameters();
    return (ChunkGenerator) (new NoiseBasedChunkGenerator(new TerraBiomeSource(parameterList, (Holder<Biome>) reference, LATERANO_CENTER_RADIUS), context.getNoiseSettings()));
  }

}

