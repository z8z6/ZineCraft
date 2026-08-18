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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public final class ZinecraftPonderPlugin implements PonderPlugin {
  @NotNull
  public static final ZinecraftPonderPlugin INSTANCE = new ZinecraftPonderPlugin();

  private ZinecraftPonderPlugin() {
  }

  private static final void registerScenesHelper0$0(SkillEntry _entry, SceneBuilder scene, SceneBuildingUtil util) {
    ZinecraftPonderPlugin var10000 = INSTANCE;
    var10000.buildSkillScene(scene, util, _entry.definition());
  }

  @NotNull
  public String getModId() {
    return Zinecraft.MOD_ID;
  }

  public void registerScenes(@NotNull PonderSceneRegistrationHelper<ResourceLocation> helper) {
    Iterable _this_forEach_iv = Zinecraft.SKILLS.entries;
    int _i_f_forEach = 0;

    for (Object element_iv : _this_forEach_iv) {
      SkillEntry entry = (SkillEntry) element_iv;
      int var7 = 0;
      helper.addStoryBoard(
          Zinecraft.REGISTRAR.id(entry.definition().getPath()),
          "skill_demo/training_ground",
          (scene, util) -> INSTANCE.buildSkillScene(scene, util, entry.definition()),
          new ResourceLocation[0]
      );
    }
  }

  private final void buildSkillScene(SceneBuilder scene, SceneBuildingUtil util, SkillDefinition skill) {
    scene.title("skill_demo_" + skill.getPath(), skill.getOperatorEnUs() + ": " + skill.getEnUs());
    scene.configureBasePlate(0, 0, 7);
    scene.showBasePlate();
    scene.idle(10);
    scene.world().showSection(util.select().layersFrom(1), Direction.DOWN);
    scene.idle(15);
    Vec3 center = util.vector().topOf(3, 1, 3);
    scene.overlay()
        .showText(45)
        .colored(PonderPalette.WHITE)
        .text("A " + skill.getProfession().getEnUs() + " skill used by " + skill.getOperatorEnUs())
        .pointAt(center);
    scene.idle(50);
    scene.overlay().showText(40).colored(PonderPalette.BLUE).text(skill.getRecoveryEnUs() + " · " + skill.getTriggerEnUs()).pointAt(center);
    scene.idle(45);
    scene.overlay().showText(45).colored(PonderPalette.OUTPUT).text(this.statsText(skill)).pointAt(center);
    scene.idle(50);
    this.animateTheme(scene, util, skill.getTheme());
    scene.overlay().showText(80).colored(PonderPalette.WHITE).text(skill.getDescriptionEnUs()).independent(8);
    scene.idle(85);
    scene.overlay().showText(45).colored(PonderPalette.INPUT).text("This is a Minecraft interpretation based on PRTS skill data.").independent(8);
    scene.idle(50);
    scene.markAsFinished();
  }

  private final String statsText(SkillDefinition skill) {
    Integer var10000 = skill.getDurationSeconds();
    String var5;
    if (var10000 != null) {
      int it = var10000.intValue();
      int var4 = 0;
      var5 = " · Duration " + it + "s";
    } else {
      var5 = null;
    }

    if (var5 == null) {
      var5 = "";
    }

    String duration = var5;
    return "Initial " + skill.getInitialSp() + " · Cost " + skill.getSpCost() + duration;
  }

  private final void animateTheme(SceneBuilder scene, SceneBuildingUtil util, SkillDemoTheme theme) {
    Vec3 center = util.vector().centerOf(3, 2, 3);
    Selection enemies = util.select().fromTo(1, 1, 1, 5, 1, 5).substract(util.select().position(3, 1, 3));
    switch (theme) {
      case COST_RECOVERY:
        byte var17 = 5;

        for (int var20 = 0; var20 < var17; var20++) {
          int index = var20;
          int var23 = 0;
          scene.world().createItemEntity(util.vector().centerOf(1 + index, 2, 3), new Vec3(0.0, 0.08, 0.0), new ItemStack((ItemLike) Items.EMERALD));
          scene.effects().indicateSuccess(util.grid().at(3, 1, 3));
          scene.idle(6);
        }
        break;
      case AREA_SLASH: {
        scene.overlay().showOutline(PonderPalette.RED, new Object(), enemies, 45);
        ParticleEmitter emitter = scene.effects().simpleParticleEmitter((ParticleOptions) ParticleTypes.SWEEP_ATTACK, Vec3.ZERO);
        scene.effects().emitParticles(center, emitter, 4.0F, 12);
        scene.rotateCameraY(90.0F);
        scene.idle(45);
        break;
      }
      case RAPID_FIRE:
        byte var15 = 5;

        for (int var19 = 0; var19 < var15; var19++) {
          int index = var19;
          int var9 = 0;
          scene.world().createItemEntity(center.add(0.0, 0.2, 0.0), new Vec3(0.12 * (index - 2), 0.05, -0.18), new ItemStack((ItemLike) Items.ARROW));
          scene.effects().createRedstoneParticles(util.grid().at(3, 1, 3), 16769136, 8);
          scene.idle(4);
        }

        scene.idle(25);
        break;
      case EXPLOSIVE_DAWN: {
        // 两个魂灵之影与六次大范围爆炸对应专精三的召唤和弹药机制。
        scene.world().setBlock(util.grid().at(2, 2, 3), Blocks.SOUL_LANTERN.defaultBlockState(), true);
        scene.world().setBlock(util.grid().at(4, 2, 3), Blocks.SOUL_LANTERN.defaultBlockState(), true);
        scene.overlay().showOutline(PonderPalette.BLUE, new Object(), util.select().fromTo(2, 2, 3, 4, 2, 3), 70);
        scene.overlay().showOutline(PonderPalette.RED, new Object(), enemies, 70);
        ParticleEmitter soulEmitter = scene.effects().simpleParticleEmitter((ParticleOptions) ParticleTypes.SOUL, new Vec3(0.0, 0.06, 0.0));
        scene.effects().emitParticles(util.vector().centerOf(2, 2, 3), soulEmitter, 1.0F, 12);
        scene.effects().emitParticles(util.vector().centerOf(4, 2, 3), soulEmitter, 1.0F, 12);

        for (int shot = 0; shot < 6; shot++) {
          scene.world().createItemEntity(center.add(0.0, 0.3, 0.0), new Vec3(0.14 * ((shot % 3) - 1), 0.08, -0.2), new ItemStack((ItemLike) Items.FIREWORK_ROCKET));
          ParticleEmitter explosionEmitter = scene.effects().simpleParticleEmitter((ParticleOptions) ParticleTypes.EXPLOSION, Vec3.ZERO);
          scene.effects().emitParticles(util.vector().centerOf(1 + shot % 5, 2, 2 + shot % 3), explosionEmitter, 1.5F, 2);
          scene.effects().createRedstoneParticles(util.grid().at(1 + shot % 5, 1, 2 + shot % 3), 0xE32636, 12);
          scene.idle(8);
        }
        scene.idle(25);
        break;
      }
      case VOLCANIC_BURST: {
        ParticleEmitter emitter = scene.effects().simpleParticleEmitter((ParticleOptions) ParticleTypes.LAVA, new Vec3(0.0, 0.12, 0.0));
        scene.effects().emitParticles(center, emitter, 3.0F, 25);
        scene.world().replaceBlocks(enemies, Blocks.MAGMA_BLOCK.defaultBlockState(), true);
        scene.idle(45);
        scene.world().restoreBlocks(enemies);
        break;
      }
      case HEAL_AND_SLOW: {
        Selection allies = util.select().fromTo(2, 1, 2, 4, 1, 4);
        scene.overlay().showOutline(PonderPalette.GREEN, new Object(), allies, 50);
        scene.overlay().showOutline(PonderPalette.SLOW, new Object(), enemies, 50);
        ParticleEmitter emitter = scene.effects().simpleParticleEmitter((ParticleOptions) ParticleTypes.HEART, new Vec3(0.0, 0.08, 0.0));
        scene.effects().emitParticles(center, emitter, 1.0F, 30);
        scene.idle(55);
        break;
      }
      case SANCTUARY: {
        scene.overlay().showOutline(PonderPalette.BLUE, new Object(), util.select().fromTo(1, 1, 1, 5, 3, 5), 55);
        ParticleEmitter emitter = scene.effects().simpleParticleEmitter((ParticleOptions) ParticleTypes.TOTEM_OF_UNDYING, new Vec3(0.0, 0.08, 0.0));
        scene.effects().emitParticles(center, emitter, 2.0F, 35);
        scene.idle(60);
        break;
      }
      case SLOWING_FIELD: {
        scene.overlay().showOutline(PonderPalette.SLOW, new Object(), enemies, 55);
        ParticleEmitter emitter = scene.effects().simpleParticleEmitter((ParticleOptions) ParticleTypes.SOUL_FIRE_FLAME, new Vec3(0.0, 0.03, 0.0));
        scene.effects().emitParticles(center, emitter, 2.0F, 35);
        scene.rotateCameraY(-90.0F);
        scene.idle(60);
        break;
      }
      case DEPLOYMENT_STUN: {
        scene.world().setBlock(util.grid().at(3, 2, 3), Blocks.REDSTONE_BLOCK.defaultBlockState(), true);
        scene.overlay().showOutline(PonderPalette.RED, new Object(), enemies, 45);
        ParticleEmitter emitter = scene.effects().simpleParticleEmitter((ParticleOptions) ParticleTypes.EXPLOSION, Vec3.ZERO);
        scene.effects().emitParticles(center, emitter, 4.0F, 4);
        scene.idle(20);
        byte repeatCount = 3;

        for (int it = 0; it < repeatCount; it++) {
          int var10 = 0;
          scene.effects().indicateRedstone(util.grid().at(3, 2, 3));
          scene.idle(10);
        }
        break;
      }
      default:
        throw new IllegalStateException("未知的技能演示主题：" + theme);
    }
  }

  public static final class WhenMappings {
    public static final int[] _EnumSwitchMapping$0;

    static {
      int[] var0 = new int[SkillDemoTheme.values().length];

      try {
        var0[SkillDemoTheme.COST_RECOVERY.ordinal()] = 1;
      } catch (NoSuchFieldError var9) {
      }

      try {
        var0[SkillDemoTheme.AREA_SLASH.ordinal()] = 2;
      } catch (NoSuchFieldError var8) {
      }

      try {
        var0[SkillDemoTheme.RAPID_FIRE.ordinal()] = 3;
      } catch (NoSuchFieldError var7) {
      }

      try {
        var0[SkillDemoTheme.VOLCANIC_BURST.ordinal()] = 4;
      } catch (NoSuchFieldError var6) {
      }

      try {
        var0[SkillDemoTheme.HEAL_AND_SLOW.ordinal()] = 5;
      } catch (NoSuchFieldError var5) {
      }

      try {
        var0[SkillDemoTheme.SANCTUARY.ordinal()] = 6;
      } catch (NoSuchFieldError var4) {
      }

      try {
        var0[SkillDemoTheme.SLOWING_FIELD.ordinal()] = 7;
      } catch (NoSuchFieldError var3) {
      }

      try {
        var0[SkillDemoTheme.DEPLOYMENT_STUN.ordinal()] = 8;
      } catch (NoSuchFieldError var2) {
      }

      _EnumSwitchMapping$0 = var0;
    }
  }
}
