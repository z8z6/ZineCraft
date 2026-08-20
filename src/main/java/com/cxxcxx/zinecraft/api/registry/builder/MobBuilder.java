package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.entity.MobSpawnRestriction;
import com.cxxcxx.zinecraft.api.registry.catalog.EntityCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 生物实体声明构建器，集中保存实体类型、属性、生成限制、刷怪蛋和掉落物元数据。
 *
 * @param <T> 要登记的生物实体类型
 */
public final class MobBuilder<T extends Mob> implements Supplier<EntityType<T>> {
  public final String path;
  public final String zhCn;
  public String enUs;
  public final EntityType.EntityFactory<T> factory;
  public final MobCategory category;
  public final Supplier<? extends AttributeSupplier.Builder> attributes;
  public final MobSpawnRestriction<T> restriction;
  public final Consumer<? super EntityType.Builder<T>> configure;
  private final EntityCatalog catalog;
  private final List<MobDrop> mutableDrops = new ArrayList<>();
  public final List<MobDrop> drops = Collections.unmodifiableList(mutableDrops);
  public boolean noDrops;
  public SpawnEggData spawnEggData;
  private Supplier<EntityType<T>> type;
  private DeferredItem<SpawnEggItem> spawnEgg;

  /**
   * 创建生物实体声明。
   *
   * @param catalog     接收生物的实体目录
   * @param path        实体类型的命名空间内路径
   * @param zhCn        实体的简体中文名称
   * @param factory     根据实体类型和世界创建实体的工厂
   * @param category    实体生成类别
   * @param attributes  创建默认属性集合的供应器
   * @param restriction 自然生成位置限制；为 {@code null} 时不登记生成限制
   * @param configure   对原版实体类型构建器进行尺寸等额外配置的回调
   */
  public MobBuilder(
      EntityCatalog catalog,
      String path,
      String zhCn,
      EntityType.EntityFactory<T> factory,
      MobCategory category,
      Supplier<? extends AttributeSupplier.Builder> attributes,
      MobSpawnRestriction<T> restriction,
      Consumer<? super EntityType.Builder<T>> configure
  ) {
    this.catalog = catalog;
    this.path = path;
    this.zhCn = zhCn;
    this.enUs = TranslationCatalog.toDisplayName(path);
    this.factory = Objects.requireNonNull(factory, "Mob factory 不能为空：" + path);
    this.category = Objects.requireNonNull(category, "Mob category 不能为空：" + path);
    this.attributes = Objects.requireNonNull(attributes, "Mob attributes 不能为空：" + path);
    this.restriction = restriction;
    this.configure = Objects.requireNonNull(configure, "Mob configure 不能为空：" + path);
  }

  /**
   * 声明该生物的刷怪蛋颜色和名称。
   *
   * @param primary 刷怪蛋主色的 RGB 整数值
   * @param secondary 刷怪蛋斑点色的 RGB 整数值
   * @param zhCn 刷怪蛋的简体中文名称
   * @param enUs 刷怪蛋的英文名称
   * @return 当前构建器
   */
  public MobBuilder<T> spawnEgg(int primary, int secondary, String zhCn, String enUs) {
    if (spawnEggData != null) throw new IllegalStateException("Mob 不能重复声明刷怪蛋：" + path);
    spawnEggData = new SpawnEggData(primary, secondary, zhCn, enUs);
    return this;
  }

  /**
   * 添加一种供战利品数据生成使用的掉落物。
   *
   * @param item 掉落物品
   * @return 当前构建器
   */
  public MobBuilder<T> drop(ItemLike item) {
    if (noDrops) throw new IllegalStateException("无掉落 Mob 不能再声明掉落物：" + path);
    mutableDrops.add(new MobDrop(Objects.requireNonNull(item, "掉落物不能为空")));
    return this;
  }

  /**
   * 明确声明该生物没有战利品掉落。
   */
  public MobBuilder<T> noDrops() {
    if (!mutableDrops.isEmpty()) throw new IllegalStateException("已有掉落物的 Mob 不能声明无掉落：" + path);
    noDrops = true;
    return this;
  }

  /**
   * 校验并将生物、刷怪蛋和翻译登记到所属目录。
   *
   * @return 当前构建器
   */
  public MobBuilder<T> build() {
    if (type != null) throw new IllegalStateException("Mob builder 不能重复 build：" + path);
    return catalog.register(this);
  }

  /** @return 登记后的实体类型供应器 */
  public Supplier<EntityType<T>> type() {
    return Objects.requireNonNull(type, "Mob 尚未 build：" + path);
  }

  /** @return 登记后的刷怪蛋延迟物品句柄 */
  public DeferredItem<SpawnEggItem> spawnEgg() {
    return Objects.requireNonNull(spawnEgg, "Mob 尚未 build：" + path);
  }

  /**
   * 由实体目录绑定登记结果。
   *
   * @param type 实体类型供应器
   * @param spawnEgg 刷怪蛋延迟物品句柄
   */
  public void bind(Supplier<EntityType<T>> type, DeferredItem<SpawnEggItem> spawnEgg) {
    this.type = Objects.requireNonNull(type, "type");
    this.spawnEgg = Objects.requireNonNull(spawnEgg, "spawnEgg");
  }

  /** @return 已登记的实体类型 */
  @Override
  public EntityType<T> get() {
    return type().get();
  }

  /**
   * 生物掉落物声明。
   *
   * @param item 掉落物品
   */
  public record MobDrop(ItemLike item) {
  }

  public MobBuilder<T> enUs(String enUs) {
    this.enUs = enUs;
    return this;
  }

  /**
   * 刷怪蛋的颜色和本地化名称。
   *
   * @param primary 主色 RGB 整数值
   * @param secondary 斑点色 RGB 整数值
   * @param zhCn 简体中文名称
   * @param enUs 英文名称
   */
  public record SpawnEggData(int primary, int secondary, String zhCn, String enUs) {
  }
}
