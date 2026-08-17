package com.cxxcxx.zinecraft.api.world.biome;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public final class BiomeCatalog {
  private final ModRegistrar registrar;
  private final TranslationCatalog translations;
  private final List<BiomeRegistration> entries = new ArrayList<>();

  public BiomeCatalog(ModRegistrar registrar, TranslationCatalog translations) {
    this.registrar = Objects.requireNonNull(registrar, "registrar");
    this.translations = Objects.requireNonNull(translations, "translations");
  }

  public ResourceKey<Biome> register(
      String path,
      String zhCn,
      Consumer<? super SimpleBiomeBuilder> configure
  ) {
    if (!ResourceLocation.isValidPath(path)) {
      throw new IllegalArgumentException("群系 ID 路径无效：" + path);
    }
    if (zhCn == null || zhCn.isBlank()) {
      throw new IllegalArgumentException("群系中文名不能为空：" + path);
    }
    Objects.requireNonNull(configure, "群系配置不能为空：" + path);
    if (entries.stream().anyMatch(entry -> entry.key().location().getPath().equals(path))) {
      throw new IllegalArgumentException("群系 ID 重复：" + path);
    }

    ResourceKey<Biome> key = registrar.key(Registries.BIOME, path);
    entries.add(new BiomeRegistration(key, configure::accept));
    translations.add(
        "biome." + registrar.namespace + "." + path,
        zhCn,
        TranslationNames.toDisplayName(path)
    );
    return key;
  }

  public void bootstrap(BootstrapContext<Biome> context) {
    var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
    var configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
    for (BiomeRegistration entry : entries) {
      SimpleBiomeBuilder builder = new SimpleBiomeBuilder(placedFeatures, configuredCarvers);
      entry.configure().accept(builder);
      registrar.dynamic(context, entry.key(), builder.build());
    }
  }

  private record BiomeRegistration(
      ResourceKey<Biome> key,
      Consumer<SimpleBiomeBuilder> configure
  ) {
  }
}
