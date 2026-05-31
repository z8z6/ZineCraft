package com.cxxcxx.zinecraft.core.structure

import com.cxxcxx.zinecraft.core.data.ModTemplatePool
import com.mojang.datafixers.util.Either
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate
import java.util.*
import java.util.function.Function

class ExampleSinglePoolElement(
  either: Either<ResourceLocation?, StructureTemplate?>,
  holder: Holder<StructureProcessorList?>,
  projection: StructureTemplatePool.Projection,
  overrideLiquidSettings: Optional<LiquidSettings?>,
  private val groundLevelDelta: Int
) : SinglePoolElement(either, holder, projection, overrideLiquidSettings) {
  override fun getGroundLevelDelta(): Int {
    return groundLevelDelta
  }

  override fun getType(): StructurePoolElementType<*> {
    return ModTemplatePool.SINGLE_POOL
  }

  override fun toString(): String {
    return "Single[" + this.template + "]"
  }

  companion object {
    val CODEC: MapCodec<ExampleSinglePoolElement> =
      RecordCodecBuilder.mapCodec<ExampleSinglePoolElement>(Function { instance: RecordCodecBuilder.Instance<ExampleSinglePoolElement?>? ->
        instance!!.group(
          templateCodec<ExampleSinglePoolElement?>(),
          processorsCodec<ExampleSinglePoolElement?>(),
          projectionCodec<ExampleSinglePoolElement?>(),
          overrideLiquidSettingsCodec<ExampleSinglePoolElement?>(),
          Codec.INT.fieldOf("ground_level_delta")
            .forGetter<ExampleSinglePoolElement?>({ o: ExampleSinglePoolElement? -> o!!.groundLevelDelta })
        ).apply<ExampleSinglePoolElement?>(
          instance,
          { either: Either<ResourceLocation?, StructureTemplate?>?, holder: Holder<StructureProcessorList?>?, projection: StructureTemplatePool.Projection?, overrideLiquidSettings: Optional<LiquidSettings?>?, groundLevelDelta: Int? ->
            ExampleSinglePoolElement(
              either!!,
              holder!!,
              projection!!,
              overrideLiquidSettings!!,
              groundLevelDelta!!
            )
          })
      })

    fun make(
      string: String,
      holder: Holder<StructureProcessorList?>,
      groundLevelDelta: Int
    ): Function<StructureTemplatePool.Projection?, SinglePoolElement?> {
      return Function { projection: StructureTemplatePool.Projection? ->
        ExampleSinglePoolElement(
          Either.left(
            ResourceLocation.parse(string)
          ), holder, projection!!, Optional.empty<LiquidSettings>() as Optional<LiquidSettings?>, groundLevelDelta
        )
      }
    }
  }
}