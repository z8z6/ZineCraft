"""Independent Kazdel rust-steel mobile-city settlement builder."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview
NATION="kazdel"; SETTLEMENT="kazdel_sarkaz_settlement"; ROAD="minecraft:polished_blackstone"; GROUND="zinecraft:kazdel_scarred_ash"; WALL="zinecraft:kazdel_fortress_plate"; METAL="minecraft:raw_iron_block"; PATCH="minecraft:dark_oak_planks"; GLASS="minecraft:gray_stained_glass"; LIGHT="minecraft:shroomlight"; DOOR="minecraft:crimson_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,16,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((6,0,6),(25,0,25),GROUND); t.cuboid((9,1,9),(22,5,22),WALL); t.clear((11,1,11),(20,4,20)); t.cuboid((13,1,13),(18,11,18),METAL); t.cuboid((10,12,10),(21,14,21),WALL); t.cuboid((5,7,14),(26,8,17),METAL); t.block(8,6,15,LIGHT); t.block(23,6,16,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,12,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((8,0,0),(11,0,31),GROUND); t.cuboid((20,0,0),(23,0,31),GROUND); t.cuboid((7,7,14),(24,8,17),METAL); t.cuboid((9,9,15),(22,10,16),WALL)
    for z in (5,15,25): t.cuboid((9,1,z),(9,4,z),METAL); t.block(9,5,z,LIGHT); t.cuboid((22,1,z),(22,4,z),METAL); t.block(22,5,z,LIGHT)
    t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,11,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((7,0,7),(11,0,25),GROUND); t.cuboid((20,0,20),(27,0,24),GROUND); t.cuboid((8,1,21),(11,7,25),WALL); t.cuboid((7,8,13),(21,9,16),METAL); t.block(10,6,14,LIGHT); t.block(10,7,14,METAL); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,0,z),(x+2,0,z+2),GROUND); t.cuboid((x+1,1,z+1),(x+1,4,z+1),METAL); t.block(x+1,5,z+1,LIGHT)
    t.cuboid((6,7,15),(25,8,16),METAL)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,12,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),GROUND); t.cuboid((7,1,23),(24,7,29),WALL); t.clear((10,1,23),(21,5,28)); t.cuboid((6,8,22),(17,10,30),PATCH); t.cuboid((16,7,24),(25,9,29),METAL); t.block(9,6,23,LIGHT); t.block(22,6,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def canvas_house()->Template:
    t=Template(NATION,SETTLEMENT,"canvas_house",(25,22,30),"building"); t.clear((0,0,0),(24,21,29)); t.cuboid((1,0,1),(23,0,28),GROUND); t.cuboid((2,1,2),(14,11,26),WALL); t.clear((3,1,3),(13,10,25)); t.cuboid((11,5,6),(22,15,24),WALL); t.clear((12,5,7),(21,14,23)); t.cuboid((4,11,8),(10,17,25),PATCH); t.cuboid((17,15,10),(22,20,21),METAL); t.cuboid((1,6,5),(17,7,9),METAL); t.cuboid((2,3,2),(12,5,2),GLASS); t.clear((13,1,7),(16,3,9)); t.block(12,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(12,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((12,1,2),(12,2,2))
    # Patched vestibule and workers' room close independently.
    t.cuboid((3,1,12),(13,4,12),WALL); t.clear((7,1,12),(7,2,12))
    t.block(7,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(7,2,12,DOOR,{"half":"upper","facing":"south"})
    # The raised canvas pod gets a four-step stair, landing, and divided sleeping loft.
    t.cuboid((12,4,12),(21,4,23),METAL); t.clear((12,4,8),(12,7,11))
    for y,z in enumerate(range(8,12),start=1): t.block(12,y,z,"minecraft:polished_blackstone_brick_stairs",{"facing":"south"})
    t.cuboid((12,5,17),(21,8,17),WALL); t.clear((16,5,17),(16,6,17))
    t.block(16,5,17,DOOR,{"half":"lower","facing":"south"}); t.block(16,6,17,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((14,10),(19,10),(14,21),(19,21)): t.block(x,14,z,METAL); t.block(x,13,z,LIGHT)
    for x,z in ((5,7),(10,7),(5,18),(10,18),(17,9),(19,19)): t.block(x,5 if x<12 else 9,z,METAL); t.block(x,4 if x<12 else 8,z,LIGHT)
    t.block(1,4,7,LIGHT)
    for x,z in ((15,14),(20,14),(15,22),(20,22)): t.block(x,5,z,METAL); t.block(x,4,z,LIGHT)
    t.block(10,1,23,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(12,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("patched vestibule",(12,1,3)); t.require_reachable("worker living",(7,1,17)); t.require_reachable("service passage",(16,1,8)); t.require_walk_region("lower habitation",(3,1,3),(13,1,25)); return t
def forge()->Template:
    t=Template(NATION,SETTLEMENT,"forge",(31,29,38),"building"); t.clear((0,0,0),(30,28,37)); t.cuboid((1,0,1),(29,0,36),GROUND); t.cuboid((2,1,2),(28,15,34),WALL); t.clear((3,1,3),(27,14,33)); t.cuboid((3,15,5),(16,19,32),METAL); t.cuboid((18,14,9),(28,23,29),WALL); t.cuboid((22,23,14),(27,28,25),METAL); t.cuboid((6,1,14),(13,5,25),"minecraft:blast_furnace"); t.cuboid((16,1,13),(24,2,15),"create:andesite_casing"); t.cuboid((17,3,14),(23,3,14),"create:shaft"); t.cuboid((4,5,2),(12,9,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Material receiving, hot forge floor, and finished-stock room.
    t.cuboid((3,1,10),(27,4,10),WALL); t.clear((15,1,10),(15,2,10))
    t.block(15,1,10,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,10,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,28),(27,4,28),WALL); t.clear((25,1,28),(25,2,28))
    t.block(25,1,28,DOOR,{"half":"lower","facing":"south"}); t.block(25,2,28,DOOR,{"half":"upper","facing":"south"})
    for x in (5,15,25):
        for z in (7,20,31): t.block(x,5,z,METAL); t.block(x,4,z,LIGHT)
    t.block(26,1,31,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("material receiving",(15,1,3)); t.require_reachable("furnace platform",(15,1,20)); t.require_reachable("finished stock",(25,1,31)); t.require_walk_region("forge floor",(3,1,3),(27,1,33)); return t
def mercenary_lodge()->Template:
    t=Template(NATION,SETTLEMENT,"mercenary_lodge",(31,24,36),"building"); t.clear((0,0,0),(30,23,35)); t.cuboid((1,0,1),(29,0,34),GROUND); t.cuboid((2,1,2),(28,14,32),WALL); t.clear((3,1,3),(27,13,31)); t.cuboid((3,14,5),(18,18,30),PATCH); t.cuboid((19,13,9),(28,21,27),METAL); t.cuboid((4,1,20),(10,6,29),METAL); t.clear((5,1,21),(9,5,28)); t.clear((10,1,23),(11,3,25)); t.cuboid((3,4,2),(26,7,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Guard vestibule and contract barracks are divided; the medical cage locks.
    t.cuboid((3,1,11),(27,4,11),WALL); t.clear((15,1,11),(15,2,11))
    t.block(15,1,11,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,11,DOOR,{"half":"upper","facing":"south"})
    t.block(10,1,24,DOOR,{"half":"lower","facing":"east"}); t.block(10,2,24,DOOR,{"half":"upper","facing":"east"})
    for x in (6,15,24):
        for z in (7,17,29): t.block(x,5,z,METAL); t.block(x,4,z,LIGHT)
    t.block(7,4,25,LIGHT); t.block(7,5,25,METAL); t.block(25,1,29,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("guarded entrance",(15,1,3)); t.require_reachable("contract records",(15,1,17)); t.require_reachable("medical store",(24,1,29)); t.require_walk_region("barracks ground floor",(3,1,3),(27,1,31)); return t
def provision_store()->Template:
    t=Template(NATION,SETTLEMENT,"provision_store",(29,20,32),"building"); t.clear((0,0,0),(28,19,31)); t.cuboid((1,0,1),(27,0,30),GROUND); t.cuboid((2,1,2),(26,11,28),WALL); t.clear((3,1,3),(25,10,27)); t.cuboid((3,11,5),(17,15,26),PATCH); t.cuboid((16,13,8),(26,18,23),METAL); t.cuboid((1,6,1),(27,8,8),METAL); t.cuboid((5,1,14),(23,2,16),"create:andesite_casing"); t.cuboid((6,3,15),(22,3,15),"create:belt"); t.block(14,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((14,1,2),(14,2,2))
    # Loading counter, powered ration line, and ration store form three rooms.
    t.cuboid((3,1,11),(25,4,11),WALL); t.clear((14,1,11),(14,2,11))
    t.block(14,1,11,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,11,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,23),(25,4,23),WALL); t.clear((23,1,23),(23,2,23))
    t.block(23,1,23,DOOR,{"half":"lower","facing":"south"}); t.block(23,2,23,DOOR,{"half":"upper","facing":"south"})
    for x in (5,14,23):
        for z in (8,19,26): t.block(x,5,z,METAL); t.block(x,4,z,LIGHT)
    t.block(1,4,4,LIGHT); t.block(27,4,4,LIGHT)
    t.block(24,1,25,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(14,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("loading counter",(14,1,3)); t.require_reachable("distribution line",(14,1,19)); t.require_reachable("ration store",(23,1,25)); t.require_walk_region("distribution floor",(3,1,3),(25,1,27)); return t

def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),canvas_house(),forge(),mercenary_lodge(),provision_store()]; validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Kazdel settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Kazdel templates")
if __name__=="__main__": main()
