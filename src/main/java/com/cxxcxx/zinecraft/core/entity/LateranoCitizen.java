package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.nation.NationAffiliated;
import com.cxxcxx.zinecraft.api.nation.TerraNation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LateranoCitizen extends PathfinderMob implements NationAffiliated {
  @NotNull
  public static final LateranoCitizen.Access ACCESS = new LateranoCitizen.Access();
  @NotNull
  private final TerraNation nation;

  public LateranoCitizen(@NotNull EntityType<? extends LateranoCitizen> type, @NotNull Level level) {
    super(type, level);
    this.nation = TerraNation.LATERANO;
  }

  @NotNull
  @Override
  public TerraNation getNation() {
    return this.nation;
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
    EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
    RandomSource randomSource = level.getRandom();
    this.setItemSlot(equipmentSlot, LateranoLoadout.createGun(randomSource));
    this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    return spawnGroupData;
  }

  public static final class Access {
    private Access() {
    }

    @NotNull
    public final Builder attributes() {
      Builder builder = Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.FOLLOW_RANGE, 16.0);
      return builder;
    }

    public final boolean canSpawn(
        @NotNull EntityType<LateranoCitizen> type,
        @NotNull ServerLevelAccessor level,
        @NotNull MobSpawnType reason,
        @NotNull BlockPos pos,
        @NotNull RandomSource random
    ) {
      return Mob.checkMobSpawnRules(type, (LevelAccessor) level, reason, pos, random);
    }
  }
}
