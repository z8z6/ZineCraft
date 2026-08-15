package com.cxxcxx.zinecraft.api.weapon.tacz;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class TaczPackInfo {
  @NotNull
  private final String sourceName;
  @NotNull
  private final String namespace;
  @Nullable
  private final String version;
  @Nullable
  private final String displayName;
  @Nullable
  private final String license;
  @NotNull
  private final List<String> authors;
  @Nullable
  private final String url;

  public TaczPackInfo(
      @NotNull String sourceName,
      @NotNull String namespace,
      @Nullable String version,
      @Nullable String displayName,
      @Nullable String license,
      @NotNull List<String> authors,
      @Nullable String url
  ) {
    super();
    this.sourceName = sourceName;
    this.namespace = namespace;
    this.version = version;
    this.displayName = displayName;
    this.license = license;
    this.authors = authors;
    this.url = url;
  }

  // $VF: synthetic method
  public static TaczPackInfo copy$default(
      TaczPackInfo var0, String var1, String var2, String var3, String var4, String var5, List var6, String var7, int var8, Object var9
  ) {
    if ((var8 & 1) != 0) {
      var1 = var0.sourceName;
    }

    if ((var8 & 2) != 0) {
      var2 = var0.namespace;
    }

    if ((var8 & 4) != 0) {
      var3 = var0.version;
    }

    if ((var8 & 8) != 0) {
      var4 = var0.displayName;
    }

    if ((var8 & 16) != 0) {
      var5 = var0.license;
    }

    if ((var8 & 32) != 0) {
      var6 = var0.authors;
    }

    if ((var8 & 64) != 0) {
      var7 = var0.url;
    }

    return var0.copy(var1, var2, var3, var4, var5, var6, var7);
  }

  @NotNull
  public final String getSourceName() {
    return this.sourceName;
  }

  @NotNull
  public final String getNamespace() {
    return this.namespace;
  }

  @Nullable
  public final String getVersion() {
    return this.version;
  }

  @Nullable
  public final String getDisplayName() {
    return this.displayName;
  }

  @Nullable
  public final String getLicense() {
    return this.license;
  }

  @NotNull
  public final List<String> getAuthors() {
    return this.authors;
  }

  @Nullable
  public final String getUrl() {
    return this.url;
  }

  @NotNull
  public final String component1() {
    return this.sourceName;
  }

  @NotNull
  public final String component2() {
    return this.namespace;
  }

  @Nullable
  public final String component3() {
    return this.version;
  }

  @Nullable
  public final String component4() {
    return this.displayName;
  }

  @Nullable
  public final String component5() {
    return this.license;
  }

  @NotNull
  public final List<String> component6() {
    return this.authors;
  }

  @Nullable
  public final String component7() {
    return this.url;
  }

  @NotNull
  public final TaczPackInfo copy(
      @NotNull String sourceName,
      @NotNull String namespace,
      @Nullable String version,
      @Nullable String displayName,
      @Nullable String license,
      @NotNull List<String> authors,
      @Nullable String url
  ) {
    return new TaczPackInfo(sourceName, namespace, version, displayName, license, authors, url);
  }

  @Override
  public int hashCode() {
    int i = this.sourceName.hashCode();
    i = i * 31 + this.namespace.hashCode();
    i = i * 31 + (this.version == null ? 0 : this.version.hashCode());
    i = i * 31 + (this.displayName == null ? 0 : this.displayName.hashCode());
    i = i * 31 + (this.license == null ? 0 : this.license.hashCode());
    i = i * 31 + this.authors.hashCode();
    return i * 31 + (this.url == null ? 0 : this.url.hashCode());
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof TaczPackInfo taczPackInfo)) {
      return false;
    } else if (!java.util.Objects.equals(this.sourceName, taczPackInfo.sourceName)) {
      return false;
    } else if (!java.util.Objects.equals(this.namespace, taczPackInfo.namespace)) {
      return false;
    } else if (!java.util.Objects.equals(this.version, taczPackInfo.version)) {
      return false;
    } else if (!java.util.Objects.equals(this.displayName, taczPackInfo.displayName)) {
      return false;
    } else if (!java.util.Objects.equals(this.license, taczPackInfo.license)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.authors, taczPackInfo.authors) ? false : java.util.Objects.equals(this.url, taczPackInfo.url);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "TaczPackInfo(sourceName="
        + this.sourceName
        + ", namespace="
        + this.namespace
        + ", version="
        + this.version
        + ", displayName="
        + this.displayName
        + ", license="
        + this.license
        + ", authors="
        + this.authors
        + ", url="
        + this.url
        + ")";
  }
}

