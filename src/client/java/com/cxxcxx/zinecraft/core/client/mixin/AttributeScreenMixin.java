package com.cxxcxx.zinecraft.core.client.mixin;

import com.cxxcxx.zinecraft.core.client.collection.CollectibleEffectDisplay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

/**
 * 将当前装备藏品的探索效果汇总追加到 L2 Attributes 属性列表。
 */
@Mixin(targets = "dev.xkmc.l2tabs.tabs.contents.BaseAttributeScreen")
public abstract class AttributeScreenMixin extends Screen {
  private static final int LINE_HEIGHT = 10;
  private static final String COLLECTIBLE_MODIFIER_PREFIX = "collectible/";

  protected AttributeScreenMixin(Component title) {
    super(title);
  }

  @Shadow
  public abstract LivingEntity getEntity();

  @Accessor("page")
  protected abstract int zinecraft_getPage();

  @Invoker("getSize")
  protected static int zinecraft_getPageSize() {
    throw new AssertionError();
  }

  @ModifyVariable(method = "init()V", at = @At("STORE"), ordinal = 2)
  private int zinecraft_includeCollectibleLinesInPageCount(int attributeCount) {
    return attributeCount + zinecraft_collectibleLines(getEntity()).size();
  }

  @Inject(
      method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIFLnet/minecraft/world/entity/LivingEntity;Ljava/util/List;)V",
      at = @At("TAIL")
  )
  private void zinecraft_renderCollectibleEffects(
      GuiGraphics graphics,
      int mouseX,
      int mouseY,
      float partialTick,
      LivingEntity entity,
      List<?> attributes,
      CallbackInfo callback
  ) {
    List<CollectibleEffectDisplay.Entry> lines = zinecraft_collectibleLines(entity);
    int pageSize = zinecraft_getPageSize();
    int pageStart = zinecraft_getPage() * pageSize;
    int pageEnd = pageStart + pageSize;
    BaseTextScreenAccessor screen = (BaseTextScreenAccessor) (Object) this;
    CollectibleEffectDisplay.Entry hovered = null;
    for (int index = 0; index < lines.size(); index++) {
      int combinedIndex = attributes.size() + index;
      if (combinedIndex < pageStart || combinedIndex >= pageEnd) continue;
      int x = screen.zinecraft_getLeftPos() + 8;
      int y = screen.zinecraft_getTopPos() + 6 + (combinedIndex - pageStart) * LINE_HEIGHT;
      CollectibleEffectDisplay.Entry entry = lines.get(index);
      graphics.drawString(font, entry.line(), x, y, 0, false);
      if (mouseX > x && mouseX < x + font.width(entry.line())
          && mouseY > y && mouseY < y + LINE_HEIGHT) {
        hovered = entry;
      }
    }
    if (hovered != null) {
      graphics.renderComponentTooltip(font, hovered.tooltip(hasShiftDown()), mouseX, mouseY);
    }
  }

  @Inject(
      method = "name(Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)"
          + "Lnet/minecraft/network/chat/MutableComponent;",
      at = @At("RETURN"),
      cancellable = true
  )
  private static void zinecraft_useCollectibleName(
      AttributeModifier modifier,
      CallbackInfoReturnable<MutableComponent> callback
  ) {
    ResourceLocation modifierId = modifier.id();
    String path = modifierId.getPath();
    if (!path.startsWith(COLLECTIBLE_MODIFIER_PREFIX)) return;
    int pathEnd = path.indexOf('/', COLLECTIBLE_MODIFIER_PREFIX.length());
    if (pathEnd < 0) return;
    String collectiblePath = path.substring(COLLECTIBLE_MODIFIER_PREFIX.length(), pathEnd);
    ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(
        modifierId.getNamespace(), collectiblePath
    );
    if (!BuiltInRegistries.ITEM.containsKey(itemId)) return;
    Item item = BuiltInRegistries.ITEM.get(itemId);
    callback.setReturnValue(
        Component.literal(" [")
            .append(Component.translatable(item.getDescriptionId()))
            .append("]")
            .withStyle(ChatFormatting.DARK_GRAY)
    );
  }

  private static List<CollectibleEffectDisplay.Entry> zinecraft_collectibleLines(
      LivingEntity entity
  ) {
    return CollectibleEffectDisplay.entries(entity);
  }
}
