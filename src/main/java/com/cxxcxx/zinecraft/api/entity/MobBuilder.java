package com.cxxcxx.zinecraft.api.entity;

import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class MobBuilder<T extends Mob> {
  public final String path;
  public final String zhCn;
  public final String enUs;
  final EntityType.EntityFactory<T> factory;
  final MobCategory category;
  final Supplier<? extends AttributeSupplier.Builder> attributes;
  final MobSpawnRestriction<T> restriction;
  final Consumer<? super EntityType.Builder<T>> configure;
  private final EntityCatalog catalog;
  private final List<MobDrop> mutableDrops = new ArrayList<>();
  public final List<MobDrop> drops = Collections.unmodifiableList(mutableDrops);
  public Supplier<EntityType<T>> type;
  public DeferredItem<SpawnEggItem> spawnEgg;
  NaturalSpawn naturalSpawn;
  SpawnEggData spawnEggData;

  MobBuilder(
      EntityCatalog catalog,
      String path,
      String zhCn,
      String enUs,
      EntityType.EntityFactory<T> factory,
      MobCategory category,
      Supplier<? extends AttributeSupplier.Builder> attributes,
      MobSpawnRestriction<T> restriction,
      Consumer<? super EntityType.Builder<T>> configure
  ) {
    this.catalog = catalog;
    this.path = path;
    this.zhCn = zhCn;
    this.enUs = enUs;
    this.factory = Objects.requireNonNull(factory, "Mob factory 不能为空：" + path);
    this.category = Objects.requireNonNull(category, "Mob category 不能为空：" + path);
    this.attributes = Objects.requireNonNull(attributes, "Mob attributes 不能为空：" + path);
    this.restriction = restriction;
    this.configure = Objects.requireNonNull(configure, "Mob configure 不能为空：" + path);
  }

  public MobBuilder<T> spawnEgg(int primary, int secondary, String zhCn, String enUs) {
    if (spawnEggData != null) throw new IllegalStateException("Mob 不能重复声明刷怪蛋：" + path);
    spawnEggData = new SpawnEggData(primary, secondary, zhCn, enUs);
    return this;
  }

  public MobBuilder<T> drop(ItemLike item) {
    mutableDrops.add(new MobDrop(Objects.requireNonNull(item, "掉落物不能为空")));
    return this;
  }

  public MobBuilder<T> naturalSpawn(int weight, int min, int max, BiomeSelection biomes) {
    if (naturalSpawn != null) throw new IllegalStateException("Mob 不能重复声明自然生成：" + path);
    if (weight <= 0 || min <= 0 || max < min) throw new IllegalArgumentException("自然生成参数无效：" + path);
    naturalSpawn = new NaturalSpawn(weight, min, max, Objects.requireNonNull(biomes, "生成群系不能为空"));
    return this;
  }

  public Supplier<EntityType<T>> build() {
    if (type != null) throw new IllegalStateException("Mob builder 不能重复 build：" + path);
    return catalog.register(this);
  }

  public record MobDrop(ItemLike item) {
  }

  record NaturalSpawn(int weight, int min, int max, BiomeSelection biomes) {
  }

  record SpawnEggData(int primary, int secondary, String zhCn, String enUs) {
  }
}
