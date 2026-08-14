package com.cxxcxx.zinecraft.core.client.weapon.tacz

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import net.minecraft.resources.ResourceLocation

internal data class TaczVector(val x: Float, val y: Float, val z: Float) {
  operator fun plus(other: TaczVector) = TaczVector(x + other.x, y + other.y, z + other.z)
  operator fun minus(other: TaczVector) = TaczVector(x - other.x, y - other.y, z - other.z)
  operator fun times(scale: Float) = TaczVector(x * scale, y * scale, z * scale)

  companion object {
    val ZERO = TaczVector(0f, 0f, 0f)
    val ONE = TaczVector(1f, 1f, 1f)
  }
}

internal data class TaczFaceUv(val u: Float, val v: Float, val width: Float, val height: Float)

internal data class TaczCube(
  val origin: TaczVector,
  val size: TaczVector,
  val pivot: TaczVector,
  val rotation: TaczVector,
  val inflate: Float,
  val faces: Map<String, TaczFaceUv>
)

internal data class TaczBone(
  val name: String,
  val parent: String?,
  val pivot: TaczVector,
  val rotation: TaczVector,
  val cubes: List<TaczCube>
)

internal data class TaczBedrockModel(
  val textureWidth: Float,
  val textureHeight: Float,
  val bones: List<TaczBone>
)

internal data class TaczBoneTransform(
  val position: TaczVector = TaczVector.ZERO,
  val rotation: TaczVector = TaczVector.ZERO,
  val scale: TaczVector = TaczVector.ONE
)

internal data class TaczAnimationClip(
  val length: Float,
  val loop: Boolean,
  val bones: Map<String, TaczAnimatedBone>,
  val sounds: List<TaczAnimationSound> = emptyList()
) {
  fun sample(timeSeconds: Float): Map<String, TaczBoneTransform> {
    val time = if (loop && length > 0f) timeSeconds % length else timeSeconds.coerceAtMost(length)
    return bones.mapValues { (_, bone) ->
      TaczBoneTransform(
        bone.position.sample(time, TaczVector.ZERO),
        bone.rotation.sample(time, TaczVector.ZERO),
        bone.scale.sample(time, TaczVector.ONE)
      )
    }
  }
}

internal data class TaczAnimatedBone(
  val position: TaczKeyframes,
  val rotation: TaczKeyframes,
  val scale: TaczKeyframes
)

internal data class TaczAnimationSound(val time: Float, val id: ResourceLocation)

internal data class TaczKeyframe(val time: Float, val value: TaczVector, val catmullRom: Boolean)

internal data class TaczKeyframes(val values: List<TaczKeyframe>) {
  fun sample(time: Float, fallback: TaczVector): TaczVector {
    if (values.isEmpty()) return fallback
    if (values.size == 1 || time <= values.first().time) return values.first().value
    if (time >= values.last().time) return values.last().value
    val upper = values.indexOfFirst { it.time >= time }.coerceAtLeast(1)
    val startFrame = values[upper - 1]
    val endFrame = values[upper]
    val startTime = startFrame.time
    val endTime = endFrame.time
    val alpha = ((time - startTime) / (endTime - startTime)).coerceIn(0f, 1f)
    if (!endFrame.catmullRom) return startFrame.value * (1f - alpha) + endFrame.value * alpha
    val before = values.getOrElse(upper - 2) { startFrame }.value
    val after = values.getOrElse(upper + 1) { endFrame }.value
    return catmull(before, startFrame.value, endFrame.value, after, alpha)
  }

  private fun catmull(p0: TaczVector, p1: TaczVector, p2: TaczVector, p3: TaczVector, t: Float): TaczVector {
    val t2 = t * t
    val t3 = t2 * t
    return (p1 * 2f + (p2 - p0) * t + (p0 * 2f - p1 * 5f + p2 * 4f - p3) * t2 +
        (p3 - p0 + (p1 - p2) * 3f) * t3) * 0.5f
  }
}

internal object TaczBedrockParser {
  fun model(input: InputStream): TaczBedrockModel {
    val root = json(input)
    val geometry = root.getAsJsonArray("minecraft:geometry")?.firstOrNull()?.asJsonObject
      ?: error("Missing minecraft:geometry")
    val description = geometry.getAsJsonObject("description")
    val bones = geometry.getAsJsonArray("bones")?.map { element ->
      val bone = element.asJsonObject
      val pivot = bone.vector("pivot", TaczVector.ZERO)
      TaczBone(
        name = bone.get("name").asString,
        parent = bone.string("parent"),
        pivot = pivot,
        rotation = bone.vector("rotation", TaczVector.ZERO),
        cubes = bone.getAsJsonArray("cubes")?.map { cubeElement -> cube(cubeElement.asJsonObject, pivot) }
          ?: emptyList()
      )
    } ?: emptyList()
    return TaczBedrockModel(
      textureWidth = description?.float("texture_width", 16f) ?: 16f,
      textureHeight = description?.float("texture_height", 16f) ?: 16f,
      bones = bones
    )
  }

  fun animations(input: InputStream): Map<String, TaczAnimationClip> {
    val animations = json(input).getAsJsonObject("animations") ?: return emptyMap()
    return animations.entrySet().associate { (name, element) ->
      val clip = element.asJsonObject
      val loopValue = clip.get("loop")
      val loop =
        loopValue?.let { it.isJsonPrimitive && (it.asJsonPrimitive.isBoolean && it.asBoolean || it.asString == "hold_on_last_frame") }
          ?: false
      val animatedBones = clip.getAsJsonObject("bones")?.entrySet()?.associate { (boneName, boneValue) ->
        val bone = boneValue.asJsonObject
        boneName to TaczAnimatedBone(
          keyframes(bone.get("position"), TaczVector.ZERO),
          keyframes(bone.get("rotation"), TaczVector.ZERO),
          keyframes(bone.get("scale"), TaczVector.ONE)
        )
      } ?: emptyMap()
      val sounds = clip.getAsJsonObject("sound_effects")?.entrySet()?.mapNotNull { (time, value) ->
        val effect = value.takeIf(JsonElement::isJsonObject)?.asJsonObject?.string("effect") ?: return@mapNotNull null
        val id = ResourceLocation.tryParse(effect) ?: return@mapNotNull null
        (time.toFloatOrNull() ?: return@mapNotNull null) to id
      }?.sortedBy(Pair<Float, ResourceLocation>::first)?.map { TaczAnimationSound(it.first, it.second) } ?: emptyList()
      name to TaczAnimationClip(clip.float("animation_length", 0f), loop, animatedBones, sounds)
    }
  }

  private fun cube(json: JsonObject, bonePivot: TaczVector): TaczCube {
    val size = json.vector("size", TaczVector.ZERO)
    val uv = json.get("uv")
    return TaczCube(
      origin = json.vector("origin", TaczVector.ZERO),
      size = size,
      pivot = json.vector("pivot", bonePivot),
      rotation = json.vector("rotation", TaczVector.ZERO),
      inflate = json.float("inflate", 0f),
      faces = when {
        uv == null -> emptyMap()
        uv.isJsonObject -> uv.asJsonObject.entrySet().mapNotNull { (face, value) ->
          val data = value.takeIf(JsonElement::isJsonObject)?.asJsonObject ?: return@mapNotNull null
          val origin = data.vector2("uv") ?: return@mapNotNull null
          val extent = data.vector2("uv_size") ?: return@mapNotNull null
          face to TaczFaceUv(origin.first, origin.second, extent.first, extent.second)
        }.toMap()

        uv.isJsonArray -> boxUv(uv.asJsonArray[0].asFloat, uv.asJsonArray[1].asFloat, size)
        else -> emptyMap()
      }
    )
  }

  private fun boxUv(u: Float, v: Float, size: TaczVector): Map<String, TaczFaceUv> = mapOf(
    "west" to TaczFaceUv(u, v + size.z, size.z, size.y),
    "north" to TaczFaceUv(u + size.z, v + size.z, size.x, size.y),
    "east" to TaczFaceUv(u + size.z + size.x, v + size.z, size.z, size.y),
    "south" to TaczFaceUv(u + size.z * 2 + size.x, v + size.z, size.x, size.y),
    "up" to TaczFaceUv(u + size.z, v, size.x, size.z),
    "down" to TaczFaceUv(u + size.z + size.x, v, size.x, size.z)
  )

  private fun keyframes(element: JsonElement?, fallback: TaczVector): TaczKeyframes {
    if (element == null) return TaczKeyframes(emptyList())
    if (element.isJsonArray) return TaczKeyframes(listOf(TaczKeyframe(0f, element.vector(fallback), false)))
    if (!element.isJsonObject) return TaczKeyframes(emptyList())
    val values = element.asJsonObject.entrySet().mapNotNull { (time, value) ->
      val timestamp = time.toFloatOrNull() ?: return@mapNotNull null
      val vectorElement =
        if (value.isJsonObject) value.asJsonObject.get("post") ?: value.asJsonObject.get("pre") else value
      val mode = value.takeIf(JsonElement::isJsonObject)?.asJsonObject?.string("lerp_mode")
      TaczKeyframe(timestamp, vectorElement.vector(fallback), mode == "catmullrom")
    }.sortedBy(TaczKeyframe::time)
    return TaczKeyframes(values)
  }

  private fun json(input: InputStream): JsonObject = InputStreamReader(input, StandardCharsets.UTF_8).use { reader ->
    JsonParser.parseReader(JsonReader(reader).apply { isLenient = true }).asJsonObject
  }

  private fun JsonObject.vector(key: String, fallback: TaczVector): TaczVector = get(key)?.vector(fallback) ?: fallback
  private fun JsonElement.vector(fallback: TaczVector): TaczVector {
    if (!isJsonArray || asJsonArray.size() < 3) return fallback
    return TaczVector(asJsonArray[0].asFloat, asJsonArray[1].asFloat, asJsonArray[2].asFloat)
  }

  private fun JsonObject.vector2(key: String): Pair<Float, Float>? {
    val array = getAsJsonArray(key) ?: return null
    if (array.size() < 2) return null
    return array[0].asFloat to array[1].asFloat
  }

  private fun JsonObject.string(key: String): String? = get(key)?.takeIf { it.isJsonPrimitive }?.asString
  private fun JsonObject.float(key: String, fallback: Float): Float =
    get(key)?.takeIf { it.isJsonPrimitive }?.asFloat ?: fallback
}
