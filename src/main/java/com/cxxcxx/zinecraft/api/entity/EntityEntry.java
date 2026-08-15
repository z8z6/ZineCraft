package com.cxxcxx.zinecraft.api.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class EntityEntry<T extends Entity> {
  @NotNull
  private final String path;
  @NotNull
  private final Supplier<EntityType<T>> type;

  public EntityEntry(@NotNull String path, @NotNull Supplier<EntityType<T>> type) {
    super();
    this.path = path;
    this.type = type;
  }

  @NotNull
  public final String getPath() {
    return this.path;
  }

  @NotNull
  public final EntityType<T> getType() {
    return this.type.get();
  }
}
