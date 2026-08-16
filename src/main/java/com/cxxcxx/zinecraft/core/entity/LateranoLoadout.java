package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.core.weapon.ModWeapons;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * 为拉特兰人形生物选择可序列化到装备槽的默认铳械。
 */
final class LateranoLoadout {
  private LateranoLoadout() {
  }

  static ItemStack createGun(RandomSource random) {
    return new ItemStack(ModWeapons.INSTANCE.getTEST_RIFLE_ITEM().getItem());
  }
}
