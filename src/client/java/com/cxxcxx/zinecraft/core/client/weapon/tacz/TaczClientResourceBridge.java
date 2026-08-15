package com.cxxcxx.zinecraft.core.client.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.HashSet;
import java.util.regex.Pattern;

@EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT)
public final class TaczClientResourceBridge {
  public static final TaczClientResourceBridge INSTANCE = new TaczClientResourceBridge();
  private static final String DIRECTORY_NAME = "zinecraft_tacz_bridge";
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  private static final Pattern LANGUAGE = Pattern.compile("^assets/[^/]+/lang/[^/]+\\.json$");
  private static final Pattern SOUND = Pattern.compile("^assets/([^/]+)/tacz_sounds/(.+)\\.ogg$");
  private static boolean pending;

  private TaczClientResourceBridge() {
  }

  @SubscribeEvent
  public static void firstTick(ClientTickEvent.Post event) {
    if (!pending) return;
    pending = false;
    INSTANCE.installAndEnable(Minecraft.getInstance());
  }

  private static void writePackMetadata(Path target) throws IOException {
    JsonObject root = new JsonObject();
    JsonObject pack = new JsonObject();
    pack.addProperty("pack_format", 34);
    pack.addProperty("description", "Runtime bridge for user-installed TaCZ gun packs");
    root.add("pack", pack);
    Files.writeString(target.resolve("pack.mcmeta"), GSON.toJson(root), StandardCharsets.UTF_8);
  }

  private static void copyLanguages(Path target) {
    TaczGunPacks.INSTANCE.getSnapshot().paths().filter(path -> LANGUAGE.matcher(path).matches()).forEach(sourcePath -> {
      try {
        Path destination = safeResolve(target, sourcePath);
        Files.createDirectories(destination.getParent());
        try (var input = TaczGunPacks.INSTANCE.getSnapshot().open(sourcePath)) {
          if (input != null) Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
        }
      } catch (IOException exception) {
        throw new IllegalStateException("Unable to copy TaCZ language " + sourcePath, exception);
      }
    });
  }

  private static void writeSounds(Path target) throws IOException {
    JsonObject definitions = new JsonObject();
    HashSet<ResourceLocation> copied = new HashSet<>();
    TaczGunPacks.INSTANCE.getSnapshot().paths().forEach(sourcePath -> {
      var match = SOUND.matcher(sourcePath);
      if (!match.matches()) return;
      String runtimePath = "tacz/" + match.group(1) + "/" + match.group(2);
      ResourceLocation runtimeId = ResourceLocation.fromNamespaceAndPath(Zinecraft.MOD_ID, runtimePath);
      if (!copied.add(runtimeId)) return;
      try {
        Path destination = safeResolve(target, "assets/" + Zinecraft.MOD_ID + "/sounds/" + runtimePath + ".ogg");
        Files.createDirectories(destination.getParent());
        try (var input = TaczGunPacks.INSTANCE.getSnapshot().open(sourcePath)) {
          if (input == null) return;
          Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
        }
        JsonObject entry = new JsonObject();
        JsonArray variants = new JsonArray();
        JsonObject variant = new JsonObject();
        variant.addProperty("name", runtimeId.toString());
        variants.add(variant);
        entry.add("sounds", variants);
        definitions.add(runtimePath, entry);
      } catch (IOException exception) {
        throw new IllegalStateException("Unable to copy TaCZ sound " + sourcePath, exception);
      }
    });
    Path soundsJson = target.resolve("assets/" + Zinecraft.MOD_ID + "/sounds.json");
    Files.createDirectories(soundsJson.getParent());
    Files.writeString(soundsJson, GSON.toJson(definitions), StandardCharsets.UTF_8);
  }

  private static Path safeResolve(Path root, String relative) {
    Path destination = root.resolve(relative.replace('/', java.io.File.separatorChar)).normalize();
    if (!destination.startsWith(root)) throw new IllegalStateException("Unsafe TaCZ resource path: " + relative);
    return destination;
  }

  public void initialize() {
    pending = true;
  }

  private void installAndEnable(Minecraft client) {
    var snapshot = TaczGunPacks.INSTANCE.getSnapshot();
    if (snapshot.getPacks().isEmpty()) return;
    try {
      Path resourcePacks = FMLPaths.GAMEDIR.get().resolve("resourcepacks").normalize();
      Path target = resourcePacks.resolve(DIRECTORY_NAME).normalize();
      if (!resourcePacks.equals(target.getParent()))
        throw new IllegalStateException("Unsafe TaCZ bridge path: " + target);
      if (Files.exists(target)) {
        try (var paths = Files.walk(target)) {
          for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) Files.delete(path);
        }
      }
      Files.createDirectories(target);
      writePackMetadata(target);
      copyLanguages(target);
      writeSounds(target);

      var repository = client.getResourcePackRepository();
      repository.reload();
      String id = repository.getAvailableIds().stream().filter(value -> value.endsWith(DIRECTORY_NAME)).findFirst()
          .orElseThrow(() -> new IllegalStateException("Generated TaCZ resource bridge was not discovered"));
      if (!repository.getSelectedIds().contains(id)) repository.addPack(id);
      client.reloadResourcePacks();
      Zinecraft.INSTANCE.getLogger().info("Enabled generated TaCZ client resource bridge as pack {}", id);
    } catch (Exception exception) {
      Zinecraft.INSTANCE.getLogger().error("Failed to install the TaCZ client resource bridge", exception);
    }
  }
}
