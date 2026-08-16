"""NBT plumbing for nation landmark builders.

This module deliberately contains no building profiles, room templates or
silhouette algorithms.  Nation modules own every architectural decision; this
file only serialises block coordinates and checks the six-piece Jigsaw contract.
"""

from __future__ import annotations

import gzip
import hashlib
import struct
from collections import Counter, deque
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any


ROOT = Path(__file__).resolve().parents[2]
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/nation_landmarks"
DATA_VERSION = 3955
MODULE_NAMES = ("foundation", "core", "facade", "roof", "annex", "surrounding")
MAX_DISTANCE_FROM_CENTER = 112

NATION_MATERIALS = {
    "aegir": ("zinecraft:aegir_abyssal_slate", "zinecraft:aegir_pressure_tile", "minecraft:oxidized_copper"),
    "bolivar": ("zinecraft:bolivar_war_scoured_soil", "zinecraft:bolivar_dossoles_stucco", "minecraft:cut_copper"),
    "higashi": ("zinecraft:higashi_shadow_loam", "zinecraft:higashi_machiya_plaster", "minecraft:dark_oak_planks"),
    "durin": ("zinecraft:durin_garden_moss", "zinecraft:durin_ideal_city_panel", "minecraft:yellow_terracotta"),
    "columbia": ("zinecraft:columbia_canyon_soil", "zinecraft:columbia_frontier_panel", "minecraft:iron_block"),
    "kazimierz": ("zinecraft:kazimierz_steppe_turf", "zinecraft:kazimierz_arena_masonry", "minecraft:gold_block"),
    "kazdel": ("zinecraft:kazdel_scarred_ash", "zinecraft:kazdel_fortress_plate", "minecraft:polished_blackstone"),
    "laterano": ("zinecraft:laterano_alluvial_chalk", "zinecraft:laterano_basilica_marble", "minecraft:gold_block"),
    "leithanien": ("zinecraft:leithanien_twilight_humus", "zinecraft:leithanien_resonant_brick", "minecraft:amethyst_block"),
    "rim_billiton": ("zinecraft:rim_billiton_mine_tailings", "zinecraft:rim_billiton_corrugated_steel", "minecraft:cut_copper"),
    "minos": ("zinecraft:minos_sunbaked_earth", "zinecraft:minos_heroic_masonry", "minecraft:chiseled_sandstone"),
    "sargon": ("zinecraft:sargon_desert_crust", "zinecraft:sargon_oasis_adobe", "minecraft:cut_sandstone"),
    "sami": ("zinecraft:sami_frost_moss", "zinecraft:sami_tribal_timber", "minecraft:spruce_planks"),
    "victoria": ("zinecraft:victoria_moorland_soil", "zinecraft:victoria_industrial_brick", "create:industrial_iron_block"),
    "ursus": ("zinecraft:ursus_permafrost", "zinecraft:ursus_imperial_masonry", "minecraft:iron_block"),
    "kjerag": ("zinecraft:kjerag_sacred_snowstone", "zinecraft:kjerag_monastery_stone", "minecraft:spruce_planks"),
    "siracusa": ("zinecraft:siracusa_rain_darkened_soil", "zinecraft:siracusa_family_masonry", "minecraft:dark_oak_planks"),
    "yan": ("zinecraft:yan_mountain_soil", "zinecraft:yan_courtyard_brick", "minecraft:polished_tuff"),
    "iberia": ("zinecraft:iberia_salt_crusted_gravel", "zinecraft:iberia_coastal_masonry", "minecraft:oxidized_copper"),
}

# Stable state slots.  Builder files place these directly; no shared room or
# massing routine exists here.
AIR, GROUND, WALL, TRIM, GLASS, LIGHT = range(6)
GIRDER, CASING, SHAFT_X, COG, CHEST, BARREL = range(6, 12)
CRAFTING, CARTOGRAPHY, LECTERN, BOOKSHELF, SLAB = range(12, 17)
STAIR_E, STAIR_W, BARS, FENCE, DOOR_LOWER, DOOR_UPPER = range(17, 23)
WATER, ANVIL, LADDER, STAIR_N, STAIR_S, RAIL, CHAIN = range(23, 30)
LEAVES, GLOW, COPPER_GRATE = range(30, 33)

BASE_PALETTE = (
    ("minecraft:air", None), ("GROUND", None), ("WALL", None), ("TRIM", None),
    ("minecraft:tinted_glass", None), ("minecraft:sea_lantern", None),
    ("create:metal_girder", None), ("create:andesite_casing", None),
    ("create:shaft", {"axis": "x"}), ("create:cogwheel", {"axis": "x"}),
    ("minecraft:chest", {"facing": "west", "type": "single", "waterlogged": "false"}),
    ("minecraft:barrel", {"facing": "up", "open": "false"}),
    ("minecraft:crafting_table", None), ("minecraft:cartography_table", None),
    ("minecraft:lectern", {"facing": "south", "has_book": "false", "powered": "false"}),
    ("minecraft:bookshelf", None),
    ("minecraft:dark_oak_slab", {"type": "top", "waterlogged": "false"}),
    ("minecraft:polished_andesite_stairs", {"facing": "east", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:polished_andesite_stairs", {"facing": "west", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:iron_bars", None),
    ("minecraft:dark_oak_fence", {"east": "false", "north": "false", "south": "false", "waterlogged": "false", "west": "false"}),
    ("create:brass_door", {"facing": "south", "half": "lower", "hinge": "left", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "south", "half": "upper", "hinge": "left", "open": "false", "powered": "false", "visible": "true"}),
    ("minecraft:water", {"level": "0"}), ("minecraft:anvil", {"facing": "north"}),
    ("minecraft:ladder", {"facing": "north", "waterlogged": "false"}),
    ("minecraft:polished_andesite_stairs", {"facing": "north", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:polished_andesite_stairs", {"facing": "south", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:rail", {"shape": "north_south", "waterlogged": "false"}),
    ("minecraft:chain", {"axis": "y", "waterlogged": "false"}),
    ("minecraft:azalea_leaves", {"distance": "1", "persistent": "true", "waterlogged": "false"}),
    ("minecraft:ochre_froglight", {"axis": "y"}), ("create:copper_grate", {"waterlogged": "false"}),
)

JIGSAW_DIRECTIONS = ("east", "west", "north", "south", "up", "down")
JIGSAW_ORIENTATION = {
    "east": "east_up", "west": "west_up", "north": "north_up",
    "south": "south_up", "up": "up_east", "down": "down_east",
}
LADDER_STATE = "minecraft:ladder[facing=north,waterlogged=false]"


@dataclass(frozen=True)
class Spec:
    path: str
    nation: str
    program: str
    source_image: str
    anchors: tuple[str, ...]
    scale: str


@dataclass
class Module:
    spec: Spec
    name: str
    size: tuple[int, int, int]
    blocks: dict[tuple[int, int, int], int] = field(default_factory=dict)
    nbt: dict[tuple[int, int, int], dict[str, Any]] = field(default_factory=dict)
    connectors: list[tuple[str, str, str, tuple[int, int, int], str]] = field(default_factory=list)
    lights: set[tuple[int, int, int]] = field(default_factory=set)
    containers: set[tuple[int, int, int]] = field(default_factory=set)
    doors: set[tuple[int, int, int]] = field(default_factory=set)
    stairs: set[tuple[int, int, int]] = field(default_factory=set)
    access_targets: set[tuple[int, int, int]] = field(default_factory=set)

    def set(self, x: int, y: int, z: int, state: int) -> None:
        if not (0 <= x < self.size[0] and 0 <= y < self.size[1] and 0 <= z < self.size[2]):
            raise ValueError(f"{self.spec.path}/{self.name}: out of bounds {(x, y, z)} for {self.size}")
        self.blocks[(x, y, z)] = state
        self.nbt.pop((x, y, z), None)

    def fill(self, a: tuple[int, int, int], b: tuple[int, int, int], state: int) -> None:
        for x in range(a[0], b[0] + 1):
            for y in range(a[1], b[1] + 1):
                for z in range(a[2], b[2] + 1):
                    self.set(x, y, z, state)

    def parent(self, pos: tuple[int, int, int], direction: str, seam: str, pool: str,
               final_state: str = "minecraft:air") -> None:
        self._jigsaw(pos, direction, seam, pool, final_state, True)

    def child(self, pos: tuple[int, int, int], direction: str, seam: str,
              final_state: str = "minecraft:air") -> None:
        self._jigsaw(pos, direction, seam, "minecraft:empty", final_state, False)

    def _jigsaw(self, pos: tuple[int, int, int], direction: str, seam: str, pool: str,
                final_state: str, parent: bool) -> None:
        state = len(BASE_PALETTE) + JIGSAW_DIRECTIONS.index(direction)
        self.set(*pos, state)
        target = f"zinecraft:{self.spec.path}/{seam}"
        self.nbt[pos] = {
            "id": "minecraft:jigsaw", "name": target,
            "target": target if parent else "minecraft:empty",
            "pool": f"zinecraft:{self.spec.path}/{pool}" if parent else pool,
            "final_state": final_state, "joint": "aligned",
            "selection_priority": 0, "placement_priority": 0,
        }
        self.connectors.append((seam, pool, direction, pos, final_state))

    def light(self, x: int, y: int, z: int) -> None:
        self.set(x, y, z, LIGHT)
        self.lights.add((x, y, z))

    def chest(self, x: int, y: int, z: int) -> None:
        self.set(x, y, z, CHEST)
        self.nbt[(x, y, z)] = {
            "id": "minecraft:chest",
            "LootTable": f"zinecraft:chests/nation/{self.spec.nation}_structure",
        }
        self.containers.add((x, y, z))

    def door(self, x: int, y: int, z: int) -> None:
        self.set(x, y, z, DOOR_LOWER)
        self.set(x, y + 1, z, DOOR_UPPER)
        self.doors.update(((x, y, z), (x, y + 1, z)))

    @property
    def palette(self) -> list[tuple[str, dict[str, str] | None]]:
        ground, wall, trim = NATION_MATERIALS[self.spec.nation]
        palette = list(BASE_PALETTE)
        palette[GROUND], palette[WALL], palette[TRIM] = (ground, None), (wall, None), (trim, None)
        palette.extend(("minecraft:jigsaw", {"orientation": JIGSAW_ORIENTATION[d]}) for d in JIGSAW_DIRECTIONS)
        return palette


@dataclass(frozen=True)
class LandmarkBuild:
    spec: Spec
    modules: tuple[Module, ...]
    assembled_scale: tuple[int, int, int]


@dataclass(frozen=True)
class AssembledBuild:
    origins: dict[str, tuple[int, int, int]]
    bounds: tuple[tuple[int, int, int], tuple[int, int, int]]
    blocks: dict[tuple[int, int, int], int]
    module_for_block: dict[tuple[int, int, int], str]

    @property
    def scale(self) -> tuple[int, int, int]:
        lo, hi = self.bounds
        return tuple(hi[i] - lo[i] + 1 for i in range(3))


def _utf(value: str) -> bytes:
    encoded = value.encode("utf-8")
    return struct.pack(">H", len(encoded)) + encoded


def _named(tag_type: int, name: str, payload: bytes) -> bytes:
    return bytes([tag_type]) + _utf(name) + payload


def _list(tag_type: int, values: list[bytes]) -> bytes:
    return bytes([tag_type]) + struct.pack(">i", len(values)) + b"".join(values)


def _compound(tags: list[bytes]) -> bytes:
    return b"".join(tags) + b"\x00"


def _value(name: str, value: Any) -> bytes:
    if isinstance(value, int):
        return _named(3, name, struct.pack(">i", value))
    if isinstance(value, str):
        return _named(8, name, _utf(value))
    if isinstance(value, dict):
        return _named(10, name, _compound([_value(k, v) for k, v in value.items()]))
    raise TypeError(f"unsupported NBT value {name}={value!r}")


def write_module(module: Module) -> None:
    palette_payload = []
    for name, properties in module.palette:
        tags = [_named(8, "Name", _utf(name))]
        if properties:
            tags.append(_named(10, "Properties", _compound([_named(8, k, _utf(v)) for k, v in properties.items()])))
        palette_payload.append(_compound(tags))
    block_payload = []
    for pos, state in sorted(module.blocks.items()):
        tags = [
            _named(9, "pos", _list(3, [struct.pack(">i", v) for v in pos])),
            _named(3, "state", struct.pack(">i", state)),
        ]
        if pos in module.nbt:
            tags.append(_named(10, "nbt", _compound([_value(k, v) for k, v in module.nbt[pos].items()])))
        block_payload.append(_compound(tags))
    root = _compound([
        _named(3, "DataVersion", struct.pack(">i", DATA_VERSION)),
        _named(9, "size", _list(3, [struct.pack(">i", v) for v in module.size])),
        _named(9, "palette", _list(10, palette_payload)),
        _named(9, "blocks", _list(10, block_payload)),
        _named(9, "entities", _list(10, [])),
    ])
    target = OUTPUT / module.spec.path / f"{module.name}.nbt"
    target.parent.mkdir(parents=True, exist_ok=True)
    with gzip.GzipFile(filename=str(target), mode="wb", mtime=0) as stream:
        stream.write(bytes([10]) + _utf("") + root)


def geometry_digest(build: LandmarkBuild) -> str:
    digest = hashlib.sha256()
    digest.update(str(build.assembled_scale).encode())
    for module in build.modules:
        digest.update(f"{module.name}:{module.size}".encode())
        for pos, state in sorted(module.blocks.items()):
            # National material substitution is intentionally ignored: the
            # digest proves the actual massing differs, not merely the palette.
            digest.update(f"{pos}:{state}".encode())
    return digest.hexdigest()


_DIRECTION_VECTOR = {
    "east": (1, 0, 0), "west": (-1, 0, 0),
    "north": (0, 0, -1), "south": (0, 0, 1),
    "up": (0, 1, 0), "down": (0, -1, 0),
}
_OPPOSITE = {
    "east": "west", "west": "east", "north": "south",
    "south": "north", "up": "down", "down": "up",
}


def _add(a: tuple[int, int, int], b: tuple[int, int, int]) -> tuple[int, int, int]:
    return a[0] + b[0], a[1] + b[1], a[2] + b[2]


def _sub(a: tuple[int, int, int], b: tuple[int, int, int]) -> tuple[int, int, int]:
    return a[0] - b[0], a[1] - b[1], a[2] - b[2]


def assemble_build(build: LandmarkBuild) -> AssembledBuild:
    """Resolve the default Jigsaw placement from connector facing and position."""
    modules = {module.name: module for module in build.modules}
    seam_entries: dict[str, list[tuple[Module, tuple[str, str, str, tuple[int, int, int], str]]]] = {}
    for module in build.modules:
        for connector in module.connectors:
            seam_entries.setdefault(connector[0], []).append((module, connector))

    origins: dict[str, tuple[int, int, int]] = {"foundation": (0, 0, 0)}
    pending = dict(seam_entries)
    while pending:
        progressed = False
        for seam, pair in tuple(pending.items()):
            if len(pair) != 2:
                raise ValueError(f"{build.spec.path}/{seam}: expected connector pair")
            parent_entries = [entry for entry in pair if entry[1][1] != "minecraft:empty"]
            child_entries = [entry for entry in pair if entry[1][1] == "minecraft:empty"]
            if len(parent_entries) != 1 or len(child_entries) != 1:
                raise ValueError(f"{build.spec.path}/{seam}: parent/child role mismatch")
            parent_module, parent_connector = parent_entries[0]
            child_module, child_connector = child_entries[0]
            parent_direction, child_direction = parent_connector[2], child_connector[2]
            if child_direction != _OPPOSITE[parent_direction]:
                raise ValueError(f"{build.spec.path}/{seam}: connector orientation mismatch")
            if parent_module.name not in origins:
                continue
            parent_world = _add(origins[parent_module.name], parent_connector[3])
            child_world = _add(parent_world, _DIRECTION_VECTOR[parent_direction])
            child_origin = _sub(child_world, child_connector[3])
            previous = origins.get(child_module.name)
            if previous is not None and previous != child_origin:
                raise ValueError(f"{build.spec.path}/{seam}: inconsistent child origin")
            origins[child_module.name] = child_origin
            del pending[seam]
            progressed = True
        if not progressed:
            raise ValueError(f"{build.spec.path}: disconnected Jigsaw graph {sorted(pending)}")
    if set(origins) != set(modules):
        raise ValueError(f"{build.spec.path}: unplaced modules {set(modules) - set(origins)}")

    boxes: dict[str, tuple[tuple[int, int, int], tuple[int, int, int]]] = {}
    for name, module in modules.items():
        lo = origins[name]
        hi = tuple(lo[i] + module.size[i] - 1 for i in range(3))
        boxes[name] = lo, hi
    names = tuple(modules)
    for index, first in enumerate(names):
        alo, ahi = boxes[first]
        for second in names[index + 1:]:
            blo, bhi = boxes[second]
            if all(alo[i] <= bhi[i] and blo[i] <= ahi[i] for i in range(3)):
                raise ValueError(f"{build.spec.path}: module AABBs overlap: {first}/{second}")

    lo = tuple(min(box[0][i] for box in boxes.values()) for i in range(3))
    hi = tuple(max(box[1][i] for box in boxes.values()) for i in range(3))
    blocks: dict[tuple[int, int, int], int] = {}
    owners: dict[tuple[int, int, int], str] = {}
    for name, module in modules.items():
        final_states = {
            connector[3]: LADDER if connector[4] == LADDER_STATE else AIR
            for connector in module.connectors
        }
        for local, state in module.blocks.items():
            world = _add(origins[name], local)
            blocks[world] = final_states.get(local, state)
            owners[world] = name
    return AssembledBuild(origins, (lo, hi), blocks, owners)


def _world_pos(assembled: AssembledBuild, module: Module,
               pos: tuple[int, int, int]) -> tuple[int, int, int]:
    return _add(assembled.origins[module.name], pos)


def _strict_access_and_light(build: LandmarkBuild, assembled: AssembledBuild) -> None:
    # Collision and skylight semantics are intentionally conservative: panes,
    # fences, chains, foliage and tinted glass are not player space, and tinted
    # glass must not leak an artificial light proof through a sealed room.
    passable = {AIR, DOOR_LOWER, DOOR_UPPER, LADDER, WATER}
    transparent = set(passable)
    lo, hi = assembled.bounds

    def inside(pos: tuple[int, int, int]) -> bool:
        return all(lo[i] <= pos[i] <= hi[i] for i in range(3))

    def state(pos: tuple[int, int, int]) -> int:
        return assembled.blocks.get(pos, AIR)

    def standing(pos: tuple[int, int, int]) -> bool:
        x, y, z = pos
        if not inside(pos) or y + 1 > hi[1]:
            return False
        if state(pos) not in passable or state((x, y + 1, z)) not in passable:
            return False
        return state(pos) == LADDER or state((x, y - 1, z)) not in passable

    goals: set[tuple[int, int, int]] = set()
    chest_stands: dict[tuple[int, int, int], tuple[int, int, int]] = {}
    for module in build.modules:
        origin = assembled.origins[module.name]
        if not module.lights:
            raise ValueError(f"{build.spec.path}/{module.name}: module has no purpose-placed supported light")
        goals.update(_add(origin, pos) for pos in module.access_targets)
        if module.stairs:
            stair_landings = sorted(module.stairs, key=lambda pos: pos[1])
            goals.add(_add(origin, (stair_landings[0][0], stair_landings[0][1] + 1, stair_landings[0][2])))
            goals.add(_add(origin, (stair_landings[-1][0], stair_landings[-1][1] + 1, stair_landings[-1][2])))
        ladder_positions = sorted(pos for pos, value in module.blocks.items() if value == LADDER)
        if ladder_positions:
            goals.add(_add(origin, ladder_positions[0]))
            goals.add(_add(origin, ladder_positions[-1]))
        for _seam, _pool, _direction, local, _final_state in module.connectors:
            goals.add(_add(origin, local))
        for x, y, z in module.containers:
            chest_world = _add(origin, (x, y, z))
            if state((chest_world[0], chest_world[1] + 1, chest_world[2])) not in passable:
                raise ValueError(f"{build.spec.path}/{module.name}: chest cannot open at {(x, y, z)}")
            adjacent = tuple(
                p for p in (
                    (chest_world[0] - 1, chest_world[1], chest_world[2]),
                    (chest_world[0] + 1, chest_world[1], chest_world[2]),
                    (chest_world[0], chest_world[1], chest_world[2] - 1),
                    (chest_world[0], chest_world[1], chest_world[2] + 1),
                ) if standing(p)
            )
            if not adjacent:
                raise ValueError(f"{build.spec.path}/{module.name}: chest has no usable adjacent stance {(x, y, z)}")
            chest_stands[chest_world] = adjacent[0]
            goals.add(adjacent[0])
        for x, y, z in module.doors:
            if module.blocks.get((x, y, z)) != DOOR_LOWER:
                continue
            world = _add(origin, (x, y, z))
            sides = ((world[0], world[1], world[2] - 1), (world[0], world[1], world[2] + 1))
            if not all(standing(side) for side in sides):
                raise ValueError(f"{build.spec.path}/{module.name}: door lacks two-sided two-high clearance {(x, y, z)}")
            goals.update(sides)

    if not goals:
        raise ValueError(f"{build.spec.path}: no declared accessible targets")
    invalid = [pos for pos in goals if not standing(pos)]
    if invalid:
        raise ValueError(f"{build.spec.path}: declared targets are not standable {invalid[:8]}")

    start = next(iter(goals))
    queue, seen = deque([start]), {start}
    stair_states = {STAIR_E, STAIR_W, STAIR_N, STAIR_S}
    while queue:
        x, y, z = queue.popleft()
        candidates: list[tuple[int, int, int]] = []
        for dx, dz in ((1, 0), (-1, 0), (0, 1), (0, -1)):
            candidates.append((x + dx, y, z + dz))
            uphill = (x + dx, y + 1, z + dz)
            downhill = (x + dx, y - 1, z + dz)
            if state((uphill[0], uphill[1] - 1, uphill[2])) in stair_states or \
                    state((x, y, z)) == LADDER or state(uphill) == LADDER:
                candidates.append(uphill)
            if state((x, y - 1, z)) in stair_states or \
                    state((x, y, z)) == LADDER or state(downhill) == LADDER:
                candidates.append(downhill)
        if state((x, y, z)) == LADDER:
            candidates.extend(((x, y - 1, z), (x, y + 1, z)))
        for candidate in candidates:
            if candidate not in seen and standing(candidate):
                seen.add(candidate); queue.append(candidate)
    missing = goals - seen
    if missing:
        raise ValueError(f"{build.spec.path}: assembled 3D route misses {len(missing)} targets; sample={sorted(missing)[:8]}")

    light_levels: dict[tuple[int, int, int], int] = {}
    light_queue: deque[tuple[tuple[int, int, int], int]] = deque()
    for module in build.modules:
        for local in module.lights:
            world = _world_pos(assembled, module, local)
            light_levels[world] = 15
            light_queue.append((world, 15))
    while light_queue:
        pos, level = light_queue.popleft()
        if level <= 1:
            continue
        x, y, z = pos
        for neighbour in ((x + 1, y, z), (x - 1, y, z), (x, y + 1, z),
                          (x, y - 1, z), (x, y, z + 1), (x, y, z - 1)):
            if not inside(neighbour) or state(neighbour) not in transparent:
                continue
            if light_levels.get(neighbour, 0) >= level - 1:
                continue
            light_levels[neighbour] = level - 1
            light_queue.append((neighbour, level - 1))
    def covered(pos: tuple[int, int, int]) -> bool:
        x, y, z = pos
        # The player's two-block column is already known clear.  Any opaque
        # structure block above it makes this an interior/covered route node.
        return any(state((x, ceiling_y, z)) not in transparent | {LIGHT}
                   for ceiling_y in range(y + 2, hi[1] + 1))

    lit_contract = goals | {pos for pos in seen if covered(pos)}
    dark = [pos for pos in lit_contract if light_levels.get(pos, 0) <= 0]
    if dark:
        raise ValueError(
            f"{build.spec.path}: {len(dark)} entrance-reachable covered standing nodes are dark; "
            f"sample={sorted(dark)[:8]}"
        )


def validate_build(build: LandmarkBuild) -> None:
    spec, modules = build.spec, build.modules
    if spec.nation not in NATION_MATERIALS:
        raise ValueError(f"{spec.path}: unknown nation {spec.nation}")
    if {m.name for m in modules} != set(MODULE_NAMES) or len(modules) != 6:
        raise ValueError(f"{spec.path}: exactly six named modules are required")
    if any(m.spec != spec for m in modules):
        raise ValueError(f"{spec.path}: mixed landmark specs")
    if not all(1 <= axis <= 48 for m in modules for axis in m.size):
        raise ValueError(f"{spec.path}: a module exceeds Minecraft's 48-block template limit")
    if build.assembled_scale[0] < 64 or build.assembled_scale[2] < 48 or build.assembled_scale[1] < 24:
        raise ValueError(f"{spec.path}: below evidence-led L/XL landmark scale")
    if len(spec.anchors) < 3 or not spec.source_image.lower().endswith(".png"):
        raise ValueError(f"{spec.path}: missing CG source or silhouette anchors")

    seams = Counter(c[0] for module in modules for c in module.connectors)
    expected = Counter({
        "foundation_core": 2, "foundation_surrounding": 2,
        "core_facade": 2, "core_annex": 2, "core_roof": 2,
    })
    if seams != expected:
        raise ValueError(f"{spec.path}: invalid Jigsaw seam set {seams}")

    seam_entries: dict[str, list[tuple[Module, tuple[str, str, str, tuple[int, int, int], str]]]] = {}
    for module in modules:
        if not module.blocks:
            raise ValueError(f"{spec.path}/{module.name}: empty module")
        for connector in module.connectors:
            seam_entries.setdefault(connector[0], []).append((module, connector))
    for seam, pair in seam_entries.items():
        if len(pair) != 2 or pair[0][1][2] != _OPPOSITE[pair[1][1][2]]:
            raise ValueError(f"{spec.path}/{seam}: connector directions do not oppose")
        for module, (_, _, direction, (x, y, z), final_state) in pair:
            if direction in {"east", "west", "north", "south"}:
                if final_state != "minecraft:air":
                    raise ValueError(f"{spec.path}/{seam}: horizontal seam is not air")
                if module.blocks.get((x, y + 1, z), AIR) != AIR or module.blocks.get((x, y + 2, z), AIR) != AIR:
                    raise ValueError(f"{spec.path}/{seam}: interface headroom is blocked")
    if sum(len(m.lights) for m in modules) < 8:
        raise ValueError(f"{spec.path}: insufficient supported lighting markers")
    if sum(len(m.containers) for m in modules) < 3:
        raise ValueError(f"{spec.path}: insufficient national loot containers")
    if sum(len(m.doors) for m in modules) < 4:
        raise ValueError(f"{spec.path}: rooms lack doors")
    if sum(len(m.stairs) for m in modules) < 8:
        raise ValueError(f"{spec.path}: no usable stair route")
    supported = {GROUND, WALL, TRIM, GIRDER, CASING, FENCE, CHAIN}
    for module in modules:
        for x, y, z in module.lights:
            neighbours = ((x + 1, y, z), (x - 1, y, z), (x, y + 1, z),
                          (x, y - 1, z), (x, y, z + 1), (x, y, z - 1))
            if not any(module.blocks.get(p) in supported for p in neighbours):
                raise ValueError(f"{spec.path}/{module.name}: floating light {(x, y, z)}")
        for x, y, z in module.containers:
            if module.blocks.get((x, y - 1, z)) not in {GROUND, WALL, TRIM}:
                raise ValueError(f"{spec.path}/{module.name}: unsupported chest {(x, y, z)}")
            if module.nbt[(x, y, z)].get("LootTable") != f"zinecraft:chests/nation/{spec.nation}_structure":
                raise ValueError(f"{spec.path}/{module.name}: wrong national loot table")
        for x, y, z in module.stairs:
            if module.blocks.get((x, y + 1, z), AIR) != AIR or module.blocks.get((x, y + 2, z), AIR) != AIR:
                raise ValueError(f"{spec.path}/{module.name}: stair headroom blocked at {(x, y, z)}")

    assembled = assemble_build(build)
    if assembled.scale != build.assembled_scale:
        raise ValueError(
            f"{spec.path}: declared scale {build.assembled_scale} != assembled AABB {assembled.scale} "
            f"at {assembled.bounds}"
        )
    foundation = next(module for module in modules if module.name == "foundation")
    foundation_origin = assembled.origins["foundation"]
    center = tuple(foundation_origin[i] + (foundation.size[i] - 1) // 2 for i in range(3))
    for quarter_turns in range(4):
        for module in modules:
            origin = assembled.origins[module.name]
            for x in (origin[0], origin[0] + module.size[0] - 1):
                for y in (origin[1], origin[1] + module.size[1] - 1):
                    for z in (origin[2], origin[2] + module.size[2] - 1):
                        dx, dz = x - center[0], z - center[2]
                        for _ in range(quarter_turns):
                            dx, dz = -dz, dx
                        if max(abs(dx), abs(y - center[1]), abs(dz)) > MAX_DISTANCE_FROM_CENTER:
                            raise ValueError(
                                f"{spec.path}/{module.name}: assembled AABB exceeds max distance "
                                f"after {quarter_turns * 90}deg rotation at {(x, y, z)}"
                            )
    for seam, pair in seam_entries.items():
        world = []
        for module, connector in pair:
            world.append((_world_pos(assembled, module, connector[3]), connector[2]))
        if _add(world[0][0], _DIRECTION_VECTOR[world[0][1]]) != world[1][0] and \
                _add(world[1][0], _DIRECTION_VECTOR[world[1][1]]) != world[0][0]:
            raise ValueError(f"{spec.path}/{seam}: assembled connector blocks are not adjacent")
    _strict_access_and_light(build, assembled)


def prove_room_route(module: Module, start: tuple[int, int, int], goals: tuple[tuple[int, int, int], ...]) -> None:
    """Generic voxel reachability assertion; builders still define all rooms and targets."""
    passable = {AIR, DOOR_LOWER, DOOR_UPPER, LADDER, STAIR_E, STAIR_W, STAIR_N, STAIR_S}
    queue, seen = deque([start]), {start}
    while queue:
        x, y, z = queue.popleft()
        for pos in ((x + 1, y, z), (x - 1, y, z), (x, y, z + 1), (x, y, z - 1)):
            px, py, pz = pos
            if pos in seen or not (0 <= px < module.size[0] and 0 <= py + 1 < module.size[1] and 0 <= pz < module.size[2]):
                continue
            if module.blocks.get(pos, AIR) in passable and module.blocks.get((px, py + 1, pz), AIR) in passable:
                seen.add(pos)
                queue.append(pos)
    missing = [goal for goal in goals if goal not in seen]
    if missing:
        raise ValueError(f"{module.spec.path}/{module.name}: unreachable room targets {missing}")
