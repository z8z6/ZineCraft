"""Independent Higashi builders from the Jinda/Kaji dense-street backgrounds."""

from .base import AIR, BARREL, FENCE, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, SHAFT_X, SLAB, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec


def build_rift_precinct() -> LandmarkBuild:
    spec=Spec("higashi_rift_shrine","higashi","rift-side public precinct","64_g1_jindastreet_d.png",
              ("narrow deep street canyon", "asymmetric stacked shop masses", "multiple cross-street steel frames", "single red vertical service core"),"L 144x64x136")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,19),(47,1,28),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(40,40,48)); c.fill((0,0,0),(39,0,47),WALL)
    # Five non-aligned street-wall masses make a canyon rather than a pavilion.
    for x0,x1,z0,z1,h in ((1,13,3,19,31),(2,16,29,45,38),(24,38,2,16,35),(21,35,27,46,28),(14,25,8,39,19)):
        c.fill((x0,1,z0),(x1,h,z1),WALL); c.fill((x0+2,2,z0+2),(x1-2,h-2,z1-2),AIR)
    for y in (12,23,34):
        c.fill((5,y,20),(34,y+2,27),GIRDER); c.fill((9,y+1,22),(30,y+2,25),AIR)
    c.fill((3,1,19),(15,9,19),WALL); c.fill((7,1,19),(9,3,19),AIR); c.door(8,1,19); c.fill((23,1,27),(35,9,27),WALL); c.fill((28,1,27),(30,3,27),AIR); c.door(29,1,27)
    for i in range(9): x,y,z=4+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((5,12),(12,12),(26,12),(34,12)): c.set(x,10,z,TRIM); c.light(x,9,z)
    c.chest(12,1,16); c.chest(33,1,13); c.set(30,11,34,TRIM); c.chest(30,12,34)
    for y in range(29,39): c.set(10,y,15,WALL); c.set(10,y,14,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((39,2,23),"east","core_facade","facade"); c.parent((19,2,47),"south","core_annex","annex"); c.parent((10,39,14),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(39,2,23),(19,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,40,48)); a.fill((0,0,0),(39,0,47),TRIM)
    for i,(x,z,h,w) in enumerate(((1,4,22,8),(8,9,35,9),(17,3,28,7),(24,13,38,10),(34,6,25,5))):
        a.fill((x,1,z),(min(39,x+w),h,min(47,z+28)),WALL); a.fill((x+2,4,z),(min(39,x+w-2),h-4,z),GLASS)
        a.fill((x-1 if x else x,min(39,h+1),z-2),(min(39,x+w+2),min(39,h+2),min(47,z+30)),TRIM)
    a.child((0,2,23),"west","core_facade"); a.set(0,3,23,AIR); a.set(0,4,23,AIR)
    r=Module(spec,"roof",(40,28,48)); r.fill((1,0,1),(38,1,46),TRIM); r.child((10,0,14),"down","core_roof",LADDER_STATE); r.set(10,1,14,LADDER); r.set(10,2,14,LADDER); r.set(10,1,15,WALL)
    for z in (6,18,30,42):
        for x in range(2,38): r.set(x,5+abs(20-x)//6,z,WALL)
    for x in (5,17,29,37): r.fill((x,2,3),(x+1,22,44),GIRDER)
    r.set(20,23,23,GIRDER); r.light(20,24,23)
    n=Module(spec,"annex",(40,24,40)); n.fill((0,0,0),(39,0,39),TRIM); n.fill((2,1,2),(37,17,37),WALL); n.fill((5,2,5),(34,16,34),AIR); n.fill((20,2,5),(20,16,34),WALL); n.fill((20,2,18),(20,4,20),AIR); n.door(20,2,19); n.fill((6,2,30),(16,4,32),BARREL); n.child((19,2,0),"north","core_annex"); n.set(19,3,0,AIR); n.set(19,4,0,AIR)
    s=Module(spec,"surrounding",(48,12,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,18),(47,1,29),TRIM)
    for x in (8,20,32,44): s.fill((x,1,4),(x+1,9,43),GIRDER)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(176,68,88))


def build_sokogawa_watchtower() -> LandmarkBuild:
    spec=Spec("higashi_sokogawa_watchtower","higashi","border watch and relay tower","64_g18_kajistreet_n.png",
              ("slender offset relay mast", "cantilever watch decks", "external maintenance cage", "low fortified border store"),"L 136x88x104")
    f=Module(spec,"foundation",(44,10,44)); f.fill((0,0,0),(43,0,43),GROUND); f.fill((4,1,4),(39,3,39),TRIM)
    for x,z in ((5,5),(38,5),(5,38),(38,38)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((43,2,21),"east","foundation_core","core"); f.parent((0,2,21),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(36,44,36)); c.fill((0,0,0),(35,0,35),WALL)
    c.fill((3,1,5),(19,21,31),WALL); c.fill((6,2,8),(16,20,28),AIR); c.fill((21,1,12),(32,12,30),WALL); c.fill((24,2,15),(29,11,27),AIR)
    for y,x0,x1 in ((8,0,25),(16,5,35),(25,9,29),(34,12,26)): c.fill((x0,y,6),(x1,y+2,29),TRIM); c.fill((x0+3,y+1,9),(x1-3,y+2,26),AIR)
    c.fill((5,1,17),(17,9,17),WALL); c.fill((9,1,17),(11,3,17),AIR); c.door(10,1,17); c.fill((22,1,17),(32,8,17),WALL); c.fill((25,1,17),(27,3,17),AIR); c.door(26,1,17)
    for i in range(9): x,y,z=5+i,1+i,9; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(15,12),(24,14),(31,14)): c.set(x,10,z,TRIM); c.light(x,9,z)
    c.chest(16,1,28); c.chest(29,1,27); c.set(16,15,26,TRIM); c.chest(16,16,26)
    for y in range(32,43): c.set(17,y,19,WALL); c.set(17,y,18,LADDER)
    c.child((0,2,17),"west","foundation_core"); c.parent((35,2,17),"east","core_facade","facade"); c.parent((17,2,35),"south","core_annex","annex"); c.parent((17,43,18),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,17),(35,2,17),(17,2,35)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(28,36,36)); a.fill((0,0,0),(27,0,35),TRIM)
    for x in (2,7,13,20,25): a.fill((x,1,5),(x+1,32,30),GIRDER)
    a.fill((4,5,8),(23,26,27),WALL); a.fill((7,7,11),(20,24,24),AIR)
    a.child((0,2,17),"west","core_facade"); a.set(0,3,17,AIR); a.set(0,4,17,AIR)
    r=Module(spec,"roof",(36,44,36)); r.fill((4,0,4),(31,1,31),TRIM); r.child((17,0,18),"down","core_roof",LADDER_STATE); r.set(17,1,18,LADDER); r.set(17,2,18,LADDER); r.set(17,1,19,WALL)
    for y in range(2,40):
        radius=max(1,5-y//9); r.fill((17-radius,y,17-radius),(17+radius,y,17+radius),GIRDER if y%6 else TRIM)
    for y in (10,21,32): r.fill((5,y,8),(29,y+2,27),TRIM); r.fill((8,y+1,11),(26,y+2,24),AIR)
    r.set(17,40,17,GIRDER); r.light(17,41,17)
    n=Module(spec,"annex",(36,20,36)); n.fill((0,0,0),(35,0,35),TRIM); n.fill((2,1,2),(33,13,33),WALL); n.fill((5,2,5),(30,12,30),AIR); n.fill((18,2,5),(18,12,30),WALL); n.fill((18,2,16),(18,4,18),AIR); n.door(18,2,17); n.fill((6,2,27),(15,3,29),SHAFT_X); n.child((17,2,0),"north","core_annex"); n.set(17,3,0,AIR); n.set(17,4,0,AIR)
    s=Module(spec,"surrounding",(44,10,44)); s.fill((0,0,0),(43,0,43),GROUND); s.fill((0,1,18),(43,1,25),TRIM); s.fill((5,1,5),(14,7,13),WALL); s.fill((30,1,30),(39,6,38),WALL); s.child((43,2,21),"east","foundation_surrounding"); s.set(43,3,21,AIR); s.set(43,4,21,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(152,88,76))


def build_all(): return build_rift_precinct(), build_sokogawa_watchtower()
