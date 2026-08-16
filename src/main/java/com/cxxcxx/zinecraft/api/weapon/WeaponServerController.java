package com.cxxcxx.zinecraft.api.weapon;

import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionCancelledPayload;
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionRequestPayload;
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionStartedPayload;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Server-authoritative execution and validation of weapon actions.
 */
public final class WeaponServerController {
  public static final WeaponServerController INSTANCE = new WeaponServerController();
  private final Map<UUID, ActiveAction> activeActions = new LinkedHashMap<>();

  private WeaponServerController() {
  }

  public static void requestWithDefaults(WeaponServerController self, ServerPlayer player, WeaponInput input, InteractionHand hand, int mask, Object marker) {
    self.request(player, input, (mask & 4) != 0 ? InteractionHand.MAIN_HAND : hand);
  }

  public void handleRequest(WeaponActionRequestPayload payload, IPayloadContext context) {
    if (context.player() instanceof ServerPlayer player) {
      request(player, payload.getInput(), InteractionHand.MAIN_HAND);
    }
  }

  public void onServerTick(ServerTickEvent.Post event) {
    tick(event.getServer());
  }

  public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
    activeActions.remove(event.getEntity().getUUID());
  }

  public void request(ServerPlayer player, WeaponInput input, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    WeaponDefinition definition = Zinecraft.INSTANCE.getWEAPONS().definition(stack);
    if (definition == null) return;
    ResourceLocation actionId = definition.action(input);
    if (actionId == null) return;
    var action = Zinecraft.INSTANCE.getWEAPONS().action(actionId);
    if (action == null) return;

    var weaponContext = new WeaponContext(player, stack, hand, definition);
    if (!action.canStart(weaponContext)) return;

    ActiveAction running = activeActions.get(player.getUUID());
    if (running != null) {
      if (!running.runtime().canInterrupt(input)) return;
      activeActions.remove(player.getUUID());
      broadcast(player, new WeaponActionCancelledPayload(player.getId(), running.actionId()));
    }

    player.resetAttackStrengthTicker();
    activeActions.put(player.getUUID(), new ActiveAction(
        definition, actionId, hand, stack, action.createRuntime(weaponContext)
    ));
    broadcast(player, new WeaponActionStartedPayload(
        player.getId(), definition.getId(), actionId, player.serverLevel().getGameTime()
    ));
  }

  private void tick(MinecraftServer server) {
    for (ServerPlayer player : server.getPlayerList().getPlayers()) {
      clearInactiveAiming(player);
    }
    var iterator = activeActions.entrySet().iterator();
    while (iterator.hasNext()) {
      var entry = iterator.next();
      var active = entry.getValue();
      var player = server.getPlayerList().getPlayer(entry.getKey());
      if (player != null && isStillValid(player, active)) {
        active.runtime().tick();
        if (active.runtime().getFinished()) iterator.remove();
      } else {
        if (player != null) {
          broadcast(player, new WeaponActionCancelledPayload(player.getId(), active.actionId()));
        }
        iterator.remove();
      }
    }
  }

  private void clearInactiveAiming(ServerPlayer player) {
    ItemStack held = player.isAlive() && !player.isSpectator() ? player.getMainHandItem() : ItemStack.EMPTY;
    var inventory = player.getInventory();
    for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
      ItemStack stack = inventory.getItem(slot);
      if (stack != held && stack.getOrDefault(WeaponStateComponents.INSTANCE.getAIMING(), false)) {
        stack.set(WeaponStateComponents.INSTANCE.getAIMING(), false);
      }
    }
  }

  private boolean isStillValid(ServerPlayer player, ActiveAction active) {
    if (!player.isAlive() || player.isSpectator() || player.getItemInHand(active.hand()) != active.stack())
      return false;
    var definition = Zinecraft.INSTANCE.getWEAPONS().definition(player.getItemInHand(active.hand()));
    return definition != null && definition.getId().equals(active.weapon().getId());
  }

  private void broadcast(ServerPlayer player, CustomPacketPayload payload) {
    PacketDistributor.sendToPlayersTrackingEntityAndSelf(player, payload);
  }

  private record ActiveAction(
      WeaponDefinition weapon,
      ResourceLocation actionId,
      InteractionHand hand,
      ItemStack stack,
      WeaponActionRuntime runtime
  ) {
  }
}
