package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.combat.CombatDamageProfile;
import com.cxxcxx.zinecraft.api.combat.CombatDamageProvider;
import com.cxxcxx.zinecraft.api.registry.catalog.SkillEffectCatalog;
import com.cxxcxx.zinecraft.api.skill.SkillCastContext;
import com.cxxcxx.zinecraft.api.skill.SkillEffect;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * 声明并登记一个可由武器动作或其他服务端入口调用的技能效果。
 */
public final class SkillEffectBuilder<T extends SkillEffect> implements CombatDamageProvider {
  public final SkillEffectCatalog catalog;
  public final String path;
  private final Function<ResourceLocation, ? extends T> factory;
  private T effect;

  public SkillEffectBuilder(
      SkillEffectCatalog catalog,
      String path,
      Function<ResourceLocation, ? extends T> factory
  ) {
    this.catalog = Objects.requireNonNull(catalog, "技能效果目录不能为空");
    this.path = Objects.requireNonNull(path, "技能效果 ID 不能为空");
    this.factory = Objects.requireNonNull(factory, "技能效果 factory 不能为空：" + path);
  }

  public ResourceLocation resourceKey() {
    return ResourceLocation.fromNamespaceAndPath(catalog.namespace(), "skill/" + path);
  }

  public ResourceLocation getId() {
    requireBuilt();
    return resourceKey();
  }

  public T getEffect() {
    requireBuilt();
    return effect;
  }

  @Override
  public List<CombatDamageProfile> damageProfiles() {
    return getEffect().damageProfiles();
  }

  public boolean canCast(SkillCastContext context) {
    return getEffect().canCast(Objects.requireNonNull(context, "技能施放上下文不能为空"));
  }

  public boolean cast(SkillCastContext context) {
    SkillCastContext castContext = Objects.requireNonNull(context, "技能施放上下文不能为空");
    T builtEffect = getEffect();
    if (!builtEffect.canCast(castContext)) return false;
    builtEffect.cast(castContext);
    return true;
  }

  public SkillEffectBuilder<T> build() {
    if (effect != null) throw new IllegalStateException("技能效果 builder 不能重复 build：" + resourceKey());
    effect = Objects.requireNonNull(factory.apply(resourceKey()), "技能效果 factory 返回了 null：" + resourceKey());
    try {
      return catalog.register(this);
    } catch (RuntimeException exception) {
      effect = null;
      throw exception;
    }
  }

  private void requireBuilt() {
    if (effect == null) throw new IllegalStateException("技能效果尚未 build：" + resourceKey());
  }
}
