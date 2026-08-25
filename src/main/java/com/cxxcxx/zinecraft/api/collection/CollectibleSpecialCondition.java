package com.cxxcxx.zinecraft.api.collection;

import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

/** 保存集成战略运行时为实体选择的多档藏品特殊条件。 */
public final class CollectibleSpecialCondition {
  public static final int MAX_TIER = 3;
  private static final String TIER_KEY = "zinecraft.collectible_effect_tier";

  private CollectibleSpecialCondition() {
  }

  /** 未设置时返回基础档 0。 */
  public static int tier(LivingEntity entity) {
    Objects.requireNonNull(entity, "entity");
    return Math.clamp(entity.getPersistentData().getInt(TIER_KEY), 0, MAX_TIER);
  }

  /** 由服务端集成战略运行时在特殊条件变化时调用。 */
  public static void setTier(LivingEntity entity, int tier) {
    Objects.requireNonNull(entity, "entity");
    if (tier < 0 || tier > MAX_TIER) {
      throw new IllegalArgumentException("藏品特殊条件档位必须在 0.." + MAX_TIER + "：" + tier);
    }
    if (tier == 0) entity.getPersistentData().remove(TIER_KEY);
    else entity.getPersistentData().putInt(TIER_KEY, tier);
  }
}
