"""Independent Minos civic-town settlement builder; ordinary blocks are not temples."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview
NATION="minos"; SETTLEMENT="minos_heroic_polis"; ROAD="minecraft:cut_sandstone"; GROUND="zinecraft:minos_sunbaked_earth"; PUBLIC="zinecraft:minos_heroic_masonry"; PLASTER="minecraft:calcite"; ROOF="minecraft:terracotta"; SILVER="minecraft:iron_block"; GLASS="minecraft:light_gray_stained_glass"; LIGHT="minecraft:ochre_froglight"; DOOR="minecraft:jungle_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,16,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((4,0,4),(27,0,27),PUBLIC); t.cuboid((8,0,8),(23,0,23),ROAD); t.cuboid((11,1,11),(20,5,20),PUBLIC); t.clear((13,1,13),(18,4,18)); t.cuboid((9,6,9),(22,8,22),ROOF); t.cuboid((13,9,13),(18,14,18),SILVER)
    for x,z in ((6,6),(25,6),(6,25),(25,25)): t.cuboid((x,1,z),(x,4,z),PUBLIC); t.block(x,5,z,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),GROUND); t.cuboid((20,0,0),(24,0,31),GROUND)
    for z in (4,12,20,28): t.cuboid((9,1,z),(9,4,z),PUBLIC); t.block(9,5,z,LIGHT); t.cuboid((22,1,z),(22,4,z),PUBLIC); t.block(22,5,z,LIGHT)
    t.cuboid((7,7,14),(24,8,17),PLASTER); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((7,0,7),(11,0,25),GROUND); t.cuboid((20,0,20),(27,0,24),GROUND); t.cuboid((8,1,21),(11,6,25),PLASTER); t.cuboid((7,7,12),(21,8,15),ROOF); t.block(10,6,13,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,9,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,0,z),(x+2,0,z+2),GROUND); t.cuboid((x+1,1,z+1),(x+1,4,z+1),PUBLIC); t.block(x+1,5,z+1,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,12,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),GROUND); t.cuboid((7,1,23),(24,7,29),PLASTER); t.clear((10,1,23),(21,5,28)); t.cuboid((6,8,22),(17,10,30),ROOF); t.cuboid((16,7,24),(25,9,29),ROOF); t.block(9,6,23,LIGHT); t.block(22,6,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def courtyard_house()->Template:
    t=Template(NATION,SETTLEMENT,"courtyard_house",(29,20,32),"building"); t.clear((0,0,0),(28,19,31)); t.cuboid((1,0,1),(27,0,30),GROUND); t.cuboid((2,1,2),(26,11,28),PLASTER); t.clear((3,1,3),(25,10,27)); t.cuboid((14,1,14),(25,1,25),GROUND); t.cuboid((16,0,16),(23,0,23),"minecraft:water"); t.cuboid((3,11,5),(14,15,26),ROOF); t.cuboid((16,13,8),(26,18,23),ROOF); t.cuboid((1,7,4),(18,9,9),PLASTER); t.cuboid((3,3,2),(23,6,2),GLASS); t.block(14,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,1,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((5,7),(12,7),(20,7),(5,18),(12,18),(5,26),(12,26)): t.block(x,5,z,PLASTER); t.block(x,4,z,LIGHT)
    t.block(12,1,25,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(14,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("sloped house entry",(14,1,3)); t.require_reachable("family work loggia",(8,1,18)); t.require_reachable("courtyard store",(11,1,25)); t.require_walk_region("domestic wing",(3,1,3),(13,1,27)); return t
def olive_market()->Template:
    t=Template(NATION,SETTLEMENT,"olive_market",(31,20,40),"building"); t.clear((0,0,0),(30,19,39)); t.cuboid((1,0,1),(29,0,38),GROUND); t.cuboid((2,1,2),(28,10,36),PLASTER); t.clear((3,1,3),(27,9,35)); t.cuboid((3,11,5),(18,14,34),ROOF); t.cuboid((18,10,9),(28,17,31),ROOF); t.cuboid((5,1,15),(25,2,17),PUBLIC); t.cuboid((5,1,28),(25,2,30),PUBLIC); t.cuboid((3,4,2),(27,7,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in (5,12,20,26):
        for z in (7,20,33): t.block(x,5,z,PLASTER); t.block(x,4,z,LIGHT)
    t.block(26,1,33,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("produce entrance",(15,1,3)); t.require_reachable("central loading lane",(15,1,22)); t.require_reachable("dry goods store",(25,1,33)); t.require_walk_region("produce market floor",(3,1,3),(27,1,35)); return t
def training_hall()->Template:
    t=Template(NATION,SETTLEMENT,"training_hall",(31,24,42),"building"); t.clear((0,0,0),(30,23,41)); t.cuboid((1,0,1),(29,0,40),GROUND); t.cuboid((2,1,2),(28,13,38),PLASTER); t.clear((3,1,3),(27,12,37)); t.cuboid((8,1,17),(26,1,33),GROUND); t.cuboid((10,0,19),(24,0,31),ROAD); t.cuboid((3,13,5),(15,18,36),ROOF); t.cuboid((17,13,8),(28,21,30),ROOF); t.cuboid((4,4,2),(26,8,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in (5,14,24):
        for z in (7,17,34): t.block(x,5,z,PLASTER); t.block(x,4,z,LIGHT)
    t.block(25,1,35,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("training reception",(15,1,3)); t.require_reachable("open training court",(18,2,24)); t.require_reachable("medical equipment",(24,1,35)); t.require_walk_region("covered training floor",(3,1,3),(27,1,37)); return t
def council_house()->Template:
    t=Template(NATION,SETTLEMENT,"council_house",(31,29,40),"building"); t.clear((0,0,0),(30,28,39)); t.cuboid((1,0,1),(29,0,38),GROUND); t.cuboid((2,1,2),(28,16,36),PUBLIC); t.clear((3,1,3),(27,15,35)); t.cuboid((3,16,5),(18,22,33),PLASTER); t.cuboid((18,16,9),(28,25,30),PLASTER); t.cuboid((21,25,13),(26,28,25),SILVER); t.cuboid((1,1,1),(29,1,8),PUBLIC); t.cuboid((3,5,2),(27,10,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in (6,15,24):
        for z in (7,19,33): t.block(x,5,z,PUBLIC); t.block(x,4,z,LIGHT)
    t.block(25,1,33,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("public stair hall",(15,2,5)); t.require_reachable("district assembly",(15,1,19)); t.require_reachable("records store",(24,1,33)); t.require_walk_region("assembly house floor",(3,1,3),(27,1,35)); return t

def _assert_not_all_temples(ts:list[Template])->None:
    house=next(t for t in ts if t.name=="courtyard_house"); assert PUBLIC not in {name for name,_ in house.palette}
def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),courtyard_house(),olive_market(),training_hall(),council_house()]; _assert_not_all_temples(ts); validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Minos settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Minos templates")
if __name__=="__main__": main()
