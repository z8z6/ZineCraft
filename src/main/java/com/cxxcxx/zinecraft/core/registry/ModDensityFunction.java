package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.DensityFunctionBuilder;
import com.cxxcxx.zinecraft.api.world.dimension.OverworldNoiseSettingsFactory;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.worldgen.density.TerraCityRegionDensityFunction;
import com.cxxcxx.zinecraft.core.worldgen.density.TerraNationDensityFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.Noises;

import java.util.LinkedHashMap;
import java.util.Map;

/** 泰拉国家与城市 Region 地形密度函数注册入口。 */
public final class ModDensityFunction {
  public static final int SURFACE_CITY_GROUND_Y = 80;
  public static final int UNDERGROUND_CITY_GROUND_Y = -64;
  public static final double NATION_BORDER_BLEND_WIDTH = 512.0;
  public static final double REGION_HILL_NOISE_SCALE = 0.006;
  public static final double REGION_HILL_BASE_OFFSET = 0.0125;
  public static final double REGION_HILL_AMPLITUDE = 0.035;
  public static final double MOBILE_PLOT_TRANSITION_WIDTH = 256.0;
  public static final int TERRAIN_CEILING_FADE_START_Y = 256;
  public static final int TERRAIN_CEILING_FADE_END_Y = 384;

  static {
    Zinecraft.DENSITY_FUNCTIONS.type("nation_terrain", TerraNationDensityFunction.CODEC);
    Zinecraft.DENSITY_FUNCTIONS.type("city_region_terrain", TerraCityRegionDensityFunction.CODEC);
  }

  /** 未叠加国家差异和城市平地规则的原版主世界最终密度。 */
  public static final DensityFunctionBuilder TERRA_BASE_DENSITY = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/base_density")
      .function(context -> OverworldNoiseSettingsFactory.createOverworldRouter(
          context.lookup(Registries.DENSITY_FUNCTION),
          context.lookup(Registries.NOISE)
      ).finalDensity())
      .build();

  public static final DensityFunctionBuilder AEGIR_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/aegir")
      .function(new NationTerrainParameters(-0.0296875), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder BOLIVAR_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/bolivar")
      .function(new NationTerrainParameters(-0.0265625), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder COLUMBIA_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/columbia")
      .function(new NationTerrainParameters(-0.0234375), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder DURIN_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/durin")
      .function(new NationTerrainParameters(-0.0203125), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder HIGASHI_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/higashi")
      .function(new NationTerrainParameters(-0.0171875), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder IBERIA_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/iberia")
      .function(new NationTerrainParameters(-0.0140625), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder KAZDEL_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/kazdel")
      .function(new NationTerrainParameters(-0.0109375), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder KAZIMIERZ_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/kazimierz")
      .function(new NationTerrainParameters(-0.0078125), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder KJERAG_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/kjerag")
      .function(new NationTerrainParameters(-0.0046875), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder LATERANO_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/laterano")
      .function(new NationTerrainParameters(-0.0015625), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder LEITHANIEN_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/leithanien")
      .function(new NationTerrainParameters(0.0015625), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder MINOS_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/minos")
      .function(new NationTerrainParameters(0.0046875), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder RIM_BILLITON_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/rim_billiton")
      .function(new NationTerrainParameters(0.0078125), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder SAMI_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/sami")
      .function(new NationTerrainParameters(0.0109375), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder SARGON_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/sargon")
      .function(new NationTerrainParameters(0.0140625), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder SIESTA_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/siesta")
      .function(new NationTerrainParameters(0.0171875), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder SIRACUSA_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/siracusa")
      .function(new NationTerrainParameters(0.0203125), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder URSUS_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/ursus")
      .function(new NationTerrainParameters(0.0234375), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder VICTORIA_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/victoria")
      .function(new NationTerrainParameters(0.0265625), ModDensityFunction::createNationTerrain)
      .build();
  public static final DensityFunctionBuilder YAN_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation/yan")
      .function(new NationTerrainParameters(0.0296875), ModDensityFunction::createNationTerrain)
      .build();

  /** 国家 ID 到独立密度函数字段的显式映射。 */
  public static final Map<String, DensityFunctionBuilder> NATION_TERRAINS = Map.ofEntries(
      Map.entry(ModNation.AEGIR.id, AEGIR_TERRAIN),
      Map.entry(ModNation.BOLIVAR.id, BOLIVAR_TERRAIN),
      Map.entry(ModNation.COLUMBIA.id, COLUMBIA_TERRAIN),
      Map.entry(ModNation.DURIN.id, DURIN_TERRAIN),
      Map.entry(ModNation.HIGASHI.id, HIGASHI_TERRAIN),
      Map.entry(ModNation.IBERIA.id, IBERIA_TERRAIN),
      Map.entry(ModNation.KAZDEL.id, KAZDEL_TERRAIN),
      Map.entry(ModNation.KAZIMIERZ.id, KAZIMIERZ_TERRAIN),
      Map.entry(ModNation.KJERAG.id, KJERAG_TERRAIN),
      Map.entry(ModNation.LATERANO.id, LATERANO_TERRAIN),
      Map.entry(ModNation.LEITHANIEN.id, LEITHANIEN_TERRAIN),
      Map.entry(ModNation.MINOS.id, MINOS_TERRAIN),
      Map.entry(ModNation.RIM_BILLITON.id, RIM_BILLITON_TERRAIN),
      Map.entry(ModNation.SAMI.id, SAMI_TERRAIN),
      Map.entry(ModNation.SARGON.id, SARGON_TERRAIN),
      Map.entry(ModNation.SIESTA.id, SIESTA_TERRAIN),
      Map.entry(ModNation.SIRACUSA.id, SIRACUSA_TERRAIN),
      Map.entry(ModNation.URSUS.id, URSUS_TERRAIN),
      Map.entry(ModNation.VICTORIA.id, VICTORIA_TERRAIN),
      Map.entry(ModNation.YAN.id, YAN_TERRAIN)
  );

  /** 根据冻结的国家边界选择对应国家地形。 */
  public static final DensityFunctionBuilder TERRA_NATION_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/nation_terrain")
      .function(context -> new TerraNationDensityFunction(nationReferences(context), reference(
          context, TERRA_BASE_DENSITY
      ), NATION_BORDER_BLEND_WIDTH))
      .build();

  /** City Region 的移动地块之外使用低频噪声抬升为连绵山丘。 */
  public static final DensityFunctionBuilder TERRA_REGION_HILLS = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/region_hills")
      .function(context -> DensityFunctions.add(
          reference(context, TERRA_NATION_TERRAIN),
          DensityFunctions.add(
              DensityFunctions.constant(REGION_HILL_BASE_OFFSET),
              DensityFunctions.mul(
                  DensityFunctions.constant(REGION_HILL_AMPLITUDE),
                  DensityFunctions.noise(
                      context.lookup(Registries.NOISE).getOrThrow(Noises.SURFACE_SECONDARY),
                      REGION_HILL_NOISE_SCALE,
                      0.0
                  )
              )
          )
      ))
      .build();

  /** 移动地块内使用平地，City Region 的其余位置使用山丘地形。 */
  public static final DensityFunctionBuilder TERRA_CITY_REGION_TERRAIN = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/city_region_terrain")
      .function(context -> new TerraCityRegionDensityFunction(
          reference(context, TERRA_NATION_TERRAIN),
          reference(context, TERRA_REGION_HILLS),
          flatTerrain(SURFACE_CITY_GROUND_Y),
          flatTerrain(UNDERGROUND_CITY_GROUND_Y),
          MOBILE_PLOT_TRANSITION_WIDTH
      ))
      .build();

  /** 高空逐步强制为空气，避免附加偏移抵消原版高空负密度并形成通天石柱。 */
  public static final DensityFunctionBuilder TERRA_FINAL_DENSITY = Zinecraft.DENSITY_FUNCTIONS
      .densityFunction("terra/final_density")
      .function(context -> DensityFunctions.min(
          reference(context, TERRA_CITY_REGION_TERRAIN),
          DensityFunctions.yClampedGradient(
              TERRAIN_CEILING_FADE_START_Y,
              TERRAIN_CEILING_FADE_END_Y,
              1.0,
              -1.0
          )
      ))
      .build();

  private ModDensityFunction() {
  }

  private static DensityFunction createNationTerrain(
      BootstrapContext<DensityFunction> context,
      NationTerrainParameters parameters
  ) {
    return DensityFunctions.add(
        reference(context, TERRA_BASE_DENSITY),
        DensityFunctions.constant(parameters.densityOffset())
    );
  }

  private static Map<String, DensityFunction> nationReferences(BootstrapContext<DensityFunction> context) {
    LinkedHashMap<String, DensityFunction> references = new LinkedHashMap<>();
    NATION_TERRAINS.forEach((id, builder) -> references.put(id, reference(context, builder)));
    return references;
  }

  private static DensityFunction reference(
      BootstrapContext<DensityFunction> context,
      DensityFunctionBuilder builder
  ) {
    return new DensityFunctions.HolderHolder(
        context.lookup(Registries.DENSITY_FUNCTION).getOrThrow(builder.key())
    );
  }

  private static DensityFunction flatTerrain(int groundY) {
    return DensityFunctions.yClampedGradient(groundY, groundY + 1, 1.0, -1.0);
  }

  /** 每个国家在 Builder 声明中显式传入的地形参数。 */
  public record NationTerrainParameters(double densityOffset) {
    public NationTerrainParameters {
      if (!Double.isFinite(densityOffset)) throw new IllegalArgumentException("国家密度偏移必须是有限数");
    }
  }

  public static void bootstrap() {
  }
}
