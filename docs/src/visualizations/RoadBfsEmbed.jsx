import * as d3 from 'd3'
import { useEffect, useRef, useState } from 'react'

const WIDTH = 9
const HEIGHT = 7
const CELL_SIZE = 52
const CELL_GAP = 4
const ROAD_CELLS = [
  [1, 1], [1, 2], [1, 3], [2, 3], [3, 3], [4, 3],
]
const DIRECTIONS = [
  { name: '北', dx: 0, dz: -1 },
  { name: '东', dx: 1, dz: 0 },
  { name: '南', dx: 0, dz: 1 },
  { name: '西', dx: -1, dz: 0 },
]

const key = (x, z) => `${x},${z}`

function buildDistanceField() {
  const cells = Array.from({ length: HEIGHT }, (_, z) => (
    Array.from({ length: WIDTH }, (_, x) => ({ x, z, distance: Number.POSITIVE_INFINITY }))
  )).flat()
  const byKey = new Map(cells.map((cell) => [key(cell.x, cell.z), cell]))
  const queue = []

  for (const [x, z] of ROAD_CELLS) {
    const cell = byKey.get(key(x, z))
    cell.distance = 0
    queue.push(cell)
  }
  for (let cursor = 0; cursor < queue.length; cursor++) {
    const current = queue[cursor]
    for (const { dx, dz } of DIRECTIONS) {
      const next = byKey.get(key(current.x + dx, current.z + dz))
      if (!next || Number.isFinite(next.distance)) continue
      next.distance = current.distance + 1
      queue.push(next)
    }
  }

  const maximum = d3.max(cells, (cell) => cell.distance)
  const target = cells
    .filter((cell) => cell.distance === maximum)
    .sort((first, second) => second.x - first.x || second.z - first.z)[0]
  const path = [target]
  const decisions = []
  while (path.at(-1).distance > 0) {
    const current = path.at(-1)
    const evaluations = DIRECTIONS.map((direction) => {
      const next = byKey.get(key(current.x + direction.dx, current.z + direction.dz))
      const eligible = Boolean(next && next.distance === current.distance - 1)
      return { ...direction, next, eligible, weight: eligible ? 1 : 0 }
    })
    const eligible = evaluations.filter((candidate) => candidate.eligible)
    // 固定游标只负责让文档演示可复现；生产代码使用该层的 java.util.Random。
    const selected = eligible[(current.x * 31 + current.z * 17 + decisions.length * 7) % eligible.length]
    decisions.push({ current, evaluations, selected, eligibleCount: eligible.length })
    path.push(selected.next)
  }
  return { cells, maximum, target, path, decisions }
}

const FIELD = buildDistanceField()
const TOTAL_FRAMES = FIELD.maximum + FIELD.path.length

export default function RoadBfsEmbed() {
  const host = useRef(null)
  const chart = useRef(null)
  const [frame, setFrame] = useState(0)
  const [playing, setPlaying] = useState(false)
  const [interval, setIntervalMs] = useState(650)
  const reducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches

  useEffect(() => {
    const chartWidth = WIDTH * CELL_SIZE + 86
    const chartHeight = HEIGHT * CELL_SIZE + 76
    const svg = d3.select(host.current)
      .append('svg')
      .attr('viewBox', `0 0 ${chartWidth} ${chartHeight}`)
      .attr('role', 'img')
      .attr('aria-label', '道路多源广度优先搜索距离场与最短梯度接路动画')

    svg.append('title').text('道路 BFS 距离场')
    svg.append('desc').text(
      '绿色格是既有道路。动画从所有道路格同时扩展距离波前，随后从最远格沿距离递减路径回到道路。',
    )
    const layer = svg.append('g').attr('transform', 'translate(54,24)')
    const cellGroups = layer.selectAll('g')
      .data(FIELD.cells, (cell) => key(cell.x, cell.z))
      .join('g')
      .attr('transform', (cell) => `translate(${cell.x * CELL_SIZE},${cell.z * CELL_SIZE})`)

    cellGroups.append('rect')
      .attr('width', CELL_SIZE - CELL_GAP)
      .attr('height', CELL_SIZE - CELL_GAP)
      .attr('rx', 3)
      .attr('stroke-width', 2)
    cellGroups.append('text')
      .attr('x', (CELL_SIZE - CELL_GAP) / 2)
      .attr('y', (CELL_SIZE - CELL_GAP) / 2 + 5)
      .attr('text-anchor', 'middle')
      .attr('font-family', '"JetBrains Mono", monospace')
      .attr('font-size', 13)
      .attr('font-weight', 700)

    svg.append('text').attr('x', 18).attr('y', 20)
      .attr('fill', '#62685f').attr('font-size', 12).text('Z')
    svg.append('text').attr('x', chartWidth - 20).attr('y', chartHeight - 16)
      .attr('fill', '#62685f').attr('font-size', 12).text('X')
    layer.selectAll('.x-label').data(d3.range(WIDTH)).join('text')
      .attr('class', 'x-label')
      .attr('x', (value) => value * CELL_SIZE + (CELL_SIZE - CELL_GAP) / 2)
      .attr('y', HEIGHT * CELL_SIZE + 15)
      .attr('text-anchor', 'middle').attr('fill', '#777c73').attr('font-size', 10)
      .text((value) => value)
    layer.selectAll('.z-label').data(d3.range(HEIGHT)).join('text')
      .attr('class', 'z-label')
      .attr('x', -13)
      .attr('y', (value) => value * CELL_SIZE + (CELL_SIZE - CELL_GAP) / 2 + 4)
      .attr('text-anchor', 'middle').attr('fill', '#777c73').attr('font-size', 10)
      .text((value) => value)

    const pathLayer = layer.append('path')
      .attr('fill', 'none').attr('stroke', '#b85c2e').attr('stroke-width', 5)
      .attr('stroke-linecap', 'square').attr('stroke-linejoin', 'round')
      .attr('pointer-events', 'none')
    const distanceColor = d3.scaleLinear()
      .domain([1, FIELD.maximum])
      .range(['#dce6bd', '#78968b'])
      .interpolate(d3.interpolateRgb)

    chart.current = { svg, cellGroups, pathLayer, distanceColor }
    return () => {
      svg.interrupt()
      svg.selectAll('*').interrupt()
      svg.remove()
      chart.current = null
    }
  }, [])

  useEffect(() => {
    if (!chart.current) return
    const { svg, cellGroups, pathLayer, distanceColor } = chart.current
    svg.interrupt()
    cellGroups.interrupt()
    cellGroups.selectAll('*').interrupt()
    pathLayer.interrupt()
    const waveFrame = Math.min(frame, FIELD.maximum)
    const pathCount = Math.max(0, frame - FIELD.maximum)
    const visiblePath = FIELD.path.slice(0, pathCount)
    const pathKeys = new Set(visiblePath.map((cell) => key(cell.x, cell.z)))
    const transition = svg.transition()
      .duration(reducedMotion ? 0 : 220)
      .ease(d3.easeCubicOut)

    cellGroups.select('rect').transition(transition)
      .attr('fill', (cell) => {
        if (cell.distance === 0) return '#456d68'
        if (pathKeys.has(key(cell.x, cell.z))) return '#d58a52'
        if (cell.distance <= waveFrame) return distanceColor(cell.distance)
        return '#e8e3d7'
      })
      .attr('stroke', (cell) => {
        if (cell.distance === waveFrame && waveFrame > 0 && frame <= FIELD.maximum) return '#9aae45'
        if (key(cell.x, cell.z) === key(FIELD.target.x, FIELD.target.z)
            && frame >= FIELD.maximum) return '#8c4a29'
        return cell.distance === 0 ? '#2f5f58' : '#bcb7aa'
      })

    cellGroups.select('text')
      .text((cell) => {
        if (cell.distance === 0) return '道路'
        if (cell.distance <= waveFrame) return `D=${cell.distance}`
        return ''
      })
      .attr('fill', (cell) => cell.distance === 0 ? '#fff' : '#28322c')

    const line = d3.line()
      .x((cell) => cell.x * CELL_SIZE + (CELL_SIZE - CELL_GAP) / 2)
      .y((cell) => cell.z * CELL_SIZE + (CELL_SIZE - CELL_GAP) / 2)
      .curve(d3.curveStepAfter)
    pathLayer.datum(visiblePath).transition(transition)
      .attr('d', visiblePath.length > 1 ? line : null)
    return () => {
      svg.interrupt()
      cellGroups.interrupt()
      cellGroups.selectAll('*').interrupt()
      pathLayer.interrupt()
    }
  }, [frame, reducedMotion])

  useEffect(() => {
    if (!playing) return undefined
    if (frame >= TOTAL_FRAMES) {
      setPlaying(false)
      return undefined
    }
    const timer = window.setTimeout(() => setFrame((value) => value + 1), interval)
    return () => window.clearTimeout(timer)
  }, [frame, interval, playing])

  const phaseText = frame === 0
    ? '初始状态：所有道路 Chunk 同时作为距离 0 的 BFS 起点。'
    : frame <= FIELD.maximum
      ? `距离波前 ${frame}：写入与道路相距 ${frame} 步的 Chunk。`
      : `接路阶段：已沿距离递减方向走过 ${Math.min(FIELD.path.length, frame - FIELD.maximum)}/${FIELD.path.length} 个 Chunk。`
  const pathCount = Math.max(0, frame - FIELD.maximum)
  const decisionIndex = Math.min(Math.max(0, pathCount - 1), FIELD.decisions.length - 1)
  const decision = frame >= FIELD.maximum && FIELD.decisions[decisionIndex]

  const togglePlay = () => {
    if (frame >= TOTAL_FRAMES) setFrame(0)
    setPlaying((value) => !value)
  }

  return (
    <section className="road-bfs-viz" aria-labelledby="road-bfs-viz-title">
      <header className="road-bfs-viz__header">
        <div>
          <p className="road-bfs-viz__eyebrow">REACT + D3 · MULTI-SOURCE BFS</p>
          <h4 id="road-bfs-viz-title">从既有道路扩展距离场，再把最远格接回道路</h4>
        </div>
        <span className="road-bfs-viz__counter">{frame} / {TOTAL_FRAMES}</span>
      </header>
      <div ref={host} className="road-bfs-viz__canvas" />
      <div className="road-bfs-viz__controls">
        <button type="button" onClick={togglePlay}>{playing ? '暂停' : '播放'}</button>
        <button
          type="button"
          disabled={frame >= TOTAL_FRAMES}
          onClick={() => { setPlaying(false); setFrame((value) => Math.min(TOTAL_FRAMES, value + 1)) }}
        >
          单步
        </button>
        <button
          type="button"
          disabled={frame === 0}
          onClick={() => { setPlaying(false); setFrame(0) }}
        >
          重置
        </button>
        <label>
          <span>步进间隔</span>
          <input
            type="range"
            min="250"
            max="1200"
            step="50"
            value={interval}
            aria-label="动画步进间隔"
            onChange={(event) => setIntervalMs(Number(event.target.value))}
          />
          <output>{interval} ms</output>
        </label>
      </div>
      <p className="road-bfs-viz__status" aria-live="polite">{phaseText}</p>
      <div className="road-bfs-viz__decision" aria-live="polite">
        <div className="road-bfs-viz__decision-head">
          <div>
            <span>候选支路逐条评分</span>
            <strong>
              {decision
                ? `当前 Chunk (${decision.current.x}, ${decision.current.z})，目标距离 D=${decision.current.distance - 1}`
                : '完成距离场后，从最远 Chunk 开始评估'}
            </strong>
          </div>
          <div className="road-bfs-viz__priority" aria-label="道路等级优先级">
            <span>道路重叠优先级</span>
            <b>PRIMARY 3</b><b>SECONDARY 2</b><b>SERVICE 1</b>
          </div>
        </div>
        <div className="road-bfs-viz__score-grid">
          {(decision?.evaluations || DIRECTIONS.map((direction) => ({ ...direction }))).map((candidate) => {
            const selected = decision?.selected === candidate
            const probability = candidate.eligible
              ? `${Math.round(100 / decision.eligibleCount)}%`
              : '0%'
            return (
              <div
                className={`road-bfs-viz__score${selected ? ' is-selected' : ''}${decision && !candidate.eligible ? ' is-rejected' : ''}`}
                key={candidate.name}
              >
                <div><b>{candidate.name}</b><span>{candidate.next ? `(${candidate.next.x}, ${candidate.next.z})` : '越界'}</span></div>
                <dl>
                  <div><dt>下一距离</dt><dd>{candidate.next ? `D=${candidate.next.distance}` : '—'}</dd></div>
                  <div><dt>梯度门槛</dt><dd>{decision ? (candidate.eligible ? '通过' : '淘汰') : '待计算'}</dd></div>
                  <div><dt>抽取权重</dt><dd>{decision ? candidate.weight : '—'}</dd></div>
                  <div><dt>抽取概率</dt><dd>{decision ? probability : '—'}</dd></div>
                </dl>
                <span className="road-bfs-viz__score-state">
                  {selected ? '本步选中' : candidate.eligible ? '等权候选' : decision ? '不参与抽取' : '等待 BFS'}
                </span>
              </div>
            )
          })}
        </div>
      </div>
      <p className="road-bfs-viz__note">
        动画把源码中的硬筛选等价记作权重 1/0；Java 代码并不保存这个权重字段。只有距离恰好减少 1 的方向进入等权抽取。橙色路线使用固定演示游标，
        实际生成器由该层稳定随机源决定并列方向。道路等级 3/2/1 只在道路重叠时保留较高等级，不计入这里的抽取概率。
      </p>
    </section>
  )
}
