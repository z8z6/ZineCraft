package com.cxxcxx.zinecraft.core.block;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.dimension.StarGateControllerBlock;
import com.cxxcxx.zinecraft.core.dimension.StarGatePortalBlock;
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class ModBlock {
  public static final ModBlock INSTANCE = new ModBlock();
  public final DeferredBlock<Block> STARGATE_ARCH = Zinecraft.BLOCKS
      .builder("stargate_arch", "星门拱石", ModBlock::createStargateArch)
      .enUs("Stargate Archstone")
      .build();
  public final DeferredBlock<StarGateControllerBlock> STARGATE_CONTROLLER = Zinecraft.BLOCKS
      .builder("stargate_controller", "星门协议控制器", ModBlock::createStargateController)
      .enUs("Stargate Protocol Controller")
      .build();
  public final DeferredBlock<StarGatePortalBlock> STARGATE_PORTAL = Zinecraft.BLOCKS
      .builder("stargate_portal", "星门事件视界", ModBlock::createStargatePortal)
      .enUs("Stargate Event Horizon")
      .noLoot()
      .noCubeModel()
      .noBlockItem()
      .build();
  public final DeferredBlock<ExampleEntityBlock> EXAMPLE_ENTITY_BLOCK = Zinecraft.BLOCKS
      .builder("example_entity_block", "示例实体方块", ModBlock::createExampleEntityBlock)
      .enUs("Example Entity Block")
      .build();

  static {
    Zinecraft.TRANSLATIONS
        .add("message.zinecraft.stargate.requires_protocol_originium", "控制器拒绝访问：请手持协议源石右键激活", "Access denied: right-click while holding Protocol Originium");
    Zinecraft.TRANSLATIONS
        .add("message.zinecraft.stargate.activated", "协议认证完成，星门事件视界已建立", "Protocol accepted. Stargate event horizon established");
    Zinecraft.TRANSLATIONS.add("message.zinecraft.stargate.already_active", "星门已处于激活状态", "The stargate is already active");
    Zinecraft.TRANSLATIONS
        .add("message.zinecraft.stargate.damaged", "星门拱体不完整，无法建立事件视界", "The stargate arch is incomplete and cannot form an event horizon");
  }

  private ModBlock() {
  }

  private static Block createStargateArch() {
    return new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(18.0F, 1200.0F).sound(SoundType.DEEPSLATE));
  }

  private static int stargateControllerLight(BlockState state) {
    return state.getValue(StarGateControllerBlock.ACCESS.getACTIVE()) ? 12 : 3;
  }

  private static StarGateControllerBlock createStargateController() {
    Properties properties = Properties.of()
        .mapColor(MapColor.COLOR_CYAN)
        .requiresCorrectToolForDrops()
        .strength(18.0F, 1200.0F)
        .lightLevel(ModBlock::stargateControllerLight)
        .sound(SoundType.METAL);
    return new StarGateControllerBlock(properties);
  }

  private static int stargatePortalLight(BlockState state) {
    return 12;
  }

  private static StarGatePortalBlock createStargatePortal() {
    Properties properties = Properties.of()
        .mapColor(MapColor.COLOR_CYAN)
        .noCollission()
        .noOcclusion()
        .strength(-1.0F, 3600000.0F)
        .lightLevel(ModBlock::stargatePortalLight)
        .sound(SoundType.GLASS);
    return new StarGatePortalBlock(properties);
  }

  private static ExampleEntityBlock createExampleEntityBlock() {
    Properties properties = Properties.of().sound(SoundType.GRASS);
    return new ExampleEntityBlock(properties);
  }

}
