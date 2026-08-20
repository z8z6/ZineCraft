package com.cxxcxx.zinecraft.core.skill;

import com.cxxcxx.zinecraft.api.combat.CombatDamageType;
import com.cxxcxx.zinecraft.api.registry.builder.SkillBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.VfxBuilder;
import com.cxxcxx.zinecraft.api.skill.SkillDemoTheme;
import com.cxxcxx.zinecraft.api.skill.SkillProfession;
import com.cxxcxx.zinecraft.core.Zinecraft;

public final class ModSkill {
  public static final SkillBuilder SUPPORT_BETA = skill("skill_support_beta", "支援号令·β型")
      .enUs("Support β")
      .operator("桃金娘", "Myrtle", SkillProfession.VANGUARD)
      .activation("自动回复", "Auto Recovery", "手动触发", "Manual")
      .stats(13, 22, 8)
      .description(
          "停止攻击，并在持续时间内逐步回复部署费用。",
          "Stops attacking and gradually recovers deployment points during the skill."
      )
      .effect(vfx("skill/support_beta"))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  public static final SkillBuilder TRUESILVER_SLASH = skill("skill_truesilver_slash", "真银斩")
      .enUs("Truesilver Slash")
      .operator("银灰", "SilverAsh", SkillProfession.GUARD)
      .activation("自动回复", "Auto Recovery", "手动触发", "Manual")
      .stats(75, 90, 30)
      .damage(3.0, CombatDamageType.PHYSICAL)
      .description(
          "降低自身防御，大幅提高攻击并扩大范围，同时攻击至多六个目标。",
          "Reduces DEF, greatly increases ATK and range, and attacks up to six targets."
      )
      .effect(vfx("skill/truesilver_slash"))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  public static final SkillBuilder OVERLOADING_MODE = skill("skill_overloading_mode", "过载模式")
      .enUs("Overloading Mode")
      .operator("能天使", "Exusiai", SkillProfession.SNIPER)
      .activation("自动回复", "Auto Recovery", "自动触发", "Auto Trigger")
      .stats(20, 30, 15)
      .damage(1.1, CombatDamageType.PHYSICAL)
      .description(
          "自动开启，攻击变为五连射并缩短攻击间隔。",
          "Activates automatically, changes attacks to five-shot bursts, and shortens the interval."
      )
      .effect(vfx("skill/overloading_mode"))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  public static final SkillBuilder EXPLOSIVE_DAWN = skill("skill_explosive_dawn", "爆裂黎明")
      .enUs("Explosive Dawn")
      .operator("维什戴尔", "Wiš'adel", SkillProfession.SNIPER)
      .activation("自动回复", "Auto Recovery", "手动触发", "Manual Toggled")
      .stats(40, 50, null)
      .damage(2.2, CombatDamageType.PHYSICAL)
      .description(
          "立刻在攻击范围内召唤2个魂灵之影（最多存在3个，技能结束后保留），攻击力+180%，攻击间隔大幅增大，攻击时攻击力提升至220%，溅射范围大幅扩大且第一天赋的发动概率提高至100%。攻击装有6发弹药，打完后结束（可随时停止技能）。",
          "Immediately summons 2 Revenant's Shadows in attack range (up to 3 at a time; they remain after the skill expires). ATK +180%, Attack Interval is greatly increased, attacks deal 220% ATK, splash area is greatly increased, and Talent 1 trigger chance is increased to 100%. Skill activation grants 6 ammo and the skill ends when all ammo are used (can be manually deactivated)."
      )
      .effect(vfx("skill/explosive_dawn"))
      .theme(SkillDemoTheme.EXPLOSIVE_DAWN)
      .build();

  public static final SkillBuilder VOLCANO = skill("skill_volcano", "火山")
      .enUs("Volcano")
      .operator("艾雅法拉", "Eyjafjalla", SkillProfession.CASTER)
      .activation("自动回复", "Auto Recovery", "手动触发", "Manual")
      .stats(55, 80, 15)
      .damage(2.3, CombatDamageType.ARTS)
      .description(
          "攻击范围扩大并快速向范围内至多六个敌人发射熔岩。",
          "Expands range and rapidly launches lava at up to six enemies in range."
      )
      .effect(vfx("skill/volcano"))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  public static final SkillBuilder CALCIFICATION = skill("skill_calcification", "钙质化")
      .enUs("Calcification")
      .operator("塞雷娅", "Saria", SkillProfession.DEFENDER)
      .activation("自动回复", "Auto Recovery", "手动触发", "Manual")
      .stats(70, 80, 30)
      .description(
          "持续治疗附近友方，同时使附近敌人减速并更易受到法术伤害。",
          "Continuously heals nearby allies while slowing enemies and amplifying Arts damage."
      )
      .effect(vfx("skill/calcification"))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  public static final SkillBuilder SANCTUARY = skill("skill_sanctuary", "圣域")
      .enUs("Sanctuary")
      .operator("夜莺", "Nightingale", SkillProfession.MEDIC)
      .activation("自动回复", "Auto Recovery", "手动触发", "Manual")
      .stats(115, 120, 60)
      .description(
          "扩大治疗范围并强化攻击，使范围内友方获得法术抗性与法术闪避。",
          "Expands healing range and grants allies Arts resistance and Arts evasion."
      )
      .effect(vfx("skill/sanctuary"))
      .theme(SkillDemoTheme.SANCTUARY)
      .build();

  public static final SkillBuilder FOXFIRE_HAZE = skill("skill_foxfire_haze", "狐火渺然")
      .enUs("Foxfire Haze")
      .operator("铃兰", "Suzuran", SkillProfession.SUPPORTER)
      .activation("自动回复", "Auto Recovery", "手动触发", "Manual")
      .stats(50, 70, 35)
      .description(
          "停止攻击，扩大范围，使敌人停顿并持续恢复范围内友方生命。",
          "Stops attacking, expands range, slows enemies, and continuously restores allies."
      )
      .effect(vfx("skill/foxfire_haze"))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  public static final SkillBuilder WOLFPACK = skill("skill_wolfpack", "狼群")
      .enUs("Wolfpack")
      .operator("红", "Projekt Red", SkillProfession.SPECIALIST)
      .activation("被动", "Passive", "部署触发", "On Deployment")
      .stats(0, 0, null)
      .damage(2.5, CombatDamageType.PHYSICAL)
      .description(
          "部署后立即伤害周围所有地面敌人，并使命中目标晕眩三秒。",
          "On deployment, damages all nearby ground enemies and stuns them for three seconds."
      )
      .effect(vfx("skill/wolfpack"))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  private ModSkill() {
  }

  private static SkillBuilder skill(String path, String zhCn) {
    return new SkillBuilder(Zinecraft.SKILLS, path, zhCn);
  }

  private static VfxBuilder vfx(String path) {
    return new VfxBuilder(Zinecraft.VFX, path).build();
  }

  public static void bootstrap() {
  }
}
