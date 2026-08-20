package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.weapon.TimedWeaponSound;
import com.cxxcxx.zinecraft.api.weapon.TimedWeaponVfx;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 构建单个武器动作的客户端表现时间线。
 */
public final class WeaponPresentationBuilder {
  private final ResourceLocation actionId;
  @Nullable
  private ResourceLocation playerAnimation;
  @Nullable
  private ResourceLocation weaponAnimation;
  private List<TimedWeaponVfx> vfx = new ArrayList<>();
  private List<TimedWeaponSound> sounds = new ArrayList<>();
  private int durationTicks;
  private boolean built;

  public WeaponPresentationBuilder(ResourceLocation actionId) {
    this.actionId = Objects.requireNonNull(actionId, "表现对应的动作 ID 不能为空");
  }

  public WeaponPresentationBuilder duration(int ticks) {
    ensureMutable();
    this.durationTicks = ticks;
    return this;
  }

  public WeaponPresentationBuilder playerAnimation(AnimationBuilder animation) {
    ensureMutable();
    AnimationBuilder resource = Objects.requireNonNull(animation, "玩家动画 builder 不能为空：" + actionId);
    if (resource.target != AnimationBuilder.Target.PLAYER) {
      throw new IllegalArgumentException("玩家动画必须使用 PLAYER 类型：" + resource.resourceKey());
    }
    this.playerAnimation = resource.getId();
    return this;
  }

  public WeaponPresentationBuilder weaponAnimation(AnimationBuilder animation) {
    ensureMutable();
    AnimationBuilder resource = Objects.requireNonNull(animation, "武器动画 builder 不能为空：" + actionId);
    if (resource.target != AnimationBuilder.Target.WEAPON) {
      throw new IllegalArgumentException("武器动画必须使用 WEAPON 类型：" + resource.resourceKey());
    }
    this.weaponAnimation = resource.getId();
    return this;
  }

  public WeaponPresentationBuilder vfx(VfxBuilder effect, int tick) {
    ensureMutable();
    ResourceLocation id = Objects.requireNonNull(effect, "武器特效 builder 不能为空：" + actionId).getId();
    vfx.add(new TimedWeaponVfx(id, tick));
    return this;
  }

  public WeaponPresentationBuilder sound(SoundBuilder sound, int tick) {
    ensureMutable();
    ResourceLocation id = Objects.requireNonNull(sound, "武器声音 builder 不能为空：" + actionId).getId();
    sounds.add(new TimedWeaponSound(id, tick));
    return this;
  }

  public WeaponPresentationBuilder build() {
    ensureMutable();
    if (durationTicks <= 0) {
      throw new IllegalArgumentException("表现持续时间必须大于 0：" + actionId);
    }
    if (vfx.stream().anyMatch(cue -> !isOnTimeline(cue.getTick()))) {
      throw new IllegalArgumentException("特效时间必须位于表现时间线内：" + actionId);
    }
    if (sounds.stream().anyMatch(cue -> !isOnTimeline(cue.getTick()))) {
      throw new IllegalArgumentException("声音时间必须位于表现时间线内：" + actionId);
    }
    vfx = List.copyOf(vfx);
    sounds = List.copyOf(sounds);
    built = true;
    return this;
  }

  public ResourceLocation actionId() {
    requireBuilt();
    return actionId;
  }

  @Nullable
  public ResourceLocation playerAnimation() {
    requireBuilt();
    return playerAnimation;
  }

  @Nullable
  public ResourceLocation weaponAnimation() {
    requireBuilt();
    return weaponAnimation;
  }

  public List<TimedWeaponVfx> vfx() {
    requireBuilt();
    return vfx;
  }

  public List<TimedWeaponSound> sounds() {
    requireBuilt();
    return sounds;
  }

  public int durationTicks() {
    requireBuilt();
    return durationTicks;
  }

  private boolean isOnTimeline(int tick) {
    return tick >= 0 && tick < durationTicks;
  }

  private void ensureMutable() {
    if (built) {
      throw new IllegalStateException("武器表现 builder 不能重复 build 或在 build 后修改：" + actionId);
    }
  }

  private void requireBuilt() {
    if (!built) {
      throw new IllegalStateException("武器表现尚未 build：" + actionId);
    }
  }
}
