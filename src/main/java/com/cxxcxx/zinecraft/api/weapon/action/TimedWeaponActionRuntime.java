package com.cxxcxx.zinecraft.api.weapon.action;

import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

public abstract class TimedWeaponActionRuntime implements WeaponActionRuntime {
  @NotNull
  private final IntRange activeTicks;
  private final int durationTicks;
  private int currentTick;

  public TimedWeaponActionRuntime(@NotNull IntRange activeTicks, int durationTicks) {
    super();
    this.activeTicks = activeTicks;
    this.durationTicks = durationTicks;
    if (this.durationTicks <= 0) {
      int j = 0;
      String string1 = "动作持续时间必须大于 0";
      throw new IllegalArgumentException(string1.toString());
    }

    if (this.activeTicks.getFirst() < 0 || this.activeTicks.getLast() >= this.durationTicks) {
      int i = 0;
      String string = "生效阶段必须位于动作时间线内";
      throw new IllegalArgumentException(string.toString());
    }
  }

  @Override
  public final int getCurrentTick() {
    return this.currentTick;
  }

  @NotNull
  @Override
  public final ActionPhase getPhase() {
    ActionPhase actionPhase;
    if (this.currentTick < this.activeTicks.getFirst()) {
      actionPhase = ActionPhase.STARTUP;
    } else {
      IntRange intRange = this.activeTicks;
      int i = intRange.getFirst();
      int j = intRange.getLast();
      int k = this.currentTick;
      actionPhase = (i <= k ? k <= j : false) ? ActionPhase.ACTIVE : (this.currentTick < this.durationTicks ? ActionPhase.RECOVERY : ActionPhase.FINISHED);
    }

    return actionPhase;
  }

  @Override
  public final boolean getFinished() {
    return this.getPhase() == ActionPhase.FINISHED;
  }

  @Override
  public final void tick() {
    if (!this.getFinished()) {
      this.onTick(this.currentTick);
      int i = this.currentTick++;
    }
  }

  protected abstract void onTick(int var1);
}

