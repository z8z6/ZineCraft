package com.cxxcxx.zinecraft.api.weapon.tacz

import com.cxxcxx.zinecraft.core.Zinecraft
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resources.ResourceLocation
import java.io.FilterInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipFile
import kotlin.io.path.extension
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.name
import kotlin.math.ceil
import kotlin.math.max

data class TaczPackInfo(
  val sourceName: String,
  val namespace: String,
  val version: String?,
  val displayName: String?,
  val license: String?,
  val authors: List<String>,
  val url: String?
)

data class TaczGunAssets(
  val modelPath: String?,
  val texturePath: String?,
  val slotTexturePath: String?,
  val animationPath: String?,
  val defaultAnimationPath: String?,
  val stateMachinePath: String?,
  val stateMachineParameters: JsonObject,
  val playerAnimationPath: String?,
  val playerAnimationId: ResourceLocation?,
  val thirdPersonAnimation: String,
  val fixedThirdPersonHand: Boolean,
  val sounds: Map<String, TaczSoundAsset>
)

data class TaczSoundAsset(
  val sourcePath: String,
  val runtimeId: ResourceLocation
)

data class TaczGunSpec(
  val id: ResourceLocation,
  val runtimeId: ResourceLocation,
  val translationKey: String,
  val tooltipKey: String?,
  val type: String,
  val sort: Int,
  val ammoId: ResourceLocation,
  val capacity: Int,
  val bolt: String,
  val rpm: Int,
  val burstCount: Int,
  val burstRpm: Int,
  val damage: Float,
  val projectileCount: Int,
  val range: Double,
  val reloadFeedTicks: Int,
  val reloadDurationTicks: Int,
  val drawTicks: Int,
  val aimTicks: Int,
  val putAwayTicks: Int,
  val boltActionTicks: Int,
  val feedType: String,
  val meleeDamage: Float,
  val meleeDistance: Double,
  val meleeCooldownTicks: Int,
  val fireModes: List<String>,
  val assets: TaczGunAssets,
  val pack: TaczPackInfo
)

data class TaczAmmoSpec(
  val id: ResourceLocation,
  val translationKey: String,
  val stackSize: Int,
  val sort: Int,
  val modelPath: String?,
  val texturePath: String?,
  val slotTexturePath: String?
)

class TaczCatalogSnapshot internal constructor(
  val packs: List<TaczPackInfo>,
  val guns: Map<ResourceLocation, TaczGunSpec>,
  val ammunition: Map<ResourceLocation, TaczAmmoSpec>,
  internal val resources: TaczLayeredResources
) {
  fun open(path: String): InputStream? = resources.open(path)
  fun paths(): Sequence<String> = resources.paths()
}

/**
 * Reads unmodified TaCZ 1.1.x gun packs from `<gameDir>/tacz`.
 *
 * TaCZ packs are deliberately kept external: this adapter only parses them at runtime and never
 * places third-party assets in the Zinecraft jar.
 */
object TaczGunPacks {
  val directory: Path
    get() = FabricLoader.getInstance().gameDir.resolve("tacz")

  @Volatile
  var snapshot: TaczCatalogSnapshot =
    TaczCatalogSnapshot(emptyList(), emptyMap(), emptyMap(), TaczLayeredResources(emptyList()))
    private set

  fun reload(): TaczCatalogSnapshot {
    Files.createDirectories(directory)
    val loaded = TaczGunPackLoader.load(directory)
    snapshot = loaded
    Zinecraft.logger.info(
      "Loaded {} TaCZ gun pack(s), {} gun(s), and {} ammunition type(s) from {}",
      loaded.packs.size,
      loaded.guns.size,
      loaded.ammunition.size,
      directory
    )
    return loaded
  }

  fun gun(id: ResourceLocation): TaczGunSpec? = snapshot.guns[id]
  fun ammo(id: ResourceLocation): TaczAmmoSpec? = snapshot.ammunition[id]
}

internal object TaczGunPackLoader {
  private val gunIndexPattern = Regex("^data/([^/]+)/index/guns/(.+)\\.json$")
  private val ammoIndexPattern = Regex("^data/([^/]+)/index/ammo/(.+)\\.json$")

  fun load(directory: Path): TaczCatalogSnapshot {
    val sources = Files.list(directory).use { stream ->
      stream.filter { candidate ->
        (candidate.isDirectory() || (candidate.isRegularFile() && candidate.extension.equals("zip", true))) &&
            hasManifest(candidate)
      }.sorted().map { candidate -> TaczPackSource(candidate) }.toList()
    }
    val resources = TaczLayeredResources(sources)
    val packs = sources.mapNotNull { source -> loadPackInfo(source, resources) }
    val packByNamespace = packs.associateBy { it.namespace }
    val guns = linkedMapOf<ResourceLocation, TaczGunSpec>()
    val ammo = linkedMapOf<ResourceLocation, TaczAmmoSpec>()

    resources.paths().forEach { path ->
      gunIndexPattern.matchEntire(path)?.let { match ->
        val id = id(match.groupValues[1], match.groupValues[2]) ?: return@let
        runCatching { loadGun(id, readObject(resources, path), resources, packByNamespace) }
          .onSuccess { guns[id] = it }
          .onFailure { Zinecraft.logger.warn("Skipping invalid TaCZ gun index {}", id, it) }
      }
      ammoIndexPattern.matchEntire(path)?.let { match ->
        val id = id(match.groupValues[1], match.groupValues[2]) ?: return@let
        runCatching { loadAmmo(id, readObject(resources, path), resources) }
          .onSuccess { ammo[id] = it }
          .onFailure { Zinecraft.logger.warn("Skipping invalid TaCZ ammunition index {}", id, it) }
      }
    }
    return TaczCatalogSnapshot(packs, guns, ammo, resources)
  }

  private fun hasManifest(path: Path): Boolean = if (path.isDirectory()) {
    path.resolve("gunpack.meta.json").isRegularFile()
  } else {
    runCatching { ZipFile(path.toFile()).use { it.getEntry("gunpack.meta.json") != null } }.getOrDefault(false)
  }

  private fun loadPackInfo(source: TaczPackSource, resources: TaczLayeredResources): TaczPackInfo? {
    val meta = source.open("gunpack.meta.json")?.use(::readObject) ?: return null
    val namespace = meta.string("namespace") ?: return null
    val info = source.open("assets/$namespace/gunpack_info.json")?.use(::readObject)
    return TaczPackInfo(
      sourceName = source.name,
      namespace = namespace,
      version = info?.string("version"),
      displayName = info?.string("name"),
      license = info?.string("license"),
      authors = info?.getAsJsonArray("authors")?.mapNotNull { it.takeIf { value -> value.isJsonPrimitive }?.asString }
        ?: emptyList(),
      url = info?.string("url")
    )
  }

  private fun loadGun(
    id: ResourceLocation,
    index: JsonObject,
    resources: TaczLayeredResources,
    packs: Map<String, TaczPackInfo>
  ): TaczGunSpec {
    val dataId = requiredId(index, "data")
    val displayId = requiredId(index, "display")
    val data = readObject(resources, "data/${dataId.namespace}/data/guns/${dataId.path}.json")
    val display = readObject(resources, "assets/${displayId.namespace}/display/guns/${displayId.path}.json")
    val bullet = data.objectValue("bullet")
    val reload = data.objectValue("reload")
    val burst = data.objectValue("burst_data")
    val feed = reload?.objectValue("feed")
    val cooldown = reload?.objectValue("cooldown")
    val model = display.id("model")
    val texture = display.id("texture")
    val slot = display.id("slot")
    val animation = display.id("animation")
    val defaultAnimation = display.id("default_animation") ?: when (display.string("use_default_animation")) {
      "rifle" -> ResourceLocation.fromNamespaceAndPath("tacz", "rifle_default")
      "pistol" -> ResourceLocation.fromNamespaceAndPath("tacz", "pistol_default")
      else -> null
    }
    val stateMachine =
      display.id("state_machine") ?: ResourceLocation.fromNamespaceAndPath("tacz", "default_state_machine")
    val playerAnimation = display.id("player_animator_3rd")
    val melee = data.objectValue("melee")?.objectValue("default")
    val sounds = display.objectValue("sounds")?.entrySet()?.mapNotNull { (cue, value) ->
      if (!value.isJsonPrimitive || !value.asJsonPrimitive.isString) return@mapNotNull null
      val soundId = ResourceLocation.tryParse(value.asString) ?: return@mapNotNull null
      val runtimeId = ResourceLocation.fromNamespaceAndPath(
        Zinecraft.MOD_ID,
        "tacz/${soundId.namespace}/${soundId.path}"
      )
      cue to TaczSoundAsset("assets/${soundId.namespace}/tacz_sounds/${soundId.path}.ogg", runtimeId)
    }?.toMap() ?: emptyMap()
    val capacity = data.int("ammo_amount", 1).coerceIn(1, 4096)
    val rpm = data.int("rpm", 60).coerceIn(1, 2400)
    val life = bullet?.double("life", 1.0) ?: 1.0
    val speed = bullet?.double("speed", 64.0) ?: 64.0
    val namespacePack =
      packs[id.namespace] ?: TaczPackInfo(id.namespace, id.namespace, null, null, null, emptyList(), null)

    return TaczGunSpec(
      id = id,
      runtimeId = ResourceLocation.fromNamespaceAndPath(Zinecraft.MOD_ID, "tacz/${id.namespace}/${id.path}"),
      translationKey = index.string("name") ?: "tacz.gun.${id.path.replace('/', '.')}.name",
      tooltipKey = index.string("tooltip"),
      type = index.string("type") ?: "unknown",
      sort = index.int("sort", 0),
      ammoId = requiredId(data, "ammo"),
      capacity = capacity,
      bolt = data.string("bolt") ?: "open_bolt",
      rpm = rpm,
      burstCount = (burst?.int("count", 3) ?: 3).coerceIn(1, 16),
      burstRpm = (burst?.int("bpm", rpm) ?: rpm).coerceIn(1, 2400),
      damage = max(0.1f, (bullet?.double("damage", 1.0) ?: 1.0).toFloat()),
      projectileCount = (bullet?.int("bullet_amount", 1) ?: 1).coerceIn(1, 64),
      range = (life * speed).coerceIn(1.0, 512.0),
      reloadFeedTicks = secondsToTicks(feed?.double("empty", feed.doubleOr("tactical", 1.0))),
      reloadDurationTicks = secondsToTicks(feed?.double("empty", feed.doubleOr("tactical", 1.0))) +
          secondsToTicks(cooldown?.double("empty", cooldown.doubleOr("tactical", 1.0))),
      drawTicks = secondsToTicks(data.double("draw_time", 0.25)),
      aimTicks = secondsToTicks(data.double("aim_time", 0.2)),
      putAwayTicks = secondsToTicks(data.double("put_away_time", 0.4)),
      boltActionTicks = secondsToTicks(data.double("bolt_action_time", 0.0)),
      feedType = reload?.string("type") ?: "magazine",
      meleeDamage = (melee?.double("damage", 3.0) ?: 3.0).toFloat().coerceAtLeast(0f),
      meleeDistance = (melee?.double("distance", 1.5) ?: 1.5).coerceIn(0.1, 8.0),
      meleeCooldownTicks = secondsToTicks(melee?.double("cooldown", 0.5)),
      fireModes = data.getAsJsonArray("fire_mode")
        ?.mapNotNull { it.takeIf { value -> value.isJsonPrimitive }?.asString } ?: listOf("semi"),
      assets = TaczGunAssets(
        modelPath = model?.let { "assets/${it.namespace}/geo_models/${it.path}.json" },
        texturePath = texture?.let { "assets/${it.namespace}/textures/${it.path}.png" },
        slotTexturePath = slot?.let { "assets/${it.namespace}/textures/${it.path}.png" },
        animationPath = animation?.let { "assets/${it.namespace}/animations/${it.path}.animation.json" },
        defaultAnimationPath = defaultAnimation?.let { "assets/${it.namespace}/animations/${it.path}.animation.json" },
        stateMachinePath = stateMachine.let { "assets/${it.namespace}/scripts/${it.path}.lua" },
        stateMachineParameters = display.objectValue("state_machine_param")?.deepCopy() ?: JsonObject(),
        playerAnimationPath = playerAnimation?.let { "assets/${it.namespace}/player_animator/${it.path}.json" },
        playerAnimationId = playerAnimation,
        thirdPersonAnimation = display.string("third_person_animation") ?: "default",
        fixedThirdPersonHand = display.boolean("3rd_fixed_hand", false),
        sounds = sounds
      ),
      pack = namespacePack
    )
  }

  private fun loadAmmo(id: ResourceLocation, index: JsonObject, resources: TaczLayeredResources): TaczAmmoSpec {
    val displayId = requiredId(index, "display")
    val display = readObject(resources, "assets/${displayId.namespace}/display/ammo/${displayId.path}.json")
    val model = display.id("model")
    val texture = display.id("texture")
    val slot = display.id("slot")
    return TaczAmmoSpec(
      id = id,
      translationKey = index.string("name") ?: "tacz.ammo.${id.path.replace('/', '.')}.name",
      stackSize = index.int("stack_size", 64).coerceIn(1, 99),
      sort = index.int("sort", 0),
      modelPath = model?.let { "assets/${it.namespace}/geo_models/${it.path}.json" },
      texturePath = texture?.let { "assets/${it.namespace}/textures/${it.path}.png" },
      slotTexturePath = slot?.let { "assets/${it.namespace}/textures/${it.path}.png" }
    )
  }

  private fun secondsToTicks(seconds: Double?): Int = max(1, ceil((seconds ?: 0.05) * 20.0).toInt())

  private fun requiredId(json: JsonObject, key: String): ResourceLocation =
    json.id(key) ?: error("Missing or invalid resource location '$key'")

  private fun readObject(resources: TaczLayeredResources, path: String): JsonObject =
    resources.open(path)?.use(::readObject) ?: error("Missing TaCZ resource: $path")

  private fun readObject(input: InputStream): JsonObject =
    InputStreamReader(input, StandardCharsets.UTF_8).use { reader ->
      val jsonReader = JsonReader(reader).apply { isLenient = true }
      JsonParser.parseReader(jsonReader).asJsonObject
    }

  private fun id(namespace: String, path: String): ResourceLocation? =
    runCatching { ResourceLocation.fromNamespaceAndPath(namespace, path) }.getOrNull()

  private fun JsonObject.string(key: String): String? = get(key)?.takeIf { it.isJsonPrimitive }?.asString
  private fun JsonObject.int(key: String, fallback: Int): Int =
    get(key)?.takeIf { it.isJsonPrimitive }?.asInt ?: fallback

  private fun JsonObject.double(key: String, fallback: Double): Double =
    get(key)?.takeIf { it.isJsonPrimitive }?.asDouble ?: fallback

  private fun JsonObject.boolean(key: String, fallback: Boolean): Boolean =
    get(key)?.takeIf { it.isJsonPrimitive }?.asBoolean ?: fallback

  private fun JsonObject.objectValue(key: String): JsonObject? = get(key)?.takeIf { it.isJsonObject }?.asJsonObject
  private fun JsonObject.id(key: String): ResourceLocation? = string(key)?.let(ResourceLocation::tryParse)
  private fun JsonObject?.doubleOr(key: String, fallback: Double): Double = this?.double(key, fallback) ?: fallback
}

internal class TaczLayeredResources(private val sources: List<TaczPackSource>) {
  fun open(path: String): InputStream? {
    val normalized = normalize(path) ?: return null
    return sources.asReversed().firstNotNullOfOrNull { it.open(normalized) }
  }

  fun paths(): Sequence<String> {
    val merged = linkedSetOf<String>()
    sources.forEach { source -> source.paths().forEach(merged::add) }
    return merged.asSequence()
  }
}

internal sealed interface TaczPackSource {
  val name: String
  fun open(path: String): InputStream?
  fun paths(): Sequence<String>

  companion object {
    operator fun invoke(path: Path): TaczPackSource = if (path.isDirectory()) Directory(path) else Zip(path)
  }

  class Directory(private val root: Path) : TaczPackSource {
    override val name: String = root.name

    override fun open(path: String): InputStream? {
      val normalized = normalize(path) ?: return null
      val target = root.resolve(normalized).normalize()
      if (!target.startsWith(root.normalize()) || !target.isRegularFile()) return null
      return Files.newInputStream(target)
    }

    override fun paths(): Sequence<String> = Files.walk(root).use { stream ->
      stream.filter(Files::isRegularFile)
        .map { root.relativize(it).toString().replace('\\', '/') }
        .toList()
        .asSequence()
    }
  }

  class Zip(private val file: Path) : TaczPackSource {
    override val name: String = file.name

    override fun open(path: String): InputStream? {
      val normalized = normalize(path) ?: return null
      val zip = runCatching { ZipFile(file.toFile()) }.getOrNull() ?: return null
      val entry = zip.getEntry(normalized)
      if (entry == null || entry.isDirectory) {
        zip.close()
        return null
      }
      return object : FilterInputStream(zip.getInputStream(entry)) {
        override fun close() {
          super.close()
          zip.close()
        }
      }
    }

    override fun paths(): Sequence<String> = ZipFile(file.toFile()).use { zip ->
      zip.entries().asSequence().filterNot { it.isDirectory }.map { it.name }.toList().asSequence()
    }
  }
}

private fun normalize(path: String): String? {
  val value = path.replace('\\', '/').removePrefix("/")
  if (value.isBlank() || value.split('/').any { it == ".." || it.isBlank() }) return null
  return value
}
