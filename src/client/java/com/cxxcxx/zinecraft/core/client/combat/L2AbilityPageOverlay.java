package com.cxxcxx.zinecraft.core.client.combat;

import com.cxxcxx.zinecraft.api.combat.CombatService;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Adds Zinecraft's live Arknights values and formulas beside L2Tabs' attribute page.
 */
@EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT)
public final class L2AbilityPageOverlay {
  private static final String L2_ATTRIBUTE_SCREEN = "dev.xkmc.l2tabs.tabs.contents.AttributeScreen";
  private static final DecimalFormat NUMBER = new DecimalFormat("0.##");

  private L2AbilityPageOverlay() {
  }

  @SubscribeEvent
  public static void render(ScreenEvent.Render.Post event) {
    if (!event.getScreen().getClass().getName().equals(L2_ATTRIBUTE_SCREEN)) return;
    var minecraft = Minecraft.getInstance();
    var player = minecraft.player;
    if (player == null) return;

    int panelWidth = 188;
    int panelHeight = 166;
    int centerX = event.getScreen().width / 2;
    int x = centerX + 96;
    if (x + panelWidth > event.getScreen().width) x = centerX - 96 - panelWidth;
    int y = (event.getScreen().height - panelHeight) / 2;
    var graphics = event.getGuiGraphics();
    graphics.fill(x, y, x + panelWidth, y + panelHeight, 0xDD101820);
    graphics.fill(x + 1, y + 1, x + panelWidth - 1, y + panelHeight - 1, 0xDDDEE6EA);

    int textX = x + 8;
    int textY = y + 7;
    graphics.drawString(minecraft.font, Component.translatable("menu.zinecraft.ability.values"), textX, textY, 0x263238, false);
    textY += 13;
    for (Component line : valueLines(player)) {
      graphics.drawString(minecraft.font, line, textX, textY, 0x37474F, false);
      textY += 10;
    }
    textY += 3;
    graphics.drawString(minecraft.font, Component.translatable("menu.zinecraft.ability.formulas"), textX, textY, 0x263238, false);
    textY += 12;
    for (String key : List.of(
        "menu.zinecraft.ability.formula.attribute",
        "menu.zinecraft.ability.formula.physical",
        "menu.zinecraft.ability.formula.arts",
        "menu.zinecraft.ability.formula.speed"
    )) {
      for (var wrapped : minecraft.font.split(Component.translatable(key), panelWidth - 16)) {
        graphics.drawString(minecraft.font, wrapped, textX, textY, 0x455A64, false);
        textY += 9;
      }
    }
  }

  private static List<Component> valueLines(net.minecraft.client.player.LocalPlayer player) {
    double baseAttack = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
    double attackSpeed = CombatService.INSTANCE.attackSpeed(player, 100.0);
    int collectibles = CuriosApi.getCuriosInventory(player).map(handler -> handler.findCurios("relic").size()).orElse(0);
    return List.of(
        Component.translatable("menu.zinecraft.ability.max_health", NUMBER.format(player.getMaxHealth())),
        Component.translatable("menu.zinecraft.ability.attack", NUMBER.format(CombatService.INSTANCE.attack(player, baseAttack)), NUMBER.format(baseAttack)),
        Component.translatable("menu.zinecraft.ability.defense", NUMBER.format(player.getAttributeValue(Attributes.ARMOR))),
        Component.translatable("menu.zinecraft.ability.resistance", NUMBER.format(Math.clamp(player.getAttributeValue(Attributes.ARMOR_TOUGHNESS), 0.0, 100.0))),
        Component.translatable("menu.zinecraft.ability.attack_speed", NUMBER.format(attackSpeed)),
        Component.translatable("menu.zinecraft.ability.interval", NUMBER.format(100.0 / attackSpeed)),
        Component.translatable("menu.zinecraft.ability.collectibles", collectibles)
    );
  }
}
