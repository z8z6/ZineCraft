import { access, readFile } from 'node:fs/promises'
import { dirname, resolve } from 'node:path'
import { fileURLToPath } from 'node:url'

const docsRoot = resolve(dirname(fileURLToPath(import.meta.url)), '..')
const catalog = JSON.parse(await readFile(resolve(docsRoot, 'src/data/catalog.json'), 'utf8'))
const guideMap = JSON.parse(await readFile(resolve(docsRoot, 'src/data/guide-map.json'), 'utf8'))
const catalogTypes = [...new Set(catalog.entries.map((entry) => entry.type))].sort()
const missingTypes = catalogTypes.filter((type) => !guideMap[type])
const unusedTypes = Object.keys(guideMap).filter((type) => !catalogTypes.includes(type)).sort()
const missingGuides = []

for (const type of catalogTypes) {
  const guide = guideMap[type]
  if (!guide) continue
  if (!guide.label || !guide.slug) {
    missingGuides.push(`${type}: 映射缺少 label 或 slug`)
    continue
  }
  try {
    await access(resolve(docsRoot, 'guides', `${guide.slug}.md`))
  } catch {
    missingGuides.push(`${type}: guides/${guide.slug}.md 不存在`)
  }
}

if (missingTypes.length || unusedTypes.length || missingGuides.length) {
  if (missingTypes.length) console.error(`缺少教程映射：${missingTypes.join(', ')}`)
  if (unusedTypes.length) console.error(`存在无目录类型的映射：${unusedTypes.join(', ')}`)
  for (const failure of missingGuides) console.error(failure)
  process.exitCode = 1
} else {
  console.log(`Guide coverage: ${catalog.entries.length}/${catalog.entries.length} entries, ${catalogTypes.length}/${catalogTypes.length} types.`)
  for (const type of catalogTypes) {
    console.log(`  ${type}: ${catalog.totals[type]} -> ${guideMap[type].slug}`)
  }
}
