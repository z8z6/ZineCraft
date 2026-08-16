package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.ConcentricRingBounds;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NationWorldgenContractTest {
  private static final int LEGACY_SETTLEMENT_SPACING = 52;
  private static final int LEGACY_SETTLEMENT_SEPARATION = 24;
  private static final int LEGACY_SETTLEMENT_JIGSAW_DEPTH = 7;
  private static final Map<String, String> NATION_BIOMES = nationBiomes();

  private static String expectedBiome(String structure) {
    return NATION_BIOMES.entrySet().stream()
        .filter(entry -> structure.startsWith(entry.getKey() + "_"))
        .map(Map.Entry::getValue)
        .findFirst()
        .orElseThrow(() -> new AssertionError("未知国家结构: " + structure));
  }

  private static String readMain(String relative) throws IOException {
    return Files.readString(Path.of("src/main/java/com/cxxcxx/zinecraft", relative));
  }

  private static String readGenerated(String relative) throws IOException {
    return Files.readString(Path.of("src/generated/resources/data/zinecraft", relative));
  }

  private static int jsonInt(String json, String name) {
    var matcher = Pattern.compile("\\\"" + name + "\\\"\\s*:\\s*(\\d+)").matcher(json);
    assertTrue(matcher.find(), name);
    return Integer.parseInt(matcher.group(1));
  }

  private static int intConstant(String source, String name) {
    var matcher = Pattern.compile("static final int " + name + " = (\\d+);").matcher(source);
    assertTrue(matcher.find(), name);
    return Integer.parseInt(matcher.group(1));
  }

  private static Map<String, String> nationBiomes() {
    var result = new LinkedHashMap<String, String>();
    result.put("rim_billiton", "RIM_BILLITON_MINING_BADLANDS");
    result.put("aegir", "AEGIR_ABYSSAL_SEA");
    result.put("bolivar", "BOLIVAR_PLAIN");
    result.put("higashi", "HIGASHI_SHADOW_RIFT");
    result.put("durin", "DURIN_UNDERGROUND_GARDEN");
    result.put("columbia", "COLUMBIA_SANDSTONE_WILDS");
    result.put("kazimierz", "KAZIMIERZ_KNIGHTLAND");
    result.put("kazdel", "KAZDEL_SCARRED_WASTES");
    result.put("laterano", "LATERANO_HOLY_FIELDS");
    result.put("leithanien", "LEITHANIEN_TWILIGHT_FOREST");
    result.put("minos", "MINOS_SUNLIT_HILLS");
    result.put("sargon", "SARGON_ROCKY_DESERT");
    result.put("sami", "SAMI_FROZEN_FOREST");
    result.put("victoria", "VICTORIA_MISTY_HIGHLANDS");
    result.put("ursus", "URSUS_FROZEN_STEPPE");
    result.put("kjerag", "KJERAG_SNOWY_PEAKS");
    result.put("siracusa", "SIRACUSA_RAINY_WOODLAND");
    result.put("yan", "YAN_MOUNTAIN_GROVE");
    result.put("iberia", "IBERIA_SALT_DELTA");
    return Map.copyOf(result);
  }

  @Test
  void everyNationalBiomeHasExactlyOneMatchingSettlement() throws IOException {
    String source = readMain("core/structure/NationSettlements.java");
    var pattern = Pattern.compile(
        "settlementWithDefaults\\(\\s*INSTANCE,\\s*\"([a-z_]+)\",\\s*NationBiomes\\.INSTANCE\\.get([A-Z0-9_]+)\\(\\)",
        Pattern.MULTILINE
    );
    var matcher = pattern.matcher(source);
    var coverage = new LinkedHashMap<String, String>();
    while (matcher.find()) {
      coverage.put(matcher.group(1), matcher.group(2));
    }

    assertEquals(19, coverage.size());
    assertEquals(19, coverage.values().stream().distinct().count());
    coverage.forEach((structure, biome) -> assertEquals(expectedBiome(structure), biome, structure));
  }

  @Test
  void nationalSettlementsAreDenserWithoutUnsafeOverlap() throws IOException {
    String source = readMain("core/structure/NationSettlements.java");
    int spacing = intConstant(source, "DENSE_SETTLEMENT_SPACING");
    int separation = intConstant(source, "DENSE_SETTLEMENT_SEPARATION");
    int depth = intConstant(source, "DENSE_SETTLEMENT_JIGSAW_DEPTH");
    int maxDistance = intConstant(source, "DENSE_SETTLEMENT_MAX_DISTANCE");

    assertEquals(36, spacing);
    assertEquals(16, separation);
    assertEquals(9, depth);
    assertEquals(112, maxDistance);
    assertTrue(spacing > separation);
    assertTrue(spacing < LEGACY_SETTLEMENT_SPACING);
    assertTrue(separation < LEGACY_SETTLEMENT_SEPARATION);
    assertTrue(depth > LEGACY_SETTLEMENT_JIGSAW_DEPTH && depth <= 20);
    assertTrue(maxDistance <= 112);

    double candidateDensityMultiplier = Math.pow((double) LEGACY_SETTLEMENT_SPACING / spacing, 2.0D);
    assertTrue(candidateDensityMultiplier >= 2.0D, "聚落候选区密度至少应为旧配置的两倍");
    assertTrue(
        (separation + 1) * 16 > maxDistance * 2,
        "最近聚落中心间距必须大于两个完整 Jigsaw 半径"
    );

    assertTrue(source.contains("spacing, DENSE_SETTLEMENT_SEPARATION,"));
    assertTrue(source.contains("spacing != DENSE_SETTLEMENT_SPACING"));
    assertTrue(source.contains("DENSE_SETTLEMENT_JIGSAW_DEPTH, DENSE_SETTLEMENT_MAX_DISTANCE,"));
    assertTrue(source.contains("fixedOriginSettlement("));
    assertTrue(source.contains("\"laterano_monastery_town\".equals(path)"));
  }

  @Test
  void generatedSettlementJsonMatchesTheDenseRuntimeContract() throws IOException {
    String source = readMain("core/structure/NationSettlements.java");
    var matcher = Pattern.compile(
        "settlementWithDefaults\\(\\s*INSTANCE,\\s*\"([a-z_]+)\",\\s*NationBiomes\\.INSTANCE\\.get([A-Z0-9_]+)\\(\\)",
        Pattern.MULTILINE
    ).matcher(source);
    int count = 0;
    while (matcher.find()) {
      count++;
      String structureId = matcher.group(1);
      String structureSet = readGenerated("worldgen/structure_set/" + structureId + ".json");
      if ("laterano_monastery_town".equals(structureId)) {
        assertTrue(structureSet.contains("\"type\": \"zinecraft:fixed_origin\""), structureId);
      } else {
        assertEquals(36, jsonInt(structureSet, "spacing"), structureId);
        assertEquals(16, jsonInt(structureSet, "separation"), structureId);
      }

      String structure = readGenerated("worldgen/structure/" + structureId + ".json");
      assertEquals(9, jsonInt(structure, "size"), structureId);
      assertEquals(112, jsonInt(structure, "max_distance_from_center"), structureId);
      assertTrue(
          structure.contains("\"biomes\": \"zinecraft:"
              + matcher.group(2).toLowerCase(Locale.ROOT) + "\""),
          structureId
      );
    }
    assertEquals(19, count);
  }

  @Test
  void allFormalLandmarksPreferTheirNationAndStayWithinFiveThousandBlocks() throws IOException {
    String source = readMain("core/structure/NationLandmarks.java");
    var pattern = Pattern.compile(
        "modularLandmark\\(\\s*\"([a-z_]+)\",\\s*NationBiomes\\.INSTANCE\\.get([A-Z0-9_]+)\\(\\),\\s*(\\d+)",
        Pattern.MULTILINE
    );
    var matcher = pattern.matcher(source);
    int count = 0;
    while (matcher.find()) {
      count++;
      String structure = matcher.group(1);
      assertEquals(expectedBiome(structure), matcher.group(2), structure);
      assertTrue(
          ConcentricRingBounds.maximumRadiusBlocks(Integer.parseInt(matcher.group(3)))
              <= ConcentricRingBounds.GUARANTEED_LANDMARK_RADIUS_BLOCKS,
          structure
      );
    }
    assertEquals(38, count);
    assertTrue(source.contains("getSTRUCTURES().guaranteedLandmark("));
    assertTrue(source.contains("NationBiomes.INSTANCE.getALL_TERRA_BIOMES()"));
  }

  @Test
  void landmarkFallbackContainsAllNationalBiomes() throws IOException {
    String source = readMain("core/biome/NationBiomes.java");
    int methodStart = source.indexOf("getALL_TERRA_BIOMES()");
    assertTrue(methodStart >= 0);
    String method = source.substring(methodStart);
    NATION_BIOMES.values().forEach(biome -> assertTrue(method.contains(biome), biome));
    assertTrue(method.contains("TERRA_CATASTROPHE_ZONE"));
  }
}
