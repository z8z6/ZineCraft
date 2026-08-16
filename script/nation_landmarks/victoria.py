"""Independent Victoria builders: cannon matches the pilot logic; station follows Londinium massing."""

from .base import AIR, BARS, CASING, CHAIN, GIRDER, GLASS, GROUND, LADDER, LADDER_STATE, RAIL, SHAFT_X, SLAB, STAIR_E, TRIM, WALL, LandmarkBuild, Module, Spec


def _commission_victoria(f,c,a,r,n,s,*,doors,chests,ladder,core_targets,annex_door,interfaces,lamps):
    """Cut Victoria's armoured service galleries and independently supported work lights."""
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
    for z in range(0,nz+2): n.set(23,1,z,TRIM); n.fill((23,2,z),(23,3,z),AIR)
    n.set(nx,1,nz-1,TRIM); n.fill((nx,2,nz-1),(nx,3,nz-1),AIR)
    for module,x,y,z in interfaces: module.set(x,y-1,z,STAIR_E); module.fill((x,y,z),(x,y+1,z),AIR)
    for level in range(1,top+1): c.set(lx,level,lz+1,WALL); c.set(lx,level,lz,LADDER)
    for x,y,z in chests:
        if y>1:
            for level in range(1,y+1): c.set(x-1,level,z,LADDER)
    for level in range(1,6): r.set(lx,level,lz+1,WALL); r.set(lx,level,lz,LADDER)
    r.set(lx,6,lz,AIR); r.set(lx-1,4,lz,TRIM); r.fill((lx-1,5,lz),(lx-1,6,lz),AIR)
    for x in (4,14,24,34,44): f.set(x,5,23,TRIM); f.light(x,4,23)
    for x,z in ((3,23),(11,24),(22,24),(33,24),(44,23),(23,43)): c.set(x,4,z,TRIM); c.light(x,3,z)
    for level in range(7,top,10): c.set(lx+1,level,lz+1,TRIM); c.light(lx+1,level,lz)
    for module,support,light in lamps: module.set(*support,TRIM); module.light(*light)
    for x,y,z in chests: c.set(x-2,y-1,z,TRIM); c.light(x-2,y,z)
    for module in (f,c,a,r,n,s):
        for (x,y,z),state in tuple(module.blocks.items()):
            if state in {GROUND,WALL,TRIM} and (x+3*y)%7==3 and (z+y)%7==1 and y+2<module.size[1] \
                    and module.blocks.get((x,y+1,z),AIR)==AIR and module.blocks.get((x,y+2,z),AIR)==AIR:
                module.light(x,y,z)


def build_defence_cannon() -> LandmarkBuild:
    spec=Spec("victoria_defence_cannon","victoria","Londinium defence cannon","21_G3_victoria_street_d.png",
              ("long barrel dominating the skyline", "sloped armoured barbette", "continuous high wall", "rear shell-hoist and maintenance hall"),"XL 192x72x128")
    f=Module(spec,"foundation",(48,12,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,14),(47,3,33),WALL)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding"); f.set(47,3,23,AIR); f.set(47,4,23,AIR); f.set(0,3,23,AIR); f.set(0,4,23,AIR)
    c=Module(spec,"core",(48,40,48)); c.fill((0,0,0),(47,0,47),WALL)
    # Chamfered barbette tiers support a separate long gun body.
    for step in range(8): c.fill((step*2,1+step*2,step),(47-step,2+step*2,47-step*2),TRIM if step%2 else WALL)
    c.fill((12,5,12),(35,27,35),WALL); c.fill((16,6,16),(31,26,31),AIR)
    c.fill((20,20,0),(27,30,47),CASING); c.fill((22,22,0),(25,28,47),AIR)
    for z in range(0,48):
        if z%5==0: c.fill((18,19,z),(29,31,z),TRIM)
    c.fill((5,1,23),(19,11,23),WALL); c.fill((10,1,23),(12,3,23),AIR); c.door(11,1,23); c.fill((29,1,23),(43,11,23),WALL); c.fill((34,1,23),(36,3,23),AIR); c.door(35,1,23)
    for i in range(10): x,y,z=7+i,1+i,10; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((8,13),(19,13),(29,13),(40,13)): c.set(x,12,z,GIRDER); c.light(x,11,z)
    c.fill((8,1,32),(20,1,35),SLAB); c.fill((28,1,32),(40,1,35),SLAB)
    c.chest(20,1,40); c.chest(28,1,40); c.set(38,15,36,TRIM); c.chest(38,16,36)
    for y in range(29,39): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,39,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,40,48)); a.fill((0,0,0),(47,0,47),TRIM)
    for step in range(9): a.fill((step,1+step*2,step),(47-step,3+step*2,47-step),WALL)
    a.fill((18,18,0),(29,31,47),CASING); a.fill((21,21,0),(26,28,47),AIR)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,36,48)); r.fill((2,0,2),(45,1,45),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    r.fill((18,8,0),(29,20,47),CASING); r.fill((21,10,0),(26,18,47),AIR)
    for z in range(0,48,5): r.fill((16,7,z),(31,21,z),TRIM)
    r.fill((8,2,8),(39,6,39),WALL); r.fill((12,3,12),(35,5,35),AIR); r.set(24,22,24,GIRDER); r.light(24,23,24)
    n=Module(spec,"annex",(48,28,44)); n.fill((0,0,0),(47,0,43),TRIM); n.fill((2,1,2),(45,20,41),WALL); n.fill((5,2,5),(42,19,38),AIR); n.fill((24,2,5),(24,19,38),WALL); n.fill((24,2,20),(24,4,22),AIR); n.door(24,2,21); n.fill((6,3,34),(41,3,36),SHAFT_X); n.set(12,3,33,CASING); n.set(35,3,33,CASING); n.fill((10,4,31),(10,17,31),CHAIN); n.fill((37,4,31),(37,17,31),CHAIN); n.child((23,2,0),"north","core_annex"); n.set(23,3,0,AIR); n.set(23,4,0,AIR)
    s=Module(spec,"surrounding",(48,16,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,12),(47,12,17),WALL); s.fill((0,1,30),(47,12,35),WALL); s.fill((0,1,18),(47,1,29),TRIM); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_victoria(
        f,c,a,r,n,s, doors=((c,11,1,23),(c,35,1,23),(n,24,2,21)),
        chests=((20,1,40),(28,1,40),(38,16,36)), ladder=(24,38,24),
        core_targets=((0,23),(23,1),(23,46),(10,22),(10,24),(34,22),(34,24),(19,40),(27,40),(37,36),(6,10)),
        annex_door=(24,21), interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,23,2,0),(c,23,2,47),(a,23,2,47),(n,23,2,0),(s,47,2,23)),
        lamps=((c,(8,3,12),(8,3,11)),(c,(16,12,12),(16,12,11)),(c,(22,1,1),(22,2,1)),(a,(22,1,46),(22,2,46)),(a,(25,4,45),(24,4,45)),(n,(25,4,3),(24,4,3)),(n,(25,4,20),(24,4,20)),(s,(45,4,25),(45,4,24)),(r,(25,3,25),(25,3,24))),
    )
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,76,140))


def build_steam_station() -> LandmarkBuild:
    spec=Spec("victoria_steam_station","victoria","Londinium district transport hub","21_G3_victoria_street_d.png",
              ("long street-wall entrance", "repeated tall industrial windows", "three-span train shed", "corner towers and sunken freight level"),"XL 192x64x152")
    f=Module(spec,"foundation",(48,10,48)); f.fill((0,0,0),(47,0,47),GROUND); f.fill((0,1,12),(47,1,35),TRIM)
    for z in (17,24,31): f.fill((0,2,z),(47,2,z),RAIL)
    for x,z in ((5,5),(42,5),(5,42),(42,42)): f.fill((x,1,z),(x,3,z),GIRDER); f.light(x,4,z)
    f.parent((47,2,23),"east","foundation_core","core"); f.parent((0,2,23),"west","foundation_surrounding","surrounding")
    c=Module(spec,"core",(48,36,48)); c.fill((0,0,0),(47,0,47),WALL)
    c.fill((2,1,4),(45,24,43),WALL); c.fill((5,2,7),(42,23,40),AIR)
    for z in (12,24,36):
        for x in range(4,44): c.set(x,22-abs(24-x)//5,z,WALL)
        for x in (6,18,30,42): c.fill((x,2,z-5),(x+1,22,z+5),GIRDER)
    c.fill((4,1,23),(21,11,23),WALL); c.fill((11,1,23),(13,3,23),AIR); c.door(12,1,23); c.fill((27,1,23),(44,11,23),WALL); c.fill((34,1,23),(36,3,23),AIR); c.door(35,1,23)
    for i in range(10): x,y,z=6+i,1+i,8; c.set(x,y,z,STAIR_E); c.stairs.add((x,y,z)); c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    for x,z in ((7,10),(19,10),(29,10),(41,10)): c.set(x,24,z,GIRDER); c.light(x,23,z)
    c.fill((7,1,33),(19,1,36),SLAB); c.fill((29,1,33),(41,1,36),SLAB)
    c.chest(19,1,40); c.chest(29,1,40); c.set(38,23,36,TRIM); c.chest(38,24,36)
    for y in range(25,35): c.set(24,y,25,WALL); c.set(24,y,24,LADDER)
    c.child((0,2,23),"west","foundation_core"); c.parent((23,2,0),"north","core_facade","facade"); c.parent((23,2,47),"south","core_annex","annex"); c.parent((24,35,24),"up","core_roof","roof",LADDER_STATE)
    for x,y,z in ((0,2,23),(47,2,23),(23,2,47)): c.set(x,y+1,z,AIR); c.set(x,y+2,z,AIR)
    a=Module(spec,"facade",(48,36,48)); a.fill((0,0,0),(47,0,47),TRIM); a.fill((1,1,3),(46,27,44),WALL); a.fill((4,2,6),(43,26,41),AIR)
    for x in range(3,46,5): a.fill((x,1,2),(x+1,31,6),TRIM); a.fill((x,6,1),(x+1,22,1),GLASS)
    for x in (3,40): a.fill((x,1,4),(x+5,34,11),WALL)
    a.child((23,2,47),"south","core_facade"); a.set(23,3,47,AIR); a.set(23,4,47,AIR)
    r=Module(spec,"roof",(48,28,48)); r.fill((1,0,1),(46,1,46),TRIM); r.child((24,0,24),"down","core_roof",LADDER_STATE); r.set(24,1,24,LADDER); r.set(24,2,24,LADDER); r.set(24,1,25,WALL)
    for z in (9,23,37):
        for x in range(2,46): r.set(x,4+abs(24-x)//4,z,WALL)
    for x in (4,16,28,40): r.fill((x,2,4),(x+1,22,43),GIRDER)
    r.set(24,23,24,GIRDER); r.light(24,24,24)
    n=Module(spec,"annex",(48,24,40)); n.fill((0,0,0),(47,0,39),TRIM); n.fill((2,1,2),(45,17,37),WALL); n.fill((5,2,5),(42,16,34),AIR); n.fill((24,2,5),(24,16,34),WALL); n.fill((24,2,18),(24,4,20),AIR); n.door(24,2,19); n.fill((6,2,30),(41,3,32),SHAFT_X); n.set(12,2,29,CASING); n.set(35,2,29,CASING); n.child((23,2,0),"north","core_annex"); n.set(23,3,0,AIR); n.set(23,4,0,AIR)
    s=Module(spec,"surrounding",(48,14,48)); s.fill((0,0,0),(47,0,47),GROUND); s.fill((0,1,11),(47,1,36),TRIM)
    for z in (16,23,30): s.fill((0,2,z),(47,2,z),RAIL)
    s.fill((5,1,4),(18,9,11),WALL); s.fill((30,1,37),(43,9,44),WALL); s.child((47,2,23),"east","foundation_surrounding"); s.set(47,3,23,AIR); s.set(47,4,23,AIR)
    _commission_victoria(
        f,c,a,r,n,s, doors=((c,12,1,23),(c,35,1,23),(n,24,2,19)),
        chests=((19,1,40),(29,1,40),(38,24,36)), ladder=(24,34,24),
        core_targets=((0,23),(23,1),(23,46),(11,22),(11,24),(34,22),(34,24),(18,40),(28,40),(37,36),(5,8)),
        annex_door=(24,19), interfaces=((f,47,2,23),(f,0,2,23),(c,0,2,23),(c,23,2,0),(c,23,2,47),(a,23,2,47),(n,23,2,0),(s,47,2,23)),
        lamps=((c,(7,3,10),(7,3,9)),(c,(15,12,10),(15,12,9)),(c,(24,4,2),(23,4,2)),(a,(25,4,45),(24,4,45)),(n,(25,4,3),(24,4,3)),(n,(25,4,18),(24,4,18)),(s,(45,4,25),(45,4,24)),(r,(25,3,25),(25,3,24))),
    )
    return LandmarkBuild(spec,(f,c,a,r,n,s),(144,64,136))


def build_all(): return build_defence_cannon(), build_steam_station()
