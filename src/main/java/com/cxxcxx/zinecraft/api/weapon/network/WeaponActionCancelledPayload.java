package com.cxxcxx.zinecraft.api.weapon.network;

import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WeaponActionCancelledPayload implements CustomPacketPayload {
  @NotNull
  public static final WeaponActionCancelledPayload.Access ACCESS = new WeaponActionCancelledPayload.Access();
  @NotNull
  private static final Type<WeaponActionCancelledPayload> TYPE = new Type(Zinecraft.INSTANCE.getREGISTRAR().id("weapon_action_cancelled"));
  @NotNull
  private static final StreamCodec<RegistryFriendlyByteBuf, WeaponActionCancelledPayload> CODEC = new StreamCodec<RegistryFriendlyByteBuf, WeaponActionCancelledPayload>() {
    public WeaponActionCancelledPayload decode(RegistryFriendlyByteBuf buffer) {
      int i = buffer.readVarInt();
      ResourceLocation resourceLocation = buffer.readResourceLocation();
      return new WeaponActionCancelledPayload(i, resourceLocation);
    }

    public void encode(RegistryFriendlyByteBuf buffer, WeaponActionCancelledPayload payload) {
      buffer.writeVarInt(payload.getEntityId());
      buffer.writeResourceLocation(payload.getActionId());
    }
  };
  private final int entityId;
  @NotNull
  private final ResourceLocation actionId;

  public WeaponActionCancelledPayload(int entityId, @NotNull ResourceLocation actionId) {
    super();
    this.entityId = entityId;
    this.actionId = actionId;
  }

  public final int getEntityId() {
    return this.entityId;
  }

  @NotNull
  public final ResourceLocation getActionId() {
    return this.actionId;
  }

  @NotNull
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    int i = Integer.hashCode(this.entityId);
    return i * 31 + this.actionId.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof WeaponActionCancelledPayload weaponActionCancelledPayload)) {
      return false;
    } else {
      return this.entityId != weaponActionCancelledPayload.entityId ? false : java.util.Objects.equals(this.actionId, weaponActionCancelledPayload.actionId);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "WeaponActionCancelledPayload(entityId=" + this.entityId + ", actionId=" + this.actionId + ")";
  }

  public static final class Access {
    private Access() {
    }

    @NotNull
    public final Type<WeaponActionCancelledPayload> getTYPE() {
      return WeaponActionCancelledPayload.TYPE;
    }

    @NotNull
    public final StreamCodec<RegistryFriendlyByteBuf, WeaponActionCancelledPayload> getCODEC() {
      return WeaponActionCancelledPayload.CODEC;
    }
  }
}
