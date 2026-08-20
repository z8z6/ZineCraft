package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.WeaponActionBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.WeaponBuilder;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * 武器运行时目录，统一登记动作、武器 builder 与物品解析器。
 */
public final class WeaponCatalog {
  private static final Pattern PATH_PATTERN = Pattern.compile("[a-z0-9_]+");

  private final String namespace;
  private final Map<ResourceLocation, WeaponAction> actions = new LinkedHashMap<>();
  private final Map<ResourceLocation, WeaponBuilder> weapons = new LinkedHashMap<>();
  private final List<Function<ItemStack, WeaponBuilder>> resolvers = new ArrayList<>();
  private final List<WeaponBuilder> mutableEntries = new ArrayList<>();
  public final List<WeaponBuilder> entries = Collections.unmodifiableList(mutableEntries);
  private final List<WeaponActionBuilder<?>> mutableActionEntries = new ArrayList<>();
  public final List<WeaponActionBuilder<?>> actionEntries = Collections.unmodifiableList(mutableActionEntries);

  public WeaponCatalog(String namespace) {
    if (namespace == null || namespace.isBlank()) {
      throw new IllegalArgumentException("武器目录命名空间不能为空");
    }
    this.namespace = namespace;
  }

  public String namespace() {
    return namespace;
  }

  /**
   * 校验并登记一个声明式武器。
   */
  public WeaponBuilder register(WeaponBuilder builder) {
    Objects.requireNonNull(builder, "武器 builder 不能为空");
    if (builder.catalog != this) {
      throw new IllegalArgumentException("武器 builder 不属于当前目录：" + builder.path);
    }
    if (!PATH_PATTERN.matcher(builder.path).matches()) {
      throw new IllegalArgumentException("武器 ID 必须是 snake_case：" + builder.path);
    }

    ResourceLocation id = builder.resourceKey();
    if (weapons.containsKey(id)) {
      throw new IllegalArgumentException("重复的武器 ID：" + id);
    }
    if (mutableEntries.stream().anyMatch(entry -> entry.item == builder.item)) {
      throw new IllegalArgumentException("物品已绑定武器：" + builder.path);
    }

    validateActions(builder);
    weapons.put(id, builder);
    mutableEntries.add(builder);
    return builder;
  }

  /**
   * 校验并登记一个声明式武器动作。
   */
  public WeaponActionBuilder<?> register(WeaponActionBuilder<?> builder) {
    Objects.requireNonNull(builder, "武器动作 builder 不能为空");
    if (builder.catalog != this) {
      throw new IllegalArgumentException("武器动作 builder 不属于当前目录：" + builder.path);
    }
    if (!PATH_PATTERN.matcher(builder.path).matches()) {
      throw new IllegalArgumentException("武器动作 ID 必须是 snake_case：" + builder.path);
    }
    WeaponAction action = builder.getAction();
    if (actions.containsKey(action.getId())) {
      throw new IllegalArgumentException("重复的武器动作 ID：" + action.getId());
    }
    registerAction(action);
    mutableActionEntries.add(builder);
    return builder;
  }

  /**
   * 登记可由一个或多个武器引用的服务端动作。重复登记同一实例是幂等的。
   */
  public WeaponAction registerAction(WeaponAction action) {
    Objects.requireNonNull(action, "武器动作不能为空");
    WeaponAction previous = actions.putIfAbsent(action.getId(), action);
    if (previous != null && previous != action) {
      throw new IllegalArgumentException("重复的武器动作 ID：" + action.getId());
    }
    return action;
  }

  public void registerResolver(Function<? super ItemStack, ? extends WeaponBuilder> resolver) {
    Objects.requireNonNull(resolver, "武器解析器不能为空");
    resolvers.add(resolver::apply);
  }

  @Nullable
  public WeaponAction action(ResourceLocation id) {
    return actions.get(id);
  }

  @Nullable
  public WeaponBuilder weapon(ResourceLocation id) {
    return weapons.get(id);
  }

  @Nullable
  public WeaponBuilder weapon(ItemStack stack) {
    Objects.requireNonNull(stack, "物品栈不能为空");
    for (WeaponBuilder entry : mutableEntries) {
      if (stack.is(entry.item.asItem())) return entry;
    }
    for (Function<ItemStack, WeaponBuilder> resolver : resolvers) {
      WeaponBuilder result = resolver.apply(stack);
      if (result != null) return result;
    }
    return null;
  }

  private void validateActions(WeaponBuilder weapon) {
    if (!weapon.actionIds().values().stream().allMatch(actions::containsKey)) {
      throw new IllegalArgumentException("武器 " + weapon.resourceKey() + " 引用了未注册的动作");
    }
  }
}
