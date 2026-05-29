package com.cxxcxx.zinecraft.core.datagen

import com.cxxcxx.zinecraft.core.ZinecraftCore
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight
import net.minecraft.world.level.levelgen.placement.*

object ModWorldPlacedFeatures {

  val EXAMPLE_BLOCK_ORE_PLACED_KEY: ResourceKey<PlacedFeature?> = ResourceKey.create(
    Registries.PLACED_FEATURE,
    ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, "example_block_ore_placed")
  )

  fun configure(context: BootstrapContext<PlacedFeature>) {
    val configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE)
    // 定义放置修饰器。它们是在生成地物时设置的属性
    val exampleBlockVeinModifiers = listOf(
      CountPlacement.of(6),          // 每个区块中该地物实例的数量
      BiomeFilter.biome(),              // 会在哪些生物群系或维度中生成
      InSquarePlacement.spread(),
      HeightRangePlacement.of(          // 指定地物可生成的 y 坐标范围
        BiasedToBottomHeight.of(
          VerticalAnchor.BOTTOM,
          VerticalAnchor.absolute(0), 3
        )
      )
    )
    context.register(
      EXAMPLE_BLOCK_ORE_PLACED_KEY,
      PlacedFeature(
        configuredFeatures.getOrThrow(ModWorldConfiguredFeatures.EXAMPLE_BLOCK_VEIN_CONFIGURED_KEY),
        exampleBlockVeinModifiers
      )
    )
  }
}