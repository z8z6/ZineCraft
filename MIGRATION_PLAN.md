# NeoForge Migration Plan — Completed

This plan is retained as a completion record. The current architecture is documented in [`README.md`](README.md), [
`docs/README.md`](docs/README.md), and [`MIGRATION_NOTES.md`](MIGRATION_NOTES.md).

## Target baseline

- Minecraft 1.21.1
- NeoForge 21.1.244
- Java 21
- ModDevGradle with Parchment mappings
- One `zinecraft` module with common Java sources in `src/main/java` and client Java sources in `src/client/java`

## Completed phases

1. Replaced the old loader build, metadata, entrypoints, and language runtime with the NeoForge Java toolchain.
2. Migrated static registrations to NeoForge lifecycle/deferred registration and dynamic content to registry
   bootstrap/datagen.
3. Migrated payload networking, events, persistence, and server-authoritative Weapon Runtime behavior.
4. Migrated client input, renderers, Ponder integration, resource reloads, and the TaCZ resource bridge.
5. Migrated biome modifiers, Terra world generation, Curios equipment, FTB Quests templates, and development
   integrations.
6. Rewrote tests in Java and restored `test`, `runData`, `build`, and `runClient` validation.
7. Removed generated language data-class/default-argument methods and obsolete `*Kt` utility names from the Java source
   tree.

## Preserved compatibility

- Stable registry IDs and resource locations were retained wherever the target APIs support the same format.
- Existing old-loader accessory storage is not imported automatically; users should recover important equipped items
  before converting a save.
- External TaCZ gun packs remain separate from the project JAR and are interpreted by Zinecraft's own runtime.

## Ongoing cleanup

Future cleanup is ordinary Java maintenance rather than loader migration: improve mechanically converted helper names
and loops, remove placeholder content, expand automated game tests, and refine optional integration boundaries.
