# NeoForge Migration Notes

The Minecraft 1.21.1 migration is complete. Zinecraft now targets NeoForge 21.1.244 and Java 21 only.

## Completed

- Replaced Fabric Loader, Fabric API, Loom, Fabric networking/events/datagen, and loader-specific biome hooks with
  NeoForge or Minecraft 1.21.1 APIs.
- Converted common, client, and test sources to Java; removed the additional language runtime and generated
  data-class/default-argument bridges.
- Migrated the Trinkets `chest/relic` concept to the Curios `relic` slot. Existing Trinkets-equipped items are not
  automatically imported from old saves.
- Restored `runData`, Java tests, full `build`, and `runClient` startup validation.
- Preserved resource IDs and gameplay data where the target APIs support the same format.

## Current compatibility notes

- NeoForge is the only supported loader. No Fabric/NeoForge abstraction layer is maintained.
- TaCZ support reads external TaCZ 1.1.x gun-pack formats; it does not load the Forge TaCZ mod JAR. Server-authoritative
  firearm gameplay, ammunition state, sounds, effects, static/animated resources, and player presentation are
  implemented by Zinecraft's own runtime.
- The Curios slot migration changes equipment storage. Recover important Trinkets-equipped items with an old build
  before opening that save with the NeoForge build.
- Generated language, model, loot, recipe, biome, dimension, feature, and structure data remains owned by `runData` and
  the declarative catalogs.

## Verification baseline

```powershell
.\gradlew.bat test
.\gradlew.bat runData
.\gradlew.bat build
.\gradlew.bat runClient
```

The historical migration specification remains in [`docs/neoforge.md`](docs/neoforge.md). It documents the conversion
requirements and should not be interpreted as unfinished work.
