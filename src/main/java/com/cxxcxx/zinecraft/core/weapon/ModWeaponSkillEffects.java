package com.cxxcxx.zinecraft.core.weapon;

import com.cxxcxx.zinecraft.api.skill.SkillCastContext;
import com.cxxcxx.zinecraft.api.skill.SkillEffect;
import com.cxxcxx.zinecraft.api.weapon.combat.HitscanService;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public final class ModWeaponSkillEffects {
  @NotNull
  public static final ModWeaponSkillEffects INSTANCE = new ModWeaponSkillEffects();
  @NotNull
  private static final ResourceLocation ARCANE_BOLT = Zinecraft.INSTANCE.getREGISTRAR().id("skill/arcane_bolt");
  @NotNull
  private static final ResourceLocation MENDING_LIGHT = Zinecraft.INSTANCE.getREGISTRAR().id("skill/mending_light");

  static {
    Zinecraft.INSTANCE.getSKILL_SERVICE().register(ARCANE_BOLT, new SkillEffect() {
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
            livingEntity.hurt(context.getPlayer().damageSources().indirectMagic((Entity) context.getPlayer(), (Entity) context.getPlayer()), 8.0F);
            return;
          }
        }
      }
    });
    Zinecraft.INSTANCE.getSKILL_SERVICE().register(MENDING_LIGHT, new SkillEffect() {
      @Override
      public boolean canCast(SkillCastContext context) {
        return context.getPlayer().isAlive() && context.getPlayer().getHealth() < context.getPlayer().getMaxHealth();
      }

      @Override
      public void cast(SkillCastContext context) {
        context.getPlayer().heal(6.0F);
      }
    });
  }

  private ModWeaponSkillEffects() {
  }

  @NotNull
  public final ResourceLocation getARCANE_BOLT() {
    return ARCANE_BOLT;
  }

  @NotNull
  public final ResourceLocation getMENDING_LIGHT() {
    return MENDING_LIGHT;
  }
}

