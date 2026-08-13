package com.cxxcxx.zinecraft.core.client.ponder

import com.cxxcxx.zinecraft.api.skill.SkillDefinition
import com.cxxcxx.zinecraft.api.skill.SkillDemoTheme
import com.cxxcxx.zinecraft.core.Zinecraft
import net.createmod.ponder.api.PonderPalette
import net.createmod.ponder.api.registration.PonderPlugin
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper
import net.createmod.ponder.api.scene.SceneBuilder
import net.createmod.ponder.api.scene.SceneBuildingUtil
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.Vec3

/** 将技能物品注册为 Ponder 组件，并根据技能主题生成教学动画。 */
object ZinecraftPonderPlugin : PonderPlugin {
  override fun getModId(): String = Zinecraft.MOD_ID

  override fun registerScenes(helper: PonderSceneRegistrationHelper<ResourceLocation>) {
    Zinecraft.SKILLS.entries.forEach { entry ->
      helper.addStoryBoard(
        Zinecraft.REGISTRAR.id(entry.definition.path),
        "skill_demo/training_ground",
        { scene, util -> buildSkillScene(scene, util, entry.definition) }
      )
    }
  }

  private fun buildSkillScene(
    scene: SceneBuilder,
    util: SceneBuildingUtil,
    skill: SkillDefinition
  ) {
    scene.title("skill_demo_${skill.path}", "${skill.operatorEnUs}: ${skill.enUs}")
    scene.configureBasePlate(0, 0, 7)
    scene.showBasePlate()
    scene.idle(10)
    scene.world().showSection(util.select().layersFrom(1), Direction.DOWN)
    scene.idle(15)

    val center = util.vector().topOf(3, 1, 3)
    scene.overlay().showText(45)
      .colored(PonderPalette.WHITE)
      .text("A ${skill.profession.enUs} skill used by ${skill.operatorEnUs}")
      .pointAt(center)
    scene.idle(50)
    scene.overlay().showText(40)
      .colored(PonderPalette.BLUE)
      .text("${skill.recoveryEnUs} · ${skill.triggerEnUs}")
      .pointAt(center)
    scene.idle(45)
    scene.overlay().showText(45)
      .colored(PonderPalette.OUTPUT)
      .text(statsText(skill))
      .pointAt(center)
    scene.idle(50)

    animateTheme(scene, util, skill.theme)

    scene.overlay().showText(80)
      .colored(PonderPalette.WHITE)
      .text(skill.descriptionEnUs)
      .independent(8)
    scene.idle(85)
    scene.overlay().showText(45)
      .colored(PonderPalette.INPUT)
      .text("This is a Minecraft interpretation based on PRTS skill data.")
      .independent(8)
    scene.idle(50)
    scene.markAsFinished()
  }

  private fun statsText(skill: SkillDefinition): String {
    val duration = skill.durationSeconds?.let { " · Duration ${it}s" }.orEmpty()
    return "Initial ${skill.initialSp} · Cost ${skill.spCost}$duration"
  }

  private fun animateTheme(scene: SceneBuilder, util: SceneBuildingUtil, theme: SkillDemoTheme) {
    val center = util.vector().centerOf(3, 2, 3)
    val enemies = util.select().fromTo(1, 1, 1, 5, 1, 5)
      .substract(util.select().position(3, 1, 3))
    when (theme) {
      SkillDemoTheme.COST_RECOVERY -> {
        repeat(5) { index ->
          scene.world().createItemEntity(
            util.vector().centerOf(1 + index, 2, 3),
            Vec3(0.0, 0.08, 0.0),
            ItemStack(Items.EMERALD)
          )
          scene.effects().indicateSuccess(util.grid().at(3, 1, 3))
          scene.idle(6)
        }
      }

      SkillDemoTheme.AREA_SLASH -> {
        scene.overlay().showOutline(PonderPalette.RED, Any(), enemies, 45)
        val emitter = scene.effects().simpleParticleEmitter(ParticleTypes.SWEEP_ATTACK, Vec3.ZERO)
        scene.effects().emitParticles(center, emitter, 4f, 12)
        scene.rotateCameraY(90f)
        scene.idle(45)
      }

      SkillDemoTheme.RAPID_FIRE -> {
        repeat(5) { index ->
          scene.world()
            .createItemEntity(center.add(0.0, 0.2, 0.0), Vec3(0.12 * (index - 2), 0.05, -0.18), ItemStack(Items.ARROW))
          scene.effects().createRedstoneParticles(util.grid().at(3, 1, 3), 0xFFE070, 8)
          scene.idle(4)
        }
        scene.idle(25)
      }

      SkillDemoTheme.VOLCANIC_BURST -> {
        val emitter = scene.effects().simpleParticleEmitter(ParticleTypes.LAVA, Vec3(0.0, 0.12, 0.0))
        scene.effects().emitParticles(center, emitter, 3f, 25)
        scene.world().replaceBlocks(enemies, Blocks.MAGMA_BLOCK.defaultBlockState(), true)
        scene.idle(45)
        scene.world().restoreBlocks(enemies)
      }

      SkillDemoTheme.HEAL_AND_SLOW -> {
        val allies = util.select().fromTo(2, 1, 2, 4, 1, 4)
        scene.overlay().showOutline(PonderPalette.GREEN, Any(), allies, 50)
        scene.overlay().showOutline(PonderPalette.SLOW, Any(), enemies, 50)
        val emitter = scene.effects().simpleParticleEmitter(ParticleTypes.HEART, Vec3(0.0, 0.08, 0.0))
        scene.effects().emitParticles(center, emitter, 1f, 30)
        scene.idle(55)
      }

      SkillDemoTheme.SANCTUARY -> {
        scene.overlay().showOutline(PonderPalette.BLUE, Any(), util.select().fromTo(1, 1, 1, 5, 3, 5), 55)
        val emitter = scene.effects().simpleParticleEmitter(ParticleTypes.TOTEM_OF_UNDYING, Vec3(0.0, 0.08, 0.0))
        scene.effects().emitParticles(center, emitter, 2f, 35)
        scene.idle(60)
      }

      SkillDemoTheme.SLOWING_FIELD -> {
        scene.overlay().showOutline(PonderPalette.SLOW, Any(), enemies, 55)
        val emitter = scene.effects().simpleParticleEmitter(ParticleTypes.SOUL_FIRE_FLAME, Vec3(0.0, 0.03, 0.0))
        scene.effects().emitParticles(center, emitter, 2f, 35)
        scene.rotateCameraY(-90f)
        scene.idle(60)
      }

      SkillDemoTheme.DEPLOYMENT_STUN -> {
        scene.world().setBlock(util.grid().at(3, 2, 3), Blocks.REDSTONE_BLOCK.defaultBlockState(), true)
        scene.overlay().showOutline(PonderPalette.RED, Any(), enemies, 45)
        val emitter = scene.effects().simpleParticleEmitter(ParticleTypes.EXPLOSION, Vec3.ZERO)
        scene.effects().emitParticles(center, emitter, 4f, 4)
        scene.idle(20)
        repeat(3) {
          scene.effects().indicateRedstone(util.grid().at(3, 2, 3))
          scene.idle(10)
        }
      }
    }
  }
}
