package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.AnimationBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 玩家与武器动画资源 ID 的声明目录；实际播放由客户端动画后端负责。
 */
public final class AnimationCatalog {
  private final String namespace;
  private final List<AnimationBuilder> mutableEntries = new ArrayList<>();
  public final List<AnimationBuilder> entries = Collections.unmodifiableList(mutableEntries);

  public AnimationCatalog(String namespace) {
    if (namespace == null || namespace.isBlank()) throw new IllegalArgumentException("动画目录命名空间不能为空");
    this.namespace = namespace;
  }

  public String namespace() {
    return namespace;
  }

  public AnimationBuilder register(AnimationBuilder builder) {
    Objects.requireNonNull(builder, "动画 builder 不能为空");
    if (builder.catalog != this) throw new IllegalArgumentException("动画 builder 不属于当前目录：" + builder.path);
    if (!ResourceLocation.isValidPath(builder.path)) {
      throw new IllegalArgumentException("动画 ID 路径无效：" + builder.path);
    }
    ResourceLocation id = builder.getId();
    if (mutableEntries.stream().anyMatch(entry -> entry.resourceKey().equals(id))) {
      throw new IllegalArgumentException("动画 ID 重复：" + id);
    }
    mutableEntries.add(builder);
    return builder;
  }
}
