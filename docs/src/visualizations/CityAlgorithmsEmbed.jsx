import * as d3 from 'd3'
import { useEffect, useRef, useState } from 'react'

const COLORS = {
  ink: '#252b27', road: '#456d68', lime: '#9aae45', orange: '#d58a52',
  paper: '#e7e2d7', line: '#aaa69b', muted: '#737970', danger: '#a75128',
}

function AlgorithmPlayer({ id, eyebrow, title, stages, logic, draw, note }) {
  const host = useRef(null)
  const [stage, setStage] = useState(0)
  const [playing, setPlaying] = useState(false)
  const reducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches

  useEffect(() => {
    const svg = d3.select(host.current).selectAll('svg').data([null]).join('svg')
      .attr('viewBox', '0 0 680 360').attr('role', 'img')
      .attr('aria-label', `${title}：${stages[stage]}`)
    svg.interrupt()
    svg.selectAll('*').interrupt()
    svg.selectAll('*').remove()
    const frame = svg.append('g').attr('class', 'algorithm-viz__frame').attr('data-stage', stage)
    frame.append('title').text(`${title}：${stages[stage]}`)
    frame.append('rect').attr('x', 14).attr('y', 14).attr('width', 652).attr('height', 332)
      .attr('fill', COLORS.paper).attr('stroke', COLORS.line)
    draw(frame, stage, reducedMotion ? 0 : 320)
    return () => {
      frame.interrupt()
      frame.selectAll('*').interrupt()
      frame.remove()
    }
  }, [draw, stage, stages, title, reducedMotion])

  useEffect(() => {
    if (!playing) return undefined
    const timer = window.setTimeout(() => {
      if (stage === stages.length - 1) setPlaying(false)
      else setStage((value) => value + 1)
    }, 900)
    return () => window.clearTimeout(timer)
  }, [playing, stage, stages.length])

  return (
    <section className="algorithm-viz" aria-labelledby={id}>
      <header className="algorithm-viz__header">
        <div><p>{eyebrow}</p><h4 id={id}>{title}</h4></div>
        <span>{stage + 1} / {stages.length}</span>
      </header>
      <ol className="algorithm-viz__steps">
        {stages.map((label, index) => <li className={index === stage ? 'active' : index < stage ? 'done' : ''} key={label}>{label}</li>)}
      </ol>
      <div ref={host} className="algorithm-viz__canvas" />
      <div className="algorithm-viz__logic" aria-live="polite">
        <div><span>输入</span><b>{logic[stage].input}</b></div>
        <div><span>本步计算</span><code>{logic[stage].operation}</code></div>
        <div><span>判定条件</span><b>{logic[stage].guard}</b></div>
        <div><span>本步输出</span><b>{logic[stage].output}</b></div>
      </div>
      <div className="algorithm-viz__controls">
        <button type="button" onClick={() => { if (stage === stages.length - 1) setStage(0); setPlaying((value) => !value) }}>{playing ? '暂停' : '播放'}</button>
        <button type="button" disabled={stage === stages.length - 1} onClick={() => { setPlaying(false); setStage((value) => Math.min(stages.length - 1, value + 1)) }}>单步</button>
        <button type="button" disabled={stage === 0} onClick={() => { setPlaying(false); setStage(0) }}>重置</button>
        <strong aria-live="polite">{stages[stage]}</strong>
      </div>
      <p className="algorithm-viz__note">{note}</p>
    </section>
  )
}

function label(svg, x, y, text, options = {}) {
  return svg.append('text').attr('x', x).attr('y', y)
    .attr('text-anchor', options.anchor || 'middle').attr('fill', options.fill || COLORS.ink)
    .attr('font-size', options.size || 11).attr('font-weight', options.weight || 700).text(text)
}

function grid(svg, { x, y, columns, rows, size, usable = () => true }) {
  const cells = d3.cross(d3.range(columns), d3.range(rows)).map(([cx, cz]) => ({ cx, cz }))
  return svg.append('g').selectAll('rect').data(cells).join('rect')
    .attr('x', (cell) => x + cell.cx * size).attr('y', (cell) => y + cell.cz * size)
    .attr('width', size - 1).attr('height', size - 1)
    .attr('fill', (cell) => usable(cell) ? '#f1ede3' : '#cbc5b9')
    .attr('stroke', COLORS.line).attr('stroke-width', .6)
}

const nationDraw = (svg, stage, duration) => {
  const nations = [
    { name: '炎', color: '#456d68', points: [[95, 100], [205, 140], [300, 105]] },
    { name: '乌萨斯', color: '#9aae45', points: [[380, 70], [500, 115], [590, 85]] },
    { name: '维多利亚', color: '#b66b42', points: [[165, 295], [315, 260], [500, 310]] },
  ]
  const line = d3.line()
  const samples = nations.flatMap((nation, nationIndex) => nation.points.slice(1).flatMap((point, index) => {
    const start = nation.points[index]
    return d3.range(18).map((step) => {
      const t = step / 17
      return { x: start[0] + (point[0] - start[0]) * t, y: start[1] + (point[1] - start[1]) * t, nationIndex }
    })
  }))
  if (stage >= 1) {
    const delaunay = d3.Delaunay.from(samples, (point) => point.x, (point) => point.y)
    const voronoi = delaunay.voronoi([15, 15, 665, 345])
    svg.append('g').selectAll('path').data(samples).join('path')
      .attr('d', (_, index) => voronoi.renderCell(index))
      .attr('fill', (point) => nations[point.nationIndex].color)
      .attr('fill-opacity', stage === 1 ? .26 : .42)
      .attr('stroke', stage === 1 ? '#ffffff' : 'none').attr('stroke-width', .35)
      .style('opacity', 0).transition().duration(duration).style('opacity', 1)
  }
  nations.forEach((nation) => {
    svg.append('path').attr('d', line(nation.points)).attr('fill', 'none')
      .attr('stroke', nation.color).attr('stroke-width', stage === 0 ? 8 : 3)
      .attr('stroke-dasharray', stage >= 2 ? '7 5' : null)
    label(svg, nation.points[1][0], nation.points[1][1] - 15, nation.name)
  })
  if (stage === 2) label(svg, 340, 335, '合并同一国家的 primitive faces，得到地表国家单元', { size: 12 })
  if (stage >= 3) {
    svg.append('rect').attr('x', 490).attr('y', 205).attr('width', 105).attr('height', 105)
      .attr('fill', '#343934').attr('fill-opacity', .16).attr('stroke', COLORS.ink).attr('stroke-width', 3)
    label(svg, 542, 260, '地下国家', { size: 12 })
    label(svg, 542, 279, '弧长中点 + size', { size: 9, fill: COLORS.muted })
  }
}

const cityDraw = (svg, stage, duration) => {
  const boundary = [[75, 65], [560, 45], [625, 170], [550, 315], [120, 325], [45, 190]]
  const sites = [
    { name: '龙门', nx: -.52, nz: -.22, x: 185, y: 170 },
    { name: '尚蜀', nx: .28, nz: -.35, x: 405, y: 135 },
    { name: '玉门', nx: .15, nz: .48, x: 370, y: 265 },
  ]
  const line = d3.line()
  svg.append('path').attr('d', `${line(boundary)}Z`).attr('fill', '#f1ede3')
    .attr('stroke', COLORS.road).attr('stroke-width', 3)
  if (stage === 0) {
    const mini = svg.append('g').attr('transform', 'translate(235,100)')
    mini.append('rect').attr('width', 210).attr('height', 160).attr('fill', 'none').attr('stroke', COLORS.line)
    mini.append('line').attr('x1', 105).attr('x2', 105).attr('y2', 160).attr('stroke', COLORS.line)
    mini.append('line').attr('y1', 80).attr('x2', 210).attr('y2', 80).attr('stroke', COLORS.line)
    sites.forEach((site) => {
      mini.append('circle').attr('cx', (site.nx + 1) * 105).attr('cy', (site.nz + 1) * 80)
        .attr('r', 6).attr('fill', COLORS.orange)
      label(mini, (site.nx + 1) * 105, (site.nz + 1) * 80 - 10, `${site.name} (${site.nx}, ${site.nz})`, { size: 9 })
    })
  } else {
    const delaunay = d3.Delaunay.from(sites, (site) => site.x, (site) => site.y)
    const voronoi = delaunay.voronoi([45, 45, 625, 325])
    if (stage >= 2) {
      svg.append('defs').append('clipPath').attr('id', 'city-boundary-clip')
        .append('path').attr('d', `${line(boundary)}Z`)
      svg.append('g').attr('clip-path', 'url(#city-boundary-clip)').selectAll('path').data(sites).join('path')
        .attr('d', (_, index) => voronoi.renderCell(index)).attr('fill', (_, index) => ['#dce6bd', '#d9ded5', '#f0d2b7'][index])
        .attr('stroke', COLORS.muted).attr('stroke-width', 2).style('opacity', 0)
        .transition().duration(duration).style('opacity', .88)
    }
    sites.forEach((site) => {
      svg.append('circle').attr('cx', site.x).attr('cy', site.y).attr('r', 6).attr('fill', COLORS.ink)
      label(svg, site.x + 9, site.y - 9, site.name, { anchor: 'start' })
      if (stage === 1) label(svg, site.x, site.y + 23, `(${site.x}, ${site.y})`, { size: 9, fill: COLORS.muted })
    })
    if (stage >= 3) label(svg, 340, 338, '所有城市 Voronoi 单元再次裁剪到国家多边形内', { size: 11 })
  }
}

const regionDraw = (svg, stage) => {
  if (stage === 8) {
    label(svg, 340, 58, '特殊情况：随机采样未找到足够候选', { size: 15, fill: COLORS.danger })
    const sampled = d3.range(12).map((index) => ({ x: 78 + (index % 6) * 48, y: 105 + Math.floor(index / 6) * 48 }))
    svg.append('g').selectAll('circle').data(sampled).join('circle')
      .attr('cx', (item) => item.x).attr('cy', (item) => item.y).attr('r', 9)
      .attr('fill', (_, index) => index < 10 ? '#cbc5b9' : COLORS.danger)
    label(svg, 196, 230, '最多 128K 次尝试', { size: 11 })
    svg.append('path').attr('d', 'M 330 154 L 410 154').attr('stroke', COLORS.orange).attr('stroke-width', 5)
    label(svg, 370, 140, 'minPlotCount 未满足', { size: 9, fill: COLORS.danger })
    svg.append('rect').attr('x', 430).attr('y', 92).attr('width', 170).attr('height', 135)
      .attr('fill', '#dce6bd').attr('stroke', COLORS.road).attr('stroke-width', 3)
    d3.range(5).forEach((index) => svg.append('line').attr('x1', 445).attr('x2', 585)
      .attr('y1', 112 + index * 22).attr('y2', 112 + index * 22).attr('stroke', COLORS.road).attr('stroke-dasharray', '5 4'))
    label(svg, 515, 255, '回退：完整 frontier 枚举', { size: 12, fill: COLORS.road })
    return
  }
  if (stage === 9) {
    label(svg, 340, 58, '特殊情况：必选 Region 没有合法候选', { size: 15, fill: COLORS.danger })
    const checks = ['OUTSIDE_CITY', 'OVERLAPS_PLOT', 'NO_CONNECTION', 'COVERAGE_LIMIT']
    checks.forEach((text, index) => {
      svg.append('rect').attr('x', 70).attr('y', 94 + index * 52).attr('width', 235).attr('height', 36)
        .attr('fill', '#f0d2b7').attr('stroke', COLORS.danger)
      label(svg, 188, 117 + index * 52, text, { size: 10, fill: COLORS.danger })
    })
    svg.append('path').attr('d', 'M 330 180 L 405 180').attr('stroke', COLORS.danger).attr('stroke-width', 5)
    svg.append('rect').attr('x', 430).attr('y', 128).attr('width', 185).attr('height', 104)
      .attr('fill', COLORS.ink).attr('stroke', COLORS.danger).attr('stroke-width', 3)
    label(svg, 522, 169, '停止生成', { fill: '#fff', size: 15 })
    label(svg, 522, 197, 'MANDATORY_PLOTS_', { fill: '#f0d2b7', size: 10 })
    label(svg, 522, 214, 'CANNOT_FIT', { fill: '#f0d2b7', size: 10 })
    return
  }
  const usable = ({ cx, cz }) => !(cx < 2 && cz < 2) && !(cx > 13 && cz < 2) && !(cx < 1 && cz > 9)
  const cells = grid(svg, { x: 65, y: 45, columns: 16, rows: 12, size: 22, usable })
  if (stage >= 1) {
    svg.append('circle').attr('cx', 230).attr('cy', 177).attr('r', 10).attr('fill', COLORS.orange)
    label(svg, 230, 205, 'c*：最大边界净空', { size: 10 })
  }
  const candidates = [
    { id: 'A', x: 196, y: 111, w: 88, h: 66, score: 7.71, valid: true },
    { id: 'B', x: 306, y: 155, w: 66, h: 88, score: 7.39, valid: true },
    { id: 'C', x: 86, y: 67, w: 88, h: 66, score: 0, valid: false },
  ]
  if (stage === 2) {
    label(svg, 505, 112, '必选类型顺序', { anchor: 'start', size: 13 })
    label(svg, 505, 142, '1. weight 降序', { anchor: 'start', size: 10 })
    label(svg, 505, 166, '2. Region ID 升序', { anchor: 'start', size: 10 })
    label(svg, 505, 190, '3. 展开 minCount', { anchor: 'start', size: 10 })
    label(svg, 505, 225, '失败立即终止', { anchor: 'start', fill: COLORS.danger })
  }
  if (stage >= 3) {
    svg.append('g').selectAll('rect').data(candidates).join('rect')
      .attr('x', (item) => item.x).attr('y', (item) => item.y).attr('width', (item) => item.w).attr('height', (item) => item.h)
      .attr('fill', (item) => stage >= 6 && item.id === 'A' ? COLORS.orange : '#dce6bd')
      .attr('fill-opacity', (item) => stage >= 4 && !item.valid ? .18 : .78)
      .attr('stroke', (item) => stage >= 4 && !item.valid ? COLORS.danger : item.id === 'A' ? COLORS.orange : COLORS.road)
      .attr('stroke-width', (item) => stage >= 6 && item.id === 'A' ? 4 : 2)
      .attr('stroke-dasharray', (item) => stage >= 4 && !item.valid ? '5 4' : null)
    candidates.forEach((item) => label(svg, item.x + item.w / 2, item.y + item.h / 2, item.id))
  }
  if (stage === 4) label(svg, 535, 92, '硬门槛', { size: 13 })
  if (stage === 4) ['完整落城内', '不重叠', '道路间距', '覆盖率', '类型上限'].forEach((text, index) => label(svg, 535, 120 + index * 22, `${index + 1}. ${text}`, { anchor: 'start', size: 10 }))
  if (stage === 5) {
    label(svg, 535, 255, 'σA = 7.71', { anchor: 'start', fill: COLORS.orange })
    label(svg, 535, 277, 'σB = 7.39', { anchor: 'start', fill: COLORS.road })
  }
  if (stage === 6) {
    svg.append('rect').attr('x', 196).attr('y', 177).attr('width', 88).attr('height', 12).attr('fill', COLORS.road)
    label(svg, 240, 101, '接受 A + 占用连接道路', { fill: COLORS.danger })
  }
  if (stage === 7) {
    label(svg, 535, 307, '未到 maxPlotCount 时进入可选循环', { anchor: 'middle', fill: COLORS.road, size: 10 })
  }
  cells.lower()
}

const layerRoadDraw = (svg, stage) => {
  if (stage === 7) {
    label(svg, 340, 58, '特殊情况：Region 太小，clamp 后楼梯坐标重合', { size: 15, fill: COLORS.danger })
    grid(svg, { x: 220, y: 92, columns: 5, rows: 5, size: 48 })
    const collapsed = [[1,1],[3,1],[1,3],[1,3]]
    collapsed.forEach(([x, z], index) => {
      svg.append('rect').attr('x', 220 + x * 48).attr('y', 92 + z * 48).attr('width', 47).attr('height', 47)
        .attr('fill', index === 3 ? COLORS.danger : COLORS.orange).attr('fill-opacity', index === 3 ? .7 : 1)
      label(svg, 244 + x * 48, 121 + z * 48, `S${index + 1}`, { size: 10, fill: index === 3 ? '#fff' : COLORS.ink })
    })
    label(svg, 340, 330, 'distinct(stairs)=3 ≠ 4 → 抛出“核心区尺寸不足”', { size: 12, fill: COLORS.danger })
    return
  }
  const cellSize = 24
  grid(svg, { x: 58, y: 44, columns: 15, rows: 11, size: cellSize })
  const stairs = [[4, 3], [10, 3], [4, 7], [10, 7]]
  if (stage >= 0) stairs.forEach(([x, z], index) => {
    svg.append('rect').attr('x', 58 + x * cellSize).attr('y', 44 + z * cellSize)
      .attr('width', cellSize - 1).attr('height', cellSize - 1).attr('fill', COLORS.orange)
    label(svg, 58 + x * cellSize + 12, 44 + z * cellSize + 16, `S${index + 1}`, { size: 9 })
  })
  if (stage >= 1) {
    svg.append('circle').attr('cx', 58 + 8 * cellSize).attr('cy', 44 + 5 * cellSize).attr('r', 10).attr('fill', COLORS.lime)
    label(svg, 58 + 8 * cellSize, 44 + 5 * cellSize - 16, '本层 hub', { size: 10 })
    svg.append('rect').attr('x', 58).attr('y', 44 + 5 * cellSize).attr('width', cellSize - 1).attr('height', cellSize - 1)
      .attr('fill', COLORS.orange)
    label(svg, 70, 44 + 5 * cellSize - 8, 'Entrance 投影', { anchor: 'start', size: 9 })
  }
  if (stage >= 2) {
    let roads
    if (stage === 2) {
      roads = [[[2, 3], [13, 3]], [[2, 7], [13, 7]], [[5, 1], [5, 9]], [[10, 1], [10, 9]]]
      label(svg, 500, 70, 'GRID', { size: 13 })
    } else if (stage === 3) {
      roads = [[[3, 2], [12, 2], [12, 8], [3, 8], [3, 2]], [[6, 4], [9, 4], [9, 6], [6, 6], [6, 4]]]
      label(svg, 500, 70, 'CONCENTRIC', { size: 13 })
    } else if (stage === 4) {
      roads = [[[1, 5], [14, 5]], [[8, 0], [8, 10]], [[3, 2], [12, 8]], [[3, 8], [12, 2]]]
      label(svg, 500, 70, 'RADIAL_GRID', { size: 13 })
    } else {
      roads = [[[2, 5], [13, 5]], [[8, 1], [8, 9]], ...stairs.map(([x, z]) => [[x, z], [8, 5]])]
    }
    roads.forEach((road) => svg.append('path')
      .attr('d', d3.line().curve(d3.curveStepAfter)(road.map(([x, z]) => [58 + x * cellSize + 12, 44 + z * cellSize + 12])))
      .attr('fill', 'none').attr('stroke', COLORS.road).attr('stroke-width', 8).attr('stroke-linecap', 'square'))
  }
  if (stage >= 6) {
    const names = ['POWER', 'SUPPORT', 'LIFE', 'SURFACE']
    names.forEach((name, index) => {
      svg.append('rect').attr('x', 455).attr('y', 65 + index * 58).attr('width', 165).attr('height', 42)
        .attr('fill', index === 3 ? '#f0d2b7' : '#dce6bd').attr('stroke', COLORS.line)
      label(svg, 537, 83 + index * 58, name, { size: 10 })
      label(svg, 537, 98 + index * 58, '同楼梯 · 独立道路随机源', { size: 8, fill: COLORS.muted })
    })
  }
}

const cleanupDraw = (svg, stage) => {
  if (stage === 5) {
    grid(svg, { x: 174, y: 54, columns: 5, rows: 4, size: 58 })
    const square = [[1,1],[2,1],[1,2],[2,2]]
    square.forEach(([x, z], index) => {
      svg.append('rect').attr('x', 174 + x * 58).attr('y', 54 + z * 58).attr('width', 57).attr('height', 57)
        .attr('fill', index === 0 ? COLORS.orange : COLORS.road).attr('stroke', COLORS.ink).attr('stroke-width', 2)
    })
    label(svg, 261, 137, '楼梯', { size: 10 })
    svg.append('path').attr('d', 'M 390 150 L 462 150').attr('stroke', COLORS.danger).attr('stroke-width', 5).attr('stroke-dasharray', '8 5')
    label(svg, 426, 134, '跳过', { fill: COLORS.danger })
    label(svg, 545, 132, 'candidate ∈ protectedCells', { size: 10 })
    label(svg, 545, 157, '不做删除测试', { fill: COLORS.danger })
    label(svg, 340, 300, '特殊情况：Entrance 与楼梯即使 degree 最低也不能删除', { size: 12, fill: COLORS.danger })
    return
  }
  const points = [[2,1],[3,1],[4,1],[2,2],[3,2],[4,2],[3,3]]
  const size = 62
  grid(svg, { x: 150, y: 45, columns: 6, rows: 4, size })
  points.forEach(([x, z]) => svg.append('rect').attr('x', 150 + x * size).attr('y', 45 + z * size)
    .attr('width', size - 2).attr('height', size - 2)
    .attr('fill', stage >= 4 && x === 3 && z === 2 ? '#f1ede3' : COLORS.road)
    .attr('stroke', x === 3 && z === 2 ? COLORS.orange : '#2f5f58').attr('stroke-width', 3))
  if (stage === 1) label(svg, 540, 112, '候选 degree：2', { anchor: 'start' })
  if (stage === 2) label(svg, 540, 112, '删除后仍连通：通过', { anchor: 'start', fill: COLORS.road })
  if (stage === 3) label(svg, 540, 112, '所有非道路格仍临路：通过', { anchor: 'start', fill: COLORS.road })
  if (stage === 4) label(svg, 540, 112, '删除并重新扫描', { anchor: 'start', fill: COLORS.danger })
}

const parcelDraw = (svg, stage) => {
  if (stage === 5) {
    const size = 52
    grid(svg, { x: 92, y: 66, columns: 5, rows: 4, size })
    const occupied = [[0,0],[1,0],[0,1],[1,1],[3,0],[4,0],[3,1],[4,1],[0,3],[1,3],[2,3],[3,3],[4,3]]
    svg.append('g').selectAll('rect').data(occupied).join('rect')
      .attr('x', ([x]) => 92 + x * size).attr('y', ([,z]) => 66 + z * size)
      .attr('width', size - 1).attr('height', size - 1).attr('fill', COLORS.road)
    svg.append('rect').attr('x', 92 + 3 * size).attr('y', 66 + 2 * size).attr('width', size - 1).attr('height', size - 1)
      .attr('fill', '#f0d2b7').attr('stroke', COLORS.danger).attr('stroke-width', 4)
    label(svg, 92 + 3.5 * size, 66 + 2.45 * size, '孤立', { size: 10 })
    svg.append('path').attr('d', 'M 390 170 L 455 170').attr('stroke', COLORS.orange).attr('stroke-width', 5)
    label(svg, 520, 117, '2×2：失败', { size: 12, fill: COLORS.muted })
    label(svg, 520, 151, '1×2 / 2×1：失败', { size: 12, fill: COLORS.muted })
    label(svg, 520, 185, '1×1：接受', { size: 14, fill: COLORS.danger })
    label(svg, 520, 220, '仍属于当前 UrbanBlock', { size: 10, fill: COLORS.road })
    label(svg, 340, 312, '特殊情况单独成帧：清空尺寸说明后再演示 1×1 退化', { size: 11, fill: COLORS.danger })
    return
  }
  const size = 34
  const roads = new Set(['0,3','1,3','2,3','3,3','4,3','5,3','6,3','3,0','3,1','3,2','3,4','3,5','3,6'])
  const cells = d3.cross(d3.range(7), d3.range(7)).map(([x, z]) => ({ x, z, road: roads.has(`${x},${z}`) }))
  svg.append('g').selectAll('rect').data(cells).join('rect')
    .attr('x', (cell) => 70 + cell.x * size).attr('y', (cell) => 54 + cell.z * size)
    .attr('width', size - 2).attr('height', size - 2)
    .attr('fill', (cell) => cell.road ? COLORS.road : stage >= 1 ? (cell.x < 3 ? '#dce6bd' : '#f0d2b7') : '#f1ede3')
    .attr('stroke', COLORS.line)
  if (stage >= 2) {
    const parcels = [[0,0,2,2],[2,0,1,2],[4,0,2,2],[6,0,1,2],[0,4,2,2],[2,4,1,2],[4,4,2,2],[6,4,1,2]]
    parcels.forEach(([x,z,w,h], index) => {
      svg.append('rect').attr('x', 70 + x * size).attr('y', 54 + z * size)
        .attr('width', w * size - 2).attr('height', h * size - 2).attr('fill', 'none')
        .attr('stroke', COLORS.orange).attr('stroke-width', 3)
      label(svg, 70 + (x + w / 2) * size, 54 + (z + h / 2) * size, `P${index}`, { size: 9 })
    })
  }
  if (stage === 3) {
    label(svg, 405, 105, '1. 先切 2×2 unique', { anchor: 'start' })
    label(svg, 405, 135, '2. 再切 1×2 / 2×1', { anchor: 'start' })
    label(svg, 405, 165, '3. 剩余退化为 1×1', { anchor: 'start' })
  }
  if (stage === 4) {
    label(svg, 405, 220, '记录每个真实临路面', { anchor: 'start', fill: COLORS.road })
    label(svg, 405, 247, 'RoadClass / 方向 / Road ID', { anchor: 'start', fill: COLORS.muted, size: 10 })
  }
}

const roadTileDraw = (svg, stage) => {
  const patterns = [
    [], ['N'], ['N','S'], ['N','E'], ['N','E','S'], ['N','E','S','W'],
  ]
  const names = ['isolated', 'end', 'straight', 'corner', 'tee', 'cross']
  const active = patterns[stage]
  const cx = 250
  const cy = 180
  svg.append('rect').attr('x', cx - 42).attr('y', cy - 42).attr('width', 84).attr('height', 84)
    .attr('fill', COLORS.road).attr('stroke', '#2f5f58').attr('stroke-width', 4)
  const directions = { N: [cx, cy - 120, cx, cy - 42], E: [cx + 42, cy, cx + 120, cy], S: [cx, cy + 42, cx, cy + 120], W: [cx - 120, cy, cx - 42, cy] }
  Object.entries(directions).forEach(([name, [x1,y1,x2,y2]]) => {
    svg.append('line').attr('x1', x1).attr('y1', y1).attr('x2', x2).attr('y2', y2)
      .attr('stroke', active.includes(name) ? COLORS.road : COLORS.line)
      .attr('stroke-width', active.includes(name) ? 22 : 3).attr('stroke-dasharray', active.includes(name) ? null : '5 5')
  })
  label(svg, 485, 135, `连接掩码：${active.length ? active.join(' + ') : '空'}`, { anchor: 'start', size: 13 })
  label(svg, 485, 175, `模板：${names[stage]}`, { anchor: 'start', fill: COLORS.orange, size: 15 })
  label(svg, 485, 207, `旋转：${stage === 2 ? 'NONE' : stage === 3 ? 'CLOCKWISE_90' : '按基准方向换算'}`, { anchor: 'start', size: 10 })
}

const runtimeDraw = (svg, stage) => {
  const choices = ['共享楼梯', '道路构件', '地下通用构件', '地表建筑锚点']
  choices.forEach((choice, index) => {
    const active = index === Math.min(stage, choices.length - 1)
    svg.append('rect').attr('x', 115 + index * 135).attr('y', 145).attr('width', 112).attr('height', 72)
      .attr('fill', active ? COLORS.orange : index < stage ? '#dce6bd' : '#d9d4c9')
      .attr('stroke', active ? COLORS.danger : COLORS.line).attr('stroke-width', active ? 3 : 1)
    label(svg, 171 + index * 135, 176, `${index + 1}`, { size: 15 })
    label(svg, 171 + index * 135, 197, choice, { size: 9 })
    if (index < choices.length - 1) label(svg, 239 + index * 135, 184, index < stage ? '否' : '待判定', { size: 8, fill: COLORS.muted })
  })
  label(svg, 340, 85, `当前 Chunk · 第 ${stage + 1} 个优先级判断`, { size: 14 })
  if (stage >= 4) label(svg, 340, 270, '只消费 schema v16 布局；运行时不重新求解几何', { fill: COLORS.road, size: 12 })
  if (stage === 5) {
    svg.selectAll('*').remove()
    label(svg, 340, 58, '特殊情况：footprint 覆盖格不是建筑锚点', { size: 15, fill: COLORS.danger })
    const chunks = [{ x: 0, label: '锚点', active: true }, { x: 1, label: '覆盖格', active: false }]
    chunks.forEach((chunk) => {
      svg.append('rect').attr('x', 190 + chunk.x * 150).attr('y', 105).attr('width', 130).attr('height', 130)
        .attr('fill', chunk.active ? '#f0d2b7' : '#d9d4c9').attr('stroke', chunk.active ? COLORS.orange : COLORS.line).attr('stroke-width', 3)
      label(svg, 255 + chunk.x * 150, 165, chunk.label, { size: 14 })
      label(svg, 255 + chunk.x * 150, 191, chunk.active ? '放置 1 次' : '跳过放置', { size: 11, fill: chunk.active ? COLORS.danger : COLORS.muted })
    })
    label(svg, 340, 285, 'buildingByAnchor 仅在最小 Chunk 坐标命中，避免同一建筑重复生成', { size: 11, fill: COLORS.road })
  }
}

export function NationBoundaryEmbed() {
  return <AlgorithmPlayer id="nation-boundary-viz" eyebrow="D3 · POLYLINE VORONOI" title="国家边界：折线站点、面合并与地下例外" stages={['折线站点', 'primitive faces', '合并国家单元', '地下固定正方形']} logic={[
    { input: '地表国家的 relativePoints 与核心半径', operation: '依次连接相邻控制点，形成折线站点 Lᵢ', guard: '地下国家不进入折线 Voronoi', output: '带国家 ID 的线段集合' },
    { input: '任意位置 p 与全部折线 Lᵢ', operation: 'd(p,Lᵢ)=minₖ d(p,[aₖ,bₖ])', guard: '距离最小的折线赢得当前位置', output: '按线段细分的 primitive faces' },
    { input: '同一国家名下的全部 primitive faces', operation: 'union(facesᵢ) ∩ Terra 矩形边界', guard: '只合并国家 ID 相同的面', output: '每个地表国家的单一边界单元' },
    { input: '地下国家折线总长与 size', operation: '沿弧长取中点，以 size/2 向四侧展开', guard: '正方形必须落在 Terra 核心范围内', output: '地下国家固定正方形边界' },
  ]} draw={nationDraw} note="动画用密集点采样近似展示折线距离分区；项目源码会直接处理折线顶点与线段，并合并同一国家的 primitive faces。" />
}

export function CityBoundaryEmbed() {
  return <AlgorithmPlayer id="city-boundary-viz" eyebrow="D3 · POINT VORONOI" title="城市边界：归一化坐标映射与多边形裁剪" stages={['相对坐标', '映射成点站点', '点 Voronoi', '裁剪到国家边界']} logic={[
    { input: '城市归一化坐标 (u,v)', operation: '(u,v) ∈ [-1,1]²', guard: '每个城市坐标必须位于归一化方形内', output: '与地图尺寸无关的相对位置' },
    { input: '国家中心 c、方向 (u,v) 与边界', operation: 's=c+λ(u,v)，λ 取射线与边界的交点比例', guard: '映射点 s 必须位于国家多边形内', output: '城市点站点 sᵢ' },
    { input: '全部城市点站点 sᵢ', operation: 'Vᵢ={p | ‖p-sᵢ‖≤‖p-sⱼ‖}', guard: '到当前站点距离不大于其他站点', output: '未裁剪的城市 Voronoi 单元' },
    { input: '城市单元 Vᵢ 与国家多边形 N', operation: 'Cᵢ=Vᵢ ∩ N', guard: '单元边界不得越过国家边界', output: '城市边界与相邻城市关系' },
  ]} draw={cityDraw} note="示例坐标为缩小演示；归一化映射、点站点 Voronoi 和国家边界裁剪顺序与源码一致。" />
}

export function RegionGrowthEmbed() {
  return <AlgorithmPlayer id="region-growth-viz" eyebrow="D3 · REGION GROWTH" title="Region 生长：Chunk、核心、类型、候选与评分" stages={['完整 Chunk', '寻找城市核心', '展开必选类型', '生成候选', '硬门槛过滤', '计算总分', '接受最高分', '可选类型循环', '采样耗尽回退', '必选无解失败']} logic={[
    { input: '城市多边形与候选 Chunk', operation: '检查 Chunk 的四角和中心是否都在城内', guard: '5 个采样点全部通过才算可用', output: '完整可用 Chunk 集合 U' },
    { input: '可用集合 U 与城市边界', operation: 'c*=argmax₍c∈U₎ clearance(c,boundary)', guard: '净空相同则按稳定坐标顺序决胜', output: '城市核心 Chunk c*' },
    { input: 'Region 类型的 minCount、weight 与 ID', operation: 'weight 降序 → ID 升序 → 展开 minCount', guard: '任一必选类型无法放置就立即失败', output: '稳定的必选放置队列' },
    { input: '当前 frontier 与类型尺寸', operation: '最多采样 8K 个前沿；尝试 128K 次后全量枚举', guard: '候选矩形需与 frontier 接触', output: '本轮候选矩形集合' },
    { input: '候选矩形与已占用布局', operation: '依次检查城内、重叠、路距、覆盖率、类型上限', guard: '五项硬门槛全部通过', output: '可评分候选集合 F' },
    { input: '候选的紧凑度 C、边界 B、邻接 A、质量 Q', operation: 'σ=4C+2B+1.5A+2Q', guard: '所有分量先按源码定义归一化', output: '每个候选的总分 σ' },
    { input: '可评分候选 F 与总分 σ', operation: '选择 argmax σ，并占用 PLOT 与连接道路', guard: '同分时使用稳定顺序，保证同种子复现', output: '新 Region 与更新后的 frontier' },
    { input: '可选类型的 effectiveWeight', operation: '按加权稳定顺序继续生成候选并评分', guard: '达到 maxPlotCount 或无合法候选时停止', output: '最终城市 Region 布局' },
    { input: '随机采样结果、frontier 与当前 plotCount', operation: '若采样不足且 plotCount<minPlotCount，则枚举完整 frontier', guard: '只有城市下限尚未满足时启用昂贵回退', output: '完整候选集合或确认确实无解' },
    { input: '必选类型与过滤后的空候选集', operation: 'candidate.isEmpty() → failure', guard: '必选实例不能跳过，也不能换成可选类型', output: 'MANDATORY_PLOTS_CANNOT_FIT' },
  ]} draw={regionDraw} note="候选数值是文中算例；类型顺序、硬门槛和 4C + 2B + 1.5A + 2Q 评分公式来自 MobileCityLayoutGenerator。" />
}

export function LayerRoadEmbed() {
  return <AlgorithmPlayer id="layer-road-viz" eyebrow="D3 · FOUR LAYERS" title="四层道路：入口、三种骨架与共享楼梯" stages={['放置共享楼梯', '入口投影与本层 hub', 'GRID', 'CONCENTRIC', 'RADIAL_GRID', '楼梯接入道路', '四层独立完成', '楼梯重合失败']} logic={[
    { input: 'Region 核心、楼梯偏移与边界', operation: 'core+offset → clamp → 去重', guard: '楼梯 X/Z 在四层完全一致且彼此不同', output: '共享楼梯坐标 S' },
    { input: '入口方向、可用边界格与本层道路域', operation: '取方向对应的最近边界格，并选本层 hub', guard: '距离相同按固定方向顺序决胜', output: 'Entrance 与 hub' },
    { input: 'GRID 参数：行列间隔与偏移', operation: '按固定间隔铺设横纵主路', guard: '仅保留落在 Region 内的道路格', output: '网格道路骨架' },
    { input: 'CONCENTRIC 参数与 Region 外框', operation: '由外向内生成同心矩形环', guard: '环宽高必须仍能形成闭环', output: '同心环道路骨架' },
    { input: 'RADIAL_GRID 的 hub、射线与辅路参数', operation: '从 hub 放射，再叠加稀疏网格', guard: '射线和辅路都裁剪到 Region 内', output: '放射网格道路骨架' },
    { input: '楼梯集合 S 与当前道路骨架 R', operation: '对每个楼梯运行 BFS，连接到最近道路格', guard: '不得穿越 Region 外或不可用格', output: '楼梯全部接入的连通道路 R′' },
    { input: '四层 ID、共享楼梯与世界种子', operation: 'seedₗ=stable(seed,layerId)，逐层独立求路', guard: '楼梯坐标共享；道路随机源互不干扰', output: 'POWER / SUPPORT / LIFE / SURFACE 四层道路' },
    { input: '尺寸过小的 Region 与 clamp 后的四个楼梯点', operation: 'distinct(stairs).count()', guard: '结果必须等于 4', output: '不足 4 时抛出“核心区尺寸不足”并停止' },
  ]} draw={layerRoadDraw} note="四层共用楼梯 X/Z；每层使用独立稳定随机源生成道路。三种道路骨架逐项演示，不代表某个导出 Region 的复刻。" />
}

export function RoadCleanupEmbed() {
  return <AlgorithmPlayer id="road-cleanup-viz" eyebrow="D3 · SAFE REMOVAL" title="2×2 道路块：按度数尝试安全删除" stages={['发现 2×2', '按 roadDegree 排序', '检查道路连通', '检查所有格仍临路', '删除并重扫', '保护格直接跳过']} logic={[
    { input: '道路集合 R', operation: '扫描每个左上角，检测四格是否都属于 R', guard: '四格齐全才构成待清理 2×2', output: '2×2 道路块候选' },
    { input: '候选四格与保护格集合 P', operation: '按 roadDegree(c) 升序排列', guard: 'Entrance 与楼梯等 c∈P 的格子跳过', output: '优先尝试删除的低度数道路格' },
    { input: '临时道路 R\\{c}', operation: '从任一道路格 BFS，统计可达数量', guard: 'visitedCount=|R|-1', output: '删除后仍保持道路连通' },
    { input: '临时道路 R\\{c} 与所有非道路格', operation: '逐格检查四邻域是否含道路', guard: 'allCellsRoadAdjacent=true', output: '每个地块仍至少有一个临路面' },
    { input: '通过两项安全检查的候选 c', operation: 'R←R\\{c}，随后从头重新扫描', guard: '任一检查失败则保留 c 并尝试下一格', output: '无可安全删除 2×2 的道路集合' },
    { input: '候选 c 与 protectedCells', operation: 'protectedCells.contains(c)', guard: '楼梯或 Entrance 命中时直接 continue', output: '保留保护道路格，不进入删除测试' },
  ]} draw={cleanupDraw} note="Entrance 与楼梯属于保护格，不能删除；任何连通性或临路检查失败都会跳过当前候选。" />
}

export function ParcelPartitionEmbed() {
  return <AlgorithmPlayer id="parcel-partition-viz" eyebrow="D3 · PARCEL PARTITION" title="从道路光栅到 UrbanBlock 与 Parcel" stages={['稳定道路', '非道路 BFS 分组', '切分 Parcel', '按尺寸优先级处理', '记录临路面', '孤立格退化 1×1']} logic={[
    { input: '清理完成的道路集合 R 与 Region 全部格子 U', operation: 'N=U\\R', guard: '道路结果固定后才开始分块', output: '待分配的非道路格集合 N' },
    { input: '非道路格集合 N', operation: '以四邻域 BFS 求 connectedComponents(N)', guard: 'BFS 不可跨越道路格', output: '相互独立的 UrbanBlock' },
    { input: '一个 UrbanBlock 的未占用格', operation: '按锚点稳定顺序尝试矩形 footprint', guard: 'footprint 内每格未占用且属于同一 Block', output: '不重叠的 Parcel 集合' },
    { input: '地表可用 footprint', operation: '依次尝试 2×2 unique、1×2 / 2×1、1×1', guard: '无法组成大 Parcel 时必须退化，地下层固定 1×1', output: '覆盖全部非道路格的 Parcel' },
    { input: 'Parcel 边界与相邻道路格', operation: '按 N/E/S/W 扫描并记录 RoadClass、方向、Road ID', guard: '只记录真实共享边的道路连接', output: '建筑选型可直接消费的 roadConnections' },
    { input: '无法组成 2×2、1×2 或 2×1 的剩余格', operation: '为当前稳定锚点创建 1×1 Parcel', guard: '剩余格仍必须属于当前 UrbanBlock', output: '不丢格且完全覆盖的 Parcel 分区' },
  ]} draw={parcelDraw} note="地表优先 2×2 unique，再尝试面积 2，最后退化到 1×1；下三层当前全部使用 1×1 Parcel。" />
}

export function RoadTileEmbed() {
  const masks = ['0000', '1000', '1010', '1100', '1110', '1111']
  const outputs = ['isolated', 'end + 基准旋转', 'straight + 轴向旋转', 'corner + 象限旋转', 'tee + 缺口方向旋转', 'cross + NONE']
  return <AlgorithmPlayer id="road-tile-viz" eyebrow="D3 · CONNECTION MASK" title="道路四向连接怎样选择模板与旋转" stages={['isolated', 'end', 'straight', 'corner', 'tee', 'cross']} logic={masks.map((mask, index) => ({
    input: `按 N/E/S/W 读取相邻道路：${mask}`,
    operation: `degree=popcount(${mask})=${mask.split('').filter((bit) => bit === '1').length}`,
    guard: index === 2 ? 'degree=2 且两个方向相反' : index === 3 ? 'degree=2 且两个方向相邻' : `degree=${mask.split('').filter((bit) => bit === '1').length}`,
    output: outputs[index],
  }))} draw={roadTileDraw} note="北、东、南、西连接掩码统一由 RegionLayout.roadTile(...) 分类，导出器和运行时共用同一结果。" />
}

export function RuntimePlacementEmbed() {
  return <AlgorithmPlayer id="runtime-placement-viz" eyebrow="D3 · RUNTIME LOOKUP" title="运行时逐 Chunk 放置优先级" stages={['检查楼梯', '检查道路', '检查地下层', '检查地表锚点', '完成查表放置', '非锚点覆盖格跳过']} logic={[
    { input: '当前 Chunk 坐标与 schema v16 楼梯索引', operation: 'stairsByChunk.get(chunkKey)', guard: '命中楼梯时优先放置并结束本 Chunk 分支', output: '共享楼梯构件或继续检查' },
    { input: '当前 Chunk 与 roadTile 查表结果', operation: 'roadTile(chunk) → template + rotation', guard: '命中道路格时不再尝试建筑', output: '已定向道路构件或继续检查' },
    { input: '当前层 ID 与地下通用构件表', operation: 'undergroundPiece(layerId,chunkKey)', guard: '仅 POWER / SUPPORT / LIFE 层进入此分支', output: '地下通用构件或继续检查' },
    { input: '地表 Parcel、建筑锚点与 footprint', operation: 'buildingByAnchor.get(chunkKey)', guard: '只有最小坐标锚点放置；其余 footprint Chunk 跳过', output: '建筑模板与 rotation' },
    { input: '前四级查表结果', operation: '按首个命中项调用结构放置器', guard: '运行时不重新生成边界、道路或 Parcel', output: '与导出布局一致的 Chunk 内容' },
    { input: '建筑 footprint 内的非最小坐标 Chunk', operation: 'buildingByAnchor.get(chunkKey)=null', guard: '只有锚点 Chunk 可以触发结构展开', output: '当前 Chunk 不重复放置同一建筑' },
  ]} draw={runtimeDraw} note="该阶段只查询已导出的 schema v16；已有世界中已经生成的 Chunk 不会因资源变化自动重建。" />
}
