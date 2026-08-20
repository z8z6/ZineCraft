package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.WeaponCatalog;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.Function;

/**
 * 声明并登记一个服务端权威武器动作。
 */
public final class WeaponActionBuilder<T extends WeaponAction> {
  public final WeaponCatalog catalog;
  public final String path;
  private final Function<ResourceLocation, ? extends T> factory;
  private T action;

  public WeaponActionBuilder(
      WeaponCatalog catalog,
      String path,
      Function<ResourceLocation, ? extends T> factory
  ) {
    this.catalog = Objects.requireNonNull(catalog, "武器目录不能为空");
    this.path = Objects.requireNonNull(path, "武器动作 ID 不能为空");
    this.factory = Objects.requireNonNull(factory, "武器动作 factory 不能为空：" + path);
  }

  public ResourceLocation resourceKey() {
    return ResourceLocation.fromNamespaceAndPath(catalog.namespace(), path);
  }

  public T getAction() {
    if (action == null) throw new IllegalStateException("武器动作尚未 build：" + path);
    return action;
  }

  public WeaponActionBuilder<T> build() {
    if (action != null) throw new IllegalStateException("武器动作 builder 不能重复 build：" + path);
    action = Objects.requireNonNull(factory.apply(resourceKey()), "武器动作 factory 返回了 null：" + path);
    if (!resourceKey().equals(action.getId())) {
      action = null;
      throw new IllegalArgumentException("武器动作 factory 返回了错误 ID：" + path);
    }
    try {
      catalog.register(this);
      return this;
    } catch (RuntimeException exception) {
      action = null;
      throw exception;
    }
  }
}
