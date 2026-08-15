package com.cxxcxx.zinecraft.api.skill;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public final class SkillService {
  @NotNull
  private final Map<ResourceLocation, SkillEffect> effects = new LinkedHashMap<>();

  @NotNull
  public final ResourceLocation register(@NotNull ResourceLocation id, @NotNull SkillEffect effect) {
    if (this.effects.putIfAbsent(id, effect) != null) {
      int i = 0;
      String string = "重复的技能效果 ID：" + id;
      throw new IllegalArgumentException(string.toString());
    } else {
      return id;
    }
  }

  public final boolean canCast(@NotNull ResourceLocation id, @NotNull SkillCastContext context) {
    SkillEffect skillEffect = this.effects.get(id);
    return skillEffect != null ? skillEffect.canCast(context) : false;
  }

  public final boolean cast(@NotNull ResourceLocation id, @NotNull SkillCastContext context) {
    SkillEffect skillEffect1 = this.effects.get(id);
    if (skillEffect1 == null) {
      return false;
    }

    SkillEffect skillEffect = skillEffect1;
    if (!skillEffect.canCast(context)) {
      return false;
    }

    skillEffect.cast(context);
    return true;
  }
}

