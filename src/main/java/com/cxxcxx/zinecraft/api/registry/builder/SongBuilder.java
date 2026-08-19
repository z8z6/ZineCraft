package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.SoundCatalog;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class SongBuilder extends SoundBuilder {
  public final ResourceKey<JukeboxSong> key;
  private float length;
  private int signal = 15;

  public SongBuilder(SoundCatalog catalog, String path, String zhCn) {
    super(catalog, path, zhCn);
    key = ResourceKey.create(Registries.JUKEBOX_SONG, resourceKey());
  }

  public SongBuilder enUs(String enUs) {
    this.enUs = enUs;
    return this;
  }

  @Override
  public String transKey() {
    return "jukebox_song." + resourceKey().getNamespace() + "." + path.replace('.', '_');
  }

  @Override
  public SongBuilder build() {
    if (signal < 0 || signal > 15) {
      throw new IllegalArgumentException("唱片红石信号必须在 0 到 15 之间：" + path);
    }
    super.build();
    return this;
  }

  public SongBuilder length(float length) {
    this.length = length;
    return this;
  }

  public SongBuilder signal(int signal) {
    this.signal = signal;
    return this;
  }

  public JukeboxSong create() {
    if (sound == null) {
      throw new IllegalStateException("唱片必须先 build：" + path);
    }
    return new JukeboxSong(sound, Component.translatable(transKey()), length, signal);
  }
}
