"""Independent Yan builders grounded in the Yumen and Shangshu background sets."""

def _commission_yan(f,c,a,r,n,s,*,doors,chests,ladder,core_targets,core_lamps,annex_entry_x,annex_door,interfaces,lamps):
    """Cut layered Yan beacon walks and independently supported courtyard lights."""
    for module,x,y,z in doors:
        for side in (z-1,z+1): module.set(x,y-1,side,TRIM); module.fill((x,y,side),(x,y+1,side),AIR)
        if y>2:
            for level in range(1,y+1): module.set(x,level,z-1,LADDER)
    for x,y,z in chests:
        c.set(x,y+1,z,AIR); c.set(x-1,y-1,z,TRIM); c.fill((x-1,y,z),(x-1,y+1,z),AIR)
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    lx,top,lz=ladder; hub=(lx-1,lz)
    for level in range(1,top+1): c.set(lx,level,lz+1,WALL); c.set(lx,level,lz,LADDER)
    for tx,tz in core_targets:
        for x in range(min(hub[0],tx),max(hub[0],tx)+1): c.set(x,0,hub[1],TRIM); c.fill((x,1,hub[1]),(x,2,hub[1]),AIR)
        for z in range(min(hub[1],tz),max(hub[1],tz)+1): c.set(tx,0,z,TRIM); c.fill((tx,1,z),(tx,2,z),AIR)
    for x in range(48): f.set(x,1,23,TRIM); f.fill((x,2,23),(x,3,23),AIR)
    nx,nz=annex_door
    for z in range(0,nz+2): n.set(annex_entry_x,1,z,TRIM); n.fill((annex_entry_x,2,z),(annex_entry_x,3,z),AIR)
    n.set(nx,1,nz-1,TRIM); n.fill((nx,2,nz-1),(nx,3,nz-1),AIR)
    for module,x,y,z in interfaces: module.set(x,y-1,z,STAIR_E); module.fill((x,y,z),(x,y+1,z),AIR)
    for level in range(1,top+1): c.set(lx,level,lz+1,WALL); c.set(lx,level,lz,LADDER)
    for x,y,z in chests:
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    for module,x,y,z in doors:
        if y>2:
            for level in range(1,y+1): module.set(x,level,z-1,LADDER)
    for level in range(1,6): r.set(lx,level,lz+1,WALL); r.set(lx,level,lz,LADDER)
    r.set(lx,6,lz,AIR); r.set(lx-1,4,lz,TRIM); r.fill((lx-1,5,lz),(lx-1,6,lz),AIR)
    for x in (4,14,24,34,44): f.set(x,5,23,TRIM); f.light(x,4,23)
    for x,z in core_lamps: c.set(x,4,z,TRIM); c.light(x,3,z)
    for level in range(7,top,10): c.set(lx+1,level,lz+1,TRIM); c.light(lx+1,level,lz)
    for module,support,light in lamps: module.set(*support,TRIM); module.light(*light)
    for x,y,z in chests: c.set(x-2,y-1,z,TRIM); c.light(x-2,y,z)
    for module in (f,c,a,r,n,s):
        for (x,y,z),state in tuple(module.blocks.items()):
            if state in {GROUND,WALL,TRIM} and (x+3*y)%6==1 and (z+5*y)%6==4 and y+2<module.size[1] \
                    and module.blocks.get((x,y+1,z),AIR)==AIR and module.blocks.get((x,y+2,z),AIR)==AIR:
                module.light(x,y,z)


from .base import (
    AIR, BARS, BARREL, CARTOGRAPHY, CASING, CHAIN, FENCE, GIRDER, GLASS, GROUND,
    LADDER, LADDER_STATE, LECTERN, RAIL, SHAFT_X, SLAB, STAIR_E, STAIR_N,
    TRIM, WALL, LandmarkBuild, Module, Spec, prove_room_route,
)


def build_yumen_beacon() -> LandmarkBuild:
    spec = Spec(
        "yan_yumen_beacon", "yan", "Yumen gate and observation axis",
        "35_g6_yumengate.png",
        ("city-scale rectangular gate slab", "dense full-height vertical ribs", "deep central road aperture", "cantilevered city platform over the wall"),
        "XXL-implied 192x104x128 (six-module playable crop)",
    )
    f = Module(spec, "foundation", (48, 12, 48)); f.fill((0, 0, 0), (47, 0, 47), GROUND)
    f.fill((0, 1, 17), (47, 3, 30), TRIM); f.fill((0, 2, 20), (47, 5, 27), AIR)
    for x in (5, 14, 33, 42):
        f.fill((x, 1, 6), (x, 5, 7), GIRDER); f.light(x, 6, 7)
    f.parent((47, 2, 23), "east", "foundation_core", "core"); f.parent((0, 2, 23), "west", "foundation_surrounding", "surrounding")

    c = Module(spec, "core", (48, 48, 48)); c.fill((0, 0, 0), (47, 0, 47), WALL)
    # Twin wall bodies frame a 14-wide, 30-high road aperture.
    c.fill((0, 1, 3), (15, 44, 44), WALL); c.fill((32, 1, 3), (47, 44, 44), WALL)
    c.fill((16, 31, 3), (31, 44, 44), WALL)
    for x in list(range(1, 16, 3)) + list(range(33, 48, 3)):
        c.fill((x, 1, 1), (x + 1, 46, 4), TRIM); c.fill((x, 1, 43), (x + 1, 46, 46), TRIM)
    # Deep chamfered lower aperture and independent inspection/control rooms.
    for y in range(1, 12):
        inset = y // 3
        c.fill((15 - inset, y, 8), (16, y, 39), TRIM); c.fill((31, y, 8), (32 + inset, y, 39), TRIM)
    c.fill((2, 1, 22), (14, 10, 22), TRIM); c.fill((7, 1, 22), (9, 3, 22), AIR); c.door(8, 1, 22)
    c.fill((33, 1, 22), (45, 10, 22), TRIM); c.fill((38, 1, 22), (40, 3, 22), AIR); c.door(39, 1, 22)
    c.fill((3, 1, 29), (13, 1, 31), SLAB); c.set(8, 1, 28, CARTOGRAPHY)
    c.fill((34, 1, 29), (44, 1, 31), SLAB); c.set(39, 1, 28, LECTERN)
    for i in range(11):
        x, y, z = 3 + i, 1 + i, 8
        c.set(x, y, z, STAIR_E); c.stairs.add((x, y, z)); c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    for x, z in ((5, 10), (12, 10), (35, 10), (42, 10), (5, 36), (12, 36), (35, 36), (42, 36)):
        c.set(x, 13, z, TRIM); c.light(x, 12, z)
    c.chest(13, 1, 40); c.chest(34, 1, 40); c.chest(44, 32, 38)
    for y in range(35, 47): c.set(39, y, 37, WALL); c.set(39, y, 36, LADDER)
    c.child((0, 2, 23), "west", "foundation_core"); c.parent((23, 2, 0), "north", "core_facade", "facade")
    c.parent((23, 2, 47), "south", "core_annex", "annex"); c.parent((39, 47, 36), "up", "core_roof", "roof", LADDER_STATE)
    for x, y, z in ((0, 2, 23), (47, 2, 23), (23, 2, 47)):
        c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    prove_room_route(c, (17, 1, 23), ((30, 1, 23),))

    a = Module(spec, "facade", (48, 48, 48)); a.fill((0, 0, 0), (47, 0, 47), TRIM)
    # The facade is a rib field with deliberate discontinuities, not a flat skin.
    for x in range(1, 48, 3):
        depth = 3 + (x * 7 % 6)
        top = 44 - (x % 5)
        a.fill((x, 1, depth), (x + 1 if x < 47 else x, top, depth + 2), WALL)
        if x not in (22, 25): a.fill((x, 8, depth - 1), (x, top - 5, depth - 1), GLASS)
    a.fill((16, 1, 10), (31, 30, 40), WALL); a.fill((18, 2, 12), (29, 29, 38), AIR)
    a.fill((14, 31, 8), (33, 40, 42), WALL)
    a.child((23, 2, 47), "south", "core_facade"); a.set(23, 3, 47, AIR); a.set(23, 4, 47, AIR)

    r = Module(spec, "roof", (48, 48, 48)); r.fill((0, 0, 3), (47, 3, 44), TRIM)
    r.child((39, 0, 36), "down", "core_roof", LADDER_STATE); r.set(39, 1, 36, LADDER); r.set(39, 2, 36, LADDER); r.set(39, 1, 37, WALL)
    # Suspended upper city deck and observation crown.
    r.fill((3, 8, 0), (44, 12, 47), WALL); r.fill((8, 9, 6), (39, 11, 41), AIR)
    for x in range(4, 45, 4): r.fill((x, 3, 5), (x + 1, 8, 42), GIRDER)
    r.fill((17, 13, 14), (30, 37, 33), WALL); r.fill((20, 15, 17), (27, 34, 30), AIR)
    for y in range(17, 38, 5): r.fill((14, y, 22), (33, y + 1, 25), TRIM)
    r.set(23, 38, 23, GIRDER); r.light(23, 39, 23)
    for x in range(2, 46): r.set(x, 4, 3, BARS); r.set(x, 4, 44, BARS)

    n = Module(spec, "annex", (48, 28, 48)); n.fill((0, 0, 0), (47, 0, 47), TRIM)
    n.fill((2, 1, 2), (45, 20, 45), WALL); n.fill((5, 2, 5), (42, 19, 42), AIR)
    n.fill((23, 2, 5), (24, 19, 42), WALL); n.fill((23, 2, 22), (24, 4, 25), AIR); n.door(23, 2, 23)
    # Gate drive: brakes, shafts and a separate maintenance catwalk.
    n.fill((7, 3, 35), (40, 3, 37), SHAFT_X); n.set(11, 3, 34, CASING); n.set(36, 3, 34, CASING)
    n.fill((6, 8, 33), (41, 9, 40), GIRDER)
    n.child((23, 2, 0), "north", "core_annex"); n.set(23, 3, 0, AIR); n.set(23, 4, 0, AIR)

    s = Module(spec, "surrounding", (48, 16, 48)); s.fill((0, 0, 0), (47, 0, 47), GROUND)
    s.fill((0, 1, 17), (47, 1, 30), TRIM)
    for z in (19, 23, 27):
        s.fill((0, 2, z), (47, 2, z), RAIL)
    for x in (7, 19, 31, 43): s.fill((x, 1, 5), (x, 12, 6), GIRDER)
    s.child((47, 2, 23), "east", "foundation_surrounding"); s.set(47, 3, 23, AIR); s.set(47, 4, 23, AIR)
    _commission_yan(f,c,a,r,n,s, doors=((c,8,1,22),(c,39,1,22),(n,23,2,23)), chests=((13,1,40),(34,1,40),(44,32,38)), ladder=(39,46,36),
        core_targets=((0,23),(23,1),(23,46),(7,21),(7,23),(38,21),(38,23),(12,40),(33,40),(43,38),(2,8)), core_lamps=((3,23),(12,36),(24,36),(36,36),(44,23),(23,43)), annex_entry_x=23,annex_door=(23,23),
        interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,23,2,0),(c,23,2,47),(a,23,2,47),(n,23,2,0),(s,47,2,23)),
        lamps=((c,(9,0,22),(9,1,22)),(c,(40,0,22),(40,1,22)),(c,(4,3,10),(4,3,9)),(c,(13,13,10),(13,13,9)),(c,(22,1,1),(22,2,1)),(a,(22,1,46),(22,2,46)),(a,(25,4,45),(24,4,45)),(n,(24,4,3),(23,4,3)),(n,(24,4,22),(23,4,22)),(s,(45,4,25),(45,4,24)),(r,(40,3,37),(40,3,36))))
    return LandmarkBuild(spec, (f, c, a, r, n, s), (144, 96, 144))


def build_shangshu_pavilion() -> LandmarkBuild:
    spec = Spec(
        "yan_shangshu_pavilion", "yan", "Shangshu hill, cable and public terrace",
        "25_g05_mountaincity_d.png",
        ("inhabited stepped artificial hill", "stacked public roofs instead of one pavilion", "cross-valley cable axis", "service tunnels cut through the mass"),
        "XL 168x72x144",
    )
    f = Module(spec, "foundation", (48, 20, 48)); f.fill((0, 0, 0), (47, 0, 47), GROUND)
    for step in range(8):
        f.fill((step * 2, step + 1, step), (47 - step, step + 1, 47 - step * 2), WALL if step % 2 else TRIM)
    f.fill((0, 2, 20), (47, 5, 27), AIR)
    for x, z in ((5, 5), (42, 5), (5, 42), (42, 42)):
        f.fill((x, 1, z), (x, 4, z), FENCE); f.light(x, 5, z)
    f.parent((47, 2, 23), "east", "foundation_core", "core"); f.parent((0, 2, 23), "west", "foundation_surrounding", "surrounding")

    c = Module(spec, "core", (48, 40, 48)); c.fill((0, 0, 0), (47, 0, 47), WALL)
    # Offset terraces climb diagonally like a built mountain.
    terraces = ((2, 1, 44, 8), (6, 9, 39, 8), (12, 17, 33, 8), (19, 25, 27, 9))
    for inset, y, end, height in terraces:
        c.fill((inset, y, inset), (end, y + height, end), WALL)
        room_y = y if y == 1 else y + 1
        c.fill((inset + 3, room_y, inset + 3), (end - 3, y + height - 1, end - 3), AIR)
        c.fill((inset - 1 if inset else 0, y + height + 1, inset - 1 if inset else 0), (min(47, end + 3), y + height + 2, min(47, end + 3)), TRIM)
    c.fill((4, 1, 23), (42, 7, 23), WALL); c.fill((20, 1, 23), (23, 3, 23), AIR); c.door(21, 1, 23)
    c.fill((15, 9, 21), (35, 15, 21), WALL); c.fill((23, 9, 21), (25, 11, 21), AIR); c.door(24, 9, 21)
    c.fill((7, 1, 31), (18, 1, 33), SLAB); c.set(12, 1, 30, CARTOGRAPHY)
    c.fill((29, 9, 30), (39, 9, 32), SLAB); c.set(34, 9, 29, LECTERN)
    for i in range(10):
        x, y, z = 5 + i, 1 + i, 8
        c.set(x, y, z, STAIR_E); c.stairs.add((x, y, z)); c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    for x, z in ((7, 12), (20, 12), (34, 12), (41, 12), (7, 35), (20, 35), (34, 35), (41, 35)):
        c.set(x, 15, z, TRIM); c.light(x, 14, z)
    c.chest(40, 1, 40)
    c.set(36, 8, 36, TRIM); c.chest(36, 9, 36)
    c.set(29, 16, 29, TRIM); c.chest(29, 17, 29)
    for y in range(28, 39): c.set(23, y, 25, WALL); c.set(23, y, 24, LADDER)
    c.child((0, 2, 23), "west", "foundation_core"); c.parent((47, 2, 23), "east", "core_facade", "facade")
    c.parent((23, 2, 47), "south", "core_annex", "annex"); c.parent((23, 39, 24), "up", "core_roof", "roof", LADDER_STATE)
    for x, y, z in ((0, 2, 23), (47, 2, 23), (23, 2, 47)):
        c.set(x, y + 1, z, AIR); c.set(x, y + 2, z, AIR)
    prove_room_route(c, (6, 1, 6), ((21, 1, 24), (39, 1, 40)))

    a = Module(spec, "facade", (40, 32, 48)); a.fill((0, 0, 0), (39, 0, 47), TRIM)
    # Narrow hill-street fronts occupy different setbacks.
    for i, (x, z, h) in enumerate(((2, 5, 12), (10, 9, 17), (19, 4, 22), (28, 13, 14), (34, 7, 19))):
        a.fill((x, 1, z), (min(39, x + 7), h, min(47, z + 22)), WALL)
        a.fill((x + 2, 4, z), (min(39, x + 5), h - 3, z), GLASS)
        a.fill((max(0, x - 1), h + 1, z - 1), (min(39, x + 9), h + 2, min(47, z + 24)), TRIM)
    a.child((0, 2, 23), "west", "core_facade"); a.set(0, 3, 23, AIR); a.set(0, 4, 23, AIR)

    r = Module(spec, "roof", (48, 32, 48)); r.fill((2, 0, 2), (45, 1, 45), TRIM)
    r.child((23, 0, 24), "down", "core_roof", LADDER_STATE); r.set(23, 1, 24, LADDER); r.set(23, 2, 24, LADDER); r.set(23, 1, 25, WALL)
    # Cable pylons and a broad public lookout deck.
    for x in (7, 40):
        r.fill((x, 2, 20), (x + 2, 27, 22), GIRDER); r.fill((x - 3, 27, 18), (x + 5, 29, 24), TRIM)
    for x in range(10, 40):
        y = 26 - abs(24 - x) // 5
        r.set(x, y, 21, CHAIN)
    r.fill((12, 4, 8), (35, 8, 39), WALL); r.fill((15, 5, 11), (32, 7, 36), AIR)
    r.set(23, 9, 23, GIRDER); r.light(23, 10, 23)

    n = Module(spec, "annex", (48, 28, 48)); n.fill((0, 0, 0), (47, 0, 47), TRIM)
    # Cable station: passenger hall and a separated drive tunnel.
    n.fill((2, 1, 2), (45, 18, 45), WALL); n.fill((5, 2, 5), (42, 17, 42), AIR)
    n.fill((24, 2, 5), (24, 17, 42), WALL); n.fill((24, 2, 20), (24, 4, 23), AIR); n.door(24, 2, 21)
    n.fill((28, 3, 31), (41, 3, 33), SHAFT_X); n.set(30, 3, 30, CASING); n.set(39, 3, 30, CASING)
    for x in (7, 13, 19): n.set(x, 2, 36, BARREL)
    n.child((23, 2, 0), "north", "core_annex"); n.set(23, 3, 0, AIR); n.set(23, 4, 0, AIR)

    s = Module(spec, "surrounding", (48, 28, 48)); s.fill((0, 0, 0), (47, 0, 47), GROUND)
    for step in range(8): s.fill((step * 2, step + 1, 5 + step), (47 - step, step + 1, 42 - step), TRIM if step % 2 else WALL)
    s.fill((0, 2, 20), (47, 5, 27), AIR)
    s.child((47, 2, 23), "east", "foundation_surrounding"); s.set(47, 3, 23, AIR); s.set(47, 4, 23, AIR)
    _commission_yan(f,c,a,r,n,s, doors=((c,21,1,23),(c,24,9,21),(n,24,2,21)), chests=((29,17,29),(36,9,36),(40,1,40)), ladder=(23,38,24),
        core_targets=((0,23),(47,23),(23,47),(20,22),(20,24),(24,20),(28,29),(35,36),(39,40),(4,8)), core_lamps=((3,23),(11,24),(22,24),(33,24),(44,23),(23,43)), annex_entry_x=23,annex_door=(24,21),
        interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,47,2,23),(c,23,2,47),(a,0,2,23),(n,23,2,0),(s,47,2,23)),
        lamps=((c,(5,3,10),(5,3,9)),(c,(14,12,10),(14,12,9)),(a,(2,4,25),(2,4,24)),(n,(25,4,3),(24,4,3)),(n,(25,4,20),(24,4,20)),(s,(45,4,25),(45,4,24)),(r,(24,3,25),(24,3,24))))
    return LandmarkBuild(spec, (f, c, a, r, n, s), (184, 72, 96))


def build_all() -> tuple[LandmarkBuild, LandmarkBuild]:
    return build_yumen_beacon(), build_shangshu_pavilion()
