package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.combat.CombatDamageProfile;
import com.cxxcxx.zinecraft.api.combat.CombatDamageProvider;
import com.cxxcxx.zinecraft.api.combat.CombatDamageType;
import com.cxxcxx.zinecraft.api.registry.catalog.SkillCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
import com.cxxcxx.zinecraft.api.skill.SkillDemoTheme;
import com.cxxcxx.zinecraft.api.skill.SkillItem;
import com.cxxcxx.zinecraft.api.skill.SkillProfession;
import com.cxxcxx.zinecraft.api.skill.SkillSpRecoveryType;
import com.cxxcxx.zinecraft.api.skill.SkillTriggerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 技能注册条目，集中保存技能资料、Ponder 主题与对应物品的注册结果。
 */
public final class SkillBuilder implements ItemLike, CombatDamageProvider {
  /** 接收并最终登记当前技能声明的技能目录。 */
  public final SkillCatalog catalog;

  /** 技能与对应物品共用的注册路径，不包含命名空间。 */
  public final String path;

  /** 技能的简体中文显示名称。 */
  public final String zhCn;

  /** 尚未 build 的直接伤害段；对外只通过 damageProfiles() 暴露不可变副本。 */
  private final List<CombatDamageProfile> mutableDamageProfiles = new ArrayList<>();

  /** 尚未 build 的客户端特效声明；对外只通过 effects() 暴露不可变副本。 */
  private final List<VfxBuilder> mutableEffects = new ArrayList<>();

  /** 技能的英文显示名称；构造时默认由 path 转换得到。 */
  public String enUs;

  /** 使用该技能的干员简体中文名称。 */
  public String operatorZhCn;

  /** 使用该技能的干员英文名称。 */
  public String operatorEnUs;

  /** 干员所属职业，用于 Tooltip 与 Ponder 展示。 */
  public SkillProfession profession;

  /** 技能的技力回复类型，用于统一产生双语显示文本。 */
  public SkillSpRecoveryType recoveryType;

  /** 技能的触发方式，用于统一产生双语显示文本。 */
  public SkillTriggerType triggerType;

  /** 技能资料中的初始技力；当前不代表运行时已经实现 SP 状态。 */
  public int initialSp;

  /** 技能资料中的技力消耗；当前不代表施放时会自动扣除 SP。 */
  public int spCost;

  /** 技能资料中的持续秒数；瞬时或弹药技能为 {@code null}。 */
  @Nullable
  public Integer durationSeconds;

  /** 技能效果说明的简体中文文本。 */
  public String descriptionZhCn;

  /** 技能效果说明的英文文本。 */
  public String descriptionEnUs;

  /** 客户端 Ponder 演示所采用的预设主题。 */
  public SkillDemoTheme theme;

  /** 是否显式调用过 stats(...)，用于区分合法的全零数值与尚未配置。 */
  public boolean statsConfigured;

  /** build 后由 SkillCatalog 绑定的 NeoForge 延迟物品句柄；build 前为 null。 */
  public DeferredItem<SkillItem> item;

  /**
   * 创建尚未登记的技能声明，英文名默认由路径生成。
   *
   * @param catalog 接收技能的目录
   * @param path    技能及其物品共用的注册路径
   * @param zhCn    技能简体中文名称
   */
  public SkillBuilder(SkillCatalog catalog, String path, String zhCn) {
    this.catalog = Objects.requireNonNull(catalog, "技能目录不能为空");
    this.path = Objects.requireNonNull(path, "技能 ID 不能为空");
    this.zhCn = Objects.requireNonNull(zhCn, "技能中文名不能为空：" + path);
    this.enUs = TranslationCatalog.toDisplayName(path);
  }

  /**
   * 设置技能英文名称。
   */
  public SkillBuilder enUs(String enUs) {
    ensureMutable();
    this.enUs = Objects.requireNonNull(enUs, "技能英文名不能为空：" + path);
    return this;
  }

  /**
   * 设置使用该技能的干员及其职业。
   */
  public SkillBuilder operator(String zhCn, String enUs, SkillProfession profession) {
    ensureMutable();
    this.operatorZhCn = Objects.requireNonNull(zhCn, "干员中文名不能为空：" + path);
    this.operatorEnUs = Objects.requireNonNull(enUs, "干员英文名不能为空：" + path);
    this.profession = Objects.requireNonNull(profession, "技能职业不能为空：" + path);
    return this;
  }

  /**
   * 设置技力回复类型与触发方式。
   */
  public SkillBuilder activation(
      SkillSpRecoveryType recoveryType,
      SkillTriggerType triggerType
  ) {
    ensureMutable();
    this.recoveryType = Objects.requireNonNull(recoveryType, "技力回复类型不能为空：" + path);
    this.triggerType = Objects.requireNonNull(triggerType, "技能触发方式不能为空：" + path);
    return this;
  }

  /**
   * 设置技能技力数值与持续时间；瞬时或弹药技能的持续时间传入 {@code null}。
   */
  public SkillBuilder stats(int initialSp, int spCost, @Nullable Integer durationSeconds) {
    ensureMutable();
    this.initialSp = initialSp;
    this.spCost = spCost;
    this.durationSeconds = durationSeconds;
    this.statsConfigured = true;
    return this;
  }

  /**
   * 设置技能的双语描述。
   */
  public SkillBuilder description(String zhCn, String enUs) {
    ensureMutable();
    this.descriptionZhCn = Objects.requireNonNull(zhCn, "技能中文描述不能为空：" + path);
    this.descriptionEnUs = Objects.requireNonNull(enUs, "技能英文描述不能为空：" + path);
    return this;
  }

  /**
   * 声明技能每次直接命中的攻击力倍率与伤害类型。
   * 例如 {@code 2.5} 表示造成当前攻击力 250% 的伤害。
   */
  public SkillBuilder damage(double attackMultiplier, CombatDamageType type) {
    ensureMutable();
    mutableDamageProfiles.add(CombatDamageProfile.attackMultiplier(attackMultiplier, type));
    return this;
  }

  /**
   * 设置客户端 Ponder 演示使用的视觉主题。
   */
  public SkillBuilder theme(SkillDemoTheme theme) {
    ensureMutable();
    this.theme = Objects.requireNonNull(theme, "技能演示主题不能为空：" + path);
    return this;
  }

  /**
   * 声明技能激活或生效时使用的客户端特效。
   */
  public SkillBuilder effect(VfxBuilder effect) {
    ensureMutable();
    Objects.requireNonNull(effect, "技能特效不能为空：" + path);
    if (mutableEffects.stream().anyMatch(entry -> entry.getId().equals(effect.getId()))) {
      throw new IllegalArgumentException("技能特效重复：" + path + " / " + effect.getId());
    }
    mutableEffects.add(effect);
    return this;
  }

  /**
   * @return 技能声明的全部客户端特效
   */
  public List<VfxBuilder> effects() {
    return List.copyOf(mutableEffects);
  }

  /**
   * @return 技能的全部直接伤害段；辅助、治疗或控制技能为空列表
   */
  @Override
  public List<CombatDamageProfile> damageProfiles() {
    return List.copyOf(mutableDamageProfiles);
  }

  /**
   * @return 已登记技能对应的 NeoForge 延迟物品句柄
   */
  public DeferredItem<SkillItem> getItem() {
    if (item == null) throw new IllegalStateException("技能尚未 build：" + path);
    return item;
  }

  /**
   * @return 已登记技能对应的物品实例
   */
  @Override
  public @NotNull Item asItem() {
    return getItem().get();
  }

  /**
   * 校验并将技能登记到所属目录。
   *
   * @return 已登记的当前构建器
   */
  public SkillBuilder build() {
    ensureMutable();
    return catalog.register(this);
  }

  private void ensureMutable() {
    if (item != null) {
      throw new IllegalStateException("技能 builder 不能重复 build 或在 build 后修改：" + path);
    }
  }
}
