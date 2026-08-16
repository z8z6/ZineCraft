"""Independent Minos builders from the Athenus temple and garden background set."""

from .base import AIR, FENCE, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, LEAVES, SLAB, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec


def build_heroes_temple() -> LandmarkBuild:
    spec=Spec("minos_heroes_temple","minos","city-state civic and training hall","69_g7_firsttemple.png",
              ("terraced white civic mass", "very tall sparse colonnades", "split temple tower", "open garden and steel service frame"),"XL 168x72x144")
    f=Module(spec,"foundation",(48,12,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for step in range(5): f.fill((4+step,1+step,5+step),(43-step,1+step,42-step),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,40,48)); c.fill((0,0,0),(47,0,47),WALL)
    c.fill((4,1,6),(43,12,41),WALL); c.fill((7,2,9),(40,11,38),AIR)
    c.fill((9,13,10),(38,24,37),WALL); c.fill((12,14,13),(35,23,34),AIR)
    c.fill((16,25,14),(31,38,33),WALL); c.fill((19,26,17),(28,37,30),AIR)
    for x in (6,12,18,29,35,41): c.fill((x,1,3),(x+1,25,7),TRIM); c.fill((x,1,40),(x+1,25,44),TRIM)
    c.fill((6,1,23),(20,10,23),WALL); c.fill((11,1,23),(13,3,23),AIR); c.door(12,1,23); c.fill((28,1,23),(42,10,23),WALL); c.fill((33,1,23),(35,3,23),AIR); c.door(34,1,23)
    for i in range(10): x,y,z=7+i,1+i,10; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((8,13),(19,13),(29,13),(40,13)): c.set(x,12,z,TRIM); c.light(x,11,z)
    c.fill((8,1,31),(19,1,34),SLAB); c.fill((29,1,31),(40,1,34),SLAB)
    c.chest(19,1,39); c.chest(29,1,39); c.set(35,12,32,TRIM); c.chest(35,13,32)
    for y in range(29,39): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,39,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,40,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for x in (2,8,14,20,27,33,39,45): a.fill((x,1,6),(min(47,x+1),32,10),WALL)
    a.fill((3,1,14),(44,22,43),WALL); a.fill((7,2,18),(40,21,39),AIR); a.fill((0,33,4),(47,36,13),TRIM)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,32,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for z in range(4,44):
        h=3+min(z,47-z)//3; r.fill((5,h,z),(42,h+1,z),WALL)
    r.fill((17,8,15),(30,27,32),WALL); r.fill((20,10,18),(27,25,29),AIR); r.set(24,28,24,GIRDER); r.light(24,29,24)
    n=Module(spec,"annex",(44,24,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,17,37),WALL); n.fill((5,2,5),(38,16,34),AIR); n.fill((22,2,5),(22,16,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,14,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,16),(47,1,31),TRIM)
    for x,z in ((7,10),(15,38),(33,10),(41,38)): s.set(x,2,z,LEAVES)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,72,136))


def build_heroes_plaza() -> LandmarkBuild:
    spec=Spec("minos_heroes_plaza","minos","heroic public assembly plaza","69_g10_templegarden.png",
              ("very broad stepped assembly ground", "detached column frames", "sunken training court", "modern white tower as distant secondary mass"),"XL 184x56x160")
    f=Module(spec,"foundation",(48,12,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for step in range(6): f.fill((step*2,1+step,step),(47-step,1+step,47-step*2),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,28,48)); c.fill((0,0,0),(47,0,47),WALL); c.fill((4,1,4),(43,4,43),TRIM); c.fill((10,1,10),(37,3,37),AIR)
    for x,z in ((7,7),(16,7),(31,7),(40,7),(7,40),(16,40),(31,40),(40,40)):
        c.fill((x,4,z),(x+2,22,z+2),WALL); c.fill((x-1,23,z-1),(x+3,25,z+3),TRIM)
    c.fill((6,1,23),(20,9,23),WALL); c.fill((11,1,23),(13,3,23),AIR); c.door(12,1,23); c.fill((28,1,23),(42,9,23),WALL); c.fill((33,1,23),(35,3,23),AIR); c.door(34,1,23)
    for i in range(8): x,y,z=7+i,1+i,12; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((8,10),(19,10),(29,10),(40,10)): c.set(x,10,z,TRIM); c.light(x,9,z)
    c.chest(19,1,40); c.chest(29,1,40); c.set(37,9,35,TRIM); c.chest(37,10,35)
    for y in range(17,27): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,27,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,28,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for x in range(2,47,6): a.fill((x,1,5),(x+2,22,10),WALL)
    a.fill((2,23,3),(47,26,12),TRIM); a.fill((8,1,16),(39,12,43),WALL); a.fill((11,2,19),(36,11,40),AIR)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,24,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for x,z in ((7,7),(17,7),(30,7),(40,7),(7,40),(17,40),(30,40),(40,40)): r.fill((x,2,z),(x+1,18,z+1),WALL)
    r.set(24,19,24,GIRDER); r.light(24,20,24)
    n=Module(spec,"annex",(44,20,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,13,37),WALL); n.fill((5,2,5),(38,12,34),AIR); n.fill((22,2,5),(22,12,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,16,48)); s.fill((0,0,0),(47,0,47),GROUND)
    for step in range(7): s.fill((step,1+step,step*2),(47-step*2,1+step,47-step),TRIM)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,52,136))


def build_all(): return build_heroes_temple(), build_heroes_plaza()
