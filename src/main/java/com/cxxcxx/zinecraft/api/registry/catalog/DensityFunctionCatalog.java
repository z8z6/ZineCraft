package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.datagen.RegistryDataContributor;
import com.cxxcxx.zinecraft.api.registry.builder.DensityFunctionBuilder;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.util.KeyDispatchDataCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** 密度函数注册目录，负责 Builder 校验、资源键分配和动态注册表数据生成。 */
public final class DensityFunctionCatalog implements RegistryDataContributor {
  private final String namespace;
  private final DeferredRegister<MapCodec<? extends DensityFunction>> types;
  private final List<DensityFunctionBuilder> mutableEntries = new ArrayList<>();
  public final List<DensityFunctionBuilder> entries = Collections.unmodifiableList(mutableEntries);

  public DensityFunctionCatalog(String namespace) {
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.types = DeferredRegister.create(BuiltInRegistries.DENSITY_FUNCTION_TYPE.key(), namespace);
  }

  /** 登记可在 worldgen JSON 中使用的自定义密度函数类型。 */
  public <T extends DensityFunction> KeyDispatchDataCodec<T> type(String path, KeyDispatchDataCodec<T> codec) {
    Objects.requireNonNull(codec, "密度函数类型 codec 不能为空：" + path);
    types.register(path, codec::codec);
    return codec;
  }

  /** 将密度函数类型的延迟注册器挂接到模组事件总线。 */
  public void register(IEventBus modBus) {
    types.register(modBus);
  }

  public DensityFunctionBuilder densityFunction(String path) {
    return new DensityFunctionBuilder(this, path);
  }

  public DensityFunctionBuilder register(DensityFunctionBuilder builder) {
    Objects.requireNonNull(builder, "密度函数 builder 不能为空");
    if (!builder.belongsTo(this)) throw new IllegalArgumentException("密度函数 builder 不属于当前目录：" + builder.path);
    if (!ResourceLocation.isValidPath(builder.path)) throw new IllegalArgumentException("密度函数 ID 路径无效：" + builder.path);
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("密度函数 ID 重复：" + builder.path);
    }
    ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace, builder.path);
    builder.bind(ResourceKey.create(Registries.DENSITY_FUNCTION, id));
    mutableEntries.add(builder);
    return builder;
  }

  @Override
  public void contribute(RegistrySetBuilder registryBuilder) {
    registryBuilder.add(Registries.DENSITY_FUNCTION, this::bootstrap);
  }

  public void bootstrap(BootstrapContext<DensityFunction> context) {
    for (DensityFunctionBuilder builder : entries) {
      context.register(builder.key(), builder.create(context));
    }
  }
}
