package com.cxxcxx.zinecraft.core.datagen;

import com.cxxcxx.zinecraft.api.datagen.CatalogLanguageProvider;
import com.cxxcxx.zinecraft.api.datagen.CatalogLootTableProvider;
import com.cxxcxx.zinecraft.api.datagen.CatalogModelProvider;
import com.cxxcxx.zinecraft.api.datagen.RegistryDataContributor;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.registry.ModRecipeProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

/**
 * NeoForge data-generation entrypoint.
 */
public final class ZinecraftDataGenerator {
  private ZinecraftDataGenerator() {
  }

  public static void gatherData(GatherDataEvent event) {
    Zinecraft.bootstrapContent();

    var output = event.getGenerator().getPackOutput();
    var lookup = event.getLookupProvider();
    if (event.includeClient()) {
      event.addProvider(new CatalogLanguageProvider(output, Zinecraft.TRANSLATIONS, "en_us"));
      event.addProvider(new CatalogLanguageProvider(output, Zinecraft.TRANSLATIONS, "zh_cn"));
      event.addProvider(new CatalogModelProvider(output, Zinecraft.ITEMS, Zinecraft.BLOCKS));
    }
    if (event.includeServer()) {
      var registryBuilder = new RegistrySetBuilder();
      List<RegistryDataContributor> contributors = List.of(
          Zinecraft.BIOMES,
          Zinecraft.FEATURES,
          Zinecraft.DENSITY_FUNCTIONS,
          Zinecraft.DIMENSIONS,
          Zinecraft.STRUCTURES,
          Zinecraft.ENCHANTMENTS,
          Zinecraft.SOUNDS
      );
      contributors.forEach(contributor -> contributor.contribute(registryBuilder));
      event.createDatapackRegistryObjects(registryBuilder);

      String terrainHeightmapOutput = System.getProperty(TerraTerrainHeightmapProvider.OUTPUT_PROPERTY);
      if (terrainHeightmapOutput != null && !terrainHeightmapOutput.isBlank()) {
        event.addProvider(new TerraTerrainHeightmapProvider(event.getLookupProvider()));
      }

      event.addProvider(new CatalogLootTableProvider(output, lookup, Zinecraft.BLOCKS, Zinecraft.ENTITIES));
      event.addProvider(new ModRecipeProvider(output, lookup));
    }
  }
}
