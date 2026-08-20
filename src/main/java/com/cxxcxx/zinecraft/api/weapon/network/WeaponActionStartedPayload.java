package com.cxxcxx.zinecraft.api.weapon.network;

import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WeaponActionStartedPayload implements CustomPacketPayload {
  @NotNull
  public static final WeaponActionStartedPayload.Access ACCESS = new WeaponActionStartedPayload.Access();
  @NotNull
  private static final Type<WeaponActionStartedPayload> TYPE = new Type(Zinecraft.id("weapon_action_started"));
  @NotNull
  private static final StreamCodec<RegistryFriendlyByteBuf, WeaponActionStartedPayload> CODEC = new StreamCodec<RegistryFriendlyByteBuf, WeaponActionStartedPayload>() {
    public WeaponActionStartedPayload decode(RegistryFriendlyByteBuf buffer) {
      int i = buffer.readVarInt();
      ResourceLocation resourceLocation = buffer.readResourceLocation();
      ResourceLocation resourceLocation1 = buffer.readResourceLocation();
      return new WeaponActionStartedPayload(i, resourceLocation, resourceLocation1, buffer.readVarLong());
    }

    public void encode(RegistryFriendlyByteBuf buffer, WeaponActionStartedPayload payload) {
      buffer.writeVarInt(payload.getEntityId());
      buffer.writeResourceLocation(payload.getWeaponId());
      buffer.writeResourceLocation(payload.getActionId());
      buffer.writeVarLong(payload.getStartGameTick());
    }
  };
  private final int entityId;
  @NotNull
  private final ResourceLocation weaponId;
  @NotNull
  private final ResourceLocation actionId;
  private final long startGameTick;

  public WeaponActionStartedPayload(int entityId, @NotNull ResourceLocation weaponId, @NotNull ResourceLocation actionId, long startGameTick) {
    super();
    this.entityId = entityId;
    this.weaponId = weaponId;
    this.actionId = actionId;
    this.startGameTick = startGameTick;
  }

  public final int getEntityId() {
    return this.entityId;
  }

  @NotNull
  public final ResourceLocation getWeaponId() {
    return this.weaponId;
  }

  @NotNull
  public final ResourceLocation getActionId() {
    return this.actionId;
  }

  public final long getStartGameTick() {
    return this.startGameTick;
  }

  @NotNull
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    int i = Integer.hashCode(this.entityId);
    i = i * 31 + this.weaponId.hashCode();
    i = i * 31 + this.actionId.hashCode();
    return i * 31 + Long.hashCode(this.startGameTick);
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof WeaponActionStartedPayload weaponActionStartedPayload)) {
      return false;
    } else if (this.entityId != weaponActionStartedPayload.entityId) {
      return false;
    } else if (!java.util.Objects.equals(this.weaponId, weaponActionStartedPayload.weaponId)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.actionId, weaponActionStartedPayload.actionId)
          ? false
          : this.startGameTick == weaponActionStartedPayload.startGameTick;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "WeaponActionStartedPayload(entityId="
        + this.entityId
        + ", weaponId="
        + this.weaponId
        + ", actionId="
        + this.actionId
        + ", startGameTick="
        + this.startGameTick
        + ")";
  }

  public static final class Access {
    private Access() {
    }

    @NotNull
    public final Type<WeaponActionStartedPayload> getTYPE() {
      return WeaponActionStartedPayload.TYPE;
    }

    @NotNull
    public final StreamCodec<RegistryFriendlyByteBuf, WeaponActionStartedPayload> getCODEC() {
      return WeaponActionStartedPayload.CODEC;
    }
  }
}
