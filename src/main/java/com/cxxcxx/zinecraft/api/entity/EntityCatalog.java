package com.cxxcxx.zinecraft.api.entity;

import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class EntityCatalog {
  public static final Access ACCESS = new Access();
  private final ModRegistrar registrar;
  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final List<NaturalSpawn> naturalSpawns = new ArrayList<>();

  public EntityCatalog(ModRegistrar registrar, ItemCatalog items, TranslationCatalog translations) {
    this.registrar = registrar;
    this.items = items;
    this.translations = translations;
  }

  public <T extends Entity> EntityEntry<T> register(String path, String zhCn, String enUs,
                                                    EntityType.EntityFactory<T> factory, MobCategory category, Consumer<? super EntityType.Builder<T>> configure) {
    var type = registrar.entity(path, factory, category, configure);
    translations.add("entity." + registrar.getNamespace() + "." + path, zhCn, enUs);
    return new EntityEntry<>(path, type);
  }

  public <T extends Mob> MobEntry<T> mob(String path, String zhCn, String enUs,
                                         EntityType.EntityFactory<T> factory, MobCategory category,
                                         Supplier<? extends AttributeSupplier.Builder> attributes, MobSpawnRestriction<T> restriction,
                                         Consumer<? super EntityType.Builder<T>> configure) {
    var type = registrar.mob(path, factory, category, attributes,
        restriction == null ? null : restriction.getPlacement(),
        restriction == null ? null : restriction.getHeightmap(),
        restriction == null ? null : restriction.getPredicate(), configure);
    translations.add("entity." + registrar.getNamespace() + "." + path, zhCn, enUs);
    return new MobEntry<>(path, type, category, items, this::addNaturalSpawn);
  }

  private void addNaturalSpawn(NaturalSpawn spawn) {
    naturalSpawns.add(spawn);
  }

  public void bootstrapBiomeModifiers(BootstrapContext<BiomeModifier> context) {
    var biomes = context.lookup(Registries.BIOME);
    for (var spawn : naturalSpawns) {
      var parts = spawn.biomes().resolveParts(biomes);
      for (int i = 0; i < parts.size(); i++) {
        String path = spawn.path() + "_spawn" + (parts.size() == 1 ? "" : "_" + i);
        context.register(registrar.key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, path),
            BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(parts.get(i),
                new MobSpawnSettings.SpawnerData(spawn.type().get(), spawn.weight(), spawn.min(), spawn.max())));
      }
    }
  }

  record NaturalSpawn(String path, java.util.function.Supplier<? extends EntityType<?>> type, int weight, int min,
                      int max, BiomeSelection biomes) {
  }

  public static final class Access {
    private static final ModelTemplate SPAWN_EGG_MODEL = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("item/template_spawn_egg")), Optional.empty());

    public ModelTemplate getSPAWN_EGG_MODEL() {
      return SPAWN_EGG_MODEL;
    }
  }
}
