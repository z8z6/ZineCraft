package com.cxxcxx.zinecraft.api.weapon.action

/** 用于只有单段启动/生效/后摇的动作，避免每种武器重复 tick 状态机。 */
abstract class TimedWeaponActionRuntime(
  private val activeTicks: IntRange,
  private val durationTicks: Int
) : WeaponActionRuntime {
  init {
    require(durationTicks > 0) { "动作持续时间必须大于 0" }
    require(activeTicks.first >= 0 && activeTicks.last < durationTicks) { "生效阶段必须位于动作时间线内" }
  }

  final override var currentTick: Int = 0
    private set

  final override val phase: ActionPhase
    get() = when {
      currentTick < activeTicks.first -> ActionPhase.STARTUP
      currentTick in activeTicks -> ActionPhase.ACTIVE
      currentTick < durationTicks -> ActionPhase.RECOVERY
      else -> ActionPhase.FINISHED
    }

  final override val finished: Boolean
    get() = phase == ActionPhase.FINISHED

  final override fun tick() {
    if (finished) return
    onTick(currentTick)
    currentTick++
  }

  protected abstract fun onTick(tick: Int)
}
