"""Independent Iberia salt-wind low town, drainage court, and old-port settlement builder."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview

NATION="iberia"; SETTLEMENT="iberia_coastal_town"; ROAD="minecraft:tuff_bricks"; GROUND="zinecraft:iberia_salt_crusted_gravel"; WALL="zinecraft:iberia_coastal_masonry"; SALT="minecraft:calcite"; DARK="minecraft:weathered_copper"; WOOD="minecraft:stripped_spruce_log"; GLASS="minecraft:cyan_stained_glass"; LIGHT="minecraft:sea_lantern"; DOOR="minecraft:spruce_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,18,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((4,0,4),(27,0,27),GROUND); t.cuboid((6,1,6),(25,2,25),WALL); t.clear((8,1,8),(23,2,23)); t.cuboid((9,1,9),(22,1,22),"minecraft:water"); t.cuboid((12,2,12),(19,4,19),SALT); t.cuboid((14,5,14),(17,10,17),DARK); t.cuboid((8,3,7),(11,8,12),WALL); t.cuboid((20,3,18),(24,11,24),SALT); t.cuboid((19,12,17),(25,14,25),DARK); t.cuboid((21,15,20),(23,17,22),"minecraft:lightning_rod"); t.block(8,4,15,LIGHT); t.block(8,5,15,SALT); t.block(23,4,16,LIGHT); t.block(23,5,16,SALT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,12,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),GROUND); t.cuboid((20,0,0),(24,0,31),GROUND); t.cuboid((9,0,0),(10,0,31),"minecraft:water"); t.cuboid((21,0,0),(22,0,31),"minecraft:water"); t.cuboid((7,1,4),(8,3,27),WALL); t.cuboid((23,1,7),(24,5,24),SALT); t.cuboid((5,7,14),(26,9,17),WOOD); t.cuboid((9,10,15),(22,11,16),DARK); t.block(9,5,8,LIGHT); t.block(9,6,8,WOOD); t.block(22,6,24,LIGHT); t.block(22,7,24,WOOD); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,14,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((5,0,5),(11,0,27),GROUND); t.cuboid((20,0,20),(28,0,27),GROUND); t.cuboid((9,0,5),(10,0,24),"minecraft:water"); t.cuboid((20,0,21),(27,0,22),"minecraft:water"); t.cuboid((6,1,19),(11,6,26),WALL); t.cuboid((5,7,18),(13,9,27),SALT); t.cuboid((21,1,23),(27,4,26),DARK); t.cuboid((24,5,24),(26,11,25),WOOD); t.block(10,5,18,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,13,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z,h in ((8,8,4),(22,8,7),(8,22,9),(22,22,5)): t.cuboid((x,0,z),(x+1,0,z+1),GROUND); t.cuboid((x,1,z),(x,h,z),WOOD); t.block(x,h+1,z,LIGHT)
    t.cuboid((4,11,15),(27,12,16),"minecraft:chain")
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t

def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,16,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((4,0,21),(27,0,30),GROUND); t.cuboid((6,1,23),(25,7,29),WALL); t.clear((9,1,23),(22,5,28)); t.cuboid((5,8,22),(18,10,30),SALT); t.cuboid((17,7,24),(26,12,29),DARK); t.cuboid((20,13,25),(24,15,28),SALT); t.block(9,6,23,LIGHT); t.block(22,6,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def saltstone_house()->Template:
    t=Template(NATION,SETTLEMENT,"saltstone_house",(23,20,32),"building"); t.clear((0,0,0),(22,19,31)); t.cuboid((1,0,1),(21,0,30),GROUND); t.cuboid((2,1,2),(20,10,28),WALL); t.clear((3,1,3),(19,9,27)); t.cuboid((11,7,12),(19,7,25),WOOD); t.cuboid((3,11,5),(16,14,26),SALT); t.cuboid((14,11,9),(21,16,23),WALL); t.cuboid((16,17,12),(19,19,20),DARK); t.cuboid((4,4,2),(7,5,2),GLASS); t.block(11,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(11,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(4,11): t.block(x,x-3,9,SALT)
    t.block(11,4,7,LIGHT); t.block(11,5,7,SALT); t.block(11,4,21,LIGHT); t.block(11,5,21,SALT); t.block(9,9,9,LIGHT); t.block(9,10,9,SALT); t.block(18,1,25,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(11,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("salt porch",(11,1,3)); t.require_reachable("wind room",(11,1,20)); t.require_reachable("roof stair",(10,8,9)); return t

def shipwright()->Template:
    t=Template(NATION,SETTLEMENT,"shipwright",(31,27,46),"building"); t.clear((0,0,0),(30,26,45)); t.cuboid((1,0,1),(29,0,44),GROUND); t.cuboid((2,1,2),(28,13,42),WALL); t.clear((3,1,3),(27,12,41)); t.cuboid((4,1,19),(25,3,23),"create:andesite_casing"); t.cuboid((6,4,20),(23,4,22),"create:belt"); t.clear((14,1,19),(16,3,23)); t.cuboid((7,8,27),(24,8,39),WOOD); t.cuboid((3,14,5),(20,18,39),SALT); t.cuboid((18,14,10),(29,23,35),DARK); t.cuboid((22,24,15),(27,26,29),WOOD); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in range(5,12): t.block(x,x-4,11,SALT)
    for x,z in ((7,8),(15,8),(23,8),(7,28),(15,28),(23,28),(15,39)): t.block(x,4,z,LIGHT); t.block(x,5,z,WOOD)
    t.block(10,9,11,LIGHT); t.block(10,10,11,WOOD); t.block(25,1,38,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("slipway gate",(15,1,3)); t.require_reachable("keel shed",(15,1,29)); t.require_reachable("rigging stair",(11,8,11)); return t

def fish_market()->Template:
    t=Template(NATION,SETTLEMENT,"fish_market",(29,22,40),"building"); t.clear((0,0,0),(28,21,39)); t.cuboid((1,0,1),(27,0,38),GROUND); t.cuboid((2,1,2),(26,11,36),SALT); t.clear((3,1,3),(25,10,35)); t.cuboid((5,1,17),(22,2,19),WOOD); t.cuboid((6,1,25),(23,2,27),WOOD); t.cuboid((10,7,22),(24,7,34),DARK); t.cuboid((1,12,5),(20,15,34),WALL); t.cuboid((18,12,9),(27,18,30),SALT); t.cuboid((21,19,14),(25,21,25),DARK); t.block(14,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,1,DOOR,{"half":"upper","facing":"south"})
    for z in range(6,13): t.block(21,z-5,z,SALT)
    for x,z in ((7,8),(14,8),(22,8),(7,23),(14,23),(22,23),(14,33)): t.block(x,4,z,LIGHT); t.block(x,5,z,WOOD)
    t.block(20,9,12,LIGHT); t.block(20,10,12,WOOD); t.block(24,1,33,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(14,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("drain threshold",(14,1,3)); t.require_reachable("auction aisle",(14,1,31)); t.require_reachable("salt loft stair",(21,8,12)); return t

def inquisitor_office()->Template:
    t=Template(NATION,SETTLEMENT,"inquisitor_office",(27,31,42),"building"); t.clear((0,0,0),(26,30,41)); t.cuboid((1,0,1),(25,0,40),GROUND); t.cuboid((2,1,2),(24,15,38),WALL); t.clear((3,1,3),(23,14,37)); t.cuboid((6,1,21),(20,2,24),DARK); t.cuboid((8,9,25),(22,9,35),WOOD); t.cuboid((3,16,5),(17,22,35),SALT); t.cuboid((15,16,9),(25,26,31),WALL); t.cuboid((18,27,14),(23,30,26),DARK); t.cuboid((5,6,2),(21,10,2),GLASS); t.block(13,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(13,2,1,DOOR,{"half":"upper","facing":"south"})
    for z in range(7,15): t.block(19,z-6,z,SALT)
    for x,z in ((6,8),(13,8),(20,8),(6,22),(13,22),(20,22),(6,35),(13,35),(20,35)): t.block(x,5,z,LIGHT); t.block(x,6,z,WOOD)
    t.block(19,11,14,LIGHT); t.block(19,12,14,WOOD); t.block(21,1,35,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(13,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("narrow gate",(13,1,3)); t.require_reachable("evidence chamber",(13,1,29)); t.require_reachable("watch stair",(19,9,14)); return t

def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),saltstone_house(),shipwright(),fish_market(),inquisitor_office()]; validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Iberia settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Iberia templates")
if __name__=="__main__": main()
