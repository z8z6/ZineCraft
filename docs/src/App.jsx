import { useEffect, useMemo, useRef, useState } from 'react'
import ReactMarkdown from 'react-markdown'
import rehypeKatex from 'rehype-katex'
import remarkGfm from 'remark-gfm'
import remarkMath from 'remark-math'
import '@fontsource/jetbrains-mono/400.css'
import '@fontsource/jetbrains-mono/600.css'
import '@fontsource/jetbrains-mono/700.css'
import 'katex/dist/katex.min.css'
import HighlightedCodeBlock from './components/HighlightedCodeBlock'
import catalog from './data/catalog.json'
import BuildingSelectionEmbed from './visualizations/BuildingSelectionEmbed'
import {
  CityBoundaryEmbed,
  LayerRoadEmbed,
  NationBoundaryEmbed,
  ParcelPartitionEmbed,
  RegionGrowthEmbed,
  RoadCleanupEmbed,
  RoadTileEmbed,
  RuntimePlacementEmbed,
} from './visualizations/CityAlgorithmsEmbed'
import MermaidEmbed from './visualizations/MermaidEmbed'
import RoadBfsEmbed from './visualizations/RoadBfsEmbed'
import ZoomableImage from './visualizations/ZoomableImage'

const guideModules = import.meta.glob('../guides/**/*.md', {
  eager: true,
  query: '?raw',
  import: 'default',
})

const itemTextureModules = import.meta.glob('../../src/main/resources/assets/zinecraft/textures/item/*.png', { eager: true, query: '?url', import: 'default' })
const blockTextureModules = import.meta.glob('../../src/main/resources/assets/zinecraft/textures/block/*.png', { eager: true, query: '?url', import: 'default' })
const entityTextureModules = import.meta.glob('../../src/main/resources/assets/zinecraft/textures/entity/*.png', { eager: true, query: '?url', import: 'default' })
const textureIndex = (modules) => Object.fromEntries(Object.entries(modules).map(([path, url]) => [path.split('/').at(-1).replace(/\.png$/, ''), url]))
const catalogTextures = {
  item: textureIndex(itemTextureModules),
  block: textureIndex(blockTextureModules),
  entity: textureIndex(entityTextureModules),
}
const genericCatalogTextures = {
  items: './vanilla/item.png',
  skills: './vanilla/item.png',
  weapons: './vanilla/weapon.png',
  biomes: './vanilla/biome.png',
  structures: './vanilla/structure.png',
  features: './vanilla/feature.png',
  dimensions: './vanilla/dimension.png',
  effects: './vanilla/effect.png',
  nations: './vanilla/nation.png',
  cities: './vanilla/city.png',
  regions: './vanilla/region.png',
  sounds: './vanilla/sound.png',
}

const categoryMeta = {
  overview: { label: '入门与工作流', code: 'GETTING STARTED', index: '01' },
  item: { label: '物品开发', code: 'ITEMS', index: '02' },
  block: { label: '方块开发', code: 'BLOCKS', index: '03' },
  entity: { label: '生物开发', code: 'ENTITIES', index: '04' },
  world: { label: '世界生成', code: 'WORLDGEN', index: '05' },
  combat: { label: '战斗系统', code: 'COMBAT', index: '06' },
  skill: { label: '技能与武器', code: 'SKILLS', index: '07' },
  collectible: { label: '藏品系统', code: 'COLLECTIBLES', index: '08' },
  modeling: { label: '模型制作', code: 'MODELING', index: '09' },
  interface: { label: '界面呈现', code: 'INTERFACE', index: '10' },
}

const catalogCategoryMeta = {
  items: { label: '普通物品', code: 'ITEM', glyph: '◆' },
  collectibles: { label: '集成战略藏品', code: 'RELIC', glyph: '✦' },
  skills: { label: '技能资料', code: 'SKILL', glyph: '⚔' },
  weapons: { label: '武器', code: 'WEAPON', glyph: '†' },
  blocks: { label: '方块', code: 'BLOCK', glyph: '■' },
  entities: { label: '生物', code: 'ENTITY', glyph: '♟' },
  biomes: { label: '群系', code: 'BIOME', glyph: '⌁' },
  structures: { label: '结构', code: 'STRUCTURE', glyph: '⌂' },
  features: { label: '世界特征', code: 'FEATURE', glyph: '⌘' },
  dimensions: { label: '维度', code: 'DIMENSION', glyph: '◎' },
  effects: { label: '战斗状态', code: 'EFFECT', glyph: '✹' },
  nations: { label: '国家', code: 'NATION', glyph: '◇' },
  cities: { label: '城市', code: 'CITY', glyph: '▦' },
  regions: { label: '城市区域', code: 'REGION', glyph: '▧' },
  sounds: { label: '声音与唱片', code: 'SOUND', glyph: '♫' },
}

const showcaseItems = [
  ['orirock', '固源岩'], ['originite', '源石'], ['oriron', '异铁'], ['aketone', '酮凝集'],
  ['bipolar_nanosheet', '双极纳米片'], ['arts_killer', '法术杀手'], ['chip_guard', '近卫芯片'],
  ['chip_caster', '术师芯片'], ['hot_water_kettle', '热水壶'], ['caerula_arbor', '深蓝之树'],
  ['antique_coins', '古旧钱币'], ['arch_glyph', '拱形符文'],
]

const residentFaces = [
  ['yan', '炎国居民'], ['victoria', '维多利亚居民'], ['laterano', '拉特兰居民'],
  ['ursus', '乌萨斯居民'], ['sami', '萨米居民'], ['kazdel', '卡兹戴尔居民'],
]

const contributors = [
  {
    login: 'z8z6',
    avatar: './contributors/z8z6.png',
    profile: 'https://github.com/z8z6',
    contributions: 65,
  },
  {
    login: 'YeXingchenawa',
    avatar: './contributors/YeXingchenawa.png',
    profile: 'https://github.com/YeXingchenawa',
    contributions: 4,
  },
]

function normalizeDocument([path, content]) {
  const guide = true
  const relative = path.replace('../guides/', '')
  const slug = relative.replace(/\.md$/i, '')
  const title = content.match(/^#\s+(.+)$/m)?.[1]?.trim() || slug.split('/').at(-1)
  const section = relative.split('/')[0]
  let category = categoryMeta[section] ? section : 'world'
  return { path: relative, slug: `guide/${slug}`, title, category, content, guide }
}

const documents = Object.entries(guideModules)
  .map(normalizeDocument)
  .sort((a, b) => Number(b.guide) - Number(a.guide) || a.title.localeCompare(b.title, 'zh-CN'))

function useHashRoute() {
  const read = () => {
    const value = window.location.hash.replace(/^#\/?/, '')
    try { return decodeURIComponent(value) } catch { return value }
  }
  const [route, setRoute] = useState(read)
  useEffect(() => {
    const update = () => setRoute(read())
    window.addEventListener('hashchange', update)
    return () => window.removeEventListener('hashchange', update)
  }, [])
  return route
}

const documentHref = (slug) => `#/doc/${slug.split('/').map(encodeURIComponent).join('/')}`

function Icon({ name }) {
  const icons = { arrow: '↗', search: '⌕', menu: '≡', close: '×', cube: '◆', chevron: '›' }
  return <span aria-hidden="true">{icons[name]}</span>
}

function Header({ onSearch }) {
  const [open, setOpen] = useState(false)
  return (
    <header className="topbar">
      <a className="brand" href="#/" aria-label="Zinecraft 文档首页">
        <img src="./icon.png" alt="" />
        <span><strong>ZINECRAFT</strong><small>DEVELOPER ARCHIVE</small></span>
      </a>
      <nav className={open ? 'nav nav--open' : 'nav'} aria-label="主导航">
        <a href="#/" onClick={() => setOpen(false)}>首页</a>
        <a href="#/catalog" onClick={() => setOpen(false)}>图鉴</a>
        <a href="#/docs" onClick={() => setOpen(false)}>开发文档</a>
        <a href="https://github.com/z8z6/ZineCraft" target="_blank" rel="noreferrer">源码 <Icon name="arrow" /></a>
      </nav>
      <div className="topbar__tools">
        <button className="search-trigger" onClick={onSearch}><Icon name="search" /> <span>搜索文档</span><kbd>Ctrl K</kbd></button>
        <button className="menu-trigger" onClick={() => setOpen(!open)} aria-label="切换导航"><Icon name={open ? 'close' : 'menu'} /></button>
      </div>
    </header>
  )
}

function PixelButton({ href, children, secondary = false }) {
  return <a className={secondary ? 'pixel-button pixel-button--secondary' : 'pixel-button'} href={href}>{children}<Icon name="chevron" /></a>
}

function Home() {
  const homeRef = useRef(null)
  const pageScrollerRef = useRef(null)
  const [activePage, setActivePage] = useState(0)
  const groups = Object.entries(categoryMeta).map(([id, meta]) => ({
    id, ...meta, docs: documents.filter((doc) => doc.category === id),
  })).filter((group) => group.docs.length)
  const pageLabels = ['首页', '项目概览', '游戏资源', '开发资料', '图鉴与作者', '设计原则']
  const pageClass = (index, className) => `${className} home-page${activePage === index ? ' is-active' : ''}`
  const scrollToPage = (index) => {
    if (pageScrollerRef.current) {
      pageScrollerRef.current(index)
      return
    }
    const page = homeRef.current?.querySelectorAll('[data-scroll-page]')[index]
    if (!page) return
    setActivePage(index)
    page.scrollIntoView({
      behavior: window.matchMedia('(prefers-reduced-motion: reduce)').matches ? 'auto' : 'smooth',
      block: 'start',
    })
  }

  useEffect(() => {
    const home = homeRef.current
    if (!home) return undefined
    const pages = [...home.querySelectorAll('[data-scroll-page]')]
    const desktop = window.matchMedia('(min-width: 1051px)')
    const reducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)')
    let locked = false
    let unlockTimer
    let scrollFrame
    let observerFrame

    const nearestPage = () => {
      const headerHeight = document.querySelector('.topbar')?.offsetHeight || 0
      return pages.reduce((nearest, page, index) => (
        Math.abs(page.getBoundingClientRect().top - headerHeight)
          < Math.abs(pages[nearest].getBoundingClientRect().top - headerHeight) ? index : nearest
      ), 0)
    }
    const updateActivePage = () => {
      if (locked || observerFrame) return
      observerFrame = window.requestAnimationFrame(() => {
        observerFrame = undefined
        setActivePage(nearestPage())
      })
    }
    const goToPage = (requestedIndex) => {
      const index = Math.max(0, Math.min(pages.length - 1, requestedIndex))
      const page = pages[index]
      if (!page) return
      window.clearTimeout(unlockTimer)
      if (scrollFrame) window.cancelAnimationFrame(scrollFrame)
      setActivePage(index)
      const headerHeight = document.querySelector('.topbar')?.offsetHeight || 0
      const startY = window.scrollY
      const targetY = Math.max(0, page.getBoundingClientRect().top + startY - headerHeight)
      const distance = targetY - startY
      if (reducedMotion.matches || Math.abs(distance) < 2) {
        window.scrollTo(0, targetY)
        locked = true
        unlockTimer = window.setTimeout(() => { locked = false }, 40)
        return
      }
      locked = true
      const immediateDistance = Math.sign(distance) * Math.min(56, Math.abs(distance) * .08)
      const animatedStartY = startY + immediateDistance
      const animatedDistance = targetY - animatedStartY
      window.scrollTo(0, animatedStartY)
      const startedAt = performance.now()
      const duration = Math.min(380, Math.max(260, Math.abs(animatedDistance) * .2))
      const animate = (now) => {
        const progress = Math.min(1, (now - startedAt) / duration)
        const eased = 1 - (1 - progress) ** 3
        window.scrollTo(0, animatedStartY + animatedDistance * eased)
        if (progress < 1) {
          scrollFrame = window.requestAnimationFrame(animate)
          return
        }
        scrollFrame = undefined
        window.scrollTo(0, targetY)
        unlockTimer = window.setTimeout(() => { locked = false }, 35)
      }
      scrollFrame = window.requestAnimationFrame(animate)
    }
    pageScrollerRef.current = goToPage
    const handleWheel = (event) => {
      if (!desktop.matches || event.deltaY === 0 || event.ctrlKey) return
      if (locked) {
        event.preventDefault()
        return
      }
      const current = nearestPage()
      const next = Math.max(0, Math.min(pages.length - 1, current + Math.sign(event.deltaY)))
      if (next === current) return
      event.preventDefault()
      goToPage(next)
    }

    home.addEventListener('wheel', handleWheel, { passive: false })
    window.addEventListener('scroll', updateActivePage, { passive: true })
    updateActivePage()
    return () => {
      pageScrollerRef.current = null
      home.removeEventListener('wheel', handleWheel)
      window.removeEventListener('scroll', updateActivePage)
      window.clearTimeout(unlockTimer)
      if (scrollFrame) window.cancelAnimationFrame(scrollFrame)
      if (observerFrame) window.cancelAnimationFrame(observerFrame)
    }
  }, [])

  return (
    <main className="home-scroll" ref={homeRef}>
      <nav className="home-page-nav" aria-label="首页章节">
        {pageLabels.map((label, index) => (
          <button type="button" className={activePage === index ? 'active' : ''} aria-label={`前往${label}`} aria-current={activePage === index ? 'step' : undefined} onClick={() => scrollToPage(index)} key={label}>
            <span>{String(index + 1).padStart(2, '0')}</span>
          </button>
        ))}
      </nav>
      <section className={pageClass(0, 'hero')} data-scroll-page>
        <div className="hero__noise" />
        <div className="hero__content">
          <div className="hero__copy">
            <p className="eyebrow"><span /> MINECRAFT 1.21.1 · NEOFORGE</p>
            <div className="mc-wordmark"><span>ZINECRAFT</span><small>MINECRAFT × TERRA DEVELOPMENT</small></div>
            <h1>把泰拉<br /><em>构筑进方块世界</em></h1>
            <p className="hero__lead">Zinecraft 是面向 Minecraft 1.21.1 的《明日方舟》主题内容模组。这里收录世界生成、战斗运行时、集成战略藏品与模型制作的开发资料。</p>
            <div className="hero__actions">
              <PixelButton href="#/catalog">打开模组图鉴</PixelButton>
              <PixelButton href="#/docs" secondary>学习内容开发</PixelButton>
            </div>
          </div>
          <div className="build-board">
            <div className="build-board__head"><span>BUILD PROFILE</span><b>稳定开发基线</b></div>
            <dl>
              <div><dt>Minecraft</dt><dd>1.21.1</dd></div>
              <div><dt>NeoForge</dt><dd>21.1.244</dd></div>
              <div><dt>Java Runtime</dt><dd>21 LTS</dd></div>
              <div><dt>Mod ID</dt><dd>zinecraft</dd></div>
            </dl>
            <div className="build-board__status"><i /> DOCUMENTATION ONLINE <span>v1.0</span></div>
          </div>
        </div>
        <div className="artifact-strip" aria-label="藏品素材预览">
          {['hot_water_kettle', 'arch_glyph', 'caerula_arbor', 'antique_coins', 'black_tulip'].map((name, index) => (
            <div className="artifact" key={name}><span>0{index + 1}</span><img src={`./artifacts/${name}.png`} alt="" /></div>
          ))}
          <div className="artifact-strip__label"><b>742</b><span>COLLECTIBLES<br />REGISTERED</span></div>
        </div>
      </section>
      <div className="terrain-divider" aria-hidden="true" />

      <section className={pageClass(1, 'section section--intro')} id="quick-start" data-scroll-page>
        <div className="section-heading">
          <div><p className="eyebrow eyebrow--dark">PROJECT OVERVIEW</p><h2>从一份能运行的工程开始</h2></div>
          <p>文档与源码保持在同一仓库中，覆盖数据生成、服务端战斗结算、Terra 世界布局和内容资产生产流程。</p>
        </div>
        <div className="command-grid">
          <article><span>01 / DATA</span><h3>生成项目数据</h3><code>.\gradlew.bat runData</code><p>生成语言、模型、配方、战利品表和动态注册表。</p></article>
          <article><span>02 / CLIENT</span><h3>启动开发客户端</h3><code>.\gradlew.bat runClient</code><p>在 NeoForge 开发环境中验证内容、渲染与交互。</p></article>
          <article><span>03 / BUILD</span><h3>构建发布文件</h3><code>.\gradlew.bat build</code><p>编译 Java 21 源码，并将模组输出到 build/libs。</p></article>
        </div>
      </section>

      <section className={pageClass(2, 'section mc-library')} data-scroll-page>
        <div className="section-heading section-heading--line">
          <div><p className="eyebrow eyebrow--dark">IN-GAME ARCHIVE</p><h2>从游戏资源认识项目</h2></div>
          <p>直接使用模组内注册的物品纹理与国家居民皮肤，像浏览 Minecraft 物品栏一样进入 Zinecraft 的内容体系。</p>
        </div>
        <div className="mc-showcase-grid">
          <article className="inventory-panel">
            <header><span>物品与材料</span><small>12 / 742 ITEMS</small></header>
            <div className="inventory-grid">
              {showcaseItems.map(([name, label]) => <div className="inventory-slot" key={name} title={label}><img src={`./items/${name}.png`} alt={label} /><span>{label}</span></div>)}
            </div>
            <div className="panel-hint">将鼠标悬停在物品格上查看名称</div>
          </article>
          <article className="resident-panel">
            <header><span>泰拉居民</span><small>ENTITY PROFILES</small></header>
            <div className="resident-grid">
              {residentFaces.map(([name, label]) => <div className="resident-card" key={name}><div className="skin-face" style={{ backgroundImage: `url(./entities/${name}.png)` }} /><span>{label}</span><small>ONLINE</small></div>)}
            </div>
          </article>
        </div>
      </section>

      <section className={pageClass(3, 'section section--docs')} data-scroll-page>
        <div className="section-heading section-heading--line">
          <div><p className="eyebrow eyebrow--dark">KNOWLEDGE BASE</p><h2>开发资料分区</h2></div>
          <PixelButton href="#/docs" secondary>浏览全部 {documents.length} 篇</PixelButton>
        </div>
        <div className="category-grid">
          {groups.map((group) => (
            <a className="category-card" href={`#/docs?category=${group.id}`} key={group.id}>
              <span className="category-card__index">{group.index}</span>
              <div><small>{group.code}</small><h3>{group.label}</h3><p>{group.docs.slice(0, 2).map((doc) => doc.title).join(' · ')}</p></div>
              <b>{String(group.docs.length).padStart(2, '0')}<small>DOCS</small></b>
              <Icon name="arrow" />
            </a>
          ))}
        </div>
      </section>

      <div className={pageClass(4, 'home-page-cluster')} data-scroll-page>
        <section className="catalog-callout">
          <div><p className="eyebrow"><span /> COMPLETE MOD INDEX</p><h2>{catalog.entries.length.toLocaleString('zh-CN')} 项新增内容，<br />都能从图鉴找到。</h2></div>
          <p>覆盖 742 件藏品、方块、生物、群系、结构、技能、城市与世界生成数据；可按中文名或注册 ID 搜索。</p>
          <PixelButton href="#/catalog">进入完整图鉴</PixelButton>
        </section>

        <section className="section contributor-section" aria-labelledby="contributors-title">
          <div className="section-heading section-heading--line">
            <div><p className="eyebrow eyebrow--dark">PROJECT AUTHORS</p><h2 id="contributors-title">作者与贡献者</h2></div>
            <p>以下名单和头像来自 ZineCraft GitHub 仓库的贡献者记录，点击卡片可前往对应的 GitHub 主页。</p>
          </div>
          <div className="contributor-grid">
            {contributors.map((contributor, index) => (
              <a className="contributor-card" href={contributor.profile} target="_blank" rel="noreferrer" key={contributor.login}>
                <span className="contributor-card__index">{String(index + 1).padStart(2, '0')}</span>
                <img src={contributor.avatar} alt={`${contributor.login} 的 GitHub 头像`} />
                <span className="contributor-card__identity"><small>GITHUB CONTRIBUTOR</small><strong>{contributor.login}</strong></span>
                <span className="contributor-card__commits"><strong>{contributor.contributions}</strong><small>CONTRIBUTIONS</small></span>
                <Icon name="arrow" />
              </a>
            ))}
          </div>
        </section>
      </div>

      <section className={pageClass(5, 'manifesto')} data-scroll-page>
        <p>DESIGNED FOR BUILDERS</p>
        <h2>规则由服务端裁定，<br />世界由数据生成。</h2>
        <div><span>SERVER AUTHORITATIVE</span><span>DATA DRIVEN</span><span>REPRODUCIBLE</span></div>
      </section>
    </main>
  )
}

function catalogTexture(entry) {
  if (['items', 'collectibles', 'skills', 'weapons'].includes(entry.type)) {
    return catalogTextures.item[entry.id] || genericCatalogTextures[entry.type] || genericCatalogTextures.items
  }
  if (entry.type === 'blocks') return catalogTextures.block[entry.id] || './vanilla/structure.png'
  if (entry.type === 'entities') return catalogTextures.entity[entry.id] || genericCatalogTextures.items
  return genericCatalogTextures[entry.type] || genericCatalogTextures.items
}

function CatalogPage({ initialCategory }) {
  const [query, setQuery] = useState('')
  const routeCategory = initialCategory && catalogCategoryMeta[initialCategory] ? initialCategory : 'all'
  const [category, setCategory] = useState(routeCategory)
  const [limit, setLimit] = useState(72)
  useEffect(() => { setCategory(routeCategory) }, [routeCategory])
  useEffect(() => { setLimit(72); window.scrollTo(0, 0) }, [query, category])
  const selectCategory = (nextCategory) => {
    const nextHash = nextCategory === 'all' ? '#/catalog' : `#/catalog?category=${encodeURIComponent(nextCategory)}`
    if (window.location.hash === nextHash) {
      setCategory(nextCategory)
      return
    }
    window.location.hash = nextHash
  }
  const filtered = useMemo(() => catalog.entries.filter((entry) => {
    const categoryMatches = category === 'all' || entry.type === category
    const text = `${entry.name}\n${entry.id}\n${entry.description}\n${catalogCategoryMeta[entry.type]?.label || ''}`
    return categoryMatches && (!query || text.toLowerCase().includes(query.toLowerCase()))
  }), [query, category])
  const visible = filtered.slice(0, limit)

  return (
    <main className="catalog-page">
      <section className="catalog-masthead">
        <div>
          <p className="eyebrow"><span /> ZINECRAFT COMPENDIUM</p>
          <h1>模组图鉴</h1>
          <p>浏览 Zinecraft 添加的全部注册内容与数据驱动世界元素。</p>
        </div>
        <div className="catalog-total"><b>{catalog.entries.length.toLocaleString('zh-CN')}</b><span>REGISTERED<br />ENTRIES</span></div>
      </section>
      <div className="catalog-toolbar">
        <label><Icon name="search" /><input value={query} onChange={(event) => setQuery(event.target.value)} placeholder="搜索名称、注册 ID 或效果…" /></label>
        <span>显示 {visible.length} / {filtered.length}</span>
      </div>
      <div className="catalog-layout">
        <aside className="catalog-filter">
          <p>CONTENT TYPE</p>
          <button className={category === 'all' ? 'active' : ''} aria-pressed={category === 'all'} onClick={() => selectCategory('all')}><span>全部内容</span><b>{catalog.entries.length}</b></button>
          {Object.entries(catalogCategoryMeta).map(([id, meta]) => (
            <button key={id} className={category === id ? 'active' : ''} aria-pressed={category === id} onClick={() => selectCategory(id)}>
              <i>{meta.glyph}</i><span>{meta.label}</span><b>{catalog.totals[id] || 0}</b>
            </button>
          ))}
        </aside>
        <section>
          <div className="catalog-grid">
            {visible.map((entry) => {
              const meta = catalogCategoryMeta[entry.type]
              const texture = catalogTexture(entry)
              return <article className="catalog-card" key={`${entry.type}/${entry.id}`}>
                <div className={`catalog-card__visual catalog-card__visual--${entry.type}`}>
                  {texture ? <img src={texture} alt="" loading="lazy" /> : <span>{meta.glyph}</span>}
                  <small>{meta.code}</small>
                </div>
                <div className="catalog-card__body">
                  <p>{meta.label}</p><h2>{entry.name}</h2><code>zinecraft:{entry.id}</code>
                  {entry.description && <span>{entry.description}</span>}
                  <a href={`https://github.com/z8z6/ZineCraft/blob/neoforge/src/main/java/com/cxxcxx/zinecraft/${entry.source}`} target="_blank" rel="noreferrer">查看注册源码 <Icon name="arrow" /></a>
                </div>
              </article>
            })}
          </div>
          {!visible.length && <div className="empty-state"><Icon name="cube" /><h2>没有匹配的内容</h2><p>尝试搜索中文名称或切换分类。</p></div>}
          {visible.length < filtered.length && <button className="load-more" onClick={() => setLimit((value) => value + 72)}>加载更多 <span>{filtered.length - visible.length}</span></button>}
        </section>
      </div>
    </main>
  )
}

function DocsIndex({ initialCategory }) {
  const [query, setQuery] = useState('')
  const [category, setCategory] = useState(initialCategory || 'all')
  const filtered = useMemo(() => documents.filter((doc) => {
    const categoryMatches = category === 'all' || doc.category === category
    const textMatches = !query || `${doc.title}\n${doc.content}`.toLowerCase().includes(query.toLowerCase())
    return categoryMatches && textMatches
  }), [query, category])

  return (
    <main className="docs-index">
      <section className="docs-masthead">
        <p className="eyebrow"><span /> DEVELOPMENT ARCHIVE</p>
        <h1>开发文档</h1>
        <p>搜索项目内部约定、数据结构与内容制作流程。</p>
        <label className="docs-search"><Icon name="search" /><input value={query} onChange={(event) => setQuery(event.target.value)} placeholder="搜索标题或正文…" /><kbd>ENTER</kbd></label>
      </section>
      <div className="docs-layout">
        <aside className="filter-panel">
          <p>DOCUMENT FILTER</p>
          <button className={category === 'all' ? 'active' : ''} onClick={() => setCategory('all')}><span>全部文档</span><b>{documents.length}</b></button>
          {Object.entries(categoryMeta).map(([id, meta]) => {
            const count = documents.filter((doc) => doc.category === id).length
            return count ? <button key={id} className={category === id ? 'active' : ''} onClick={() => setCategory(id)}><span>{meta.label}</span><b>{count}</b></button> : null
          })}
        </aside>
        <section className="document-list">
          <div className="document-list__head"><span>{filtered.length} RESULTS</span><span>UPDATED WITH REPOSITORY</span></div>
          {filtered.map((doc, index) => (
            <a href={documentHref(doc.slug)} className="document-row" key={doc.slug}>
              <span>{String(index + 1).padStart(2, '0')}</span>
              <div><small>{categoryMeta[doc.category].code} / {doc.guide ? 'BEGINNER GUIDE' : 'REFERENCE'}</small><h2>{doc.title}</h2><p>{doc.path}</p></div>
              <Icon name="arrow" />
            </a>
          ))}
          {!filtered.length && <div className="empty-state"><Icon name="cube" /><h2>没有匹配的文档</h2><p>尝试缩短关键词或切换分类。</p></div>}
        </section>
      </div>
    </main>
  )
}

function DocumentPage({ document }) {
  const [copied, setCopied] = useState('')
  const headings = useMemo(() => [...document.content.matchAll(/^(#{2,3})\s+(.+)$/gm)].map((match) => ({ level: match[1].length, text: match[2] })), [document])
  useEffect(() => {
    window.scrollTo(0, 0)
  }, [document.slug])

  const copyCode = async (value) => {
    await navigator.clipboard.writeText(value)
    setCopied(value)
    window.setTimeout(() => setCopied(''), 1200)
  }

  const headingId = (value) => String(value).trim().toLowerCase().replace(/[^\p{L}\p{N}]+/gu, '-')
  const relativePath = (href) => {
    const base = document.path.split('/').slice(0, -1)
    for (const segment of href.split('/')) {
      if (segment === '..') base.pop()
      else if (segment !== '.') base.push(segment)
    }
    return base.join('/')
  }
  const resolveLink = (href = '') => {
    if (/^(https?:|#)/.test(href)) return href
    if (href.endsWith('.md')) {
      return documentHref(relativePath(href).replace(/\.md$/i, ''))
    }
    if (href.includes('src/')) {
      return `https://github.com/z8z6/ZineCraft/blob/neoforge/${href.slice(href.indexOf('src/'))}`
    }
    return href
  }

  return (
    <main className="reader-layout">
      <aside className="reader-nav">
        <a className="reader-nav__back" href="#/docs">← 返回全部文档</a>
        <p>{categoryMeta[document.category].label}</p>
        {documents.filter((doc) => doc.category === document.category).map((doc) => <a key={doc.slug} className={doc.slug === document.slug ? 'active' : ''} href={documentHref(doc.slug)}>{doc.title}</a>)}
      </aside>
      <article className="markdown-shell">
        <div className="markdown-meta"><span>{categoryMeta[document.category].code}</span><span>{document.path}</span></div>
        <ReactMarkdown
          remarkPlugins={[remarkGfm, remarkMath]}
          rehypePlugins={[rehypeKatex]}
          components={{
            h2: ({ children }) => <h2 id={headingId(children)}>{children}</h2>,
            h3: ({ children }) => <h3 id={headingId(children)}>{children}</h3>,
            a: ({ href, children }) => {
              const resolved = resolveLink(href)
              return <a href={resolved} target={resolved.startsWith('http') ? '_blank' : undefined} rel="noreferrer">{children}</a>
            },
            img: ({ src, alt, ...props }) => <ZoomableImage src={src} alt={alt} {...props} />,
            pre: ({ children }) => {
              const language = children?.props?.className || ''
              const value = children?.props?.children?.toString?.().replace(/\n$/, '') || ''
              if (language.includes('language-mermaid')) return <MermaidEmbed chart={value} />
              if (language.includes('language-nation-boundary-d3')) return <NationBoundaryEmbed />
              if (language.includes('language-city-boundary-d3')) return <CityBoundaryEmbed />
              if (language.includes('language-region-growth-d3')) return <RegionGrowthEmbed />
              if (language.includes('language-layer-road-d3')) return <LayerRoadEmbed />
              if (language.includes('language-road-bfs-d3')) return <RoadBfsEmbed />
              if (language.includes('language-road-cleanup-d3')) return <RoadCleanupEmbed />
              if (language.includes('language-parcel-partition-d3')) return <ParcelPartitionEmbed />
              if (language.includes('language-building-selection-d3')) return <BuildingSelectionEmbed />
              if (language.includes('language-road-tile-d3')) return <RoadTileEmbed />
              if (language.includes('language-runtime-placement-d3')) return <RuntimePlacementEmbed />
              return <HighlightedCodeBlock value={value} languageClass={language} copied={copied === value} onCopy={copyCode} />
            },
          }}
        >{document.content}</ReactMarkdown>
      </article>
      <aside className="toc">
        <p>ON THIS PAGE</p>
        {headings.map((heading, index) => <button type="button" onClick={() => window.document.getElementById(headingId(heading.text))?.scrollIntoView()} className={heading.level === 3 ? 'toc__sub' : ''} key={`${heading.text}-${index}`}>{heading.text}</button>)}
      </aside>
    </main>
  )
}

function SearchModal({ open, onClose }) {
  const [query, setQuery] = useState('')
  useEffect(() => { if (open) setQuery('') }, [open])
  if (!open) return null
  const results = documents.filter((doc) => !query || `${doc.title}\n${doc.content}`.toLowerCase().includes(query.toLowerCase())).slice(0, 8)
  return <div className="search-modal" onMouseDown={onClose}><div className="search-modal__panel" onMouseDown={(event) => event.stopPropagation()}><label><Icon name="search" /><input autoFocus value={query} onChange={(event) => setQuery(event.target.value)} placeholder="搜索开发文档" /><button onClick={onClose}>ESC</button></label><div>{results.map((doc) => <a href={documentHref(doc.slug)} onClick={onClose} key={doc.slug}><span>{categoryMeta[doc.category].label}</span><b>{doc.title}</b><Icon name="chevron" /></a>)}</div></div></div>
}

function Footer() {
  const backToTop = () => window.scrollTo({
    top: 0,
    behavior: window.matchMedia('(prefers-reduced-motion: reduce)').matches ? 'auto' : 'smooth',
  })
  return <footer><div><img src="./icon.png" alt="" /><span><b>ZINECRAFT</b><small>UNOFFICIAL FAN PROJECT</small></span></div><p>《明日方舟》相关资料权利归鹰角网络所有。Minecraft 相关权利归 Mojang Studios 所有。</p><button type="button" onClick={backToTop}>BACK TO TOP ↑</button></footer>
}

export default function App() {
  const route = useHashRoute()
  const [searchOpen, setSearchOpen] = useState(false)
  useEffect(() => {
    const handler = (event) => {
      if ((event.ctrlKey || event.metaKey) && event.key.toLowerCase() === 'k') {
        event.preventDefault()
        setSearchOpen(true)
      }
      if (event.key === 'Escape') setSearchOpen(false)
    }
    window.addEventListener('keydown', handler)
    return () => window.removeEventListener('keydown', handler)
  }, [])

  const [path, queryString] = route.split('?')
  const category = new URLSearchParams(queryString).get('category')
  const document = path.startsWith('doc/') ? documents.find((entry) => entry.slug === path.slice(4)) : null
  const content = document ? <DocumentPage document={document} />
    : path.startsWith('catalog') ? <CatalogPage initialCategory={category} />
      : path.startsWith('docs') ? <DocsIndex initialCategory={category} /> : <Home />

  return <><Header onSearch={() => setSearchOpen(true)} />{content}<Footer /><SearchModal open={searchOpen} onClose={() => setSearchOpen(false)} /></>
}
