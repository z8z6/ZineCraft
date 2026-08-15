package com.cxxcxx.zinecraft.api.entity;

import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.item.ItemEntry;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class MobEntry<T extends Mob> extends EntityEntry<T> {
  private final MobCategory category;
  private final ItemCatalog items;
  private final Consumer<EntityCatalog.NaturalSpawn> spawnSink;

  MobEntry(String path, Supplier<EntityType<T>> type, MobCategory category, ItemCatalog items,
           Consumer<EntityCatalog.NaturalSpawn> spawnSink) {
    super(path, type);
    this.category = category;
    this.items = items;
    this.spawnSink = spawnSink;
  }

  public static ItemEntry<SpawnEggItem> spawnEgg$default(MobEntry<?> self, int primary, int secondary,
                                                         String zhCn, String enUs, Item.Properties properties, int mask, Object marker) {
    return self.spawnEgg(primary, secondary, zhCn,
        (mask & 8) != 0 ? self.getPath() + " Spawn Egg" : enUs,
        (mask & 16) != 0 ? new Item.Properties() : properties);
  }

  public static MobEntry<?> naturalSpawn$default(MobEntry<?> self, int weight, int min, int max,
                                                 BiomeSelection biomes, int mask, Object marker) {
    return self.naturalSpawn(weight, min, max, (mask & 8) != 0 ? BiomeSelection.overworld() : biomes);
  }

  public ItemEntry<SpawnEggItem> spawnEgg(int primary, int secondary, String zhCn, String enUs, Item.Properties properties) {
    return items.register(getPath() + "_spawn_egg", zhCn, enUs,
        EntityCatalog.Companion.getSPAWN_EGG_MODEL$zinecraft(), true,
        () -> new SpawnEggItem(getType(), primary, secondary, properties));
  }

  public MobEntry<T> naturalSpawn(int weight, int min, int max, BiomeSelection biomes) {
    if (weight <= 0 || min <= 0 || max < min) throw new IllegalArgumentException("自然生成参数无效: " + getPath());
    spawnSink.accept(new EntityCatalog.NaturalSpawn(getPath(), this::getType, weight, min, max, biomes));
    return this;
  }
}
