package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
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

  /**
   * 创建并立即登记一条本地化消息。
   *
   * @param translations 接收消息文本的翻译目录
   * @param key          消息翻译键
   * @param zhCn         简体中文文本
   * @param enUs         英文文本
   */
  public MessageBuilder(TranslationCatalog translations, String key, String zhCn, String enUs) {
    Objects.requireNonNull(translations, "翻译目录不能为空");
    this.key = key;
    this.zhCn = zhCn;
    this.enUs = enUs;
    translations.add(key, zhCn, enUs);
  }

  /**
   * 创建引用当前翻译键的可变文本组件。
   *
   * @param arguments 传给翻译模板的格式化参数
   * @return 可用于聊天、界面或提示的本地化组件
   */
  public MutableComponent component(Object... arguments) {
    return Component.translatable(key, arguments);
  }
}
