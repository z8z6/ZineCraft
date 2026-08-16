"""Independent Ursus northern slab-block and cold-industry settlement builder."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview

NATION="ursus"; SETTLEMENT="ursus_northern_town"; ROAD="minecraft:polished_deepslate"; GROUND="zinecraft:ursus_permafrost"; WALL="zinecraft:ursus_imperial_masonry"; SLAB="minecraft:deepslate_tiles"; STEEL="minecraft:iron_block"; GLASS="minecraft:gray_stained_glass"; LIGHT="minecraft:sea_lantern"; DOOR="minecraft:iron_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,21,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((4,0,4),(27,0,27),GROUND); t.cuboid((7,1,7),(24,3,24),WALL); t.clear((10,1,10),(21,3,21)); t.cuboid((12,1,12),(19,2,19),STEEL); t.cuboid((14,3,14),(17,15,17),"create:andesite_casing"); t.cuboid((9,10,14),(22,12,17),STEEL); t.cuboid((11,16,11),(20,18,20),SLAB); t.cuboid((14,19,14),(17,20,17),"minecraft:campfire"); t.block(11,5,15,LIGHT); t.block(11,6,15,STEEL); t.block(20,5,16,LIGHT); t.block(20,6,16,STEEL)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,13,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),GROUND); t.cuboid((20,0,0),(24,0,31),GROUND); t.cuboid((7,1,3),(7,5,28),WALL); t.cuboid((24,1,7),(24,5,24),WALL); t.cuboid((5,7,8),(26,9,11),STEEL); t.cuboid((8,10,9),(23,11,10),"create:fluid_pipe"); t.block(9,5,7,LIGHT); t.block(9,6,7,STEEL); t.block(22,5,24,LIGHT); t.block(22,6,24,STEEL); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,15,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((5,0,5),(11,0,26),GROUND); t.cuboid((20,0,20),(27,0,26),GROUND); t.cuboid((6,1,19),(10,8,25),WALL); t.cuboid((7,9,20),(19,12,24),SLAB); t.cuboid((20,1,21),(26,4,25),STEEL); t.cuboid((9,13,21),(16,14,23),"create:fluid_pipe"); t.block(11,6,18,LIGHT); t.block(11,7,18,STEEL); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,14,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z,h in ((7,7,8),(22,7,6),(7,22,5),(22,22,9)): t.cuboid((x,0,z),(x+2,0,z+2),GROUND); t.cuboid((x+1,1,z+1),(x+1,h,z+1),STEEL); t.block(x+1,h+1,z+1,LIGHT)
    t.cuboid((5,11,15),(26,13,16),"create:fluid_pipe")
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,18,32),"street"); t.cuboid((12,0,0),(19,0,23),ROAD); t.cuboid((5,0,21),(26,0,30),GROUND); t.cuboid((6,1,23),(25,8,29),WALL); t.clear((9,1,23),(22,6,28)); t.cuboid((7,9,22),(17,13,30),SLAB); t.cuboid((17,7,24),(25,15,29),STEEL); t.cuboid((19,16,26),(23,17,28),"create:andesite_casing"); t.block(10,6,23,LIGHT); t.block(22,6,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def heated_house()->Template:
    t=Template(NATION,SETTLEMENT,"heated_house",(29,24,34),"building"); t.clear((0,0,0),(28,23,33)); t.cuboid((1,0,1),(27,0,32),GROUND); t.cuboid((2,1,2),(26,13,30),WALL); t.clear((3,1,3),(25,12,29)); t.cuboid((4,7,4),(24,7,28),SLAB); t.cuboid((3,14,5),(18,18,27),WALL); t.cuboid((16,14,9),(26,21,25),SLAB); t.cuboid((20,22,13),(25,23,21),STEEL); t.cuboid((5,4,2),(10,6,2),GLASS); t.cuboid((18,4,2),(23,6,2),GLASS); t.block(14,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,1,DOOR,{"half":"upper","facing":"south"}); t.cuboid((7,1,19),(10,3,23),"create:andesite_casing"); t.cuboid((8,4,20),(9,5,22),"create:blaze_burner")
    for x in range(5,11): t.block(x,x-4,10,SLAB)
    t.block(9,7,10,AIR); t.block(10,7,10,AIR)
    t.cuboid((11,7,8),(23,7,18),SLAB); t.block(14,4,10,LIGHT); t.block(14,5,10,STEEL); t.block(9,4,18,LIGHT); t.block(9,5,18,STEEL); t.block(19,10,13,LIGHT); t.block(19,11,13,STEEL); t.block(24,1,27,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(14,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("heated vestibule",(14,1,3)); t.require_reachable("radiator room",(9,1,18)); t.require_reachable("enclosed balcony landing",(12,8,10)); return t

def military_storehouse()->Template:
    t=Template(NATION,SETTLEMENT,"military_storehouse",(31,29,42),"building"); t.clear((0,0,0),(30,28,41)); t.cuboid((1,0,1),(29,0,40),GROUND); t.cuboid((2,1,2),(28,15,38),WALL); t.clear((3,1,3),(27,14,37)); t.cuboid((4,8,4),(26,8,36),STEEL); t.cuboid((3,16,6),(17,21,35),SLAB); t.cuboid((16,16,10),(28,25,31),WALL); t.cuboid((21,26,15),(27,28,26),STEEL); t.cuboid((5,1,20),(11,3,32),"create:andesite_casing"); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for z in range(6,12): t.block(8,z-5,z,SLAB)
    t.block(8,8,11,AIR)
    t.cuboid((9,8,12),(24,8,24),STEEL); t.block(15,4,9,LIGHT); t.block(15,5,9,STEEL); t.block(16,4,25,LIGHT); t.block(16,5,25,STEEL); t.block(18,11,18,LIGHT); t.block(18,12,18,STEEL); t.block(25,1,34,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("inspection gate",(15,1,3)); t.require_reachable("ordnance aisle",(16,1,25)); t.require_reachable("armoury stair",(8,7,11)); return t

def mine_office()->Template:
    t=Template(NATION,SETTLEMENT,"mine_office",(27,26,38),"building"); t.clear((0,0,0),(26,25,37)); t.cuboid((1,0,1),(25,0,36),GROUND); t.cuboid((2,1,2),(24,12,34),WALL); t.clear((3,1,3),(23,11,33)); t.cuboid((3,7,12),(21,7,31),SLAB); t.cuboid((4,13,4),(23,18,24),WALL); t.cuboid((2,15,23),(16,21,34),STEEL); t.cuboid((7,22,26),(13,25,32),"create:andesite_casing"); t.cuboid((5,1,20),(20,2,23),"create:andesite_casing"); t.cuboid((6,3,21),(19,3,22),"create:belt"); t.block(13,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(13,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(5,11): t.block(x,x-4,9,SLAB)
    t.block(9,7,9,AIR); t.block(10,7,9,AIR)
    t.cuboid((11,7,7),(21,7,16),SLAB); t.block(13,4,8,LIGHT); t.block(13,5,8,STEEL); t.block(16,10,13,LIGHT); t.block(16,11,13,STEEL); t.block(22,1,31,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(13,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("survey entry",(13,1,3)); t.require_reachable("ore plan table",(13,1,18)); t.require_reachable("foreman loft",(12,8,9)); return t

def communal_hall()->Template:
    t=Template(NATION,SETTLEMENT,"communal_hall",(31,25,46),"building"); t.clear((0,0,0),(30,24,45)); t.cuboid((1,0,1),(29,0,44),GROUND); t.cuboid((2,1,2),(28,13,42),WALL); t.clear((3,1,3),(27,12,41)); t.cuboid((5,7,18),(25,7,40),SLAB); t.cuboid((3,14,5),(20,19,38),WALL); t.cuboid((18,14,10),(28,22,33),SLAB); t.cuboid((8,20,9),(15,24,28),STEEL); t.cuboid((8,1,16),(22,2,18),"minecraft:spruce_planks"); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for z in range(7,13): t.block(6,z-6,z,SLAB)
    t.block(6,7,11,AIR); t.block(6,7,12,AIR); t.block(6,6,13,SLAB)
    t.cuboid((7,7,13),(24,7,25),SLAB)
    for x,z in ((8,8),(15,8),(22,8),(8,24),(15,24),(22,24),(8,37),(15,37),(22,37)): t.block(x,4,z,LIGHT); t.block(x,5,z,STEEL)
    t.block(25,1,39,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("warming lobby",(15,1,3)); t.require_reachable("assembly benches",(15,1,26)); t.require_reachable("public gallery",(8,8,13)); return t

def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),heated_house(),military_storehouse(),mine_office(),communal_hall()]; validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Ursus settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Ursus templates")
if __name__=="__main__": main()
