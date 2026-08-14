package com.cxxcxx.zinecraft.api.weapon

import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class WeaponRegistry {
  private val actions = mutableMapOf<ResourceLocation, WeaponAction>()
  private val definitions = mutableMapOf<ResourceLocation, WeaponDefinition>()
  private val definitionsByItem = mutableMapOf<Item, WeaponDefinition>()
  private val dynamicDefinitionIds = mutableMapOf<ResourceLocation, Set<ResourceLocation>>()
  private val resolvers = mutableListOf<(ItemStack) -> WeaponDefinition?>()

  fun registerAction(action: WeaponAction): WeaponAction {
    require(actions.putIfAbsent(action.id, action) == null) { "重复的武器动作 ID：${action.id}" }
    return action
  }

  fun register(item: Item, definition: WeaponDefinition): WeaponDefinition {
    require(definition.actions.values.all(actions::containsKey)) { "武器 ${definition.id} 引用了未注册的动作" }
    require(definitions.putIfAbsent(definition.id, definition) == null) { "重复的武器 ID：${definition.id}" }
    require(definitionsByItem.putIfAbsent(item, definition) == null) { "物品已绑定武器定义：$item" }
    return definition
  }

  /** Replaces one runtime-loaded definition set while keeping statically registered weapons intact. */
  @Synchronized
  fun replaceDynamic(source: ResourceLocation, replacements: Collection<WeaponDefinition>) {
    replacements.forEach { definition ->
      require(definition.actions.values.all(actions::containsKey)) { "武器 ${definition.id} 引用了未注册的动作" }
      require(definitions[definition.id] == null || dynamicDefinitionIds.values.any { definition.id in it }) {
        "动态武器 ID 与静态武器冲突：${definition.id}"
      }
    }
    dynamicDefinitionIds.remove(source)?.forEach(definitions::remove)
    replacements.forEach { definitions[it.id] = it }
    dynamicDefinitionIds[source] = replacements.mapTo(linkedSetOf(), WeaponDefinition::id)
  }

  fun registerResolver(resolver: (ItemStack) -> WeaponDefinition?) {
    resolvers += resolver
  }

  fun action(id: ResourceLocation): WeaponAction? = actions[id]

  fun definition(id: ResourceLocation): WeaponDefinition? = definitions[id]

  fun definition(stack: ItemStack): WeaponDefinition? =
    definitionsByItem[stack.item] ?: resolvers.firstNotNullOfOrNull { it(stack) }
}
