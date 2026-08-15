package com.cxxcxx.zinecraft.api.skill;

public enum SkillProfession {
  VANGUARD("先锋", "Vanguard"), GUARD("近卫", "Guard"), SNIPER("狙击", "Sniper"),
  CASTER("术师", "Caster"), DEFENDER("重装", "Defender"), MEDIC("医疗", "Medic"),
  SUPPORTER("辅助", "Supporter"), SPECIALIST("特种", "Specialist");
  private final String zhCn, enUs;

  SkillProfession(String zhCn, String enUs) {
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
