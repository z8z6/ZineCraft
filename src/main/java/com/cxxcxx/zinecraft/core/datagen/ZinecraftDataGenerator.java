package com.cxxcxx.zinecraft.core.datagen;

import com.cxxcxx.zinecraft.api.datagen.CatalogLanguageProvider;
import com.cxxcxx.zinecraft.api.datagen.CatalogLootTableProvider;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.recipe.ModRecipeProvider;
import com.cxxcxx.zinecraft.core.sound.ModSound;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.data.event.GatherDataEvent;

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
      event.addProvider(new ModCatalogModelProvider(output));
    }
    if (event.includeServer()) {
      var registryBuilder = new RegistrySetBuilder();
      Zinecraft.WORLDGEN.addDataGeneration(registryBuilder);
      registryBuilder.add(net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
        Zinecraft.WORLDGEN.getFeatures().bootstrapBiomeModifiers(context);
        Zinecraft.ENTITIES.bootstrapBiomeModifiers(context);
      });
      registryBuilder.add(Registries.ENCHANTMENT, Zinecraft.ENCHANTMENTS::bootstrap);
      registryBuilder.add(Registries.JUKEBOX_SONG, ModSound.INSTANCE::configure);
      event.createDatapackRegistryObjects(registryBuilder);

      event.addProvider(new CatalogLootTableProvider(output, lookup, Zinecraft.BLOCKS));
      event.addProvider(new ModRecipeProvider(output, lookup));
    }
  }
}
