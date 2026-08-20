package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.datagen.RegistryDataContributor;
import com.cxxcxx.zinecraft.api.registry.builder.BiomeBuilder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 群系动态注册目录，负责校验 Builder、登记翻译并在 bootstrap 阶段创建群系值。
 */
public final class BiomeCatalog implements RegistryDataContributor {
  private final String namespace;
  private final TranslationCatalog translations;
  private final List<BiomeBuilder> mutableEntries = new ArrayList<>();
  public final List<BiomeBuilder> entries = Collections.unmodifiableList(mutableEntries);
  private final List<ResourceKey<Biome>> mutableKeys = new ArrayList<>();
  public final List<ResourceKey<Biome>> keys = Collections.unmodifiableList(mutableKeys);

  /**
   * 创建群系目录。
   *
   * @param namespace    模组命名空间
   * @param translations 用于登记群系名称的翻译目录
   */
  public BiomeCatalog(String namespace, TranslationCatalog translations) {
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.translations = Objects.requireNonNull(translations, "translations");
  }

  /**
   * 校验群系声明、分配资源键并登记中英文名称。
   *
   * @param builder 群系声明
   * @return 已绑定资源键的当前声明
   */
  public BiomeBuilder register(BiomeBuilder builder) {
    Objects.requireNonNull(builder, "群系 builder 不能为空");
    if (!builder.belongsTo(this)) {
      throw new IllegalArgumentException("群系 builder 不属于当前目录：" + builder.path);
    }
    if (!ResourceLocation.isValidPath(builder.path)) {
      throw new IllegalArgumentException("群系 ID 路径无效：" + builder.path);
    }
    if (builder.zhCn.isBlank()) {
      throw new IllegalArgumentException("群系中文名不能为空：" + builder.path);
    }
    if (builder.enUs.isBlank()) {
      throw new IllegalArgumentException("群系英文名不能为空：" + builder.path);
    }
    builder.climate();
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("群系 ID 重复：" + builder.path);
    }

    ResourceKey<Biome> key = ResourceKey.create(
        Registries.BIOME,
        ResourceLocation.fromNamespaceAndPath(namespace, builder.path)
    );
    builder.bind(key);
    mutableEntries.add(builder);
    mutableKeys.add(key);
    translations.add("biome." + namespace + "." + builder.path, builder.zhCn, builder.enUs);
    return builder;
  }

  /**
   * @param registryBuilder 动态注册表数据生成构建器
   */
  @Override
  public void contribute(RegistrySetBuilder registryBuilder) {
    registryBuilder.add(Registries.BIOME, this::bootstrap);
  }

  /**
   * 执行所有 Builder 的配置回调并写入群系动态注册表。
   *
   * @param context 群系的启动注册上下文
   */
  public void bootstrap(BootstrapContext<Biome> context) {
    var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
    var configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
    for (BiomeBuilder builder : entries) {
      context.register(builder.key(), builder.create(placedFeatures, configuredCarvers));
    }
  }
}
