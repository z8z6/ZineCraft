package com.cxxcxx.zinecraft.main.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ExampleScreen extends AbstractContainerScreen<ExampleMenu> {

    // 背景图（先用原版熔炉图，你可以自己换）
    private static final ResourceLocation BACKGROUND =
            ResourceLocation.parse("minecraft:textures/gui/container/generic_54.png");

    // 箱子界面标准宽度高度
    private static final int CHEST_WIDTH = 176;
    private static final int CHEST_HEIGHT = 168;

    public ExampleScreen(ExampleMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        // 必须设置箱子界面尺寸
        this.imageWidth = CHEST_WIDTH;
        this.imageHeight = CHEST_HEIGHT;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // 绘制箱子背景（3x3 小箱子样式）
        gui.blit(BACKGROUND, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        super.render(gui, mouseX, mouseY, partialTick);
        this.renderTooltip(gui, mouseX, mouseY);
    }
}