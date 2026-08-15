package com.cxxcxx.zinecraft.api.sound;

import com.cxxcxx.zinecraft.api.item.ItemEntry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import org.jetbrains.annotations.NotNull;

public final class SongEntry {
  @NotNull
  private final String path;
  @NotNull
  private final Holder<SoundEvent> sound;
  @NotNull
  private final ResourceKey<JukeboxSong> key;
  @NotNull
  private final String descriptionKey;
  private final float lengthSeconds;
  private final int signal;
  @NotNull
  private final ItemEntry<Item> itemEntry;

  public SongEntry(
      @NotNull String path,
      @NotNull Holder<SoundEvent> sound,
      @NotNull ResourceKey<JukeboxSong> key,
      @NotNull String descriptionKey,
      float lengthSeconds,
      int signal,
      @NotNull ItemEntry<Item> itemEntry
  ) {
    super();
    this.path = path;
    this.sound = sound;
    this.key = key;
    this.descriptionKey = descriptionKey;
    this.lengthSeconds = lengthSeconds;
    this.signal = signal;
    this.itemEntry = itemEntry;
  }

  @NotNull
  public final String getPath() {
    return this.path;
  }

  @NotNull
  public final Holder<SoundEvent> getSound() {
    return this.sound;
  }

  @NotNull
  public final ResourceKey<JukeboxSong> getKey() {
    return this.key;
  }

  @NotNull
  public final String getDescriptionKey() {
    return this.descriptionKey;
  }

  public final float getLengthSeconds() {
    return this.lengthSeconds;
  }

  public final int getSignal() {
    return this.signal;
  }

  @NotNull
  public final ItemEntry<Item> getItemEntry() {
    return this.itemEntry;
  }

  @NotNull
  public final Item getItem() {
    return this.itemEntry.getItem();
  }

  @NotNull
  public final JukeboxSong create() {
    return new JukeboxSong((Holder) this.sound, (Component) Component.translatable(this.descriptionKey), this.lengthSeconds, this.signal);
  }
}

