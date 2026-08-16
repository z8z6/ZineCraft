"""Independent Kazimierz Grand Knight district settlement builder."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview
NATION="kazimierz"; SETTLEMENT="kazimierz_knight_borough"; ROAD="minecraft:smooth_stone"; GROUND="zinecraft:kazimierz_steppe_turf"; WALL="zinecraft:kazimierz_arena_masonry"; STEEL="minecraft:iron_block"; GLASS="minecraft:gray_stained_glass"; LIGHT="minecraft:ochre_froglight"; DOOR="minecraft:spruce_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,14,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((5,0,5),(26,0,26),WALL); t.cuboid((8,1,8),(23,5,23),WALL); t.clear((10,1,10),(21,4,21)); t.cuboid((6,7,10),(25,8,21),STEEL); t.cuboid((9,9,13),(22,11,18),STEEL); t.cuboid((13,6,13),(18,12,18),GLASS)
    for x,z in ((7,7),(24,7),(7,24),(24,24)): t.cuboid((x,1,z),(x,5,z),STEEL); t.block(x,6,z,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,11,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),WALL); t.cuboid((20,0,0),(24,0,31),WALL); t.cuboid((6,7,14),(25,8,17),STEEL)
    for z in (5,15,25): t.cuboid((9,1,z),(9,4,z),STEEL); t.block(9,5,z,LIGHT); t.cuboid((22,1,z),(22,4,z),STEEL); t.block(22,5,z,LIGHT)
    t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((7,0,7),(11,0,25),WALL); t.cuboid((20,0,20),(27,0,24),WALL); t.cuboid((8,1,21),(11,6,24),STEEL); t.cuboid((8,7,12),(21,8,15),STEEL); t.block(10,6,13,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,9,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,0,z),(x+2,0,z+2),WALL); t.cuboid((x+1,1,z+1),(x+1,4,z+1),STEEL); t.block(x+1,5,z+1,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,12,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),WALL); t.cuboid((7,1,23),(24,4,29),WALL); t.clear((10,1,23),(21,3,28)); t.cuboid((6,6,22),(25,7,30),STEEL); t.cuboid((10,8,24),(21,10,28),GLASS); t.block(9,5,23,LIGHT); t.block(22,5,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def tenement()->Template:
    t=Template(NATION,SETTLEMENT,"tenement",(31,34,34),"building"); t.clear((0,0,0),(30,33,33)); t.cuboid((1,0,1),(29,0,32),GROUND); t.cuboid((2,1,2),(28,17,31),WALL); t.clear((3,1,3),(27,16,30)); t.cuboid((4,17,5),(19,24,29),WALL); t.cuboid((17,17,8),(28,29,26),WALL); t.cuboid((21,29,12),(26,33,22),STEEL); t.cuboid((3,4,2),(27,7,2),GLASS); t.cuboid((1,9,5),(12,10,12),STEEL); t.cuboid((15,12,22),(29,13,28),STEEL); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Commercial lobby, residents' common room, and team equipment store.
    t.cuboid((3,1,12),(27,4,12),WALL); t.clear((15,1,12),(15,2,12))
    t.block(15,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,12,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,24),(27,4,24),WALL); t.clear((24,1,24),(24,2,24))
    t.block(24,1,24,DOOR,{"half":"lower","facing":"south"}); t.block(24,2,24,DOOR,{"half":"upper","facing":"south"})
    # An eight-step masonry stair serves a real competitors' sleeping loft.
    t.cuboid((3,8,14),(27,8,29),STEEL); t.clear((4,8,6),(4,11,13))
    for y,z in enumerate(range(6,14),start=1): t.block(4,y,z,"minecraft:polished_blackstone_brick_stairs",{"facing":"south"})
    t.cuboid((3,9,22),(27,12,22),WALL); t.clear((15,9,22),(15,10,22))
    t.block(15,9,22,DOOR,{"half":"lower","facing":"south"}); t.block(15,10,22,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((7,16),(16,16),(24,16),(7,27),(16,27),(24,27)): t.block(x,16,z,STEEL); t.block(x,15,z,LIGHT)
    for x in (6,15,24):
        for z in (7,17,27): t.block(x,5,z,STEEL); t.block(x,4,z,LIGHT)
    for x in (1,29):
        for z in (6,11,25,29): t.block(x,4,z,LIGHT)
    for x,z in ((4,15),(12,15),(20,15),(27,15),(4,28),(12,28),(20,28),(27,28)): t.block(x,14,z,STEEL); t.block(x,13,z,LIGHT)
    t.block(25,1,28,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("commercial lobby",(15,1,3)); t.require_reachable("shared residence",(8,1,17)); t.require_reachable("team store",(24,1,27)); t.require_walk_region("赛事公寓首层",(3,1,3),(27,1,30)); return t
def armor_workshop()->Template:
    t=Template(NATION,SETTLEMENT,"armor_workshop",(31,28,42),"building"); t.clear((0,0,0),(30,27,41)); t.cuboid((1,0,1),(29,0,40),GROUND); t.cuboid((2,1,2),(28,15,38),WALL); t.clear((3,1,3),(27,14,37)); t.cuboid((3,15,5),(14,19,36),STEEL); t.cuboid((17,15,8),(28,23,33),STEEL); t.cuboid((22,23,13),(27,27,28),STEEL); t.cuboid((5,1,14),(25,2,16),"create:andesite_casing"); t.cuboid((6,3,15),(24,3,15),"create:shaft"); t.cuboid((4,6,2),(12,10,2),GLASS); t.cuboid((18,6,2),(27,10,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Equipment receiving, maintenance bay, and controlled armour store.
    t.cuboid((3,1,11),(27,4,11),WALL); t.clear((15,1,11),(15,2,11))
    t.block(15,1,11,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,11,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,30),(27,4,30),WALL); t.clear((25,1,30),(25,2,30))
    t.block(25,1,30,DOOR,{"half":"lower","facing":"south"}); t.block(25,2,30,DOOR,{"half":"upper","facing":"south"})
    for x in (6,15,24):
        for z in (7,20,34): t.block(x,5,z,STEEL); t.block(x,4,z,LIGHT)
    t.block(15,5,28,STEEL); t.block(15,4,28,LIGHT)
    t.block(26,1,35,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("equipment receiving",(15,1,3)); t.require_reachable("unequal maintenance bays",(10,1,22)); t.require_reachable("controlled equipment store",(25,1,35)); t.require_walk_region("maintenance floor",(3,1,3),(27,1,37)); return t
def sponsor_shop()->Template:
    t=Template(NATION,SETTLEMENT,"sponsor_shop",(31,20,36),"building"); t.clear((0,0,0),(30,19,35)); t.cuboid((1,0,1),(29,0,34),GROUND); t.cuboid((2,1,2),(28,11,32),WALL); t.clear((3,1,3),(27,10,31)); t.cuboid((3,11,5),(21,14,30),WALL); t.cuboid((18,13,9),(28,18,27),STEEL); t.cuboid((1,7,1),(29,9,7),STEEL); t.cuboid((3,3,2),(27,6,2),GLASS); t.cuboid((4,1,22),(10,5,30),"create:andesite_casing"); t.clear((5,1,23),(9,4,29)); t.clear((10,1,25),(11,3,27)); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Retail gallery and fitting floor are divided; stock cage retains a staffed door.
    t.cuboid((3,1,11),(27,4,11),WALL); t.clear((15,1,11),(15,2,11))
    t.block(15,1,11,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,11,DOOR,{"half":"upper","facing":"south"})
    t.block(10,1,26,DOOR,{"half":"lower","facing":"east"}); t.block(10,2,26,DOOR,{"half":"upper","facing":"east"})
    for x in (6,15,24):
        for z in (8,18,29): t.block(x,5,z,STEEL); t.block(x,4,z,LIGHT)
    t.block(1,4,4,LIGHT); t.block(29,4,4,LIGHT)
    t.block(7,4,26,LIGHT); t.block(7,5,26,STEEL); t.block(3,4,28,LIGHT); t.block(3,5,28,STEEL); t.block(25,1,29,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("continuous retail gallery",(15,1,3)); t.require_reachable("fitting supply",(16,1,18)); t.require_reachable("rear stock route",(24,1,29)); t.require_walk_region("retail floor",(3,1,3),(27,1,31)); return t
def tournament_inn()->Template:
    t=Template(NATION,SETTLEMENT,"tournament_inn",(31,31,44),"building"); t.clear((0,0,0),(30,30,43)); t.cuboid((1,0,1),(29,0,42),GROUND); t.cuboid((2,1,2),(28,14,40),WALL); t.clear((3,1,3),(27,13,39)); t.cuboid((3,14,5),(11,26,37),WALL); t.cuboid((19,14,8),(28,22,34),WALL); t.cuboid((9,14,25),(21,18,40),WALL); t.cuboid((4,26,10),(10,30,31),STEEL); t.cuboid((20,22,12),(27,26,29),STEEL); t.cuboid((3,4,2),(26,8,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Team reception, public dining hall, and secure equipment deposit.
    t.cuboid((3,1,12),(27,4,12),WALL); t.clear((15,1,12),(15,2,12))
    t.block(15,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,12,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,32),(27,4,32),WALL); t.clear((24,1,32),(24,2,32))
    t.block(24,1,32,DOOR,{"half":"lower","facing":"south"}); t.block(24,2,32,DOOR,{"half":"upper","facing":"south"})
    for x in (6,15,24):
        for z in (7,19,31,38): t.block(x,5,z,STEEL); t.block(x,4,z,LIGHT)
    t.block(25,1,37,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("team reception",(15,1,3)); t.require_reachable("public dining",(15,1,19)); t.require_reachable("equipment deposit",(24,1,37)); t.require_walk_region("inn lower floor",(3,1,3),(27,1,39)); return t

def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),tenement(),armor_workshop(),sponsor_shop(),tournament_inn()]; validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Kazimierz settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Kazimierz templates")
if __name__=="__main__": main()
