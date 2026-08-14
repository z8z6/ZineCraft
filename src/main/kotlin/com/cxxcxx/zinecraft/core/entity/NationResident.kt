package com.cxxcxx.zinecraft.core.entity

import com.cxxcxx.zinecraft.api.nation.NationAffiliated
import com.cxxcxx.zinecraft.api.nation.TerraNation
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
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
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.ServerLevelAccessor

data class NationResidentProfile(
  override val nation: TerraNation,
  val heldItem: Item,
  val aquatic: Boolean = false
) : NationAffiliated

/** 国家聚落中的和平居民；所属国家由实体类型固定，不依赖客户端外观判断。 */
class NationResident(
  type: EntityType<out NationResident>,
  level: Level,
  val profile: NationResidentProfile
) : PathfinderMob(type, level), NationAffiliated {
  override val nation: TerraNation
    get() = profile.nation

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
    setItemSlot(EquipmentSlot.MAINHAND, ItemStack(profile.heldItem))
    setDropChance(EquipmentSlot.MAINHAND, 0.0f)
    if (profile.aquatic) {
      addEffect(MobEffectInstance(MobEffects.WATER_BREATHING, MobEffectInstance.INFINITE_DURATION, 0, false, false))
    }
    return result
  }

  companion object {
    fun attributes(): AttributeSupplier.Builder = Mob.createMobAttributes()
      .add(Attributes.MAX_HEALTH, 20.0)
      .add(Attributes.MOVEMENT_SPEED, 0.25)
      .add(Attributes.FOLLOW_RANGE, 16.0)

    fun canSpawn(
      type: EntityType<NationResident>,
      level: ServerLevelAccessor,
      reason: MobSpawnType,
      pos: BlockPos,
      random: RandomSource
    ): Boolean = Mob.checkMobSpawnRules(type, level, reason, pos, random)
  }
}
