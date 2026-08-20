package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.BiomeBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
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
