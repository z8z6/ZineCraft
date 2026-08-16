"""Independent Higashi/Jinda settlement blockout from 64-series CGs."""

from __future__ import annotations

from .common import AIR, Template, output_argument, validate_nation, write_preview

NATION = "higashi"; SETTLEMENT = "higashi_sokogawa_town"
ROAD = "minecraft:polished_deepslate"; GROUND = "zinecraft:higashi_shadow_loam"; WALL = "zinecraft:higashi_machiya_plaster"
WOOD = "minecraft:dark_oak_planks"; METAL = "minecraft:polished_blackstone"; GLASS = "minecraft:tinted_glass"; LIGHT = "minecraft:shroomlight"; DOOR = "minecraft:dark_oak_door"


def center() -> Template:
    t = Template(NATION, SETTLEMENT, "center", (32, 14, 32), "center")
    t.cuboid((0, 0, 12), (31, 0, 19), ROAD); t.cuboid((12, 0, 0), (19, 0, 31), ROAD)
    t.cuboid((7, 0, 7), (24, 0, 24), GROUND); t.cuboid((10, 0, 10), (21, 0, 21), WALL)
    t.cuboid((11, 1, 11), (20, 1, 20), WOOD); t.cuboid((13, 2, 13), (18, 7, 18), METAL); t.clear((14, 2, 14), (17, 6, 17))
    t.cuboid((6, 8, 14), (25, 9, 17), METAL); t.cuboid((8, 10, 15), (23, 11, 16), METAL); t.block(10, 7, 15, LIGHT); t.block(21, 7, 16, LIGHT)
    for direction, pos in (("north", (15, 1, 0)), ("south", (16, 1, 31)), ("west", (0, 1, 16)), ("east", (31, 1, 15))): t.connector(*pos, direction, f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    return t


def street_straight() -> Template:
    t = Template(NATION, SETTLEMENT, "street_straight", (32, 13, 32), "street"); t.cuboid((12, 0, 0), (19, 0, 31), ROAD); t.cuboid((9, 0, 0), (11, 0, 31), WALL); t.cuboid((20, 0, 0), (22, 0, 31), WALL)
    for z in (4, 12, 20, 28): t.cuboid((10, 1, z), (10, 4, z), METAL); t.block(10, 5, z, LIGHT); t.cuboid((21, 1, z), (21, 4, z), METAL); t.block(21, 5, z, LIGHT)
    t.cuboid((7, 8, 14), (24, 9, 17), METAL); t.cuboid((9, 10, 15), (22, 11, 16), METAL); t.cuboid((8, 7, 15), (8, 8, 16), WOOD)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(16, 1, 31, "south", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.connector(31, 1, 16, "east", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); return t


def street_corner() -> Template:
    t = Template(NATION, SETTLEMENT, "street_corner", (32, 12, 32), "street"); t.cuboid((12, 0, 0), (19, 0, 19), ROAD); t.cuboid((12, 0, 12), (31, 0, 19), ROAD)
    t.cuboid((8, 0, 6), (11, 0, 25), WALL); t.cuboid((20, 0, 20), (27, 0, 23), WALL); t.cuboid((7, 7, 13), (22, 8, 16), METAL); t.block(9, 6, 14, LIGHT); t.block(20, 6, 15, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(31, 1, 15, "east", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(15, 1, 31, "south", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); return t


def street_cross() -> Template:
    t = Template(NATION, SETTLEMENT, "street_cross", (32, 11, 32), "street"); t.cuboid((12, 0, 0), (19, 0, 31), ROAD); t.cuboid((0, 0, 12), (31, 0, 19), ROAD)
    t.cuboid((6, 6, 14), (25, 7, 17), METAL); t.cuboid((10, 8, 15), (21, 9, 16), METAL); t.block(8, 5, 15, LIGHT); t.block(23, 5, 16, LIGHT)
    for direction, pos in (("north", (15, 1, 0)), ("south", (16, 1, 31)), ("west", (0, 1, 16)), ("east", (31, 1, 15))): t.connector(*pos, direction, f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD)
    return t


def street_end() -> Template:
    t = Template(NATION, SETTLEMENT, "street_end", (32, 12, 32), "street"); t.cuboid((12, 0, 0), (19, 0, 24), ROAD); t.cuboid((6, 0, 22), (25, 0, 30), GROUND)
    t.cuboid((8, 1, 24), (23, 6, 29), WALL); t.clear((10, 1, 24), (21, 4, 28)); t.cuboid((7, 7, 23), (24, 8, 30), WOOD); t.cuboid((10, 9, 25), (21, 10, 28), WOOD); t.block(10, 5, 24, LIGHT); t.block(21, 5, 24, LIGHT)
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/street", f"zinecraft:{SETTLEMENT}/streets", ROAD); t.connector(0, 1, 15, "west", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.connector(31, 1, 16, "east", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); return t


def machiya() -> Template:
    t = Template(NATION, SETTLEMENT, "machiya", (24, 30, 46), "building"); t.clear((0, 0, 0), (23, 29, 45)); t.cuboid((1, 0, 1), (22, 0, 44), GROUND)
    # Three narrow streetwall units with unequal eaves and rear equipment spine.
    t.cuboid((2, 1, 2), (21, 15, 42), WALL); t.clear((3, 1, 3), (20, 14, 41)); t.cuboid((3, 15, 4), (9, 21, 34), WALL); t.cuboid((11, 15, 7), (20, 25, 38), WALL); t.cuboid((17, 25, 12), (21, 28, 31), METAL)
    for y, inset in ((7, 0), (14, 1), (21, 2), (26, 3)): t.cuboid((2 + inset, y, 2 + inset), (21, y + 1, 42 - inset), WOOD)
    t.cuboid((2, 3, 2), (21, 5, 2), GLASS); t.cuboid((2, 1, 36), (5, 20, 42), METAL); t.cuboid((4, 6, 39), (7, 6, 39), "create:shaft")
    t.block(12, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(12, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((12, 1, 2), (12, 2, 2))
    # Street shop, shared living room, and rear service spine have distinct closures.
    t.cuboid((3, 1, 12), (20, 4, 12), WALL); t.clear((12, 1, 12), (12, 2, 12))
    t.block(12, 1, 12, DOOR, {"half": "lower", "facing": "south"}); t.block(12, 2, 12, DOOR, {"half": "upper", "facing": "south"})
    t.cuboid((3, 1, 34), (20, 4, 34), WALL); t.clear((18, 1, 34), (18, 2, 34))
    t.block(18, 1, 34, DOOR, {"half": "lower", "facing": "south"}); t.block(18, 2, 34, DOOR, {"half": "upper", "facing": "south"})
    # Open the existing timber floor and give the family loft a seven-step stair.
    t.clear((4, 7, 8), (4, 11, 15))
    for y, z in enumerate(range(8, 16), start=1):
        t.block(4, y, z, "minecraft:dark_oak_stairs", {"facing": "south"})
    t.cuboid((3, 9, 22), (20, 11, 22), WALL); t.clear((12, 9, 22), (12, 10, 22))
    t.block(12, 9, 22, DOOR, {"half": "lower", "facing": "south"}); t.block(12, 10, 22, DOOR, {"half": "upper", "facing": "south"})
    for x, z in ((7, 17), (16, 17), (7, 29), (16, 29)):
        t.block(x, 14, z, WOOD); t.block(x, 13, z, LIGHT)
    for x in (5, 10, 15, 20):
        for z in (5, 12, 20, 28, 36, 41): t.block(x, 13, z, LIGHT)
    for x in (5, 12, 19):
        for z in (7, 19, 31, 40): t.block(x, 5, z, WOOD); t.block(x, 4, z, LIGHT)
    t.block(19, 1, 39, "minecraft:barrel", nbt={"id": "minecraft:barrel", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(12, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("street shop threshold", (12, 1, 3)); t.require_reachable("deep shared living", (12, 1, 22)); t.require_reachable("rear service spine", (18, 1, 39)); t.require_walk_region("deep ground unit", (3, 1, 3), (20, 1, 41)); return t


def swordsmith() -> Template:
    t = Template(NATION, SETTLEMENT, "swordsmith", (31, 25, 40), "building"); t.clear((0, 0, 0), (30, 24, 39)); t.cuboid((1, 0, 1), (29, 0, 38), GROUND)
    t.cuboid((2, 1, 2), (28, 14, 36), WALL); t.clear((3, 1, 3), (27, 13, 35)); t.cuboid((3, 14, 4), (13, 18, 34), WOOD); t.cuboid((16, 14, 8), (28, 21, 31), METAL); t.cuboid((23, 21, 18), (27, 24, 27), METAL)
    t.cuboid((4, 1, 14), (11, 2, 30), "create:andesite_casing"); t.cuboid((6, 3, 16), (9, 3, 28), "create:shaft"); t.cuboid((18, 1, 17), (25, 4, 25), "minecraft:blast_furnace")
    t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Customer counter, hot machining floor, and quality store are fire-separated.
    t.cuboid((3, 1, 11), (27, 4, 11), WALL); t.clear((15, 1, 11), (15, 2, 11))
    t.block(15, 1, 11, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 11, DOOR, {"half": "upper", "facing": "south"})
    t.cuboid((3, 1, 30), (27, 4, 30), WALL); t.clear((25, 1, 30), (25, 2, 30))
    t.block(25, 1, 30, DOOR, {"half": "lower", "facing": "south"}); t.block(25, 2, 30, DOOR, {"half": "upper", "facing": "south"})
    for x in (6, 15, 24):
        for z in (7, 19, 31): t.block(x, 5, z, METAL); t.block(x, 4, z, LIGHT)
    for z in (14, 28): t.block(26, 5, z, METAL); t.block(26, 4, z, LIGHT)
    t.block(26, 1, 33, "minecraft:barrel", nbt={"id": "minecraft:barrel", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("wide works entrance", (15, 1, 3)); t.require_reachable("machining line", (14, 1, 20)); t.require_reachable("quality store", (25, 1, 33)); t.require_walk_region("processing floor", (3, 1, 3), (27, 1, 35)); return t


def tea_house() -> Template:
    t = Template(NATION, SETTLEMENT, "tea_house", (25, 20, 34), "building"); t.clear((0, 0, 0), (24, 19, 33)); t.cuboid((1, 0, 1), (23, 0, 32), GROUND)
    t.cuboid((2, 1, 2), (22, 12, 30), WALL); t.clear((3, 1, 3), (21, 11, 29)); t.cuboid((13, 1, 17), (21, 1, 27), "minecraft:moss_block"); t.cuboid((15, 0, 19), (19, 0, 25), "minecraft:water")
    t.cuboid((2, 12, 3), (13, 15, 28), WOOD); t.cuboid((10, 14, 8), (22, 17, 25), WOOD); t.cuboid((3, 4, 2), (20, 6, 2), GLASS)
    t.block(12, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(12, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((12, 1, 2), (12, 2, 2))
    # Public tearoom, private meeting room, and recessed court service are distinct.
    t.cuboid((3, 1, 12), (21, 4, 12), WALL); t.clear((12, 1, 12), (12, 2, 12))
    t.block(12, 1, 12, DOOR, {"half": "lower", "facing": "south"}); t.block(12, 2, 12, DOOR, {"half": "upper", "facing": "south"})
    t.cuboid((3, 1, 24), (13, 4, 24), WALL); t.clear((12, 1, 24), (12, 2, 24))
    t.block(12, 1, 24, DOOR, {"half": "lower", "facing": "south"}); t.block(12, 2, 24, DOOR, {"half": "upper", "facing": "south"})
    for x, z in ((6, 7), (12, 7), (18, 7), (6, 18), (12, 18), (6, 27), (12, 27)):
        t.block(x, 5, z, WOOD); t.block(x, 4, z, LIGHT)
    t.block(21, 4, 14, LIGHT)
    t.block(5, 1, 27, "minecraft:chest", {"facing": "east"}, {"id": "minecraft:chest", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(12, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("restaurant front", (12, 1, 3)); t.require_reachable("meeting room", (6, 1, 20)); t.require_reachable("recessed court edge", (12, 1, 27)); t.require_walk_region("front and service floor", (3, 1, 3), (12, 1, 29)); return t


def magistrate_house() -> Template:
    t = Template(NATION, SETTLEMENT, "magistrate_house", (31, 31, 36), "building"); t.clear((0, 0, 0), (30, 30, 35)); t.cuboid((1, 0, 1), (29, 0, 34), GROUND)
    t.cuboid((2, 1, 2), (28, 16, 33), WALL); t.clear((3, 1, 3), (27, 15, 32)); t.cuboid((4, 16, 5), (17, 22, 31), WALL); t.cuboid((19, 16, 9), (28, 27, 28), METAL); t.cuboid((22, 27, 14), (26, 30, 23), METAL)
    t.cuboid((4, 1, 20), (10, 6, 29), "create:andesite_casing"); t.clear((5, 1, 21), (9, 5, 28)); t.cuboid((2, 5, 2), (12, 9, 2), GLASS); t.cuboid((18, 4, 2), (27, 8, 2), GLASS)
    t.clear((10, 1, 23), (10, 2, 24))
    t.block(15, 1, 1, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 1, DOOR, {"half": "upper", "facing": "south"})
    t.clear((15, 1, 2), (15, 2, 2))
    # Petition hall and distribution office are divided from the secure record cage.
    t.cuboid((3, 1, 12), (27, 4, 12), WALL); t.clear((15, 1, 12), (15, 2, 12))
    t.block(15, 1, 12, DOOR, {"half": "lower", "facing": "south"}); t.block(15, 2, 12, DOOR, {"half": "upper", "facing": "south"})
    t.block(10, 1, 24, DOOR, {"half": "lower", "facing": "east"}); t.block(10, 2, 24, DOOR, {"half": "upper", "facing": "east"})
    for x in (6, 15, 24):
        for z in (7, 18, 30): t.block(x, 5, z, METAL); t.block(x, 4, z, LIGHT)
    t.block(7, 5, 24, METAL); t.block(7, 4, 24, LIGHT)
    t.block(25, 1, 30, "minecraft:chest", {"facing": "west"}, {"id": "minecraft:chest", "LootTable": f"zinecraft:chests/nation/{NATION}_structure", "LootTableSeed": 0})
    t.connector(15, 1, 0, "north", f"zinecraft:{SETTLEMENT}/building", "minecraft:empty", "minecraft:empty", AIR); t.require_reachable("bank hall", (15, 1, 3)); t.require_reachable("distribution desk", (16, 1, 20)); t.require_reachable("rear loading office", (24, 1, 30)); t.require_walk_region("bank and distribution floor", (3, 1, 3), (27, 1, 32)); return t


def build_templates() -> list[Template]:
    templates = [center(), street_straight(), street_corner(), street_cross(), street_end(), machiya(), swordsmith(), tea_house(), magistrate_house()]; validate_nation(templates, NATION, SETTLEMENT); return templates


def main() -> None:
    args = output_argument("Generate isolated Higashi CG settlement previews"); templates = build_templates()
    if not args.validate_only: write_preview(templates, args.output)
    print(f"Validated {len(templates)} independent Higashi templates")


if __name__ == "__main__": main()
