package com.cxxcxx.zinecraft.api.world.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SimpleBiomeBuilder {
  @NotNull
  private final Builder spawns;
  @NotNull
  private final net.minecraft.world.level.biome.BiomeGenerationSettings.Builder generation;
  private boolean precipitation;
  private float temperature;
  private float downfall;
  private int waterColor;
  private int waterFogColor;
  private int fogColor;
  @Nullable
  private Integer skyColor;
  @Nullable
  private Integer grassColor;
  @Nullable
  private Integer foliageColor;
  @Nullable
  private Music music;

  public SimpleBiomeBuilder(@NotNull HolderGetter<PlacedFeature> placedFeatures, @NotNull HolderGetter<ConfiguredWorldCarver<?>> carvers) {
    super();
    this.precipitation = true;
    this.temperature = 0.8F;
    this.downfall = 0.4F;
    this.waterColor = 4159204;
    this.waterFogColor = 329011;
    this.fogColor = 12638463;
    this.spawns = new Builder();
    this.generation = new net.minecraft.world.level.biome.BiomeGenerationSettings.Builder(placedFeatures, carvers);
  }

  public final boolean getPrecipitation() {
    return this.precipitation;
  }

  public final void setPrecipitation(boolean var1) {
    this.precipitation = var1/* $VF was: <set-?> */;
  }

  public final float getTemperature() {
    return this.temperature;
  }

  public final void setTemperature(float var1) {
    this.temperature = var1/* $VF was: <set-?> */;
  }

  public final float getDownfall() {
    return this.downfall;
  }

  public final void setDownfall(float var1) {
    this.downfall = var1/* $VF was: <set-?> */;
  }

  public final int getWaterColor() {
    return this.waterColor;
  }

  public final void setWaterColor(int var1) {
    this.waterColor = var1/* $VF was: <set-?> */;
  }

  public final int getWaterFogColor() {
    return this.waterFogColor;
  }

  public final void setWaterFogColor(int var1) {
    this.waterFogColor = var1/* $VF was: <set-?> */;
  }

  public final int getFogColor() {
    return this.fogColor;
  }

  public final void setFogColor(int var1) {
    this.fogColor = var1/* $VF was: <set-?> */;
  }

  @Nullable
  public final Integer getSkyColor() {
    return this.skyColor;
  }

  public final void setSkyColor(@Nullable Integer var1) {
    this.skyColor = var1/* $VF was: <set-?> */;
  }

  @Nullable
  public final Integer getGrassColor() {
    return this.grassColor;
  }

  public final void setGrassColor(@Nullable Integer var1) {
    this.grassColor = var1/* $VF was: <set-?> */;
  }

  @Nullable
  public final Integer getFoliageColor() {
    return this.foliageColor;
  }

  public final void setFoliageColor(@Nullable Integer var1) {
    this.foliageColor = var1/* $VF was: <set-?> */;
  }

  @Nullable
  public final Music getMusic() {
    return this.music;
  }

  public final void setMusic(@Nullable Music var1) {
    this.music = var1/* $VF was: <set-?> */;
  }

  @NotNull
  public final Builder getSpawns() {
    return this.spawns;
  }

  @NotNull
  public final net.minecraft.world.level.biome.BiomeGenerationSettings.Builder getGeneration() {
    return this.generation;
  }

  public final void defaultOverworldGeneration() {
    BiomeDefaultFeatures.addDefaultCarversAndLakes(this.generation);
    BiomeDefaultFeatures.addDefaultCrystalFormations(this.generation);
    BiomeDefaultFeatures.addDefaultMonsterRoom(this.generation);
    BiomeDefaultFeatures.addDefaultUndergroundVariety(this.generation);
    BiomeDefaultFeatures.addDefaultSprings(this.generation);
    BiomeDefaultFeatures.addSurfaceFreezing(this.generation);
  }

  @NotNull
  public final Biome build() {
    net.minecraft.world.level.biome.BiomeSpecialEffects.Builder builder = new net.minecraft.world.level.biome.BiomeSpecialEffects.Builder()
        .waterColor(this.waterColor)
        .waterFogColor(this.waterFogColor)
        .fogColor(this.fogColor)
        .skyColor(this.skyColor != null ? this.skyColor : net.minecraft.util.Mth.hsvToRgb(
            0.62222224F - net.minecraft.util.Mth.clamp(this.temperature / 3.0F, -1.0F, 1.0F) * 0.05F,
            0.5F + net.minecraft.util.Mth.clamp(this.temperature / 3.0F, -1.0F, 1.0F) * 0.1F, 1.0F))
        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
        .backgroundMusic(this.music);
    if (this.grassColor != null) {
      Integer integer = this.grassColor;
      net.minecraft.world.level.biome.BiomeSpecialEffects.Builder builder1 = builder;
      int i = integer.intValue();
      int j = 0;
      builder1.grassColorOverride(i);
    }

    if (this.foliageColor != null) {
      Integer integer1 = this.foliageColor;
      net.minecraft.world.level.biome.BiomeSpecialEffects.Builder builder2 = builder;
      int k = integer1.intValue();
      int l = 0;
      builder2.foliageColorOverride(k);
    }

    Biome biome = new BiomeBuilder()
        .hasPrecipitation(this.precipitation)
        .temperature(this.temperature)
        .downfall(this.downfall)
        .specialEffects(builder.build())
        .mobSpawnSettings(this.spawns.build())
        .generationSettings(this.generation.build())
        .build();
    return biome;
  }
}

