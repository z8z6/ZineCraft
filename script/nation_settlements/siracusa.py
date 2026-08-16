"""Independent Siracusa rain-arcade settlement, bridging old borough and Volsinii street."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview

NATION="siracusa"; SETTLEMENT="siracusa_family_town"; ROAD="minecraft:polished_blackstone_bricks"; GROUND="zinecraft:siracusa_rain_darkened_soil"; WALL="zinecraft:siracusa_family_masonry"; BRICK="minecraft:bricks"; DARK="minecraft:polished_deepslate"; WOOD="minecraft:dark_oak_planks"; GLASS="minecraft:tinted_glass"; LIGHT="minecraft:ochre_froglight"; DOOR="minecraft:dark_oak_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,23,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((4,0,4),(27,0,27),GROUND); t.cuboid((6,1,6),(25,3,25),WALL); t.clear((9,1,9),(22,3,22)); t.cuboid((10,1,10),(21,1,21),"minecraft:water"); t.cuboid((13,2,13),(18,5,18),DARK); t.cuboid((15,6,15),(16,13,16),"minecraft:iron_bars"); t.cuboid((7,4,7),(11,14,11),BRICK); t.cuboid((20,4,8),(24,18,12),WALL); t.cuboid((8,15,8),(18,19,10),DARK); t.cuboid((18,19,9),(25,22,11),GLASS); t.block(9,4,13,LIGHT); t.block(9,5,13,DARK); t.block(22,4,18,LIGHT); t.block(22,5,18,DARK)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,18,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),GROUND); t.cuboid((20,0,0),(24,0,31),GROUND); t.cuboid((7,1,2),(9,8,29),WALL); t.cuboid((22,1,5),(24,11,26),BRICK); t.cuboid((5,9,3),(13,11,28),WOOD); t.cuboid((18,12,7),(27,14,25),DARK); t.cuboid((9,15,13),(23,17,18),GLASS); t.block(10,6,8,LIGHT); t.block(21,8,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,17,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((5,0,5),(11,0,27),GROUND); t.cuboid((20,0,20),(28,0,27),GROUND); t.cuboid((6,1,18),(11,9,26),WALL); t.clear((8,1,18),(9,4,26)); t.cuboid((5,10,17),(14,12,27),WOOD); t.cuboid((21,1,21),(27,6,26),BRICK); t.cuboid((23,7,22),(28,13,25),DARK); t.block(10,6,17,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,15,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z,h in ((7,7,5),(23,7,9),(7,23,7),(23,23,11)): t.cuboid((x,0,z),(x+1,0,z+1),GROUND); t.cuboid((x,1,z),(x,h,z),DARK); t.block(x,h+1,z,LIGHT)
    t.cuboid((4,13,15),(27,14,16),"minecraft:iron_bars")
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,21,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((4,0,21),(27,0,30),GROUND); t.cuboid((6,1,23),(25,10,29),WALL); t.clear((9,1,23),(22,7,28)); t.cuboid((5,11,22),(18,14,30),BRICK); t.cuboid((17,9,24),(26,17,29),DARK); t.cuboid((20,18,25),(25,20,28),GLASS); t.block(9,8,23,LIGHT); t.block(22,8,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def family_house()->Template:
    t=Template(NATION,SETTLEMENT,"family_house",(27,28,36),"building"); t.clear((0,0,0),(26,27,35)); t.cuboid((1,0,1),(25,0,34),GROUND); t.cuboid((2,1,2),(24,14,32),WALL); t.clear((3,1,3),(23,13,31)); t.cuboid((4,1,19),(9,5,26),WOOD); t.cuboid((3,15,5),(18,20,29),BRICK); t.cuboid((16,15,9),(25,24,27),DARK); t.cuboid((20,25,13),(24,27,23),"minecraft:stone_bricks"); t.cuboid((4,7,2),(10,10,2),GLASS); t.block(13,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(13,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(5,12): t.block(x,x-4,11,WOOD)
    t.block(13,4,8,LIGHT); t.block(13,5,8,DARK); t.block(13,4,23,LIGHT); t.block(13,5,23,DARK); t.block(10,9,11,LIGHT); t.block(10,10,11,DARK); t.block(21,1,29,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(13,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("family arcade",(13,1,3)); t.require_reachable("rain courtyard",(15,1,22)); t.require_reachable("upper stair",(11,8,11)); return t

def trattoria()->Template:
    t=Template(NATION,SETTLEMENT,"trattoria",(31,24,42),"building"); t.clear((0,0,0),(30,23,41)); t.cuboid((1,0,1),(29,0,40),GROUND); t.cuboid((2,1,2),(28,12,38),BRICK); t.clear((3,1,3),(27,11,37)); t.cuboid((5,1,20),(22,2,22),WOOD); t.cuboid((7,1,27),(11,4,34),"minecraft:bricks"); t.cuboid((12,7,24),(26,7,36),WOOD); t.cuboid((3,13,4),(21,17,35),WALL); t.cuboid((18,13,9),(29,21,31),DARK); t.cuboid((21,22,14),(27,23,26),BRICK); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for z in range(7,14): t.block(23,z-6,z,WOOD)
    for x,z in ((8,8),(16,8),(24,8),(8,20),(16,20),(24,20),(15,32)): t.block(x,4,z,LIGHT); t.block(x,5,z,DARK)
    t.block(25,1,35,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("awning entrance",(15,1,3)); t.require_reachable("dining hall",(15,1,25)); t.require_reachable("service stair",(23,8,13)); return t

def tailor_shop()->Template:
    t=Template(NATION,SETTLEMENT,"tailor_shop",(25,25,40),"building"); t.clear((0,0,0),(24,24,39)); t.cuboid((1,0,1),(23,0,38),GROUND); t.cuboid((2,1,2),(22,12,36),WALL); t.clear((3,1,3),(21,11,35)); t.cuboid((5,1,18),(18,2,20),"minecraft:loom"); t.cuboid((11,7,23),(20,7,34),WOOD); t.cuboid((4,13,4),(22,17,31),BRICK); t.cuboid((2,14,24),(15,21,36),DARK); t.cuboid((6,22,28),(12,24,34),GLASS); t.cuboid((4,5,2),(19,8,2),GLASS); t.block(12,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(12,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(4,11): t.block(x,x-3,10,WOOD)
    t.block(12,4,8,LIGHT); t.block(12,5,8,DARK); t.block(12,4,25,LIGHT); t.block(12,5,25,DARK); t.block(9,9,10,LIGHT); t.block(9,10,10,DARK); t.block(19,1,32,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(12,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("display arcade",(12,1,3)); t.require_reachable("cutting floor",(12,1,25)); t.require_reachable("pattern stair",(10,8,10)); return t

def meeting_hall()->Template:
    t=Template(NATION,SETTLEMENT,"meeting_hall",(31,34,46),"building"); t.clear((0,0,0),(30,33,45)); t.cuboid((1,0,1),(29,0,44),GROUND); t.cuboid((2,1,2),(28,16,42),WALL); t.clear((3,1,3),(27,15,41)); t.cuboid((7,1,17),(23,2,20),WOOD); t.cuboid((5,9,24),(25,9,39),DARK); t.cuboid((3,17,5),(18,24,39),BRICK); t.cuboid((16,17,9),(29,28,35),WALL); t.cuboid((21,29,14),(27,33,29),DARK); t.cuboid((5,7,2),(25,11,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(6,14): t.block(x,x-5,12,WOOD)
    for x,z in ((7,8),(15,8),(23,8),(7,22),(15,22),(23,22),(7,36),(15,36),(23,36)): t.block(x,5,z,LIGHT); t.block(x,6,z,DARK)
    t.block(13,11,12,LIGHT); t.block(13,12,12,DARK); t.block(25,1,39,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("family threshold",(15,1,3)); t.require_reachable("council chamber",(15,1,28)); t.require_reachable("observer stair",(13,9,12)); return t

def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),family_house(),trattoria(),tailor_shop(),meeting_hall()]; validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Siracusa settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Siracusa templates")
if __name__=="__main__": main()
