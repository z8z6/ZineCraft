package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.CreativeTabBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.item.ModCollectible;

/**
 * 集中声明 Zinecraft 的全部创造模式页签。
 */
public final class ModCreativeTab {
  public static final CreativeTabBuilder ITEMS;
  public static final CreativeTabBuilder COLLECTIBLES;
  public static final CreativeTabBuilder SKILLS;

  static {
    ITEMS = new CreativeTabBuilder(Zinecraft.CREATIVE_TABS, "item", "Zinecraft")
        .enUs("Zinecraft")
        .icon(ModItem.D32_STEEL)
        .includeCatalogItems()
        .includeCatalogBlocks()
        .build();


    COLLECTIBLES = new CreativeTabBuilder(
        Zinecraft.CREATIVE_TABS, "collectibles", "Zinecraft 藏品")
        .enUs("Zinecraft Collectibles")
        .icon(ModCollectible.ALL.getFirst())
        .displayItems(output -> ModCollectible.ALL.forEach(output::accept))
        .build();

    SKILLS = new CreativeTabBuilder(
        Zinecraft.CREATIVE_TABS, "skills", "Zinecraft 技能")
        .enUs("Zinecraft Skills")
        .icon(Zinecraft.SKILLS.entries.getFirst())
        .displayItems(output -> Zinecraft.SKILLS.entries.forEach(output::accept))
        .build();
  }

  private ModCreativeTab() {
  }

  public static void bootstrap() {
  }
}
