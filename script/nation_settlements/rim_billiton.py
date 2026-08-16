"""Independent Rim Billiton steel-jungle mining settlement builder."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview
NATION="rim_billiton"; SETTLEMENT="rim_billiton_mining_camp"; ROAD="minecraft:cobbled_deepslate"; GROUND="zinecraft:rim_billiton_mine_tailings"; WALL="zinecraft:rim_billiton_corrugated_steel"; STEEL="minecraft:raw_iron_block"; RUST="minecraft:weathered_copper"; GLASS="minecraft:gray_stained_glass"; LIGHT="minecraft:ochre_froglight"; DOOR="minecraft:iron_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,18,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((5,0,5),(26,0,26),GROUND); t.cuboid((8,1,8),(23,4,23),WALL); t.clear((10,1,10),(21,3,21)); t.cuboid((13,1,13),(18,12,18),STEEL); t.cuboid((10,13,10),(21,16,21),WALL); t.cuboid((5,8,14),(26,10,17),STEEL); t.block(8,7,15,LIGHT); t.block(23,7,16,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,14,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((8,0,0),(11,0,31),GROUND); t.cuboid((20,0,0),(23,0,31),GROUND); t.cuboid((6,8,14),(25,10,17),STEEL); t.cuboid((9,11,15),(22,12,16),WALL)
    for z in (4,12,20,28): t.cuboid((9,1,z),(9,5,z),STEEL); t.block(9,6,z,LIGHT); t.cuboid((22,1,z),(22,5,z),STEEL); t.block(22,6,z,LIGHT)
    t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,13,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((7,0,7),(11,0,25),GROUND); t.cuboid((20,0,20),(27,0,24),GROUND); t.cuboid((8,1,21),(11,8,25),WALL); t.cuboid((7,9,13),(21,11,16),STEEL); t.block(10,8,14,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,12,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,0,z),(x+2,0,z+2),GROUND); t.cuboid((x+1,1,z+1),(x+1,5,z+1),STEEL); t.block(x+1,6,z+1,LIGHT)
    t.cuboid((5,8,15),(26,10,16),STEEL)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,15,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),GROUND); t.cuboid((7,1,23),(24,8,29),WALL); t.clear((10,1,23),(21,6,28)); t.cuboid((6,9,22),(17,12,30),STEEL); t.cuboid((16,8,24),(25,13,29),RUST); t.block(9,7,23,LIGHT); t.block(22,7,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def miner_bunkhouse()->Template:
    t=Template(NATION,SETTLEMENT,"miner_bunkhouse",(31,24,34),"building"); t.clear((0,0,0),(30,23,33)); t.cuboid((1,0,1),(29,0,32),GROUND); t.cuboid((2,1,2),(28,13,30),WALL); t.clear((3,1,3),(27,12,29)); t.cuboid((3,13,5),(18,17,28),WALL); t.cuboid((17,15,8),(28,21,26),STEEL); t.cuboid((22,21,12),(27,23,22),RUST); t.cuboid((1,7,4),(20,9,9),STEEL); t.cuboid((3,4,2),(26,7,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Mud-room, bunk hall, and medical locker are separated by steel doors.
    t.cuboid((3,1,12),(27,4,12),WALL); t.clear((15,1,12),(15,2,12))
    t.block(15,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,12,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,24),(27,4,24),WALL); t.clear((24,1,24),(24,2,24))
    t.block(24,1,24,DOOR,{"half":"lower","facing":"south"}); t.block(24,2,24,DOOR,{"half":"upper","facing":"south"})
    # Seven industrial steps rise to the workers' sleeping loft.
    t.cuboid((3,7,13),(27,7,29),STEEL); t.clear((4,7,6),(4,10,12))
    for y,z in enumerate(range(6,13),start=1): t.block(4,y,z,"minecraft:deepslate_tile_stairs",{"facing":"south"})
    t.cuboid((3,8,21),(27,10,21),WALL); t.clear((15,8,21),(15,9,21))
    t.block(15,8,21,DOOR,{"half":"lower","facing":"south"}); t.block(15,9,21,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((7,15),(16,15),(24,15),(7,27),(16,27),(24,27)): t.block(x,12,z,STEEL); t.block(x,11,z,LIGHT)
    for x in (6,15,24):
        for z in (7,17,27): t.block(x,5,z,STEEL); t.block(x,4,z,LIGHT)
    t.block(1,4,6,LIGHT)
    t.block(25,1,27,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("mud boot vestibule",(15,1,3)); t.require_reachable("shared bunk hall",(15,1,17)); t.require_reachable("medical locker",(24,1,27)); t.require_walk_region("bunkhouse floor",(3,1,3),(27,1,29)); return t
def ore_workshop()->Template:
    t=Template(NATION,SETTLEMENT,"ore_workshop",(31,34,44),"building"); t.clear((0,0,0),(30,33,43)); t.cuboid((1,0,1),(29,0,42),GROUND); t.cuboid((2,1,2),(28,16,40),WALL); t.clear((3,1,3),(27,15,39)); t.cuboid((3,16,5),(14,22,37),STEEL); t.cuboid((17,16,8),(28,27,34),WALL); t.cuboid((21,27,13),(27,33,29),RUST); t.cuboid((5,1,14),(11,8,25),"create:andesite_casing"); t.cuboid((14,1,13),(25,3,16),"create:andesite_casing"); t.cuboid((15,4,14),(24,4,15),"create:shaft"); t.clear((11,1,18),(13,4,21)); t.cuboid((4,5,2),(12,10,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Ore receiving, screening hall, and product store use dust-control doors.
    t.cuboid((3,1,11),(27,4,11),WALL); t.clear((15,1,11),(15,2,11))
    t.block(15,1,11,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,11,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,31),(27,4,31),WALL); t.clear((25,1,31),(25,2,31))
    t.block(25,1,31,DOOR,{"half":"lower","facing":"south"}); t.block(25,2,31,DOOR,{"half":"upper","facing":"south"})
    for x in (5,15,25):
        for z in (7,20,34,39): t.block(x,5,z,STEEL); t.block(x,4,z,LIGHT)
    t.block(9,5,13,STEEL); t.block(9,4,13,LIGHT)
    t.block(8,4,20,LIGHT); t.block(8,5,20,STEEL); t.block(9,4,27,LIGHT); t.block(9,5,27,STEEL); t.block(26,1,38,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("ore receiving",(15,1,3)); t.require_reachable("screening bypass",(13,1,20)); t.require_reachable("product store",(25,1,38)); t.require_walk_region("processing floor",(3,1,3),(27,1,39)); return t
def freight_depot()->Template:
    t=Template(NATION,SETTLEMENT,"freight_depot",(31,29,46),"building"); t.clear((0,0,0),(30,28,45)); t.cuboid((1,0,1),(29,0,44),GROUND); t.cuboid((2,1,2),(28,15,42),WALL); t.clear((3,1,3),(27,14,41)); t.cuboid((3,15,5),(18,20,39),STEEL); t.cuboid((19,15,9),(28,25,35),WALL); t.cuboid((22,25,14),(27,28,30),RUST); t.cuboid((5,1,14),(25,2,17),"create:andesite_casing"); t.cuboid((6,3,15),(24,3,16),"create:belt"); t.cuboid((1,8,22),(29,10,26),STEEL); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Freight reception, crossline loading hall, and dispatch store.
    t.cuboid((3,1,12),(27,4,12),WALL); t.clear((15,1,12),(15,2,12))
    t.block(15,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,12,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,35),(27,4,35),WALL); t.clear((25,1,35),(25,2,35))
    t.block(25,1,35,DOOR,{"half":"lower","facing":"south"}); t.block(25,2,35,DOOR,{"half":"upper","facing":"south"})
    for x in (6,15,24):
        for z in (7,19,32,40): t.block(x,5,z,STEEL); t.block(x,4,z,LIGHT)
    for x in (1,29): t.block(x,4,24,LIGHT)
    t.block(26,1,40,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("freight reception",(15,1,3)); t.require_reachable("crossline loading",(15,1,24)); t.require_reachable("dispatch store",(25,1,40)); t.require_walk_region("freight floor",(3,1,3),(27,1,41)); return t
def canteen()->Template:
    t=Template(NATION,SETTLEMENT,"canteen",(29,20,36),"building"); t.clear((0,0,0),(28,19,35)); t.cuboid((1,0,1),(27,0,34),GROUND); t.cuboid((2,1,2),(26,11,32),WALL); t.clear((3,1,3),(25,10,31)); t.cuboid((3,11,5),(18,15,29),WALL); t.cuboid((17,13,9),(26,18,27),STEEL); t.cuboid((1,7,1),(27,9,8),RUST); t.cuboid((5,1,20),(22,2,22),"create:andesite_casing"); t.cuboid((6,3,21),(21,3,21),"create:belt"); t.block(14,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((14,1,2),(14,2,2))
    # Queue vestibule, shift dining room, and relief-supply store.
    t.cuboid((3,1,11),(25,4,11),WALL); t.clear((14,1,11),(14,2,11))
    t.block(14,1,11,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,11,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,26),(25,4,26),WALL); t.clear((23,1,26),(23,2,26))
    t.block(23,1,26,DOOR,{"half":"lower","facing":"south"}); t.block(23,2,26,DOOR,{"half":"upper","facing":"south"})
    for x in (5,14,23):
        for z in (8,18,29): t.block(x,5,z,STEEL); t.block(x,4,z,LIGHT)
    t.block(1,4,4,LIGHT); t.block(27,4,4,LIGHT)
    t.block(24,1,29,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(14,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("queue vestibule",(14,1,3)); t.require_reachable("shift dining",(14,1,18)); t.require_reachable("relief supplies",(23,1,29)); t.require_walk_region("canteen floor",(3,1,3),(25,1,31)); return t

def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),miner_bunkhouse(),ore_workshop(),freight_depot(),canteen()]; validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Rim Billiton settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Rim Billiton templates")
if __name__=="__main__": main()
