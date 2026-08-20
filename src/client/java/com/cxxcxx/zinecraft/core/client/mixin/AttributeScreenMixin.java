package com.cxxcxx.zinecraft.core.client.mixin;

import com.cxxcxx.zinecraft.core.client.collection.CollectibleEffectDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * 在 L2 Attributes 页旁显示当前装备藏品的探索效果汇总。
 */
@Mixin(targets = "dev.xkmc.l2tabs.tabs.contents.BaseAttributeScreen")
public abstract class AttributeScreenMixin extends Screen {
  private static final int MIN_PANEL_WIDTH = 150;
  private static final int MAX_PANEL_WIDTH = 230;
  private static final int LINE_HEIGHT = 10;

  @Shadow
  public int imageWidth;
  @Shadow
  public int leftPos;
  @Shadow
  public int topPos;

  protected AttributeScreenMixin(Component title) {
    super(title);
  }

  @Shadow
  public abstract LivingEntity getEntity();

  @Inject(
      method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
      at = @At("TAIL")
  )
  private void zinecraft_renderCollectibleEffects(
      GuiGraphics graphics,
      int mouseX,
      int mouseY,
      float partialTick,
      CallbackInfo callback
  ) {
    List<Component> lines = CollectibleEffectDisplay.lines(getEntity());
    Component heading = Component.translatable("menu.tabs.attribute.collectible_effects");
    int contentWidth = Math.max(font.width(heading), lines.stream().mapToInt(font::width).max().orElse(0));
    int panelWidth = Math.clamp(contentWidth + 12, MIN_PANEL_WIDTH, MAX_PANEL_WIDTH);
    int panelHeight = 19 + lines.size() * LINE_HEIGHT;
    int x = leftPos + imageWidth + 6;
    if (x + panelWidth > width - 4) x = Math.max(4, leftPos - panelWidth - 6);
    int y = Math.max(4, Math.min(topPos, height - panelHeight - 4));

    graphics.fill(x, y, x + panelWidth, y + panelHeight, 0xD0101010);
    graphics.fill(x, y, x + panelWidth, y + 1, 0xFF4A778E);
    graphics.drawString(font, heading, x + 6, y + 6, 0x7FD9FF, false);
    for (int index = 0; index < lines.size(); index++) {
      graphics.drawString(font, lines.get(index), x + 6, y + 18 + index * LINE_HEIGHT, 0xE0E0E0, false);
    }
  }
}
