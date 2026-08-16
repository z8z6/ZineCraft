"""Independent Leithanien builder separating historic tower streets and acoustics."""
from __future__ import annotations
from .common import AIR, Template, output_argument, validate_nation, write_preview
NATION="leithanien"; SETTLEMENT="leithanien_music_town"; ROAD="minecraft:deepslate_tiles"; GROUND="zinecraft:leithanien_twilight_humus"; HISTORIC="minecraft:calcite"; ROOF="minecraft:polished_blackstone"; ACOUSTIC="zinecraft:leithanien_resonant_brick"; SILVER="minecraft:iron_block"; RED="minecraft:red_terracotta"; GLASS="minecraft:gray_stained_glass"; LIGHT="minecraft:sea_lantern"; DOOR="minecraft:spruce_door"

def center()->Template:
    t=Template(NATION,SETTLEMENT,"center",(32,18,32),"center"); t.cuboid((0,0,12),(31,0,19),ROAD); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((5,0,5),(26,0,26),HISTORIC); t.cuboid((8,0,8),(23,0,23),ROAD); t.cuboid((12,1,12),(19,9,19),HISTORIC); t.clear((14,1,14),(17,7,17)); t.cuboid((13,10,13),(18,13,18),ROOF); t.cuboid((15,14,15),(16,17,16),ROOF)
    for x,z in ((6,6),(25,6),(6,25),(25,25)): t.cuboid((x,1,z),(x,4,z),SILVER); t.block(x,5,z,LIGHT)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_straight()->Template:
    t=Template(NATION,SETTLEMENT,"street_straight",(32,13,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((7,0,0),(11,0,31),HISTORIC); t.cuboid((20,0,0),(24,0,31),HISTORIC)
    for z in (4,12,20,28): t.cuboid((9,1,z),(9,4,z),ROOF); t.block(9,5,z,LIGHT); t.cuboid((22,1,z),(22,4,z),ROOF); t.block(22,5,z,LIGHT)
    t.cuboid((8,7,0),(10,9,31),HISTORIC); t.cuboid((21,8,0),(23,10,31),HISTORIC); t.cuboid((7,10,14),(24,11,17),ROOF); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(16,1,31,"south",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_corner()->Template:
    t=Template(NATION,SETTLEMENT,"street_corner",(32,12,32),"street"); t.cuboid((12,0,0),(19,0,19),ROAD); t.cuboid((12,0,12),(31,0,19),ROAD); t.cuboid((7,0,7),(11,0,25),HISTORIC); t.cuboid((20,0,20),(27,0,24),HISTORIC); t.cuboid((8,1,21),(11,7,25),HISTORIC); t.cuboid((7,8,13),(21,10,16),ROOF); t.block(10,7,14,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(31,1,15,"east",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(15,1,31,"south",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t
def street_cross()->Template:
    t=Template(NATION,SETTLEMENT,"street_cross",(32,10,32),"street"); t.cuboid((12,0,0),(19,0,31),ROAD); t.cuboid((0,0,12),(31,0,19),ROAD)
    for x,z in ((8,8),(23,8),(8,23),(23,23)): t.cuboid((x,0,z),(x+2,0,z+2),HISTORIC); t.cuboid((x+1,1,z+1),(x+1,4,z+1),ROOF); t.block(x+1,5,z+1,LIGHT)
    t.cuboid((6,7,15),(25,8,16),ROOF)
    for d,p in (("north",(15,1,0)),("south",(16,1,31)),("west",(0,1,16)),("east",(31,1,15))): t.connector(*p,d,f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD)
    return t
def street_end()->Template:
    t=Template(NATION,SETTLEMENT,"street_end",(32,14,32),"street"); t.cuboid((12,0,0),(19,0,24),ROAD); t.cuboid((5,0,21),(26,0,30),HISTORIC); t.cuboid((7,1,23),(24,8,29),HISTORIC); t.clear((10,1,23),(21,6,28)); t.cuboid((6,9,22),(17,11,30),ROOF); t.cuboid((16,8,24),(25,12,29),ROOF); t.block(9,7,23,LIGHT); t.block(22,7,23,LIGHT); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/street",f"zinecraft:{SETTLEMENT}/streets",ROAD); t.connector(0,1,15,"west",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.connector(31,1,16,"east",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); return t

def twilight_house()->Template:
    t=Template(NATION,SETTLEMENT,"twilight_house",(31,34,36),"building"); t.clear((0,0,0),(30,33,35)); t.cuboid((1,0,1),(29,0,34),GROUND); t.cuboid((2,1,2),(28,17,32),HISTORIC); t.clear((3,1,3),(27,16,31)); t.cuboid((3,17,5),(14,24,30),HISTORIC); t.cuboid((17,17,8),(28,28,27),HISTORIC); t.cuboid((20,28,12),(25,33,23),ROOF); t.cuboid((1,18,7),(16,21,12),ROOF); t.cuboid((15,24,19),(29,27,27),ROOF); t.cuboid((3,4,2),(27,8,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Historic lobby, shared stair hall, and domestic store have explicit doors.
    t.cuboid((3,1,12),(27,4,12),HISTORIC); t.clear((15,1,12),(15,2,12))
    t.block(15,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,12,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,25),(27,4,25),HISTORIC); t.clear((24,1,25),(24,2,25))
    t.block(24,1,25,DOOR,{"half":"lower","facing":"south"}); t.block(24,2,25,DOOR,{"half":"upper","facing":"south"})
    # An eight-step stone stair reaches a divided domestic loft.
    t.cuboid((3,8,13),(27,8,30),HISTORIC); t.clear((4,8,5),(4,11,12))
    for y,z in enumerate(range(5,13),start=1): t.block(4,y,z,"minecraft:deepslate_brick_stairs",{"facing":"south"})
    t.cuboid((3,9,22),(27,12,22),HISTORIC); t.clear((15,9,22),(15,10,22))
    t.block(15,9,22,DOOR,{"half":"lower","facing":"south"}); t.block(15,10,22,DOOR,{"half":"upper","facing":"south"})
    for x,z in ((7,15),(16,15),(24,15),(7,28),(16,28),(24,28)): t.block(x,16,z,ROOF); t.block(x,15,z,LIGHT)
    for x in (6,15,24):
        for z in (7,18,29): t.block(x,5,z,ROOF); t.block(x,4,z,LIGHT)
    t.block(1,4,9,LIGHT)
    t.block(29,4,22,LIGHT); t.block(29,4,26,LIGHT)
    for x,z in ((4,15),(12,15),(20,15),(27,15),(4,28),(12,28),(20,28),(27,28)): t.block(x,14,z,ROOF); t.block(x,13,z,LIGHT)
    t.block(25,1,29,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("historic street lobby",(15,1,3)); t.require_reachable("shared stair hall",(15,1,18)); t.require_reachable("domestic store",(24,1,29)); t.require_walk_region("historic residence floor",(3,1,3),(27,1,31)); return t
def instrument_workshop()->Template:
    t=Template(NATION,SETTLEMENT,"instrument_workshop",(31,24,36),"building"); t.clear((0,0,0),(30,23,35)); t.cuboid((1,0,1),(29,0,34),GROUND); t.cuboid((2,1,2),(28,13,32),HISTORIC); t.clear((3,1,3),(27,12,31)); t.cuboid((3,13,5),(19,17,30),HISTORIC); t.cuboid((18,15,9),(28,21,27),ROOF); t.cuboid((22,21,13),(27,23,23),ROOF); t.cuboid((4,4,2),(26,9,2),GLASS); t.cuboid((5,1,16),(14,2,18),"create:andesite_casing"); t.cuboid((6,3,17),(13,3,17),"create:shaft"); t.cuboid((18,1,20),(24,5,28),ACOUSTIC); t.clear((19,1,21),(23,4,27)); t.clear((18,1,23),(18,3,25)); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Drawing reception opens into the craft floor; tuning booth has acoustic door.
    t.cuboid((3,1,11),(27,4,11),HISTORIC); t.clear((15,1,11),(15,2,11))
    t.block(15,1,11,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,11,DOOR,{"half":"upper","facing":"south"})
    t.block(18,1,24,DOOR,{"half":"lower","facing":"east"}); t.block(18,2,24,DOOR,{"half":"upper","facing":"east"})
    for x in (6,15,24):
        for z in (7,18,29): t.block(x,5,z,ROOF); t.block(x,4,z,LIGHT)
    t.block(21,4,24,LIGHT); t.block(21,5,24,ROOF); t.block(25,1,29,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("drawing reception",(15,1,3)); t.require_reachable("mixed craft floor",(15,1,18)); t.require_reachable("tuning room",(21,1,24)); t.require_walk_region("instrument workshop floor",(3,1,3),(27,1,31)); return t
def rehearsal_hall()->Template:
    t=Template(NATION,SETTLEMENT,"rehearsal_hall",(31,31,44),"building"); t.clear((0,0,0),(30,30,43)); t.cuboid((1,0,1),(29,0,42),GROUND); t.cuboid((2,1,2),(28,17,40),ROOF); t.clear((3,1,3),(27,16,39)); t.cuboid((3,17,5),(13,23,37),ACOUSTIC); t.cuboid((16,19,8),(28,27,34),ACOUSTIC); t.cuboid((20,27,13),(27,30,29),SILVER); t.cuboid((1,10,8),(12,13,19),RED); t.cuboid((18,14,23),(29,18,33),RED); t.cuboid((5,1,18),(25,2,21),SILVER); t.cuboid((6,3,19),(24,3,20),"create:shaft"); t.cuboid((4,5,2),(26,10,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Acoustic foyer, column-free rehearsal room, and instrument service room.
    t.cuboid((3,1,12),(27,4,12),ACOUSTIC); t.clear((15,1,12),(15,2,12))
    t.block(15,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,12,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,34),(27,4,34),ACOUSTIC); t.clear((24,1,34),(24,2,34))
    t.block(24,1,34,DOOR,{"half":"lower","facing":"south"}); t.block(24,2,34,DOOR,{"half":"upper","facing":"south"})
    for x in (6,15,24):
        for z in (7,19,32,38): t.block(x,5,z,SILVER); t.block(x,4,z,LIGHT)
    for x in (1,29):
        for z in (10,16,26,32): t.block(x,4,z,LIGHT)
    t.block(25,1,37,"minecraft:barrel",nbt={"id":"minecraft:barrel","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("acoustic foyer",(15,1,3)); t.require_reachable("column-free rehearsal floor",(15,1,24)); t.require_reachable("instrument service",(24,1,37)); t.require_walk_region("acoustic rehearsal floor",(3,1,3),(27,1,39)); return t
def arts_academy()->Template:
    t=Template(NATION,SETTLEMENT,"arts_academy",(31,34,46),"building"); t.clear((0,0,0),(30,33,45)); t.cuboid((1,0,1),(29,0,44),GROUND); t.cuboid((2,1,2),(28,15,42),HISTORIC); t.clear((3,1,3),(27,14,41)); t.cuboid((8,1,17),(22,1,31),GROUND); t.cuboid((10,0,19),(20,0,29),ROAD); t.cuboid((3,15,5),(12,23,39),HISTORIC); t.cuboid((19,15,8),(28,20,36),HISTORIC); t.cuboid((12,15,28),(22,25,42),ACOUSTIC); t.cuboid((21,20,12),(28,30,28),ROOF); t.cuboid((23,30,16),(26,33,24),ROOF); t.cuboid((3,4,2),(26,8,2),GLASS); t.block(15,1,1,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,1,DOOR,{"half":"upper","facing":"south"})
    t.clear((15,1,2),(15,2,2))
    # Reception, raised teaching court, and auditorium service form three zones.
    t.cuboid((3,1,12),(27,4,12),HISTORIC); t.clear((15,1,12),(15,2,12))
    t.block(15,1,12,DOOR,{"half":"lower","facing":"south"}); t.block(15,2,12,DOOR,{"half":"upper","facing":"south"})
    t.cuboid((3,1,34),(27,4,34),HISTORIC); t.clear((24,1,34),(24,2,34))
    t.block(24,1,34,DOOR,{"half":"lower","facing":"south"}); t.block(24,2,34,DOOR,{"half":"upper","facing":"south"})
    t.block(15,1,16,"minecraft:deepslate_brick_stairs",{"facing":"south"})
    for x in (6,15,24):
        for z in (7,17,30,40): t.block(x,5,z,ROOF); t.block(x,4,z,LIGHT)
    t.block(25,1,40,"minecraft:chest",{"facing":"west"},{"id":"minecraft:chest","LootTable":f"zinecraft:chests/nation/{NATION}_structure","LootTableSeed":0}); t.connector(15,1,0,"north",f"zinecraft:{SETTLEMENT}/building","minecraft:empty","minecraft:empty",AIR); t.require_reachable("academy reception",(15,1,3)); t.require_reachable("teaching court",(15,2,24)); t.require_reachable("auditorium service",(24,1,40)); t.require_walk_region("academy ground level",(3,1,3),(27,1,41)); return t

def _assert_separate_contexts(ts:list[Template])->None:
    historic=next(t for t in ts if t.name=="twilight_house"); acoustic=next(t for t in ts if t.name=="rehearsal_hall")
    assert ACOUSTIC not in {name for name,_ in historic.palette}; assert ACOUSTIC in {name for name,_ in acoustic.palette} and RED in {name for name,_ in acoustic.palette}
def build_templates()->list[Template]:
    ts=[center(),street_straight(),street_corner(),street_cross(),street_end(),twilight_house(),instrument_workshop(),rehearsal_hall(),arts_academy()]; _assert_separate_contexts(ts); validate_nation(ts,NATION,SETTLEMENT); return ts
def main()->None:
    a=output_argument("Generate isolated Leithanien settlement previews"); ts=build_templates(); (not a.validate_only) and write_preview(ts,a.output); print(f"Validated {len(ts)} independent Leithanien templates")
if __name__=="__main__": main()
