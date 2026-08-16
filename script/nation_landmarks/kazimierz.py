"""Independent Kazimierz builders from the Grand Knight arena backgrounds."""

from .base import AIR, BARS, CASING, CHAIN, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, SHAFT_X, SLAB, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec


def build_arena_gate() -> LandmarkBuild:
    spec=Spec("kazimierz_arena_gate","kazimierz","competition arena entrance","bg_arena_1.png",
              ("enormous enclosed stadium bowl", "two steep seating banks", "steel truss roof ring", "suspended central screen over the field"),"XL 192x72x160")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,18),(47,1,29),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,40,48)); c.fill((0,0,0),(47,0,47),WALL)
    # Stepped U-shaped seating leaves the field and vomitories open.
    for step in range(10):
        y=1+step*2; inset=step+2
        c.fill((inset,y,inset),(47-inset,y+1,12+step),TRIM); c.fill((inset,y,35-step),(47-inset,y+1,47-inset),TRIM)
        c.fill((inset,y,13+step),(8+step,y+1,34-step),TRIM); c.fill((39-step,y,13+step),(47-inset,y+1,34-step),TRIM)
    c.fill((10,1,14),(37,1,33),GROUND)
    c.fill((3,1,23),(16,10,23),WALL); c.fill((8,1,23),(10,3,23),AIR); c.door(9,1,23); c.fill((31,1,23),(44,10,23),WALL); c.fill((36,1,23),(38,3,23),AIR); c.door(37,1,23)
    for i in range(10): x,y,z=4+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(18,8),(30,8),(42,12)): c.set(x,20,z,TRIM); c.light(x,19,z)
    c.chest(14,1,40); c.chest(33,1,40); c.set(39,19,36,TRIM); c.chest(39,20,36)
    for y in range(29,39): c.set(23,y,25,WALL); c.set(23,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((23,39,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,40,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for x in range(2,47,5): a.fill((x,1,5),(x+1,30,42),GIRDER)
    for step in range(8): a.fill((step+2,step*2+1,8+step),(45-step,step*2+2,39-step),WALL)
    a.fill((8,1,18),(39,16,29),AIR); a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,36,48)); r.fill((1,0,1),(46,1,46),TRIM); r.child((23,0,24),"down","core_roof",LADDER_STATE); r.set(23,1,24,LADDER); r.set(23,2,24,LADDER); r.set(23,1,25,WALL)
    for inset,y in ((2,4),(7,10),(12,16)):
        for x in range(inset,48-inset): r.set(x,y,inset,GIRDER); r.set(x,y,47-inset,GIRDER)
        for z in range(inset,48-inset): r.set(inset,y,z,GIRDER); r.set(47-inset,y,z,GIRDER)
    r.fill((18,19,18),(29,26,29),WALL); r.fill((20,21,20),(27,24,27),GLASS)
    for x,z in ((17,17),(30,17),(17,30),(30,30)): r.fill((x,4,z),(x,18,z),CHAIN)
    r.set(23,27,23,GIRDER); r.light(23,28,23)
    n=Module(spec,"annex",(48,24,40)); n.fill((0,0,0),(47,0,39),TRIM); n.fill((2,1,2),(45,17,37),WALL); n.fill((5,2,5),(42,16,34),AIR); n.fill((24,2,5),(24,16,34),WALL); n.fill((24,2,18),(24,4,20),AIR); n.door(24,2,19); n.fill((6,2,30),(41,3,32),SHAFT_X); n.set(12,2,29,CASING); n.set(35,2,29,CASING); n.child((23,2,0),"north","core_annex"); n.set(23,3,0,AIR); n.set(23,4,0,AIR)
    s=Module(spec,"surrounding",(48,12,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,16),(47,1,31),TRIM); s.fill((6,1,4),(41,4,11),WALL); s.fill((6,1,36),(41,4,43),WALL); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,76,136))


def build_knight_memorial() -> LandmarkBuild:
    spec=Spec("kazimierz_knight_monument","kazimierz","knight equipment and memorial hall","bg_arena_2.png",
              ("low public workshop hall", "cluster of leaning lance-like masts", "sponsor-light canopy", "open equipment yard"),"L 144x72x120")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,16),(47,1,31),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(40,32,48)); c.fill((0,0,0),(39,0,47),WALL)
    c.fill((2,1,4),(37,16,43),WALL); c.fill((5,2,7),(34,15,40),AIR)
    c.fill((5,1,23),(34,12,23),WALL); c.fill((12,1,23),(14,3,23),AIR); c.door(13,1,23); c.fill((25,1,23),(27,3,23),AIR); c.door(26,1,23)
    for x in (8,14,20,27,33):
        for y in range(3,31):
            z=8+(y//4)+(x%3); c.set(x,y,min(46,z),GIRDER)
    c.fill((6,1,31),(17,1,34),SLAB); c.fill((24,1,31),(35,1,34),SLAB)
    for i in range(9): x,y,z=6+i,1+i,9; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((7,12),(17,12),(25,12),(34,12)): c.set(x,17,z,TRIM); c.light(x,16,z)
    c.chest(16,1,39); c.chest(25,1,39); c.set(33,15,36,TRIM); c.chest(33,16,36)
    for y in range(21,31): c.set(20,y,25,WALL); c.set(20,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((39,2,23),"east","core_facade","facade"); c.parent((19,2,47),"south","core_annex","annex"); c.parent((20,31,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(39,2,23),(19,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(36,32,48)); a.fill((0,0,0),(35,0,47),TRIM); a.fill((3,1,5),(32,18,42),WALL); a.fill((6,2,8),(29,17,39),AIR)
    for x in range(3,34,4): a.fill((x,1,3),(x+1,27,6),GIRDER); a.fill((x,5,2),(x+1,14,2),GLASS)
    a.child((0,2,23),"west","core_facade"); a.set(0,3,23,AIR); a.set(0,4,23,AIR)
    r=Module(spec,"roof",(40,40,48)); r.fill((2,0,2),(37,1,45),TRIM); r.child((20,0,24),"down","core_roof",LADDER_STATE); r.set(20,1,24,LADDER); r.set(20,2,24,LADDER); r.set(20,1,25,WALL)
    for i,x in enumerate((5,11,17,23,29,35)):
        for y in range(2,35-i*2): r.set(x,y,min(47,7+y//3),GIRDER)
    r.fill((4,8,10),(36,10,40),GLASS); r.set(20,36,24,GIRDER); r.light(20,37,24)
    n=Module(spec,"annex",(40,22,40)); n.fill((0,0,0),(39,0,39),TRIM); n.fill((2,1,2),(37,15,37),WALL); n.fill((5,2,5),(34,14,34),AIR); n.fill((20,2,5),(20,14,34),WALL); n.fill((20,2,18),(20,4,20),AIR); n.door(20,2,19); n.fill((6,2,30),(33,3,32),SHAFT_X); n.child((19,2,0),"north","core_annex"); n.set(19,3,0,AIR); n.set(19,4,0,AIR)
    s=Module(spec,"surrounding",(48,12,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,14),(47,1,33),TRIM); s.fill((5,1,6),(15,6,13),WALL); s.fill((32,1,35),(43,6,42),WALL); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(172,72,88))


def build_all(): return build_arena_gate(), build_knight_memorial()
