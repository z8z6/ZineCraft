package com.cxxcxx.zinecraft.core.block;

import com.cxxcxx.zinecraft.api.block.BlockCatalog;
import com.cxxcxx.zinecraft.api.block.BlockEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.item.ModItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class MaterialOres {
  @NotNull
  public static final MaterialOres INSTANCE = new MaterialOres();
  @NotNull
  public static final BlockEntry<Block> ORIGINITE_ORE = INSTANCE.ore("originite_ore", "源石矿", "Originite Ore", ModItem.ORIGINITE);
  @NotNull
  public static final BlockEntry<Block> ORIROCK_ORE = INSTANCE.ore("orirock_ore", "源岩矿", "Orirock Ore", ModItem.ORIROCK);
  @NotNull
  public static final BlockEntry<Block> ORIRON_ORE = INSTANCE.ore("oriron_ore", "异铁矿", "Oriron Ore", ModItem.ORIRON_SHARD);
  @NotNull
  public static final BlockEntry<Block> MANGANESE_ORE = INSTANCE.ore("manganese_ore_block", "轻锰矿脉", "Manganese Vein", ModItem.MANGANESE_ORE);
  @NotNull
  public static final BlockEntry<Block> GRINDSTONE_ORE = INSTANCE.ore("grindstone_ore", "研磨石矿", "Grindstone Ore", ModItem.GRINDSTONE);
  @NotNull
  public static final BlockEntry<Block> RMA70_ORE = INSTANCE.ore("rma70_ore", "RMA70 矿", "RMA70 Ore", ModItem.RMA70_12);
  @NotNull
  public static final BlockEntry<Block> CRYSTAL_ELEMENT_ORE = INSTANCE.ore(
      "crystal_element_ore", "晶体元件矿", "Crystalline Component Ore", ModItem.CRYSTAL_ELEMENT
  );
  @NotNull
  public static final BlockEntry<Block> LOXIC_KOHL_ORE = INSTANCE.ore("loxic_kohl_ore", "炽合金矿", "Incandescent Alloy Ore", ModItem.LOXIC_KOHL);
  @NotNull
  public static final List<BlockEntry<Block>> ALL;

  static {
    BlockEntry[] blockEntrys = new BlockEntry[]{
        ORIGINITE_ORE, ORIROCK_ORE, ORIRON_ORE, MANGANESE_ORE, GRINDSTONE_ORE, RMA70_ORE, CRYSTAL_ELEMENT_ORE, LOXIC_KOHL_ORE
    };
    ALL = java.util.List.of(blockEntrys);
  }

  private MaterialOres() {
  }

  private static final Block oreHelper0() {
    return new Block(Properties.ofFullCopy((BlockBehaviour) Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE));
  }

  private final BlockEntry<Block> ore(String path, String zhCn, String enUs, ItemLike drop) {
    return BlockCatalog.registerWithDefaults(Zinecraft.BLOCKS, path, zhCn, enUs, false, drop, false, false, MaterialOres::oreHelper0, 96, null);
  }
}
