"""Independent Columbia frontier-platform settlement builder."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview

NATION="columbia"; SETTLEMENT="columbia_frontier_town"; ROAD="minecraft:gravel"; GROUND="zinecraft:columbia_canyon_soil"; WALL="zinecraft:columbia_frontier_panel"; METAL="minecraft:cut_copper"; GLASS="minecraft:gray_stained_glass"; LIGHT="minecraft:sea_lantern"; DOOR="minecraft:copper_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,13,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((6,0,6),(25,0,25),WALL); t.cuboid((9,1,9),(22,3,22),METAL); t.clear((11,1,11),(20,3,20)); t.cuboid((14,1,14),(17,9,17),WALL); t.cuboid((13,10,13),(18,11,18),METAL)
    for x,z in ((7,7),(24,7),(7,24),(24,24)): t.cuboid((x,1,z),(x,4,z),METAL); t.block(x,5,z,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((8,0,0),(11,0,31),WALL); t.cuboid((20,0,0),(23,0,31),WALL); t.cuboid((6,5,14),(25,6,17),METAL)
    for z in (5,14,23,29): t.cuboid((9,1,z),(9,3,z),METAL); t.block(9,4,z,LIGHT); t.cuboid((22,1,z),(22,3,z),METAL); t.block(22,4,z,LIGHT)
    t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((7,0,7),(11,0,25),WALL); t.cuboid((20,0,20),(26,0,24),WALL); t.cuboid((8,1,21),(11,7,25),METAL); t.block(10,6,21,LIGHT)
    t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,9,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,0,z),(x+2,0,z+2),WALL); t.cuboid((x+1,1,z+1),(x+1,4,z+1),METAL); t.block(x+1,5,z+1,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,11,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),GROUND); t.cuboid((7,1,23),(24,7,29),WALL); t.clear((10,1,23),(21,5,28)); t.cuboid((6,8,22),(25,9,30),METAL); t.block(9,6,23,LIGHT); t.block(22,6,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def prefab_house()->Template:
    t=Template(NATION,SETTLEMENT,"prefab_house",(31,21,32),"building"); t.clear((0,0,0),(30,20,31)); t.cuboid((1,0,1),(29,0,30),GROUND); t.cuboid((2,1,2),(12,12,28),WALL); t.clear((3,1,3),(11,11,27)); t.cuboid((15,1,5),(28,15,27),WALL); t.clear((16,1,6),(27,14,26)); t.cuboid((9,6,8),(19,9,14),METAL); t.clear((10,6,9),(18,8,13)); t.cuboid((4,12,7),(10,16,24),METAL); t.cuboid((22,15,10),(27,19,22),METAL); t.cuboid((3,4,2),(10,7,2),GLASS); t.cuboid((28,3,9),(28,6,23),GLASS); t.clear((12,1,7),(16,3,9)); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,4))
    # The connector lobby branches through real doors into both habitation pods.
    t.block(12,1,8,DOOR,{"half":"lower","facing":"east"}); t.block(12,2,8,DOOR,{"half":"upper","facing":"east"})
    t.block(15,1,8,DOOR,{"half":"lower","facing":"west"}); t.block(15,2,8,DOOR,{"half":"upper","facing":"west"})
    # A compact west sleeping loft is reached by a continuous seven-step stair.
    t.cuboid((3,7,15),(11,7,26),METAL); t.clear((4,7,8),(4,10,14))
    for y,z in enumerate(range(8,15),start=1): t.block(4,y,z,"minecraft:cut_copper_stairs",{"facing":"south"})
    t.cuboid((3,8,21),(11,10,21),WALL); t.clear((7,8,21),(7,9,21))
    t.block(7,8,21,DOOR,{"half":"lower","facing":"south"}); t.block(7,9,21,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((6,17),(9,17),(6,24),(9,24)): t.block(x,12,z,METAL); t.block(x,11,z,LIGHT)
    for x,z in ((6,7),(6,18),(6,26),(18,9),(24,9),(18,21),(24,21)): t.block(x,5,z,METAL); t.block(x,4,z,LIGHT)
    t.block(25,1,24,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("wind vestibule",(15,1,3)); t.require_reachable("west habitation pod",(8,1,12)); t.require_reachable("east habitation pod",(20,1,12)); t.require_walk_region("west pod",(3,1,4),(11,1,26)); t.require_walk_region("east pod",(16,1,7),(27,1,25)); return t

def pioneer_lab()->Template:
    t=Template(NATION,SETTLEMENT,"pioneer_lab",(31,24,40),"building"); t.clear((0,0,0),(30,23,39)); t.cuboid((1,0,1),(29,0,38),GROUND); t.cuboid((2,1,2),(28,13,36),WALL); t.clear((3,1,3),(27,12,35)); t.cuboid((3,13,6),(20,17,33),WALL); t.cuboid((20,13,11),(28,21,30),METAL); t.cuboid((8,1,14),(14,6,24),"create:andesite_casing"); t.clear((9,1,15),(13,5,23)); t.clear((14,1,18),(15,3,20)); t.cuboid((3,4,2),(25,8,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Dust lock, sample-transfer laboratory, and records room are pressure-separated.
    t.cuboid((3,1,10),(27,4,10),WALL); t.clear((15,1,10),(15,2,10))
    t.block(15,1,10,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,10,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,29),(27,4,29),WALL); t.clear((25,1,29),(25,2,29))
    t.block(25,1,29,DOOR,{"half":"lower","facing":"south"}); t.block(25,2,29,DOOR,{"half":"upper","facing":"south"})
    for x in (6,16,25):
        for z in (7,19,31): t.block(x,5,z,METAL); t.block(x,4,z,LIGHT)
    t.block(11,5,26,METAL); t.block(11,4,26,LIGHT)
    t.block(11,4,19,LIGHT); t.block(11,5,19,METAL); t.block(26,1,33,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("dust lock",(15,1,3)); t.require_reachable("sample transfer",(15,1,19)); t.require_reachable("analysis records",(25,1,32)); t.require_walk_region("field analysis floor",(3,1,3),(27,1,35)); return t

def logistics_depot()->Template:
    t=Template(NATION,SETTLEMENT,"logistics_depot",(31,27,46),"building"); t.clear((0,0,0),(30,26,45)); t.cuboid((1,0,1),(29,0,44),GROUND); t.cuboid((2,1,2),(28,15,42),WALL); t.clear((3,1,3),(27,14,41)); t.cuboid((3,15,5),(18,19,39),METAL); t.cuboid((19,15,10),(28,24,34),WALL); t.cuboid((23,24,15),(27,26,29),METAL); t.cuboid((5,1,15),(25,2,17),"create:andesite_casing"); t.cuboid((6,3,16),(24,3,16),"create:belt"); t.cuboid((4,1,28),(12,5,36),"create:brass_casing"); t.clear((5,1,29),(11,4,35)); t.clear((12,1,31),(13,3,33)); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Receiving office, powered sorting hall, and dispatch bay are distinct zones.
    t.cuboid((3,1,12),(27,4,12),WALL); t.clear((15,1,12),(15,2,12))
    t.block(15,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,12,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,37),(27,4,37),WALL); t.clear((25,1,37),(25,2,37))
    t.block(25,1,37,DOOR,{"half":"lower","facing":"south"}); t.block(25,2,37,DOOR,{"half":"upper","facing":"south"})
    for x in (6,15,24):
        for z in (7,20,33,40): t.block(x,5,z,METAL); t.block(x,4,z,LIGHT)
    t.block(8,4,32,LIGHT); t.block(8,5,32,METAL); t.block(3,4,33,LIGHT); t.block(3,5,33,METAL); t.block(26,1,39,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("receiving gate",(15,1,3)); t.require_reachable("sorting spine",(15,1,20)); t.require_reachable("dispatch bay",(25,1,39)); t.require_walk_region("depot floor",(3,1,3),(27,1,41)); return t

def sheriff_office()->Template:
    t=Template(NATION,SETTLEMENT,"sheriff_office",(29,29,34),"building"); t.clear((0,0,0),(28,28,33)); t.cuboid((1,0,1),(27,0,32),GROUND); t.cuboid((2,1,2),(26,16,30),WALL); t.clear((3,1,3),(25,15,29)); t.cuboid((4,16,5),(18,21,28),WALL); t.cuboid((18,14,9),(26,25,25),METAL); t.cuboid((21,25,13),(24,28,21),METAL); t.cuboid((3,1,20),(9,6,28),"create:andesite_casing"); t.clear((4,1,21),(8,5,27)); t.clear((9,1,23),(10,3,25)); t.cuboid((4,4,2),(23,8,2),GLASS); t.block(14,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((14,1,2),(14,2,2))
    # Emergency reception and dispatch are divided; the shelter cage has its own door.
    t.cuboid((3,1,12),(25,4,12),WALL); t.clear((14,1,12),(14,2,12))
    t.block(14,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,12,DOOR,{"half":"upper","facing":"south"})
    t.block(9,1,24,DOOR,{"half":"lower","facing":"east"}); t.block(9,2,24,DOOR,{"half":"upper","facing":"east"})
    for x in (6,14,22):
        for z in (7,17,27): t.block(x,5,z,METAL); t.block(x,4,z,LIGHT)
    t.block(6,4,24,LIGHT); t.block(6,5,24,METAL); t.block(3,4,29,LIGHT); t.block(3,5,29,METAL); t.block(23,1,27,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(14,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("emergency reception",(14,1,3)); t.require_reachable("dispatch room",(14,1,17)); t.require_reachable("shelter store",(22,1,27)); t.require_walk_region("administration floor",(3,1,3),(25,1,29)); return t

def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),prefab_house(),pioneer_lab(),logistics_depot(),sheriff_office()]; validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Columbia settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Columbia templates")
if __name__=="__main__": main()
