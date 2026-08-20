package com.cxxcxx.zinecraft.api.world.structure;

import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;

import java.util.List;
import java.util.Objects;

/**
 * 一个 Jigsaw 模板池的不可变定义。
 *
 * @param name       池名称
 * @param templates  模板及权重
 * @param projection 模板投影方式
 */
public record JigsawPoolDefinition(
    String name,
    List<JigsawTemplateElement> templates,
    Projection projection
) {
  public JigsawPoolDefinition {
    Objects.requireNonNull(name, "模板池名称不能为空");
    templates = List.copyOf(templates);
    Objects.requireNonNull(projection, "模板池投影不能为空：" + name);
  }
}
