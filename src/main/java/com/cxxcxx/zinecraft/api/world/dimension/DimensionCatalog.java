package com.cxxcxx.zinecraft.api.world.dimension;

import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public final class DimensionCatalog {
  @NotNull
  private final ModRegistrar registrar;
  @NotNull
  private final List<DimensionEntry> entries;

  public DimensionCatalog(@NotNull ModRegistrar registrar) {
    super();
    this.registrar = registrar;
    this.entries = new ArrayList<>();
  }

  public static DimensionEntry registerWithDefaults(DimensionCatalog var0, String var1, List var2, Function var3, int var4, Object var5) {
    if ((var4 & 4) != 0) {
      var3 = null;
    }

    return var0.register(var1, var2, var3);
  }

  @NotNull
  public final DimensionEntry register(
      @NotNull String path,
      @NotNull List<DimensionBiome> biomes,
      @Nullable Function<? super DimensionBootstrapContext, ? extends ChunkGenerator> createGenerator
  ) {
    if (path.isBlank()) {
      int l = 0;
      String string4 = "维度 ID 不能为空";
      throw new IllegalArgumentException(string4.toString());
    }

    var iterable = this.entries;
    int i = 0;
    boolean bl;
    if (iterable instanceof Collection && ((Collection) iterable).isEmpty()) {
      bl = true;
    } else {
      Iterator p0 = iterable.iterator();

      while (true) {
        if (!p0.hasNext()) {
          bl = true;
          break;
        }

        Object object = p0.next();
        DimensionEntry dimensionEntry = (DimensionEntry) object;
        int j = 0;
        if (java.util.Objects.equals(dimensionEntry.getPath(), path)) {
          bl = false;
          break;
        }
      }
    }

    if (!bl) {
      i = 0;
      String string3 = "维度 ID 重复: " + path;
      throw new IllegalArgumentException(string3.toString());
    }

    if (biomes.isEmpty()) {
      i = 0;
      String string2 = "维度至少需要一个群系: " + path;
      throw new IllegalArgumentException(string2.toString());
    }

    i = 0;
    Iterable iterable1 = biomes;
    var collection1 = new ArrayList(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(biomes, 10));
    int n = 0;

    for (Object object1 : iterable1) {
      DimensionBiome it = (DimensionBiome) object1;
      Collection collection = collection1;
      int k = 0;
      collection.add(it.getBiome());
    }

    if (com.cxxcxx.zinecraft.api.util.CollectionSupport.distinct((Iterable & List) collection1).size() != biomes.size()) {
      i = 0;
      String string1 = "维度群系资源键重复: " + path;
      throw new IllegalArgumentException(string1.toString());
    }

    i = 0;
    iterable1 = biomes;
    collection1 = new ArrayList(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(biomes, 10));
    n = 0;

    for (Object object2 : iterable1) {
      DimensionBiome dimensionBiome1 = (DimensionBiome) object2;
      Collection collection2 = collection1;
      int o = 0;
      collection2.add(dimensionBiome1.getParameters());
    }

    if (com.cxxcxx.zinecraft.api.util.CollectionSupport.distinct((Iterable & List) collection1).size() != biomes.size()) {
      i = 0;
      String string = "维度气候点重复: " + path;
      throw new IllegalArgumentException(string.toString());
    } else {
      ResourceKey resourceKey1 = ResourceKey.create(Registries.DIMENSION, this.registrar.id(path));
      ModRegistrar modRegistrar1 = this.registrar;
      ResourceKey resourceKey2 = Registries.LEVEL_STEM;
      ResourceKey resourceKey = modRegistrar1.key(resourceKey2, path);
      ModRegistrar modRegistrar = this.registrar;
      ResourceKey resourceKey3 = Registries.DIMENSION_TYPE;
      resourceKey2 = modRegistrar.key(resourceKey3, path);
      resourceKey3 = NoiseGeneratorSettings.OVERWORLD;
      DimensionEntry dimensionEntry1 = new DimensionEntry(
          path, resourceKey1, resourceKey, resourceKey2, resourceKey3, com.cxxcxx.zinecraft.api.util.CollectionSupport.toList(biomes), createGenerator
      );
      List list = this.entries;
      DimensionEntry dimensionEntry2 = dimensionEntry1;
      int m = 0;
      list.add(dimensionEntry2);
      return dimensionEntry1;
    }
  }

  public final void bootstrapDimensionTypes(@NotNull BootstrapContext<DimensionType> context) {
    Iterable iterable = this.entries;
    int i = 0;

    for (Object object : iterable) {
      DimensionEntry dimensionEntry = (DimensionEntry) object;
      int j = 0;
      this.registrar.dynamic(context, dimensionEntry.getTypeKey(), DimensionHelper.INSTANCE.overworldLikeType());
    }
  }

  public final void bootstrapLevelStems(@NotNull BootstrapContext<LevelStem> context) {
    HolderGetter holderGetter = context.lookup(Registries.DIMENSION_TYPE);
    HolderGetter holderGetter1 = context.lookup(Registries.NOISE_SETTINGS);
    HolderGetter holderGetter2 = context.lookup(Registries.BIOME);
    Iterable iterable = this.entries;
    int i = 0;

    for (Object object : iterable) {
      DimensionEntry dimensionEntry = (DimensionEntry) object;
      int j = 0;
      Iterable iterable1 = dimensionEntry.getBiomes();
      int k = 0;
      Iterable _this_mapTo_iv_iv = iterable1;
      var collection = new ArrayList(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(iterable1, 10));
      int l = 0;

      for (Object object1 : _this_mapTo_iv_iv) {
        DimensionBiome biome = (DimensionBiome) object1;
        Collection collection1 = collection;
        int m = 0;
        collection1.add(Pair.of(biome.getParameters(), holderGetter2.getOrThrow(biome.getBiome())));
      }

      ChunkGenerator chunkGenerator1;
      label25:
      {
        List list = (List) collection;
        ParameterList parameterList = new ParameterList(list);
        MultiNoiseBiomeSource multiNoiseBiomeSource = MultiNoiseBiomeSource.createFromList(parameterList);
        Reference reference = holderGetter1.getOrThrow(dimensionEntry.getNoiseSettingsKey());
        DimensionBootstrapContext dimensionBootstrapContext = new DimensionBootstrapContext(
            dimensionEntry, multiNoiseBiomeSource, parameterList, holderGetter2, (Holder<NoiseGeneratorSettings>) reference
        );
        Function function1 = dimensionEntry.getCreateGenerator();
        if (function1 != null) {
          chunkGenerator1 = (ChunkGenerator) function1.apply(dimensionBootstrapContext);
          if (chunkGenerator1 != null) {
            break label25;
          }
        }

        chunkGenerator1 = (ChunkGenerator) (
            new NoiseBasedChunkGenerator((BiomeSource) dimensionBootstrapContext.getBiomeSource(), dimensionBootstrapContext.getNoiseSettings())
        );
      }

      ChunkGenerator chunkGenerator = chunkGenerator1;
      this.registrar
          .dynamic(context, dimensionEntry.getStemKey(), new LevelStem((Holder) holderGetter.getOrThrow(dimensionEntry.getTypeKey()), chunkGenerator));
    }
  }
}

