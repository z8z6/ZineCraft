package com.cxxcxx.zinecraft.api.weapon.state;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;

public final class WeaponStateComponents {
  public static final WeaponStateComponents INSTANCE = new WeaponStateComponents();
  public static final DataComponentType<Integer> AMMO = register("weapon_ammo", DataComponentType.<Integer>builder().persistent(Codec.intRange(0, 4096)).networkSynchronized(ByteBufCodecs.VAR_INT).build());
  public static final DataComponentType<Boolean> AIMING = register("weapon_aiming", DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
  public static final DataComponentType<Integer> FIRE_MODE = register("weapon_fire_mode", DataComponentType.<Integer>builder().persistent(Codec.intRange(0, 3)).networkSynchronized(ByteBufCodecs.VAR_INT).build());
  public static final DataComponentType<Boolean> NEEDS_BOLT = register("weapon_needs_bolt", DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
  public static final DataComponentType<ResourceLocation> TACZ_GUN_ID = register("tacz_gun_id", DataComponentType.<ResourceLocation>builder().persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).build());
  public static final DataComponentType<ResourceLocation> TACZ_AMMO_ID = register("tacz_ammo_id", DataComponentType.<ResourceLocation>builder().persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).build());

  private WeaponStateComponents() {
  }

  private static <T> DataComponentType<T> register(String path, DataComponentType<T> type) {
    return Zinecraft.INSTANCE.getREGISTRAR().register(BuiltInRegistries.DATA_COMPONENT_TYPE, path, type);
  }

  public DataComponentType<Integer> getAMMO() {
    return AMMO;
  }

  public DataComponentType<Boolean> getAIMING() {
    return AIMING;
  }

  public DataComponentType<Integer> getFIRE_MODE() {
    return FIRE_MODE;
  }

  public DataComponentType<Boolean> getNEEDS_BOLT() {
    return NEEDS_BOLT;
  }

  public DataComponentType<ResourceLocation> getTACZ_GUN_ID() {
    return TACZ_GUN_ID;
  }

  public DataComponentType<ResourceLocation> getTACZ_AMMO_ID() {
    return TACZ_AMMO_ID;
  }
}
