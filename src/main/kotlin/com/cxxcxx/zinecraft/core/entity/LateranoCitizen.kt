package com.cxxcxx.zinecraft.core.entity

import com.cxxcxx.zinecraft.api.nation.NationAffiliated
import com.cxxcxx.zinecraft.api.nation.TerraNation
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.SpawnGroupData
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.FloatGoal
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.ServerLevelAccessor

/**
 * 拉特兰公民的和平人形表达。
 *
 * 枪械在服务端 finalizeSpawn 阶段写入主手并持久化；客户端只负责渲染装备与对应持枪动作。
 */
class LateranoCitizen(type: EntityType<out LateranoCitizen>, level: Level) :
  PathfinderMob(type, level), NationAffiliated {
  override val nation: TerraNation = TerraNation.LATERANO

  override fun registerGoals() {
    goalSelector.addGoal(0, FloatGoal(this))
    goalSelector.addGoal(5, WaterAvoidingRandomStrollGoal(this, 0.8))
    goalSelector.addGoal(6, LookAtPlayerGoal(this, Player::class.java, 8.0f))
    goalSelector.addGoal(7, RandomLookAroundGoal(this))
  }

  override fun finalizeSpawn(
    level: ServerLevelAccessor,
    difficulty: DifficultyInstance,
    reason: MobSpawnType,
    spawnData: SpawnGroupData?
  ): SpawnGroupData? {
    val result = super.finalizeSpawn(level, difficulty, reason, spawnData)
    setItemSlot(EquipmentSlot.MAINHAND, LateranoLoadout.createGun(level.getRandom()))
    setDropChance(EquipmentSlot.MAINHAND, 0.0f)
    return result
  }

  companion object {
    fun attributes(): AttributeSupplier.Builder = Mob.createMobAttributes()
      .add(Attributes.MAX_HEALTH, 20.0)
      .add(Attributes.MOVEMENT_SPEED, 0.25)
      .add(Attributes.FOLLOW_RANGE, 16.0)

    fun canSpawn(
      type: EntityType<LateranoCitizen>,
      level: ServerLevelAccessor,
      reason: MobSpawnType,
      pos: BlockPos,
      random: RandomSource
    ): Boolean = Mob.checkMobSpawnRules(type, level, reason, pos, random)
  }
}
