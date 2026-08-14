package com.cxxcxx.zinecraft.api.weapon

import net.minecraft.resources.ResourceLocation

enum class WeaponInput {
  PRIMARY,
  SECONDARY,
  RELOAD,
  SPECIAL,
  FIRE_SELECT,
  INSPECT,
  MELEE,
  BOLT
}

/** 不含任何 ItemStack 运行状态的一种武器静态定义。 */
data class WeaponDefinition(
  val id: ResourceLocation,
  val actions: Map<WeaponInput, ResourceLocation>,
  val presentations: Map<ResourceLocation, WeaponPresentation> = emptyMap(),
  val metadata: WeaponMetadata
) {
  init {
    require(actions.isNotEmpty()) { "武器至少需要绑定一个动作" }
    require(metadata.translationKey.isNotBlank()) { "武器翻译键不能为空" }
    require(presentations.keys.all(actions.values::contains)) { "表现只能引用该武器已绑定的动作" }
  }

  fun action(input: WeaponInput): ResourceLocation? = actions[input]

  fun presentation(actionId: ResourceLocation): WeaponPresentation? = presentations[actionId]
}

data class WeaponMetadata(
  val translationKey: String
)

/** 客户端从服务端开始时间重放的确定性表现时间线。 */
data class WeaponPresentation(
  val playerAnimation: ResourceLocation? = null,
  val weaponAnimation: ResourceLocation? = null,
  val vfx: List<TimedWeaponVfx> = emptyList(),
  val sounds: List<TimedWeaponSound> = emptyList(),
  val durationTicks: Int
) {
  init {
    require(durationTicks > 0) { "表现持续时间必须大于 0" }
    require(vfx.all { it.tick in 0 until durationTicks }) { "特效时间必须位于表现时间线内" }
    require(sounds.all { it.tick in 0 until durationTicks }) { "声音时间必须位于表现时间线内" }
  }
}

data class TimedWeaponVfx(val id: ResourceLocation, val tick: Int)

data class TimedWeaponSound(val id: ResourceLocation, val tick: Int)
