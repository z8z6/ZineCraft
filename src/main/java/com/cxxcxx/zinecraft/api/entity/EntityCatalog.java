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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class EntityCatalog {
  public static final Access ACCESS = new Access();
  private final ModRegistrar registrar;
  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final List<NaturalSpawn> naturalSpawns = new ArrayList<>();
  private final List<MobBuilder<?>> mutableMobs = new ArrayList<>();
  public final List<MobBuilder<?>> mobs = Collections.unmodifiableList(mutableMobs);

  public EntityCatalog(ModRegistrar registrar, ItemCatalog items, TranslationCatalog translations) {
    this.registrar = registrar;
    this.items = items;
    this.translations = translations;
  }

  public <T extends Entity> Supplier<EntityType<T>> register(String path, String zhCn, String enUs,
                                                             EntityType.EntityFactory<T> factory, MobCategory category, Consumer<? super EntityType.Builder<T>> configure) {
    var type = registrar.entity(path, factory, category, configure);
    translations.add("entity." + registrar.namespace + "." + path, zhCn, enUs);
    return type;
  }

  public <T extends Mob> MobBuilder<T> mob(String path, String zhCn, String enUs,
                                           EntityType.EntityFactory<T> factory, MobCategory category,
                                           Supplier<? extends AttributeSupplier.Builder> attributes, MobSpawnRestriction<T> restriction,
                                           Consumer<? super EntityType.Builder<T>> configure) {
    return new MobBuilder<>(this, path, zhCn, enUs, factory, category, attributes, restriction, configure);
  }

  <T extends Mob> MobEntry<T> register(MobBuilder<T> builder) {
    validate(builder);
    var restriction = builder.restriction;
    var type = registrar.mob(builder.path, builder.factory, builder.category, builder.attributes,
        restriction == null ? null : restriction.getPlacement(),
        restriction == null ? null : restriction.getHeightmap(),
        restriction == null ? null : restriction.getPredicate(), builder.configure);
    var egg = builder.spawnEggData;
    var spawnEgg = items.builder(
            builder.path + "_spawn_egg",
            egg.zhCn(),
            () -> new SpawnEggItem(type.get(), egg.primary(), egg.secondary(), new Item.Properties())
        )
        .enUs(egg.enUs())
        .model(ACCESS.getSPAWN_EGG_MODEL())
        .build();
    MobEntry<T> entry = new MobEntry<>(type, spawnEgg);
    builder.entry = entry;
    translations.add("entity." + registrar.namespace + "." + builder.path, builder.zhCn, builder.enUs);
    if (builder.naturalSpawn != null) {
      var spawn = builder.naturalSpawn;
      naturalSpawns.add(new NaturalSpawn(builder.path, type, spawn.weight(), spawn.min(), spawn.max(), spawn.biomes()));
    }
    mutableMobs.add(builder);
    return entry;
  }

  private void validate(MobBuilder<?> builder) {
    if (!ResourceLocation.isValidPath(builder.path))
      throw new IllegalArgumentException("Mob ID 路径无效：" + builder.path);
    if (builder.zhCn == null || builder.zhCn.isBlank())
      throw new IllegalArgumentException("Mob 中文名不能为空：" + builder.path);
    if (builder.enUs == null || builder.enUs.isBlank())
      throw new IllegalArgumentException("Mob 英文名不能为空：" + builder.path);
    Objects.requireNonNull(builder.spawnEggData, "Mob 必须声明刷怪蛋：" + builder.path);
    if (builder.drops.isEmpty()) throw new IllegalStateException("Mob 必须声明至少一种掉落物：" + builder.path);
    if (mutableMobs.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("Mob ID 重复：" + builder.path);
    }
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
