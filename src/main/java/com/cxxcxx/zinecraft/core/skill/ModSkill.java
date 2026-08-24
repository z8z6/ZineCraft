package com.cxxcxx.zinecraft.core.skill;

import com.cxxcxx.zinecraft.api.combat.CombatDamageType;
import com.cxxcxx.zinecraft.api.registry.builder.SkillBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.VfxBuilder;
import com.cxxcxx.zinecraft.api.skill.SkillDemoTheme;
import com.cxxcxx.zinecraft.api.skill.SkillProfession;
import com.cxxcxx.zinecraft.api.skill.SkillSpRecoveryType;
import com.cxxcxx.zinecraft.api.skill.SkillTriggerType;
import com.cxxcxx.zinecraft.core.Zinecraft;

public final class ModSkill {
  public static final SkillBuilder SUPPORT_BETA = skill("skill_support_beta", "支援号令·β型")
      .enUs("Support β")
      .operator("桃金娘", "Myrtle", SkillProfession.VANGUARD)
      .activation(SkillSpRecoveryType.AUTO_RECOVERY, SkillTriggerType.MANUAL)
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
      .activation(SkillSpRecoveryType.AUTO_RECOVERY, SkillTriggerType.MANUAL)
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
      .activation(SkillSpRecoveryType.AUTO_RECOVERY, SkillTriggerType.AUTO)
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
      .activation(SkillSpRecoveryType.AUTO_RECOVERY, SkillTriggerType.MANUAL_TOGGLED)
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
      .activation(SkillSpRecoveryType.AUTO_RECOVERY, SkillTriggerType.MANUAL)
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
      .activation(SkillSpRecoveryType.AUTO_RECOVERY, SkillTriggerType.MANUAL)
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
      .activation(SkillSpRecoveryType.AUTO_RECOVERY, SkillTriggerType.MANUAL)
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
      .activation(SkillSpRecoveryType.AUTO_RECOVERY, SkillTriggerType.MANUAL)
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
      .activation(SkillSpRecoveryType.PASSIVE, SkillTriggerType.ON_DEPLOYMENT)
      .stats(0, 0, null)
      .damage(2.5, CombatDamageType.PHYSICAL)
      .description(
          "部署后立即伤害周围所有地面敌人，并使命中目标晕眩三秒。",
          "On deployment, damages all nearby ground enemies and stuns them for three seconds."
      )
      .effect(vfx("skill/wolfpack"))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // BEGIN GENERATED PRTS SIX-STAR THIRD SKILLS
  // PRTS: https://prts.wiki/w/%E6%8E%A8%E8%BF%9B%E4%B9%8B%E7%8E%8B
  public static final SkillBuilder SKULL_BREAKER = skill(
      "skill_skull_breaker",
      "碎颅击"
  )
      .enUs("Skull Breaker")
      .operator(
          "推进之王",
          "Siege",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 30, 25)
      .description(
          "攻击间隔增大(+1.0)，攻击时攻击力提高至380%，并且有40%的概率晕眩目标1.5秒",
          "攻击间隔增大(+1.0)，攻击时攻击力提高至380%，并且有40%的概率晕眩目标1.5秒"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E4%BC%8A%E8%8A%99%E5%88%A9%E7%89%B9
  public static final SkillBuilder SCORCHED_EARTH = skill(
      "skill_scorched_earth",
      "灼地"
  )
      .enUs("Scorched Earth")
      .operator(
          "伊芙利特",
          "Ifrit",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(0, 28, 20)
      .description(
          "对攻击范围内的地面敌人造成每秒相当于攻击力140%的法术伤害，命中目标的法术抗性-20；自己每秒流失最大生命值的2%",
          "对攻击范围内的地面敌人造成每秒相当于攻击力140%的法术伤害，命中目标的法术抗性-20；自己每秒流失最大生命值的2%"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E5%AE%89%E6%B4%81%E8%8E%89%E5%A8%9C
  public static final SkillBuilder ARCANE_STAFF_ANTI_GRAVITY_MODE = skill(
      "skill_arcane_staff_anti_gravity_mode",
      "秘杖·反重力模式"
  )
      .enUs("Arcane Staff - Anti-Gravity Mode")
      .operator(
          "安洁莉娜",
          "Angelina",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(10, 25, 25)
      .description(
          "全场所有敌人失重，攻击范围扩大，攻击力+150%，可以攻击5个敌人；技能未开启时无法普通攻击",
          "全场所有敌人失重，攻击范围扩大，攻击力+150%，可以攻击5个敌人；技能未开启时无法普通攻击"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E9%97%AA%E7%81%B5
  public static final SkillBuilder CREED_FIELD = skill(
      "skill_creed_field",
      "教条力场"
  )
      .enUs("Creed Field")
      .operator(
          "闪灵",
          "Shining",
          SkillProfession.MEDIC
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(115, 120, 60)
      .description(
          "攻击力+50%；攻击范围内的所有友方单位防御力+100%",
          "攻击力+50%；攻击范围内的所有友方单位防御力+100%"
      )
      .effect(reusedEffect(SkillDemoTheme.SANCTUARY))
      .theme(SkillDemoTheme.SANCTUARY)
      .build();

  // PRTS: https://prts.wiki/w/%E6%98%9F%E7%86%8A
  public static final SkillBuilder SAW_OF_STRENGTH = skill(
      "skill_saw_of_strength",
      "力之锯"
  )
      .enUs("Saw of Strength")
      .operator(
          "星熊",
          "Hoshiguma",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 50, 25)
      .description(
          "攻击力+140%，防御力+90%，对前方一格的所有敌人使用盾牌进行切割",
          "攻击力+140%，防御力+90%，对前方一格的所有敌人使用盾牌进行切割"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E6%96%AF%E5%8D%A1%E8%92%82
  public static final SkillBuilder TIDAL_ELEGY = skill(
      "skill_tidal_elegy",
      "涌潮悲歌"
  )
      .enUs("Tidal Elegy")
      .operator(
          "斯卡蒂",
          "Skadi",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(70, 90, 50)
      .description(
          "攻击力、防御力和生命上限各+130%",
          "攻击力、防御力和生命上限各+130%"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E9%99%88
  public static final SkillBuilder CHI_XIAO_SHADOWLESS = skill(
      "skill_chi_xiao_shadowless",
      "赤霄·绝影"
  )
      .enUs("Chi Xiao - Shadowless")
      .operator(
          "陈",
          "Ch'en",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.OFFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 30, null)
      .description(
          "向周围寻找最近的敌方目标，对其发动10次连续斩击，每次造成相当于攻击力320%的物理伤害，并在最后一击时使目标晕眩4秒",
          "向周围寻找最近的敌方目标，对其发动10次连续斩击，每次造成相当于攻击力320%的物理伤害，并在最后一击时使目标晕眩4秒"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E9%BB%91
  public static final SkillBuilder FINAL_TACTICS = skill(
      "skill_final_tactics",
      "战术的终结"
  )
      .enUs("Final Tactics")
      .operator(
          "黑",
          "Schwarz",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(12, 25, 25)
      .description(
          "攻击范围改为前方4格，攻击间隔略微增大(+0.4)，攻击力+180%，天赋的发动概率提高至100%",
          "攻击范围改为前方4格，攻击间隔略微增大(+0.4)，攻击力+180%，天赋的发动概率提高至100%"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E8%B5%AB%E6%8B%89%E6%A0%BC
  public static final SkillBuilder FULL_MOON = skill(
      "skill_full_moon",
      "满月"
  )
      .enUs("Full Moon")
      .operator(
          "赫拉格",
          "Hellagur",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(15, 35, 15)
      .description(
          "攻击力+100%，攻击范围+2格，最多可以同时攻击3个目标",
          "攻击力+100%，攻击范围+2格，最多可以同时攻击3个目标"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E9%BA%A6%E5%93%B2%E4%BC%A6
  public static final SkillBuilder ARMED_COMBAT_MODULE = skill(
      "skill_armed_combat_module",
      "武装打击模块"
  )
      .enUs("Armed Combat Module")
      .operator(
          "麦哲伦",
          "Magallan",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 38, 15)
      .description(
          "被动效果：无人机可以部署在远程位，进行群体物理攻击；主动开启：麦哲伦和她的无人机攻击力+150%，无人机攻击的爆炸范围扩大，技能结束时回收所有无人机",
          "被动效果：无人机可以部署在远程位，进行群体物理攻击；主动开启：麦哲伦和她的无人机攻击力+150%，无人机攻击的爆炸范围扩大，技能结束时回收所有无人机"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E8%8E%AB%E6%96%AF%E6%8F%90%E9%A9%AC
  public static final SkillBuilder KEY_OF_CHRONOLOGY = skill(
      "skill_key_of_chronology",
      "序时之匙"
  )
      .enUs("Key of Chronology")
      .operator(
          "莫斯提马",
          "Mostima",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(80, 110, 27)
      .description(
          "攻击范围扩大，攻击变为向外扩散的波纹，攻击力+170%，第二天赋的效果提升至3倍，小力度地击退攻击目标",
          "攻击范围扩大，攻击变为向外扩散的波纹，攻击力+170%，第二天赋的效果提升至3倍，小力度地击退攻击目标"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E7%85%8C
  public static final SkillBuilder BOILING_BURST = skill(
      "skill_boiling_burst",
      "沸腾爆裂"
  )
      .enUs("Boiling Burst")
      .operator(
          "煌",
          "Blaze",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 35, 10)
      .description(
          "技能开启后，攻击力和防御力逐渐增至+80%并对前方一格内的敌方单位进行切割。技能结束时对附近所有敌人造成此时攻击力400%的物理伤害，自己流失25%的生命值",
          "技能开启后，攻击力和防御力逐渐增至+80%并对前方一格内的敌方单位进行切割。技能结束时对附近所有敌人造成此时攻击力400%的物理伤害，自己流失25%的生命值"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E9%98%BF
  public static final SkillBuilder DURIAN_FLAVORED_STIMPACK = skill(
      "skill_durian_flavored_stimpack",
      "爆发剂·榴莲味"
  )
      .enUs("Durian-Flavored Stimpack")
      .operator(
          "阿",
          "Aak",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 35, 20)
      .description(
          "立即对前方最近（优先选取正前方）的一名友方单位用500的攻击力攻击15次，之后持续时间内使自身和目标攻击力+50%，攻击速度+50",
          "立即对前方最近（优先选取正前方）的一名友方单位用500的攻击力攻击15次，之后持续时间内使自身和目标攻击力+50%，攻击速度+50"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E5%B9%B4
  public static final SkillBuilder IRON_DEFENSE = skill(
      "skill_iron_defense",
      "铁御"
  )
      .enUs("Iron Defense")
      .operator(
          "年",
          "Nian",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(70, 85, 45)
      .description(
          "攻击力+120%；周围其他友方干员的防御力+80%，阻挡数+1，并获得抵抗",
          "攻击力+120%；周围其他友方干员的防御力+80%，阻挡数+1，并获得抵抗"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E5%88%BB%E4%BF%84%E6%9F%8F
  public static final SkillBuilder REALLY_HEAVY_SPEAR = skill(
      "skill_really_heavy_spear",
      "“很重的枪”"
  )
      .enUs("'Really Heavy Spear'")
      .operator(
          "刻俄柏",
          "Ceobe",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(55, 80, 60)
      .description(
          "攻击范围扩大，攻击力+210%，伤害类型变为物理，优先攻击防御力最低的目标，并令其失去特殊能力5秒",
          "攻击范围扩大，攻击力+210%，伤害类型变为物理，优先攻击防御力最低的目标，并令其失去特殊能力5秒"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E9%A3%8E%E7%AC%9B
  public static final SkillBuilder LOCKED_BREECH_BURST = skill(
      "skill_locked_breech_burst",
      "闭膛连发"
  )
      .enUs("Locked Breech Burst")
      .operator(
          "风笛",
          "Bagpipe",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 40, 20)
      .description(
          "攻击间隔增大(+70%)，阻挡数+1，攻击力和防御力+120%，攻击变为三连击",
          "攻击间隔增大(+70%)，阻挡数+1，攻击力和防御力+120%，攻击变为三连击"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E5%82%80%E5%BD%B1
  public static final SkillBuilder NIGHT_RAID = skill(
      "skill_night_raid",
      "夜幕突袭"
  )
      .enUs("Night Raid")
      .operator(
          "傀影",
          "Phantom",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.PASSIVE,
          SkillTriggerType.ON_DEPLOYMENT
      )
      .stats(0, 0, null)
      .description(
          "部署后立即对周围所有敌人造成相当于攻击力300%的物理伤害，将其小力地推开并随机施加以下任意一个状态（停顿、束缚、晕眩）4.5秒",
          "部署后立即对周围所有敌人造成相当于攻击力300%的物理伤害，将其小力地推开并随机施加以下任意一个状态（停顿、束缚、晕眩）4.5秒"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E6%B8%A9%E8%92%82
  public static final SkillBuilder LIQUID_NITROGEN_CANNON = skill(
      "skill_liquid_nitrogen_cannon",
      "液氮大炮"
  )
      .enUs("Liquid Nitrogen Cannon")
      .operator(
          "温蒂",
          "Weedy",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 33, null)
      .description(
          "立即发射一个压缩液氮炮，造成相当于攻击力350%的群体法术伤害并将敌人大力地推开，令其8秒内移动时受到正比于距离的真实伤害；如果蓄水炮在周围4格内的话也会同样进行发射",
          "立即发射一个压缩液氮炮，造成相当于攻击力350%的群体法术伤害并将敌人大力地推开，令其8秒内移动时受到正比于距离的真实伤害；如果蓄水炮在周围4格内的话也会同样进行发射"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/W
  public static final SkillBuilder D12 = skill(
      "skill_d12",
      "D12"
  )
      .enUs("D12")
      .operator(
          "W",
          "W",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 33, null)
      .description(
          "在攻击范围内生命值最多的4个敌人身上放置一枚炸弹；炸弹会在一定延迟后引爆，每个对其周围的所有敌人造成相当于攻击力310%的物理伤害并令其晕眩5秒",
          "在攻击范围内生命值最多的4个敌人身上放置一枚炸弹；炸弹会在一定延迟后引爆，每个对其周围的所有敌人造成相当于攻击力310%的物理伤害并令其晕眩5秒"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E6%97%A9%E9%9C%B2
  public static final SkillBuilder AVALANCHE_BREAKER = skill(
      "skill_avalanche_breaker",
      "雪崩击"
  )
      .enUs("Avalanche Breaker")
      .operator(
          "早露",
          "Роса",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(15, 30, 8)
      .description(
          "攻击力+25%，立即向至多4个重量最重的敌人发射束缚叉枪；技能持续时间内所有目标受到束缚效果，每秒受到一次攻击",
          "攻击力+25%，立即向至多4个重量最重的敌人发射束缚叉枪；技能持续时间内所有目标受到束缚效果，每秒受到一次攻击"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E6%A3%98%E5%88%BA
  public static final SkillBuilder DESTREZA = skill(
      "skill_destreza",
      "至高之术"
  )
      .enUs("Destreza")
      .operator(
          "棘刺",
          "Thorns",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.OFFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(0, 15, 30)
      .description(
          "攻击范围扩大，攻击力+60%，攻击速度+25，远程攻击不再降低攻击力；第二次及以后使用时能力加成变为最初的两倍，且持续时间无限",
          "攻击范围扩大，攻击力+60%，攻击速度+25，远程攻击不再降低攻击力；第二次及以后使用时能力加成变为最初的两倍，且持续时间无限"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E6%A3%AE%E8%9A%BA
  public static final SkillBuilder IRON_WILL = skill(
      "skill_iron_will",
      "钢铁意志"
  )
      .enUs("Iron Will")
      .operator(
          "森蚺",
          "Eunectes",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 45, 35)
      .description(
          "攻击力+230%，防御力+160%，阻挡数+2，每秒恢复6%生命；技能结束后自身晕眩5秒",
          "攻击力+230%，防御力+160%，阻挡数+2，每秒恢复6%生命；技能结束后自身晕眩5秒"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E5%8F%B2%E5%B0%94%E7%89%B9%E5%B0%94
  public static final SkillBuilder TWILIGHT = skill(
      "skill_twilight",
      "黄昏"
  )
      .enUs("Twilight")
      .operator(
          "史尔特尔",
          "Surtr",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(0, 5, null)
      .description(
          "立即恢复所有生命；攻击力+330%，攻击距离+2，攻击目标数+3，生命上限+5000，逐渐失去生命（60秒后到达最大生命20%/秒）；持续时间无限",
          "立即恢复所有生命；攻击力+330%，攻击距离+2，攻击目标数+3，生命上限+5000，逐渐失去生命（60秒后到达最大生命20%/秒）；持续时间无限"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E7%91%95%E5%85%89
  public static final SkillBuilder DIVINE_AVATAR = skill(
      "skill_divine_avatar",
      "先贤化身"
  )
      .enUs("Divine Avatar")
      .operator(
          "瑕光",
          "Blemishine",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.DEFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(15, 25, 30)
      .description(
          "攻击力+110%，防御力+60%，每次攻击额外造成相当于攻击力100%的法术伤害，并恢复周围一名其他友方单位相当于攻击力100%的生命",
          "攻击力+110%，防御力+60%，每次攻击额外造成相当于攻击力100%的法术伤害，并恢复周围一名其他友方单位相当于攻击力100%的生命"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E6%B3%A5%E5%B2%A9
  public static final SkillBuilder BLOODLINE_OF_DESECRATED_EARTH = skill(
      "skill_bloodline_of_desecrated_earth",
      "秽壤的血脉"
  )
      .enUs("Bloodline of Desecrated Earth")
      .operator(
          "泥岩",
          "Mudrock",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(15, 25, 30)
      .description(
          "技能开启后无法行动且不受到伤害10秒并使周围敌人移动速度-60%；该状态结束后晕眩周围地面敌人5秒，攻击间隔缩短(-30%)，攻击力+140%，防御力+80%，攻击阻挡的所有敌人",
          "技能开启后无法行动且不受到伤害10秒并使周围敌人移动速度-60%；该状态结束后晕眩周围地面敌人5秒，攻击间隔缩短(-30%)，攻击力+140%，防御力+80%，攻击阻挡的所有敌人"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E8%BF%B7%E8%BF%AD%E9%A6%99
  public static final SkillBuilder AS_YOU_WISH = skill(
      "skill_as_you_wish",
      "“如你所愿”"
  )
      .enUs("'As You Wish'")
      .operator(
          "迷迭香",
          "Rosmontis",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(35, 60, 30)
      .description(
          "攻击间隔缩短(-50%)，攻击力+75%，同时攻击2个敌人但仅选择被阻挡的敌人作为目标；立即在攻击范围内的近战位部署两个战术装备（部署后晕眩周围敌人3秒，阻挡的敌人防御力-160)",
          "攻击间隔缩短(-50%)，攻击力+75%，同时攻击2个敌人但仅选择被阻挡的敌人作为目标；立即在攻击范围内的近战位部署两个战术装备（部署后晕眩周围敌人3秒，阻挡的敌人防御力-160)"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E5%B1%B1
  public static final SkillBuilder EARTH_SHATTERING_SMASH = skill(
      "skill_earth_shattering_smash",
      "震地碎岩击"
  )
      .enUs("Earth-Shattering Smash")
      .operator(
          "山",
          "Mountain",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 40, 30)
      .description(
          "攻击间隔增大(+70%)，攻击力+100%，攻击变为2连击，第一天赋的发动几率提升至75%，每次攻击周围最多4个敌人并将其中等力度地推动",
          "攻击间隔增大(+70%)，攻击力+100%，攻击变为2连击，第一天赋的发动几率提升至75%，每次攻击周围最多4个敌人并将其中等力度地推动"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E7%A9%BA%E5%BC%A6
  public static final SkillBuilder THUNDERING_ARROWS = skill(
      "skill_thundering_arrows",
      "箭矢·暴风"
  )
      .enUs("Thundering Arrows")
      .operator(
          "空弦",
          "Archetto",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.OFFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(15, 30, 20)
      .description(
          "攻击力+30%，攻击距离+1，攻击变为3连击，每次可以攻击2个敌人",
          "攻击力+30%，攻击距离+1，攻击变为3连击，每次可以攻击2个敌人"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E5%B5%AF%E5%B3%A8
  public static final SkillBuilder FIERCE_GLARE = skill(
      "skill_fierce_glare",
      "怒目"
  )
      .enUs("Fierce Glare")
      .operator(
          "嵯峨",
          "Saga",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(28, 40, 20)
      .description(
          "技能持续时间内逐渐获得20点部署费用，攻击间隔稍微增大(+0.5s)，攻击距离+1，攻击力+130%，同时攻击阻挡的所有敌人，目标生命值低于一半时额外追加一次攻击",
          "技能持续时间内逐渐获得20点部署费用，攻击间隔稍微增大(+0.5s)，攻击距离+1，攻击力+130%，同时攻击阻挡的所有敌人，目标生命值低于一半时额外追加一次攻击"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E5%A4%95
  public static final SkillBuilder IMAGE_OVER_FORM = skill(
      "skill_image_over_form",
      "写意胜形"
  )
      .enUs("Image over Form")
      .operator(
          "夕",
          "Dusk",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(70, 100, 60)
      .description(
          "攻击间隔增大(+40%)，优先攻击未阻挡的敌人，攻击范围与溅射范围扩大，攻击力+120%，每次攻击时在目标位置（可部署地面）召唤/刷新一个\"小自在\"（持续25秒）",
          "攻击间隔增大(+40%)，优先攻击未阻挡的敌人，攻击范围与溅射范围扩大，攻击力+120%，每次攻击时在目标位置（可部署地面）召唤/刷新一个\"小自在\"（持续25秒）"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E7%81%B0%E7%83%AC
  public static final SkillBuilder BREACHING_ROUNDS = skill(
      "skill_breaching_rounds",
      "攻坚榴弹"
  )
      .enUs("Breaching Rounds")
      .operator(
          "灰烬",
          "Ash",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(0, 25, null)
      .description(
          "向前发射破墙弹，对沿途敌人造成300%的物理伤害并向后较大力度推动；爆炸对周围造成400%的物理伤害（从低地撞到高台直接爆炸且造成800%的物理伤害）；每次部署只能释放2次",
          "向前发射破墙弹，对沿途敌人造成300%的物理伤害并向后较大力度推动；爆炸对周围造成400%的物理伤害（从低地撞到高台直接爆炸且造成800%的物理伤害）；每次部署只能释放2次"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E5%BC%82%E5%AE%A2
  public static final SkillBuilder GLORIOUS_SHARDS = skill(
      "skill_glorious_shards",
      "辉煌裂片"
  )
      .enUs("Glorious Shards")
      .operator(
          "异客",
          "Passenger",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(0, 30, null)
      .description(
          "立即寻找大范围内生命值最高的目标，在其位置生成持续4秒的雷暴区域，期间每0.5秒以150%的攻击力对雷暴区域内的随机敌人进行一次额外攻击；可充能2次",
          "立即寻找大范围内生命值最高的目标，在其位置生成持续4秒的雷暴区域，期间每0.5秒以150%的攻击力对雷暴区域内的随机敌人进行一次额外攻击；可充能2次"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E6%AD%8C%E8%95%BE%E8%92%82%E5%A8%85
  public static final SkillBuilder WATERLESS_DANCE_OF_THE_SHATTERED_MAELSTROM = skill(
      "skill_waterless_dance_of_the_shattered_maelstrom",
      "缺水的碎漩狂舞"
  )
      .enUs("Waterless Dance of the Shattered Maelstrom")
      .operator(
          "歌蕾蒂娅",
          "Gladiia",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(26, 35, 8)
      .description(
          "对一个远处目标束缚并制造一个龙卷风使周围敌人移动速度-50%，每1.5秒造成130%攻击力的法术伤害并较大力度地拖拽至中心。技能结束时把目标地点周围的敌人较大力度地拖拽至面前",
          "对一个远处目标束缚并制造一个龙卷风使周围敌人移动速度-50%，每1.5秒造成130%攻击力的法术伤害并较大力度地拖拽至中心。技能结束时把目标地点周围的敌人较大力度地拖拽至面前"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E5%87%AF%E5%B0%94%E5%B8%8C
  public static final SkillBuilder COMMAND_MELTDOWN = skill(
      "skill_command_meltdown",
      "指令：熔毁"
  )
      .enUs("Command: Meltdown")
      .operator(
          "凯尔希",
          "Kal'tsit",
          SkillProfession.MEDIC
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(0, 15, 20)
      .description(
          "Mon3tr的防御力+200%，技能期间攻击力从+260%逐渐降低至+0%且伤害类型变为真实，此期间如果未击杀任何敌人则技能结束后流失最大生命的50%。该技能与Mon3tr绑定",
          "Mon3tr的防御力+200%，技能期间攻击力从+260%逐渐降低至+0%且伤害类型变为真实，此期间如果未击杀任何敌人则技能结束后流失最大生命的50%。该技能与Mon3tr绑定"
      )
      .effect(reusedEffect(SkillDemoTheme.SANCTUARY))
      .theme(SkillDemoTheme.SANCTUARY)
      .build();

  // PRTS: https://prts.wiki/w/%E6%B5%8A%E5%BF%83%E6%96%AF%E5%8D%A1%E8%92%82
  public static final SkillBuilder THE_TIDE_SURGES_THE_TIDE_RECEDES = skill(
      "skill_the_tide_surges_the_tide_recedes",
      "\"潮涌，潮枯\""
  )
      .enUs("'The Tide Surges, The Tide Recedes'")
      .operator(
          "浊心斯卡蒂",
          "Skadi the Corrupting Heart",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(15, 35, 20)
      .description(
          "特性变为自身每秒流失5%生命，使范围内所有敌人每秒受到70%攻击力的真实伤害（自身与海嗣造成的伤害可叠加），范围内所有友方单位获得相当于浊心斯卡蒂110%攻击力的鼓舞效果",
          "特性变为自身每秒流失5%生命，使范围内所有敌人每秒受到70%攻击力的真实伤害（自身与海嗣造成的伤害可叠加），范围内所有友方单位获得相当于浊心斯卡蒂110%攻击力的鼓舞效果"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E5%8D%A1%E6%B6%85%E5%88%A9%E5%AE%89
  public static final SkillBuilder MARK_OF_GLUTTONY = skill(
      "skill_mark_of_gluttony",
      "食噬之印"
  )
      .enUs("Mark of Gluttony")
      .operator(
          "卡涅利安",
          "Carnelian",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(5, 28, 21)
      .description(
          "攻击范围扩大，攻击力逐渐增至+280%；蓄力额外效果：每次攻击使目标受到来自卡涅利安的伤害提升20%（最多叠加5次），持续至技能结束",
          "攻击范围扩大，攻击力逐渐增至+280%；蓄力额外效果：每次攻击使目标受到来自卡涅利安的伤害提升20%（最多叠加5次），持续至技能结束"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E5%B8%95%E6%8B%89%E6%96%AF
  public static final SkillBuilder BLESSING_OF_HEROISM = skill(
      "skill_blessing_of_heroism",
      "英勇的祝福"
  )
      .enUs("Blessing of Heroism")
      .operator(
          "帕拉斯",
          "Pallas",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(35, 50, 30)
      .description(
          "攻击力+100%，额外攻击两个目标，身前一格为近战位并部署我方干员时使其获得以下增益：生命值高于80%获得+50%攻击力的精力充沛、防御力+35%、阻挡数+1（若身前一格不存在干员或不为近战位时，该效果由自身获得）",
          "攻击力+100%，额外攻击两个目标，身前一格为近战位并部署我方干员时使其获得以下增益：生命值高于80%获得+50%攻击力的精力充沛、防御力+35%、阻挡数+1（若身前一格不存在干员或不为近战位时，该效果由自身获得）"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E6%B0%B4%E6%9C%88
  public static final SkillBuilder MOON_IN_THE_WATER = skill(
      "skill_moon_in_the_water",
      "镜花水月"
  )
      .enUs("Moon in the Water")
      .operator(
          "水月",
          "Mizuki",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 60, 30)
      .description(
          "攻击范围扩大，攻击力+150%，第一天赋额外攻击2个目标并附加1秒晕眩；每次攻击命中目标少于3名敌人时，自身流失最大生命值的12%。",
          "攻击范围扩大，攻击力+150%，第一天赋额外攻击2个目标并附加1秒晕眩；每次攻击命中目标少于3名敌人时，自身流失最大生命值的12%。"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E5%81%87%E6%97%A5%E5%A8%81%E9%BE%99%E9%99%88
  public static final SkillBuilder HOLIDAY_STORM = skill(
      "skill_holiday_storm",
      "“假日风暴”"
  )
      .enUs("'Holiday Storm'")
      .operator(
          "假日威龙陈",
          "Ch'en the Holungday",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 55, null)
      .description(
          "攻击范围扩大，攻击力+100%，攻击造成两次伤害，对攻击范围内的所有敌人应用特性加成，每次攻击在范围内生成持续5秒的粘液，地面敌人经过时移动速度-45%、防御力-220（不叠加）；攻击装有32发弹药，每次攻击消耗2发，打完后结束（可随时停止技能）",
          "攻击范围扩大，攻击力+100%，攻击造成两次伤害，对攻击范围内的所有敌人应用特性加成，每次攻击在范围内生成持续5秒的粘液，地面敌人经过时移动速度-45%、防御力-220（不叠加）；攻击装有32发弹药，每次攻击消耗2发，打完后结束（可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E7%90%B4%E6%9F%B3
  public static final SkillBuilder GLORIOUS_BANNER = skill(
      "skill_glorious_banner",
      "光辉旗帜"
  )
      .enUs("Glorious Banner")
      .operator(
          "琴柳",
          "Saileach",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(7, 20, 10)
      .description(
          "停止攻击，立即回复10点部署费用，将军旗掷向地面敌人所在位置，并对周围造成300%的物理伤害和3.5秒晕眩；期间军旗周围8格敌人受到停顿和30%的脆弱效果。技能结束时收回军旗。",
          "停止攻击，立即回复10点部署费用，将军旗掷向地面敌人所在位置，并对周围造成300%的物理伤害和3.5秒晕眩；期间军旗周围8格敌人受到停顿和30%的脆弱效果。技能结束时收回军旗。"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E8%BF%9C%E7%89%99
  public static final SkillBuilder FEATHERSHINE_ARROWS = skill(
      "skill_feathershine_arrows",
      "光羽箭"
  )
      .enUs("Feathershine Arrows")
      .operator(
          "远牙",
          "Fartooth",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(10, 20, 20)
      .description(
          "攻击范围改为前方无限长的直线，攻击力+140%，攻击原本范围以外的目标时，造成的伤害提高至140%",
          "攻击范围改为前方无限长的直线，攻击力+140%，攻击原本范围以外的目标时，造成的伤害提高至140%"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E7%84%B0%E5%B0%BE
  public static final SkillBuilder FLAMEHEART = skill(
      "skill_flameheart",
      "焰心"
  )
      .enUs("Flameheart")
      .operator(
          "焰尾",
          "Flametail",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(6, 16, 8)
      .description(
          "技能持续时间内逐渐获得8点部署费用，攻击间隔缩短(*70%)，攻击力+90%，阻挡数+1，获得80%的物理和法术闪避",
          "技能持续时间内逐渐获得8点部署费用，攻击间隔缩短(*70%)，攻击力+90%，阻挡数+1，获得80%的物理和法术闪避"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E8%80%80%E9%AA%91%E5%A3%AB%E4%B8%B4%E5%85%89
  public static final SkillBuilder BLAZING_SUNS_OBEISANCE = skill(
      "skill_blazing_suns_obeisance",
      "耀阳颔首"
  )
      .enUs("Blazing Sun's Obeisance")
      .operator(
          "耀骑士临光",
          "Nearl the Radiant Knight",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 40, 25)
      .description(
          "在周围四格可部署地面召唤一把\"耀阳\"对周围敌人造成110%耀骑士临光攻击力的真实伤害并晕眩3秒，自身攻击范围扩大，攻击力+140%、防御力+100%，攻击自身与\"耀阳\"阻挡的单位时伤害类型变为真实",
          "在周围四格可部署地面召唤一把\"耀阳\"对周围敌人造成110%耀骑士临光攻击力的真实伤害并晕眩3秒，自身攻击范围扩大，攻击力+140%、防御力+100%，攻击自身与\"耀阳\"阻挡的单位时伤害类型变为真实"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E7%81%B5%E7%9F%A5
  public static final SkillBuilder HYPOTHERMIA = skill(
      "skill_hypothermia",
      "失温症"
  )
      .enUs("Hypothermia")
      .operator(
          "灵知",
          "Gnosis",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 40, 13)
      .description(
          "攻击速度+130，同时攻击2个敌人；范围内所有敌人的冻结延长至技能结束，且技能结束时对所有冻结的敌人造成600%的法术伤害并结束冻结；优先攻击未冻结的单位",
          "攻击速度+130，同时攻击2个敌人；范围内所有敌人的冻结延长至技能结束，且技能结束时对所有冻结的敌人造成600%的法术伤害并结束冻结；优先攻击未冻结的单位"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E8%80%81%E9%B2%A4
  public static final SkillBuilder HONORED_GUEST = skill(
      "skill_honored_guest",
      "贵客盈门"
  )
      .enUs("Honored Guest")
      .operator(
          "老鲤",
          "Lee",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.AUTO
      )
      .stats(0, 7, null)
      .description(
          "攻击范围扩大，攻击力和防御力+50%；攻击小力度推开范围内除当前目标外的敌人。自身嘲讽等级容易成为攻击目标，且有70%概率闪避来自范围外的物理或法术伤害；持续时间无限",
          "攻击范围扩大，攻击力和防御力+50%；攻击小力度推开范围内除当前目标外的敌人。自身嘲讽等级容易成为攻击目标，且有70%概率闪避来自范围外的物理或法术伤害；持续时间无限"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E4%BB%A4
  public static final SkillBuilder TO_REMAIN_ONESELF = skill(
      "skill_to_remain_oneself",
      "宁作吾"
  )
      .enUs("To Remain Oneself")
      .operator(
          "令",
          "Ling",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(15, 40, 30)
      .description(
          "被动：召唤物可部署在近战位且攻击范围内存在其他召唤物时与其合并为高级形态；开启：自身与召唤物攻击力+100%，防御力+100%，召唤物每0.5秒对周围四格敌人造成20%令攻击力的法术伤害；技能结束时获得1个召唤物",
          "被动：召唤物可部署在近战位且攻击范围内存在其他召唤物时与其合并为高级形态；开启：自身与召唤物攻击力+100%，防御力+100%，召唤物每0.5秒对周围四格敌人造成20%令攻击力的法术伤害；技能结束时获得1个召唤物"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E6%BE%84%E9%97%AA
  public static final SkillBuilder CRYSTALLINE_SHINE = skill(
      "skill_crystalline_shine",
      "澄净闪耀"
  )
      .enUs("Crystalline Shine")
      .operator(
          "澄闪",
          "Goldenglow",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(17, 35, 30)
      .description(
          "停止攻击，浮游单元+2，释放浮游单元锁定敌人攻击，攻击范围扩大至整个战场，攻击力+80%，浮游单元攻击附带0.5秒停顿；浮游单元锁敌后直至敌人被击杀、自爆或技能结束后返回干员身边",
          "停止攻击，浮游单元+2，释放浮游单元锁定敌人攻击，攻击范围扩大至整个战场，攻击力+80%，浮游单元攻击附带0.5秒停顿；浮游单元锁敌后直至敌人被击杀、自爆或技能结束后返回干员身边"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E8%8F%B2%E4%BA%9A%E6%A2%85%E5%A1%94
  public static final SkillBuilder REPONITE = skill(
      "skill_reponite",
      "“你须偿还”"
  )
      .enUs("'Reponite'")
      .operator(
          "菲亚梅塔",
          "Fiammetta",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.OFFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(0, 15, null)
      .description(
          "持续固定攻击范围内正前方最远一格，爆炸范围扩大，攻击力提升至125%，对目标位置附近小范围内的敌人攻击力额外提升至220%；持续时间无限，可主动关闭技能",
          "持续固定攻击范围内正前方最远一格，爆炸范围扩大，攻击力提升至125%，对目标位置附近小范围内的敌人攻击力额外提升至220%；持续时间无限，可主动关闭技能"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E5%8F%B7%E8%A7%92
  public static final SkillBuilder ULTIMATE_LINE_OF_DEFENSE = skill(
      "skill_ultimate_line_of_defense",
      "终极防线"
  )
      .enUs("Ultimate Line of Defense")
      .operator(
          "号角",
          "Horn",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 35, 24)
      .description(
          "攻击力+70%，攻击间隔大幅缩短(-1.8)；过载：攻击力改为+140%，逐渐流失生命（12秒后到达最大生命12%/秒）该技能可随时主动关闭",
          "攻击力+70%，攻击间隔大幅缩短(-1.8)；过载：攻击力改为+140%，逐渐流失生命（12秒后到达最大生命12%/秒）该技能可随时主动关闭"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E6%B5%81%E6%98%8E
  public static final SkillBuilder THIS_LANTERN_UNDYING = skill(
      "skill_this_lantern_undying",
      "灯火不灭"
  )
      .enUs("This Lantern Undying")
      .operator(
          "流明",
          "Lumen",
          SkillProfession.MEDIC
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(0, 50, null)
      .description(
          "攻击力+55%，攻击速度+30，优先治疗处于异常状态中的单位；只在治疗异常状态中的单位时会消耗子弹，使该次治疗量提升至攻击力的200%并解除目标所受的异常状态；攻击装有8发子弹，打完后技能结束（期间可随时停止技能）",
          "攻击力+55%，攻击速度+30，优先治疗处于异常状态中的单位；只在治疗异常状态中的单位时会消耗子弹，使该次治疗量提升至攻击力的200%并解除目标所受的异常状态；攻击装有8发子弹，打完后技能结束（期间可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.SANCTUARY))
      .theme(SkillDemoTheme.SANCTUARY)
      .build();

  // PRTS: https://prts.wiki/w/%E8%89%BE%E4%B8%BD%E5%A6%AE
  public static final SkillBuilder JUDGMENT = skill(
      "skill_judgment",
      "判决"
  )
      .enUs("Judgment")
      .operator(
          "艾丽妮",
          "Irene",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.OFFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(16, 24, null)
      .description(
          "对周围所有地面敌人造成相当于攻击力300%的物理伤害并使其浮空4秒，然后快速轰击12次，每次对随机目标周围小范围内所有敌人造成相当于攻击力250%的物理伤害",
          "对周围所有地面敌人造成相当于攻击力300%的物理伤害并使其浮空4秒，然后快速轰击12次，每次对随机目标周围小范围内所有敌人造成相当于攻击力250%的物理伤害"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E5%BD%92%E6%BA%9F%E5%B9%BD%E7%81%B5%E9%B2%A8
  public static final SkillBuilder THE_PRESSURE_TO_SURVIVE = skill(
      "skill_the_pressure_to_survive",
      "生存的重压"
  )
      .enUs("The Pressure to Survive")
      .operator(
          "归溟幽灵鲨",
          "Specter the Unchained",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 40, 25)
      .description(
          "攻击间隔延长(+1.0)，攻击所有阻挡的敌人，攻击力+260%，生命上限+200%，攻击生命比例高于或等于自身的敌人时额外造成一次攻击力70%的物理伤害，否则自身流失3%生命",
          "攻击间隔延长(+1.0)，攻击所有阻挡的敌人，攻击力+260%，生命上限+200%，攻击生命比例高于或等于自身的敌人时额外造成一次攻击力70%的物理伤害，否则自身流失3%生命"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E9%BB%91%E9%94%AE
  public static final SkillBuilder SOUND_OF_SILENCE = skill(
      "skill_sound_of_silence",
      "寂静之声"
  )
      .enUs("Sound of Silence")
      .operator(
          "黑键",
          "Ebenholz",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(10, 20, 30)
      .description(
          "攻击速度+80，攻击力+65%，只以精英或领袖敌人为攻击目标，第一天赋的伤害加成提升至原本的140%；可主动关闭技能（期间可随时停止技能）",
          "攻击速度+80，攻击力+65%，只以精英或领袖敌人为攻击目标，第一天赋的伤害加成提升至原本的140%；可主动关闭技能（期间可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E5%A4%9A%E8%90%9D%E8%A5%BF
  public static final SkillBuilder HIGH_SPEED_RESONATING_TROUBLESHOOTER = skill(
      "skill_high_speed_resonating_troubleshooter",
      "高速共振排障"
  )
      .enUs("High-speed Resonating Troubleshooter")
      .operator(
          "多萝西",
          "Dorothy",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.AUTO
      )
      .stats(0, 12, null)
      .description(
          "被动效果：陷阱触发时对范围内所有目标造成相当于攻击力350%的法术伤害，使其停顿5秒，并延迟触发范围内的其他共振装置；主动效果：立即获得一个陷阱",
          "被动效果：陷阱触发时对范围内所有目标造成相当于攻击力350%的法术伤害，使其停顿5秒，并延迟触发范围内的其他共振装置；主动效果：立即获得一个陷阱"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E9%B8%BF%E9%9B%AA
  public static final SkillBuilder OPPROBRIUM = skill(
      "skill_opprobrium",
      "锐笔速写"
  )
      .enUs("Opprobrium")
      .operator(
          "鸿雪",
          "Позёмка",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(23, 35, 30)
      .description(
          "攻击范围扩大，攻击间隔缩短(-0.6)，每次攻击的攻击力提升至200%（对正前方3格提升至255%）",
          "攻击范围扩大，攻击间隔缩短(-0.6)，每次攻击的攻击力提升至200%（对正前方3格提升至255%）"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E7%99%BE%E7%82%BC%E5%98%89%E7%BB%B4%E5%B0%94
  public static final SkillBuilder SOUL_OF_THE_JUNGLE = skill(
      "skill_soul_of_the_jungle",
      "丛林之魂"
  )
      .enUs("Soul of the Jungle")
      .operator(
          "百炼嘉维尔",
          "Gavial the Invincible",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 35, 25)
      .description(
          "攻击力+140%，攻击速度+100，阻挡数+2，技能期间暂时只受到50%的伤害，其余伤害延后至技能结束，变为持续20秒等量的生命流失效果",
          "攻击力+140%，攻击速度+100，阻挡数+2，技能期间暂时只受到50%的伤害，其余伤害延后至技能结束，变为持续20秒等量的生命流失效果"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E7%8E%9B%E6%81%A9%E7%BA%B3
  public static final SkillBuilder UNBRILLIANT_GLORY = skill(
      "skill_unbrilliant_glory",
      "未照耀的荣光"
  )
      .enUs("Unbrilliant Glory")
      .operator(
          "玛恩纳",
          "Młynar",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 42, 28)
      .description(
          "攻击范围扩大，特性提升至2倍（每击倒一名敌人时特性倍率-10%），攻击对5个目标造成相当于180%攻击力的物理伤害。范围内所有敌人受到卡西米尔干员攻击时额外附带玛恩纳12%攻击力的真实伤害",
          "攻击范围扩大，特性提升至2倍（每击倒一名敌人时特性倍率-10%），攻击对5个目标造成相当于180%攻击力的物理伤害。范围内所有敌人受到卡西米尔干员攻击时额外附带玛恩纳12%攻击力的真实伤害"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E7%99%BD%E9%93%81
  public static final SkillBuilder PROTOTYPE_OF_FEISTS_METAL_CRAB = skill(
      "skill_prototype_of_feists_metal_crab",
      "铁钳号·原型机"
  )
      .enUs("Prototype of Feist's Metal Crab")
      .operator(
          "白铁",
          "Stainless",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 35, 30)
      .description(
          "携带此技能时：装置可被我方干员攻击但不会受伤，可通过技能造成群体物理伤害但会流失生命；开启时立即获得一个装置，攻击力+55%，攻击速度+55",
          "携带此技能时：装置可被我方干员攻击但不会受伤，可通过技能造成群体物理伤害但会流失生命；开启时立即获得一个装置，攻击力+55%，攻击速度+55"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E4%BC%BA%E5%A4%9C
  public static final SkillBuilder PACKLEADERS_DIGNITY = skill(
      "skill_packleaders_dignity",
      "领袖的尊严"
  )
      .enUs("Packleader's Dignity")
      .operator(
          "伺夜",
          "Vigil",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(10, 35, 15)
      .description(
          "技能持续时间内逐渐获得12点部署费用，攻击变为三连击；狼群与伺夜攻击被狼群阻挡的单位造成伤害时，额外造成相当于伺夜攻击力50%的法术伤害",
          "技能持续时间内逐渐获得12点部署费用，攻击变为三连击；狼群与伺夜攻击被狼群阻挡的单位造成伤害时，额外造成相当于伺夜攻击力50%的法术伤害"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E6%96%A5%E7%BD%AA
  public static final SkillBuilder TRIAL_OF_THORNS = skill(
      "skill_trial_of_thorns",
      "披荆斩棘"
  )
      .enUs("Trial of Thorns")
      .operator(
          "斥罪",
          "Penance",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.DEFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(0, 20, 30)
      .description(
          "立即获得相当于生命上限130%的屏障，攻击间隔增大(+0.9)，攻击力+400%，自身更容易受到敌人攻击",
          "立即获得相当于生命上限130%的屏障，攻击间隔增大(+0.9)，攻击力+400%，自身更容易受到敌人攻击"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E7%BC%84%E9%BB%98%E5%BE%B7%E5%85%8B%E8%90%A8%E6%96%AF
  public static final SkillBuilder TORRENTIAL_SWORD_RAIN = skill(
      "skill_torrential_sword_rain",
      "剑雨滂沱"
  )
      .enUs("Torrential Sword Rain")
      .operator(
          "缄默德克萨斯",
          "Texas the Omertosa",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.PASSIVE,
          SkillTriggerType.ON_DEPLOYMENT
      )
      .stats(0, 0, 8)
      .description(
          "部署后立即对周围所有敌人造成两次相当于攻击力165%的法术伤害并使目标晕眩2秒，之后每秒释放剑雨攻击范围内最多4个不同的目标，造成攻击力130%的法术伤害和0.2秒晕眩",
          "部署后立即对周围所有敌人造成两次相当于攻击力165%的法术伤害并使目标晕眩2秒，之后每秒释放剑雨攻击范围内最多4个不同的目标，造成攻击力130%的法术伤害和0.2秒晕眩"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E7%84%B0%E5%BD%B1%E8%8B%87%E8%8D%89
  public static final SkillBuilder EMBER_OF_LIFE = skill(
      "skill_ember_of_life",
      "生命火种"
  )
      .enUs("Ember of Life")
      .operator(
          "焰影苇草",
          "Reed The Flame Shadow",
          SkillProfession.MEDIC
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 40, 30)
      .description(
          "同时攻击两名敌人，攻击力+60%，第一天赋触发几率提升至100%，技能期间附带灼痕效果的敌人每秒受到60%焰影苇草攻击力的法术伤害、被击倒时对周围敌人造成140%焰影苇草攻击力的法术伤害并施加灼痕效果；灼痕效果持续至技能结束",
          "同时攻击两名敌人，攻击力+60%，第一天赋触发几率提升至100%，技能期间附带灼痕效果的敌人每秒受到60%焰影苇草攻击力的法术伤害、被击倒时对周围敌人造成140%焰影苇草攻击力的法术伤害并施加灼痕效果；灼痕效果持续至技能结束"
      )
      .effect(reusedEffect(SkillDemoTheme.SANCTUARY))
      .theme(SkillDemoTheme.SANCTUARY)
      .build();

  // PRTS: https://prts.wiki/w/%E6%9E%97
  public static final SkillBuilder RIVING_LIGHTTAILS = skill(
      "skill_riving_lighttails",
      "流光乍裂"
  )
      .enUs("Riving Lighttails")
      .operator(
          "林",
          "Lin",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 50, 30)
      .description(
          "攻击力+200%，攻击范围与琉璃璧的破碎伤害范围扩大，琉璃璧破碎的伤害阈值提升至3倍，每次攻击若击倒敌人则使身上的琉璃璧破碎并立刻生成；可主动关闭技能（期间可随时停止技能）",
          "攻击力+200%，攻击范围与琉璃璧的破碎伤害范围扩大，琉璃璧破碎的伤害阈值提升至3倍，每次攻击若击倒敌人则使身上的琉璃璧破碎并立刻生成；可主动关闭技能（期间可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E9%87%8D%E5%B2%B3
  public static final SkillBuilder ANATTA = skill(
      "skill_anatta",
      "我无"
  )
      .enUs("Anatta")
      .operator(
          "重岳",
          "Chongyue",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.OFFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(0, 8, null)
      .description(
          "对目标及其周围造成相当于攻击力380%的物理伤害；累计使用五次技能后：重岳攻击的范围扩大且攻击变为二连击，技能变为自动释放且造成额外一次伤害",
          "对目标及其周围造成相当于攻击力380%的物理伤害；累计使用五次技能后：重岳攻击的范围扩大且攻击变为二连击，技能变为自动释放且造成额外一次伤害"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E4%BB%87%E7%99%BD
  public static final SkillBuilder QUESTIONING_SNOW = skill(
      "skill_questioning_snow",
      "问雪"
  )
      .enUs("Questioning Snow")
      .operator(
          "仇白",
          "Qiubai",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(45, 55, 30)
      .description(
          "攻击范围扩大，攻击力+55%，伤害类型变为法术，额外攻击2个目标，第一天赋的伤害提升至2倍，远程攻击不再降低伤害，每次攻击使自身攻击速度+13（最多叠加8次）",
          "攻击范围扩大，攻击力+55%，伤害类型变为法术，额外攻击2个目标，第一天赋的伤害提升至2倍，远程攻击不再降低伤害，每次攻击使自身攻击速度+13（最多叠加8次）"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E9%BA%92%E9%BA%9FR%E5%A4%9C%E5%88%80
  public static final SkillBuilder MIDAIR_SPINNING_BLADE_DANCE = skill(
      "skill_midair_spinning_blade_dance",
      "空中回旋乱舞"
  )
      .enUs("Midair Spinning Blade Dance")
      .operator(
          "麒麟R夜刀",
          "Kirin R Yato",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.PASSIVE,
          SkillTriggerType.ON_DEPLOYMENT
      )
      .stats(0, 0, null)
      .description(
          "向前突进2格，每突进一段距离都会对周围所有敌人发动攻击力300%的斩击；期间每攻击到一个敌人都会使突进距离延长（最多延长至5格，可以攻击空中单位）",
          "向前突进2格，每突进一段距离都会对周围所有敌人发动攻击力300%的斩击；期间每攻击到一个敌人都会使突进距离延长（最多延长至5格，可以攻击空中单位）"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E4%BC%8A%E5%86%85%E4%B8%9D
  public static final SkillBuilder SOLITARY_RETURN = skill(
      "skill_solitary_return",
      "独影归途"
  )
      .enUs("Solitary Return")
      .operator(
          "伊内丝",
          "Ines",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.PASSIVE,
          SkillTriggerType.ON_DEPLOYMENT
      )
      .stats(0, 0, 16)
      .description(
          "首次部署时不消耗部署费用，放置一个影哨后离场并立刻刷新再部署时间；部署后攻击力+160%，立刻收回影哨对穿过的最多6名敌人造成相当于攻击力200%的物理伤害，技能期间每对一个敌人造成伤害就获得1点部署费用",
          "首次部署时不消耗部署费用，放置一个影哨后离场并立刻刷新再部署时间；部署后攻击力+160%，立刻收回影哨对穿过的最多6名敌人造成相当于攻击力200%的物理伤害，技能期间每对一个敌人造成伤害就获得1点部署费用"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E6%B7%AC%E7%BE%BD%E8%B5%AB%E9%BB%98
  public static final SkillBuilder DREADNOUGHT_PROTOCOL = skill(
      "skill_dreadnought_protocol",
      "无畏者协议"
  )
      .enUs("Dreadnought Protocol")
      .operator(
          "淬羽赫默",
          "Silence the Paradigmatic",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 30, 60)
      .description(
          "攻击力+30%，第一天赋效果提升至1.8倍；技能期间仅一次，攻击范围内的一名干员受到致命伤时，使其生命值在10秒内不低于1；同一次作战中最多使用2次，可主动关闭（期间可随时停止技能）",
          "攻击力+30%，第一天赋效果提升至1.8倍；技能期间仅一次，攻击范围内的一名干员受到致命伤时，使其生命值在10秒内不低于1；同一次作战中最多使用2次，可主动关闭（期间可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E9%9C%8D%E5%B0%94%E6%B5%B7%E9%9B%85
  public static final SkillBuilder WELL_READ_RAVINGS = skill(
      "skill_well_read_ravings",
      "博览者的狂语"
  )
      .enUs("Well-Read Ravings")
      .operator(
          "霍尔海雅",
          "Ho'olheyak",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(45, 60, 45)
      .description(
          "攻击范围扩大，攻击间隔延长(+1.4)，攻击变为向前吹出平行的旋风，旋风伤害随行进距离正比增强并在行进三格后达到最大，使命中的第一个目标浮空2.2秒，并造成最低280%、最高420%攻击力的法术伤害",
          "攻击范围扩大，攻击间隔延长(+1.4)，攻击变为向前吹出平行的旋风，旋风伤害随行进距离正比增强并在行进三格后达到最大，使命中的第一个目标浮空2.2秒，并造成最低280%、最高420%攻击力的法术伤害"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E7%BC%AA%E5%B0%94%E8%B5%9B%E6%80%9D
  public static final SkillBuilder SUPERFICIAL_REGULATION = skill(
      "skill_superficial_regulation",
      "浅层非熵适应"
  )
      .enUs("Superficial Regulation")
      .operator(
          "缪尔赛思",
          "Muelsyse",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(18, 35, 15)
      .description(
          "立即获得15点费用，自身与流形攻击力+50%，若流形为近战复制体时每2秒对周围八格敌人向自己中心小力度地拖拽并使所有阻挡的单位持续晕眩，若为远程复制体时刷新所有流形且攻击附带持续1.5秒的束缚",
          "立即获得15点费用，自身与流形攻击力+50%，若流形为近战复制体时每2秒对周围八格敌人向自己中心小力度地拖拽并使所有阻挡的单位持续晕眩，若为远程复制体时刷新所有流形且攻击附带持续1.5秒的束缚"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E5%9C%A3%E7%BA%A6%E9%80%81%E8%91%AC%E4%BA%BA
  public static final SkillBuilder DAMNATUS_EX_FOEDERE = skill(
      "skill_damnatus_ex_foedere",
      "圣约决裁"
  )
      .enUs("Damnatus Ex Foedere")
      .operator(
          "圣约送葬人",
          "Executor the Ex Foedere",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.OFFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(12, 24, null)
      .description(
          "攻击间隔略微增大(+0.5)，攻击范围扩大，攻击力+180%，每消耗1颗弹药攻击力额外+6%（最多30层），特性的回复生命效果提高至3倍；技能结束时，对技能期间攻击过的目标造成相当于攻击力250%的物理伤害；攻击装有16发弹药，打完后结束（可随时停止技能）",
          "攻击间隔略微增大(+0.5)，攻击范围扩大，攻击力+180%，每消耗1颗弹药攻击力额外+6%（最多30层），特性的回复生命效果提高至3倍；技能结束时，对技能期间攻击过的目标造成相当于攻击力250%的物理伤害；攻击装有16发弹药，打完后结束（可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E6%8F%90%E4%B8%B0
  public static final SkillBuilder ETERNAL_HUNT = skill(
      "skill_eternal_hunt",
      "“永恒狩猎”"
  )
      .enUs("'Eternal Hunt'")
      .operator(
          "提丰",
          "Typhon",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 40, null)
      .description(
          "立刻标记攻击范围内的一名目标，攻击间隔大幅增大(+3.1)，攻击变为对标记目标发射一轮箭雨；箭雨会随机攻击标记目标周围的敌人，共造成5次相当于攻击力175%的物理伤害并使目标晕眩0.4秒；攻击装有10发弹药，打完后结束（可随时停止技能）",
          "立刻标记攻击范围内的一名目标，攻击间隔大幅增大(+3.1)，攻击变为对标记目标发射一轮箭雨；箭雨会随机攻击标记目标周围的敌人，共造成5次相当于攻击力175%的物理伤害并使目标晕眩0.4秒；攻击装有10发弹药，打完后结束（可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E7%90%B3%E7%90%85%E8%AF%97%E6%80%80%E9%9B%85
  public static final SkillBuilder LAVISH_AND_PRODIGAL = skill(
      "skill_lavish_and_prodigal",
      "千金一掷"
  )
      .enUs("Lavish and Prodigal")
      .operator(
          "琳琅诗怀雅",
          "Swire the Elegant Wit",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.AUTO
      )
      .stats(0, 5, null)
      .description(
          "攻击变为二连击，击倒敌人时获得一枚金币；主动关闭技能时消耗所有金币对前方范围内敌人随机攻击，每消耗一枚金币造成一次相当于攻击力150%的物理伤害，并将目标中等力度地向前推开；持续时间无限，可随时主动关闭技能；携带此技能时金币上限为10",
          "攻击变为二连击，击倒敌人时获得一枚金币；主动关闭技能时消耗所有金币对前方范围内敌人随机攻击，每消耗一枚金币造成一次相当于攻击力150%的物理伤害，并将目标中等力度地向前推开；持续时间无限，可随时主动关闭技能；携带此技能时金币上限为10"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E7%BA%AF%E7%83%AC%E8%89%BE%E9%9B%85%E6%B3%95%E6%8B%89
  public static final SkillBuilder VOLCANIC_ECHOES = skill(
      "skill_volcanic_echoes",
      "火山回响"
  )
      .enUs("Volcanic Echoes")
      .operator(
          "纯烬艾雅法拉",
          "Eyjafjalla the Hvít Aska",
          SkillProfession.MEDIC
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(40, 60, 50)
      .description(
          "攻击范围扩大至整个战场，治疗变为60%治疗量和元素损伤回复量的5连发，优先治疗不同的目标，第二天赋的效果提升至5倍",
          "攻击范围扩大至整个战场，治疗变为60%治疗量和元素损伤回复量的5连发，优先治疗不同的目标，第二天赋的效果提升至5倍"
      )
      .effect(reusedEffect(SkillDemoTheme.SANCTUARY))
      .theme(SkillDemoTheme.SANCTUARY)
      .build();

  // PRTS: https://prts.wiki/w/%E6%B6%A4%E7%81%AB%E6%9D%B0%E8%A5%BF%E5%8D%A1
  public static final SkillBuilder SATURATION_BURST = skill(
      "skill_saturation_burst",
      "饱和迸射"
  )
      .enUs("Saturation Burst")
      .operator(
          "涤火杰西卡",
          "Jessica the Liberated",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 40, null)
      .description(
          "攻击距离+1，攻击间隔增大(+0.6)，攻击力+310%，防御力+80%，机动盾牌防御力+170%；机动盾牌存在时，立刻向前发射一枚炮弹，命中或到达终点时对范围内所有敌人造成相当于攻击力250%的物理伤害，并使其晕眩6秒；攻击装有20发弹药，打完后结束（可随时停止技能）",
          "攻击距离+1，攻击间隔增大(+0.6)，攻击力+310%，防御力+80%，机动盾牌防御力+170%；机动盾牌存在时，立刻向前发射一枚炮弹，命中或到达终点时对范围内所有敌人造成相当于攻击力250%的物理伤害，并使其晕眩6秒；攻击装有20发弹药，打完后结束（可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E8%B5%AB%E5%BE%B7%E9%9B%B7
  public static final SkillBuilder FOG_OF_WAR_UPON_DEATHS_DOOR = skill(
      "skill_fog_of_war_upon_deaths_door",
      "死境硝烟"
  )
      .enUs("Fog of War Upon Death's Door")
      .operator(
          "赫德雷",
          "Hoederer",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(40, 50, 70)
      .description(
          "每秒流失100点生命，并使自身攻击过和攻击过自身的敌人每秒受到200点真实伤害；攻击距离+1，生命上限+60%，攻击力+120%，每次攻击回复自身5%的生命且有25%概率使目标晕眩5秒",
          "每秒流失100点生命，并使自身攻击过和攻击过自身的敌人每秒受到200点真实伤害；攻击距离+1，生命上限+60%，攻击力+120%，每次攻击回复自身5%的生命且有25%概率使目标晕眩5秒"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E6%AD%A2%E9%A2%82
  public static final SkillBuilder OATHBREAKER = skill(
      "skill_oathbreaker",
      "苦修破誓"
  )
      .enUs("Oathbreaker")
      .operator(
          "止颂",
          "Lessing",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 40, 20)
      .description(
          "自身免疫异常状态，生命值+110%，攻击被阻挡目标时，造成相当于攻击力220%的物理伤害；干员处于异常状态时可以释放技能并清除异常状态，但会对自身造成600点法术伤害",
          "自身免疫异常状态，生命值+110%，攻击被阻挡目标时，造成相当于攻击力220%的物理伤害；干员处于异常状态时可以释放技能并清除异常状态，但会对自身造成600点法术伤害"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E8%96%87%E8%96%87%E5%AE%89%E5%A8%9C
  public static final SkillBuilder FLICKER = skill(
      "skill_flicker",
      "“明灭”"
  )
      .enUs("'Flicker'")
      .operator(
          "薇薇安娜",
          "Viviana",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(18, 25, 15)
      .description(
          "攻击间隔延长(+0.5)，攻击力+110%，防御力+90%，法术抗性+25，攻击变为二连击，第二天赋的触发几率提升至2.5倍，优先攻击精英或领袖敌人；第二次及以后使用时，攻击范围扩大，攻击变为三连击，持续时间延长至25秒",
          "攻击间隔延长(+0.5)，攻击力+110%，防御力+90%，法术抗性+25，攻击变为二连击，第二天赋的触发几率提升至2.5倍，优先攻击精英或领袖敌人；第二次及以后使用时，攻击范围扩大，攻击变为三连击，持续时间延长至25秒"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E5%A1%91%E5%BF%83
  public static final SkillBuilder LIBERAL_TANGO = skill(
      "skill_liberal_tango",
      "“自由的探戈”"
  )
      .enUs("'Liberal Tango'")
      .operator(
          "塑心",
          "Virtuosa",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(40, 60, 40)
      .description(
          "停止攻击，攻击范围扩大，攻击力+180%，第二天赋效果提升至2.5倍；技能期间使攻击范围内其他干员生命上限最高的生命上限+30%、攻击力最高的攻击力+30%、防御力最高的防御力+30%",
          "停止攻击，攻击范围扩大，攻击力+180%，第二天赋效果提升至2.5倍；技能期间使攻击范围内其他干员生命上限最高的生命上限+30%、攻击力最高的攻击力+30%、防御力最高的防御力+30%"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E9%94%8F
  public static final SkillBuilder RETURN_TO_SILENCE = skill(
      "skill_return_to_silence",
      "归于宁静"
  )
      .enUs("Return To Silence")
      .operator(
          "锏",
          "Degenbrecher",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 30, null)
      .description(
          "持续发动总计10次斩击，每次斩击对最多6名敌人造成相当于攻击力235%的物理伤害，天赋的发动概率提升至100%，并持续将敌人中等力度地拖拽至自身中心，之后将造成一次相当于攻击力330%的物理伤害并将敌人较大力地拖拽至自身中心",
          "持续发动总计10次斩击，每次斩击对最多6名敌人造成相当于攻击力235%的物理伤害，天赋的发动概率提升至100%，并持续将敌人中等力度地拖拽至自身中心，之后将造成一次相当于攻击力330%的物理伤害并将敌人较大力地拖拽至自身中心"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E8%8E%B1%E4%BC%8A
  public static final SkillBuilder SEE_THE_LIGHT = skill(
      "skill_see_the_light",
      "“得见光芒”"
  )
      .enUs("'See the Light'")
      .operator(
          "莱伊",
          "Ray",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 30, 16)
      .description(
          "立即停止攻击直至子弹装满，装填间隔大幅缩短(-1.2)，攻击范围扩大，攻击造成相当于攻击力330%的物理伤害并使目标束缚2秒；技能期间若击倒敌人，技能结束时获得10点技力",
          "立即停止攻击直至子弹装满，装填间隔大幅缩短(-1.2)，攻击范围扩大，攻击造成相当于攻击力330%的物理伤害并使目标束缚2秒；技能期间若击倒敌人，技能结束时获得10点技力"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E5%B7%A6%E4%B9%90
  public static final SkillBuilder BLESSINGS_UNTO_YAN = skill(
      "skill_blessings_unto_yan",
      "佑序有炎"
  )
      .enUs("Blessings Unto Yan")
      .operator(
          "左乐",
          "Zuo Le",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(10, 25, null)
      .description(
          "立刻对前方进行7次斩击，每次对最多3名敌人造成攻击力245%的物理伤害（最后一击系数加倍且使目标晕眩5秒），期间特性的生命回复改为获得相当于回复量3倍的屏障；屏障最高叠加至最大生命的2倍，持续15秒",
          "立刻对前方进行7次斩击，每次对最多3名敌人造成攻击力245%的物理伤害（最后一击系数加倍且使目标晕眩5秒），期间特性的生命回复改为获得相当于回复量3倍的屏障；屏障最高叠加至最大生命的2倍，持续15秒"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E9%BB%8D
  public static final SkillBuilder SAMSARA = skill(
      "skill_samsara",
      "离离枯荣"
  )
      .enUs("Samsara")
      .operator(
          "黍",
          "Shu",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 45, 30)
      .description(
          "治疗范围扩大，攻击同时可治疗我方干员，攻击力+50%，有地面敌人处于播种地块时技能范围内的我方单位攻击力+25%、攻击速度+25，敌人经过播种地块时获得如下效果：当远离该地块达到2格时被传送回该地块",
          "治疗范围扩大，攻击同时可治疗我方干员，攻击力+50%，有地面敌人处于播种地块时技能范围内的我方单位攻击力+25%、攻击速度+25，敌人经过播种地块时获得如下效果：当远离该地块达到2格时被传送回该地块"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E8%89%BE%E6%8B%89
  public static final SkillBuilder THE_BOSAK_TEMPEST = skill(
      "skill_the_bosak_tempest",
      "“博萨克风暴”"
  )
      .enUs("'The Bosak Tempest'")
      .operator(
          "艾拉",
          "Ela",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(16, 34, null)
      .description(
          "被动效果：陷阱触发时使周围所有目标停顿并获得35%的脆弱效果，持续7秒；主动效果：攻击间隔缩短(-0.35)，攻击力+90%，优先攻击受到雷鸣地雷效果影响的敌人；攻击装有40发子弹，打完后技能结束（期间可随时停止技能），技能结束时获得两个陷阱",
          "被动效果：陷阱触发时使周围所有目标停顿并获得35%的脆弱效果，持续7秒；主动效果：攻击间隔缩短(-0.35)，攻击力+90%，优先攻击受到雷鸣地雷效果影响的敌人；攻击装有40发子弹，打完后技能结束（期间可随时停止技能），技能结束时获得两个陷阱"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E9%98%BF%E6%96%AF%E5%8D%A1%E7%BA%B6
  public static final SkillBuilder DESCENT = skill(
      "skill_descent",
      "降临"
  )
      .enUs("Descent")
      .operator(
          "阿斯卡纶",
          "Ascalon",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(35, 45, 45)
      .description(
          "攻击范围扩大，攻击力+50%，攻击间隔较大幅度缩短(-1.5)，使攻击范围内的地面敌人物理与法术命中率-50%。自身嘲讽等级更易受到敌人攻击，敌人未命中自身或自身闪避时回复8%最大生命值",
          "攻击范围扩大，攻击力+50%，攻击间隔较大幅度缩短(-1.5)，使攻击范围内的地面敌人物理与法术命中率-50%。自身嘲讽等级更易受到敌人攻击，敌人未命中自身或自身闪避时回复8%最大生命值"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E9%AD%94%E7%8E%8B
  public static final SkillBuilder THE_PRESENT_RECONSTRUCTED = skill(
      "skill_the_present_reconstructed",
      "编织重构现世"
  )
      .enUs("The Present Reconstructed")
      .operator(
          "魔王",
          "Civilight Eterna",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(35, 55, 30)
      .description(
          "攻击范围扩大，自身特性效果提高至90%，“微尘”不再消失，攻击范围内所有其它友方单位获得相当于魔王100%的最大生命值的鼓舞效果，每隔2秒重新分配攻击范围内所有友方单位的生命",
          "攻击范围扩大，自身特性效果提高至90%，“微尘”不再消失，攻击范围内所有其它友方单位获得相当于魔王100%的最大生命值的鼓舞效果，每隔2秒重新分配攻击范围内所有友方单位的生命"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E9%80%BB%E5%90%84%E6%96%AF
  public static final SkillBuilder EXTENDED_ACUITY = skill(
      "skill_extended_acuity",
      "延异视阈"
  )
      .enUs("Extended Acuity")
      .operator(
          "逻各斯",
          "Logos",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 45, 30)
      .description(
          "攻击范围扩大，攻击力+300%，同时攻击4个目标，使攻击范围内敌方子弹的飞行速度大幅降低、并在技能结束时将其全部清除",
          "攻击范围扩大，攻击力+300%，同时攻击4个目标，使攻击范围内敌方子弹的飞行速度大幅降低、并在技能结束时将其全部清除"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E4%B9%8C%E5%B0%94%E6%AF%94%E5%AE%89
  public static final SkillBuilder PATHS_MUST_BE_OPENED = skill(
      "skill_paths_must_be_opened",
      "必须开辟的通路"
  )
      .enUs("Paths Must Be Opened")
      .operator(
          "乌尔比安",
          "Ulpianus",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 25, 25)
      .description(
          "最大生命值+80%，攻击力+260%，立即朝面前扔出一个船锚，撞击到目标或达到最远距离时停止，并对周围所有敌人造成攻击力160%的物理伤害和6秒晕眩。若船锚停留的位置可以部署，乌尔比安会移动到该位置；可以手动结束技能，技能结束时乌尔比安会返回到初始的位置",
          "最大生命值+80%，攻击力+260%，立即朝面前扔出一个船锚，撞击到目标或达到最远距离时停止，并对周围所有敌人造成攻击力160%的物理伤害和6秒晕眩。若船锚停留的位置可以部署，乌尔比安会移动到该位置；可以手动结束技能，技能结束时乌尔比安会返回到初始的位置"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E5%A6%AE%E8%8A%99
  public static final SkillBuilder CRUMBLING_HEART = skill(
      "skill_crumbling_heart",
      "心防溃决"
  )
      .enUs("Crumbling Heart")
      .operator(
          "妮芙",
          "Nymph",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(36, 55, 35)
      .description(
          "攻击范围扩大，攻击力+220%，攻击速度+60，同时攻击2个目标，若目标处于凋亡损伤爆发期间，攻击造成元素伤害",
          "攻击范围扩大，攻击力+220%，攻击速度+60，同时攻击2个目标，若目标处于凋亡损伤爆发期间，攻击造成元素伤害"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E5%A8%9C%E4%BB%81%E5%9B%BE%E4%BA%9A
  public static final SkillBuilder SUNSWALLOWER = skill(
      "skill_sunswallower",
      "吞日"
  )
      .enUs("Sunswallower")
      .operator(
          "娜仁图亚",
          "Narantuya",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 30, 20)
      .description(
          "每次攻击发射3个回旋投射物（每个造成相当于攻击力175%的物理伤害），投射物全部回收时娜仁图亚对周围8格内至多3名敌人造成一次相当于攻击力160%的物理伤害并使其停顿1秒",
          "每次攻击发射3个回旋投射物（每个造成相当于攻击力175%的物理伤害），投射物全部回收时娜仁图亚对周围8格内至多3名敌人造成一次相当于攻击力160%的物理伤害并使其停顿1秒"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E4%BD%A9%E4%BD%A9
  public static final SkillBuilder SHOCK_OF_TIME = skill(
      "skill_shock_of_time",
      "时光震荡"
  )
      .enUs("Shock of Time")
      .operator(
          "佩佩",
          "Pepe",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(35, 45, 40)
      .description(
          "攻击间隔略微增大(+0.2)，攻击力+240%，攻击使命中的目标晕眩0.8秒，对主目标提升至1.5秒，每次攻击后使溅射范围扩大且攻击力额外+25%，最多叠加4层",
          "攻击间隔略微增大(+0.2)，攻击力+240%，攻击使命中的目标晕眩0.8秒，对主目标提升至1.5秒，每次攻击后使溅射范围扩大且攻击力额外+25%，最多叠加4层"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E7%8E%9B%E9%9C%B2%E8%A5%BF%E5%B0%94
  public static final SkillBuilder EXPLOSION_MAGIC = skill(
      "skill_explosion_magic",
      "爆破魔法"
  )
      .enUs("Explosion Magic")
      .operator(
          "玛露西尔",
          "Marcille",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(40, 80, null)
      .description(
          "吟唱5秒后，消耗8点魔力，在正前方位置造成爆炸，对周围敌人造成390%攻击力的法术伤害，炸到的高台会崩开碎片晕眩其周围敌人4秒；可追加吟唱10秒，完成后消耗剩余所有魔力，每额外消耗8点魔力，追加1次爆炸；追加吟唱可随时停止",
          "吟唱5秒后，消耗8点魔力，在正前方位置造成爆炸，对周围敌人造成390%攻击力的法术伤害，炸到的高台会崩开碎片晕眩其周围敌人4秒；可追加吟唱10秒，完成后消耗剩余所有魔力，每额外消耗8点魔力，追加1次爆炸；追加吟唱可随时停止"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E7%BB%B4%E5%A8%9C%C2%B7%E7%BB%B4%E5%A4%9A%E5%88%A9%E4%BA%9A
  public static final SkillBuilder AND_ALL_BE_IN_MY_NAME = skill(
      "skill_and_all_be_in_my_name",
      "俱以我之名"
  )
      .enUs("And All Be In My Name")
      .operator(
          "维娜·维多利亚",
          "Vina Victoria",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(35, 50, 25)
      .description(
          "立即在天赋一生效范围内可部署地面召唤“黄金盟誓”；技能期间可攻击被天赋一生效范围友方单位阻挡的敌人，攻击力+190%，攻击目标数+3，攻击间隔缩短(-0.25)，攻击时伤害类型变为真实",
          "立即在天赋一生效范围内可部署地面召唤“黄金盟誓”；技能期间可攻击被天赋一生效范围友方单位阻挡的敌人，攻击力+190%，攻击目标数+3，攻击间隔缩短(-0.25)，攻击时伤害类型变为真实"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E5%BC%91%E5%90%9B%E8%80%85
  public static final SkillBuilder SMOKESCREEN_EXECUTION = skill(
      "skill_smokescreen_execution",
      "烽烟行刑场"
  )
      .enUs("Smokescreen Execution")
      .operator(
          "弑君者",
          "Crownslayer",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.PASSIVE,
          SkillTriggerType.ON_DEPLOYMENT
      )
      .stats(0, 0, 16)
      .description(
          "部署后第一天赋的生效范围扩大，弑君者消失在烟雾中，获得隐匿且阻挡数变为0，每2秒现身对烟雾内的一名地面敌人造成两次相当于攻击力250%的物理伤害并使目标晕眩4秒（对同一目标6秒内只会触发一次）",
          "部署后第一天赋的生效范围扩大，弑君者消失在烟雾中，获得隐匿且阻挡数变为0，每2秒现身对烟雾内的一名地面敌人造成两次相当于攻击力250%的物理伤害并使目标晕眩4秒（对同一目标6秒内只会触发一次）"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E5%BF%8D%E5%86%AC
  public static final SkillBuilder LA_VOLPE_NASCOSTA = skill(
      "skill_la_volpe_nascosta",
      "隐狐之艺"
  )
      .enUs("La Volpe Nascosta")
      .operator(
          "忍冬",
          "Vulpisfoglia",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(10, 18, 10)
      .description(
          "技能开启时立即获得9点部署费用，攻击范围扩大，攻击力+110%，攻击速度从+180逐渐衰减至+0，同时攻击阻挡的所有敌人，每次攻击晕眩敌人0.2秒；若技能期间击倒敌人，则技能结束时进入迷彩状态，直至下一次开启技能",
          "技能开启时立即获得9点部署费用，攻击范围扩大，攻击力+110%，攻击速度从+180逐渐衰减至+0，同时攻击阻挡的所有敌人，每次攻击晕眩敌人0.2秒；若技能期间击倒敌人，则技能结束时进入迷彩状态，直至下一次开启技能"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E8%8D%92%E8%8A%9C%E6%8B%89%E6%99%AE%E5%85%B0%E5%BE%B7
  public static final SkillBuilder FINALE_CATASTROFE = skill(
      "skill_finale_catastrofe",
      "终幕·浩劫"
  )
      .enUs("Finale: Catastrofe")
      .operator(
          "荒芜拉普兰德",
          "Lappland the Decadenza",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(38, 54, 40)
      .description(
          "浮游单元+2，攻击力+80%，释放特殊形态的浮游单元散开后在整个战场范围各自追逐较近的敌人，追上时使目标恐惧3秒并锁定其攻击，浮游单元周围的敌人移动速度-50%且每秒受到相当于攻击力120%的法术伤害（不叠加）；浮游单元在锁定目标倒下后重新索敌，直至技能结束后返回干员身边",
          "浮游单元+2，攻击力+80%，释放特殊形态的浮游单元散开后在整个战场范围各自追逐较近的敌人，追上时使目标恐惧3秒并锁定其攻击，浮游单元周围的敌人移动速度-50%且每秒受到相当于攻击力120%的法术伤害（不叠加）；浮游单元在锁定目标倒下后重新索敌，直至技能结束后返回干员身边"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E5%BC%95%E6%98%9F%E6%A3%98%E5%88%BA
  public static final SkillBuilder ZONA_MAR_TIMA_PERSONAL = skill(
      "skill_zona_mar_tima_personal",
      "“我的海疆”"
  )
      .enUs("'Zona Marítima Personal'")
      .operator(
          "引星棘刺",
          "Thorns the Lodestar",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(48, 60, null)
      .description(
          "被动效果：攻击范围扩大；主动开启：向阻挡数最低的4名干员投掷炼金单元，在23秒内使围绕区域内的敌人攻击力-15%，防御力-35%，法术抗性-35%（不叠加），每秒受到攻击力210%的法术伤害，效果逐渐提升（15秒后达到最大，攻击力-30%，防御力-50%，法术抗性-50%，每秒伤害390%）",
          "被动效果：攻击范围扩大；主动开启：向阻挡数最低的4名干员投掷炼金单元，在23秒内使围绕区域内的敌人攻击力-15%，防御力-35%，法术抗性-35%（不叠加），每秒受到攻击力210%的法术伤害，效果逐渐提升（15秒后达到最大，攻击力-30%，防御力-50%，法术抗性-50%，每秒伤害390%）"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E7%83%9B%E7%85%8C
  public static final SkillBuilder PYRE_OF_PERDITION = skill(
      "skill_pyre_of_perdition",
      "众恶的焚场"
  )
      .enUs("Pyre of Perdition")
      .operator(
          "烛煌",
          "Blaze the Igniting Spark",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 30, null)
      .description(
          "攻击范围改变，攻击力+115%，攻击间隔大幅缩短(-1.3)，攻击变为群体攻击；若目标处于灼燃损伤爆发期间则额外造成攻击力80%的元素伤害；自身每秒流失最大生命的3%，攻击装有25发弹药，全场有敌人进入灼燃损伤爆发时获得2颗额外弹药，弹药打完后结束（可随时停止技能）",
          "攻击范围改变，攻击力+115%，攻击间隔大幅缩短(-1.3)，攻击变为群体攻击；若目标处于灼燃损伤爆发期间则额外造成攻击力80%的元素伤害；自身每秒流失最大生命的3%，攻击装有25发弹药，全场有敌人进入灼燃损伤爆发时获得2颗额外弹药，弹药打完后结束（可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E4%BD%99
  public static final SkillBuilder ENTER_THE_STOVES_PALM = skill(
      "skill_enter_the_stoves_palm",
      "灶里乾坤"
  )
      .enUs("Enter the Stove's Palm")
      .operator(
          "余",
          "Yu",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(42, 55, 45)
      .description(
          "生命上限+110%，攻击力+110%，防御力+110%，将第二天赋效果赋予全场所有干员；生成一面跨越整个战场的火墙，其他友方穿过火墙造成法术伤害时附带相当于余攻击力10%的灼燃损伤，敌方子弹穿过火墙时有20%几率被清除",
          "生命上限+110%，攻击力+110%，防御力+110%，将第二天赋效果赋予全场所有干员；生成一面跨越整个战场的火墙，其他友方穿过火墙造成法术伤害时附带相当于余攻击力10%的灼燃损伤，敌方子弹穿过火墙时有20%几率被清除"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E9%9A%90%E5%BE%B7%E6%9D%A5%E5%B8%8C
  public static final SkillBuilder CLINGING_DESIRE_LONGING_SPIRIT = skill(
      "skill_clinging_desire_longing_spirit",
      "灵与欲的惜别"
  )
      .enUs("Clinging Desire, Longing Spirit")
      .operator(
          "隐德来希",
          "Entelechia",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(22, 30, 20)
      .description(
          "攻击范围扩大，攻击力+135%，攻击速度+100，立刻为攻击范围内最多3名生命值最高的地面敌人召唤对应的心烛，每次攻击对心烛至少造成35%攻击力的伤害，心烛继承原敌人当前60%的生命值，受到伤害时原敌人也会受到等量的真实伤害；心烛只受隐德来希攻击的影响",
          "攻击范围扩大，攻击力+135%，攻击速度+100，立刻为攻击范围内最多3名生命值最高的地面敌人召唤对应的心烛，每次攻击对心烛至少造成35%攻击力的伤害，心烛继承原敌人当前60%的生命值，受到伤害时原敌人也会受到等量的真实伤害；心烛只受隐德来希攻击的影响"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E6%AD%BB%E8%8A%92
  public static final SkillBuilder CROWN_THE_DEAD = skill(
      "skill_crown_the_dead",
      "冠死以冕"
  )
      .enUs("Crown the Dead")
      .operator(
          "死芒",
          "Necrass",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(5, 16, null)
      .description(
          "被动：最多1名悲叹的仆役变为特殊形态，此形态下只能通过技能升级，最多升级6次；开启：立刻对范围内所有敌人造成攻击力800%的法术伤害，之后消耗一个悲叹的仆役使特殊仆役升级并回复20%生命（若悲叹的仆役已升级则翻倍），重复3次",
          "被动：最多1名悲叹的仆役变为特殊形态，此形态下只能通过技能升级，最多升级6次；开启：立刻对范围内所有敌人造成攻击力800%的法术伤害，之后消耗一个悲叹的仆役使特殊仆役升级并回复20%生命（若悲叹的仆役已升级则翻倍），重复3次"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/Mon3tr
  public static final SkillBuilder STRATAGEM_MELTDOWN = skill(
      "skill_stratagem_meltdown",
      "策略：熔毁"
  )
      .enUs("Stratagem: Meltdown")
      .operator(
          "Mon3tr",
          "Mon3tr",
          SkillProfession.MEDIC
      )
      .activation(
          SkillSpRecoveryType.OFFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(11, 15, 25)
      .description(
          "攻击范围改变，移动至重构体位置，攻击力+330%，攻击间隔降低(-1.5)，阻挡数+2，生命上限+5000，每秒流失80点生命值，同时攻击阻挡的所有敌人，伤害类型变为真实，攻击治疗自身相当于攻击力50%的生命值；技能结束或受到致命伤害时，返回初始位置",
          "攻击范围改变，移动至重构体位置，攻击力+330%，攻击间隔降低(-1.5)，阻挡数+2，生命上限+5000，每秒流失80点生命值，同时攻击阻挡的所有敌人，伤害类型变为真实，攻击治疗自身相当于攻击力50%的生命值；技能结束或受到致命伤害时，返回初始位置"
      )
      .effect(reusedEffect(SkillDemoTheme.SANCTUARY))
      .theme(SkillDemoTheme.SANCTUARY)
      .build();

  // PRTS: https://prts.wiki/w/%E4%BF%A1%E4%BB%B0%E6%90%85%E6%8B%8C%E6%9C%BA
  public static final SkillBuilder PRE_RETIREMENT_PREACHING = skill(
      "skill_pre_retirement_preaching",
      "退休前布道"
  )
      .enUs("Pre-Retirement Preaching")
      .operator(
          "信仰搅拌机",
          "Sankta Miksaparato",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 35, null)
      .description(
          "技能开启时为所有其他【拉特兰】干员补弹4发；攻击范围扩大，生命上限+80%，攻击力+220%，防御力+80%，停止主动攻击敌人，受到攻击时立即向攻击范围内最多3个敌人进行1次反击，反击最小间隔为实际攻击间隔的10%；攻击装有30发弹药，打完后结束（可随时停止技能）",
          "技能开启时为所有其他【拉特兰】干员补弹4发；攻击范围扩大，生命上限+80%，攻击力+220%，防御力+80%，停止主动攻击敌人，受到攻击时立即向攻击范围内最多3个敌人进行1次反击，反击最小间隔为实际攻击间隔的10%；攻击装有30发弹药，打完后结束（可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E8%95%BE%E7%BC%AA%E5%AE%89
  public static final SkillBuilder SALUTATIO_SCLOPETI_MEMENTO_MORI = skill(
      "skill_salutatio_sclopeti_memento_mori",
      "礼炮·强制追思"
  )
      .enUs("Salutatio Sclopeti: Memento Mori")
      .operator(
          "蕾缪安",
          "Lemuen",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(32, 38, null)
      .description(
          "停止攻击，每0.5秒消耗1发弹药依次锁定敌人，技能结束时对每名被锁定的敌人所在位置进行轰炸，轰炸造成攻击力300%的范围物理伤害（中心伤害提升至攻击力的450%）；攻击装有5发弹药，打完后结束（可随时停止技能）",
          "停止攻击，每0.5秒消耗1发弹药依次锁定敌人，技能结束时对每名被锁定的敌人所在位置进行轰炸，轰炸造成攻击力300%的范围物理伤害（中心伤害提升至攻击力的450%）；攻击装有5发弹药，打完后结束（可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E6%96%B0%E7%BA%A6%E8%83%BD%E5%A4%A9%E4%BD%BF
  public static final SkillBuilder GUARANTEED_SUCCESS = skill(
      "skill_guaranteed_success",
      "使命必达！"
  )
      .enUs("Guaranteed Success!")
      .operator(
          "新约能天使",
          "Exusiai the New Covenant",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 35, null)
      .description(
          "攻击力+30%，每次攻击变为相当于攻击力160%的5连击，若存在投递坐标，立刻对该处造成一次相当于攻击力250%的物理溅射伤害并将一名再部署时间最长的地面干员部署至该处，使其获得6点技力；部署后获得投递坐标，攻击装有50发弹药，每次攻击消耗5发，打完后结束（可随时停止技能）",
          "攻击力+30%，每次攻击变为相当于攻击力160%的5连击，若存在投递坐标，立刻对该处造成一次相当于攻击力250%的物理溅射伤害并将一名再部署时间最长的地面干员部署至该处，使其获得6点技力；部署后获得投递坐标，攻击装有50发弹药，每次攻击消耗5发，打完后结束（可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E9%85%92%E7%A5%9E
  public static final SkillBuilder LE_TH_TRE_DU_VIDE = skill(
      "skill_le_th_tre_du_vide",
      "空剧场"
  )
      .enUs("Le Théâtre du Vide")
      .operator(
          "酒神",
          "Tragodia",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 40, 30)
      .description(
          "攻击范围扩大，攻击力+125%，优先攻击未处于损伤爆发期间的敌人，技能期间酒神造成过神经损伤的敌人每秒受到攻击力10%的神经损伤直至爆发；范围内敌人神经损伤冷却恢复速度+50%，神经损伤爆发时在其地面位置生成/刷新一个迷狂牢笼",
          "攻击范围扩大，攻击力+125%，优先攻击未处于损伤爆发期间的敌人，技能期间酒神造成过神经损伤的敌人每秒受到攻击力10%的神经损伤直至爆发；范围内敌人神经损伤冷却恢复速度+50%，神经损伤爆发时在其地面位置生成/刷新一个迷狂牢笼"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E5%8F%B8%E9%9C%86%E6%83%8A%E8%9B%B0
  public static final SkillBuilder ALL_THE_THUNDERLIGHT = skill(
      "skill_all_the_thunderlight",
      "天地通明"
  )
      .enUs("All the Thunderlight")
      .operator(
          "司霆惊蛰",
          "Leizi the Thunderbringer",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(21, 36, 24)
      .description(
          "攻击范围扩大，攻击间隔大幅延长(+1.7)，攻击造成攻击力300%的范围物理伤害，目标位置产生朝四周流动三格的电流。电流碰到自身或高台时反弹，电流所在地块上所有敌人每0.6秒受到司霆惊蛰攻击力70%的法术伤害，且有15%概率战栗3秒",
          "攻击范围扩大，攻击间隔大幅延长(+1.7)，攻击造成攻击力300%的范围物理伤害，目标位置产生朝四周流动三格的电流。电流碰到自身或高台时反弹，电流所在地块上所有敌人每0.6秒受到司霆惊蛰攻击力70%的法术伤害，且有15%概率战栗3秒"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E7%94%B5%E5%BC%A7
  public static final SkillBuilder HAND_IN_HAND = skill(
      "skill_hand_in_hand",
      "手牵手"
  )
      .enUs("Hand in Hand")
      .operator(
          "电弧",
          "Raidian",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 40, 30)
      .description(
          "被动：召唤物可部署在远程位，召唤物找不到攻击目标且可攻击时，可以与其自身范围内其他电弧的召唤物协同攻击；开启：自身和召唤物攻击力+150%，对敌人造成伤害时，使敌人受到停顿与35％法术脆弱效果，持续2秒；技能开启时获得1个召唤物",
          "被动：召唤物可部署在远程位，召唤物找不到攻击目标且可攻击时，可以与其自身范围内其他电弧的召唤物协同攻击；开启：自身和召唤物攻击力+150%，对敌人造成伤害时，使敌人受到停顿与35％法术脆弱效果，持续2秒；技能开启时获得1个召唤物"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E9%81%A5
  public static final SkillBuilder THE_FINS_OF_SUMMER = skill(
      "skill_the_fins_of_summer",
      "夏末游鳞"
  )
      .enUs("The Fins of Summer")
      .operator(
          "遥",
          "Haruka",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(53, 60, 40)
      .description(
          "攻击力+55%，攻击范围扩大，攻击间隔缩短(-0.6)，技能期间浮泡提供的庇护提升至2倍；攻击我方浮泡的敌人将浮空4秒，在此浮空期间每秒受到相当于遥攻击力80%的法术伤害",
          "攻击力+55%，攻击范围扩大，攻击间隔缩短(-0.6)，技能期间浮泡提供的庇护提升至2倍；攻击我方浮泡的敌人将浮空4秒，在此浮空期间每秒受到相当于遥攻击力80%的法术伤害"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E6%96%A9%E4%B8%9A%E6%98%9F%E7%86%8A
  public static final SkillBuilder HELL_ITSELF = skill(
      "skill_hell_itself",
      "地狱变相"
  )
      .enUs("Hell Itself")
      .operator(
          "斩业星熊",
          "Hoshiguma the Breacher",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(37, 45, 32)
      .description(
          "攻击范围扩大，生命上限+100%，攻击力+230%，以二连击攻击最多3名敌人；主动关闭技能后11秒内保留技能加成且不会被击倒，攻击变为四连击，承担攻击范围内我方干员受到的致命伤害，11秒后强制退出战场；可主动关闭（期间可随时停止技能）",
          "攻击范围扩大，生命上限+100%，攻击力+230%，以二连击攻击最多3名敌人；主动关闭技能后11秒内保留技能加成且不会被击倒，攻击变为四连击，承担攻击范围内我方干员受到的致命伤害，11秒后强制退出战场；可主动关闭（期间可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E4%B8%B0%E5%B7%9D%E7%A5%A5%E5%AD%90
  public static final SkillBuilder CRESCENT_MOON_ECHOES = skill(
      "skill_crescent_moon_echoes",
      "残月的余响"
  )
      .enUs("Crescent Moon Echoes")
      .operator(
          "丰川祥子",
          "Togawa Sakiko",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.OFFENSIVE_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(34, 42, 25)
      .description(
          "攻击范围扩大，攻击同时使用钢琴和风琴音色演奏，各自演奏2个造成相当于攻击力220%物理和法术伤害的音符，且分别追踪法术抗性和防御力最高的敌人。；Fever期间Ave Mujica成员受到致命伤害时不撤退，Fever结束后退场",
          "攻击范围扩大，攻击同时使用钢琴和风琴音色演奏，各自演奏2个造成相当于攻击力220%物理和法术伤害的音符，且分别追踪法术抗性和防御力最高的敌人。；Fever期间Ave Mujica成员受到致命伤害时不撤退，Fever结束后退场"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E7%9C%9F%E8%A8%80
  public static final SkillBuilder TRUTH_UNCHANTED = skill(
      "skill_truth_unchanted",
      "无言为真"
  )
      .enUs("Truth Unchanted")
      .operator(
          "真言",
          "Mantra",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(31, 40, 40)
      .description(
          "攻击范围扩大，范围内敌人隐匿失效且麻痹层数上限降为2，攻击力+275%同时攻击2个目标，范围中干员开启技能时（包括自身）使范围内敌人获得2层麻痹（至多3次），范围内敌人麻痹层数每超过上限1层在目标及1名敌人间跳跃1次185%攻击力的元素伤害，跳跃间存在短暂间隔",
          "攻击范围扩大，范围内敌人隐匿失效且麻痹层数上限降为2，攻击力+275%同时攻击2个目标，范围中干员开启技能时（包括自身）使范围内敌人获得2层麻痹（至多3次），范围内敌人麻痹层数每超过上限1层在目标及1名敌人间跳跃1次185%攻击力的元素伤害，跳跃间存在短暂间隔"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E6%BA%AF%E5%85%89%E6%98%9F%E6%BA%90
  public static final SkillBuilder FLUX_CHAIN = skill(
      "skill_flux_chain",
      "并流连锁"
  )
      .enUs("Flux Chain")
      .operator(
          "溯光星源",
          "Astgenne the Lightchaser",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 35, 25)
      .description(
          "攻击范围扩大，攻击力+70%，攻击间隔缩短(-0.7)，持续锁定2个敌人进行攻击，被锁定的敌人会互相链接；每个被链接的敌人会把即将受到法术伤害的25%额外传导给其他链接目标",
          "攻击范围扩大，攻击力+70%，攻击间隔缩短(-0.7)，持续锁定2个敌人进行攻击，被锁定的敌人会互相链接；每个被链接的敌人会把即将受到法术伤害的25%额外传导给其他链接目标"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E5%9C%A3%E8%81%86%E5%88%9D%E9%9B%AA
  public static final SkillBuilder TOWARDS_THE_MOUNTAINS_BOW = skill(
      "skill_towards_the_mountains_bow",
      "群山俯首"
  )
      .enUs("Towards the Mountains, Bow")
      .operator(
          "圣聆初雪",
          "Pramanix the Prerita",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(42, 50, 35)
      .description(
          "攻击范围扩大，立即诱导攻击范围内的所有敌人至自身周围的可达地面，持续10秒，积雪生成速度加快，攻击力+100%，攻击速度+30，攻击无视目标10点法术抗性，每次攻击造成相当于攻击力260%的法术伤害",
          "攻击范围扩大，立即诱导攻击范围内的所有敌人至自身周围的可达地面，持续10秒，积雪生成速度加快，攻击力+100%，攻击速度+30，攻击无视目标10点法术抗性，每次攻击造成相当于攻击力260%的法术伤害"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E5%87%9B%E5%BE%A1%E9%93%B6%E7%81%B0
  public static final SkillBuilder CASTLED_KING = skill(
      "skill_castled_king",
      "变革已至"
  )
      .enUs("Castled King")
      .operator(
          "凛御银灰",
          "SilverAsh the Reignfrost",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(46, 55, 48)
      .description(
          "攻击范围扩大并使敌人隐匿失效，攻击对直线范围造成攻击力200%的物理伤害和30%的脆弱；立即获得9点后持续获得24点部署费用；首次开启时交换待部署区中费用最高（优先选择近卫/术师/狙击干员）与最低干员的基础部署费用；技能期间“风雪之眼”变为可部署",
          "攻击范围扩大并使敌人隐匿失效，攻击对直线范围造成攻击力200%的物理伤害和30%的脆弱；立即获得9点后持续获得24点部署费用；首次开启时交换待部署区中费用最高（优先选择近卫/术师/狙击干员）与最低干员的基础部署费用；技能期间“风雪之眼”变为可部署"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E5%A8%9C%E6%96%AF%E6%8F%90
  public static final SkillBuilder ROOSTING_GROUNDS = skill(
      "skill_roosting_grounds",
      "栖脚地"
  )
      .enUs("Roosting Grounds")
      .operator(
          "娜斯提",
          "Nasti",
          SkillProfession.SUPPORTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(17, 25, 15)
      .description(
          "携带此技能时：装置可建造并升级一个可以放置远程干员并为其提供增益的特殊高台；攻击力+160%，防御力+160%；小工程师装置为高台装置回复技力和生命的效果翻倍；技能开启时获得一个装置",
          "携带此技能时：装置可建造并升级一个可以放置远程干员并为其提供增益的特殊高台；攻击力+160%，防御力+160%；小工程师装置为高台装置回复技力和生命的效果翻倍；技能开启时获得一个装置"
      )
      .effect(reusedEffect(SkillDemoTheme.SLOWING_FIELD))
      .theme(SkillDemoTheme.SLOWING_FIELD)
      .build();

  // PRTS: https://prts.wiki/w/%E7%BC%87%E7%BC%87
  public static final SkillBuilder HISTORYS_BLOOM = skill(
      "skill_historys_bloom",
      "旧日绽放"
  )
      .enUs("History's Bloom")
      .operator(
          "缇缇",
          "Titi",
          SkillProfession.MEDIC
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(28, 37, 25)
      .description(
          "攻击力+180%，同时攻击两名敌人，攻击非沉睡目标时使其沉睡5秒，技能期间，全场敌人从沉睡状态醒来或于沉睡中被击倒时，受到法术伤害（随沉睡时长提高至攻击力460%），并使周围一名其他敌人沉睡5秒，攻击范围内的其他友方干员受到致命伤害时，陷入沉睡至生命值完全恢复或技能结束",
          "攻击力+180%，同时攻击两名敌人，攻击非沉睡目标时使其沉睡5秒，技能期间，全场敌人从沉睡状态醒来或于沉睡中被击倒时，受到法术伤害（随沉睡时长提高至攻击力460%），并使周围一名其他敌人沉睡5秒，攻击范围内的其他友方干员受到致命伤害时，陷入沉睡至生命值完全恢复或技能结束"
      )
      .effect(reusedEffect(SkillDemoTheme.SANCTUARY))
      .theme(SkillDemoTheme.SANCTUARY)
      .build();

  // PRTS: https://prts.wiki/w/%E8%B5%A4%E5%88%83%E6%98%8E%E9%9C%84%E9%99%88
  public static final SkillBuilder CHI_XIAO_HEAVENS_PLAINT = skill(
      "skill_chi_xiao_heavens_plaint",
      "赤霄·天喟"
  )
      .enUs("Chi Xiao - Heaven's Plaint")
      .operator(
          "赤刃明霄陈",
          "Ch'en the Dawnstreak",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(18, 25, 20)
      .description(
          "技能开启时向前释放一道可转向的剑气，对穿过的敌人造成相当于其当前生命值6%的法术伤害（至少造成自身攻击力580%的法术伤害）；攻击范围扩大，每次攻击对最多4名地面敌人造成3次攻击力210%的法术伤害",
          "技能开启时向前释放一道可转向的剑气，对穿过的敌人造成相当于其当前生命值6%的法术伤害（至少造成自身攻击力580%的法术伤害）；攻击范围扩大，每次攻击对最多4名地面敌人造成3次攻击力210%的法术伤害"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E6%9C%9B
  public static final SkillBuilder ALL_DOMINATING_FIGHT = skill(
      "skill_all_dominating_fight",
      "天下劫"
  )
      .enUs("All-Dominating Fight")
      .operator(
          "望",
          "Wang",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(38, 50, null)
      .description(
          "被动效果：棋子的触发和伤害范围扩大，造成相当于攻击力380%的法术伤害；主动效果：停止攻击但攻击范围扩大；立即获得8枚棋子，然后将超出上限的棋子优先部署在范围内敌人所在位置；在攻击范围内手动部署棋子时可部署至敌人所在位置，且第一天赋额外至多部署3枚棋子并消耗等量弹药；装有20发弹药，手动停止或棋子耗尽后技能结束，剩余的弹药返还为棋子",
          "被动效果：棋子的触发和伤害范围扩大，造成相当于攻击力380%的法术伤害；主动效果：停止攻击但攻击范围扩大；立即获得8枚棋子，然后将超出上限的棋子优先部署在范围内敌人所在位置；在攻击范围内手动部署棋子时可部署至敌人所在位置，且第一天赋额外至多部署3枚棋子并消耗等量弹药；装有20发弹药，手动停止或棋子耗尽后技能结束，剩余的弹药返还为棋子"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();

  // PRTS: https://prts.wiki/w/%E8%B4%9D%E6%B4%9B%E5%86%85
  public static final SkillBuilder RISCOSSIONE = skill(
      "skill_riscossione",
      "清算"
  )
      .enUs("Riscossione")
      .operator(
          "贝洛内",
          "Bellone",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(27, 35, 30)
      .description(
          "攻击力+170%，攻击速度+50，攻击时有50%的几率造成相当于攻击力185%的物理伤害；技能开启后立即选取自身周围一定范围内的一名地面目标，若其所在位置可部署，自身会移动至目标所在位置进行攻击；自身攻击的精英、领袖敌人被击倒，或自身持续1秒未进行攻击时，会立即重新选取目标；受到致命伤害时不撤退但会结束技能，技能结束后返回初始位置",
          "攻击力+170%，攻击速度+50，攻击时有50%的几率造成相当于攻击力185%的物理伤害；技能开启后立即选取自身周围一定范围内的一名地面目标，若其所在位置可部署，自身会移动至目标所在位置进行攻击；自身攻击的精英、领袖敌人被击倒，或自身持续1秒未进行攻击时，会立即重新选取目标；受到致命伤害时不撤退但会结束技能，技能结束后返回初始位置"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E6%80%92%E6%BD%AE%E5%87%9B%E5%86%AC
  public static final SkillBuilder ZIMA_THE_RAGING_TIDE_THIRD_SKILL = skill(
      "skill_zima_the_raging_tide_third_skill",
      "无可抵挡"
  )
      .enUs("无可抵挡")
      .operator(
          "怒潮凛冬",
          "Zima the Raging Tide",
          SkillProfession.GUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(20, 27, null)
      .description(
          "攻击力+100%，对前方一格持续进行五次溅射范围更大的锤击，每次造成相当于攻击力240%的物理伤害并使攻击力额外+30%；技能期间触发第一天赋的高台会向四周高台扩散该效果（扩散次数随攻击次数递增），并使伤害效果提升至3.5倍，控制效果变为2秒束缚",
          "攻击力+100%，对前方一格持续进行五次溅射范围更大的锤击，每次造成相当于攻击力240%的物理伤害并使攻击力额外+30%；技能期间触发第一天赋的高台会向四周高台扩散该效果（扩散次数随攻击次数递增），并使伤害效果提升至3.5倍，控制效果变为2秒束缚"
      )
      .effect(reusedEffect(SkillDemoTheme.AREA_SLASH))
      .theme(SkillDemoTheme.AREA_SLASH)
      .build();

  // PRTS: https://prts.wiki/w/%E7%BB%B4%E4%BC%8A
  public static final SkillBuilder OPERATOR_415_THIRD_SKILL = skill(
      "skill_operator_415_third_skill",
      "“用赤铁铭记”"
  )
      .enUs("“用赤铁铭记”")
      .operator(
          "维伊",
          "Вий",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(28, 45, null)
      .description(
          "攻击速度+180，储存的能量发射后会在敌人间（优先不同目标）跳跃一次，并造成相当于165%攻击力的法术伤害，转置能量改为跳跃3次；攻击装有27发弹药，仅在发射每个储存能量时消耗1发弹药，发射转置能量消耗3发弹药，弹药打完后结束（可随时停止技能）",
          "攻击速度+180，储存的能量发射后会在敌人间（优先不同目标）跳跃一次，并造成相当于165%攻击力的法术伤害，转置能量改为跳跃3次；攻击装有27发弹药，仅在发射每个储存能量时消耗1发弹药，发射转置能量消耗3发弹药，弹药打完后结束（可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E5%8F%AF%E9%9C%B2%E5%B8%8C%E5%B0%94
  public static final SkillBuilder CLOSURE_THIRD_SKILL = skill(
      "skill_closure_third_skill",
      "Q.E.D."
  )
      .enUs("Q.E.D.")
      .operator(
          "可露希尔",
          "Closure",
          SkillProfession.VANGUARD
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(23, 30, 30)
      .description(
          "技能持续时间内逐渐获得18点部署费用，攻击间隔缩短(-0.5)，锁定自身和援军原本攻击范围内的敌人进行攻击，造成可露希尔250%攻击力的物理伤害并施加持续3秒的6%迟钝效果（可叠加，最高60%），每攻击9次后攻击目标数+1（最多触发6次）",
          "技能持续时间内逐渐获得18点部署费用，攻击间隔缩短(-0.5)，锁定自身和援军原本攻击范围内的敌人进行攻击，造成可露希尔250%攻击力的物理伤害并施加持续3秒的6%迟钝效果（可叠加，最高60%），每攻击9次后攻击目标数+1（最多触发6次）"
      )
      .effect(reusedEffect(SkillDemoTheme.COST_RECOVERY))
      .theme(SkillDemoTheme.COST_RECOVERY)
      .build();

  // PRTS: https://prts.wiki/w/%E5%87%AF%E5%B0%94%E5%B8%8C%C2%B7%E6%80%9D%E8%A1%A1%E6%89%98
  public static final SkillBuilder KALTSIT_ESPERANTA_THIRD_SKILL = skill(
      "skill_kaltsit_esperanta_third_skill",
      "破梏重生"
  )
      .enUs("破梏重生")
      .operator(
          "凯尔希·思衡托",
          "Kal'tsit·Esperanta",
          SkillProfession.MEDIC
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(35, 50, 35)
      .description(
          "立刻获得战术锚点，攻击力+150%，攻击间隔大幅缩小(-1.55)，额外治疗1个目标，部署战术锚点后，自身移动至该位置，并使攻击范围内最多2名友方干员可以部署至自身攻击范围的另一位置",
          "立刻获得战术锚点，攻击力+150%，攻击间隔大幅缩小(-1.55)，额外治疗1个目标，部署战术锚点后，自身移动至该位置，并使攻击范围内最多2名友方干员可以部署至自身攻击范围的另一位置"
      )
      .effect(reusedEffect(SkillDemoTheme.SANCTUARY))
      .theme(SkillDemoTheme.SANCTUARY)
      .build();

  // PRTS: https://prts.wiki/w/%E7%84%B0%E7%8B%90%E9%BE%99%E6%A2%93%E5%85%B0
  public static final SkillBuilder VIOLET_MIZUTSUNE_ORCHID_THIRD_SKILL = skill(
      "skill_violet_mizutsune_orchid_third_skill",
      "龙之箭"
  )
      .enUs("龙之箭")
      .operator(
          "焰狐龙梓兰",
          "Violet Mizutsune Orchid",
          SkillProfession.SNIPER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(30, 25, null)
      .description(
          "蓄力3秒后射出直线飞行的贯穿箭矢，箭矢以较大力度推动每个经过的敌人，每飞行一段距离都会对周围所有敌人造成攻击力360%的物理伤害和攻击力60%的法术伤害，箭矢射程无限；可充能2次",
          "蓄力3秒后射出直线飞行的贯穿箭矢，箭矢以较大力度推动每个经过的敌人，每飞行一段距离都会对周围所有敌人造成攻击力360%的物理伤害和攻击力60%的法术伤害，箭矢射程无限；可充能2次"
      )
      .effect(reusedEffect(SkillDemoTheme.RAPID_FIRE))
      .theme(SkillDemoTheme.RAPID_FIRE)
      .build();

  // PRTS: https://prts.wiki/w/%E8%B0%AC%E5%9B%A0
  public static final SkillBuilder APHRISSA_THIRD_SKILL = skill(
      "skill_aphrissa_third_skill",
      "混沌的本质"
  )
      .enUs("混沌的本质")
      .operator(
          "谬因",
          "Aphrissa",
          SkillProfession.CASTER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(33, 40, null)
      .description(
          "技能开启后停止攻击3秒，撤回已部署的“中继器”并重置再部署；攻击间隔较大幅度缩短(-1.1)，攻击力+145%，攻击造成200%的法术伤害，并使目标停顿0.4秒；攻击经过“中继器”后，额外造成一次攻击力30%的法术伤害，且停顿时间延长0.4秒；技能期间可额外部署2个“中继器”，“中继器”不会撤退，技能结束时自动撤退；攻击装有20发弹药，打完后结束（可随时停止技能）",
          "技能开启后停止攻击3秒，撤回已部署的“中继器”并重置再部署；攻击间隔较大幅度缩短(-1.1)，攻击力+145%，攻击造成200%的法术伤害，并使目标停顿0.4秒；攻击经过“中继器”后，额外造成一次攻击力30%的法术伤害，且停顿时间延长0.4秒；技能期间可额外部署2个“中继器”，“中继器”不会撤退，技能结束时自动撤退；攻击装有20发弹药，打完后结束（可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.VOLCANIC_BURST))
      .theme(SkillDemoTheme.VOLCANIC_BURST)
      .build();

  // PRTS: https://prts.wiki/w/%E6%9C%BA%E6%A2%B0%E5%B8%88
  public static final SkillBuilder MECHANIST_THIRD_SKILL = skill(
      "skill_mechanist_third_skill",
      "工程学十字星"
  )
      .enUs("工程学十字星")
      .operator(
          "机械师",
          "Mechanist",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(25, 35, 40)
      .description(
          "攻击力+280%，攻击间隔大幅增大(+2.3)，攻击变为对十字范围内所有敌人造成攻击力260%的法术伤害，可攻击结构性原理阻挡的目标；结构性原理向前方冲锋，碰到敌人或高台时对范围内所有敌人造成机械师攻击力300%的物理伤害并停下，周围的敌人获得逐渐衰减的50%的虚弱效果",
          "攻击力+280%，攻击间隔大幅增大(+2.3)，攻击变为对十字范围内所有敌人造成攻击力260%的法术伤害，可攻击结构性原理阻挡的目标；结构性原理向前方冲锋，碰到敌人或高台时对范围内所有敌人造成机械师攻击力300%的物理伤害并停下，周围的敌人获得逐渐衰减的50%的虚弱效果"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E7%8F%8A%E6%AF%94
  public static final SkillBuilder THUMPY_THIRD_SKILL = skill(
      "skill_thumpy_third_skill",
      "“不准走！”"
  )
      .enUs("“不准走！”")
      .operator(
          "珊比",
          "Thumpy",
          SkillProfession.DEFENDER
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(42, 55, 60)
      .description(
          "阻挡数+2且同时攻击所有阻挡的敌人，攻击力+100%，防御力+135%；向前展开4格的便携传送带，使重量小于等于4且未被阻挡的敌人以每秒0.8格的速度移向珊比；传送带上的敌人每秒受到相当于珊比攻击力40%的物理伤害，且侵蚀损伤爆发时防御力额外-30",
          "阻挡数+2且同时攻击所有阻挡的敌人，攻击力+100%，防御力+135%；向前展开4格的便携传送带，使重量小于等于4且未被阻挡的敌人以每秒0.8格的速度移向珊比；传送带上的敌人每秒受到相当于珊比攻击力40%的物理伤害，且侵蚀损伤爆发时防御力额外-30"
      )
      .effect(reusedEffect(SkillDemoTheme.HEAL_AND_SLOW))
      .theme(SkillDemoTheme.HEAL_AND_SLOW)
      .build();

  // PRTS: https://prts.wiki/w/%E4%BA%88%E6%84%BF%E5%AE%89%E6%B4%81%E8%8E%89%E5%A8%9C
  public static final SkillBuilder ANGELINA_THE_MELLOW_WISH_THIRD_SKILL = skill(
      "skill_angelina_the_mellow_wish_third_skill",
      "酸橙的心事"
  )
      .enUs("酸橙的心事")
      .operator(
          "予愿安洁莉娜",
          "Angelina the Mellow Wish",
          SkillProfession.SPECIALIST
      )
      .activation(
          SkillSpRecoveryType.AUTO_RECOVERY,
          SkillTriggerType.MANUAL
      )
      .stats(24, 30, null)
      .description(
          "立刻起飞，攻击力+30%，获得65%的对空庇护，攻击范围扩大，且周围8格视作予愿安洁莉娜的额外攻击范围，攻击对3个敌人造成攻击力380%的物理伤害且额外攻击1个飞行敌人；范围内飞行敌人移动速度-60%，原技能范围内有未被阻挡的可阻挡飞行敌人，且自身未阻挡时，移动至该敌人所在格；攻击装有33发弹药，打完后技能结束（期间可随时停止技能）",
          "立刻起飞，攻击力+30%，获得65%的对空庇护，攻击范围扩大，且周围8格视作予愿安洁莉娜的额外攻击范围，攻击对3个敌人造成攻击力380%的物理伤害且额外攻击1个飞行敌人；范围内飞行敌人移动速度-60%，原技能范围内有未被阻挡的可阻挡飞行敌人，且自身未阻挡时，移动至该敌人所在格；攻击装有33发弹药，打完后技能结束（期间可随时停止技能）"
      )
      .effect(reusedEffect(SkillDemoTheme.DEPLOYMENT_STUN))
      .theme(SkillDemoTheme.DEPLOYMENT_STUN)
      .build();
  // END GENERATED PRTS SIX-STAR THIRD SKILLS

  private ModSkill() {
  }

  private static SkillBuilder skill(String path, String zhCn) {
    return new SkillBuilder(Zinecraft.SKILLS, path, zhCn);
  }

  private static VfxBuilder vfx(String path) {
    return new VfxBuilder(Zinecraft.VFX, path).build();
  }

  private static VfxBuilder reusedEffect(SkillDemoTheme theme) {
    SkillBuilder source = switch (theme) {
      case COST_RECOVERY -> SUPPORT_BETA;
      case AREA_SLASH -> TRUESILVER_SLASH;
      case RAPID_FIRE -> OVERLOADING_MODE;
      case EXPLOSIVE_DAWN -> EXPLOSIVE_DAWN;
      case VOLCANIC_BURST -> VOLCANO;
      case HEAL_AND_SLOW -> CALCIFICATION;
      case SANCTUARY -> SANCTUARY;
      case SLOWING_FIELD -> FOXFIRE_HAZE;
      case DEPLOYMENT_STUN -> WOLFPACK;
    };
    return source.effects().get(0);
  }

  public static void bootstrap() {
  }
}
