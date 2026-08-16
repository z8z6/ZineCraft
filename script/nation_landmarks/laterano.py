"""Independent Laterano landmark builders.

Source checked visually: 26_g1_laterano_cathedralfront.png.  The two public
compatibility ids deliberately implement different masses: a needle/arcade
communications tower and the axial folded-front community sanctuary.
"""

from .base import (
    AIR, BARS, BOOKSHELF, CARTOGRAPHY, CASING, FENCE, GIRDER, GLASS,
    GROUND, LADDER, LADDER_STATE, LECTERN, LIGHT, SLAB, STAIR_E, TRIM,
    WALL, LandmarkBuild, Module, Spec, prove_room_route,
)


def build_revelation_tower() -> LandmarkBuild:
    spec = Spec(
        "laterano_revelation_tower", "laterano", "public law and communications tower",
        "26_g1_laterano_cathedralfront.png",
        ("long white civic axis", "repeated slender arcade", "stepped needle above a broad podium", "thin gold vertical accents"),
        "XL 144x96x112",
    )
    f = Module(spec, "foundation", (48, 10, 48))
    f.fill((0, 0, 0), (47, 0, 47), GROUND)
    for inset, y in ((2, 1), (6, 2), (11, 3)):
        f.fill((inset, y, inset), (47 - inset, y, 47 - inset), TRIM)
    f.fill((0, 1, 21), (47, 3, 26), WALL)
    f.fill((0, 2, 22), (47, 3, 25), AIR)
    for x, z in ((7, 7), (40, 7), (7, 40), (40, 40)):
        f.fill((x, 1, z), (x, 3, z), FENCE); f.light(x, 4, z)
    f.parent((47, 2, 23), "east", "foundation_core", "core")
    f.parent((0, 2, 23), "west", "foundation_surrounding", "surrounding")

    c = Module(spec, "core", (40, 48, 40))
    c.fill((0, 0, 0), (39, 0, 39), WALL)
    for y in range(1, 42):
        inset = min(9, y // 6)
        for x in range(inset, 40 - inset):
            c.set(x, y, inset, WALL); c.set(x, y, 39 - inset, WALL)
        for z in range(inset, 40 - inset):
            c.set(inset, y, z, WALL); c.set(39 - inset, y, z, WALL)
    for y in (10, 20, 30):
        c.fill((2 + y // 6, y, 2 + y // 6), (37 - y // 6, y, 37 - y // 6), TRIM)
    # Four-storey archive/control stack with a non-central stair spine.
    c.fill((2, 1, 20), (37, 9, 20), WALL)
    c.fill((19, 1, 20), (21, 3, 20), AIR); c.door(20, 1, 20)
    c.fill((2, 11, 18), (31, 19, 18), WALL)
    c.fill((14, 11, 18), (16, 13, 18), AIR); c.door(15, 11, 18)
    for i in range(9):
        x, y, z = 4 + i, 1 + i, 6
        c.set(x, y, z, STAIR_E); c.stairs.add((x, y, z)); c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    for x, z in ((8, 8), (28, 8), (8, 30), (28, 30), (12, 24), (24, 24), (12, 14), (24, 14)):
        c.set(x, 9, z, TRIM); c.light(x, 8, z)
    for x in (6, 11, 16, 23, 28, 33):
        for y in (4, 14, 24):
            c.fill((x, y, 0), (x + 1, y + 4, 0), GLASS)
    c.fill((25, 1, 26), (33, 1, 28), SLAB); c.set(29, 1, 25, LECTERN)
    c.fill((5, 1, 28), (12, 3, 28), BOOKSHELF)
    c.chest(35, 1, 35); c.chest(35, 11, 29); c.chest(25, 21, 25)
    for y in range(31, 47):
        c.set(19, y, 21, TRIM); c.set(19, y, 20, LADDER)
    c.child((0, 2, 19), "west", "foundation_core")
    c.parent((39, 2, 19), "east", "core_facade", "facade")
    c.parent((19, 2, 39), "south", "core_annex", "annex")
    c.parent((19, 47, 20), "up", "core_roof", "roof", LADDER_STATE)
    for x, y, z in ((0, 2, 19), (39, 2, 19), (19, 2, 39)):
        c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    prove_room_route(c, (4, 1, 4), ((20, 1, 21), (34, 1, 35)))

    a = Module(spec, "facade", (32, 36, 40))
    a.fill((0, 0, 0), (31, 0, 39), TRIM)
    # A deep screen of lancet-like slots instead of a flat wall.
    for x in range(2, 31, 4):
        height = 18 + (12 - abs(16 - x)) // 2
        a.fill((x, 1, 5), (x + 1, height, 7), WALL)
        a.fill((x, 5, 4), (x + 1, height - 3, 4), GLASS)
        a.fill((x - 1, height + 1, 5), (min(31, x + 2), height + 2, 7), TRIM)
    a.fill((2, 1, 12), (29, 4, 34), WALL)
    a.fill((5, 2, 13), (26, 4, 33), AIR)
    a.child((0, 2, 19), "west", "core_facade")
    a.set(0, 3, 19, AIR); a.set(0, 4, 19, AIR)

    r = Module(spec, "roof", (40, 48, 40))
    r.fill((4, 0, 4), (35, 1, 35), TRIM)
    r.child((19, 0, 20), "down", "core_roof", LADDER_STATE)
    r.set(19, 1, 21, TRIM); r.set(19, 1, 20, LADDER); r.set(19, 2, 20, LADDER)
    for y in range(2, 45):
        radius = max(1, 8 - y // 6)
        r.fill((19 - radius, y, 20 - radius), (19 + radius, y, 20 + radius), WALL if y % 7 else TRIM)
    for y in (8, 16, 24, 32, 40):
        r.fill((10, y, 20), (28, y, 20), TRIM)
    r.light(19, 45, 20); r.set(19, 44, 20, GIRDER)
    for x in range(5, 35):
        r.set(x, 2, 4, BARS); r.set(x, 2, 35, BARS)

    n = Module(spec, "annex", (36, 24, 32))
    n.fill((0, 0, 0), (35, 0, 31), TRIM)
    for x in range(2, 35, 5):
        n.fill((x, 1, 4), (x + 1, 14, 6), WALL)
        n.fill((x, 15, 4), (x + 4 if x < 30 else 34, 16, 27), TRIM)
    n.fill((2, 1, 6), (34, 12, 27), WALL); n.fill((4, 2, 8), (32, 11, 25), AIR)
    n.fill((18, 2, 8), (18, 11, 25), WALL); n.fill((18, 2, 15), (18, 4, 17), AIR); n.door(18, 2, 16)
    n.set(12, 1, 20, CARTOGRAPHY); n.set(24, 1, 20, LECTERN)
    n.child((17, 2, 0), "north", "core_annex")
    n.set(17, 3, 0, AIR); n.set(17, 4, 0, AIR)

    s = Module(spec, "surrounding", (48, 12, 48))
    s.fill((0, 0, 0), (47, 0, 47), GROUND)
    s.fill((0, 1, 20), (47, 1, 27), TRIM)
    for x in range(4, 45, 6):
        s.fill((x, 1, 6), (x + 1, 8, 7), WALL)
        s.fill((x, 1, 40), (x + 1, 8, 41), WALL)
    s.child((47, 2, 23), "east", "foundation_surrounding")
    s.set(47, 3, 23, AIR); s.set(47, 4, 23, AIR)
    return LandmarkBuild(spec, (f, c, a, r, n, s), (168, 96, 76))


def build_ambrosius_chapel() -> LandmarkBuild:
    spec = Spec(
        "laterano_ambrosius_chapel", "laterano", "community hall and sanctuary",
        "26_g1_laterano_cathedralfront.png",
        ("deep axial nave", "folded white frontal ribs", "needle-thin pointed entrance", "arcaded civic square"),
        "XL 160x72x120",
    )
    f = Module(spec, "foundation", (48, 10, 48)); f.fill((0, 0, 0), (47, 0, 47), GROUND)
    for z in range(5, 48): f.fill((18, 1, z), (29, 1, z), TRIM)
    for step in range(4): f.fill((8 + step, 1 + step, 6 + step), (39 - step, 1 + step, 18 + step), WALL)
    for x, z in ((6, 6), (41, 6), (6, 41), (41, 41)):
        f.fill((x, 1, z), (x, 3, z), FENCE); f.light(x, 4, z)
    f.parent((47, 2, 23), "east", "foundation_core", "core"); f.parent((0, 2, 23), "west", "foundation_surrounding", "surrounding")
    f.set(47, 3, 23, AIR); f.set(47, 4, 23, AIR); f.set(0, 3, 23, AIR); f.set(0, 4, 23, AIR)

    c = Module(spec, "core", (48, 36, 40)); c.fill((0, 0, 0), (47, 0, 39), WALL)
    # Long nave shell tapers upward; the side aisles remain visibly lower.
    for y in range(1, 31):
        inset = max(0, (y - 20) // 3)
        c.fill((3 + inset, y, 2), (44 - inset, y, 3), WALL); c.fill((3 + inset, y, 36), (44 - inset, y, 37), WALL)
    c.fill((3, 1, 2), (5, 20, 37), WALL); c.fill((42, 1, 2), (44, 20, 37), WALL)
    for x in (10, 18, 29, 37):
        c.fill((x, 1, 5), (x + 1, 19, 7), WALL); c.fill((x, 1, 32), (x + 1, 19, 34), WALL)
        c.fill((x, 6, 4), (x + 1, 15, 4), GLASS); c.fill((x, 6, 35), (x + 1, 15, 35), GLASS)
    c.fill((8, 1, 20), (39, 10, 20), WALL); c.fill((22, 1, 20), (25, 4, 20), AIR); c.door(23, 1, 20)
    c.fill((6, 1, 26), (18, 1, 28), SLAB); c.fill((29, 1, 26), (41, 1, 28), SLAB)
    c.set(23, 1, 30, LECTERN); c.fill((21, 1, 32), (26, 3, 32), BOOKSHELF)
    for i in range(8):
        x, y, z = 7 + i, 1 + i, 10
        c.set(x, y, z, STAIR_E); c.stairs.add((x, y, z)); c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    for x, z in ((8, 10), (20, 10), (32, 10), (40, 10), (8, 29), (20, 29), (32, 29), (40, 29)):
        c.set(x, 19, z, TRIM); c.light(x, 18, z)
    c.chest(6, 1, 35); c.chest(40, 1, 35); c.set(40, 10, 30, TRIM); c.chest(40, 11, 30)
    c.fill((32, 11, 20), (43, 19, 20), WALL); c.fill((36, 11, 20), (38, 13, 20), AIR); c.door(37, 11, 20)
    for y in range(21, 35): c.set(23, y, 21, TRIM); c.set(23, y, 20, LADDER)
    c.child((0, 2, 19), "west", "foundation_core"); c.parent((47, 2, 19), "east", "core_facade", "facade")
    c.parent((23, 2, 39), "south", "core_annex", "annex"); c.parent((23, 35, 20), "up", "core_roof", "roof", LADDER_STATE)
    for x, y, z in ((0, 2, 19), (47, 2, 19), (23, 2, 39)):
        c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    prove_room_route(c, (8, 1, 8), ((23, 1, 21), (39, 1, 35)))

    a = Module(spec, "facade", (40, 40, 40)); a.fill((0, 0, 0), (39, 0, 39), TRIM)
    # Folded vertical planes read as the CG's draped/pleated white front.
    for x in range(2, 39, 3):
        depth = 5 + abs(20 - x) // 5
        height = 22 + (18 - abs(20 - x)) // 2
        a.fill((x, 1, depth), (x + 1, height, depth + 2), WALL)
        if 8 < x < 31: a.fill((x, 7, depth - 1), (x + 1, height - 4, depth - 1), GLASS)
    a.fill((17, 1, 2), (22, 14, 9), WALL); a.fill((19, 1, 1), (20, 10, 9), AIR)
    a.child((0, 2, 19), "west", "core_facade"); a.set(0, 3, 19, AIR); a.set(0, 4, 19, AIR)

    r = Module(spec, "roof", (48, 32, 40)); r.fill((3, 0, 2), (44, 1, 37), TRIM)
    r.child((23, 0, 20), "down", "core_roof", LADDER_STATE); r.set(23, 1, 20, LADDER); r.set(23, 2, 20, LADDER); r.set(23, 1, 21, TRIM)
    for z in range(3, 37):
        rise = min(14, 3 + z // 3, 3 + (39 - z) // 3)
        r.fill((5, 2, z), (42, rise, z), WALL if z % 4 else TRIM)
    for x in range(5, 43): r.set(x, 2, 2, BARS); r.set(x, 2, 37, BARS)
    r.set(23, 16, 20, GIRDER); r.light(23, 17, 20)

    n = Module(spec, "annex", (40, 24, 32)); n.fill((0, 0, 0), (39, 0, 31), TRIM)
    n.fill((2, 1, 2), (37, 16, 29), WALL); n.fill((4, 2, 4), (35, 15, 27), AIR)
    n.fill((20, 2, 4), (20, 15, 27), WALL); n.fill((20, 2, 14), (20, 4, 16), AIR); n.door(20, 2, 15)
    n.child((19, 2, 0), "north", "core_annex"); n.set(19, 3, 0, AIR); n.set(19, 4, 0, AIR)

    s = Module(spec, "surrounding", (48, 14, 48)); s.fill((0, 0, 0), (47, 0, 47), GROUND)
    s.fill((0, 1, 19), (47, 1, 28), TRIM)
    for x in range(3, 46, 4):
        s.fill((x, 1, 5), (x + 1, 8, 6), WALL); s.fill((x, 1, 41), (x + 1, 8, 42), WALL)
    s.child((47, 2, 23), "east", "foundation_surrounding"); s.set(47, 3, 23, AIR); s.set(47, 4, 23, AIR)
    return LandmarkBuild(spec, (f, c, a, r, n, s), (184, 68, 76))


def build_all() -> tuple[LandmarkBuild, LandmarkBuild]:
    return build_revelation_tower(), build_ambrosius_chapel()
