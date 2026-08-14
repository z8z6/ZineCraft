package com.cxxcxx.zinecraft.core.entity

import com.cxxcxx.zinecraft.api.entity.MobSpawnRestriction
import com.cxxcxx.zinecraft.api.entity.MobEntry
import com.cxxcxx.zinecraft.api.nation.TerraNation
import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.biome.NationBiomes
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.entity.SpawnPlacementTypes
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.Heightmap

object ModEntities {
  /** 拉特兰圣田的人形公民；枪械是默认装备，不作为击杀掉落来源。 */
  val LATERANO_CITIZEN = Zinecraft.ENTITIES.mob(
    path = "laterano_citizen",
    zhCn = "拉特兰公民",
    enUs = "Laterano Citizen",
    factory = ::LateranoCitizen,
    category = MobCategory.CREATURE,
    attributes = LateranoCitizen::attributes,
    spawnRestriction = MobSpawnRestriction(
      SpawnPlacementTypes.ON_GROUND,
      Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
      LateranoCitizen::canSpawn
    )
  ) {
    sized(0.6f, 1.8f)
    clientTrackingRange(8)
  }.naturalSpawn(
    weight = 10,
    minGroupSize = 1,
    maxGroupSize = 2,
    biomes = BiomeSelectors.includeByKey(NationBiomes.LATERANO_HOLY_FIELDS)
  )

  val LATERANO_CITIZEN_SPAWN_EGG = LATERANO_CITIZEN.spawnEgg(
    primaryColor = 0xF1E8D0,
    secondaryColor = 0xD9B64C,
    zhCn = "拉特兰公民生成蛋",
    enUs = "Laterano Citizen Spawn Egg"
  )

  val AEGIR_RESIDENT = resident(
    TerraNation.AEGIR,
    NationBiomes.AEGIR_ABYSSAL_SEA,
    Items.NAUTILUS_SHELL,
    placement = SpawnPlacementTypes.IN_WATER,
    aquatic = true
  )
  val BOLIVAR_RESIDENT = resident(TerraNation.BOLIVAR, NationBiomes.BOLIVAR_PLAIN, Items.COOKIE)
  val HIGASHI_RESIDENT = resident(TerraNation.HIGASHI, NationBiomes.HIGASHI_SHADOW_RIFT, Items.IRON_SWORD)
  val DURIN_RESIDENT = resident(TerraNation.DURIN, NationBiomes.DURIN_UNDERGROUND_GARDEN, Items.REDSTONE)
  val COLUMBIA_RESIDENT = resident(TerraNation.COLUMBIA, NationBiomes.COLUMBIA_SANDSTONE_WILDS, Items.COMPASS)
  val KAZIMIERZ_RESIDENT = resident(TerraNation.KAZIMIERZ, NationBiomes.KAZIMIERZ_KNIGHTLAND, Items.IRON_SWORD)
  val KAZDEL_RESIDENT = resident(TerraNation.KAZDEL, NationBiomes.KAZDEL_SCARRED_WASTES, Items.IRON_AXE)
  val LEITHANIEN_RESIDENT = resident(TerraNation.LEITHANIEN, NationBiomes.LEITHANIEN_TWILIGHT_FOREST, Items.NOTE_BLOCK)
  val RIM_BILLITON_RESIDENT = resident(
    TerraNation.RIM_BILLITON,
    NationBiomes.RIM_BILLITON_MINING_BADLANDS,
    Items.IRON_PICKAXE
  )
  val MINOS_RESIDENT = resident(TerraNation.MINOS, NationBiomes.MINOS_SUNLIT_HILLS, Items.SHIELD)
  val SARGON_RESIDENT = resident(TerraNation.SARGON, NationBiomes.SARGON_ROCKY_DESERT, Items.EMERALD)
  val SAMI_RESIDENT = resident(TerraNation.SAMI, NationBiomes.SAMI_FROZEN_FOREST, Items.BONE)
  val VICTORIA_RESIDENT = resident(TerraNation.VICTORIA, NationBiomes.VICTORIA_MISTY_HIGHLANDS, Items.IRON_INGOT)
  val URSUS_RESIDENT = resident(TerraNation.URSUS, NationBiomes.URSUS_FROZEN_STEPPE, Items.IRON_AXE)
  val KJERAG_RESIDENT = resident(TerraNation.KJERAG, NationBiomes.KJERAG_SNOWY_PEAKS, Items.EMERALD)
  val SIRACUSA_RESIDENT = resident(TerraNation.SIRACUSA, NationBiomes.SIRACUSA_RAINY_WOODLAND, Items.SHEARS)
  val YAN_RESIDENT = resident(TerraNation.YAN, NationBiomes.YAN_MOUNTAIN_GROVE, Items.PAPER)
  val IBERIA_RESIDENT = resident(TerraNation.IBERIA, NationBiomes.IBERIA_SALT_DELTA, Items.COD)

  private val GENERIC_RESIDENTS_BY_NATION = linkedMapOf(
    TerraNation.AEGIR to AEGIR_RESIDENT,
    TerraNation.BOLIVAR to BOLIVAR_RESIDENT,
    TerraNation.HIGASHI to HIGASHI_RESIDENT,
    TerraNation.DURIN to DURIN_RESIDENT,
    TerraNation.COLUMBIA to COLUMBIA_RESIDENT,
    TerraNation.KAZIMIERZ to KAZIMIERZ_RESIDENT,
    TerraNation.KAZDEL to KAZDEL_RESIDENT,
    TerraNation.LEITHANIEN to LEITHANIEN_RESIDENT,
    TerraNation.RIM_BILLITON to RIM_BILLITON_RESIDENT,
    TerraNation.MINOS to MINOS_RESIDENT,
    TerraNation.SARGON to SARGON_RESIDENT,
    TerraNation.SAMI to SAMI_RESIDENT,
    TerraNation.VICTORIA to VICTORIA_RESIDENT,
    TerraNation.URSUS to URSUS_RESIDENT,
    TerraNation.KJERAG to KJERAG_RESIDENT,
    TerraNation.SIRACUSA to SIRACUSA_RESIDENT,
    TerraNation.YAN to YAN_RESIDENT,
    TerraNation.IBERIA to IBERIA_RESIDENT
  )

  val GENERIC_RESIDENT_TYPES: List<EntityType<NationResident>> =
    GENERIC_RESIDENTS_BY_NATION.values.map(MobEntry<NationResident>::type)

  val RESIDENT_TYPES_BY_NATION: Map<TerraNation, EntityType<out net.minecraft.world.entity.Mob>> = buildMap {
    GENERIC_RESIDENTS_BY_NATION.forEach { (nation, resident) ->
      put(nation, resident.type)
    }
    put(TerraNation.LATERANO, LATERANO_CITIZEN.type)
  }.also { require(it.keys == TerraNation.entries.toSet()) { "必须为全部十九国注册居民" } }

  private fun resident(
    nation: TerraNation,
    biome: ResourceKey<Biome>,
    heldItem: Item,
    placement: net.minecraft.world.entity.SpawnPlacementType = SpawnPlacementTypes.ON_GROUND,
    aquatic: Boolean = false
  ): MobEntry<NationResident> {
    val profile = NationResidentProfile(nation, heldItem, aquatic)
    return Zinecraft.ENTITIES.mob(
      path = "${nation.id}_resident",
      zhCn = "${nation.zhCn}居民",
      enUs = "${nation.enUs} Resident",
      factory = { type, level -> NationResident(type, level, profile) },
      category = MobCategory.CREATURE,
      attributes = NationResident::attributes,
      spawnRestriction = MobSpawnRestriction(
        placement,
        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
        NationResident::canSpawn
      )
    ) {
      sized(0.6f, 1.8f)
      clientTrackingRange(8)
    }.naturalSpawn(
      weight = 8,
      minGroupSize = 1,
      maxGroupSize = 3,
      biomes = BiomeSelectors.includeByKey(biome)
    )
  }
}
