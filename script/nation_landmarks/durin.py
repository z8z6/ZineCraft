"""Independent Durin builders from Ideal City garden and civic backgrounds."""

from .base import AIR, CASING, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, LEAVES, SHAFT_X, SLAB, STAIR_E, TRIM, WATER, WALL, LandmarkBuild, Module, Spec


def build_dome_station() -> LandmarkBuild:
    spec=Spec("durin_dome_station","durin","underground garden transit dome","30_g4_durinsquare.png",
              ("low blue sweeping half-dome", "white modular garden terraces", "open central transit throat", "transparent canopy shards"),"XL 168x56x144")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,15),(47,2,32),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,32,48)); c.fill((0,0,0),(47,0,47),WALL)
    # A stepped half-dome stays broadly open at ground level.
    for x in range(2,46):
        rise=max(5,26-abs(24-x)); c.fill((x,rise,4),(x,rise+2,43),WALL if x%4 else TRIM)
    c.fill((5,1,7),(42,8,40),WALL); c.fill((8,2,10),(39,7,37),AIR)
    c.fill((0,8,12),(47,11,20),TRIM); c.fill((4,9,14),(43,11,18),AIR)
    c.fill((6,1,23),(20,8,23),WALL); c.fill((11,1,23),(13,3,23),AIR); c.door(12,1,23); c.fill((28,1,23),(42,8,23),WALL); c.fill((33,1,23),(35,3,23),AIR); c.door(34,1,23)
    for i in range(8): x,y,z=7+i,1+i,12; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((8,14),(20,14),(30,14),(40,14)): c.set(x,9,z,TRIM); c.light(x,8,z)
    c.chest(18,1,36); c.chest(30,1,36); c.set(38,10,33,TRIM); c.chest(38,11,33)
    for y in range(20,31): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((47,2,23),"east","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,31,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,28,48)); a.fill((0,0,0),(39,0,47),TRIM)
    for i in range(8):
        x=2+i*5; h=12+(i*3%12); a.fill((x,1,5),(min(39,x+5),h,40),WALL); a.fill((x+2,4,4),(min(39,x+4),h-3,4),GLASS); a.fill((x,h+1,3),(min(39,x+7),h+2,43),TRIM)
    a.child((0,2,23),"west","core_facade"); a.set(0,3,23,AIR); a.set(0,4,23,AIR)
    r=Module(spec,"roof",(48,28,48)); r.fill((3,0,3),(44,1,44),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for x in range(3,45):
        h=max(3,24-abs(24-x)); r.fill((x,h,5),(x,h+1,42),GLASS if x%3 else TRIM)
    r.fill((7,3,8),(40,7,39),WALL); r.fill((10,4,11),(37,6,36),AIR); r.set(24,24,24,GIRDER); r.light(24,25,24)
    n=Module(spec,"annex",(44,20,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,13,37),WALL); n.fill((5,2,5),(38,12,34),AIR); n.fill((22,2,5),(22,12,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((5,2,30),(38,3,32),SHAFT_X); n.set(10,2,29,CASING); n.set(33,2,29,CASING); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,14,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,16),(47,1,31),TRIM); s.fill((4,1,4),(14,1,12),WATER); s.fill((34,1,35),(44,1,43),WATER)
    for x,z in ((8,14),(14,8),(34,39),(40,33)): s.set(x,2,z,LEAVES)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(184,60,88))


def build_water_garden() -> LandmarkBuild:
    spec=Spec("durin_water_park","durin","engineered public water garden","30_g2_fountainlake.png",
              ("interlocking ribbon canopies", "sunken water courts", "bright modular terraces", "garden planted into structural frames"),"XL 176x40x152")
    f=Module(spec,"foundation",(48,12,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for inset,y in ((2,1),(7,2),(12,3)): f.fill((inset,y,inset),(47-inset,y,47-inset),TRIM)
    f.fill((10,1,10),(37,2,37),WATER)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,28,48)); c.fill((0,0,0),(47,0,47),WALL)
    c.fill((3,1,3),(44,5,44),TRIM); c.fill((7,1,7),(40,4,40),AIR)
    for i in range(8):
        x=3+i*6; z=5+(i*7%28); h=12+(i%4)*3
        c.fill((x,5,z),(min(47,x+9),7,min(47,z+18)),WALL); c.fill((x+2,6,z+2),(min(47,x+7),7,min(47,z+16)),AIR); c.fill((x,h,z+4),(min(47,x+5),h+2,min(47,z+22)),GLASS)
    c.fill((6,1,23),(20,8,23),WALL); c.fill((11,1,23),(13,3,23),AIR); c.door(12,1,23); c.fill((28,1,23),(42,8,23),WALL); c.fill((33,1,23),(35,3,23),AIR); c.door(34,1,23)
    for i in range(8): x,y,z=8+i,1+i,10; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((8,14),(20,14),(30,14),(40,14)): c.set(x,9,z,TRIM); c.light(x,8,z)
    c.chest(18,1,37); c.chest(30,1,37); c.set(38,9,34,TRIM); c.chest(38,10,34)
    for y in range(17,27): c.set(23,y,25,WALL); c.set(23,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((23,27,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,24,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for i in range(9):
        x=i*5; z=4+i*3; a.fill((x,2,z),(min(47,x+12),5,min(47,z+28)),WALL if i%2 else GLASS)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,28,48)); r.fill((4,0,4),(43,1,43),TRIM); r.child((23,0,24),"down","core_roof",LADDER_STATE); r.set(23,1,24,LADDER); r.set(23,2,24,LADDER); r.set(23,1,25,WALL)
    for i in range(8):
        x=3+i*6; y=4+(i%3)*5; r.fill((x,y,5),(min(47,x+10),y+2,42),GLASS if i%2 else WALL)
    r.set(23,19,24,GIRDER); r.light(23,20,24)
    n=Module(spec,"annex",(44,20,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,13,37),WALL); n.fill((5,2,5),(38,12,34),AIR); n.fill((22,2,5),(22,12,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((6,2,30),(37,3,32),SHAFT_X); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,14,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,15),(47,1,32),TRIM); s.fill((4,1,4),(43,1,12),WATER); s.fill((4,1,35),(43,1,43),WATER)
    for x in range(6,44,6): s.set(x,2,14,LEAVES); s.set(x,2,33,LEAVES)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,56,136))


def build_all(): return build_dome_station(), build_water_garden()
