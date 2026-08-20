package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.WeaponCatalog;
import com.cxxcxx.zinecraft.api.weapon.WeaponInput;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 声明一个静态武器及其输入、服务端动作和客户端表现映射。
 */
public final class WeaponBuilder implements ItemLike {
  public final WeaponCatalog catalog;
  public final String path;
  public final ItemLike item;
  private final Map<WeaponInput, WeaponActionBuilder<?>> mutableActions = new LinkedHashMap<>();
  private final Map<ResourceLocation, WeaponPresentationBuilder> mutablePresentations = new LinkedHashMap<>();
  private String translationKey;
  private boolean built;

  public WeaponBuilder(WeaponCatalog catalog, String path, ItemLike item) {
    this.catalog = Objects.requireNonNull(catalog, "武器目录不能为空");
    this.path = Objects.requireNonNull(path, "武器 ID 不能为空");
    this.item = Objects.requireNonNull(item, "武器物品不能为空：" + path);
    this.translationKey = "item." + catalog.namespace() + "." + path;
  }

  public WeaponBuilder translationKey(String translationKey) {
    ensureMutable();
    if (translationKey == null || translationKey.isBlank()) {
      throw new IllegalArgumentException("武器翻译键不能为空：" + path);
    }
    this.translationKey = translationKey;
    return this;
  }

  public WeaponBuilder action(WeaponInput input, WeaponActionBuilder<?> action) {
    ensureMutable();
    Objects.requireNonNull(input, "武器输入不能为空：" + path);
    Objects.requireNonNull(action, "武器动作不能为空：" + path);
    if (mutableActions.putIfAbsent(input, action) != null) {
      throw new IllegalArgumentException("武器输入重复：" + path + " / " + input);
    }
    return this;
  }

  public WeaponBuilder presentation(WeaponActionBuilder<?> action, Consumer<WeaponPresentationBuilder> configure) {
    ensureMutable();
    Objects.requireNonNull(action, "表现对应的武器动作不能为空：" + path);
    Objects.requireNonNull(configure, "武器表现配置不能为空：" + path);
    if (!mutableActions.containsValue(action)) {
      throw new IllegalArgumentException("表现只能引用该武器已绑定的动作：" + action.resourceKey());
    }
    WeaponPresentationBuilder builder = new WeaponPresentationBuilder(action.resourceKey());
    configure.accept(builder);
    if (mutablePresentations.putIfAbsent(action.resourceKey(), builder.build()) != null) {
      throw new IllegalArgumentException("武器动作表现重复：" + action.resourceKey());
    }
    return this;
  }

  public ResourceLocation resourceKey() {
    return ResourceLocation.fromNamespaceAndPath(catalog.namespace(), path);
  }

  /**
   * @return 此武器声明绑定的物品实例
   */
  @Override
  public @NotNull Item asItem() {
    return item.asItem();
  }

  public Map<WeaponInput, WeaponAction> actions() {
    requireBuilt();
    Map<WeaponInput, WeaponAction> actions = new LinkedHashMap<>();
    mutableActions.forEach((input, builder) -> actions.put(input, builder.getAction()));
    return Map.copyOf(actions);
  }

  public Map<WeaponInput, ResourceLocation> actionIds() {
    requireBuilt();
    Map<WeaponInput, ResourceLocation> actionIds = new LinkedHashMap<>();
    mutableActions.forEach((input, action) -> actionIds.put(input, action.resourceKey()));
    return Map.copyOf(actionIds);
  }

  public Map<ResourceLocation, WeaponPresentationBuilder> presentations() {
    requireBuilt();
    return Map.copyOf(mutablePresentations);
  }

  public String translationKey() {
    requireBuilt();
    return translationKey;
  }

  @Nullable
  public ResourceLocation action(WeaponInput input) {
    requireBuilt();
    WeaponActionBuilder<?> action = mutableActions.get(Objects.requireNonNull(input, "武器输入不能为空"));
    return action == null ? null : action.resourceKey();
  }

  @Nullable
  public WeaponPresentationBuilder presentation(ResourceLocation actionId) {
    requireBuilt();
    return mutablePresentations.get(Objects.requireNonNull(actionId, "武器动作 ID 不能为空"));
  }

  public WeaponBuilder build() {
    ensureMutable();
    if (mutableActions.isEmpty()) {
      throw new IllegalArgumentException("武器至少需要绑定一个动作：" + path);
    }
    built = true;
    try {
      return catalog.register(this);
    } catch (RuntimeException exception) {
      built = false;
      throw exception;
    }
  }

  private void ensureMutable() {
    if (built) {
      throw new IllegalStateException("武器 builder 不能重复 build 或在 build 后修改：" + path);
    }
  }

  private void requireBuilt() {
    if (!built) {
      throw new IllegalStateException("武器尚未 build：" + path);
    }
  }
}
