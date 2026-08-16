"""Independent Siracusa builders from old-city rain street, court and theatre imagery."""

from .base import AIR, CASING, CHAIN, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, SHAFT_X, SLAB, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec

def _commission_siracusa(f,c,a,r,n,s,*,doors,chests,ladder,core_targets,core_lamps,annex_entry_x,annex_door,interfaces,lamps):
    """Cut guarded Siracusa family passages and independently supported court lights."""
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
            if state in {GROUND,WALL,TRIM} and (x+y)%7==5 and (z+3*y)%7==4 and y+2<module.size[1] \
                    and module.blocks.get((x,y+1,z),AIR)==AIR and module.blocks.get((x,y+2,z),AIR)==AIR:
                module.light(x,y,z)


def build_family_court() -> LandmarkBuild:
    spec=Spec("siracusa_family_court","siracusa","city-state united court","33_g1_srcstreet.png",
              ("deep old mansion block", "new public court wing grafted to one side", "arcaded rain court", "visible blast/remodelling notch"),"XL 168x64x144")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,17),(47,1,30),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,36,48)); c.fill((0,0,0),(47,0,47),WALL)
    c.fill((2,1,4),(29,27,44),WALL); c.fill((5,2,7),(26,26,41),AIR)
    c.fill((30,1,9),(45,33,39),WALL); c.fill((33,2,12),(42,32,36),AIR)
    c.fill((10,1,15),(20,16,33),AIR)  # rain court void
    for x in (5,11,18,25): c.fill((x,1,3),(x+1,24,7),TRIM); c.fill((x,5,2),(x+1,16,2),GLASS)
    # Blast/remodelling scar removes an irregular corner from the old block.
    for y in range(8,25): c.fill((max(2,22-y//4),y,37),(29,y,44),AIR)
    c.fill((4,1,23),(19,10,23),WALL); c.fill((10,1,23),(12,3,23),AIR); c.door(11,1,23); c.fill((31,1,23),(44,10,23),WALL); c.fill((36,1,23),(38,3,23),AIR); c.door(37,1,23)
    for i in range(10): x,y,z=6+i,1+i,9; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((7,12),(18,12),(31,13),(42,13)): c.set(x,11,z,TRIM); c.light(x,10,z)
    c.fill((6,1,32),(18,1,35),SLAB); c.fill((32,1,31),(42,1,34),SLAB)
    c.chest(18,1,40); c.chest(32,1,40); c.set(39,14,35,TRIM); c.chest(39,15,35)
    for y in range(25,35): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,35,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,36,48)); a.fill((0,0,0),(47,0,47),TRIM); a.fill((2,1,4),(29,27,43),WALL); a.fill((32,1,10),(45,33,38),WALL)
    for x in (5,11,18,25,34,40): a.fill((x,4,3),(x+1,18,3),GLASS); a.fill((x-1,18,3),(x+2,20,5),TRIM)
    for y in range(9,26): a.fill((max(2,22-y//4),y,35),(29,y,43),AIR)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,28,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for z in range(3,45): r.fill((3,4+abs(24-z)//5,z),(29,6+abs(24-z)//5,z),WALL)
    r.fill((32,3,10),(44,23,37),WALL); r.fill((35,5,13),(41,21,34),AIR); r.set(24,24,24,GIRDER); r.light(24,25,24)
    n=Module(spec,"annex",(44,24,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,17,37),WALL); n.fill((5,2,5),(38,16,34),AIR); n.fill((22,2,5),(22,16,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((6,2,30),(37,3,32),SHAFT_X); n.set(12,2,29,CASING); n.set(32,2,29,CASING); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,14,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,16),(47,1,31),TRIM); s.fill((5,1,5),(18,10,13),WALL); s.fill((31,1,35),(44,10,43),WALL); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_siracusa(f,c,a,r,n,s, doors=((c,11,1,23),(c,37,1,23),(n,22,2,19)), chests=((18,1,40),(32,1,40),(39,15,35)), ladder=(24,34,24),
        core_targets=((0,23),(23,1),(23,46),(10,22),(10,24),(36,22),(36,24),(17,40),(31,40),(38,35),(5,9)), core_lamps=((3,23),(11,24),(22,24),(33,24),(44,23),(23,43)), annex_entry_x=21,annex_door=(22,19),
        interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,23,2,0),(c,23,2,47),(a,23,2,47),(n,21,2,0),(s,47,2,23)),
        lamps=((c,(7,3,11),(7,3,10)),(c,(15,12,11),(15,12,10)),(c,(22,1,1),(22,2,1)),(a,(22,1,46),(22,2,46)),(a,(25,4,45),(24,4,45)),(n,(23,4,3),(22,4,3)),(n,(23,4,18),(22,4,18)),(s,(45,4,25),(45,4,24)),(r,(25,3,25),(25,3,24))))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,64,136))


def build_public_theatre() -> LandmarkBuild:
    spec=Spec("siracusa_family_theatre","siracusa","city-state public theatre","33_g1_srcstreet.png",
              ("tall fly tower", "deep rounded auditorium shell", "rain-canopy foyer", "rear scenery loading court"),"XL 176x72x152")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,15),(47,1,32),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,40,48)); c.fill((0,0,0),(47,0,47),WALL)
    # Stepped auditorium wraps a central performance void; fly tower rises behind.
    for step in range(9):
        c.fill((step+3,1+step*2,step+5),(44-step,2+step*2,18+step),TRIM)
        c.fill((step+3,1+step*2,29-step),(44-step,2+step*2,42-step),TRIM)
    c.fill((14,1,15),(33,29,38),WALL); c.fill((17,2,18),(30,28,35),AIR)
    c.fill((18,20,8),(29,38,20),WALL); c.fill((21,22,11),(26,36,18),AIR)
    c.fill((4,1,23),(19,10,23),WALL); c.fill((10,1,23),(12,3,23),AIR); c.door(11,1,23); c.fill((29,1,23),(44,10,23),WALL); c.fill((35,1,23),(37,3,23),AIR); c.door(36,1,23)
    for i in range(10): x,y,z=6+i,1+i,9; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((7,12),(19,12),(29,12),(41,12)): c.set(x,19,z,TRIM); c.light(x,18,z)
    c.chest(18,1,40); c.chest(30,1,40); c.set(38,18,35,TRIM); c.chest(38,19,35)
    for y in range(29,39): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,39,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,40,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for step in range(9): a.fill((step+2,1+step*2,step+4),(45-step,2+step*2,43-step),WALL)
    a.fill((18,19,7),(29,38,25),WALL); a.fill((21,21,10),(26,36,22),AIR)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,36,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for step in range(8): r.fill((step+3,2+step*2,step+4),(44-step,3+step*2,43-step),WALL)
    r.fill((18,17,8),(29,33,25),WALL); r.fill((21,19,11),(26,31,22),AIR); r.set(24,34,24,GIRDER); r.light(24,35,24)
    n=Module(spec,"annex",(48,28,44)); n.fill((0,0,0),(47,0,43),TRIM); n.fill((2,1,2),(45,20,41),WALL); n.fill((5,2,5),(42,19,38),AIR); n.fill((24,2,5),(24,19,38),WALL); n.fill((24,2,20),(24,4,22),AIR); n.door(24,2,21); n.fill((8,17,9),(39,19,34),GIRDER)
    for x in range(10,39,6): n.fill((x,4,10),(x,16,10),CHAIN)
    n.fill((6,3,34),(41,3,36),SHAFT_X); n.child((23,2,0),"north","core_annex"); n.set(23,3,0,AIR); n.set(23,4,0,AIR)
    s=Module(spec,"surrounding",(48,16,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,14),(47,1,33),TRIM); s.fill((5,1,5),(17,9,13),WALL); s.fill((31,1,35),(44,10,43),WALL); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_siracusa(f,c,a,r,n,s, doors=((c,11,1,23),(c,36,1,23),(n,24,2,21)), chests=((18,1,40),(30,1,40),(38,19,35)), ladder=(24,38,24),
        core_targets=((0,23),(23,1),(23,46),(10,22),(10,24),(35,22),(35,24),(17,40),(29,40),(37,35),(5,9)), core_lamps=((3,23),(11,24),(22,24),(33,24),(44,23),(23,43)), annex_entry_x=23,annex_door=(24,21),
        interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,23,2,0),(c,23,2,47),(a,23,2,47),(n,23,2,0),(s,47,2,23)),
        lamps=((c,(7,3,11),(7,3,10)),(c,(15,12,11),(15,12,10)),(c,(22,1,1),(22,2,1)),(a,(22,1,46),(22,2,46)),(a,(25,4,45),(24,4,45)),(n,(25,4,3),(24,4,3)),(n,(25,4,20),(24,4,20)),(s,(45,4,25),(45,4,24)),(r,(25,3,25),(25,3,24))))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,76,140))


def build_all(): return build_family_court(), build_public_theatre()
