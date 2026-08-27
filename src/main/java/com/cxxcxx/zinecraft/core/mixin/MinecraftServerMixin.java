package com.cxxcxx.zinecraft.core.mixin;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.dimension.TerraDimensionLoadPolicy;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.dimension.LevelStem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.Set;

/** 在服务端实例化维度前，为超平坦存档排除泰拉 LevelStem。 */
@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
  @Redirect(
      method = "createLevels",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/core/Registry;entrySet()Ljava/util/Set;"
      )
  )
  private Set<Map.Entry<ResourceKey<LevelStem>, LevelStem>> zinecraft$filterFlatWorldDimensions(
      Registry<LevelStem> registry
  ) {
    Set<Map.Entry<ResourceKey<LevelStem>, LevelStem>> dimensions =
        TerraDimensionLoadPolicy.levelStemsForServer(registry);
    if (dimensions.size() != registry.entrySet().size()) {
      Zinecraft.LOGGER.info("检测到超平坦主世界：跳过泰拉维度及其国家、城市世界生成");
    }
    return dimensions;
  }
}