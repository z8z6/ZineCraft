package com.cxxcxx.zinecraft.api.world.structure;

import java.util.Objects;

/**
 * 模板池中的结构模板及选择权重。
 *
 * @param template 模板资源路径
 * @param weight   正整数权重
 */
public record JigsawTemplateElement(String template, int weight) {
  public JigsawTemplateElement {
    Objects.requireNonNull(template, "Jigsaw 模板路径不能为空");
    if (template.isBlank()) throw new IllegalArgumentException("Jigsaw 模板路径不能为空");
    if (weight <= 0) throw new IllegalArgumentException("Jigsaw 模板权重必须大于 0：" + template);
  }
}
