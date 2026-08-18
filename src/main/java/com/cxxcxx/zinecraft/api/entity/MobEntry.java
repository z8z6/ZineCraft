package com.cxxcxx.zinecraft.api.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A mob registration together with its automatically registered spawn egg.
 */
public final class MobEntry<T extends Mob> implements Supplier<EntityType<T>> {
  private final Supplier<EntityType<T>> type;
  private final DeferredItem<SpawnEggItem> spawnEgg;

  MobEntry(Supplier<EntityType<T>> type, DeferredItem<SpawnEggItem> spawnEgg) {
    this.type = Objects.requireNonNull(type, "type");
    this.spawnEgg = Objects.requireNonNull(spawnEgg, "spawnEgg");
  }

  public Supplier<EntityType<T>> type() {
    return type;
  }

  public DeferredItem<SpawnEggItem> spawnEgg() {
    return spawnEgg;
  }

  @Override
  public EntityType<T> get() {
    return type.get();
  }
}
