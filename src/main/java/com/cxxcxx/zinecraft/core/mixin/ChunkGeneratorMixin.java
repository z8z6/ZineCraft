package com.cxxcxx.zinecraft.core.mixin;

import com.cxxcxx.zinecraft.core.dimension.TerraDimensionLoadPolicy;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** 阻止超平坦区块生成器自然放置任何 Zinecraft 结构。 */
@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {
  @Inject(method = "tryGenerateStructure", at = @At("HEAD"), cancellable = true)
  private void zinecraft$skipStructuresInFlatWorlds(
      StructureSet.StructureSelectionEntry structureSelectionEntry,
      StructureManager structureManager,
      RegistryAccess registryAccess,
      RandomState random,
      StructureTemplateManager structureTemplateManager,
      long seed,
      ChunkAccess chunk,
      ChunkPos chunkPos,
      SectionPos sectionPos,
      CallbackInfoReturnable<Boolean> callback
  ) {
    if (!TerraDimensionLoadPolicy.shouldGenerateStructure(
        (ChunkGenerator) (Object) this,
        structureSelectionEntry.structure()
    )) {
      callback.setReturnValue(false);
    }
  }
}