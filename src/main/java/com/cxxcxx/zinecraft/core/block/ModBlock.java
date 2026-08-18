package com.cxxcxx.zinecraft.core.block;

import com.cxxcxx.zinecraft.api.block.BlockEntry;
import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.api.world.feature.MaterialOre;
import com.cxxcxx.zinecraft.api.world.feature.OreEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.ModBiome;
import com.cxxcxx.zinecraft.core.dimension.StarGateControllerBlock;
import com.cxxcxx.zinecraft.core.dimension.StarGatePortalBlock;
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock;
import com.cxxcxx.zinecraft.core.item.ModItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

public final class ModBlock {
  public static final ModBlock INSTANCE = new ModBlock();
  public final BlockEntry<Block> STARGATE_ARCH = Zinecraft.BLOCKS
      .builder("stargate_arch", "星门拱石", ModBlock::createStargateArch)
      .enUs("Stargate Archstone")
      .build();
  public final BlockEntry<StarGateControllerBlock> STARGATE_CONTROLLER = Zinecraft.BLOCKS
      .builder("stargate_controller", "星门协议控制器", ModBlock::createStargateController)
      .enUs("Stargate Protocol Controller")
      .build();
  public final BlockEntry<StarGatePortalBlock> STARGATE_PORTAL = Zinecraft.BLOCKS
      .builder("stargate_portal", "星门事件视界", ModBlock::createStargatePortal)
      .enUs("Stargate Event Horizon")
      .noLoot()
      .noCubeModel()
      .noBlockItem()
      .build();
  public final BlockEntry<ExampleEntityBlock> EXAMPLE_ENTITY_BLOCK = Zinecraft.BLOCKS
      .builder("example_entity_block", "示例实体方块", ModBlock::createExampleEntityBlock)
      .enUs("Example Entity Block")
      .build();

  public final BlockEntry<Block> AEGIR_ABYSSAL_SLATE = material(
      "aegir_abyssal_slate", "阿戈尔深渊岩板", Blocks.DARK_PRISMARINE
  );
  public final BlockEntry<Block> AEGIR_PRESSURE_TILE = material(
      "aegir_pressure_tile", "阿戈尔耐压墙砖", Blocks.PRISMARINE_BRICKS
  );
  public final BlockEntry<Block> BOLIVAR_WAR_SCOURED_SOIL = material(
      "bolivar_war_scoured_soil", "玻利瓦尔战蚀土", Blocks.COARSE_DIRT
  );
  public final BlockEntry<Block> BOLIVAR_DOSSOLES_STUCCO = material(
      "bolivar_dossoles_stucco", "多索雷斯灰泥墙", Blocks.TERRACOTTA
  );
  public final BlockEntry<Block> HIGASHI_SHADOW_LOAM = material(
      "higashi_shadow_loam", "东国裂谷暗壤", Blocks.PODZOL
  );
  public final BlockEntry<Block> HIGASHI_MACHIYA_PLASTER = material(
      "higashi_machiya_plaster", "东国町屋灰泥墙", Blocks.DARK_OAK_PLANKS
  );
  public final BlockEntry<Block> DURIN_GARDEN_MOSS = material(
      "durin_garden_moss", "杜林花园苔土", Blocks.MOSS_BLOCK
  );
  public final BlockEntry<Block> DURIN_IDEAL_CITY_PANEL = material(
      "durin_ideal_city_panel", "杜林理想城彩板", Blocks.CUT_COPPER
  );
  public final BlockEntry<Block> COLUMBIA_CANYON_SOIL = material(
      "columbia_canyon_soil", "哥伦比亚峡谷砂土", Blocks.RED_SAND
  );
  public final BlockEntry<Block> COLUMBIA_FRONTIER_PANEL = material(
      "columbia_frontier_panel", "哥伦比亚拓荒墙板", Blocks.WHITE_CONCRETE
  );
  public final BlockEntry<Block> KAZIMIERZ_STEPPE_TURF = material(
      "kazimierz_steppe_turf", "卡西米尔旱原草皮", Blocks.GRASS_BLOCK
  );
  public final BlockEntry<Block> KAZIMIERZ_ARENA_MASONRY = material(
      "kazimierz_arena_masonry", "卡西米尔竞技场石砌", Blocks.SMOOTH_QUARTZ
  );
  public final BlockEntry<Block> KAZDEL_SCARRED_ASH = material(
      "kazdel_scarred_ash", "卡兹戴尔战痕灰烬", Blocks.BLACKSTONE
  );
  public final BlockEntry<Block> KAZDEL_FORTRESS_PLATE = material(
      "kazdel_fortress_plate", "卡兹戴尔要塞装甲板", Blocks.POLISHED_BLACKSTONE_BRICKS
  );
  public final BlockEntry<Block> LATERANO_ALLUVIAL_CHALK = material(
      "laterano_alluvial_chalk", "拉特兰冲积白垩", Blocks.CALCITE
  );
  public final BlockEntry<Block> LATERANO_BASILICA_MARBLE = material(
      "laterano_basilica_marble", "拉特兰圣堂大理石", Blocks.QUARTZ_BRICKS
  );
  public final BlockEntry<Block> LATERANO_HOST_CASING = material(
      "laterano_host_casing", "拉特兰主机银壳", Blocks.IRON_BLOCK
  );
  public final BlockEntry<Block> LATERANO_HOST_CONDUIT = material(
      "laterano_host_conduit", "拉特兰主机同步导管", Blocks.SEA_LANTERN
  );
  public final BlockEntry<Block> LEITHANIEN_TWILIGHT_HUMUS = material(
      "leithanien_twilight_humus", "莱塔尼亚暮林腐殖土", Blocks.ROOTED_DIRT
  );
  public final BlockEntry<Block> LEITHANIEN_RESONANT_BRICK = material(
      "leithanien_resonant_brick", "莱塔尼亚共振砖", Blocks.POLISHED_DEEPSLATE
  );
  public final BlockEntry<Block> RIM_BILLITON_MINE_TAILINGS = material(
      "rim_billiton_mine_tailings", "雷姆必拓矿渣土", Blocks.TERRACOTTA
  );
  public final BlockEntry<Block> RIM_BILLITON_CORRUGATED_STEEL = material(
      "rim_billiton_corrugated_steel", "雷姆必拓波纹钢板", Blocks.CUT_COPPER
  );
  public final BlockEntry<Block> MINOS_SUNBAKED_EARTH = material(
      "minos_sunbaked_earth", "米诺斯晒土地", Blocks.PACKED_MUD
  );
  public final BlockEntry<Block> MINOS_HEROIC_MASONRY = material(
      "minos_heroic_masonry", "米诺斯英雄石砌", Blocks.SMOOTH_SANDSTONE
  );
  public final BlockEntry<Block> SARGON_DESERT_CRUST = material(
      "sargon_desert_crust", "萨尔贡岩漠硬壳", Blocks.SAND
  );
  public final BlockEntry<Block> SARGON_OASIS_ADOBE = material(
      "sargon_oasis_adobe", "萨尔贡绿洲土坯", Blocks.TERRACOTTA
  );
  public final BlockEntry<Block> SAMI_FROST_MOSS = material(
      "sami_frost_moss", "萨米冻原苔土", Blocks.SNOW_BLOCK
  );
  public final BlockEntry<Block> SAMI_RITUAL_STONE = material(
      "sami_ritual_stone", "萨米祭仪石", Blocks.STONE_BRICKS
  );
  public final BlockEntry<Block> SAMI_TRIBAL_TIMBER = material(
      "sami_tribal_timber", "萨米部族木构", Blocks.SPRUCE_PLANKS
  );
  public final BlockEntry<Block> VICTORIA_MOORLAND_SOIL = material(
      "victoria_moorland_soil", "维多利亚雾沼土", Blocks.MUD
  );
  public final BlockEntry<Block> VICTORIA_INDUSTRIAL_BRICK = material(
      "victoria_industrial_brick", "维多利亚工业砖", Blocks.BRICKS
  );
  public final BlockEntry<Block> VICTORIA_WALL_ARMOR = material(
      "victoria_wall_armor", "维多利亚城防装甲", Blocks.IRON_BLOCK
  );
  public final BlockEntry<Block> VICTORIA_CANNON_CASING = material(
      "victoria_cannon_casing", "维多利亚巨炮壳板", Blocks.IRON_BLOCK
  );
  public final BlockEntry<Block> VICTORIA_STRUCTURAL_FRAME = material(
      "victoria_structural_frame", "维多利亚承力骨架", Blocks.POLISHED_DEEPSLATE
  );
  public final BlockEntry<Block> VICTORIA_REINFORCED_FLOOR = material(
      "victoria_reinforced_floor", "维多利亚防滑钢地板", Blocks.IRON_BLOCK
  );
  public final BlockEntry<Block> VICTORIA_CONTROL_PANEL = material(
      "victoria_control_panel", "维多利亚火控面板", Blocks.IRON_BLOCK
  );
  public final BlockEntry<Block> VICTORIA_BATTLE_SCARRED_ARMOR = material(
      "victoria_battle_scarred_armor", "维多利亚弹痕装甲", Blocks.IRON_BLOCK
  );
  public final BlockEntry<Block> VICTORIA_BLAST_SCARRED_ARMOR = material(
      "victoria_blast_scarred_armor", "维多利亚爆蚀装甲", Blocks.IRON_BLOCK
  );
  public final BlockEntry<Block> URSUS_PERMAFROST = material(
      "ursus_permafrost", "乌萨斯永冻土", Blocks.PACKED_ICE
  );
  public final BlockEntry<Block> URSUS_IMPERIAL_MASONRY = material(
      "ursus_imperial_masonry", "乌萨斯帝国石砌", Blocks.STONE_BRICKS
  );
  public final BlockEntry<Block> KJERAG_SACRED_SNOWSTONE = material(
      "kjerag_sacred_snowstone", "谢拉格圣雪岩", Blocks.BLUE_ICE
  );
  public final BlockEntry<Block> KJERAG_MONASTERY_STONE = material(
      "kjerag_monastery_stone", "谢拉格蔓珠院石墙", Blocks.STONE_BRICKS
  );
  public final BlockEntry<Block> SIRACUSA_RAIN_DARKENED_SOIL = material(
      "siracusa_rain_darkened_soil", "叙拉古雨浸土", Blocks.MOSSY_COBBLESTONE
  );
  public final BlockEntry<Block> SIRACUSA_FAMILY_MASONRY = material(
      "siracusa_family_masonry", "叙拉古家族石砌", Blocks.BRICKS
  );
  public final BlockEntry<Block> YAN_MOUNTAIN_SOIL = material(
      "yan_mountain_soil", "炎国山壤", Blocks.TUFF
  );
  public final BlockEntry<Block> YAN_COURTYARD_BRICK = material(
      "yan_courtyard_brick", "炎国院墙青砖", Blocks.TUFF_BRICKS
  );
  public final BlockEntry<Block> IBERIA_SALT_CRUSTED_GRAVEL = material(
      "iberia_salt_crusted_gravel", "伊比利亚盐壳砾石", Blocks.GRAVEL
  );
  public final BlockEntry<Block> IBERIA_COASTAL_MASONRY = material(
      "iberia_coastal_masonry", "伊比利亚海岸石砌", Blocks.STONE_BRICKS
  );

  public final BlockEntry<HeadBlock> Z8Z6Z8Z6_HEAD = register("z8z6z8z6_head", "z8z6z8z6 的头", "z8z6z8z6's Head");
  public final BlockEntry<HeadBlock> YE_XINGCHEN_HEAD = register("ye_xingchen_head", "Ye_xingchen 的头", "Ye_xingchen's Head");

  private final BiomeSelection MATERIAL_DIMENSIONS = BiomeSelection.union(
      BiomeSelection.overworld(),
      BiomeSelection.of(ModBiome.ALL_TERRA_BIOMES)
  );

  public final MaterialOre ORIGINITE_ORE = ore(
      "originite_ore", "originite_ore", "源石矿", ModItem.INSTANCE.ORIGINITE,
      "originite", 3, 2, -32, 0.25F
  );
  public final MaterialOre ORIROCK_ORE = ore(
      "orirock_ore", "orirock_ore", "源岩矿", ModItem.INSTANCE.ORIROCK,
      "orirock", 10, 12, 64, 0.0F
  );
  public final MaterialOre ORIRON_ORE = ore(
      "oriron_ore", "oriron_ore", "异铁矿", ModItem.INSTANCE.ORIRON_SHARD,
      "oriron_shard", 7, 8, 32, 0.0F
  );
  public final MaterialOre MANGANESE_ORE = ore(
      "manganese_ore_block", "manganese_ore", "轻锰矿脉", ModItem.INSTANCE.MANGANESE_ORE,
      "manganese_ore", 5, 6, 16, 0.1F
  );
  public final MaterialOre GRINDSTONE_ORE = ore(
      "grindstone_ore", "grindstone_ore", "研磨石矿", ModItem.INSTANCE.GRINDSTONE,
      "grindstone", 6, 5, 0, 0.15F
  );
  public final MaterialOre RMA70_ORE = ore(
      "rma70_ore", "rma70_ore", "RMA70 矿", ModItem.INSTANCE.RMA70_12,
      "rma70_12", 4, 3, -32, 0.25F
  );
  public final MaterialOre CRYSTAL_ELEMENT_ORE = ore(
      "crystal_element_ore", "crystal_element_ore", "晶体元件矿", ModItem.INSTANCE.CRYSTAL_ELEMENT,
      "crystal_element", 5, 4, 16, 0.15F
  );
  public final MaterialOre LOXIC_KOHL_ORE = ore(
      "loxic_kohl_ore", "loxic_kohl_ore", "炽合金矿", ModItem.INSTANCE.LOXIC_KOHL,
      "loxic_kohl", 4, 3, -16, 0.2F
  );

  public final List<MaterialOre> ORES = List.of(
      ORIGINITE_ORE, ORIROCK_ORE, ORIRON_ORE, MANGANESE_ORE,
      GRINDSTONE_ORE, RMA70_ORE, CRYSTAL_ELEMENT_ORE, LOXIC_KOHL_ORE
  );

  static {
    Zinecraft.TRANSLATIONS
        .add("message.zinecraft.stargate.requires_protocol_originium", "控制器拒绝访问：请手持协议源石右键激活", "Access denied: right-click while holding Protocol Originium");
    Zinecraft.TRANSLATIONS
        .add("message.zinecraft.stargate.activated", "协议认证完成，星门事件视界已建立", "Protocol accepted. Stargate event horizon established");
    Zinecraft.TRANSLATIONS.add("message.zinecraft.stargate.already_active", "星门已处于激活状态", "The stargate is already active");
    Zinecraft.TRANSLATIONS
        .add("message.zinecraft.stargate.damaged", "星门拱体不完整，无法建立事件视界", "The stargate arch is incomplete and cannot form an event horizon");
  }

  private ModBlock() {
  }

  private static Block copyBlock(Block physicalTemplate) {
    return new Block(Properties.ofFullCopy((physicalTemplate)));
  }

  private static BlockEntry<Block> material(String path, String zhCn, Block physicalTemplate) {
    return Zinecraft.BLOCKS.builder(path, zhCn, () -> copyBlock(physicalTemplate))
        .enUs(TranslationNames.toDisplayName(path))
        .build();
  }

  private static BlockEntry<HeadBlock> register(String path, String zhCn, String enUs) {
    return Zinecraft.BLOCKS.builder(
            path,
            zhCn,
            () -> new HeadBlock(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BROWN)
                .noOcclusion()
                .strength(1.0F)
                .sound(SoundType.WOOD))
        )
        .enUs(enUs)
        .noCubeModel()
        .itemProperties(new Item.Properties().rarity(Rarity.UNCOMMON))
        .build();
  }

  private static Block createOre() {
    return new Block(
        Properties.ofFullCopy(Blocks.DEEPSLATE)
            .requiresCorrectToolForDrops()
            .strength(4.0F, 6.0F)
            .sound(SoundType.DEEPSLATE)
    );
  }

  private MaterialOre ore(
      String blockPath,
      String featurePath,
      String zhCn,
      ItemLike drop,
      String cookingGroup,
      int veinSize,
      int veinsPerChunk,
      int maxY,
      float discardChance
  ) {
    BlockEntry<Block> block = Zinecraft.BLOCKS.builder(blockPath, zhCn, ModBlock::createOre)
        .enUs(TranslationNames.toDisplayName(blockPath))
        .drop(drop)
        .build();
    OreEntry feature = Zinecraft.WORLDGEN.getFeatures().ore(
        featurePath,
        block,
        veinSize,
        veinsPerChunk,
        maxY,
        discardChance,
        MATERIAL_DIMENSIONS
    );
    return new MaterialOre(block, feature, drop, cookingGroup);
  }

  private static Block createStargateArch() {
    return new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(18.0F, 1200.0F).sound(SoundType.DEEPSLATE));
  }

  private static int stargateControllerLight(BlockState state) {
    return state.getValue(StarGateControllerBlock.ACCESS.getACTIVE()) ? 12 : 3;
  }

  private static StarGateControllerBlock createStargateController() {
    Properties properties = Properties.of()
        .mapColor(MapColor.COLOR_CYAN)
        .requiresCorrectToolForDrops()
        .strength(18.0F, 1200.0F)
        .lightLevel(ModBlock::stargateControllerLight)
        .sound(SoundType.METAL);
    return new StarGateControllerBlock(properties);
  }

  private static int stargatePortalLight(BlockState state) {
    return 12;
  }

  private static StarGatePortalBlock createStargatePortal() {
    Properties properties = Properties.of()
        .mapColor(MapColor.COLOR_CYAN)
        .noCollission()
        .noOcclusion()
        .strength(-1.0F, 3600000.0F)
        .lightLevel(ModBlock::stargatePortalLight)
        .sound(SoundType.GLASS);
    return new StarGatePortalBlock(properties);
  }

  private static ExampleEntityBlock createExampleEntityBlock() {
    Properties properties = Properties.of().sound(SoundType.GRASS);
    return new ExampleEntityBlock(properties);
  }

}
