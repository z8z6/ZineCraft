package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.BlockEntityCatalog;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 将方块实体类型与承载它的实体方块一并声明。
 *
 * @param <E> 方块实体类型
 * @param <B> 同时实现 {@link EntityBlock} 的方块类型
 */
public final class BlockEntityBuilder<E extends BlockEntity, B extends Block & EntityBlock>
    implements Supplier<BlockEntityType<E>> {
  public final String path;
  public final BlockEntitySupplier<E> factory;
  private final BlockEntityCatalog catalog;
  private final BlockBuilder<B> entityBlock;
  private Supplier<BlockEntityType<E>> entityType;

  /**
   * 创建方块实体声明。
   *
   * @param catalog     接收方块实体类型的目录
   * @param path        方块实体类型的命名空间内路径
   * @param factory     根据位置和方块状态创建方块实体的工厂
   * @param entityBlock 承载该方块实体的方块声明
   */
  public BlockEntityBuilder(
      BlockEntityCatalog catalog,
      String path,
      BlockEntitySupplier<E> factory,
      BlockBuilder<B> entityBlock
  ) {
    this.catalog = Objects.requireNonNull(catalog, "方块实体目录不能为空");
    this.path = Objects.requireNonNull(path, "方块实体 ID 不能为空");
    this.factory = Objects.requireNonNull(factory, "方块实体 factory 不能为空：" + path);
    this.entityBlock = Objects.requireNonNull(entityBlock, "方块实体对应方块不能为空：" + path);
  }

  /**
   * 校验并将方块实体类型登记到所属目录。
   *
   * @return 当前构建器
   */
  public BlockEntityBuilder<E, B> build() {
    if (entityType != null) {
      throw new IllegalStateException("方块实体 builder 不能重复 build：" + path);
    }
    return catalog.register(this);
  }

  /** @return 承载当前方块实体的方块声明 */
  public BlockBuilder<B> entityBlock() {
    return entityBlock;
  }

  /** @return 登记后的方块实体类型供应器 */
  public Supplier<BlockEntityType<E>> entityType() {
    return Objects.requireNonNull(entityType, "方块实体尚未 build：" + path);
  }

  /**
   * 判断该声明是否属于指定目录。
   *
   * @param catalog 待比较的方块实体目录
   * @return 属于该目录时为 {@code true}
   */
  public boolean belongsTo(BlockEntityCatalog catalog) {
    return this.catalog == catalog;
  }

  /**
   * 绑定目录创建的延迟方块实体类型句柄。
   *
   * @param entityType 登记后的方块实体类型供应器
   */
  public void bind(Supplier<BlockEntityType<E>> entityType) {
    this.entityType = Objects.requireNonNull(entityType, "entityType");
  }

  /** @return 已登记的方块实体类型 */
  @Override
  public BlockEntityType<E> get() {
    return entityType().get();
  }
}
