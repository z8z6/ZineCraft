package com.cxxcxx.zinecraft.api.weapon.network

import com.cxxcxx.zinecraft.api.weapon.WeaponInput
import com.cxxcxx.zinecraft.core.Zinecraft
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation

data class WeaponActionRequestPayload(val input: WeaponInput) : CustomPacketPayload {
  override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE

  companion object {
    val TYPE = CustomPacketPayload.Type<WeaponActionRequestPayload>(Zinecraft.REGISTRAR.id("weapon_action_request"))
    val CODEC = object : StreamCodec<RegistryFriendlyByteBuf, WeaponActionRequestPayload> {
      override fun decode(buffer: RegistryFriendlyByteBuf) =
        WeaponActionRequestPayload(buffer.readEnum(WeaponInput::class.java))

      override fun encode(buffer: RegistryFriendlyByteBuf, payload: WeaponActionRequestPayload) {
        buffer.writeEnum(payload.input)
      }
    }
  }
}

data class WeaponActionStartedPayload(
  val entityId: Int,
  val weaponId: ResourceLocation,
  val actionId: ResourceLocation,
  val startGameTick: Long
) : CustomPacketPayload {
  override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE

  companion object {
    val TYPE = CustomPacketPayload.Type<WeaponActionStartedPayload>(Zinecraft.REGISTRAR.id("weapon_action_started"))
    val CODEC = object : StreamCodec<RegistryFriendlyByteBuf, WeaponActionStartedPayload> {
      override fun decode(buffer: RegistryFriendlyByteBuf) = WeaponActionStartedPayload(
        buffer.readVarInt(),
        buffer.readResourceLocation(),
        buffer.readResourceLocation(),
        buffer.readVarLong()
      )

      override fun encode(buffer: RegistryFriendlyByteBuf, payload: WeaponActionStartedPayload) {
        buffer.writeVarInt(payload.entityId)
        buffer.writeResourceLocation(payload.weaponId)
        buffer.writeResourceLocation(payload.actionId)
        buffer.writeVarLong(payload.startGameTick)
      }
    }
  }
}

data class WeaponActionCancelledPayload(
  val entityId: Int,
  val actionId: ResourceLocation
) : CustomPacketPayload {
  override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE

  companion object {
    val TYPE = CustomPacketPayload.Type<WeaponActionCancelledPayload>(Zinecraft.REGISTRAR.id("weapon_action_cancelled"))
    val CODEC = object : StreamCodec<RegistryFriendlyByteBuf, WeaponActionCancelledPayload> {
      override fun decode(buffer: RegistryFriendlyByteBuf) =
        WeaponActionCancelledPayload(buffer.readVarInt(), buffer.readResourceLocation())

      override fun encode(buffer: RegistryFriendlyByteBuf, payload: WeaponActionCancelledPayload) {
        buffer.writeVarInt(payload.entityId)
        buffer.writeResourceLocation(payload.actionId)
      }
    }
  }
}

object WeaponPayloadTypes {
  fun register() {
    PayloadTypeRegistry.playC2S().register(WeaponActionRequestPayload.TYPE, WeaponActionRequestPayload.CODEC)
    PayloadTypeRegistry.playS2C().register(WeaponActionStartedPayload.TYPE, WeaponActionStartedPayload.CODEC)
    PayloadTypeRegistry.playS2C().register(WeaponActionCancelledPayload.TYPE, WeaponActionCancelledPayload.CODEC)
  }
}
