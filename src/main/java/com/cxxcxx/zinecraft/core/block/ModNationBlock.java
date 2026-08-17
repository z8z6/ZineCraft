package com.cxxcxx.zinecraft.core.block;

import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class ModNationBlock {
  public static final ModNationBlock INSTANCE = new ModNationBlock();

  public static final DeferredBlock<Block> AEGIR_ABYSSAL_SLATE = material(
      "aegir_abyssal_slate", "阿戈尔深渊岩板", Blocks.DARK_PRISMARINE
  );
  public static final DeferredBlock<Block> AEGIR_PRESSURE_TILE = material(
      "aegir_pressure_tile", "阿戈尔耐压墙砖", Blocks.PRISMARINE_BRICKS
  );
  public static final DeferredBlock<Block> BOLIVAR_WAR_SCOURED_SOIL = material(
      "bolivar_war_scoured_soil", "玻利瓦尔战蚀土", Blocks.COARSE_DIRT
  );
  public static final DeferredBlock<Block> BOLIVAR_DOSSOLES_STUCCO = material(
      "bolivar_dossoles_stucco", "多索雷斯灰泥墙", Blocks.TERRACOTTA
  );
  public static final DeferredBlock<Block> HIGASHI_SHADOW_LOAM = material(
      "higashi_shadow_loam", "东国裂谷暗壤", Blocks.PODZOL
  );
  public static final DeferredBlock<Block> HIGASHI_MACHIYA_PLASTER = material(
      "higashi_machiya_plaster", "东国町屋灰泥墙", Blocks.DARK_OAK_PLANKS
  );
  public static final DeferredBlock<Block> DURIN_GARDEN_MOSS = material(
      "durin_garden_moss", "杜林花园苔土", Blocks.MOSS_BLOCK
  );
  public static final DeferredBlock<Block> DURIN_IDEAL_CITY_PANEL = material(
      "durin_ideal_city_panel", "杜林理想城彩板", Blocks.CUT_COPPER
  );
  public static final DeferredBlock<Block> COLUMBIA_CANYON_SOIL = material(
      "columbia_canyon_soil", "哥伦比亚峡谷砂土", Blocks.RED_SAND
  );
  public static final DeferredBlock<Block> COLUMBIA_FRONTIER_PANEL = material(
      "columbia_frontier_panel", "哥伦比亚拓荒墙板", Blocks.WHITE_CONCRETE
  );
  public static final DeferredBlock<Block> KAZIMIERZ_STEPPE_TURF = material(
      "kazimierz_steppe_turf", "卡西米尔旱原草皮", Blocks.GRASS_BLOCK
  );
  public static final DeferredBlock<Block> KAZIMIERZ_ARENA_MASONRY = material(
      "kazimierz_arena_masonry", "卡西米尔竞技场石砌", Blocks.SMOOTH_QUARTZ
  );
  public static final DeferredBlock<Block> KAZDEL_SCARRED_ASH = material(
      "kazdel_scarred_ash", "卡兹戴尔战痕灰烬", Blocks.BLACKSTONE
  );
  public static final DeferredBlock<Block> KAZDEL_FORTRESS_PLATE = material(
      "kazdel_fortress_plate", "卡兹戴尔要塞装甲板", Blocks.POLISHED_BLACKSTONE_BRICKS
  );
  public static final DeferredBlock<Block> LATERANO_ALLUVIAL_CHALK = material(
      "laterano_alluvial_chalk", "拉特兰冲积白垩", Blocks.CALCITE
  );
  public static final DeferredBlock<Block> LATERANO_BASILICA_MARBLE = material(
      "laterano_basilica_marble", "拉特兰圣堂大理石", Blocks.QUARTZ_BRICKS
  );
  public static final DeferredBlock<Block> LATERANO_HOST_CASING = material(
      "laterano_host_casing", "拉特兰主机银壳", Blocks.IRON_BLOCK
  );
  public static final DeferredBlock<Block> LATERANO_HOST_CONDUIT = material(
      "laterano_host_conduit", "拉特兰主机同步导管", Blocks.SEA_LANTERN
  );
  public static final DeferredBlock<Block> LEITHANIEN_TWILIGHT_HUMUS = material(
      "leithanien_twilight_humus", "莱塔尼亚暮林腐殖土", Blocks.ROOTED_DIRT
  );
  public static final DeferredBlock<Block> LEITHANIEN_RESONANT_BRICK = material(
      "leithanien_resonant_brick", "莱塔尼亚共振砖", Blocks.POLISHED_DEEPSLATE
  );
  public static final DeferredBlock<Block> RIM_BILLITON_MINE_TAILINGS = material(
      "rim_billiton_mine_tailings", "雷姆必拓矿渣土", Blocks.TERRACOTTA
  );
  public static final DeferredBlock<Block> RIM_BILLITON_CORRUGATED_STEEL = material(
      "rim_billiton_corrugated_steel", "雷姆必拓波纹钢板", Blocks.CUT_COPPER
  );
  public static final DeferredBlock<Block> MINOS_SUNBAKED_EARTH = material(
      "minos_sunbaked_earth", "米诺斯晒土地", Blocks.PACKED_MUD
  );
  public static final DeferredBlock<Block> MINOS_HEROIC_MASONRY = material(
      "minos_heroic_masonry", "米诺斯英雄石砌", Blocks.SMOOTH_SANDSTONE
  );
  public static final DeferredBlock<Block> SARGON_DESERT_CRUST = material(
      "sargon_desert_crust", "萨尔贡岩漠硬壳", Blocks.SAND
  );
  public static final DeferredBlock<Block> SARGON_OASIS_ADOBE = material(
      "sargon_oasis_adobe", "萨尔贡绿洲土坯", Blocks.TERRACOTTA
  );
  public static final DeferredBlock<Block> SAMI_FROST_MOSS = material(
      "sami_frost_moss", "萨米冻原苔土", Blocks.SNOW_BLOCK
  );
  public static final DeferredBlock<Block> SAMI_RITUAL_STONE = material(
      "sami_ritual_stone", "萨米祭仪石", Blocks.STONE_BRICKS
  );
  public static final DeferredBlock<Block> SAMI_TRIBAL_TIMBER = material(
      "sami_tribal_timber", "萨米部族木构", Blocks.SPRUCE_PLANKS
  );
  public static final DeferredBlock<Block> VICTORIA_MOORLAND_SOIL = material(
      "victoria_moorland_soil", "维多利亚雾沼土", Blocks.MUD
  );
  public static final DeferredBlock<Block> VICTORIA_INDUSTRIAL_BRICK = material(
      "victoria_industrial_brick", "维多利亚工业砖", Blocks.BRICKS
  );
  public static final DeferredBlock<Block> VICTORIA_WALL_ARMOR = material(
      "victoria_wall_armor", "维多利亚城防装甲", Blocks.IRON_BLOCK
  );
  public static final DeferredBlock<Block> VICTORIA_CANNON_CASING = material(
      "victoria_cannon_casing", "维多利亚巨炮壳板", Blocks.IRON_BLOCK
  );
  public static final DeferredBlock<Block> VICTORIA_STRUCTURAL_FRAME = material(
      "victoria_structural_frame", "维多利亚承力骨架", Blocks.POLISHED_DEEPSLATE
  );
  public static final DeferredBlock<Block> VICTORIA_REINFORCED_FLOOR = material(
      "victoria_reinforced_floor", "维多利亚防滑钢地板", Blocks.IRON_BLOCK
  );
  public static final DeferredBlock<Block> VICTORIA_CONTROL_PANEL = material(
      "victoria_control_panel", "维多利亚火控面板", Blocks.IRON_BLOCK
  );
  public static final DeferredBlock<Block> VICTORIA_BATTLE_SCARRED_ARMOR = material(
      "victoria_battle_scarred_armor", "维多利亚弹痕装甲", Blocks.IRON_BLOCK
  );
  public static final DeferredBlock<Block> VICTORIA_BLAST_SCARRED_ARMOR = material(
      "victoria_blast_scarred_armor", "维多利亚爆蚀装甲", Blocks.IRON_BLOCK
  );
  public static final DeferredBlock<Block> URSUS_PERMAFROST = material(
      "ursus_permafrost", "乌萨斯永冻土", Blocks.PACKED_ICE
  );
  public static final DeferredBlock<Block> URSUS_IMPERIAL_MASONRY = material(
      "ursus_imperial_masonry", "乌萨斯帝国石砌", Blocks.STONE_BRICKS
  );
  public static final DeferredBlock<Block> KJERAG_SACRED_SNOWSTONE = material(
      "kjerag_sacred_snowstone", "谢拉格圣雪岩", Blocks.BLUE_ICE
  );
  public static final DeferredBlock<Block> KJERAG_MONASTERY_STONE = material(
      "kjerag_monastery_stone", "谢拉格蔓珠院石墙", Blocks.STONE_BRICKS
  );
  public static final DeferredBlock<Block> SIRACUSA_RAIN_DARKENED_SOIL = material(
      "siracusa_rain_darkened_soil", "叙拉古雨浸土", Blocks.MOSSY_COBBLESTONE
  );
  public static final DeferredBlock<Block> SIRACUSA_FAMILY_MASONRY = material(
      "siracusa_family_masonry", "叙拉古家族石砌", Blocks.BRICKS
  );
  public static final DeferredBlock<Block> YAN_MOUNTAIN_SOIL = material(
      "yan_mountain_soil", "炎国山壤", Blocks.TUFF
  );
  public static final DeferredBlock<Block> YAN_COURTYARD_BRICK = material(
      "yan_courtyard_brick", "炎国院墙青砖", Blocks.TUFF_BRICKS
  );
  public static final DeferredBlock<Block> IBERIA_SALT_CRUSTED_GRAVEL = material(
      "iberia_salt_crusted_gravel", "伊比利亚盐壳砾石", Blocks.GRAVEL
  );
  public static final DeferredBlock<Block> IBERIA_COASTAL_MASONRY = material(
      "iberia_coastal_masonry", "伊比利亚海岸石砌", Blocks.STONE_BRICKS
  );

  private static Block copyBlock(Block physicalTemplate) {
    return new Block(Properties.ofFullCopy((physicalTemplate)));
  }

  private static DeferredBlock<Block> material(String path, String zhCn, Block physicalTemplate) {
    return Zinecraft.BLOCKS.builder(path, zhCn, () -> copyBlock(physicalTemplate))
        .enUs(TranslationNames.toDisplayName(path))
        .build();
  }
}
