package com.cxxcxx.zinecraft.api.sound;

import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.item.ItemEntry;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class SongCatalog {
  @NotNull
  private final ModRegistrar registrar;
  @NotNull
  private final SoundCatalog sounds;
  @NotNull
  private final ItemCatalog items;
  @NotNull
  private final TranslationCatalog translations;
  @NotNull
  private final List<SongEntry> entries;

  public SongCatalog(@NotNull ModRegistrar registrar, @NotNull SoundCatalog sounds, @NotNull ItemCatalog items, @NotNull TranslationCatalog translations) {
    super();
    this.registrar = registrar;
    this.sounds = sounds;
    this.items = items;
    this.translations = translations;
    this.entries = new ArrayList<>();
  }

  // $VF: synthetic method
  public static SongEntry register$default(SongCatalog var0, String var1, float var2, String var3, String var4, String var5, int var6, int var7, Object var8) {
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

  private static final Item register$lambda$2(ResourceKey $key) {
    return new Item(new Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable($key));
  }

  @NotNull
  public final List<SongEntry> getEntries$zinecraft() {
    return this.entries;
  }

  @NotNull
  public final SongEntry register(
      @NotNull String path, float lengthSeconds, @NotNull String description, @NotNull String zhCn, @NotNull String enUs, int signal
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
      String string = "jukebox_song." + this.registrar.getNamespace() + "." + path.replace('.', '_');
      ItemCatalog itemCatalog = this.items;
      ModelTemplate modelTemplate = ModelTemplates.MUSIC_DISC;
      ItemEntry itemEntry = itemCatalog.register(path, zhCn, enUs, modelTemplate, true,
          () -> register$lambda$2(resourceKey));
      SongEntry songEntry = new SongEntry(path, reference, resourceKey, string, lengthSeconds, signal, itemEntry);
      SongEntry songEntry1 = songEntry;
      int i = 0;
      this.entries.add(songEntry1);
      this.translations.add(string, description, description);
      return songEntry;
    }
  }

  public final void bootstrap(@NotNull BootstrapContext<JukeboxSong> context) {
    Iterable iterable = this.entries;
    int i = 0;

    for (Object object : iterable) {
      SongEntry songEntry = (SongEntry) object;
      int j = 0;
      this.registrar.dynamic(context, songEntry.getKey(), songEntry.create$zinecraft());
    }
  }
}

