package com.cxxcxx.zinecraft.api.combat;

import com.cxxcxx.zinecraft.api.skill.SkillProfession;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

/**
 * 一份不可变的藏品能力快照。
 *
 * <p>能力包括直接参与计算的数值属性、每秒触发的函数、每秒重新判断的条件函数，以及每次击杀生物时
 * 触发的函数。藏品效果统一表示为 {@code CombatStat -> CombatStat}，通过返回新快照
 * 修改数值或注册触发函数。战斗字段与 Minecraft 原版属性之间的转换也集中在本类中。</p>
 */
public record CombatStat(
    /** 最大生命值，单位为 Minecraft 生命值点数。 */
    double maxHealth,
    /** 攻击力，用作物理、法术、治疗等项目战斗公式的基础数值。 */
    double attack,
    /** 防御力，用于降低物理伤害。 */
    double defense,
    /** 法术抗性，取值范围为 0～100，用于降低法术伤害。 */
    double resistance,
    /** 攻击速度，以 100 为中性值；数值越高，攻击间隔越短。 */
    double attackSpeed,
    /** 所有藏品累计提供的敌方伤害减免比例，在结算时统一限制到 0～1。 */
    double damageReduction,
    /** 所有藏品累计提供的物理防御无视比例，在结算时统一限制到 0～1。 */
    double defenseIgnore,
    /** 敌方受到物理伤害的加成比例，作为防御结算后的独立最终乘区。 */
    double enemyPhysicalDamageTakenBonus,
    /** 敌方受到法术伤害的加成比例，作为法抗结算后的独立最终乘区。 */
    double enemyMagicDamageTakenBonus,
    /** 受到的治疗与生命回复效果加成比例；0.2 表示最终回复量增加 20%。 */
    double healingAndHealthRegenerationBonus,
    /** 攻击回复与受击回复技能额外获得的技力，单位为每秒技力值。 */
    double offensiveDefensiveSkillPointRegeneration,
    /** 自然回复技能额外获得的技力，单位为每秒技力值。 */
    double naturalSkillPointRegeneration,
    /** 造成的真实伤害加成比例；1.5 表示最终真实伤害增加 150%。 */
    double trueDamageBonus,
    /** 造成的元素损伤加成比例；0.2 表示最终元素损伤增加 20%。 */
    double elementalDamageBonus,
    /** 受到的元素损伤减免比例；0.2 表示最终元素损伤减少 20%。 */
    double elementalDamageReduction,
    /** 受到物理伤害时的闪避概率，结算时统一限制到 0～1。 */
    double physicalDamageEvasionRate,
    /** 受到法术伤害时的闪避概率，结算时统一限制到 0～1。 */
    double magicDamageEvasionRate,
    /** 对敌方施加异常状态时的持续时间加成比例；1.0 表示延长 100%。 */
    double enemyStatusDurationBonus,
    /** 我方受到异常状态时的持续时间减免比例；0.5 表示缩短 50%。 */
    double friendlyStatusDurationReduction,
    /** 所有敌方单位的移动速度降低比例；0.15 表示降低 15%。 */
    double enemyMovementSpeedReduction,
    /** 推拉结算时忽略的敌方重量等级；1 表示按低一个重量等级处理。 */
    int enemyWeightIgnore,

    /** 仅在先锋技能结算时应用的藏品战斗效果。 */
    List<ProfessionEffect> vanguardEffects,
    /** 仅在近卫技能结算时应用的藏品战斗效果。 */
    List<ProfessionEffect> guardEffects,
    /** 仅在狙击技能结算时应用的藏品战斗效果。 */
    List<ProfessionEffect> sniperEffects,
    /** 仅在术师技能结算时应用的藏品战斗效果。 */
    List<ProfessionEffect> casterEffects,
    /** 仅在重装技能结算时应用的藏品战斗效果。 */
    List<ProfessionEffect> defenderEffects,
    /** 仅在医疗技能结算时应用的藏品战斗效果。 */
    List<ProfessionEffect> medicEffects,
    /** 仅在辅助技能结算时应用的藏品战斗效果。 */
    List<ProfessionEffect> supporterEffects,
    /** 仅在特种技能结算时应用的藏品战斗效果。 */
    List<ProfessionEffect> specialistEffects,

    /** 希望 */
    int hope,
    /** 源石锭 */
    int originiumIngots,
    /** 探索行动力 */
    int actionPoints,
    /** 抗干扰指数 */
    int antiInterferenceIndex,
    /** 坍缩值 */
    int collapseValue,
    /** 负荷临界点 */
    int mentalBurdenLimit,
    /** 思绪数量 */
    int thoughts,
    /** 烛火数量 */
    int candles,
    /** 编队可容纳干员数量的增量。 */
    int squadCapacity,
    /** 战斗中可同时部署单位数量的增量。 */
    int deploymentLimit,
    /** 战斗开始时可用部署点数的增量。 */
    int initialDeploymentPoints,
    /** 钥匙数量 */
    int keys,
    /** 骰子数量 */
    int dice,
    /** 灯火值 */
    int light,
    /** 指挥经验获取倍率，以 1.0 为不变。 */
    double commandExperienceMultiplier,
    /** 每经过一个非战斗节点额外获得的希望。 */
    int hopePerNonCombatNode,
    /** 每经过一个非战斗节点额外获得的源石锭。 */
    int originiumIngotsPerNonCombatNode,
    /** 战斗结束时源石锭获取倍率，以 1.0 为不变。 */
    double battleOriginiumIngotMultiplier,
    /** 受到致命伤害时一次性恢复的生命值。 */
    int oneTimeFailureRecoveryObjectiveLife,
    /** 每秒执行一次的能力函数，按藏品与函数的声明顺序触发。 */
    List<PerSecondEffect> perSecondEffects,
    /** 每秒重新判断一次并按当前快照修改能力的条件函数。 */
    List<PerSecondConditionalEffect> perSecondConditionalEffects,
    /** 击杀生物时执行的能力函数，接收击杀者和被击杀实体。 */
    List<KillEffect> killEffects,
    /** 仅在敌方第一次生成时应用的属性快照函数。 */
    List<EnemySpawnStatEffect> enemySpawnStatEffects,
    /** 当前集成战略特殊条件档位；0 为基础档，数值越大启用越高档分支。 */
    int collectibleEffectTier
) {
  public static final CombatStat EMPTY = new CombatStat(
      0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
      0.0, 0.0,
      0,
      List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of(),
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
      1.0, 0, 0, 1.0, 0,
      List.of(), List.of(), List.of(), List.of(), 0
  );

  /** 每秒对装备者执行一次的能力函数。 */
  @FunctionalInterface
  public interface PerSecondEffect {
    void apply(LivingEntity entity);
  }

  /** 每秒基于当前聚合快照重新判断并返回修改后快照的条件函数。 */
  @FunctionalInterface
  public interface PerSecondConditionalEffect {
    CombatStat apply(CombatStat current);
  }

  /** 每当装备者击杀生物时执行一次的能力函数。 */
  @FunctionalInterface
  public interface KillEffect {
    void apply(LivingEntity killer, LivingEntity killed);
  }

  /** 将一项生成时属性效果应用到敌方属性快照。 */
  @FunctionalInterface
  public interface EnemySpawnStatEffect {
    CombatStat apply(LivingEntity enemy, CombatStat enemyStats);
  }

  /** 将一项职业限定藏品效果应用到技能战斗快照。 */
  @FunctionalInterface
  public interface ProfessionEffect {
    CombatStat apply(CombatStat stats);
  }

  public CombatStat {
    requireFinite(maxHealth, "maxHealth");
    requireFinite(attack, "attack");
    requireFinite(defense, "defense");
    requireFinite(resistance, "resistance");
    requireFinite(attackSpeed, "attackSpeed");
    requireFinite(damageReduction, "damageReduction");
    requireFinite(defenseIgnore, "defenseIgnore");
    requireFinite(enemyPhysicalDamageTakenBonus, "enemyPhysicalDamageTakenBonus");
    requireFinite(enemyMagicDamageTakenBonus, "enemyMagicDamageTakenBonus");
    requireFinite(healingAndHealthRegenerationBonus, "healingAndHealthRegenerationBonus");
    requireFinite(offensiveDefensiveSkillPointRegeneration, "offensiveDefensiveSkillPointRegeneration");
    requireFinite(naturalSkillPointRegeneration, "naturalSkillPointRegeneration");
    requireFinite(trueDamageBonus, "trueDamageBonus");
    requireFinite(elementalDamageBonus, "elementalDamageBonus");
    requireFinite(elementalDamageReduction, "elementalDamageReduction");
    requireFinite(physicalDamageEvasionRate, "physicalDamageEvasionRate");
    requireFinite(magicDamageEvasionRate, "magicDamageEvasionRate");
    requireFinite(enemyStatusDurationBonus, "enemyStatusDurationBonus");
    requireFinite(friendlyStatusDurationReduction, "friendlyStatusDurationReduction");
    requireFinite(enemyMovementSpeedReduction, "enemyMovementSpeedReduction");
    vanguardEffects = checkedFunctions(vanguardEffects, "vanguardEffect");
    guardEffects = checkedFunctions(guardEffects, "guardEffect");
    sniperEffects = checkedFunctions(sniperEffects, "sniperEffect");
    casterEffects = checkedFunctions(casterEffects, "casterEffect");
    defenderEffects = checkedFunctions(defenderEffects, "defenderEffect");
    medicEffects = checkedFunctions(medicEffects, "medicEffect");
    supporterEffects = checkedFunctions(supporterEffects, "supporterEffect");
    specialistEffects = checkedFunctions(specialistEffects, "specialistEffect");
    requirePositive(commandExperienceMultiplier, "commandExperienceMultiplier");
    requirePositive(battleOriginiumIngotMultiplier, "battleOriginiumIngotMultiplier");
    perSecondEffects = checkedFunctions(perSecondEffects, "perSecondEffect");
    perSecondConditionalEffects = checkedFunctions(perSecondConditionalEffects, "perSecondConditionalEffect");
    killEffects = checkedFunctions(killEffects, "killEffect");
    enemySpawnStatEffects = checkedFunctions(enemySpawnStatEffects, "enemySpawnStatEffect");
    if (collectibleEffectTier < 0) {
      throw new IllegalArgumentException("collectibleEffectTier 不能为负数");
    }
  }

  /** 从实体当前的 Minecraft 属性创建战斗属性快照。 */
  public static CombatStat fromVanilla(LivingEntity entity) {
    Objects.requireNonNull(entity, "entity");
    return EMPTY.change(values -> {
      values.maxHealth = entity.getAttributeValue(Attributes.MAX_HEALTH);
      values.attack = entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
      values.defense = entity.getAttributeValue(Attributes.ARMOR);
      values.resistance = entity.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
      double baseAttackSpeed = entity.getAttributeBaseValue(Attributes.ATTACK_SPEED);
      values.attackSpeed = baseAttackSpeed > 0.0
          ? entity.getAttributeValue(Attributes.ATTACK_SPEED) / baseAttackSpeed * 100.0
          : 100.0;
    });
  }

  /** 将战斗字段限制到项目采用的合法范围。 */
  public CombatStat limited() {
    return change(values -> {
      values.maxHealth = Math.max(0.0, values.maxHealth);
      values.attack = Math.max(0.0, values.attack);
      values.defense = Math.max(0.0, values.defense);
      values.resistance = Math.clamp(values.resistance, 0.0, 100.0);
      values.attackSpeed = Math.clamp(values.attackSpeed, 20.0, 600.0);
      values.damageReduction = Math.clamp(values.damageReduction, 0.0, 1.0);
      values.defenseIgnore = Math.clamp(values.defenseIgnore, 0.0, 1.0);
      values.enemyPhysicalDamageTakenBonus = Math.max(0.0, values.enemyPhysicalDamageTakenBonus);
      values.enemyMagicDamageTakenBonus = Math.max(0.0, values.enemyMagicDamageTakenBonus);
      values.healingAndHealthRegenerationBonus = Math.max(0.0, values.healingAndHealthRegenerationBonus);
      values.offensiveDefensiveSkillPointRegeneration = Math.max(0.0,
          values.offensiveDefensiveSkillPointRegeneration);
      values.naturalSkillPointRegeneration = Math.max(0.0, values.naturalSkillPointRegeneration);
      values.trueDamageBonus = Math.max(0.0, values.trueDamageBonus);
      values.elementalDamageBonus = Math.max(0.0, values.elementalDamageBonus);
      values.elementalDamageReduction = Math.clamp(values.elementalDamageReduction, 0.0, 1.0);
      values.physicalDamageEvasionRate = Math.clamp(values.physicalDamageEvasionRate, 0.0, 1.0);
      values.magicDamageEvasionRate = Math.clamp(values.magicDamageEvasionRate, 0.0, 1.0);
      values.enemyStatusDurationBonus = Math.max(0.0, values.enemyStatusDurationBonus);
      values.friendlyStatusDurationReduction = Math.clamp(values.friendlyStatusDurationReduction, 0.0, 1.0);
      values.enemyMovementSpeedReduction = Math.clamp(values.enemyMovementSpeedReduction, 0.0, 1.0);
      values.enemyWeightIgnore = Math.max(0, values.enemyWeightIgnore);
    });
  }

  public CombatStat withMaxHealth(double value) {
    return change(values -> values.maxHealth = value);
  }

  public CombatStat withAttack(double value) {
    return change(values -> values.attack = value);
  }

  public CombatStat withDefense(double value) {
    return change(values -> values.defense = value);
  }

  public CombatStat withResistance(double value) {
    return change(values -> values.resistance = value);
  }

  public CombatStat withAttackSpeed(double value) {
    return change(values -> values.attackSpeed = value);
  }

  public CombatStat multiplyMaxHealth(double bonus) {
    return withMaxHealth(maxHealth * (1.0 + bonus));
  }

  public CombatStat multiplyAttack(double bonus) {
    return withAttack(attack * (1.0 + bonus));
  }

  public CombatStat multiplyDefense(double bonus) {
    return withDefense(defense * (1.0 + bonus));
  }

  public CombatStat multiplyResistance(double bonus) {
    return withResistance(resistance * (1.0 + bonus));
  }

  public CombatStat multiplyAttackSpeed(double bonus) {
    return withAttackSpeed(attackSpeed * (1.0 + bonus));
  }

  public CombatStat addMaxHealth(double value) {
    return withMaxHealth(maxHealth + value);
  }

  public CombatStat addAttack(double value) {
    return withAttack(attack + value);
  }

  public CombatStat addDefense(double value) {
    return withDefense(defense + value);
  }

  public CombatStat addResistance(double value) {
    return withResistance(resistance + value);
  }

  public CombatStat addAttackSpeed(double value) {
    return withAttackSpeed(attackSpeed + value);
  }

  /** 累加我方受到敌方攻击时的伤害减免，暂不提前截断。 */
  public CombatStat addDamageReduction(double value) {
    return change(values -> values.damageReduction += value);
  }

  /** 累加我方攻击敌方时的物理防御无视，暂不提前截断。 */
  public CombatStat addDefenseIgnore(double value) {
    return change(values -> values.defenseIgnore += value);
  }

  /** 累加敌方受到的物理伤害加成，作为独立最终乘区统一结算。 */
  public CombatStat addEnemyPhysicalDamageTakenBonus(double value) {
    return change(values -> values.enemyPhysicalDamageTakenBonus += value);
  }

  /** 累加敌方受到的法术伤害加成，作为独立最终乘区统一结算。 */
  public CombatStat addEnemyMagicDamageTakenBonus(double value) {
    return change(values -> values.enemyMagicDamageTakenBonus += value);
  }

  /** 返回敌方受伤独立最终乘区；真实伤害不使用物理或法术受伤加成。 */
  public double enemyDamageTakenMultiplier(CombatMitigationType mitigation) {
    return switch (Objects.requireNonNull(mitigation, "mitigation")) {
      case PHYSICAL -> 1.0 + limited().enemyPhysicalDamageTakenBonus;
      case MAGIC -> 1.0 + limited().enemyMagicDamageTakenBonus;
      case NONE -> 1.0;
    };
  }

  /** 累加受到的治疗与生命回复效果加成，结算前不提前截断。 */
  public CombatStat addHealingAndHealthRegenerationBonus(double value) {
    return change(values -> values.healingAndHealthRegenerationBonus += value);
  }

  /** 累加攻击回复与受击回复技能的额外每秒技力。 */
  public CombatStat addOffensiveDefensiveSkillPointRegeneration(double value) {
    return change(values -> values.offensiveDefensiveSkillPointRegeneration += value);
  }

  /** 累加自然回复技能的额外每秒技力。 */
  public CombatStat addNaturalSkillPointRegeneration(double value) {
    return change(values -> values.naturalSkillPointRegeneration += value);
  }

  /** 累加造成的真实伤害加成，结算前不提前截断。 */
  public CombatStat addTrueDamageBonus(double value) {
    return change(values -> values.trueDamageBonus += value);
  }

  /** 累加造成的元素损伤加成，结算前不提前截断。 */
  public CombatStat addElementalDamageBonus(double value) {
    return change(values -> values.elementalDamageBonus += value);
  }

  /** 累加受到的元素损伤减免，结算前不提前截断。 */
  public CombatStat addElementalDamageReduction(double value) {
    return change(values -> values.elementalDamageReduction += value);
  }

  /** 累加物理伤害闪避率，结算前不提前截断。 */
  public CombatStat addPhysicalDamageEvasionRate(double value) {
    return change(values -> values.physicalDamageEvasionRate += value);
  }

  /** 累加法术伤害闪避率，结算前不提前截断。 */
  public CombatStat addMagicDamageEvasionRate(double value) {
    return change(values -> values.magicDamageEvasionRate += value);
  }

  /** 累加对敌方施加的异常状态持续时间加成。 */
  public CombatStat addEnemyStatusDurationBonus(double value) {
    return change(values -> values.enemyStatusDurationBonus += value);
  }

  /** 累加我方受到的异常状态持续时间减免，结算前不提前截断。 */
  public CombatStat addFriendlyStatusDurationReduction(double value) {
    return change(values -> values.friendlyStatusDurationReduction += value);
  }

  /** 累加所有敌方单位的移动速度降低比例，结算前不提前截断。 */
  public CombatStat addEnemyMovementSpeedReduction(double value) {
    return change(values -> values.enemyMovementSpeedReduction += value);
  }

  /** 累加推拉结算时忽略的敌方重量等级。 */
  public CombatStat addEnemyWeightIgnore(int value) {
    return change(values -> values.enemyWeightIgnore += value);
  }

  /** 登记一项只对指定技能职业生效的藏品战斗效果。 */
  public CombatStat addProfessionEffect(SkillProfession profession, ProfessionEffect effect) {
    Objects.requireNonNull(profession, "profession");
    Objects.requireNonNull(effect, "effect");
    return change(values -> values.professionEffects(profession).add(effect));
  }

  /** 应用指定技能职业的全部藏品效果，并清空职业效果列表，防止重复解析。 */
  public CombatStat resolveProfession(SkillProfession profession) {
    Objects.requireNonNull(profession, "profession");
    CombatStat result = change(Builder::clearProfessionEffects);
    return resolveProfession(profession, result);
  }

  /** 只把当前快照登记的指定职业效果应用到另一份基础快照。 */
  public CombatStat resolveProfession(SkillProfession profession, CombatStat base) {
    Objects.requireNonNull(profession, "profession");
    CombatStat result = Objects.requireNonNull(base, "base")
        .change(Builder::clearProfessionEffects);
    for (ProfessionEffect effect : professionEffects(profession)) {
      result = Objects.requireNonNull(effect.apply(result), "职业藏品效果不能返回 null");
    }
    return result;
  }

  private List<ProfessionEffect> professionEffects(SkillProfession profession) {
    return switch (profession) {
      case VANGUARD -> vanguardEffects;
      case GUARD -> guardEffects;
      case SNIPER -> sniperEffects;
      case CASTER -> casterEffects;
      case DEFENDER -> defenderEffects;
      case MEDIC -> medicEffects;
      case SUPPORTER -> supporterEffects;
      case SPECIALIST -> specialistEffects;
    };
  }

  /** 根据聚合后的敌方异常状态延长比例换算最终持续 tick。 */
  public int enemyStatusDurationTicks(int baseDurationTicks) {
    return scaleDurationTicks(baseDurationTicks, 1.0 + limited().enemyStatusDurationBonus);
  }

  /** 根据聚合后的我方异常状态减免比例换算最终持续 tick。 */
  public int friendlyStatusDurationTicks(int baseDurationTicks) {
    return scaleDurationTicks(baseDurationTicks, 1.0 - limited().friendlyStatusDurationReduction);
  }

  /** 注册一项每秒触发的能力函数。 */
  public CombatStat addPerSecondEffect(PerSecondEffect effect) {
    return change(values -> values.perSecondEffects.add(Objects.requireNonNull(effect, "effect")));
  }

  /** 注册一项每秒重新判断的条件能力函数。 */
  public CombatStat addPerSecondConditionalEffect(PerSecondConditionalEffect effect) {
    return change(values -> values.perSecondConditionalEffects.add(
        Objects.requireNonNull(effect, "effect")
    ));
  }

  /** 注册一项击杀生物时触发的能力函数。 */
  public CombatStat addKillEffect(KillEffect effect) {
    return change(values -> values.killEffects.add(Objects.requireNonNull(effect, "effect")));
  }

  /** 注册一项只在敌方第一次生成时应用的属性函数。 */
  public CombatStat addEnemySpawnStatEffect(EnemySpawnStatEffect effect) {
    return change(values -> values.enemySpawnStatEffects.add(Objects.requireNonNull(effect, "effect")));
  }

  /** 按声明顺序把已聚合的敌方生成属性函数应用到基础快照。 */
  public CombatStat applyEnemySpawnStatEffects(LivingEntity enemy, CombatStat base) {
    Objects.requireNonNull(enemy, "enemy");
    CombatStat result = Objects.requireNonNull(base, "base");
    for (EnemySpawnStatEffect effect : enemySpawnStatEffects) {
      result = Objects.requireNonNull(effect.apply(enemy, result), "敌方生成属性效果不能返回 null");
    }
    return result;
  }

  /** 按注册顺序执行每秒能力。 */
  public void triggerPerSecondEffects(LivingEntity entity) {
    Objects.requireNonNull(entity, "entity");
    perSecondEffects.forEach(effect -> effect.apply(entity));
  }

  /** 按注册顺序每秒重新判断条件，并返回本次判断得到的能力快照。 */
  public CombatStat evaluatePerSecondConditionalEffects() {
    CombatStat result = this;
    for (PerSecondConditionalEffect effect : perSecondConditionalEffects) {
      result = Objects.requireNonNull(effect.apply(result), "每秒条件能力不能返回 null");
    }
    return result;
  }

  /** 按注册顺序执行击杀能力。 */
  public void triggerKillEffects(LivingEntity killer, LivingEntity killed) {
    Objects.requireNonNull(killer, "killer");
    Objects.requireNonNull(killed, "killed");
    killEffects.forEach(effect -> effect.apply(killer, killed));
  }

  public CombatStat hope(int value) {
    return change(values -> values.hope += value);
  }


  public CombatStat originiumIngots(int value) {
    return change(values -> values.originiumIngots += value);
  }

  public CombatStat actionPoints(int value) {
    return change(values -> values.actionPoints += value);
  }

  public CombatStat antiInterferenceIndex(int value) {
    return change(values -> values.antiInterferenceIndex += value);
  }

  public CombatStat collapseValue(int value) {
    return change(values -> values.collapseValue += value);
  }

  public CombatStat mentalBurdenLimit(int value) {
    return change(values -> values.mentalBurdenLimit += value);
  }

  public CombatStat thoughts(int value) {
    return change(values -> values.thoughts += value);
  }

  public CombatStat candles(int value) {
    return change(values -> values.candles += value);
  }

  /** 以探索运行时提供的当前源石锭总数覆盖快照中的资源值。 */
  public CombatStat withOriginiumIngots(int value) {
    if (value < 0) throw new IllegalArgumentException("当前源石锭不能为负数");
    return change(values -> values.originiumIngots = value);
  }

  /** 由集成战略运行时写入当前特殊条件档位。 */
  public CombatStat withCollectibleEffectTier(int value) {
    if (value < 0) throw new IllegalArgumentException("藏品特殊条件档位不能为负数");
    return change(values -> values.collectibleEffectTier = value);
  }

  public CombatStat squadCapacity(int value) {
    return change(values -> values.squadCapacity += value);
  }

  public CombatStat deploymentLimit(int value) {
    return change(values -> values.deploymentLimit += value);
  }

  public CombatStat initialDeploymentPoints(int value) {
    return change(values -> values.initialDeploymentPoints += value);
  }

  public CombatStat keys(int value) {
    return change(values -> values.keys += value);
  }

  public CombatStat dice(int value) {
    return change(values -> values.dice += value);
  }

  public CombatStat light(int value) {
    return change(values -> values.light += value);
  }

  public CombatStat commandExperienceMultiplier(double value) {
    return change(values -> values.commandExperienceMultiplier *= value);
  }

  public CombatStat hopePerNonCombatNode(int value) {
    return change(values -> values.hopePerNonCombatNode += value);
  }

  public CombatStat originiumIngotsPerNonCombatNode(int value) {
    return change(values -> values.originiumIngotsPerNonCombatNode += value);
  }

  public CombatStat battleOriginiumIngotMultiplier(double value) {
    return change(values -> values.battleOriginiumIngotMultiplier *= value);
  }

  public CombatStat oneTimeFailureRecoveryObjectiveLife(int value) {
    return change(values -> values.oneTimeFailureRecoveryObjectiveLife += value);
  }

  /** 是否不包含任何探索属性。 */
  public boolean hasNoExplorationProperties() {
    return hope == 0
        && originiumIngots == 0
        && actionPoints == 0
        && antiInterferenceIndex == 0
        && collapseValue == 0
        && mentalBurdenLimit == 0
        && thoughts == 0
        && candles == 0
        && squadCapacity == 0
        && deploymentLimit == 0
        && initialDeploymentPoints == 0
        && keys == 0
        && dice == 0
        && light == 0
        && Double.compare(commandExperienceMultiplier, 1.0) == 0
        && hopePerNonCombatNode == 0
        && originiumIngotsPerNonCombatNode == 0
        && Double.compare(battleOriginiumIngotMultiplier, 1.0) == 0
        && oneTimeFailureRecoveryObjectiveLife == 0;
  }
  /** 是否不包含任何回复或探索属性。 */
  public boolean hasNoCollectionProperties() {
    return perSecondEffects.isEmpty()
        && perSecondConditionalEffects.isEmpty()
        && killEffects.isEmpty()
        && vanguardEffects.isEmpty()
        && guardEffects.isEmpty()
        && sniperEffects.isEmpty()
        && casterEffects.isEmpty()
        && defenderEffects.isEmpty()
        && medicEffects.isEmpty()
        && supporterEffects.isEmpty()
        && specialistEffects.isEmpty()
        && hope == 0
        && originiumIngots == 0
        && actionPoints == 0
        && antiInterferenceIndex == 0
        && collapseValue == 0
        && mentalBurdenLimit == 0
        && thoughts == 0
        && candles == 0
        && squadCapacity == 0
        && deploymentLimit == 0
        && initialDeploymentPoints == 0
        && keys == 0
        && dice == 0
        && light == 0
        && Double.compare(commandExperienceMultiplier, 1.0) == 0
        && hopePerNonCombatNode == 0
        && originiumIngotsPerNonCombatNode == 0
        && Double.compare(battleOriginiumIngotMultiplier, 1.0) == 0
        && oneTimeFailureRecoveryObjectiveLife == 0;
  }

  /**
   * 将一个函数式藏品效果转换为 Curios 可挂载的 Minecraft 属性修饰器。
   * 战斗字段必须是仿射变换；非线性或跨字段战斗效果应由专门的服务端消费者处理。
   */
  public static Multimap<Holder<Attribute>, AttributeModifier> toVanillaModifiers(
      UnaryOperator<CombatStat> effect,
      ResourceLocation baseId
  ) {
    return toVanillaModifiers(effect, baseId, 0);
  }

  /** 按指定特殊条件档位生成 Curios 属性修饰器。 */
  public static Multimap<Holder<Attribute>, AttributeModifier> toVanillaModifiers(
      UnaryOperator<CombatStat> effect,
      ResourceLocation baseId,
      int collectibleEffectTier
  ) {
    Objects.requireNonNull(effect, "effect");
    Objects.requireNonNull(baseId, "baseId");
    if (collectibleEffectTier < 0) {
      throw new IllegalArgumentException("藏品特殊条件档位不能为负数");
    }
    UnaryOperator<CombatStat> tieredEffect = value -> effect.apply(
        value.withCollectibleEffectTier(collectibleEffectTier)
    );
    var result = ArrayListMultimap.<Holder<Attribute>, AttributeModifier>create();
    addAffineModifiers(result, tieredEffect, baseId, "max_health", Attributes.MAX_HEALTH,
        CombatStat::maxHealth, CombatStat::withMaxHealth);
    addAffineModifiers(result, tieredEffect, baseId, "attack", Attributes.ATTACK_DAMAGE,
        CombatStat::attack, CombatStat::withAttack);
    addAffineModifiers(result, tieredEffect, baseId, "defense", Attributes.ARMOR,
        CombatStat::defense, CombatStat::withDefense);
    addAffineModifiers(result, tieredEffect, baseId, "resistance", Attributes.ARMOR_TOUGHNESS,
        CombatStat::resistance, CombatStat::withResistance);
    addAttackSpeedModifier(result, tieredEffect, baseId);
    return result;
  }

  private static void addAffineModifiers(
      Multimap<Holder<Attribute>, AttributeModifier> result,
      UnaryOperator<CombatStat> effect,
      ResourceLocation baseId,
      String path,
      Holder<Attribute> attribute,
      ToDoubleFunction<CombatStat> getter,
      BiFunction<CombatStat, Double, CombatStat> setter
  ) {
    double atZero = getter.applyAsDouble(apply(effect, setter.apply(EMPTY, 0.0)));
    double atOne = getter.applyAsDouble(apply(effect, setter.apply(EMPTY, 1.0)));
    double atTwo = getter.applyAsDouble(apply(effect, setter.apply(EMPTY, 2.0)));
    double scale = atOne - atZero;
    if (!approximately(atTwo, atZero + 2.0 * scale)) {
      throw new IllegalArgumentException("藏品战斗效果无法转换为原版属性：" + path);
    }
    if (!approximately(atZero, 0.0)) {
      result.put(attribute, new AttributeModifier(id(baseId, path + "/addition"), atZero,
          AttributeModifier.Operation.ADD_VALUE));
    }
    double multiplier = scale - 1.0;
    if (!approximately(multiplier, 0.0)) {
      result.put(attribute, new AttributeModifier(id(baseId, path + "/multiplier"), multiplier,
          AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
  }

  private static void addAttackSpeedModifier(
      Multimap<Holder<Attribute>, AttributeModifier> result,
      UnaryOperator<CombatStat> effect,
      ResourceLocation baseId
  ) {
    double transformed = apply(effect, EMPTY.withAttackSpeed(100.0)).attackSpeed;
    double multiplier = transformed / 100.0 - 1.0;
    if (!approximately(multiplier, 0.0)) {
      result.put(Attributes.ATTACK_SPEED, new AttributeModifier(
          id(baseId, "attack_speed/multiplier"), multiplier, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
  }

  private static CombatStat apply(UnaryOperator<CombatStat> effect, CombatStat value) {
    return Objects.requireNonNull(effect.apply(value), "藏品效果不能返回 null");
  }

  private static ResourceLocation id(ResourceLocation base, String suffix) {
    return ResourceLocation.fromNamespaceAndPath(base.getNamespace(), base.getPath() + "/" + suffix);
  }

  private static boolean approximately(double left, double right) {
    return Math.abs(left - right) <= 1.0E-9;
  }

  private CombatStat change(java.util.function.Consumer<Builder> change) {
    Builder values = new Builder(this);
    change.accept(values);
    return values.build();
  }

  private static <T> List<T> checkedFunctions(List<T> functions, String name) {
    List<T> copied = List.copyOf(functions);
    copied.forEach(function -> Objects.requireNonNull(function, name));
    return copied;
  }

  private static int scaleDurationTicks(int baseDurationTicks, double multiplier) {
    if (baseDurationTicks < 0) throw new IllegalArgumentException("状态持续时间不能为负数");
    double result = baseDurationTicks * multiplier;
    return result >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) Math.round(result);
  }

  private static void requireFinite(double value, String name) {
    if (!Double.isFinite(value)) throw new IllegalArgumentException(name + " 必须是有限数");
  }

  private static void requirePositive(double value, String name) {
    requireFinite(value, name);
    if (value <= 0.0) throw new IllegalArgumentException(name + " 必须大于 0");
  }

  private static final class Builder {
    private double maxHealth;
    private double attack;
    private double defense;
    private double resistance;
    private double attackSpeed;
    private double damageReduction;
    private double defenseIgnore;
    private double enemyPhysicalDamageTakenBonus;
    private double enemyMagicDamageTakenBonus;
    private double healingAndHealthRegenerationBonus;
    private double offensiveDefensiveSkillPointRegeneration;
    private double naturalSkillPointRegeneration;
    private double trueDamageBonus;
    private double elementalDamageBonus;
    private double elementalDamageReduction;
    private double physicalDamageEvasionRate;
    private double magicDamageEvasionRate;
    private double enemyStatusDurationBonus;
    private double friendlyStatusDurationReduction;
    private double enemyMovementSpeedReduction;
    private int enemyWeightIgnore;
    private final ArrayList<ProfessionEffect> vanguardEffects;
    private final ArrayList<ProfessionEffect> guardEffects;
    private final ArrayList<ProfessionEffect> sniperEffects;
    private final ArrayList<ProfessionEffect> casterEffects;
    private final ArrayList<ProfessionEffect> defenderEffects;
    private final ArrayList<ProfessionEffect> medicEffects;
    private final ArrayList<ProfessionEffect> supporterEffects;
    private final ArrayList<ProfessionEffect> specialistEffects;
    private int hope;
    private int originiumIngots;
    private int actionPoints;
    private int antiInterferenceIndex;
    private int collapseValue;
    private int mentalBurdenLimit;
    private int thoughts;
    private int candles;
    private int squadCapacity;
    private int deploymentLimit;
    private int initialDeploymentPoints;
    private int keys;
    private int dice;
    private int light;
    private double commandExperienceMultiplier;
    private int hopePerNonCombatNode;
    private int originiumIngotsPerNonCombatNode;
    private double battleOriginiumIngotMultiplier;
    private int oneTimeFailureRecoveryObjectiveLife;
    private final ArrayList<PerSecondEffect> perSecondEffects;
    private final ArrayList<PerSecondConditionalEffect> perSecondConditionalEffects;
    private final ArrayList<KillEffect> killEffects;
    private final ArrayList<EnemySpawnStatEffect> enemySpawnStatEffects;
    private int collectibleEffectTier;

    private Builder(CombatStat source) {
      maxHealth = source.maxHealth;
      attack = source.attack;
      defense = source.defense;
      resistance = source.resistance;
      attackSpeed = source.attackSpeed;
      damageReduction = source.damageReduction;
      defenseIgnore = source.defenseIgnore;
      enemyPhysicalDamageTakenBonus = source.enemyPhysicalDamageTakenBonus;
      enemyMagicDamageTakenBonus = source.enemyMagicDamageTakenBonus;
      healingAndHealthRegenerationBonus = source.healingAndHealthRegenerationBonus;
      offensiveDefensiveSkillPointRegeneration = source.offensiveDefensiveSkillPointRegeneration;
      naturalSkillPointRegeneration = source.naturalSkillPointRegeneration;
      trueDamageBonus = source.trueDamageBonus;
      elementalDamageBonus = source.elementalDamageBonus;
      elementalDamageReduction = source.elementalDamageReduction;
      physicalDamageEvasionRate = source.physicalDamageEvasionRate;
      magicDamageEvasionRate = source.magicDamageEvasionRate;
      enemyStatusDurationBonus = source.enemyStatusDurationBonus;
      friendlyStatusDurationReduction = source.friendlyStatusDurationReduction;
      enemyMovementSpeedReduction = source.enemyMovementSpeedReduction;
      enemyWeightIgnore = source.enemyWeightIgnore;
      vanguardEffects = new ArrayList<>(source.vanguardEffects);
      guardEffects = new ArrayList<>(source.guardEffects);
      sniperEffects = new ArrayList<>(source.sniperEffects);
      casterEffects = new ArrayList<>(source.casterEffects);
      defenderEffects = new ArrayList<>(source.defenderEffects);
      medicEffects = new ArrayList<>(source.medicEffects);
      supporterEffects = new ArrayList<>(source.supporterEffects);
      specialistEffects = new ArrayList<>(source.specialistEffects);
      hope = source.hope;
      originiumIngots = source.originiumIngots;
      actionPoints = source.actionPoints;
      antiInterferenceIndex = source.antiInterferenceIndex;
      collapseValue = source.collapseValue;
      mentalBurdenLimit = source.mentalBurdenLimit;
      thoughts = source.thoughts;
      candles = source.candles;
      squadCapacity = source.squadCapacity;
      deploymentLimit = source.deploymentLimit;
      initialDeploymentPoints = source.initialDeploymentPoints;
      keys = source.keys;
      dice = source.dice;
      light = source.light;
      commandExperienceMultiplier = source.commandExperienceMultiplier;
      hopePerNonCombatNode = source.hopePerNonCombatNode;
      originiumIngotsPerNonCombatNode = source.originiumIngotsPerNonCombatNode;
      battleOriginiumIngotMultiplier = source.battleOriginiumIngotMultiplier;
      oneTimeFailureRecoveryObjectiveLife = source.oneTimeFailureRecoveryObjectiveLife;
      perSecondEffects = new ArrayList<>(source.perSecondEffects);
      perSecondConditionalEffects = new ArrayList<>(source.perSecondConditionalEffects);
      killEffects = new ArrayList<>(source.killEffects);
      enemySpawnStatEffects = new ArrayList<>(source.enemySpawnStatEffects);
      collectibleEffectTier = source.collectibleEffectTier;
    }

    private ArrayList<ProfessionEffect> professionEffects(SkillProfession profession) {
      return switch (profession) {
        case VANGUARD -> vanguardEffects;
        case GUARD -> guardEffects;
        case SNIPER -> sniperEffects;
        case CASTER -> casterEffects;
        case DEFENDER -> defenderEffects;
        case MEDIC -> medicEffects;
        case SUPPORTER -> supporterEffects;
        case SPECIALIST -> specialistEffects;
      };
    }

    private void clearProfessionEffects() {
      vanguardEffects.clear();
      guardEffects.clear();
      sniperEffects.clear();
      casterEffects.clear();
      defenderEffects.clear();
      medicEffects.clear();
      supporterEffects.clear();
      specialistEffects.clear();
    }

    private CombatStat build() {
      return new CombatStat(maxHealth, attack, defense, resistance, attackSpeed,
          damageReduction, defenseIgnore, enemyPhysicalDamageTakenBonus,
          enemyMagicDamageTakenBonus, healingAndHealthRegenerationBonus,
          offensiveDefensiveSkillPointRegeneration, naturalSkillPointRegeneration,
          trueDamageBonus, elementalDamageBonus, elementalDamageReduction,
          physicalDamageEvasionRate, magicDamageEvasionRate,
          enemyStatusDurationBonus, friendlyStatusDurationReduction,
          enemyMovementSpeedReduction, enemyWeightIgnore,
          vanguardEffects, guardEffects, sniperEffects, casterEffects,
          defenderEffects, medicEffects, supporterEffects, specialistEffects,
          hope, originiumIngots, actionPoints, antiInterferenceIndex, collapseValue,
          mentalBurdenLimit, thoughts, candles, squadCapacity, deploymentLimit,
          initialDeploymentPoints, keys, dice, light,
          commandExperienceMultiplier, hopePerNonCombatNode, originiumIngotsPerNonCombatNode,
          battleOriginiumIngotMultiplier, oneTimeFailureRecoveryObjectiveLife,
          perSecondEffects, perSecondConditionalEffects, killEffects, enemySpawnStatEffects,
          collectibleEffectTier);
    }
  }
}
