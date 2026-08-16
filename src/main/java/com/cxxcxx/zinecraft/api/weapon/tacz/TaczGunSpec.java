package com.cxxcxx.zinecraft.api.weapon.tacz;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class TaczGunSpec {
  @NotNull
  private final ResourceLocation id;
  @NotNull
  private final ResourceLocation runtimeId;
  @NotNull
  private final String translationKey;
  @Nullable
  private final String tooltipKey;
  @NotNull
  private final String type;
  private final int sort;
  @NotNull
  private final ResourceLocation ammoId;
  private final int capacity;
  @NotNull
  private final String bolt;
  private final int rpm;
  private final int burstCount;
  private final int burstRpm;
  private final float damage;
  private final int projectileCount;
  private final double range;
  private final int reloadFeedTicks;
  private final int reloadDurationTicks;
  @NotNull
  private final TaczReloadTimings reloadTimings;
  private final int drawTicks;
  private final int aimTicks;
  private final int putAwayTicks;
  private final int boltActionTicks;
  @NotNull
  private final String feedType;
  private final float meleeDamage;
  private final double meleeDistance;
  private final int meleeCooldownTicks;
  @NotNull
  private final List<String> fireModes;
  @NotNull
  private final TaczGunAssets assets;
  @NotNull
  private final TaczPackInfo pack;

  public TaczGunSpec(
      @NotNull ResourceLocation id,
      @NotNull ResourceLocation runtimeId,
      @NotNull String translationKey,
      @Nullable String tooltipKey,
      @NotNull String type,
      int sort,
      @NotNull ResourceLocation ammoId,
      int capacity,
      @NotNull String bolt,
      int rpm,
      int burstCount,
      int burstRpm,
      float damage,
      int projectileCount,
      double range,
      int reloadFeedTicks,
      int reloadDurationTicks,
      @NotNull TaczReloadTimings reloadTimings,
      int drawTicks,
      int aimTicks,
      int putAwayTicks,
      int boltActionTicks,
      @NotNull String feedType,
      float meleeDamage,
      double meleeDistance,
      int meleeCooldownTicks,
      @NotNull List<String> fireModes,
      @NotNull TaczGunAssets assets,
      @NotNull TaczPackInfo pack
  ) {
    super();
    this.id = id;
    this.runtimeId = runtimeId;
    this.translationKey = translationKey;
    this.tooltipKey = tooltipKey;
    this.type = type;
    this.sort = sort;
    this.ammoId = ammoId;
    this.capacity = capacity;
    this.bolt = bolt;
    this.rpm = rpm;
    this.burstCount = burstCount;
    this.burstRpm = burstRpm;
    this.damage = damage;
    this.projectileCount = projectileCount;
    this.range = range;
    this.reloadFeedTicks = reloadFeedTicks;
    this.reloadDurationTicks = reloadDurationTicks;
    this.reloadTimings = reloadTimings;
    this.drawTicks = drawTicks;
    this.aimTicks = aimTicks;
    this.putAwayTicks = putAwayTicks;
    this.boltActionTicks = boltActionTicks;
    this.feedType = feedType;
    this.meleeDamage = meleeDamage;
    this.meleeDistance = meleeDistance;
    this.meleeCooldownTicks = meleeCooldownTicks;
    this.fireModes = fireModes;
    this.assets = assets;
    this.pack = pack;
  }

  @NotNull
  public final ResourceLocation getId() {
    return this.id;
  }

  @NotNull
  public final ResourceLocation getRuntimeId() {
    return this.runtimeId;
  }

  @NotNull
  public final String getTranslationKey() {
    return this.translationKey;
  }

  @Nullable
  public final String getTooltipKey() {
    return this.tooltipKey;
  }

  @NotNull
  public final String getType() {
    return this.type;
  }

  public final int getSort() {
    return this.sort;
  }

  @NotNull
  public final ResourceLocation getAmmoId() {
    return this.ammoId;
  }

  public final int getCapacity() {
    return this.capacity;
  }

  @NotNull
  public final String getBolt() {
    return this.bolt;
  }

  public final int getRpm() {
    return this.rpm;
  }

  public final int getBurstCount() {
    return this.burstCount;
  }

  public final int getBurstRpm() {
    return this.burstRpm;
  }

  public final float getDamage() {
    return this.damage;
  }

  public final int getProjectileCount() {
    return this.projectileCount;
  }

  public final double getRange() {
    return this.range;
  }

  public final int getReloadFeedTicks() {
    return this.reloadFeedTicks;
  }

  public final int getReloadDurationTicks() {
    return this.reloadDurationTicks;
  }

  @NotNull
  public final TaczReloadTimings getReloadTimings() {
    return reloadTimings;
  }

  public final int getDrawTicks() {
    return this.drawTicks;
  }

  public final int getAimTicks() {
    return this.aimTicks;
  }

  public final int getPutAwayTicks() {
    return this.putAwayTicks;
  }

  public final int getBoltActionTicks() {
    return this.boltActionTicks;
  }

  @NotNull
  public final String getFeedType() {
    return this.feedType;
  }

  public final float getMeleeDamage() {
    return this.meleeDamage;
  }

  public final double getMeleeDistance() {
    return this.meleeDistance;
  }

  public final int getMeleeCooldownTicks() {
    return this.meleeCooldownTicks;
  }

  @NotNull
  public final List<String> getFireModes() {
    return this.fireModes;
  }

  @NotNull
  public final TaczGunAssets getAssets() {
    return this.assets;
  }

  @NotNull
  public final TaczPackInfo getPack() {
    return this.pack;
  }

  @Override
  public int hashCode() {
    int i = this.id.hashCode();
    i = i * 31 + this.runtimeId.hashCode();
    i = i * 31 + this.translationKey.hashCode();
    i = i * 31 + (this.tooltipKey == null ? 0 : this.tooltipKey.hashCode());
    i = i * 31 + this.type.hashCode();
    i = i * 31 + Integer.hashCode(this.sort);
    i = i * 31 + this.ammoId.hashCode();
    i = i * 31 + Integer.hashCode(this.capacity);
    i = i * 31 + this.bolt.hashCode();
    i = i * 31 + Integer.hashCode(this.rpm);
    i = i * 31 + Integer.hashCode(this.burstCount);
    i = i * 31 + Integer.hashCode(this.burstRpm);
    i = i * 31 + Float.hashCode(this.damage);
    i = i * 31 + Integer.hashCode(this.projectileCount);
    i = i * 31 + Double.hashCode(this.range);
    i = i * 31 + Integer.hashCode(this.reloadFeedTicks);
    i = i * 31 + Integer.hashCode(this.reloadDurationTicks);
    i = i * 31 + this.reloadTimings.hashCode();
    i = i * 31 + Integer.hashCode(this.drawTicks);
    i = i * 31 + Integer.hashCode(this.aimTicks);
    i = i * 31 + Integer.hashCode(this.putAwayTicks);
    i = i * 31 + Integer.hashCode(this.boltActionTicks);
    i = i * 31 + this.feedType.hashCode();
    i = i * 31 + Float.hashCode(this.meleeDamage);
    i = i * 31 + Double.hashCode(this.meleeDistance);
    i = i * 31 + Integer.hashCode(this.meleeCooldownTicks);
    i = i * 31 + this.fireModes.hashCode();
    i = i * 31 + this.assets.hashCode();
    return i * 31 + this.pack.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof TaczGunSpec taczGunSpec)) {
      return false;
    } else if (!java.util.Objects.equals(this.id, taczGunSpec.id)) {
      return false;
    } else if (!java.util.Objects.equals(this.runtimeId, taczGunSpec.runtimeId)) {
      return false;
    } else if (!java.util.Objects.equals(this.translationKey, taczGunSpec.translationKey)) {
      return false;
    } else if (!java.util.Objects.equals(this.tooltipKey, taczGunSpec.tooltipKey)) {
      return false;
    } else if (!java.util.Objects.equals(this.type, taczGunSpec.type)) {
      return false;
    } else if (this.sort != taczGunSpec.sort) {
      return false;
    } else if (!java.util.Objects.equals(this.ammoId, taczGunSpec.ammoId)) {
      return false;
    } else if (this.capacity != taczGunSpec.capacity) {
      return false;
    } else if (!java.util.Objects.equals(this.bolt, taczGunSpec.bolt)) {
      return false;
    } else if (this.rpm != taczGunSpec.rpm) {
      return false;
    } else if (this.burstCount != taczGunSpec.burstCount) {
      return false;
    } else if (this.burstRpm != taczGunSpec.burstRpm) {
      return false;
    } else if (Float.compare(this.damage, taczGunSpec.damage) != 0) {
      return false;
    } else if (this.projectileCount != taczGunSpec.projectileCount) {
      return false;
    } else if (Double.compare(this.range, taczGunSpec.range) != 0) {
      return false;
    } else if (this.reloadFeedTicks != taczGunSpec.reloadFeedTicks) {
      return false;
    } else if (this.reloadDurationTicks != taczGunSpec.reloadDurationTicks) {
      return false;
    } else if (!java.util.Objects.equals(this.reloadTimings, taczGunSpec.reloadTimings)) {
      return false;
    } else if (this.drawTicks != taczGunSpec.drawTicks) {
      return false;
    } else if (this.aimTicks != taczGunSpec.aimTicks) {
      return false;
    } else if (this.putAwayTicks != taczGunSpec.putAwayTicks) {
      return false;
    } else if (this.boltActionTicks != taczGunSpec.boltActionTicks) {
      return false;
    } else if (!java.util.Objects.equals(this.feedType, taczGunSpec.feedType)) {
      return false;
    } else if (Float.compare(this.meleeDamage, taczGunSpec.meleeDamage) != 0) {
      return false;
    } else if (Double.compare(this.meleeDistance, taczGunSpec.meleeDistance) != 0) {
      return false;
    } else if (this.meleeCooldownTicks != taczGunSpec.meleeCooldownTicks) {
      return false;
    } else if (!java.util.Objects.equals(this.fireModes, taczGunSpec.fireModes)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.assets, taczGunSpec.assets) ? false : java.util.Objects.equals(this.pack, taczGunSpec.pack);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "TaczGunSpec(id="
        + this.id
        + ", runtimeId="
        + this.runtimeId
        + ", translationKey="
        + this.translationKey
        + ", tooltipKey="
        + this.tooltipKey
        + ", type="
        + this.type
        + ", sort="
        + this.sort
        + ", ammoId="
        + this.ammoId
        + ", capacity="
        + this.capacity
        + ", bolt="
        + this.bolt
        + ", rpm="
        + this.rpm
        + ", burstCount="
        + this.burstCount
        + ", burstRpm="
        + this.burstRpm
        + ", damage="
        + this.damage
        + ", projectileCount="
        + this.projectileCount
        + ", range="
        + this.range
        + ", reloadFeedTicks="
        + this.reloadFeedTicks
        + ", reloadDurationTicks="
        + this.reloadDurationTicks
        + ", reloadTimings="
        + this.reloadTimings
        + ", drawTicks="
        + this.drawTicks
        + ", aimTicks="
        + this.aimTicks
        + ", putAwayTicks="
        + this.putAwayTicks
        + ", boltActionTicks="
        + this.boltActionTicks
        + ", feedType="
        + this.feedType
        + ", meleeDamage="
        + this.meleeDamage
        + ", meleeDistance="
        + this.meleeDistance
        + ", meleeCooldownTicks="
        + this.meleeCooldownTicks
        + ", fireModes="
        + this.fireModes
        + ", assets="
        + this.assets
        + ", pack="
        + this.pack
        + ")";
  }
}
