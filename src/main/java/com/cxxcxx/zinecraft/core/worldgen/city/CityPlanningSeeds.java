package com.cxxcxx.zinecraft.core.worldgen.city;

import com.cxxcxx.zinecraft.api.nation.TerraPlace;

import java.util.Objects;

/**
 * 不依赖区块加载顺序的城市规划种子。
 */
public final class CityPlanningSeeds {
  private CityPlanningSeeds() {
  }

  public static long citySeed(long worldSeed, TerraPlace place, long citySalt) {
    Objects.requireNonNull(place, "城市地点不能为空");
    long center = ((long) place.x() << 32) ^ (place.z() & 0xFFFFFFFFL);
    long id = Integer.toUnsignedLong(place.id().hashCode());
    return mix64(worldSeed ^ Long.rotateLeft(center, 21) ^ Long.rotateLeft(id, 43) ^ citySalt);
  }

  public static long lotSeed(long citySeed, String lotId) {
    Objects.requireNonNull(lotId, "地块 ID 不能为空");
    return mix64(citySeed ^ Integer.toUnsignedLong(lotId.hashCode()));
  }

  private static long mix64(long value) {
    value = (value ^ value >>> 30) * 0xBF58476D1CE4E5B9L;
    value = (value ^ value >>> 27) * 0x94D049BB133111EBL;
    return value ^ value >>> 31;
  }
}
