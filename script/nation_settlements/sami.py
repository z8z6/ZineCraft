"""Independent Sami migratory timber-platform settlement builder."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview
NATION="sami"; SETTLEMENT="sami_snowpriest_village"; ROAD="minecraft:spruce_planks"; GROUND="zinecraft:sami_frost_moss"; TIMBER="zinecraft:sami_tribal_timber"; PLANK="minecraft:stripped_spruce_log"; COVER="minecraft:brown_wool"; STONE="zinecraft:sami_ritual_stone"; LIGHT="minecraft:shroomlight"; DOOR="minecraft:spruce_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,13,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((6,0,6),(25,0,25),GROUND); t.cuboid((8,1,8),(23,1,23),TIMBER); t.clear((11,1,11),(20,1,20)); t.cuboid((13,0,13),(18,0,18),STONE); t.cuboid((14,1,14),(17,4,17),PLANK); t.cuboid((9,6,11),(22,8,20),COVER); t.cuboid((11,9,13),(20,11,18),COVER)
    for x,z in ((7,7),(24,7),(7,24),(24,24)): t.cuboid((x,1,z),(x,4,z),TIMBER); t.block(x,5,z,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),GROUND); t.cuboid((20,0,0),(24,0,31),GROUND); t.cuboid((8,1,0),(10,1,31),TIMBER); t.cuboid((21,1,0),(23,1,31),TIMBER)
    for z in (4,12,20,28): t.cuboid((9,2,z),(9,4,z),TIMBER); t.block(9,5,z,LIGHT); t.cuboid((22,2,z),(22,4,z),TIMBER); t.block(22,5,z,LIGHT)
    t.cuboid((6,6,14),(25,7,17),COVER); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((7,0,7),(11,0,25),GROUND); t.cuboid((20,0,20),(27,0,24),GROUND); t.cuboid((8,1,8),(10,1,24),TIMBER); t.cuboid((9,2,21),(9,5,24),TIMBER); t.cuboid((7,6,12),(21,7,15),COVER); t.block(9,5,13,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,9,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,0,z),(x+2,0,z+2),GROUND); t.cuboid((x+1,1,z+1),(x+1,4,z+1),TIMBER); t.block(x+1,5,z+1,LIGHT)
    t.cuboid((5,7,15),(26,8,16),COVER)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,12,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),GROUND); t.cuboid((7,1,23),(24,1,29),TIMBER); t.cuboid((8,2,24),(11,6,28),TIMBER); t.cuboid((15,2,23),(23,5,28),PLANK); t.cuboid((6,7,22),(17,9,30),COVER); t.cuboid((16,6,24),(25,10,29),COVER); t.block(9,6,23,LIGHT); t.block(22,5,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def snow_lodge()->Template:
    t=Template(NATION,SETTLEMENT,"snow_lodge",(25,17,30),"building"); t.clear((0,0,0),(24,16,29)); t.cuboid((1,0,1),(23,0,28),GROUND); t.cuboid((4,1,5),(21,1,27),TIMBER); t.cuboid((5,2,6),(20,10,25),TIMBER); t.clear((6,2,7),(19,9,24)); t.cuboid((2,8,4),(15,11,28),COVER); t.cuboid((12,10,7),(23,15,25),COVER); t.cuboid((11,1,1),(13,1,6),TIMBER); t.block(12,2,6,DOOR,{"half":"lower","facing":"south"}); t.block(12,3,6,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((7,9),(13,9),(18,9),(7,18),(13,18),(18,18),(7,24),(18,24)): t.block(x,6,z,TIMBER); t.block(x,5,z,LIGHT)
    t.block(18,2,23,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(12,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("raised ramp landing",(12,2,2)); t.require_reachable("migratory lodge platform",(12,2,7)); t.require_reachable("drying and sleeping",(18,2,22)); t.require_walk_region("raised lodge floor",(6,2,7),(19,2,24)); return t
def hunter_camp()->Template:
    t=Template(NATION,SETTLEMENT,"hunter_camp",(29,16,34),"building"); t.clear((0,0,0),(28,15,33)); t.cuboid((1,0,1),(27,0,32),GROUND); t.cuboid((3,1,5),(25,1,30),TIMBER); t.cuboid((4,2,6),(13,9,28),TIMBER); t.clear((5,2,7),(12,8,27)); t.cuboid((15,2,8),(25,7,27),PLANK); t.clear((16,2,9),(24,6,26)); t.cuboid((1,8,4),(17,11,31),COVER); t.cuboid((14,7,7),(27,13,28),COVER); t.cuboid((13,1,1),(15,1,7),TIMBER); t.block(14,2,6,DOOR,{"half":"lower","facing":"south"}); t.block(14,3,6,DOOR,{"half":"upper","facing":"south"}); t.block(10,2,6,DOOR,{"half":"lower","facing":"south"}); t.block(10,3,6,DOOR,{"half":"upper","facing":"south"}); t.block(20,2,8,DOOR,{"half":"lower","facing":"south"}); t.block(20,3,8,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((6,9),(11,9),(18,11),(23,11),(6,20),(11,20),(18,23),(23,23)): t.block(x,6,z,TIMBER); t.block(x,5,z,LIGHT)
    t.block(14,5,3,TIMBER); t.block(14,4,3,LIGHT); t.block(14,5,17,TIMBER); t.block(14,4,17,LIGHT)
    t.block(23,2,25,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(14,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("raised work-ramp landing",(14,2,2)); t.require_reachable("clean work shelter",(10,2,14)); t.require_reachable("observation equipment",(22,2,24)); t.require_walk_region("raised work platforms",(5,2,7),(24,2,27)); return t
def ritual_house()->Template:
    t=Template(NATION,SETTLEMENT,"ritual_house",(31,19,40),"building"); t.clear((0,0,0),(30,18,39)); t.cuboid((1,0,1),(29,0,38),GROUND); t.cuboid((3,1,5),(27,1,36),TIMBER); t.cuboid((4,2,6),(26,11,34),TIMBER); t.clear((5,2,7),(25,10,33)); t.cuboid((2,10,4),(18,14,37),COVER); t.cuboid((15,12,7),(29,17,34),COVER); t.cuboid((13,1,1),(17,1,7),TIMBER); t.block(15,2,6,DOOR,{"half":"lower","facing":"south"}); t.block(15,3,6,DOOR,{"half":"upper","facing":"south"}); t.cuboid((12,1,17),(18,1,23),STONE)
    for x in (6,15,24):
        for z in (9,20,31): t.block(x,6,z,TIMBER); t.block(x,5,z,LIGHT)
    t.block(24,2,31,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("raised assembly ramp",(15,2,2)); t.require_reachable("uncolumned gathering floor",(15,2,12)); t.require_reachable("injury-rest supplies",(23,2,31)); t.require_walk_region("raised assembly floor",(5,2,7),(25,2,33)); return t
def supply_shed()->Template:
    t=Template(NATION,SETTLEMENT,"supply_shed",(21,15,28),"building"); t.clear((0,0,0),(20,14,27)); t.cuboid((1,0,1),(19,0,26),GROUND); t.cuboid((3,1,6),(17,1,24),TIMBER); t.cuboid((4,2,7),(16,9,22),TIMBER); t.clear((5,2,8),(15,8,21)); t.cuboid((1,8,5),(12,11,26),COVER); t.cuboid((10,9,7),(19,13,23),COVER); t.cuboid((9,1,1),(11,1,8),TIMBER); t.block(10,2,7,DOOR,{"half":"lower","facing":"south"}); t.block(10,3,7,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((6,10),(10,10),(14,10),(6,19),(10,19),(14,19)): t.block(x,6,z,TIMBER); t.block(x,5,z,LIGHT)
    t.block(14,2,20,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(10,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("raised sled-ramp landing",(10,2,2)); t.require_reachable("dry-goods platform",(10,2,9)); t.require_reachable("fuel store",(13,2,20)); t.require_walk_region("raised supply floor",(5,2,8),(15,2,21)); return t

def _assert_migratory_timber(ts:list[Template])->None:
    for t in ts: assert TIMBER in {name for name,_ in t.palette}, f"{t.label}: missing Sami timber"
def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),snow_lodge(),hunter_camp(),ritual_house(),supply_shed()]; _assert_migratory_timber(ts); validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Sami migratory settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Sami templates")
if __name__=="__main__": main()
