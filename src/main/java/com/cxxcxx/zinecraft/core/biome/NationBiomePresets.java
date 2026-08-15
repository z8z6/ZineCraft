package com.cxxcxx.zinecraft.core.biome;

import com.cxxcxx.zinecraft.api.world.biome.SimpleBiomeBuilder;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import org.jetbrains.annotations.NotNull;

public final class NationBiomePresets {
  @NotNull
  public static final NationBiomePresets INSTANCE = new NationBiomePresets();

  private NationBiomePresets() {
  }

  // $VF: synthetic method
  public static void featuredSpawn$default(
      NationBiomePresets var0, SimpleBiomeBuilder var1, MobCategory var2, EntityType var3, int var4, int var5, int var6, int var7, Object var8
  ) {
    if ((var7 & 16) != 0) {
      var5 = 1;
    }

    if ((var7 & 32) != 0) {
      var6 = var5;
    }

    var0.featuredSpawn(var1, var2, var3, var4, var5, var6);
  }

  public final void featuredSpawn(
      @NotNull SimpleBiomeBuilder biome, @NotNull MobCategory category, @NotNull EntityType<?> type, int weight, int minCount, int maxCount
  ) {
    if (weight <= 0) {
      int j = 0;
      String string1 = "特色生物生成权重必须大于 0";
      throw new IllegalArgumentException(string1.toString());
    }

    if (minCount <= 0 || maxCount < minCount) {
      int i = 0;
      String string = "特色生物群体数量无效";
      throw new IllegalArgumentException(string.toString());
    }

    biome.getSpawns().addSpawn(category, new SpawnerData(type, weight, minCount, maxCount));
  }

  public final void plains(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    INSTANCE.commonBase(simpleBiomeBuilder);
    BiomeDefaultFeatures.addPlainVegetation(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addPlainGrass(simpleBiomeBuilder.getGeneration());
  }

  public final void forest(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    INSTANCE.commonBase(simpleBiomeBuilder);
    BiomeDefaultFeatures.addBirchTrees(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addForestFlowers(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addForestGrass(simpleBiomeBuilder.getGeneration());
  }

  public final void rainyForest(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    INSTANCE.commonBase(simpleBiomeBuilder);
    BiomeDefaultFeatures.addTallBirchTrees(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addForestFlowers(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addFerns(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addForestGrass(simpleBiomeBuilder.getGeneration());
  }

  public final void mountain(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    INSTANCE.commonBase(simpleBiomeBuilder);
    BiomeDefaultFeatures.addMountainTrees(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addMeadowVegetation(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addExtraEmeralds(simpleBiomeBuilder.getGeneration());
  }

  public final void snowyForest(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    BiomeDefaultFeatures.snowySpawns(simpleBiomeBuilder.getSpawns());
    INSTANCE.generationBase(simpleBiomeBuilder);
    BiomeDefaultFeatures.addSnowyTrees(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addTaigaGrass(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addCommonBerryBushes(simpleBiomeBuilder.getGeneration());
  }

  public final void desert(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    BiomeDefaultFeatures.desertSpawns(simpleBiomeBuilder.getSpawns());
    INSTANCE.generationBase(simpleBiomeBuilder, false);
    BiomeDefaultFeatures.addDesertVegetation(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addDesertExtraVegetation(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addDesertExtraDecoration(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addFossilDecoration(simpleBiomeBuilder.getGeneration());
  }

  public final void badlands(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    BiomeDefaultFeatures.desertSpawns(simpleBiomeBuilder.getSpawns());
    INSTANCE.generationBase(simpleBiomeBuilder, false);
    BiomeDefaultFeatures.addBadlandsTrees(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addBadlandGrass(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addBadlandExtraVegetation(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addExtraGold(simpleBiomeBuilder.getGeneration());
  }

  public final void jungle(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    BiomeDefaultFeatures.baseJungleSpawns(simpleBiomeBuilder.getSpawns());
    INSTANCE.generationBase(simpleBiomeBuilder);
    BiomeDefaultFeatures.addJungleTrees(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addJungleGrass(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addJungleVines(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addJungleMelons(simpleBiomeBuilder.getGeneration());
  }

  public final void wetland(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    BiomeDefaultFeatures.commonSpawns(simpleBiomeBuilder.getSpawns());
    INSTANCE.generationBase(simpleBiomeBuilder, false);
    BiomeDefaultFeatures.addSwampVegetation(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addSwampExtraVegetation(simpleBiomeBuilder.getGeneration());
  }

  public final void ocean(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    BiomeDefaultFeatures.oceanSpawns(simpleBiomeBuilder.getSpawns(), 3, 4, 15);
    INSTANCE.generationBase(simpleBiomeBuilder);
    BiomeDefaultFeatures.addDefaultSeagrass(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addColdOceanExtraVegetation(simpleBiomeBuilder.getGeneration());
  }

  public final void cavern(@NotNull SimpleBiomeBuilder biome) {
    SimpleBiomeBuilder simpleBiomeBuilder = biome;
    int i = 0;
    BiomeDefaultFeatures.caveSpawns(simpleBiomeBuilder.getSpawns());
    INSTANCE.generationBase(simpleBiomeBuilder);
    BiomeDefaultFeatures.addDripstone(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addLushCavesVegetationFeatures(simpleBiomeBuilder.getGeneration());
    BiomeDefaultFeatures.addLushCavesSpecialOres(simpleBiomeBuilder.getGeneration());
  }

  private final void commonBase(SimpleBiomeBuilder $this$commonBase) {
    BiomeDefaultFeatures.plainsSpawns($this$commonBase.getSpawns());
    this.generationBase($this$commonBase);
  }

  private final void generationBase(SimpleBiomeBuilder $this$generationBase) {
    this.generationBase($this$generationBase, true);
  }

  private final void generationBase(SimpleBiomeBuilder $this$generationBase, boolean addDefaultExtraVegetation) {
    $this$generationBase.defaultOverworldGeneration();
    BiomeDefaultFeatures.addDefaultOres($this$generationBase.getGeneration());
    BiomeDefaultFeatures.addDefaultSoftDisks($this$generationBase.getGeneration());
    BiomeDefaultFeatures.addDefaultMushrooms($this$generationBase.getGeneration());
    if (addDefaultExtraVegetation) {
      BiomeDefaultFeatures.addDefaultExtraVegetation($this$generationBase.getGeneration());
    }
  }
}
