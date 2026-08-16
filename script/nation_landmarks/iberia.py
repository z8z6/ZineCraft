"""Independent Iberia builders from town, church, coast and tower backgrounds."""

from .base import AIR, BARS, CASING, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, SHAFT_X, STAIR_E, TRIM, WATER, WALL, LandmarkBuild, Module, Spec

def _commission_iberia(f,c,a,r,n,s,*,doors,chests,ladder,core_targets,core_lamps,annex_entry_x,annex_door,interfaces,lamps):
    """Cut salt-safe Iberia watch passages and independently supported coastal lights."""
    for module,x,y,z in doors:
        for side in (z-1,z+1): module.set(x,y-1,side,TRIM); module.fill((x,y,side),(x,y+1,side),AIR)
    for x,y,z in chests:
        c.set(x,y+1,z,AIR); c.set(x-1,y-1,z,TRIM); c.fill((x-1,y,z),(x-1,y+1,z),AIR)
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    lx,top,lz=ladder; hub=(lx-1,lz)
    for level in range(1,top+1): c.set(lx,level,lz+1,WALL); c.set(lx,level,lz,LADDER)
    for tx,tz in core_targets:
        for x in range(min(hub[0],tx),max(hub[0],tx)+1): c.set(x,0,hub[1],TRIM); c.fill((x,1,hub[1]),(x,2,hub[1]),AIR)
        for z in range(min(hub[1],tz),max(hub[1],tz)+1): c.set(tx,0,z,TRIM); c.fill((tx,1,z),(tx,2,z),AIR)
    for x in range(48): f.set(x,1,23,TRIM); f.fill((x,2,23),(x,3,23),AIR)
    nx,nz=annex_door
    for z in range(0,nz+2): n.set(annex_entry_x,1,z,TRIM); n.fill((annex_entry_x,2,z),(annex_entry_x,3,z),AIR)
    n.set(nx,1,nz-1,TRIM); n.fill((nx,2,nz-1),(nx,3,nz-1),AIR)
    for module,x,y,z in interfaces: module.set(x,y-1,z,STAIR_E); module.fill((x,y,z),(x,y+1,z),AIR)
    for level in range(1,top+1): c.set(lx,level,lz+1,WALL); c.set(lx,level,lz,LADDER)
    for x,y,z in chests:
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    for level in range(1,6): r.set(lx,level,lz+1,WALL); r.set(lx,level,lz,LADDER)
    r.set(lx,6,lz,AIR); r.set(lx-1,4,lz,TRIM); r.fill((lx-1,5,lz),(lx-1,6,lz),AIR)
    for x in (4,14,24,34,44): f.set(x,5,23,TRIM); f.light(x,4,23)
    for x,z in core_lamps: c.set(x,4,z,TRIM); c.light(x,3,z)
    for level in range(7,top,10): c.set(lx+1,level,lz+1,TRIM); c.light(lx+1,level,lz)
    for module,support,light in lamps: module.set(*support,TRIM); module.light(*light)
    for x,y,z in chests: c.set(x-2,y-1,z,TRIM); c.light(x-2,y,z)
    for module in (f,c,a,r,n,s):
        for (x,y,z),state in tuple(module.blocks.items()):
            if state in {GROUND,WALL,TRIM} and (x+5*y)%7==6 and (z+2*y)%7==1 and y+2<module.size[1] \
                    and module.blocks.get((x,y+1,z),AIR)==AIR and module.blocks.get((x,y+2,z),AIR)==AIR:
                module.light(x,y,z)


def build_eye_lighthouse() -> LandmarkBuild:
    spec=Spec("iberia_eye_lighthouse","iberia","coastal navigation and warning tower","bg_ibtownd.png",
              ("extremely tall salt-eroded tower", "four massive flying buttresses", "narrow beacon chamber", "broken harbour jetty"),"XL 160x112x128")
    f=Module(spec,"foundation",(48,12,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,17),(47,2,30),TRIM); f.fill((3,1,3),(44,1,12),WATER)
    for x,z in ((5,15),(42,15),(5,34),(42,34)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(44,48,44)); c.fill((0,0,0),(43,0,43),WALL)
    for y in range(1,46):
        radius=max(4,15-y//4); c.fill((22-radius,y,22-radius),(21+radius,y,21+radius),WALL if y%6 else TRIM)
        c.fill((22-radius+3,y,22-radius+3),(18+radius,y,18+radius),AIR)
    for x,z in ((3,3),(38,3),(3,38),(38,38)):
        for y in range(1,34):
            tx=22+(x-22)*y//34; tz=22+(z-22)*y//34; c.fill((min(x,tx),y,min(z,tz)),(min(43,max(x,tx)+1),y,min(43,max(z,tz)+1)),TRIM)
    c.fill((5,1,21),(18,10,21),WALL); c.fill((10,1,21),(12,3,21),AIR); c.door(11,1,21); c.fill((26,1,21),(39,10,21),WALL); c.fill((31,1,21),(33,3,21),AIR); c.door(32,1,21)
    for i in range(10): x,y,z=5+i,1+i,9; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(17,12),(27,12),(38,12)): c.set(x,11,z,TRIM); c.light(x,10,z)
    c.chest(17,1,36); c.chest(27,1,36); c.set(34,17,32,TRIM); c.chest(34,18,32)
    for y in range(37,47): c.set(22,y,23,WALL); c.set(22,y,22,LADDER)
    c.child((0,2,21),"west","foundation_core"); c.parent((43,2,21),"east","core_facade","facade"); c.parent((21,2,43),"south","core_annex","annex"); c.parent((22,47,22),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,21),(43,2,21),(21,2,43)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,48,44)); a.fill((0,0,0),(39,0,43),TRIM)
    for y in range(1,46):
        radius=max(3,14-y//4); a.fill((20-radius,y,5),(19+radius,y,38),WALL if y%5 else TRIM)
        if radius>5: a.fill((20-radius+3,y,8),(16+radius,y,35),AIR)
    for y in range(8,40,8): a.fill((17,y,4),(22,y+5,4),GLASS); a.fill((15,y+5,4),(24,y+6,6),TRIM)
    a.child((0,2,21),"west","core_facade"); a.set(0,3,21,AIR); a.set(0,4,21,AIR)
    r=Module(spec,"roof",(44,48,44)); r.fill((2,0,2),(41,1,41),TRIM); r.child((22,0,22),"down","core_roof",LADDER_STATE); r.set(22,1,22,LADDER); r.set(22,2,22,LADDER); r.set(22,1,23,WALL)
    for y in range(2,38):
        radius=max(3,10-y//5); r.fill((22-radius,y,22-radius),(21+radius,y,21+radius),WALL if y%5 else TRIM); r.fill((22-radius+2,y,22-radius+2),(19+radius,y,19+radius),AIR)
    r.fill((10,25,10),(33,33,33),GLASS); r.fill((14,27,14),(29,31,29),AIR); r.set(22,34,22,GIRDER); r.light(22,35,22)
    n=Module(spec,"annex",(44,24,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,17,37),WALL); n.fill((5,2,5),(38,16,34),AIR); n.fill((22,2,5),(22,16,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((6,2,30),(37,3,32),SHAFT_X); n.set(12,2,29,CASING); n.set(32,2,29,CASING); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,16,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,17),(47,1,30),TRIM); s.fill((3,1,3),(44,1,13),WATER)
    for i in range(7): s.fill((i*7,2,11),(min(47,i*7+5),3,18),TRIM)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_iberia(f,c,a,r,n,s, doors=((c,11,1,21),(c,32,1,21),(n,22,2,19)), chests=((17,1,36),(27,1,36),(34,18,32)), ladder=(22,46,22),
        core_targets=((0,21),(43,21),(21,43),(10,20),(10,22),(31,20),(31,22),(16,36),(26,36),(33,32),(4,9)), core_lamps=((3,21),(10,22),(20,22),(30,22),(40,21),(21,39)), annex_entry_x=21,annex_door=(22,19),
        interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,21),(c,43,2,21),(c,21,2,43),(a,0,2,21),(n,21,2,0),(s,47,2,23)),
        lamps=((c,(6,3,11),(6,3,10)),(c,(14,12,11),(14,12,10)),(a,(2,4,23),(2,4,22)),(n,(23,4,3),(22,4,3)),(n,(23,4,18),(22,4,18)),(s,(45,4,25),(45,4,24)),(r,(23,3,23),(23,3,22))))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(180,96,86))


def build_saltwind_chapel() -> LandmarkBuild:
    spec=Spec("iberia_saltwind_chapel","iberia","salt-wind refuge and service hall","57_g13_ibtown_d.png",
              ("low salt-white refuge mass", "uneven stepped roofline", "eroded blind arches", "drainage court and broken seaward wall"),"L 152x48x136")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,16),(47,1,31),TRIM); f.fill((4,1,4),(43,1,11),WATER)
    for x,z in ((5,14),(42,14),(5,33),(42,33)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,28,48)); c.fill((0,0,0),(47,0,47),WALL)
    for x0,x1,z0,z1,h in ((2,18,5,42,17),(16,34,8,40,23),(32,45,4,36,14)):
        c.fill((x0,1,z0),(x1,h,z1),WALL); c.fill((x0+3,2,z0+3),(x1-3,h-2,z1-3),AIR)
    for x in (5,11,18,25,33,40):
        h=10+(x%4)*2; c.fill((x,4,3),(x+1,h,3),GLASS); c.fill((x-1,h,3),(x+2,h+2,5),TRIM)
    # Salt erosion creates missing corners and blind, damaged bays.
    for y in range(8,18): c.fill((max(0,45-y//3),y,30),(47,y,44),AIR)
    c.fill((4,1,23),(19,10,23),WALL); c.fill((10,1,23),(12,3,23),AIR); c.door(11,1,23); c.fill((29,1,23),(44,10,23),WALL); c.fill((35,1,23),(37,3,23),AIR); c.door(36,1,23)
    for i in range(9): x,y,z=6+i,1+i,10; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((7,13),(19,13),(29,13),(41,13)): c.set(x,11,z,TRIM); c.light(x,10,z)
    c.chest(18,1,40); c.chest(30,1,40); c.set(38,10,35,TRIM); c.chest(38,11,35)
    for y in range(17,27): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((47,2,23),"east","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,27,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,28,48)); a.fill((0,0,0),(39,0,47),TRIM)
    for x0,x1,z0,z1,h in ((1,14,5,42,16),(13,29,9,40,23),(27,39,4,35,13)):
        a.fill((x0,1,z0),(x1,h,z1),WALL); a.fill((x0+3,2,z0+3),(x1-3,h-2,z1-3),AIR)
    for y in range(8,18): a.fill((max(0,37-y//3),y,30),(39,y,43),AIR)
    a.child((0,2,23),"west","core_facade"); a.set(0,3,23,AIR); a.set(0,4,23,AIR)
    r=Module(spec,"roof",(48,24,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for z in range(2,46):
        h=3+abs(24-z)//5; r.fill((2,h,z),(20,h+1,z),WALL); r.fill((18,h+4,z),(34,h+5,z),WALL); r.fill((32,h-1,z),(45,h,z),WALL)
    r.set(24,16,24,GIRDER); r.light(24,17,24)
    n=Module(spec,"annex",(44,20,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,13,37),WALL); n.fill((5,2,5),(38,12,34),AIR); n.fill((22,2,5),(22,12,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((6,2,30),(37,3,32),SHAFT_X); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,14,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,15),(47,1,32),TRIM); s.fill((4,1,4),(43,1,12),WATER); s.fill((5,1,35),(18,8,43),WALL); s.fill((31,1,35),(44,7,43),WALL); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_iberia(f,c,a,r,n,s, doors=((c,11,1,23),(c,36,1,23),(n,22,2,19)), chests=((18,1,40),(30,1,40),(38,11,35)), ladder=(24,26,24),
        core_targets=((0,23),(47,23),(23,47),(10,22),(10,24),(35,22),(35,24),(17,40),(29,40),(37,35),(5,10)), core_lamps=((3,23),(11,24),(22,24),(33,24),(44,23),(23,43)), annex_entry_x=21,annex_door=(22,19),
        interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,47,2,23),(c,23,2,47),(a,0,2,23),(n,21,2,0),(s,47,2,23)),
        lamps=((c,(46,1,22),(46,2,22)),(a,(1,1,22),(1,2,22)),(c,(7,3,12),(7,3,11)),(c,(14,11,12),(14,11,11)),(a,(2,4,25),(2,4,24)),(n,(23,4,3),(22,4,3)),(n,(23,4,18),(22,4,18)),(s,(45,4,25),(45,4,24)),(r,(25,3,25),(25,3,24))))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(184,52,88))


def build_all(): return build_eye_lighthouse(), build_saltwind_chapel()
