package com.cxxcxx.zinecraft.api.registry;

import com.mojang.serialization.MapCodec;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
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
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 绑定模组命名空间并通过 NeoForge DeferredRegister 提交静态注册内容。
 */
public final class ModRegistrar {
  private final String namespace;
  private final Map<ResourceKey<?>, DeferredRegister<?>> deferredRegisters = new LinkedHashMap<>();
  private final List<MobRegistration<?>> mobs = new ArrayList<>();

  public ModRegistrar(String namespace) {
    this.namespace = namespace;
  }

  public static Block block$default(ModRegistrar self, String path, Block block, boolean registerItem, Item.Properties properties, int mask, Object marker) {
    return self.block(path, block, (mask & 4) != 0 || registerItem, (mask & 8) != 0 ? new Item.Properties() : properties);
  }

  public static EntityType<?> entity$default(
      ModRegistrar self,
      String path,
      EntityType.EntityFactory<?> factory,
      MobCategory category,
      Function1<?, Unit> configure,
      int mask,
      Object marker
  ) {
    @SuppressWarnings("unchecked")
    var typedFactory = (EntityType.EntityFactory<Entity>) factory;
    @SuppressWarnings("unchecked")
    Function1<EntityType.Builder<Entity>, Unit> typedConfigure;
    if ((mask & 8) != 0) {
      typedConfigure = builder -> Unit.INSTANCE;
    } else {
      typedConfigure = (Function1<EntityType.Builder<Entity>, Unit>) configure;
    }
    return self.entity(path, typedFactory, category, typedConfigure);
  }

  public static EntityType<?> mob$default(
      ModRegistrar self,
      String path,
      EntityType.EntityFactory<?> factory,
      MobCategory category,
      Function0<? extends AttributeSupplier.Builder> attributes,
      SpawnPlacementType placement,
      Heightmap.Types heightmap,
      SpawnPlacements.SpawnPredicate<?> predicate,
      Function1<?, Unit> configure,
      int mask,
      Object marker
  ) {
    @SuppressWarnings("unchecked") var typedFactory = (EntityType.EntityFactory<Mob>) factory;
    @SuppressWarnings("unchecked") var typedPredicate = (SpawnPlacements.SpawnPredicate<Mob>) predicate;
    Function1<EntityType.Builder<Mob>, Unit> typedConfigure;
    if ((mask & 128) != 0) {
      typedConfigure = builder -> Unit.INSTANCE;
    } else {
      typedConfigure = (Function1<EntityType.Builder<Mob>, Unit>) configure;
    }
    return self.mob(path, typedFactory, category, attributes,
        (mask & 16) != 0 ? null : placement,
        (mask & 32) != 0 ? null : heightmap,
        (mask & 64) != 0 ? null : typedPredicate,
        typedConfigure);
  }

  public String getNamespace() {
    return namespace;
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
  public <T extends Item> Supplier<T> item(String path, Supplier<? extends T> factory) {
    var items = (DeferredRegister.Items) deferredRegisters.computeIfAbsent(
        BuiltInRegistries.ITEM.key(), ignored -> DeferredRegister.createItems(namespace)
    );
    return items.register(path, factory);
  }

  public <T extends Block> T block(String path, T block, boolean registerItem, Item.Properties itemProperties) {
    register(BuiltInRegistries.BLOCK, path, block);
    if (registerItem) {
      item(path, () -> new BlockItem(block, itemProperties));
    }
    return block;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public <T extends BlockEntity> BlockEntityType<T> blockEntity(
      String path,
      BlockEntityType.BlockEntitySupplier<? extends T> factory,
      Block... blocks
  ) {
    var type = (BlockEntityType<T>) BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier) factory, blocks).build(null);
    return register(BuiltInRegistries.BLOCK_ENTITY_TYPE, path, type);
  }

  public <T extends Entity> EntityType<T> entity(
      String path,
      EntityType.EntityFactory<T> factory,
      MobCategory category,
      Function1<? super EntityType.Builder<T>, Unit> configure
  ) {
    var builder = EntityType.Builder.of(factory, category);
    configure.invoke(builder);
    return register(BuiltInRegistries.ENTITY_TYPE, path, builder.build(path));
  }

  public <T extends Mob> EntityType<T> mob(
      String path,
      EntityType.EntityFactory<T> factory,
      MobCategory category,
      Function0<? extends AttributeSupplier.Builder> attributes,
      SpawnPlacementType placement,
      Heightmap.Types heightmap,
      SpawnPlacements.SpawnPredicate<T> predicate,
      Function1<? super EntityType.Builder<T>, Unit> configure
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
    mobs.forEach(mob -> event.put(mob.type(), mob.attributes().invoke().build()));
  }

  private void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
    mobs.forEach(mob -> mob.registerPlacement(event));
  }

  private record MobRegistration<T extends Mob>(
      EntityType<T> type,
      Function0<? extends AttributeSupplier.Builder> attributes,
      SpawnPlacementType placement,
      Heightmap.Types heightmap,
      SpawnPlacements.SpawnPredicate<T> predicate
  ) {
    void registerPlacement(RegisterSpawnPlacementsEvent event) {
      if (predicate != null) {
        event.register(type, placement, heightmap, predicate, RegisterSpawnPlacementsEvent.Operation.REPLACE);
      }
    }
  }
}
