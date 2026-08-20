package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.SoundCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

/**
 * 声音事件声明构建器，保存注册路径、显示名称及注册后的声音持有者。
 */
public class SoundBuilder {
  private final SoundCatalog catalog;
  public String path;
  public String zhCn;
  public String enUs;

  public Holder<SoundEvent> sound;

  /**
   * 创建声音事件声明。
   *
   * @param catalog 接收该声音的声音目录
   * @param path    声音事件的命名空间内路径
   * @param zhCn    声音的简体中文名称
   */
  public SoundBuilder(SoundCatalog catalog, String path, String zhCn) {
    this.catalog = catalog;
    this.path = path;
    this.zhCn = zhCn;
    this.enUs = TranslationCatalog.toDisplayName(path);
  }

  /**
   * 设置声音的英文名称。
   *
   * @param enUs 英文名称
   * @return 当前构建器
   */
  public SoundBuilder enUs(String enUs) {
    this.enUs = enUs;
    return this;
  }

  /**
   * 生成声音名称使用的翻译键。
   *
   * @return 完整翻译键
   */
  public String transKey() {
    return "sound." + catalog.registry.getNamespace() + "." + path;
  }

  /**
   * 生成声音事件的资源位置。
   *
   * @return 由目录命名空间和声音路径组成的资源位置
   */
  public ResourceLocation resourceKey() {
    return ResourceLocation.fromNamespaceAndPath(catalog.registry.getNamespace(), path);
  }

  /**
   * 将声明登记到声音目录。
   *
   * @return 当前构建器
   */
  public SoundBuilder build() {
    catalog.register(this);
    return this;
  }
}
