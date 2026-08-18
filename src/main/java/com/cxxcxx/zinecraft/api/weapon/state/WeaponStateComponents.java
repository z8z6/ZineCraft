package com.cxxcxx.zinecraft.api.weapon.state;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

public final class WeaponStateComponents {
  public static final DataComponentType<Integer> AMMO = register("weapon_ammo", DataComponentType.<Integer>builder().persistent(Codec.intRange(0, 4096)).networkSynchronized(ByteBufCodecs.VAR_INT).build());
  public static final DataComponentType<Boolean> AIMING = register("weapon_aiming", DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
  public static final DataComponentType<Integer> FIRE_MODE = register("weapon_fire_mode", DataComponentType.<Integer>builder().persistent(Codec.intRange(0, 3)).networkSynchronized(ByteBufCodecs.VAR_INT).build());
  public static final DataComponentType<Boolean> NEEDS_BOLT = register("weapon_needs_bolt", DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

  private WeaponStateComponents() {
  }

  private static <T> DataComponentType<T> register(String path, DataComponentType<T> type) {
    return Zinecraft.REGISTRAR.register(BuiltInRegistries.DATA_COMPONENT_TYPE, path, type);
  }

  public static void bootstrap() {
  }
}
