package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.DensityFunctionCatalog;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/** 密度函数声明构建器，保存动态注册路径与基于 bootstrap 上下文的函数工厂。 */
public final class DensityFunctionBuilder {
  public final String path;
  private final DensityFunctionCatalog catalog;
  @Nullable private Function<BootstrapContext<DensityFunction>, DensityFunction> createFunction;
  @Nullable private ResourceKey<DensityFunction> key;

  public DensityFunctionBuilder(DensityFunctionCatalog catalog, String path) {
    this.catalog = Objects.requireNonNull(catalog, "密度函数目录不能为空");
    this.path = Objects.requireNonNull(path, "密度函数 ID 不能为空");
  }

  public DensityFunctionBuilder function(Function<? super BootstrapContext<DensityFunction>, ? extends DensityFunction> createFunction) {
    Objects.requireNonNull(createFunction, "密度函数工厂不能为空：" + path);
    this.createFunction = createFunction::apply;
    return this;
  }

  /**
   * 将一组声明参数与密度函数工厂绑定，便于不同字段复用同一公式而显式传入各自参数。
   */
  public <P> DensityFunctionBuilder function(
      P parameters,
      BiFunction<? super BootstrapContext<DensityFunction>, ? super P, ? extends DensityFunction> createFunction
  ) {
    Objects.requireNonNull(parameters, "密度函数参数不能为空：" + path);
    Objects.requireNonNull(createFunction, "密度函数参数化工厂不能为空：" + path);
    return function(context -> createFunction.apply(context, parameters));
  }

  public DensityFunctionBuilder build() {
    if (key != null) throw new IllegalStateException("Density function builder 不能重复 build：" + path);
    if (createFunction == null) throw new IllegalStateException("密度函数工厂尚未设置：" + path);
    return catalog.register(this);
  }

  public void bind(ResourceKey<DensityFunction> key) {
    this.key = Objects.requireNonNull(key, "key");
  }

  public ResourceKey<DensityFunction> key() {
    return Objects.requireNonNull(key, "密度函数尚未 build：" + path);
  }

  public DensityFunction create(BootstrapContext<DensityFunction> context) {
    return Objects.requireNonNull(
        Objects.requireNonNull(createFunction, "密度函数工厂尚未设置：" + path).apply(context),
        "密度函数工厂不能返回 null：" + path
    );
  }

  public boolean belongsTo(DensityFunctionCatalog catalog) {
    return this.catalog == catalog;
  }
}
