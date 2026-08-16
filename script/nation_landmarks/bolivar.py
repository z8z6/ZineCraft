"""Independent Bolivar builders; Dossoles imagery is not generalized to the war plain."""

from .base import AIR, BARREL, CASING, FENCE, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, LEAVES, SHAFT_X, SLAB, STAIR_E, TRIM, WATER, WALL, LandmarkBuild, Module, Spec


def _bolivar_floor_lights(modules) -> None:
    for module in modules:
        for (x,y,z),state in tuple(module.blocks.items()):
            if state in {GROUND,WALL,TRIM} and (x+4*y)%7==2 and (z+y)%7==5 and y+2<module.size[1] \
                    and module.blocks.get((x,y+1,z),AIR)==AIR and module.blocks.get((x,y+2,z),AIR)==AIR:
                module.light(x,y,z)


def build_dossoles_gallery() -> LandmarkBuild:
    spec=Spec("bolivar_dossoles_yacht","bolivar","Dossoles waterfront gallery","48_g2_galleriessquare.png",
              ("faceted crystalline crown", "black-white cantilever decks", "tension cables sweeping to the plaza", "very low landscape podium"),"XL 184x80x144")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,17),(47,1,30),TRIM)
    f.fill((4,1,4),(43,1,12),WATER); f.fill((5,1,35),(42,1,43),WATER)
    for x,z in ((5,15),(42,15),(5,32),(42,32)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,36,48)); c.fill((0,0,0),(47,0,47),TRIM)
    # Offset decks slide beyond a narrow core; no enclosing rectangular tower.
    c.fill((18,1,15),(29,18,32),WALL); c.fill((20,2,17),(27,17,30),AIR)
    for y,x0,x1,z0,z1 in ((8,4,43,10,37),(14,0,39,14,42),(20,10,47,6,33)):
        c.fill((x0,y,z0),(x1,y+2,z1),TRIM); c.fill((x0+3,y+1,z0+3),(x1-3,y+2,z1-3),AIR)
    # Crystalline top is made from intersecting wedges with large voids.
    for i in range(8):
        x=6+i*5; h=10+(i*7%14); c.fill((x,21,12+i%3),(min(47,x+3),min(35,21+h),16+i*3),GLASS); c.fill((x,21,15+i*3),(min(47,x+5),23,18+i*3),GIRDER)
    c.fill((18,1,23),(29,8,23),WALL); c.fill((22,1,23),(24,3,23),AIR); c.door(23,1,23)
    c.fill((20,9,23),(31,14,23),WALL); c.fill((24,9,23),(26,11,23),AIR); c.door(25,9,23)
    for i in range(8): x,y,z=19+i,1+i,17; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((8,13),(20,13),(32,13),(42,13)): c.set(x,19,z,TRIM); c.light(x,18,z)
    c.chest(21,1,29); c.chest(27,1,29); c.set(35,13,30,TRIM); c.chest(35,14,30)
    for y in range(24,35): c.set(23,y,25,WALL); c.set(23,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((47,9,23),"east","core_facade","facade"); c.parent((23,9,47),"south","core_annex","annex"); c.parent((23,35,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,9,23),(23,9,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,36,48)); a.fill((0,0,0),(39,0,47),TRIM)
    for i in range(10):
        x=i*4; y=3+i%4; a.fill((x,y,5),(min(39,x+8),y+2,42),WALL if i%2 else GLASS)
    for x in (4,12,20,28,36): a.fill((x,1,4),(x,28,44),GIRDER)
    a.child((0,9,23),"west","core_facade"); a.set(0,10,23,AIR); a.set(0,11,23,AIR)
    r=Module(spec,"roof",(48,40,48)); r.fill((12,0,12),(35,1,35),TRIM); r.child((23,0,24),"down","core_roof",LADDER_STATE); r.set(23,1,24,LADDER); r.set(23,2,24,LADDER); r.set(23,1,25,WALL)
    for i in range(9):
        x=4+i*5; height=12+(i*11%24); r.fill((x,2,8+i%5),(min(47,x+4),height,12+i*4),GLASS); r.fill((x,2,12+i*4),(min(47,x+7),4,15+i*4),GIRDER)
    r.set(23,36,24,GIRDER); r.light(23,37,24)
    n=Module(spec,"annex",(48,24,40)); n.fill((0,0,0),(47,0,39),TRIM); n.fill((2,1,2),(45,16,37),WALL); n.fill((5,2,5),(42,15,34),AIR); n.fill((26,2,5),(26,15,34),WALL); n.fill((26,2,18),(26,4,20),AIR); n.door(26,2,19); n.fill((6,3,30),(40,3,32),SHAFT_X); n.set(12,3,29,CASING); n.set(34,3,29,CASING); n.child((23,9,0),"north","core_annex"); n.set(23,10,0,AIR); n.set(23,11,0,AIR)
    s=Module(spec,"surrounding",(48,16,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,17),(47,1,30),TRIM); s.fill((3,1,3),(44,1,12),WATER); s.fill((3,1,35),(44,1,44),WATER)
    for x,z in ((7,15),(15,33),(33,15),(41,33)): s.set(x,2,z,LEAVES)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    # Dossoles uses open promenade ramps plus two discrete lift cages for the raised gallery.
    for module,x,y,z in ((c,23,1,23),(c,25,9,23),(n,26,2,19)):
        for side in (z-1,z+1): module.set(x,y-1,side,TRIM); module.fill((x,y,side),(x,y+1,side),AIR)
    for x,y,z in ((21,1,29),(27,1,29),(35,14,30)):
        c.set(x,y+1,z,AIR); c.set(x-1,y-1,z,TRIM); c.fill((x-1,y,z),(x-1,y+1,z),AIR)
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    for level in range(1,35): c.set(23,level,25,WALL); c.set(23,level,24,LADDER)
    for level in range(1,10): c.set(46,level,23,LADDER); c.set(23,level,46,LADDER); n.set(23,level,1,LADDER)
    for level in range(1,10): c.set(25,level,22,LADDER)
    for x in range(48): f.set(x,1,23,TRIM); f.fill((x,2,23),(x,3,23),AIR)
    for tx,tz in ((0,23),(46,23),(23,46),(22,23),(25,22),(20,29),(26,29),(34,30),(18,17)):
        for x in range(min(22,tx),max(22,tx)+1): c.set(x,0,24,TRIM); c.fill((x,1,24),(x,2,24),AIR)
        for z in range(min(24,tz),max(24,tz)+1): c.set(tx,0,z,TRIM); c.fill((tx,1,z),(tx,2,z),AIR)
    for z in range(1,21): n.set(23,1,z,TRIM); n.fill((23,2,z),(23,3,z),AIR)
    for module,x,y,z in ((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,47,9,23),(c,23,9,47),(a,0,9,23),(n,23,9,0),(s,47,2,23)):
        module.set(x,y-1,z,STAIR_E); module.fill((x,y,z),(x,y+1,z),AIR)
    for level in range(1,35): c.set(23,level,25,WALL); c.set(23,level,24,LADDER)
    for level in range(1,10): c.set(46,level,23,LADDER); c.set(23,level,46,LADDER); n.set(23,level,1,LADDER); c.set(25,level,22,LADDER)
    for level in range(1,6): r.set(23,level,25,WALL); r.set(23,level,24,LADDER)
    r.set(23,6,24,AIR); r.set(22,4,24,TRIM); r.fill((22,5,24),(22,6,24),AIR)
    for x in (4,14,24,34,44): f.set(x,5,23,TRIM); f.light(x,4,23)
    for x,z in ((3,23),(12,24),(22,24),(32,24),(43,23),(23,43),(21,29),(27,29)):
        c.set(x,4,z,TRIM); c.light(x,3,z)
    for level in (8,18,28): c.set(24,level,25,TRIM); c.light(24,level,24)
    c.set(33,13,30,TRIM); c.light(33,14,30)
    a.set(2,11,25,WALL); a.light(2,10,25); n.set(24,5,3,WALL); n.light(24,4,3)
    n.set(24,5,18,WALL); n.light(24,4,18)
    s.set(45,4,25,WALL); s.light(45,4,24); r.set(24,3,25,WALL); r.light(24,3,24)
    _bolivar_floor_lights((f,c,a,r,n,s))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(184,76,88))


def build_race_checkpoint() -> LandmarkBuild:
    spec=Spec("bolivar_race_checkpoint","bolivar","war-plain relief logistics hub","48_g1_dossolesstreet_n.png",
              ("low repairable sheds", "diagonal road-spanning inspection canopy", "protected clinic core", "freight lanes and blast gaps"),"L 152x40x120")
    f=Module(spec,"foundation",(48,8,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,16),(47,1,31),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(40,24,48)); c.fill((0,0,0),(39,0,47),WALL)
    c.fill((2,1,4),(17,14,43),WALL); c.fill((5,2,7),(14,13,40),AIR); c.fill((23,1,8),(37,11,39),WALL); c.fill((26,2,11),(34,10,36),AIR)
    c.fill((0,13,5),(39,16,17),TRIM); c.fill((5,16,9),(39,19,21),GIRDER)
    c.fill((4,1,22),(16,8,22),WALL); c.fill((8,1,22),(10,3,22),AIR); c.door(9,1,22); c.fill((25,1,22),(36,8,22),WALL); c.fill((29,1,22),(31,3,22),AIR); c.door(30,1,22)
    for i in range(8): x,y,z=5+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(14,12),(27,14),(35,14)): c.set(x,10,z,TRIM); c.light(x,9,z)
    c.chest(14,1,38); c.chest(26,1,35); c.set(32,10,32,TRIM); c.chest(32,11,32)
    for y in range(13,23): c.set(12,y,12,WALL); c.set(12,y,11,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((39,2,23),"east","core_facade","facade"); c.parent((19,2,47),"south","core_annex","annex"); c.parent((12,23,11),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(39,2,23),(19,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(36,20,48)); a.fill((0,0,0),(35,0,47),TRIM)
    for i in range(7):
        x=2+i*5; a.fill((x,1,4+i),(min(35,x+7),10+i%4,38+i),WALL); a.fill((x+2,3,3+i),(min(35,x+5),7,3+i),GLASS)
    a.child((0,2,23),"west","core_facade"); a.set(0,3,23,AIR); a.set(0,4,23,AIR)
    r=Module(spec,"roof",(40,16,48)); r.fill((2,0,2),(37,1,45),TRIM); r.child((12,0,11),"down","core_roof",LADDER_STATE); r.set(12,1,11,LADDER); r.set(12,2,11,LADDER); r.set(12,1,12,WALL)
    for x in range(2,38): y=3+abs(20-x)//5; r.fill((x,y,5),(x,y+1,42),GIRDER if x%4==0 else WALL)
    r.set(20,11,23,GIRDER); r.light(20,12,23)
    n=Module(spec,"annex",(40,20,40)); n.fill((0,0,0),(39,0,39),TRIM); n.fill((2,1,2),(37,13,37),WALL); n.fill((5,2,5),(34,12,34),AIR); n.fill((20,2,5),(20,12,34),WALL); n.fill((20,2,18),(20,4,20),AIR); n.door(20,2,19); n.fill((5,2,30),(15,4,32),BARREL); n.child((19,2,0),"north","core_annex"); n.set(19,3,0,AIR); n.set(19,4,0,AIR)
    s=Module(spec,"surrounding",(48,10,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,14),(47,1,33),TRIM); s.fill((8,1,6),(12,5,12),WALL); s.fill((35,1,35),(42,6,41),WALL); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    # The relief checkpoint keeps one low blast-safe circulation spine and a compact watch ladder.
    for module,x,y,z in ((c,9,1,22),(c,30,1,22),(n,20,2,19)):
        for side in (z-1,z+1): module.set(x,y-1,side,TRIM); module.fill((x,y,side),(x,y+1,side),AIR)
    for x,y,z in ((14,1,38),(26,1,35),(32,11,32)):
        c.set(x,y+1,z,AIR); c.set(x-1,y-1,z,TRIM); c.fill((x-1,y,z),(x-1,y+1,z),AIR)
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    for level in range(1,23): c.set(12,level,12,WALL); c.set(12,level,11,LADDER)
    for x in range(48): f.set(x,1,23,TRIM); f.fill((x,2,23),(x,3,23),AIR)
    for tx,tz in ((0,23),(39,23),(19,47),(8,21),(8,23),(29,21),(29,23),(13,38),(25,35),(31,32),(4,8)):
        for x in range(min(11,tx),max(11,tx)+1): c.set(x,0,11,TRIM); c.fill((x,1,11),(x,2,11),AIR)
        for z in range(min(11,tz),max(11,tz)+1): c.set(tx,0,z,TRIM); c.fill((tx,1,z),(tx,2,z),AIR)
    for z in range(0,21): n.set(19,1,z,TRIM); n.fill((19,2,z),(19,3,z),AIR)
    n.set(20,1,18,TRIM); n.fill((20,2,18),(20,3,18),AIR)
    for module,x,z in ((f,47,23),(f,0,23),(c,0,23),(c,39,23),(c,19,47),(a,0,23),(n,19,0),(s,47,23)):
        module.set(x,1,z,STAIR_E); module.fill((x,2,z),(x,3,z),AIR)
    for level in range(1,23): c.set(12,level,12,WALL); c.set(12,level,11,LADDER)
    for level in range(1,6): r.set(12,level,12,WALL); r.set(12,level,11,LADDER)
    r.set(12,6,11,AIR); r.set(11,4,11,TRIM); r.fill((11,5,11),(11,6,11),AIR)
    for x in (4,14,24,34,44): f.set(x,5,23,TRIM); f.light(x,4,23)
    for x,z in ((2,23),(10,11),(20,11),(30,11),(37,23),(19,43),(9,22),(30,22),(14,38),(26,35)):
        c.set(x,4,z,TRIM); c.light(x,3,z)
    for level in (7,15,21): c.set(13,level,12,TRIM); c.light(13,level,11)
    c.set(30,10,32,TRIM); c.light(30,11,32)
    a.set(2,4,25,WALL); a.light(2,4,24); n.set(21,5,2,WALL); n.light(21,4,2)
    n.set(21,5,18,WALL); n.light(21,4,18)
    s.set(45,4,25,WALL); s.light(45,4,24); r.set(13,3,12,WALL); r.light(13,3,11)
    _bolivar_floor_lights((f,c,a,r,n,s))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(172,40,88))


def build_all(): return build_dossoles_gallery(), build_race_checkpoint()
