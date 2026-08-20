package com.cxxcxx.zinecraft.api.world.structure;

import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Jigsaw 模板池构建器。
 */
public final class JigsawPoolBuilder {
  private final String name;
  private final Projection projection;
  private final List<JigsawTemplateElement> templates = new ArrayList<>();

  /**
   * @param name 池名称 @param projection 模板投影方式
   */
  public JigsawPoolBuilder(String name, Projection projection) {
    this.name = Objects.requireNonNull(name, "Jigsaw pool 名称不能为空");
    this.projection = Objects.requireNonNull(projection, "Jigsaw pool 投影不能为空：" + name);
  }

  /**
   * @param path 模板资源路径 @return 当前构建器
   */
  public JigsawPoolBuilder template(String path) {
    return template(path, 1);
  }

  /**
   * @param path 模板资源路径 @param weight 正整数选择权重 @return 当前构建器
   */
  public JigsawPoolBuilder template(String path, int weight) {
    templates.add(new JigsawTemplateElement(path, weight));
    return this;
  }

  /**
   * @return 完成校验的不可变模板池定义
   */
  public JigsawPoolDefinition build() {
    if (templates.isEmpty()) throw new IllegalArgumentException("Jigsaw 模板池不能为空：" + name);
    return new JigsawPoolDefinition(name, List.copyOf(templates), projection);
  }
}
