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

    var registryBuilder = new RegistrySetBuilder();
    Zinecraft.INSTANCE.getWORLDGEN().addDataGeneration(registryBuilder);
    registryBuilder.add(net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
      Zinecraft.INSTANCE.getFEATURES().bootstrapBiomeModifiers(context);
      Zinecraft.INSTANCE.getENTITIES().bootstrapBiomeModifiers(context);
    });
    registryBuilder.add(Registries.ENCHANTMENT, Zinecraft.INSTANCE.getENCHANTMENTS()::bootstrap);
    registryBuilder.add(Registries.JUKEBOX_SONG, ModSound.INSTANCE::configure);
    event.createDatapackRegistryObjects(registryBuilder);

    var output = event.getGenerator().getPackOutput();
    var lookup = event.getLookupProvider();
    if (event.includeClient()) {
      event.addProvider(new CatalogLanguageProvider(output, Zinecraft.INSTANCE.getTRANSLATIONS(), "en_us"));
      event.addProvider(new CatalogLanguageProvider(output, Zinecraft.INSTANCE.getTRANSLATIONS(), "zh_cn"));
      event.addProvider(new ModCatalogModelProvider(output));
    }
    if (event.includeServer()) {
      event.addProvider(new CatalogLootTableProvider(output, lookup, Zinecraft.INSTANCE.getBLOCKS()));
      event.addProvider(new ModRecipeProvider(output, lookup));
    }
  }
}
