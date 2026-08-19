package com.cxxcxx.zinecraft.core.sound;

import com.cxxcxx.zinecraft.api.registry.builder.MusicDiscBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.JukeboxSong;

public final class ModSound {
  public static final MusicDiscBuilder AMBIENT_PICTURES_OF_THE_PAST = musicDisc("pictures_of_the_past", 95.0F, "James Primate - Pictures of the Past");
  public static final MusicDiscBuilder AMBIENT_RANDOM_GODS = musicDisc("random_gods", 199.0F, "James Primate - Random Gods (Theme III)");
  public static final MusicDiscBuilder AMBIENT_STRANGER_THINK = musicDisc("stranger_think", 240.0F, "C418 - Stranger Think");

  private ModSound() {
  }

  private static MusicDiscBuilder musicDisc(String path, float length, String description) {
    return new MusicDiscBuilder(Zinecraft.SOUNDS, Zinecraft.ITEMS, path, length, description).build();
  }

  public static void configure(BootstrapContext<JukeboxSong> context) {
    Zinecraft.SOUNDS.bootstrapSongs(context);
  }

  public static void bootstrap() {
  }
}
