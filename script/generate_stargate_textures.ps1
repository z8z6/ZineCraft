param(
  [string]$ResourceRoot
)

$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Drawing

if ([string]::IsNullOrWhiteSpace($ResourceRoot)) {
  $repoRoot = Resolve-Path (Join-Path $PSScriptRoot '..')
  $ResourceRoot = Join-Path $repoRoot 'src\main\resources\assets\zinecraft\textures'
}

$blockDirectory = Join-Path $ResourceRoot 'block'
$itemDirectory = Join-Path $ResourceRoot 'item'
New-Item -ItemType Directory -Force -Path $blockDirectory, $itemDirectory | Out-Null

function Brush([string]$hex) {
  [System.Drawing.SolidBrush]::new([System.Drawing.ColorTranslator]::FromHtml($hex))
}

function Rect($graphics, [string]$hex, [int]$x, [int]$y, [int]$width, [int]$height) {
  $brush = Brush $hex
  $graphics.FillRectangle($brush, $x, $y, $width, $height)
  $brush.Dispose()
}

function Pixel($graphics, [string]$hex, [int]$x, [int]$y) {
  Rect $graphics $hex $x $y 1 1
}

function Save-Canvas($bitmap, $graphics, [string]$path) {
  $graphics.Dispose()
  $bitmap.Save($path, [System.Drawing.Imaging.ImageFormat]::Png)
  $bitmap.Dispose()
}

# 星门拱石：深色耐蚀合金、分块接缝与青金色协议线路。
$archBitmap = [System.Activator]::CreateInstance([System.Drawing.Bitmap], [object[]]@(16, 16))
$arch = [System.Drawing.Graphics]::FromImage($archBitmap)
$arch.Clear([System.Drawing.ColorTranslator]::FromHtml('#111820'))
for ($y = 0; $y -lt 16; $y++) {
  for ($x = 0; $x -lt 16; $x++) {
    $noise = (($x * 31 + $y * 47 + ($x * $y * 7)) -band 15)
    if ($noise -lt 3) { Pixel $arch '#202D38' $x $y }
    elseif ($noise -eq 15) { Pixel $arch '#34434D' $x $y }
  }
}
Rect $arch '#080D13' 0 0 16 1
Rect $arch '#080D13' 0 7 16 1
Rect $arch '#080D13' 0 15 16 1
Rect $arch '#273641' 0 1 16 1
Rect $arch '#273641' 0 8 16 1
Rect $arch '#0A7480' 2 4 12 1
Rect $arch '#43D6D2' 5 4 2 1
Rect $arch '#C8A64A' 11 4 2 1
Rect $arch '#143E4B' 3 11 10 1
Pixel $arch '#55E8DF' 8 11
Pixel $arch '#D9BA5B' 9 11
Save-Canvas $archBitmap $arch (Join-Path $blockDirectory 'stargate_arch.png')

# 控制器：同一张六面纹理，以中央菱形表示协议源石读取槽。
$controllerBitmap = [System.Activator]::CreateInstance([System.Drawing.Bitmap], [object[]]@(16, 16))
$controller = [System.Drawing.Graphics]::FromImage($controllerBitmap)
$controller.Clear([System.Drawing.ColorTranslator]::FromHtml('#101820'))
Rect $controller '#293844' 1 1 14 14
Rect $controller '#0B1117' 2 2 12 12
Rect $controller '#42515A' 3 3 10 10
Rect $controller '#17242D' 4 4 8 8
Rect $controller '#0B6974' 6 4 4 8
Rect $controller '#0B6974' 4 6 8 4
Rect $controller '#42D8D4' 7 4 2 8
Rect $controller '#42D8D4' 4 7 8 2
Rect $controller '#D4B34F' 7 6 2 4
Rect $controller '#F4DD84' 7 7 2 2
Pixel $controller '#77FFF5' 6 6
Pixel $controller '#77FFF5' 9 9
Save-Canvas $controllerBitmap $controller (Join-Path $blockDirectory 'stargate_controller.png')

# 协议源石：透明物品图标，源石晶核嵌入青金色通信协议框架。
$keyBitmap = [System.Activator]::CreateInstance([System.Drawing.Bitmap], [object[]]@(16, 16))
$key = [System.Drawing.Graphics]::FromImage($keyBitmap)
$key.Clear([System.Drawing.Color]::Transparent)
Rect $key '#17222A' 4 2 8 12
Rect $key '#3B4C55' 3 4 10 8
Rect $key '#9B7A32' 5 1 6 14
Rect $key '#D7B64F' 4 3 8 10
Rect $key '#1A3038' 5 4 6 8
Rect $key '#08717A' 7 3 2 10
Rect $key '#08717A' 5 6 6 4
Rect $key '#43DCD6' 7 5 2 6
Rect $key '#43DCD6' 6 7 4 2
Rect $key '#E8D27B' 7 7 2 2
Pixel $key '#A4FFF8' 6 6
Pixel $key '#A4FFF8' 9 9
Pixel $key '#5D6870' 3 5
Pixel $key '#5D6870' 12 10
Save-Canvas $keyBitmap $key (Join-Path $itemDirectory 'protocol_originium.png')

Write-Output 'Generated stargate_arch.png, stargate_controller.png, and protocol_originium.png.'
