package com.cxxcxx.zinecraft.api.weapon.network;

import com.cxxcxx.zinecraft.api.weapon.WeaponInput;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WeaponActionRequestPayload implements CustomPacketPayload {
  @NotNull
  public static final WeaponActionRequestPayload.Access ACCESS = new WeaponActionRequestPayload.Access();
  @NotNull
  private static final Type<WeaponActionRequestPayload> TYPE = new Type(Zinecraft.id("weapon_action_request"));
  @NotNull
  private static final StreamCodec<RegistryFriendlyByteBuf, WeaponActionRequestPayload> CODEC = new StreamCodec<RegistryFriendlyByteBuf, WeaponActionRequestPayload>() {
    public WeaponActionRequestPayload decode(RegistryFriendlyByteBuf buffer) {
      Enum enum_ = buffer.readEnum(WeaponInput.class);
      return new WeaponActionRequestPayload((WeaponInput) enum_);
    }

    public void encode(RegistryFriendlyByteBuf buffer, WeaponActionRequestPayload payload) {
      buffer.writeEnum(payload.getInput());
    }
  };
  @NotNull
  private final WeaponInput input;

  public WeaponActionRequestPayload(@NotNull WeaponInput input) {
    super();
    this.input = input;
  }

  @NotNull
  public final WeaponInput getInput() {
    return this.input;
  }

  @NotNull
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return this.input.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else {
      return !(other instanceof WeaponActionRequestPayload weaponActionRequestPayload) ? false : this.input == weaponActionRequestPayload.input;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "WeaponActionRequestPayload(input=" + this.input + ")";
  }

  public static final class Access {
    private Access() {
    }

    @NotNull
    public final Type<WeaponActionRequestPayload> getTYPE() {
      return WeaponActionRequestPayload.TYPE;
    }

    @NotNull
    public final StreamCodec<RegistryFriendlyByteBuf, WeaponActionRequestPayload> getCODEC() {
      return WeaponActionRequestPayload.CODEC;
    }
  }
}
