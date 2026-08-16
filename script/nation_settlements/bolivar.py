"""Independent Bolivar/Dossoles settlement blockout from 48-series CGs."""

from __future__ import annotations

from .common import AIR, Template, output_argument, validate_nation, write_preview

NATION = "bolivar"; SETTLEMENT = "bolivar_dossoles_district"
ROAD = "minecraft:smooth_sandstone"; WALL = "zinecraft:bolivar_dossoles_stucco"
GROUND = "zinecraft:bolivar_war_scoured_soil"; TRIM = "minecraft:cut_copper"
GLASS = "minecraft:light_blue_stained_glass"; LIGHT = "minecraft:ochre_froglight"; DOOR = "minecraft:jungle_door"


def center() -> Template:
    t = Template(NATION, SETTLEMENT, "center", (32, 12, 32), "center")
    t.cuboid((0, 0, 12), (31, 0, 19), ROAD); t.cuboid((12, 0, 0), (19, 0, 31), ROAD)
    t.cuboid((5, 0, 5), (26, 0, 26), WALL); t.cuboid((8, 0, 8), (23, 0, 23), "minecraft:water")
    t.cuboid((10, 0, 10), (21, 0, 21), ROAD); t.cuboid((13, 1, 13), (18, 6, 18), GLASS)
    t.cuboid((14, 7, 14), (17, 10, 17), GLASS)
    for x, z in ((6, 6), (25, 6), (6, 25), (25, 25)):
        t.cuboid((x, 1, z), (x, 4, z), TRIM); t.block(x, 5, z, LIGHT)
    for direction, pos in (("north", (15, 1, 0)), ("south", (16, 1, 31)), ("west", (0, 1, 16)), ("east", (31, 1, 15))):
        t.connector(*pos, direction, f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    return t


def street_straight() -> Template:
    t = Template(NATION, SETTLEMENT, "street_straight", (32, 9, 32), "street")
    t.cuboid((12, 0, 0), (19, 0, 31), ROAD); t.cuboid((8, 0, 0), (11, 0, 31), "minecraft:moss_block"); t.cuboid((20, 0, 0), (23, 0, 31), "minecraft:moss_block")
    t.cuboid((5, 0, 0), (7, 0, 31), WALL); t.cuboid((24, 0, 0), (26, 0, 31), WALL)
    for z in (5, 15, 25):
        t.cuboid((9, 1, z), (9, 4, z), TRIM); t.block(9, 5, z, LIGHT); t.cuboid((22, 1, z), (22, 4, z), TRIM); t.block(22, 5, z, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(16, 1, 31, "south", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.connector(31, 1, 16, "east", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    return t


def street_corner() -> Template:
    t = Template(NATION, SETTLEMENT, "street_corner", (32, 10, 32), "street")
    t.cuboid((12, 0, 0), (19, 0, 19), ROAD); t.cuboid((12, 0, 12), (31, 0, 19), ROAD)
    t.cuboid((5, 0, 7), (11, 0, 26), "minecraft:moss_block"); t.cuboid((20, 0, 20), (27, 0, 25), WALL)
    t.cuboid((7, 1, 9), (7, 5, 24), TRIM); t.cuboid((7, 6, 9), (18, 6, 9), TRIM); t.block(8, 5, 9, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(31, 1, 15, "east", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    t.connector(15, 1, 31, "south", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    return t


def street_cross() -> Template:
    t = Template(NATION, SETTLEMENT, "street_cross", (32, 8, 32), "street")
    t.cuboid((12, 0, 0), (19, 0, 31), ROAD); t.cuboid((0, 0, 12), (31, 0, 19), ROAD)
    for x, z in ((8, 8), (23, 8), (8, 23), (23, 23)):
        t.cuboid((x, 0, z), (x + 2, 0, z + 2), "minecraft:moss_block"); t.cuboid((x + 1, 1, z + 1), (x + 1, 3, z + 1), TRIM); t.block(x + 1, 4, z + 1, LIGHT)
    for direction, pos in (("north", (15, 1, 0)), ("south", (16, 1, 31)), ("west", (0, 1, 16)), ("east", (31, 1, 15))): t.connector(*pos, direction, f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    return t


def street_end() -> Template:
    t = Template(NATION, SETTLEMENT, "street_end", (32, 9, 32), "street")
    t.cuboid((12, 0, 0), (19, 0, 23), ROAD); t.cuboid((6, 0, 20), (25, 0, 30), WALL); t.cuboid((8, 0, 23), (23, 0, 30), "minecraft:water")
    t.cuboid((5, 1, 21), (26, 1, 23), TRIM); t.cuboid((7, 2, 24), (7, 5, 29), TRIM); t.cuboid((24, 2, 24), (24, 5, 29), TRIM); t.block(7, 6, 26, LIGHT); t.block(24, 6, 26, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.connector(31, 1, 16, "east", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR)
    return t


def canal_house() -> Template:
    t = Template(NATION, SETTLEMENT, "canal_house", (29, 24, 32), "building"); t.clear((0, 0, 0), (28, 23, 31)); t.cuboid((1, 0, 1), (27, 0, 30), GROUND)
    t.cuboid((2, 1, 2), (26, 10, 27), WALL); t.clear((3, 1, 3), (25, 9, 26))
    t.cuboid((7, 10, 6), (26, 16, 24), WALL); t.clear((8, 10, 7), (25, 15, 23)); t.cuboid((13, 16, 10), (24, 20, 21), WALL)
    t.cuboid((2, 11, 3), (6, 13, 18), "minecraft:moss_block"); t.cuboid((3, 5, 2), (24, 7, 2), GLASS); t.cuboid((5, 1, 28), (23, 1, 30), ROAD)
    t.block(14, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(14, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((14, 1, 2), (14, 2, 2))
    # Canal vestibule and waterfront living room are divided by a lockable inner door.
    t.cuboid((3, 1, 11), (25, 4, 11), WALL); t.clear((14, 1, 11), (14, 2, 11))
    t.block(14, 1, 11, DOOR, {"half": "lower", "facing": "south"}); t.block(14, 2, 11, DOOR, {"half": "upper", "facing": "south"})
    # The hollow upper canal wing is a real sleeping floor, reached by a continuous stair.
    t.cuboid((8, 9, 7), (25, 9, 23), WALL); t.clear((8, 9, 7), (8, 11, 15))
    for y, z in enumerate(range(7, 16), start=1):
        t.block(8, y, z, "minecraft:cut_copper_stairs", {"facing": "south"})
    t.cuboid((9, 10, 16), (25, 13, 16), WALL); t.clear((16, 10, 16), (16, 11, 16))
    t.block(16, 10, 16, DOOR, {"half": "lower", "facing": "south"}); t.block(16, 11, 16, DOOR, {"half": "upper", "facing": "south"})
    for x, z in ((11, 10), (20, 10), (11, 20), (20, 20)):
        t.block(x, 15, z, TRIM); t.block(x, 14, z, LIGHT)
    for x, z in ((7, 7), (14, 7), (21, 7), (7, 19), (14, 19), (21, 19)):
        t.block(x, 5, z, TRIM); t.block(x, 4, z, LIGHT)
    t.block(24, 1, 24, "minecraft:barrel", nbt={"id": "minecraft:barrel", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(14, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("shared lobby", (14, 1, 3)); t.require_reachable("waterfront living room", (8, 1, 20)); t.require_walk_region("public lower floor", (4, 1, 4), (24, 1, 25)); return t


def beach_market() -> Template:
    t = Template(NATION, SETTLEMENT, "beach_market", (31, 20, 40), "building"); t.clear((0, 0, 0), (30, 19, 39)); t.cuboid((1, 0, 1), (29, 0, 38), GROUND)
    # Open gallery: five independent blade supports under a folded canopy.
    for x in (2, 8, 15, 22, 28): t.cuboid((x, 1, 3), (x, 10 + (x % 3), 35), WALL)
    t.clear((15, 1, 3), (15, 2, 5))
    t.cuboid((2, 11, 3), (16, 12, 35), WALL); t.cuboid((12, 13, 6), (28, 15, 32), WALL); t.cuboid((5, 3, 8), (25, 3, 10), TRIM); t.cuboid((5, 3, 25), (25, 3, 27), TRIM)
    t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Two vendor lockups flank the covered public gallery; both use real doors.
    t.cuboid((3, 1, 12), (9, 4, 18), WALL); t.clear((4, 1, 13), (8, 3, 17))
    t.block(9, 1, 15, DOOR, {"half": "lower", "facing": "east"}); t.block(9, 2, 15, DOOR, {"half": "upper", "facing": "east"})
    t.cuboid((20, 1, 30), (27, 4, 36), WALL); t.clear((21, 1, 31), (26, 3, 35))
    t.block(20, 1, 33, DOOR, {"half": "lower", "facing": "west"}); t.block(20, 2, 33, DOOR, {"half": "upper", "facing": "west"})
    for x, z in ((5, 6), (12, 6), (20, 6), (26, 6), (5, 20), (12, 20), (20, 20), (26, 20), (5, 33), (20, 33)):
        t.block(x, 5, z, TRIM); t.block(x, 4, z, LIGHT)
    for x in (5, 11, 18, 25):
        t.block(x, 5, 31, TRIM); t.block(x, 4, 31, LIGHT)
    t.block(6, 3, 15, LIGHT)
    t.block(23, 5, 29, TRIM); t.block(23, 4, 29, LIGHT)
    t.block(26, 1, 34, "minecraft:chest", {"facing": "west"}, {"id": "minecraft:chest", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("market threshold", (15, 1, 3)); t.require_reachable("rear service lane", (15, 1, 36)); t.require_walk_region("open market gallery", (3, 1, 3), (27, 1, 36)); return t


def race_workshop() -> Template:
    t = Template(NATION, SETTLEMENT, "race_workshop", (31, 25, 38), "building"); t.clear((0, 0, 0), (30, 24, 37)); t.cuboid((1, 0, 1), (29, 0, 36), GROUND)
    t.cuboid((2, 1, 2), (28, 13, 35), WALL); t.clear((3, 1, 3), (27, 12, 34)); t.cuboid((4, 13, 5), (14, 17, 33), WALL); t.cuboid((17, 13, 8), (28, 21, 30), WALL)
    t.cuboid((3, 4, 2), (12, 9, 2), GLASS); t.cuboid((18, 4, 2), (27, 9, 2), GLASS); t.cuboid((7, 1, 13), (23, 1, 15), "create:andesite_casing"); t.cuboid((8, 2, 14), (22, 2, 14), "create:shaft")
    t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Reception, repair bay, and controlled parts cage form three sequential rooms.
    t.cuboid((3, 1, 10), (27, 4, 10), WALL); t.clear((15, 1, 10), (15, 2, 10))
    t.block(15, 1, 10, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 10, DOOR, {"half": "upper", "facing": "south"})
    t.cuboid((3, 1, 28), (27, 4, 28), WALL); t.clear((24, 1, 28), (24, 2, 28))
    t.block(24, 1, 28, DOOR, {"half": "lower", "facing": "south"}); t.block(24, 2, 28, DOOR, {"half": "upper", "facing": "south"})
    for x, z in ((6, 7), (15, 7), (24, 7), (6, 20), (15, 20), (24, 20), (6, 31), (15, 31), (24, 31)):
        t.block(x, 5, z, TRIM); t.block(x, 4, z, LIGHT)
    t.block(25, 1, 32, "minecraft:barrel", nbt={"id": "minecraft:barrel", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("vehicle reception", (15, 1, 3)); t.require_reachable("unequal repair bay", (8, 1, 24)); t.require_reachable("parts control", (24, 1, 31)); t.require_walk_region("workshop service floor", (4, 1, 4), (26, 1, 33)); return t


def festival_hall() -> Template:
    t = Template(NATION, SETTLEMENT, "festival_hall", (31, 32, 46), "building"); t.clear((0, 0, 0), (30, 31, 45)); t.cuboid((1, 0, 1), (29, 0, 44), GROUND)
    t.cuboid((2, 1, 2), (28, 18, 42), WALL); t.clear((3, 1, 3), (27, 17, 41)); t.cuboid((5, 18, 6), (25, 21, 37), WALL); t.cuboid((10, 22, 11), (27, 25, 34), WALL); t.cuboid((18, 26, 16), (25, 30, 28), GLASS)
    t.cuboid((3, 5, 2), (10, 12, 2), GLASS); t.cuboid((20, 5, 2), (27, 12, 2), GLASS); t.cuboid((5, 1, 28), (25, 2, 38), TRIM); t.clear((6, 1, 29), (24, 4, 37))
    t.clear((14, 1, 28), (16, 3, 29))
    t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Crystal foyer, exhibition hall, and backstage loading are separately closable.
    t.cuboid((3, 1, 12), (27, 4, 12), WALL); t.clear((15, 1, 12), (15, 2, 12))
    t.block(15, 1, 12, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 12, DOOR, {"half": "upper", "facing": "south"})
    t.cuboid((3, 1, 27), (27, 4, 27), WALL); t.clear((15, 1, 27), (15, 2, 27))
    t.block(15, 1, 27, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 27, DOOR, {"half": "upper", "facing": "south"})
    t.clear((24, 1, 38), (24, 2, 38))
    t.block(24, 1, 38, DOOR, {"half": "lower", "facing": "south"}); t.block(24, 2, 38, DOOR, {"half": "upper", "facing": "south"})
    for x in (6, 15, 24):
        for z in (7, 19, 31, 40): t.block(x, 5, z, TRIM); t.block(x, 4, z, LIGHT)
    t.block(25, 1, 40, "minecraft:chest", {"facing": "west"}, {"id": "minecraft:chest", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("crystal entry", (15, 1, 3)); t.require_reachable("public exhibition floor", (8, 1, 20)); t.require_reachable("backstage loading", (24, 1, 40)); t.require_walk_region("exhibition hall", (4, 1, 4), (26, 1, 40)); return t


def build_templates() -> list[Template]:
    templates = [center(), street_straight(), street_corner(), street_cross(), street_end(), canal_house(), beach_market(), race_workshop(), festival_hall()]; validate_nation(templates, NATION, SETTLEMENT); return templates


def main() -> None:
    args = output_argument("Generate isolated Bolivar CG settlement previews"); templates = build_templates()
    if not args.validate_only: write_preview(templates, args.output)
    print(f"Validated {len(templates)} independent Bolivar templates")


if __name__ == "__main__": main()
