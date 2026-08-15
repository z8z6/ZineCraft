package com.cxxcxx.zinecraft.api.sound;

import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import kotlin.text.StringsKt;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

public final class SoundCatalog {
  @NotNull
  private final ModRegistrar registrar;

  public SoundCatalog(@NotNull ModRegistrar registrar) {
    super();
    this.registrar = registrar;
  }

  @NotNull
  public final Holder<SoundEvent> register(@NotNull String path) {
    if (StringsKt.isBlank(path)) {
      int i = 0;
      String string = "声音 ID 不能为空";
      throw new IllegalArgumentException(string.toString());
    } else {
      return this.registrar.sound(path);
    }
  }
}

