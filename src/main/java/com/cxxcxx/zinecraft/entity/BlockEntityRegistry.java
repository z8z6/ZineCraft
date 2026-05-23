package com.cxxcxx.zinecraft.entity;

import com.cxxcxx.zinecraft.item.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.cxxcxx.zinecraft.ZineCraft.MODID;

public class BlockEntityRegistry {
    public static void init(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final Supplier<BlockEntityType<ExampleBlockEntity>> EXAMPLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("example_block_entity",
                    () -> BlockEntityType.Builder.of(ExampleBlockEntity::new,
                                    BlockRegistry.EXAMPLE_ENTITY_BLOCK.get())
                            .build(null)
            );
}
