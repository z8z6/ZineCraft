package com.cxxcxx.zinecraft.core.datagen

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.block.ModBlock
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest

object ModWorldConfiguredFeatures {

  val EXAMPLE_BLOCK_VEIN_CONFIGURED_KEY: ResourceKey<ConfiguredFeature<*, *>?> = ResourceKey.create(
    Registries.CONFIGURED_FEATURE,
    ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, "example_block_vein")
  )

  fun configure(context: BootstrapContext<ConfiguredFeature<*, *>>) {
    // 哪些方块应该被替换的规则
    val deepslateReplaceableRule: RuleTest = TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
    val stoneReplaceableRule: RuleTest = TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES)
    // 定义替换规则
    val diamondBlockOreConfig = listOf(
      OreConfiguration.target(
        deepslateReplaceableRule,
        ModBlock.EXAMPLE_ENTITY_BLOCK.defaultBlockState()
      ),
      OreConfiguration.target(
        stoneReplaceableRule,
        ModBlock.EXAMPLE_ENTITY_BLOCK.defaultBlockState()
      )
    )
    // 可以使用 place 命令手动生成
    // 生成后需要重新进入世界才会显示
    context.register(
      EXAMPLE_BLOCK_VEIN_CONFIGURED_KEY,
      ConfiguredFeature(
        Feature.ORE,
        OreConfiguration(diamondBlockOreConfig, 30)
      )
    )
  }
}