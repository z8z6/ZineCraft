"""Independent Kjerag builders from Karlan headquarters, street and station backgrounds."""

from .base import AIR, BARS, CASING, CHAIN, FENCE, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, RAIL, SHAFT_X, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec

def _commission_kjerag(f,c,a,r,n,s,*,doors,chests,ladder,core_targets,core_lamps,annex_entry_x,annex_door,interfaces,lamps):
    """Cut sheltered Kjerag pilgrim galleries and independently supported shrine lights."""
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
            if state in {GROUND,WALL,TRIM} and (x+4*y)%7==2 and (z+2*y)%7==5 and y+2<module.size[1] \
                    and module.blocks.get((x,y+1,z),AIR)==AIR and module.blocks.get((x,y+2,z),AIR)==AIR:
                module.light(x,y,z)


def build_karlan_monastery() -> LandmarkBuild:
    spec=Spec("kjerag_karlan_monastery","kjerag","mountain monastery and trade waypoint","45_g11_karlanheadquarters.png",
              ("monumental fan-braced portal", "deep stair ascent", "layered stone and metal enclosure", "high vertical trade hall"),"XL 168x88x136")
    f=Module(spec,"foundation",(48,16,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for step in range(10): f.fill((step,1+step,step),(47-step,1+step,47-step),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,44,48)); c.fill((0,0,0),(47,0,47),WALL)
    c.fill((3,1,4),(44,36,43),WALL); c.fill((7,2,8),(40,35,39),AIR)
    # V-shaped braces converge on a tall central door as in the HQ hall.
    for x in range(2,46,4):
        for y in range(2,40):
            z=5+abs(24-x)//2+y//5
            if z<44: c.set(x,y,z,GIRDER)
    c.fill((19,1,6),(28,34,14),WALL); c.fill((22,1,5),(25,24,14),AIR)
    c.fill((5,1,23),(20,10,23),WALL); c.fill((11,1,23),(13,3,23),AIR); c.door(12,1,23); c.fill((28,1,23),(43,10,23),WALL); c.fill((34,1,23),(36,3,23),AIR); c.door(35,1,23)
    for i in range(10): x,y,z=6+i,1+i,10; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((7,13),(19,13),(29,13),(41,13)): c.set(x,11,z,TRIM); c.light(x,10,z)
    c.chest(19,1,39); c.chest(29,1,39); c.set(38,16,35,TRIM); c.chest(38,17,35)
    for y in range(33,43): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,43,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,44,48)); a.fill((0,0,0),(47,0,47),TRIM); a.fill((3,1,4),(44,38,43),WALL); a.fill((7,2,8),(40,37,39),AIR)
    for x in range(2,46,4):
        for y in range(2,42):
            z=3+abs(24-x)//2+y//5
            if z<45: a.set(x,y,z,GIRDER)
    a.fill((20,1,4),(27,34,14),WALL); a.fill((22,1,3),(25,25,14),AIR)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,40,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for x in range(0,48): r.fill((x,3+abs(24-x)//2,2),(x,5+abs(24-x)//2,45),TRIM)
    for x in (6,14,33,41): r.fill((x,2,5),(x+1,34,42),GIRDER)
    r.set(24,35,24,GIRDER); r.light(24,36,24)
    n=Module(spec,"annex",(44,24,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,17,37),WALL); n.fill((5,2,5),(38,16,34),AIR); n.fill((22,2,5),(22,16,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((6,2,30),(37,3,32),SHAFT_X); n.set(12,2,29,CASING); n.set(32,2,29,CASING); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,20,48)); s.fill((0,0,0),(47,0,47),GROUND)
    for step in range(10): s.fill((step,1+step,step),(47-step,1+step,47-step),TRIM)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_kjerag(f,c,a,r,n,s, doors=((c,12,1,23),(c,35,1,23),(n,22,2,19)), chests=((19,1,39),(29,1,39),(38,17,35)), ladder=(24,42,24),
        core_targets=((0,23),(23,1),(23,46),(11,22),(11,24),(34,22),(34,24),(18,39),(28,39),(37,35),(5,10)), core_lamps=((3,23),(11,24),(22,24),(33,24),(44,23),(23,43)), annex_entry_x=21,annex_door=(22,19),
        interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,23,2,0),(c,23,2,47),(a,23,2,47),(n,21,2,0),(s,47,2,23)),
        lamps=((c,(7,3,12),(7,3,11)),(c,(15,12,12),(15,12,11)),(c,(22,1,1),(22,2,1)),(a,(22,1,46),(22,2,46)),(a,(25,4,45),(24,4,45)),(n,(23,4,3),(22,4,3)),(n,(23,4,18),(22,4,18)),(s,(45,4,25),(45,4,24)),(r,(25,3,25),(25,3,24))))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,84,136))


def build_sacred_plaza() -> LandmarkBuild:
    spec=Spec("kjerag_sacred_plaza","kjerag","terraced mountain public plaza","45_g1_kjeragtrainstation.png",
              ("switchback mountain terraces", "rail/cable arrival axis", "stone retaining walls", "small route shelters instead of one monumental hall"),"XL 176x56x160")
    f=Module(spec,"foundation",(48,20,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for step in range(10): f.fill((step*2,1+step,step),(47-step,1+step,47-step*2),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,28,48)); c.fill((0,0,0),(47,0,47),WALL)
    for step in range(9): c.fill((step*2,1+step*2,step),(47-step,2+step*2,47-step*2),TRIM)
    for x0,z0,x1,z1,h in ((4,5,17,16,10),(27,8,43,19,14),(8,31,23,43,12),(31,29,44,42,9)):
        c.fill((x0,1,z0),(x1,h,z1),WALL); c.fill((x0+2,2,z0+2),(x1-2,h-2,z1-2),AIR)
    c.fill((4,1,23),(19,9,23),WALL); c.fill((10,1,23),(12,3,23),AIR); c.door(11,1,23); c.fill((29,1,23),(44,9,23),WALL); c.fill((35,1,23),(37,3,23),AIR); c.door(36,1,23)
    for i in range(9): x,y,z=6+i,1+i,10; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((7,13),(19,13),(29,13),(41,13)): c.set(x,10,z,TRIM); c.light(x,9,z)
    c.chest(18,1,40); c.chest(30,1,40); c.set(39,10,36,TRIM); c.chest(39,11,36)
    for y in range(17,27): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,27,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,28,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for step in range(9): a.fill((step*2,1+step*2,step),(47-step,2+step*2,47-step*2),WALL)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,24,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for x,z in ((6,7),(19,31),(33,9),(39,34)): r.fill((x,2,z),(min(47,x+7),12,min(47,z+6)),WALL)
    for x in (8,23,38): r.fill((x,2,3),(x+1,20,44),GIRDER)
    r.set(24,21,24,GIRDER); r.light(24,22,24)
    n=Module(spec,"annex",(44,20,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,13,37),WALL); n.fill((5,2,5),(38,12,34),AIR); n.fill((22,2,5),(22,12,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((6,2,30),(37,3,32),SHAFT_X); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,24,48)); s.fill((0,0,0),(47,0,47),GROUND)
    for step in range(11): s.fill((step*2,1+step,step),(47-step,1+step,47-step*2),TRIM)
    for z in (19,25,31): s.fill((0,2,z),(47,2,z),RAIL)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_kjerag(f,c,a,r,n,s, doors=((c,11,1,23),(c,36,1,23),(n,22,2,19)), chests=((18,1,40),(30,1,40),(39,11,36)), ladder=(24,26,24),
        core_targets=((0,23),(23,1),(23,46),(10,22),(10,24),(35,22),(35,24),(17,40),(29,40),(38,36),(5,10)), core_lamps=((3,23),(11,24),(22,24),(33,24),(44,23),(23,43)), annex_entry_x=21,annex_door=(22,19),
        interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,23,2,0),(c,23,2,47),(a,23,2,47),(n,21,2,0),(s,47,2,23)),
        lamps=((c,(7,3,12),(7,3,11)),(c,(14,11,12),(14,11,11)),(c,(22,1,1),(22,2,1)),(a,(22,1,46),(22,2,46)),(a,(25,4,45),(24,4,45)),(n,(23,4,3),(22,4,3)),(n,(23,4,18),(22,4,18)),(s,(45,4,25),(45,4,24)),(r,(25,3,25),(25,3,24))))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,52,136))


def build_all(): return build_karlan_monastery(), build_sacred_plaza()
