import mermaid from 'mermaid'
import { useEffect, useId, useRef, useState } from 'react'
import { MediaLightbox } from './MediaLightbox'

let initialized = false
let renderSequence = 0

function initializeMermaid() {
  if (initialized) return
  mermaid.initialize({
    startOnLoad: false,
    securityLevel: 'strict',
    theme: 'base',
    fontFamily: '"JetBrains Mono", monospace',
    flowchart: { curve: 'basis', htmlLabels: false },
    themeVariables: {
      fontFamily: '"JetBrains Mono", monospace',
      primaryColor: '#dce6bd',
      primaryTextColor: '#252b27',
      primaryBorderColor: '#456d68',
      lineColor: '#59615a',
      secondaryColor: '#e3ded3',
      tertiaryColor: '#f0d2b7',
      clusterBkg: '#f1ede3',
      clusterBorder: '#aaa69b',
    },
  })
  initialized = true
}

export default function MermaidEmbed({ chart }) {
  const host = useRef(null)
  const rawId = useId()
  const [error, setError] = useState('')
  const [open, setOpen] = useState(false)
  const [svgMarkup, setSvgMarkup] = useState('')

  useEffect(() => {
    let active = true
    initializeMermaid()
    const render = async () => {
      try {
        const id = `mermaid-${rawId.replace(/[^a-zA-Z0-9_-]/g, '')}-${++renderSequence}`
        const result = await mermaid.render(id, chart)
        if (!active || !host.current) return
        host.current.innerHTML = result.svg
        result.bindFunctions?.(host.current)
        setSvgMarkup(result.svg)
        setError('')
      } catch (renderError) {
        if (active) setError(renderError instanceof Error ? renderError.message : String(renderError))
      }
    }
    render()
    return () => {
      active = false
      if (host.current) host.current.innerHTML = ''
    }
  }, [chart, rawId])

  if (error) {
    return <pre className="mermaid-error" role="alert">流程图渲染失败：{error}{'\n\n'}{chart}</pre>
  }
  return (
    <>
      <button type="button" className="mermaid-preview" disabled={!svgMarkup} onClick={() => setOpen(true)} aria-label="放大查看流程图">
        <figure className="mermaid-frame" ref={host} aria-label="流程图" />
        <span aria-hidden="true">点击放大</span>
      </button>
      <MediaLightbox open={open} onClose={() => setOpen(false)} label="放大查看流程图">
        <div className="mermaid-lightbox" dangerouslySetInnerHTML={{ __html: svgMarkup }} />
      </MediaLightbox>
    </>
  )
}
