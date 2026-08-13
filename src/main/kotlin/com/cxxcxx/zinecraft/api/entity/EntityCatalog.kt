package com.cxxcxx.zinecraft.api.entity

import com.cxxcxx.zinecraft.api.item.ItemCatalog
import com.cxxcxx.zinecraft.api.item.ItemEntry
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import com.cxxcxx.zinecraft.api.localization.toDisplayName
import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.item.Item
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.level.levelgen.Heightmap
import java.util.*
import java.util.function.Predicate

class EntityCatalog(
  private val registrar: ModRegistrar,
  private val items: ItemCatalog,
  private val translations: TranslationCatalog
) {
  fun <T : Entity> register(
    path: String,
    zhCn: String,
    enUs: String = path.toDisplayName(),
    factory: EntityType.EntityFactory<T>,
    category: MobCategory,
    configure: EntityType.Builder<T>.() -> Unit = {}
  ): EntityEntry<T> {
    val type = registrar.entity(path, factory, category, configure)
    translations.add(type.descriptionId, zhCn, enUs)
    return EntityEntry(path, type)
  }

  fun <T : Mob> mob(
    path: String,
    zhCn: String,
    enUs: String = path.toDisplayName(),
    factory: EntityType.EntityFactory<T>,
    category: MobCategory,
    attributes: () -> AttributeSupplier.Builder,
    spawnRestriction: MobSpawnRestriction<T>? = null,
    configure: EntityType.Builder<T>.() -> Unit = {}
  ): MobEntry<T> {
    val type = registrar.mob(
      path,
      factory,
      category,
      attributes,
      spawnRestriction?.placement,
      spawnRestriction?.heightmap,
      spawnRestriction?.predicate,
      configure
    )
    translations.add(type.descriptionId, zhCn, enUs)
    return MobEntry(path, type, category, items)
  }

  companion object {
    internal val SPAWN_EGG_MODEL = ModelTemplate(
      Optional.of(ResourceLocation.withDefaultNamespace("item/template_spawn_egg")),
      Optional.empty()
    )
  }
}

open class EntityEntry<T : Entity> internal constructor(
  val path: String,
  val type: EntityType<T>
)

class MobEntry<T : Mob> internal constructor(
  path: String,
  type: EntityType<T>,
  private val category: MobCategory,
  private val items: ItemCatalog
) : EntityEntry<T>(path, type) {
  fun spawnEgg(
    primaryColor: Int,
    secondaryColor: Int,
    zhCn: String,
    enUs: String = "$path Spawn Egg",
    properties: Item.Properties = Item.Properties()
  ): ItemEntry<SpawnEggItem> = items.register(
    "${path}_spawn_egg",
    zhCn,
    enUs,
    EntityCatalog.SPAWN_EGG_MODEL
  ) {
    SpawnEggItem(type, primaryColor, secondaryColor, properties)
  }

  fun naturalSpawn(
    weight: Int,
    minGroupSize: Int,
    maxGroupSize: Int,
    biomes: Predicate<BiomeSelectionContext> = BiomeSelectors.foundInOverworld()
  ): MobEntry<T> = apply {
    require(weight > 0) { "生物生成权重必须大于 0" }
    require(minGroupSize > 0) { "生物最小群体数量必须大于 0" }
    require(maxGroupSize >= minGroupSize) { "生物最大群体数量不能小于最小群体数量" }
    BiomeModifications.addSpawn(biomes, category, type, weight, minGroupSize, maxGroupSize)
  }
}

data class MobSpawnRestriction<T : Mob>(
  val placement: SpawnPlacementType,
  val heightmap: Heightmap.Types,
  val predicate: SpawnPlacements.SpawnPredicate<T>
)
