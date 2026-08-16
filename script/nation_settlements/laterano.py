"""Independent Laterano fixed-origin monastery-town settlement builder."""
from __future__ import annotations
from .common import AIR, JIGSAW, Template, output_argument, validate_nation, write_preview
NATION="laterano"; SETTLEMENT="laterano_monastery_town"; ROAD="minecraft:quartz_bricks"; GROUND="zinecraft:laterano_alluvial_chalk"; WALL="zinecraft:laterano_basilica_marble"; GOLD="minecraft:gold_block"; GLASS="minecraft:light_blue_stained_glass"; LIGHT="minecraft:sea_lantern"; DOOR="minecraft:birch_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,18,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((4,0,4),(27,0,27),WALL); t.cuboid((7,0,7),(24,0,24),ROAD); t.cuboid((10,0,10),(21,0,21),"minecraft:water"); t.cuboid((13,1,13),(18,4,18),WALL); t.cuboid((14,5,14),(17,15,17),WALL); t.cuboid((15,16,15),(16,17,16),GOLD)
    for x,z in ((5,5),(26,5),(5,26),(26,26)): t.cuboid((x,1,z),(x,5,z),WALL); t.block(x,6,z,LIGHT)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,1,z),(x,3,z),GOLD); t.block(x,4,z,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,11,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),WALL); t.cuboid((20,0,0),(24,0,31),WALL)
    for z in (3,9,15,21,27): t.cuboid((9,1,z),(9,4,z),WALL); t.block(9,5,z,LIGHT); t.cuboid((22,1,z),(22,4,z),WALL); t.block(22,5,z,LIGHT)
    t.cuboid((6,7,14),(25,8,17),WALL); t.cuboid((8,9,15),(23,10,16),GOLD); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((7,0,7),(11,0,25),WALL); t.cuboid((20,0,20),(27,0,24),WALL); t.cuboid((8,1,21),(11,6,25),WALL); t.clear((9,1,21),(10,4,23)); t.cuboid((7,7,12),(21,8,15),WALL); t.block(10,6,13,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,9,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,0,z),(x+2,0,z+2),WALL); t.cuboid((x+1,1,z+1),(x+1,4,z+1),GOLD); t.block(x+1,5,z+1,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,13,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),WALL); t.cuboid((7,1,23),(24,7,29),WALL); t.clear((9,1,23),(11,5,28)); t.clear((14,1,23),(17,5,28)); t.clear((20,1,23),(22,5,28)); t.cuboid((6,8,22),(25,9,30),WALL); t.cuboid((10,10,24),(21,11,28),GOLD); t.block(9,6,23,LIGHT); t.block(22,6,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def white_residence()->Template:
    t=Template(NATION,SETTLEMENT,"white_residence",(31,29,34),"building"); t.clear((0,0,0),(30,28,33)); t.cuboid((1,0,1),(29,0,32),GROUND); t.cuboid((2,1,2),(28,16,30),WALL); t.clear((3,1,3),(27,15,29)); t.cuboid((8,1,13),(22,1,25),GROUND); t.cuboid((10,0,15),(20,0,23),"minecraft:water"); t.cuboid((3,16,5),(14,22,28),WALL); t.cuboid((17,16,8),(28,25,26),WALL); t.cuboid((21,25,12),(26,28,22),GOLD)
    for x in (4,9,14,19,24): t.clear((x,1,2),(x+1,5,3)); t.cuboid((x,6,2),(x+1,8,2),WALL)
    t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,3))
    # Arcade vestibule, shared loggia, and domestic store use two interior doors.
    t.cuboid((3,1,12),(27,4,12),WALL); t.clear((7,1,12),(7,2,12))
    t.block(7,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(7,2,12,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,24),(27,4,24),WALL); t.clear((7,1,24),(7,2,24))
    t.block(7,1,24,DOOR,{"half":"lower","facing":"south"}); t.block(7,2,24,DOOR,{"half":"upper","facing":"south"})
    # Paired marble steps make both sides of the raised loggia part of the route.
    t.block(8,1,17,"minecraft:quartz_stairs",{"facing":"east"}); t.block(22,1,17,"minecraft:quartz_stairs",{"facing":"east"})
    # The residential gallery has a real upper floor and eight-step marble stair.
    t.cuboid((3,8,13),(27,8,28),WALL); t.clear((4,8,5),(4,11,12))
    for y,z in enumerate(range(5,13),start=1): t.block(4,y,z,"minecraft:quartz_stairs",{"facing":"south"})
    t.cuboid((3,9,21),(27,12,21),WALL); t.clear((15,9,21),(15,10,21))
    t.block(15,9,21,DOOR,{"half":"lower","facing":"south"}); t.block(15,10,21,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((7,15),(16,15),(24,15),(7,27),(16,27),(24,27)): t.block(x,15,z,WALL); t.block(x,14,z,LIGHT)
    for x in (6,15,24):
        for z in (7,17,27): t.block(x,5,z,WALL); t.block(x,4,z,LIGHT)
    t.block(25,1,27,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("arcade vestibule",(15,1,3)); t.require_reachable("shared loggia",(7,1,17)); t.require_reachable("domestic store",(24,1,27)); t.require_walk_region("arcade residence floor",(3,1,3),(27,1,29)); return t
def confectionery()->Template:
    t=Template(NATION,SETTLEMENT,"confectionery",(27,20,30),"building"); t.clear((0,0,0),(26,19,29)); t.cuboid((1,0,1),(25,0,28),GROUND); t.cuboid((2,1,2),(24,11,26),WALL); t.clear((3,1,3),(23,10,25)); t.cuboid((3,11,5),(16,14,24),WALL); t.cuboid((15,13,8),(24,18,22),WALL); t.cuboid((1,7,1),(25,9,7),GOLD); t.cuboid((3,3,2),(22,6,2),GLASS); t.cuboid((5,1,15),(20,2,17),"create:andesite_casing"); t.cuboid((6,3,16),(19,3,16),"create:shaft"); t.block(13,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(13,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((13,1,2),(13,2,2))
    # Display hall, powered confectionery line, and ingredient store.
    t.cuboid((3,1,11),(23,4,11),WALL); t.clear((13,1,11),(13,2,11))
    t.block(13,1,11,DOOR,{"half":"lower","facing":"south"}); t.block(13,2,11,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,21),(23,4,21),WALL); t.clear((21,1,21),(21,2,21))
    t.block(21,1,21,DOOR,{"half":"lower","facing":"south"}); t.block(21,2,21,DOOR,{"half":"upper","facing":"south"})
    for x in (5,13,21):
        for z in (8,19,24): t.block(x,5,z,WALL); t.block(x,4,z,LIGHT)
    t.block(1,4,4,LIGHT); t.block(25,4,4,LIGHT)
    t.block(22,1,23,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(13,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("display hall",(13,1,3)); t.require_reachable("production line",(13,1,19)); t.require_reachable("ingredient store",(21,1,23)); t.require_walk_region("confectionery floor",(3,1,3),(23,1,25)); return t
def notary_office()->Template:
    t=Template(NATION,SETTLEMENT,"notary_office",(31,32,38),"building"); t.clear((0,0,0),(30,31,37)); t.cuboid((1,0,1),(29,0,36),GROUND); t.cuboid((2,1,2),(28,17,34),WALL); t.clear((3,1,3),(27,16,33)); t.cuboid((3,17,5),(18,23,32),WALL); t.cuboid((18,17,9),(28,27,29),WALL); t.cuboid((21,27,13),(26,31,24),GOLD); t.cuboid((4,1,21),(11,6,31),"create:andesite_casing"); t.clear((5,1,22),(10,5,30)); t.clear((11,1,25),(12,3,27))
    for x in (4,9,14,19,24): t.clear((x,4,2),(x+1,9,2)); t.cuboid((x,10,2),(x+1,12,2),WALL)
    t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Public waiting hall and interview counter precede the controlled record cage.
    t.cuboid((3,1,12),(27,4,12),WALL); t.clear((15,1,12),(15,2,12))
    t.block(15,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,12,DOOR,{"half":"upper","facing":"south"})
    t.block(11,1,26,DOOR,{"half":"lower","facing":"east"}); t.block(11,2,26,DOOR,{"half":"upper","facing":"east"})
    for x in (6,15,24):
        for z in (7,18,31): t.block(x,5,z,WALL); t.block(x,4,z,LIGHT)
    t.block(8,4,26,LIGHT); t.block(8,5,26,WALL); t.block(25,1,31,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("public waiting hall",(15,1,3)); t.require_reachable("interview counter",(15,1,18)); t.require_reachable("records dispatch",(24,1,31)); t.require_walk_region("public institution floor",(3,1,3),(27,1,33)); return t
def bell_chapel()->Template:
    t=Template(NATION,SETTLEMENT,"bell_chapel",(31,31,40),"building"); t.clear((0,0,0),(30,30,39)); t.cuboid((1,0,1),(29,0,38),GROUND); t.cuboid((2,1,2),(28,15,36),WALL); t.clear((3,1,3),(27,14,35)); t.cuboid((3,15,5),(13,21,33),WALL); t.cuboid((17,15,8),(28,20,31),WALL); t.cuboid((11,15,17),(19,26,28),WALL); t.cuboid((13,26,19),(17,30,26),GOLD); t.cuboid((4,4,2),(12,10,2),GLASS); t.cuboid((18,4,2),(27,9,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Service lobby, clinic wing, and classroom store are separately closable.
    t.cuboid((3,1,12),(27,4,12),WALL); t.clear((15,1,12),(15,2,12))
    t.block(15,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,12,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,28),(27,4,28),WALL); t.clear((24,1,28),(24,2,28))
    t.block(24,1,28,DOOR,{"half":"lower","facing":"south"}); t.block(24,2,28,DOOR,{"half":"upper","facing":"south"})
    for x in (6,15,24):
        for z in (7,19,32): t.block(x,5,z,WALL); t.block(x,4,z,LIGHT)
    t.block(25,1,33,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("shared service lobby",(15,1,3)); t.require_reachable("clinic wing",(23,1,19)); t.require_reachable("classroom store",(24,1,33)); t.require_walk_region("chapel clinic floor",(3,1,3),(27,1,35)); return t

def _assert_fixed_origin_center(t:Template)->None:
    assert t.name=="center" and t.size[0]==32 and t.size[2]==32
    connectors=[nbt for _,nbt in t.blocks.values() if nbt and nbt.get("id")==JIGSAW and str(nbt.get("target","")).endswith("/street")]
    assert len(connectors)==4 and all(nbt["pool"]==f"zinecraft:{SETTLEMENT}/streets" for nbt in connectors)
def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),white_residence(),confectionery(),notary_office(),bell_chapel()]; _assert_fixed_origin_center(ts[0]); validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Laterano fixed-origin settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Laterano templates")
if __name__=="__main__": main()
