"""Independent Yan Shangshu stepped-street, courtyard, and cable-relay settlement builder."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview

NATION="yan"; SETTLEMENT="yan_shangshu_town"; ROAD="minecraft:polished_tuff"; GROUND="zinecraft:yan_mountain_soil"; WALL="zinecraft:yan_courtyard_brick"; WOOD="minecraft:dark_oak_log"; PLANK="minecraft:bamboo_planks"; TILE="minecraft:deepslate_tiles"; GLASS="minecraft:light_blue_stained_glass"; LIGHT="minecraft:shroomlight"; DOOR="minecraft:dark_oak_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,22,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((4,0,4),(27,0,27),GROUND); t.cuboid((6,1,7),(25,2,24),WALL); t.clear((9,1,10),(22,2,21)); t.cuboid((10,3,11),(21,4,20),PLANK); t.cuboid((8,5,9),(23,6,22),TILE); t.cuboid((11,7,12),(20,8,19),TILE); t.cuboid((14,9,14),(17,16,17),WOOD); t.cuboid((10,17,12),(21,19,19),TILE); t.cuboid((13,20,14),(18,21,17),PLANK); t.block(9,4,15,LIGHT); t.block(22,4,16,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,18,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),GROUND); t.cuboid((20,0,0),(24,0,31),GROUND)
    for z,y in ((4,1),(8,2),(12,3),(16,4),(20,3),(24,2),(28,1)): t.cuboid((9,y,z),(10,y+1,z+2),WALL); t.cuboid((21,y,z),(22,y+1,z+2),WALL)
    t.cuboid((5,10,13),(26,11,18),WOOD); t.cuboid((8,12,14),(23,13,17),TILE); t.cuboid((11,14,15),(20,15,16),"minecraft:chain"); t.block(10,6,7,LIGHT); t.block(10,7,7,WOOD); t.block(21,6,24,LIGHT); t.block(21,7,24,WOOD); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,16,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((5,0,5),(11,0,27),GROUND); t.cuboid((20,0,20),(28,0,27),GROUND); t.cuboid((6,1,18),(11,5,26),WALL); t.cuboid((5,6,17),(14,8,27),TILE); t.cuboid((20,1,21),(27,3,26),PLANK); t.cuboid((23,4,23),(25,12,24),WOOD); t.cuboid((18,13,22),(29,15,25),TILE); t.block(10,5,17,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,17,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z,h in ((8,8,6),(22,8,9),(8,22,11),(22,22,7)): t.cuboid((x,0,z),(x+1,0,z+1),GROUND); t.cuboid((x,1,z),(x,h,z),WOOD); t.block(x,h+1,z,LIGHT)
    t.cuboid((4,14,15),(27,16,16),"minecraft:chain")
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,20,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((4,0,21),(27,0,30),GROUND); t.cuboid((6,1,23),(25,7,29),WALL); t.clear((9,1,23),(22,5,28)); t.cuboid((4,8,22),(27,10,30),TILE); t.cuboid((8,11,24),(21,13,28),TILE); t.cuboid((12,14,25),(17,18,27),WOOD); t.block(9,6,23,LIGHT); t.block(22,6,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def courtyard_residence()->Template:
    t=Template(NATION,SETTLEMENT,"courtyard_residence",(31,25,38),"building"); t.clear((0,0,0),(30,24,37)); t.cuboid((1,0,1),(29,0,36),GROUND); t.cuboid((2,1,2),(28,12,34),WALL); t.clear((3,1,3),(27,11,33)); t.cuboid((9,0,15),(21,0,26),"minecraft:moss_block"); t.cuboid((10,1,16),(20,1,25),AIR); t.cuboid((4,13,5),(19,18,32),PLANK); t.cuboid((17,13,9),(29,21,28),WALL); t.cuboid((21,22,13),(27,24,24),TILE); t.cuboid((3,7,2),(9,9,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(5,12): t.block(x,x-4,10,PLANK)
    t.block(15,4,8,LIGHT); t.block(15,5,8,WOOD); t.block(7,4,22,LIGHT); t.block(7,5,22,WOOD); t.block(23,4,22,LIGHT); t.block(23,5,22,WOOD); t.block(10,9,10,LIGHT); t.block(10,10,10,WOOD); t.block(25,1,30,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("moon gate",(15,1,3)); t.require_reachable("open courtyard",(15,1,20)); t.require_reachable("family stair",(11,8,10)); return t

def tea_house()->Template:
    t=Template(NATION,SETTLEMENT,"tea_house",(27,23,36),"building"); t.clear((0,0,0),(26,22,35)); t.cuboid((1,0,1),(25,0,34),GROUND); t.cuboid((2,1,2),(24,11,32),WALL); t.clear((3,1,3),(23,10,31)); t.cuboid((5,1,18),(21,2,20),PLANK); t.cuboid((7,1,24),(19,2,26),PLANK); t.cuboid((12,7,13),(22,7,29),PLANK); t.cuboid((1,12,5),(25,14,29),TILE); t.cuboid((4,15,8),(22,17,26),TILE); t.cuboid((8,18,11),(18,21,23),WOOD); t.block(13,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(13,2,1,DOOR,{"half":"upper","facing":"south"})
    for z in range(6,13): t.block(20,z-5,z,PLANK)
    for x,z in ((7,8),(13,8),(19,8),(7,22),(13,22),(19,22)): t.block(x,4,z,LIGHT); t.block(x,5,z,WOOD)
    t.block(19,9,12,LIGHT); t.block(19,10,12,WOOD); t.block(21,1,29,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(13,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("street counter",(13,1,3)); t.require_reachable("tea salon",(13,1,28)); t.require_reachable("private stair",(20,8,12)); return t

def artisan_workshop()->Template:
    t=Template(NATION,SETTLEMENT,"artisan_workshop",(29,30,44),"building"); t.clear((0,0,0),(28,29,43)); t.cuboid((1,0,1),(27,0,42),GROUND); t.cuboid((2,1,2),(26,14,40),WALL); t.clear((3,1,3),(25,13,39)); t.cuboid((5,1,19),(11,4,31),"create:andesite_casing"); t.cuboid((13,1,18),(22,3,21),"minecraft:smithing_table"); t.cuboid((14,9,24),(24,9,37),PLANK); t.cuboid((3,15,5),(19,20,37),TILE); t.cuboid((17,15,9),(27,25,33),PLANK); t.cuboid((21,26,14),(26,29,28),WOOD); t.block(14,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(5,13): t.block(x,x-4,11,PLANK)
    t.block(14,4,8,LIGHT); t.block(14,5,8,WOOD); t.block(14,5,26,LIGHT); t.block(14,6,26,WOOD); t.block(11,10,11,LIGHT); t.block(11,11,11,WOOD); t.block(24,1,37,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(14,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("shop threshold",(14,1,3)); t.require_reachable("crafting lane",(14,1,28)); t.require_reachable("pattern stair",(12,9,11)); return t

def relay_office()->Template:
    t=Template(NATION,SETTLEMENT,"relay_office",(25,34,46),"building"); t.clear((0,0,0),(24,33,45)); t.cuboid((1,0,1),(23,0,44),GROUND); t.cuboid((2,1,2),(22,15,42),WALL); t.clear((3,1,3),(21,14,41)); t.cuboid((5,1,22),(19,2,25),PLANK); t.cuboid((8,9,27),(20,9,39),PLANK); t.cuboid((3,16,5),(17,23,39),TILE); t.cuboid((14,16,10),(23,28,35),WALL); t.cuboid((17,29,16),(21,33,29),"minecraft:chain"); t.cuboid((4,6,2),(20,10,2),GLASS); t.block(12,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(12,2,1,DOOR,{"half":"upper","facing":"south"})
    for z in range(7,15): t.block(18,z-6,z,PLANK)
    for x,z in ((6,8),(12,8),(18,8),(6,23),(12,23),(18,23),(6,37),(12,37),(18,37)): t.block(x,5,z,LIGHT); t.block(x,6,z,WOOD)
    t.block(18,11,14,LIGHT); t.block(18,12,14,WOOD); t.block(19,1,39,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(12,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("courier gate",(12,1,3)); t.require_reachable("dispatch maps",(12,1,29)); t.require_reachable("cable stair",(18,9,14)); return t

def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),courtyard_residence(),tea_house(),artisan_workshop(),relay_office()]; validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Yan Shangshu settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Yan templates")
if __name__=="__main__": main()
