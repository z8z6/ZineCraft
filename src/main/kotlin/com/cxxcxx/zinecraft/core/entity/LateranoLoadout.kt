package com.cxxcxx.zinecraft.core.entity

import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunSpec
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons
import com.cxxcxx.zinecraft.core.weapon.ModWeapons
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack
import java.util.Locale

/** 为拉特兰人形生物选择可序列化到装备槽的默认铳械。 */
internal object LateranoLoadout {
  fun createGun(random: RandomSource): ItemStack {
    val guns = TaczGunPacks.snapshot.guns.values.sortedWith(
      compareBy(TaczGunSpec::sort, { it.id.toString() })
    )
    val pistols = guns.filter { gun ->
      val type = gun.type.lowercase(Locale.ROOT)
      type.contains("pistol") || type.contains("handgun")
    }
    val candidates = pistols.ifEmpty { guns }
    return if (candidates.isNotEmpty()) {
      ModTaczWeapons.gunStack(candidates[random.nextInt(candidates.size)])
    } else {
      // 外置枪包不是服务端启动的硬依赖；没有 TaCZ 枪包时仍必须满足默认持枪语义。
      ItemStack(ModWeapons.TEST_RIFLE_ITEM.item)
    }
  }
}
