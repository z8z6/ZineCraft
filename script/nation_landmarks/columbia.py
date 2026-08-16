"""Independent Columbia builders; exterior claims stay conservative where only interiors are visible."""

from .base import AIR, CASING, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, LEAVES, SHAFT_X, SLAB, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec


def build_frontier_lab() -> LandmarkBuild:
    spec=Spec("columbia_frontier_lab","columbia","research and energy complex","38_g1_rhinemeetingroom.png",
              ("low wide clean research floor", "circular atrium and spiral circulation", "continuous linear ceiling light", "interior planting under a gridded roof"),"L 152x48x136, exterior C-grade")
    f=Module(spec,"foundation",(48,8,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,16),(47,1,31),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,28,48)); c.fill((0,0,0),(47,0,47),WALL)
    # Three curved/offset lab wings wrap a transparent atrium.
    for inset,y in ((2,1),(7,7),(13,13)):
        c.fill((inset,y,inset),(47-inset,y+5,47-inset),WALL); c.fill((inset+3,y+1,inset+3),(44-inset,y+4,44-inset),AIR)
    for r,y in ((15,3),(11,8),(7,14)):
        for x in range(24-r,24+r+1): c.set(x,y,24-r,GLASS); c.set(x,y,24+r,GLASS)
        for z in range(24-r,24+r+1): c.set(24-r,y,z,GLASS); c.set(24+r,y,z,GLASS)
    c.fill((4,1,23),(20,6,23),WALL); c.fill((10,1,23),(12,3,23),AIR); c.door(11,1,23); c.fill((28,1,23),(44,6,23),WALL); c.fill((34,1,23),(36,3,23),AIR); c.door(35,1,23)
    for i in range(9): x,y,z=6+i,1+i,9; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((7,12),(18,12),(30,12),(41,12)): c.set(x,7,z,TRIM); c.light(x,6,z)
    c.fill((6,1,31),(18,1,34),SLAB); c.fill((30,1,31),(42,1,34),SLAB)
    c.chest(18,1,40); c.chest(30,1,40); c.set(39,12,36,TRIM); c.chest(39,13,36)
    for y in range(17,27): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((47,2,23),"east","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,27,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,24,48)); a.fill((0,0,0),(39,0,47),TRIM)
    for i,(x,z,w,h) in enumerate(((2,5,13,12),(11,9,15,18),(23,4,14,14),(31,13,8,20))):
        a.fill((x,1,z),(min(39,x+w),h,min(47,z+28)),WALL); a.fill((x+2,4,z),(min(39,x+w-2),h-3,z),GLASS)
    a.child((0,2,23),"west","core_facade"); a.set(0,3,23,AIR); a.set(0,4,23,AIR)
    r=Module(spec,"roof",(48,24,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for r0,y in ((18,3),(13,8),(8,13)):
        for x in range(24-r0,24+r0+1): r.set(x,y,24-r0,GIRDER); r.set(x,y,24+r0,GIRDER)
        for z in range(24-r0,24+r0+1): r.set(24-r0,y,z,GIRDER); r.set(24+r0,y,z,GIRDER)
    r.set(24,17,24,GIRDER); r.light(24,18,24)
    n=Module(spec,"annex",(44,20,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,13,37),WALL); n.fill((5,2,5),(38,12,34),AIR); n.fill((22,2,5),(22,12,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((7,2,30),(37,3,32),SHAFT_X); n.set(12,2,29,CASING); n.set(32,2,29,CASING); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,12,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,15),(47,1,32),TRIM)
    for x,z in ((8,10),(16,38),(32,10),(40,38)): s.set(x,2,z,LEAVES)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(184,52,88))


def build_platform_outpost() -> LandmarkBuild:
    spec=Spec("columbia_prison_outpost","columbia","mobile platform service node","38_g12_trimountrestarea.png",
              ("raised mobile deck", "exposed diagonal chassis", "offset prefabricated service pods", "vehicle docking notch"),"L 144x52x120, lore-compatible exterior")
    f=Module(spec,"foundation",(48,12,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for x,z in ((6,6),(41,6),(6,41),(41,41),(15,15),(32,15),(15,32),(32,32)): f.fill((x,1,z),(x+1,8,z+1),GIRDER)
    f.fill((3,9,3),(44,10,44),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.light(x,8,z); f.set(x,7,z,GIRDER)
    f.parent((47,9,23),"east","foundation_core","core"); f.parent((0,9,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(40,28,48)); c.fill((0,0,0),(39,0,47),TRIM)
    for x0,x1,z0,z1,h in ((2,17,4,22,15),(20,37,8,30,21),(5,25,28,44,12)):
        c.fill((x0,1,z0),(x1,h,z1),WALL); c.fill((x0+3,2,z0+3),(x1-3,h-2,z1-3),AIR)
    for x in (5,15,25,35): c.fill((x,1,2),(x+1,20,45),GIRDER)
    c.fill((3,1,20),(16,8,20),WALL); c.fill((8,1,20),(10,3,20),AIR); c.door(9,1,20); c.fill((21,1,20),(36,8,20),WALL); c.fill((27,1,20),(29,3,20),AIR); c.door(28,1,20)
    for i in range(8): x,y,z=4+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(15,12),(25,14),(34,14)): c.set(x,10,z,TRIM); c.light(x,9,z)
    c.chest(15,1,18); c.chest(34,1,28); c.set(22,11,38,TRIM); c.chest(22,12,38)
    for y in range(17,27): c.set(20,y,25,WALL); c.set(20,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((39,2,23),"east","core_facade","facade"); c.parent((19,2,47),"south","core_annex","annex"); c.parent((20,27,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(39,2,23),(19,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(36,24,48)); a.fill((0,0,0),(35,0,47),TRIM)
    for i in range(6):
        x=2+i*6; z=4+(i%3)*8; h=11+(i%4)*3; a.fill((x,1,z),(min(35,x+9),h,min(47,z+24)),WALL); a.fill((x+2,4,z),(min(35,x+7),h-3,z),GLASS)
    a.child((0,2,23),"west","core_facade"); a.set(0,3,23,AIR); a.set(0,4,23,AIR)
    r=Module(spec,"roof",(40,20,48)); r.fill((2,0,2),(37,1,45),TRIM); r.child((20,0,24),"down","core_roof",LADDER_STATE); r.set(20,1,24,LADDER); r.set(20,2,24,LADDER); r.set(20,1,25,WALL)
    for x in range(3,38,4): r.fill((x,2,5),(x+1,15,42),GIRDER)
    r.fill((5,8,8),(34,10,39),WALL); r.fill((8,9,11),(31,10,36),AIR); r.set(20,16,24,GIRDER); r.light(20,17,24)
    n=Module(spec,"annex",(40,20,40)); n.fill((0,0,0),(39,0,39),TRIM); n.fill((2,1,2),(37,13,37),WALL); n.fill((5,2,5),(34,12,34),AIR); n.fill((20,2,5),(20,12,34),WALL); n.fill((20,2,18),(20,4,20),AIR); n.door(20,2,19); n.fill((6,2,30),(33,3,32),SHAFT_X); n.child((19,2,0),"north","core_annex"); n.set(19,3,0,AIR); n.set(19,4,0,AIR)
    s=Module(spec,"surrounding",(48,12,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,15),(47,1,32),TRIM); s.fill((4,1,5),(14,7,14),WALL); s.fill((32,1,34),(44,8,43),WALL); s.child((47,9,23),"east","foundation_surrounding"); s.set(47,10,23,AIR); s.set(47,11,23,AIR)
    return LandmarkBuild(spec,(f,c,a,r,n,s),(172,55,88))


def build_all(): return build_frontier_lab(), build_platform_outpost()
