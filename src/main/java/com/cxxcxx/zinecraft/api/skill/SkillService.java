package com.cxxcxx.zinecraft.api.skill;

import com.cxxcxx.zinecraft.api.combat.CombatDamageProfile;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class SkillService {
  @NotNull
  private final Map<ResourceLocation, SkillEffect> effects = new LinkedHashMap<>();

  @NotNull
  public ResourceLocation register(@NotNull ResourceLocation id, @NotNull SkillEffect effect) {
    if (this.effects.putIfAbsent(id, effect) != null) {
      int i = 0;
      String string = "重复的技能效果 ID：" + id;
      throw new IllegalArgumentException(string.toString());
    } else {
      return id;
    }
  }

  public boolean canCast(@NotNull ResourceLocation id, @NotNull SkillCastContext context) {
    SkillEffect skillEffect = this.effects.get(id);
    return skillEffect != null && skillEffect.canCast(context);
  }

  /**
   * @return 已注册技能效果的全部直接伤害段；效果不存在或不直接造成伤害时为空列表
   */
  public List<CombatDamageProfile> damageProfiles(@NotNull ResourceLocation id) {
    SkillEffect effect = effects.get(id);
    return effect == null ? List.of() : effect.damageProfiles();
  }

  public boolean cast(@NotNull ResourceLocation id, @NotNull SkillCastContext context) {
    SkillEffect skillEffect = this.effects.get(id);
    if (skillEffect == null) {
      return false;
    }

    if (!skillEffect.canCast(context)) {
      return false;
    }

    skillEffect.cast(context);
    return true;
  }
}

