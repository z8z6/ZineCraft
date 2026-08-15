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
  private static final BlockEntry<Block> ORIGINITE_ORE = INSTANCE.ore("originite_ore", "源石矿", "Originite Ore", ModItem.INSTANCE.getORIGINITE());
  @NotNull
  private static final BlockEntry<Block> ORIROCK_ORE = INSTANCE.ore("orirock_ore", "源岩矿", "Orirock Ore", ModItem.INSTANCE.getORIROCK());
  @NotNull
  private static final BlockEntry<Block> ORIRON_ORE = INSTANCE.ore("oriron_ore", "异铁矿", "Oriron Ore", ModItem.INSTANCE.getORIRON_SHARD());
  @NotNull
  private static final BlockEntry<Block> MANGANESE_ORE = INSTANCE.ore("manganese_ore_block", "轻锰矿脉", "Manganese Vein", ModItem.INSTANCE.getMANGANESE_ORE());
  @NotNull
  private static final BlockEntry<Block> GRINDSTONE_ORE = INSTANCE.ore("grindstone_ore", "研磨石矿", "Grindstone Ore", ModItem.INSTANCE.getGRINDSTONE());
  @NotNull
  private static final BlockEntry<Block> RMA70_ORE = INSTANCE.ore("rma70_ore", "RMA70 矿", "RMA70 Ore", ModItem.INSTANCE.getRMA70_12());
  @NotNull
  private static final BlockEntry<Block> CRYSTAL_ELEMENT_ORE = INSTANCE.ore(
      "crystal_element_ore", "晶体元件矿", "Crystalline Component Ore", ModItem.INSTANCE.getCRYSTAL_ELEMENT()
  );
  @NotNull
  private static final BlockEntry<Block> LOXIC_KOHL_ORE = INSTANCE.ore("loxic_kohl_ore", "炽合金矿", "Incandescent Alloy Ore", ModItem.INSTANCE.getLOXIC_KOHL());
  @NotNull
  private static final List<BlockEntry<Block>> ALL;

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

  @NotNull
  public final BlockEntry<Block> getORIGINITE_ORE() {
    return ORIGINITE_ORE;
  }

  @NotNull
  public final BlockEntry<Block> getORIROCK_ORE() {
    return ORIROCK_ORE;
  }

  @NotNull
  public final BlockEntry<Block> getORIRON_ORE() {
    return ORIRON_ORE;
  }

  @NotNull
  public final BlockEntry<Block> getMANGANESE_ORE() {
    return MANGANESE_ORE;
  }

  @NotNull
  public final BlockEntry<Block> getGRINDSTONE_ORE() {
    return GRINDSTONE_ORE;
  }

  @NotNull
  public final BlockEntry<Block> getRMA70_ORE() {
    return RMA70_ORE;
  }

  @NotNull
  public final BlockEntry<Block> getCRYSTAL_ELEMENT_ORE() {
    return CRYSTAL_ELEMENT_ORE;
  }

  @NotNull
  public final BlockEntry<Block> getLOXIC_KOHL_ORE() {
    return LOXIC_KOHL_ORE;
  }

  @NotNull
  public final List<BlockEntry<Block>> getALL() {
    return ALL;
  }

  private final BlockEntry<Block> ore(String path, String zhCn, String enUs, ItemLike drop) {
    return BlockCatalog.registerWithDefaults(Zinecraft.INSTANCE.getBLOCKS(), path, zhCn, enUs, false, drop, false, false, MaterialOres::oreHelper0, 96, null);
  }
}
