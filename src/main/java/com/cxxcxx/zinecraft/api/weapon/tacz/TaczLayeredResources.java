package com.cxxcxx.zinecraft.api.weapon.tacz;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;

final class TaczLayeredResources {
  private final List<TaczPackSource> sources;

  TaczLayeredResources(List<? extends TaczPackSource> sources) {
    this.sources = List.copyOf(sources);
  }

  InputStream open(String path) {
    String normalized = TaczPackPaths.normalize(path);
    if (normalized == null) return null;
    List<TaczPackSource> reversed = new ArrayList<>(sources);
    Collections.reverse(reversed);
    for (TaczPackSource source : reversed) {
      InputStream input = source.open(normalized);
      if (input != null) return input;
    }
    return null;
  }

  Stream<String> paths() {
    LinkedHashSet<String> merged = new LinkedHashSet<>();
    for (TaczPackSource source : sources) source.paths().forEach(merged::add);
    return merged.stream();
  }
}
