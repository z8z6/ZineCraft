package com.cxxcxx.zinecraft.main.item;

import com.cxxcxx.zinecraft.core.annotation.Register;
import com.cxxcxx.zinecraft.main.ZineCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.cxxcxx.zinecraft.main.ZineCraft.*;
import static com.cxxcxx.zinecraft.main.item.BlockRegistry.*;


public class ItemRegistry {

    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> Originite = ITEMS.register(
            "originite",
            () -> new Item(new Item.Properties())
    );

    @Register(mod= ZineCraft.MODID, en_us = "www", zh_cn = "你好")
    public static final DeferredItem<Item> WWW = ITEMS.register(
            "www",
            () -> new Item(new Item.Properties())
            );;

    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem(
            "example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    public static final DeferredItem<BlockItem> EXAMPLE_ENTITY_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem("example_entity_block", EXAMPLE_ENTITY_BLOCK);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB =
            CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.zinecraft"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> Originite.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(EXAMPLE_ITEM.get());
                        output.accept(EXAMPLE_BLOCK_ITEM.get());
                        output.accept(EXAMPLE_ENTITY_BLOCK_ITEM.get());
                        output.accept(Originite.get());
                    }).build());
}
