package com.cxxcxx.zinecraft.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExampleBlockEntity extends BaseContainerBlockEntity {
    // 物品栏大小
    public static final int SLOTS = 9;

    // 真正的机器物品栏（你写对了）
    private NonNullList<ItemStack> ItemList = NonNullList.withSize(SLOTS, ItemStack.EMPTY);

    // 进度数据（熔炉进度之类的）
    private final ContainerData data = new ContainerData() {
        @Override public int get(int idx) { return 0; }
        @Override public void set(int idx, int val) {}
        @Override public int getCount() { return 0; }
    };

    // 构造方法
    public ExampleBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.EXAMPLE_BLOCK_ENTITY.get(), pos, blockState);
    }

    // 方块默认名字
    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.zinecraft.example_block");
    }

    // 获取物品栏
    @Override
    protected NonNullList<ItemStack> getItems() {
        return ItemList;
    }

    // 设置物品栏
    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.ItemList = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }

//    // 绑定菜单（关键修复）
//    @Override
//    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
//        // 传给菜单的是：this(自己)，不是错误的 inventory！
//        return new ExampleMenu(id, playerInventory, this, this.data);
//    }

    // 返回格子数量（必须返回9！）
    @Override
    public int getContainerSize() {
        return SLOTS;
    }
}