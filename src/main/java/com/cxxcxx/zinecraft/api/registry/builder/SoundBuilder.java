package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.api.registry.SoundCatalog;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

// 声音注册器
public class SoundBuilder {
  private final SoundCatalog catalog;
  public String path;
  public String zhCn;
  public String enUs;

  public Holder<SoundEvent> sound;

  public SoundBuilder(SoundCatalog catalog, String path, String zhCn) {
    this.catalog = catalog;
    this.path = path;
    this.zhCn = zhCn;
    this.enUs = TranslationNames.toDisplayName(path);
  }

  public SoundBuilder enUs(String enUs) {
    this.enUs = enUs;
    return this;
  }

  public String transKey() {
    return "sound." + catalog.registry.getNamespace() + "." + path;
  }

  public ResourceLocation resourceKey() {
    return ResourceLocation.fromNamespaceAndPath(catalog.registry.getNamespace(), path);
  }

  public SoundBuilder build() {
    catalog.register(this);
    return this;
  }
}
