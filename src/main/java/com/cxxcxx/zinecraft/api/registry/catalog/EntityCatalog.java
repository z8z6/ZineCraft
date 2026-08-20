package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.ItemBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.MobBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 实体类型注册目录，并为生物统一登记属性、生成限制、刷怪蛋和名称翻译。
 */
public final class EntityCatalog {
  public static final Access ACCESS = new Access();

  private final String namespace;
  private final DeferredRegister<EntityType<?>> registry;
  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final List<MobBuilder<?>> mutableMobs = new ArrayList<>();
  public final List<MobBuilder<?>> mobs = Collections.unmodifiableList(mutableMobs);

  /**
   * 创建实体注册目录。
   *
   * @param namespace    模组命名空间
   * @param items        用于登记刷怪蛋的物品目录
   * @param translations 用于登记实体名称的翻译目录
   */
  public EntityCatalog(String namespace, ItemCatalog items, TranslationCatalog translations) {
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.registry = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE.key(), namespace);
    this.items = Objects.requireNonNull(items, "items");
    this.translations = Objects.requireNonNull(translations, "translations");
  }

  /**
   * 登记一个通用实体类型及其名称翻译。
   *
   * @param path 实体类型的命名空间内路径
   * @param zhCn 实体的简体中文名称
   * @param enUs 实体的英文名称
   * @param factory 创建实体实例的工厂
   * @param category 实体类别
   * @param configure 对原版实体类型构建器进行附加配置的回调
   * @param <T> 实体类型
   * @return 实体类型供应器
   */
  public <T extends Entity> Supplier<EntityType<T>> register(
      String path,
      String zhCn,
      String enUs,
      EntityType.EntityFactory<T> factory,
      MobCategory category,
      Consumer<? super EntityType.Builder<T>> configure
  ) {
    Supplier<EntityType<T>> type = registerType(path, factory, category, configure);
    translations.add("entity." + namespace + "." + path, zhCn, enUs);
    return type;
  }

  /**
   * 登记生物实体、刷怪蛋和名称翻译，并保存事件与数据生成所需元数据。
   *
   * @param builder 生物实体声明
   * @param <T> 生物实体类型
   * @return 已绑定登记结果的声明
   */
  public <T extends Mob> MobBuilder<T> register(MobBuilder<T> builder) {
    validate(builder);
    Supplier<EntityType<T>> type = registerType(builder.path, builder.factory, builder.category, builder.configure);
    var egg = builder.spawnEggData;
    var spawnEgg = new ItemBuilder<>(items,
        builder.path + "_spawn_egg",
        egg.zhCn(),
        egg.enUs(),
        () -> new net.minecraft.world.item.SpawnEggItem(
            type.get(), egg.primary(), egg.secondary(), new Item.Properties()),
        ACCESS.getSPAWN_EGG_MODEL(),
        true
    ).build().getItem();

    builder.bind(type, spawnEgg);
    translations.add("entity." + namespace + "." + builder.path, builder.zhCn, builder.enUs);
    mutableMobs.add(builder);
    return builder;
  }

  /**
   * 将实体延迟注册器和生物事件监听器挂接到模组事件总线。
   *
   * @param modBus 模组事件总线
   */
  public void register(IEventBus modBus) {
    registry.register(modBus);
    modBus.addListener(this::createAttributes);
    modBus.addListener(this::registerSpawnPlacements);
  }

  /**
   * 创建并登记一个实体类型。
   *
   * @param path 实体类型路径
   * @param factory 实体工厂
   * @param category 实体类别
   * @param configure 原版实体类型构建器配置回调
   * @param <T> 实体类型
   * @return 实体类型供应器
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private <T extends Entity> Supplier<EntityType<T>> registerType(
      String path,
      EntityType.EntityFactory<T> factory,
      MobCategory category,
      Consumer<? super EntityType.Builder<T>> configure
  ) {
    return (Supplier) registry.register(path, () -> {
      EntityType.Builder<T> type = EntityType.Builder.of(factory, category);
      configure.accept(type);
      return type.build(path);
    });
  }

  /**
   * 响应属性创建事件并登记所有生物的默认属性。
   *
   * @param event 实体属性创建事件
   */
  private void createAttributes(EntityAttributeCreationEvent event) {
    for (MobBuilder<?> mob : mobs) {
      event.put(mob.get(), mob.attributes.get().build());
    }
  }

  /**
   * 响应生成位置事件并登记所有生物的自然生成限制。
   *
   * @param event 生成位置登记事件
   */
  private void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
    for (MobBuilder<?> mob : mobs) {
      registerSpawnPlacement(event, mob);
    }
  }

  /**
   * 登记单个生物的自然生成位置限制。
   *
   * @param event 生成位置登记事件
   * @param mob 生物声明
   * @param <T> 生物实体类型
   */
  private <T extends Mob> void registerSpawnPlacement(RegisterSpawnPlacementsEvent event, MobBuilder<T> mob) {
    if (mob.restriction != null) {
      event.register(
          mob.get(),
          mob.restriction.placement(),
          mob.restriction.heightmap(),
          mob.restriction.predicate(),
          RegisterSpawnPlacementsEvent.Operation.REPLACE
      );
    }
  }

  /**
   * 校验生物 ID、名称、刷怪蛋、掉落物及重复项。
   *
   * @param builder 待校验的生物声明
   */
  private void validate(MobBuilder<?> builder) {
    if (!ResourceLocation.isValidPath(builder.path)) {
      throw new IllegalArgumentException("Mob ID 路径无效：" + builder.path);
    }
    if (builder.zhCn == null || builder.zhCn.isBlank()) {
      throw new IllegalArgumentException("Mob 中文名不能为空：" + builder.path);
    }
    if (builder.enUs == null || builder.enUs.isBlank()) {
      throw new IllegalArgumentException("Mob 英文名不能为空：" + builder.path);
    }
    Objects.requireNonNull(builder.spawnEggData, "Mob 必须声明刷怪蛋：" + builder.path);
    if (builder.drops.isEmpty() && !builder.noDrops) {
      throw new IllegalStateException("Mob 必须声明至少一种掉落物或显式声明无掉落：" + builder.path);
    }
    if (mutableMobs.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("Mob ID 重复：" + builder.path);
    }
  }

  /**
   * 暴露注册数据生成需要复用的原版模型模板。
   */
  public static final class Access {
    private static final ModelTemplate SPAWN_EGG_MODEL = new ModelTemplate(
        Optional.of(ResourceLocation.withDefaultNamespace("item/template_spawn_egg")), Optional.empty());

    /** @return 原版刷怪蛋物品模型模板 */
    public ModelTemplate getSPAWN_EGG_MODEL() {
      return SPAWN_EGG_MODEL;
    }
  }
}
