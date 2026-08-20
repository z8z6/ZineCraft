package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.combat.CombatDamageProfile;
import com.cxxcxx.zinecraft.api.combat.CombatDamageType;
import com.cxxcxx.zinecraft.api.combat.CombatService;
import com.cxxcxx.zinecraft.api.registry.builder.SkillEffectBuilder;
import com.cxxcxx.zinecraft.api.skill.SkillCastContext;
import com.cxxcxx.zinecraft.api.skill.SkillEffect;
import com.cxxcxx.zinecraft.api.weapon.combat.HitscanService;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

/**
 * 原生武器使用的服务端技能效果声明。
 */
public final class ModWeaponSkillEffects {
  public static final SkillEffectBuilder<ArcaneBoltEffect> ARCANE_BOLT = effect(
      "arcane_bolt", ignored -> new ArcaneBoltEffect()
  );
  public static final SkillEffectBuilder<MendingLightEffect> MENDING_LIGHT = effect(
      "mending_light", ignored -> new MendingLightEffect()
  );

  private ModWeaponSkillEffects() {
  }

  private static <T extends SkillEffect> SkillEffectBuilder<T> effect(
      String path,
      Function<ResourceLocation, T> factory
  ) {
    return new SkillEffectBuilder<>(Zinecraft.SKILL_EFFECTS, path, factory).build();
  }

  public static void bootstrap() {
  }

  public static final class ArcaneBoltEffect implements SkillEffect {
    private final CombatDamageProfile damage = CombatDamageProfile.flat(8.0, CombatDamageType.ARTS);

    private ArcaneBoltEffect() {
    }

    @Override
    public List<CombatDamageProfile> damageProfiles() {
      return List.of(damage);
    }

    @Override
    public boolean canCast(@NotNull SkillCastContext context) {
      return context.player().isAlive() && !context.player().isSpectator();
    }

    @Override
    public void cast(@NotNull SkillCastContext context) {
      HitscanService.Hit hit = HitscanService.INSTANCE.trace(context.player(), 24.0, 0.45);
      LivingEntity target = hit == null ? null : hit.getTarget();
      if (target != null) {
        CombatService.INSTANCE.damage(context.player(), target, damage);
      }
    }
  }

  public static final class MendingLightEffect implements SkillEffect {
    private MendingLightEffect() {
    }

    @Override
    public boolean canCast(@NotNull SkillCastContext context) {
      return context.player().isAlive() && context.player().getHealth() < context.player().getMaxHealth();
    }

    @Override
    public void cast(@NotNull SkillCastContext context) {
      CombatService.INSTANCE.heal(context.player(), context.player(), 6.0, 1.0, 0.0);
    }
  }
}
