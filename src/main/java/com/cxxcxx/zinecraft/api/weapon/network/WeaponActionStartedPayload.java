package com.cxxcxx.zinecraft.api.weapon.network;

import com.cxxcxx.zinecraft.core.Zinecraft;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WeaponActionStartedPayload implements CustomPacketPayload {
  @NotNull
  public static final WeaponActionStartedPayload.Companion Companion = new WeaponActionStartedPayload.Companion(null);
  @NotNull
  private static final Type<WeaponActionStartedPayload> TYPE = new Type(Zinecraft.INSTANCE.getREGISTRAR().id("weapon_action_started"));
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

  // $VF: synthetic method
  public static WeaponActionStartedPayload copy$default(
      WeaponActionStartedPayload var0, int var1, ResourceLocation var2, ResourceLocation var3, long var4, int var6, Object var7
  ) {
    if ((var6 & 1) != 0) {
      var1 = var0.entityId;
    }

    if ((var6 & 2) != 0) {
      var2 = var0.weaponId;
    }

    if ((var6 & 4) != 0) {
      var3 = var0.actionId;
    }

    if ((var6 & 8) != 0) {
      var4 = var0.startGameTick;
    }

    return var0.copy(var1, var2, var3, var4);
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

  public final int component1() {
    return this.entityId;
  }

  @NotNull
  public final ResourceLocation component2() {
    return this.weaponId;
  }

  @NotNull
  public final ResourceLocation component3() {
    return this.actionId;
  }

  public final long component4() {
    return this.startGameTick;
  }

  @NotNull
  public final WeaponActionStartedPayload copy(int entityId, @NotNull ResourceLocation weaponId, @NotNull ResourceLocation actionId, long startGameTick) {
    return new WeaponActionStartedPayload(entityId, weaponId, actionId, startGameTick);
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

  public static final class Companion {
    private Companion() {
    }

    // $VF: synthetic method
    public Companion(DefaultConstructorMarker $constructor_marker) {
      this();
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

