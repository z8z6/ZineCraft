package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.VfxCatalog;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * 声明一个可由武器动作或技能复用的客户端特效资源。
 */
public final class VfxBuilder {
  public final VfxCatalog catalog;
  public final String path;
  private boolean built;

  public VfxBuilder(VfxCatalog catalog, String path) {
    this.catalog = Objects.requireNonNull(catalog, "特效目录不能为空");
    this.path = Objects.requireNonNull(path, "特效 ID 不能为空");
  }

  public ResourceLocation resourceKey() {
    return ResourceLocation.fromNamespaceAndPath(catalog.namespace(), path);
  }

  public ResourceLocation getId() {
    if (!built) throw new IllegalStateException("特效尚未 build：" + path);
    return resourceKey();
  }

  public VfxBuilder build() {
    if (built) throw new IllegalStateException("特效 builder 不能重复 build：" + path);
    built = true;
    try {
      return catalog.register(this);
    } catch (RuntimeException exception) {
      built = false;
      throw exception;
    }
  }
}
