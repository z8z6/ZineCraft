# NeoForge Migration Notes

- The Trinkets `chest/relic` slot is now the Curios `relic` slot. Existing items equipped through Trinkets are not
  migrated automatically; recover them with the old Fabric build before opening an important save on NeoForge.
- The TaCZ animation compatibility module is temporarily disabled. Lua animation state machines, first-person bone
  animation, and third-person player animation are not loaded. TaCZ weapon gameplay, ammunition state, external gun-pack
  discovery, sounds, effects, and static item-model rendering remain available.
- Registry IDs, resource locations, and existing Zinecraft gameplay data are otherwise preserved wherever the target
  NeoForge APIs support the same format.

## Incomplete migration work

- Item registration and sound registration now use NeoForge deferred holders. Block, block-entity, entity, and several
  content-dependent static initializers still need the same holder-based conversion before `runData`, `runClient`, or
  `runServer` can load the mod.
- The converted Java sources still contain Kotlin standard-library calls produced by decompilation. Kotlin stdlib is
  temporarily embedded and added to development runs until those calls and synthetic default-argument methods are
  rewritten as normal Java APIs.
- Language/model/loot output remains datagen-owned. Because the mod currently stops during block static initialization,
  `runData` cannot regenerate `assets/zinecraft/lang/*.json`, and the collectible JAR resource verification consequently
  cannot pass yet.
