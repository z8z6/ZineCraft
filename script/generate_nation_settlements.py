"""Validate and export the nineteen nation-owned settlement template sets.

This file is deliberately an orchestrator.  Every nation module owns its block
placement and architectural decisions; this entry point only enforces the
published identifiers, cross-module contracts, and deterministic NBT export.
"""

from __future__ import annotations

import shutil
from collections.abc import Callable
from pathlib import Path

from nation_settlements.aegir import build_templates as build_aegir
from nation_settlements.bolivar import build_templates as build_bolivar
from nation_settlements.columbia import build_templates as build_columbia
from nation_settlements.common import AIR, JIGSAW, Template, validate_batch
from nation_settlements.durin import build_templates as build_durin
from nation_settlements.higashi import build_templates as build_higashi
from nation_settlements.iberia import build_templates as build_iberia
from nation_settlements.kazdel import build_templates as build_kazdel
from nation_settlements.kazimierz import build_templates as build_kazimierz
from nation_settlements.kjerag import build_templates as build_kjerag
from nation_settlements.laterano import build_templates as build_laterano
from nation_settlements.leithanien import build_templates as build_leithanien
from nation_settlements.minos import build_templates as build_minos
from nation_settlements.rim_billiton import build_templates as build_rim_billiton
from nation_settlements.sami import build_templates as build_sami
from nation_settlements.sargon import build_templates as build_sargon
from nation_settlements.siracusa import build_templates as build_siracusa
from nation_settlements.ursus import build_templates as build_ursus
from nation_settlements.victoria import build_templates as build_victoria
from nation_settlements.yan import build_templates as build_yan


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/data/zinecraft/structure/nation_settlements"
FIXED_IDS = frozenset(
    {"center", "street_straight", "street_corner", "street_cross", "street_end"}
)
Builder = Callable[[], list[Template]]
Contract = tuple[str, str, Builder, frozenset[str]]


CONTRACTS: tuple[Contract, ...] = (
    (
        "aegir",
        "aegir_subsea_enclave",
        build_aegir,
        frozenset({"pressure_residence", "hydroponics_lab", "bathysphere_dock", "current_archive"}),
    ),
    (
        "bolivar",
        "bolivar_dossoles_district",
        build_bolivar,
        frozenset({"canal_house", "beach_market", "race_workshop", "festival_hall"}),
    ),
    (
        "columbia",
        "columbia_frontier_town",
        build_columbia,
        frozenset({"prefab_house", "pioneer_lab", "logistics_depot", "sheriff_office"}),
    ),
    (
        "durin",
        "durin_ideal_city_block",
        build_durin,
        frozenset({"dome_apartment", "machine_shop", "arcade", "transit_station"}),
    ),
    (
        "higashi",
        "higashi_sokogawa_town",
        build_higashi,
        frozenset({"machiya", "swordsmith", "tea_house", "magistrate_house"}),
    ),
    (
        "iberia",
        "iberia_coastal_town",
        build_iberia,
        frozenset({"saltstone_house", "shipwright", "fish_market", "inquisitor_office"}),
    ),
    (
        "kazdel",
        "kazdel_sarkaz_settlement",
        build_kazdel,
        frozenset({"canvas_house", "forge", "mercenary_lodge", "provision_store"}),
    ),
    (
        "kazimierz",
        "kazimierz_knight_borough",
        build_kazimierz,
        frozenset({"tenement", "armor_workshop", "sponsor_shop", "tournament_inn"}),
    ),
    (
        "kjerag",
        "kjerag_mountain_village",
        build_kjerag,
        frozenset({"stone_chalet", "tea_workshop", "caravan_post", "shrine_house"}),
    ),
    (
        "laterano",
        "laterano_monastery_town",
        build_laterano,
        frozenset({"white_residence", "confectionery", "notary_office", "bell_chapel"}),
    ),
    (
        "leithanien",
        "leithanien_music_town",
        build_leithanien,
        frozenset({"twilight_house", "instrument_workshop", "rehearsal_hall", "arts_academy"}),
    ),
    (
        "minos",
        "minos_heroic_polis",
        build_minos,
        frozenset({"courtyard_house", "olive_market", "training_hall", "council_house"}),
    ),
    (
        "rim_billiton",
        "rim_billiton_mining_camp",
        build_rim_billiton,
        frozenset({"miner_bunkhouse", "ore_workshop", "freight_depot", "canteen"}),
    ),
    (
        "sami",
        "sami_snowpriest_village",
        build_sami,
        frozenset({"snow_lodge", "hunter_camp", "ritual_house", "supply_shed"}),
    ),
    (
        "sargon",
        "sargon_oasis_town",
        build_sargon,
        frozenset({"adobe_house", "spice_market", "caravanserai", "well_house"}),
    ),
    (
        "siracusa",
        "siracusa_family_town",
        build_siracusa,
        frozenset({"family_house", "trattoria", "tailor_shop", "meeting_hall"}),
    ),
    (
        "ursus",
        "ursus_northern_town",
        build_ursus,
        frozenset({"heated_house", "military_storehouse", "mine_office", "communal_hall"}),
    ),
    (
        "victoria",
        "victoria_industrial_borough",
        build_victoria,
        frozenset({"brick_tenement", "steam_workshop", "rail_warehouse", "council_hall"}),
    ),
    (
        "yan",
        "yan_shangshu_town",
        build_yan,
        frozenset({"courtyard_residence", "tea_house", "artisan_workshop", "relay_office"}),
    ),
)


def _collect_and_validate() -> list[Template]:
    assert len(CONTRACTS) == 19
    assert len({nation for nation, _, _, _ in CONTRACTS}) == 19
    assert len({settlement for _, settlement, _, _ in CONTRACTS}) == 19

    groups: list[list[Template]] = []
    for nation, settlement, builder, building_ids in CONTRACTS:
        group = builder()
        expected_ids = FIXED_IDS | building_ids
        assert len(group) == 9, f"{nation}: expected 9 templates, got {len(group)}"
        assert {template.nation for template in group} == {nation}, f"{nation}: nation mismatch"
        assert {template.settlement for template in group} == {settlement}, f"{nation}: settlement mismatch"
        assert {template.name for template in group} == expected_ids, f"{nation}: public ID mismatch"
        assert sum(template.category == "center" for template in group) == 1
        assert sum(template.category == "street" for template in group) == 4
        assert sum(template.category == "building" for template in group) == 4
        assert all(
            template.size[0] == 32 and template.size[2] == 32
            for template in group
            if template.category != "building"
        ), f"{nation}: center/street grid mismatch"
        groups.append(group)

    templates = validate_batch(groups)
    buildings = [template for template in templates if template.category == "building"]
    assert len(templates) == 171 and len(buildings) == 76

    by_shape: dict[tuple[tuple[int, int, int], frozenset[tuple[int, int, int]]], list[str]] = {}
    for template in buildings:
        occupied = frozenset(
            position
            for position in template.blocks
            if template.block_name(position) not in {None, AIR, JIGSAW}
        )
        by_shape.setdefault((template.size, occupied), []).append(template.label)
    collisions = [labels for labels in by_shape.values() if len(labels) > 1]
    assert not collisions, f"material-independent building shape collisions: {collisions}"
    assert len(by_shape) == 76
    return templates


def _prepare_output() -> None:
    expected = ROOT / "src/main/resources/data/zinecraft/structure/nation_settlements"
    assert OUTPUT.resolve() == expected.resolve()
    assert OUTPUT.name == "nation_settlements"
    if OUTPUT.exists():
        shutil.rmtree(OUTPUT)
    OUTPUT.mkdir(parents=True)


def main() -> None:
    templates = _collect_and_validate()
    _prepare_output()
    written = [template.write(OUTPUT) for template in templates]
    assert len(written) == 171 and all(path.is_file() for path in written)
    print("Generated and validated 171 templates: 19 centers, 76 streets, 76 buildings")
    print("Verified 76/76 material-independent building shapes are unique")


if __name__ == "__main__":
    main()
