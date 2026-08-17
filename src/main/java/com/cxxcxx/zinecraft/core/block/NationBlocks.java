package com.cxxcxx.zinecraft.core.block;

import com.cxxcxx.zinecraft.api.block.BlockCatalog;
import com.cxxcxx.zinecraft.api.block.BlockEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.NotNull;

public final class NationBlocks {
  @NotNull
  public static final NationBlocks INSTANCE = new NationBlocks();
  @NotNull
  public static final BlockEntry<Block> AEGIR_ABYSSAL_SLATE;
  @NotNull
  public static final BlockEntry<Block> AEGIR_PRESSURE_TILE;
  @NotNull
  public static final BlockEntry<Block> BOLIVAR_WAR_SCOURED_SOIL;
  @NotNull
  public static final BlockEntry<Block> BOLIVAR_DOSSOLES_STUCCO;
  @NotNull
  public static final BlockEntry<Block> HIGASHI_SHADOW_LOAM;
  @NotNull
  public static final BlockEntry<Block> HIGASHI_MACHIYA_PLASTER;
  @NotNull
  public static final BlockEntry<Block> DURIN_GARDEN_MOSS;
  @NotNull
  public static final BlockEntry<Block> DURIN_IDEAL_CITY_PANEL;
  @NotNull
  public static final BlockEntry<Block> COLUMBIA_CANYON_SOIL;
  @NotNull
  public static final BlockEntry<Block> COLUMBIA_FRONTIER_PANEL;
  @NotNull
  public static final BlockEntry<Block> KAZIMIERZ_STEPPE_TURF;
  @NotNull
  public static final BlockEntry<Block> KAZIMIERZ_ARENA_MASONRY;
  @NotNull
  public static final BlockEntry<Block> KAZDEL_SCARRED_ASH;
  @NotNull
  public static final BlockEntry<Block> KAZDEL_FORTRESS_PLATE;
  @NotNull
  public static final BlockEntry<Block> LATERANO_ALLUVIAL_CHALK;
  @NotNull
  public static final BlockEntry<Block> LATERANO_BASILICA_MARBLE;
  @NotNull
  public static final BlockEntry<Block> LATERANO_HOST_CASING;
  @NotNull
  public static final BlockEntry<Block> LATERANO_HOST_CONDUIT;
  @NotNull
  public static final BlockEntry<Block> LEITHANIEN_TWILIGHT_HUMUS;
  @NotNull
  public static final BlockEntry<Block> LEITHANIEN_RESONANT_BRICK;
  @NotNull
  public static final BlockEntry<Block> RIM_BILLITON_MINE_TAILINGS;
  @NotNull
  public static final BlockEntry<Block> RIM_BILLITON_CORRUGATED_STEEL;
  @NotNull
  public static final BlockEntry<Block> MINOS_SUNBAKED_EARTH;
  @NotNull
  public static final BlockEntry<Block> MINOS_HEROIC_MASONRY;
  @NotNull
  public static final BlockEntry<Block> SARGON_DESERT_CRUST;
  @NotNull
  public static final BlockEntry<Block> SARGON_OASIS_ADOBE;
  @NotNull
  public static final BlockEntry<Block> SAMI_FROST_MOSS;
  @NotNull
  public static final BlockEntry<Block> SAMI_RITUAL_STONE;
  @NotNull
  public static final BlockEntry<Block> SAMI_TRIBAL_TIMBER;
  @NotNull
  public static final BlockEntry<Block> VICTORIA_MOORLAND_SOIL;
  @NotNull
  public static final BlockEntry<Block> VICTORIA_INDUSTRIAL_BRICK;
  @NotNull
  public static final BlockEntry<Block> VICTORIA_WALL_ARMOR;
  @NotNull
  public static final BlockEntry<Block> VICTORIA_CANNON_CASING;
  @NotNull
  public static final BlockEntry<Block> VICTORIA_STRUCTURAL_FRAME;
  @NotNull
  public static final BlockEntry<Block> VICTORIA_REINFORCED_FLOOR;
  @NotNull
  public static final BlockEntry<Block> VICTORIA_CONTROL_PANEL;
  @NotNull
  public static final BlockEntry<Block> VICTORIA_BATTLE_SCARRED_ARMOR;
  @NotNull
  public static final BlockEntry<Block> VICTORIA_BLAST_SCARRED_ARMOR;
  @NotNull
  public static final BlockEntry<Block> URSUS_PERMAFROST;
  @NotNull
  public static final BlockEntry<Block> URSUS_IMPERIAL_MASONRY;
  @NotNull
  public static final BlockEntry<Block> KJERAG_SACRED_SNOWSTONE;
  @NotNull
  public static final BlockEntry<Block> KJERAG_MONASTERY_STONE;
  @NotNull
  public static final BlockEntry<Block> SIRACUSA_RAIN_DARKENED_SOIL;
  @NotNull
  public static final BlockEntry<Block> SIRACUSA_FAMILY_MASONRY;
  @NotNull
  public static final BlockEntry<Block> YAN_MOUNTAIN_SOIL;
  @NotNull
  public static final BlockEntry<Block> YAN_COURTYARD_BRICK;
  @NotNull
  public static final BlockEntry<Block> IBERIA_SALT_CRUSTED_GRAVEL;
  @NotNull
  public static final BlockEntry<Block> IBERIA_COASTAL_MASONRY;

  static {
    NationBlocks nationBlocks = INSTANCE;
    Block block = Blocks.DARK_PRISMARINE;
    AEGIR_ABYSSAL_SLATE = nationBlocks.material("aegir_abyssal_slate", "阿戈尔深渊岩板", "Aegir Abyssal Slate", block);
    nationBlocks = INSTANCE;
    block = Blocks.PRISMARINE_BRICKS;
    AEGIR_PRESSURE_TILE = nationBlocks.material("aegir_pressure_tile", "阿戈尔耐压墙砖", "Aegir Pressure Tile", block);
    nationBlocks = INSTANCE;
    block = Blocks.COARSE_DIRT;
    BOLIVAR_WAR_SCOURED_SOIL = nationBlocks.material("bolivar_war_scoured_soil", "玻利瓦尔战蚀土", "Bolivar War-Scoured Soil", block);
    nationBlocks = INSTANCE;
    block = Blocks.TERRACOTTA;
    BOLIVAR_DOSSOLES_STUCCO = nationBlocks.material("bolivar_dossoles_stucco", "多索雷斯灰泥墙", "Dossoles Stucco", block);
    nationBlocks = INSTANCE;
    block = Blocks.PODZOL;
    HIGASHI_SHADOW_LOAM = nationBlocks.material("higashi_shadow_loam", "东国裂谷暗壤", "Higashi Shadow Loam", block);
    nationBlocks = INSTANCE;
    block = Blocks.DARK_OAK_PLANKS;
    HIGASHI_MACHIYA_PLASTER = nationBlocks.material("higashi_machiya_plaster", "东国町屋灰泥墙", "Higashi Machiya Plaster", block);
    nationBlocks = INSTANCE;
    block = Blocks.MOSS_BLOCK;
    DURIN_GARDEN_MOSS = nationBlocks.material("durin_garden_moss", "杜林花园苔土", "Durin Garden Moss", block);
    nationBlocks = INSTANCE;
    block = Blocks.CUT_COPPER;
    DURIN_IDEAL_CITY_PANEL = nationBlocks.material("durin_ideal_city_panel", "杜林理想城彩板", "Durin Ideal City Panel", block);
    nationBlocks = INSTANCE;
    block = Blocks.RED_SAND;
    COLUMBIA_CANYON_SOIL = nationBlocks.material("columbia_canyon_soil", "哥伦比亚峡谷砂土", "Columbia Canyon Soil", block);
    nationBlocks = INSTANCE;
    block = Blocks.WHITE_CONCRETE;
    COLUMBIA_FRONTIER_PANEL = nationBlocks.material("columbia_frontier_panel", "哥伦比亚拓荒墙板", "Columbia Frontier Panel", block);
    nationBlocks = INSTANCE;
    block = Blocks.GRASS_BLOCK;
    KAZIMIERZ_STEPPE_TURF = nationBlocks.material("kazimierz_steppe_turf", "卡西米尔旱原草皮", "Kazimierz Steppe Turf", block);
    nationBlocks = INSTANCE;
    block = Blocks.SMOOTH_QUARTZ;
    KAZIMIERZ_ARENA_MASONRY = nationBlocks.material("kazimierz_arena_masonry", "卡西米尔竞技场石砌", "Kazimierz Arena Masonry", block);
    nationBlocks = INSTANCE;
    block = Blocks.BLACKSTONE;
    KAZDEL_SCARRED_ASH = nationBlocks.material("kazdel_scarred_ash", "卡兹戴尔战痕灰烬", "Kazdel Scarred Ash", block);
    nationBlocks = INSTANCE;
    block = Blocks.POLISHED_BLACKSTONE_BRICKS;
    KAZDEL_FORTRESS_PLATE = nationBlocks.material("kazdel_fortress_plate", "卡兹戴尔要塞装甲板", "Kazdel Fortress Plate", block);
    nationBlocks = INSTANCE;
    block = Blocks.CALCITE;
    LATERANO_ALLUVIAL_CHALK = nationBlocks.material("laterano_alluvial_chalk", "拉特兰冲积白垩", "Laterano Alluvial Chalk", block);
    nationBlocks = INSTANCE;
    block = Blocks.QUARTZ_BRICKS;
    LATERANO_BASILICA_MARBLE = nationBlocks.material("laterano_basilica_marble", "拉特兰圣堂大理石", "Laterano Basilica Marble", block);
    nationBlocks = INSTANCE;
    block = Blocks.IRON_BLOCK;
    LATERANO_HOST_CASING = nationBlocks.material("laterano_host_casing", "拉特兰主机银壳", "Laterano Host Casing", block);
    nationBlocks = INSTANCE;
    block = Blocks.SEA_LANTERN;
    LATERANO_HOST_CONDUIT = nationBlocks.material("laterano_host_conduit", "拉特兰主机同步导管", "Laterano Host Synchronization Conduit", block);
    nationBlocks = INSTANCE;
    block = Blocks.ROOTED_DIRT;
    LEITHANIEN_TWILIGHT_HUMUS = nationBlocks.material("leithanien_twilight_humus", "莱塔尼亚暮林腐殖土", "Leithanien Twilight Humus", block);
    nationBlocks = INSTANCE;
    block = Blocks.POLISHED_DEEPSLATE;
    LEITHANIEN_RESONANT_BRICK = nationBlocks.material("leithanien_resonant_brick", "莱塔尼亚共振砖", "Leithanien Resonant Brick", block);
    nationBlocks = INSTANCE;
    block = Blocks.TERRACOTTA;
    RIM_BILLITON_MINE_TAILINGS = nationBlocks.material("rim_billiton_mine_tailings", "雷姆必拓矿渣土", "Rim Billiton Mine Tailings", block);
    nationBlocks = INSTANCE;
    block = Blocks.CUT_COPPER;
    RIM_BILLITON_CORRUGATED_STEEL = nationBlocks.material("rim_billiton_corrugated_steel", "雷姆必拓波纹钢板", "Rim Billiton Corrugated Steel", block);
    nationBlocks = INSTANCE;
    block = Blocks.PACKED_MUD;
    MINOS_SUNBAKED_EARTH = nationBlocks.material("minos_sunbaked_earth", "米诺斯晒土地", "Minos Sunbaked Earth", block);
    nationBlocks = INSTANCE;
    block = Blocks.SMOOTH_SANDSTONE;
    MINOS_HEROIC_MASONRY = nationBlocks.material("minos_heroic_masonry", "米诺斯英雄石砌", "Minos Heroic Masonry", block);
    nationBlocks = INSTANCE;
    block = Blocks.SAND;
    SARGON_DESERT_CRUST = nationBlocks.material("sargon_desert_crust", "萨尔贡岩漠硬壳", "Sargon Desert Crust", block);
    nationBlocks = INSTANCE;
    block = Blocks.TERRACOTTA;
    SARGON_OASIS_ADOBE = nationBlocks.material("sargon_oasis_adobe", "萨尔贡绿洲土坯", "Sargon Oasis Adobe", block);
    nationBlocks = INSTANCE;
    block = Blocks.SNOW_BLOCK;
    SAMI_FROST_MOSS = nationBlocks.material("sami_frost_moss", "萨米冻原苔土", "Sami Frost Moss", block);
    nationBlocks = INSTANCE;
    block = Blocks.STONE_BRICKS;
    SAMI_RITUAL_STONE = nationBlocks.material("sami_ritual_stone", "萨米祭仪石", "Sami Ritual Stone", block);
    nationBlocks = INSTANCE;
    block = Blocks.SPRUCE_PLANKS;
    SAMI_TRIBAL_TIMBER = nationBlocks.material("sami_tribal_timber", "萨米部族木构", "Sami Tribal Timber", block);
    nationBlocks = INSTANCE;
    block = Blocks.MUD;
    VICTORIA_MOORLAND_SOIL = nationBlocks.material("victoria_moorland_soil", "维多利亚雾沼土", "Victoria Moorland Soil", block);
    nationBlocks = INSTANCE;
    block = Blocks.BRICKS;
    VICTORIA_INDUSTRIAL_BRICK = nationBlocks.material("victoria_industrial_brick", "维多利亚工业砖", "Victoria Industrial Brick", block);
    nationBlocks = INSTANCE;
    block = Blocks.IRON_BLOCK;
    VICTORIA_WALL_ARMOR = nationBlocks.material("victoria_wall_armor", "维多利亚城防装甲", "Victoria Wall Armor", block);
    nationBlocks = INSTANCE;
    block = Blocks.IRON_BLOCK;
    VICTORIA_CANNON_CASING = nationBlocks.material("victoria_cannon_casing", "维多利亚巨炮壳板", "Victoria Cannon Casing", block);
    nationBlocks = INSTANCE;
    block = Blocks.POLISHED_DEEPSLATE;
    VICTORIA_STRUCTURAL_FRAME = nationBlocks.material("victoria_structural_frame", "维多利亚承力骨架", "Victoria Structural Frame", block);
    nationBlocks = INSTANCE;
    block = Blocks.IRON_BLOCK;
    VICTORIA_REINFORCED_FLOOR = nationBlocks.material("victoria_reinforced_floor", "维多利亚防滑钢地板", "Victoria Reinforced Floor", block);
    nationBlocks = INSTANCE;
    block = Blocks.IRON_BLOCK;
    VICTORIA_CONTROL_PANEL = nationBlocks.material("victoria_control_panel", "维多利亚火控面板", "Victoria Fire-Control Panel", block);
    nationBlocks = INSTANCE;
    block = Blocks.IRON_BLOCK;
    VICTORIA_BATTLE_SCARRED_ARMOR = nationBlocks.material("victoria_battle_scarred_armor", "维多利亚弹痕装甲", "Victoria Battle-Scarred Armor", block);
    nationBlocks = INSTANCE;
    block = Blocks.IRON_BLOCK;
    VICTORIA_BLAST_SCARRED_ARMOR = nationBlocks.material("victoria_blast_scarred_armor", "维多利亚爆蚀装甲", "Victoria Blast-Scarred Armor", block);
    nationBlocks = INSTANCE;
    block = Blocks.PACKED_ICE;
    URSUS_PERMAFROST = nationBlocks.material("ursus_permafrost", "乌萨斯永冻土", "Ursus Permafrost", block);
    nationBlocks = INSTANCE;
    block = Blocks.STONE_BRICKS;
    URSUS_IMPERIAL_MASONRY = nationBlocks.material("ursus_imperial_masonry", "乌萨斯帝国石砌", "Ursus Imperial Masonry", block);
    nationBlocks = INSTANCE;
    block = Blocks.BLUE_ICE;
    KJERAG_SACRED_SNOWSTONE = nationBlocks.material("kjerag_sacred_snowstone", "谢拉格圣雪岩", "Kjerag Sacred Snowstone", block);
    nationBlocks = INSTANCE;
    block = Blocks.STONE_BRICKS;
    KJERAG_MONASTERY_STONE = nationBlocks.material("kjerag_monastery_stone", "谢拉格蔓珠院石墙", "Kjerag Monastery Stone", block);
    nationBlocks = INSTANCE;
    block = Blocks.MOSSY_COBBLESTONE;
    SIRACUSA_RAIN_DARKENED_SOIL = nationBlocks.material("siracusa_rain_darkened_soil", "叙拉古雨浸土", "Siracusa Rain-Darkened Soil", block);
    nationBlocks = INSTANCE;
    block = Blocks.BRICKS;
    SIRACUSA_FAMILY_MASONRY = nationBlocks.material("siracusa_family_masonry", "叙拉古家族石砌", "Siracusa Family Masonry", block);
    nationBlocks = INSTANCE;
    block = Blocks.TUFF;
    YAN_MOUNTAIN_SOIL = nationBlocks.material("yan_mountain_soil", "炎国山壤", "Yan Mountain Soil", block);
    nationBlocks = INSTANCE;
    block = Blocks.TUFF_BRICKS;
    YAN_COURTYARD_BRICK = nationBlocks.material("yan_courtyard_brick", "炎国院墙青砖", "Yan Courtyard Brick", block);
    nationBlocks = INSTANCE;
    block = Blocks.GRAVEL;
    IBERIA_SALT_CRUSTED_GRAVEL = nationBlocks.material("iberia_salt_crusted_gravel", "伊比利亚盐壳砾石", "Iberia Salt-Crusted Gravel", block);
    nationBlocks = INSTANCE;
    block = Blocks.STONE_BRICKS;
    IBERIA_COASTAL_MASONRY = nationBlocks.material("iberia_coastal_masonry", "伊比利亚海岸石砌", "Iberia Coastal Masonry", block);
  }

  private NationBlocks() {
  }

  private static final Block materialHelper0(Block _physicalTemplate) {
    return new Block(Properties.ofFullCopy((BlockBehaviour) _physicalTemplate));
  }

  private final BlockEntry<Block> material(String path, String zhCn, String enUs, Block physicalTemplate) {
    return BlockCatalog.registerWithDefaults(
        Zinecraft.BLOCKS, path, zhCn, enUs, false, null, false, false, () -> materialHelper0(physicalTemplate), 120, null
    );
  }
}
