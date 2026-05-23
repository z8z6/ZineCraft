package com.cxxcxx.zinecraft.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.cxxcxx.zinecraft.ZineCraft.CREATIVE_MODE_TABS;
import static com.cxxcxx.zinecraft.ZineCraft.ITEMS;


public class ItemRegistry {

    public static final DeferredItem<Item> ExampleItem = ITEMS.register(
            "example_item",
            () -> new Item(new Item.Properties())
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MY_TAB =
            CREATIVE_MODE_TABS.register("zinecraft", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.zinecraft"))
            .icon(() -> ExampleItem.get().getDefaultInstance())
            .displayItems((params, output) -> {
                output.accept(ExampleItem.get());
            })
            .build());
}
