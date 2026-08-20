package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.AnimationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.SoundBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.VfxBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;

/**
 * 集中声明所有原生武器表现资源，避免武器与客户端后端重复拼接资源路径。
 */
public final class ModWeaponPresentation {
  public static final AnimationBuilder PLAYER_LIGHT_ATTACK = playerAnimation("light_attack");
  public static final AnimationBuilder WEAPON_LIGHT_ATTACK = weaponAnimation("test_sword_light_attack");
  public static final AnimationBuilder PLAYER_RIFLE_FIRE = playerAnimation("rifle_fire");
  public static final AnimationBuilder WEAPON_RIFLE_FIRE = weaponAnimation("test_rifle_fire");
  public static final AnimationBuilder PLAYER_RIFLE_RELOAD = playerAnimation("rifle_reload");
  public static final AnimationBuilder WEAPON_RIFLE_RELOAD = weaponAnimation("test_rifle_reload");
  public static final AnimationBuilder PLAYER_RIFLE_AIM = playerAnimation("rifle_aim");
  public static final AnimationBuilder PLAYER_STAFF_CAST = playerAnimation("staff_cast");
  public static final AnimationBuilder WEAPON_STAFF_CAST = weaponAnimation("test_staff_cast");
  public static final AnimationBuilder PLAYER_STAFF_HEAL = playerAnimation("staff_heal");

  public static final VfxBuilder TEST_SWORD_TRAIL = vfx("weapon/sword_slash");
  public static final VfxBuilder TEST_SWORD_IMPACT = vfx("vfx/test_sword_impact");
  public static final VfxBuilder RIFLE_MUZZLE = vfx("vfx/test_rifle_muzzle");
  public static final VfxBuilder RIFLE_IMPACT = vfx("weapon/explosion");
  public static final VfxBuilder STAFF_ARCANE_CAST = vfx("vfx/test_staff_arcane_cast");
  public static final VfxBuilder STAFF_ARCANE_IMPACT = vfx("vfx/test_staff_arcane_impact");
  public static final VfxBuilder STAFF_HEAL = vfx("weapon/healing");

  public static final SoundBuilder TEST_SWORD_SWING = sound(
      "sound/test_sword_swing", "测试剑挥动", "Test Sword Swing"
  );
  public static final SoundBuilder RIFLE_FIRE = sound(
      "sound/test_rifle_fire", "测试步枪开火", "Test Rifle Fire"
  );
  public static final SoundBuilder RIFLE_RELOAD = sound(
      "sound/test_rifle_reload", "测试步枪换弹", "Test Rifle Reload"
  );
  public static final SoundBuilder STAFF_CAST = sound(
      "sound/test_staff_cast", "测试法杖施法", "Test Staff Cast"
  );

  private ModWeaponPresentation() {
  }

  private static AnimationBuilder playerAnimation(String path) {
    return animation(AnimationBuilder.Target.PLAYER, path);
  }

  private static AnimationBuilder weaponAnimation(String path) {
    return animation(AnimationBuilder.Target.WEAPON, path);
  }

  private static AnimationBuilder animation(AnimationBuilder.Target target, String path) {
    return new AnimationBuilder(Zinecraft.ANIMATIONS, target, path).build();
  }

  private static VfxBuilder vfx(String path) {
    return new VfxBuilder(Zinecraft.VFX, path).build();
  }

  private static SoundBuilder sound(String path, String zhCn, String enUs) {
    return new SoundBuilder(Zinecraft.SOUNDS, path, zhCn).enUs(enUs).build();
  }

  public static void bootstrap() {
  }
}
