package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.MobEffectBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/** MobEffect 注册目录，统一管理注册、翻译和声明元数据。 */
public final class MobEffectCatalog {
  private static final Pattern PATH_PATTERN = Pattern.compile("[a-z0-9_]+(?:/[a-z0-9_]+)*");
  private final String namespace;
  private final TranslationCatalog translations;
  private final DeferredRegister<MobEffect> registry;
  private final List<MobEffectBuilder> mutableEntries = new ArrayList<>();
  public final List<MobEffectBuilder> entries = Collections.unmodifiableList(mutableEntries);

  public MobEffectCatalog(String namespace, TranslationCatalog translations) {
    if (namespace == null || namespace.isBlank()) {
      throw new IllegalArgumentException("MobEffect 目录命名空间不能为空");
    }
    this.namespace = namespace;
    this.translations = Objects.requireNonNull(translations, "MobEffect 翻译目录不能为空");
    this.registry = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT.key(), namespace);
  }

  public String namespace() {
    return namespace;
  }

  public MobEffectBuilder register(MobEffectBuilder builder) {
    Objects.requireNonNull(builder, "MobEffect builder 不能为空");
    if (builder.catalog != this) {
      throw new IllegalArgumentException("MobEffect builder 不属于当前目录：" + builder.path);
    }
    if (!PATH_PATTERN.matcher(builder.path).matches()) {
      throw new IllegalArgumentException("MobEffect ID 格式无效：" + builder.path);
    }
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("MobEffect ID 重复：" + builder.path);
    }
    DeferredHolder<MobEffect, MobEffect> effect = registry.register(builder.path, builder::create);
    builder.bind(effect);
    translations.add("effect." + namespace + "." + builder.path, builder.zhCn, builder.enUs);
    mutableEntries.add(builder);
    return builder;
  }

  public void register(IEventBus modBus) {
    registry.register(modBus);
  }
}