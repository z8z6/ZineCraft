package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.collection.CollectibleItem;
import com.cxxcxx.zinecraft.api.collection.CollectiblePower;
import com.cxxcxx.zinecraft.api.registry.catalog.CollectibleCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * 藏品注册条目，集中保存显示文本、原作资料、Minecraft 效果和物品注册结果。
 */
public final class CollectibleBuilder implements ItemLike {
  public final CollectibleCatalog catalog;
  public final String path;
  public final String orderId;
  public final String zhCn;
  public String enUs;
  public String originalEffectZhCn;
  public String originalEffectEnUs;
  public String descriptionZhCn;
  public String descriptionEnUs;
  public String minecraftEffectZhCn;
  public String minecraftEffectEnUs;
  public CollectiblePower power;
  public List<String> sourceRules = List.of();
  public Rarity rarity = Rarity.COMMON;
  public int originalEffectLineCount;
  public int descriptionLineCount;
  public DeferredItem<CollectibleItem> item;

  /**
   * 创建尚未登记的藏品声明。
   *
   * @param catalog 接收藏品的目录
   * @param path    藏品物品的命名空间内路径
   * @param orderId 原作藏品编号
   * @param zhCn    藏品简体中文名称
   */
  public CollectibleBuilder(CollectibleCatalog catalog, String path, String orderId, String zhCn) {
    this.catalog = Objects.requireNonNull(catalog, "藏品目录不能为空");
    this.path = Objects.requireNonNull(path, "藏品 ID 不能为空");
    this.orderId = Objects.requireNonNull(orderId, "藏品编号不能为空：" + path);
    this.zhCn = Objects.requireNonNull(zhCn, "藏品中文名不能为空：" + path);
    this.enUs = TranslationCatalog.toDisplayName(path);
  }

  /**
   * 设置藏品英文名称。
   */
  public CollectibleBuilder enUs(String enUs) {
    ensureMutable();
    this.enUs = Objects.requireNonNull(enUs, "藏品英文名不能为空：" + path);
    return this;
  }

  /**
   * 设置原作效果文本。
   */
  public CollectibleBuilder originalEffect(String zhCn, String enUs) {
    ensureMutable();
    this.originalEffectZhCn = Objects.requireNonNull(zhCn, "藏品中文原效果不能为空：" + path);
    this.originalEffectEnUs = Objects.requireNonNull(enUs, "藏品英文原效果不能为空：" + path);
    return this;
  }

  /**
   * 设置原作描述文本。
   */
  public CollectibleBuilder description(String zhCn, String enUs) {
    ensureMutable();
    this.descriptionZhCn = Objects.requireNonNull(zhCn, "藏品中文描述不能为空：" + path);
    this.descriptionEnUs = Objects.requireNonNull(enUs, "藏品英文描述不能为空：" + path);
    return this;
  }

  /**
   * 设置 Minecraft 适配说明及服务端效果。
   */
  public CollectibleBuilder minecraftEffect(String zhCn, String enUs, CollectiblePower power) {
    ensureMutable();
    this.minecraftEffectZhCn = Objects.requireNonNull(zhCn, "藏品中文适配说明不能为空：" + path);
    this.minecraftEffectEnUs = Objects.requireNonNull(enUs, "藏品英文适配说明不能为空：" + path);
    this.power = Objects.requireNonNull(power, "藏品效果不能为空：" + path);
    return this;
  }

  /**
   * 设置物品稀有度。
   */
  /** 记录尚未由运行时消费的原作规则。 */
  public CollectibleBuilder sourceRules(List<String> sourceRules) {
    ensureMutable();
    this.sourceRules = List.copyOf(sourceRules);
    return this;
  }

  public CollectibleBuilder rarity(Rarity rarity) {
    ensureMutable();
    this.rarity = Objects.requireNonNull(rarity, "藏品稀有度不能为空：" + path);
    return this;
  }

  /**
   * @return 已登记藏品对应的物品实例
   */
  @Override
  public @NotNull Item asItem() {
    return item.get();
  }

  /**
   * @return 已登记藏品的可组合效果
   */
  public CollectiblePower getPower() {
    return power;
  }

  /**
   * @return NeoForge 延迟物品句柄；调用前应先完成 {@link #build()}
   */
  public DeferredItem<CollectibleItem> getItem() {
    return item;
  }

  /**
   * 校验并将藏品登记到所属目录。
   *
   * @return 已登记的当前构建器
   */
  public CollectibleBuilder build() {
    ensureMutable();
    return catalog.register(this);
  }

  private void ensureMutable() {
    if (item != null) {
      throw new IllegalStateException("藏品 builder 不能重复 build 或在 build 后修改：" + path);
    }
  }
}
