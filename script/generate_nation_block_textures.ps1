param(
  [string]$OutputRoot
)

$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Drawing

if ([string]::IsNullOrWhiteSpace($OutputRoot)) {
  $repoRoot = Resolve-Path (Join-Path $PSScriptRoot '..')
  $OutputRoot = Join-Path $repoRoot 'src\main\resources\assets\zinecraft\textures\block'
}

New-Item -ItemType Directory -Force -Path $OutputRoot | Out-Null

# 调色板与纹理节奏由原创 imagegen 样张归纳；最终 16x16 像素由本脚本确定性绘制，便于审查和重建。
$specs = @(
  @{ id='aegir_abyssal_slate'; kind='terrain'; colors=@('#071B33','#0C2C4A','#174563','#2C6376','#16A6B7') }
  @{ id='aegir_pressure_tile'; kind='panel'; colors=@('#07182B','#12354B','#1D5667','#39909A','#21D0D3') }
  @{ id='bolivar_war_scoured_soil'; kind='terrain'; colors=@('#5B452D','#80623A','#A48349','#C1A160','#73723C') }
  @{ id='bolivar_dossoles_stucco'; kind='masonry'; colors=@('#8B4F35','#C96F4D','#E9A16F','#F2D5A2','#F7E6C0') }
  @{ id='higashi_shadow_loam'; kind='roots'; colors=@('#171713','#29231D','#40372C','#5A5145','#566331') }
  @{ id='higashi_machiya_plaster'; kind='timber'; colors=@('#231915','#3B2920','#6A4631','#BEB9A8','#9B3033') }
  @{ id='durin_garden_moss'; kind='terrain'; colors=@('#063C2B','#0E6240','#278648','#63B447','#7A49A8') }
  @{ id='durin_ideal_city_panel'; kind='panel'; colors=@('#176C7C','#33B6BE','#A7DFD0','#F1C85B','#D7659A') }
  @{ id='columbia_canyon_soil'; kind='terrain'; colors=@('#632E22','#8C4027','#B85C32','#D48B55','#E0B47B') }
  @{ id='columbia_frontier_panel'; kind='panel'; colors=@('#4B555B','#7F8B8E','#C9C7B9','#EEE8D7','#B66A3C') }
)
$specs += @(
  @{ id='kazimierz_steppe_turf'; kind='terrain'; colors=@('#4B4A25','#6C6D2D','#90913E','#B7A957','#D1C17A') }
  @{ id='kazimierz_arena_masonry'; kind='masonry'; colors=@('#4C555B','#7E8A8C','#C5C4B6','#EEE7D3','#D2A831') }
  @{ id='kazdel_scarred_ash'; kind='cracked'; colors=@('#171318','#272126','#3B3234','#574748','#8B4C59') }
  @{ id='kazdel_fortress_plate'; kind='plate'; colors=@('#111116','#26262E','#3D3C48','#625765','#8C3E54') }
  @{ id='laterano_alluvial_chalk'; kind='terrain'; colors=@('#A89E7F','#CFC7A8','#E5DDC1','#F3EDD5','#D2B963') }
  @{ id='laterano_basilica_marble'; kind='marble'; colors=@('#8D8A82','#C8C4B8','#E8E4D8','#FAF5E7','#D3AC42') }
  @{ id='laterano_host_casing'; kind='plate'; colors=@('#343C45','#66727C','#A8B2B6','#E4E5DF','#D6B557') }
  @{ id='laterano_host_conduit'; kind='panel'; colors=@('#24384A','#47758B','#8AD6D2','#F7E9A3','#D6B557') }
  @{ id='leithanien_twilight_humus'; kind='roots'; colors=@('#221A24','#342A34','#4B3A45','#5D5845','#725985') }
  @{ id='leithanien_resonant_brick'; kind='brick'; colors=@('#1B1720','#322A38','#574660','#806A8C','#B48AC2') }
)
$specs += @(
  @{ id='rim_billiton_mine_tailings'; kind='terrain'; colors=@('#4A352B','#6A4934','#8C6040','#A98254','#667078') }
  @{ id='rim_billiton_corrugated_steel'; kind='corrugated'; colors=@('#28343A','#43545A','#68777A','#8B8D82','#B56A43') }
  @{ id='minos_sunbaked_earth'; kind='cracked'; colors=@('#6E482C','#93643A','#B98149','#D6A85F','#E2C282') }
  @{ id='minos_heroic_masonry'; kind='masonry'; colors=@('#8F7045','#B9945D','#D9BD83','#EEE0B6','#B98535') }
  @{ id='sargon_desert_crust'; kind='terrain'; colors=@('#8B572A','#B57937','#D59B4C','#E7BD70','#80552D') }
  @{ id='sargon_oasis_adobe'; kind='adobe'; colors=@('#7B3E28','#A95B35','#D27B48','#E5A767','#E4C184') }
  @{ id='sami_frost_moss'; kind='terrain'; colors=@('#29464A','#466666','#6D8580','#A9C2B8','#D7E2D4') }
  @{ id='sami_ritual_stone'; kind='rune_stone'; colors=@('#263A40','#40545A','#65777A','#92A2A0','#6D8E91') }
  @{ id='victoria_moorland_soil'; kind='terrain'; colors=@('#292825','#3F4035','#555B43','#6D7353','#787264') }
  @{ id='victoria_industrial_brick'; kind='brick'; colors=@('#472820','#6D3828','#954B32','#B96945','#36373A') }
)
$specs += @(
  @{ id='victoria_wall_armor'; kind='plate'; colors=@('#20262B','#39434A','#59656B','#7A8588','#A56A3D') }
  @{ id='victoria_cannon_casing'; kind='plate'; colors=@('#4A5358','#6D787C','#96A0A0','#C0C5BF','#B47A43') }
  @{ id='victoria_structural_frame'; kind='corrugated'; colors=@('#14191D','#252D32','#3B464B','#586268','#8C5537') }
  @{ id='victoria_reinforced_floor'; kind='diamond_plate'; colors=@('#161B1E','#293136','#424C50','#647075','#B07B42') }
  @{ id='victoria_control_panel'; kind='panel'; colors=@('#10171B','#243039','#3E5159','#D39A45','#63AEB5') }
  @{ id='victoria_battle_scarred_armor'; kind='ballistic_plate'; colors=@('#12171A','#2B3338','#4A565B','#717B7D','#9A633D') }
  @{ id='victoria_blast_scarred_armor'; kind='blast_plate'; colors=@('#0D1113','#252A2C','#454B4C','#696B66','#875038') }
  @{ id='ursus_permafrost'; kind='terrain'; colors=@('#50626B','#71848D','#9BABB0','#C5D0D2','#E2E9E8') }
  @{ id='ursus_imperial_masonry'; kind='masonry'; colors=@('#434950','#626A70','#879096','#ADB5B6','#704348') }
  @{ id='kjerag_sacred_snowstone'; kind='terrain'; colors=@('#607B88','#83A2AD','#B2CBD2','#DCE9EA','#F4F4EC') }
  @{ id='kjerag_monastery_stone'; kind='masonry'; colors=@('#4E6265','#6F8281','#95A4A0','#C0C7BD','#704F3E') }
  @{ id='siracusa_rain_darkened_soil'; kind='roots'; colors=@('#1D2823','#2D3B30','#40513E','#55704F','#6A6250') }
  @{ id='siracusa_family_masonry'; kind='masonry'; colors=@('#3B3030','#59403A','#755149','#96695B','#3C4C43') }
)
$specs += @(
  @{ id='yan_mountain_soil'; kind='terrain'; colors=@('#34423A','#4B5A48','#64705A','#7D836A','#9B8E6B') }
  @{ id='yan_courtyard_brick'; kind='brick'; colors=@('#263C3B','#355352','#4D6C65','#71887B','#93463E') }
  @{ id='iberia_salt_crusted_gravel'; kind='terrain'; colors=@('#52635F','#71817B','#98A49A','#C3C7B8','#E0DDC9') }
  @{ id='iberia_coastal_masonry'; kind='masonry'; colors=@('#41565A','#607477','#879493','#BAC0B7','#7D9C98') }
)

function Color([string]$hex) {
  [System.Drawing.ColorTranslator]::FromHtml($hex)
}

function Hash([int]$x, [int]$y, [int]$seed) {
  $value = ([int64]$x * 3749L) + ([int64]$y * 6689L) + (([int64]$seed % 65521L) * 31L)
  $value = $value -bxor ($value -shr 7)
  [int](($value * 97L + 101L) -band 255L)
}

function StableSeed([string]$text) {
  [uint32]$hash = 2166136261
  foreach ($character in $text.ToCharArray()) {
    $hash = [uint32](([uint64]($hash -bxor [uint32][char]$character) * 16777619L) -band 0x7FFFFFFF)
  }
  [int]$hash
}

function PaletteIndex($spec, [int]$x, [int]$y, [int]$seed) {
  $kind = $spec.kind
  $noise = Hash $x $y $seed
  $cluster = Hash ([math]::Floor($x / 2)) ([math]::Floor($y / 2)) ($seed + 17)

  switch ($kind) {
    'panel' {
      if (($x % 8) -eq 0 -or ($y % 8) -eq 0) { return 0 }
      if (($x % 8) -eq 1 -or ($y % 8) -eq 1) { return 2 }
      if (($x % 8) -eq 7 -or ($y % 8) -eq 7) { return 1 }
      if ($noise -gt 246) { return 4 }
      return 2 + [int]($noise -gt 180)
    }
    'plate' {
      if (($x % 8) -eq 0 -or ($y % 8) -eq 0) { return 0 }
      if (($x % 8) -eq 7 -or ($y % 8) -eq 7) { return 1 }
      if ((($x % 8) -in 1,6) -and (($y % 8) -in 1,6)) { return 4 }
      return 1 + [int]($noise -gt 160)
    }
    'corrugated' {
      $stripe = $x % 4
      if ($stripe -eq 0) { return 0 }
      if ($stripe -eq 1) { return 2 }
      if ($stripe -eq 2) { return 1 }
      if ($noise -gt 236) { return 4 }
      return 1
    }
    'diamond_plate' {
      $phase = ($x + (2 * $y)) % 6
      if ($phase -in 0,1 -and (($x + $y) % 3) -eq 0) { return 3 }
      if ($phase -eq 5 -and (($x + $y) % 3) -eq 0) { return 0 }
      if ($noise -gt 246) { return 4 }
      return 1 + [int]($noise -gt 150)
    }
    'ballistic_plate' {
      if (($x % 8) -eq 0 -or ($y % 8) -eq 0) { return 0 }
      $impact = (($x - 4) * ($x - 4)) + (($y - 5) * ($y - 5))
      if ($impact -le 1 -or (($x -eq 12) -and ($y -eq 11))) { return 0 }
      if ($impact -le 5) { return 4 }
      if ((($x + 2 * $y + $seed) % 23) -eq 0) { return 4 }
      return 1 + [int]($noise -gt 150)
    }
    'blast_plate' {
      $dx = $x - 7
      $dy = $y - 8
      $radius = ($dx * $dx) + ($dy * $dy)
      if ($radius -le 5) { return 0 }
      if ($radius -le 18) { return 4 }
      if ((($x * 5 + $y * 7 + $seed) % 17) -in 0,1) { return 0 }
      return 1 + [int]($noise -gt 145)
    }
    'timber' {
      if (($x % 8) -eq 0 -or ($x % 8) -eq 1 -or ($y % 8) -eq 0 -or ($y % 8) -eq 1) { return 0 }
      if ((($x % 8) -eq 2 -and ($y % 8) -eq 2)) { return 4 }
      return 3 - [int]($noise -lt 55)
    }
    { $_ -in 'masonry','brick','adobe','marble' } {
      $row = [math]::Floor($y / 4)
      $offset = if (($row % 2) -eq 0) { 0 } else { 4 }
      if (($y % 4) -eq 0 -or ((($x + $offset) % 8) -eq 0)) { return 0 }
      if ($kind -eq 'marble' -and (($x + $y + $seed) % 11) -eq 0) { return 4 }
      if ($noise -gt 238) { return 4 }
      return 1 + [int]($noise -gt 105) + [int]($noise -gt 215)
    }
    'rune_stone' {
      if (($y % 8) -eq 0 -or (($x + $(if (([math]::Floor($y / 8) % 2) -eq 0) {0} else {4})) % 8) -eq 0) { return 0 }
      if ((($x + $y * 3 + $seed) % 13) -eq 0) { return 4 }
      return 1 + [int]($noise -gt 145)
    }
    'roots' {
      if ((($x * 3 + $y * 5 + $seed) % 17) -in 0,1) { return 3 }
      if ($noise -gt 238) { return 4 }
      return [math]::Min(2, [int](($cluster + $noise) / 150))
    }
    'cracked' {
      if ((($x * 5 + $y * 7 + $seed) % 19) -in 0,1) { return 0 }
      if ($noise -gt 242) { return 4 }
      return [math]::Min(3, [int](($cluster + $noise) / 145))
    }
    default {
      if ($noise -gt 244) { return 4 }
      return [math]::Min(3, [int](($cluster + $noise) / 135))
    }
  }
}

foreach ($spec in $specs) {
  $seed = StableSeed $spec.id
  $palette = @($spec.colors | ForEach-Object { Color $_ })
  $bitmap = [System.Drawing.Bitmap]::new(16, 16, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
  try {
    for ($y = 0; $y -lt 16; $y++) {
      for ($x = 0; $x -lt 16; $x++) {
        $index = PaletteIndex $spec $x $y $seed
        $bitmap.SetPixel($x, $y, $palette[$index])
      }
    }
    $target = Join-Path $OutputRoot "$($spec.id).png"
    $bitmap.Save($target, [System.Drawing.Imaging.ImageFormat]::Png)
  } finally {
    $bitmap.Dispose()
  }
}

Write-Output "Generated $($specs.Count) deterministic 16x16 nation block textures in $OutputRoot"
