package com.cxxcxx.zinecraft.api.item;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class ItemCatalog {
  private final ModRegistrar registrar;
  private final TranslationCatalog translations;
  private final List<ItemBuilder<?>> mutableEntries = new ArrayList<>();
  public final List<ItemBuilder<?>> entries = Collections.unmodifiableList(mutableEntries);

  public ItemCatalog(ModRegistrar registrar, TranslationCatalog translations) {
    this.registrar = Objects.requireNonNull(registrar, "registrar");
    this.translations = Objects.requireNonNull(translations, "translations");
  }

  public ItemBuilder<Item> builder(String path, String zhCn) {
    return new ItemBuilder<>(this, path, zhCn, () -> new Item(new Item.Properties()));
  }

  public <T extends Item> ItemBuilder<T> builder(String path, String zhCn, Supplier<? extends T> factory) {
    return new ItemBuilder<>(this, path, zhCn, factory);
  }

  private static void validate(String path, String zhCn, String enUs) {
    if (!ResourceLocation.isValidPath(path)) throw new IllegalArgumentException("物品 ID 路径无效：" + path);
    if (zhCn == null || zhCn.isBlank()) throw new IllegalArgumentException("物品中文名不能为空：" + path);
    if (enUs == null || enUs.isBlank()) throw new IllegalArgumentException("物品英文名不能为空：" + path);
  }

  // 注册物品的核心方法
  private <T extends Item> DeferredItem<T> register(ItemBuilder<T> builder) {
    validate(builder.path, builder.zhCn, builder.enUs);
    Objects.requireNonNull(builder.model, "物品模型不能为空：" + builder.path);
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("物品 ID 重复：" + builder.path);
    }

    DeferredItem<T> item = registrar.item(builder.path, builder.factory);
    builder.item = item;
    mutableEntries.add(builder);
    translations.add("item." + registrar.namespace + "." + builder.path, builder.zhCn, builder.enUs);
    return item;
  }

  // Item 物品辅助注册类
  public static final class ItemBuilder<T extends Item> {
    private final ItemCatalog catalog;
    private final Supplier<? extends T> factory;
    public final String path;
    public final String zhCn;
    public String enUs;
    public ModelTemplate model = ModelTemplates.FLAT_ITEM;
    public boolean inCreativeTab = true;
    public int fuelTicks;
    public float compostChance = -1.0F;
    public DeferredItem<T> item;

    private ItemBuilder(ItemCatalog catalog, String path, String zhCn, Supplier<? extends T> factory) {
      this.catalog = catalog;
      this.path = path;
      this.zhCn = zhCn;
      this.enUs = TranslationNames.toDisplayName(path);
      this.factory = Objects.requireNonNull(factory, "物品 factory 不能为空：" + path);
    }

    public ItemBuilder<T> enUs(String enUs) {
      this.enUs = enUs;
      return this;
    }

    public ItemBuilder<T> model(ModelTemplate model) {
      this.model = model;
      return this;
    }

    public ItemBuilder<T> hideCreativeTab() {
      inCreativeTab = false;
      return this;
    }

    public ItemBuilder<T> fuel(int ticks) {
      if (ticks <= 0) throw new IllegalArgumentException("燃料时间必须大于 0");
      fuelTicks = ticks;
      return this;
    }

    public ItemBuilder<T> compost(float chance) {
      if (chance < 0.0F || chance > 1.0F) throw new IllegalArgumentException("堆肥概率必须在 0 到 1 之间");
      compostChance = chance;
      return this;
    }

    public DeferredItem<T> build() {
      if (item != null) throw new IllegalStateException("物品 builder 不能重复 build：" + path);
      return catalog.register(this);
    }
  }
}
