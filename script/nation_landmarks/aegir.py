"""Independent Aegir silhouettes from 51_g4_aegirstreet_1.png and beacon/port set."""

from .base import AIR, BARS, CASING, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, SHAFT_X, SLAB, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec


def _aegir_pressure_floor_lights(modules) -> None:
    for module in modules:
        for (x,y,z),state in tuple(module.blocks.items()):
            if state in {GROUND,WALL,TRIM} and (x+2*y)%6==4 and (z+3*y)%6==1 and y+2<module.size[1] \
                    and module.blocks.get((x,y+1,z),AIR)==AIR and module.blocks.get((x,y+2,z),AIR)==AIR:
                module.light(x,y,z)


def build_volcanic_beacon() -> LandmarkBuild:
    spec = Spec("aegir_volcanic_beacon", "aegir", "energy beacon complex", "51_g1_beaconsquare.png",
                ("extreme vertical pressure shaft", "stacked luminous rings", "continuous tall arches", "bridges over a sunken energy well"), "XL 160x112x112")
    f=Module(spec,"foundation",(48,12,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for r,y in ((20,1),(15,2),(10,3)): f.fill((24-r,y,24-r),(23+r,y,23+r),TRIM)
    f.fill((16,1,16),(31,5,31),AIR)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    f.set(47,3,23,AIR); f.set(47,4,23,AIR); f.set(0,3,23,AIR); f.set(0,4,23,AIR)
    c=Module(spec,"core",(40,48,40)); c.fill((0,0,0),(39,0,39),WALL)
    # Four hollow pressure piers and a central energy void make a vertically porous mass.
    for cx,cz,h in ((7,7,38),(32,7,44),(7,32,44),(32,32,38)):
        for y in range(1,h):
            radius=4 if y<24 else 2
            c.fill((cx-radius,y,cz-radius),(cx+radius,y,cz+radius),WALL); c.fill((cx-radius+2,y,cz-radius+2),(cx+radius-2,y,cz+radius-2),AIR)
    for y,r in ((8,18),(18,15),(29,12),(39,8)):
        for x in range(20-r,20+r+1): c.set(x,y,20-r,TRIM); c.set(x,y,20+r,TRIM)
        for z in range(20-r,20+r+1): c.set(20-r,y,z,TRIM); c.set(20+r,y,z,TRIM)
    c.fill((2,1,19),(15,9,19),WALL); c.fill((7,1,19),(9,3,19),AIR); c.door(8,1,19)
    c.fill((24,1,19),(37,9,19),WALL); c.fill((29,1,19),(31,3,19),AIR); c.door(30,1,19)
    for i in range(10): x,y,z=4+i,1+i,10; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(14,12),(25,12),(33,12)): c.set(x,10,z,TRIM); c.light(x,9,z)
    c.fill((5,1,27),(14,1,29),SLAB); c.fill((25,1,27),(34,1,29),SLAB)
    c.chest(14,1,35); c.chest(25,1,35); c.set(33,17,33,TRIM); c.chest(33,18,33)
    for y in range(37,47): c.set(19,y,21,WALL); c.set(19,y,20,LADDER)
    c.child((0,2,19),"west","foundation_core"); c.parent((39,2,19),"east","core_facade","facade"); c.parent((19,2,39),"south","core_annex","annex"); c.parent((19,47,20),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,19),(39,2,19),(19,2,39)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(36,48,40)); a.fill((0,0,0),(35,0,39),TRIM)
    for x in range(2,35,5):
        a.fill((x,1,4),(x+2,40,7),WALL)
        for y in (5,14,23,32): a.fill((x,y,3),(x+2,y+5,3),GLASS); a.fill((x-1,y+5,3),(x+3,y+6,4),TRIM)
    a.fill((4,1,12),(31,18,35),WALL); a.fill((7,2,15),(28,17,32),AIR)
    a.child((0,2,19),"west","core_facade"); a.set(0,3,19,AIR); a.set(0,4,19,AIR)
    r=Module(spec,"roof",(40,48,40)); r.fill((3,0,3),(36,1,36),TRIM); r.child((19,0,20),"down","core_roof",LADDER_STATE); r.set(19,1,20,LADDER); r.set(19,2,20,LADDER); r.set(19,1,21,WALL)
    for y in range(2,45):
        radius=max(2,10-y//5); r.fill((19-radius,y,20-radius),(19+radius,y,20+radius),WALL if y%6 else TRIM)
        if y%8==0: r.fill((5,y,19),(34,y+1,21),GIRDER)
    r.set(19,45,20,GIRDER); r.light(19,46,20)
    n=Module(spec,"annex",(40,24,36)); n.fill((0,0,0),(39,0,35),TRIM); n.fill((2,1,2),(37,17,33),WALL); n.fill((5,2,5),(34,16,30),AIR); n.fill((20,2,5),(20,16,30),WALL); n.fill((20,2,16),(20,4,18),AIR); n.door(20,2,17); n.fill((6,3,28),(33,3,30),SHAFT_X); n.set(10,3,27,CASING); n.set(29,3,27,CASING); n.child((19,2,0),"north","core_annex"); n.set(19,3,0,AIR); n.set(19,4,0,AIR)
    s=Module(spec,"surrounding",(48,16,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,20),(47,2,27),TRIM)
    for x in (8,20,32,44): s.fill((x,1,5),(x+1,12,6),GIRDER); s.fill((x,1,41),(x+1,12,42),GIRDER)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    # Aegir pressure-service circulation is deliberately cut as narrow sealed galleries.
    for module,x,y,z in ((c,8,1,19),(c,30,1,19),(n,20,2,17)):
        for side in (z-1,z+1): module.set(x,y-1,side,TRIM); module.fill((x,y,side),(x,y+1,side),AIR)
    for x,y,z in ((14,1,35),(25,1,35),(33,18,33)):
        c.set(x,y+1,z,AIR); c.set(x-1,y-1,z,TRIM); c.fill((x-1,y,z),(x-1,y+1,z),AIR)
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    for level in range(1,47): c.set(19,level,21,WALL); c.set(19,level,20,LADDER)
    for level in (6,16,26,36,45): c.set(20,level,21,TRIM); c.light(20,level,20)
    a.set(2,4,21,WALL); a.light(2,4,20); n.set(21,4,2,WALL); n.light(21,4,1)
    s.set(45,4,25,WALL); s.light(45,4,24); r.set(20,3,21,WALL); r.light(20,3,20)
    for module,x,z in ((f,47,23),(f,0,23),(c,0,19),(c,39,19),(c,19,39),(a,0,19),(n,19,0),(s,47,23)):
        module.set(x,1,z,TRIM)
    for x in range(48): f.set(x,1,23,TRIM); f.fill((x,2,23),(x,3,23),AIR)
    for tx,tz in ((0,19),(39,19),(19,39),(7,18),(7,20),(29,18),(29,20),(13,35),(32,33),(3,10)):
        for x in range(min(18,tx),max(18,tx)+1): c.set(x,0,20,TRIM); c.fill((x,1,20),(x,2,20),AIR)
        for z in range(min(20,tz),max(20,tz)+1): c.set(tx,0,z,TRIM); c.fill((tx,1,z),(tx,2,z),AIR)
    for z in range(0,19): n.set(19,1,z,TRIM); n.fill((19,2,z),(19,3,z),AIR)
    n.set(20,1,18,TRIM); n.fill((20,2,18),(20,3,18),AIR)
    for module,x,z in ((c,0,19),(c,39,19),(c,19,39),(n,19,0)):
        module.set(x,1,z,STAIR_E); module.fill((x,2,z),(x,3,z),AIR)
    for level in range(1,47): c.set(19,level,21,WALL); c.set(19,level,20,LADDER)
    for level in range(1,19): c.set(32,level,33,LADDER)
    for x in (4,14,24,34,44): f.set(x,5,23,TRIM); f.light(x,4,23)
    for x,z in ((2,19),(10,20),(18,20),(28,20),(37,19),(19,35),(8,19),(30,19),(14,35),(25,35)):
        c.set(x,4,z,TRIM); c.light(x,3,z)
    c.set(31,17,33,TRIM); c.light(31,18,33)
    for z in (3,11,17): n.set(19,5,z,TRIM); n.light(19,4,z)
    for level in range(1,6): r.set(19,level,21,WALL); r.set(19,level,20,LADDER)
    r.set(19,6,20,AIR)
    r.fill((18,5,20),(18,6,20),AIR); r.set(18,4,20,TRIM)
    _aegir_pressure_floor_lights((f,c,a,r,n,s))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(172,96,80))


def build_abyssal_observatory() -> LandmarkBuild:
    spec=Spec("aegir_abyssal_observatory","aegir","pressure observatory","51_g4_aegirstreet_1.png",
              ("three tall offset slabs", "repeated nested arch windows", "mid-air bridge galleries", "deeply sunken lower street"),"XL 176x96x128")
    f=Module(spec,"foundation",(48,14,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,18),(47,3,29),TRIM); f.fill((10,1,10),(37,8,37),AIR)
    for x,z in ((5,6),(42,6),(5,41),(42,41)): f.fill((x,1,z),(x,4,z),GIRDER); f.light(x,5,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    f.set(47,3,23,AIR); f.set(47,4,23,AIR); f.set(0,3,23,AIR); f.set(0,4,23,AIR)
    c=Module(spec,"core",(48,48,40)); c.fill((0,0,0),(47,0,39),WALL)
    for lo,hi,depth,top in ((2,13,4,43),(18,31,10,47),(36,45,2,37)):
        c.fill((lo,1,depth),(hi,top,37-depth),WALL); c.fill((lo+3,2,depth+3),(hi-3,top-2,34-depth),AIR)
        for y in range(5,top-5,9): c.fill((lo,y,depth-1),(hi,y+5,depth-1),GLASS); c.fill((lo-1,y+5,depth-1),(hi+1,y+6,depth),TRIM)
    c.fill((12,20,15),(37,23,24),TRIM); c.fill((15,21,17),(34,22,22),AIR)
    c.fill((3,1,19),(13,9,19),WALL); c.fill((7,1,19),(9,3,19),AIR); c.door(8,1,19); c.fill((19,1,19),(30,9,19),WALL); c.fill((23,1,19),(25,3,19),AIR); c.door(24,1,19)
    for i in range(8): x,y,z=4+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,10),(12,10),(22,12),(28,12)): c.set(x,10,z,TRIM); c.light(x,9,z)
    c.chest(11,1,32); c.chest(27,1,32); c.set(25,19,20,TRIM); c.chest(25,20,20)
    for y in range(38,47): c.set(24,y,21,WALL); c.set(24,y,20,LADDER)
    c.child((0,2,19),"west","foundation_core"); c.parent((47,2,19),"east","core_facade","facade"); c.parent((24,2,39),"south","core_annex","annex"); c.parent((24,47,20),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,19),(47,2,19),(24,2,39)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,48,40)); a.fill((0,0,0),(39,0,39),TRIM)
    for x in range(2,39,4):
        h=24+(x*5%20); a.fill((x,1,4),(x+1,h,8),WALL)
        for y in range(5,h-4,8): a.fill((x,y,3),(x+1,y+4,3),GLASS); a.fill((max(0,x-1),y+4,3),(min(39,x+2),y+5,4),TRIM)
    a.child((0,2,19),"west","core_facade"); a.set(0,3,19,AIR); a.set(0,4,19,AIR)
    r=Module(spec,"roof",(48,40,40)); r.fill((2,0,2),(45,1,37),TRIM); r.child((24,0,20),"down","core_roof",LADDER_STATE); r.set(24,1,20,LADDER); r.set(24,2,20,LADDER); r.set(24,1,21,WALL)
    for cx,h in ((8,36),(24,30),(39,24)):
        for y in range(2,h): r.fill((cx-2,y,8),(cx+2,y,31),WALL if y%5 else TRIM)
    r.fill((5,18,18),(42,21,22),GIRDER); r.set(24,31,20,GIRDER); r.light(24,32,20)
    n=Module(spec,"annex",(48,24,40)); n.fill((0,0,0),(47,0,39),TRIM); n.fill((2,1,2),(45,17,37),WALL); n.fill((5,2,5),(42,16,34),AIR); n.fill((25,2,5),(25,16,34),WALL); n.fill((25,2,18),(25,4,20),AIR); n.door(25,2,19); n.child((24,2,0),"north","core_annex"); n.set(24,3,0,AIR); n.set(24,4,0,AIR)
    s=Module(spec,"surrounding",(48,20,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,18),(47,2,29),TRIM); s.fill((8,1,8),(39,12,39),AIR); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    # Observatory galleries use an offset service riser beside the three slab towers.
    for module,x,y,z in ((c,8,1,19),(c,24,1,19),(n,25,2,19)):
        for side in (z-1,z+1): module.set(x,y-1,side,TRIM); module.fill((x,y,side),(x,y+1,side),AIR)
    for x,y,z in ((11,1,32),(27,1,32),(25,20,20)):
        c.set(x,y+1,z,AIR); c.set(x-1,y-1,z,TRIM); c.fill((x-1,y,z),(x-1,y+1,z),AIR)
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    for level in range(1,47): c.set(24,level,21,WALL); c.set(24,level,20,LADDER)
    for level in (6,16,26,36,45): c.set(25,level,21,TRIM); c.light(25,level,20)
    a.set(2,4,21,WALL); a.light(2,4,20); n.set(26,4,2,WALL); n.light(26,4,1)
    s.set(45,4,25,WALL); s.light(45,4,24); r.set(25,3,21,WALL); r.light(25,3,20)
    for module,x,z in ((f,47,23),(f,0,23),(c,0,19),(c,47,19),(c,24,39),(a,0,19),(n,24,0),(s,47,23)):
        module.set(x,1,z,TRIM)
    for x in range(48): f.set(x,1,23,TRIM); f.fill((x,2,23),(x,3,23),AIR)
    for tx,tz in ((0,19),(47,19),(24,39),(7,18),(7,20),(23,18),(23,20),(10,32),(26,32),(24,20),(3,8)):
        for x in range(min(23,tx),max(23,tx)+1): c.set(x,0,20,TRIM); c.fill((x,1,20),(x,2,20),AIR)
        for z in range(min(20,tz),max(20,tz)+1): c.set(tx,0,z,TRIM); c.fill((tx,1,z),(tx,2,z),AIR)
    for z in range(0,21): n.set(24,1,z,TRIM); n.fill((24,2,z),(24,3,z),AIR)
    n.set(25,1,18,TRIM); n.fill((25,2,18),(25,3,18),AIR)
    for module,x,z in ((c,0,19),(c,47,19),(c,24,39),(n,24,0)):
        module.set(x,1,z,STAIR_E); module.fill((x,2,z),(x,3,z),AIR)
    for level in range(1,47): c.set(24,level,21,WALL); c.set(24,level,20,LADDER)
    for level in range(1,21): c.set(24,level,20,LADDER)
    for x in (4,14,24,34,44): f.set(x,5,23,TRIM); f.light(x,4,23)
    for x,z in ((2,19),(12,20),(23,20),(36,20),(45,19),(24,35),(8,19),(24,19),(11,32),(27,32)):
        c.set(x,4,z,TRIM); c.light(x,3,z)
    for z in (3,11,19): n.set(24,5,z,TRIM); n.light(24,4,z)
    for level in range(1,6): r.set(24,level,21,WALL); r.set(24,level,20,LADDER)
    r.set(24,6,20,AIR)
    r.fill((23,5,20),(23,6,20),AIR); r.set(23,4,20,TRIM)
    _aegir_pressure_floor_lights((f,c,a,r,n,s))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(184,88,84))


def build_all(): return build_volcanic_beacon(), build_abyssal_observatory()
