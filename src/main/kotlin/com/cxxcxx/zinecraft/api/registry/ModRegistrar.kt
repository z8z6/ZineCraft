package com.cxxcxx.zinecraft.api.registry

import com.mojang.serialization.MapCodec
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityType
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.biome.BiomeSource
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.StructureType
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType.StructureTemplateType
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType

/**
 * 绑定模组命名空间的注册器。
 *
 * 静态注册表内容通过 [register] 及各类型快捷方法注册；动态注册表内容
 * （群系、配置地物、结构、结构集等）使用 [key] 创建资源键，再在数据生成的
 * BootstrapContext 中完成注册。
 */
class ModRegistrar(val namespace: String) {

  fun id(path: String): ResourceLocation =
    ResourceLocation.fromNamespaceAndPath(namespace, path)

  fun <T : Any> key(registryKey: ResourceKey<out Registry<T>>, path: String): ResourceKey<T> =
    ResourceKey.create(registryKey, id(path))

  fun <V : Any, T : V> register(registry: Registry<V>, path: String, value: T): T =
    Registry.register(registry, id(path), value)

  fun <T : Any> dynamic(
    context: BootstrapContext<T>,
    registryKey: ResourceKey<out Registry<T>>,
    path: String,
    value: T
  ): ResourceKey<T> {
    val resourceKey = key(registryKey, path)
    context.register(resourceKey, value)
    return resourceKey
  }

  fun <T : Any> dynamic(context: BootstrapContext<T>, key: ResourceKey<T>, value: T): T {
    context.register(key, value)
    return value
  }

  fun <T : Item> item(path: String, item: T): T =
    register(BuiltInRegistries.ITEM, path, item)

  fun <T : Block> block(
    path: String,
    block: T,
    registerItem: Boolean = true,
    itemProperties: Item.Properties = Item.Properties()
  ): T {
    if (registerItem) {
      item(path, BlockItem(block, itemProperties))
    }
    return register(BuiltInRegistries.BLOCK, path, block)
  }

  fun <T : BlockEntity> blockEntity(
    path: String,
    factory: BlockEntityType.BlockEntitySupplier<out T>,
    vararg blocks: Block
  ): BlockEntityType<T> = register(
    BuiltInRegistries.BLOCK_ENTITY_TYPE,
    path,
    BlockEntityType.Builder.of(factory, *blocks).build()
  )

  fun <T : Entity> entity(
    path: String,
    factory: EntityType.EntityFactory<T>,
    category: MobCategory,
    configure: EntityType.Builder<T>.() -> Unit = {}
  ): EntityType<T> {
    val builder = EntityType.Builder.of(factory, category).apply(configure)
    return register(BuiltInRegistries.ENTITY_TYPE, path, builder.build(path))
  }

  fun <T : Mob> mob(
    path: String,
    factory: EntityType.EntityFactory<T>,
    category: MobCategory,
    attributes: () -> AttributeSupplier.Builder,
    placement: SpawnPlacementType? = null,
    heightmap: Heightmap.Types? = null,
    predicate: SpawnPlacements.SpawnPredicate<T>? = null,
    configure: EntityType.Builder<T>.() -> Unit = {}
  ): EntityType<T> {
    require((placement == null) == (heightmap == null) && (heightmap == null) == (predicate == null)) {
      "生成限制的 placement、heightmap 和 predicate 必须同时提供"
    }
    val builder = FabricEntityType.Builder.createMob(factory, category) { mobBuilder ->
      mobBuilder.defaultAttributes(attributes).also { configured ->
        if (predicate != null) {
          configured.spawnRestriction(placement!!, heightmap!!, predicate)
        }
      }
    }.apply(configure)
    return register(BuiltInRegistries.ENTITY_TYPE, path, builder.build(path))
  }

  fun creativeTab(
    path: String,
    tab: CreativeModeTab
  ): Pair<ResourceKey<CreativeModeTab?>, CreativeModeTab> {
    val resourceKey: ResourceKey<CreativeModeTab?> = key(BuiltInRegistries.CREATIVE_MODE_TAB.key(), path)
    register(BuiltInRegistries.CREATIVE_MODE_TAB, path, tab)
    return resourceKey to tab
  }

  fun sound(path: String): Holder.Reference<SoundEvent> =
    Registry.registerForHolder(
      BuiltInRegistries.SOUND_EVENT,
      id(path),
      SoundEvent.createVariableRangeEvent(id(path))
    )

  fun <S : Structure> structureType(path: String, codec: MapCodec<S>): StructureType<S> =
    register(BuiltInRegistries.STRUCTURE_TYPE, path, StructureType { codec })

  fun <S : BiomeSource> biomeSource(path: String, codec: MapCodec<S>): MapCodec<S> =
    register(BuiltInRegistries.BIOME_SOURCE, path, codec)

  fun <S : StructurePlacement> structurePlacement(
    path: String,
    codec: MapCodec<S>
  ): StructurePlacementType<S> = register(
    BuiltInRegistries.STRUCTURE_PLACEMENT,
    path,
    StructurePlacementType { codec }
  )

  fun structurePiece(path: String, type: StructureTemplateType): StructurePieceType =
    register(BuiltInRegistries.STRUCTURE_PIECE, path, type)

  fun <S : StructurePoolElement> structurePoolElement(
    path: String,
    codec: MapCodec<S>
  ): StructurePoolElementType<S> = register(
    BuiltInRegistries.STRUCTURE_POOL_ELEMENT,
    path,
    StructurePoolElementType { codec }
  )
}
