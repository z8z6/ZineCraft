"""Independent Kjerag contour village and mountain-rail settlement builder."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview

NATION="kjerag"; SETTLEMENT="kjerag_mountain_village"; ROAD="minecraft:stone_bricks"; GROUND="zinecraft:kjerag_sacred_snowstone"; WALL="zinecraft:kjerag_monastery_stone"; WOOD="minecraft:dark_oak_log"; PLANK="minecraft:spruce_planks"; ROOF="minecraft:deepslate_tile_stairs"; GLASS="minecraft:white_stained_glass"; LIGHT="minecraft:shroomlight"; DOOR="minecraft:spruce_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,20,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((4,0,5),(27,0,26),GROUND); t.cuboid((6,1,8),(25,2,23),WALL); t.clear((8,1,10),(23,2,21)); t.cuboid((8,3,9),(23,3,22),PLANK); t.cuboid((6,4,8),(25,4,23),ROOF); t.cuboid((9,5,10),(22,5,21),ROOF); t.cuboid((12,6,12),(19,6,19),ROOF); t.cuboid((14,7,14),(17,15,17),WOOD); t.cuboid((10,16,12),(21,18,19),ROOF); t.block(8,3,15,LIGHT); t.block(23,3,16,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,16,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),GROUND); t.cuboid((20,0,0),(24,0,31),GROUND); t.cuboid((8,1,2),(9,3,29),WALL); t.cuboid((22,1,5),(23,5,26),WALL); t.cuboid((5,8,13),(26,10,18),PLANK); t.cuboid((7,11,14),(24,12,17),ROOF); t.cuboid((10,13,15),(21,14,16),"minecraft:iron_bars"); t.block(10,5,8,LIGHT); t.block(10,6,8,WOOD); t.block(21,7,24,LIGHT); t.block(21,8,24,WOOD); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,14,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((5,0,6),(11,0,26),GROUND); t.cuboid((20,0,20),(27,0,27),GROUND); t.cuboid((6,1,17),(10,6,25),WALL); t.cuboid((5,7,16),(12,8,26),ROOF); t.cuboid((19,1,21),(26,3,26),PLANK); t.cuboid((20,4,22),(25,7,25),WOOD); t.block(10,5,17,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,13,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z,h in ((8,8,4),(22,8,6),(8,22,8),(22,22,5)): t.cuboid((x,0,z),(x+1,0,z+1),GROUND); t.cuboid((x,1,z),(x,h,z),WOOD); t.block(x,h+1,z,LIGHT)
    t.cuboid((4,10,15),(27,11,16),"minecraft:iron_bars")
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,17,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),GROUND); t.cuboid((7,1,23),(24,6,29),WALL); t.clear((10,1,23),(21,5,28)); t.cuboid((5,7,22),(26,8,30),ROOF); t.cuboid((8,9,24),(21,11,28),ROOF); t.cuboid((12,12,25),(17,15,27),WOOD); t.block(9,5,23,LIGHT); t.block(22,5,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def stone_chalet()->Template:
    t=Template(NATION,SETTLEMENT,"stone_chalet",(25,22,32),"building"); t.clear((0,0,0),(24,21,31)); t.cuboid((1,0,1),(23,0,30),GROUND); t.cuboid((2,1,2),(22,11,28),WALL); t.clear((3,1,3),(21,10,27)); t.cuboid((11,7,8),(21,7,25),PLANK); t.cuboid((1,12,4),(23,13,26),ROOF); t.cuboid((3,14,6),(21,15,24),ROOF); t.cuboid((6,16,9),(18,17,21),ROOF); t.cuboid((9,18,12),(15,20,18),WOOD); t.cuboid((4,4,2),(9,6,2),GLASS); t.block(12,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(12,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(5,11): t.block(x,x-4,9,PLANK)
    t.block(12,4,8,LIGHT); t.block(12,5,8,WOOD); t.block(16,10,15,LIGHT); t.block(16,11,15,WOOD); t.block(19,1,24,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(12,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("snow porch",(12,1,3)); t.require_reachable("hearth room",(12,1,18)); t.require_reachable("sleeping loft",(12,8,9)); return t

def tea_workshop()->Template:
    t=Template(NATION,SETTLEMENT,"tea_workshop",(29,27,40),"building"); t.clear((0,0,0),(28,26,39)); t.cuboid((1,0,1),(27,0,38),GROUND); t.cuboid((2,1,2),(26,13,36),WALL); t.clear((3,1,3),(25,12,35)); t.cuboid((4,1,18),(10,4,31),"create:andesite_casing"); t.cuboid((5,5,19),(9,5,30),"minecraft:composter"); t.cuboid((12,8,12),(24,8,33),PLANK); t.cuboid((1,14,5),(19,16,35),ROOF); t.cuboid((15,14,9),(27,20,30),WOOD); t.cuboid((18,21,13),(25,25,25),ROOF); t.block(14,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,1,DOOR,{"half":"upper","facing":"south"})
    for z in range(6,13): t.block(20,z-5,z,PLANK)
    t.block(20,8,11,AIR); t.block(20,8,12,AIR)
    t.block(14,4,9,LIGHT); t.block(14,5,9,WOOD); t.block(17,4,25,LIGHT); t.block(17,5,25,WOOD); t.block(19,11,17,LIGHT); t.block(19,12,17,WOOD); t.block(24,1,33,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(14,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("goods porch",(14,1,3)); t.require_reachable("drying room",(15,1,23)); t.require_reachable("sorting gallery",(20,9,13)); return t

def caravan_post()->Template:
    t=Template(NATION,SETTLEMENT,"caravan_post",(31,30,46),"building"); t.clear((0,0,0),(30,29,45)); t.cuboid((1,0,1),(29,0,44),GROUND); t.cuboid((2,1,2),(28,14,42),WALL); t.clear((3,1,3),(27,13,41)); t.cuboid((3,1,15),(27,2,18),"create:andesite_casing"); t.cuboid((4,3,16),(26,3,17),"create:belt"); t.clear((14,1,15),(16,2,18)); t.cuboid((7,9,20),(19,9,39),PLANK); t.cuboid((1,15,4),(22,17,39),ROOF); t.cuboid((19,15,10),(29,23,35),WOOD); t.cuboid((21,24,15),(27,28,30),ROOF); t.cuboid((4,7,2),(25,10,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(6,13): t.block(x,x-5,12,PLANK)
    t.block(15,4,8,LIGHT); t.block(15,5,8,WOOD); t.block(15,4,27,LIGHT); t.block(15,5,27,WOOD); t.block(12,10,12,LIGHT); t.block(12,11,12,WOOD); t.block(25,1,38,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("rail vestibule",(15,1,3)); t.require_reachable("transshipment lane",(15,1,26)); t.require_reachable("dispatch stair",(12,8,12)); return t

def shrine_house()->Template:
    t=Template(NATION,SETTLEMENT,"shrine_house",(27,25,36),"building"); t.clear((0,0,0),(26,24,35)); t.cuboid((1,0,1),(25,0,34),GROUND); t.cuboid((3,1,2),(23,12,32),WALL); t.clear((4,1,3),(22,11,31)); t.cuboid((5,7,15),(21,7,29),PLANK); t.cuboid((1,13,5),(25,14,29),ROOF); t.cuboid((4,15,8),(22,17,26),ROOF); t.cuboid((8,18,11),(18,20,23),ROOF); t.cuboid((11,21,14),(15,23,20),WOOD); t.cuboid((9,1,18),(17,2,21),PLANK); t.block(13,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(13,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(5,11): t.block(x,x-4,10,PLANK)
    t.block(13,4,8,LIGHT); t.block(13,5,8,WOOD); t.block(13,4,23,LIGHT); t.block(13,5,23,WOOD); t.block(10,9,10,LIGHT); t.block(10,10,10,WOOD); t.block(20,1,29,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(13,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("monk service door",(13,1,3)); t.require_reachable("petition room",(13,1,25)); t.require_reachable("archive stair",(10,7,10)); return t

def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),stone_chalet(),tea_workshop(),caravan_post(),shrine_house()]; validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Kjerag settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Kjerag templates")
if __name__=="__main__": main()
