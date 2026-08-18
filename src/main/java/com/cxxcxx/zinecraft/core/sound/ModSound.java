package com.cxxcxx.zinecraft.core.sound;

import com.cxxcxx.zinecraft.api.sound.SongCatalog;
import com.cxxcxx.zinecraft.api.sound.SongEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.JukeboxSong;

public final class ModSound {
  public static final SongEntry AMBIENT_PICTURES_OF_THE_PAST = song("ambient.pictures_of_the_past", 95.0F, "James Primate - Pictures of the Past");
  public static final SongEntry AMBIENT_RANDOM_GODS = song("ambient.random_gods", 199.0F, "James Primate - Random Gods (Theme III)");
  public static final SongEntry AMBIENT_STRANGER_THINK = song("ambient.stranger_think", 240.0F, "C418 - Stranger Think");

  private static SongEntry song(
      String path,
      float length,
      String description) {
    return SongCatalog.registerWithDefaults(Zinecraft.SONGS, path, length, description, null, null, 0, 56, null);
  }

  private ModSound() {
  }

  public static void configure(BootstrapContext<JukeboxSong> context) {
    Zinecraft.SONGS.bootstrap(context);
  }

  public static void bootstrap() {
  }
}
