package com.cxxcxx.zinecraft.core.client.weapon;

import com.cxxcxx.zinecraft.api.weapon.WeaponInput;
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionRequestPayload;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT)
public final class WeaponClientInput {
  private static final String CATEGORY = "key.categories.zinecraft.weapon";
  private static final KeyMapping RELOAD = new KeyMapping("key.zinecraft.weapon_reload", GLFW.GLFW_KEY_R, CATEGORY);
  private static final KeyMapping FIRE_SELECT = new KeyMapping("key.zinecraft.weapon_fire_select", GLFW.GLFW_KEY_B, CATEGORY);
  private static final KeyMapping INSPECT = new KeyMapping("key.zinecraft.weapon_inspect", GLFW.GLFW_KEY_X, CATEGORY);
  private static final KeyMapping MELEE = new KeyMapping("key.zinecraft.weapon_melee", GLFW.GLFW_KEY_V, CATEGORY);
  private static boolean useWasDown;
  private static boolean taczUseHeld;

  private WeaponClientInput() {
  }

  @SubscribeEvent
  public static void tick(ClientTickEvent.Post event) {
    Minecraft client = Minecraft.getInstance();
    while (RELOAD.consumeClick()) request(client, WeaponInput.RELOAD);
    while (FIRE_SELECT.consumeClick()) request(client, WeaponInput.FIRE_SELECT);
    while (INSPECT.consumeClick()) request(client, WeaponInput.INSPECT);
    while (MELEE.consumeClick()) request(client, WeaponInput.MELEE);
    boolean useDown = client.options.keyUse.isDown();
    if (!useWasDown && useDown) {
      taczUseHeld = client.screen == null && isHoldingTaczGun(client);
    } else if (useWasDown && !useDown) {
      // Do not consult the synchronized AIMING component here: a quick release can arrive before
      // the server's pressed-state update. The server validates both requests authoritatively.
      if (taczUseHeld && client.screen == null && isHoldingTaczGun(client)) {
        request(client, WeaponInput.SECONDARY);
      }
      taczUseHeld = false;
    }
    useWasDown = useDown;
  }

  private static boolean isHoldingTaczGun(Minecraft client) {
    return client.player != null
        && client.player.getMainHandItem().getItem() == ModTaczWeapons.INSTANCE.getGUN_ITEM().getItem();
  }

  public static boolean requestPrimary() {
    Minecraft client = Minecraft.getInstance();
    var player = client.player;
    if (player == null || Zinecraft.INSTANCE.getWEAPONS().definition(player.getMainHandItem()) == null) return false;
    if (client.hitResult != null && client.hitResult.getType() == HitResult.Type.BLOCK
        && player.getMainHandItem().getItem() instanceof SwordItem) return false;
    WeaponInput input = player.getMainHandItem().getItem() == ModTaczWeapons.INSTANCE.getGUN_ITEM().getItem()
        && player.getMainHandItem().getOrDefault(WeaponStateComponents.INSTANCE.getNEEDS_BOLT(), false)
        ? WeaponInput.BOLT : WeaponInput.PRIMARY;
    PacketDistributor.sendToServer(new WeaponActionRequestPayload(input));
    return true;
  }

  private static void request(Minecraft client, WeaponInput input) {
    var player = client.player;
    if (player == null) return;
    var definition = Zinecraft.INSTANCE.getWEAPONS().definition(player.getMainHandItem());
    if (definition == null || definition.action(input) == null) return;
    PacketDistributor.sendToServer(new WeaponActionRequestPayload(input));
  }

  @EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
  public static final class ModEvents {
    private ModEvents() {
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
      event.register(RELOAD);
      event.register(FIRE_SELECT);
      event.register(INSPECT);
      event.register(MELEE);
    }
  }
}
