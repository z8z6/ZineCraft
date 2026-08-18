package com.cxxcxx.zinecraft.core.skill;

import com.cxxcxx.zinecraft.api.skill.SkillDemoTheme;
import com.cxxcxx.zinecraft.api.skill.SkillEntry;
import com.cxxcxx.zinecraft.api.skill.SkillProfession;
import com.cxxcxx.zinecraft.core.Zinecraft;

public final class ModSkills {
  public static final SkillEntry SUPPORT_BETA = Zinecraft.SKILLS
      .register(
          "skill_support_beta",
          "支援号令·β型",
          "Support β",
          "桃金娘",
          "Myrtle",
          SkillProfession.VANGUARD,
          "自动回复",
          "Auto Recovery",
          "手动触发",
          "Manual",
          13,
          22,
          8,
          "停止攻击，并在持续时间内逐步回复部署费用。",
          "Stops attacking and gradually recovers deployment points during the skill.",
          SkillDemoTheme.COST_RECOVERY
      );
  public static final SkillEntry TRUESILVER_SLASH = Zinecraft.SKILLS
      .register(
          "skill_truesilver_slash",
          "真银斩",
          "Truesilver Slash",
          "银灰",
          "SilverAsh",
          SkillProfession.GUARD,
          "自动回复",
          "Auto Recovery",
          "手动触发",
          "Manual",
          75,
          90,
          30,
          "降低自身防御，大幅提高攻击并扩大范围，同时攻击至多六个目标。",
          "Reduces DEF, greatly increases ATK and range, and attacks up to six targets.",
          SkillDemoTheme.AREA_SLASH
      );
  public static final SkillEntry OVERLOADING_MODE = Zinecraft.SKILLS
      .register(
          "skill_overloading_mode",
          "过载模式",
          "Overloading Mode",
          "能天使",
          "Exusiai",
          SkillProfession.SNIPER,
          "自动回复",
          "Auto Recovery",
          "自动触发",
          "Auto Trigger",
          20,
          30,
          15,
          "自动开启，攻击变为五连射并缩短攻击间隔。",
          "Activates automatically, changes attacks to five-shot bursts, and shortens the interval.",
          SkillDemoTheme.RAPID_FIRE
      );
  public static final SkillEntry EXPLOSIVE_DAWN = Zinecraft.SKILLS
      .register(
          "skill_explosive_dawn",
          "爆裂黎明",
          "Explosive Dawn",
          "维什戴尔",
          "Wiš'adel",
          SkillProfession.SNIPER,
          "自动回复",
          "Auto Recovery",
          "手动触发",
          "Manual Toggled",
          40,
          50,
          null,
          "立刻在攻击范围内召唤2个魂灵之影（最多存在3个，技能结束后保留），攻击力+180%，攻击间隔大幅增大，攻击时攻击力提升至220%，溅射范围大幅扩大且第一天赋的发动概率提高至100%。攻击装有6发弹药，打完后结束（可随时停止技能）。",
          "Immediately summons 2 Revenant's Shadows in attack range (up to 3 at a time; they remain after the skill expires). ATK +180%, Attack Interval is greatly increased, attacks deal 220% ATK, splash area is greatly increased, and Talent 1 trigger chance is increased to 100%. Skill activation grants 6 ammo and the skill ends when all ammo are used (can be manually deactivated).",
          SkillDemoTheme.EXPLOSIVE_DAWN
      );
  public static final SkillEntry VOLCANO = Zinecraft.SKILLS
      .register(
          "skill_volcano",
          "火山",
          "Volcano",
          "艾雅法拉",
          "Eyjafjalla",
          SkillProfession.CASTER,
          "自动回复",
          "Auto Recovery",
          "手动触发",
          "Manual",
          55,
          80,
          15,
          "攻击范围扩大并快速向范围内至多六个敌人发射熔岩。",
          "Expands range and rapidly launches lava at up to six enemies in range.",
          SkillDemoTheme.VOLCANIC_BURST
      );
  public static final SkillEntry CALCIFICATION = Zinecraft.SKILLS
      .register(
          "skill_calcification",
          "钙质化",
          "Calcification",
          "塞雷娅",
          "Saria",
          SkillProfession.DEFENDER,
          "自动回复",
          "Auto Recovery",
          "手动触发",
          "Manual",
          70,
          80,
          30,
          "持续治疗附近友方，同时使附近敌人减速并更易受到法术伤害。",
          "Continuously heals nearby allies while slowing enemies and amplifying Arts damage.",
          SkillDemoTheme.HEAL_AND_SLOW
      );
  public static final SkillEntry SANCTUARY = Zinecraft.SKILLS
      .register(
          "skill_sanctuary",
          "圣域",
          "Sanctuary",
          "夜莺",
          "Nightingale",
          SkillProfession.MEDIC,
          "自动回复",
          "Auto Recovery",
          "手动触发",
          "Manual",
          115,
          120,
          60,
          "扩大治疗范围并强化攻击，使范围内友方获得法术抗性与法术闪避。",
          "Expands healing range and grants allies Arts resistance and Arts evasion.",
          SkillDemoTheme.SANCTUARY
      );
  public static final SkillEntry FOXFIRE_HAZE = Zinecraft.SKILLS
      .register(
          "skill_foxfire_haze",
          "狐火渺然",
          "Foxfire Haze",
          "铃兰",
          "Suzuran",
          SkillProfession.SUPPORTER,
          "自动回复",
          "Auto Recovery",
          "手动触发",
          "Manual",
          50,
          70,
          35,
          "停止攻击，扩大范围，使敌人停顿并持续恢复范围内友方生命。",
          "Stops attacking, expands range, slows enemies, and continuously restores allies.",
          SkillDemoTheme.SLOWING_FIELD
      );
  public static final SkillEntry WOLFPACK = Zinecraft.SKILLS
      .register(
          "skill_wolfpack",
          "狼群",
          "Wolfpack",
          "红",
          "Projekt Red",
          SkillProfession.SPECIALIST,
          "被动",
          "Passive",
          "部署触发",
          "On Deployment",
          0,
          0,
          null,
          "部署后立即伤害周围所有地面敌人，并使命中目标晕眩三秒。",
          "On deployment, damages all nearby ground enemies and stuns them for three seconds.",
          SkillDemoTheme.DEPLOYMENT_STUN
      );

  private ModSkills() {
  }

  public static void bootstrap() {
  }
}
