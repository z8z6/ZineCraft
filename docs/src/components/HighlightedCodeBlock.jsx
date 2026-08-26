import { useMemo } from 'react'
import hljs from 'highlight.js/lib/core'
import java from 'highlight.js/lib/languages/java'
import plaintext from 'highlight.js/lib/languages/plaintext'
import powershell from 'highlight.js/lib/languages/powershell'

const javaWithFunctionCalls = (api) => {
  const definition = java(api)
  definition.contains.unshift({
    scope: 'title.function.invoke',
    begin: /\b(?!(?:if|for|while|switch|catch|new|return|throw|synchronized|this|super)\b)[A-Za-z_$][\w$]*(?=\s*\()/,
    relevance: 0,
  })
  return definition
}

hljs.registerLanguage('java', javaWithFunctionCalls)
hljs.registerLanguage('plaintext', plaintext)
hljs.registerLanguage('powershell', powershell)
hljs.registerLanguage('mcfunction', () => ({
  name: 'Minecraft Function',
  case_insensitive: true,
  keywords: {
    keyword: 'advancement attribute bossbar clear clone damage data datapack debug defaultgamemode difficulty effect enchant execute experience fill fillbiome forceload function gamemode gamerule give help item jfr kick kill list locate loot me msg particle place playsound publish random recipe reload return ride rotate save-all save-off save-on say schedule scoreboard seed setblock setidletimeout setworldspawn spawnpoint spectate spreadplayers stop stopsound summon tag team teammsg teleport tellraw tick time title transfer trigger weather whitelist worldborder',
    literal: 'true false',
  },
  contains: [
    hljs.HASH_COMMENT_MODE,
    hljs.QUOTE_STRING_MODE,
    { className: 'variable', begin: /@[aeprs](?:\[[^\]]*\])?/ },
    { className: 'title.function', begin: /\b[a-z0-9_.-]+:[a-z0-9_./-]+(?=\s|$)/ },
    { className: 'number', begin: /[-+]?\b\d+(?:\.\d+)?\b/ },
  ],
}))

const LANGUAGE_ALIASES = { text: 'plaintext', txt: 'plaintext', ps1: 'powershell' }

export default function HighlightedCodeBlock({ value, languageClass, copied, onCopy }) {
  const requestedLanguage = languageClass.replace(/^language-/, '').split(/\s+/)[0] || 'text'
  const language = LANGUAGE_ALIASES[requestedLanguage] || requestedLanguage
  const highlighted = useMemo(() => {
    const supportedLanguage = hljs.getLanguage(language) ? language : 'plaintext'
    return hljs.highlight(value, { language: supportedLanguage, ignoreIllegals: true }).value
  }, [language, value])

  return (
    <div className="code-frame" data-language={requestedLanguage.toUpperCase()}>
      <span className="code-frame__language" aria-hidden="true">{requestedLanguage}</span>
      <button type="button" onClick={() => onCopy(value)}>{copied ? '已复制' : '复制'}</button>
      <pre><code className={`hljs language-${language}`} dangerouslySetInnerHTML={{ __html: highlighted }} /></pre>
    </div>
  )
}
