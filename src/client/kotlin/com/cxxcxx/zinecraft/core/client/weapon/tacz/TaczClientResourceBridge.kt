package com.cxxcxx.zinecraft.core.client.weapon.tacz

import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks
import com.cxxcxx.zinecraft.core.Zinecraft
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

/** Makes TaCZ's non-vanilla language and `tacz_sounds` paths visible to Minecraft's resource manager. */
object TaczClientResourceBridge {
  private const val DIRECTORY_NAME = "zinecraft_tacz_bridge"
  private val gson = GsonBuilder().setPrettyPrinting().create()

  fun initialize() {
    ClientLifecycleEvents.CLIENT_STARTED.register { client -> installAndEnable(client) }
  }

  private fun installAndEnable(client: Minecraft) {
    val snapshot = TaczGunPacks.snapshot
    if (snapshot.packs.isEmpty()) return
    runCatching {
      val resourcePacks = FabricLoader.getInstance().gameDir.resolve("resourcepacks").normalize()
      val target = resourcePacks.resolve(DIRECTORY_NAME).normalize()
      check(target.parent == resourcePacks) { "Unsafe TaCZ bridge path: $target" }
      if (Files.exists(target)) {
        Files.walk(target).use { paths -> paths.sorted(Comparator.reverseOrder()).forEach(Files::delete) }
      }
      Files.createDirectories(target)
      writePackMetadata(target)
      copyLanguages(target)
      writeSounds(target)

      val repository = client.resourcePackRepository
      repository.reload()
      val id = repository.availableIds.firstOrNull { it.endsWith(DIRECTORY_NAME) }
        ?: error("Generated TaCZ resource bridge was not discovered")
      if (id !in repository.selectedIds) repository.addPack(id)
      client.reloadResourcePacks()
      Zinecraft.logger.info("Enabled generated TaCZ client resource bridge as pack {}", id)
    }.onFailure { Zinecraft.logger.error("Failed to install the TaCZ client resource bridge", it) }
  }

  private fun writePackMetadata(target: Path) {
    val root = JsonObject()
    val pack = JsonObject()
    pack.addProperty("pack_format", 34)
    pack.addProperty("description", "Runtime bridge for user-installed TaCZ gun packs")
    root.add("pack", pack)
    Files.writeString(target.resolve("pack.mcmeta"), gson.toJson(root), StandardCharsets.UTF_8)
  }

  private fun copyLanguages(target: Path) {
    val languagePattern = Regex("^assets/[^/]+/lang/[^/]+\\.json$")
    TaczGunPacks.snapshot.paths().filter(languagePattern::matches).forEach { sourcePath ->
      val destination = safeResolve(target, sourcePath)
      Files.createDirectories(destination.parent)
      TaczGunPacks.snapshot.open(sourcePath)?.use { input ->
        Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING)
      }
    }
  }

  private fun writeSounds(target: Path) {
    val definitions = JsonObject()
    val soundPattern = Regex("^assets/([^/]+)/tacz_sounds/(.+)\\.ogg$")
    TaczGunPacks.snapshot.paths().mapNotNull { path ->
      val match = soundPattern.matchEntire(path) ?: return@mapNotNull null
      val runtimePath = "tacz/${match.groupValues[1]}/${match.groupValues[2]}"
      Triple(
        path,
        runtimePath,
        net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(Zinecraft.MOD_ID, runtimePath)
      )
    }.distinctBy { it.third }.forEach { (sourcePath, runtimePath, runtimeId) ->
      val relative = "assets/${Zinecraft.MOD_ID}/sounds/$runtimePath.ogg"
      val destination = safeResolve(target, relative)
      Files.createDirectories(destination.parent)
      TaczGunPacks.snapshot.open(sourcePath)?.use { input ->
        Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING)
      } ?: return@forEach

      val entry = JsonObject()
      val variants = JsonArray()
      variants.add(JsonObject().apply { addProperty("name", runtimeId.toString()) })
      entry.add("sounds", variants)
      definitions.add(runtimePath, entry)
    }
    val soundsJson = target.resolve("assets/${Zinecraft.MOD_ID}/sounds.json")
    Files.createDirectories(soundsJson.parent)
    Files.writeString(soundsJson, gson.toJson(definitions), StandardCharsets.UTF_8)
  }

  private fun safeResolve(root: Path, relative: String): Path {
    val destination = root.resolve(relative.replace('/', java.io.File.separatorChar)).normalize()
    check(destination.startsWith(root)) { "Unsafe TaCZ resource path: $relative" }
    return destination
  }
}
