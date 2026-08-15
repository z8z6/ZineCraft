package com.cxxcxx.zinecraft.api.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class EntityEntry<T extends Entity> {
  @NotNull
  private final String path;
  @NotNull
  private final EntityType<T> type;

  public EntityEntry(@NotNull String path, @NotNull EntityType<T> type) {
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
    return this.type;
  }
}

