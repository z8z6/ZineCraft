import { mkdir, readFile, readdir, writeFile } from 'node:fs/promises'
import { dirname, extname, join, relative, resolve } from 'node:path'
import { fileURLToPath } from 'node:url'

const docsRoot = resolve(dirname(fileURLToPath(import.meta.url)), '..')
const root = resolve(docsRoot, '..')
const generated = join(root, 'src/generated/resources')
const javaRoot = join(root, 'src/main/java/com/cxxcxx/zinecraft')
const lang = JSON.parse(await readFile(join(generated, 'assets/zinecraft/lang/zh_cn.json'), 'utf8'))

const read = (path) => readFile(join(root, path), 'utf8')
const idsFrom = (content, expression) => new Set([...content.matchAll(expression)].map((match) => match[1]))
const filesIn = async (path, extension = '.json') => {
  try {
    return (await readdir(join(root, path), { recursive: true }))
      .filter((name) => extname(name) === extension)
      .map((name) => name.replaceAll('\\', '/').replace(new RegExp(`${extension}$`), ''))
  } catch {
    return []
  }
}
const exactLanguageEntries = (prefix) => Object.entries(lang)
  .filter(([key]) => key.startsWith(prefix) && !key.slice(prefix.length).includes('.'))
  .map(([key, name]) => ({ id: key.slice(prefix.length), name }))

const collectibleJava = await read('src/main/java/com/cxxcxx/zinecraft/core/registry/ModCollectible.java')
const skillJava = await read('src/main/java/com/cxxcxx/zinecraft/core/skill/ModSkill.java')
const weaponJava = await read('src/main/java/com/cxxcxx/zinecraft/core/registry/ModWeapon.java')
const collectibleIds = new Set(
  [...collectibleJava.matchAll(/public static final CollectibleBuilder\s+(\w+)\s*=/g)]
    .map((match) => match[1].toLowerCase()),
)
const skillIds = idsFrom(skillJava, /SkillBuilder\s+\w+\s*=\s*skill\(\s*"([^"]+)"/g)
const weaponIds = idsFrom(weaponJava, /WeaponBuilder\s+\w+\s*=\s*weapon\(\s*"([^"]+)"/g)
const blockIds = new Set(exactLanguageEntries('block.zinecraft.').map(({ id }) => id))

const descriptionFor = (id, type) => {
  const base = `item.zinecraft.${id}`
  if (type === 'collectibles') return lang[`${base}.original_effect`] || lang[`${base}.description`] || ''
  if (type === 'skills') return lang[`${base}.tooltip.description`] || lang[`${base}.tooltip.operator`] || ''
  return ''
}

const entries = []
const add = (type, id, name, source, description = '') => entries.push({
  type, id, name: String(name || id), description: String(description || ''), source,
})

for (const { id, name } of exactLanguageEntries('item.zinecraft.')) {
  if (blockIds.has(id)) continue
  const type = collectibleIds.has(id) ? 'collectibles' : skillIds.has(id) ? 'skills' : weaponIds.has(id) ? 'weapons' : 'items'
  const source = type === 'collectibles' ? 'core/registry/ModCollectible.java'
    : type === 'skills' ? 'core/skill/ModSkill.java'
      : type === 'weapons' ? 'core/registry/ModWeapon.java' : 'core/registry/ModItem.java'
  add(type, id, name, source, descriptionFor(id, type))
}
for (const { id, name } of exactLanguageEntries('block.zinecraft.')) add('blocks', id, name, 'core/registry/ModBlock.java')
for (const { id, name } of exactLanguageEntries('entity.zinecraft.')) add('entities', id, name, 'core/registry/ModEntity.java')
for (const { id, name } of exactLanguageEntries('biome.zinecraft.')) add('biomes', id, name, 'core/registry/ModBiome.java')
const structureEntries = exactLanguageEntries('structure.zinecraft.')
const structureIds = new Set(structureEntries.map(({ id }) => id))
for (const { id, name } of structureEntries) add('structures', id, name, 'core/registry/ModStructure.java')
for (const id of await filesIn('src/generated/resources/data/zinecraft/worldgen/structure')) {
  if (!structureIds.has(id)) add('structures', id, id.replaceAll('_', ' '), 'core/registry/ModStructure.java')
}
for (const { id, name } of exactLanguageEntries('effect.zinecraft.')) add('effects', id, name, 'core/registry/ModMobEffect.java')
for (const { id, name } of exactLanguageEntries('dimension.zinecraft.')) add('dimensions', id, name, 'core/registry/ModDimension.java')
for (const { id, name } of exactLanguageEntries('journeymap.zinecraft.nation.')) add('nations', id, name, 'core/registry/ModNation.java')
for (const { id, name } of exactLanguageEntries('journeymap.zinecraft.city.')) add('cities', id, name, 'core/registry/ModCity.java')

for (const [key, name] of Object.entries(lang).filter(([key]) => key.startsWith('journeymap.zinecraft.region.'))) {
  const id = key.slice('journeymap.zinecraft.region.'.length)
  add('regions', id, name, 'core/registry/ModCityRegion.java')
}
for (const id of await filesIn('src/generated/resources/data/zinecraft/worldgen/configured_feature')) {
  add('features', id, id.replaceAll('_', ' '), 'core/registry/ModWorldFeature.java')
}
for (const { id, name } of exactLanguageEntries('sound.zinecraft.')) add('sounds', id, name, 'core/registry/ModSound.java')

entries.sort((a, b) => a.type.localeCompare(b.type) || a.name.localeCompare(b.name, 'zh-CN') || a.id.localeCompare(b.id))
const output = {
  generatedFrom: 'src/generated/resources + core registry source',
  totals: Object.fromEntries([...new Set(entries.map(({ type }) => type))].map((type) => [type, entries.filter((entry) => entry.type === type).length])),
  entries,
}

const target = join(docsRoot, 'src/data/catalog.json')
await mkdir(dirname(target), { recursive: true })
await writeFile(target, JSON.stringify(output, null, 2) + '\n')
console.log(`Generated ${relative(root, target)} with ${entries.length} entries.`)
