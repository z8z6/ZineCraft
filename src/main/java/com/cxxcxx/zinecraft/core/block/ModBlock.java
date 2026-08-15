package com.cxxcxx.zinecraft.core.block;

import com.cxxcxx.zinecraft.api.block.BlockCatalog;
import com.cxxcxx.zinecraft.api.block.BlockEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.dimension.StarGateControllerBlock;
import com.cxxcxx.zinecraft.core.dimension.StarGatePortalBlock;
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public final class ModBlock {
  @NotNull
  public static final ModBlock INSTANCE = new ModBlock();
  @NotNull
  private static final BlockEntry<Block> STARGATE_ARCH = BlockCatalog.register$default(
          Zinecraft.INSTANCE.getBLOCKS(), "stargate_arch", "星门拱石", "Stargate Archstone", false, null, false, false, ModBlock::STARGATE_ARCH$lambda$0, 120, null
  );
  @NotNull
  private static final BlockEntry<StarGateControllerBlock> STARGATE_CONTROLLER = BlockCatalog.register$default(
          Zinecraft.INSTANCE.getBLOCKS(),
          "stargate_controller",
          "星门协议控制器",
          "Stargate Protocol Controller",
          false,
          null,
          false,
          false,
          ModBlock::STARGATE_CONTROLLER$lambda$0,
          120,
          null
  );
  @NotNull
  private static final BlockEntry<StarGatePortalBlock> STARGATE_PORTAL = BlockCatalog.register$default(
          Zinecraft.INSTANCE.getBLOCKS(),
          "stargate_portal",
          "星门事件视界",
          "Stargate Event Horizon",
          false,
          null,
          false,
          false,
          ModBlock::STARGATE_PORTAL$lambda$0,
          16,
          null
  );
  @NotNull
  private static final BlockEntry<ExampleEntityBlock> EXAMPLE_ENTITY_BLOCK = BlockCatalog.register$default(
          Zinecraft.INSTANCE.getBLOCKS(),
          "example_entity_block",
          "示例实体方块",
          "Example Entity Block",
          false,
          null,
          false,
          false,
          ModBlock::EXAMPLE_ENTITY_BLOCK$lambda$0,
          120,
          null
  );

  static {
    Zinecraft.INSTANCE
        .getTRANSLATIONS()
        .add("message.zinecraft.stargate.requires_protocol_originium", "控制器拒绝访问：请手持协议源石右键激活", "Access denied: right-click while holding Protocol Originium");
    Zinecraft.INSTANCE
        .getTRANSLATIONS()
        .add("message.zinecraft.stargate.activated", "协议认证完成，星门事件视界已建立", "Protocol accepted. Stargate event horizon established");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("message.zinecraft.stargate.already_active", "星门已处于激活状态", "The stargate is already active");
    Zinecraft.INSTANCE
        .getTRANSLATIONS()
        .add("message.zinecraft.stargate.damaged", "星门拱体不完整，无法建立事件视界", "The stargate arch is incomplete and cannot form an event horizon");
  }

  private ModBlock() {
  }

  private static final Block STARGATE_ARCH$lambda$0() {
    return new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(18.0F, 1200.0F).sound(SoundType.DEEPSLATE));
  }

  private static final int STARGATE_CONTROLLER$lambda$0$0(BlockState state) {
    return state.getValue(StarGateControllerBlock.Companion.getACTIVE()) ? 12 : 3;
  }

  private static final StarGateControllerBlock STARGATE_CONTROLLER$lambda$0() {
    Properties properties = Properties.of()
        .mapColor(MapColor.COLOR_CYAN)
        .requiresCorrectToolForDrops()
        .strength(18.0F, 1200.0F)
        .lightLevel(ModBlock::STARGATE_CONTROLLER$lambda$0$0)
        .sound(SoundType.METAL);
    return new StarGateControllerBlock(properties);
  }

  private static final int STARGATE_PORTAL$lambda$0$0(BlockState it) {
    return 12;
  }

  private static final StarGatePortalBlock STARGATE_PORTAL$lambda$0() {
    Properties properties = Properties.of()
        .mapColor(MapColor.COLOR_CYAN)
        .noCollission()
        .noOcclusion()
        .strength(-1.0F, 3600000.0F)
        .lightLevel(ModBlock::STARGATE_PORTAL$lambda$0$0)
        .sound(SoundType.GLASS);
    return new StarGatePortalBlock(properties);
  }

  private static final ExampleEntityBlock EXAMPLE_ENTITY_BLOCK$lambda$0() {
    Properties properties = Properties.of().sound(SoundType.GRASS);
    return new ExampleEntityBlock(properties);
  }

  @NotNull
  public final Block getSTARGATE_ARCH() {
    return STARGATE_ARCH.getBlock();
  }

  @NotNull
  public final StarGateControllerBlock getSTARGATE_CONTROLLER() {
    return STARGATE_CONTROLLER.getBlock();
  }

  @NotNull
  public final StarGatePortalBlock getSTARGATE_PORTAL() {
    return STARGATE_PORTAL.getBlock();
  }

  @NotNull
  public final ExampleEntityBlock getEXAMPLE_ENTITY_BLOCK() {
    return EXAMPLE_ENTITY_BLOCK.getBlock();
  }
}
