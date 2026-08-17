package com.cxxcxx.zinecraft.api.sound;

import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;

public final class SongCatalog {
  private final ModRegistrar registrar;
  private final SoundCatalog sounds;
  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final List<SongEntry> entries;

  public SongCatalog(ModRegistrar registrar, SoundCatalog sounds, ItemCatalog items, TranslationCatalog translations) {
    super();
    this.registrar = registrar;
    this.sounds = sounds;
    this.items = items;
    this.translations = translations;
    this.entries = new ArrayList<>();
  }

  public static SongEntry registerWithDefaults(SongCatalog var0, String var1, float var2, String var3, String var4, String var5, int var6, int var7, Object var8) {
    if ((var7 & 8) != 0) {
      var4 = "音乐唱片";
    }

    if ((var7 & 16) != 0) {
      var5 = "Music Disc";
    }

    if ((var7 & 32) != 0) {
      var6 = 15;
    }

    return var0.register(var1, var2, var3, var4, var5, var6);
  }

  private static final Item registerHelper2(ResourceKey _key) {
    return new Item(new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(_key));
  }

  public final List<SongEntry> getEntries() {
    return this.entries;
  }

  public final SongEntry register(
      String path, float lengthSeconds, String description, String zhCn, String enUs, int signal
  ) {
    if (!(lengthSeconds > 0.0F)) {
      int k = 0;
      String string2 = "唱片时长必须大于 0";
      throw new IllegalArgumentException(string2.toString());
    } else if (0 <= signal ? signal >= 16 : true) {
      int j = 0;
      String string1 = "唱片红石信号必须在 0 到 15 之间";
      throw new IllegalArgumentException(string1.toString());
    } else {
      Holder<net.minecraft.sounds.SoundEvent> reference = this.sounds.register(path);
      ModRegistrar modRegistrar = this.registrar;
      ResourceKey resourceKey1 = Registries.JUKEBOX_SONG;
      ResourceKey resourceKey = modRegistrar.key(resourceKey1, path);
      String string = "jukebox_song." + this.registrar.namespace + "." + path.replace('.', '_');
      DeferredItem<Item> item = this.items.builder(path, zhCn, () -> registerHelper2(resourceKey))
          .enUs(enUs)
          .model(ModelTemplates.MUSIC_DISC)
          .build();
      SongEntry songEntry = new SongEntry(path, reference, resourceKey, string, lengthSeconds, signal, item);
      SongEntry songEntry1 = songEntry;
      int i = 0;
      this.entries.add(songEntry1);
      this.translations.add(string, description, description);
      return songEntry;
    }
  }

  public final void bootstrap(BootstrapContext<JukeboxSong> context) {
    Iterable iterable = this.entries;
    int i = 0;

    for (Object object : iterable) {
      SongEntry songEntry = (SongEntry) object;
      int j = 0;
      this.registrar.dynamic(context, songEntry.getKey(), songEntry.create());
    }
  }
}
