package com.cxxcxx.zinecraft.api.collection;

import com.cxxcxx.zinecraft.api.combat.CombatModifierPhase;
import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 一件藏品的可组合运行时效果。
 */
public record CollectiblePower(
    List<AttributeBoost> attributes,
    List<CombatStatModifier> combatStats,
    @Nullable Regeneration regeneration,
    Exploration exploration,
    List<String> sourceRules
) {
  public static final CollectiblePower NONE = builder().build();

  /**
   * 复制传入集合并校验藏品效果，使构造后的效果对象保持不可变且可安全组合。
   */
  public CollectiblePower {
    attributes = List.copyOf(attributes);
    combatStats = List.copyOf(combatStats);
    exploration = Objects.requireNonNull(exploration, "exploration");
    sourceRules = sourceRules.stream().map(String::trim).filter(rule -> !rule.isEmpty()).toList();
    if (attributes.stream().map(AttributeBoost::attribute).distinct().count() != attributes.size()) {
      throw new IllegalArgumentException("复合藏品不能重复修饰同一原版属性");
    }
    for (CombatStatModifier modifier : combatStats) {
      if (modifier.phase() != CombatModifierPhase.COLLECTIBLE_ADDITION
          && modifier.phase() != CombatModifierPhase.COLLECTIBLE_MULTIPLIER) {
        throw new IllegalArgumentException("藏品只能使用藏品加算或乘算阶段");
      }
    }
  }

  /**
   * 创建一个空的藏品效果构建器。
   *
   * @return 新的效果构建器
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * 表示直接作用于 Minecraft 原版属性的一项修饰。
   */
  public record AttributeBoost(Holder<Attribute> attribute, double amount, Operation operation) {
    /** 校验属性、运算方式和修饰值是否合法。 */
    public AttributeBoost {
      Objects.requireNonNull(attribute, "attribute");
      Objects.requireNonNull(operation, "operation");
      if (!Double.isFinite(amount)) throw new IllegalArgumentException("属性修饰值必须是有限数：" + amount);
    }
  }

  /**
   * 表示藏品提供的周期性生命回复；{@code amount} 必须为正数，
   * {@code percentage=true} 时表示每次回复最大生命值的一定比例。
   */
  public record Regeneration(float amount, int intervalTicks, boolean percentage) {
    /** 校验回复量与触发间隔。 */
    public Regeneration {
      if (!Float.isFinite(amount) || amount <= 0.0F) throw new IllegalArgumentException("回复量必须是有限正数");
      if (intervalTicks <= 0) throw new IllegalArgumentException("回复间隔必须大于 0");
    }

    /**
     * 创建按最大生命值比例回复的效果。
     *
     * @param fraction 每次回复的最大生命值比例
     * @param intervalTicks 两次回复之间的游戏刻数
     * @return 百分比生命回复效果
     */
    public static Regeneration percentage(float fraction, int intervalTicks) {
      return new Regeneration(fraction, intervalTicks, true);
    }

    /**
     * 创建按固定生命值回复的效果。
     *
     * @param health 每次回复的生命值
     * @param intervalTicks 两次回复之间的游戏刻数
     * @return 固定生命回复效果
     */
    public static Regeneration flat(float health, int intervalTicks) {
      return new Regeneration(health, intervalTicks, false);
    }
  }

  /**
   * 集成战略探索资源的直接增量或倍率；倍率字段以 1.0 为不变。
   */
  public record Exploration(
      int hope,
      int objectiveLife,
      int temporaryObjectiveLife,
      int originiumIngots,
      int squadCapacity,
      int deploymentLimit,
      int initialDeploymentPoints,
      int keys,
      int dice,
      int light,
      double commandExperienceMultiplier,
      int hopePerNonCombatNode,
      int originiumIngotsPerNonCombatNode,
      double battleOriginiumIngotMultiplier,
      int oneTimeFailureRecoveryObjectiveLife
  ) {
    public static final Exploration NONE = new Exploration(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.0, 0, 0, 1.0, 0);

    /** 校验探索效果中的倍率字段是否为有限正数。 */
    public Exploration {
      if (!Double.isFinite(commandExperienceMultiplier) || commandExperienceMultiplier <= 0.0) {
        throw new IllegalArgumentException("指挥经验倍率必须是有限正数");
      }
      if (!Double.isFinite(battleOriginiumIngotMultiplier) || battleOriginiumIngotMultiplier <= 0.0) {
        throw new IllegalArgumentException("战斗源石锭倍率必须是有限正数");
      }
    }

    /**
     * 合并另一组探索效果：资源增量相加，倍率字段相乘。
     *
     * @param other 要合并的探索效果
     * @return 合并后生成的新探索效果
     */
    public Exploration plus(Exploration other) {
      Objects.requireNonNull(other, "other");
      return new Exploration(
          hope + other.hope,
          objectiveLife + other.objectiveLife,
          temporaryObjectiveLife + other.temporaryObjectiveLife,
          originiumIngots + other.originiumIngots,
          squadCapacity + other.squadCapacity,
          deploymentLimit + other.deploymentLimit,
          initialDeploymentPoints + other.initialDeploymentPoints,
          keys + other.keys,
          dice + other.dice,
          light + other.light,
          commandExperienceMultiplier * other.commandExperienceMultiplier,
          hopePerNonCombatNode + other.hopePerNonCombatNode,
          originiumIngotsPerNonCombatNode + other.originiumIngotsPerNonCombatNode,
          battleOriginiumIngotMultiplier * other.battleOriginiumIngotMultiplier,
          oneTimeFailureRecoveryObjectiveLife + other.oneTimeFailureRecoveryObjectiveLife
      );
    }

    /**
     * 判断当前对象是否没有提供任何探索增益。
     *
     * @return 所有增量为零且倍率均为 1.0 时返回 {@code true}
     */
    public boolean isEmpty() {
      return equals(NONE);
    }
  }

  /**
   * 以链式调用方式累积一件藏品的属性、战斗、回复和探索效果。
   */
  public static final class Builder {
    private final List<AttributeBoost> attributes = new ArrayList<>();
    private final List<CombatStatModifier> combatStats = new ArrayList<>();
    private final List<String> sourceRules = new ArrayList<>();
    private Regeneration regeneration;
    private int hope;
    private int objectiveLife;
    private int temporaryObjectiveLife;
    private int originiumIngots;
    private int squadCapacity;
    private int deploymentLimit;
    private int initialDeploymentPoints;
    private int keys;
    private int dice;
    private int light;
    private double commandExperienceMultiplier = 1.0;
    private int hopePerNonCombatNode;
    private int originiumIngotsPerNonCombatNode;
    private double battleOriginiumIngotMultiplier = 1.0;
    private int oneTimeFailureRecoveryObjectiveLife;

    /** 添加一项 Minecraft 原版属性修饰。 */
    public Builder attribute(AttributeBoost value) {
      attributes.add(Objects.requireNonNull(value));
      return this;
    }

    /** 添加一项藏品战斗属性修饰。 */
    public Builder combatStat(CombatStatModifier value) {
      combatStats.add(Objects.requireNonNull(value));
      return this;
    }

    /** 设置唯一的周期性生命回复效果。 */
    public Builder regeneration(Regeneration value) {
      if (regeneration != null) throw new IllegalStateException("每件藏品只能声明一种回复效果");
      regeneration = Objects.requireNonNull(value);
      return this;
    }

    /** 记录一条用于追溯原作效果映射依据的规则文本。 */
    public Builder sourceRule(String value) {
      sourceRules.add(Objects.requireNonNull(value));
      return this;
    }

    /** 累加希望值。 */
    public Builder hope(int value) {
      hope += value;
      return this;
    }

    /** 累加目标生命值。 */
    public Builder objectiveLife(int value) {
      objectiveLife += value;
      return this;
    }

    /** 累加临时目标生命值。 */
    public Builder temporaryObjectiveLife(int value) {
      temporaryObjectiveLife += value;
      return this;
    }

    /** 累加源石锭数量。 */
    public Builder originiumIngots(int value) {
      originiumIngots += value;
      return this;
    }

    /** 累加编队容量。 */
    public Builder squadCapacity(int value) {
      squadCapacity += value;
      return this;
    }

    /** 累加可部署单位上限。 */
    public Builder deploymentLimit(int value) {
      deploymentLimit += value;
      return this;
    }

    /** 累加战斗初始部署点数。 */
    public Builder initialDeploymentPoints(int value) {
      initialDeploymentPoints += value;
      return this;
    }

    /** 累加钥匙数量。 */
    public Builder keys(int value) {
      keys += value;
      return this;
    }

    /** 累加骰子数量。 */
    public Builder dice(int value) {
      dice += value;
      return this;
    }

    /** 累加灯火值。 */
    public Builder light(int value) {
      light += value;
      return this;
    }

    /** 乘入指挥经验获取倍率。 */
    public Builder commandExperienceMultiplier(double value) {
      commandExperienceMultiplier *= value;
      return this;
    }

    /** 累加每个非战斗节点获得的希望。 */
    public Builder hopePerNonCombatNode(int value) {
      hopePerNonCombatNode += value;
      return this;
    }

    /** 累加每个非战斗节点获得的源石锭。 */
    public Builder originiumIngotsPerNonCombatNode(int value) {
      originiumIngotsPerNonCombatNode += value;
      return this;
    }

    /** 乘入战斗获得源石锭的倍率。 */
    public Builder battleOriginiumIngotMultiplier(double value) {
      battleOriginiumIngotMultiplier *= value;
      return this;
    }

    /** 累加首次失败时恢复的目标生命值。 */
    public Builder oneTimeFailureRecoveryObjectiveLife(int value) {
      oneTimeFailureRecoveryObjectiveLife += value;
      return this;
    }

    /**
     * 根据已累积的字段构建不可变藏品效果。
     *
     * @return 完整的藏品运行时效果
     */
    public CollectiblePower build() {
      return new CollectiblePower(
          attributes, combatStats, regeneration,
          new Exploration(hope, objectiveLife, temporaryObjectiveLife, originiumIngots,
              squadCapacity, deploymentLimit, initialDeploymentPoints, keys, dice, light,
              commandExperienceMultiplier, hopePerNonCombatNode, originiumIngotsPerNonCombatNode,
              battleOriginiumIngotMultiplier, oneTimeFailureRecoveryObjectiveLife),
          sourceRules
      );
    }
  }
}
