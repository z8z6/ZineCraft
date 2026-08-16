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
CARTOGRAPHY_TABLE = 76
LECTERN_SOUTH = 77
SMOKER_NORTH = 78
VICTORIA_BANNER_SOUTH = 79
VICTORIA_BANNER_NORTH = 80
VICTORIA_BANNER_EAST = 81
VICTORIA_BANNER_WEST = 82

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
    ("minecraft:cartography_table", None),
    ("minecraft:lectern", {"facing": "south", "has_book": "false", "powered": "false"}),
    ("minecraft:smoker", {"facing": "north", "lit": "false"}),
    ("minecraft:red_wall_banner", {"facing": "south"}),
    ("minecraft:red_wall_banner", {"facing": "north"}),
    ("minecraft:red_wall_banner", {"facing": "east"}),
    ("minecraft:red_wall_banner", {"facing": "west"}),
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
    if isinstance(value, list) and all(isinstance(child, dict) for child in value):
        return named(
            9,
            name,
            list_payload(
                10,
                [compound_payload([named_nbt_value(key, nested) for key, nested in child.items()]) for child in value],
            ),
        )
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
        position = (x, y, z)
        if self.blocks.get(position) != material:
            self.block_nbt.pop(position, None)
        self.blocks[position] = material

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

    def victoria_banner(self, x: int, y: int, z: int, state: int) -> None:
        self.block(x, y, z, state)
        self.block_nbt[(x, y, z)] = {
            "id": "minecraft:banner",
            "CustomName": '{"text":"Victoria field banner","italic":false}',
            "patterns": [
                {"pattern": "minecraft:border", "color": "black"},
                {"pattern": "minecraft:rhombus", "color": "yellow"},
                {"pattern": "minecraft:circle", "color": "black"},
                {"pattern": "minecraft:triangle_top", "color": "yellow"},
            ],
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


@dataclass(frozen=True)
class RoomSpec:
    module: str
    room_id: str
    zh_name: str
    en_name: str
    purpose: str
    bounds: tuple[int, int, int, int]
    door_side: str
    door_axis: int
    loot_table: str
    exterior_window: bool
    floor_y: int = 1
    ceiling_y: int = 7

    @property
    def width(self) -> int:
        return self.bounds[2] - self.bounds[0] + 1

    @property
    def depth(self) -> int:
        return self.bounds[3] - self.bounds[1] + 1


ROOM_SPECS: tuple[RoomSpec, ...] = (
    RoomSpec("wall_rear_left", "ordnance_magazine", "源石军械库", "ORDNANCE MAGAZINE",
             "隔离存放源石工业材料、发射药与制式军械", (1, 1, 14, 13), "south", 7, "ordnance", False),
    RoomSpec("wall_rear_left", "operations_room", "作战会议室", "OPERATIONS ROOM",
             "研究城防态势、航道与射界并保存作战文件", (17, 1, 30, 13), "south", 23, "planning", True),
    RoomSpec("wall_rear_right", "auxiliary_stores", "备件库", "AUXILIARY STORES",
             "存放机械备件、管线、工具与应急材料", (1, 18, 14, 30), "north", 7, "maintenance", False),
    RoomSpec("wall_rear_right", "maintenance_shop", "动力维修间", "MAINTENANCE SHOP",
             "检修传动、冷却、泵与炮座动力系统", (17, 18, 30, 30), "north", 23, "maintenance", True),
    RoomSpec("wall_front_left", "signals_archive", "通信档案室", "SIGNALS ARCHIVE",
             "整理值勤记录、通信资料与路线图", (1, 1, 14, 13), "south", 7, "planning", True),
    RoomSpec("wall_front_left", "fire_control", "火控室", "FIRE CONTROL",
             "观测、测距、计算射击诸元并控制城防炮", (17, 1, 30, 13), "south", 23, "control", True),
    RoomSpec("wall_front_right", "crew_quarters", "乘员待命室", "CREW QUARTERS",
             "炮组轮值、休息、更衣与个人补给", (1, 18, 14, 30), "north", 7, "supply", True),
    RoomSpec("wall_front_right", "mess_supply", "餐务补给室", "MESS AND SUPPLY",
             "配餐、短时集结和日常补给发放", (17, 18, 30, 30), "north", 23, "supply", True),
    RoomSpec("wall_rear_left", "command_gallery", "城防指挥厅", "DEFENCE COMMAND",
             "汇总各炮位状态并组织城墙防御", (1, 1, 30, 13), "south", 15,
             "planning", True, 8, 14),
    RoomSpec("wall_rear_right", "engineering_gallery", "工程控制厅", "ENGINEERING CONTROL",
             "监控动力、冷却和结构负载", (1, 18, 30, 30), "north", 15,
             "maintenance", True, 8, 14),
    RoomSpec("wall_front_left", "rangefinding_gallery", "测距观测厅", "RANGEFINDING GALLERY",
             "进行远距观测、测绘与火控复核", (1, 1, 30, 13), "south", 15,
             "control", True, 8, 14),
    RoomSpec("wall_front_right", "reserve_barracks", "预备炮组室", "RESERVE GUN CREW",
             "容纳预备炮组、医疗物资和换班装备", (1, 18, 30, 30), "north", 15,
             "supply", True, 8, 14),
    RoomSpec("turret_core", "breech_chamber", "后膛检修舱", "BREECH CHAMBER",
             "装填、检查后膛并维护炮座传动与冷却系统", (4, 8, 26, 23), "north", 15,
             "maintenance", False, 3, 12),
)


def hanging_light(module: Module, x: int, z: int, ceiling_y: int = 14) -> None:
    module.block(x, ceiling_y, z, CHAIN)
    module.block(x, ceiling_y - 1, z, HANGING_LANTERN)


def ceiling_light_bank(module: Module, x: int, z: int, ceiling_y: int = 14) -> None:
    """Use bright electric fixtures for work light; lanterns remain atmosphere/navigation lights."""
    for offset in (-1, 0, 1):
        module.block(x + offset, ceiling_y, z, BRIGHT_LIGHT)


def table_with_chairs(module: Module, x: int, z: int, length: int = 5, floor_y: int = 1) -> None:
    module.block(x, floor_y + 1, z, IRON_BARS)
    module.block(x + length - 1, floor_y + 1, z, IRON_BARS)
    module.cuboid((x, floor_y + 2, z), (x + length - 1, floor_y + 2, z), TABLE_SLAB)
    module.block(x + 1, floor_y + 1, z - 1, CHAIR_SOUTH)
    module.block(x + length - 2, floor_y + 1, z + 1, CHAIR_NORTH)


def conference_table(module: Module, x: int, z: int, floor_y: int = 1) -> None:
    """A 9x4 battle-planning table with seating on all four sides."""
    module.cuboid((x, floor_y + 1, z), (x + 8, floor_y + 1, z + 3), RED_CARPET)
    for leg_x in (x, x + 8):
        for leg_z in (z, z + 3):
            module.block(leg_x, floor_y + 1, leg_z, DARK_OAK_FENCE)
    module.cuboid((x, floor_y + 2, z), (x + 8, floor_y + 2, z + 3), TABLE_SLAB)
    for chair_x in (x + 1, x + 3, x + 5, x + 7):
        module.block(chair_x, floor_y + 1, z - 1, CHAIR_SOUTH)
        module.block(chair_x, floor_y + 1, z + 4, CHAIR_NORTH)
    module.block(x - 1, floor_y + 1, z + 1, CHAIR_EAST)
    module.block(x + 9, floor_y + 1, z + 2, CHAIR_WEST)


def tactical_globe(module: Module, x: int, z: int, floor_y: int = 1) -> None:
    """Small stylised Terra globe for spatial planning, not a claim about a canonical map."""
    module.block(x, floor_y + 1, z, ANDESITE_CASING)
    module.block(x, floor_y + 2, z, DARK_OAK_FENCE)
    for position in ((x, floor_y + 4, z), (x - 1, floor_y + 4, z), (x + 1, floor_y + 4, z),
                     (x, floor_y + 3, z), (x, floor_y + 5, z),
                     (x, floor_y + 4, z - 1), (x, floor_y + 4, z + 1)):
        module.block(*position, BLUE_WOOL)
    for position in ((x - 1, floor_y + 4, z), (x, floor_y + 5, z), (x, floor_y + 4, z + 1)):
        module.block(*position, GREEN_WOOL)


def brass_double_door(module: Module, x: int, z: int, facing: str, y: int = 2) -> None:
    states = {
        "south": (BRASS_DOOR_SOUTH_LEFT_LOWER, BRASS_DOOR_SOUTH_LEFT_UPPER,
                  BRASS_DOOR_SOUTH_RIGHT_LOWER, BRASS_DOOR_SOUTH_RIGHT_UPPER),
        "north": (BRASS_DOOR_NORTH_LEFT_LOWER, BRASS_DOOR_NORTH_LEFT_UPPER,
                  BRASS_DOOR_NORTH_RIGHT_LOWER, BRASS_DOOR_NORTH_RIGHT_UPPER),
        "east": (BRASS_DOOR_EAST_LEFT_LOWER, BRASS_DOOR_EAST_LEFT_UPPER,
                 BRASS_DOOR_EAST_RIGHT_LOWER, BRASS_DOOR_EAST_RIGHT_UPPER),
    }[facing]
    if facing == "east":
        positions = ((x, y, z), (x, y + 1, z), (x, y, z + 1), (x, y + 1, z + 1))
    else:
        positions = ((x, y, z), (x, y + 1, z), (x + 1, y, z), (x + 1, y + 1, z))
    for position, state in zip(positions, states):
        module.block(*position, state)


def room_light_positions(spec: RoomSpec) -> tuple[tuple[int, int, int], ...]:
    x0, z0, x1, z1 = spec.bounds
    return tuple(
        (x, spec.ceiling_y, z)
        for x in range(x0 + 2, x1, 4)
        for z in range(z0 + 2, z1, 4)
    )


def build_room_shell(module: Module, spec: RoomSpec) -> None:
    """Build a closed, labelled room connected directly to the central corridor."""
    x0, z0, x1, z1 = spec.bounds
    floor_y = spec.floor_y
    ceiling_y = spec.ceiling_y
    module.cuboid((x0, floor_y, z0), (x1, floor_y, z1), ANDESITE_CASING)
    module.cuboid((x0, floor_y + 1, z0), (x1, ceiling_y, z0), ANDESITE_CASING)
    module.cuboid((x0, floor_y + 1, z1), (x1, ceiling_y, z1), ANDESITE_CASING)
    module.cuboid((x0, floor_y + 1, z0), (x0, ceiling_y, z1), ANDESITE_CASING)
    module.cuboid((x1, floor_y + 1, z0), (x1, ceiling_y, z1), ANDESITE_CASING)
    module.cuboid((x0, ceiling_y, z0), (x1, ceiling_y, z1), ANDESITE_CASING)

    if spec.exterior_window:
        window_z = z0 if z0 == 1 else z1
        exterior_z = 0 if z0 == 1 else 31
        window_bottom = floor_y + 3
        window_top = ceiling_y - 1
        module.cuboid((x0 + 3, window_bottom, window_z), (x1 - 3, window_top, window_z), GLASS_PANE)
        module.cuboid((x0 + 3, window_bottom, exterior_z), (x1 - 3, window_top, exterior_z), GLASS_PANE)
        for x in (x0 + 3, x1 - 3):
            module.cuboid((x, window_bottom, window_z), (x, window_top, window_z), CREATE_GIRDER)
            module.cuboid((x, window_bottom, exterior_z), (x, window_top, exterior_z), FRAME)

    if spec.door_side == "south":
        module.cuboid((spec.door_axis, floor_y + 1, z1), (spec.door_axis + 1, floor_y + 3, z1), AIR)
        brass_double_door(module, spec.door_axis, z1, "south", floor_y + 1)
        module.sign(spec.door_axis + 3, floor_y + 4, z1 + 1, WALL_SIGN_SOUTH, (spec.zh_name, spec.en_name))
    elif spec.door_side == "north":
        module.cuboid((spec.door_axis, floor_y + 1, z0), (spec.door_axis + 1, floor_y + 3, z0), AIR)
        brass_double_door(module, spec.door_axis, z0, "north", floor_y + 1)
        module.sign(spec.door_axis + 3, floor_y + 4, z0 - 1, WALL_SIGN_NORTH, (spec.zh_name, spec.en_name))
    else:
        raise ValueError(f"{spec.room_id}: unsupported corridor door side {spec.door_side}")

    for position in room_light_positions(spec):
        module.block(*position, BRIGHT_LIGHT)


def room_specs_for(module_name: str) -> tuple[RoomSpec, ...]:
    return tuple(spec for spec in ROOM_SPECS if spec.module == module_name)


def add_room_wall_decor(module: Module, spec: RoomSpec) -> None:
    """Hang an original heraldic Victoria tapestry on a supported interior wall."""
    x0, z0, x1, z1 = spec.bounds
    y = spec.floor_y + 3
    if z0 == 1:
        module.victoria_banner(x0 + 2, y, z0 + 1, VICTORIA_BANNER_SOUTH)
        if spec.floor_y > 1:
            module.victoria_banner(x1 - 2, y, z0 + 1, VICTORIA_BANNER_SOUTH)
    else:
        module.victoria_banner(x0 + 2, y, z1 - 1, VICTORIA_BANNER_NORTH)
        if spec.floor_y > 1:
            module.victoria_banner(x1 - 2, y, z1 - 1, VICTORIA_BANNER_NORTH)


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
    module.cuboid((start_x + 15, 17, z - 1), (start_x + 19, 17, z + 1), ANDESITE_CASING)
    module.cuboid((start_x + 14, 17, z - 1), (start_x + 14, 18, z + 1), AIR)
    for x in range(start_x + 15, start_x + 20):
        module.block(x, 18, z - 1, IRON_BARS)
        module.block(x, 18, z + 1, IRON_BARS)
    module.block(start_x + 19, 18, z, BRIGHT_LIGHT)


def upper_stair_landing(module: Module, start_x: int, z: int) -> None:
    """Branch from the roof stair into the y=8 upper corridor without blocking stair headroom."""
    branch_x = start_x + 6
    z_values = range(17, z) if z > 17 else range(z + 1, 15)
    for bridge_z in z_values:
        module.cuboid((branch_x, 8, bridge_z), (branch_x + 2, 8, bridge_z), FLOOR)
        module.block(branch_x + 2, 9, bridge_z, IRON_BARS)


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
    for x in (8, 20):
        module.block(x, 18, lane_start, FRAME)
        module.victoria_banner(x, 18, lane_start + 1, VICTORIA_BANNER_SOUTH)


def open_boundary(module: Module, side: str, center: int) -> None:
    if side in ("east", "west"):
        x = 31 if side == "east" else 0
        module.cuboid((x, 2, center - 2), (x, 6, center + 1), AIR)
        module.cuboid((x, 7, center - 2), (x, 7, center + 1), FRAME)
        module.cuboid((x, 8, center - 2), (x, 8, center + 1), FLOOR)
        module.cuboid((x, 9, center - 2), (x, 13, center + 1), AIR)
        module.cuboid((x, 14, center - 2), (x, 14, center + 1), FRAME)
    else:
        z = 31 if side == "south" else 0
        module.cuboid((center - 2, 2, z), (center + 1, 6, z), AIR)
        module.cuboid((center - 2, 7, z), (center + 1, 7, z), FRAME)
        module.cuboid((center - 2, 8, z), (center + 1, 8, z), FLOOR)
        module.cuboid((center - 2, 9, z), (center + 1, 13, z), AIR)
        module.cuboid((center - 2, 14, z), (center + 1, 14, z), FRAME)


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
    module.cuboid((1, 7, 14), (30, 7, 17), ANDESITE_CASING)
    module.cuboid((1, 8, 14), (30, 8, 17), FLOOR)
    module.cuboid((1, 9, 14), (30, 13, 17), AIR)
    for x in (4, 11, 19, 27):
        ceiling_light_bank(module, x, 15, 7)
        ceiling_light_bank(module, x, 16, 14)

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

    # Exterior shaping precedes the interior pass so buttresses and battle wear cannot invade rooms.
    buttresses = {
        "wall_rear_left": (("north", 16), ("west", 8)),
        "wall_rear_right": (("south", 8), ("west", 20)),
        "wall_front_left": (("north", 20), ("east", 20)),
        "wall_front_right": (("south", 20), ("east", 8)),
    }
    for side, center in buttresses[name]:
        stepped_buttress(module, side, center)
    for y in range(12, 17):
        for x in range(3):
            for z in range(3 - x):
                for px, pz in ((x, z), (31 - x, z), (x, 31 - z), (31 - x, 31 - z)):
                    module.block(px, y, pz, AIR if x + z < 2 else SCARRED_ARMOR)
    add_battle_damage(module)

    specs = {spec.room_id: spec for spec in room_specs_for(name)}
    for spec in specs.values():
        build_room_shell(module, spec)

    if name == "wall_rear_left":
        # Ordnance magazine: separated racks leave a direct aisle from the door to the issue chest.
        for x in (3, 6, 11):
            for z in (3, 7, 11):
                module.block(x, 2, z, BARREL)
                module.block(x, 3, z, BARREL)
        module.cuboid((13, 2, 4), (13, 6, 10), IRON_BARS)
        module.loot_chest(12, 2, 8, specs["ordnance_magazine"].loot_table)
        module.block(3, 2, 12, TARGET)

        # Operations room: full conference table, map workstations, archive and Terra globe.
        module.cuboid((19, 2, 4), (28, 2, 10), GRAY_CARPET)
        conference_table(module, 19, 5)
        tactical_globe(module, 28, 11)
        module.cuboid((18, 2, 2), (18, 5, 5), BOOKSHELF)
        module.block(20, 2, 11, CARTOGRAPHY_TABLE)
        module.block(22, 2, 11, LECTERN_SOUTH)
        module.loot_chest(29, 2, 3, specs["operations_room"].loot_table)
        module.sign(26, 7, 14, WALL_SIGN_SOUTH, ("保持警戒", "KEEP WATCH"))

        # Upper command gallery uses the second storey for wall-wide coordination.
        module.cuboid((5, 9, 3), (24, 9, 10), GRAY_CARPET)
        conference_table(module, 9, 5, 8)
        tactical_globe(module, 26, 10, 8)
        module.cuboid((2, 9, 2), (2, 12, 7), BOOKSHELF)
        module.block(4, 9, 10, CARTOGRAPHY_TABLE)
        module.block(6, 9, 10, LECTERN_SOUTH)
        module.loot_chest(28, 9, 3, specs["command_gallery"].loot_table)
    elif name == "wall_rear_right":
        # Auxiliary stores: labelled-looking rack rhythm, tools and a clear central aisle.
        for x in (3, 6, 11):
            module.block(x, 2, 27, BARREL)
            module.block(x, 3, 27, BARREL)
        module.cuboid((2, 2, 20), (2, 5, 25), BOOKSHELF)
        module.block(12, 2, 21, ANVIL)
        module.block(12, 2, 23, GRINDSTONE)
        module.loot_chest(12, 2, 28, specs["auxiliary_stores"].loot_table)

        # Maintenance shop: Create power train, coolant line and conventional work stations.
        module.block(19, 2, 27, CRAFTING_TABLE)
        module.block(21, 2, 27, BLAST_FURNACE)
        module.block(23, 2, 27, CAULDRON)
        module.loot_chest(28, 2, 27, specs["maintenance_shop"].loot_table)
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

        # Upper engineering gallery monitors the systems serviced below.
        module.cuboid((4, 9, 21), (12, 10, 22), CONTROL)
        module.cuboid((18, 9, 26), (27, 10, 27), CONTROL)
        module.block(5, 11, 22, CREATE_SPEEDOMETER)
        module.block(8, 11, 22, CREATE_STRESSOMETER)
        module.block(19, 11, 26, CREATE_ANALOG_LEVER)
        module.block(22, 11, 26, CREATE_GEARBOX)
        module.block(25, 11, 26, CREATE_CLUTCH)
        module.block(28, 9, 20, CARTOGRAPHY_TABLE)
        module.loot_chest(28, 9, 28, specs["engineering_gallery"].loot_table)
    elif name == "wall_front_left":
        # Signals archive: route planning, written records and redundant communications consoles.
        module.cuboid((2, 2, 2), (2, 5, 10), BOOKSHELF)
        module.block(5, 2, 4, CARTOGRAPHY_TABLE)
        module.block(8, 2, 4, LECTERN_SOUTH)
        module.cuboid((4, 2, 9), (11, 3, 10), CONTROL)
        for x in (5, 8, 11):
            module.block(x, 4, 10, LEVER)
        module.loot_chest(12, 2, 3, specs["signals_archive"].loot_table)

        # Fire control: two console rows, operator chairs and independent mechanical instruments.
        module.cuboid((19, 2, 4), (28, 3, 5), CONTROL)
        module.cuboid((19, 2, 9), (28, 3, 10), CONTROL)
        for x in (20, 23, 26, 28):
            module.block(x, 4, 5, LEVER)
            module.block(x, 4, 10, LEVER)
        for x in (20, 23, 26):
            module.block(x, 2, 7, CHAIR_SOUTH)
        module.block(21, 4, 4, CREATE_ANALOG_LEVER)
        module.block(24, 4, 4, CREATE_SPEEDOMETER)
        module.block(27, 4, 4, CREATE_STRESSOMETER)
        module.loot_chest(29, 2, 11, specs["fire_control"].loot_table)

        # Upper rangefinding gallery combines observation stations with map verification.
        module.cuboid((4, 9, 4), (13, 10, 5), CONTROL)
        module.cuboid((18, 9, 4), (27, 10, 5), CONTROL)
        for x in (5, 8, 11, 19, 22, 25):
            module.block(x, 11, 5, LEVER)
            module.block(x, 9, 7, CHAIR_SOUTH)
        module.block(5, 9, 10, CARTOGRAPHY_TABLE)
        module.block(8, 9, 10, LECTERN_SOUTH)
        module.block(25, 11, 4, CREATE_SPEEDOMETER)
        module.loot_chest(28, 9, 11, specs["rangefinding_gallery"].loot_table)
    elif name == "wall_front_right":
        # Crew quarters: carpet, six bunks, lockers and personal supply chest.
        module.cuboid((2, 2, 19), (13, 2, 29), GRAY_CARPET)
        for x, z in ((3, 21), (3, 25), (8, 21)):
            module.block(x, 2, z, BED_FOOT_EAST)
            module.block(x + 1, 2, z, BED_HEAD_EAST)
        for x in (3, 6, 10):
            module.block(x, 2, 28, BARREL)
        module.loot_chest(12, 2, 28, specs["crew_quarters"].loot_table)
        module.block(12, 2, 20, POTTED_FERN)

        # Mess and supply: dining table, food preparation, wash point and issued supplies.
        module.cuboid((18, 2, 19), (29, 2, 29), RED_CARPET)
        table_with_chairs(module, 20, 23, 7)
        module.block(19, 2, 28, SMOKER_NORTH)
        module.block(21, 2, 28, CAULDRON)
        module.block(23, 2, 28, CRAFTING_TABLE)
        module.block(26, 2, 28, BARREL)
        module.loot_chest(28, 2, 28, specs["mess_supply"].loot_table)
        module.block(28, 2, 20, POTTED_POPPY)

        # Upper reserve room holds the relief crew and emergency medical stores.
        module.cuboid((2, 9, 19), (29, 9, 29), GRAY_CARPET)
        for x, z in ((3, 21), (3, 25), (9, 21), (21, 25)):
            module.block(x, 9, z, BED_FOOT_EAST)
            module.block(x + 1, 9, z, BED_HEAD_EAST)
        table_with_chairs(module, 14, 23, 6, 8)
        for x in (4, 8, 24, 27):
            module.block(x, 9, 28, BARREL)
        module.block(27, 9, 20, POTTED_FERN)
        module.loot_chest(28, 9, 28, specs["reserve_barracks"].loot_table)

    for spec in specs.values():
        add_room_wall_decor(module, spec)

    # Apply access and roof furniture last so shell, parapet and damage passes cannot seal the exit.
    stair_x, stair_z = staircase_positions[name]
    east_staircase(module, stair_x, stair_z)
    upper_stair_landing(module, stair_x, stair_z)
    roof_service_detail(module, lane_start, lane_end, stair_x, stair_z)
    if name == "wall_front_left":
        # Continuous ladder beside the upward Jigsaw aperture enters the turret vestibule.
        module.cuboid((18, 18, 16), (18, 19, 16), FRAME)
        module.block(17, 18, 16, CREATE_LADDER)
        module.block(17, 19, 16, CREATE_LADDER)

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
            f"{PREVIEW_ID}/turret", "minecraft:air",
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

    # Roof-to-breech vestibule: the ladder aligns with wall_front_left and ends at a brass airlock.
    breech_spec = room_specs_for("turret_core")[0]
    bx0, bz0, bx1, bz1 = breech_spec.bounds
    module.cuboid((14, 0, 0), (18, 6, 8), AIR)
    module.cuboid((14, 3, 0), (18, 3, 8), ANDESITE_CASING)
    module.cuboid((14, 4, 0), (14, 7, 8), ANDESITE_CASING)
    module.cuboid((18, 4, 0), (18, 7, 8), ANDESITE_CASING)
    module.cuboid((14, 7, 0), (18, 7, 8), ANDESITE_CASING)
    module.cuboid((14, 4, 8), (18, 7, 8), ANDESITE_CASING)
    module.cuboid((15, 4, 8), (16, 6, 8), AIR)
    brass_double_door(module, 15, 8, "south", 4)
    module.cuboid((18, 0, 0), (18, 3, 0), FRAME)
    for y in range(0, 5):
        module.block(17, y, 0, CREATE_LADDER)
    module.sign(14, 6, 7, WALL_SIGN_NORTH, (breech_spec.zh_name, breech_spec.en_name))
    # Every light is recessed into a continuous girder rather than floating in the chamber.
    for _, _, z in room_light_positions(breech_spec):
        module.cuboid((bx0 + 1, breech_spec.ceiling_y, z),
                      (bx1 - 1, breech_spec.ceiling_y, z), CREATE_GIRDER)
    for position in room_light_positions(breech_spec):
        module.block(*position, BRIGHT_LIGHT)

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
        "minecraft:air",
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
        for room in ("control", "maintenance", "ordnance", "planning", "supply")
    }
    if loot_tables != expected_loot_tables:
        raise ValueError(f"unexpected loot-table coverage: {sorted(loot_tables)}")
    module_by_name = {module.name: module for module in modules}
    doorway_checks = {
        "wall_rear_left": ((31, 3, 15), (15, 3, 31), (31, 10, 15), (15, 10, 31)),
        "wall_rear_right": ((15, 3, 0), (31, 3, 15), (15, 10, 0), (31, 10, 15)),
        "wall_front_left": ((0, 3, 15), (15, 3, 31), (0, 10, 15), (15, 10, 31)),
        "wall_front_right": ((15, 3, 0), (0, 3, 15), (31, 3, 24),
                             (15, 10, 0), (0, 10, 15), (31, 10, 24)),
    }
    if len(ROOM_SPECS) != 13 or len({spec.room_id for spec in ROOM_SPECS}) != len(ROOM_SPECS):
        raise ValueError("room program must contain thirteen uniquely named rooms")
    expected_room_features = {
        "ordnance_magazine": {BARREL, TARGET},
        "operations_room": {TABLE_SLAB, CARTOGRAPHY_TABLE, LECTERN_SOUTH, BLUE_WOOL, GREEN_WOOL},
        "auxiliary_stores": {BARREL, ANVIL, GRINDSTONE},
        "maintenance_shop": {CRAFTING_TABLE, BLAST_FURNACE, CREATE_PUMP, CREATE_GEARBOX},
        "signals_archive": {BOOKSHELF, CARTOGRAPHY_TABLE, LECTERN_SOUTH, CONTROL},
        "fire_control": {CONTROL, CHAIR_SOUTH, CREATE_ANALOG_LEVER, CREATE_SPEEDOMETER},
        "crew_quarters": {BED_FOOT_EAST, BED_HEAD_EAST, BARREL},
        "mess_supply": {TABLE_SLAB, SMOKER_NORTH, CAULDRON, BARREL},
        "command_gallery": {TABLE_SLAB, CARTOGRAPHY_TABLE, LECTERN_SOUTH, BLUE_WOOL, GREEN_WOOL},
        "engineering_gallery": {CONTROL, CREATE_SPEEDOMETER, CREATE_STRESSOMETER, CREATE_GEARBOX},
        "rangefinding_gallery": {CONTROL, CHAIR_SOUTH, CARTOGRAPHY_TABLE, CREATE_SPEEDOMETER},
        "reserve_barracks": {BED_FOOT_EAST, BED_HEAD_EAST, TABLE_SLAB, BARREL},
        "breech_chamber": {CONTROL, CREATE_GEARBOX, CREATE_PUMP, BARREL},
    }
    lower_door_states = {
        BRASS_DOOR_SOUTH_LEFT_LOWER, BRASS_DOOR_SOUTH_RIGHT_LOWER,
        BRASS_DOOR_NORTH_LEFT_LOWER, BRASS_DOOR_NORTH_RIGHT_LOWER,
        BRASS_DOOR_EAST_LEFT_LOWER, BRASS_DOOR_EAST_RIGHT_LOWER,
    }
    passable_states = {AIR, GRAY_CARPET, RED_CARPET, STAIR_EAST} | lower_door_states

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
        if any(module.blocks.get((stair_x + 14, y, stair_z)) != AIR for y in (17, 18)):
            raise ValueError(f"{name}: roof stair headroom is blocked")
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
        module_specs = room_specs_for(name)
        if len(module_specs) != 3:
            raise ValueError(f"{name}: expected two lower rooms and one upper room")
        for spec in module_specs:
            x0, z0, x1, z1 = spec.bounds
            if spec.width < 12 or spec.depth < 11:
                raise ValueError(f"{spec.room_id}: room is too small for its declared function")
            for x in range(x0, x1 + 1):
                for z in range(z0, z1 + 1):
                    if module.blocks.get((x, spec.floor_y, z)) != ANDESITE_CASING:
                        raise ValueError(f"{spec.room_id}: unfinished floor at {(x, spec.floor_y, z)}")
            side_wall_y = spec.floor_y + 2
            if any(module.blocks.get((x0, side_wall_y, z)) != ANDESITE_CASING for z in range(z0, z1 + 1)):
                raise ValueError(f"{spec.room_id}: west wall is incomplete")
            if any(module.blocks.get((x1, side_wall_y, z)) != ANDESITE_CASING for z in range(z0, z1 + 1)):
                raise ValueError(f"{spec.room_id}: east wall is incomplete")
            for x in range(x0, x1 + 1):
                for z in range(z0, z1 + 1):
                    if module.blocks.get((x, spec.ceiling_y, z)) not in (ANDESITE_CASING, BRIGHT_LIGHT):
                        raise ValueError(f"{spec.room_id}: ceiling is incomplete at {(x, spec.ceiling_y, z)}")
            light_positions = room_light_positions(spec)
            if any(module.blocks.get(position) != BRIGHT_LIGHT for position in light_positions):
                raise ValueError(f"{spec.room_id}: a ceiling light was overwritten")
            for x in range(x0 + 1, x1):
                for z in range(z0 + 1, z1):
                    distance = min(
                        abs(x - lx) + (spec.ceiling_y - spec.floor_y) + abs(z - lz)
                        for lx, _, lz in light_positions
                    )
                    if distance > 14:
                        raise ValueError(f"{spec.room_id}: floor tile {(x, z)} has no positive block-light coverage")
            room_states = {
                state
                for (x, y, z), state in module.blocks.items()
                if x0 < x < x1 and z0 < z < z1 and spec.floor_y < y < spec.ceiling_y
            }
            missing_features = expected_room_features[spec.room_id] - room_states
            if missing_features:
                raise ValueError(f"{spec.room_id}: purpose-specific furniture is incomplete: {missing_features}")
            room_loot = {
                str(nbt.get("LootTable"))
                for (x, y, z), nbt in module.block_nbt.items()
                if x0 < x < x1 and z0 < z < z1
                and spec.floor_y < y < spec.ceiling_y and "LootTable" in nbt
            }
            expected_room_loot = {f"zinecraft:chests/victoria_defence_cannon_{spec.loot_table}"}
            if room_loot != expected_room_loot:
                raise ValueError(f"{spec.room_id}: expected one purpose-matched loot chest")

        # Flood both storeys independently; each must reach its room doors and the shared roof stair.
        for floor_y in (1, 8):
            walk_y = floor_y + 1
            start = (15, 15)
            visited = {start}
            frontier = [start]
            while frontier:
                x, z = frontier.pop()
                for next_position in ((x - 1, z), (x + 1, z), (x, z - 1), (x, z + 1)):
                    nx, nz = next_position
                    if not (0 <= nx < 32 and 0 <= nz < 32) or next_position in visited:
                        continue
                    if module.blocks.get((nx, walk_y, nz), AIR) not in passable_states:
                        continue
                    visited.add(next_position)
                    frontier.append(next_position)
            level_specs = tuple(spec for spec in module_specs if spec.floor_y == floor_y)
            route_targets = [
                (spec.door_axis, spec.bounds[3] - 1 if spec.door_side == "south" else spec.bounds[1] + 1)
                for spec in level_specs
            ]
            route_targets.append((stair_x, stair_z) if floor_y == 1 else (stair_x + 7, stair_z))
            if any(target not in visited for target in route_targets):
                raise ValueError(f"{name}: storey {floor_y} door or roof stair is disconnected")

    breech_spec = room_specs_for("turret_core")[0]
    breech = module_by_name["turret_core"]
    bx0, bz0, bx1, bz1 = breech_spec.bounds
    breech_lights = room_light_positions(breech_spec)
    if any(breech.blocks.get(position) != BRIGHT_LIGHT for position in breech_lights):
        raise ValueError("breech_chamber: high-output light grid is incomplete")
    for x in range(bx0 + 1, bx1):
        for z in range(bz0 + 1, bz1):
            distance = min(
                abs(x - lx) + (breech_spec.ceiling_y - breech_spec.floor_y) + abs(z - lz)
                for lx, _, lz in breech_lights
            )
            if distance > 14:
                raise ValueError(f"breech_chamber: floor tile {(x, z)} has no positive block-light coverage")
    breech_states = {
        state
        for (x, y, z), state in breech.blocks.items()
        if bx0 < x < bx1 and bz0 < z < bz1 and 4 <= y <= 18
    }
    missing_breech_features = expected_room_features["breech_chamber"] - breech_states
    if missing_breech_features:
        raise ValueError(f"breech_chamber: purpose-specific furniture is incomplete: {missing_breech_features}")
    if breech.blocks.get((17, 0, 0)) != CREATE_LADDER:
        raise ValueError("breech_chamber: wall-to-turret access ladder is missing")
    if breech.blocks.get((15, 4, 8)) not in lower_door_states:
        raise ValueError("breech_chamber: brass entrance door is missing")
    breech_loot = {
        str(nbt.get("LootTable"))
        for (x, y, z), nbt in breech.block_nbt.items()
        if bx0 < x < bx1 and bz0 < z < bz1 and "LootTable" in nbt
    }
    if breech_loot != {"zinecraft:chests/victoria_defence_cannon_maintenance"}:
        raise ValueError("breech_chamber: maintenance loot chest is missing")
    breech_visited = {(17, 0)}
    breech_frontier = [(17, 0)]
    while breech_frontier:
        x, z = breech_frontier.pop()
        for next_position in ((x - 1, z), (x + 1, z), (x, z - 1), (x, z + 1)):
            nx, nz = next_position
            if not (14 <= nx <= 18 and 0 <= nz <= 9) or next_position in breech_visited:
                continue
            if breech.blocks.get((nx, 4, nz), AIR) not in (passable_states | {CREATE_LADDER}):
                continue
            breech_visited.add(next_position)
            breech_frontier.append(next_position)
    if (15, 9) not in breech_visited:
        raise ValueError("breech_chamber: entrance vestibule does not reach the chamber")
    if sum(state in (BED_FOOT_EAST, BED_HEAD_EAST) for state in module_by_name["wall_front_right"].blocks.values()) < 6:
        raise ValueError("crew quarters are missing beds")
    if sum(state == TABLE_SLAB for module in modules for state in module.blocks.values()) < 20:
        raise ValueError("furnished table surfaces are incomplete")
    if sum(state in (BLUE_WOOL, GREEN_WOOL) for module in modules for state in module.blocks.values()) < 7:
        raise ValueError("operations-room Terra globe is incomplete")
    if sum(BRASS_DOOR_SOUTH_LEFT_LOWER <= state <= BRASS_DOOR_EAST_RIGHT_UPPER
           for module in modules for state in module.blocks.values()) < 52:
        raise ValueError("room brass-door coverage is incomplete")
    sign_count = sum(
        nbt.get("id") == "minecraft:sign"
        for module in modules
        for nbt in module.block_nbt.values()
    )
    if sign_count < 5:
        raise ValueError("room signage is incomplete")
    banner_support = {
        VICTORIA_BANNER_SOUTH: (0, 0, -1),
        VICTORIA_BANNER_NORTH: (0, 0, 1),
        VICTORIA_BANNER_EAST: (-1, 0, 0),
        VICTORIA_BANNER_WEST: (1, 0, 0),
    }
    banner_count = 0
    for module in modules:
        for (x, y, z), state in module.blocks.items():
            if state not in banner_support:
                continue
            banner_count += 1
            dx, dy, dz = banner_support[state]
            if module.blocks.get((x + dx, y + dy, z + dz), AIR) in (AIR, GLASS_PANE, IRON_BARS):
                raise ValueError(f"{module.name}: unsupported Victoria banner at {(x, y, z)}")
            banner_nbt = module.block_nbt.get((x, y, z), {})
            if banner_nbt.get("id") != "minecraft:banner" or len(banner_nbt.get("patterns", ())) != 4:
                raise ValueError(f"{module.name}: Victoria banner has incomplete heraldry at {(x, y, z)}")
    if banner_count < 24:
        raise ValueError(f"Victoria flag and wall-tapestry coverage is insufficient: {banner_count}")

    light_support_states = {ANDESITE_CASING, CASING, FLOOR, FRAME, CREATE_GIRDER}
    neighbours = ((1, 0, 0), (-1, 0, 0), (0, 1, 0), (0, -1, 0), (0, 0, 1), (0, 0, -1))
    for module in modules:
        for (x, y, z), state in module.blocks.items():
            if state != BRIGHT_LIGHT:
                continue
            if not any(module.blocks.get((x + dx, y + dy, z + dz)) in light_support_states
                       for dx, dy, dz in neighbours):
                raise ValueError(f"{module.name}: unsupported high-output light at {(x, y, z)}")
    for module in modules:
        for (x, y, z), nbt in module.block_nbt.items():
            if "LootTable" not in nbt:
                continue
            if y not in (2, 4, 9, 18):
                raise ValueError(f"{module.name}: unexpected loot chest elevation at {(x, y, z)}")
            if module.blocks.get((x, y - 1, z)) not in (FLOOR, ANDESITE_CASING):
                raise ValueError(f"{module.name}: loot chest lacks a finished floor at {(x, y, z)}")
    if sum(state in (CREATE_GEARBOX, CREATE_CLUTCH, CREATE_FLYWHEEL, CREATE_SPEEDOMETER,
                     CREATE_STRESSOMETER, CREATE_ANALOG_LEVER) for module in modules for state in module.blocks.values()) < 12:
        raise ValueError("Create machinery detail is insufficient")
    for module in modules:
        module.write()
        print(f"{module.name}: size={module.size}, blocks={len(module.blocks)}")
