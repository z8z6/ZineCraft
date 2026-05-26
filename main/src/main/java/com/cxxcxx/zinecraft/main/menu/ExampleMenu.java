package com.cxxcxx.zinecraft.main.menu;

import com.cxxcxx.zinecraft.main.entity.ExampleBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ExampleMenu extends AbstractContainerMenu {
    // 机器格子数 3x3 = 9
    private static final int CONTAINER_SIZE = 9;
    public final ExampleBlockEntity blockEntity;
    public Container container;

    // 正确构造方法：3个参数，由 BlockEntity 传入
    public ExampleMenu(int id, Inventory playerInv, FriendlyByteBuf extraData) {
        this(id, playerInv, playerInv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public ExampleMenu(int id, Inventory playerInv, BlockEntity entity) {
        super(MenuRegistry.EXAMPLE_MENU.get(), id);
        blockEntity = (ExampleBlockEntity) entity;
        container = blockEntity;
        // 检查容器大小（必须）
        checkContainerSize(container, CONTAINER_SIZE);

        // =============== 添加机器格子 ===============
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                addSlot(new Slot(container, col + row * 3, 62 + col * 18, 17 + row * 18));
            }
        }

        // =============== 添加玩家背包 ===============
        // 玩家背包 3x9
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInv, 9 + col + row * 9, 8 + col * 18, 84 + row * 18));
            }
        }
        // 玩家快捷栏 9格
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    // ========== Shift 快速移动物品 ==========
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = getSlot(index).getItem();
        ItemStack copy = stack.copy();

        if (index < CONTAINER_SIZE) {
            if (!moveItemStackTo(stack, CONTAINER_SIZE, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(stack, 0, CONTAINER_SIZE, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            getSlot(index).set(ItemStack.EMPTY);
        } else {
            getSlot(index).setChanged();
        }

        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isRemoved();
    }
}