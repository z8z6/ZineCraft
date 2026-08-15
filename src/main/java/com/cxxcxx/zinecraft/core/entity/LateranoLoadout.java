package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks;
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunSpec;
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons;
import com.cxxcxx.zinecraft.core.weapon.ModWeapons;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * 为拉特兰人形生物选择可序列化到装备槽的默认铳械。
 */
final class LateranoLoadout {
  private LateranoLoadout() {
  }

  static ItemStack createGun(RandomSource random) {
    var guns = new ArrayList<>(TaczGunPacks.INSTANCE.getSnapshot().getGuns().values());
    guns.sort(Comparator.comparingInt(TaczGunSpec::getSort).thenComparing(gun -> gun.getId().toString()));
    List<TaczGunSpec> pistols = guns.stream().filter(gun -> {
      var type = gun.getType().toLowerCase(Locale.ROOT);
      return type.contains("pistol") || type.contains("handgun");
    }).toList();
    var candidates = pistols.isEmpty() ? guns : pistols;
    if (!candidates.isEmpty()) {
      return ModTaczWeapons.INSTANCE.gunStack(candidates.get(random.nextInt(candidates.size())));
    }
    // 外置枪包不是服务端启动的硬依赖；没有 TaCZ 枪包时仍必须满足默认持枪语义。
    return new ItemStack(ModWeapons.INSTANCE.getTEST_RIFLE_ITEM().getItem());
  }
}
