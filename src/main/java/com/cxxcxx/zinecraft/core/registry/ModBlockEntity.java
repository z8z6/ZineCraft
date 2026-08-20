package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.BlockBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.BlockEntityBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.entity.ExampleBlockEntity;
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public final class ModBlockEntity {
  private ModBlockEntity() {
  }

  public static final BlockEntityBuilder<ExampleBlockEntity, ExampleEntityBlock> EXAMPLE_BLOCK_ENTITY =
      new BlockEntityBuilder<>(
          Zinecraft.BLOCK_ENTITIES,
          "example_block_entity",
          ExampleBlockEntity::new,
          new BlockBuilder<>(
              Zinecraft.BLOCKS,
              "example_entity_block",
              "示例实体方块",
              () -> new ExampleEntityBlock(Properties.of().sound(SoundType.GRASS))
          )
      ).build();

  public static void bootstrap() {
  }
}
