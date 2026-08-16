package com.cxxcxx.zinecraft.core.client.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks;
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunSpec;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.client.weapon.WeaponAnimationService;
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Client-side TaCZ animation playback. Gameplay remains owned by the server-side weapon runtime; this
 * service only turns approved presentation events into Bedrock bone transforms.
 */
public final class TaczWeaponAnimationService implements WeaponAnimationService {
  public static final TaczWeaponAnimationService INSTANCE = new TaczWeaponAnimationService();
  private static final long TRANSITION_NANOS = 80_000_000L;
  private static final Map<AnimationKey, Map<String, TaczAnimationClip>> CLIPS = new ConcurrentHashMap<>();
  private final Map<Integer, Playback> active = new ConcurrentHashMap<>();
  private ResourceLocation heldGun;
  private long heldSinceNanos;
  private boolean heldAiming;
  private long aimChangedNanos;

  private TaczWeaponAnimationService() {
  }

  static void clearCaches() {
    CLIPS.clear();
    INSTANCE.reset();
  }

  private static boolean sameGun(ItemStack stack, ResourceLocation gunId) {
    return stack.getItem() == ModTaczWeapons.INSTANCE.getGUN_ITEM().getItem()
        && gunId.equals(stack.get(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID()));
  }

  private static float secondsSince(long start, long now) {
    return Math.max(0, now - start) / 1_000_000_000.0f;
  }

  private static float transition(float seconds) {
    return Math.clamp(seconds / (TRANSITION_NANOS / 1_000_000_000.0f), 0.0f, 1.0f);
  }

  private static Map<String, TaczBoneTransform> sample(TaczAnimationClip clip, float seconds) {
    return clip == null ? new LinkedHashMap<>() : new LinkedHashMap<>(clip.sample(seconds));
  }

  private static Map<String, TaczBoneTransform> blend(Map<String, TaczBoneTransform> base,
                                                      Map<String, TaczBoneTransform> action, float weight) {
    if (weight <= 0) return base;
    Map<String, TaczBoneTransform> result = new LinkedHashMap<>(base);
    action.forEach((name, target) -> {
      TaczBoneTransform source = result.getOrDefault(name, TaczBoneTransform.IDENTITY);
      result.put(name, new TaczBoneTransform(
          lerp(source.position(), target.position(), weight),
          lerp(source.rotation(), target.rotation(), weight),
          lerp(source.scale(), target.scale(), weight)));
    });
    return result;
  }

  private static TaczVector lerp(TaczVector from, TaczVector to, float weight) {
    return from.multiply(1.0f - weight).add(to.multiply(weight));
  }

  private static String fireMode(TaczGunSpec gun, ItemStack stack) {
    int selected = stack.getOrDefault(WeaponStateComponents.INSTANCE.getFIRE_MODE(), 0);
    List<String> modes = gun.getFireModes();
    return modes.isEmpty() ? "semi" : modes.get(Math.floorMod(selected, modes.size())).toLowerCase(java.util.Locale.ROOT);
  }

  static String chooseClip(ResourceLocation animation, TaczGunSpec gun, ItemStack stack,
                           Map<String, TaczAnimationClip> clips) {
    int ammo = stack.getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), gun.getCapacity());
    boolean aiming = stack.getOrDefault(WeaponStateComponents.INSTANCE.getAIMING(), false);
    return chooseClip(animation, gun, stack, clips, ammo, aiming);
  }

  static String chooseClip(ResourceLocation animation, TaczGunSpec gun, ItemStack stack,
                           Map<String, TaczAnimationClip> clips, int initialAmmo, boolean initialAiming) {
    String action = animation.getPath();
    action = action.substring(action.lastIndexOf('/') + 1);
    return switch (action) {
      case "fire" -> first(clips, initialAmmo <= 1 ? "shoot_last" : null, "shoot", "shoot_semi");
      case "reload" -> {
        int currentAmmo = stack.getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), gun.getCapacity());
        if (gun.getReloadTimings().shellByShell()) {
          if (currentAmmo >= gun.getCapacity()) yield first(clips, "reload_end", "reload_loop");
          if (currentAmmo > initialAmmo) yield first(clips, "reload_loop", "reload_loop_2", "reload_intro");
          yield first(clips, initialAmmo <= 0 ? "reload_intro_empty" : "reload_intro",
              "reload_intro", "reload_intro_empty", "reload_loop");
        }
        yield first(clips, initialAmmo <= 0 ? "reload_empty" : "reload_tactical", "reload_empty",
            "reload_tactical", "reload_intro");
      }
      case "aim" -> first(clips, initialAiming ? "aim_start" : "aim_end", initialAiming ? "aim" : null,
          "aim_start", "aim_end");
      case "fire_select" ->
          first(clips, "switch_" + fireMode(gun, stack), "switch_semi", "switch_auto", "switch_burst");
      case "inspect" -> first(clips, initialAmmo <= 0 ? "inspect_empty" : "inspect", "inspect", "inspect_empty");
      case "melee" -> first(clips, "melee_bayonet_1", "melee_stock", "melee_push", "melee");
      case "bolt" -> first(clips, "bolt", "charge");
      default -> clips.containsKey(action) ? action : null;
    };
  }

  private static String first(Map<String, TaczAnimationClip> clips, String... names) {
    for (String name : names) if (name != null && clips.containsKey(name)) return name;
    return null;
  }

  private static Map<String, TaczAnimationClip> clips(TaczGunSpec gun) {
    AnimationKey key = new AnimationKey(gun.getAssets().getDefaultAnimationPath(), gun.getAssets().getAnimationPath());
    return CLIPS.computeIfAbsent(key, ignored -> {
      Map<String, TaczAnimationClip> result = new LinkedHashMap<>();
      load(key.defaultPath(), result);
      load(key.gunPath(), result);
      return Map.copyOf(result);
    });
  }

  private static void load(String path, Map<String, TaczAnimationClip> destination) {
    if (path == null) return;
    try (var input = TaczGunPacks.INSTANCE.getSnapshot().open(path)) {
      if (input != null) destination.putAll(TaczBedrockParser.animations(input));
    } catch (Exception exception) {
      Zinecraft.INSTANCE.getLogger().warn("Failed to load TaCZ Bedrock animation {}", path, exception);
    }
  }

  @Override
  public void play(@NotNull LivingEntity entity, @NotNull ItemStack stack, @NotNull ResourceLocation animation) {
    ResourceLocation gunId = stack.get(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID());
    if (gunId == null || stack.getItem() != ModTaczWeapons.INSTANCE.getGUN_ITEM().getItem()) return;
    active.put(entity.getId(), new Playback(gunId, animation, System.nanoTime(),
        stack.getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), 0),
        stack.getOrDefault(WeaponStateComponents.INSTANCE.getAIMING(), false)));
  }

  @Override
  public void stop(@NotNull LivingEntity entity, @NotNull ItemStack stack, @NotNull ResourceLocation animation) {
    active.computeIfPresent(entity.getId(), (ignored, playback) -> {
      if (!playback.animation().equals(animation)) return playback;
      return playback.withStoppedAt(System.nanoTime());
    });
  }

  public void reset() {
    active.clear();
    heldGun = null;
    heldSinceNanos = 0;
    heldAiming = false;
    aimChangedNanos = 0;
  }

  Map<String, TaczBoneTransform> sample(ItemStack stack) {
    ResourceLocation gunId = stack.get(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID());
    TaczGunSpec gun = gunId == null ? null : TaczGunPacks.INSTANCE.gun(gunId);
    if (gun == null) return Map.of();
    Map<String, TaczAnimationClip> clips = clips(gun);
    if (clips.isEmpty()) return Map.of();

    long now = System.nanoTime();
    if (!Objects.equals(heldGun, gunId)) {
      heldGun = gunId;
      heldSinceNanos = now;
      heldAiming = stack.getOrDefault(WeaponStateComponents.INSTANCE.getAIMING(), false);
      aimChangedNanos = now;
    }

    Map<String, TaczBoneTransform> base = sample(clips.get("static_idle"), secondsSince(heldSinceNanos, now));
    String fireMode = fireMode(gun, stack);
    TaczAnimationClip modeClip = clips.get("static_" + fireMode);
    if (modeClip != null) base.putAll(modeClip.sample(secondsSince(heldSinceNanos, now)));
    boolean aiming = stack.getOrDefault(WeaponStateComponents.INSTANCE.getAIMING(), false);
    if (heldAiming != aiming) {
      heldAiming = aiming;
      aimChangedNanos = now;
    }
    if (aiming) {
      TaczAnimationClip aim = clips.get("aim");
      if (aim != null) {
        float aimSeconds = Math.max(gun.getAimTicks(), 1) / 20.0f;
        float progress = Math.clamp(secondsSince(aimChangedNanos, now) / aimSeconds, 0.0f, 1.0f);
        base = blend(base, aim.sample(aim.length() * progress), progress);
      }
    }

    Minecraft client = Minecraft.getInstance();
    if (client.player == null || !sameGun(client.player.getMainHandItem(), gunId)) return base;
    Playback playback = active.get(client.player.getId());
    if (playback == null || !playback.gunId().equals(gunId)) {
      TaczAnimationClip draw = clips.get("draw");
      float drawTime = secondsSince(heldSinceNanos, now);
      if (draw != null && drawTime < draw.length()) return blend(base, draw.sample(drawTime), transition(drawTime));
      return base;
    }

    String clipName = chooseClip(playback.animation(), gun, stack, clips, playback.initialAmmo(), playback.initialAiming());
    TaczAnimationClip action = clipName == null ? null : clips.get(clipName);
    if (action == null) return base;
    float elapsed = secondsSince(playback.startedAtNanos(), now);
    if (gun.getReloadTimings().shellByShell() && playback.animation().getPath().endsWith("/reload")) {
      int currentAmmo = stack.getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), gun.getCapacity());
      if ("reload_loop".equals(clipName) || "reload_loop_2".equals(clipName)) {
        if (action.length() > 0) elapsed %= action.length();
      } else if ("reload_end".equals(clipName)) {
        int needed = Math.max(gun.getCapacity() - playback.initialAmmo(), 1);
        float endingStart = (gun.getReloadTimings().durationTicks(playback.initialAmmo() <= 0, needed)
            - gun.getReloadTimings().endingTicks() - 1) / 20.0f;
        elapsed = Math.max(0, elapsed - endingStart);
      }
    }
    float weight = transition(elapsed);
    if (!action.loop() && action.length() > 0 && elapsed > action.length()) {
      weight *= 1.0f - Math.clamp((elapsed - action.length())
          / (TRANSITION_NANOS / 1_000_000_000.0f), 0.0f, 1.0f);
      if (weight <= 0.0f) {
        active.remove(client.player.getId(), playback);
        return base;
      }
    }
    if (playback.stoppedAtNanos() != 0) {
      float fade = secondsSince(playback.stoppedAtNanos(), now);
      weight *= 1.0f - Math.clamp(fade / (TRANSITION_NANOS / 1_000_000_000.0f), 0.0f, 1.0f);
      if (weight <= 0.0f) {
        active.remove(client.player.getId(), playback);
        return base;
      }
    }
    return blend(base, action.sample(elapsed), weight);
  }

  private record AnimationKey(String defaultPath, String gunPath) {
  }

  private record Playback(ResourceLocation gunId, ResourceLocation animation, long startedAtNanos,
                          int initialAmmo, boolean initialAiming, long stoppedAtNanos) {
    private Playback(ResourceLocation gunId, ResourceLocation animation, long startedAtNanos,
                     int initialAmmo, boolean initialAiming) {
      this(gunId, animation, startedAtNanos, initialAmmo, initialAiming, 0);
    }

    private Playback withStoppedAt(long value) {
      return new Playback(gunId, animation, startedAtNanos, initialAmmo, initialAiming, value);
    }
  }
}
