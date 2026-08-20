package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.datagen.RegistryDataContributor;
import com.cxxcxx.zinecraft.api.registry.builder.SongBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.SoundBuilder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 声音注册目录，统一管理声音事件、歌曲动态注册值和名称翻译。
 */
public final class SoundCatalog implements RegistryDataContributor {
  // neoforge 的声音注册器
  public final DeferredRegister<SoundEvent> registry;
  // 翻译注册器
  private final TranslationCatalog translations;
  private final List<SoundBuilder> mutableEntries = new ArrayList<>();
  public final List<SoundBuilder> entries = Collections.unmodifiableList(mutableEntries);

  /**
   * 创建声音注册目录。
   *
   * @param mod          模组命名空间
   * @param translations 用于登记声音或歌曲名称的翻译目录
   */
  public SoundCatalog(String mod, TranslationCatalog translations) {
    registry = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT.key(), mod);
    this.translations = translations;
  }

  /**
   * 登记声音事件及其名称翻译。
   *
   * @param builder 声音声明
   */
  public void register(SoundBuilder builder) {
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("声音 ID 重复：" + builder.path);
    }

    builder.sound = registry.register(
        builder.path,
        () -> SoundEvent.createVariableRangeEvent(builder.resourceKey())
    );
    mutableEntries.add(builder);
    translations.add(builder.transKey(), builder.zhCn, builder.enUs);
  }

  /**
   * 将所有歌曲声明写入点唱机歌曲动态注册表。
   *
   * @param context 点唱机歌曲的启动注册上下文
   */
  public void bootstrapSongs(BootstrapContext<JukeboxSong> context) {
    mutableEntries.stream()
        .filter(SongBuilder.class::isInstance)
        .map(SongBuilder.class::cast)
        .forEach(song -> context.register(song.key, song.create()));
  }

  /**
   * @param registryBuilder 数据包动态注册表构建器
   */
  @Override
  public void contribute(RegistrySetBuilder registryBuilder) {
    registryBuilder.add(Registries.JUKEBOX_SONG, this::bootstrapSongs);
  }

  /**
   * 将声音事件延迟注册器挂接到模组事件总线。
   *
   * @param modBus 模组事件总线
   */
  public void register(IEventBus modBus) {
    registry.register(modBus);
  }
}
