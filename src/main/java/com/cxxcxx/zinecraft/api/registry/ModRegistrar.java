package com.cxxcxx.zinecraft.api.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType.StructureTemplateType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;


public final class ModRegistrar {
  // 模组名称
  public final String namespace;
  private final Map<ResourceKey<?>, DeferredRegister<?>> deferredRegisters = new LinkedHashMap<>();
  private final List<MobRegistration<?>> mobs = new ArrayList<>();

  public ModRegistrar(String namespace) {
    this.namespace = namespace;
  }

  public static Supplier<? extends EntityType<?>> entityWithDefaults(
      ModRegistrar self,
      String path,
      EntityType.EntityFactory<?> factory,
      MobCategory category,
      Consumer<?> configure,
      int mask,
      Object marker
  ) {

    @SuppressWarnings("unchecked") var typedFactory = (EntityType.EntityFactory<Entity>) factory;

    @SuppressWarnings("unchecked") Consumer<EntityType.Builder<Entity>> typedConfigure;
    if ((mask & 8) != 0) {
      typedConfigure = builder -> {
      };
    } else {
      typedConfigure = (Consumer<EntityType.Builder<Entity>>) configure;
    }
    return self.entity(path, typedFactory, category, typedConfigure);
  }

  public static Supplier<? extends EntityType<?>> mobWithDefaults(
      ModRegistrar self,
      String path,
      EntityType.EntityFactory<?> factory,
      MobCategory category,
      Supplier<? extends AttributeSupplier.Builder> attributes,
      SpawnPlacementType placement,
      Heightmap.Types heightmap,
      SpawnPlacements.SpawnPredicate<?> predicate,
      Consumer<?> configure,
      int mask,
      Object marker
  ) {
    @SuppressWarnings("unchecked") var typedFactory = (EntityType.EntityFactory<Mob>) factory;
    @SuppressWarnings("unchecked") var typedPredicate = (SpawnPlacements.SpawnPredicate<Mob>) predicate;
    Consumer<EntityType.Builder<Mob>> typedConfigure;
    if ((mask & 128) != 0) {
      typedConfigure = builder -> {
      };
    } else {
      typedConfigure = (Consumer<EntityType.Builder<Mob>>) configure;
    }
    return self.mob(path, typedFactory, category, attributes,
        (mask & 16) != 0 ? null : placement,
        (mask & 32) != 0 ? null : heightmap,
        (mask & 64) != 0 ? null : typedPredicate,
        typedConfigure);
  }

  public ResourceLocation id(String path) {
    return ResourceLocation.fromNamespaceAndPath(namespace, path);
  }

  public <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registryKey, String path) {
    return ResourceKey.create(registryKey, id(path));
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public <V, T extends V> T register(Registry<V> registry, String path, T value) {
    var deferred = (DeferredRegister<V>) deferredRegisters.computeIfAbsent(
        registry.key(), ignored -> DeferredRegister.create(registry.key(), namespace)
    );
    deferred.register(path, () -> value);
    return value;
  }

  public void register(IEventBus modBus) {
    deferredRegisters.values().forEach(register -> register.register(modBus));
    modBus.addListener(this::createAttributes);
    modBus.addListener(this::registerSpawnPlacements);
  }

  public <T> ResourceKey<T> dynamic(
      BootstrapContext<T> context,
      ResourceKey<? extends Registry<T>> registryKey,
      String path,
      T value
  ) {
    var key = key(registryKey, path);
    context.register(key, value);
    return key;
  }

  public <T> T dynamic(BootstrapContext<T> context, ResourceKey<T> key, T value) {
    context.register(key, value);
    return value;
  }


  @SuppressWarnings("unchecked")
  public <T extends Item> DeferredItem<T> item(String path, Supplier<? extends T> factory) {
    var items = (DeferredRegister.Items) deferredRegisters.computeIfAbsent(
        BuiltInRegistries.ITEM.key(), ignored -> DeferredRegister.createItems(namespace)
    );
    return items.register(path, factory);
  }


  @SuppressWarnings("unchecked")
  public <T extends Block> BlockRegistration<T> block(
      String path, Supplier<? extends T> factory, boolean registerItem, Item.Properties itemProperties
  ) {
    var blocks = (DeferredRegister.Blocks) deferredRegisters.computeIfAbsent(
        BuiltInRegistries.BLOCK.key(), ignored -> DeferredRegister.createBlocks(namespace)
    );
    DeferredBlock<T> block = blocks.register(path, factory);
    DeferredItem<BlockItem> blockItem = registerItem
        ? item(path, () -> new BlockItem(block.get(), itemProperties))
        : null;
    return new BlockRegistration<>(block, Optional.ofNullable(blockItem));
  }

  public record BlockRegistration<T extends Block>(
      DeferredBlock<T> block,
      Optional<DeferredItem<BlockItem>> blockItem
  ) {
  }


  @SuppressWarnings({"unchecked", "rawtypes"})
  public <T extends BlockEntity> Supplier<BlockEntityType<T>> blockEntity(
      String path,
      BlockEntityType.BlockEntitySupplier<? extends T> factory,
      Supplier<? extends Block>... blocks
  ) {
    var deferred = (DeferredRegister<BlockEntityType<?>>) deferredRegisters.computeIfAbsent(
        BuiltInRegistries.BLOCK_ENTITY_TYPE.key(),
        ignored -> DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE.key(), namespace)
    );
    return (Supplier) deferred.register(path, () -> {
      Block[] boundBlocks = java.util.Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new);
      return BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier) factory, boundBlocks).build(null);
    });
  }


  @SuppressWarnings("unchecked")
  public <T extends Entity> Supplier<EntityType<T>> entity(
      String path,
      EntityType.EntityFactory<T> factory,
      MobCategory category,
      Consumer<? super EntityType.Builder<T>> configure
  ) {
    var deferred = (DeferredRegister<EntityType<?>>) deferredRegisters.computeIfAbsent(
        BuiltInRegistries.ENTITY_TYPE.key(),
        ignored -> DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE.key(), namespace)
    );
    return (Supplier) deferred.register(path, () -> {
      var builder = EntityType.Builder.of(factory, category);
      configure.accept(builder);
      return builder.build(path);
    });
  }

  public <T extends Mob> Supplier<EntityType<T>> mob(
      String path,
      EntityType.EntityFactory<T> factory,
      MobCategory category,
      Supplier<? extends AttributeSupplier.Builder> attributes,
      SpawnPlacementType placement,
      Heightmap.Types heightmap,
      SpawnPlacements.SpawnPredicate<T> predicate,
      Consumer<? super EntityType.Builder<T>> configure
  ) {
    if (!((placement == null) == (heightmap == null) && (heightmap == null) == (predicate == null))) {
      throw new IllegalArgumentException("生成限制的 placement、heightmap 和 predicate 必须同时提供");
    }
    var type = entity(path, factory, category, configure);
    mobs.add(new MobRegistration<>(type, attributes, placement, heightmap, predicate));
    return type;
  }

  public Pair<ResourceKey<CreativeModeTab>, CreativeModeTab> creativeTab(String path, CreativeModeTab tab) {
    var key = key(BuiltInRegistries.CREATIVE_MODE_TAB.key(), path);
    register(BuiltInRegistries.CREATIVE_MODE_TAB, path, tab);
    return new Pair<>(key, tab);
  }


  @SuppressWarnings("unchecked")
  public Holder<SoundEvent> sound(String path) {
    var deferred = (DeferredRegister<SoundEvent>) deferredRegisters.computeIfAbsent(
        BuiltInRegistries.SOUND_EVENT.key(),
        ignored -> DeferredRegister.create(BuiltInRegistries.SOUND_EVENT.key(), namespace)
    );
    return deferred.register(path, () -> SoundEvent.createVariableRangeEvent(id(path)));
  }

  public <S extends Structure> StructureType<S> structureType(String path, MapCodec<S> codec) {
    return register(BuiltInRegistries.STRUCTURE_TYPE, path, () -> codec);
  }

  public <S extends BiomeSource> MapCodec<S> biomeSource(String path, MapCodec<S> codec) {
    return register(BuiltInRegistries.BIOME_SOURCE, path, codec);
  }

  public <S extends StructurePlacement> StructurePlacementType<S> structurePlacement(String path, MapCodec<S> codec) {
    return register(BuiltInRegistries.STRUCTURE_PLACEMENT, path, () -> codec);
  }

  public StructurePieceType structurePiece(String path, StructureTemplateType type) {
    return register(BuiltInRegistries.STRUCTURE_PIECE, path, type);
  }

  public <S extends StructurePoolElement> StructurePoolElementType<S> structurePoolElement(String path, MapCodec<S> codec) {
    return register(BuiltInRegistries.STRUCTURE_POOL_ELEMENT, path, () -> codec);
  }

  private void createAttributes(EntityAttributeCreationEvent event) {
    mobs.forEach(mob -> event.put(mob.type().get(), mob.attributes().get().build()));
  }

  private void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
    mobs.forEach(mob -> mob.registerPlacement(event));
  }

  private record MobRegistration<T extends Mob>(
      Supplier<EntityType<T>> type,
      Supplier<? extends AttributeSupplier.Builder> attributes,
      SpawnPlacementType placement,
      Heightmap.Types heightmap,
      SpawnPlacements.SpawnPredicate<T> predicate
  ) {
    void registerPlacement(RegisterSpawnPlacementsEvent event) {
      if (predicate != null) {
        event.register(type.get(), placement, heightmap, predicate, RegisterSpawnPlacementsEvent.Operation.REPLACE);
      }
    }
  }
}
