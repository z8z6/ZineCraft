package com.cxxcxx.zinecraft.core.block;

import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

/**
 * Decorative heads built from the mod authors' public Minecraft skins.
 */
public final class ModHeadBlock {
  public static final ModHeadBlock INSTANCE = new ModHeadBlock();

  public static final DeferredBlock<HeadBlock> Z8Z6Z8Z6_HEAD = register("z8z6z8z6_head", "z8z6z8z6 的头", "z8z6z8z6's Head");
  public static final DeferredBlock<HeadBlock> YE_XINGCHEN_HEAD = register("ye_xingchen_head", "Ye_xingchen 的头", "Ye_xingchen's Head");

  private static DeferredBlock<HeadBlock> register(String path, String zhCn, String enUs) {
    return Zinecraft.BLOCKS.builder(
            path,
            zhCn,
            () -> new HeadBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .noOcclusion()
            .strength(1.0F)
            .sound(SoundType.WOOD))
        )
        .enUs(enUs)
        .noCubeModel()
        .itemProperties(new Item.Properties().rarity(Rarity.UNCOMMON))
        .build();
  }

}
