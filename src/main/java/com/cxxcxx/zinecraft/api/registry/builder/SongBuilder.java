package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.SoundCatalog;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

/**
 * 点唱机歌曲声明构建器，在声音事件之上配置播放时长和红石信号强度。
 */
public class SongBuilder extends SoundBuilder {
  public final ResourceKey<JukeboxSong> key;
  private float length;
  private int signal = 15;

  /**
   * 创建点唱机歌曲声明及对应的动态注册键。
   *
   * @param catalog 接收歌曲声音的声音目录
   * @param path    歌曲的命名空间内路径
   * @param zhCn    歌曲的简体中文描述
   */
  public SongBuilder(SoundCatalog catalog, String path, String zhCn) {
    super(catalog, path, zhCn);
    key = ResourceKey.create(Registries.JUKEBOX_SONG, resourceKey());
  }

  /**
   * 设置歌曲的英文描述。
   *
   * @param enUs 英文描述
   * @return 当前构建器
   */
  public SongBuilder enUs(String enUs) {
    this.enUs = enUs;
    return this;
  }

  /**
   * 校验红石信号并登记歌曲对应的声音事件。
   *
   * @return 当前构建器
   */
  @Override
  public SongBuilder build() {
    if (signal < 0 || signal > 15) {
      throw new IllegalArgumentException("唱片红石信号必须在 0 到 15 之间：" + path);
    }
    super.build();
    return this;
  }

  /**
   * 设置歌曲的播放时长。
   *
   * @param length 播放时长（秒）
   * @return 当前构建器
   */
  public SongBuilder length(float length) {
    this.length = length;
    return this;
  }

  /**
   * 设置唱片在比较器中产生的红石信号强度。
   *
   * @param signal 0 到 15 的信号强度
   * @return 当前构建器
   */
  public SongBuilder signal(int signal) {
    this.signal = signal;
    return this;
  }

  /**
   * 根据已登记的声音事件创建动态注册表中的点唱机歌曲值。
   *
   * @return 点唱机歌曲实例
   */
  public JukeboxSong create() {
    if (sound == null) {
      throw new IllegalStateException("唱片必须先 build：" + path);
    }
    return new JukeboxSong(sound, Component.translatable(transKey()), length, signal);
  }
}
