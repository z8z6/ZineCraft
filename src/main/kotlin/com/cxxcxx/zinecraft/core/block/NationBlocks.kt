package com.cxxcxx.zinecraft.core.block

import com.cxxcxx.zinecraft.core.Zinecraft
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

/**
 * 十九国地貌与建筑主材。
 *
 * 每国至少提供一种可用于群系表层的地貌方块和一种可用于聚落外墙的建筑方块；
 * 原版方块只作为硬度、声音等物理属性模板，不再承担国家视觉主题。
 */
object NationBlocks {
  val AEGIR_ABYSSAL_SLATE = material(
    "aegir_abyssal_slate",
    "阿戈尔深渊岩板",
    "Aegir Abyssal Slate",
    net.minecraft.world.level.block.Blocks.DARK_PRISMARINE
  )
  val AEGIR_PRESSURE_TILE = material(
    "aegir_pressure_tile",
    "阿戈尔耐压墙砖",
    "Aegir Pressure Tile",
    net.minecraft.world.level.block.Blocks.PRISMARINE_BRICKS
  )

  val BOLIVAR_WAR_SCOURED_SOIL = material(
    "bolivar_war_scoured_soil",
    "玻利瓦尔战蚀土",
    "Bolivar War-Scoured Soil",
    net.minecraft.world.level.block.Blocks.COARSE_DIRT
  )
  val BOLIVAR_DOSSOLES_STUCCO = material(
    "bolivar_dossoles_stucco",
    "多索雷斯灰泥墙",
    "Dossoles Stucco",
    net.minecraft.world.level.block.Blocks.TERRACOTTA
  )

  val HIGASHI_SHADOW_LOAM = material(
    "higashi_shadow_loam",
    "东国裂谷暗壤",
    "Higashi Shadow Loam",
    net.minecraft.world.level.block.Blocks.PODZOL
  )
  val HIGASHI_MACHIYA_PLASTER = material(
    "higashi_machiya_plaster",
    "东国町屋灰泥墙",
    "Higashi Machiya Plaster",
    net.minecraft.world.level.block.Blocks.DARK_OAK_PLANKS
  )

  val DURIN_GARDEN_MOSS = material(
    "durin_garden_moss",
    "杜林花园苔土",
    "Durin Garden Moss",
    net.minecraft.world.level.block.Blocks.MOSS_BLOCK
  )
  val DURIN_IDEAL_CITY_PANEL = material(
    "durin_ideal_city_panel",
    "杜林理想城彩板",
    "Durin Ideal City Panel",
    net.minecraft.world.level.block.Blocks.CUT_COPPER
  )

  val COLUMBIA_CANYON_SOIL = material(
    "columbia_canyon_soil",
    "哥伦比亚峡谷砂土",
    "Columbia Canyon Soil",
    net.minecraft.world.level.block.Blocks.RED_SAND
  )
  val COLUMBIA_FRONTIER_PANEL = material(
    "columbia_frontier_panel",
    "哥伦比亚拓荒墙板",
    "Columbia Frontier Panel",
    net.minecraft.world.level.block.Blocks.WHITE_CONCRETE
  )

  val KAZIMIERZ_STEPPE_TURF = material(
    "kazimierz_steppe_turf",
    "卡西米尔旱原草皮",
    "Kazimierz Steppe Turf",
    net.minecraft.world.level.block.Blocks.GRASS_BLOCK
  )
  val KAZIMIERZ_ARENA_MASONRY = material(
    "kazimierz_arena_masonry",
    "卡西米尔竞技场石砌",
    "Kazimierz Arena Masonry",
    net.minecraft.world.level.block.Blocks.SMOOTH_QUARTZ
  )

  val KAZDEL_SCARRED_ASH = material(
    "kazdel_scarred_ash",
    "卡兹戴尔战痕灰烬",
    "Kazdel Scarred Ash",
    net.minecraft.world.level.block.Blocks.BLACKSTONE
  )
  val KAZDEL_FORTRESS_PLATE = material(
    "kazdel_fortress_plate",
    "卡兹戴尔要塞装甲板",
    "Kazdel Fortress Plate",
    net.minecraft.world.level.block.Blocks.POLISHED_BLACKSTONE_BRICKS
  )

  val LATERANO_ALLUVIAL_CHALK = material(
    "laterano_alluvial_chalk",
    "拉特兰冲积白垩",
    "Laterano Alluvial Chalk",
    net.minecraft.world.level.block.Blocks.CALCITE
  )
  val LATERANO_BASILICA_MARBLE = material(
    "laterano_basilica_marble",
    "拉特兰圣堂大理石",
    "Laterano Basilica Marble",
    net.minecraft.world.level.block.Blocks.QUARTZ_BRICKS
  )
  val LATERANO_HOST_CASING = material(
    "laterano_host_casing",
    "拉特兰主机银壳",
    "Laterano Host Casing",
    net.minecraft.world.level.block.Blocks.IRON_BLOCK
  )
  val LATERANO_HOST_CONDUIT = material(
    "laterano_host_conduit",
    "拉特兰主机同步导管",
    "Laterano Host Synchronization Conduit",
    net.minecraft.world.level.block.Blocks.SEA_LANTERN
  )

  val LEITHANIEN_TWILIGHT_HUMUS = material(
    "leithanien_twilight_humus",
    "莱塔尼亚暮林腐殖土",
    "Leithanien Twilight Humus",
    net.minecraft.world.level.block.Blocks.ROOTED_DIRT
  )
  val LEITHANIEN_RESONANT_BRICK = material(
    "leithanien_resonant_brick",
    "莱塔尼亚共振砖",
    "Leithanien Resonant Brick",
    net.minecraft.world.level.block.Blocks.POLISHED_DEEPSLATE
  )

  val RIM_BILLITON_MINE_TAILINGS = material(
    "rim_billiton_mine_tailings",
    "雷姆必拓矿渣土",
    "Rim Billiton Mine Tailings",
    net.minecraft.world.level.block.Blocks.TERRACOTTA
  )
  val RIM_BILLITON_CORRUGATED_STEEL = material(
    "rim_billiton_corrugated_steel",
    "雷姆必拓波纹钢板",
    "Rim Billiton Corrugated Steel",
    net.minecraft.world.level.block.Blocks.CUT_COPPER
  )

  val MINOS_SUNBAKED_EARTH = material(
    "minos_sunbaked_earth",
    "米诺斯晒土地",
    "Minos Sunbaked Earth",
    net.minecraft.world.level.block.Blocks.PACKED_MUD
  )
  val MINOS_HEROIC_MASONRY = material(
    "minos_heroic_masonry",
    "米诺斯英雄石砌",
    "Minos Heroic Masonry",
    net.minecraft.world.level.block.Blocks.SMOOTH_SANDSTONE
  )

  val SARGON_DESERT_CRUST = material(
    "sargon_desert_crust",
    "萨尔贡岩漠硬壳",
    "Sargon Desert Crust",
    net.minecraft.world.level.block.Blocks.SAND
  )
  val SARGON_OASIS_ADOBE = material(
    "sargon_oasis_adobe",
    "萨尔贡绿洲土坯",
    "Sargon Oasis Adobe",
    net.minecraft.world.level.block.Blocks.TERRACOTTA
  )

  val SAMI_FROST_MOSS =
    material("sami_frost_moss", "萨米冻原苔土", "Sami Frost Moss", net.minecraft.world.level.block.Blocks.SNOW_BLOCK)
  val SAMI_RITUAL_STONE = material(
    "sami_ritual_stone",
    "萨米祭仪石",
    "Sami Ritual Stone",
    net.minecraft.world.level.block.Blocks.STONE_BRICKS
  )

  val VICTORIA_MOORLAND_SOIL = material(
    "victoria_moorland_soil",
    "维多利亚雾沼土",
    "Victoria Moorland Soil",
    net.minecraft.world.level.block.Blocks.MUD
  )
  val VICTORIA_INDUSTRIAL_BRICK = material(
    "victoria_industrial_brick",
    "维多利亚工业砖",
    "Victoria Industrial Brick",
    net.minecraft.world.level.block.Blocks.BRICKS
  )

  val URSUS_PERMAFROST =
    material("ursus_permafrost", "乌萨斯永冻土", "Ursus Permafrost", net.minecraft.world.level.block.Blocks.PACKED_ICE)
  val URSUS_IMPERIAL_MASONRY = material(
    "ursus_imperial_masonry",
    "乌萨斯帝国石砌",
    "Ursus Imperial Masonry",
    net.minecraft.world.level.block.Blocks.STONE_BRICKS
  )

  val KJERAG_SACRED_SNOWSTONE = material(
    "kjerag_sacred_snowstone",
    "谢拉格圣雪岩",
    "Kjerag Sacred Snowstone",
    net.minecraft.world.level.block.Blocks.BLUE_ICE
  )
  val KJERAG_MONASTERY_STONE = material(
    "kjerag_monastery_stone",
    "谢拉格蔓珠院石墙",
    "Kjerag Monastery Stone",
    net.minecraft.world.level.block.Blocks.STONE_BRICKS
  )

  val SIRACUSA_RAIN_DARKENED_SOIL = material(
    "siracusa_rain_darkened_soil",
    "叙拉古雨浸土",
    "Siracusa Rain-Darkened Soil",
    net.minecraft.world.level.block.Blocks.MOSSY_COBBLESTONE
  )
  val SIRACUSA_FAMILY_MASONRY = material(
    "siracusa_family_masonry",
    "叙拉古家族石砌",
    "Siracusa Family Masonry",
    net.minecraft.world.level.block.Blocks.BRICKS
  )

  val YAN_MOUNTAIN_SOIL =
    material("yan_mountain_soil", "炎国山壤", "Yan Mountain Soil", net.minecraft.world.level.block.Blocks.TUFF)
  val YAN_COURTYARD_BRICK = material(
    "yan_courtyard_brick",
    "炎国院墙青砖",
    "Yan Courtyard Brick",
    net.minecraft.world.level.block.Blocks.TUFF_BRICKS
  )

  val IBERIA_SALT_CRUSTED_GRAVEL = material(
    "iberia_salt_crusted_gravel",
    "伊比利亚盐壳砾石",
    "Iberia Salt-Crusted Gravel",
    net.minecraft.world.level.block.Blocks.GRAVEL
  )
  val IBERIA_COASTAL_MASONRY = material(
    "iberia_coastal_masonry",
    "伊比利亚海岸石砌",
    "Iberia Coastal Masonry",
    net.minecraft.world.level.block.Blocks.STONE_BRICKS
  )

  private fun material(path: String, zhCn: String, enUs: String, physicalTemplate: Block): Block =
    Zinecraft.BLOCKS.register(path, zhCn, enUs) {
      Block(BlockBehaviour.Properties.ofFullCopy(physicalTemplate))
    }.block
}
