package com.cxxcxx.zinecraft.api.skill;

import com.cxxcxx.zinecraft.api.combat.CombatDamageProfile;
import com.cxxcxx.zinecraft.api.combat.CombatDamageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface SkillEffect extends CombatDamageProvider {
  @Override
  default List<CombatDamageProfile> damageProfiles() {
    return List.of();
  }

  boolean canCast(@NotNull SkillCastContext var1);

  void cast(@NotNull SkillCastContext var1);
}
