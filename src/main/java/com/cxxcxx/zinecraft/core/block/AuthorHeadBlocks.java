package com.cxxcxx.zinecraft.core.block;

import com.cxxcxx.zinecraft.api.block.BlockEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

/**
 * Decorative heads built from the mod authors' public Minecraft skins.
 */
public final class AuthorHeadBlocks {
  public static final AuthorHeadBlocks INSTANCE = new AuthorHeadBlocks();

  private static final BlockEntry<AuthorHeadBlock> Z8Z6Z8Z6_HEAD = register("z8z6z8z6_head", "z8z6z8z6 的头", "z8z6z8z6's Head");
  private static final BlockEntry<AuthorHeadBlock> YE_XINGCHEN_HEAD = register("ye_xingchen_head", "Ye_xingchen 的头", "Ye_xingchen's Head");

  private AuthorHeadBlocks() {
  }

  private static BlockEntry<AuthorHeadBlock> register(String path, String zhCn, String enUs) {
    return Zinecraft.INSTANCE.getBLOCKS().register(
        path,
        zhCn,
        enUs,
        true,
        null,
        false,
        true,
        new Item.Properties().rarity(Rarity.UNCOMMON),
        () -> new AuthorHeadBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .noOcclusion()
            .strength(1.0F)
            .sound(SoundType.WOOD))
    );
  }

  public AuthorHeadBlock getZ8Z6Z8Z6Head() {
    return Z8Z6Z8Z6_HEAD.getBlock();
  }

  public AuthorHeadBlock getYeXingchenHead() {
    return YE_XINGCHEN_HEAD.getBlock();
  }
}
