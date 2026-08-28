package com.cxxcxx.zinecraft.core.client.music;

import com.cxxcxx.zinecraft.core.registry.ModDimension;
import com.cxxcxx.zinecraft.core.registry.ModSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.Music;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.common.NeoForge;

/** Selects Lifeflow as the only situational background music in Terra. */
public final class TerraMusicController {
  private static final int MIN_DELAY = 20 * 10;
  private static final int MAX_DELAY = 20 * 60;
  private static boolean initialized;
  private static Music terraMusic;

  private TerraMusicController() {
  }

  public static void initialize() {
    if (initialized) return;
    initialized = true;
    terraMusic = new Music(ModSound.AMBIENT_LIFEFLOW.song.sound, MIN_DELAY, MAX_DELAY, true);
    NeoForge.EVENT_BUS.addListener(TerraMusicController::selectMusic);
  }

  private static void selectMusic(SelectMusicEvent event) {
    ClientLevel level = Minecraft.getInstance().level;
    if (level != null && level.dimension().equals(ModDimension.TERRA.levelKey())) {
      event.overrideMusic(terraMusic);
    }
  }
}