package com.cxxcxx.zinecraft.api.weapon.state

import com.cxxcxx.zinecraft.core.Zinecraft
import com.mojang.serialization.Codec
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.resources.ResourceLocation

/** 仅保存具体 ItemStack 的持久状态；动作 Runtime 不写入物品组件。 */
object WeaponStateComponents {
  val AMMO: DataComponentType<Int> = Zinecraft.REGISTRAR.register(
    BuiltInRegistries.DATA_COMPONENT_TYPE,
    "weapon_ammo",
    DataComponentType.builder<Int>()
      .persistent(Codec.intRange(0, 4096))
      .networkSynchronized(ByteBufCodecs.VAR_INT)
      .build()
  )

  val AIMING: DataComponentType<Boolean> = Zinecraft.REGISTRAR.register(
    BuiltInRegistries.DATA_COMPONENT_TYPE,
    "weapon_aiming",
    DataComponentType.builder<Boolean>()
      .persistent(Codec.BOOL)
      .networkSynchronized(ByteBufCodecs.BOOL)
      .build()
  )

  /** TaCZ fire-mode ordinal: auto, semi, burst, unknown. */
  val FIRE_MODE: DataComponentType<Int> = Zinecraft.REGISTRAR.register(
    BuiltInRegistries.DATA_COMPONENT_TYPE,
    "weapon_fire_mode",
    DataComponentType.builder<Int>()
      .persistent(Codec.intRange(0, 3))
      .networkSynchronized(ByteBufCodecs.VAR_INT)
      .build()
  )

  val NEEDS_BOLT: DataComponentType<Boolean> = Zinecraft.REGISTRAR.register(
    BuiltInRegistries.DATA_COMPONENT_TYPE,
    "weapon_needs_bolt",
    DataComponentType.builder<Boolean>()
      .persistent(Codec.BOOL)
      .networkSynchronized(ByteBufCodecs.BOOL)
      .build()
  )

  /** TaCZ uses one registered item plus this identifier for every external gun definition. */
  val TACZ_GUN_ID: DataComponentType<ResourceLocation> = Zinecraft.REGISTRAR.register(
    BuiltInRegistries.DATA_COMPONENT_TYPE,
    "tacz_gun_id",
    DataComponentType.builder<ResourceLocation>()
      .persistent(ResourceLocation.CODEC)
      .networkSynchronized(ResourceLocation.STREAM_CODEC)
      .build()
  )

  /** Identifies an external TaCZ ammunition stack without dynamically mutating the item registry. */
  val TACZ_AMMO_ID: DataComponentType<ResourceLocation> = Zinecraft.REGISTRAR.register(
    BuiltInRegistries.DATA_COMPONENT_TYPE,
    "tacz_ammo_id",
    DataComponentType.builder<ResourceLocation>()
      .persistent(ResourceLocation.CODEC)
      .networkSynchronized(ResourceLocation.STREAM_CODEC)
      .build()
  )
}
