"""Independent Ursus builders from northern government, mine and heavy interior backgrounds."""

from .base import AIR, BARS, CASING, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, RAIL, SHAFT_X, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec

def _commission_ursus(f,c,a,r,n,s,*,doors,chests,ladder,core_targets,core_lamps,annex_entry_x,annex_door,interfaces,lamps):
    """Cut Ursus armoured transit galleries and independently supported work lights."""
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
            if state in {GROUND,WALL,TRIM} and (x+2*y)%7==4 and (z+4*y)%7==3 and y+2<module.size[1] \
                    and module.blocks.get((x,y+1,z),AIR)==AIR and module.blocks.get((x,y+2,z),AIR)==AIR:
                module.light(x,y,z)


def build_sarcophagus_station() -> LandmarkBuild:
    spec=Spec("ursus_sarcophagus_station","ursus","restricted heavy-shell utility station","66_g2_farnorthgovernment.png",
              ("sunken heavy shell", "multiple isolation rings", "dark diagonal structural braces", "small lit control court inside a huge void"),"XL 184x72x152, specific sarcophagus exterior U")
    f=Module(spec,"foundation",(48,16,48)); f.fill((0,0,0),(47,0,47),GROUND)
    for step in range(9): f.fill((step,1+step,step),(47-step,1+step,47-step),TRIM)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,40,48)); c.fill((0,0,0),(47,0,47),WALL)
    for inset,y in ((2,1),(6,5),(10,9),(14,13)):
        c.fill((inset,y,inset),(47-inset,35-y//2,47-inset),WALL); c.fill((inset+3,y+1,inset+3),(44-inset,32-y//2,44-inset),AIR)
    for x in (5,13,34,42): c.fill((x,1,4),(x+2,34,43),GIRDER)
    for z in (7,19,31,41): c.fill((4,1,z),(43,34,z+2),GIRDER)
    c.fill((5,1,23),(20,10,23),WALL); c.fill((11,1,23),(13,3,23),AIR); c.door(12,1,23); c.fill((28,1,23),(43,10,23),WALL); c.fill((34,1,23),(36,3,23),AIR); c.door(35,1,23)
    for i in range(10): x,y,z=6+i,1+i,10; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((7,13),(19,13),(29,13),(41,13)): c.set(x,11,z,GIRDER); c.light(x,10,z)
    c.chest(19,1,40); c.chest(29,1,40); c.set(38,14,35,TRIM); c.chest(38,15,35)
    for y in range(29,39): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,39,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,40,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for inset in range(2,20,4): a.fill((inset,1,inset),(47-inset,36-inset//2,47-inset),WALL); a.fill((inset+3,2,inset+3),(44-inset,33-inset//2,44-inset),AIR)
    for x in (4,12,35,43): a.fill((x,1,3),(x+2,37,44),GIRDER)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,36,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for inset,y in ((2,3),(7,9),(12,15)):
        r.fill((inset,y,inset),(47-inset,y+3,47-inset),WALL); r.fill((inset+3,y+1,inset+3),(44-inset,y+3,44-inset),AIR)
    r.fill((18,19,18),(29,31,29),CASING); r.fill((21,21,21),(26,29,26),AIR); r.set(24,32,24,GIRDER); r.light(24,33,24)
    n=Module(spec,"annex",(48,28,44)); n.fill((0,0,0),(47,0,43),TRIM); n.fill((2,1,2),(45,20,41),WALL); n.fill((5,2,5),(42,19,38),AIR); n.fill((24,2,5),(24,19,38),WALL); n.fill((24,2,20),(24,4,22),AIR); n.door(24,2,21); n.fill((6,3,34),(41,3,36),SHAFT_X); n.set(12,3,33,CASING); n.set(35,3,33,CASING); n.child((23,2,0),"north","core_annex"); n.set(23,3,0,AIR); n.set(23,4,0,AIR)
    s=Module(spec,"surrounding",(48,20,48)); s.fill((0,0,0),(47,0,47),GROUND)
    for step in range(9): s.fill((step,1+step,step),(47-step,1+step,47-step),TRIM)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_ursus(
        f,c,a,r,n,s, doors=((c,12,1,23),(c,35,1,23),(n,24,2,21)), chests=((19,1,40),(29,1,40),(38,15,35)), ladder=(24,38,24),
        core_targets=((0,23),(23,1),(23,46),(11,22),(11,24),(34,22),(34,24),(18,40),(28,40),(37,35),(5,10)), core_lamps=((3,23),(11,24),(22,24),(33,24),(44,23),(23,43)),
        annex_entry_x=23,annex_door=(24,21), interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,23,2,0),(c,23,2,47),(a,23,2,47),(n,23,2,0),(s,47,2,23)),
        lamps=((c,(7,3,12),(7,3,11)),(c,(15,12,12),(15,12,11)),(c,(22,1,1),(22,2,1)),(a,(22,1,46),(22,2,46)),(a,(25,4,45),(24,4,45)),(n,(25,4,3),(24,4,3)),(n,(25,4,20),(24,4,20)),(s,(45,4,25),(45,4,24)),(r,(25,3,25),(25,3,24))),
    )
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,76,140))


def build_northern_mine_tower() -> LandmarkBuild:
    spec=Spec("ursus_northern_mine_tower","ursus","northern mine hoist and sorting tower","66_g3_miningarea.png",
              ("closed cold-weather headframe", "stepped sorting tower", "ore hopper row", "heated low service wing"),"XL 160x88x128")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,16),(47,2,31),TRIM)
    for z in (19,25,31): f.fill((0,2,z),(47,2,z),RAIL)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(44,44,44)); c.fill((0,0,0),(43,0,43),WALL)
    c.fill((4,1,5),(39,18,39),WALL); c.fill((7,2,8),(36,17,36),AIR)
    c.fill((10,19,9),(33,31,34),WALL); c.fill((13,20,12),(30,30,31),AIR)
    c.fill((16,32,14),(27,42,29),WALL); c.fill((19,33,17),(24,40,26),AIR)
    for x,z in ((7,8),(34,8),(7,35),(34,35)): c.fill((x,2,z),(x+2,39,z+2),GIRDER)
    c.fill((5,1,21),(19,10,21),WALL); c.fill((10,1,21),(12,3,21),AIR); c.door(11,1,21); c.fill((25,1,21),(39,10,21),WALL); c.fill((30,1,21),(32,3,21),AIR); c.door(31,1,21)
    for i in range(10): x,y,z=6+i,1+i,9; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((7,12),(18,12),(27,12),(38,12)): c.set(x,18,z,TRIM); c.light(x,17,z)
    c.chest(18,1,36); c.chest(26,1,36); c.set(34,18,32,TRIM); c.chest(34,19,32)
    for y in range(33,43): c.set(22,y,23,WALL); c.set(22,y,22,LADDER)
    c.child((0,2,21),"west","foundation_core"); c.parent((43,2,21),"east","core_facade","facade"); c.parent((21,2,43),"south","core_annex","annex"); c.parent((22,43,22),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,21),(43,2,21),(21,2,43)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(40,44,44)); a.fill((0,0,0),(39,0,43),TRIM); a.fill((3,1,4),(36,40,39),WALL); a.fill((7,2,8),(32,37,35),AIR)
    for x in (4,12,26,34): a.fill((x,1,2),(x+2,42,41),GIRDER)
    a.child((0,2,21),"west","core_facade"); a.set(0,3,21,AIR); a.set(0,4,21,AIR)
    r=Module(spec,"roof",(44,44,44)); r.fill((2,0,2),(41,1,41),TRIM); r.child((22,0,22),"down","core_roof",LADDER_STATE); r.set(22,1,22,LADDER); r.set(22,2,22,LADDER); r.set(22,1,23,WALL)
    for x,z in ((8,8),(35,8),(8,35),(35,35)): r.fill((x,2,z),(x+2,40,z+2),GIRDER)
    for y in (12,25,38): r.fill((7,y,7),(38,y+2,38),GIRDER); r.fill((10,y+1,10),(35,y+2,35),AIR)
    r.set(22,41,22,GIRDER); r.light(22,42,22)
    n=Module(spec,"annex",(44,24,40)); n.fill((0,0,0),(43,0,39),TRIM); n.fill((2,1,2),(41,17,37),WALL); n.fill((5,2,5),(38,16,34),AIR); n.fill((22,2,5),(22,16,34),WALL); n.fill((22,2,18),(22,4,20),AIR); n.door(22,2,19); n.fill((6,2,30),(37,3,32),SHAFT_X); n.child((21,2,0),"north","core_annex"); n.set(21,3,0,AIR); n.set(21,4,0,AIR)
    s=Module(spec,"surrounding",(48,14,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,14),(47,1,33),TRIM)
    for z in (18,24,30): s.fill((0,2,z),(47,2,z),RAIL)
    s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_ursus(
        f,c,a,r,n,s, doors=((c,11,1,21),(c,31,1,21),(n,22,2,19)), chests=((18,1,36),(26,1,36),(34,19,32)), ladder=(22,42,22),
        core_targets=((0,21),(43,21),(21,43),(10,20),(10,22),(30,20),(30,22),(17,36),(25,36),(33,32),(5,9)), core_lamps=((3,21),(10,22),(20,22),(30,22),(40,21),(21,39)),
        annex_entry_x=21,annex_door=(22,19), interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,21),(c,43,2,21),(c,21,2,43),(a,0,2,21),(n,21,2,0),(s,47,2,23)),
        lamps=((c,(7,3,11),(7,3,10)),(c,(15,12,11),(15,12,10)),(a,(2,4,23),(2,4,22)),(n,(23,4,3),(22,4,3)),(n,(23,4,18),(22,4,18)),(s,(45,4,25),(45,4,24)),(r,(23,3,23),(23,3,22))),
    )
    return LandmarkBuild(spec,(f,c,a,r,n,s),(180,88,86))


def build_all(): return build_sarcophagus_station(), build_northern_mine_tower()
