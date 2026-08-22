param(
    [string]$ModelDirectory = "src/main/resources/assets/zinecraft/blockbench/entity",
    [string]$TextureDirectory = "src/main/resources/assets/zinecraft/textures/entity"
)

$ErrorActionPreference = "Stop"
Add-Type -AssemblyName System.Drawing

$residents = @(
    @{ Name = "sankta_formal_resident"; Palette = "sankta_formal_resident_palette.png" },
    @{ Name = "feline_victorian_resident"; Palette = "feline_victorian_resident_palette.png" }
)

foreach ($resident in $residents) {
    $modelPath = [System.IO.Path]::GetFullPath((Join-Path $ModelDirectory "$($resident.Name).bbmodel"))
    $palettePath = [System.IO.Path]::GetFullPath((Join-Path $TextureDirectory $resident.Palette))
    $outputPath = [System.IO.Path]::GetFullPath((Join-Path $TextureDirectory "$($resident.Name).png"))
    $model = Get-Content -Raw -LiteralPath $modelPath | ConvertFrom-Json
    $palette = [System.Drawing.Bitmap]::new($palettePath)
    $atlas = [System.Drawing.Bitmap]::new(256, 256, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
    $graphics = [System.Drawing.Graphics]::FromImage($atlas)
    try {
        $graphics.Clear([System.Drawing.Color]::Transparent)
        $cursorX = 0
        $cursorY = 0
        $rowHeight = 0
        foreach ($element in $model.elements) {
            $sizeX = [double]$element.to[0] - [double]$element.from[0]
            $sizeY = [double]$element.to[1] - [double]$element.from[1]
            $sizeZ = [double]$element.to[2] - [double]$element.from[2]
            $regionWidth = [int][Math]::Ceiling(2 * ($sizeX + $sizeZ)) + 2
            $regionHeight = [int][Math]::Ceiling($sizeY + $sizeZ) + 2
            if ($cursorX + $regionWidth -gt 256) {
                $cursorX = 0
                $cursorY += $rowHeight
                $rowHeight = 0
            }
            if ($cursorY + $regionHeight -gt 256) {
                throw "Resident texture atlas overflow: $($resident.Name)"
            }

            $paletteX = [int][Math]::Floor([double]$element.faces.north.uv[0])
            $color = $palette.GetPixel($paletteX, 0)
            $brush = [System.Drawing.SolidBrush]::new($color)
            try {
                $graphics.FillRectangle($brush, $cursorX, $cursorY, $regionWidth, $regionHeight)
            }
            finally {
                $brush.Dispose()
            }
            $cursorX += $regionWidth
            $rowHeight = [Math]::Max($rowHeight, $regionHeight)
        }
        [System.IO.Directory]::CreateDirectory([System.IO.Path]::GetDirectoryName($outputPath)) | Out-Null
        $atlas.Save($outputPath, [System.Drawing.Imaging.ImageFormat]::Png)
    }
    finally {
        $graphics.Dispose()
        $atlas.Dispose()
        $palette.Dispose()
    }
    Write-Output "Exported $($resident.Name) native texture atlas to $outputPath"
}
