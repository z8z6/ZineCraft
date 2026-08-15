package com.cxxcxx.zinecraft.api.weapon;

import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import kotlin.jvm.functions.Function1;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public final class WeaponRegistry {
  private final Map<ResourceLocation, WeaponAction> actions = new LinkedHashMap<>();
  private final Map<ResourceLocation, WeaponDefinition> definitions = new LinkedHashMap<>();
  private final Map<Item, WeaponDefinition> definitionsByItem = new LinkedHashMap<>();
  private final Map<ResourceLocation, Set<ResourceLocation>> dynamicDefinitionIds = new LinkedHashMap<>();
  private final List<Function1<ItemStack, WeaponDefinition>> resolvers = new ArrayList<>();

  public WeaponAction registerAction(WeaponAction action) {
    if (actions.putIfAbsent(action.getId(), action) != null)
      throw new IllegalArgumentException("重复的武器动作 ID：" + action.getId());
    return action;
  }

  public WeaponDefinition register(Item item, WeaponDefinition definition) {
    validateActions(definition);
    if (definitions.putIfAbsent(definition.getId(), definition) != null)
      throw new IllegalArgumentException("重复的武器 ID：" + definition.getId());
    if (definitionsByItem.putIfAbsent(item, definition) != null)
      throw new IllegalArgumentException("物品已绑定武器定义：" + item);
    return definition;
  }

  public synchronized void replaceDynamic(ResourceLocation source, Collection<WeaponDefinition> replacements) {
    for (var definition : replacements) {
      validateActions(definition);
      boolean ownedByDynamicSource = dynamicDefinitionIds.values().stream().anyMatch(ids -> ids.contains(definition.getId()));
      if (definitions.containsKey(definition.getId()) && !ownedByDynamicSource)
        throw new IllegalArgumentException("动态武器 ID 与静态武器冲突：" + definition.getId());
    }
    var old = dynamicDefinitionIds.remove(source);
    if (old != null) old.forEach(definitions::remove);
    var ids = new LinkedHashSet<ResourceLocation>();
    for (var definition : replacements) {
      definitions.put(definition.getId(), definition);
      ids.add(definition.getId());
    }
    dynamicDefinitionIds.put(source, ids);
  }

  public void registerResolver(Function1<? super ItemStack, WeaponDefinition> resolver) {
    resolvers.add(stack -> resolver.invoke(stack));
  }

  public WeaponAction action(ResourceLocation id) {
    return actions.get(id);
  }

  public WeaponDefinition definition(ResourceLocation id) {
    return definitions.get(id);
  }

  public WeaponDefinition definition(ItemStack stack) {
    var direct = definitionsByItem.get(stack.getItem());
    if (direct != null) return direct;
    for (var resolver : resolvers) {
      var result = resolver.invoke(stack);
      if (result != null) return result;
    }
    return null;
  }

  private void validateActions(WeaponDefinition definition) {
    if (!definition.getActions().values().stream().allMatch(actions::containsKey))
      throw new IllegalArgumentException("武器 " + definition.getId() + " 引用了未注册的动作");
  }
}
