package com.cxxcxx.zinecraft.api.skill;

/** 技能的触发方式及其双语显示文本。 */
public enum SkillTriggerType {
  MANUAL("手动触发", "Manual"),
  AUTO("自动触发", "Auto Trigger"),
  MANUAL_TOGGLED("手动触发", "Manual Toggled"),
  ON_DEPLOYMENT("部署触发", "On Deployment");

  private final String zhCn;
  private final String enUs;

  SkillTriggerType(String zhCn, String enUs) {
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
