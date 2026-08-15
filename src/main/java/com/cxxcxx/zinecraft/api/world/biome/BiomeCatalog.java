package com.cxxcxx.zinecraft.api.world.biome;

import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class BiomeCatalog {
  @NotNull
  private final ModRegistrar registrar;
  @NotNull
  private final List<BiomeEntry> entries;

  public BiomeCatalog(@NotNull ModRegistrar registrar) {
    super();
    this.registrar = registrar;
    this.entries = new ArrayList<>();
  }

  @NotNull
  public final ResourceKey<Biome> register(@NotNull String path, @NotNull Consumer<? super SimpleBiomeBuilder> build) {
    ModRegistrar modRegistrar = this.registrar;
    ResourceKey resourceKey1 = Registries.BIOME;
    ResourceKey resourceKey = modRegistrar.key(resourceKey1, path);
    this.entries.add(new BiomeEntry(resourceKey, build));
    return resourceKey;
  }

  public final void bootstrap(@NotNull BootstrapContext<Biome> context) {
    HolderGetter holderGetter2 = context.lookup(Registries.PLACED_FEATURE);
    HolderGetter holderGetter = holderGetter2;
    holderGetter2 = context.lookup(Registries.CONFIGURED_CARVER);
    HolderGetter holderGetter1 = holderGetter2;
    Iterable iterable = this.entries;
    int i = 0;

    for (Object object : iterable) {
      BiomeEntry biomeEntry = (BiomeEntry) object;
      int j = 0;
      ModRegistrar modRegistrar = this.registrar;
      ResourceKey resourceKey = biomeEntry.getKey();
      SimpleBiomeBuilder simpleBiomeBuilder = new SimpleBiomeBuilder(holderGetter, holderGetter1);
      biomeEntry.getBuild().accept(simpleBiomeBuilder);
      modRegistrar.dynamic(context, resourceKey, simpleBiomeBuilder.build());
    }
  }
}

