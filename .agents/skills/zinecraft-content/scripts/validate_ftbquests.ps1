param(
  [string]$QuestRoot
)

$ErrorActionPreference = 'Stop'
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new($false)

if ([string]::IsNullOrWhiteSpace($QuestRoot)) {
  $repoRoot = Resolve-Path (Join-Path $PSScriptRoot '..\..\..\..')
  $QuestRoot = Join-Path $repoRoot 'src\main\resources\zinecraft\ftbquests\quests'
}

$chapterFiles = @(Get-ChildItem -LiteralPath (Join-Path $QuestRoot 'chapters') -Filter '*.snbt' -File)
if ($chapterFiles.Count -eq 0) {
  throw "未找到章节文件：$QuestRoot"
}

$chapterText = ($chapterFiles | ForEach-Object { Get-Content -LiteralPath $_.FullName -Raw }) -join "`n"
$chapterGroupFile = Join-Path $QuestRoot 'chapter_groups.snbt'
if (-not (Test-Path -LiteralPath $chapterGroupFile -PathType Leaf)) {
  throw "缺少章节组文件：$chapterGroupFile"
}
$chapterGroupText = Get-Content -LiteralPath $chapterGroupFile -Raw
$chapterGroupIds = @([regex]::Matches($chapterGroupText, '(?m)\bid:\s*"([0-9A-F]{16})"') | ForEach-Object { $_.Groups[1].Value })
$chapterIds = @([regex]::Matches($chapterText, '(?m)\bid:\s*"([0-9A-F]{16})"') | ForEach-Object { $_.Groups[1].Value })
$ids = @($chapterGroupIds + $chapterIds)
$duplicateIds = @($ids | Group-Object | Where-Object Count -gt 1)
if ($duplicateIds.Count -gt 0) {
  throw "发现重复对象 ID：$($duplicateIds.Name -join ', ')"
}

$dependencies = @([regex]::Matches($chapterText, '(?m)dependencies:\s*\[([^\]]*)\]') | ForEach-Object {
  [regex]::Matches($_.Groups[1].Value, '"([0-9A-F]{16})"') | ForEach-Object { $_.Groups[1].Value }
})
$missingDependencies = @($dependencies | Where-Object { $_ -notin $ids } | Sort-Object -Unique)
if ($missingDependencies.Count -gt 0) {
  throw "发现不存在的任务依赖：$($missingDependencies -join ', ')"
}

$chapterGroupReferences = @([regex]::Matches($chapterText, '(?m)^\s*group:\s*"([0-9A-F]{16})"') | ForEach-Object { $_.Groups[1].Value })
$missingChapterGroups = @($chapterGroupReferences | Where-Object { $_ -notin $chapterGroupIds } | Sort-Object -Unique)
if ($missingChapterGroups.Count -gt 0) {
  throw "章节引用了不存在的章节组：$($missingChapterGroups -join ', ')"
}

$localeKeys = @{}
foreach ($locale in @('en_us', 'zh_cn')) {
  $localeFile = Join-Path $QuestRoot "lang\$locale.snbt"
  if (-not (Test-Path -LiteralPath $localeFile -PathType Leaf)) {
    throw "缺少根级语言表：$localeFile"
  }
  $localeText = Get-Content -LiteralPath $localeFile -Raw
  $localeKeys[$locale] = @([regex]::Matches($localeText, '(?m)^\s*((?:chapter_group|chapter|quest|task|reward)\.[^:]+):') |
    ForEach-Object { $_.Groups[1].Value } | Sort-Object -Unique)
}

$localeDiff = @(Compare-Object $localeKeys['en_us'] $localeKeys['zh_cn'])
if ($localeDiff.Count -gt 0) {
  throw "中英文语言键不一致：$($localeDiff.InputObject -join ', ')"
}

$nestedLanguageFiles = @(Get-ChildItem -LiteralPath (Join-Path $QuestRoot 'lang') -Recurse -Filter '*.snbt' -File |
  Where-Object { $_.DirectoryName -ne (Join-Path $QuestRoot 'lang') })
if ($nestedLanguageFiles.Count -gt 0) {
  throw "FTB Quests 2101.1.x 不读取嵌套语言表：$($nestedLanguageFiles.FullName -join ', ')"
}

Write-Output "FTB Quests 校验通过：章节组=$($chapterGroupIds.Count)，章节=$($chapterFiles.Count)，对象ID=$($ids.Count)，依赖=$($dependencies.Count)，双语键=$($localeKeys['zh_cn'].Count)"
