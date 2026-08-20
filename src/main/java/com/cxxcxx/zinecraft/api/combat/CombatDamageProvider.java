package com.cxxcxx.zinecraft.api.combat;

import java.util.List;

/**
 * 公开武器、技能或其他内容所描述的直接伤害。
 * 非伤害型内容返回空列表，不需要伪造零伤害或无意义的伤害类型。
 */
public interface CombatDamageProvider {
  /**
   * @return 不可变的直接伤害段列表；不直接造成伤害时为空列表
   */
  List<CombatDamageProfile> damageProfiles();
}
