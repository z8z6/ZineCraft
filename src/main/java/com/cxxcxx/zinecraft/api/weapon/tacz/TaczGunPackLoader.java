package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

final class TaczGunPackLoader {
  private static final Pattern GUN_INDEX = Pattern.compile("^data/([^/]+)/index/guns/(.+)\\.json$");
  private static final Pattern AMMO_INDEX = Pattern.compile("^data/([^/]+)/index/ammo/(.+)\\.json$");

  private TaczGunPackLoader() {
  }

  static TaczCatalogSnapshot load(Path directory) {
    List<TaczPackSource> sources;
    try (var stream = Files.list(directory)) {
      sources = stream.filter(TaczGunPackLoader::hasManifest).sorted().map(TaczPackSource::of).toList();
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to list TaCZ packs in " + directory, exception);
    }

    TaczLayeredResources resources = new TaczLayeredResources(sources);
    List<TaczPackInfo> packs = new ArrayList<>();
    for (TaczPackSource source : sources) {
      TaczPackInfo info = loadPackInfo(source);
      if (info != null) packs.add(info);
    }
    Map<String, TaczPackInfo> packsByNamespace = new LinkedHashMap<>();
    for (TaczPackInfo pack : packs) packsByNamespace.put(pack.getNamespace(), pack);

    Map<ResourceLocation, TaczGunSpec> guns = new LinkedHashMap<>();
    Map<ResourceLocation, TaczAmmoSpec> ammunition = new LinkedHashMap<>();
    resources.paths().forEach(path -> {
      Matcher gun = GUN_INDEX.matcher(path);
      if (gun.matches()) {
        ResourceLocation id = id(gun.group(1), gun.group(2));
        if (id != null) tryLoad(id, () -> loadGun(id, readObject(resources, path), resources, packsByNamespace),
            spec -> guns.put(id, spec), "gun");
      }
      Matcher ammo = AMMO_INDEX.matcher(path);
      if (ammo.matches()) {
        ResourceLocation id = id(ammo.group(1), ammo.group(2));
        if (id != null) tryLoad(id, () -> loadAmmo(id, readObject(resources, path), resources),
            spec -> ammunition.put(id, spec), "ammunition");
      }
    });
    return new TaczCatalogSnapshot(packs, guns, ammunition, resources);
  }

  private static boolean hasManifest(Path path) {
    if (Files.isDirectory(path)) return Files.isRegularFile(path.resolve("gunpack.meta.json"));
    if (!Files.isRegularFile(path) || !extension(path).equalsIgnoreCase("zip")) return false;
    try (ZipFile zip = new ZipFile(path.toFile())) {
      return zip.getEntry("gunpack.meta.json") != null;
    } catch (IOException ignored) {
      return false;
    }
  }

  private static String extension(Path path) {
    String name = path.getFileName().toString();
    int separator = name.lastIndexOf('.');
    return separator < 0 ? "" : name.substring(separator + 1);
  }

  private static TaczPackInfo loadPackInfo(TaczPackSource source) {
    try (InputStream input = source.open("gunpack.meta.json")) {
      if (input == null) return null;
      JsonObject meta = readObject(input);
      String namespace = string(meta, "namespace");
      if (namespace == null) return null;
      JsonObject info = null;
      try (InputStream details = source.open("assets/" + namespace + "/gunpack_info.json")) {
        if (details != null) info = readObject(details);
      }
      List<String> authors = new ArrayList<>();
      if (info != null && info.has("authors") && info.get("authors").isJsonArray()) {
        for (JsonElement author : info.getAsJsonArray("authors")) {
          if (author.isJsonPrimitive()) authors.add(author.getAsString());
        }
      }
      return new TaczPackInfo(source.name(), namespace, string(info, "version"), string(info, "name"),
          string(info, "license"), authors, string(info, "url"));
    } catch (Exception exception) {
      Zinecraft.INSTANCE.getLogger().warn("Skipping invalid TaCZ pack {}", source.name(), exception);
      return null;
    }
  }

  private static TaczGunSpec loadGun(ResourceLocation id, JsonObject index, TaczLayeredResources resources,
                                     Map<String, TaczPackInfo> packs) {
    ResourceLocation dataId = requiredId(index, "data");
    ResourceLocation displayId = requiredId(index, "display");
    JsonObject data = readObject(resources, "data/" + dataId.getNamespace() + "/data/guns/" + dataId.getPath() + ".json");
    JsonObject display = readObject(resources, "assets/" + displayId.getNamespace() + "/display/guns/" + displayId.getPath() + ".json");
    JsonObject bullet = object(data, "bullet");
    JsonObject reload = object(data, "reload");
    JsonObject burst = object(data, "burst_data");
    JsonObject feed = object(reload, "feed");
    JsonObject cooldown = object(reload, "cooldown");
    JsonObject melee = object(object(data, "melee"), "default");
    ResourceLocation model = resource(display, "model");
    ResourceLocation texture = resource(display, "texture");
    ResourceLocation slot = resource(display, "slot");
    ResourceLocation animation = resource(display, "animation");
    ResourceLocation defaultAnimation = resource(display, "default_animation");
    if (defaultAnimation == null) {
      String useDefault = string(display, "use_default_animation");
      if ("rifle".equals(useDefault)) defaultAnimation = ResourceLocation.fromNamespaceAndPath("tacz", "rifle_default");
      else if ("pistol".equals(useDefault))
        defaultAnimation = ResourceLocation.fromNamespaceAndPath("tacz", "pistol_default");
    }
    ResourceLocation stateMachine = resource(display, "state_machine");
    if (stateMachine == null) stateMachine = ResourceLocation.fromNamespaceAndPath("tacz", "default_state_machine");
    ResourceLocation playerAnimation = resource(display, "player_animator_3rd");

    Map<String, TaczSoundAsset> sounds = new LinkedHashMap<>();
    JsonObject soundObject = object(display, "sounds");
    if (soundObject != null) soundObject.entrySet().forEach(entry -> {
      if (!entry.getValue().isJsonPrimitive() || !entry.getValue().getAsJsonPrimitive().isString()) return;
      ResourceLocation soundId = ResourceLocation.tryParse(entry.getValue().getAsString());
      if (soundId == null) return;
      ResourceLocation runtimeId = ResourceLocation.fromNamespaceAndPath(Zinecraft.MOD_ID,
          "tacz/" + soundId.getNamespace() + "/" + soundId.getPath());
      sounds.put(entry.getKey(), new TaczSoundAsset(
          "assets/" + soundId.getNamespace() + "/tacz_sounds/" + soundId.getPath() + ".ogg", runtimeId));
    });

    int capacity = clamp(integer(data, "ammo_amount", 1), 1, 4096);
    int rpm = clamp(integer(data, "rpm", 60), 1, 2400);
    double life = decimal(bullet, "life", 1.0);
    double speed = decimal(bullet, "speed", 64.0);
    double feedSeconds = decimal(feed, "empty", decimal(feed, "tactical", 1.0));
    double cooldownSeconds = decimal(cooldown, "empty", decimal(cooldown, "tactical", 1.0));
    TaczPackInfo pack = packs.getOrDefault(id.getNamespace(),
        new TaczPackInfo(id.getNamespace(), id.getNamespace(), null, null, null, List.of(), null));
    List<String> fireModes = new ArrayList<>();
    if (data.has("fire_mode") && data.get("fire_mode").isJsonArray()) {
      for (JsonElement mode : data.getAsJsonArray("fire_mode"))
        if (mode.isJsonPrimitive()) fireModes.add(mode.getAsString());
    }
    if (fireModes.isEmpty()) fireModes = List.of("semi");

    return new TaczGunSpec(id,
        ResourceLocation.fromNamespaceAndPath(Zinecraft.MOD_ID, "tacz/" + id.getNamespace() + "/" + id.getPath()),
        valueOr(string(index, "name"), "tacz.gun." + id.getPath().replace('/', '.') + ".name"),
        string(index, "tooltip"), valueOr(string(index, "type"), "unknown"), integer(index, "sort", 0),
        requiredId(data, "ammo"), capacity, valueOr(string(data, "bolt"), "open_bolt"), rpm,
        clamp(integer(burst, "count", 3), 1, 16), clamp(integer(burst, "bpm", rpm), 1, 2400),
        Math.max(0.1f, (float) decimal(bullet, "damage", 1.0)), clamp(integer(bullet, "bullet_amount", 1), 1, 64),
        clamp(life * speed, 1.0, 512.0), secondsToTicks(feedSeconds),
        secondsToTicks(feedSeconds) + secondsToTicks(cooldownSeconds), secondsToTicks(decimal(data, "draw_time", 0.25)),
        secondsToTicks(decimal(data, "aim_time", 0.2)), secondsToTicks(decimal(data, "put_away_time", 0.4)),
        secondsToTicks(decimal(data, "bolt_action_time", 0.0)), valueOr(string(reload, "type"), "magazine"),
        Math.max(0.0f, (float) decimal(melee, "damage", 3.0)), clamp(decimal(melee, "distance", 1.5), 0.1, 8.0),
        secondsToTicks(decimal(melee, "cooldown", 0.5)), fireModes,
        new TaczGunAssets(asset(model, "geo_models/", ".json"), asset(texture, "textures/", ".png"),
            asset(slot, "textures/", ".png"), asset(animation, "animations/", ".animation.json"),
            asset(defaultAnimation, "animations/", ".animation.json"), asset(stateMachine, "scripts/", ".lua"),
            object(display, "state_machine_param") == null ? new JsonObject() : object(display, "state_machine_param").deepCopy(),
            asset(playerAnimation, "player_animator/", ".json"), playerAnimation,
            valueOr(string(display, "third_person_animation"), "default"), bool(display, "3rd_fixed_hand", false), sounds), pack);
  }

  private static TaczAmmoSpec loadAmmo(ResourceLocation id, JsonObject index, TaczLayeredResources resources) {
    ResourceLocation displayId = requiredId(index, "display");
    JsonObject display = readObject(resources, "assets/" + displayId.getNamespace() + "/display/ammo/" + displayId.getPath() + ".json");
    return new TaczAmmoSpec(id,
        valueOr(string(index, "name"), "tacz.ammo." + id.getPath().replace('/', '.') + ".name"),
        clamp(integer(index, "stack_size", 64), 1, 99), integer(index, "sort", 0),
        asset(resource(display, "model"), "geo_models/", ".json"),
        asset(resource(display, "texture"), "textures/", ".png"),
        asset(resource(display, "slot"), "textures/", ".png"));
  }

  private static String asset(ResourceLocation id, String directory, String suffix) {
    return id == null ? null : "assets/" + id.getNamespace() + "/" + directory + id.getPath() + suffix;
  }

  private static JsonObject readObject(TaczLayeredResources resources, String path) {
    try (InputStream input = resources.open(path)) {
      if (input == null) throw new IllegalStateException("Missing TaCZ resource: " + path);
      return readObject(input);
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to read TaCZ resource: " + path, exception);
    }
  }

  private static JsonObject readObject(InputStream input) {
    JsonReader reader = new JsonReader(new InputStreamReader(input, StandardCharsets.UTF_8));
    reader.setLenient(true);
    return JsonParser.parseReader(reader).getAsJsonObject();
  }

  private static ResourceLocation requiredId(JsonObject json, String key) {
    ResourceLocation id = resource(json, key);
    if (id == null) throw new IllegalStateException("Missing or invalid resource location '" + key + "'");
    return id;
  }

  private static ResourceLocation resource(JsonObject json, String key) {
    String value = string(json, key);
    return value == null ? null : ResourceLocation.tryParse(value);
  }

  private static ResourceLocation id(String namespace, String path) {
    try {
      return ResourceLocation.fromNamespaceAndPath(namespace, path);
    } catch (RuntimeException ignored) {
      return null;
    }
  }

  private static String string(JsonObject json, String key) {
    if (json == null) return null;
    JsonElement value = json.get(key);
    return value != null && value.isJsonPrimitive() ? value.getAsString() : null;
  }

  private static int integer(JsonObject json, String key, int fallback) {
    if (json == null) return fallback;
    JsonElement value = json.get(key);
    try {
      return value != null && value.isJsonPrimitive() ? value.getAsInt() : fallback;
    } catch (RuntimeException ignored) {
      return fallback;
    }
  }

  private static double decimal(JsonObject json, String key, double fallback) {
    if (json == null) return fallback;
    JsonElement value = json.get(key);
    try {
      return value != null && value.isJsonPrimitive() ? value.getAsDouble() : fallback;
    } catch (RuntimeException ignored) {
      return fallback;
    }
  }

  private static boolean bool(JsonObject json, String key, boolean fallback) {
    if (json == null) return fallback;
    JsonElement value = json.get(key);
    try {
      return value != null && value.isJsonPrimitive() ? value.getAsBoolean() : fallback;
    } catch (RuntimeException ignored) {
      return fallback;
    }
  }

  private static JsonObject object(JsonObject json, String key) {
    if (json == null) return null;
    JsonElement value = json.get(key);
    return value != null && value.isJsonObject() ? value.getAsJsonObject() : null;
  }

  private static int secondsToTicks(double seconds) {
    return Math.max(1, (int) Math.ceil(seconds * 20.0));
  }

  private static int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
  }

  private static double clamp(double value, double min, double max) {
    return Math.max(min, Math.min(max, value));
  }

  private static String valueOr(String value, String fallback) {
    return value == null ? fallback : value;
  }

  private static <T> void tryLoad(ResourceLocation id, Factory<T> factory, Consumer<T> sink, String kind) {
    try {
      sink.accept(factory.get());
    } catch (RuntimeException exception) {
      Zinecraft.INSTANCE.getLogger().warn("Skipping invalid TaCZ {} index {}", kind, id, exception);
    }
  }

  @FunctionalInterface
  private interface Factory<T> {
    T get();
  }
}
