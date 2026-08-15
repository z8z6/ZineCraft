package com.cxxcxx.zinecraft.core.client.weapon.tacz;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.minecraft.resources.ResourceLocation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

record TaczVector(float x, float y, float z) {
  static final TaczVector ZERO = new TaczVector(0, 0, 0);
  static final TaczVector ONE = new TaczVector(1, 1, 1);

  TaczVector add(TaczVector other) {
    return new TaczVector(x + other.x, y + other.y, z + other.z);
  }

  TaczVector subtract(TaczVector other) {
    return new TaczVector(x - other.x, y - other.y, z - other.z);
  }

  TaczVector multiply(float scale) {
    return new TaczVector(x * scale, y * scale, z * scale);
  }
}

record TaczFaceUv(float u, float v, float width, float height) {
}

record TaczCube(TaczVector origin, TaczVector size, TaczVector pivot, TaczVector rotation, float inflate,
                Map<String, TaczFaceUv> faces) {
}

record TaczBone(String name, String parent, TaczVector pivot, TaczVector rotation, List<TaczCube> cubes) {
}

record TaczBedrockModel(float textureWidth, float textureHeight, List<TaczBone> bones) {
}

record TaczBoneTransform(TaczVector position, TaczVector rotation, TaczVector scale) {
  static final TaczBoneTransform IDENTITY = new TaczBoneTransform(TaczVector.ZERO, TaczVector.ZERO, TaczVector.ONE);
}

record TaczAnimationClip(float length, boolean loop, Map<String, TaczAnimatedBone> bones,
                         List<TaczAnimationSound> sounds) {
  Map<String, TaczBoneTransform> sample(float timeSeconds) {
    float time = loop && length > 0 ? timeSeconds % length : Math.min(timeSeconds, length);
    Map<String, TaczBoneTransform> result = new LinkedHashMap<>();
    bones.forEach((name, bone) -> result.put(name, new TaczBoneTransform(
        bone.position().sample(time, TaczVector.ZERO), bone.rotation().sample(time, TaczVector.ZERO),
        bone.scale().sample(time, TaczVector.ONE))));
    return result;
  }
}

record TaczAnimatedBone(TaczKeyframes position, TaczKeyframes rotation, TaczKeyframes scale) {
}

record TaczAnimationSound(float time, ResourceLocation id) {
}

record TaczKeyframe(float time, TaczVector value, boolean catmullRom) {
}

record TaczKeyframes(List<TaczKeyframe> values) {
  private static TaczVector catmull(TaczVector p0, TaczVector p1, TaczVector p2, TaczVector p3, float t) {
    float t2 = t * t;
    float t3 = t2 * t;
    return p1.multiply(2).add(p2.subtract(p0).multiply(t))
        .add(p0.multiply(2).subtract(p1.multiply(5)).add(p2.multiply(4)).subtract(p3).multiply(t2))
        .add(p3.subtract(p0).add(p1.subtract(p2).multiply(3)).multiply(t3)).multiply(0.5f);
  }

  TaczVector sample(float time, TaczVector fallback) {
    if (values.isEmpty()) return fallback;
    if (values.size() == 1 || time <= values.getFirst().time()) return values.getFirst().value();
    if (time >= values.getLast().time()) return values.getLast().value();
    int upper = 1;
    while (upper < values.size() && values.get(upper).time() < time) upper++;
    TaczKeyframe start = values.get(upper - 1);
    TaczKeyframe end = values.get(upper);
    float alpha = Math.clamp((time - start.time()) / (end.time() - start.time()), 0, 1);
    if (!end.catmullRom()) return start.value().multiply(1 - alpha).add(end.value().multiply(alpha));
    TaczVector before = values.get(Math.max(0, upper - 2)).value();
    TaczVector after = values.get(Math.min(values.size() - 1, upper + 1)).value();
    return catmull(before, start.value(), end.value(), after, alpha);
  }
}

final class TaczBedrockParser {
  private TaczBedrockParser() {
  }

  static TaczBedrockModel model(InputStream input) {
    JsonObject root = json(input);
    var geometries = root.getAsJsonArray("minecraft:geometry");
    if (geometries == null || geometries.isEmpty()) throw new IllegalStateException("Missing minecraft:geometry");
    JsonObject geometry = geometries.get(0).getAsJsonObject();
    JsonObject description = geometry.getAsJsonObject("description");
    List<TaczBone> bones = new ArrayList<>();
    var boneArray = geometry.getAsJsonArray("bones");
    if (boneArray != null) for (JsonElement element : boneArray) {
      JsonObject bone = element.getAsJsonObject();
      TaczVector pivot = vector(bone, "pivot", TaczVector.ZERO);
      List<TaczCube> cubes = new ArrayList<>();
      var cubeArray = bone.getAsJsonArray("cubes");
      if (cubeArray != null) for (JsonElement cube : cubeArray) cubes.add(cube(cube.getAsJsonObject(), pivot));
      bones.add(new TaczBone(bone.get("name").getAsString(), string(bone, "parent"), pivot,
          vector(bone, "rotation", TaczVector.ZERO), List.copyOf(cubes)));
    }
    return new TaczBedrockModel(decimal(description, "texture_width", 16),
        decimal(description, "texture_height", 16), List.copyOf(bones));
  }

  static Map<String, TaczAnimationClip> animations(InputStream input) {
    JsonObject animations = json(input).getAsJsonObject("animations");
    if (animations == null) return Map.of();
    Map<String, TaczAnimationClip> result = new LinkedHashMap<>();
    animations.entrySet().forEach(entry -> {
      JsonObject clip = entry.getValue().getAsJsonObject();
      JsonElement loopValue = clip.get("loop");
      // Bedrock's "hold_on_last_frame" is a one-shot clip that clamps at its end, not a loop.
      boolean loop = loopValue != null && loopValue.isJsonPrimitive()
          && loopValue.getAsJsonPrimitive().isBoolean() && loopValue.getAsBoolean();
      Map<String, TaczAnimatedBone> animatedBones = new LinkedHashMap<>();
      JsonObject bones = clip.getAsJsonObject("bones");
      if (bones != null) bones.entrySet().forEach(boneEntry -> {
        JsonObject bone = boneEntry.getValue().getAsJsonObject();
        animatedBones.put(boneEntry.getKey(), new TaczAnimatedBone(
            keyframes(bone.get("position"), TaczVector.ZERO),
            keyframes(bone.get("rotation"), TaczVector.ZERO),
            keyframes(bone.get("scale"), TaczVector.ONE)));
      });
      List<TaczAnimationSound> sounds = new ArrayList<>();
      JsonObject soundEffects = clip.getAsJsonObject("sound_effects");
      if (soundEffects != null) soundEffects.entrySet().forEach(sound -> {
        try {
          if (!sound.getValue().isJsonObject()) return;
          ResourceLocation id = ResourceLocation.tryParse(string(sound.getValue().getAsJsonObject(), "effect"));
          if (id != null) sounds.add(new TaczAnimationSound(Float.parseFloat(sound.getKey()), id));
        } catch (RuntimeException ignored) {
        }
      });
      sounds.sort(Comparator.comparing(TaczAnimationSound::time));
      result.put(entry.getKey(), new TaczAnimationClip(decimal(clip, "animation_length", 0), loop,
          Map.copyOf(animatedBones), List.copyOf(sounds)));
    });
    return Map.copyOf(result);
  }

  private static TaczCube cube(JsonObject json, TaczVector bonePivot) {
    TaczVector size = vector(json, "size", TaczVector.ZERO);
    JsonElement uv = json.get("uv");
    Map<String, TaczFaceUv> faces = new LinkedHashMap<>();
    if (uv != null && uv.isJsonObject()) uv.getAsJsonObject().entrySet().forEach(entry -> {
      if (!entry.getValue().isJsonObject()) return;
      JsonObject data = entry.getValue().getAsJsonObject();
      float[] origin = vector2(data, "uv");
      float[] extent = vector2(data, "uv_size");
      if (origin != null && extent != null)
        faces.put(entry.getKey(), new TaczFaceUv(origin[0], origin[1], extent[0], extent[1]));
    });
    else if (uv != null && uv.isJsonArray() && uv.getAsJsonArray().size() >= 2) {
      faces.putAll(boxUv(uv.getAsJsonArray().get(0).getAsFloat(), uv.getAsJsonArray().get(1).getAsFloat(), size));
    }
    return new TaczCube(vector(json, "origin", TaczVector.ZERO), size, vector(json, "pivot", bonePivot),
        vector(json, "rotation", TaczVector.ZERO), decimal(json, "inflate", 0), Map.copyOf(faces));
  }

  private static Map<String, TaczFaceUv> boxUv(float u, float v, TaczVector size) {
    return Map.of("west", new TaczFaceUv(u, v + size.z(), size.z(), size.y()),
        "north", new TaczFaceUv(u + size.z(), v + size.z(), size.x(), size.y()),
        "east", new TaczFaceUv(u + size.z() + size.x(), v + size.z(), size.z(), size.y()),
        "south", new TaczFaceUv(u + size.z() * 2 + size.x(), v + size.z(), size.x(), size.y()),
        "up", new TaczFaceUv(u + size.z(), v, size.x(), size.z()),
        "down", new TaczFaceUv(u + size.z() + size.x(), v, size.x(), size.z()));
  }

  private static TaczKeyframes keyframes(JsonElement element, TaczVector fallback) {
    if (element == null) return new TaczKeyframes(List.of());
    if (element.isJsonArray()) return new TaczKeyframes(List.of(new TaczKeyframe(0, vector(element, fallback), false)));
    if (!element.isJsonObject()) return new TaczKeyframes(List.of());
    List<TaczKeyframe> values = new ArrayList<>();
    element.getAsJsonObject().entrySet().forEach(entry -> {
      try {
        float timestamp = Float.parseFloat(entry.getKey());
        JsonElement value = entry.getValue();
        JsonElement vector = value.isJsonObject()
            ? value.getAsJsonObject().has("post") ? value.getAsJsonObject().get("post") : value.getAsJsonObject().get("pre")
            : value;
        String mode = value.isJsonObject() ? string(value.getAsJsonObject(), "lerp_mode") : null;
        values.add(new TaczKeyframe(timestamp, vector(vector, fallback), "catmullrom".equals(mode)));
      } catch (RuntimeException ignored) {
      }
    });
    values.sort(Comparator.comparing(TaczKeyframe::time));
    return new TaczKeyframes(List.copyOf(values));
  }

  private static JsonObject json(InputStream input) {
    JsonReader reader = new JsonReader(new InputStreamReader(input, StandardCharsets.UTF_8));
    reader.setLenient(true);
    return JsonParser.parseReader(reader).getAsJsonObject();
  }

  private static TaczVector vector(JsonObject json, String key, TaczVector fallback) {
    return json == null ? fallback : vector(json.get(key), fallback);
  }

  private static TaczVector vector(JsonElement element, TaczVector fallback) {
    if (element == null || !element.isJsonArray() || element.getAsJsonArray().size() < 3) return fallback;
    var array = element.getAsJsonArray();
    return new TaczVector(array.get(0).getAsFloat(), array.get(1).getAsFloat(), array.get(2).getAsFloat());
  }

  private static float[] vector2(JsonObject json, String key) {
    var array = json.getAsJsonArray(key);
    return array == null || array.size() < 2 ? null : new float[]{array.get(0).getAsFloat(), array.get(1).getAsFloat()};
  }

  private static String string(JsonObject json, String key) {
    if (json == null) return null;
    JsonElement value = json.get(key);
    return value != null && value.isJsonPrimitive() ? value.getAsString() : null;
  }

  private static float decimal(JsonObject json, String key, float fallback) {
    if (json == null) return fallback;
    JsonElement value = json.get(key);
    return value != null && value.isJsonPrimitive() ? value.getAsFloat() : fallback;
  }
}
