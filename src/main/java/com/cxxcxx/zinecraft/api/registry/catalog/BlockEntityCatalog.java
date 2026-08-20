package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.BlockEntityBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 方块实体类型注册目录，负责关联方块实体工厂与承载方块。
 */
public final class BlockEntityCatalog {
  private final DeferredRegister<BlockEntityType<?>> registry;
  private final List<BlockEntityBuilder<?, ?>> mutableEntries = new ArrayList<>();
  public final List<BlockEntityBuilder<?, ?>> entries = Collections.unmodifiableList(mutableEntries);

  /**
   * 创建方块实体注册目录。
   *
   * @param namespace 模组命名空间
   */
  public BlockEntityCatalog(String namespace) {
    registry = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE.key(), namespace);
  }

  /**
   * 确保承载方块已登记，再登记方块实体类型。
   *
   * @param builder 方块实体声明
   * @param <E>     方块实体类型
   * @param <B>     承载方块类型
   * @return 已绑定类型句柄的声明
   */
  public <E extends BlockEntity, B extends Block & EntityBlock> BlockEntityBuilder<E, B> register(
      BlockEntityBuilder<E, B> builder
  ) {
    validate(builder);
    if (builder.entityBlock().block == null) {
      builder.entityBlock().build();
    }

    Supplier<BlockEntityType<E>> entityType = registerType(builder);
    builder.bind(entityType);
    mutableEntries.add(builder);
    return builder;
  }

  /**
   * 将方块实体延迟注册器挂接到模组事件总线。
   *
   * @param modBus 模组事件总线
   */
  public void register(IEventBus modBus) {
    registry.register(modBus);
  }

  /**
   * 创建并登记具体的方块实体类型。
   *
   * @param builder 方块实体声明
   * @param <E> 方块实体类型
   * @param <B> 承载方块类型
   * @return 方块实体类型供应器
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private <E extends BlockEntity, B extends Block & EntityBlock> Supplier<BlockEntityType<E>> registerType(
      BlockEntityBuilder<E, B> builder
  ) {
    return (Supplier) registry.register(builder.path, () -> BlockEntityType.Builder.of(
        (BlockEntitySupplier) builder.factory,
        builder.entityBlock().get()
    ).build(null));
  }

  /**
   * 校验声明归属、注册路径和重复 ID。
   *
   * @param builder 待校验的方块实体声明
   */
  private void validate(BlockEntityBuilder<?, ?> builder) {
    Objects.requireNonNull(builder, "方块实体 builder 不能为空");
    if (!builder.belongsTo(this)) {
      throw new IllegalArgumentException("方块实体 builder 不属于当前目录：" + builder.path);
    }
    if (!ResourceLocation.isValidPath(builder.path)) {
      throw new IllegalArgumentException("方块实体 ID 路径无效：" + builder.path);
    }
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("方块实体 ID 重复：" + builder.path);
    }
  }
}
