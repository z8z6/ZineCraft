package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.VfxBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 客户端特效资源 ID 的声明目录；不负责执行任何玩法逻辑。
 */
public final class VfxCatalog {
  private static final Pattern PATH_PATTERN = Pattern.compile("[a-z0-9_./-]+");

  private final String namespace;
  private final List<VfxBuilder> mutableEntries = new ArrayList<>();
  public final List<VfxBuilder> entries = Collections.unmodifiableList(mutableEntries);

  public VfxCatalog(String namespace) {
    if (namespace == null || namespace.isBlank()) throw new IllegalArgumentException("特效目录命名空间不能为空");
    this.namespace = namespace;
  }

  public String namespace() {
    return namespace;
  }

  public VfxBuilder register(VfxBuilder builder) {
    Objects.requireNonNull(builder, "特效 builder 不能为空");
    if (builder.catalog != this) throw new IllegalArgumentException("特效 builder 不属于当前目录：" + builder.path);
    if (!PATH_PATTERN.matcher(builder.path).matches())
      throw new IllegalArgumentException("特效 ID 路径无效：" + builder.path);
    ResourceLocation id = builder.getId();
    if (mutableEntries.stream().anyMatch(entry -> entry.resourceKey().equals(id))) {
      throw new IllegalArgumentException("特效 ID 重复：" + id);
    }
    mutableEntries.add(builder);
    return builder;
  }
}
