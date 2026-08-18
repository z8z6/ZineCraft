package com.cxxcxx.zinecraft.core.biome;

import com.cxxcxx.zinecraft.api.world.biome.SimpleBiomeBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.function.Consumer;

public final class ModBiome {

  public static final ResourceKey<Biome> AEGIR_ABYSSAL_SEA = biome(
      "aegir_abyssal_sea",
      "阿戈尔深海",
      builder -> {
        builder.setPrecipitation(false);
        builder.setTemperature(0.4F);
        builder.setDownfall(1.0F);
        builder.setWaterColor(1523551);
        builder.setWaterFogColor(465964);
        builder.setFogColor(5401986);
        NationBiomePresets.INSTANCE.ocean(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.WATER_CREATURE, EntityType.DOLPHIN, 8, 1, 2
        );
      }
  );
  public static final ResourceKey<Biome> BOLIVAR_PLAIN = biome(
      "bolivar_plain",
      "玻利瓦尔平原",
      builder -> {
        builder.setTemperature(0.9F);
        builder.setDownfall(0.35F);
        builder.setGrassColor(10201685);
        builder.setFoliageColor(8885319);
        NationBiomePresets.INSTANCE.plains(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.PARROT, 12, 1, 2
        );
      }
  );
  public static final ResourceKey<Biome> HIGASHI_SHADOW_RIFT = biome(
      "higashi_shadow_rift",
      "东国常暗裂谷",
      builder -> {
        builder.setTemperature(0.6F);
        builder.setDownfall(0.75F);
        builder.setFogColor(6910074);
        builder.setGrassColor(5338204);
        builder.setFoliageColor(4088909);
        NationBiomePresets.INSTANCE.mountain(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.FOX, 10, 1, 2
        );
      }
  );
  public static final ResourceKey<Biome> DURIN_UNDERGROUND_GARDEN = biome(
      "durin_underground_garden",
      "杜林地下花园",
      builder -> {
        builder.setPrecipitation(false);
        builder.setTemperature(1.0F);
        builder.setDownfall(0.8F);
        builder.setWaterColor(3516320);
        builder.setFogColor(9072552);
        builder.setGrassColor(5613672);
        NationBiomePresets.INSTANCE.cavern(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.GLOW_SQUID, 16, 2, 4
        );
      }
  );
  public static final ResourceKey<Biome> COLUMBIA_SANDSTONE_WILDS = biome(
      "columbia_sandstone_wilds",
      "哥伦比亚砂岩荒野",
      builder -> {
        builder.setPrecipitation(false);
        builder.setTemperature(1.4F);
        builder.setDownfall(0.1F);
        builder.setGrassColor(10189641);
        builder.setFoliageColor(8743742);
        NationBiomePresets.INSTANCE.badlands(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.ARMADILLO, 14, 1, 3
        );
      }
  );
  public static final ResourceKey<Biome> KAZIMIERZ_KNIGHTLAND = biome(
      "kazimierz_knightland",
      "卡西米尔骑士领",
      builder -> {
        builder.setTemperature(0.75F);
        builder.setDownfall(0.35F);
        builder.setGrassColor(9416530);
        builder.setFoliageColor(7312197);
        NationBiomePresets.INSTANCE.plains(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.HORSE, 18, 2, 5
        );
      }
  );
  public static final ResourceKey<Biome> KAZDEL_SCARRED_WASTES = biome(
      "kazdel_scarred_wastes",
      "卡兹戴尔伤痕荒地",
      builder -> {
        builder.setPrecipitation(false);
        builder.setTemperature(0.8F);
        builder.setDownfall(0.05F);
        builder.setFogColor(6445666);
        builder.setGrassColor(6642512);
        builder.setFoliageColor(5590341);
        NationBiomePresets.INSTANCE.badlands(builder);
      }
  );
  public static final ResourceKey<Biome> LATERANO_HOLY_FIELDS = biome(
      "laterano_holy_fields",
      "拉特兰圣田",
      builder -> {
        builder.setTemperature(0.9F);
        builder.setDownfall(0.5F);
        builder.setGrassColor(11058793);
        builder.setFoliageColor(9546332);
        builder.setFogColor(14276295);
        NationBiomePresets.INSTANCE.plains(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.BEE, 16, 2, 4
        );
      }
  );
  public static final ResourceKey<Biome> LEITHANIEN_TWILIGHT_FOREST = biome(
      "leithanien_twilight_forest",
      "莱塔尼亚暮色林",
      builder -> {
        builder.setTemperature(0.55F);
        builder.setDownfall(0.8F);
        builder.setFogColor(9077147);
        builder.setGrassColor(5796684);
        builder.setFoliageColor(4218175);
        NationBiomePresets.INSTANCE.forest(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.WOLF, 12, 2, 4
        );
      }
  );
  public static final ResourceKey<Biome> RIM_BILLITON_MINING_BADLANDS = biome(
      "rim_billiton_mining_badlands",
      "雷姆必拓矿业荒地",
      builder -> {
        builder.setPrecipitation(false);
        builder.setTemperature(1.5F);
        builder.setDownfall(0.1F);
        builder.setGrassColor(10122311);
        builder.setFoliageColor(8741438);
        NationBiomePresets.INSTANCE.badlands(builder);
      }
  );
  public static final ResourceKey<Biome> MINOS_SUNLIT_HILLS = biome(
      "minos_sunlit_hills",
      "米诺斯日照丘陵",
      builder -> {
        builder.setTemperature(1.1F);
        builder.setDownfall(0.35F);
        builder.setGrassColor(10926170);
        builder.setFoliageColor(8559437);
        NationBiomePresets.INSTANCE.mountain(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.GOAT, 14, 2, 4
        );
      }
  );
  public static final ResourceKey<Biome> SARGON_ROCKY_DESERT = biome(
      "sargon_rocky_desert",
      "萨尔贡岩漠",
      builder -> {
        builder.setPrecipitation(false);
        builder.setTemperature(2.0F);
        builder.setDownfall(0.0F);
        builder.setFogColor(14070909);
        NationBiomePresets.INSTANCE.desert(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.CAMEL, 12, 1, 2
        );
      }
  );
  public static final ResourceKey<Biome> SAMI_FROZEN_FOREST = biome(
      "sami_frozen_forest",
      "萨米冻林",
      builder -> {
        builder.setTemperature(-0.5F);
        builder.setDownfall(0.8F);
        builder.setFogColor(12176339);
        builder.setGrassColor(7176824);
        builder.setFoliageColor(5796198);
        NationBiomePresets.INSTANCE.snowyForest(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.POLAR_BEAR, 10, 1, 2
        );
      }
  );
  public static final ResourceKey<Biome> VICTORIA_MISTY_HIGHLANDS = biome(
      "victoria_misty_highlands",
      "维多利亚雾岭",
      builder -> {
        builder.setTemperature(0.65F);
        builder.setDownfall(0.9F);
        builder.setFogColor(11054512);
        builder.setGrassColor(6718554);
        builder.setFoliageColor(5600076);
        NationBiomePresets.INSTANCE.mountain(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.SHEEP, 18, 2, 4
        );
      }
  );
  public static final ResourceKey<Biome> URSUS_FROZEN_STEPPE = biome(
      "ursus_frozen_steppe",
      "乌萨斯冻原",
      builder -> {
        builder.setTemperature(-0.2F);
        builder.setDownfall(0.3F);
        builder.setFogColor(12700624);
        builder.setGrassColor(8687754);
        builder.setFoliageColor(7438456);
        NationBiomePresets.INSTANCE.snowyForest(builder);
      }
  );
  public static final ResourceKey<Biome> KJERAG_SNOWY_PEAKS = biome(
      "kjerag_snowy_peaks",
      "谢拉格雪峰",
      builder -> {
        builder.setTemperature(-0.7F);
        builder.setDownfall(0.7F);
        builder.setFogColor(14016999);
        builder.setGrassColor(7901322);
        builder.setFoliageColor(6454645);
        NationBiomePresets.INSTANCE.snowyForest(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.RABBIT, 14, 2, 3
        );
      }
  );
  public static final ResourceKey<Biome> SIRACUSA_RAINY_WOODLAND = biome(
      "siracusa_rainy_woodland",
      "叙拉古雨林",
      builder -> {
        builder.setTemperature(0.8F);
        builder.setDownfall(0.95F);
        builder.setFogColor(9213584);
        builder.setGrassColor(4683086);
        builder.setFoliageColor(3761215);
        NationBiomePresets.INSTANCE.rainyForest(builder);
      }
  );
  public static final ResourceKey<Biome> YAN_MOUNTAIN_GROVE = biome(
      "yan_mountain_grove",
      "炎国山林",
      builder -> {
        builder.setTemperature(0.7F);
        builder.setDownfall(0.8F);
        builder.setWaterColor(4159141);
        builder.setGrassColor(6329947);
        builder.setFoliageColor(4882764);
        NationBiomePresets.INSTANCE.mountain(builder);
        NationBiomePresets.INSTANCE.featuredSpawn(
            builder, MobCategory.CREATURE, EntityType.PANDA, 12, 1, 2
        );
      }
  );
  public static final ResourceKey<Biome> IBERIA_SALT_DELTA = biome(
      "iberia_salt_delta",
      "伊比利亚盐风三角洲",
      builder -> {
        builder.setTemperature(1.0F);
        builder.setDownfall(0.8F);
        builder.setWaterColor(3043719);
        builder.setWaterFogColor(1457482);
        builder.setFogColor(11450548);
        builder.setGrassColor(7638130);
        builder.setFoliageColor(6519653);
        NationBiomePresets.INSTANCE.wetland(builder);
      }
  );
  public static final ResourceKey<Biome> TERRA_CATASTROPHE_ZONE = biome(
      "terra_catastrophe_zone",
      "泰拉天灾区",
      builder -> {
        builder.setPrecipitation(false);
        builder.setTemperature(1.2F);
        builder.setDownfall(0.0F);
        builder.setFogColor(0x51405F);
        builder.setGrassColor(0x5B514B);
        builder.setFoliageColor(0x51453F);
        NationBiomePresets.INSTANCE.badlands(builder);
      }
  );

  /**
   * 泰拉群系目录注册的全部群系，其中包含非国家群系“天灾区”。
   */
  public static final List<ResourceKey<Biome>> ALL_TERRA_BIOMES =
      List.copyOf(Zinecraft.WORLDGEN.biomes.keys);

  private ModBiome() {
  }

  private static ResourceKey<Biome> biome(
      String path,
      String zhCn,
      Consumer<? super SimpleBiomeBuilder> configure
  ) {
    return Zinecraft.WORLDGEN.biomes.register(path, zhCn, configure);
  }

  public static void bootstrap() {
  }
}
