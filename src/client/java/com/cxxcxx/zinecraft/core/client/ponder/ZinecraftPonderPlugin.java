package com.cxxcxx.zinecraft.core.client.ponder;

import com.cxxcxx.zinecraft.api.skill.SkillDefinition;
import com.cxxcxx.zinecraft.api.skill.SkillDemoTheme;
import com.cxxcxx.zinecraft.api.skill.SkillEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.createmod.ponder.api.ParticleEmitter;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class ZinecraftPonderPlugin implements PonderPlugin {
  private static final String TRAINING_GROUND_SCENE = "skill_demo/training_ground";
  private static final int CENTER_X = 3;
  private static final int CENTER_Y = 2;
  private static final int CENTER_Z = 3;

  public static final ZinecraftPonderPlugin INSTANCE = new ZinecraftPonderPlugin();

  private ZinecraftPonderPlugin() {
  }

  private static void buildSkillScene(
      SceneBuilder scene,
      SceneBuildingUtil util,
      SkillDefinition skill
  ) {
    scene.title("skill_demo_" + skill.getPath(), skill.getOperatorEnUs() + ": " + skill.getEnUs());
    scene.configureBasePlate(0, 0, 7);
    scene.showBasePlate();
    scene.idle(10);
    scene.world().showSection(util.select().layersFrom(1), Direction.DOWN);
    scene.idle(15);

    Vec3 textAnchor = util.vector().topOf(CENTER_X, 1, CENTER_Z);
    scene.overlay()
        .showText(45)
        .colored(PonderPalette.WHITE)
        .text("A " + skill.getProfession().getEnUs() + " skill used by " + skill.getOperatorEnUs())
        .pointAt(textAnchor);
    scene.idle(50);
    scene.overlay()
        .showText(40)
        .colored(PonderPalette.BLUE)
        .text(skill.getRecoveryEnUs() + " · " + skill.getTriggerEnUs())
        .pointAt(textAnchor);
    scene.idle(45);
    scene.overlay()
        .showText(45)
        .colored(PonderPalette.OUTPUT)
        .text(statsText(skill))
        .pointAt(textAnchor);
    scene.idle(50);

    animateTheme(scene, util, skill.getTheme());

    scene.overlay()
        .showText(80)
        .colored(PonderPalette.WHITE)
        .text(skill.getDescriptionEnUs())
        .independent(8);
    scene.idle(85);
    scene.overlay()
        .showText(45)
        .colored(PonderPalette.INPUT)
        .text("This is a Minecraft interpretation based on PRTS skill data.")
        .independent(8);
    scene.idle(50);
    scene.markAsFinished();
  }

  private static String statsText(SkillDefinition skill) {
    Integer durationSeconds = skill.getDurationSeconds();
    String duration = durationSeconds == null ? "" : " · Duration " + durationSeconds + "s";
    return "Initial " + skill.getInitialSp() + " · Cost " + skill.getSpCost() + duration;
  }

  private static void animateTheme(
      SceneBuilder scene,
      SceneBuildingUtil util,
      SkillDemoTheme theme
  ) {
    Vec3 center = util.vector().centerOf(CENTER_X, CENTER_Y, CENTER_Z);
    Selection enemies = util.select()
        .fromTo(1, 1, 1, 5, 1, 5)
        .substract(util.select().position(CENTER_X, 1, CENTER_Z));

    switch (theme) {
      case COST_RECOVERY -> animateCostRecovery(scene, util);
      case AREA_SLASH -> animateAreaSlash(scene, center, enemies);
      case RAPID_FIRE -> animateRapidFire(scene, util, center);
      case EXPLOSIVE_DAWN -> animateExplosiveDawn(scene, util, center, enemies);
      case VOLCANIC_BURST -> animateVolcanicBurst(scene, center, enemies);
      case HEAL_AND_SLOW -> animateHealAndSlow(scene, util, center, enemies);
      case SANCTUARY -> animateSanctuary(scene, util, center);
      case SLOWING_FIELD -> animateSlowingField(scene, center, enemies);
      case DEPLOYMENT_STUN -> animateDeploymentStun(scene, util, center, enemies);
      default -> throw new IllegalStateException("未知的技能演示主题：" + theme);
    }
  }

  private static void animateCostRecovery(SceneBuilder scene, SceneBuildingUtil util) {
    for (int index = 0; index < 5; index++) {
      scene.world().createItemEntity(
          util.vector().centerOf(1 + index, 2, 3),
          new Vec3(0.0, 0.08, 0.0),
          new ItemStack(Items.EMERALD)
      );
      scene.effects().indicateSuccess(util.grid().at(CENTER_X, 1, CENTER_Z));
      scene.idle(6);
    }
  }

  private static void animateAreaSlash(
      SceneBuilder scene,
      Vec3 center,
      Selection enemies
  ) {
    scene.overlay().showOutline(PonderPalette.RED, new Object(), enemies, 45);
    emitParticles(scene, center, ParticleTypes.SWEEP_ATTACK, Vec3.ZERO, 4.0F, 12);
    scene.rotateCameraY(90.0F);
    scene.idle(45);
  }

  private static void animateRapidFire(SceneBuilder scene, SceneBuildingUtil util, Vec3 center) {
    for (int index = 0; index < 5; index++) {
      scene.world().createItemEntity(
          center.add(0.0, 0.2, 0.0),
          new Vec3(0.12 * (index - 2), 0.05, -0.18),
          new ItemStack(Items.ARROW)
      );
      scene.effects().createRedstoneParticles(util.grid().at(CENTER_X, 1, CENTER_Z), 0xFFE070, 8);
      scene.idle(4);
    }
    scene.idle(25);
  }

  private static void animateExplosiveDawn(
      SceneBuilder scene,
      SceneBuildingUtil util,
      Vec3 center,
      Selection enemies
  ) {
    // 两个魂灵之影与六次大范围爆炸对应专精三的召唤和弹药机制。
    scene.world().setBlock(util.grid().at(2, 2, 3), Blocks.SOUL_LANTERN.defaultBlockState(), true);
    scene.world().setBlock(util.grid().at(4, 2, 3), Blocks.SOUL_LANTERN.defaultBlockState(), true);
    scene.overlay().showOutline(
        PonderPalette.BLUE,
        new Object(),
        util.select().fromTo(2, 2, 3, 4, 2, 3),
        70
    );
    scene.overlay().showOutline(PonderPalette.RED, new Object(), enemies, 70);
    Vec3 soulMotion = new Vec3(0.0, 0.06, 0.0);
    emitParticles(scene, util.vector().centerOf(2, 2, 3), ParticleTypes.SOUL, soulMotion, 1.0F, 12);
    emitParticles(scene, util.vector().centerOf(4, 2, 3), ParticleTypes.SOUL, soulMotion, 1.0F, 12);

    for (int shot = 0; shot < 6; shot++) {
      int targetX = 1 + shot % 5;
      int targetZ = 2 + shot % 3;
      scene.world().createItemEntity(
          center.add(0.0, 0.3, 0.0),
          new Vec3(0.14 * ((shot % 3) - 1), 0.08, -0.2),
          new ItemStack(Items.FIREWORK_ROCKET)
      );
      emitParticles(
          scene,
          util.vector().centerOf(targetX, 2, targetZ),
          ParticleTypes.EXPLOSION,
          Vec3.ZERO,
          1.5F,
          2
      );
      scene.effects().createRedstoneParticles(util.grid().at(targetX, 1, targetZ), 0xE32636, 12);
      scene.idle(8);
    }
    scene.idle(25);
  }

  private static void animateVolcanicBurst(
      SceneBuilder scene,
      Vec3 center,
      Selection enemies
  ) {
    emitParticles(scene, center, ParticleTypes.LAVA, new Vec3(0.0, 0.12, 0.0), 3.0F, 25);
    scene.world().replaceBlocks(enemies, Blocks.MAGMA_BLOCK.defaultBlockState(), true);
    scene.idle(45);
    scene.world().restoreBlocks(enemies);
  }

  private static void animateHealAndSlow(
      SceneBuilder scene,
      SceneBuildingUtil util,
      Vec3 center,
      Selection enemies
  ) {
    Selection allies = util.select().fromTo(2, 1, 2, 4, 1, 4);
    scene.overlay().showOutline(PonderPalette.GREEN, new Object(), allies, 50);
    scene.overlay().showOutline(PonderPalette.SLOW, new Object(), enemies, 50);
    emitParticles(scene, center, ParticleTypes.HEART, new Vec3(0.0, 0.08, 0.0), 1.0F, 30);
    scene.idle(55);
  }

  private static void animateSanctuary(SceneBuilder scene, SceneBuildingUtil util, Vec3 center) {
    scene.overlay().showOutline(
        PonderPalette.BLUE,
        new Object(),
        util.select().fromTo(1, 1, 1, 5, 3, 5),
        55
    );
    emitParticles(
        scene,
        center,
        ParticleTypes.TOTEM_OF_UNDYING,
        new Vec3(0.0, 0.08, 0.0),
        2.0F,
        35
    );
    scene.idle(60);
  }

  private static void animateSlowingField(
      SceneBuilder scene,
      Vec3 center,
      Selection enemies
  ) {
    scene.overlay().showOutline(PonderPalette.SLOW, new Object(), enemies, 55);
    emitParticles(
        scene,
        center,
        ParticleTypes.SOUL_FIRE_FLAME,
        new Vec3(0.0, 0.03, 0.0),
        2.0F,
        35
    );
    scene.rotateCameraY(-90.0F);
    scene.idle(60);
  }

  private static void animateDeploymentStun(
      SceneBuilder scene,
      SceneBuildingUtil util,
      Vec3 center,
      Selection enemies
  ) {
    scene.world().setBlock(
        util.grid().at(CENTER_X, CENTER_Y, CENTER_Z),
        Blocks.REDSTONE_BLOCK.defaultBlockState(),
        true
    );
    scene.overlay().showOutline(PonderPalette.RED, new Object(), enemies, 45);
    emitParticles(scene, center, ParticleTypes.EXPLOSION, Vec3.ZERO, 4.0F, 4);
    scene.idle(20);
    for (int repeat = 0; repeat < 3; repeat++) {
      scene.effects().indicateRedstone(util.grid().at(CENTER_X, CENTER_Y, CENTER_Z));
      scene.idle(10);
    }
  }

  private static void emitParticles(
      SceneBuilder scene,
      Vec3 position,
      ParticleOptions particle,
      Vec3 motion,
      float spread,
      int count
  ) {
    ParticleEmitter emitter = scene.effects().simpleParticleEmitter(particle, motion);
    scene.effects().emitParticles(position, emitter, spread, count);
  }

  @Override
  public @NotNull String getModId() {
    return Zinecraft.MOD_ID;
  }

  @Override
  public void registerScenes(@NotNull PonderSceneRegistrationHelper<ResourceLocation> helper) {
    for (SkillEntry entry : Zinecraft.SKILLS.entries) {
      SkillDefinition skill = entry.definition();
      helper.addStoryBoard(
          Zinecraft.id(skill.getPath()),
          TRAINING_GROUND_SCENE,
          (scene, util) -> buildSkillScene(scene, util, skill)
      );
    }
  }
}
