package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.ItemCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.SoundCatalog;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.Objects;

/**
 * 同时声明点唱机歌曲和对应唱片物品的组合构建器。
 */
public class MusicDiscBuilder {
  private final SoundCatalog sounds;
  private final ItemCatalog items;
  private final String path;
  private final String description;
  public SongBuilder song;
  public ItemBuilder<Item> item;
  private float length;
  private int signal = 15;

  /**
   * 创建音乐唱片声明。
   *
   * @param sounds      接收歌曲声音的声音目录
   * @param items       接收唱片物品的物品目录
   * @param path        歌曲和物品共用的注册路径
   * @param length      歌曲播放时长（秒）
   * @param description 唱片和歌曲的显示描述
   */
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

  /**
   * 设置唱片在比较器中产生的红石信号强度。
   *
   * @param signal 0 到 15 的信号强度
   * @return 当前构建器
   */
  public MusicDiscBuilder signal(int signal) {
    this.signal = signal;
    return this;
  }

  /**
   * 创建并登记歌曲声音、点唱机歌曲键和唱片物品。
   *
   * @return 当前构建器
   */
  public MusicDiscBuilder build() {
    if (song != null || item != null) {
      throw new IllegalStateException("音乐唱片 builder 不能重复 build：" + path);
    }

    song = new SongBuilder(sounds, path, description)
        .enUs(description)
        .length(length)
        .signal(signal)
        .build();
    item = new ItemBuilder<>(items,
        path,
        song.zhCn,
        song.enUs,
        () -> new Item(new Item.Properties()
            .stacksTo(1)
            .rarity(Rarity.RARE)
            .jukeboxPlayable(song.key)),
        ModelTemplates.MUSIC_DISC,
        true
    ).build();
    return this;
  }
}
