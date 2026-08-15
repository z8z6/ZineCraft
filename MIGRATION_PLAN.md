# Current Architecture

- Minecraft 1.21.1 Fabric mod, mod id `zinecraft`, Java 21 target, implemented primarily in Kotlin.
- `com.cxxcxx.zinecraft.api`: declarative catalogs for registries, localization, weapons, skills, worldgen and datagen.
- `com.cxxcxx.zinecraft.core`: content declarations and the Fabric common entrypoint.
- `src/client`: renderers, Ponder scenes, weapon input/presentation, TaCZ-compatible external resources and two client
  mixins.
- 101 Kotlin source files (about 10,000 lines), 2 Java mixins, no access widener.
- Resources use standard `assets/zinecraft` and `data/zinecraft` paths; Fabric metadata is `fabric.mod.json`.

# Fabric Dependencies

- Build/runtime: Fabric Loom, Fabric Loader, Fabric API, Fabric Language Kotlin.
- Entrypoints: `ModInitializer`, `ClientModInitializer`, Fabric datagen entrypoint and TerraBlender Fabric entrypoint
  metadata.
- Registries: direct vanilla `Registry.register`, Fabric entity builder, item group events, fuel and compost registries.
- Events: Fabric server/client ticks, connection lifecycle and client lifecycle events.
- Networking: Fabric payload type registries and client/server play networking.
- Worldgen: Fabric biome selectors and biome modifications.
- Client: Fabric key bindings, entity renderers, built-in item renderer and resource reload listener.
- Platform lookup/paths: `FabricLoader` mod presence, game directory and config directory.
- Datagen: Fabric model, language, loot, recipe and dynamic-registry providers.
- Integrations: TerraBlender Fabric, Trinkets, Ponder Fabric, FTB Quests Fabric and Cloth Config Fabric.
- Mixins: two client-only mixins; no access widener.

# Kotlin Dependencies

- Extensive `object` declarations, data classes, sealed interfaces/classes and companion objects.
- Extension functions/properties, delegated/lazy properties and Kotlin collection operations.
- Nullable types, safe calls, Elvis expressions, smart casts and default/named arguments.
- Lambdas with receivers and Kotlin-generated default overloads.
- No coroutine subsystem was found.

# Migration Risk

- **LOW**: metadata, Java 21 build setup, simple items/blocks/sounds, resource paths and platform path lookup.
- **MEDIUM**: DeferredRegister conversion, creative tabs, entity attributes/spawns, events, Ponder integration, FTB
  guide installation and datagen providers.
- **HIGH**: 10k-line Kotlin-to-Java conversion, networking, biome injection/worldgen codecs, client-only TaCZ
  rendering/resource reloads, and replacing Trinkets with a NeoForge accessory equivalent while preserving saves/slots.

# Execution Order

1. Replace Loom/Kotlin build and metadata with the official NeoForge 1.21.1 ModDevGradle layout; compile the minimal
   entrypoint.
2. Convert common catalogs/content to Java and DeferredRegister; compile after each registry family.
3. Migrate networking, events, persistence and server-authoritative weapon behavior.
4. Convert client render/input/animation/resource code and retain only mixins still required.
5. Migrate worldgen, integrations and datagen; preserve resource IDs and record unavoidable compatibility changes.
6. Run datagen, tests, full build, client smoke test and dedicated-server smoke test.
