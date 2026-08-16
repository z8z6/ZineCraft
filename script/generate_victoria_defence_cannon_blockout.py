"""Generate the registered Londinium defence-cannon architecture preview.

The preview uses original nation materials, traversable service interiors and
loot-bearing logistics spaces while retaining the reviewed seven-module silhouette.
"""

from __future__ import annotations

import gzip
import struct
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any


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
SCARRED_ARMOR = 24
BLAST_ARMOR = 25
LANTERN = 26
HANGING_LANTERN = 27
CHAIN = 28
GLASS_PANE = 29
STAIR_EAST = 30
STAIR_WEST = 31
STAIR_NORTH = 32
STAIR_SOUTH = 33
TABLE_SLAB = 34
CHAIR_NORTH = 35
CHAIR_SOUTH = 36
CHAIR_EAST = 37
CHAIR_WEST = 38
BED_FOOT_EAST = 39
BED_HEAD_EAST = 40
BOOKSHELF = 41
POTTED_FERN = 42
POTTED_POPPY = 43
CREATE_GEARBOX = 44
CREATE_CLUTCH = 45
CREATE_FLYWHEEL = 46
CREATE_SPEEDOMETER = 47
CREATE_STRESSOMETER = 48
CREATE_ANALOG_LEVER = 49
CREATE_LADDER = 50
ANDESITE_CASING = 51
BRIGHT_LIGHT = 52
GRAY_CARPET = 53
RED_CARPET = 54
BLUE_WOOL = 55
GREEN_WOOL = 56
DARK_OAK_FENCE = 57
WALL_SIGN_SOUTH = 58
WALL_SIGN_NORTH = 59
TARGET = 60
ANVIL = 61
GRINDSTONE = 62
BRASS_DOOR_SOUTH_LEFT_LOWER = 63
BRASS_DOOR_SOUTH_LEFT_UPPER = 64
BRASS_DOOR_SOUTH_RIGHT_LOWER = 65
BRASS_DOOR_SOUTH_RIGHT_UPPER = 66
BRASS_DOOR_NORTH_LEFT_LOWER = 67
BRASS_DOOR_NORTH_LEFT_UPPER = 68
BRASS_DOOR_NORTH_RIGHT_LOWER = 69
BRASS_DOOR_NORTH_RIGHT_UPPER = 70
BRASS_DOOR_EAST_LEFT_LOWER = 71
BRASS_DOOR_EAST_LEFT_UPPER = 72
BRASS_DOOR_EAST_RIGHT_LOWER = 73
BRASS_DOOR_EAST_RIGHT_UPPER = 74
WALL_SIGN_EAST = 75

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
    ("zinecraft:victoria_battle_scarred_armor", None),
    ("zinecraft:victoria_blast_scarred_armor", None),
    ("minecraft:lantern", {"hanging": "false", "waterlogged": "false"}),
    ("minecraft:lantern", {"hanging": "true", "waterlogged": "false"}),
    ("minecraft:chain", {"axis": "y", "waterlogged": "false"}),
    ("minecraft:glass_pane", None),
    ("minecraft:polished_deepslate_stairs", {"facing": "east", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:polished_deepslate_stairs", {"facing": "west", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:polished_deepslate_stairs", {"facing": "north", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:polished_deepslate_stairs", {"facing": "south", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:dark_oak_slab", {"type": "top", "waterlogged": "false"}),
    ("minecraft:dark_oak_stairs", {"facing": "north", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:dark_oak_stairs", {"facing": "south", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:dark_oak_stairs", {"facing": "east", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:dark_oak_stairs", {"facing": "west", "half": "bottom", "shape": "straight", "waterlogged": "false"}),
    ("minecraft:gray_bed", {"facing": "east", "occupied": "false", "part": "foot"}),
    ("minecraft:gray_bed", {"facing": "east", "occupied": "false", "part": "head"}),
    ("minecraft:bookshelf", None),
    ("minecraft:potted_fern", None),
    ("minecraft:potted_poppy", None),
    ("create:gearbox", None),
    ("create:clutch", None),
    ("create:flywheel", None),
    ("create:speedometer", None),
    ("create:stressometer", None),
    ("create:analog_lever", None),
    ("create:andesite_ladder", {"facing": "west", "waterlogged": "false"}),
    ("create:andesite_casing", None),
    ("minecraft:sea_lantern", None),
    ("minecraft:gray_carpet", None),
    ("minecraft:red_carpet", None),
    ("minecraft:blue_wool", None),
    ("minecraft:green_wool", None),
    ("minecraft:dark_oak_fence", {"east": "false", "north": "false", "south": "false", "waterlogged": "false", "west": "false"}),
    ("minecraft:dark_oak_wall_sign", {"facing": "south", "waterlogged": "false"}),
    ("minecraft:dark_oak_wall_sign", {"facing": "north", "waterlogged": "false"}),
    ("minecraft:target", None),
    ("minecraft:anvil", {"facing": "north"}),
    ("minecraft:grindstone", {"face": "floor", "facing": "north"}),
    ("create:brass_door", {"facing": "south", "half": "lower", "hinge": "left", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "south", "half": "upper", "hinge": "left", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "south", "half": "lower", "hinge": "right", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "south", "half": "upper", "hinge": "right", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "north", "half": "lower", "hinge": "left", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "north", "half": "upper", "hinge": "left", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "north", "half": "lower", "hinge": "right", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "north", "half": "upper", "hinge": "right", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "east", "half": "lower", "hinge": "left", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "east", "half": "upper", "hinge": "left", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "east", "half": "lower", "hinge": "right", "open": "false", "powered": "false", "visible": "true"}),
    ("create:brass_door", {"facing": "east", "half": "upper", "hinge": "right", "open": "false", "powered": "false", "visible": "true"}),
    ("minecraft:dark_oak_wall_sign", {"facing": "east", "waterlogged": "false"}),
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


def named_nbt_value(name: str, value: Any) -> bytes:
    if isinstance(value, int):
        return named(3, name, int_payload(value))
    if isinstance(value, str):
        return named(8, name, string_payload(value))
    if isinstance(value, dict):
        return named(10, name, compound_payload([named_nbt_value(key, child) for key, child in value.items()]))
    if isinstance(value, list) and all(isinstance(child, str) for child in value):
        return named(9, name, list_payload(8, [string_payload(child) for child in value]))
    raise TypeError(f"unsupported NBT value for {name}: {value!r}")


def block_entry(position: tuple[int, int, int], state: int, nbt: dict[str, Any] | None) -> bytes:
    tags = [
        named(9, "pos", list_payload(3, [int_payload(value) for value in position])),
        named(3, "state", int_payload(state)),
    ]
    if nbt:
        tags.append(
            named(
                10,
                "nbt",
                compound_payload([named_nbt_value(key, value) for key, value in nbt.items()]),
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
    block_nbt: dict[tuple[int, int, int], dict[str, Any]] = field(default_factory=dict)

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

    def sign(self, x: int, y: int, z: int, state: int, lines: tuple[str, str]) -> None:
        blank = '{"text":""}'
        self.block(x, y, z, state)
        self.block_nbt[(x, y, z)] = {
            "id": "minecraft:sign",
            "front_text": {
                "messages": [
                    f'{{"text":"{lines[0]}","color":"gold"}}',
                    f'{{"text":"{lines[1]}","color":"gray"}}',
                    blank,
                    blank,
                ],
                "color": "black",
                "has_glowing_text": 1,
            },
            "back_text": {
                "messages": [blank, blank, blank, blank],
                "color": "black",
                "has_glowing_text": 0,
            },
            "is_waxed": 1,
        }

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


def hanging_light(module: Module, x: int, z: int, ceiling_y: int = 14) -> None:
    module.block(x, ceiling_y, z, CHAIN)
    module.block(x, ceiling_y - 1, z, HANGING_LANTERN)


def ceiling_light_bank(module: Module, x: int, z: int, ceiling_y: int = 14) -> None:
    """Use bright electric fixtures for work light; lanterns remain atmosphere/navigation lights."""
    for offset in (-1, 0, 1):
        module.block(x + offset, ceiling_y, z, BRIGHT_LIGHT)


def table_with_chairs(module: Module, x: int, z: int, length: int = 5) -> None:
    module.block(x, 2, z, IRON_BARS)
    module.block(x + length - 1, 2, z, IRON_BARS)
    module.cuboid((x, 3, z), (x + length - 1, 3, z), TABLE_SLAB)
    module.block(x + 1, 2, z - 1, CHAIR_SOUTH)
    module.block(x + length - 2, 2, z + 1, CHAIR_NORTH)


def conference_table(module: Module, x: int, z: int) -> None:
    """A 9x4 battle-planning table with seating on all four sides."""
    module.cuboid((x, 2, z), (x + 8, 2, z + 3), RED_CARPET)
    for leg_x in (x, x + 8):
        for leg_z in (z, z + 3):
            module.block(leg_x, 2, leg_z, DARK_OAK_FENCE)
    module.cuboid((x, 3, z), (x + 8, 3, z + 3), TABLE_SLAB)
    for chair_x in (x + 1, x + 3, x + 5, x + 7):
        module.block(chair_x, 2, z - 1, CHAIR_SOUTH)
        module.block(chair_x, 2, z + 4, CHAIR_NORTH)
    module.block(x - 1, 2, z + 1, CHAIR_EAST)
    module.block(x + 9, 2, z + 2, CHAIR_WEST)


def tactical_globe(module: Module, x: int, z: int) -> None:
    """Small stylised Terra globe for spatial planning, not a claim about a canonical map."""
    module.block(x, 2, z, ANDESITE_CASING)
    module.block(x, 3, z, DARK_OAK_FENCE)
    for position in ((x, 5, z), (x - 1, 5, z), (x + 1, 5, z),
                     (x, 4, z), (x, 6, z), (x, 5, z - 1), (x, 5, z + 1)):
        module.block(*position, BLUE_WOOL)
    for position in ((x - 1, 5, z), (x, 6, z), (x, 5, z + 1)):
        module.block(*position, GREEN_WOOL)


def brass_double_door(module: Module, x: int, z: int, facing: str) -> None:
    states = {
        "south": (BRASS_DOOR_SOUTH_LEFT_LOWER, BRASS_DOOR_SOUTH_LEFT_UPPER,
                  BRASS_DOOR_SOUTH_RIGHT_LOWER, BRASS_DOOR_SOUTH_RIGHT_UPPER),
        "north": (BRASS_DOOR_NORTH_LEFT_LOWER, BRASS_DOOR_NORTH_LEFT_UPPER,
                  BRASS_DOOR_NORTH_RIGHT_LOWER, BRASS_DOOR_NORTH_RIGHT_UPPER),
        "east": (BRASS_DOOR_EAST_LEFT_LOWER, BRASS_DOOR_EAST_LEFT_UPPER,
                 BRASS_DOOR_EAST_RIGHT_LOWER, BRASS_DOOR_EAST_RIGHT_UPPER),
    }[facing]
    if facing == "east":
        positions = ((x, 2, z), (x, 3, z), (x, 2, z + 1), (x, 3, z + 1))
    else:
        positions = ((x, 2, z), (x, 3, z), (x + 1, 2, z), (x + 1, 3, z))
    for position, state in zip(positions, states):
        module.block(*position, state)


def east_staircase(module: Module, start_x: int, z: int) -> None:
    """Cut a full-height stairwell and positively join it to the y=17 wall walk."""
    module.cuboid((start_x, 2, z - 1), (start_x + 17, 16, z + 1), AIR)
    for step in range(15):
        x = start_x + step
        y = 2 + step
        module.block(x, y, z, STAIR_EAST)
        if y + 1 < module.size[1]:
            module.block(x, y + 1, z, AIR)
        if y + 2 < module.size[1]:
            module.block(x, y + 2, z, AIR)
        if z + 1 < module.size[2]:
            module.block(x, y + 1, z + 1, IRON_BARS)
    module.cuboid((start_x + 14, 17, z - 1), (start_x + 18, 17, z + 1), ANDESITE_CASING)
    for x in range(start_x + 14, start_x + 19):
        module.block(x, 18, z - 1, IRON_BARS)
        module.block(x, 18, z + 1, IRON_BARS)
    module.block(start_x + 18, 18, z, BRIGHT_LIGHT)


def roof_service_detail(module: Module, lane_start: int, lane_end: int, stair_x: int, stair_z: int) -> None:
    """Rail the roof walk and add believable maintenance storage and powered lighting."""
    landing_x = range(stair_x + 13, stair_x + 20)
    for x in range(1, 29):
        if not (x in landing_x and lane_start - 1 <= stair_z <= lane_end + 1):
            module.block(x, 18, lane_start, IRON_BARS)
            module.block(x, 18, lane_end, IRON_BARS)
    for x in (2, 10, 18, 26):
        module.block(x, 18, lane_start, CREATE_GIRDER)
        module.block(x, 19, lane_start, BRIGHT_LIGHT)
    crate_z = lane_start + 2
    for x in (5, 7, 23):
        module.block(x, 18, crate_z, BARREL)
    module.loot_chest(25, 18, crate_z, "supply")
    module.block(12, 18, crate_z, ANVIL)
    module.block(14, 18, crate_z, GRINDSTONE)
    module.block(18, 18, crate_z, TARGET)


def open_boundary(module: Module, side: str, center: int) -> None:
    if side in ("east", "west"):
        x = 31 if side == "east" else 0
        module.cuboid((x, 2, center - 2), (x, 6, center + 1), AIR)
        module.cuboid((x, 7, center - 2), (x, 7, center + 1), FRAME)
    else:
        z = 31 if side == "south" else 0
        module.cuboid((center - 2, 2, z), (center + 1, 6, z), AIR)
        module.cuboid((center - 2, 7, z), (center + 1, 7, z), FRAME)


def window(module: Module, side: str, start: int, end: int) -> None:
    if side in ("east", "west"):
        x = 31 if side == "east" else 0
        module.cuboid((x, 5, start), (x, 8, end), GLASS_PANE)
        for z in range(start, end + 1, 3):
            module.cuboid((x, 5, z), (x, 8, z), FRAME)
    else:
        z = 31 if side == "south" else 0
        module.cuboid((start, 5, z), (end, 8, z), GLASS_PANE)
        for x in range(start, end + 1, 3):
            module.cuboid((x, 5, z), (x, 8, z), FRAME)


def stepped_buttress(module: Module, side: str, center: int) -> None:
    """Add a three-stage wall brace so long façades do not read as flat boxes."""
    for depth, top in ((0, 13), (1, 10), (2, 7)):
        material = SCARRED_ARMOR if depth == 0 else ARMOR
        if side == "north":
            module.cuboid((center - 1, 2, depth), (center + 1, top, depth), material)
        elif side == "south":
            module.cuboid((center - 1, 2, 31 - depth), (center + 1, top, 31 - depth), material)
        elif side == "west":
            module.cuboid((depth, 2, center - 1), (depth, top, center + 1), material)
        else:
            module.cuboid((31 - depth, 2, center - 1), (31 - depth, top, center + 1), material)


def add_battle_damage(module: Module) -> None:
    # Deterministic ballistic pitting; only exterior shell states are replaced.
    for x, y, z in ((4, 9, 0), (11, 6, 31), (20, 12, 0), (27, 8, 31),
                    (0, 10, 6), (31, 7, 12), (0, 5, 24), (31, 13, 27)):
        if module.blocks.get((x, y, z)) in (ARMOR, CASING):
            module.block(x, y, z, SCARRED_ARMOR)

    breach_by_module = {
        "wall_rear_left": (24, 11, 0, "north"),
        "wall_front_right": (31, 11, 5, "east"),
    }
    breach = breach_by_module.get(module.name)
    if breach:
        cx, cy, cz, side = breach
        for first in range(-2, 3):
            for second in range(-2, 3):
                distance = abs(first) + abs(second)
                if side == "north":
                    position = (cx + first, cy + second, cz)
                else:
                    position = (cx, cy + second, cz + first)
                module.block(*position, AIR if distance <= 1 else BLAST_ARMOR)

    # Broken parapet segments and a roof-shell crater make the mass less pristine.
    if module.name == "wall_rear_right":
        module.cuboid((0, 17, 6), (2, 18, 10), AIR)
        module.cuboid((1, 16, 6), (3, 16, 10), BLAST_ARMOR)
    if module.name == "wall_front_left":
        for x in range(24, 29):
            for z in range(24, 29):
                distance = abs(x - 26) + abs(z - 26)
                if distance <= 2:
                    module.block(x, 15, z, AIR)
                    module.block(x, 16, z, AIR)
                elif distance == 3:
                    module.block(x, 16, z, BLAST_ARMOR)


def wall_module(name: str, outside: bool, right: bool) -> Module:
    module = Module(name, (32, 20, 32))
    # Two-block foundation, hollow service volume and armored roof.
    module.cuboid((0, 0, 0), (31, 0, 31), FRAME)
    module.cuboid((0, 1, 0), (31, 1, 31), FLOOR)
    module.cuboid((1, 1, 1), (30, 1, 30), ANDESITE_CASING)
    module.cuboid((1, 2, 1), (30, 14, 30), AIR)
    module.cuboid((0, 2, 0), (0, 15, 31), ARMOR)
    module.cuboid((31, 2, 0), (31, 15, 31), ARMOR)
    module.cuboid((1, 2, 0), (30, 15, 0), ARMOR)
    module.cuboid((1, 2, 31), (30, 15, 31), ARMOR)
    module.cuboid((0, 15, 0), (31, 16, 31), CASING)

    # Open structural grid: ribs are columns and overhead beams, not room-blocking walls.
    for x in (7, 15, 23):
        for z in (1, 8, 15, 23, 30):
            module.cuboid((x, 2, z), (x, 14, z), FRAME)
        module.cuboid((x, 14, 1), (x, 14, 30), CREATE_GIRDER)
    module.cuboid((1, 2, 14), (30, 7, 17), AIR)
    module.cuboid((1, 1, 14), (30, 1, 17), FLOOR)
    for x in (4, 11, 19, 27):
        hanging_light(module, x, 15)
        ceiling_light_bank(module, x, 16)

    # Every quadrant has a real staircase to the roof walk.
    staircase_positions = {
        "wall_rear_left": (2, 20),
        "wall_rear_right": (2, 10),
        "wall_front_left": (2, 24),
        "wall_front_right": (12, 10),
    }
    # Four modules form one continuous interior ring through paired boundary openings.
    boundary_openings = {
        "wall_rear_left": (("east", 16), ("south", 16)),
        "wall_rear_right": (("north", 16), ("east", 16)),
        "wall_front_left": (("west", 16), ("south", 16)),
        "wall_front_right": (("north", 16), ("west", 16), ("east", 25)),
    }
    for side, center in boundary_openings[name]:
        open_boundary(module, side, center)

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

    for x in (4, 12, 20, 28):
        if lane_start <= 6 <= lane_end:
            module.block(x, 18, 6, BRIGHT_LIGHT)
        if lane_start <= 24 <= lane_end:
            module.block(x, 18, 24, BRIGHT_LIGHT)

    if name == "wall_rear_left":
        window(module, "north", 3, 12)
        window(module, "west", 19, 27)
        # Blast-separated Originium ordnance magazine: racks, sealed crates and issue chest.
        module.cuboid((2, 2, 3), (13, 8, 3), ANDESITE_CASING)
        module.cuboid((2, 2, 12), (13, 8, 12), ANDESITE_CASING)
        module.cuboid((3, 1, 4), (12, 1, 11), ANDESITE_CASING)
        module.cuboid((7, 2, 3), (9, 5, 3), AIR)
        brass_double_door(module, 7, 3, "south")
        for x in (4, 7, 10):
            for z in (5, 10):
                module.block(x, 2, z, BARREL)
                module.block(x, 3, z, BARREL)
        module.loot_chest(12, 2, 8, "ordnance")
        module.cuboid((13, 3, 6), (13, 6, 10), IRON_BARS)
        hanging_light(module, 8, 8, 10)
        ceiling_light_bank(module, 8, 8, 10)
        module.sign(10, 5, 4, WALL_SIGN_SOUTH, ("军械储存区", "MAGAZINE"))
        # Operations room: large battle-planning table, records and Terra globe.
        module.cuboid((18, 2, 3), (29, 9, 3), ANDESITE_CASING)
        module.cuboid((22, 2, 3), (24, 5, 3), AIR)
        brass_double_door(module, 22, 3, "south")
        module.cuboid((19, 2, 5), (27, 2, 10), GRAY_CARPET)
        conference_table(module, 19, 6)
        tactical_globe(module, 29, 6)
        module.cuboid((28, 2, 10), (29, 4, 11), BOOKSHELF)
        module.block(28, 2, 5, POTTED_FERN)
        module.sign(21, 6, 4, WALL_SIGN_SOUTH, ("作战会议室", "OPERATIONS"))
        module.sign(26, 7, 4, WALL_SIGN_SOUTH, ("保持警戒", "KEEP WATCH"))
        hanging_light(module, 23, 8)
        ceiling_light_bank(module, 23, 8)
    elif name == "wall_rear_right":
        window(module, "south", 19, 28)
        window(module, "west", 3, 11)
        # Maintenance workshop and coolant service point.
        module.cuboid((18, 1, 20), (29, 1, 29), ANDESITE_CASING)
        module.cuboid((19, 2, 28), (28, 4, 29), ANDESITE_CASING)
        module.cuboid((25, 2, 28), (27, 4, 29), AIR)
        brass_double_door(module, 25, 29, "north")
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
        module.block(20, 3, 21, CREATE_GEARBOX)
        module.block(21, 3, 21, CREATE_CLUTCH)
        module.block(22, 3, 21, CREATE_FLYWHEEL)
        module.block(23, 3, 21, CREATE_SPEEDOMETER)
        module.block(24, 3, 21, CREATE_STRESSOMETER)
        hanging_light(module, 23, 26)
        ceiling_light_bank(module, 23, 25)
        table_with_chairs(module, 3, 24, 5)
        module.block(10, 3, 27, POTTED_FERN)
        module.sign(23, 4, 27, WALL_SIGN_NORTH, ("动力维修间", "MAINTENANCE"))
    elif name == "wall_front_left":
        window(module, "north", 3, 12)
        window(module, "east", 3, 11)
        # Fire-control room: consoles face the cannon through a casing-and-glass partition.
        module.cuboid((17, 1, 3), (29, 1, 12), ANDESITE_CASING)
        module.cuboid((17, 2, 3), (17, 10, 12), ANDESITE_CASING)
        module.cuboid((17, 5, 4), (17, 8, 11), GLASS)
        module.cuboid((17, 2, 7), (17, 5, 8), AIR)
        brass_double_door(module, 17, 7, "east")
        module.cuboid((20, 2, 5), (27, 3, 6), CONTROL)
        module.cuboid((20, 2, 9), (27, 3, 10), CONTROL)
        for x in (21, 24, 27):
            module.block(x, 4, 6, LEVER)
            module.block(x, 4, 10, LEVER)
            module.block(x, 3, 7, CHAIR_SOUTH)
        module.block(22, 4, 5, CREATE_ANALOG_LEVER)
        module.block(25, 4, 5, CREATE_SPEEDOMETER)
        module.block(28, 4, 5, CREATE_STRESSOMETER)
        module.loot_chest(28, 2, 11, "control")
        hanging_light(module, 23, 8)
        ceiling_light_bank(module, 23, 8)
        module.sign(18, 6, 3, WALL_SIGN_EAST, ("火控室", "FIRE CONTROL"))
        table_with_chairs(module, 4, 8, 6)
        module.cuboid((12, 2, 4), (13, 4, 5), BOOKSHELF)
        module.block(11, 3, 5, POTTED_POPPY)
    elif name == "wall_front_right":
        window(module, "south", 3, 12)
        window(module, "east", 20, 28)
        # Crew ready/supply room and roof access.
        module.cuboid((3, 1, 19), (13, 1, 29), ANDESITE_CASING)
        module.cuboid((3, 2, 19), (13, 2, 29), GRAY_CARPET)
        module.cuboid((2, 2, 18), (14, 8, 18), ANDESITE_CASING)
        module.cuboid((7, 2, 18), (9, 5, 18), AIR)
        brass_double_door(module, 7, 18, "south")
        module.cuboid((4, 2, 27), (11, 3, 28), ANDESITE_CASING)
        module.block(5, 2, 25, BARREL)
        module.block(7, 2, 25, BARREL)
        module.loot_chest(10, 2, 25, "supply")
        for x, z in ((3, 20), (3, 24), (8, 20)):
            module.block(x, 2, z, BED_FOOT_EAST)
            module.block(x + 1, 2, z, BED_HEAD_EAST)
        table_with_chairs(module, 20, 24, 6)
        module.block(28, 3, 27, POTTED_FERN)
        module.block(28, 3, 23, POTTED_POPPY)
        hanging_light(module, 8, 23)
        hanging_light(module, 23, 24)
        ceiling_light_bank(module, 8, 23)
        ceiling_light_bank(module, 23, 24)
        module.sign(10, 5, 19, WALL_SIGN_SOUTH, ("乘员待命区", "CREW READY"))

    buttresses = {
        "wall_rear_left": (("north", 16), ("west", 8)),
        "wall_rear_right": (("south", 8), ("west", 20)),
        "wall_front_left": (("north", 20), ("east", 20)),
        "wall_front_right": (("south", 20), ("east", 8)),
    }
    for side, center in buttresses[name]:
        stepped_buttress(module, side, center)

    # Chamfered upper corners, sparse damaged plates and controlled breaches.
    for y in range(12, 17):
        for x in range(3):
            for z in range(3 - x):
                for px, pz in ((x, z), (31 - x, z), (x, 31 - z), (31 - x, 31 - z)):
                    module.block(px, y, pz, AIR if x + z < 2 else SCARRED_ARMOR)
    add_battle_damage(module)

    # Apply access and roof furniture last so shell, parapet and damage passes cannot seal the exit.
    stair_x, stair_z = staircase_positions[name]
    east_staircase(module, stair_x, stair_z)
    roof_service_detail(module, lane_start, lane_end, stair_x, stair_z)

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
        hanging_light(module, 7, z, 18)

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
    module.block(15, 8, 10, CREATE_GEARBOX)
    module.block(19, 8, 10, CREATE_CLUTCH)
    module.block(23, 8, 10, CREATE_FLYWHEEL)
    module.block(16, 9, 10, CREATE_SPEEDOMETER)
    module.block(20, 9, 10, CREATE_STRESSOMETER)
    module.cuboid((14, 4, 22), (24, 4, 22), CREATE_GIRDER)
    module.cuboid((24, 5, 22), (24, 12, 22), CREATE_PIPE)
    module.block(24, 9, 22, CREATE_PUMP)
    # Full stair flight from the breech chamber to the upper gun deck.
    for step in range(15):
        z = 8 + step
        y = 4 + step
        module.block(23, y, z, STAIR_SOUTH)
        if y + 1 < 28:
            module.block(23, y + 1, z, AIR)
        if y + 2 < 28:
            module.block(23, y + 2, z, AIR)
        module.block(24, y + 1, z, IRON_BARS)
    module.cuboid((22, 19, 23), (24, 19, 25), FLOOR)

    # Exterior service lights and non-uniform battle wear.
    for x, z in ((2, 2), (2, 29), (28, 29)):
        module.block(x, 8, z, LANTERN)
    for position in ((1, 6, 4), (4, 10, 28), (8, 14, 6), (12, 19, 24), (30, 21, 18)):
        if module.blocks.get(position) in (ARMOR, CASING):
            module.block(*position, SCARRED_ARMOR)
    module.cuboid((6, 13, 5), (8, 15, 5), BLAST_ARMOR)
    module.block(7, 14, 5, AIR)
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
    # Illuminated inspection points and visible recoil machinery.
    module.block(5, 15, 3, LANTERN)
    module.block(5, 15, 12, LANTERN)
    module.block(13, 12, 5, LANTERN)
    module.cuboid((2, 2, 2), (9, 2, 2), CREATE_SHAFT_X)
    module.block(4, 2, 3, CREATE_GEARBOX)
    module.block(7, 2, 3, CREATE_FLYWHEEL)
    for position in ((3, 8, 0), (8, 11, 15), (15, 4, 8), (28, 11, 7)):
        if module.blocks.get(position) in (ARMOR, CASING, FRAME):
            module.block(*position, SCARRED_ARMOR)
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
    for x in (4, 25, 30):
        module.block(x, 10, 3, LANTERN)
        module.block(x, 10, 8, LANTERN)
    for position in ((6, 4, 3), (13, 9, 6), (20, 3, 8), (27, 8, 9), (30, 5, 2)):
        if module.blocks.get(position) in (CASING, FRAME):
            module.block(*position, SCARRED_ARMOR)
    module.block(31, 8, 8, BLAST_ARMOR)
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
        "create:gearbox", "create:clutch", "create:flywheel", "create:speedometer",
        "create:stressometer", "create:analog_lever", "create:andesite_casing", "create:brass_door",
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
    module_by_name = {module.name: module for module in modules}
    doorway_checks = {
        "wall_rear_left": ((31, 3, 15), (15, 3, 31)),
        "wall_rear_right": ((15, 3, 0), (31, 3, 15)),
        "wall_front_left": ((0, 3, 15), (15, 3, 31)),
        "wall_front_right": ((15, 3, 0), (0, 3, 15), (31, 3, 24)),
    }
    for name, positions in doorway_checks.items():
        module = module_by_name[name]
        if any(module.blocks.get(position) != AIR for position in positions):
            raise ValueError(f"{name}: a module-boundary doorway is blocked")
        stair_count = sum(state == STAIR_EAST for state in module.blocks.values())
        if stair_count < 15:
            raise ValueError(f"{name}: roof staircase is incomplete")
        stair_x, stair_z = {
            "wall_rear_left": (2, 20), "wall_rear_right": (2, 10),
            "wall_front_left": (2, 24), "wall_front_right": (12, 10),
        }[name]
        if module.blocks.get((stair_x + 14, 16, stair_z)) != STAIR_EAST:
            raise ValueError(f"{name}: staircase does not reach the roof elevation")
        if module.blocks.get((stair_x + 15, 17, stair_z)) != ANDESITE_CASING:
            raise ValueError(f"{name}: staircase roof landing is missing")
        light_count = sum(state in (LANTERN, HANGING_LANTERN, LAMP, BRIGHT_LIGHT) for state in module.blocks.values())
        if light_count < 15:
            raise ValueError(f"{name}: interior or roof lighting is insufficient")
        if sum(state == IRON_BARS for state in module.blocks.values()) < 50:
            raise ValueError(f"{name}: roof or stair railings are insufficient")
        if sum(state == ANDESITE_CASING for state in module.blocks.values()) < 500:
            raise ValueError(f"{name}: Andesite Casing interior finish is insufficient")
        damage_count = sum(state in (SCARRED_ARMOR, BLAST_ARMOR) for state in module.blocks.values())
        if damage_count < 4:
            raise ValueError(f"{name}: battle-wear distribution is insufficient")
    if sum(state in (BED_FOOT_EAST, BED_HEAD_EAST) for state in module_by_name["wall_front_right"].blocks.values()) < 6:
        raise ValueError("crew quarters are missing beds")
    if sum(state == TABLE_SLAB for module in modules for state in module.blocks.values()) < 20:
        raise ValueError("furnished table surfaces are incomplete")
    if sum(state in (BLUE_WOOL, GREEN_WOOL) for module in modules for state in module.blocks.values()) < 7:
        raise ValueError("operations-room Terra globe is incomplete")
    if sum(BRASS_DOOR_SOUTH_LEFT_LOWER <= state <= BRASS_DOOR_EAST_RIGHT_UPPER
           for module in modules for state in module.blocks.values()) < 20:
        raise ValueError("room brass-door coverage is incomplete")
    sign_count = sum(
        nbt.get("id") == "minecraft:sign"
        for module in modules
        for nbt in module.block_nbt.values()
    )
    if sign_count < 5:
        raise ValueError("room signage is incomplete")
    for module in modules:
        for (x, y, z), nbt in module.block_nbt.items():
            if "LootTable" not in nbt:
                continue
            if y not in (2, 4, 18):
                raise ValueError(f"{module.name}: unexpected loot chest elevation at {(x, y, z)}")
            if module.blocks.get((x, y - 1, z)) not in (FLOOR, ANDESITE_CASING):
                raise ValueError(f"{module.name}: loot chest lacks a finished floor at {(x, y, z)}")
    if sum(state in (CREATE_GEARBOX, CREATE_CLUTCH, CREATE_FLYWHEEL, CREATE_SPEEDOMETER,
                     CREATE_STRESSOMETER, CREATE_ANALOG_LEVER) for module in modules for state in module.blocks.values()) < 12:
        raise ValueError("Create machinery detail is insufficient")
    for module in modules:
        module.write()
        print(f"{module.name}: size={module.size}, blocks={len(module.blocks)}")
