"""Independent Aegir settlement blockout based on the 51-series CG anchors."""

from __future__ import annotations

from .common import AIR, Template, output_argument, validate_nation, write_preview


NATION = "aegir"
SETTLEMENT = "aegir_subsea_enclave"
ROAD = "zinecraft:aegir_abyssal_slate"
WALL = "zinecraft:aegir_pressure_tile"
TRIM = "minecraft:oxidized_copper"
GLASS = "minecraft:cyan_stained_glass"
LIGHT = "minecraft:sea_lantern"
DOOR = "minecraft:warped_door"


def center() -> Template:
    t = Template(NATION, SETTLEMENT, "center", (32, 14, 32), "center")
    t.cuboid((0, 0, 12), (31, 0, 19), ROAD)
    t.cuboid((12, 0, 0), (19, 0, 31), ROAD)
    t.cuboid((7, 0, 7), (24, 0, 24), WALL)
    t.cuboid((9, 0, 9), (22, 0, 22), "minecraft:dark_prismarine")
    t.cuboid((11, 0, 11), (20, 0, 20), "minecraft:water")
    for x, z in ((8, 8), (23, 8), (8, 23), (23, 23)):
        t.cuboid((x, 1, z), (x, 7, z), TRIM)
        t.block(x, 8, z, LIGHT)
    for y, radius in ((2, 5), (5, 4), (8, 3)):
        t.cuboid((15 - radius, y, 15 - radius), (16 + radius, y, 15 - radius), TRIM)
        t.cuboid((15 - radius, y, 16 + radius), (16 + radius, y, 16 + radius), TRIM)
        t.cuboid((15 - radius, y, 15 - radius), (15 - radius, y, 16 + radius), TRIM)
        t.cuboid((16 + radius, y, 15 - radius), (16 + radius, y, 16 + radius), TRIM)
    t.cuboid((15, 1, 15), (16, 10, 16), WALL)
    t.block(15, 11, 15, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    t.connector(16, 1, 31, "south", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    t.connector(31, 1, 15, "east", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    t.connector(0, 1, 16, "west", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    return t


def street_straight() -> Template:
    t = Template(NATION, SETTLEMENT, "street_straight", (32, 10, 32), "street")
    t.cuboid((12, 0, 0), (19, 0, 31), ROAD)
    t.cuboid((9, 0, 0), (11, 0, 31), WALL)
    t.cuboid((20, 0, 0), (22, 0, 31), WALL)
    for z in (4, 12, 20, 28):
        t.cuboid((10, 1, z), (10, 3, z), TRIM); t.block(10, 4, z, LIGHT)
        t.cuboid((21, 1, z), (21, 3, z), TRIM); t.block(21, 4, z, LIGHT)
    t.cuboid((7, 4, 14), (24, 4, 17), TRIM)
    t.cuboid((8, 5, 15), (23, 6, 16), GLASS)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    t.connector(16, 1, 31, "south", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    t.connector(31, 1, 16, "east", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    return t


def street_corner() -> Template:
    t = Template(NATION, SETTLEMENT, "street_corner", (32, 10, 32), "street")
    t.cuboid((12, 0, 0), (19, 0, 19), ROAD); t.cuboid((12, 0, 12), (31, 0, 19), ROAD)
    t.cuboid((8, 0, 7), (11, 0, 23), WALL); t.cuboid((20, 0, 20), (27, 0, 23), WALL)
    t.cuboid((5, 1, 20), (11, 5, 26), WALL); t.clear((7, 1, 22), (9, 3, 24))
    t.cuboid((6, 6, 21), (10, 6, 25), TRIM); t.block(8, 5, 21, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    t.connector(31, 1, 15, "east", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    t.connector(15, 1, 31, "south", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    return t


def street_cross() -> Template:
    t = Template(NATION, SETTLEMENT, "street_cross", (32, 8, 32), "street")
    t.cuboid((12, 0, 0), (19, 0, 31), ROAD); t.cuboid((0, 0, 12), (31, 0, 19), ROAD)
    for x, z in ((9, 9), (22, 9), (9, 22), (22, 22)):
        t.cuboid((x, 0, z), (x + 1, 3, z + 1), TRIM); t.block(x, 4, z, LIGHT)
    for direction, pos in (("north", (15, 1, 0)), ("south", (16, 1, 31)), ("west", (0, 1, 16)), ("east", (31, 1, 15))):
        t.connector(*pos, direction, f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    return t


def street_end() -> Template:
    t = Template(NATION, SETTLEMENT, "street_end", (32, 10, 32), "street")
    t.cuboid((12, 0, 0), (19, 0, 22), ROAD)
    t.cuboid((8, 0, 20), (23, 0, 29), WALL); t.cuboid((11, 0, 22), (20, 0, 27), "minecraft:water")
    t.cuboid((9, 1, 21), (9, 5, 28), TRIM); t.cuboid((22, 1, 21), (22, 5, 28), TRIM)
    t.cuboid((9, 6, 21), (22, 6, 22), TRIM); t.block(11, 5, 22, LIGHT); t.block(20, 5, 22, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    t.connector(31, 1, 16, "east", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    return t


def pressure_residence() -> Template:
    t = Template(NATION, SETTLEMENT, "pressure_residence", (31, 34, 36), "building")
    t.clear((0, 0, 0), (30, 33, 35)); t.cuboid((1, 0, 1), (29, 0, 34), ROAD)
    # Two unequal pressure stacks separated by a full-height slot.
    t.cuboid((2, 1, 2), (13, 25, 33), WALL); t.clear((3, 1, 3), (12, 24, 32))
    t.cuboid((17, 1, 5), (28, 31, 31), WALL); t.clear((18, 1, 6), (27, 30, 30))
    for y in (5, 11, 17, 23):
        t.cuboid((2, y, 5), (2, y + 2, 29), GLASS); t.cuboid((28, y, 8), (28, y + 2, 27), GLASS)
    t.cuboid((10, 17, 13), (20, 21, 20), TRIM); t.clear((11, 18, 14), (19, 20, 19))
    t.cuboid((5, 25, 8), (10, 27, 27), TRIM); t.cuboid((20, 31, 10), (25, 33, 25), TRIM)
    t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    t.cuboid((13, 1, 2), (17, 4, 7), WALL); t.clear((14, 1, 2), (16, 3, 7)); t.clear((13, 1, 5), (17, 3, 5))
    t.clear((12, 1, 6), (18, 3, 8))
    # The pressure vestibule now seals independently into the two dwelling stacks.
    t.block(13, 1, 6, DOOR, {"half": "lower", "facing": "east"}); t.block(13, 2, 6, DOOR, {"half": "upper", "facing": "east"})
    t.block(17, 1, 6, DOOR, {"half": "lower", "facing": "west"}); t.block(17, 2, 6, DOOR, {"half": "upper", "facing": "west"})
    # A real sleeping loft and six-step stair occupy the lower left pressure stack.
    t.cuboid((3, 6, 9), (12, 6, 29), TRIM); t.clear((4, 6, 9), (4, 8, 14))
    for y, z in enumerate(range(9, 15), start=1):
        t.block(4, y, z, "minecraft:oxidized_cut_copper_stairs", {"facing": "south"})
    t.cuboid((4, 7, 19), (12, 9, 19), WALL); t.clear((8, 7, 19), (8, 8, 19))
    t.block(8, 7, 19, DOOR, {"half": "lower", "facing": "south"}); t.block(8, 8, 19, DOOR, {"half": "upper", "facing": "south"})
    for x, z in ((6, 11), (10, 11), (6, 24), (10, 24)):
        t.block(x, 10, z, TRIM); t.block(x, 9, z, LIGHT)
    for x, z in ((15, 8), (7, 14), (7, 26), (23, 13), (23, 25)):
        t.cuboid((x, 4, z), (x, 5, z), TRIM); t.block(x, 3, z, LIGHT)
    for x, z in ((4, 4), (15, 20)):
        t.block(x, 5, z, TRIM); t.block(x, 4, z, LIGHT)
    t.block(5, 1, 29, "minecraft:barrel", nbt={"id": "minecraft:barrel", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    t.require_reachable("pressure vestibule", (15, 1, 3)); t.require_reachable("left residential stack", (10, 1, 10)); t.require_reachable("right residential stack", (20, 1, 10))
    t.require_walk_region("left ground pressure floor", (4, 1, 6), (12, 1, 28))
    t.require_walk_region("right ground pressure floor", (18, 1, 7), (26, 1, 28))
    return t


def hydroponics_lab() -> Template:
    t = Template(NATION, SETTLEMENT, "hydroponics_lab", (31, 24, 40), "building")
    t.clear((0, 0, 0), (30, 23, 39)); t.cuboid((1, 0, 1), (29, 0, 38), ROAD)
    t.cuboid((3, 1, 2), (27, 12, 35), WALL); t.clear((4, 1, 3), (26, 11, 34))
    t.cuboid((9, 12, 9), (25, 19, 31), WALL); t.clear((10, 12, 10), (24, 18, 30))
    t.cuboid((4, 13, 5), (8, 16, 20), TRIM); t.cuboid((5, 14, 6), (7, 15, 19), GLASS)
    t.cuboid((2, 6, 8), (2, 9, 31), GLASS); t.cuboid((28, 4, 12), (28, 8, 33), GLASS)
    for x in (7, 12, 17, 22):
        t.cuboid((x, 1, 11), (x, 1, 31), "minecraft:oxidized_cut_copper"); t.cuboid((x, 2, 12), (x, 2, 30), "create:fluid_pipe")
    t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Sample airlock, wet cultivation hall, and rear environment-control room.
    t.cuboid((4, 1, 9), (26, 4, 9), WALL); t.clear((15, 1, 9), (15, 2, 9))
    t.block(15, 1, 9, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 9, DOOR, {"half": "upper", "facing": "south"})
    t.cuboid((23, 1, 27), (23, 4, 34), WALL); t.clear((23, 1, 32), (23, 2, 32))
    t.block(23, 1, 32, DOOR, {"half": "lower", "facing": "east"}); t.block(23, 2, 32, DOOR, {"half": "upper", "facing": "east"})
    for x, z in ((8, 7), (15, 7), (22, 7), (8, 20), (15, 20), (22, 20), (8, 33), (22, 33)):
        t.block(x, 11, z, TRIM); t.block(x, 10, z, LIGHT)
    for x, z in ((5, 10), (15, 10), (25, 10), (5, 25), (15, 25), (25, 25)):
        t.block(x, 5, z, TRIM); t.block(x, 4, z, LIGHT)
    for x, z in ((2, 10), (2, 22), (2, 32)):
        t.block(x, 4, z, LIGHT)
    for x, z in ((10, 3), (20, 3), (28, 14), (28, 24), (28, 33)):
        t.block(x, 4, z, LIGHT)
    t.block(25, 1, 32, "minecraft:chest", {"facing": "west"}, {"id": "minecraft:chest", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    t.require_reachable("sample airlock", (15, 1, 3)); t.require_reachable("cultivation wing", (8, 1, 24)); t.require_reachable("environment control", (23, 1, 32))
    t.require_walk_region("cultivation aisles", (5, 1, 5), (25, 1, 33))
    return t


def bathysphere_dock() -> Template:
    t = Template(NATION, SETTLEMENT, "bathysphere_dock", (31, 30, 46), "building")
    t.clear((0, 0, 0), (30, 29, 45)); t.cuboid((1, 0, 1), (29, 0, 44), ROAD)
    t.cuboid((2, 1, 2), (28, 18, 43), WALL); t.clear((3, 1, 3), (27, 17, 42))
    # Wet dock void and asymmetric logistics spine.
    t.cuboid((7, 0, 13), (23, 0, 41), "minecraft:water"); t.cuboid((3, 1, 8), (7, 25, 38), WALL); t.clear((4, 1, 9), (6, 24, 37))
    t.clear((7, 1, 9), (7, 3, 11))
    t.cuboid((23, 1, 20), (28, 13, 42), TRIM); t.cuboid((8, 18, 10), (22, 22, 15), TRIM)
    t.cuboid((5, 24, 17), (25, 26, 19), TRIM); t.cuboid((22, 10, 19), (25, 24, 22), TRIM)
    t.cuboid((8, 2, 12), (22, 4, 12), GLASS); t.clear((11, 1, 13), (19, 10, 40))
    t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Pressure lock, dry control gallery, cargo office, and the wet launch bay.
    t.cuboid((8, 1, 9), (22, 4, 9), WALL); t.clear((15, 1, 9), (15, 2, 9))
    t.block(15, 1, 9, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 9, DOOR, {"half": "upper", "facing": "south"})
    t.block(7, 1, 10, DOOR, {"half": "lower", "facing": "west"}); t.block(7, 2, 10, DOOR, {"half": "upper", "facing": "west"})
    for x, z in ((8, 7), (15, 7), (22, 7), (5, 15), (5, 28), (25, 18), (25, 33)):
        light_y = 16 if z > 10 else 10
        t.block(x, light_y + 1, z, TRIM); t.block(x, light_y, z, LIGHT)
    for x, z in ((5, 15), (5, 25), (5, 35), (11, 7), (20, 7)):
        t.block(x, 5, z, TRIM); t.block(x, 4, z, LIGHT)
    for x in (4, 10, 16, 22, 26):
        for z in (5, 15, 25, 35, 41):
            t.block(x, 5, z, TRIM); t.block(x, 4, z, LIGHT)
    t.block(5, 1, 35, "minecraft:barrel", nbt={"id": "minecraft:barrel", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    t.require_reachable("dock pressure lock", (15, 1, 3)); t.require_reachable("cargo control", (5, 1, 20)); t.require_reachable("wet lock overlook", (15, 1, 11))
    t.require_walk_region("dock service floor", (4, 1, 4), (26, 1, 11))
    return t


def current_archive() -> Template:
    t = Template(NATION, SETTLEMENT, "current_archive", (25, 32, 34), "building")
    t.clear((0, 0, 0), (24, 31, 33)); t.cuboid((1, 0, 1), (23, 0, 32), ROAD)
    t.cuboid((2, 1, 2), (20, 17, 31), WALL); t.clear((3, 1, 3), (19, 16, 30))
    t.cuboid((8, 17, 8), (23, 27, 28), WALL); t.clear((9, 17, 9), (22, 26, 27))
    t.cuboid((15, 27, 12), (21, 31, 24), TRIM)
    t.cuboid((2, 6, 7), (2, 10, 27), GLASS); t.cuboid((20, 19, 11), (23, 23, 25), GLASS)
    t.cuboid((9, 1, 10), (15, 12, 22), TRIM); t.clear((10, 1, 11), (14, 11, 21))
    t.block(12, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(12, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((12, 1, 2), (12, 2, 2))
    # Reception is separated from the paired map and controlled-record rings.
    t.cuboid((3, 1, 9), (19, 4, 9), WALL)
    for x in (6, 17):
        t.block(x, 1, 9, DOOR, {"half": "lower", "facing": "south"}); t.block(x, 2, 9, DOOR, {"half": "upper", "facing": "south"})
    t.block(9, 1, 17, DOOR, {"half": "lower", "facing": "east"}); t.block(9, 2, 17, DOOR, {"half": "upper", "facing": "east"})
    t.block(15, 1, 17, DOOR, {"half": "lower", "facing": "west"}); t.block(15, 2, 17, DOOR, {"half": "upper", "facing": "west"})
    for x, z in ((6, 7), (12, 7), (17, 7), (6, 18), (17, 18), (6, 28), (17, 28)):
        t.block(x, 16, z, TRIM); t.block(x, 15, z, LIGHT)
    for x, z in ((6, 7), (12, 7), (17, 7), (6, 18), (17, 18), (6, 28), (17, 28)):
        t.block(x, 5, z, TRIM); t.block(x, 4, z, LIGHT)
    for x, z in ((12, 14), (21, 10), (21, 20), (21, 28)):
        t.block(x, 5, z, TRIM); t.block(x, 4, z, LIGHT)
    t.block(4, 1, 28, "minecraft:chest", {"facing": "east"}, {"id": "minecraft:chest", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(12, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    t.require_reachable("archive reception", (12, 1, 3)); t.require_reachable("environment map ring", (6, 1, 17)); t.require_reachable("controlled archive", (17, 1, 27))
    t.require_walk_region("archive front ring", (4, 1, 4), (18, 1, 9))
    t.require_walk_region("archive west ring", (4, 1, 10), (8, 1, 22))
    t.require_walk_region("archive east ring", (16, 1, 10), (18, 1, 22))
    t.require_walk_region("archive rear ring", (4, 1, 23), (18, 1, 29))
    return t


def build_templates() -> list[Template]:
    templates = [center(), street_straight(), street_corner(), street_cross(), street_end(), pressure_residence(), hydroponics_lab(), bathysphere_dock(), current_archive()]
    validate_nation(templates, NATION, SETTLEMENT)
    return templates


def main() -> None:
    args = output_argument("Generate isolated Aegir CG settlement previews")
    templates = build_templates()
    if not args.validate_only:
        write_preview(templates, args.output)
    print(f"Validated {len(templates)} independent Aegir templates")


if __name__ == "__main__":
    main()
