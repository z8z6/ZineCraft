package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.item.CreativeModeTab;

/**
 * All Zinecraft creative tabs, initialized after their content dependencies.
 */
public final class ModCreativeTab {
  public static final ModCreativeTab INSTANCE = new ModCreativeTab();

  public final CreativeModeTab items;
  public final CreativeModeTab collectibles;
  public final CreativeModeTab skills;

  private ModCreativeTab() {
    items = Zinecraft.CREATIVE_TABS.builder("item", "Zinecraft")
        .enUs("Zinecraft")
        .icon(ModItem.INSTANCE.D32_STEEL)
        .includeCatalogItems()
        .includeCatalogBlocks()
        .build();

    if (ModCollectible.INSTANCE.ALL.isEmpty()) throw new IllegalStateException("藏品创造模式页不能为空");
    collectibles = Zinecraft.CREATIVE_TABS.builder("collectibles", "Zinecraft 藏品")
        .enUs("Zinecraft Collectibles")
        .icon(ModCollectible.INSTANCE.ALL.getFirst())
        .displayItems(output -> ModCollectible.INSTANCE.ALL.forEach(output::accept))
        .build();

    if (Zinecraft.SKILLS.getEntries().isEmpty()) throw new IllegalStateException("技能创造模式页不能为空");
    skills = Zinecraft.CREATIVE_TABS.builder("skills", "Zinecraft 技能")
        .enUs("Zinecraft Skills")
        .icon(Zinecraft.SKILLS.getEntries().getFirst())
        .displayItems(output -> Zinecraft.SKILLS.getEntries().forEach(output::accept))
        .build();
  }
}
