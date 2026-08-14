package com.cxxcxx.zinecraft.api.world.dimension

import net.minecraft.tags.BlockTags
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.dimension.BuiltinDimensionTypes
import net.minecraft.world.level.dimension.DimensionType
import java.util.OptionalLong

/** 构造维度动态注册表值时使用的无状态辅助方法。 */
object DimensionHelper {
  fun overworldLikeType(): DimensionType = DimensionType(
    OptionalLong.empty(),
    true,
    false,
    false,
    true,
    1.0,
    true,
    false,
    -64,
    384,
    384,
    BlockTags.INFINIBURN_OVERWORLD,
    BuiltinDimensionTypes.OVERWORLD_EFFECTS,
    0.0f,
    DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0)
  )

}
