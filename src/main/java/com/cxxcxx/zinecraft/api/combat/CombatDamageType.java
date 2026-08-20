package com.cxxcxx.zinecraft.api.combat;

/**
 * 伤害的实际表现类型；不同类型可以共用同一个防御结算通道。
 */
public enum CombatDamageType {
  PHYSICAL("physical", CombatMitigationType.PHYSICAL),
  MAGIC("magic", CombatMitigationType.MAGIC),
  ARTS("arts", CombatMitigationType.MAGIC),
  FIRE("fire", CombatMitigationType.MAGIC),
  ICE("ice", CombatMitigationType.MAGIC),
  LIGHTNING("lightning", CombatMitigationType.MAGIC),
  POISON("poison", CombatMitigationType.MAGIC),
  TRUE("true", CombatMitigationType.NONE);

  private final String path;
  private final CombatMitigationType mitigation;

  CombatDamageType(String path, CombatMitigationType mitigation) {
    this.path = path;
    this.mitigation = mitigation;
  }

  /**
   * @return 对应 Minecraft DamageType 的命名空间内路径
   */
  public String path() {
    return path;
  }

  /**
   * @return 该类型采用的防御结算通道
   */
  public CombatMitigationType mitigation() {
    return mitigation;
  }
}
