package com.cxxcxx.zinecraft.api.weapon.network;

import com.cxxcxx.zinecraft.api.weapon.WeaponInput;
import com.cxxcxx.zinecraft.core.Zinecraft;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WeaponActionRequestPayload implements CustomPacketPayload {
  @NotNull
  public static final WeaponActionRequestPayload.Companion Companion = new WeaponActionRequestPayload.Companion(null);
  @NotNull
  private static final Type<WeaponActionRequestPayload> TYPE = new Type(Zinecraft.INSTANCE.getREGISTRAR().id("weapon_action_request"));
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

  // $VF: synthetic method
  public static WeaponActionRequestPayload copy$default(WeaponActionRequestPayload var0, WeaponInput var1, int var2, Object var3) {
    if ((var2 & 1) != 0) {
      var1 = var0.input;
    }

    return var0.copy(var1);
  }

  @NotNull
  public final WeaponInput getInput() {
    return this.input;
  }

  @NotNull
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  @NotNull
  public final WeaponInput component1() {
    return this.input;
  }

  @NotNull
  public final WeaponActionRequestPayload copy(@NotNull WeaponInput input) {
    return new WeaponActionRequestPayload(input);
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

  public static final class Companion {
    private Companion() {
    }

    // $VF: synthetic method
    public Companion(DefaultConstructorMarker $constructor_marker) {
      this();
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

