param(
  [string]$OutputDirectory = "src/main/resources/assets/zinecraft/textures/item"
)

$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Drawing

$target = Join-Path (Resolve-Path (Join-Path $PSScriptRoot '..')) $OutputDirectory
[System.IO.Directory]::CreateDirectory($target) | Out-Null

function Color([string]$hex) {
  return [System.Drawing.ColorTranslator]::FromHtml($hex)
}

function Brush([string]$hex) {
  return [System.Drawing.SolidBrush]::new((Color $hex))
}

function Rect($graphics, [string]$hex, [int]$x, [int]$y, [int]$width, [int]$height) {
  $brush = Brush $hex
  $graphics.FillRectangle($brush, $x, $y, $width, $height)
  $brush.Dispose()
}

function Ellipse($graphics, [string]$hex, [int]$x, [int]$y, [int]$width, [int]$height) {
  $brush = Brush $hex
  $graphics.FillEllipse($brush, $x, $y, $width, $height)
  $brush.Dispose()
}

function Polygon($graphics, [string]$hex, [int[]]$coordinates) {
  $points = [System.Drawing.Point[]]::new($coordinates.Count / 2)
  for ($index = 0; $index -lt $points.Count; $index++) {
    $points[$index] = [System.Drawing.Point]::new($coordinates[$index * 2], $coordinates[$index * 2 + 1])
  }
  $brush = Brush $hex
  $graphics.FillPolygon($brush, $points)
  $brush.Dispose()
}

function Bowl($graphics, [string]$rim, [string]$body, [string]$food) {
  Ellipse $graphics $rim 2 6 12 7
  Rect $graphics $body 3 8 10 4
  Ellipse $graphics $body 3 9 10 5
  Ellipse $graphics $food 3 6 10 4
  Rect $graphics '#FFFFFF' 5 11 4 1
}

$foodIds = @(
  'aegir_fresh_shellcrab_sashimi',
  'bolivar_smoked_capsule',
  'higashi_nano_kappo',
  'durin_honey_slugpudding',
  'columbia_originium_roasted_fowl',
  'kazimierz_knight_supplement',
  'kazdel_cartilage_tack',
  'laterano_sacred_tone_soup',
  'leithanien_musical_roast_extract',
  'rim_billiton_mining_ration',
  'minos_poetry_gel',
  'sargon_grass_cheese_gel',
  'sami_instant_bone_soup',
  'victoria_central_valley_roast',
  'ursus_ham_supplement',
  'kjerag_valley_pie',
  'siracusa_stew_gathering',
  'yan_wasteland_meat_stir_fry',
  'iberia_chitin_cluster'
)

foreach ($id in $foodIds) {
  $bitmap = [System.Drawing.Bitmap]::new(16, 16, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
  $graphics = [System.Drawing.Graphics]::FromImage($bitmap)
  $graphics.Clear([System.Drawing.Color]::Transparent)
  $graphics.SmoothingMode = [System.Drawing.Drawing2D.SmoothingMode]::None
  $graphics.PixelOffsetMode = [System.Drawing.Drawing2D.PixelOffsetMode]::Half

  switch ($id) {
    'aegir_fresh_shellcrab_sashimi' {
      Ellipse $graphics '#071D36' 1 6 14 8
      Ellipse $graphics '#14507A' 2 7 12 6
      Ellipse $graphics '#082946' 3 8 10 4
      Polygon $graphics '#9BE7EA' @(4,7, 7,5, 9,7, 6,10)
      Polygon $graphics '#D8FFFF' @(7,7, 10,5, 12,7, 9,10)
      Rect $graphics '#43B8C4' 5 8 2 1
      Rect $graphics '#F06C4D' 11 5 2 2
      Rect $graphics '#F5B45B' 12 4 1 1
    }
    'bolivar_smoked_capsule' {
      Rect $graphics '#512A24' 3 5 10 6
      Ellipse $graphics '#512A24' 1 5 5 6
      Ellipse $graphics '#512A24' 10 5 5 6
      Rect $graphics '#D65B38' 3 6 5 4
      Rect $graphics '#F2A04B' 8 6 4 4
      Rect $graphics '#FFE0A0' 7 6 1 4
      Rect $graphics '#FFF1C7' 4 6 2 1
      Rect $graphics '#8C382F' 4 10 7 1
    }
    'higashi_nano_kappo' {
      Rect $graphics '#241C2B' 2 5 12 8
      Rect $graphics '#5C3540' 3 6 10 6
      Rect $graphics '#F2E6C9' 4 7 3 2
      Rect $graphics '#E85A4F' 4 6 3 1
      Rect $graphics '#F2E6C9' 9 7 3 2
      Rect $graphics '#D88854' 9 6 3 1
      Rect $graphics '#EAD9AD' 6 10 4 2
      Rect $graphics '#4E8D62' 7 9 2 1
      Rect $graphics '#FCF5DD' 3 12 10 1
    }
    'durin_honey_slugpudding' {
      Rect $graphics '#2F7F91' 4 9 8 4
      Ellipse $graphics '#55B7C4' 4 11 8 3
      Ellipse $graphics '#F6CF4A' 4 4 8 8
      Rect $graphics '#FFEF89' 6 4 4 1
      Rect $graphics '#E99335' 5 7 6 2
      Rect $graphics '#FFFFFF' 6 10 3 1
      Rect $graphics '#EE6B65' 10 5 2 2
      Rect $graphics '#8FD36B' 11 4 1 1
    }
    'columbia_originium_roasted_fowl' {
      Polygon $graphics '#6F2D24' @(3,5, 10,4, 13,7, 11,12, 5,13, 2,9)
      Polygon $graphics '#C45B32' @(4,6, 9,5, 11,7, 10,10, 6,11, 3,9)
      Rect $graphics '#F39B4A' 5 6 4 1
      Rect $graphics '#442331' 7 8 2 2
      Rect $graphics '#B9D4D0' 11 10 3 2
      Rect $graphics '#EDF6E8' 13 10 2 1
      Rect $graphics '#78C6D0' 3 4 2 2
    }
    'kazimierz_knight_supplement' {
      Rect $graphics '#3C344A' 5 3 6 10
      Rect $graphics '#B68B2E' 4 5 8 7
      Rect $graphics '#F2CF54' 5 6 6 5
      Rect $graphics '#FFF1A1' 6 6 3 1
      Rect $graphics '#D9483E' 7 8 2 2
      Rect $graphics '#ECE7D2' 6 2 4 2
      Rect $graphics '#8B7F9B' 7 1 2 1
    }
    'kazdel_cartilage_tack' {
      Polygon $graphics '#4A3431' @(3,4, 12,3, 14,7, 12,12, 4,13, 2,9)
      Polygon $graphics '#C89E68' @(4,5, 11,4, 13,7, 11,11, 5,12, 3,9)
      Rect $graphics '#E5C78F' 5 6 5 2
      Rect $graphics '#6E4540' 5 9 2 2
      Rect $graphics '#7E5145' 10 6 2 2
      Rect $graphics '#F0DAA7' 8 10 2 1
    }
    'laterano_sacred_tone_soup' {
      Bowl $graphics '#D7B64A' '#F5EEDB' '#F4B7D6'
      Rect $graphics '#FFF8DE' 5 6 5 1
      Rect $graphics '#FFD65A' 7 3 2 2
      Rect $graphics '#FFF4B1' 8 2 1 1
      Rect $graphics '#83CBE0' 11 5 1 2
    }
    'leithanien_musical_roast_extract' {
      Rect $graphics '#34243E' 4 4 8 9
      Rect $graphics '#6F3C78' 5 5 6 7
      Rect $graphics '#A95A69' 6 7 4 4
      Rect $graphics '#E4A45C' 7 8 2 2
      Rect $graphics '#D9C3F2' 6 4 1 2
      Rect $graphics '#D9C3F2' 9 3 1 3
      Rect $graphics '#ECE4FF' 10 3 2 1
    }
    'rim_billiton_mining_ration' {
      Rect $graphics '#3D4547' 3 4 10 9
      Rect $graphics '#8C9995' 4 5 8 7
      Rect $graphics '#BFC9BD' 5 6 6 5
      Rect $graphics '#B65335' 4 8 8 2
      Rect $graphics '#E6D6A2' 6 6 4 2
      Rect $graphics '#F5F0D7' 6 6 2 1
      Rect $graphics '#273033' 3 5 1 6
    }
    'minos_poetry_gel' {
      Ellipse $graphics '#7D522D' 3 9 10 4
      Ellipse $graphics '#D5B36A' 4 4 8 8
      Rect $graphics '#EDDB92' 5 5 6 4
      Rect $graphics '#9E6BB0' 6 7 4 3
      Rect $graphics '#F1C05B' 7 5 2 1
      Rect $graphics '#FFF2C0' 5 4 3 1
    }
    'sargon_grass_cheese_gel' {
      Ellipse $graphics '#8B552B' 3 9 10 4
      Ellipse $graphics '#6AA44C' 4 4 8 8
      Rect $graphics '#A8D35F' 5 5 6 5
      Rect $graphics '#E8D77A' 6 7 4 3
      Rect $graphics '#3D7139' 9 5 2 2
      Rect $graphics '#D7F093' 5 5 2 1
    }
    'sami_instant_bone_soup' {
      Bowl $graphics '#31506C' '#9CB8C2' '#E7D8B7'
      Rect $graphics '#F5F0DC' 7 4 2 5
      Rect $graphics '#F5F0DC' 6 3 1 2
      Rect $graphics '#F5F0DC' 9 3 1 2
      Rect $graphics '#B7E4EA' 4 7 2 1
      Rect $graphics '#D5F5F4' 5 2 1 2
      Rect $graphics '#D5F5F4' 10 1 1 3
    }
    'victoria_central_valley_roast' {
      Ellipse $graphics '#4B3530' 1 8 14 6
      Ellipse $graphics '#A75B35' 3 4 10 8
      Rect $graphics '#D9874A' 4 5 8 2
      Rect $graphics '#6E3229' 5 8 3 3
      Rect $graphics '#E3B968' 9 8 3 2
      Rect $graphics '#5D8A45' 3 11 3 1
      Rect $graphics '#F2E7C7' 10 12 3 1
    }
    'ursus_ham_supplement' {
      Polygon $graphics '#5A2E2C' @(3,5, 9,3, 13,5, 13,10, 9,13, 4,11)
      Polygon $graphics '#B64D43' @(4,6, 9,4, 12,6, 12,9, 9,12, 5,10)
      Rect $graphics '#E58B68' 6 6 4 2
      Rect $graphics '#F5D9B2' 11 4 3 2
      Rect $graphics '#FFF0D1' 13 4 2 1
      Rect $graphics '#6D2530' 5 10 3 1
    }
    'kjerag_valley_pie' {
      Ellipse $graphics '#68422D' 2 4 12 10
      Ellipse $graphics '#C9833A' 3 4 10 9
      Ellipse $graphics '#E4B35C' 4 5 8 7
      Rect $graphics '#F4D783' 5 5 6 2
      Rect $graphics '#9B4C3C' 6 8 4 2
      Rect $graphics '#D99043' 7 7 1 4
      Rect $graphics '#D99043' 5 9 6 1
      Rect $graphics '#F3F6E8' 4 12 8 1
    }
    'siracusa_stew_gathering' {
      Bowl $graphics '#50342F' '#A55A42' '#8E342A'
      Rect $graphics '#D77C3F' 5 6 3 2
      Rect $graphics '#E2B653' 9 7 2 2
      Rect $graphics '#6C954A' 7 9 2 1
      Rect $graphics '#F2D0A0' 4 7 1 1
      Rect $graphics '#D8D1C5' 12 4 1 4
    }
    'yan_wasteland_meat_stir_fry' {
      Bowl $graphics '#26343A' '#48636B' '#9C4B31'
      Rect $graphics '#D06B3F' 4 6 3 2
      Rect $graphics '#6E9A45' 8 6 3 2
      Rect $graphics '#E1B248' 6 8 2 2
      Rect $graphics '#C93F36' 10 8 2 1
      Rect $graphics '#EEF0D8' 5 11 4 1
    }
    'iberia_chitin_cluster' {
      Polygon $graphics '#5B6870' @(3,5, 7,3, 10,5, 13,4, 14,9, 11,13, 5,12, 2,9)
      Polygon $graphics '#C4C7B4' @(4,6, 7,4, 9,6, 12,5, 13,8, 10,11, 6,10, 3,8)
      Rect $graphics '#ECEBD2' 5 5 2 3
      Rect $graphics '#9FB8B5' 8 7 3 2
      Rect $graphics '#F6F2D7' 6 10 4 1
      Rect $graphics '#6AA6A6' 11 9 2 2
      Rect $graphics '#FFFFFF' 4 6 1 1
    }
  }

  $path = Join-Path $target "$id.png"
  $bitmap.Save($path, [System.Drawing.Imaging.ImageFormat]::Png)
  $graphics.Dispose()
  $bitmap.Dispose()
}

Write-Output "Generated $($foodIds.Count) deterministic 16x16 nation food textures in $target"
