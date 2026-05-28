package com.cxxcxx.zinecraft.core.item


import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.block.ModBlock
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item


object ModItem {

  val EXAMPLE_ITEM: Item = register("example_item", Item(Item.Properties()))

  fun <T : Item> register(path: String, item: T): T {
    val id = ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, path)
    return Registry.register(BuiltInRegistries.ITEM, id, item)
  }

  fun init() {
    initCreativeTab()
  }

  fun initCreativeTab() {
    ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
      .register(ModifyEntries { content: FabricItemGroupEntries ->
        content.accept(EXAMPLE_ITEM)
        content.accept(ModBlock.EXAMPLE_ENTITY_BLOCK.asItem())
      })
  }

}

