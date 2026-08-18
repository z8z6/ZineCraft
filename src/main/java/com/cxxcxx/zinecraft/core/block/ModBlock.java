package com.cxxcxx.zinecraft.core.block;

import com.cxxcxx.zinecraft.api.block.BlockEntry;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.api.world.feature.MaterialOre;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.ModBiome;
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock;
import com.cxxcxx.zinecraft.core.item.ModItem;
import com.cxxcxx.zinecraft.core.structure.stargate.StarGateControllerBlock;
import com.cxxcxx.zinecraft.core.structure.stargate.StarGatePortalBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public final class ModBlock {
  static {
    Zinecraft.TRANSLATIONS
        .add("message.zinecraft.stargate.requires_protocol_originium", "控制器拒绝访问：请手持协议源石右键激活", "Access denied: right-click while holding Protocol Originium");
    Zinecraft.TRANSLATIONS
        .add("message.zinecraft.stargate.activated", "协议认证完成，星门事件视界已建立", "Protocol accepted. Stargate event horizon established");
    Zinecraft.TRANSLATIONS.add("message.zinecraft.stargate.already_active", "星门已处于激活状态", "The stargate is already active");
    Zinecraft.TRANSLATIONS
        .add("message.zinecraft.stargate.damaged", "星门拱体不完整，无法建立事件视界", "The stargate arch is incomplete and cannot form an event horizon");
  }

  public static final BlockEntry<Block> AEGIR_ABYSSAL_SLATE = block(
      "aegir_abyssal_slate", "阿戈尔深渊岩板", Blocks.DARK_PRISMARINE
  );
  public static final BlockEntry<Block> AEGIR_PRESSURE_TILE = block(
      "aegir_pressure_tile", "阿戈尔耐压墙砖", Blocks.PRISMARINE_BRICKS
  );
  public static final BlockEntry<Block> BOLIVAR_WAR_SCOURED_SOIL = block(
      "bolivar_war_scoured_soil", "玻利瓦尔战蚀土", Blocks.COARSE_DIRT
  );
  public static final BlockEntry<Block> BOLIVAR_DOSSOLES_STUCCO = block(
      "bolivar_dossoles_stucco", "多索雷斯灰泥墙", Blocks.TERRACOTTA
  );
  public static final BlockEntry<Block> HIGASHI_SHADOW_LOAM = block(
      "higashi_shadow_loam", "东国裂谷暗壤", Blocks.PODZOL
  );
  public static final BlockEntry<Block> HIGASHI_MACHIYA_PLASTER = block(
      "higashi_machiya_plaster", "东国町屋灰泥墙", Blocks.DARK_OAK_PLANKS
  );
  public static final BlockEntry<Block> DURIN_GARDEN_MOSS = block(
      "durin_garden_moss", "杜林花园苔土", Blocks.MOSS_BLOCK
  );
  public static final BlockEntry<Block> DURIN_IDEAL_CITY_PANEL = block(
      "durin_ideal_city_panel", "杜林理想城彩板", Blocks.CUT_COPPER
  );
  public static final BlockEntry<Block> COLUMBIA_CANYON_SOIL = block(
      "columbia_canyon_soil", "哥伦比亚峡谷砂土", Blocks.RED_SAND
  );
  public static final BlockEntry<Block> COLUMBIA_FRONTIER_PANEL = block(
      "columbia_frontier_panel", "哥伦比亚拓荒墙板", Blocks.WHITE_CONCRETE
  );
  public static final BlockEntry<Block> KAZIMIERZ_STEPPE_TURF = block(
      "kazimierz_steppe_turf", "卡西米尔旱原草皮", Blocks.GRASS_BLOCK
  );
  public static final BlockEntry<Block> KAZIMIERZ_ARENA_MASONRY = block(
      "kazimierz_arena_masonry", "卡西米尔竞技场石砌", Blocks.SMOOTH_QUARTZ
  );
  public static final BlockEntry<Block> KAZDEL_SCARRED_ASH = block(
      "kazdel_scarred_ash", "卡兹戴尔战痕灰烬", Blocks.BLACKSTONE
  );
  public static final BlockEntry<Block> KAZDEL_FORTRESS_PLATE = block(
      "kazdel_fortress_plate", "卡兹戴尔要塞装甲板", Blocks.POLISHED_BLACKSTONE_BRICKS
  );
  public static final BlockEntry<Block> LATERANO_ALLUVIAL_CHALK = block(
      "laterano_alluvial_chalk", "拉特兰冲积白垩", Blocks.CALCITE
  );
  public static final BlockEntry<Block> LATERANO_BASILICA_MARBLE = block(
      "laterano_basilica_marble", "拉特兰圣堂大理石", Blocks.QUARTZ_BRICKS
  );
  public static final BlockEntry<Block> LATERANO_HOST_CASING = block(
      "laterano_host_casing", "拉特兰主机银壳", Blocks.IRON_BLOCK
  );
  public static final BlockEntry<Block> LATERANO_HOST_CONDUIT = block(
      "laterano_host_conduit", "拉特兰主机同步导管", Blocks.SEA_LANTERN
  );
  public static final BlockEntry<Block> LEITHANIEN_TWILIGHT_HUMUS = block(
      "leithanien_twilight_humus", "莱塔尼亚暮林腐殖土", Blocks.ROOTED_DIRT
  );
  public static final BlockEntry<Block> LEITHANIEN_RESONANT_BRICK = block(
      "leithanien_resonant_brick", "莱塔尼亚共振砖", Blocks.POLISHED_DEEPSLATE
  );
  public static final BlockEntry<Block> RIM_BILLITON_MINE_TAILINGS = block(
      "rim_billiton_mine_tailings", "雷姆必拓矿渣土", Blocks.TERRACOTTA
  );
  public static final BlockEntry<Block> RIM_BILLITON_CORRUGATED_STEEL = block(
      "rim_billiton_corrugated_steel", "雷姆必拓波纹钢板", Blocks.CUT_COPPER
  );
  public static final BlockEntry<Block> MINOS_SUNBAKED_EARTH = block(
      "minos_sunbaked_earth", "米诺斯晒土地", Blocks.PACKED_MUD
  );
  public static final BlockEntry<Block> MINOS_HEROIC_MASONRY = block(
      "minos_heroic_masonry", "米诺斯英雄石砌", Blocks.SMOOTH_SANDSTONE
  );
  public static final BlockEntry<Block> SARGON_DESERT_CRUST = block(
      "sargon_desert_crust", "萨尔贡岩漠硬壳", Blocks.SAND
  );
  public static final BlockEntry<Block> SARGON_OASIS_ADOBE = block(
      "sargon_oasis_adobe", "萨尔贡绿洲土坯", Blocks.TERRACOTTA
  );
  public static final BlockEntry<Block> SAMI_FROST_MOSS = block(
      "sami_frost_moss", "萨米冻原苔土", Blocks.SNOW_BLOCK
  );
  public static final BlockEntry<Block> SAMI_RITUAL_STONE = block(
      "sami_ritual_stone", "萨米祭仪石", Blocks.STONE_BRICKS
  );
  public static final BlockEntry<Block> SAMI_TRIBAL_TIMBER = block(
      "sami_tribal_timber", "萨米部族木构", Blocks.SPRUCE_PLANKS
  );
  public static final BlockEntry<Block> VICTORIA_MOORLAND_SOIL = block(
      "victoria_moorland_soil", "维多利亚雾沼土", Blocks.MUD
  );
  public static final BlockEntry<Block> VICTORIA_INDUSTRIAL_BRICK = block(
      "victoria_industrial_brick", "维多利亚工业砖", Blocks.BRICKS
  );
  public static final BlockEntry<Block> VICTORIA_WALL_ARMOR = block(
      "victoria_wall_armor", "维多利亚城防装甲", Blocks.IRON_BLOCK
  );
  public static final BlockEntry<Block> VICTORIA_CANNON_CASING = block(
      "victoria_cannon_casing", "维多利亚巨炮壳板", Blocks.IRON_BLOCK
  );
  public static final BlockEntry<Block> VICTORIA_STRUCTURAL_FRAME = block(
      "victoria_structural_frame", "维多利亚承力骨架", Blocks.POLISHED_DEEPSLATE
  );
  public static final BlockEntry<Block> VICTORIA_REINFORCED_FLOOR = block(
      "victoria_reinforced_floor", "维多利亚防滑钢地板", Blocks.IRON_BLOCK
  );
  public static final BlockEntry<Block> VICTORIA_CONTROL_PANEL = block(
      "victoria_control_panel", "维多利亚火控面板", Blocks.IRON_BLOCK
  );
  public static final BlockEntry<Block> VICTORIA_BATTLE_SCARRED_ARMOR = block(
      "victoria_battle_scarred_armor", "维多利亚弹痕装甲", Blocks.IRON_BLOCK
  );
  public static final BlockEntry<Block> VICTORIA_BLAST_SCARRED_ARMOR = block(
      "victoria_blast_scarred_armor", "维多利亚爆蚀装甲", Blocks.IRON_BLOCK
  );
  public static final BlockEntry<Block> URSUS_PERMAFROST = block(
      "ursus_permafrost", "乌萨斯永冻土", Blocks.PACKED_ICE
  );
  public static final BlockEntry<Block> URSUS_IMPERIAL_MASONRY = block(
      "ursus_imperial_masonry", "乌萨斯帝国石砌", Blocks.STONE_BRICKS
  );
  public static final BlockEntry<Block> KJERAG_SACRED_SNOWSTONE = block(
      "kjerag_sacred_snowstone", "谢拉格圣雪岩", Blocks.BLUE_ICE
  );
  public static final BlockEntry<Block> KJERAG_MONASTERY_STONE = block(
      "kjerag_monastery_stone", "谢拉格蔓珠院石墙", Blocks.STONE_BRICKS
  );
  public static final BlockEntry<Block> SIRACUSA_RAIN_DARKENED_SOIL = block(
      "siracusa_rain_darkened_soil", "叙拉古雨浸土", Blocks.MOSSY_COBBLESTONE
  );
  public static final BlockEntry<Block> SIRACUSA_FAMILY_MASONRY = block(
      "siracusa_family_masonry", "叙拉古家族石砌", Blocks.BRICKS
  );
  public static final BlockEntry<Block> YAN_MOUNTAIN_SOIL = block(
      "yan_mountain_soil", "炎国山壤", Blocks.TUFF
  );
  public static final BlockEntry<Block> YAN_COURTYARD_BRICK = block(
      "yan_courtyard_brick", "炎国院墙青砖", Blocks.TUFF_BRICKS
  );
  public static final BlockEntry<Block> IBERIA_SALT_CRUSTED_GRAVEL = block(
      "iberia_salt_crusted_gravel", "伊比利亚盐壳砾石", Blocks.GRAVEL
  );
  public static final BlockEntry<Block> IBERIA_COASTAL_MASONRY = block(
      "iberia_coastal_masonry", "伊比利亚海岸石砌", Blocks.STONE_BRICKS
  );
  public static final BlockEntry<HeadBlock> Z8Z6Z8Z6_HEAD = head(
      "z8z6z8z6_head", "z8z6z8z6 的头", "z8z6z8z6's Head"
  );
  public static final BlockEntry<HeadBlock> YE_XINGCHEN_HEAD = head(
      "ye_xingchen_head", "Ye_xingchen 的头", "Ye_xingchen's Head"
  );
  public static final MaterialOre ORIGINITE_ORE = ore(
      "originite_ore", "originite_ore", "源石矿", ModItem.ORIGINITE,
      "originite", 3, 2, -32, 0.25F
  );
  public static final MaterialOre ORIROCK_ORE = ore(
      "orirock_ore", "orirock_ore", "源岩矿", ModItem.ORIROCK,
      "orirock", 10, 12, 64, 0.0F
  );
  public static final MaterialOre ORIRON_ORE = ore(
      "oriron_ore", "oriron_ore", "异铁矿", ModItem.ORIRON_SHARD,
      "oriron_shard", 7, 8, 32, 0.0F
  );
  public static final MaterialOre MANGANESE_ORE = ore(
      "manganese_ore_block", "manganese_ore", "轻锰矿脉", ModItem.MANGANESE_ORE,
      "manganese_ore", 5, 6, 16, 0.1F
  );
  public static final MaterialOre GRINDSTONE_ORE = ore(
      "grindstone_ore", "grindstone_ore", "研磨石矿", ModItem.GRINDSTONE,
      "grindstone", 6, 5, 0, 0.15F
  );
  public static final MaterialOre RMA70_ORE = ore(
      "rma70_ore", "rma70_ore", "RMA70 矿", ModItem.RMA70_12,
      "rma70_12", 4, 3, -32, 0.25F
  );
  public static final MaterialOre CRYSTAL_ELEMENT_ORE = ore(
      "crystal_element_ore", "crystal_element_ore", "晶体元件矿", ModItem.CRYSTAL_ELEMENT,
      "crystal_element", 5, 4, 16, 0.15F
  );
  public static final MaterialOre LOXIC_KOHL_ORE = ore(
      "loxic_kohl_ore", "loxic_kohl_ore", "炽合金矿", ModItem.LOXIC_KOHL,
      "loxic_kohl", 4, 3, -16, 0.2F
  );
  public static final BlockEntry<Block> STARGATE_ARCH = Zinecraft.BLOCKS.builder(
          "stargate_arch", "星门拱石",
          () -> new Block(Properties.of()
              .mapColor(MapColor.COLOR_BLACK)
              .requiresCorrectToolForDrops()
              .strength(18.0F, 1200.0F)
              .sound(SoundType.DEEPSLATE))
      )
      .enUs("Stargate Archstone")
      .build();

  public static final BlockEntry<StarGateControllerBlock> STARGATE_CONTROLLER = Zinecraft.BLOCKS.builder(
          "stargate_controller", "星门协议控制器",
          () -> new StarGateControllerBlock(Properties.of()
              .mapColor(MapColor.COLOR_CYAN)
              .requiresCorrectToolForDrops()
              .strength(18.0F, 1200.0F)
              .lightLevel((state) -> state.getValue(StarGateControllerBlock.ACTIVE) ? 12 : 3)
              .sound(SoundType.METAL)))
      .enUs("Stargate Protocol Controller")
      .build();

  public static final BlockEntry<StarGatePortalBlock> STARGATE_PORTAL = Zinecraft.BLOCKS.builder(
          "stargate_portal", "星门事件视界",
          () -> new StarGatePortalBlock(Properties.of()
              .mapColor(MapColor.COLOR_CYAN)
              .noCollission()
              .noOcclusion()
              .strength(-1.0F, 3600000.0F)
              .lightLevel((state) -> 12)
              .sound(SoundType.GLASS)))
      .enUs("Stargate Event Horizon")
      .noLoot()
      .noCubeModel()
      .noBlockItem()
      .build();

  public static final BlockEntry<ExampleEntityBlock> EXAMPLE_ENTITY_BLOCK = Zinecraft.BLOCKS.builder(
          "example_entity_block", "示例实体方块",
          () -> new ExampleEntityBlock(Properties.of().sound(SoundType.GRASS)))
      .build();

  // 方块辅助注册函数
  private static BlockEntry<Block> block(String path, String zhCn, Block physicalTemplate) {
    return Zinecraft.BLOCKS.builder(path, zhCn, () -> new Block(Properties.ofFullCopy((physicalTemplate))))
        .build();
  }

  // 方块辅助注册函数
  private static <T extends Block> BlockEntry<T> block(String path, String zhCn, Supplier<T> factory) {
    return Zinecraft.BLOCKS.builder(path, zhCn, factory)
        .build();
  }

  // 玩家的头辅助注册函数
  private static BlockEntry<HeadBlock> head(String path, String zhCn, String enUs) {
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

  private ModBlock() {
  }

  /**
   * 注册矿石方块、矿脉地物、掉落物与烧炼元数据。
   *
   * @param blockPath     方块及其物品的注册路径
   * @param featurePath   配置地物和放置地物的注册路径
   * @param zhCn          矿石方块中文名
   * @param drop          挖掘矿石时掉落的物品
   * @param cookingGroup  熔炼与高炉配方使用的分组名
   * @param veinSize      单条矿脉最多生成的方块数
   * @param veinsPerChunk 每区块尝试放置矿脉的次数
   * @param maxY          偏向底部高度分布的最高端点
   * @param discardChance 矿石暴露于空气时被丢弃的概率
   */
  private static MaterialOre ore(
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
    return Zinecraft.WORLDGEN.features.materialOre(
        blockPath,
        featurePath,
        zhCn,
        () -> new Block(
            Properties.ofFullCopy(Blocks.DEEPSLATE)
                .requiresCorrectToolForDrops()
                .strength(4.0F, 6.0F)
                .sound(SoundType.DEEPSLATE)
        ),
        drop,
        cookingGroup,
        veinSize,
        veinsPerChunk,
        maxY,
        discardChance,
        BiomeSelection.union(
            BiomeSelection.overworld(),
            BiomeSelection.of(ModBiome.ALL_TERRA_BIOMES)
        )
    );
  }

  public static void bootstrap() {
  }
}
