"""Independent Leithanien builders from 28_g7_concerthall_outside.png."""

from .base import (
    AIR, BARS, BOOKSHELF, CASING, CHAIN, GIRDER, GLASS, GROUND, LADDER,
    LADDER_STATE, LECTERN, SHAFT_X, SLAB, STAIR_E, TRIM, WALL,
    LandmarkBuild, Module, Spec, prove_room_route,
)


def build_twin_spires() -> LandmarkBuild:
    spec = Spec(
        "leithanien_twin_spires", "leithanien", "twin acoustic tower complex",
        "28_g7_concerthall_outside.png",
        ("two separated organ-pipe towers", "high suspended resonance bridge", "red-black-silver vertical fins", "open void beneath bridge"),
        "XL 168x112x104",
    )
    f = Module(spec, "foundation", (48, 12, 48)); f.fill((0, 0, 0), (47, 0, 47), GROUND)
    for step in range(5): f.fill((4 + step, 1 + step, 7 + step), (43 - step, 1 + step, 40 - step), TRIM)
    f.fill((0, 1, 21), (47, 2, 26), WALL); f.fill((0, 2, 22), (47, 4, 25), AIR)
    for x, z in ((6, 7), (41, 7), (6, 40), (41, 40)):
        f.fill((x, 1, z), (x, 3, z), GIRDER); f.light(x, 4, z)
    f.parent((47, 2, 23), "east", "foundation_core", "core"); f.parent((0, 2, 23), "west", "foundation_surrounding", "surrounding")

    c = Module(spec, "core", (48, 48, 40)); c.fill((0, 0, 0), (47, 0, 39), WALL)
    # Two unequal, finned sound towers leave the middle as a full-height void.
    for lo, hi, top in ((2, 17, 45), (30, 44, 40)):
        c.fill((lo, 1, 4), (hi, top, 35), WALL); c.fill((lo + 3, 1, 7), (hi - 3, top - 2, 32), AIR)
        for x in range(lo, hi + 1, 3):
            c.fill((x, 2, 2), (x, top, 4), GIRDER)
            c.fill((x, 2, 35), (x, top, 37), GIRDER)
    # Suspended bridge is a perforated acoustic instrument, not a connecting box.
    c.fill((15, 29, 14), (32, 31, 25), TRIM); c.fill((18, 29, 16), (29, 31, 23), AIR)
    for x in range(16, 32, 3): c.fill((x, 25, 12), (x, 36, 13), GLASS); c.fill((x, 25, 26), (x, 36, 27), GLASS)
    c.fill((4, 1, 19), (15, 9, 19), WALL); c.fill((8, 1, 19), (10, 3, 19), AIR); c.door(9, 1, 19)
    c.fill((32, 1, 19), (43, 9, 19), WALL); c.fill((36, 1, 19), (38, 3, 19), AIR); c.door(37, 1, 19)
    for i in range(10):
        x, y, z = 4 + i, 1 + i, 8
        c.set(x, y, z, STAIR_E); c.stairs.add((x, y, z)); c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    for x, z in ((6, 10), (13, 10), (34, 10), (41, 10), (6, 29), (13, 29), (34, 29), (41, 29)):
        c.set(x, 11, z, TRIM); c.light(x, 10, z)
    c.fill((5, 1, 27), (14, 1, 29), SLAB); c.set(9, 1, 26, LECTERN)
    c.fill((33, 1, 27), (42, 3, 27), BOOKSHELF)
    c.chest(14, 1, 33); c.chest(42, 1, 33); c.chest(42, 12, 30)
    for y in range(34, 47): c.set(39, y, 31, WALL); c.set(39, y, 30, LADDER)
    c.child((0, 2, 19), "west", "foundation_core"); c.parent((47, 2, 19), "east", "core_facade", "facade")
    c.parent((23, 2, 39), "south", "core_annex", "annex"); c.parent((39, 47, 30), "up", "core_roof", "roof", LADDER_STATE)
    for x, y, z in ((0, 2, 19), (47, 2, 19), (23, 2, 39)):
        c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    prove_room_route(c, (6, 1, 8), ((9, 1, 20),))

    a = Module(spec, "facade", (40, 44, 40)); a.fill((0, 0, 0), (39, 0, 39), TRIM)
    # An array of unequal organ pipes creates a frequency-like skyline.
    heights = (16, 23, 31, 38, 27, 42, 34, 20, 29, 18)
    for i, height in enumerate(heights):
        x = 2 + i * 3
        a.fill((x, 1, 8), (x + 1, height, 11), WALL)
        a.fill((x, 3, 7), (x + 1, height - 3, 7), GLASS)
        a.fill((x, height, 5), (x + 1, height + 1, 13), TRIM)
    a.fill((4, 1, 17), (35, 10, 34), WALL); a.fill((7, 2, 19), (32, 9, 32), AIR)
    a.child((0, 2, 19), "west", "core_facade"); a.set(0, 3, 19, AIR); a.set(0, 4, 19, AIR)

    r = Module(spec, "roof", (48, 48, 40)); r.fill((2, 0, 3), (45, 1, 36), TRIM)
    r.child((39, 0, 30), "down", "core_roof", LADDER_STATE); r.set(39, 1, 30, LADDER); r.set(39, 2, 30, LADDER); r.set(39, 1, 31, WALL)
    for cx, max_y in ((10, 45), (35, 39)):
        for y in range(2, max_y):
            width = max(1, 6 - y // 9)
            r.fill((cx - width, y, 12), (cx + width, y, 27), WALL if y % 5 else TRIM)
            if y % 7 == 0: r.fill((cx - width - 2, y, 18), (cx + width + 2, y, 21), GIRDER)
    r.fill((12, 28, 17), (33, 31, 22), GIRDER); r.fill((15, 29, 18), (30, 31, 21), AIR)
    r.set(10, 45, 20, GIRDER); r.light(10, 46, 20)

    n = Module(spec, "annex", (40, 28, 36)); n.fill((0, 0, 0), (39, 0, 35), TRIM)
    n.fill((2, 1, 2), (37, 18, 33), WALL); n.fill((5, 2, 5), (34, 17, 30), AIR)
    # Rehearsal cells have splayed walls and isolated service corridors.
    for z in (9, 17, 25):
        n.fill((5, 2, z), (34, 15, z + 1), WALL); n.fill((18, 2, z), (20, 4, z + 1), AIR)
    n.door(19, 2, 9); n.door(19, 2, 25)
    n.fill((8, 1, 30), (31, 1, 31), SHAFT_X); n.set(12, 1, 29, CASING); n.set(27, 1, 29, CASING)
    n.child((19, 2, 0), "north", "core_annex"); n.set(19, 3, 0, AIR); n.set(19, 4, 0, AIR)

    s = Module(spec, "surrounding", (48, 14, 48)); s.fill((0, 0, 0), (47, 0, 47), GROUND); s.fill((0, 1, 18), (47, 1, 29), TRIM)
    for x in (6, 16, 31, 41):
        s.fill((x, 1, 6), (x, 10, 7), GIRDER); s.fill((x, 1, 40), (x, 10, 41), GIRDER)
    s.child((47, 2, 23), "east", "foundation_surrounding"); s.set(47, 3, 23, AIR); s.set(47, 4, 23, AIR)
    return LandmarkBuild(spec, (f, c, a, r, n, s), (184, 96, 80))


def build_concert_hall() -> LandmarkBuild:
    spec = Spec(
        "leithanien_concert_hall", "leithanien", "acoustic performance hall",
        "28_g7_concerthall_outside.png",
        ("levitating red-black main volume", "faceted silver acoustic fins", "narrow pedestal and shadow gap", "organ-pipe crown"),
        "XL 152x88x128",
    )
    f = Module(spec, "foundation", (48, 12, 48)); f.fill((0, 0, 0), (47, 0, 47), GROUND)
    f.fill((3, 1, 3), (44, 2, 44), TRIM); f.fill((12, 3, 12), (35, 6, 35), WALL)
    f.fill((0, 2, 21), (47, 4, 26), AIR)
    for x, z in ((6, 6), (41, 6), (6, 41), (41, 41)):
        f.fill((x, 1, z), (x, 3, z), GIRDER); f.light(x, 4, z)
    f.parent((47, 2, 23), "east", "foundation_core", "core"); f.parent((0, 2, 23), "west", "foundation_surrounding", "surrounding")

    c = Module(spec, "core", (48, 40, 48)); c.fill((0, 0, 0), (47, 0, 47), WALL)
    # Small pedestal, deep shadow gap, then a faceted cantilevered auditorium.
    c.fill((17, 1, 17), (30, 7, 30), WALL)
    for y, inset in ((8, 10), (10, 6), (14, 2), (28, 6), (34, 12)):
        c.fill((inset, y, inset), (47 - inset, min(38, y + 5), 47 - inset), WALL)
        if y < 28: c.fill((inset + 3, y + 1, inset + 3), (44 - inset, min(37, y + 4), 44 - inset), AIR)
    for x in range(4, 44, 4):
        c.fill((x, 14, 1), (x + 1, 32, 4), GIRDER); c.fill((x, 14, 43), (x + 1, 32, 46), GIRDER)
    c.fill((5, 14, 23), (42, 28, 23), WALL); c.fill((20, 14, 23), (27, 18, 23), AIR); c.door(23, 14, 23)
    c.fill((8, 14, 30), (39, 14, 35), SLAB); c.set(23, 14, 29, LECTERN)
    c.fill((7, 14, 38), (16, 17, 38), BOOKSHELF)
    for i in range(10):
        x, y, z = 17 + i, 1 + i, 18
        c.set(x, y, z, STAIR_E); c.stairs.add((x, y, z)); c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    for x, z in ((8, 8), (23, 8), (39, 8), (8, 39), (23, 39), (39, 39), (14, 23), (34, 23)):
        c.set(x, 33, z, GIRDER); c.light(x, 32, z)
    c.set(35, 13, 35, TRIM); c.chest(35, 14, 35)
    c.set(40, 13, 40, TRIM); c.chest(40, 14, 40)
    c.set(30, 28, 30, TRIM); c.chest(30, 29, 30)
    c.fill((29, 29, 23), (41, 36, 23), WALL); c.fill((34, 29, 23), (36, 31, 23), AIR); c.door(35, 29, 23)
    for y in range(34, 39): c.set(23, y, 25, WALL); c.set(23, y, 24, LADDER)
    c.child((0, 2, 23), "west", "foundation_core"); c.parent((47, 16, 23), "east", "core_facade", "facade")
    c.parent((23, 16, 47), "south", "core_annex", "annex"); c.parent((23, 39, 24), "up", "core_roof", "roof", LADDER_STATE)
    for x, y, z in ((0, 2, 23), (47, 16, 23), (23, 16, 47)):
        c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    prove_room_route(c, (16, 1, 18), ((17, 1, 18),))

    a = Module(spec, "facade", (40, 40, 48)); a.fill((0, 0, 0), (39, 0, 47), TRIM)
    for x in range(2, 39, 3):
        shift = abs(20 - x) // 4
        a.fill((x, 4 + shift, 4), (x + 1, 35 - shift, 42), TRIM if x % 6 else GIRDER)
    a.fill((2, 14, 10), (37, 31, 37), WALL); a.fill((6, 16, 13), (33, 29, 34), AIR)
    a.child((0, 16, 23), "west", "core_facade"); a.set(0, 17, 23, AIR); a.set(0, 18, 23, AIR)

    r = Module(spec, "roof", (48, 40, 48)); r.fill((5, 0, 5), (42, 1, 42), TRIM)
    r.child((23, 0, 24), "down", "core_roof", LADDER_STATE); r.set(23, 1, 24, LADDER); r.set(23, 2, 24, LADDER); r.set(23, 1, 25, WALL)
    heights = (14, 21, 29, 37, 32, 25, 18)
    for i, h in enumerate(heights):
        x = 8 + i * 5
        r.fill((x, 2, 16), (x + 2, h, 31), WALL); r.fill((x, 4, 14), (x + 2, h - 2, 15), GIRDER)
    r.set(23, 38, 24, GIRDER); r.light(23, 39, 24)

    n = Module(spec, "annex", (48, 28, 40)); n.fill((0, 0, 0), (47, 0, 39), TRIM)
    n.fill((2, 1, 2), (45, 19, 37), WALL); n.fill((5, 2, 5), (42, 18, 34), AIR)
    # Stage tower and scenery dock are visibly different from the auditorium.
    n.fill((29, 2, 5), (42, 24, 25), WALL); n.fill((32, 3, 8), (39, 22, 22), AIR)
    n.fill((7, 2, 26), (25, 16, 26), WALL); n.fill((14, 2, 26), (16, 4, 26), AIR); n.door(15, 2, 26)
    n.fill((30, 20, 8), (41, 21, 23), GIRDER)
    for x in range(31, 41, 3): n.fill((x, 3, 9), (x, 19, 9), CHAIN)
    n.child((23, 16, 0), "north", "core_annex"); n.set(23, 17, 0, AIR); n.set(23, 18, 0, AIR)

    s = Module(spec, "surrounding", (48, 16, 48)); s.fill((0, 0, 0), (47, 0, 47), GROUND); s.fill((0, 1, 17), (47, 1, 30), TRIM)
    for x in (5, 14, 33, 42):
        s.fill((x, 1, 7), (x, 12, 8), GIRDER); s.fill((x, 1, 39), (x, 12, 40), GIRDER)
    s.child((47, 2, 23), "east", "foundation_surrounding"); s.set(47, 3, 23, AIR); s.set(47, 4, 23, AIR)
    return LandmarkBuild(spec, (f, c, a, r, n, s), (184, 80, 88))


def build_all() -> tuple[LandmarkBuild, LandmarkBuild]:
    return build_twin_spires(), build_concert_hall()
