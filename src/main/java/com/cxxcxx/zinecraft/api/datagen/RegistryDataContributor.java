package com.cxxcxx.zinecraft.api.datagen;

import net.minecraft.core.RegistrySetBuilder;

/**
 * 向统一数据包动态注册表构建器贡献一个 Catalog 所管理的注册表。
 */
@FunctionalInterface
public interface RegistryDataContributor {
  /**
   * 添加当前 Catalog 管理的所有动态注册表 bootstrap。
   *
   * @param registryBuilder 数据包动态注册表构建器
   */
  void contribute(RegistrySetBuilder registryBuilder);
}
