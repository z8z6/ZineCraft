package com.cxxcxx.zinecraft.api.world.city;

import java.util.Objects;
import java.util.Optional;

/** 显式区分成功布局与可诊断失败，避免 mandatory 地块被静默跳过。 */
public record LayoutGenerationResult(
    MobileCityLayout layout,
    LayoutFailureReason failureReason,
    String message
) {
  public LayoutGenerationResult {
    if ((layout == null) == (failureReason == null)) {
      throw new IllegalArgumentException("布局结果必须且只能包含成功布局或失败原因之一");
    }
    message = Objects.requireNonNullElse(message, "");
  }

  public static LayoutGenerationResult success(MobileCityLayout layout) {
    return new LayoutGenerationResult(Objects.requireNonNull(layout), null, "");
  }

  public static LayoutGenerationResult failure(LayoutFailureReason reason, String message) {
    return new LayoutGenerationResult(null, Objects.requireNonNull(reason), message);
  }

  public boolean success() {
    return layout != null;
  }

  public Optional<MobileCityLayout> optionalLayout() {
    return Optional.ofNullable(layout);
  }
}
