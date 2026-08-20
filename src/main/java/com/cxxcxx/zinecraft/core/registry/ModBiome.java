package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.nation.TerraNation;
import com.cxxcxx.zinecraft.api.registry.builder.BiomeBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class ModBiome {

  public static final BiomeBuilder AEGIR_ABYSSAL_SEA = biome(
      "aegir_abyssal_sea",
      "阿戈尔深海",
      -0.35F, 0.65F, -1.05F, 0.35F, 0.0F, -0.75F,
      builder -> {
        builder.precipitation(false);
        builder.temperature(0.4F);
        builder.downfall(1.0F);
        builder.waterColor(1523551);
        builder.waterFogColor(465964);
        builder.fogColor(5401986);
        builder.ocean();
        builder.featuredSpawn(MobCategory.WATER_CREATURE, EntityType.DOLPHIN, 8, 1, 2);
      }
  );
  public static final BiomeBuilder BOLIVAR_PLAIN = biome(
      "bolivar_plain",
      "玻利瓦尔平原",
      0.55F, 0.0F, 0.1F, 0.55F, 0.0F, 0.0F,
      builder -> {
        builder.temperature(0.9F);
        builder.downfall(0.35F);
        builder.grassColor(10201685);
        builder.foliageColor(8885319);
        builder.plains();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.PARROT, 12, 1, 2);
      }
  );
  public static final BiomeBuilder HIGASHI_SHADOW_RIFT = biome(
      "higashi_shadow_rift",
      "东国常暗裂谷",
      -0.25F, 0.35F, 0.35F, -0.8F, 0.0F, -0.75F,
      builder -> {
        builder.temperature(0.6F);
        builder.downfall(0.75F);
        builder.fogColor(6910074);
        builder.grassColor(5338204);
        builder.foliageColor(4088909);
        builder.mountain();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.FOX, 10, 1, 2);
      }
  );
  public static final BiomeBuilder DURIN_UNDERGROUND_GARDEN = biome(
      "durin_underground_garden",
      "杜林地下花园",
      0.35F, 0.65F, 0.55F, -0.4F, 1.0F, -0.75F,
      builder -> {
        builder.precipitation(false);
        builder.temperature(1.0F);
        builder.downfall(0.8F);
        builder.waterColor(3516320);
        builder.fogColor(9072552);
        builder.grassColor(5613672);
        builder.cavern();
        builder.featuredSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.GLOW_SQUID, 16, 2, 4);
      }
  );
  public static final BiomeBuilder COLUMBIA_SANDSTONE_WILDS = biome(
      "columbia_sandstone_wilds",
      "哥伦比亚砂岩荒野",
      0.9F, -0.75F, 0.9F, -0.25F, 0.0F, 0.8F,
      builder -> {
        builder.precipitation(false);
        builder.temperature(1.4F);
        builder.downfall(0.1F);
        builder.grassColor(10189641);
        builder.foliageColor(8743742);
        builder.badlands();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.ARMADILLO, 14, 1, 3);
      }
  );
  public static final BiomeBuilder KAZIMIERZ_KNIGHTLAND = biome(
      "kazimierz_knightland",
      "卡西米尔骑士领",
      0.0F, -0.35F, 0.35F, 0.55F, 0.0F, -0.2F,
      builder -> {
        builder.temperature(0.75F);
        builder.downfall(0.35F);
        builder.grassColor(9416530);
        builder.foliageColor(7312197);
        builder.plains();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.HORSE, 18, 2, 5);
        builder.featuredSpawn(MobCategory.CREATURE, ModEntity.CLAMPBEAST.get(), 6, 1, 2);
      }
  );
  public static final BiomeBuilder KAZDEL_SCARRED_WASTES = biome(
      "kazdel_scarred_wastes",
      "卡兹戴尔伤痕荒地",
      0.0F, -0.85F, 0.9F, -0.8F, 0.0F, -0.55F,
      builder -> {
        builder.precipitation(false);
        builder.temperature(0.8F);
        builder.downfall(0.05F);
        builder.fogColor(6445666);
        builder.grassColor(6642512);
        builder.foliageColor(5590341);
        builder.badlands();
      }
  );
  public static final BiomeBuilder LATERANO_HOLY_FIELDS = biome(
      "laterano_holy_fields",
      "拉特兰圣田",
      0.45F, 0.0F, 0.1F, 0.35F, 0.0F, 0.3F,
      builder -> {
        builder.temperature(0.9F);
        builder.downfall(0.5F);
        builder.grassColor(11058793);
        builder.foliageColor(9546332);
        builder.fogColor(14276295);
        builder.plains();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.BEE, 16, 2, 4);
        builder.featuredSpawn(MobCategory.CREATURE, ModEntity.SANKTA_FORMAL_RESIDENT.get(), 8, 10, 20);
      }
  );
  public static final BiomeBuilder LEITHANIEN_TWILIGHT_FOREST = biome(
      "leithanien_twilight_forest",
      "莱塔尼亚暮色林",
      -0.3F, 0.45F, 0.35F, -0.4F, 0.0F, 0.55F,
      builder -> {
        builder.temperature(0.55F);
        builder.downfall(0.8F);
        builder.fogColor(9077147);
        builder.grassColor(5796684);
        builder.foliageColor(4218175);
        builder.forest();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.WOLF, 12, 2, 4);
      }
  );
  public static final BiomeBuilder RIM_BILLITON_MINING_BADLANDS = biome(
      "rim_billiton_mining_badlands",
      "雷姆必拓矿业荒地",
      0.85F, -0.45F, 0.9F, -0.75F, 0.0F, 0.9F,
      builder -> {
        builder.precipitation(false);
        builder.temperature(1.5F);
        builder.downfall(0.1F);
        builder.grassColor(10122311);
        builder.foliageColor(8741438);
        builder.badlands();
      }
  );
  public static final BiomeBuilder MINOS_SUNLIT_HILLS = biome(
      "minos_sunlit_hills",
      "米诺斯日照丘陵",
      0.45F, -0.25F, 0.1F, -0.2F, 0.0F, 0.55F,
      builder -> {
        builder.temperature(1.1F);
        builder.downfall(0.35F);
        builder.grassColor(10926170);
        builder.foliageColor(8559437);
        builder.mountain();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.GOAT, 14, 2, 4);
      }
  );
  public static final BiomeBuilder SARGON_ROCKY_DESERT = biome(
      "sargon_rocky_desert",
      "萨尔贡岩漠",
      0.95F, -0.9F, 0.65F, 0.05F, 0.0F, -0.1F,
      builder -> {
        builder.precipitation(false);
        builder.temperature(2.0F);
        builder.downfall(0.0F);
        builder.fogColor(14070909);
        builder.desert();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.CAMEL, 12, 1, 2);
        builder.featuredSpawn(MobCategory.CREATURE, ModEntity.SANDBEAST.get(), 8, 1, 3);
        builder.featuredSpawn(MobCategory.CREATURE, ModEntity.PACKBEAST.get(), 3, 1, 2);
      }
  );
  public static final BiomeBuilder SAMI_FROZEN_FOREST = biome(
      "sami_frozen_forest",
      "萨米冻林",
      -0.85F, 0.4F, 0.85F, -0.45F, 0.0F, 0.0F,
      builder -> {
        builder.temperature(-0.5F);
        builder.downfall(0.8F);
        builder.fogColor(12176339);
        builder.grassColor(7176824);
        builder.foliageColor(5796198);
        builder.snowyForest();
        builder.featuredSpawn(MobCategory.CREATURE, ModEntity.RIVENBEAST.get(), 4, 1, 2);
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.POLAR_BEAR, 10, 1, 2);
      }
  );
  public static final BiomeBuilder VICTORIA_MISTY_HIGHLANDS = biome(
      "victoria_misty_highlands",
      "维多利亚雾岭",
      -0.2F, 0.7F, 0.4F, 0.0F, 0.0F, 0.65F,
      builder -> {
        builder.temperature(0.65F);
        builder.downfall(0.9F);
        builder.fogColor(11054512);
        builder.grassColor(6718554);
        builder.foliageColor(5600076);
        builder.mountain();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.SHEEP, 18, 2, 4);
        builder.featuredSpawn(MobCategory.CREATURE, ModEntity.FELINE_VICTORIAN_RESIDENT.get(), 8, 10, 20);
      }
  );
  public static final BiomeBuilder URSUS_FROZEN_STEPPE = biome(
      "ursus_frozen_steppe",
      "乌萨斯冻原",
      -0.75F, -0.3F, 0.65F, 0.5F, 0.0F, -0.25F,
      builder -> {
        builder.temperature(-0.2F);
        builder.downfall(0.3F);
        builder.fogColor(12700624);
        builder.grassColor(8687754);
        builder.foliageColor(7438456);
        builder.snowyForest();
      }
  );
  public static final BiomeBuilder KJERAG_SNOWY_PEAKS = biome(
      "kjerag_snowy_peaks",
      "谢拉格雪峰",
      -0.95F, 0.35F, 0.95F, -0.85F, 0.0F, 0.95F,
      builder -> {
        builder.temperature(-0.7F);
        builder.downfall(0.7F);
        builder.fogColor(14016999);
        builder.grassColor(7901322);
        builder.foliageColor(6454645);
        builder.snowyForest();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.RABBIT, 14, 2, 3);
      }
  );
  public static final BiomeBuilder SIRACUSA_RAINY_WOODLAND = biome(
      "siracusa_rainy_woodland",
      "叙拉古雨林",
      0.05F, 0.75F, 0.15F, 0.35F, 0.0F, -0.65F,
      builder -> {
        builder.temperature(0.8F);
        builder.downfall(0.95F);
        builder.fogColor(9213584);
        builder.grassColor(4683086);
        builder.foliageColor(3761215);
        builder.rainyForest();
      }
  );
  public static final BiomeBuilder YAN_MOUNTAIN_GROVE = biome(
      "yan_mountain_grove",
      "炎国山林",
      0.05F, 0.4F, 0.45F, -0.2F, 0.0F, 0.8F,
      builder -> {
        builder.temperature(0.7F);
        builder.downfall(0.8F);
        builder.waterColor(4159141);
        builder.grassColor(6329947);
        builder.foliageColor(4882764);
        builder.mountain();
        builder.featuredSpawn(MobCategory.CREATURE, EntityType.PANDA, 12, 1, 2);
      }
  );
  public static final BiomeBuilder IBERIA_SALT_DELTA = biome(
      "iberia_salt_delta",
      "伊比利亚盐风三角洲",
      0.5F, 0.65F, -0.2F, 0.8F, 0.0F, -0.75F,
      builder -> {
        builder.temperature(1.0F);
        builder.downfall(0.8F);
        builder.waterColor(3043719);
        builder.waterFogColor(1457482);
        builder.fogColor(11450548);
        builder.grassColor(7638130);
        builder.foliageColor(6519653);
        builder.wetland();
      }
  );

  /*
   * 国家范围与自然群系分离后的第二组气候群系。名称描述自然环境，不代表新增官方行政区；
   * 每个国家至少拥有两个气候点，TerraBiomeSource 只会在该国边界内选择本国群系。
   */
  public static final BiomeBuilder AEGIR_PELAGIC_DEPTHS = biome(
      "aegir_pelagic_depths", "阿戈尔远洋深渊",
      -0.65F, 0.9F, -1.2F, -0.1F, 0.35F, 0.45F,
      builder -> builder.precipitation(false).temperature(0.25F).downfall(1.0F)
          .waterColor(0x102E4A).waterFogColor(0x04131F).fogColor(0x3D566B).ocean()
  );
  public static final BiomeBuilder BOLIVAR_BLACKFLOW_FOREST = biome(
      "bolivar_blackflow_forest", "玻利瓦尔黑流树海",
      0.65F, 0.8F, 0.15F, -0.35F, 0.0F, 0.35F,
      builder -> builder.temperature(1.0F).downfall(0.85F)
          .grassColor(0x526B3F).foliageColor(0x344D31).rainyForest()
  );
  public static final BiomeBuilder HIGASHI_MOUNTAIN_FOREST = biome(
      "higashi_mountain_forest", "东国山林",
      -0.45F, 0.65F, 0.55F, -0.55F, 0.0F, 0.55F,
      builder -> builder.temperature(0.5F).downfall(0.85F)
          .fogColor(0x718078).grassColor(0x4C673E).foliageColor(0x365734).forest()
  );
  public static final BiomeBuilder DURIN_CAVERN_LAKE = biome(
      "durin_cavern_lake", "杜林地下湖",
      0.15F, 0.9F, -0.25F, 0.2F, 1.0F, 0.35F,
      builder -> builder.precipitation(false).temperature(0.9F).downfall(1.0F)
          .waterColor(0x2A8B8B).waterFogColor(0x123F4A).fogColor(0x6C8D80).cavern()
  );
  public static final BiomeBuilder COLUMBIA_EASTERN_PRAIRIE = biome(
      "columbia_eastern_prairie", "哥伦比亚东部草原",
      0.45F, -0.1F, 0.35F, 0.45F, 0.0F, -0.35F,
      builder -> builder.temperature(0.95F).downfall(0.3F)
          .grassColor(0x9A9B55).foliageColor(0x7D874D).plains()
  );
  public static final BiomeBuilder KAZIMIERZ_FORESTED_HILLS = biome(
      "kazimierz_forested_hills", "卡西米尔林丘",
      -0.15F, 0.55F, 0.55F, -0.15F, 0.0F, 0.45F,
      builder -> builder.temperature(0.65F).downfall(0.65F)
          .grassColor(0x78925A).foliageColor(0x587448).forest()
  );
  public static final BiomeBuilder KAZDEL_RUINED_HIGHLANDS = biome(
      "kazdel_ruined_highlands", "卡兹戴尔废墟高地",
      -0.2F, -0.65F, 0.75F, -0.65F, 0.0F, 0.65F,
      builder -> builder.precipitation(false).temperature(0.65F).downfall(0.1F)
          .fogColor(0x665B63).grassColor(0x61594C).foliageColor(0x504C42).mountain()
  );
  public static final BiomeBuilder LATERANO_HIGHLAND_PLATEAU = biome(
      "laterano_highland_plateau", "拉特兰高原",
      0.15F, 0.2F, 0.65F, -0.15F, 0.0F, 0.65F,
      builder -> builder.temperature(0.75F).downfall(0.45F)
          .grassColor(0xA3A979).foliageColor(0x879767).fogColor(0xD7E4E9).mountain()
  );
  public static final BiomeBuilder LEITHANIEN_ALPINE_VALLEY = biome(
      "leithanien_alpine_valley", "莱塔尼亚山谷",
      -0.55F, 0.55F, 0.65F, 0.25F, 0.0F, -0.15F,
      builder -> builder.temperature(0.4F).downfall(0.75F)
          .fogColor(0x8B87A0).grassColor(0x52614E).foliageColor(0x3E523F).mountain()
  );
  public static final BiomeBuilder RIM_BILLITON_ARID_MESA = biome(
      "rim_billiton_arid_mesa", "雷姆必拓干旱台地",
      0.75F, -0.75F, 0.7F, -0.35F, 0.0F, 0.45F,
      builder -> builder.precipitation(false).temperature(1.35F).downfall(0.05F)
          .grassColor(0x937D57).foliageColor(0x796B4D).badlands()
  );
  public static final BiomeBuilder MINOS_RIVER_VALLEY = biome(
      "minos_river_valley", "米诺斯河谷",
      0.35F, 0.5F, 0.0F, 0.55F, 0.0F, -0.25F,
      builder -> builder.temperature(1.0F).downfall(0.55F)
          .waterColor(0x3A78A0).grassColor(0x9E9B58).foliageColor(0x7C844E).plains()
  );
  public static final BiomeBuilder SARGON_TROPICAL_RAINFOREST = biome(
      "sargon_tropical_rainforest", "萨尔贡热带雨林",
      0.95F, 0.95F, 0.2F, -0.45F, 0.0F, 0.25F,
      builder -> builder.temperature(1.8F).downfall(1.0F)
          .fogColor(0x9AB79D).grassColor(0x3D7A3B).foliageColor(0x285F31).jungle()
  );
  public static final BiomeBuilder SAMI_GLACIAL_MOUNTAINS = biome(
      "sami_glacial_mountains", "萨米冰川山脉",
      -1.0F, 0.25F, 0.95F, -0.85F, 0.0F, 0.75F,
      builder -> builder.temperature(-0.8F).downfall(0.55F)
          .fogColor(0xC7D8E6).grassColor(0x71847A).foliageColor(0x5A7068).snowyForest()
  );
  public static final BiomeBuilder VICTORIA_CENTRAL_LOWLANDS = biome(
      "victoria_central_lowlands", "维多利亚中央谷地",
      -0.05F, 0.75F, 0.05F, 0.6F, 0.0F, -0.35F,
      builder -> builder.temperature(0.7F).downfall(0.9F)
          .fogColor(0xAAB5B0).grassColor(0x69825B).foliageColor(0x527149).plains()
  );
  public static final BiomeBuilder URSUS_EASTERN_HIGHLANDS = biome(
      "ursus_eastern_highlands", "乌萨斯东部高地",
      -0.9F, -0.1F, 0.9F, -0.65F, 0.0F, 0.55F,
      builder -> builder.temperature(-0.45F).downfall(0.35F)
          .fogColor(0xC1CBD2).grassColor(0x7A866F).foliageColor(0x687966).snowyForest()
  );
  public static final BiomeBuilder KJERAG_ALPINE_FOREST = biome(
      "kjerag_alpine_forest", "谢拉格高山森林",
      -0.75F, 0.7F, 0.6F, -0.25F, 0.0F, 0.25F,
      builder -> builder.temperature(-0.35F).downfall(0.8F)
          .fogColor(0xCEDBE2).grassColor(0x71866E).foliageColor(0x58725B).snowyForest()
  );
  public static final BiomeBuilder SIRACUSA_LOWLAND_FOREST = biome(
      "siracusa_lowland_forest", "叙拉古低地林",
      0.2F, 0.9F, -0.05F, 0.65F, 0.0F, 0.15F,
      builder -> builder.temperature(0.85F).downfall(1.0F)
          .fogColor(0x8D9B91).grassColor(0x477344).foliageColor(0x315E38).rainyForest()
  );
  public static final BiomeBuilder YAN_RIVER_PLAINS = biome(
      "yan_river_plains", "炎国江河平原",
      0.35F, 0.75F, 0.0F, 0.65F, 0.0F, -0.15F,
      builder -> builder.temperature(0.85F).downfall(0.85F)
          .waterColor(0x3F7D8F).grassColor(0x69905D).foliageColor(0x4E794F).plains()
  );
  public static final BiomeBuilder IBERIA_COASTAL_CLIFFS = biome(
      "iberia_coastal_cliffs", "伊比利亚海岸崖地",
      0.25F, 0.75F, 0.35F, -0.65F, 0.0F, 0.7F,
      builder -> builder.temperature(0.85F).downfall(0.85F)
          .waterColor(0x2D657A).waterFogColor(0x163B49).fogColor(0xA9B8BE)
          .grassColor(0x718074).foliageColor(0x5D7165).mountain()
  );
  public static final BiomeBuilder TERRA_RIVER = biome(
      "terra_river",
      "泰拉河流",
      0.2F, 0.75F, -0.15F, 0.65F, 0.0F, 0.0F,
      builder -> {
        builder.temperature(0.8F);
        builder.downfall(0.8F);
        builder.waterColor(0x3F76E4);
        builder.waterFogColor(0x050533);
        builder.river();
      }
  );
  public static final BiomeBuilder TERRA_OUTER_OCEAN = biome(
      "terra_outer_ocean",
      "泰拉外海",
      -0.45F, 0.8F, -1.25F, 0.3F, 0.0F, -0.65F,
      builder -> {
        builder.temperature(0.45F);
        builder.downfall(0.9F);
        builder.waterColor(0x183D65);
        builder.waterFogColor(0x071A2D);
        builder.fogColor(0x7894A8);
        builder.ocean();
      }
  );
  public static final BiomeBuilder TERRA_CATASTROPHE_ZONE = biome(
      "terra_catastrophe_zone",
      "泰拉天灾区",
      0.8F, -0.8F, 0.5F, -0.5F, 0.0F, 0.2F,
      builder -> {
        builder.precipitation(false);
        builder.temperature(1.2F);
        builder.downfall(0.0F);
        builder.fogColor(0x51405F);
        builder.grassColor(0x5B514B);
        builder.foliageColor(0x51453F);
        builder.badlands();
      }
  );

  /**
   * 泰拉群系目录注册的全部 Builder，其中包含非国家群系“天灾区”。
   */
  public static final List<BiomeBuilder> ALL = List.copyOf(Zinecraft.BIOMES.entries);

  /**
   * 泰拉全部群系的资源键，供地物和群系选择条件使用。
   */
  public static final List<ResourceKey<Biome>> ALL_TERRA_BIOMES =
      ALL.stream().map(BiomeBuilder::key).toList();

  /**
   * 不代表国家范围、只用于河网和维度边缘的辅助群系。
   */
  public static final List<ResourceKey<Biome>> MAP_SUPPORT_BIOMES = List.of(
      TERRA_RIVER.key(), TERRA_OUTER_OCEAN.key()
  );

  /**
   * 国家是地图区域；群系是该区域内由气候噪声选择的自然环境。
   */
  public static final Map<TerraNation, List<BiomeBuilder>> NATIONAL_BIOMES = Map.ofEntries(
      Map.entry(TerraNation.AEGIR, List.of(AEGIR_ABYSSAL_SEA, AEGIR_PELAGIC_DEPTHS)),
      Map.entry(TerraNation.BOLIVAR, List.of(BOLIVAR_PLAIN, BOLIVAR_BLACKFLOW_FOREST)),
      Map.entry(TerraNation.HIGASHI, List.of(HIGASHI_SHADOW_RIFT, HIGASHI_MOUNTAIN_FOREST)),
      Map.entry(TerraNation.DURIN, List.of(DURIN_UNDERGROUND_GARDEN, DURIN_CAVERN_LAKE)),
      Map.entry(TerraNation.COLUMBIA, List.of(COLUMBIA_SANDSTONE_WILDS, COLUMBIA_EASTERN_PRAIRIE)),
      Map.entry(TerraNation.KAZIMIERZ, List.of(KAZIMIERZ_KNIGHTLAND, KAZIMIERZ_FORESTED_HILLS)),
      Map.entry(TerraNation.KAZDEL, List.of(KAZDEL_SCARRED_WASTES, KAZDEL_RUINED_HIGHLANDS)),
      Map.entry(TerraNation.LATERANO, List.of(LATERANO_HOLY_FIELDS, LATERANO_HIGHLAND_PLATEAU)),
      Map.entry(TerraNation.LEITHANIEN, List.of(LEITHANIEN_TWILIGHT_FOREST, LEITHANIEN_ALPINE_VALLEY)),
      Map.entry(TerraNation.RIM_BILLITON, List.of(RIM_BILLITON_MINING_BADLANDS, RIM_BILLITON_ARID_MESA)),
      Map.entry(TerraNation.MINOS, List.of(MINOS_SUNLIT_HILLS, MINOS_RIVER_VALLEY)),
      Map.entry(TerraNation.SARGON, List.of(SARGON_ROCKY_DESERT, SARGON_TROPICAL_RAINFOREST)),
      Map.entry(TerraNation.SAMI, List.of(SAMI_FROZEN_FOREST, SAMI_GLACIAL_MOUNTAINS)),
      Map.entry(TerraNation.VICTORIA, List.of(VICTORIA_MISTY_HIGHLANDS, VICTORIA_CENTRAL_LOWLANDS)),
      Map.entry(TerraNation.URSUS, List.of(URSUS_FROZEN_STEPPE, URSUS_EASTERN_HIGHLANDS)),
      Map.entry(TerraNation.KJERAG, List.of(KJERAG_SNOWY_PEAKS, KJERAG_ALPINE_FOREST)),
      Map.entry(TerraNation.SIRACUSA, List.of(SIRACUSA_RAINY_WOODLAND, SIRACUSA_LOWLAND_FOREST)),
      Map.entry(TerraNation.YAN, List.of(YAN_MOUNTAIN_GROVE, YAN_RIVER_PLAINS)),
      Map.entry(TerraNation.IBERIA, List.of(IBERIA_SALT_DELTA, IBERIA_COASTAL_CLIFFS))
  );

  private ModBiome() {
  }

  private static BiomeBuilder biome(
      String path,
      String zhCn,
      float climateTemperature,
      float humidity,
      float continentalness,
      float erosion,
      float depth,
      float weirdness,
      Consumer<? super BiomeBuilder> configure
  ) {
    return new BiomeBuilder(Zinecraft.BIOMES, path, zhCn)
        .climate(climateTemperature, humidity, continentalness, erosion, depth, weirdness)
        .configure(configure)
        .build();
  }

  public static void bootstrap() {
  }
}
