package com.cxxcxx.zinecraft.core.dimension;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/** 控制泰拉维度是否应随当前存档创建。 */
public final class TerraDimensionLoadPolicy {
  private static final ResourceLocation TERRA_ID =
      ResourceLocation.fromNamespaceAndPath("zinecraft", "terra");

  private TerraDimensionLoadPolicy() {
  }

  /** 超平坦主世界不创建泰拉，其他维度及普通世界保持注册表顺序。 */
  public static Set<Map.Entry<ResourceKey<LevelStem>, LevelStem>> levelStemsForServer(
      Registry<LevelStem> registry
  ) {
    LevelStem overworld = registry.get(LevelStem.OVERWORLD);
    boolean flatOverworld = overworld != null && overworld.generator() instanceof FlatLevelSource;
    if (!flatOverworld) {
      return registry.entrySet();
    }

    var filtered = new LinkedHashSet<Map.Entry<ResourceKey<LevelStem>, LevelStem>>();
    for (var entry : registry.entrySet()) {
      if (shouldLoadLevelStem(entry.getKey(), true)) filtered.add(entry);
    }
    return Collections.unmodifiableSet(filtered);
  }

  static boolean shouldLoadLevelStem(ResourceKey<LevelStem> key, boolean flatOverworld) {
    return !flatOverworld || !key.location().equals(TERRA_ID);
  }

  /** 超平坦生成器拒绝 Zinecraft 自然结构；命令或结构方块手动放置不经过此入口。 */
  public static boolean shouldGenerateStructure(
      ChunkGenerator generator,
      Holder<Structure> structure
  ) {
    return structure.unwrapKey()
        .map(key -> shouldGenerateStructure(generator instanceof FlatLevelSource, key.location()))
        .orElse(true);
  }

  static boolean shouldGenerateStructure(boolean flatWorld, ResourceLocation structureId) {
    return !flatWorld || !structureId.getNamespace().equals(TERRA_ID.getNamespace());
  }
}