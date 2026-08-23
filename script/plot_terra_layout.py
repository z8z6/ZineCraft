#!/usr/bin/env python3
"""Render the generated Terra Nation/City/Region JSON as an interactive SVG map."""

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

LEVEL_ZOOMS = {
    "nation": 1.0,
    "city": 6.0,
    "region": 24.0,
}


def arguments() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--input", type=Path, required=True, help="Terra layout JSON")
    parser.add_argument("--output", type=Path, required=True, help="Output SVG")
    parser.add_argument(
        "--show-skeleton",
        action="store_true",
        help="Show Nation point/polyline skeletons (hidden by default)",
    )
    parser.add_argument(
        "--initial-level",
        choices=LEVEL_ZOOMS,
        default="nation",
        help="Initial detail/zoom level (default: nation)",
    )
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


def text(
    place: dict[str, object],
    size: float,
    color: str,
    css_class: str,
    weight: int = 400,
) -> str:
    x, y = polygon_centroid(place["boundary"])
    label = html.escape(str(place["zh_cn_name"]))
    return (
        f'<text class="{css_class}" x="{x:.3f}" y="{y:.3f}" '
        f'font-size="{size}" data-font-size="{size}" fill="{color}" '
        f'font-weight="{weight}" text-anchor="middle" dominant-baseline="middle" '
        f'paint-order="stroke" stroke="#ffffff" stroke-width="{size * 0.12:.3f}" '
        f'data-stroke-width="{size * 0.12:.3f}" pointer-events="none">{label}</text>'
    )


def render(
    data: dict[str, object],
    show_skeleton: bool = False,
    initial_level: str = "nation",
) -> str:
    boundary = data["boundary"]
    xs = [float(point["x"]) for point in boundary]
    ys = [float(point["z"]) for point in boundary]
    margin = max(float(data["core_size_x"]), float(data["core_size_z"])) * 0.025
    min_x, max_x = min(xs) - margin, max(xs) + margin
    min_y, max_y = min(ys) - margin, max(ys) + margin
    nation_shapes: list[str] = [
        f'<path d="{path_data(boundary)}" fill="#f8fafc" stroke="#111827" stroke-width="90"/>',
    ]
    city_shapes: list[str] = []
    region_shapes: list[str] = []
    nation_labels: list[str] = []
    city_labels: list[str] = []
    region_labels: list[str] = []

    for nation_index, nation in enumerate(data["nations"]):
        color = PALETTE[nation_index % len(PALETTE)]
        underground = bool(nation.get("underground", False))
        nation_dash = ' stroke-dasharray="220 120"' if underground else ""
        nation_opacity = "0.18" if underground else "0.08"
        nation_boundaries = [nation["boundary"]]
        nation_center = nation["center"]
        nation_shapes.append(
            f'<path d="{rings_path_data(nation_boundaries)}" fill="{color}" '
            f'fill-rule="evenodd" fill-opacity="{nation_opacity}" stroke="{color}" '
            f'stroke-width="70"{nation_dash} data-target-level="city" '
            f'data-focus-x="{nation_center["x"]:.3f}" data-focus-y="{nation_center["z"]:.3f}">'
            f'<title>{title(nation)}</title></path>'
        )
        if show_skeleton:
            nation_shapes.append(
                f'<path d="{polyline_data(nation["polyline"])}" fill="none" stroke="#111827" '
                f'stroke-width="55" stroke-dasharray="180 100"><title>{title(nation)}</title></path>'
            )
#         for anchor in nation["polyline"]:
#             anchor_x, anchor_y = svg_point(anchor)
#             body.append(
#                 f'<circle cx="{anchor_x:.3f}" cy="{anchor_y:.3f}" r="105" '
#                 f'fill="#ffffff" stroke="#111827" stroke-width="45"><title>{title(nation)}</title></circle>'
#             )
        for city in nation["cities"]:
            city_center = city["center"]
            city_shapes.append(
                f'<path d="{path_data(city["boundary"])}" fill="none" stroke="{color}" '
                f'stroke-width="25" stroke-opacity="0.85" data-target-level="region" '
                f'data-focus-x="{city_center["x"]:.3f}" data-focus-y="{city_center["z"]:.3f}">'
                f'<title>{title(city)}</title></path>'
            )
            for road in city.get("roads", []):
                region_shapes.append(
                    f'<path d="{path_data(road["block_area"]["corners"])}" '
                    f'fill="#94a3b8" fill-opacity="0.85" stroke="#64748b" '
                    f'stroke-width="2"><title>Road '
                    f'{road["from_plot_id"]} → {road["to_plot_id"]}</title></path>'
                )
            for region in city["regions"]:
                is_core = str(region["zh_cn_name"]).endswith("核心区")
                fill = "#f59e0b" if is_core else "#e2e8f0"
                region_shapes.append(
                    f'<path d="{path_data(region["mobile_plot"]["corners"])}" '
                    f'fill="{fill}" fill-opacity="0.58" stroke="#334155" '
                    f'stroke-width="5" stroke-opacity="0.8"><title>{title(region)}</title></path>'
                )
                for road_edge in region.get("region_layout", {}).get("road_graph", {}).get("edges", []):
                    area = road_edge["chunk_area"]
                    min_x = area["min_chunk_x"] * 16
                    min_z = area["min_chunk_z"] * 16
                    max_x = min_x + area["width_chunks"] * 16
                    max_z = min_z + area["length_chunks"] * 16
                    road_boundary = [
                        {"x": min_x, "z": min_z}, {"x": max_x, "z": min_z},
                        {"x": max_x, "z": max_z}, {"x": min_x, "z": max_z},
                    ]
                    road_class = str(road_edge["road_class"])
                    road_fill = "#334155" if road_class == "primary" else "#64748b"
                    region_shapes.append(
                        f'<path d="{path_data(road_boundary)}" fill="{road_fill}" '
                        f'fill-opacity="0.82" stroke="none"><title>{road_class} road</title></path>'
                    )
                for building_slot in region.get("building_slots", []):
                    chunk_area = building_slot["chunk_area"]
                    min_x = chunk_area["min_chunk_x"] * 16
                    min_z = chunk_area["min_chunk_z"] * 16
                    max_x = min_x + chunk_area["width_chunks"] * 16
                    max_z = min_z + chunk_area["length_chunks"] * 16
                    building_boundary = [
                        {"x": min_x, "z": min_z},
                        {"x": max_x, "z": min_z},
                        {"x": max_x, "z": max_z},
                        {"x": min_x, "z": max_z},
                    ]
                    region_shapes.append(
                        f'<path d="{path_data(building_boundary)}" fill="#334155" '
                        f'fill-opacity="0.42" stroke="#0f172a" stroke-width="2">'
                        f'<title>{html.escape(str(building_slot["building_id"]))} '
                        f'{chunk_area["width_chunks"]}x{chunk_area["length_chunks"]} chunks, '
                        f'facing {html.escape(str(building_slot["rotation"]))}</title></path>'
                    )
                region_labels.append(text(region, 210, "#334155", "region-label", 500))
            core_x, core_y = svg_point(city["city_core"])
            region_shapes.append(
                f'<circle cx="{core_x:.3f}" cy="{core_y:.3f}" r="12" '
                f'fill="#dc2626" stroke="#ffffff" stroke-width="3">'
                f'<title>{html.escape(str(city["zh_cn_name"]))} city core</title></circle>'
            )
            city_labels.append(text(city, 340, color, "city-label", 600))
        nation_labels.append(text(nation, 520, "#000000", "nation-label", 700))

    initial_zoom = LEVEL_ZOOMS[initial_level]
    view_width = (max_x - min_x) / initial_zoom
    view_height = (max_y - min_y) / initial_zoom
    view_x = min_x + ((max_x - min_x) - view_width) / 2.0
    view_y = min_y + ((max_y - min_y) - view_height) / 2.0

    styles = """<style>
svg { background: #f8fafc; cursor: grab; user-select: none; }
svg.dragging { cursor: grabbing; }
.city-shapes, .region-shapes, .city-label, .region-label { display: none; }
svg[data-level="city"] .city-shapes,
svg[data-level="city"] .city-label { display: inline; }
svg[data-level="region"] .city-shapes,
svg[data-level="region"] .region-shapes,
svg[data-level="region"] .region-label { display: inline; }
svg[data-level="city"] .nation-label,
svg[data-level="region"] .nation-label { display: none; }
.map-controls { cursor: default; }
.control-panel { fill: #ffffff; fill-opacity: .94; stroke: #94a3b8; stroke-width: 1; }
.zoom-button { cursor: pointer; }
.zoom-button rect { fill: #f8fafc; stroke: #cbd5e1; stroke-width: 1; }
.zoom-button:hover rect, .zoom-button.active rect { fill: #dbeafe; stroke: #3b82f6; }
.zoom-button text, .zoom-status { fill: #0f172a; font: 15px "Microsoft YaHei", sans-serif; }
.zoom-status { font-size: 13px; }
[data-target-level] { cursor: zoom-in; }
</style>"""

    controls = """<g id="map-controls" class="map-controls">
<rect class="control-panel" x="0" y="0" width="344" height="82" rx="8"/>
<g class="zoom-button" data-level-button="nation" transform="translate(8 8)"><rect width="62" height="28" rx="5"/><text x="31" y="19" text-anchor="middle">国家</text></g>
<g class="zoom-button" data-level-button="city" transform="translate(76 8)"><rect width="62" height="28" rx="5"/><text x="31" y="19" text-anchor="middle">城市</text></g>
<g class="zoom-button" data-level-button="region" transform="translate(144 8)"><rect width="62" height="28" rx="5"/><text x="31" y="19" text-anchor="middle">区域</text></g>
<g class="zoom-button" data-action="out" transform="translate(214 8)"><rect width="28" height="28" rx="5"/><text x="14" y="19" text-anchor="middle">−</text></g>
<g class="zoom-button" data-action="in" transform="translate(248 8)"><rect width="28" height="28" rx="5"/><text x="14" y="19" text-anchor="middle">+</text></g>
<g class="zoom-button" data-action="reset" transform="translate(282 8)"><rect width="54" height="28" rx="5"/><text x="27" y="19" text-anchor="middle">复位</text></g>
<text id="zoom-status" class="zoom-status" x="10" y="62">国家 · 1.0×</text>
</g>"""

    script = f"""<script><![CDATA[
(() => {{
  const svg = document.documentElement;
  const controls = document.getElementById("map-controls");
  const status = document.getElementById("zoom-status");
  const labels = Array.from(svg.querySelectorAll("[data-font-size]"));
  const base = {{ x: {min_x:.6f}, y: {min_y:.6f}, width: {max_x - min_x:.6f}, height: {max_y - min_y:.6f} }};
  const levelZoom = {{ nation: 1, city: 6, region: 24 }};
  const minZoom = 1;
  const maxZoom = 64;
  let view = {{ x: {view_x:.6f}, y: {view_y:.6f}, width: {view_width:.6f}, height: {view_height:.6f} }};
  let drag = null;
  let suppressClick = false;

  const clamp = (value, low, high) => Math.max(low, Math.min(high, value));
  const zoom = () => base.width / view.width;
  const level = () => zoom() >= levelZoom.region ? "region" : zoom() >= levelZoom.city ? "city" : "nation";
  const levelName = {{ nation: "国家", city: "城市", region: "区域" }};

  function update() {{
    view.x = clamp(view.x, base.x, base.x + base.width - view.width);
    view.y = clamp(view.y, base.y, base.y + base.height - view.height);
    svg.setAttribute("viewBox", `${{view.x}} ${{view.y}} ${{view.width}} ${{view.height}}`);
    const currentZoom = zoom();
    const currentLevel = level();
    svg.dataset.level = currentLevel;
    status.textContent = `${{levelName[currentLevel]}} · ${{currentZoom.toFixed(1)}}×`;
    labels.forEach(label => {{
      label.setAttribute("font-size", Number(label.dataset.fontSize) / currentZoom);
      label.setAttribute("stroke-width", Number(label.dataset.strokeWidth) / currentZoom);
    }});
    const controlScale = view.width / 3000;
    controls.setAttribute("transform", `translate(${{view.x + 12 * controlScale}} ${{view.y + 12 * controlScale}}) scale(${{controlScale}})`);
    svg.querySelectorAll("[data-level-button]").forEach(button =>
      button.classList.toggle("active", button.dataset.levelButton === currentLevel));
  }}

  function clientPoint(event) {{
    const point = new DOMPoint(event.clientX, event.clientY);
    return point.matrixTransform(svg.getScreenCTM().inverse());
  }}

  function setZoom(targetZoom, anchor) {{
    const oldWidth = view.width;
    const oldHeight = view.height;
    const nextZoom = clamp(targetZoom, minZoom, maxZoom);
    const nextWidth = base.width / nextZoom;
    const nextHeight = base.height / nextZoom;
    const focus = anchor || {{ x: view.x + oldWidth / 2, y: view.y + oldHeight / 2 }};
    view.x = focus.x - (focus.x - view.x) * nextWidth / oldWidth;
    view.y = focus.y - (focus.y - view.y) * nextHeight / oldHeight;
    view.width = nextWidth;
    view.height = nextHeight;
    update();
  }}

  function focusAt(targetZoom, x, y) {{
    const nextZoom = clamp(targetZoom, minZoom, maxZoom);
    view.width = base.width / nextZoom;
    view.height = base.height / nextZoom;
    view.x = x - view.width / 2;
    view.y = y - view.height / 2;
    update();
  }}

  svg.addEventListener("wheel", event => {{
    event.preventDefault();
    setZoom(zoom() * Math.exp(-event.deltaY * 0.0015), clientPoint(event));
  }}, {{ passive: false }});
  svg.addEventListener("dblclick", event => {{
    if (!event.target.closest("#map-controls")) setZoom(zoom() * 2, clientPoint(event));
  }});
  svg.addEventListener("pointerdown", event => {{
    if (event.button !== 0 || event.target.closest("#map-controls")) return;
    const bounds = svg.getBoundingClientRect();
    drag = {{
      pointerId: event.pointerId,
      clientX: event.clientX,
      clientY: event.clientY,
      x: view.x,
      y: view.y,
      unitX: view.width / bounds.width,
      unitY: view.height / bounds.height,
      moved: false,
    }};
    svg.setPointerCapture(event.pointerId);
    svg.classList.add("dragging");
  }});
  svg.addEventListener("pointermove", event => {{
    if (!drag || drag.pointerId !== event.pointerId) return;
    drag.moved ||= Math.hypot(event.clientX - drag.clientX, event.clientY - drag.clientY) > 3;
    view.x = drag.x - (event.clientX - drag.clientX) * drag.unitX;
    view.y = drag.y - (event.clientY - drag.clientY) * drag.unitY;
    update();
  }});
  const stopDrag = event => {{
    if (drag && drag.pointerId === event.pointerId) {{
      suppressClick = drag.moved;
      drag = null;
      svg.classList.remove("dragging");
    }}
  }};
  svg.addEventListener("pointerup", stopDrag);
  svg.addEventListener("pointercancel", stopDrag);
  controls.addEventListener("pointerdown", event => event.stopPropagation());
  controls.addEventListener("click", event => {{
    const button = event.target.closest(".zoom-button");
    if (!button) return;
    if (button.dataset.levelButton) setZoom(levelZoom[button.dataset.levelButton]);
    if (button.dataset.action === "in") setZoom(zoom() * 2);
    if (button.dataset.action === "out") setZoom(zoom() / 2);
    if (button.dataset.action === "reset") setZoom(1);
  }});
  document.getElementById("map").addEventListener("click", event => {{
    if (suppressClick) {{
      suppressClick = false;
      return;
    }}
    const target = event.target.closest("[data-target-level]");
    if (!target) return;
    focusAt(
      levelZoom[target.dataset.targetLevel],
      Number(target.dataset.focusX),
      Number(target.dataset.focusY),
    );
  }});
  update();
}})();
]]></script>"""

    return "\n".join((
        '<?xml version="1.0" encoding="UTF-8"?>',
        f'<svg xmlns="http://www.w3.org/2000/svg" width="3000" height="3000" '
        f'viewBox="{view_x:.3f} {view_y:.3f} {view_width:.3f} {view_height:.3f}" '
        f'data-level="{initial_level}">',
        styles,
        '<rect x="-100000" y="-100000" width="200000" height="200000" fill="#f8fafc"/>',
        '<g id="map" font-family="Microsoft YaHei, Noto Sans CJK SC, sans-serif">',
        '<g class="nation-shapes">', *nation_shapes, '</g>',
        '<g class="city-shapes">', *city_shapes, '</g>',
        '<g class="region-shapes">', *region_shapes, '</g>',
        '<g class="nation-labels">', *nation_labels, '</g>',
        '<g class="city-labels">', *city_labels, '</g>',
        '<g class="region-labels">', *region_labels, '</g>',
        '</g>',
        controls,
        script,
        '</svg>',
    ))


def main() -> None:
    args = arguments()
    with args.input.open("r", encoding="utf-8") as source:
        data = json.load(source)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(
        render(data, args.show_skeleton, args.initial_level),
        encoding="utf-8",
    )
    print(f"Terra layout map written to {args.output.resolve()}")


if __name__ == "__main__":
    main()
