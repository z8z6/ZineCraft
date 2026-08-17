package com.cxxcxx.zinecraft.core.weapon;

import com.cxxcxx.zinecraft.api.combat.CombatDamageType;
import com.cxxcxx.zinecraft.api.combat.CombatRequest;
import com.cxxcxx.zinecraft.api.combat.CombatService;
import com.cxxcxx.zinecraft.api.skill.SkillCastContext;
import com.cxxcxx.zinecraft.api.skill.SkillEffect;
import com.cxxcxx.zinecraft.api.weapon.combat.HitscanService;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public final class ModWeaponSkillEffects {
  @NotNull
  public static final ModWeaponSkillEffects INSTANCE = new ModWeaponSkillEffects();
  @NotNull
  public static final ResourceLocation ARCANE_BOLT = Zinecraft.REGISTRAR.id("skill/arcane_bolt");
  @NotNull
  public static final ResourceLocation MENDING_LIGHT = Zinecraft.REGISTRAR.id("skill/mending_light");

  static {
    Zinecraft.SKILL_SERVICE.register(ARCANE_BOLT, new SkillEffect() {
      @Override
      public boolean canCast(SkillCastContext context) {
        return context.getPlayer().isAlive() && !context.getPlayer().isSpectator();
      }

      @Override
      public void cast(SkillCastContext context) {
        HitscanService.Hit hit = HitscanService.INSTANCE.trace(context.getPlayer(), 24.0, 0.45);
        if (hit != null) {
          LivingEntity livingEntity1 = hit.getTarget();
          if (livingEntity1 != null) {
            LivingEntity livingEntity = livingEntity1;
            CombatService.INSTANCE.damage(
                context.getPlayer(), livingEntity, CombatDamageType.ARTS, 8.0, CombatRequest.DEFAULT
            );
            return;
          }
        }
      }
    });
    Zinecraft.SKILL_SERVICE.register(MENDING_LIGHT, new SkillEffect() {
      @Override
      public boolean canCast(SkillCastContext context) {
        return context.getPlayer().isAlive() && context.getPlayer().getHealth() < context.getPlayer().getMaxHealth();
      }

      @Override
      public void cast(SkillCastContext context) {
        CombatService.INSTANCE.heal(context.getPlayer(), context.getPlayer(), 6.0, 1.0, 0.0);
      }
    });
  }

  private ModWeaponSkillEffects() {
  }

}
