import * as d3 from 'd3'
import { useEffect, useRef, useState } from 'react'

const STAGES = ['读取 Parcel', '由临路面确定旋转', '精确匹配 footprint', '建筑权重抽取', '校验真实入口', '无尺寸匹配失败', '无真实入口失败']
const LOGIC = [
  { input: 'Parcel 尺寸与 roadConnections', operation: '选择最高优先级临路面作为 roadFacing', guard: 'Parcel 必须至少有一个真实临路面', output: '2×1 Parcel，主临路面 WEST' },
  { input: '模板默认正面 SOUTH 与 roadFacing=WEST', operation: 'rotationForFacing(WEST)', guard: '旋转只允许 Minecraft 四种水平 Rotation', output: 'CLOCKWISE_90' },
  { input: '旋转后的 building footprint 与 Parcel area', operation: 'rotatedWidth==parcelWidth && rotatedLength==parcelLength', guard: '必须精确相等，能放入但未铺满也不算匹配', output: '只保留旋转后 2×1 的候选' },
  { input: '兼容候选及正整数 weight', operation: 'cursor=random.nextInt(totalWeight)，逐项减去 weight', guard: 'cursor 落入候选权重区间时立即选中', output: '按 2/(2)=100% 选中炎中型商铺' },
  { input: '模板 connectionFaces 与最终 rotation', operation: 'supportedFaces=rotate(connectionFaces)', guard: 'supportedFaces ∩ parcelRoadFaces 不能为空', output: '记录建筑与道路的真实入口连接' },
  { input: '一个 3×2 Parcel 与当前建筑池', operation: '逐个旋转 footprint 后执行精确匹配', guard: 'compatible.isEmpty()', output: '抛出“没有与 Parcel 尺寸匹配的建筑”' },
  { input: '尺寸匹配建筑与旋转后的 connectionFaces', operation: '过滤不在 supportedFaces 中的 roadConnections', guard: '过滤结果 roadConnections.isEmpty()', output: '抛出“建筑模板没有朝向道路的真实入口”' },
]
const CANDIDATES = [
  { name: '炎商铺', id: 'yan_shop', x: 1, z: 1, weight: 1, unique: false },
  { name: '炎中型商铺', id: 'yan_medium_shop', x: 1, z: 2, weight: 2, unique: false },
  { name: '炎国玉门烽台', id: 'yan_yumen_beacon', x: 2, z: 2, weight: 1, unique: true },
].map((candidate) => ({
  ...candidate,
  rotatedX: candidate.z,
  rotatedZ: candidate.x,
  compatible: candidate.z === 2 && candidate.x === 1,
}))

export default function BuildingSelectionEmbed() {
  const host = useRef(null)
  const [stage, setStage] = useState(0)
  const [playing, setPlaying] = useState(false)
  const reducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches

  useEffect(() => {
    const noExactFit = stage === 5
    const noEntrance = stage === 6
    const svg = d3.select(host.current).selectAll('svg').data([null]).join('svg')
      .attr('viewBox', '0 0 680 330').attr('role', 'img')
      .attr('aria-label', `建筑选择与朝向：${STAGES[stage]}`)
    svg.interrupt()
    svg.selectAll('*').interrupt()
    svg.selectAll('*').remove()
    const frame = svg.append('g').attr('class', 'algorithm-viz__frame').attr('data-stage', stage)
    frame.append('title').text(`建筑选择与朝向：${STAGES[stage]}`)
    const duration = reducedMotion ? 0 : 320

    const parcel = frame.append('g').attr('transform', 'translate(54,72)')
    parcel.append('rect').attr('width', 220).attr('height', 110).attr('fill', '#dce6bd')
      .attr('stroke', '#465b50').attr('stroke-width', 3)
    parcel.append('line').attr('x1', 110).attr('x2', 110).attr('y2', 110)
      .attr('stroke', '#94a08f').attr('stroke-dasharray', '4 4')
    parcel.append('rect').attr('x', -22).attr('y', -8).attr('width', 22).attr('height', 126)
      .attr('fill', '#456d68')
    parcel.append('text').attr('x', -11).attr('y', 58).attr('transform', 'rotate(-90,-11,58)')
      .attr('text-anchor', 'middle').attr('fill', '#fff').attr('font-size', 11).text('PRIMARY ROAD')
    parcel.append('text').attr('x', 110).attr('y', 52).attr('text-anchor', 'middle')
      .attr('font-weight', 800).attr('font-size', 18).text(noExactFit ? 'Parcel 3×2' : 'Parcel 2×1')
    parcel.append('text').attr('x', 110).attr('y', 75).attr('text-anchor', 'middle')
      .attr('font-size', 11).text('主临路面：WEST（西）')

    if (stage >= 1) {
      parcel.append('path').attr('d', 'M 150 94 A 44 44 0 0 0 194 50')
        .attr('fill', 'none').attr('stroke', '#b85c2e').attr('stroke-width', 4)
        .attr('marker-end', 'url(#building-arrow)')
      frame.append('defs').append('marker').attr('id', 'building-arrow').attr('viewBox', '0 0 10 10')
        .attr('refX', 8).attr('refY', 5).attr('markerWidth', 6).attr('markerHeight', 6)
        .attr('orient', 'auto-start-reverse').append('path').attr('d', 'M 0 0 L 10 5 L 0 10 z').attr('fill', '#b85c2e')
      parcel.append('text').attr('x', 110).attr('y', 145).attr('text-anchor', 'middle')
        .attr('fill', '#8c4a29').attr('font-weight', 800).attr('font-size', 12)
        .text('SOUTH → WEST = CLOCKWISE_90')
    }

    const list = frame.append('g').attr('transform', 'translate(330,42)')
    const scale = d3.scaleLinear().domain([0, 2]).range([0, 110])
    const rows = list.selectAll('g').data(CANDIDATES).join('g')
      .attr('transform', (_, index) => `translate(0,${index * 78})`)
    rows.append('rect').attr('width', 300).attr('height', 65).attr('rx', 3)
      .attr('fill', (candidate) => noExactFit ? '#f0d2b7' : stage >= 2 && candidate.compatible ? '#e3e7d4' : '#e3ded3')
      .attr('stroke', (candidate) => noExactFit ? '#a75128' : stage >= 2 && candidate.compatible ? '#779044' : '#aaa69b')
      .attr('stroke-width', (candidate) => noExactFit || (stage >= 2 && candidate.compatible) ? 3 : 1)
      .style('opacity', 0).transition().duration(duration).style('opacity', 1)
    rows.append('text').attr('x', 12).attr('y', 20).attr('font-size', 12).attr('font-weight', 800)
      .text((candidate) => candidate.name)
    rows.append('text').attr('x', 12).attr('y', 38).attr('font-size', 10).attr('fill', '#676d66')
      .text((candidate) => stage < 1
        ? `默认 ${candidate.x}×${candidate.z} · 权重 ${candidate.weight}${candidate.unique ? ' · unique' : ''}`
        : `旋转后 ${candidate.rotatedX}×${candidate.rotatedZ} · ${candidate.compatible ? '精确匹配' : '尺寸不符'}`)
    rows.append('rect').attr('x', 168).attr('y', 18).attr('height', 12)
      .attr('width', (candidate) => stage === 3 && candidate.compatible ? scale(candidate.weight) : 0)
      .attr('fill', '#b85c2e').transition().duration(duration)
    rows.append('text').attr('x', 286).attr('y', 49).attr('text-anchor', 'end')
      .attr('font-size', 10).attr('font-weight', 700)
      .text((candidate) => noExactFit ? '3×2 不匹配'
        : stage < 2 ? '待筛选'
          : candidate.compatible ? stage === 3 ? `权重区间 [0, ${candidate.weight})` : stage === 4 ? '本轮选中' : '保留' : '淘汰')

    if (stage === 3) {
      list.append('line').attr('x1', 223).attr('x2', 223).attr('y1', 0).attr('y2', 65)
        .attr('stroke', '#20231f').attr('stroke-width', 2)
      list.append('text').attr('x', 228).attr('y', 61).attr('font-size', 9).text('cursor = 1')
    }
    if (stage === 4) {
      parcel.append('path').attr('d', 'M 0 35 L -15 45 L 0 55 Z').attr('fill', '#b85c2e')
      parcel.append('text').attr('x', 110).attr('y', 205).attr('text-anchor', 'middle')
        .attr('fill', '#456d68').attr('font-size', 12).attr('font-weight', 800)
        .text('入口面旋转后包含 WEST ✓')
      list.append('text').attr('x', 150).attr('y', 257).attr('text-anchor', 'middle')
        .attr('fill', '#8c4a29').attr('font-size', 12).attr('font-weight', 800)
        .text('选中：炎中型商铺')
    }
    if (noExactFit) {
      frame.append('text').attr('x', 340).attr('y', 310).attr('text-anchor', 'middle')
        .attr('fill', '#a75128').attr('font-size', 12).attr('font-weight', 800)
        .text('compatible = ∅ → 立即失败，不用相近尺寸凑合')
    }
    if (noEntrance) {
      parcel.append('path').attr('d', 'M 220 35 L 235 45 L 220 55 Z').attr('fill', '#a75128')
      parcel.append('text').attr('x', 110).attr('y', 205).attr('text-anchor', 'middle')
        .attr('fill', '#a75128').attr('font-size', 12).attr('font-weight', 800)
        .text('模板入口为 EAST，与 WEST 道路无交集 ✕')
      list.append('text').attr('x', 150).attr('y', 257).attr('text-anchor', 'middle')
        .attr('fill', '#a75128').attr('font-size', 11).attr('font-weight', 800)
        .text('roadConnections = ∅ → 停止生成')
    }
    return () => {
      frame.interrupt()
      frame.selectAll('*').interrupt()
      frame.remove()
    }
  }, [stage, reducedMotion])

  useEffect(() => {
    if (!playing) return undefined
    const timer = window.setTimeout(() => {
      if (stage === STAGES.length - 1) setPlaying(false)
      else setStage((value) => value + 1)
    }, 950)
    return () => window.clearTimeout(timer)
  }, [playing, stage])

  return (
    <section className="algorithm-viz" aria-labelledby="building-selection-title">
      <header className="algorithm-viz__header">
        <div><p>D3 · BUILDING SELECTION</p><h4 id="building-selection-title">Parcel 怎样决定建筑、朝向与入口</h4></div>
        <span>{stage + 1} / {STAGES.length}</span>
      </header>
      <ol className="algorithm-viz__steps">
        {STAGES.map((label, index) => <li className={index === stage ? 'active' : index < stage ? 'done' : ''} key={label}>{label}</li>)}
      </ol>
      <div ref={host} className="algorithm-viz__canvas" />
      <div className="algorithm-viz__logic" aria-live="polite">
        <div><span>输入</span><b>{LOGIC[stage].input}</b></div>
        <div><span>本步计算</span><code>{LOGIC[stage].operation}</code></div>
        <div><span>判定条件</span><b>{LOGIC[stage].guard}</b></div>
        <div><span>本步输出</span><b>{LOGIC[stage].output}</b></div>
      </div>
      <div className="algorithm-viz__controls">
        <button type="button" onClick={() => { if (stage === STAGES.length - 1) setStage(0); setPlaying((value) => !value) }}>{playing ? '暂停' : '播放'}</button>
        <button type="button" disabled={stage === STAGES.length - 1} onClick={() => { setPlaying(false); setStage((value) => Math.min(STAGES.length - 1, value + 1)) }}>单步</button>
        <button type="button" disabled={stage === 0} onClick={() => { setPlaying(false); setStage(0) }}>重置</button>
        <strong aria-live="polite">{STAGES[stage]}</strong>
      </div>
      <p className="algorithm-viz__note">示例使用玉门核心区真实建筑池：炎商铺 1×1/权重 1、炎中型商铺 1×2/权重 2、炎国玉门烽台 2×2/权重 1；模板默认正面为 SOUTH。</p>
    </section>
  )
}
