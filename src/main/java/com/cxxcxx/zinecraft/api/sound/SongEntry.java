package com.cxxcxx.zinecraft.api.sound;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.registries.DeferredItem;

public final class SongEntry {
  private final String path;
  private final Holder<SoundEvent> sound;
  private final ResourceKey<JukeboxSong> key;
  private final String descriptionKey;
  private final float lengthSeconds;
  private final int signal;
  public final DeferredItem<Item> item;

  public SongEntry(
      String path,
      Holder<SoundEvent> sound,
      ResourceKey<JukeboxSong> key,
      String descriptionKey,
      float lengthSeconds,
      int signal,
      DeferredItem<Item> item
  ) {
    super();
    this.path = path;
    this.sound = sound;
    this.key = key;
    this.descriptionKey = descriptionKey;
    this.lengthSeconds = lengthSeconds;
    this.signal = signal;
    this.item = item;
  }

  public final String getPath() {
    return this.path;
  }

  public final Holder<SoundEvent> getSound() {
    return this.sound;
  }

  public final ResourceKey<JukeboxSong> getKey() {
    return this.key;
  }

  public final String getDescriptionKey() {
    return this.descriptionKey;
  }

  public final float getLengthSeconds() {
    return this.lengthSeconds;
  }

  public final int getSignal() {
    return this.signal;
  }

  public final Item getItem() {
    return this.item.get();
  }

  public final JukeboxSong create() {
    return new JukeboxSong(this.sound, Component.translatable(this.descriptionKey), this.lengthSeconds, this.signal);
  }
}
