"""Independent Kazdel builders from furnace platform, street and Babel backgrounds."""

from .base import AIR, BARREL, BARS, CASING, CHAIN, GIRDER, GROUND, LADDER, LADDER_STATE, SHAFT_X, SLAB, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec


def build_babel_ruins() -> LandmarkBuild:
    spec=Spec("kazdel_babel_ruins","kazdel","war-scarred civic stronghold","49_g5_furnaceplatform.png",
              ("broken blade-like towers", "open blast scars through the mass", "low patched plate decks", "fire-lit fissure field"),"XL 168x96x128")
    f=Module(spec,"foundation",(48,12,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for x in range(0,48,5): f.fill((x,1,(x*7)%40),(min(47,x+6),2,min(47,(x*7)%40+8)),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,48,48)); c.fill((0,0,0),(47,0,47),WALL)
    # Three tapered shards are intentionally incomplete and perforated.
    for cx,cz,h,lean in ((10,12,46,1),(28,25,39,-1),(40,11,31,-1)):
        for y in range(1,h):
            width=max(1,7-y//8); shift=lean*(y//10)
            c.fill((max(0,cx-width+shift),y,max(0,cz-width)),(min(47,cx+width+shift),y,min(47,cz+width)),WALL if y%7 else TRIM)
            if y%11 in (4,5): c.fill((max(0,cx-2+shift),y,max(0,cz-width)),(min(47,cx+3+shift),y,min(47,cz+width)),AIR)
    c.fill((2,1,20),(18,10,20),WALL); c.fill((8,1,20),(10,3,20),AIR); c.door(9,1,20); c.fill((26,1,29),(43,10,29),WALL); c.fill((32,1,29),(34,3,29),AIR); c.door(33,1,29)
    for i in range(9): x,y,z=4+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(16,12),(30,34),(41,34)): c.set(x,11,z,GIRDER); c.light(x,10,z)
    c.fill((5,1,32),(16,1,34),SLAB); c.fill((29,1,37),(40,1,39),SLAB)
    c.chest(16,1,40); c.chest(29,1,42); c.set(34,18,33,TRIM); c.chest(34,19,33)
    for y in range(35,47): c.set(10,y,15,WALL); c.set(10,y,14,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((47,2,23),"east","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((10,47,14),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,48,48)); a.fill((0,0,0),(39,0,47),TRIM)
    for i,(x,h,lean) in enumerate(((2,43,1),(10,28,-1),(19,37,1),(29,24,-1),(35,33,-1))):
        for y in range(1,h):
            sx=x+lean*(y//9); a.fill((max(0,sx),y,6),(min(39,sx+4),y,42),WALL if y%6 else GIRDER)
            if y%13 in (5,6,7): a.fill((max(0,sx+1),y,12),(min(39,sx+3),y,35),AIR)
    a.child((0,2,23),"west","core_facade"); a.set(0,3,23,AIR); a.set(0,4,23,AIR)
    r=Module(spec,"roof",(48,44,48)); r.fill((3,0,3),(44,1,44),TRIM); r.child((10,0,14),"down","core_roof",LADDER_STATE); r.set(10,1,14,LADDER); r.set(10,2,14,LADDER); r.set(10,1,15,WALL)
    for cx,cz,h in ((8,11,41),(24,30,33),(40,12,25)):
        for y in range(2,h): r.fill((max(0,cx-2-y//15),y,max(0,cz-2)),(min(47,cx+2),y,min(47,cz+2+y//18)),WALL)
    r.set(8,42,11,GIRDER); r.light(8,43,11)
    n=Module(spec,"annex",(44,24,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,17,37),WALL); n.fill((5,2,5),(38,16,34),AIR); n.fill((22,2,5),(22,16,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((6,2,30),(37,3,32),SHAFT_X); n.set(10,2,29,CASING); n.set(33,2,29,CASING); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,14,48)); s.fill((0,0,0),(47,0,47),GROUND)
    for i in range(9): x=2+i*5; s.fill((x,1,(i*11)%38),(min(47,x+6),2,min(47,(i*11)%38+9)),TRIM)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(184,92,88))


def build_sarkaz_camp() -> LandmarkBuild:
    spec=Spec("kazdel_sarkaz_camp","kazdel","clan logistics and shelter compound","49_g2_kazdelstreet_d.png",
              ("irregular patched shelter rows", "shared furnace spine", "broken perimeter instead of formal wall", "external pipes and repair gantries"),"L 152x36x144")
    f=Module(spec,"foundation",(48,8,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,24,48)); c.fill((0,0,0),(47,0,47),WALL)
    for x0,x1,z0,z1,h in ((2,16,4,18,11),(5,21,29,44,15),(26,44,3,17,13),(29,45,26,42,10),(18,31,14,34,18)):
        c.fill((x0,1,z0),(x1,h,z1),WALL); c.fill((x0+2,2,z0+2),(x1-2,h-2,z1-2),AIR)
        c.fill((max(0,x0-1),h+1,max(0,z0-2)),(min(47,x1+3),h+2,min(47,z1+2)),TRIM)
    c.fill((4,1,18),(17,8,18),WALL); c.fill((9,1,18),(11,3,18),AIR); c.door(10,1,18); c.fill((28,1,26),(44,8,26),WALL); c.fill((34,1,26),(36,3,26),AIR); c.door(35,1,26)
    for i in range(8): x,y,z=5+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(16,12),(31,13),(42,13)): c.set(x,10,z,GIRDER); c.light(x,9,z)
    c.chest(15,1,16); c.chest(42,1,39); c.set(25,12,28,TRIM); c.chest(25,13,28)
    for y in range(13,23): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((47,2,23),"east","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,23,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,24,48)); a.fill((0,0,0),(39,0,47),TRIM)
    for i,(x,z,h,w) in enumerate(((1,4,12,9),(8,14,18,12),(19,5,14,8),(27,19,11,11),(35,8,16,4))):
        a.fill((x,1,z),(min(39,x+w),h,min(47,z+24)),WALL); a.fill((x-1 if x else 0,h+1,max(0,z-2)),(min(39,x+w+3),h+2,min(47,z+27)),TRIM)
    a.child((0,2,23),"west","core_facade"); a.set(0,3,23,AIR); a.set(0,4,23,AIR)
    r=Module(spec,"roof",(48,24,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for x,z,h in ((6,7,16),(19,29,21),(33,8,13),(39,34,18)):
        r.fill((x,2,z),(min(47,x+6),h,min(47,z+5)),WALL); r.fill((x-2,h+1,max(0,z-2)),(min(47,x+9),h+2,min(47,z+8)),TRIM)
    r.set(24,20,24,GIRDER); r.light(24,21,24)
    n=Module(spec,"annex",(44,20,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,13,37),WALL); n.fill((5,2,5),(38,12,34),AIR); n.fill((22,2,5),(22,12,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((5,2,30),(16,4,32),BARREL); n.fill((25,2,30),(37,3,32),SHAFT_X); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,10,48)); s.fill((0,0,0),(47,0,47),GROUND)
    for x0,z0,x1,z1 in ((3,4,17,7),(7,39,24,43),(28,5,43,8),(35,34,46,38)): s.fill((x0,1,z0),(x1,4,z1),WALL)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(184,48,88))


def build_all(): return build_babel_ruins(), build_sarkaz_camp()
