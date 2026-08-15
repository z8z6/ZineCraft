package com.cxxcxx.zinecraft.api.skill;

import org.jetbrains.annotations.NotNull;

public interface SkillEffect {
  boolean canCast(@NotNull SkillCastContext var1);

  void cast(@NotNull SkillCastContext var1);
}
