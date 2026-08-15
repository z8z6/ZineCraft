package com.cxxcxx.zinecraft.api.weapon.action.staff;

import com.cxxcxx.zinecraft.api.skill.SkillCastContext;
import com.cxxcxx.zinecraft.api.skill.SkillService;
import com.cxxcxx.zinecraft.api.weapon.action.TimedWeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import kotlin.ranges.IntRange;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class CastSkillAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;
  @NotNull
  private final ResourceLocation skillId;
  @NotNull
  private final SkillService skillService;
  private final int castTick;
  private final int durationTicks;

  public CastSkillAction(@NotNull ResourceLocation id, @NotNull ResourceLocation skillId, @NotNull SkillService skillService, int castTick, int durationTicks) {
    super();
    this.id = id;
    this.skillId = skillId;
    this.skillService = skillService;
    this.castTick = castTick;
    this.durationTicks = durationTicks;
    int i = this.durationTicks;
    int j = this.castTick;
    if (0 <= j ? j >= i : true) {
      j = 0;
      String string = "施法 tick 必须位于动作时间线内";
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
    return this.skillService.canCast(this.skillId, this.toSkillContext(context));
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    IntRange intRange = new IntRange(this.castTick, this.castTick);
    int i = this.durationTicks;
    return new TimedWeaponActionRuntime(intRange, i) {
      @Override
      protected void onTick(int tick) {
        if (tick == CastSkillAction.this.castTick) {
          CastSkillAction.this.skillService.cast(CastSkillAction.this.skillId, CastSkillAction.this.toSkillContext(context));
        }
      }
    };
  }

  private final SkillCastContext toSkillContext(WeaponContext $this$toSkillContext) {
    return new SkillCastContext($this$toSkillContext.getPlayer(), $this$toSkillContext.getStack(), $this$toSkillContext.getHand());
  }
}

