package com.cxxcxx.zinecraft.entity;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

// 修正版 Menu
//public class ExampleMenu extends AbstractContainerMenu {
//
//    // 容器大小
//    private static final int SLOT_COUNT = 9;
//    private static final int PLAYER_INVENTORY_END = SLOT_COUNT + 36;
//
//    // 构造方法参数改为：Container，而不是 NonNullList
//    public ExampleMenu(int id, Inventory playerInv, Container benchInv, ContainerData data) {
//        super(MenuRegistry.EXAMPLE_MENU.get(), id);
//
//        // 检查容器大小
//        checkContainerSize(benchInv, SLOT_COUNT);
//        // 绑定数据（必须写）
//        addDataSlots(data);
//
//        // ========== 机器内部 3x3 格子 ==========
//        for (int row = 0; row < 3; row++) {
//            for (int col = 0; col < 3; col++) {
//                this.addSlot(new Slot(benchInv, col + row * 3, 62 + col * 18, 17 + row * 18));
//            }
//        }
//
//        // ========== 玩家物品栏 ==========
//        bindPlayerInventory(playerInv, 8, 84);
//    }
//
//    // ========== 快速移动（Shift 移动物品）==========
//    @Override
//    public ItemStack quickMoveStack(Player player, int index) {
//        ItemStack clickedStack = getSlot(index).getItem();
//        ItemStack copy = clickedStack.copy();
//
//        // 点击的是【机器内部格子】 → 移动到玩家背包
//        if (index < SLOT_COUNT) {
//            if (!moveItemStackTo(clickedStack, SLOT_COUNT, PLAYER_INVENTORY_END, true)) {
//                return ItemStack.EMPTY;
//            }
//        }
//        // 点击的是【玩家背包】 → 移动到机器内部
//        else {
//            if (!moveItemStackTo(clickedStack, 0, SLOT_COUNT, false)) {
//                return ItemStack.EMPTY;
//            }
//        }
//
//        if (clickedStack.isEmpty()) {
//            getSlot(index).set(ItemStack.EMPTY);
//        } else {
//            getSlot(index).setChanged();
//        }
//
//        return copy;
//    }
//
//    // ========== 权限校验 ==========
//    @Override
//    public boolean stillValid(Player player) {
//        return true;
//    }
//}