"""Generate the registered Londinium defence-cannon architecture preview.

The preview uses original nation materials, traversable service interiors and
loot-bearing logistics spaces while retaining the reviewed seven-module silhouette.
"""

from __future__ import annotations

import gzip
import struct
from dataclasses import dataclass, field
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/blockout/victoria/londinium/defence_cannon"
DATA_VERSION = 3955

AIR = 0
ARMOR = 1
CASING = 2
FRAME = 3
FLOOR = 4
CONTROL = 5
GLASS = 6
LAMP = 7
IRON_BARS = 8
LADDER = 9
CHEST = 10
BARREL = 11
LEVER = 12
CRAFTING_TABLE = 13
BLAST_FURNACE = 14
CAULDRON = 15
CREATE_CASING = 16
CREATE_GIRDER = 17
CREATE_SHAFT_X = 18
CREATE_SHAFT_Z = 19
CREATE_COG_X = 20
CREATE_LARGE_COG_X = 21
CREATE_PIPE = 22
CREATE_PUMP = 23

PALETTE: tuple[tuple[str, dict[str, str] | None], ...] = (
    ("minecraft:air", None),
    ("zinecraft:victoria_wall_armor", None),
    ("zinecraft:victoria_cannon_casing", None),
    ("zinecraft:victoria_structural_frame", None),
    ("zinecraft:victoria_reinforced_floor", None),
    ("zinecraft:victoria_control_panel", None),
    ("minecraft:tinted_glass", None),
    ("minecraft:redstone_lamp", {"lit": "true"}),
    ("minecraft:iron_bars", None),
    ("minecraft:ladder", {"facing": "west", "waterlogged": "false"}),
    ("minecraft:chest", {"facing": "west", "type": "single", "waterlogged": "false"}),
    ("minecraft:barrel", {"facing": "up", "open": "false"}),
    ("minecraft:lever", {"face": "wall", "facing": "north", "powered": "false"}),
    ("minecraft:crafting_table", None),
    ("minecraft:blast_furnace", {"facing": "south", "lit": "false"}),
    ("minecraft:water_cauldron", {"level": "1"}),
    ("create:industrial_iron_block", None),
    ("create:metal_girder", None),
    ("create:shaft", {"axis": "x"}),
    ("create:shaft", {"axis": "z"}),
    ("create:cogwheel", {"axis": "x"}),
    ("create:large_cogwheel", {"axis": "x"}),
    ("create:fluid_pipe", None),
    ("create:mechanical_pump", {"facing": "east", "waterlogged": "false"}),
)

PREVIEW_ID = "zinecraft:victoria_defence_cannon_preview"
JIGSAW_STATES = {
    direction: (orientation, len(PALETTE) + index)
    for index, (direction, orientation) in enumerate(
        (("east", "east_up"), ("west", "west_up"), ("north", "north_up"),
         ("south", "south_up"), ("up", "up_east"), ("down", "down_east"))
    )
}


def utf(value: str) -> bytes:
    encoded = value.encode("utf-8")
    return struct.pack(">H", len(encoded)) + encoded


def named(tag_type: int, name: str, payload: bytes) -> bytes:
    return bytes([tag_type]) + utf(name) + payload


def string_payload(value: str) -> bytes:
    return utf(value)


def int_payload(value: int) -> bytes:
    return struct.pack(">i", value)


def list_payload(tag_type: int, values: list[bytes]) -> bytes:
    return bytes([tag_type]) + struct.pack(">i", len(values)) + b"".join(values)


def compound_payload(tags: list[bytes]) -> bytes:
    return b"".join(tags) + b"\x00"


def palette_entry(name: str, properties: dict[str, str] | None = None) -> bytes:
    tags = [named(8, "Name", string_payload(name))]
    if properties:
        tags.append(
            named(
                10,
                "Properties",
                compound_payload([named(8, key, string_payload(value)) for key, value in properties.items()]),
            )
        )
    return compound_payload(tags)


def block_entry(position: tuple[int, int, int], state: int, nbt: dict[str, str | int] | None) -> bytes:
    tags = [
        named(9, "pos", list_payload(3, [int_payload(value) for value in position])),
        named(3, "state", int_payload(state)),
    ]
    if nbt:
        tags.append(
            named(
                10,
                "nbt",
                compound_payload(
                    [
                        named(
                            3 if isinstance(value, int) else 8,
                            key,
                            int_payload(value) if isinstance(value, int) else string_payload(value),
                        )
                        for key, value in nbt.items()
                    ]
                ),
            )
        )
    return compound_payload(tags)


def jigsaw_nbt(name: str, target: str, pool: str, final_state: str) -> dict[str, str | int]:
    return {
        "id": "minecraft:jigsaw",
        "name": name,
        "target": target,
        "pool": pool,
        "final_state": final_state,
        "joint": "aligned",
        "selection_priority": 0,
        "placement_priority": 0,
    }


def loot_chest_nbt(table: str) -> dict[str, str | int]:
    return {
        "id": "minecraft:chest",
        "LootTable": f"zinecraft:chests/victoria_defence_cannon_{table}",
    }


@dataclass
class Module:
    name: str
    size: tuple[int, int, int]
    blocks: dict[tuple[int, int, int], int] = field(default_factory=dict)
    block_nbt: dict[tuple[int, int, int], dict[str, str | int]] = field(default_factory=dict)

    def block(self, x: int, y: int, z: int, material: int) -> None:
        sx, sy, sz = self.size
        if not (0 <= x < sx and 0 <= y < sy and 0 <= z < sz):
            raise ValueError(f"{self.name}: block outside module at {(x, y, z)}")
        self.blocks[(x, y, z)] = material

    def cuboid(self, start: tuple[int, int, int], end: tuple[int, int, int], material: int) -> None:
        for x in range(start[0], end[0] + 1):
            for y in range(start[1], end[1] + 1):
                for z in range(start[2], end[2] + 1):
                    self.block(x, y, z, material)

    def connector(
        self,
        position: tuple[int, int, int],
        direction: str,
        name: str,
        target: str,
        pool: str,
        final_state: str,
    ) -> None:
        _, state = JIGSAW_STATES[direction]
        self.block(*position, state)
        self.block_nbt[position] = jigsaw_nbt(name, target, pool, final_state)

    def loot_chest(self, x: int, y: int, z: int, table: str) -> None:
        self.block(x, y, z, CHEST)
        self.block_nbt[(x, y, z)] = loot_chest_nbt(table)

    def write(self) -> None:
        if not self.blocks:
            raise ValueError(f"{self.name}: empty module")
        root = compound_payload(
            [
                named(3, "DataVersion", int_payload(DATA_VERSION)),
                named(9, "size", list_payload(3, [int_payload(value) for value in self.size])),
                named(
                    9,
                    "palette",
                    list_payload(
                        10,
                        [palette_entry(name, properties) for name, properties in PALETTE]
                        + [
                            palette_entry("minecraft:jigsaw", {"orientation": orientation})
                            for orientation, _ in JIGSAW_STATES.values()
                        ],
                    ),
                ),
                named(
                    9,
                    "blocks",
                    list_payload(
                        10,
                        [
                            block_entry(position, state, self.block_nbt.get(position))
                            for position, state in sorted(self.blocks.items())
                        ],
                    ),
                ),
                named(9, "entities", list_payload(10, [])),
            ]
        )
        OUTPUT.mkdir(parents=True, exist_ok=True)
        with gzip.GzipFile(filename=str(OUTPUT / f"{self.name}.nbt"), mode="wb", mtime=0) as stream:
            stream.write(bytes([10]) + utf("") + root)


def wall_module(name: str, outside: bool, right: bool) -> Module:
    module = Module(name, (32, 20, 32))
    # Two-block foundation, hollow service volume and armored roof.
    module.cuboid((0, 0, 0), (31, 0, 31), FRAME)
    module.cuboid((0, 1, 0), (31, 1, 31), FLOOR)
    module.cuboid((1, 2, 1), (30, 14, 30), AIR)
    module.cuboid((0, 2, 0), (0, 15, 31), ARMOR)
    module.cuboid((31, 2, 0), (31, 15, 31), ARMOR)
    module.cuboid((1, 2, 0), (30, 15, 0), ARMOR)
    module.cuboid((1, 2, 31), (30, 15, 31), ARMOR)
    module.cuboid((0, 15, 0), (31, 16, 31), CASING)

    # Structural ribs and a central cross-corridor make the interior legible.
    for x in (7, 15, 23):
        module.cuboid((x, 2, 1), (x, 14, 30), FRAME)
        module.cuboid((x + 1, 3, 2), (x + 1, 13, 29), AIR)
    module.cuboid((1, 2, 14), (30, 7, 17), AIR)
    module.cuboid((1, 1, 14), (30, 1, 17), FLOOR)
    for x in range(3, 30, 7):
        module.block(x, 14, 15, LAMP)

    # The exterior edge reads as one continuous armored band after assembly.
    if outside:
        module.cuboid((29, 17, 0), (31, 19, 31), ARMOR)
        for z in range(3, 32, 8):
            module.cuboid((29, 18, z), (31, 18, min(z + 2, 31)), FRAME)
    else:
        module.cuboid((0, 17, 0), (2, 18, 31), ARMOR)

    # A broad service lane crosses the wall behind the gun position.
    lane_start = 2 if right else 20
    lane_end = 11 if right else 29
    module.cuboid((0, 17, lane_start), (28, 17, lane_end), FLOOR)
    for x in range(2, 29, 8):
        module.cuboid((x, 18, lane_start), (min(x + 3, 28), 18, lane_start), CASING)

    if name == "wall_rear_left":
        # Blast-separated Originium ordnance magazine: racks, sealed crates and issue chest.
        module.cuboid((2, 2, 3), (13, 8, 3), FRAME)
        module.cuboid((2, 2, 12), (13, 8, 12), FRAME)
        module.cuboid((3, 2, 4), (12, 2, 11), FLOOR)
        for x in (4, 7, 10):
            for z in (5, 10):
                module.block(x, 2, z, BARREL)
                module.block(x, 3, z, BARREL)
        module.loot_chest(12, 2, 8, "ordnance")
        module.cuboid((13, 3, 6), (13, 6, 10), IRON_BARS)
        module.block(12, 7, 8, LAMP)
    elif name == "wall_rear_right":
        # Maintenance workshop and coolant service point.
        module.cuboid((18, 2, 20), (29, 2, 29), FLOOR)
        module.cuboid((19, 2, 28), (28, 4, 29), FRAME)
        module.block(20, 2, 27, CRAFTING_TABLE)
        module.block(22, 2, 27, BLAST_FURNACE)
        module.block(24, 2, 27, CAULDRON)
        module.loot_chest(27, 2, 27, "maintenance")
        module.cuboid((18, 3, 22), (18, 3, 27), CREATE_SHAFT_Z)
        module.block(18, 3, 23, CREATE_COG_X)
        module.block(18, 3, 26, CREATE_LARGE_COG_X)
        module.cuboid((26, 3, 20), (29, 3, 20), CREATE_PIPE)
        module.block(27, 3, 20, CREATE_PUMP)
        module.block(19, 2, 21, CREATE_CASING)
        module.block(28, 7, 27, LAMP)
    elif name == "wall_front_left":
        # Fire-control room: raised consoles face the cannon and observation glazing.
        module.cuboid((17, 2, 3), (29, 2, 12), FLOOR)
        module.cuboid((17, 5, 3), (17, 9, 12), GLASS)
        module.cuboid((20, 2, 5), (27, 3, 6), CONTROL)
        module.cuboid((20, 2, 9), (27, 3, 10), CONTROL)
        for x in (21, 24, 27):
            module.block(x, 4, 6, LEVER)
            module.block(x, 4, 10, LEVER)
        module.loot_chest(28, 2, 11, "control")
        module.block(28, 8, 7, LAMP)
    elif name == "wall_front_right":
        # Crew ready/supply room and roof access.
        module.cuboid((3, 2, 19), (13, 2, 29), FLOOR)
        module.cuboid((4, 2, 27), (11, 3, 28), FRAME)
        module.block(5, 2, 25, BARREL)
        module.block(7, 2, 25, BARREL)
        module.loot_chest(10, 2, 25, "supply")
        for y in range(2, 15):
            module.block(29, y, 28, LADDER)
        module.block(12, 8, 27, LAMP)

    if name == "wall_rear_left":
        module.connector(
            (31, 17, 16), "east", f"{PREVIEW_ID}/rear_left_front", f"{PREVIEW_ID}/front_left",
            f"{PREVIEW_ID}/front_left", "zinecraft:victoria_wall_armor",
        )
        module.connector(
            (16, 17, 31), "south", f"{PREVIEW_ID}/rear_left_right", f"{PREVIEW_ID}/rear_right",
            f"{PREVIEW_ID}/rear_right", "zinecraft:victoria_wall_armor",
        )
    elif name == "wall_rear_right":
        module.connector(
            (16, 17, 0), "north", f"{PREVIEW_ID}/rear_right", "minecraft:empty", "minecraft:empty",
            "zinecraft:victoria_wall_armor",
        )
    elif name == "wall_front_left":
        module.connector(
            (0, 17, 16), "west", f"{PREVIEW_ID}/front_left", "minecraft:empty", "minecraft:empty",
            "zinecraft:victoria_wall_armor",
        )
        module.connector(
            (16, 17, 31), "south", f"{PREVIEW_ID}/front_left_right", f"{PREVIEW_ID}/front_right",
            f"{PREVIEW_ID}/front_right", "zinecraft:victoria_wall_armor",
        )
        module.connector(
            (16, 19, 16), "up", f"{PREVIEW_ID}/front_left_turret", f"{PREVIEW_ID}/turret",
            f"{PREVIEW_ID}/turret", "zinecraft:victoria_wall_armor",
        )
    elif name == "wall_front_right":
        module.connector(
            (16, 17, 0), "north", f"{PREVIEW_ID}/front_right", "minecraft:empty", "minecraft:empty",
            "zinecraft:victoria_wall_armor",
        )
    return module


def turret_core() -> Module:
    module = Module("turret_core", (32, 28, 32))
    module.cuboid((0, 0, 0), (31, 1, 31), FRAME)
    module.cuboid((0, 2, 0), (31, 3, 31), FLOOR)

    # Stepped armor approximates the low, wide sloped emplacement visible in the source.
    layers = (
        (4, 7, 1),
        (8, 11, 3),
        (12, 15, 5),
        (16, 19, 7),
        (20, 22, 10),
    )
    for bottom, top, inset in layers:
        module.cuboid((inset, bottom, inset), (31, top, 31 - inset), ARMOR)

    # Carve a walkable breech chamber after shaping the armor shell.
    module.cuboid((4, 4, 8), (26, 18, 23), AIR)
    module.cuboid((4, 3, 8), (26, 3, 23), FLOOR)
    module.cuboid((5, 4, 9), (5, 16, 22), FRAME)
    module.cuboid((25, 4, 9), (25, 16, 22), FRAME)
    for z in (10, 15, 20):
        module.block(6, 17, z, LAMP)

    # Heavy breech casing and a narrow observation/control slit.
    module.cuboid((3, 19, 8), (14, 25, 23), FRAME)
    module.cuboid((5, 22, 10), (12, 24, 21), CASING)
    module.cuboid((13, 17, 12), (14, 19, 19), GLASS)
    module.cuboid((15, 20, 10), (31, 23, 21), CASING)
    module.cuboid((28, 22, 13), (31, 27, 18), FRAME)

    # Breech controls, inspection benches and vertical access to the gun deck.
    module.cuboid((7, 4, 10), (11, 5, 12), CONTROL)
    module.cuboid((7, 4, 19), (11, 5, 21), CONTROL)
    for z in (11, 20):
        module.block(12, 5, z, LEVER)
    module.block(20, 4, 10, CRAFTING_TABLE)
    module.block(22, 4, 10, BARREL)
    module.loot_chest(24, 4, 10, "maintenance")
    module.cuboid((14, 8, 9), (24, 8, 9), CREATE_SHAFT_X)
    module.block(17, 8, 9, CREATE_LARGE_COG_X)
    module.block(22, 8, 9, CREATE_COG_X)
    module.cuboid((14, 4, 22), (24, 4, 22), CREATE_GIRDER)
    module.cuboid((24, 5, 22), (24, 12, 22), CREATE_PIPE)
    for y in range(4, 20):
        module.block(27, y, 22, LADDER)
    module.connector(
        (16, 0, 0), "down", f"{PREVIEW_ID}/turret", "minecraft:empty", "minecraft:empty",
        "zinecraft:victoria_structural_frame",
    )
    module.connector(
        (31, 10, 15), "east", f"{PREVIEW_ID}/turret_barrel", f"{PREVIEW_ID}/barrel_root",
        f"{PREVIEW_ID}/barrel_root", "zinecraft:victoria_cannon_casing",
    )
    return module


def barrel_root() -> Module:
    module = Module("barrel_root", (32, 16, 16))
    # Rear machine housing occupies the overlap with the turret core.
    module.cuboid((0, 1, 1), (11, 14, 14), FRAME)
    module.cuboid((2, 3, 0), (9, 12, 15), ARMOR)
    module.cuboid((10, 4, 4), (15, 11, 11), CASING)
    # Octagonal-looking 8x8 barrel cross-section.
    for x in range(12, 32):
        module.cuboid((x, 5, 4), (x, 10, 11), FRAME)
        module.cuboid((x, 4, 5), (x, 11, 10), FRAME)
    module.cuboid((28, 4, 4), (31, 11, 11), CASING)
    module.cuboid((29, 5, 5), (31, 10, 10), FRAME)
    module.connector(
        (0, 0, 7), "west", f"{PREVIEW_ID}/barrel_root", "minecraft:empty", "minecraft:empty",
        "zinecraft:victoria_structural_frame",
    )
    module.connector(
        (31, 5, 7), "east", f"{PREVIEW_ID}/barrel_root_muzzle", f"{PREVIEW_ID}/barrel_muzzle",
        f"{PREVIEW_ID}/barrel_muzzle", "zinecraft:victoria_cannon_casing",
    )
    return module


def barrel_muzzle() -> Module:
    module = Module("barrel_muzzle", (32, 12, 12))
    for x in range(32):
        module.cuboid((x, 3, 2), (x, 8, 9), FRAME)
        module.cuboid((x, 2, 3), (x, 9, 8), FRAME)

    # Two restrained reinforcement bands and a hollow muzzle impression.
    for start in (3, 24):
        module.cuboid((start, 2, 2), (start + 2, 9, 9), CASING)
        module.cuboid((start, 3, 3), (start + 2, 8, 8), FRAME)
    module.cuboid((30, 2, 2), (31, 9, 9), CASING)
    module.cuboid((31, 4, 4), (31, 7, 7), FRAME)
    module.connector(
        (0, 3, 5), "west", f"{PREVIEW_ID}/barrel_muzzle", "minecraft:empty", "minecraft:empty",
        "zinecraft:victoria_structural_frame",
    )
    return module


def generate() -> tuple[Module, ...]:
    return (
        wall_module("wall_rear_left", outside=False, right=False),
        wall_module("wall_rear_right", outside=False, right=True),
        wall_module("wall_front_left", outside=True, right=False),
        wall_module("wall_front_right", outside=True, right=True),
        turret_core(),
        barrel_root(),
        barrel_muzzle(),
    )


if __name__ == "__main__":
    modules = generate()
    if len({module.name for module in modules}) != len(modules):
        raise ValueError("duplicate blockout module name")
    palette_names = {name for name, _ in PALETTE}
    forbidden_placeholders = {name for name in palette_names if name.endswith("_concrete")}
    if forbidden_placeholders:
        raise ValueError(f"concrete placeholders remain: {sorted(forbidden_placeholders)}")
    required_create_blocks = {
        "create:industrial_iron_block", "create:metal_girder", "create:shaft",
        "create:cogwheel", "create:large_cogwheel", "create:fluid_pipe", "create:mechanical_pump",
    }
    if not required_create_blocks.issubset(palette_names):
        raise ValueError("Create machinery palette is incomplete")
    loot_tables = {
        str(nbt.get("LootTable"))
        for module in modules
        for nbt in module.block_nbt.values()
        if "LootTable" in nbt
    }
    expected_loot_tables = {
        f"zinecraft:chests/victoria_defence_cannon_{room}"
        for room in ("control", "maintenance", "ordnance", "supply")
    }
    if loot_tables != expected_loot_tables:
        raise ValueError(f"unexpected loot-table coverage: {sorted(loot_tables)}")
    for module in modules:
        module.write()
        print(f"{module.name}: size={module.size}, blocks={len(module.blocks)}")
