package com.cxxcxx.zinecraft.core.client.weapon;

import com.cxxcxx.zinecraft.api.weapon.WeaponPresentation;
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionCancelledPayload;
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionStartedPayload;
import com.cxxcxx.zinecraft.api.weapon.network.WeaponPayloadTypes;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT)
public final class WeaponPresentationController {
  private static final Map<Key, ActivePresentation> ACTIVE = new HashMap<>();
  private static final WeaponPresentationVfxService VFX = WeaponVfxServices.create();
  private static final WeaponSoundService SOUNDS = VanillaWeaponSoundService.INSTANCE;
  private static final WeaponAnimationService WEAPON_ANIMATIONS = NoopWeaponAnimationService.INSTANCE;
  private static final PlayerAnimationService PLAYER_ANIMATIONS = VanillaPlayerAnimationService.INSTANCE;

  private WeaponPresentationController() {
  }

  public static void initialize() {
    WeaponPayloadTypes.setClientHandlers(WeaponPresentationController::start, WeaponPresentationController::cancel);
  }

  private static void start(WeaponActionStartedPayload payload) {
    var definition = Zinecraft.WEAPONS.definition(payload.getWeaponId());
    if (definition == null) return;
    var timeline = definition.presentation(payload.getActionId());
    if (timeline != null) ACTIVE.put(new Key(payload.getEntityId(), payload.getActionId()),
        new ActivePresentation(payload.getStartGameTick(), timeline));
  }

  private static void cancel(WeaponActionCancelledPayload payload) {
    var running = ACTIVE.remove(new Key(payload.getEntityId(), payload.getActionId()));
    if (running != null) stop(payload.getEntityId(), running);
  }

  @SubscribeEvent
  public static void tick(ClientTickEvent.Post event) {
    Minecraft client = Minecraft.getInstance();
    var level = client.level;
    if (level == null) return;
    Iterator<Map.Entry<Key, ActivePresentation>> iterator = ACTIVE.entrySet().iterator();
    while (iterator.hasNext()) {
      var entry = iterator.next();
      var running = entry.getValue();
      var entity = level.getEntity(entry.getKey().entityId()) instanceof LivingEntity living ? living : null;
      long elapsed = level.getGameTime() - running.startGameTick;
      if (elapsed >= running.timeline.getDurationTicks()) {
        if (entity != null) stop(entity, running);
        iterator.remove();
        continue;
      }
      if (entity == null || elapsed < 0) continue;
      if (!running.started) {
        ItemStack stack = entity.getMainHandItem();
        if (running.timeline.getWeaponAnimation() != null)
          WEAPON_ANIMATIONS.play(entity, stack, running.timeline.getWeaponAnimation());
        if (running.timeline.getPlayerAnimation() != null)
          PLAYER_ANIMATIONS.play(entity, running.timeline.getPlayerAnimation());
        running.stack = stack;
        running.started = true;
      }
      for (int i = 0; i < running.timeline.getVfx().size(); i++) {
        var cue = running.timeline.getVfx().get(i);
        if (!running.playedVfx[i] && elapsed >= cue.getTick()) {
          VFX.play(entity, cue.getId());
          running.playedVfx[i] = true;
        }
      }
      for (int i = 0; i < running.timeline.getSounds().size(); i++) {
        var cue = running.timeline.getSounds().get(i);
        if (!running.playedSounds[i] && elapsed >= cue.getTick()) {
          SOUNDS.play(entity, cue.getId());
          running.playedSounds[i] = true;
        }
      }
    }
  }

  @SubscribeEvent
  public static void logout(ClientPlayerNetworkEvent.LoggingOut event) {
    ACTIVE.clear();
  }

  private static void stop(int entityId, ActivePresentation running) {
    var level = Minecraft.getInstance().level;
    if (level != null && level.getEntity(entityId) instanceof LivingEntity living) stop(living, running);
  }

  private static void stop(LivingEntity entity, ActivePresentation running) {
    if (!running.started) return;
    ItemStack stack = running.stack == null ? entity.getMainHandItem() : running.stack;
    if (running.timeline.getWeaponAnimation() != null)
      WEAPON_ANIMATIONS.stop(entity, stack, running.timeline.getWeaponAnimation());
    if (running.timeline.getPlayerAnimation() != null)
      PLAYER_ANIMATIONS.stop(entity, running.timeline.getPlayerAnimation());
  }

  private record Key(int entityId, ResourceLocation actionId) {
  }

  private static final class ActivePresentation {
    private final long startGameTick;
    private final WeaponPresentation timeline;
    private final boolean[] playedVfx;
    private final boolean[] playedSounds;
    private boolean started;
    private ItemStack stack;

    private ActivePresentation(long startGameTick, WeaponPresentation timeline) {
      this.startGameTick = startGameTick;
      this.timeline = timeline;
      this.playedVfx = new boolean[timeline.getVfx().size()];
      this.playedSounds = new boolean[timeline.getSounds().size()];
    }
  }
}
