package com.cxxcxx.zinecraft.api.localization;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Objects;

/**
 * 已登记的本地化消息，统一保存翻译键与中英文文本。
 */
public final class MessageBuilder {
  public final String key;
  public final String zhCn;
  public final String enUs;

  MessageBuilder(TranslationCatalog translations, String key, String zhCn, String enUs) {
    Objects.requireNonNull(translations, "翻译目录不能为空");
    this.key = key;
    this.zhCn = zhCn;
    this.enUs = enUs;
    translations.add(key, zhCn, enUs);
  }

  public MutableComponent component(Object... arguments) {
    return Component.translatable(key, arguments);
  }
}
