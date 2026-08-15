package com.cxxcxx.zinecraft.api.weapon.action;

/**
 * Inclusive tick interval used by timed weapon actions.
 */
public record TickRange(int startInclusive, int endInclusive) {
  public TickRange {
    if (startInclusive < 0 || endInclusive < startInclusive) {
      throw new IllegalArgumentException("Invalid tick range: " + startInclusive + ".." + endInclusive);
    }
  }

  public static TickRange at(int tick) {
    return new TickRange(tick, tick);
  }

  public boolean contains(int tick) {
    return tick >= startInclusive && tick <= endInclusive;
  }
}
