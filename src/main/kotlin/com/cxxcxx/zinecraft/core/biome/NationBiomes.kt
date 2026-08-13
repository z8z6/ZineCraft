package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.core.Zinecraft
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory

/** 泰拉十九个现存国家的 Minecraft 群系化表达；城市建筑为缩略地标，并非一比一复刻。 */
object NationBiomes {
  /** 阿戈尔深海：暗海晶石海床，海豚巡游，唯一地标为弥利亚留姆火山能源信标。 */
  val AEGIR_ABYSSAL_SEA = Zinecraft.BIOMES.register("aegir_abyssal_sea") {
    precipitation = false
    temperature = 0.4f
    downfall = 1.0f
    waterColor = 0x173F5F
    waterFogColor = 0x071C2C
    fogColor = 0x526D82
    NationBiomePresets.ocean(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.WATER_CREATURE, EntityType.DOLPHIN, 8, 1, 2)
  }

  /** 玻利瓦尔平原：战争侵蚀的砂土，鹦鹉象征多索雷斯热带风情，唯一地标为多索雷斯游艇。 */
  val BOLIVAR_PLAIN = Zinecraft.BIOMES.register("bolivar_plain") {
    temperature = 0.9f
    downfall = 0.35f
    grassColor = 0x9BAA55
    foliageColor = 0x879447
    NationBiomePresets.plains(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.PARROT, 12, 1, 2)
  }

  /** 东国常暗裂谷：灰化土山林与狐群，唯一地标为裂谷神社。 */
  val HIGASHI_SHADOW_RIFT = Zinecraft.BIOMES.register("higashi_shadow_rift") {
    temperature = 0.6f
    downfall = 0.75f
    fogColor = 0x69707A
    grassColor = 0x51745C
    foliageColor = 0x3E644D
    NationBiomePresets.mountain(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.FOX, 10, 1, 2)
  }

  /** 杜林地下花园：苔藓洞穴与发光鱿鱼，唯一地标为际崖城穹顶车站。 */
  val DURIN_UNDERGROUND_GARDEN = Zinecraft.BIOMES.register("durin_underground_garden") {
    precipitation = false
    temperature = 1.0f
    downfall = 0.8f
    waterColor = 0x35A7A0
    fogColor = 0x8A6FA8
    grassColor = 0x55A868
    NationBiomePresets.cavern(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.UNDERGROUND_WATER_CREATURE, EntityType.GLOW_SQUID, 16, 2, 4)
  }

  /** 哥伦比亚砂岩荒野：红砂峡谷与犰狳，唯一地标为拓荒区科研站。 */
  val COLUMBIA_SANDSTONE_WILDS = Zinecraft.BIOMES.register("columbia_sandstone_wilds") {
    precipitation = false
    temperature = 1.4f
    downfall = 0.1f
    grassColor = 0x9B7B49
    foliageColor = 0x856B3E
    NationBiomePresets.badlands(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.ARMADILLO, 14, 1, 3)
  }

  /** 卡西米尔骑士领：草原与马群，唯一地标为大骑士竞技场门楼。 */
  val KAZIMIERZ_KNIGHTLAND = Zinecraft.BIOMES.register("kazimierz_knightland") {
    temperature = 0.75f
    downfall = 0.35f
    grassColor = 0x8FAF52
    foliageColor = 0x6F9345
    NationBiomePresets.plains(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.HORSE, 18, 2, 5)
  }

  /** 卡兹戴尔伤痕荒地：黑石废土与洞穴蜘蛛，唯一地标为巴别塔遗迹。 */
  val KAZDEL_SCARRED_WASTES = Zinecraft.BIOMES.register("kazdel_scarred_wastes") {
    precipitation = false
    temperature = 0.8f
    downfall = 0.05f
    fogColor = 0x625A62
    grassColor = 0x655B50
    foliageColor = 0x554D45
    NationBiomePresets.badlands(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.MONSTER, EntityType.CAVE_SPIDER, 30, 1, 2)
  }

  /** 拉特兰圣田：白色方解石地表与蜜蜂，唯一地标为启示石塔。 */
  val LATERANO_HOLY_FIELDS = Zinecraft.BIOMES.register("laterano_holy_fields") {
    temperature = 0.9f
    downfall = 0.5f
    grassColor = 0xA8BE69
    foliageColor = 0x91AA5C
    fogColor = 0xD9D6C7
    NationBiomePresets.plains(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.BEE, 16, 2, 4)
  }

  /** 莱塔尼亚暮色林：盘根森林与狼群，唯一地标为崔林特尔梅双塔。 */
  val LEITHANIEN_TWILIGHT_FOREST = Zinecraft.BIOMES.register("leithanien_twilight_forest") {
    temperature = 0.55f
    downfall = 0.8f
    fogColor = 0x8A819B
    grassColor = 0x58734C
    foliageColor = 0x405D3F
    NationBiomePresets.forest(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.WOLF, 12, 2, 4)
  }

  /** 雷姆必拓矿业荒地：陶土矿场与尸壳威胁，唯一地标为源石采掘井架。 */
  val RIM_BILLITON_MINING_BADLANDS = Zinecraft.BIOMES.register("rim_billiton_mining_badlands") {
    precipitation = false
    temperature = 1.5f
    downfall = 0.1f
    grassColor = 0x9A7447
    foliageColor = 0x85623E
    NationBiomePresets.badlands(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.MONSTER, EntityType.HUSK, 35, 1, 3)
  }

  /** 米诺斯日照丘陵：夯土山坡与山羊，唯一地标为十二英雄神殿。 */
  val MINOS_SUNLIT_HILLS = Zinecraft.BIOMES.register("minos_sunlit_hills") {
    temperature = 1.1f
    downfall = 0.35f
    grassColor = 0xA6B85A
    foliageColor = 0x829B4D
    NationBiomePresets.mountain(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.GOAT, 14, 2, 4)
  }

  /** 萨尔贡岩漠：沙海与骆驼，唯一地标为黄金之城宝石集市。 */
  val SARGON_ROCKY_DESERT = Zinecraft.BIOMES.register("sargon_rocky_desert") {
    precipitation = false
    temperature = 2.0f
    downfall = 0.0f
    fogColor = 0xD6B47D
    NationBiomePresets.desert(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.CAMEL, 12, 1, 2)
  }

  /** 萨米冻林：积雪针叶林与北极熊，唯一地标为独眼巨人祭坛。 */
  val SAMI_FROZEN_FOREST = Zinecraft.BIOMES.register("sami_frozen_forest") {
    temperature = -0.5f
    downfall = 0.8f
    fogColor = 0xB9CBD3
    grassColor = 0x6D8278
    foliageColor = 0x587166
    NationBiomePresets.snowyForest(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.POLAR_BEAR, 10, 1, 2)
  }

  /** 维多利亚雾岭：泥泞高地与羊群，唯一地标为伦蒂尼姆城防炮。 */
  val VICTORIA_MISTY_HIGHLANDS = Zinecraft.BIOMES.register("victoria_misty_highlands") {
    temperature = 0.65f
    downfall = 0.9f
    fogColor = 0xA8ADB0
    grassColor = 0x66845A
    foliageColor = 0x55734C
    NationBiomePresets.mountain(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.SHEEP, 18, 2, 4)
  }

  /** 乌萨斯冻原：坚冰台地与流髑，唯一地标为切尔诺伯格石棺站。 */
  val URSUS_FROZEN_STEPPE = Zinecraft.BIOMES.register("ursus_frozen_steppe") {
    temperature = -0.2f
    downfall = 0.3f
    fogColor = 0xC1CBD0
    grassColor = 0x84908A
    foliageColor = 0x718078
    NationBiomePresets.snowyForest(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.MONSTER, EntityType.STRAY, 40, 1, 3)
  }

  /** 谢拉格雪峰：蓝冰山脊与雪兔，唯一地标为喀兰峰蔓珠院。 */
  val KJERAG_SNOWY_PEAKS = Zinecraft.BIOMES.register("kjerag_snowy_peaks") {
    temperature = -0.7f
    downfall = 0.7f
    fogColor = 0xD5E1E7
    grassColor = 0x78908A
    foliageColor = 0x627D75
    NationBiomePresets.snowyForest(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.RABBIT, 14, 2, 3)
  }

  /** 叙拉古雨林：苔石湿林与蜘蛛，唯一地标为家族联合法院。 */
  val SIRACUSA_RAINY_WOODLAND = Zinecraft.BIOMES.register("siracusa_rainy_woodland") {
    temperature = 0.8f
    downfall = 0.95f
    fogColor = 0x8C9690
    grassColor = 0x47754E
    foliageColor = 0x39643F
    NationBiomePresets.rainyForest(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.MONSTER, EntityType.SPIDER, 45, 1, 3)
  }

  /** 炎国山林：凝灰岩峰林与熊猫，唯一地标为玉门烽火台。 */
  val YAN_MOUNTAIN_GROVE = Zinecraft.BIOMES.register("yan_mountain_grove") {
    temperature = 0.7f
    downfall = 0.8f
    waterColor = 0x3F76A5
    grassColor = 0x60965B
    foliageColor = 0x4A814C
    NationBiomePresets.mountain(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.CREATURE, EntityType.PANDA, 12, 1, 2)
  }

  /** 伊比利亚盐风三角洲：砾石海岸与溺尸，唯一地标为伊比利亚之眼灯塔。 */
  val IBERIA_SALT_DELTA = Zinecraft.BIOMES.register("iberia_salt_delta") {
    temperature = 1.0f
    downfall = 0.8f
    waterColor = 0x2E7187
    waterFogColor = 0x163D4A
    fogColor = 0xAEB8B4
    grassColor = 0x748C72
    foliageColor = 0x637B65
    NationBiomePresets.wetland(this)
    NationBiomePresets.featuredSpawn(this, MobCategory.MONSTER, EntityType.DROWNED, 35, 1, 2)
  }
}
