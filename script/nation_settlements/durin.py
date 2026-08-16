"""Independent Durin Ideal City blockout from 30-series CGs."""

from __future__ import annotations

from .common import AIR, Template, output_argument, validate_nation, write_preview

NATION = "durin"; SETTLEMENT = "durin_ideal_city_block"
ROAD = "minecraft:smooth_stone"; GROUND = "zinecraft:durin_garden_moss"; WALL = "zinecraft:durin_ideal_city_panel"
TRIM = "minecraft:oxidized_cut_copper"; GLASS = "minecraft:light_blue_stained_glass"; LIGHT = "minecraft:sea_lantern"; DOOR = "minecraft:bamboo_door"


def center() -> Template:
    t = Template(NATION, SETTLEMENT, "center", (32, 12, 32), "center"); t.cuboid((0, 0, 12), (31, 0, 19), ROAD); t.cuboid((12, 0, 0), (19, 0, 31), ROAD)
    t.cuboid((5, 0, 5), (26, 0, 26), WALL); t.cuboid((8, 0, 8), (23, 0, 23), GROUND); t.cuboid((11, 0, 11), (20, 0, 20), "minecraft:water")
    t.cuboid((13, 1, 13), (18, 4, 18), WALL); t.cuboid((14, 5, 14), (17, 8, 17), GLASS)
    for x, z in ((6, 6), (25, 6), (6, 25), (25, 25)):
        t.cuboid((x, 1, z), (x, 3, z), WALL); t.block(x, 4, z, LIGHT)
    t.cuboid((7, 8, 14), (24, 9, 17), WALL)
    for direction, pos in (("north", (15, 1, 0)), ("south", (16, 1, 31)), ("west", (0, 1, 16)), ("east", (31, 1, 15))): t.connector(*pos, direction, f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    return t


def street_straight() -> Template:
    t = Template(NATION, SETTLEMENT, "street_straight", (32, 10, 32), "street"); t.cuboid((12, 0, 0), (19, 0, 31), ROAD); t.cuboid((7, 0, 0), (11, 0, 31), GROUND); t.cuboid((20, 0, 0), (24, 0, 31), GROUND)
    t.cuboid((8, 5, 0), (10, 6, 31), WALL); t.cuboid((21, 6, 0), (23, 7, 31), WALL)
    for z in (5, 15, 25): t.block(9, 4, z, LIGHT); t.block(22, 5, z, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(16, 1, 31, "south", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.connector(31, 1, 16, "east", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); return t


def street_corner() -> Template:
    t = Template(NATION, SETTLEMENT, "street_corner", (32, 10, 32), "street"); t.cuboid((12, 0, 0), (19, 0, 19), ROAD); t.cuboid((12, 0, 12), (31, 0, 19), ROAD); t.cuboid((6, 0, 6), (11, 0, 26), GROUND); t.cuboid((20, 0, 20), (27, 0, 26), GROUND)
    t.cuboid((7, 4, 9), (11, 5, 22), WALL); t.cuboid((8, 6, 11), (18, 7, 18), WALL); t.block(10, 3, 12, LIGHT); t.block(18, 6, 17, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(31, 1, 15, "east", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(15, 1, 31, "south", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); return t


def street_cross() -> Template:
    t = Template(NATION, SETTLEMENT, "street_cross", (32, 9, 32), "street"); t.cuboid((12, 0, 0), (19, 0, 31), ROAD); t.cuboid((0, 0, 12), (31, 0, 19), ROAD)
    for x, z in ((7, 7), (24, 7), (7, 24), (24, 24)):
        t.cuboid((x, 0, z), (x + 2, 0, z + 2), GROUND); t.cuboid((x + 1, 1, z + 1), (x + 1, 3, z + 1), WALL); t.block(x + 1, 4, z + 1, LIGHT)
    for direction, pos in (("north", (15, 1, 0)), ("south", (16, 1, 31)), ("west", (0, 1, 16)), ("east", (31, 1, 15))): t.connector(*pos, direction, f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    return t


def street_end() -> Template:
    t = Template(NATION, SETTLEMENT, "street_end", (32, 10, 32), "street"); t.cuboid((12, 0, 0), (19, 0, 23), ROAD); t.cuboid((5, 0, 20), (26, 0, 30), WALL); t.cuboid((7, 0, 22), (24, 0, 29), "minecraft:water")
    t.cuboid((7, 5, 21), (24, 6, 24), WALL); t.cuboid((10, 7, 22), (21, 8, 23), WALL); t.block(8, 4, 22, LIGHT); t.block(23, 4, 22, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.connector(31, 1, 16, "east", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); return t


def dome_apartment() -> Template:
    t = Template(NATION, SETTLEMENT, "dome_apartment", (31, 28, 38), "building"); t.clear((0, 0, 0), (30, 27, 37)); t.cuboid((1, 0, 1), (29, 0, 36), ROAD)
    # Four displaced residential pods, garden balconies, and a deep common canopy.
    t.cuboid((2, 1, 2), (18, 13, 32), WALL); t.clear((3, 1, 3), (17, 12, 31)); t.cuboid((12, 7, 5), (28, 19, 27), WALL); t.clear((13, 7, 6), (27, 18, 26)); t.cuboid((5, 13, 12), (14, 23, 35), WALL); t.cuboid((18, 19, 9), (25, 27, 22), WALL)
    t.cuboid((1, 6, 5), (21, 7, 10), WALL); t.cuboid((9, 14, 24), (29, 15, 31), WALL); t.cuboid((3, 8, 6), (10, 8, 9), GROUND); t.cuboid((15, 16, 25), (26, 16, 30), GROUND)
    t.cuboid((3, 4, 2), (16, 6, 2), GLASS); t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Garden lobby, shared living pod, and service core are separately closable.
    t.cuboid((3, 1, 14), (17, 4, 14), WALL); t.clear((8, 1, 14), (8, 2, 14))
    t.block(8, 1, 14, DOOR, {"half": "lower", "facing": "south"}); t.block(8, 2, 14, DOOR, {"half": "upper", "facing": "south"})
    t.cuboid((3, 1, 25), (17, 4, 25), WALL); t.clear((15, 1, 25), (15, 2, 25))
    t.block(15, 1, 25, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 25, DOOR, {"half": "upper", "facing": "south"})
    # Five ascending blocks meet the inhabited upper pod's y=6 landing.
    t.cuboid((13, 6, 13), (27, 6, 26), WALL)
    t.clear((14, 6, 7), (14, 10, 13))
    for y, z in enumerate(range(8, 14), start=1):
        t.block(14, y, z, "minecraft:cut_copper_stairs", {"facing": "south"})
    t.cuboid((13, 7, 17), (27, 10, 17), WALL); t.clear((20, 7, 17), (20, 8, 17))
    t.block(20, 7, 17, DOOR, {"half": "lower", "facing": "south"}); t.block(20, 8, 17, DOOR, {"half": "upper", "facing": "south"})
    for x, z in ((16, 10), (24, 10), (16, 23), (24, 23)):
        t.block(x, 18, z, WALL); t.block(x, 17, z, LIGHT)
    for x in (15, 20, 26):
        for z in (14, 21, 25): t.block(x, 11, z, WALL); t.block(x, 10, z, LIGHT)
    for x in (5, 12, 17):
        for z in (7, 18, 29): t.block(x, 5, z, WALL); t.block(x, 4, z, LIGHT)
    for x in (1, 6, 12, 18, 24, 29):
        for z in (6, 13, 22, 31, 35): t.block(x, 5, z, WALL); t.block(x, 4, z, LIGHT)
    t.block(16, 1, 29, "minecraft:barrel", nbt={"id": "minecraft:barrel", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("garden lobby", (15, 1, 3)); t.require_reachable("shared living pod", (8, 1, 18)); t.require_reachable("service core", (15, 1, 29)); t.require_walk_region("lower residential pod", (3, 1, 3), (17, 1, 31)); return t


def machine_shop() -> Template:
    t = Template(NATION, SETTLEMENT, "machine_shop", (31, 22, 36), "building"); t.clear((0, 0, 0), (30, 21, 35)); t.cuboid((1, 0, 1), (29, 0, 34), ROAD)
    t.cuboid((2, 1, 2), (28, 12, 32), WALL); t.clear((3, 1, 3), (27, 11, 31)); t.cuboid((4, 12, 5), (20, 15, 30), WALL); t.cuboid((18, 14, 9), (28, 20, 27), GLASS)
    t.cuboid((5, 1, 12), (25, 1, 14), "create:andesite_casing"); t.cuboid((6, 2, 13), (24, 2, 13), "create:belt"); t.cuboid((21, 1, 18), (25, 5, 24), "create:brass_casing"); t.cuboid((3, 4, 3), (27, 7, 3), GLASS)
    t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Visitor air lobby, assembly hall, and locked parts control.
    t.cuboid((3, 1, 10), (27, 4, 10), WALL); t.clear((15, 1, 10), (15, 2, 10))
    t.block(15, 1, 10, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 10, DOOR, {"half": "upper", "facing": "south"})
    t.cuboid((3, 1, 27), (27, 4, 27), WALL); t.clear((25, 1, 27), (25, 2, 27))
    t.block(25, 1, 27, DOOR, {"half": "lower", "facing": "south"}); t.block(25, 2, 27, DOOR, {"half": "upper", "facing": "south"})
    for x in (6, 15, 24):
        for z in (7, 18, 29): t.block(x, 5, z, WALL); t.block(x, 4, z, LIGHT)
    t.block(26, 1, 29, "minecraft:chest", {"facing": "west"}, {"id": "minecraft:chest", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("visitor air lobby", (15, 1, 3)); t.require_reachable("assembly observation", (10, 1, 18)); t.require_reachable("parts control", (25, 1, 29)); t.require_walk_region("assembly floor", (3, 1, 3), (27, 1, 31)); return t


def arcade() -> Template:
    t = Template(NATION, SETTLEMENT, "arcade", (31, 25, 46), "building"); t.clear((0, 0, 0), (30, 24, 45)); t.cuboid((1, 0, 1), (29, 0, 44), ROAD)
    t.cuboid((2, 1, 2), (28, 15, 42), WALL); t.clear((3, 1, 3), (27, 14, 41)); t.cuboid((4, 15, 6), (18, 19, 38), WALL); t.cuboid((15, 17, 11), (28, 23, 34), WALL)
    # Sunken reading court remains open to the surrounding public floor.
    t.cuboid((7, 1, 18), (23, 2, 32), TRIM); t.clear((8, 1, 19), (22, 4, 31)); t.clear((14, 1, 17), (16, 3, 19)); t.cuboid((9, 0, 20), (21, 0, 30), GROUND)
    t.cuboid((3, 4, 2), (24, 8, 2), GLASS); t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Library foyer opens to the reading court; a separate rear archive secures loot.
    t.cuboid((3, 1, 13), (27, 4, 13), WALL); t.clear((15, 1, 13), (15, 2, 13))
    t.block(15, 1, 13, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 13, DOOR, {"half": "upper", "facing": "south"})
    t.cuboid((21, 1, 35), (27, 4, 41), WALL); t.clear((22, 1, 36), (26, 3, 40))
    t.block(21, 1, 38, DOOR, {"half": "lower", "facing": "west"}); t.block(21, 2, 38, DOOR, {"half": "upper", "facing": "west"})
    for x in (6, 15, 24):
        for z in (7, 16, 27, 39): t.block(x, 5, z, WALL); t.block(x, 4, z, LIGHT)
    t.block(25, 1, 39, "minecraft:chest", {"facing": "west"}, {"id": "minecraft:chest", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("library entry", (15, 1, 3)); t.require_reachable("sunken reading court", (15, 1, 25)); t.require_reachable("archive service", (24, 1, 39)); t.require_walk_region("public library floor", (3, 1, 3), (27, 1, 41)); return t


def transit_station() -> Template:
    t = Template(NATION, SETTLEMENT, "transit_station", (31, 30, 46), "building"); t.clear((0, 0, 0), (30, 29, 45)); t.cuboid((1, 0, 1), (29, 0, 44), ROAD)
    t.cuboid((2, 1, 2), (28, 13, 42), WALL); t.clear((3, 1, 3), (27, 12, 41)); t.cuboid((3, 13, 5), (19, 17, 40), WALL); t.cuboid((14, 16, 9), (28, 22, 36), WALL); t.cuboid((21, 22, 14), (26, 29, 31), GLASS)
    t.cuboid((4, 1, 22), (26, 1, 24), "create:andesite_casing"); t.cuboid((4, 2, 23), (26, 2, 23), "create:shaft"); t.cuboid((3, 5, 2), (27, 8, 2), GLASS); t.cuboid((1, 8, 7), (29, 10, 13), WALL)
    t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Concourse, platform hall, and dispatch office use two controlled thresholds.
    t.cuboid((3, 1, 12), (27, 4, 12), WALL); t.clear((15, 1, 12), (15, 2, 12))
    t.block(15, 1, 12, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 12, DOOR, {"half": "upper", "facing": "south"})
    t.cuboid((3, 1, 35), (27, 4, 35), WALL); t.clear((25, 1, 35), (25, 2, 35))
    t.block(25, 1, 35, DOOR, {"half": "lower", "facing": "south"}); t.block(25, 2, 35, DOOR, {"half": "upper", "facing": "south"})
    for x in (6, 15, 24):
        for z in (7, 18, 30, 40): t.block(x, 5, z, WALL); t.block(x, 4, z, LIGHT)
    for x in (1, 29):
        for z in (8, 12): t.block(x, 4, z, LIGHT)
    t.block(26, 1, 40, "minecraft:barrel", nbt={"id": "minecraft:barrel", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("station concourse", (15, 1, 3)); t.require_reachable("layered platform access", (10, 1, 30)); t.require_reachable("service dispatch", (25, 1, 40)); t.require_walk_region("concourse and platform floor", (3, 1, 3), (27, 1, 41)); return t


def build_templates() -> list[Template]:
    templates = [center(), street_straight(), street_corner(), street_cross(), street_end(), dome_apartment(), machine_shop(), arcade(), transit_station()]; validate_nation(templates, NATION, SETTLEMENT); return templates


def main() -> None:
    args = output_argument("Generate isolated Durin CG settlement previews"); templates = build_templates()
    if not args.validate_only: write_preview(templates, args.output)
    print(f"Validated {len(templates)} independent Durin templates")


if __name__ == "__main__": main()
