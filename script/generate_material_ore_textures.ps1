param(
  [string]$OutputDirectory = "src/main/resources/assets/zinecraft/textures/block"
)

$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Drawing

$repoRoot = Resolve-Path (Join-Path $PSScriptRoot '..')
$target = Join-Path $repoRoot $OutputDirectory
[System.IO.Directory]::CreateDirectory($target) | Out-Null

# 调色板取自 imagegen 生成的原创矿物概念图；最终发布纹理由本脚本确定性绘制为原生 16x16 像素。
$specs = @(
  @{ id='originite_ore'; dark='#7A4515'; mid='#D18420'; bright='#FFD05A'; seed=11 },
  @{ id='orirock_ore'; dark='#5D3A18'; mid='#A96A22'; bright='#E7A640'; seed=23 },
  @{ id='oriron_ore'; dark='#64271D'; mid='#A9442D'; bright='#E2744C'; seed=37 },
  @{ id='manganese_ore_block'; dark='#4A2465'; mid='#7D3FA1'; bright='#C477E4'; seed=41 },
  @{ id='grindstone_ore'; dark='#77766F'; mid='#B8B6A8'; bright='#F3F0D8'; seed=53 },
  @{ id='rma70_ore'; dark='#145750'; mid='#268C7C'; bright='#65D9C0'; seed=67 },
  @{ id='crystal_element_ore'; dark='#17627D'; mid='#34A7C3'; bright='#9BEAF2'; seed=79 },
  @{ id='loxic_kohl_ore'; dark='#8A3511'; mid='#D25C16'; bright='#FFAD35'; seed=97 }
)

function Color([string]$hex) {
  [System.Drawing.ColorTranslator]::FromHtml($hex)
}

function Hash([int]$x, [int]$y, [int]$seed) {
  $value = ([int64]$x * 3749L) + ([int64]$y * 6689L) + ([int64]$seed * 7919L)
  $value = $value -bxor ($value -shr 7)
  [int](($value * 97L + 101L) -band 255L)
}

foreach ($spec in $specs) {
  $bitmap = [System.Drawing.Bitmap]::new(16, 16, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
  $base = @('#171B20', '#20262B', '#292F34', '#33393D')
  $centers = @(
    @((3 + ($spec.seed % 3)), 3, 2),
    @(11, (4 + ($spec.seed % 2)), 2),
    @(5, 11, 2),
    @((12 - ($spec.seed % 3)), 12, 2)
  )

  for ($y = 0; $y -lt 16; $y++) {
    for ($x = 0; $x -lt 16; $x++) {
      $noise = Hash $x $y $spec.seed
      $baseIndex = [Math]::Min(3, [int]($noise / 68))
      $color = $base[$baseIndex]

      foreach ($center in $centers) {
        $dx = [Math]::Abs($x - $center[0])
        $dy = [Math]::Abs($y - $center[1])
        $distance = $dx + $dy
        $ragged = (Hash $x $y ($spec.seed + $center[0] * 13)) % 3
        if ($distance -le ($center[2] + [int]($ragged -eq 0))) {
          if ($distance -eq 0 -or ($noise % 11) -eq 0) { $color = $spec.bright }
          elseif ($distance -le 2) { $color = $spec.mid }
          else { $color = $spec.dark }
        }
      }

      # 用少量短线连接晶簇，使矿物在 16x16 下仍读作矿脉而不是散点。
      if ((($x + $y + $spec.seed) % 13) -eq 0 -and $x -gt 1 -and $x -lt 14) {
        $color = $spec.dark
      }
      $bitmap.SetPixel($x, $y, (Color $color))
    }
  }

  $path = Join-Path $target "$($spec.id).png"
  $bitmap.Save($path, [System.Drawing.Imaging.ImageFormat]::Png)
  $bitmap.Dispose()
}

Write-Output "Generated $($specs.Count) material ore textures in $target"
