package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.nation.NationAffiliated;
import com.cxxcxx.zinecraft.api.nation.TerraNation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NationResident extends PathfinderMob implements NationAffiliated {
  @NotNull
  public static final NationResident.Access ACCESS = new NationResident.Access();
  @NotNull
  private final NationResidentProfile profile;

  public NationResident(@NotNull EntityType<? extends NationResident> type, @NotNull Level level, @NotNull NationResidentProfile profile) {
    super(type, level);
    this.profile = profile;
  }

  @NotNull
  public NationResidentProfile getProfile() {
    return this.profile;
  }

  @NotNull
  @Override
  public TerraNation getNation() {
    return this.profile.getNation();
  }

  protected void registerGoals() {
    this.goalSelector.addGoal(0, (Goal) (new FloatGoal((Mob) this)));
    this.goalSelector.addGoal(5, (Goal) (new WaterAvoidingRandomStrollGoal(this, 0.8)));
    this.goalSelector.addGoal(6, (Goal) (new LookAtPlayerGoal((Mob) this, Player.class, 8.0F)));
    this.goalSelector.addGoal(7, (Goal) (new RandomLookAroundGoal((Mob) this)));
  }

  @Nullable
  public SpawnGroupData finalizeSpawn(
      @NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData
  ) {
    SpawnGroupData spawnGroupData = super.finalizeSpawn(level, difficulty, reason, spawnData);
    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike) this.profile.getHeldItem()));
    this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    if (this.profile.getAquatic()) {
      this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, -1, 0, false, false));
    }

    return spawnGroupData;
  }

  public static final class Access {
    private Access() {
    }

    @NotNull
    public Builder attributes() {
      Builder builder = Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.FOLLOW_RANGE, 16.0);
      return builder;
    }

    public boolean canSpawn(
        @NotNull EntityType<NationResident> type,
        @NotNull ServerLevelAccessor level,
        @NotNull MobSpawnType reason,
        @NotNull BlockPos pos,
        @NotNull RandomSource random
    ) {
      return Mob.checkMobSpawnRules(type, (LevelAccessor) level, reason, pos, random);
    }
  }
}
