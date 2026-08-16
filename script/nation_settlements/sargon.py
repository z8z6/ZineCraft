"""Independent Menat-only Sargon settlement builder; not a national desert kit."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview
NATION="sargon"; SETTLEMENT="sargon_oasis_town"; CONTEXT="menat_only"; ROAD="minecraft:smooth_sandstone"; GROUND="zinecraft:sargon_desert_crust"; WALL="minecraft:cut_sandstone"; SHADE="minecraft:chiseled_sandstone"; WATER="minecraft:water"; ACCENT="minecraft:oxidized_cut_copper"; GLASS="minecraft:cyan_stained_glass"; LIGHT="minecraft:ochre_froglight"; DOOR="minecraft:acacia_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,18,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((4,0,4),(27,0,27),WALL); t.cuboid((7,0,7),(24,0,24),ROAD); t.cuboid((10,0,10),(21,0,21),WATER); t.cuboid((12,1,12),(19,5,19),WALL); t.cuboid((10,6,10),(21,9,21),SHADE); t.cuboid((12,10,12),(19,16,19),WALL); t.cuboid((14,17,14),(17,17,17),ACCENT)
    for x,z in ((5,5),(26,5),(5,26),(26,26)): t.cuboid((x,1,z),(x,5,z),ACCENT); t.block(x,6,z,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,12,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((8,0,0),(11,0,31),WATER); t.cuboid((20,0,0),(23,0,31),WALL); t.cuboid((6,7,14),(25,9,17),SHADE)
    for z in (4,12,20,28): t.cuboid((9,1,z),(9,5,z),ACCENT); t.block(9,6,z,LIGHT); t.cuboid((22,1,z),(22,5,z),ACCENT); t.block(22,6,z,LIGHT)
    t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,12,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((8,0,7),(11,0,25),WATER); t.cuboid((20,0,20),(27,0,24),WALL); t.cuboid((8,1,21),(11,7,25),WALL); t.cuboid((7,8,12),(21,10,15),SHADE); t.block(10,7,13,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,0,z),(x+2,0,z+2),WATER); t.cuboid((x+1,1,z+1),(x+1,4,z+1),ACCENT); t.block(x+1,5,z+1,LIGHT)
    t.cuboid((5,7,15),(26,9,16),SHADE)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,15,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),WALL); t.cuboid((7,1,23),(24,8,29),WALL); t.clear((10,1,23),(21,6,28)); t.cuboid((6,9,22),(17,12,30),SHADE); t.cuboid((16,8,24),(25,13,29),WALL); t.block(9,7,23,LIGHT); t.block(22,7,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def adobe_house()->Template:
    t=Template(NATION,SETTLEMENT,"adobe_house",(31,24,34),"building"); t.clear((0,0,0),(30,23,33)); t.cuboid((1,0,1),(29,0,32),GROUND); t.cuboid((2,1,2),(28,14,30),WALL); t.clear((3,1,3),(27,13,29)); t.cuboid((15,1,15),(26,1,27),WATER); t.cuboid((3,14,5),(16,18,28),SHADE); t.cuboid((17,16,8),(28,22,25),WALL); t.cuboid((21,22,12),(26,23,21),ACCENT); t.cuboid((1,8,4),(18,10,10),SHADE); t.cuboid((3,4,2),(26,7,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((6,7),(14,7),(23,7),(6,18),(13,18),(6,27),(13,27)): t.block(x,5,z,WALL); t.block(x,4,z,LIGHT)
    t.block(13,1,27,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("Menat residence entry",(15,1,3)); t.require_reachable("shaded living wing",(9,1,18)); t.require_reachable("water-court store",(12,1,27)); t.require_walk_region("Menat residence floor",(3,1,3),(14,1,29)); return t
def spice_market()->Template:
    t=Template(NATION,SETTLEMENT,"spice_market",(31,23,44),"building"); t.clear((0,0,0),(30,22,43)); t.cuboid((1,0,1),(29,0,42),GROUND); t.cuboid((2,1,2),(28,12,40),WALL); t.clear((3,1,3),(27,11,39)); t.cuboid((2,13,5),(18,16,38),SHADE); t.cuboid((16,15,9),(28,21,35),WALL); t.cuboid((1,8,1),(29,10,10),SHADE); t.cuboid((5,1,15),(25,2,17),ACCENT); t.cuboid((5,1,29),(25,2,31),ACCENT); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in (5,12,20,26):
        for z in (7,20,34,39): t.block(x,5,z,WALL); t.block(x,4,z,LIGHT)
    t.block(26,1,38,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("bazaar threshold",(15,1,3)); t.require_reachable("shaded goods lane",(15,1,22)); t.require_reachable("controlled goods store",(25,1,38)); t.require_walk_region("Menat market floor",(3,1,3),(27,1,39)); return t
def caravanserai()->Template:
    t=Template(NATION,SETTLEMENT,"caravanserai",(31,27,46),"building"); t.clear((0,0,0),(30,26,45)); t.cuboid((1,0,1),(29,0,44),GROUND); t.cuboid((2,1,2),(28,14,42),WALL); t.clear((3,1,3),(27,13,41)); t.cuboid((8,1,17),(22,1,32),ROAD); t.cuboid((10,0,19),(20,0,30),WATER); t.cuboid((3,14,5),(12,21,39),SHADE); t.cuboid((19,14,8),(28,24,36),WALL); t.cuboid((21,24,13),(27,26,31),ACCENT); t.cuboid((3,4,2),(26,8,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    for x in (5,15,25):
        for z in (7,17,34,40): t.block(x,5,z,WALL); t.block(x,4,z,LIGHT)
    t.block(25,1,40,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("transit reception",(15,1,3)); t.require_reachable("regional transit court",(15,2,24)); t.require_reachable("traveller stores",(24,1,40)); t.require_walk_region("Menat transit floor",(3,1,3),(27,1,41)); return t
def well_house()->Template:
    t=Template(NATION,SETTLEMENT,"well_house",(29,29,36),"building"); t.clear((0,0,0),(28,28,35)); t.cuboid((1,0,1),(27,0,34),GROUND); t.cuboid((2,1,2),(26,14,32),WALL); t.clear((3,1,3),(25,13,31)); t.cuboid((8,1,14),(20,1,27),WATER); t.cuboid((3,14,5),(16,19,30),SHADE); t.cuboid((16,14,9),(26,24,27),WALL); t.cuboid((20,24,13),(25,28,23),ACCENT); t.cuboid((5,1,18),(7,6,26),"create:andesite_casing"); t.cuboid((3,4,2),(23,8,2),GLASS); t.block(14,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(14,2,1,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((5,7),(14,7),(23,7),(5,16),(23,16),(5,29),(14,29),(23,29)): t.block(x,5,z,WALL); t.block(x,4,z,LIGHT)
    t.block(23,1,29,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(14,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("public water entry",(14,1,3)); t.require_reachable("pump control",(4,1,18)); t.require_reachable("emergency store",(22,1,29)); t.require_walk_region("water-house dry floor",(3,1,3),(7,1,31)); t.require_walk_region("water distribution gallery",(21,1,3),(25,1,31)); return t

def _assert_menat_only(ts:list[Template])->None:
    assert CONTEXT=="menat_only" and all(t.settlement==SETTLEMENT for t in ts); assert all("jungle" not in name for t in ts for name,_ in t.palette)
def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),adobe_house(),spice_market(),caravanserai(),well_house()]; _assert_menat_only(ts); validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Menat-only Sargon settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Menat templates")
if __name__=="__main__": main()
