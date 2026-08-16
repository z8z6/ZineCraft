"""Generate the 38 Terra landmarks from independent nation-owned builders.

No silhouette, room program or architectural profile lives in this entrypoint.
It only invokes the 19 source-specific modules, applies global compatibility
checks and writes their six NBT templates.
"""

from __future__ import annotations

import shutil

from nation_landmarks import (
    aegir, bolivar, columbia, durin, higashi, iberia, kazdel, kazimierz,
    kjerag, laterano, leithanien, minos, rim_billiton, sami, sargon,
    siracusa, ursus, victoria, yan,
)
from nation_landmarks.base import (
    NATION_MATERIALS, OUTPUT, geometry_digest, validate_build, write_module,
)


NATION_BUILDERS = (
    aegir.build_all,
    bolivar.build_all,
    higashi.build_all,
    durin.build_all,
    columbia.build_all,
    kazimierz.build_all,
    kazdel.build_all,
    laterano.build_all,
    leithanien.build_all,
    rim_billiton.build_all,
    minos.build_all,
    sargon.build_all,
    sami.build_all,
    victoria.build_all,
    ursus.build_all,
    kjerag.build_all,
    siracusa.build_all,
    yan.build_all,
    iberia.build_all,
)

EXPECTED_IDS = {
    "aegir_volcanic_beacon", "aegir_abyssal_observatory",
    "bolivar_dossoles_yacht", "bolivar_race_checkpoint",
    "higashi_rift_shrine", "higashi_sokogawa_watchtower",
    "durin_dome_station", "durin_water_park",
    "columbia_frontier_lab", "columbia_prison_outpost",
    "kazimierz_arena_gate", "kazimierz_knight_monument",
    "kazdel_babel_ruins", "kazdel_sarkaz_camp",
    "laterano_revelation_tower", "laterano_ambrosius_chapel",
    "leithanien_twin_spires", "leithanien_concert_hall",
    "rim_billiton_mining_derrick", "rim_billiton_rail_depot",
    "minos_heroes_temple", "minos_heroes_plaza",
    "sargon_golden_bazaar", "sargon_long_spring_well",
    "sami_cyclops_altar", "sami_snowpriest_lodge",
    "victoria_defence_cannon", "victoria_steam_station",
    "ursus_sarcophagus_station", "ursus_northern_mine_tower",
    "kjerag_karlan_monastery", "kjerag_sacred_plaza",
    "siracusa_family_court", "siracusa_family_theatre",
    "yan_yumen_beacon", "yan_shangshu_pavilion",
    "iberia_eye_lighthouse", "iberia_saltwind_chapel",
}


def main() -> None:
    builds = tuple(build for nation_builder in NATION_BUILDERS for build in nation_builder())
    paths = {build.spec.path for build in builds}
    nations = {build.spec.nation for build in builds}
    if len(NATION_BUILDERS) != 19 or len(builds) != 38:
        raise ValueError("expected 19 independent nation modules and 38 landmark builders")
    if paths != EXPECTED_IDS:
        raise ValueError(f"public landmark id drift: missing={EXPECTED_IDS - paths}, extra={paths - EXPECTED_IDS}")
    if nations != set(NATION_MATERIALS):
        raise ValueError(f"nation coverage drift: {set(NATION_MATERIALS) ^ nations}")

    digests: dict[str, str] = {}
    for build in builds:
        validate_build(build)
        digest = geometry_digest(build)
        if digest in digests:
            raise ValueError(
                f"{build.spec.path} duplicates the physical geometry of {digests[digest]}; "
                "palette swaps do not count as a distinct landmark"
            )
        digests[digest] = build.spec.path

    if OUTPUT.exists():
        shutil.rmtree(OUTPUT)
    module_count = 0
    for build in builds:
        for module in build.modules:
            write_module(module)
            module_count += 1
    print(
        f"Generated {len(builds)} independently-built landmarks / {module_count} NBT modules "
        f"from {len(NATION_BUILDERS)} nation builder files in {OUTPUT}"
    )


if __name__ == "__main__":
    main()
