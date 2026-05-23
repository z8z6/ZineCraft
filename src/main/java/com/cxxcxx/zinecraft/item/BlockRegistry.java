package com.cxxcxx.zinecraft.item;

import com.cxxcxx.zinecraft.entity.ExampleEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.cxxcxx.zinecraft.ZineCraft.MODID;

public class BlockRegistry {
    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock(
            "example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    public static final DeferredBlock<Block> EXAMPLE_ENTITY_BLOCK =
            BLOCKS.register("example_entity_block", ExampleEntityBlock::new);

}
