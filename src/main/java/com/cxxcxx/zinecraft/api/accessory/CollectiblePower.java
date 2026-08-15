package com.cxxcxx.zinecraft.api.accessory;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public sealed interface CollectiblePower
    permits CollectiblePower.ArchiveOnly,
    CollectiblePower.AttributeBoost,
    CollectiblePower.AttributeSet,
    CollectiblePower.Regeneration {
  final class ArchiveOnly implements CollectiblePower {
    @NotNull
    public static final CollectiblePower.ArchiveOnly INSTANCE = new CollectiblePower.ArchiveOnly();

    private ArchiveOnly() {
    }

    @Override
    public int hashCode() {
      return -602024562;
    }

    @Override
    public boolean equals(@Nullable Object other) {
      return this == other ? true : other instanceof CollectiblePower.ArchiveOnly;
    }

    @NotNull
    @Override
    public String toString() {
      return "ArchiveOnly";
    }
  }

  final class AttributeBoost implements CollectiblePower {
    @NotNull
    private final Holder<Attribute> attribute;
    private final double amount;
    @NotNull
    private final Operation operation;

    public AttributeBoost(@NotNull Holder<Attribute> attribute, double amount, @NotNull Operation operation) {
      super();
      this.attribute = attribute;
      this.amount = amount;
      this.operation = operation;
      if (!(Math.abs(this.amount) <= Double.MAX_VALUE)) {
        int i = 0;
        String string = "属性修饰值必须是有限数：" + this.amount;
        throw new IllegalArgumentException(string.toString());
      }
    }

    @NotNull
    public final Holder<Attribute> getAttribute() {
      return this.attribute;
    }

    public final double getAmount() {
      return this.amount;
    }

    @NotNull
    public final Operation getOperation() {
      return this.operation;
    }

    @Override
    public int hashCode() {
      int i = this.attribute.hashCode();
      i = i * 31 + Double.hashCode(this.amount);
      return i * 31 + this.operation.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
      if (this == other) {
        return true;
      } else if (!(other instanceof CollectiblePower.AttributeBoost attributeBoost)) {
        return false;
      } else if (!java.util.Objects.equals(this.attribute, attributeBoost.attribute)) {
        return false;
      } else {
        return Double.compare(this.amount, attributeBoost.amount) != 0 ? false : this.operation == attributeBoost.operation;
      }
    }

    @NotNull
    @Override
    public String toString() {
      return "AttributeBoost(attribute=" + this.attribute + ", amount=" + this.amount + ", operation=" + this.operation + ")";
    }
  }

  final class AttributeSet implements CollectiblePower {
    @NotNull
    private final List<CollectiblePower.AttributeBoost> boosts;

    public AttributeSet(@NotNull List<CollectiblePower.AttributeBoost> boosts) {
      super();
      this.boosts = boosts;
      if (this.boosts.isEmpty()) {
        int l = 0;
        String string1 = "复合藏品至少需要一个属性修饰";
        throw new IllegalArgumentException(string1.toString());
      }

      Iterable iterable = this.boosts;
      int i = 0;
      Iterable _this_mapTo_iv_iv = iterable;
      var collection = new ArrayList(com.cxxcxx.zinecraft.api.util.CollectionSupport.sizeHint(iterable, 10));
      int j = 0;

      for (Object object : _this_mapTo_iv_iv) {
        CollectiblePower.AttributeBoost it = (CollectiblePower.AttributeBoost) object;
        Collection collection1 = collection;
        int k = 0;
        collection1.add(it.getAttribute());
      }

      if (com.cxxcxx.zinecraft.api.util.CollectionSupport.distinct((Iterable & List) collection).size() != this.boosts.size()) {
        i = 0;
        String string = "复合藏品不能重复修饰同一属性";
        throw new IllegalArgumentException(string.toString());
      }
    }

    @NotNull
    public final List<CollectiblePower.AttributeBoost> getBoosts() {
      return this.boosts;
    }

    @Override
    public int hashCode() {
      return this.boosts.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
      if (this == other) {
        return true;
      } else {
        return !(other instanceof CollectiblePower.AttributeSet attributeSet) ? false : java.util.Objects.equals(this.boosts, attributeSet.boosts);
      }
    }

    @NotNull
    @Override
    public String toString() {
      return "AttributeSet(boosts=" + this.boosts + ")";
    }
  }

  final class Regeneration implements CollectiblePower {
    private final float maxHealthFraction;
    private final int intervalTicks;

    public Regeneration(float maxHealthFraction, int intervalTicks) {
      this.maxHealthFraction = maxHealthFraction;
      this.intervalTicks = intervalTicks;
      if (!(Math.abs(this.maxHealthFraction) <= Float.MAX_VALUE) || !(this.maxHealthFraction > 0.0F)) {
        int j = 0;
        String string1 = "每次回复的最大生命比例必须是有限正数";
        throw new IllegalArgumentException(string1.toString());
      }

      if (this.intervalTicks <= 0) {
        int i = 0;
        String string = "回复间隔必须大于 0";
        throw new IllegalArgumentException(string.toString());
      }
    }

    public final float getMaxHealthFraction() {
      return this.maxHealthFraction;
    }

    public final int getIntervalTicks() {
      return this.intervalTicks;
    }

    @Override
    public int hashCode() {
      int i = Float.hashCode(this.maxHealthFraction);
      return i * 31 + Integer.hashCode(this.intervalTicks);
    }

    @Override
    public boolean equals(@Nullable Object other) {
      if (this == other) {
        return true;
      } else if (!(other instanceof CollectiblePower.Regeneration regeneration)) {
        return false;
      } else {
        return Float.compare(this.maxHealthFraction, regeneration.maxHealthFraction) != 0 ? false : this.intervalTicks == regeneration.intervalTicks;
      }
    }

    @NotNull
    @Override
    public String toString() {
      return "Regeneration(maxHealthFraction=" + this.maxHealthFraction + ", intervalTicks=" + this.intervalTicks + ")";
    }
  }
}
