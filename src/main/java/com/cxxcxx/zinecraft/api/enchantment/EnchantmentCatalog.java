package com.cxxcxx.zinecraft.api.enchantment;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Builder;
import net.minecraft.world.item.enchantment.Enchantment.Cost;
import net.minecraft.world.item.enchantment.Enchantment.EnchantmentDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public final class EnchantmentCatalog {
  @NotNull
  private final ModRegistrar registrar;
  @NotNull
  private final TranslationCatalog translations;
  @NotNull
  private final List<EnchantmentEntry> entries;

  public EnchantmentCatalog(@NotNull ModRegistrar registrar, @NotNull TranslationCatalog translations) {
    super();
    this.registrar = registrar;
    this.translations = translations;
    this.entries = new ArrayList<>();
  }

  public static EnchantmentEntry registerWithDefaults(
      EnchantmentCatalog var0,
      String var1,
      String var2,
      String var3,
      TagKey var4,
      TagKey var5,
      TagKey var6,
      int var7,
      int var8,
      Cost var9,
      Cost var10,
      int var11,
      EquipmentSlotGroup[] var12,
      Consumer var13,
      int var14,
      Object var15
  ) {
    if ((var14 & 4) != 0) {
      var3 = TranslationNames.toDisplayName(var1);
    }

    if ((var14 & 16) != 0) {
      var5 = null;
    }

    if ((var14 & 32) != 0) {
      var6 = null;
    }

    if ((var14 & 64) != 0) {
      var7 = 10;
    }

    if ((var14 & 128) != 0) {
      var8 = 1;
    }

    if ((var14 & 256) != 0) {
      Cost cost1 = Enchantment.constantCost(1);
      var9 = cost1;
    }

    if ((var14 & 512) != 0) {
      Cost cost = Enchantment.constantCost(1);
      var10 = cost;
    }

    if ((var14 & 1024) != 0) {
      var11 = 1;
    }

    if ((var14 & 2048) != 0) {
      EquipmentSlotGroup[] equipmentSlotGroups = new EquipmentSlotGroup[]{EquipmentSlotGroup.ANY};
      var12 = equipmentSlotGroups;
    }

    if ((var14 & 4096) != 0) {
      var13 = (Consumer<Builder>) (builder -> {
      });
    }

    return var0.register(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
  }

  private static void registerHelper0(Builder var0) {
    return;
  }

  @NotNull
  public final EnchantmentEntry register(
      @NotNull String path,
      @NotNull String zhCn,
      @NotNull String enUs,
      @NotNull TagKey<Item> supportedItems,
      @Nullable TagKey<Item> primaryItems,
      @Nullable TagKey<Enchantment> exclusiveWith,
      int weight,
      int maxLevel,
      @NotNull Cost minCost,
      @NotNull Cost maxCost,
      int anvilCost,
      @NotNull EquipmentSlotGroup[] slots,
      @NotNull Consumer<? super Builder> configure
  ) {
    if (path.isBlank()) {
      int m = 0;
      String string4 = "附魔 ID 不能为空";
      throw new IllegalArgumentException(string4.toString());
    } else if (1 <= weight ? weight >= 1025 : true) {
      int l = 0;
      String string3 = "附魔权重必须在 1 到 1024 之间";
      throw new IllegalArgumentException(string3.toString());
    } else if (1 <= maxLevel ? maxLevel >= 256 : true) {
      int k = 0;
      String string2 = "附魔最高等级必须在 1 到 255 之间";
      throw new IllegalArgumentException(string2.toString());
    } else if (anvilCost < 0) {
      int j = 0;
      String string1 = "附魔铁砧成本不能为负数";
      throw new IllegalArgumentException(string1.toString());
    } else if (slots.length == 0) {
      int i = 0;
      String string = "附魔至少需要一个装备槽";
      throw new IllegalArgumentException(string.toString());
    } else {
      ModRegistrar modRegistrar = this.registrar;
      ResourceKey resourceKey = Registries.ENCHANTMENT;
      EnchantmentEntry enchantmentEntry = new EnchantmentEntry(
          modRegistrar.key(resourceKey, path),
          supportedItems,
          primaryItems,
          exclusiveWith,
          weight,
          maxLevel,
          minCost,
          maxCost,
          anvilCost,
          java.util.List.of(slots),
          configure
      );
      this.entries.add(enchantmentEntry);
      this.translations.add("enchantment." + this.registrar.namespace + "." + path, zhCn, enUs);
      return enchantmentEntry;
    }
  }

  public final void bootstrap(@NotNull BootstrapContext<Enchantment> context) {
    HolderGetter holderGetter = context.lookup(Registries.ITEM);
    HolderGetter holderGetter1 = context.lookup(Registries.ENCHANTMENT);
    Iterable iterable = this.entries;
    int i = 0;

    for (Object object : iterable) {
      EnchantmentEntry enchantmentEntry = (EnchantmentEntry) object;
      int j = 0;
      EnchantmentDefinition enchantmentDefinition1;
      if (enchantmentEntry.getPrimaryItems() == null) {
        HolderSet holderSet2 = (HolderSet) holderGetter.getOrThrow(enchantmentEntry.getSupportedItems());
        int p = enchantmentEntry.getWeight();
        int q = enchantmentEntry.getMaxLevel();
        Cost cost2 = enchantmentEntry.getMinCost();
        Cost cost3 = enchantmentEntry.getMaxCost();
        int r = enchantmentEntry.getAnvilCost();
        EquipmentSlotGroup[] builder = enchantmentEntry.getSlots().toArray(new EquipmentSlotGroup[0]);
        enchantmentDefinition1 = Enchantment.definition(holderSet2, p, q, cost2, cost3, r, Arrays.copyOf(builder, builder.length));
      } else {
        HolderSet holderSet = (HolderSet) holderGetter.getOrThrow(enchantmentEntry.getSupportedItems());
        HolderSet holderSet1 = (HolderSet) holderGetter.getOrThrow(enchantmentEntry.getPrimaryItems());
        int n = enchantmentEntry.getWeight();
        int o = enchantmentEntry.getMaxLevel();
        Cost cost = enchantmentEntry.getMinCost();
        Cost cost1 = enchantmentEntry.getMaxCost();
        int s = enchantmentEntry.getAnvilCost();
        EquipmentSlotGroup[] equipmentSlotGroups1 = enchantmentEntry.getSlots().toArray(new EquipmentSlotGroup[0]);
        enchantmentDefinition1 = Enchantment.definition(
            holderSet, holderSet1, n, o, cost, cost1, s, Arrays.copyOf(equipmentSlotGroups1, equipmentSlotGroups1.length)
        );
      }

      EnchantmentDefinition enchantmentDefinition = enchantmentDefinition1;
      Builder builder1 = Enchantment.enchantment(enchantmentDefinition);
      TagKey tagKey1 = enchantmentEntry.getExclusiveWith();
      if (tagKey1 != null) {
        TagKey tagKey = tagKey1;
        int l = 0;
        builder1.exclusiveWith((HolderSet) holderGetter1.getOrThrow(tagKey));
      }

      Builder builder1x = builder1;
      enchantmentEntry.getConfigure().accept(builder1x);
      ModRegistrar modRegistrar = this.registrar;
      ResourceKey resourceKey = enchantmentEntry.getKey();
      Enchantment enchantment = builder1.build(enchantmentEntry.getKey().location());
      modRegistrar.dynamic(context, resourceKey, enchantment);
    }
  }
}
