package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.combat.CombatDamageProfile;
import com.cxxcxx.zinecraft.api.combat.CombatDamageType;
import com.cxxcxx.zinecraft.api.combat.CombatService;
import com.cxxcxx.zinecraft.api.skill.SkillCastContext;
import com.cxxcxx.zinecraft.api.skill.SkillEffect;
import com.cxxcxx.zinecraft.api.weapon.combat.HitscanService;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ModWeaponSkillEffects {
  @NotNull
  public static final ModWeaponSkillEffects INSTANCE = new ModWeaponSkillEffects();
  @NotNull
  public static final ResourceLocation ARCANE_BOLT = Zinecraft.id("skill/arcane_bolt");
  @NotNull
  public static final ResourceLocation MENDING_LIGHT = Zinecraft.id("skill/mending_light");

  static {
    Zinecraft.SKILL_SERVICE.register(ARCANE_BOLT, new SkillEffect() {
      private final CombatDamageProfile damage = CombatDamageProfile.flat(8.0, CombatDamageType.ARTS);

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
        if (hit != null) {
          LivingEntity livingEntity1 = hit.getTarget();
          if (livingEntity1 != null) {
            LivingEntity livingEntity = livingEntity1;
            CombatService.INSTANCE.damage(context.player(), livingEntity, damage);
            return;
          }
        }
      }
    });
    Zinecraft.SKILL_SERVICE.register(MENDING_LIGHT, new SkillEffect() {
      @Override
      public boolean canCast(@NotNull SkillCastContext context) {
        return context.player().isAlive() && context.player().getHealth() < context.player().getMaxHealth();
      }

      @Override
      public void cast(@NotNull SkillCastContext context) {
        CombatService.INSTANCE.heal(context.player(), context.player(), 6.0, 1.0, 0.0);
      }
    });
  }

  private ModWeaponSkillEffects() {
  }

}
