package com.cxxcxx.zinecraft.main.menu;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.cxxcxx.zinecraft.main.ZineCraft.MODID;


public class MenuRegistry {

    public static void init(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }


    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, MODID);

    public static final Supplier<MenuType<ExampleMenu>> EXAMPLE_MENU =
            register("example_menu", ExampleMenu::new);

    private static <T extends AbstractContainerMenu>
    DeferredHolder<MenuType<?>,MenuType<T>> register(String name, IContainerFactory<T> factory)
    {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

}