"""Independent Menat builders from the official grand bazaar and inverted-pyramid set."""

from .base import AIR, BARREL, FENCE, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, SHAFT_X, SLAB, STAIR_E, TRIM, WATER, WALL, LandmarkBuild, Module, Spec


def _commission_sargon(f, c, a, r, n, s, *, doors, chests, ladder, core_targets,
                       annex_x, annex_door_x, annex_door_z, interfaces, lamps) -> None:
    """Finish the two Menat sites with explicit bazaar/well service coordinates."""
    for module,x,y,z in doors:
        for side in (z-1,z+1): module.set(x,y-1,side,TRIM); module.fill((x,y,side),(x,y+1,side),AIR)
        if y>2:
            for level in range(1,y+1): module.set(x,level,z-1,LADDER)
    for x,y,z in chests:
        c.set(x,y+1,z,AIR); c.set(x-1,y-1,z,TRIM); c.fill((x-1,y,z),(x-1,y+1,z),AIR)
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    lx,top,lz=ladder
    for level in range(1,top+1): c.set(lx,level,lz+1,WALL); c.set(lx,level,lz,LADDER)
    hub=(lx-1,lz)
    for tx,tz in core_targets:
        for x in range(min(hub[0],tx),max(hub[0],tx)+1): c.set(x,0,hub[1],TRIM); c.fill((x,1,hub[1]),(x,2,hub[1]),AIR)
        for z in range(min(hub[1],tz),max(hub[1],tz)+1): c.set(tx,0,z,TRIM); c.fill((tx,1,z),(tx,2,z),AIR)
    for x in range(f.size[0]): f.set(x,1,23,TRIM); f.fill((x,2,23),(x,3,23),AIR)
    for z in range(0,annex_door_z+2): n.set(annex_x,1,z,TRIM); n.fill((annex_x,2,z),(annex_x,3,z),AIR)
    n.set(annex_door_x,1,annex_door_z-1,TRIM); n.fill((annex_door_x,2,annex_door_z-1),(annex_door_x,3,annex_door_z-1),AIR)
    for module,x,y,z in interfaces:
        module.set(x,y-1,z,STAIR_E); module.fill((x,y,z),(x,y+1,z),AIR)
    for level in range(1,top+1): c.set(lx,level,lz+1,WALL); c.set(lx,level,lz,LADDER)
    for x,y,z in chests:
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    for level in range(1,6): r.set(lx,level,lz+1,WALL); r.set(lx,level,lz,LADDER)
    r.set(lx,6,lz,AIR); r.set(lx-1,4,lz,TRIM); r.fill((lx-1,5,lz),(lx-1,6,lz),AIR)
    for x in (4,14,24,34,44): f.set(x,5,23,TRIM); f.light(x,4,23)
    for x,z in ((3,23),(11,24),(22,24),(33,24),(44,23),(23,43)):
        c.set(x,4,z,TRIM); c.light(x,3,z)
    for level in range(7,top,10): c.set(lx+1,level,lz+1,TRIM); c.light(lx+1,level,lz)
    for module,support,light in lamps: module.set(*support,TRIM); module.light(*light)
    for x,y,z in chests:
        c.set(x-2,y-1,z,TRIM); c.light(x-2,y,z)
    for module in (f,c,a,r,n,s):
        for (x,y,z),state in tuple(module.blocks.items()):
            if state in {GROUND,WALL,TRIM} and (x+2*y)%7==1 and (z+3*y)%7==2 and y+2<module.size[1] \
                    and module.blocks.get((x,y+1,z),AIR)==AIR and module.blocks.get((x,y+2,z),AIR)==AIR:
                module.light(x,y,z)


def build_golden_bazaar() -> LandmarkBuild:
    spec=Spec("sargon_golden_bazaar","sargon","Menat grand bazaar","53_g10_grandbazaar_d.png",
              ("giant sloped gate walls", "continuous coloured shade canopies", "deep commercial street axis", "water/reflection strip and service yards"),"XL 192x64x160")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,16),(47,1,31),TRIM); f.fill((3,1,3),(44,1,10),WATER)
    for x,z in ((5,13),(42,13),(5,34),(42,34)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,36,48)); c.fill((0,0,0),(47,0,47),WALL)
    # Two battered, sloping gate masses define a street rather than a palace courtyard.
    for y in range(1,32):
        inset=y//5; c.fill((1+inset,y,3),(15,y,44),WALL); c.fill((32,y,3),(46-inset,y,44),WALL)
    c.fill((4,2,7),(12,28,40),AIR); c.fill((35,2,7),(43,28,40),AIR)
    for z,y,w in ((8,8,34),(17,13,40),(27,18,30),(37,23,36)):
        c.fill(((48-w)//2,y,z-2),((48+w)//2-1,y+2,z+2),TRIM)
    c.fill((4,1,23),(15,9,23),WALL); c.fill((8,1,23),(10,3,23),AIR); c.door(9,1,23); c.fill((32,1,23),(43,9,23),WALL); c.fill((36,1,23),(38,3,23),AIR); c.door(37,1,23)
    for i in range(9): x,y,z=5+i,1+i,10; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(14,12),(34,12),(42,12)): c.set(x,10,z,TRIM); c.light(x,9,z)
    c.fill((5,1,32),(14,1,35),SLAB); c.fill((34,1,32),(43,1,35),SLAB)
    c.chest(14,1,40); c.chest(34,1,40); c.set(39,17,36,TRIM); c.chest(39,18,36)
    for y in range(25,35): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,35,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,36,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for y in range(1,33):
        inset=y//4; a.fill((inset,y,4),(15,y,43),WALL); a.fill((32,y,4),(47-inset,y,43),WALL)
    for z in range(7,43,7): a.fill((7,5,z),(40,7,min(47,z+3)),TRIM)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,32,48)); r.fill((3,0,3),(44,1,44),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for x in (6,13,20,27,34,41):
        h=12+(x*3%16); r.fill((x,2,5),(x+2,h,42),WALL); r.fill((x-2,h+1,3),(x+5,h+2,44),TRIM)
    r.set(24,28,24,GIRDER); r.light(24,29,24)
    n=Module(spec,"annex",(48,24,40)); n.fill((0,0,0),(47,0,39),TRIM); n.fill((2,1,2),(45,17,37),WALL); n.fill((5,2,5),(42,16,34),AIR); n.fill((24,2,5),(24,16,34),WALL); n.fill((24,2,18),(24,4,20),AIR); n.door(24,2,19); n.fill((5,2,30),(17,4,32),BARREL); n.fill((29,2,30),(41,3,32),SHAFT_X); n.child((23,2,0),"north","core_annex"); n.set(23,3,0,AIR); n.set(23,4,0,AIR)
    s=Module(spec,"surrounding",(48,14,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,14),(47,1,33),TRIM); s.fill((3,1,4),(44,1,11),WATER); s.fill((4,1,36),(15,8,44),WALL); s.fill((32,1,36),(43,8,44),WALL); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_sargon(
        f,c,a,r,n,s,
        doors=((c,9,1,23),(c,37,1,23),(n,24,2,19)),
        chests=((14,1,40),(34,1,40),(39,18,36)), ladder=(24,34,24),
        core_targets=((0,23),(23,1),(23,46),(8,22),(8,24),(36,22),(36,24),(13,40),(33,40),(38,36),(4,10)),
        annex_x=23,annex_door_x=24,annex_door_z=19,
        interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,23,2,0),(c,23,2,47),(a,23,2,47),(n,23,2,0),(s,47,2,23)),
        lamps=((a,(25,4,45),(24,4,45)),(n,(25,5,3),(24,4,3)),(n,(25,5,18),(24,4,18)),(s,(45,4,25),(45,4,24)),(r,(25,3,25),(25,3,24))),
    )
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,68,136))


def build_inverted_pyramid() -> LandmarkBuild:
    spec=Spec("sargon_long_spring_well","sargon","Menat inverted-pyramid landmark","53_g13_invertedpyramid.png",
              ("large inverted pyramid", "deep sunken public plaza", "water axis below the suspended point", "unknown core kept closed"),"XXL-implied 192x96x176")
    f=Module(spec,"foundation",(48,16,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for step in range(10): f.fill((step,1+step,step),(47-step,1+step,47-step),TRIM)
    f.fill((14,1,14),(33,9,33),WATER)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,48,48)); c.fill((0,0,0),(47,0,47),WALL)
    # Suspended shell narrows downward: large upper plates, a small closed point.
    for y in range(6,44):
        radius=min(22,4+(y-6)//2)
        c.fill((24-radius,y,24-radius),(23+radius,y,23+radius),WALL if y%5 else TRIM)
        if radius>7: c.fill((24-radius+3,y,24-radius+3),(20+radius,y,20+radius),AIR)
    c.fill((2,1,18),(18,10,18),WALL); c.fill((8,1,18),(10,3,18),AIR); c.door(9,1,18); c.fill((29,1,30),(45,10,30),WALL); c.fill((35,1,30),(37,3,30),AIR); c.door(36,1,30)
    for i in range(10): x,y,z=4+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(16,12),(32,35),(42,35)): c.set(x,11,z,TRIM); c.light(x,10,z)
    c.chest(16,1,40); c.chest(31,1,39); c.set(37,15,34,TRIM); c.chest(37,16,34)
    for y in range(37,47): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,47,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,48,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for y in range(2,45):
        radius=min(22,3+y//2); a.fill((24-radius,y,5),(23+radius,y,42),WALL if y%4 else TRIM)
        if radius>8: a.fill((24-radius+3,y,9),(20+radius,y,38),AIR)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,48,48)); r.fill((1,0,1),(46,1,46),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for y in range(2,43):
        radius=max(3,21-y//2); r.fill((24-radius,y,24-radius),(23+radius,y,23+radius),WALL if y%5 else TRIM)
        if radius>8: r.fill((24-radius+3,y,24-radius+3),(20+radius,y,20+radius),AIR)
    r.set(24,43,24,GIRDER); r.light(24,44,24)
    n=Module(spec,"annex",(48,28,40)); n.fill((0,0,0),(47,0,39),TRIM); n.fill((2,1,2),(45,21,37),WALL); n.fill((5,2,5),(42,20,34),AIR); n.fill((24,2,5),(24,20,34),WALL); n.fill((24,2,18),(24,4,20),AIR); n.door(24,2,19); n.fill((6,2,30),(41,3,32),SHAFT_X); n.child((23,2,0),"north","core_annex"); n.set(23,3,0,AIR); n.set(23,4,0,AIR)
    s=Module(spec,"surrounding",(48,20,48)); s.fill((0,0,0),(47,0,47),GROUND)
    for step in range(10): s.fill((step,1+step,step),(47-step,1+step,47-step),TRIM)
    s.fill((14,1,14),(33,9,33),WATER); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_sargon(
        f,c,a,r,n,s,
        doors=((c,9,1,18),(c,36,1,30),(n,24,2,19)),
        chests=((16,1,40),(31,1,39),(37,16,34)), ladder=(24,46,24),
        core_targets=((0,23),(23,1),(23,46),(8,17),(8,19),(35,29),(35,31),(15,40),(30,39),(36,34),(3,8)),
        annex_x=23,annex_door_x=24,annex_door_z=19,
        interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,23,2,0),(c,23,2,47),(a,23,2,47),(n,23,2,0),(s,47,2,23)),
        lamps=((a,(25,4,45),(24,4,45)),(n,(25,5,3),(24,4,3)),(n,(25,5,18),(24,4,18)),(s,(45,4,25),(45,4,24)),(r,(25,3,25),(25,3,24))),
    )
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,96,136))


def build_all(): return build_golden_bazaar(), build_inverted_pyramid()
