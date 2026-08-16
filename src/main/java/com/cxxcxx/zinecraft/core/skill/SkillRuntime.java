package com.cxxcxx.zinecraft.core.skill;

import com.cxxcxx.zinecraft.api.weapon.event.WeaponShotEvent;
import com.cxxcxx.zinecraft.api.weapon.vfx.WeaponVfxService;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * Skill-side event consumer. It deliberately has no dependency on TaCZ classes.
 */
@EventBusSubscriber(modid = Zinecraft.MOD_ID)
public final class SkillRuntime {
  private static final TestRapidFireSkill TEST_RAPID_FIRE = new TestRapidFireSkill(WeaponVfxService.NONE);

  private SkillRuntime() {
  }

  @SubscribeEvent
  public static void onWeaponShot(WeaponShotEvent event) {
    TEST_RAPID_FIRE.onShot(event.context());
  }
}
