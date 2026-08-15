package com.cxxcxx.zinecraft.compat.jer;

import com.cxxcxx.zinecraft.api.world.feature.OreEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.dimension.ModDimensions;
import com.cxxcxx.zinecraft.core.item.ModItem;
import com.cxxcxx.zinecraft.core.worldgen.ModWorldFeatures;
import jeresources.api.IJERAPI;
import jeresources.api.IJERPlugin;
import jeresources.api.JERPlugin;
import jeresources.api.distributions.DistributionCustom;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.compatibility.api.JERAPI;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 将项目矿脉的实际高度采样参数提供给开发环境中的 Just Enough Resources。
 */
@JERPlugin
public final class ZinecraftJerPlugin implements IJERPlugin {
  private static final int MIN_Y = -64;
  private static final int MAX_JER_Y = 255;
  private static final int BIASED_INNER = 3;
  private static final AtomicBoolean REGISTERED = new AtomicBoolean();

  /**
   * JER 1.6.0.17 的 NeoForge 实现错误地按 IJERPlugin 接口类型扫描注解，标准入口不会触发。
   * 在确认 JER 已加载后由主模组调用此入口；保留标准入口以便后续版本修复后自动恢复。
   */
  public static void install() {
    new ZinecraftJerPlugin().receive(JERAPI.getInstance());
  }

  private static void register(IJERAPI api, OreEntry ore, ItemLike drop) {
    var distribution = biasedToBottomDistribution(ore);
    var loot = new LootDrop(new ItemStack(drop));
    api.getWorldGenRegistry().register(new ItemStack(ore.getBlock()), distribution, Restriction.OVERWORLD, false, loot);
    api.getWorldGenRegistry().register(
        new ItemStack(ore.getBlock()), distribution,
        new Restriction(new DimensionRestriction(ModDimensions.INSTANCE.getTERRA().getLevelKey())), false, loot
    );
  }

  /**
   * 对应 FeatureCatalog 的 BiasedToBottomHeight(min=-64, max=maxY, inner=3)。数组总量按
   * JER 的每区块平均方块数标度归一化，因此高度曲线和矿脉规模都会显示在概率图中。
   */
  private static DistributionCustom biasedToBottomDistribution(OreEntry ore) {
    float[] chances = new float[MAX_JER_Y - MIN_Y + 1];
    int maxY = Math.min(ore.getMaxY(), MAX_JER_Y);
    int outerChoices = maxY - MIN_Y - BIASED_INNER + 1;
    if (outerChoices <= 0) {
      throw new IllegalArgumentException("JER 矿脉高度范围无效: " + ore.getPlacedKey().location());
    }

    float densityScale = ore.getVeinsPerChunk() * ore.getVeinSize() / (float) chances.length;
    for (int outer = 0; outer < outerChoices; outer++) {
      int innerChoices = outer + BIASED_INNER;
      float sampleChance = densityScale / outerChoices / innerChoices;
      for (int offset = 0; offset < innerChoices; offset++) {
        chances[offset] += sampleChance;
      }
    }
    return new DistributionCustom(chances, MIN_Y);
  }

  @Override
  public void receive(IJERAPI api) {
    if (!REGISTERED.compareAndSet(false, true)) return;
    register(api, ModWorldFeatures.INSTANCE.getORIGINITE_ORE(), ModItem.INSTANCE.getORIGINITE());
    register(api, ModWorldFeatures.INSTANCE.getORIROCK_ORE(), ModItem.INSTANCE.getORIROCK());
    register(api, ModWorldFeatures.INSTANCE.getORIRON_ORE(), ModItem.INSTANCE.getORIRON_SHARD());
    register(api, ModWorldFeatures.INSTANCE.getMANGANESE_ORE(), ModItem.INSTANCE.getMANGANESE_ORE());
    register(api, ModWorldFeatures.INSTANCE.getGRINDSTONE_ORE(), ModItem.INSTANCE.getGRINDSTONE());
    register(api, ModWorldFeatures.INSTANCE.getRMA70_ORE(), ModItem.INSTANCE.getRMA70_12());
    register(api, ModWorldFeatures.INSTANCE.getCRYSTAL_ELEMENT_ORE(), ModItem.INSTANCE.getCRYSTAL_ELEMENT());
    register(api, ModWorldFeatures.INSTANCE.getLOXIC_KOHL_ORE(), ModItem.INSTANCE.getLOXIC_KOHL());
    Zinecraft.INSTANCE.getLogger().info("Registered 8 material ore distributions for JER in the Overworld and Terra");
  }
}
