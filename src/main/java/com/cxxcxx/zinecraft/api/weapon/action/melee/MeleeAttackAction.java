package com.cxxcxx.zinecraft.api.weapon.action.melee;

import com.cxxcxx.zinecraft.api.combat.CombatDamageType;
import com.cxxcxx.zinecraft.api.combat.CombatRequest;
import com.cxxcxx.zinecraft.api.combat.CombatService;
import com.cxxcxx.zinecraft.api.weapon.action.ActionPhase;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import com.cxxcxx.zinecraft.api.weapon.combat.MeleeHitboxService;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class MeleeAttackAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;
  private final int hitTick;
  private final int durationTicks;
  private final float damage;
  private final double range;
  private final double arcDegrees;

  public MeleeAttackAction(@NotNull ResourceLocation id, int hitTick, int durationTicks, float damage, double range, double arcDegrees) {
    super();
    this.id = id;
    this.hitTick = hitTick;
    this.durationTicks = durationTicks;
    this.damage = damage;
    this.range = range;
    this.arcDegrees = arcDegrees;
    int i = this.durationTicks;
    int j = this.hitTick;
    if (0 <= j ? j >= i : true) {
      j = 0;
      String string3 = "命中 tick 必须位于动作时间线内";
      throw new IllegalArgumentException(string3.toString());
    }

    if (!(this.damage > 0.0F)) {
      j = 0;
      String string2 = "近战伤害必须大于 0";
      throw new IllegalArgumentException(string2.toString());
    }

    if (!(this.range > 0.0)) {
      j = 0;
      String string1 = "近战范围必须大于 0";
      throw new IllegalArgumentException(string1.toString());
    }

    double d = this.arcDegrees;
    if (0.0 <= d ? !(d <= 360.0) : true) {
      j = 0;
      String string = "近战弧度必须在 0 到 360 度之间";
      throw new IllegalArgumentException(string.toString());
    }
  }

  @NotNull
  @Override
  public ResourceLocation getId() {
    return this.id;
  }

  @Override
  public boolean canStart(@NotNull WeaponContext context) {
    return context.getPlayer().isAlive() && !context.getPlayer().isSpectator() && !context.getPlayer().isUsingItem();
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull WeaponContext context) {
    return new MeleeAttackAction.Runtime(context);
  }

  private final class Runtime implements WeaponActionRuntime {
    @NotNull
    private final WeaponContext context;
    private final int hitTick;
    private final int durationTicks;
    private int currentTick;

    public Runtime(@NotNull WeaponContext context) {
      super();
      this.context = context;
      var timing = CombatService.INSTANCE.actionTiming(context.getPlayer(), MeleeAttackAction.this.hitTick, MeleeAttackAction.this.durationTicks);
      this.hitTick = timing.effectTick();
      this.durationTicks = timing.durationTicks();
    }

    @Override
    public int getCurrentTick() {
      return this.currentTick;
    }

    @NotNull
    @Override
    public ActionPhase getPhase() {
      return this.getCurrentTick() < this.hitTick
          ? ActionPhase.STARTUP
          : (
          this.getCurrentTick() == this.hitTick
          ? ActionPhase.ACTIVE
          : (this.getCurrentTick() < this.durationTicks ? ActionPhase.RECOVERY : ActionPhase.FINISHED)
      );
    }

    @Override
    public boolean getFinished() {
      return this.getPhase() == ActionPhase.FINISHED;
    }

    @Override
    public void tick() {
      if (!this.getFinished()) {
        if (this.getCurrentTick() == this.hitTick) {
          this.performHit();
        }

        int i = this.getCurrentTick();
        this.currentTick = i + 1;
      }
    }

    private final void performHit() {
      ServerPlayer serverPlayer = this.context.getPlayer();
      List<LivingEntity> list = MeleeHitboxService.INSTANCE.findTargets(serverPlayer, MeleeAttackAction.this.range, MeleeAttackAction.this.arcDegrees);
      AttributeInstance attributeInstance = serverPlayer.getAttribute(Attributes.ATTACK_DAMAGE);
      float f = MeleeDamage.resolveActionMeleeDamage(MeleeAttackAction.this.damage, attributeInstance != null ? attributeInstance.getValue() : null);
      boolean bl = false;

      for (LivingEntity livingEntity : list) {
        bl = CombatService.INSTANCE.damageFromResolvedAttack(
            serverPlayer, livingEntity, CombatDamageType.PHYSICAL, f, CombatRequest.DEFAULT
        ) || bl;
      }

      if (bl) {
        this.context.getStack().hurtAndBreak(1, (LivingEntity) serverPlayer, EquipmentSlot.MAINHAND);
      }
    }
  }
}
