package com.cxxcxx.zinecraft.api.skill;

/** 技能的技力回复类型及其双语显示文本。 */
public enum SkillSpRecoveryType {
  AUTO_RECOVERY("自动回复", "Auto Recovery"),
  OFFENSIVE_RECOVERY("攻击回复", "Offensive Recovery"),
  DEFENSIVE_RECOVERY("受击回复", "Defensive Recovery"),
  PASSIVE("被动", "Passive");

  private final String zhCn;
  private final String enUs;

  SkillSpRecoveryType(String zhCn, String enUs) {
    this.zhCn = zhCn;
    this.enUs = enUs;
  }

  public String getZhCn() {
    return zhCn;
  }

  public String getEnUs() {
    return enUs;
  }
}
