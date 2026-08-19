package com.cxxcxx.zinecraft.api.registry;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.registry.builder.SongBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.SoundBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SoundCatalog {
  // neoforge 的声音注册器
  public final DeferredRegister<SoundEvent> registry;
  // 翻译注册器
  private final TranslationCatalog translations;
  private final List<SoundBuilder> mutableEntries = new ArrayList<>();
  public final List<SoundBuilder> entries = Collections.unmodifiableList(mutableEntries);

  public SoundCatalog(String mod, TranslationCatalog translations) {
    registry = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT.key(), mod);
    this.translations = translations;
  }

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

  public void bootstrapSongs(BootstrapContext<JukeboxSong> context) {
    mutableEntries.stream()
        .filter(SongBuilder.class::isInstance)
        .map(SongBuilder.class::cast)
        .forEach(song -> context.register(song.key, song.create()));
  }
}

