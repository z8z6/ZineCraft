package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.SkillEffectBuilder;
import com.cxxcxx.zinecraft.api.skill.SkillEffect;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 服务端技能效果目录，统一校验稳定 ID 并保存构建后的效果声明。
 */
public final class SkillEffectCatalog {
  private static final Pattern PATH_PATTERN = Pattern.compile("[a-z0-9_]+");

  private final String namespace;
  private final Map<ResourceLocation, SkillEffectBuilder<?>> effects = new LinkedHashMap<>();
  private final List<SkillEffectBuilder<?>> mutableEntries = new ArrayList<>();
  public final List<SkillEffectBuilder<?>> entries = Collections.unmodifiableList(mutableEntries);

  public SkillEffectCatalog(String namespace) {
    if (namespace == null || namespace.isBlank()) throw new IllegalArgumentException("技能效果目录命名空间不能为空");
    this.namespace = namespace;
  }

  public String namespace() {
    return namespace;
  }

  public <T extends SkillEffect> SkillEffectBuilder<T> register(SkillEffectBuilder<T> builder) {
    Objects.requireNonNull(builder, "技能效果 builder 不能为空");
    if (builder.catalog != this) {
      throw new IllegalArgumentException("技能效果 builder 不属于当前目录：" + builder.path);
    }
    if (!PATH_PATTERN.matcher(builder.path).matches()) {
      throw new IllegalArgumentException("技能效果 ID 必须是 snake_case：" + builder.path);
    }
    ResourceLocation id = builder.getId();
    if (effects.putIfAbsent(id, builder) != null) {
      throw new IllegalArgumentException("技能效果 ID 重复：" + id);
    }
    mutableEntries.add(builder);
    return builder;
  }

  @Nullable
  public SkillEffectBuilder<?> effect(ResourceLocation id) {
    return effects.get(Objects.requireNonNull(id, "技能效果 ID 不能为空"));
  }
}
