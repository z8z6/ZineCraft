"""Independent Londinium street/support/industrial settlement builder."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview
NATION="victoria"; SETTLEMENT="victoria_industrial_borough"; CONTEXT="londinium_city"; ROAD="minecraft:polished_andesite"; GROUND="zinecraft:victoria_moorland_soil"; BRICK="zinecraft:victoria_industrial_brick"; FRAME="create:andesite_casing"; COPPER="minecraft:weathered_copper"; GLASS="minecraft:gray_stained_glass"; LIGHT="minecraft:sea_lantern"; DOOR="create:brass_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,18,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((4,0,4),(27,0,27),BRICK); t.cuboid((7,0,7),(24,0,24),ROAD); t.cuboid((10,1,10),(21,5,21),FRAME); t.clear((12,1,12),(19,4,19)); t.cuboid((13,1,13),(18,12,18),BRICK); t.cuboid((11,13,11),(20,15,20),COPPER); t.cuboid((14,16,14),(17,17,17),FRAME); t.cuboid((5,8,14),(26,10,17),FRAME); t.block(8,7,15,LIGHT); t.block(23,7,16,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,14,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),BRICK); t.cuboid((20,0,0),(24,0,31),BRICK); t.cuboid((6,8,14),(25,10,17),FRAME); t.cuboid((9,11,15),(22,12,16),COPPER)
    for z in (4,12,20,28): t.cuboid((9,1,z),(9,5,z),FRAME); t.block(9,6,z,LIGHT); t.cuboid((22,1,z),(22,5,z),FRAME); t.block(22,6,z,LIGHT)
    t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,13,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((7,0,7),(11,0,25),BRICK); t.cuboid((20,0,20),(27,0,24),BRICK); t.cuboid((8,1,21),(11,8,25),FRAME); t.cuboid((7,9,13),(21,11,16),FRAME); t.block(10,8,14,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,11,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,0,z),(x+2,0,z+2),BRICK); t.cuboid((x+1,1,z+1),(x+1,5,z+1),FRAME); t.block(x+1,6,z+1,LIGHT)
    t.cuboid((5,8,15),(26,10,16),FRAME)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,15,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),BRICK); t.cuboid((7,1,23),(24,8,29),BRICK); t.clear((10,1,23),(21,6,28)); t.cuboid((6,9,22),(17,12,30),COPPER); t.cuboid((16,8,24),(25,13,29),FRAME); t.block(9,7,23,LIGHT); t.block(22,7,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def brick_tenement()->Template:
    t=Template(NATION,SETTLEMENT,"brick_tenement",(31,34,34),"building"); t.clear((0,0,0),(30,33,33)); t.cuboid((1,0,1),(29,0,32),GROUND); t.cuboid((2,1,2),(28,18,30),BRICK); t.clear((3,1,3),(27,17,29)); t.cuboid((3,18,5),(16,25,28),BRICK); t.cuboid((17,18,8),(28,29,26),BRICK); t.cuboid((21,29,12),(26,33,22),COPPER); t.cuboid((1,10,5),(14,12,11),FRAME); t.cuboid((16,14,21),(29,16,27),FRAME); t.cuboid((3,4,2),(27,8,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in (6,15,24):
        for z in (7,17,27): t.block(x,5,z,FRAME); t.block(x,4,z,LIGHT)
    t.block(25,1,27,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("streetwall shop lobby",(15,1,3)); t.require_reachable("shared residential hall",(15,1,17)); t.require_reachable("service-axis store",(24,1,27)); t.require_walk_region("tenement lower floor",(3,1,3),(27,1,29)); return t
def steam_workshop()->Template:
    t=Template(NATION,SETTLEMENT,"steam_workshop",(31,31,44),"building"); t.clear((0,0,0),(30,30,43)); t.cuboid((1,0,1),(29,0,42),GROUND); t.cuboid((2,1,2),(28,16,40),BRICK); t.clear((3,1,3),(27,15,39)); t.cuboid((3,16,5),(12,21,37),COPPER); t.cuboid((14,18,8),(20,24,35),COPPER); t.cuboid((22,16,11),(28,27,32),FRAME); t.cuboid((24,27,15),(27,30,28),COPPER); t.cuboid((5,1,15),(24,2,17),FRAME); t.cuboid((6,3,16),(23,3,16),"create:shaft"); t.cuboid((1,9,20),(29,11,24),FRAME); t.cuboid((4,5,2),(26,10,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in (5,15,25):
        for z in (7,20,34,39): t.block(x,5,z,FRAME); t.block(x,4,z,LIGHT)
    t.block(10,5,27,FRAME); t.block(10,4,27,LIGHT)
    t.block(26,1,38,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("maintenance loading",(15,1,3)); t.require_reachable("powered machine aisle",(15,1,22)); t.require_reachable("parts and safety store",(25,1,38)); t.require_walk_region("mechanical workshop floor",(3,1,3),(27,1,39)); return t
def rail_warehouse()->Template:
    t=Template(NATION,SETTLEMENT,"rail_warehouse",(31,30,46),"building"); t.clear((0,0,0),(30,29,45)); t.cuboid((1,0,1),(29,0,44),GROUND); t.cuboid((2,1,2),(28,15,42),BRICK); t.clear((3,1,3),(27,14,41)); t.cuboid((3,15,5),(18,20,39),COPPER); t.cuboid((19,15,9),(28,26,35),BRICK); t.cuboid((22,26,14),(27,29,30),FRAME); t.cuboid((5,1,15),(25,2,18),FRAME); t.cuboid((6,3,16),(24,3,17),"create:belt"); t.cuboid((1,8,25),(29,11,29),FRAME); t.cuboid((4,1,32),(12,5,40),FRAME); t.clear((5,1,33),(11,4,39)); t.clear((12,1,35),(13,3,37)); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in (6,15,24):
        for z in (7,20,32,40): t.block(x,5,z,FRAME); t.block(x,4,z,LIGHT)
    t.block(8,4,36,LIGHT); t.block(8,5,36,FRAME); t.block(26,1,40,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("transport receiving",(15,1,3)); t.require_reachable("sorting and lift axis",(15,1,25)); t.require_reachable("dispatch store",(25,1,40)); t.require_walk_region("transport warehouse floor",(3,1,3),(27,1,41)); return t
def council_hall()->Template:
    t=Template(NATION,SETTLEMENT,"council_hall",(31,33,40),"building"); t.clear((0,0,0),(30,32,39)); t.cuboid((1,0,1),(29,0,38),GROUND); t.cuboid((2,1,2),(28,18,36),BRICK); t.clear((3,1,3),(27,17,35)); t.cuboid((3,18,5),(17,25,33),BRICK); t.cuboid((18,18,9),(28,28,30),BRICK); t.cuboid((21,28,13),(26,32,25),COPPER); t.cuboid((1,1,1),(29,1,8),ROAD); t.cuboid((3,5,2),(27,11,2),GLASS); t.cuboid((5,1,21),(11,6,31),FRAME); t.clear((6,1,22),(10,5,30)); t.clear((11,1,25),(12,3,27)); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in (6,15,24):
        for z in (7,19,33): t.block(x,5,z,FRAME); t.block(x,4,z,LIGHT)
    t.block(8,4,26,LIGHT); t.block(8,5,26,FRAME); t.block(25,1,33,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("public-service steps",(15,2,5)); t.require_reachable("district meeting hall",(15,1,19)); t.require_reachable("emergency records",(24,1,33)); t.require_walk_region("public service floor",(3,1,3),(27,1,35)); return t

def _assert_city_industrial_layers(ts:list[Template])->None:
    assert CONTEXT=="londinium_city"; assert all(BRICK in {name for name,_ in t.palette} for t in ts); workshop=next(t for t in ts if t.name=="steam_workshop"); assert FRAME in {name for name,_ in workshop.palette} and "create:shaft" in {name for name,_ in workshop.palette}
def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),brick_tenement(),steam_workshop(),rail_warehouse(),council_hall()]; _assert_city_industrial_layers(ts); validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Londinium industrial settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Victoria templates")
if __name__=="__main__": main()
