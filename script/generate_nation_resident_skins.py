#!/usr/bin/env python3
"""Generate deterministic 64x64 wide-arm skins for Terra nation residents."""

from __future__ import annotations

from dataclasses import dataclass
from pathlib import Path

from PIL import Image, ImageDraw


ROOT = Path(__file__).resolve().parents[1]
OUTPUT = ROOT / "src/main/resources/assets/zinecraft/textures/entity/nation_resident"
PREVIEW = ROOT / "docs/entity/assets/nation_resident_skin_preview.png"


@dataclass(frozen=True)
class Style:
    path: str
    skin: str
    hair: str
    eyes: str
    primary: str
    secondary: str
    accent: str
    trousers: str
    boots: str
    pattern: str
    headwear: str = "none"


STYLES = (
    Style("aegir", "#e8c6ad", "#e9eef0", "#1aa8b4", "#e9edef", "#163348", "#28aab7", "#18394d", "#101b27", "coat"),
    Style("bolivar", "#a9653f", "#3b251d", "#2b8490", "#42aeb0", "#e7dfcf", "#ba7a42", "#b79b70", "#45372b", "workwear", "headband"),
    Style("higashi", "#e3b89b", "#17233e", "#293b73", "#243759", "#111724", "#7b5742", "#181b24", "#211b19", "robe"),
    Style("durin", "#edc6a3", "#d4a45f", "#4b9da4", "#dfe3e0", "#4e4f4a", "#ed8d20", "#5b5a56", "#30312f", "workwear", "goggles"),
    Style("columbia", "#d9ad8b", "#543324", "#5d8d88", "#584432", "#182d35", "#a98556", "#25343a", "#2b231e", "jacket", "scarf"),
    Style("kazimierz", "#e2b998", "#7c674d", "#6c8661", "#7b2728", "#28221f", "#c3a267", "#302922", "#201c19", "coat", "circlet"),
    Style("kazdel", "#d7aa90", "#ded9ce", "#9d3030", "#50463a", "#752d29", "#a98b5c", "#3a332d", "#25221f", "mercenary", "horns"),
    Style("laterano", "#eed1ab", "#e4d5b4", "#aa8451", "#eee6d7", "#a12d22", "#d8ae45", "#d9d1c5", "#594438", "coat", "halo"),
    Style("leithanien", "#ddaf91", "#17151c", "#8b49a1", "#22202b", "#3f2757", "#b09355", "#272330", "#15131a", "suit"),
    Style("rim_billiton", "#9b603f", "#33231e", "#795d43", "#d56c1e", "#33373a", "#f0a13a", "#4b4440", "#2b2928", "workwear", "miner"),
    Style("minos", "#d4a783", "#5f4a33", "#647f65", "#e9e2ce", "#936d36", "#2e7088", "#5e4c36", "#33291f", "robe", "laurel"),
    Style("sargon", "#9d6240", "#30231c", "#68533b", "#a66b1f", "#3a3024", "#d5a946", "#4c3d2d", "#29221b", "robe", "headwrap"),
    Style("sami", "#e7c5ad", "#e7e2da", "#3979a0", "#9fbfd3", "#eef0e8", "#4d7190", "#758b9a", "#453f38", "fur", "hood"),
    Style("victoria", "#ddb08d", "#4b3023", "#586e61", "#263e31", "#3a3027", "#9a7645", "#4b3c2f", "#28231f", "jacket", "cap"),
    Style("ursus", "#d4a27d", "#493026", "#704e3a", "#79362d", "#4c382b", "#b18b64", "#4a382f", "#25211e", "fur", "ushanka"),
    Style("kjerag", "#e3bea3", "#dedbd2", "#3e8587", "#dfe5df", "#497b7b", "#8f7960", "#657c7c", "#3a3631", "fur", "hood"),
    Style("siracusa", "#d7aa8c", "#211d1d", "#6c665c", "#252426", "#d5cfbf", "#773632", "#2c2a2b", "#171719", "suit", "cap"),
    Style("yan", "#e5b497", "#19191d", "#725840", "#8a251f", "#202126", "#d39c43", "#3a2423", "#211b1b", "robe", "hairpin"),
    Style("iberia", "#d6aa8c", "#493226", "#6d7769", "#242526", "#c9bca2", "#b78b36", "#34312d", "#18191a", "coat"),
)


def rgb(value: str) -> tuple[int, int, int, int]:
    value = value.lstrip("#")
    return int(value[0:2], 16), int(value[2:4], 16), int(value[4:6], 16), 255


def shade(value: str, amount: float) -> tuple[int, int, int, int]:
    color = rgb(value)
    return tuple(max(0, min(255, round(channel * amount))) for channel in color[:3]) + (255,)


def fill(draw: ImageDraw.ImageDraw, x: int, y: int, w: int, h: int, color) -> None:
    draw.rectangle((x, y, x + w - 1, y + h - 1), fill=color)


def cuboid(draw: ImageDraw.ImageDraw, faces: tuple[tuple[int, int, int, int], ...], color: str) -> None:
    tones = (1.08, 0.92, 1.0, 0.82, 0.88, 0.76)
    for face, tone in zip(faces, tones):
        fill(draw, *face, shade(color, tone))


HEAD = ((8, 0, 8, 8), (16, 0, 8, 8), (0, 8, 8, 8), (8, 8, 8, 8), (16, 8, 8, 8), (24, 8, 8, 8))
BODY = ((20, 16, 8, 4), (28, 16, 8, 4), (16, 20, 4, 12), (20, 20, 8, 12), (28, 20, 4, 12), (32, 20, 8, 12))
RIGHT_ARM = ((44, 16, 4, 4), (48, 16, 4, 4), (40, 20, 4, 12), (44, 20, 4, 12), (48, 20, 4, 12), (52, 20, 4, 12))
RIGHT_LEG = ((4, 16, 4, 4), (8, 16, 4, 4), (0, 20, 4, 12), (4, 20, 4, 12), (8, 20, 4, 12), (12, 20, 4, 12))
LEFT_ARM = ((36, 48, 4, 4), (40, 48, 4, 4), (32, 52, 4, 12), (36, 52, 4, 12), (40, 52, 4, 12), (44, 52, 4, 12))
LEFT_LEG = ((20, 48, 4, 4), (24, 48, 4, 4), (16, 52, 4, 12), (20, 52, 4, 12), (24, 52, 4, 12), (28, 52, 4, 12))


def paint_head(draw: ImageDraw.ImageDraw, style: Style) -> None:
    cuboid(draw, HEAD, style.skin)
    hair = rgb(style.hair)
    # Hair covers the top, back, upper sides and an irregular fringe.
    fill(draw, 8, 0, 8, 8, shade(style.hair, 1.08))
    fill(draw, 24, 8, 8, 8, shade(style.hair, 0.78))
    fill(draw, 0, 8, 8, 5, shade(style.hair, 0.88))
    fill(draw, 16, 8, 8, 5, shade(style.hair, 0.92))
    fill(draw, 8, 8, 8, 2, hair)
    fill(draw, 8, 10, 2, 1, hair)
    fill(draw, 14, 10, 2, 1, hair)
    draw.point((11, 12), fill=rgb(style.eyes))
    draw.point((14, 12), fill=rgb(style.eyes))
    draw.point((12, 14), fill=shade(style.skin, 0.84))
    draw.point((13, 14), fill=shade(style.skin, 0.84))

    overlay = rgb(style.accent)
    if style.headwear == "goggles":
        fill(draw, 40, 9, 8, 2, shade(style.boots, 0.9))
        fill(draw, 41, 9, 2, 2, overlay)
        fill(draw, 45, 9, 2, 2, overlay)
        fill(draw, 40, 0, 8, 2, shade(style.hair, 0.85))
    elif style.headwear in {"cap", "miner", "ushanka"}:
        cap = style.accent if style.headwear == "miner" else style.primary
        fill(draw, 40, 0, 8, 4, shade(cap, 0.9))
        fill(draw, 40, 8, 8, 2, rgb(cap))
        fill(draw, 39, 10, 10, 1, shade(cap, 0.75))
        if style.headwear == "miner":
            fill(draw, 43, 8, 2, 2, rgb(style.secondary))
        if style.headwear == "ushanka":
            fill(draw, 32, 9, 2, 6, rgb(style.secondary))
            fill(draw, 54, 9, 2, 6, rgb(style.secondary))
    elif style.headwear in {"hood", "headwrap", "scarf"}:
        hood = style.secondary if style.headwear == "hood" else style.primary
        fill(draw, 40, 0, 8, 8, shade(hood, 1.05))
        fill(draw, 40, 8, 8, 1, rgb(hood))
        fill(draw, 40, 9, 1, 7, rgb(hood))
        fill(draw, 47, 9, 1, 7, shade(hood, 0.8))
    elif style.headwear == "horns":
        horn = shade(style.secondary, 0.45)
        fill(draw, 40, 0, 2, 4, horn)
        fill(draw, 46, 0, 2, 4, horn)
        draw.point((39, 1), fill=horn)
        draw.point((48, 1), fill=horn)
    elif style.headwear == "halo":
        fill(draw, 40, 8, 8, 1, overlay)
        draw.point((40, 9), fill=shade(style.accent, 1.15))
        draw.point((47, 9), fill=shade(style.accent, 1.15))
    elif style.headwear in {"circlet", "laurel", "headband"}:
        fill(draw, 40, 9, 8, 1, overlay)
        if style.headwear == "laurel":
            for x in (40, 42, 45, 47):
                draw.point((x, 8), fill=shade(style.accent, 1.08))
    elif style.headwear == "hairpin":
        fill(draw, 46, 9, 2, 1, overlay)
        draw.point((47, 8), fill=overlay)


def paint_limbs(draw: ImageDraw.ImageDraw, style: Style) -> None:
    cuboid(draw, RIGHT_ARM, style.primary)
    cuboid(draw, LEFT_ARM, style.primary)
    cuboid(draw, RIGHT_LEG, style.trousers)
    cuboid(draw, LEFT_LEG, style.trousers)
    skin = rgb(style.skin)
    for x in (40, 44, 48, 52):
        fill(draw, x, 29, 4, 3, skin)
    for x in (32, 36, 40, 44):
        fill(draw, x, 61, 4, 3, skin)
    for x, y in ((0, 28), (4, 28), (8, 28), (12, 28), (16, 60), (20, 60), (24, 60), (28, 60)):
        fill(draw, x, y, 4, 4, rgb(style.boots))
    # Cuffs and trouser seam details.
    fill(draw, 44, 27, 4, 2, rgb(style.accent))
    fill(draw, 36, 59, 4, 2, rgb(style.accent))
    draw.line((7, 21, 7, 27), fill=shade(style.trousers, 0.75))
    draw.line((23, 53, 23, 59), fill=shade(style.trousers, 0.75))


def paint_torso(draw: ImageDraw.ImageDraw, style: Style) -> None:
    cuboid(draw, BODY, style.primary)
    primary = rgb(style.primary)
    secondary = rgb(style.secondary)
    accent = rgb(style.accent)
    dark = shade(style.primary, 0.68)
    if style.pattern == "suit":
        fill(draw, 22, 20, 4, 8, secondary)
        draw.polygon(((22, 20), (24, 24), (20, 20)), fill=dark)
        draw.polygon(((25, 20), (24, 24), (27, 20)), fill=dark)
        fill(draw, 24, 22, 1, 5, accent)
    elif style.pattern == "robe":
        fill(draw, 23, 20, 2, 12, secondary)
        fill(draw, 20, 27, 8, 2, accent)
        draw.line((20, 22, 27, 29), fill=shade(style.accent, 0.8))
    elif style.pattern == "workwear":
        fill(draw, 21, 20, 1, 10, secondary)
        fill(draw, 26, 20, 1, 10, secondary)
        fill(draw, 21, 26, 6, 1, accent)
        fill(draw, 23, 24, 2, 2, shade(style.secondary, 0.75))
    elif style.pattern == "mercenary":
        draw.line((20, 20, 27, 27), fill=accent, width=2)
        fill(draw, 20, 28, 8, 2, secondary)
        draw.point((25, 23), fill=secondary)
    elif style.pattern == "fur":
        fill(draw, 20, 20, 8, 3, secondary)
        for x in range(20, 28, 2):
            draw.point((x, 23), fill=shade(style.secondary, 0.82))
        fill(draw, 23, 23, 2, 9, dark)
    elif style.pattern == "jacket":
        fill(draw, 23, 20, 2, 12, secondary)
        fill(draw, 20, 27, 8, 1, accent)
        draw.point((22, 24), fill=accent)
        draw.point((26, 24), fill=accent)
    else:  # coat
        fill(draw, 22, 20, 4, 12, secondary)
        fill(draw, 20, 28, 8, 2, accent)
        draw.line((22, 21, 20, 24), fill=dark)
        draw.line((25, 21, 27, 24), fill=dark)
    fill(draw, 20, 30, 8, 2, shade(style.boots, 0.9))
    draw.point((24, 30), fill=accent)


def paint_outer_layer(draw: ImageDraw.ImageDraw, style: Style) -> None:
    accent = rgb(style.accent)
    if style.pattern in {"coat", "jacket", "mercenary", "fur"}:
        fill(draw, 20, 36, 2, 12, shade(style.primary, 0.82))
        fill(draw, 26, 36, 2, 12, shade(style.primary, 0.72))
        fill(draw, 22, 46, 4, 2, accent)
    if style.pattern in {"workwear", "mercenary"}:
        fill(draw, 44, 39, 4, 1, accent)
        fill(draw, 52, 55, 4, 1, accent)
    if style.pattern == "robe":
        fill(draw, 20, 42, 8, 1, accent)
        fill(draw, 4, 42, 4, 1, accent)
        fill(draw, 4, 58, 4, 1, accent)


def generate(style: Style) -> None:
    image = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
    draw = ImageDraw.Draw(image)
    paint_head(draw, style)
    paint_torso(draw, style)
    paint_limbs(draw, style)
    paint_outer_layer(draw, style)
    target = OUTPUT / f"{style.path}.png"
    image.save(target, optimize=True)


def front_preview(skin: Image.Image) -> Image.Image:
    preview = Image.new("RGBA", (16, 32), (0, 0, 0, 0))

    def part(base_box, overlay_box, position) -> None:
        preview.alpha_composite(skin.crop(base_box), position)
        preview.alpha_composite(skin.crop(overlay_box), position)

    part((8, 8, 16, 16), (40, 8, 48, 16), (4, 0))
    part((20, 20, 28, 32), (20, 36, 28, 48), (4, 8))
    part((44, 20, 48, 32), (44, 36, 48, 48), (0, 8))
    part((36, 52, 40, 64), (52, 52, 56, 64), (12, 8))
    part((4, 20, 8, 32), (4, 36, 8, 48), (4, 20))
    part((20, 52, 24, 64), (4, 52, 8, 64), (8, 20))
    return preview


def generate_preview() -> None:
    scale = 6
    cell_width, cell_height = 112, 210
    sheet = Image.new("RGBA", (cell_width * 5, cell_height * 4), (35, 38, 43, 255))
    draw = ImageDraw.Draw(sheet)
    for index, style in enumerate(STYLES):
        skin = Image.open(OUTPUT / f"{style.path}.png").convert("RGBA")
        resident = front_preview(skin).resize((16 * scale, 32 * scale), Image.Resampling.NEAREST)
        column, row = index % 5, index // 5
        x = column * cell_width + (cell_width - resident.width) // 2
        y = row * cell_height + 2
        sheet.alpha_composite(resident, (x, y))
        draw.text((column * cell_width + 6, row * cell_height + 194), style.path, fill=(235, 235, 235, 255))
    PREVIEW.parent.mkdir(parents=True, exist_ok=True)
    sheet.save(PREVIEW, optimize=True)


def main() -> None:
    OUTPUT.mkdir(parents=True, exist_ok=True)
    for style in STYLES:
        generate(style)
    generate_preview()
    print(f"Generated {len(STYLES)} nation resident skins in {OUTPUT}")


if __name__ == "__main__":
    main()
