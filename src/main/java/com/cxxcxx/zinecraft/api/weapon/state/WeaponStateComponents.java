package com.cxxcxx.zinecraft.api.weapon.state;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class WeaponStateComponents {
  private static final DeferredRegister<DataComponentType<?>> REGISTRY = DeferredRegister.create(
      BuiltInRegistries.DATA_COMPONENT_TYPE.key(), Zinecraft.MOD_ID);
  public static final DataComponentType<Integer> AMMO = register("weapon_ammo", DataComponentType.<Integer>builder().persistent(Codec.intRange(0, 4096)).networkSynchronized(ByteBufCodecs.VAR_INT).build());
  public static final DataComponentType<Boolean> AIMING = register("weapon_aiming", DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
  public static final DataComponentType<Integer> FIRE_MODE = register("weapon_fire_mode", DataComponentType.<Integer>builder().persistent(Codec.intRange(0, 3)).networkSynchronized(ByteBufCodecs.VAR_INT).build());
  public static final DataComponentType<Boolean> NEEDS_BOLT = register("weapon_needs_bolt", DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

  private WeaponStateComponents() {
  }

  private static <T> DataComponentType<T> register(String path, DataComponentType<T> type) {
    REGISTRY.register(path, () -> type);
    return type;
  }

  public static void register(IEventBus modBus) {
    REGISTRY.register(modBus);
  }

  public static void bootstrap() {
  }
}
