package com.cxxcxx.zinecraft.core.entity;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NationResidentTextureTest {
  private static final List<String> NATIONS = List.of(
      "aegir", "bolivar", "higashi", "durin", "columbia", "kazimierz", "kazdel", "laterano",
      "leithanien", "rim_billiton", "minos", "sargon", "sami", "victoria", "ursus", "kjerag",
      "siracusa", "yan", "iberia"
  );

  @Test
  void everyTerraNationHasAStandardWidePlayerSkin() throws Exception {
    assertEquals(19, NATIONS.size());
    for (String nation : NATIONS) {
      String path = "/assets/zinecraft/textures/entity/nation_resident/" + nation + ".png";
      try (var input = getClass().getResourceAsStream(path)) {
        assertNotNull(input, "Missing resident texture: " + path);
        var image = ImageIO.read(input);
        assertNotNull(image, "Unreadable resident texture: " + path);
        assertEquals(64, image.getWidth(), path);
        assertEquals(64, image.getHeight(), path);
        assertTrue(image.getColorModel().hasAlpha(), "Resident texture must preserve overlay transparency: " + path);
      }
    }
  }
}
