import { useState } from 'react'
import { MediaLightbox } from './MediaLightbox'

export default function ZoomableImage({ src, alt = '', ...props }) {
  const [open, setOpen] = useState(false)
  const label = alt ? `放大查看：${alt}` : '放大查看图片'

  return (
    <>
      <button type="button" className="zoomable-image" onClick={() => setOpen(true)} aria-label={label}>
        <img src={src} alt={alt} {...props} />
        <span aria-hidden="true">点击放大</span>
      </button>
      <MediaLightbox open={open} onClose={() => setOpen(false)} label={label}>
        <img src={src} alt={alt} />
      </MediaLightbox>
    </>
  )
}
