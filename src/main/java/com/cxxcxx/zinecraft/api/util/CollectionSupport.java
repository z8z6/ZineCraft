package com.cxxcxx.zinecraft.api.util;

import com.mojang.datafixers.util.Pair;

import java.util.*;

/**
 * Small Java collection helpers for operations not covered by the JDK factory methods.
 */
public final class CollectionSupport {
  private CollectionSupport() {
  }

  public static int sizeHint(Iterable<?> values, int fallback) {
    return values instanceof Collection<?> collection ? collection.size() : fallback;
  }

  public static <T> List<T> toList(Iterable<? extends T> values) {
    List<T> result = new ArrayList<>();
    values.forEach(result::add);
    return List.copyOf(result);
  }

  public static <T> List<T> distinct(Collection<? extends T> values) {
    return List.copyOf(new LinkedHashSet<>(values));
  }

  public static <T> T firstOrNull(List<? extends T> values) {
    return values.isEmpty() ? null : values.getFirst();
  }

  public static <T extends Comparable<? super T>> List<T> sorted(Collection<? extends T> values) {
    List<T> result = new ArrayList<>(values);
    Collections.sort(result);
    return List.copyOf(result);
  }

  public static <T> Set<T> minus(Set<? extends T> values, Collection<?> removed) {
    Set<T> result = new LinkedHashSet<>(values);
    result.removeAll(removed);
    return Set.copyOf(result);
  }

  @SafeVarargs
  public static <K, V> Map<K, V> mapOf(Pair<? extends K, ? extends V>... entries) {
    return Collections.unmodifiableMap(linkedMapOf(entries));
  }

  @SafeVarargs
  public static <K, V> LinkedHashMap<K, V> linkedMapOf(Pair<? extends K, ? extends V>... entries) {
    LinkedHashMap<K, V> result = new LinkedHashMap<>();
    for (Pair<? extends K, ? extends V> entry : entries) {
      if (result.put(entry.getFirst(), entry.getSecond()) != null) {
        throw new IllegalArgumentException("Duplicate map key: " + entry.getFirst());
      }
    }
    return result;
  }

  public static <K, V> V getRequired(Map<K, V> values, K key) {
    V value = values.get(key);
    if (value == null) throw new IllegalArgumentException("Missing map key: " + key);
    return value;
  }
}
