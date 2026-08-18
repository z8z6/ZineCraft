package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.item.CreativeModeTab;

/**
 * 集中声明 Zinecraft 的全部创造模式页签。
 */
public final class ModCreativeTab {
  public static final CreativeModeTab ITEMS;
  public static final CreativeModeTab COLLECTIBLES;
  public static final CreativeModeTab SKILLS;

  static {
    ITEMS = Zinecraft.CREATIVE_TABS.builder("item", "Zinecraft")
        .enUs("Zinecraft")
        .icon(ModItem.D32_STEEL)
        .includeCatalogItems()
        .includeCatalogBlocks()
        .build();

    if (ModCollectible.ALL.isEmpty()) throw new IllegalStateException("藏品创造模式页不能为空");
    COLLECTIBLES = Zinecraft.CREATIVE_TABS.builder("collectibles", "Zinecraft 藏品")
        .enUs("Zinecraft Collectibles")
        .icon(ModCollectible.ALL.getFirst())
        .displayItems(output -> ModCollectible.ALL.forEach(output::accept))
        .build();

    if (Zinecraft.SKILLS.entries.isEmpty()) throw new IllegalStateException("技能创造模式页不能为空");
    SKILLS = Zinecraft.CREATIVE_TABS.builder("skills", "Zinecraft 技能")
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
