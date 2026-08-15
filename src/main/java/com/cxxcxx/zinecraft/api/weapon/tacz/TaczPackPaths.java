package com.cxxcxx.zinecraft.api.weapon.tacz;

final class TaczPackPaths {
  private TaczPackPaths() {
  }

  static String normalize(String path) {
    String value = path.replace('\\', '/');
    while (value.startsWith("/")) value = value.substring(1);
    if (value.isBlank()) return null;
    for (String part : value.split("/", -1)) {
      if (part.isBlank() || part.equals("..")) return null;
    }
    return value;
  }
}
