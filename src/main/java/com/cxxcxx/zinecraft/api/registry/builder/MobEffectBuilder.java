package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.MobEffectCatalog;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/** 声明一个原版 MobEffect 及其属性修饰符。 */
public final class MobEffectBuilder implements Supplier<MobEffect> {
  private static final Pattern MODIFIER_PATH = Pattern.compile("[a-z0-9_]+(?:/[a-z0-9_]+)*");

  public final MobEffectCatalog catalog;
  public final String path;
  public final String zhCn;
  public final String enUs;
  public final MobEffectCategory category;
  public final int color;
  private final List<AttributeEffect> mutableAttributeEffects = new ArrayList<>();
  public final List<AttributeEffect> attributeEffects = Collections.unmodifiableList(mutableAttributeEffects);
  private DeferredHolder<MobEffect, MobEffect> effect;

  public MobEffectBuilder(
      MobEffectCatalog catalog,
      String path,
      String zhCn,
      String enUs,
      MobEffectCategory category,
      int color
  ) {
    this.catalog = Objects.requireNonNull(catalog, "MobEffect 目录不能为空");
    this.path = Objects.requireNonNull(path, "MobEffect ID 不能为空");
    this.zhCn = Objects.requireNonNull(zhCn, "MobEffect 中文名不能为空：" + path);
    this.enUs = Objects.requireNonNull(enUs, "MobEffect 英文名不能为空：" + path);
    this.category = Objects.requireNonNull(category, "MobEffect 类别不能为空：" + path);
    if (zhCn.isBlank() || enUs.isBlank()) throw new IllegalArgumentException("MobEffect 名称不能为空：" + path);
    if (color < 0 || color > 0xFFFFFF) throw new IllegalArgumentException("MobEffect 颜色必须是 RGB：" + path);
    this.color = color;
  }

  public MobEffectBuilder attributeModifier(
      Holder<Attribute> attribute,
      String modifierPath,
      double amount,
      AttributeModifier.Operation operation
  ) {
    if (effect != null) throw new IllegalStateException("已 build 的 MobEffect 不能继续修改：" + path);
    if (modifierPath == null || !MODIFIER_PATH.matcher(modifierPath).matches()) {
      throw new IllegalArgumentException("MobEffect 属性修饰符 ID 格式无效：" + modifierPath);
    }
    if (mutableAttributeEffects.stream().anyMatch(entry -> entry.path().equals(modifierPath))) {
      throw new IllegalArgumentException("MobEffect 属性修饰符 ID 重复：" + path + "/" + modifierPath);
    }
    mutableAttributeEffects.add(new AttributeEffect(attribute, modifierPath, amount, operation));
    return this;
  }

  public MobEffectBuilder build() {
    if (effect != null) throw new IllegalStateException("MobEffect builder 不能重复 build：" + path);
    return catalog.register(this);
  }

  /** 仅供所属 Catalog 的延迟注册工厂创建最终效果。 */
  public MobEffect create() {
    if (effect == null) throw new IllegalStateException("MobEffect 尚未 build：" + path);
    MobEffect built = new BuiltMobEffect(category, color);
    for (AttributeEffect entry : mutableAttributeEffects) {
      built.addAttributeModifier(
          entry.attribute(), modifierId(entry.path()), entry.amount(), entry.operation()
      );
    }
    return built;
  }

  /** 由所属 Catalog 绑定延迟注册句柄。 */
  public void bind(DeferredHolder<MobEffect, MobEffect> effect) {
    if (this.effect != null) throw new IllegalStateException("MobEffect 已绑定：" + path);
    this.effect = Objects.requireNonNull(effect, "effect");
  }

  public DeferredHolder<MobEffect, MobEffect> holder() {
    return Objects.requireNonNull(effect, "MobEffect 尚未 build：" + path);
  }

  @Override
  public MobEffect get() {
    return holder().get();
  }

  private ResourceLocation modifierId(String suffix) {
    return ResourceLocation.fromNamespaceAndPath(catalog.namespace(), path + "/" + suffix);
  }

  public record AttributeEffect(
      Holder<Attribute> attribute,
      String path,
      double amount,
      AttributeModifier.Operation operation
  ) {
    public AttributeEffect {
      Objects.requireNonNull(attribute, "MobEffect 属性不能为空");
      Objects.requireNonNull(path, "MobEffect 属性修饰符 ID 不能为空");
      if (!Double.isFinite(amount)) throw new IllegalArgumentException("MobEffect 属性修饰值必须是有限数");
      Objects.requireNonNull(operation, "MobEffect 属性修饰运算不能为空");
    }
  }

  private static final class BuiltMobEffect extends MobEffect {
    private BuiltMobEffect(MobEffectCategory category, int color) {
      super(category, color);
    }
  }
}