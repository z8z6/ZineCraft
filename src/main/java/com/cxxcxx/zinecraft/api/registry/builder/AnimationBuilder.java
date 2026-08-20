package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.AnimationCatalog;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * 声明一个可由客户端表现后端播放的玩家或武器动画资源。
 */
public final class AnimationBuilder {
  public final AnimationCatalog catalog;
  public final Target target;
  public final String path;
  private boolean built;
  public AnimationBuilder(AnimationCatalog catalog, Target target, String path) {
    this.catalog = Objects.requireNonNull(catalog, "动画目录不能为空");
    this.target = Objects.requireNonNull(target, "动画目标不能为空");
    this.path = Objects.requireNonNull(path, "动画 ID 不能为空");
  }

  public ResourceLocation resourceKey() {
    return ResourceLocation.fromNamespaceAndPath(
        catalog.namespace(), "animation/" + target.path + "/" + path
    );
  }

  public ResourceLocation getId() {
    if (!built) throw new IllegalStateException("动画尚未 build：" + resourceKey());
    return resourceKey();
  }

  public AnimationBuilder build() {
    if (built) throw new IllegalStateException("动画 builder 不能重复 build：" + resourceKey());
    built = true;
    try {
      return catalog.register(this);
    } catch (RuntimeException exception) {
      built = false;
      throw exception;
    }
  }

  public enum Target {
    PLAYER("player"),
    WEAPON("weapon");

    private final String path;

    Target(String path) {
      this.path = path;
    }
  }
}
