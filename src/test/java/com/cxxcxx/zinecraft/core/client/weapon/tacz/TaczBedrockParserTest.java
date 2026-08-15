package com.cxxcxx.zinecraft.core.client.weapon.tacz;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class TaczBedrockParserTest {
  private static TaczAnimationClip parse(String loop) {
    String json = """
        {
          "animations": {
            "test": {
              "loop": %s,
              "animation_length": 1.0,
              "bones": {
                "gun": {
                  "position": {
                    "0.0": [0, 0, 0],
                    "1.0": [10, 20, 30]
                  }
                }
              }
            }
          }
        }
        """.formatted(loop);
    return TaczBedrockParser.animations(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))).get("test");
  }

  @Test
  void interpolatesBedrockKeyframes() {
    TaczAnimationClip clip = parse("false");

    TaczVector halfway = clip.sample(0.5f).get("gun").position();

    assertEquals(5.0f, halfway.x(), 0.001f);
    assertEquals(10.0f, halfway.y(), 0.001f);
    assertEquals(15.0f, halfway.z(), 0.001f);
  }

  @Test
  void loopsOnlyBooleanLoopClips() {
    TaczAnimationClip looping = parse("true");
    TaczAnimationClip held = parse("\"hold_on_last_frame\"");

    assertTrue(looping.loop());
    assertEquals(5.0f, looping.sample(1.5f).get("gun").position().x(), 0.001f);
    assertFalse(held.loop());
    assertEquals(10.0f, held.sample(1.5f).get("gun").position().x(), 0.001f);
  }
}
