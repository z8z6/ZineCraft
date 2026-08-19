package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.ItemCatalog;
import com.cxxcxx.zinecraft.api.registry.SoundCatalog;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.Objects;

public class MusicDiscBuilder {
  private final SoundCatalog sounds;
  private final ItemCatalog items;
  private final String path;
  private final String description;
  public SongBuilder song;
  public ItemBuilder<Item> item;
  private float length;
  private int signal = 15;

  public MusicDiscBuilder(
      SoundCatalog sounds,
      ItemCatalog items,
      String path,
      float length,
      String description
  ) {
    this.sounds = Objects.requireNonNull(sounds, "声音目录不能为空");
    this.items = Objects.requireNonNull(items, "物品目录不能为空");
    this.path = path;
    this.length = length;
    this.description = description;
  }

  public MusicDiscBuilder signal(int signal) {
    this.signal = signal;
    return this;
  }

  public MusicDiscBuilder build() {
    if (song != null || item != null) {
      throw new IllegalStateException("音乐唱片 builder 不能重复 build：" + path);
    }

    song = new SongBuilder(sounds, path, description)
        .enUs(description)
        .length(length)
        .signal(signal)
        .build();
    item = items.builder(
        path,
        song.zhCn,
        song.enUs,
        () -> new Item(new Item.Properties()
            .stacksTo(1)
            .rarity(Rarity.RARE)
            .jukeboxPlayable(song.key)),
        ModelTemplates.MUSIC_DISC,
        true
    );
    return this;
  }
}
