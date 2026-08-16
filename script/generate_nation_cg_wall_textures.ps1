[CmdletBinding()]
param(
    [string]$SourceRoot = '',
    [string]$OutputRoot = ''
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

Add-Type -AssemblyName System.Drawing

if ([string]::IsNullOrWhiteSpace($SourceRoot)) {
    # Keep the script Windows PowerShell 5 compatible without relying on the
    # source file's UTF-8/BOM interpretation for the Chinese directory names.
    $arknights = -join ([char]0x660E, [char]0x65E5, [char]0x65B9, [char]0x821F)
    $background = -join ([char]0x80CC, [char]0x666F)
    $SourceRoot = "F:\netdisk\$arknights\CG$([char]0x3001)$background\$background"
}

if ([string]::IsNullOrWhiteSpace($OutputRoot)) {
    $OutputRoot = Join-Path $PSScriptRoot '..\src\main\resources\assets\zinecraft\textures\block'
}

# These entries are deliberately literal and reviewable. Each source rectangle is
# copied at 1:1 scale into a 128x128 RGB PNG; there is no palette reduction,
# stylisation, colour correction, synthesis, or pixel-art redraw.
$Crops = @(
    [pscustomobject]@{ Nation = 'aegir';        Target = 'aegir_pressure_tile';           Source = '51_g4_aegirstreet_1.png';             X = 0;   Y = 120; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'bolivar';      Target = 'bolivar_dossoles_stucco';       Source = '48_g7_galleriesstaircase.png';        X = 0;   Y = 250; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'higashi';      Target = 'higashi_machiya_plaster';       Source = '64_g8_tessaihome.png';                X = 575; Y = 75;  Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'durin';        Target = 'durin_ideal_city_panel';        Source = '30_g7_durinhall.png';                 X = 430; Y = 0;   Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'columbia';     Target = 'columbia_frontier_panel';       Source = '38_g2_colombiaoffice.png';            X = 280; Y = 85;  Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'kazimierz';    Target = 'kazimierz_arena_masonry';       Source = 'bg_nearllivingroom.png';              X = 290; Y = 80;  Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'kazdel';       Target = 'kazdel_fortress_plate';         Source = '49_g1_kazdelroom.png';                X = 450; Y = 448; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'laterano';     Target = 'laterano_basilica_marble';      Source = '26_g1_laterano_cathedralfront.png';   X = 640; Y = 190; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'leithanien';   Target = 'leithanien_resonant_brick';     Source = '28_g6_whitehome.png';                 X = 350; Y = 80;  Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'rim_billiton'; Target = 'rim_billiton_corrugated_steel'; Source = '46_g1_transporter.png';               X = 896; Y = 260; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'minos';        Target = 'minos_heroic_masonry';          Source = '69_g12_generalroom.png';              X = 0;   Y = 60;  Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'sargon';       Target = 'sargon_oasis_adobe';            Source = '53_g1_menatmainstreet_d.png';         X = 170; Y = 105; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'sami';         Target = 'sami_tribal_timber';            Source = '40_g5_samitribe.png';                 X = 350; Y = 448; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'victoria';     Target = 'victoria_industrial_brick';     Source = '37_g5_blockadewall.png';              X = 0;   Y = 200; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'ursus';        Target = 'ursus_imperial_masonry';        Source = '66_g12_deitygrypherburgmeeting.png';  X = 780; Y = 85;  Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'kjerag';       Target = 'kjerag_monastery_stone';        Source = '45_g9_underkjerastastue.png';         X = 390; Y = 210; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'siracusa';     Target = 'siracusa_family_masonry';       Source = '33_g1_srcstreet.png';                 X = 0;   Y = 320; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'yan';          Target = 'yan_courtyard_brick';           Source = '35_g3_yumenobservationtower_d.png';   X = 150; Y = 335; Width = 128; Height = 128 },
    [pscustomobject]@{ Nation = 'iberia';       Target = 'iberia_coastal_masonry';        Source = '57_g13_ibtown_d.png';                 X = 0;   Y = 210; Width = 128; Height = 128 }
)

if ($Crops.Count -ne 19) {
    throw "Expected exactly 19 national wall crops, found $($Crops.Count)."
}

$duplicateTargets = $Crops | Group-Object Target | Where-Object Count -ne 1
if ($duplicateTargets) {
    throw "Duplicate texture targets: $($duplicateTargets.Name -join ', ')"
}

$resolvedOutputRoot = [System.IO.Path]::GetFullPath($OutputRoot)
[System.IO.Directory]::CreateDirectory($resolvedOutputRoot) | Out-Null

$results = foreach ($entry in $Crops) {
    if ($entry.Width -ne 128 -or $entry.Height -ne 128) {
        throw "Crop for $($entry.Target) is not 128x128."
    }

    $sourcePath = Join-Path $SourceRoot $entry.Source
    if (-not (Test-Path -LiteralPath $sourcePath -PathType Leaf)) {
        throw "Missing source image: $sourcePath"
    }

    $source = [System.Drawing.Bitmap]::FromFile($sourcePath)
    try {
        if ($entry.X -lt 0 -or $entry.Y -lt 0 -or
            ($entry.X + $entry.Width) -gt $source.Width -or
            ($entry.Y + $entry.Height) -gt $source.Height) {
            throw "Crop for $($entry.Target) lies outside $($source.Width)x$($source.Height) source image."
        }

        $rectangle = [System.Drawing.Rectangle]::new($entry.X, $entry.Y, $entry.Width, $entry.Height)
        $crop = $source.Clone($rectangle, [System.Drawing.Imaging.PixelFormat]::Format24bppRgb)
        try {
            $targetPath = Join-Path $resolvedOutputRoot ($entry.Target + '.png')
            $crop.Save($targetPath, [System.Drawing.Imaging.ImageFormat]::Png)
        }
        finally {
            $crop.Dispose()
        }
    }
    finally {
        $source.Dispose()
    }

    $verification = [System.Drawing.Bitmap]::FromFile($targetPath)
    $sourceVerification = [System.Drawing.Bitmap]::FromFile($sourcePath)
    try {
        if ($verification.Width -ne 128 -or $verification.Height -ne 128) {
            throw "Unexpected output dimensions for $targetPath."
        }
        if ([System.Drawing.Image]::IsAlphaPixelFormat($verification.PixelFormat)) {
            throw "Unexpected alpha channel in $targetPath."
        }

        $leftRightDifference = 0L
        $topBottomDifference = 0L
        for ($offset = 0; $offset -lt 128; $offset++) {
            $left = $verification.GetPixel(0, $offset)
            $right = $verification.GetPixel(127, $offset)
            $top = $verification.GetPixel($offset, 0)
            $bottom = $verification.GetPixel($offset, 127)

            $leftRightDifference += [Math]::Abs([int]$left.R - [int]$right.R)
            $leftRightDifference += [Math]::Abs([int]$left.G - [int]$right.G)
            $leftRightDifference += [Math]::Abs([int]$left.B - [int]$right.B)
            $topBottomDifference += [Math]::Abs([int]$top.R - [int]$bottom.R)
            $topBottomDifference += [Math]::Abs([int]$top.G - [int]$bottom.G)
            $topBottomDifference += [Math]::Abs([int]$top.B - [int]$bottom.B)
        }

        for ($pixelY = 0; $pixelY -lt 128; $pixelY++) {
            for ($pixelX = 0; $pixelX -lt 128; $pixelX++) {
                $expected = $sourceVerification.GetPixel($entry.X + $pixelX, $entry.Y + $pixelY)
                $actual = $verification.GetPixel($pixelX, $pixelY)
                if ($actual.R -ne $expected.R -or $actual.G -ne $expected.G -or $actual.B -ne $expected.B) {
                    throw "Output pixel mismatch for $($entry.Target) at $pixelX,$pixelY."
                }
            }
        }

        $edgeMadLeftRight = [Math]::Round($leftRightDifference / (128.0 * 3.0), 2)
        $edgeMadTopBottom = [Math]::Round($topBottomDifference / (128.0 * 3.0), 2)
    }
    finally {
        $sourceVerification.Dispose()
        $verification.Dispose()
    }

    [pscustomobject]@{
        Nation = $entry.Nation
        Target = $entry.Target
        Crop = "$($entry.X),$($entry.Y),128,128"
        Dimensions = '128x128'
        EdgeMadLR = $edgeMadLeftRight
        EdgeMadTB = $edgeMadTopBottom
        ExactSourceCrop = $true
        Sha256 = (Get-FileHash -LiteralPath $targetPath -Algorithm SHA256).Hash.ToLowerInvariant()
    }
}

$duplicateHashes = $results | Group-Object Sha256 | Where-Object Count -ne 1
if ($duplicateHashes) {
    throw "Duplicate generated textures: $($duplicateHashes.Name -join ', ')"
}

$results | Format-Table -AutoSize
Write-Host "Generated and validated $($results.Count) CG-derived national wall textures."
