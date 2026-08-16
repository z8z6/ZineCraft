package com.cxxcxx.zinecraft.api.weapon.tacz;

/**
 * Server-side reload schedule extracted from TaCZ gun data; no gun-pack script is executed.
 */
public record TaczReloadTimings(
    boolean shellByShell,
    int emptyFirstFeedTicks,
    int tacticalFirstFeedTicks,
    int feedIntervalTicks,
    int endingTicks,
    int emptyDurationTicks,
    int tacticalDurationTicks
) {
  public TaczReloadTimings {
    emptyFirstFeedTicks = Math.max(emptyFirstFeedTicks, 1);
    tacticalFirstFeedTicks = Math.max(tacticalFirstFeedTicks, 1);
    feedIntervalTicks = Math.max(feedIntervalTicks, 1);
    endingTicks = Math.max(endingTicks, 0);
    emptyDurationTicks = Math.max(emptyDurationTicks, emptyFirstFeedTicks + 1);
    tacticalDurationTicks = Math.max(tacticalDurationTicks, tacticalFirstFeedTicks + 1);
  }

  public int firstFeedTicks(boolean empty) {
    return empty ? emptyFirstFeedTicks : tacticalFirstFeedTicks;
  }

  public int durationTicks(boolean empty, int roundsNeeded) {
    if (!shellByShell) return empty ? emptyDurationTicks : tacticalDurationTicks;
    return firstFeedTicks(empty) + Math.max(roundsNeeded - 1, 0) * feedIntervalTicks + endingTicks + 1;
  }
}
