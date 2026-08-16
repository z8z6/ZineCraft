"""Independent Rim Billiton builders from mine, transporter and town backgrounds."""

from .base import AIR, BARREL, CASING, CHAIN, GIRDER, GROUND, LADDER, LADDER_STATE, RAIL, SHAFT_X, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec


def build_mining_derrick() -> LandmarkBuild:
    spec=Spec("rim_billiton_mining_derrick","rim_billiton","deep mine processing headworks","46_g6_rmbtmine.png",
              ("giant ribbed excavation arch", "open mine headframe", "hopper and silo cluster", "multi-level catwalks against rough rock"),"XL 184x96x152")
    f=Module(spec,"foundation",(48,12,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,17),(47,2,30),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,48,48)); c.fill((0,0,0),(47,0,47),WALL)
    # Nested ribs form the mine mouth; the centre remains a cavernous void.
    for inset in range(0,19,3):
        for y in range(1,42-inset):
            x0=inset; x1=47-inset; z=5+inset//2
            if y<8 or y>18+inset: c.set(x0,y,z,GIRDER); c.set(x1,y,z,GIRDER)
        c.fill((inset,38-inset//2,5+inset//2),(47-inset,40-inset//2,7+inset//2),GIRDER)
    c.fill((5,1,12),(42,13,44),WALL); c.fill((9,2,15),(38,12,41),AIR)
    for x in (10,18,29,37): c.fill((x,13,14),(x+1,42,40),GIRDER)
    for y in (17,25,33): c.fill((8,y,16),(39,y+2,39),TRIM); c.fill((11,y+1,19),(36,y+2,36),AIR)
    c.fill((7,1,23),(20,10,23),WALL); c.fill((12,1,23),(14,3,23),AIR); c.door(13,1,23); c.fill((27,1,23),(40,10,23),WALL); c.fill((32,1,23),(34,3,23),AIR); c.door(33,1,23)
    for i in range(10): x,y,z=7+i,1+i,15; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((9,18),(19,18),(29,18),(39,18)): c.set(x,14,z,GIRDER); c.light(x,13,z)
    c.chest(18,1,40); c.chest(30,1,40); c.set(37,16,36,TRIM); c.chest(37,17,36)
    for y in range(37,47): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,47,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,48,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for inset in range(1,22,4):
        h=45-inset; a.fill((inset,1,4+inset//2),(inset+1,h,7+inset//2),GIRDER); a.fill((46-inset,1,4+inset//2),(47-inset,h,7+inset//2),GIRDER); a.fill((inset,h,4+inset//2),(47-inset,h+2,7+inset//2),GIRDER)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,48,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    # Four-leg headframe and suspended hoist block.
    for x,z in ((9,10),(38,10),(9,37),(38,37)): r.fill((x,2,z),(x+2,43,z+2),GIRDER)
    for y in (12,25,38): r.fill((8,y,9),(41,y+2,40),GIRDER); r.fill((11,y+1,12),(38,y+2,37),AIR)
    r.fill((18,30,18),(29,39,29),CASING); r.fill((21,32,21),(26,37,26),AIR)
    for x,z in ((18,18),(29,18),(18,29),(29,29)): r.fill((x,12,z),(x,29,z),CHAIN)
    r.set(24,44,24,GIRDER); r.light(24,45,24)
    n=Module(spec,"annex",(48,28,44)); n.fill((0,0,0),(47,0,43),TRIM); n.fill((2,1,2),(45,20,41),WALL); n.fill((5,2,5),(42,19,38),AIR); n.fill((24,2,5),(24,19,38),WALL); n.fill((24,2,20),(24,4,22),AIR); n.door(24,2,21); n.fill((6,3,34),(41,3,36),SHAFT_X); n.set(12,3,33,CASING); n.set(35,3,33,CASING); n.child((23,2,0),"north","core_annex"); n.set(23,3,0,AIR); n.set(23,4,0,AIR)
    s=Module(spec,"surrounding",(48,16,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,15),(47,1,32),TRIM)
    for z in (19,23,27): s.fill((0,2,z),(47,2,z),RAIL)
    for x,z in ((5,5),(35,5),(5,37),(35,37)): s.fill((x,1,z),(x+8,10,z+6),WALL)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,96,140))


def build_rail_depot() -> LandmarkBuild:
    spec=Spec("rim_billiton_rail_depot","rim_billiton","ore rail and freight depot","46_g2_rmbttransporter.png",
              ("very long rail shed", "sawtooth daylight roof", "cross-track loading gantries", "offset dispatch tower and ore bins"),"XL 192x48x128")
    f=Module(spec,"foundation",(48,8,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,12),(47,1,35),TRIM)
    for z in (16,22,28,34): f.fill((0,2,z),(47,2,z),RAIL)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,28,48)); c.fill((0,0,0),(47,0,47),WALL)
    c.fill((2,1,4),(45,18,43),WALL); c.fill((5,2,7),(42,17,40),AIR)
    for z in (12,18,24,30,36):
        c.fill((5,1,z),(42,1,z),RAIL)
        for x in (8,22,36): c.fill((x,2,z-2),(x+1,16,z+2),GIRDER)
    c.fill((5,1,23),(20,10,23),WALL); c.fill((11,1,23),(13,3,23),AIR); c.door(12,1,23); c.fill((28,1,23),(43,10,23),WALL); c.fill((34,1,23),(36,3,23),AIR); c.door(35,1,23)
    for i in range(9): x,y,z=7+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((8,10),(20,10),(30,10),(41,10)): c.set(x,18,z,GIRDER); c.light(x,17,z)
    c.chest(18,1,40); c.chest(30,1,40); c.set(39,17,36,TRIM); c.chest(39,18,36)
    for y in range(17,27): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,27,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,28,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for z in range(4,44,8):
        a.fill((1,1,z),(46,14,z+5),WALL); a.fill((4,2,z+1),(43,13,z+4),AIR); a.fill((1,15,z),(46,18,z+2),GIRDER)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,24,48)); r.fill((1,0,1),(46,1,46),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for z in range(3,46,7):
        for x in range(2,46): r.set(x,4+abs(24-x)//5,z,WALL); r.set(x,5+abs(24-x)//5,min(47,z+1),GIRDER)
    r.fill((35,2,8),(44,20,19),WALL); r.fill((38,4,10),(41,17,17),AIR); r.set(24,19,24,GIRDER); r.light(24,20,24)
    n=Module(spec,"annex",(48,24,40)); n.fill((0,0,0),(47,0,39),TRIM); n.fill((2,1,2),(45,17,37),WALL); n.fill((5,2,5),(42,16,34),AIR); n.fill((24,2,5),(24,16,34),WALL); n.fill((24,2,18),(24,4,20),AIR); n.door(24,2,19); n.fill((6,2,30),(41,3,32),SHAFT_X); n.set(12,2,29,CASING); n.set(35,2,29,CASING); n.child((23,2,0),"north","core_annex"); n.set(23,3,0,AIR); n.set(23,4,0,AIR)
    s=Module(spec,"surrounding",(48,12,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,11),(47,1,36),TRIM)
    for z in (15,21,27,33): s.fill((0,2,z),(47,2,z),RAIL)
    for x,z in ((4,4),(34,4),(4,38),(34,38)): s.fill((x,1,z),(x+9,7,z+6),WALL)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,52,136))


def build_all(): return build_mining_derrick(), build_rail_depot()
