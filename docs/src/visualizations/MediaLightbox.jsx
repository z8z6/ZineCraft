import { useEffect } from 'react'

export function MediaLightbox({ open, onClose, label, children }) {
  useEffect(() => {
    if (!open) return undefined
    const previousOverflow = document.body.style.overflow
    const handleKeyDown = (event) => {
      if (event.key === 'Escape') onClose()
    }
    document.body.style.overflow = 'hidden'
    window.addEventListener('keydown', handleKeyDown)
    return () => {
      document.body.style.overflow = previousOverflow
      window.removeEventListener('keydown', handleKeyDown)
    }
  }, [onClose, open])

  if (!open) return null
  return (
    <div
      className="media-lightbox"
      role="dialog"
      aria-modal="true"
      aria-label={label}
      onMouseDown={(event) => { if (event.target === event.currentTarget) onClose() }}
    >
      <button type="button" className="media-lightbox__close" onClick={onClose} autoFocus>关闭 ×</button>
      <div className="media-lightbox__content">
        {children}
      </div>
    </div>
  )
}
