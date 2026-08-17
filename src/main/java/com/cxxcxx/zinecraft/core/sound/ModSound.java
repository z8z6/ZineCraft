package com.cxxcxx.zinecraft.core.sound;

import com.cxxcxx.zinecraft.api.sound.SongCatalog;
import com.cxxcxx.zinecraft.api.sound.SongEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.JukeboxSong;
import org.jetbrains.annotations.NotNull;

public final class ModSound {
  @NotNull
  public static final ModSound INSTANCE = new ModSound();
  @NotNull
  public static final SongEntry AMBIENT_PICTURES_OF_THE_PAST = INSTANCE.song("ambient.pictures_of_the_past", 95.0F, "James Primate - Pictures of the Past");
  @NotNull
  public static final SongEntry AMBIENT_RANDOM_GODS = INSTANCE.song("ambient.random_gods", 199.0F, "James Primate - Random Gods (Theme III)");
  @NotNull
  public static final SongEntry AMBIENT_STRANGER_THINK = INSTANCE.song("ambient.stranger_think", 240.0F, "C418 - Stranger Think");

  private ModSound() {
  }

  public final void configure(@NotNull BootstrapContext<JukeboxSong> context) {
    Zinecraft.SONGS.bootstrap(context);
  }

  private final SongEntry song(String path, float length, String description) {
    return SongCatalog.registerWithDefaults(Zinecraft.SONGS, path, length, description, null, null, 0, 56, null);
  }
}

