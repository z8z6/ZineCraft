"""Independent Sami builders from 40_g5_samitribe.png; no invented ritual monument."""

from .base import AIR, BARREL, CHAIN, FENCE, GIRDER, GROUND, LADDER, LADDER_STATE, SLAB, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec

def _commission_sami(f,c,a,r,n,s,*,doors,chests,ladder,core_targets,core_lamps,entry_y,lifts,annex_entry_x,annex_door,interfaces,lamps):
    """Cut Sami portable timber walks and independently supported hearth lights."""
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
    for x in range(f.size[0]): f.set(x,entry_y-1,(f.size[2]-1)//2,TRIM); f.fill((x,entry_y,(f.size[2]-1)//2),(x,entry_y+1,(f.size[2]-1)//2),AIR)
    nx,nz=annex_door
    for z in range(0,nz+2): n.set(annex_entry_x,1,z,TRIM); n.fill((annex_entry_x,2,z),(annex_entry_x,3,z),AIR)
    n.set(nx,1,nz-1,TRIM); n.fill((nx,2,nz-1),(nx,3,nz-1),AIR)
    for module,x,y,z in interfaces: module.set(x,y-1,z,STAIR_E); module.fill((x,y,z),(x,y+1,z),AIR)
    for module,x,z,top in lifts:
        for level in range(1,top+1): module.set(x,level,z,LADDER)
    for level in range(1,top+1): c.set(lx,level,lz+1,WALL); c.set(lx,level,lz,LADDER)
    for x,y,z in chests:
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    for level in range(1,6): r.set(lx,level,lz+1,WALL); r.set(lx,level,lz,LADDER)
    r.set(lx,6,lz,AIR); r.set(lx-1,4,lz,TRIM); r.fill((lx-1,5,lz),(lx-1,6,lz),AIR)
    lamp_y=min(f.size[1]-2,entry_y+2)
    for x in range(4,f.size[0]-2,10): f.set(x,lamp_y+1,(f.size[2]-1)//2,TRIM); f.light(x,lamp_y,(f.size[2]-1)//2)
    for x,z in core_lamps: c.set(x,4,z,TRIM); c.light(x,3,z)
    for level in range(7,top,10): c.set(lx+1,level,lz+1,TRIM); c.light(lx+1,level,lz)
    for module,support,light in lamps: module.set(*support,TRIM); module.light(*light)
    for x,y,z in chests: c.set(x-2,y-1,z,TRIM); c.light(x-2,y,z)
    for module in (f,c,a,r,n,s):
        for (x,y,z),state in tuple(module.blocks.items()):
            if state in {GROUND,WALL,TRIM} and (x+y)%6==2 and (z+2*y)%6==3 and y+2<module.size[1] \
                    and module.blocks.get((x,y+1,z),AIR)==AIR and module.blocks.get((x,y+2,z),AIR)==AIR:
                module.light(x,y,z)


def build_tribe_core() -> LandmarkBuild:
    spec=Spec("sami_cyclops_altar","sami","frozen-forest settlement core","40_g5_samitribe.png",
              ("large irregular pitched shelter roofs", "raised timber platform network", "long narrow bridges over wet ground", "central communal void, not an altar"),"L network 168x48x152")
    f=Module(spec,"foundation",(48,12,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for x,z in ((5,5),(42,5),(5,42),(42,42),(14,14),(33,14),(14,33),(33,33)): f.fill((x,1,z),(x,7,z),FENCE)
    f.fill((2,8,2),(45,9,45),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.light(x,7,z); f.set(x,6,z,FENCE)
    f.parent((47,8,23),"east","foundation_core","core"); f.parent((0,8,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,28,48)); c.fill((0,0,0),(47,0,47),WALL)
    for x0,x1,z0,z1,h in ((2,18,4,19,13),(4,22,29,44,17),(27,45,3,18,15),(30,45,28,43,12)):
        c.fill((x0,1,z0),(x1,h,z1),WALL); c.fill((x0+2,2,z0+2),(x1-2,h-2,z1-2),AIR)
        # Long asymmetric pitch continues well beyond the wall plate.
        for x in range(max(0,x0-3),min(47,x1+3)+1):
            y=h+1+abs((x0+x1)//2-x)//3; c.fill((x,y,max(0,z0-3)),(x,y+1,min(47,z1+3)),TRIM)
    c.fill((3,1,19),(18,9,19),WALL); c.fill((9,1,19),(11,3,19),AIR); c.door(10,1,19); c.fill((29,1,28),(44,9,28),WALL); c.fill((35,1,28),(37,3,28),AIR); c.door(36,1,28)
    for i in range(8): x,y,z=5+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(16,12),(31,34),(42,34)): c.set(x,10,z,TRIM); c.light(x,9,z)
    c.fill((5,1,14),(16,1,16),SLAB); c.fill((31,1,36),(42,1,38),SLAB)
    c.chest(16,1,17); c.chest(31,1,40); c.set(20,11,33,TRIM); c.chest(20,12,33)
    for y in range(17,27): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,8,23),"west","foundation_core"); c.parent((47,8,23),"east","core_facade","facade"); c.parent((23,8,47),"south","core_annex","annex"); c.parent((24,27,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,8,23),(47,8,23),(23,8,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,28,48)); a.fill((0,0,0),(39,0,47),TRIM)
    for x,z,w,h in ((1,5,10,12),(8,17,15,18),(20,4,12,14),(29,20,10,11)):
        a.fill((x,1,z),(min(39,x+w),h,min(47,z+21)),WALL)
        for px in range(max(0,x-3),min(39,x+w+3)+1): a.fill((px,h+1+abs(x+w//2-px)//3,max(0,z-3)),(px,h+2+abs(x+w//2-px)//3,min(47,z+24)),TRIM)
    a.child((0,8,23),"west","core_facade"); a.set(0,9,23,AIR); a.set(0,10,23,AIR)
    r=Module(spec,"roof",(48,28,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for x in range(1,47):
        y=4+abs(24-x)//2; r.fill((x,y,2),(x,y+2,45),TRIM)
    r.fill((7,2,9),(40,6,38),WALL); r.fill((10,3,12),(37,5,35),AIR); r.set(24,18,24,GIRDER); r.light(24,19,24)
    n=Module(spec,"annex",(44,22,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,14,37),WALL); n.fill((5,2,5),(38,13,34),AIR); n.fill((22,2,5),(22,13,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((6,2,30),(18,4,32),BARREL); n.fill((30,2,30),(30,12,30),CHAIN); n.child((21,8,0),"north","core_annex"); n.set(21,9,0,AIR); n.set(21,10,0,AIR)
    s=Module(spec,"surrounding",(48,16,48)); s.fill((0,0,0),(47,0,47),GROUND)
    for x,z in ((3,4),(35,4),(5,36),(33,34)): s.fill((x,1,z),(x+9,9,z+8),WALL)
    s.fill((0,8,20),(47,9,27),TRIM); s.child((47,8,23),"east","foundation_surrounding"); s.set(47,9,23,AIR); s.set(47,10,23,AIR)
    _commission_sami(f,c,a,r,n,s, doors=((c,10,1,19),(c,36,1,28),(n,22,2,19)), chests=((16,1,17),(31,1,40),(20,12,33)), ladder=(24,26,24),
        core_targets=((1,23),(46,23),(23,46),(9,18),(9,20),(35,27),(35,29),(15,17),(30,40),(19,33),(3,8)), core_lamps=((3,23),(11,24),(22,24),(33,24),(44,23),(23,43)), entry_y=8,
        lifts=((c,1,23,8),(c,46,23,8),(c,23,46,8),(n,21,1,8)), annex_entry_x=21,annex_door=(22,19),
        interfaces=((f,47,8,23),(f,0,8,23),(c,0,8,23),(c,47,8,23),(c,23,8,47),(a,0,8,23),(n,21,8,0),(s,47,8,23)),
        lamps=((c,(4,3,10),(4,3,9)),(c,(12,10,10),(12,10,9)),(a,(2,9,25),(2,9,24)),(n,(23,4,3),(22,4,3)),(n,(23,4,18),(22,4,18)),(s,(45,9,25),(45,9,24)),(r,(25,3,25),(25,3,24))))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(184,56,88))


def build_snowline_station() -> LandmarkBuild:
    spec=Spec("sami_snowpriest_lodge","sami","snowline observation and refuge station","40_g5_samitribe.png",
              ("low lee-side shell", "single extreme windward roof plane", "short observation mast", "cave-linked emergency store"),"L 136x40x120")
    f=Module(spec,"foundation",(44,10,44)); f.fill((0,0,0),(43,0,43),GROUND); f.fill((3,1,3),(40,3,40),TRIM)
    for x,z in ((5,5),(38,5),(5,38),(38,38)): f.fill((x,1,z),(x,3,z),FENCE); f.light(x,4,z)
    f.parent((43,2,21),"east","foundation_core","core"); f.parent((0,2,21),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(36,24,44)); c.fill((0,0,0),(35,0,43),WALL); c.fill((2,1,4),(33,14,40),WALL); c.fill((5,2,7),(30,13,37),AIR)
    # Roof drops nearly to ground on the windward side.
    for x in range(0,36): y=3+x//2; c.fill((x,y,1),(x,y+2,42),TRIM)
    c.fill((4,1,21),(31,10,21),WALL); c.fill((10,1,21),(12,3,21),AIR); c.door(11,1,21); c.fill((24,1,21),(26,3,21),AIR); c.door(25,1,21)
    for i in range(8): x,y,z=5+i,1+i,9; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((6,12),(15,12),(22,12),(30,12)): c.set(x,11,z,TRIM); c.light(x,10,z)
    c.chest(14,1,36); c.chest(28,1,36); c.set(29,10,31,TRIM); c.chest(29,11,31)
    for y in range(13,23): c.set(18,y,23,WALL); c.set(18,y,22,LADDER)
    c.child((0,2,21),"west","foundation_core"); c.parent((35,2,21),"east","core_facade","facade"); c.parent((17,2,43),"south","core_annex","annex"); c.parent((18,23,22),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,21),(35,2,21),(17,2,43)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(32,24,44)); a.fill((0,0,0),(31,0,43),TRIM); a.fill((2,1,4),(29,12,39),WALL); a.fill((5,2,7),(26,11,36),AIR)
    for x in range(0,32): a.fill((x,3+x//2,1),(x,5+x//2,42),TRIM)
    a.child((0,2,21),"west","core_facade"); a.set(0,3,21,AIR); a.set(0,4,21,AIR)
    r=Module(spec,"roof",(36,28,44)); r.fill((2,0,2),(33,1,41),TRIM); r.child((18,0,22),"down","core_roof",LADDER_STATE); r.set(18,1,22,LADDER); r.set(18,2,22,LADDER); r.set(18,1,23,WALL)
    for x in range(0,36): r.fill((x,2+x//2,1),(x,4+x//2,42),TRIM)
    r.fill((27,4,18),(30,25,25),GIRDER); r.set(28,25,21,GIRDER); r.light(28,26,21)
    n=Module(spec,"annex",(36,20,36)); n.fill((0,0,0),(35,0,35),TRIM); n.fill((2,1,2),(33,13,33),WALL); n.fill((5,2,5),(30,12,30),AIR); n.fill((18,2,5),(18,12,30),WALL); n.fill((18,2,16),(18,4,18),AIR); n.door(18,2,17); n.fill((5,2,27),(15,4,29),BARREL); n.child((17,2,0),"north","core_annex"); n.set(17,3,0,AIR); n.set(17,4,0,AIR)
    s=Module(spec,"surrounding",(44,12,44)); s.fill((0,0,0),(43,0,43),GROUND); s.fill((0,1,18),(43,2,25),TRIM); s.fill((5,1,5),(16,8,13),WALL); s.fill((30,1,31),(40,7,39),WALL); s.child((43,2,21),"east","foundation_surrounding"); s.set(43,3,21,AIR); s.set(43,4,21,AIR)
    _commission_sami(f,c,a,r,n,s, doors=((c,11,1,21),(c,25,1,21),(n,18,2,17)), chests=((14,1,36),(28,1,36),(29,11,31)), ladder=(18,22,22),
        core_targets=((0,21),(35,21),(17,43),(10,20),(10,22),(24,20),(24,22),(13,36),(27,36),(28,31),(3,9)), core_lamps=((3,21),(9,22),(17,22),(25,22),(33,21),(17,39)), entry_y=2, lifts=(), annex_entry_x=17,annex_door=(18,17),
        interfaces=((f,43,2,21),(f,0,2,21),(c,0,2,21),(c,35,2,21),(c,17,2,43),(a,0,2,21),(n,17,2,0),(s,43,2,21)),
        lamps=((c,(5,3,11),(5,3,10)),(c,(12,10,11),(12,10,10)),(a,(2,4,23),(2,4,22)),(n,(19,4,3),(18,4,3)),(n,(19,4,16),(18,4,16)),(s,(41,4,23),(41,4,22)),(r,(19,3,23),(19,3,22))))
    return LandmarkBuild(spec,(f,c,a,r,n,s),(156,52,80))


def build_all(): return build_tribe_core(), build_snowline_station()
