#!/usr/bin/env python3
"""Render the generated Terra Nation/City/Region validation JSON as a scalable SVG map."""

from __future__ import annotations

import argparse
import html
import json
from pathlib import Path


PALETTE = (
    "#3b82f6", "#ef4444", "#10b981", "#f59e0b", "#8b5cf6",
    "#06b6d4", "#ec4899", "#84cc16", "#f97316", "#6366f1",
    "#14b8a6", "#d946ef", "#a3a3a3", "#eab308", "#0ea5e9",
    "#22c55e", "#a855f7", "#64748b", "#dc2626",
)


def arguments() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--input", type=Path, required=True, help="Terra layout JSON")
    parser.add_argument("--output", type=Path, required=True, help="Output SVG")
    return parser.parse_args()


def svg_point(point: dict[str, float]) -> tuple[float, float]:
    # SVG 的 Y 轴与 Minecraft 的 Z 轴都以向下为正方向。
    return float(point["x"]), float(point["z"])


def path_data(boundary: list[dict[str, float]]) -> str:
    points = [svg_point(point) for point in boundary]
    return " ".join(
        ("M" if index == 0 else "L") + f" {x:.3f} {y:.3f}"
        for index, (x, y) in enumerate(points)
    ) + " Z"


def rings_path_data(boundaries: list[list[dict[str, float]]]) -> str:
    return " ".join(path_data(boundary) for boundary in boundaries)


def polyline_data(points: list[dict[str, float]]) -> str:
    return " ".join(
        ("M" if index == 0 else "L") + f" {x:.3f} {y:.3f}"
        for index, (x, y) in enumerate(svg_point(point) for point in points)
    )


def polygon_centroid(boundary: list[dict[str, float]]) -> tuple[float, float]:
    points = [svg_point(point) for point in boundary]
    twice_area = 0.0
    weighted_x = 0.0
    weighted_y = 0.0
    for index, start in enumerate(points):
        end = points[(index + 1) % len(points)]
        cross = start[0] * end[1] - end[0] * start[1]
        twice_area += cross
        weighted_x += (start[0] + end[0]) * cross
        weighted_y += (start[1] + end[1]) * cross
    if abs(twice_area) < 1.0e-9:
        return (
            sum(point[0] for point in points) / len(points),
            sum(point[1] for point in points) / len(points),
        )
    return weighted_x / (3.0 * twice_area), weighted_y / (3.0 * twice_area)


def title(place: dict[str, object]) -> str:
    center = place["center"]
    return html.escape(
        f'{place["zh_cn_name"]} ({place["id"]}) '
        f'X={center["x"]:.3f}, Z={center["z"]:.3f}'
    )


def text(place: dict[str, object], size: float, color: str, weight: int = 400) -> str:
    x, y = polygon_centroid(place["boundary"])
    label = html.escape(str(place["zh_cn_name"]))
    return (
        f'<text x="{x:.3f}" y="{y:.3f}" font-size="{size}" fill="{color}" '
        f'font-weight="{weight}" text-anchor="middle" dominant-baseline="middle" '
        f'paint-order="stroke" stroke="#ffffff" stroke-width="{size * 0.12:.3f}">{label}</text>'
    )


def render(data: dict[str, object]) -> str:
    boundary = data["boundary"]
    xs = [float(point["x"]) for point in boundary]
    ys = [float(point["z"]) for point in boundary]
    margin = max(float(data["core_size_x"]), float(data["core_size_z"])) * 0.025
    min_x, max_x = min(xs) - margin, max(xs) + margin
    min_y, max_y = min(ys) - margin, max(ys) + margin
    body: list[str] = [
        f'<path d="{path_data(boundary)}" fill="#f8fafc" stroke="#111827" stroke-width="90"/>',
    ]

    for nation_index, nation in enumerate(data["nations"]):
        color = PALETTE[nation_index % len(PALETTE)]
        underground = bool(nation.get("underground", False))
        nation_dash = ' stroke-dasharray="220 120"' if underground else ""
        nation_opacity = "0.18" if underground else "0.08"
        nation_boundaries = [nation["boundary"]]
        body.append(
            f'<path d="{rings_path_data(nation_boundaries)}" fill="{color}" '
            f'fill-rule="evenodd" fill-opacity="{nation_opacity}" stroke="{color}" '
            f'stroke-width="70"{nation_dash}><title>{title(nation)}</title></path>'
        )
        body.append(
            f'<path d="{polyline_data(nation["polyline"])}" fill="none" stroke="#111827" '
            f'stroke-width="55" stroke-dasharray="180 100"><title>{title(nation)}</title></path>'
        )
        for anchor in nation["polyline"]:
            anchor_x, anchor_y = svg_point(anchor)
            body.append(
                f'<circle cx="{anchor_x:.3f}" cy="{anchor_y:.3f}" r="105" '
                f'fill="#ffffff" stroke="#111827" stroke-width="45"><title>{title(nation)}</title></circle>'
            )
        for city in nation["cities"]:
            body.append(
                f'<path d="{path_data(city["boundary"])}" fill="none" stroke="{color}" '
                f'stroke-width="25" stroke-opacity="0.85"><title>{title(city)}</title></path>'
            )
            for region in city["regions"]:
                body.append(
                    f'<path d="{path_data(region["boundary"])}" fill="none" stroke="#334155" '
                    f'stroke-width="7" stroke-opacity="0.55"><title>{title(region)}</title></path>'
                )
                body.append(text(region, 210, "#334155", 500))
            body.append(text(city, 340, color, 600))
        body.append(text(nation, 520, "#000000", 700))

    return "\n".join((
        '<?xml version="1.0" encoding="UTF-8"?>',
        f'<svg xmlns="http://www.w3.org/2000/svg" width="3000" height="3000" '
        f'viewBox="{min_x:.3f} {min_y:.3f} {max_x - min_x:.3f} {max_y - min_y:.3f}">',
        '<rect x="-100000" y="-100000" width="200000" height="200000" fill="#e2e8f0"/>',
        '<g font-family="Microsoft YaHei, Noto Sans CJK SC, sans-serif">',
        *body,
        '</g>',
        '</svg>',
    ))


def main() -> None:
    args = arguments()
    with args.input.open("r", encoding="utf-8") as source:
        data = json.load(source)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(render(data), encoding="utf-8")
    print(f"Terra layout map written to {args.output.resolve()}")


if __name__ == "__main__":
    main()
