param(
    [Parameter(Mandatory = $true)]
    [string]$SourceTexture,
    [string]$OutputDirectory = "docs/blockbench/armored_beast"
)

$ErrorActionPreference = "Stop"

function New-ModelId {
    return [guid]::NewGuid().ToString()
}

function New-FaceSet([int[]]$Uv) {
    $faces = [ordered]@{}
    foreach ($side in @("north", "east", "south", "west", "up", "down")) {
        $faces[$side] = [ordered]@{ uv = $Uv; texture = 0 }
    }
    return $faces
}

function Add-Group(
    [string]$Name,
    [double[]]$Origin,
    [string]$Parent = ""
) {
    $id = New-ModelId
    [void]$script:groups.Add([ordered]@{
        name = $Name
        origin = $Origin
        color = $script:groups.Count % 8
        uuid = $id
        export = $true
        mirror_uv = $false
        isOpen = $true
    })
    $script:groupChildren[$id] = [System.Collections.Generic.List[object]]::new()
    $script:groupParents[$id] = $Parent
    $script:groupNames[$Name] = $id
    return $id
}

function Add-Cube(
    [string]$Name,
    [double[]]$From,
    [double[]]$To,
    [int[]]$Uv,
    [string]$Group,
    [double[]]$Origin = @(0, 0, 0),
    [double[]]$Rotation = @(0, 0, 0),
    [double]$Inflate = 0
) {
    $id = New-ModelId
    [void]$script:elements.Add([ordered]@{
        name = $Name
        box_uv = $false
        rescale = $false
        locked = $false
        light_emission = 0
        render_order = "default"
        allow_mirror_modeling = $true
        from = $From
        to = $To
        autouv = 0
        color = $script:elements.Count % 8
        origin = $Origin
        rotation = $Rotation
        inflate = $Inflate
        faces = New-FaceSet $Uv
        type = "cube"
        uuid = $id
    })
    [void]$script:groupChildren[$Group].Add($id)
}

function New-Keyframe(
    [string]$Channel,
    [double]$Time,
    [double[]]$Value,
    [string]$Interpolation = "catmullrom"
) {
    return [ordered]@{
        channel = $Channel
        data_points = @([ordered]@{ x = $Value[0]; y = $Value[1]; z = $Value[2] })
        uuid = New-ModelId
        time = $Time
        color = -1
        interpolation = $Interpolation
    }
}

function Add-Animator(
    [System.Collections.Specialized.OrderedDictionary]$Animators,
    [string]$GroupName,
    [object[]]$Keyframes
) {
    $id = $script:groupNames[$GroupName]
    $Animators[$id] = [ordered]@{
        name = $GroupName
        type = "bone"
        keyframes = $Keyframes
    }
}

function New-Animation(
    [string]$Name,
    [string]$Loop,
    [double]$Length,
    [System.Collections.Specialized.OrderedDictionary]$Animators
) {
    return [ordered]@{
        uuid = New-ModelId
        name = $Name
        loop = $Loop
        override = $false
        length = $Length
        snapping = 20
        selected = $false
        animators = $Animators
    }
}

function New-OutlinerNode([string]$GroupId) {
    $children = @()
    foreach ($child in $script:groupChildren[$GroupId]) {
        $children += $child
    }
    foreach ($group in $script:groups) {
        if ($script:groupParents[$group.uuid] -eq $GroupId) {
            $children += ,(New-OutlinerNode $group.uuid)
        }
    }
    return [pscustomobject][ordered]@{ uuid = $GroupId; isOpen = $true; children = $children }
}

$elements = [System.Collections.Generic.List[object]]::new()
$groups = [System.Collections.Generic.List[object]]::new()
$groupChildren = [ordered]@{}
$groupParents = [ordered]@{}
$groupNames = [ordered]@{}

$root = Add-Group "root" @(0, 0, 0)
$body = Add-Group "body" @(0, 12, 0) $root
$mane = Add-Group "mane" @(0, 14, -8) $body
$armor = Add-Group "back_armor" @(0, 19, 1) $body
$head = Add-Group "head" @(0, 10, -13) $body
$jaw = Add-Group "jaw" @(0, 6, -18) $head
$leftEar = Add-Group "ear_left" @(4, 11, -16) $head
$rightEar = Add-Group "ear_right" @(-4, 11, -16) $head
$tail = Add-Group "tail" @(0, 12, 9) $body
$frontLeft = Add-Group "leg_front_left" @(6, 9, -7) $body
$frontRight = Add-Group "leg_front_right" @(-6, 9, -7) $body
$backLeft = Add-Group "leg_back_left" @(6, 9, 7) $body
$backRight = Add-Group "leg_back_right" @(-6, 9, 7) $body

$uvHide = @(2, 2, 118, 96)
$uvFur = @(130, 4, 248, 120)
$uvRock = @(2, 103, 173, 190)
$uvMuzzle = @(180, 130, 250, 193)
$uvCrystal = @(2, 200, 153, 252)
$uvClaw = @(158, 202, 233, 251)
$uvEye = @(240, 204, 253, 219)

Add-Cube "body_core" @(-8, 8, -9) @(8, 18, 9) $uvHide $body
Add-Cube "belly" @(-7, 5.5, -7) @(7, 10, 8) $uvHide $body @(0, 9, 0) @(0, 0, 0) 0.15
Add-Cube "shoulder_mass" @(-9, 8, -12) @(9, 18.5, -4) $uvFur $mane
Add-Cube "neck" @(-6, 7, -15) @(6, 15.5, -9) $uvFur $mane @(0, 11, -11) @(-10, 0, 0)
Add-Cube "chest_fur" @(-7.5, 5, -13) @(7.5, 12, -9) $uvFur $mane @(0, 9, -10) @(-8, 0, 0) 0.1

Add-Cube "head_core" @(-5, 5, -20) @(5, 12, -13) $uvMuzzle $head @(0, 10, -15) @(10, 0, 0)
Add-Cube "brow" @(-5.5, 9, -20.5) @(5.5, 12.5, -15) $uvFur $head @(0, 10, -16) @(5, 0, 0)
Add-Cube "muzzle" @(-4, 3, -23) @(4, 8, -18) $uvMuzzle $jaw @(0, 6, -19) @(8, 0, 0)
Add-Cube "lower_jaw" @(-3.5, 2, -22) @(3.5, 4.5, -17.5) $uvHide $jaw @(0, 5, -18) @(-5, 0, 0)
Add-Cube "eye_left" @(4.9, 8.1, -20.2) @(5.3, 9.4, -18.4) $uvEye $head
Add-Cube "eye_right" @(-5.3, 8.1, -20.2) @(-4.9, 9.4, -18.4) $uvEye $head
Add-Cube "ear_left" @(3.2, 10, -17) @(5.5, 14, -14.5) $uvFur $leftEar @(4, 11, -16) @(0, 0, -28)
Add-Cube "ear_right" @(-5.5, 10, -17) @(-3.2, 14, -14.5) $uvFur $rightEar @(-4, 11, -16) @(0, 0, 28)
Add-Cube "snout_horn" @(-1.2, 7, -24.5) @(1.2, 9, -20.5) $uvClaw $head @(0, 8, -21) @(-32, 0, 0)
Add-Cube "brow_horn_left" @(3.2, 10, -22) @(4.7, 12, -18.5) $uvClaw $head @(3.7, 10.5, -19) @(-24, 0, 12)
Add-Cube "brow_horn_right" @(-4.7, 10, -22) @(-3.2, 12, -18.5) $uvClaw $head @(-3.7, 10.5, -19) @(-24, 0, -12)

Add-Cube "armor_front" @(-7.5, 17, -8.5) @(7.5, 21, -3) $uvRock $armor @(0, 18, -5) @(-8, 0, 0) 0.2
Add-Cube "armor_mid" @(-8, 17.5, -4) @(8, 22, 3) $uvRock $armor @(0, 19, 0) @(0, 0, 0) 0.25
Add-Cube "armor_rear" @(-7.5, 17, 2) @(7.5, 21, 8.5) $uvRock $armor @(0, 18, 5) @(8, 0, 0) 0.2
Add-Cube "crystal_front" @(-2, 20, -6) @(2, 28, -2.5) $uvCrystal $armor @(0, 21, -4) @(-10, 0, 0)
Add-Cube "crystal_center" @(-2.3, 21, -1.8) @(2.3, 30, 2.2) $uvCrystal $armor @(0, 22, 0) @(3, 0, 0)
Add-Cube "crystal_rear" @(-1.7, 20, 2) @(1.7, 27, 5.2) $uvCrystal $armor @(0, 21, 3) @(12, 0, 0)
Add-Cube "crystal_left" @(3.3, 19.5, -2) @(6.2, 25.5, 1) $uvCrystal $armor @(4.5, 20, 0) @(5, 0, 18)
Add-Cube "crystal_right" @(-6.2, 19.5, -2) @(-3.3, 25.5, 1) $uvCrystal $armor @(-4.5, 20, 0) @(5, 0, -18)

Add-Cube "tail_base" @(-2, 9, 8) @(2, 13, 14) $uvHide $tail @(0, 11, 9) @(30, 0, 0)
Add-Cube "tail_tip" @(-1.2, 7, 13) @(1.2, 10, 19) $uvFur $tail @(0, 9, 14) @(42, 0, 0)

foreach ($leg in @(
    @{ group = $frontLeft; name = "front_left"; x1 = 4.3; x2 = 7.8; z1 = -10; z2 = -5 },
    @{ group = $frontRight; name = "front_right"; x1 = -7.8; x2 = -4.3; z1 = -10; z2 = -5 },
    @{ group = $backLeft; name = "back_left"; x1 = 4.3; x2 = 7.8; z1 = 4.5; z2 = 9.5 },
    @{ group = $backRight; name = "back_right"; x1 = -7.8; x2 = -4.3; z1 = 4.5; z2 = 9.5 }
)) {
    $pivotX = ($leg.x1 + $leg.x2) / 2
    $pivotZ = ($leg.z1 + $leg.z2) / 2
    Add-Cube "$($leg.name)_upper" @($leg.x1, 4, $leg.z1) @($leg.x2, 11, $leg.z2) $uvFur $leg.group @($pivotX, 9, $pivotZ) @(0, 0, 0) 0.1
    Add-Cube "$($leg.name)_lower" @(($leg.x1 + 0.4), 0.8, ($leg.z1 + 0.4)) @(($leg.x2 - 0.4), 5.5, ($leg.z2 - 0.4)) $uvHide $leg.group @($pivotX, 4.5, $pivotZ) @(0, 0, 0)
    Add-Cube "$($leg.name)_claw" @(($leg.x1 + 0.2), 0, ($leg.z1 - 0.8)) @(($leg.x2 - 0.2), 1.5, ($leg.z1 + 1.5)) $uvClaw $leg.group @($pivotX, 1, $leg.z1) @(-8, 0, 0)
}

$idle = [ordered]@{}
Add-Animator $idle "body" @(
    (New-Keyframe "position" 0 @(0, 0, 0)),
    (New-Keyframe "position" 1 @(0, 0.25, 0)),
    (New-Keyframe "position" 2 @(0, 0, 0))
)
Add-Animator $idle "head" @(
    (New-Keyframe "rotation" 0 @(0, 0, 0)),
    (New-Keyframe "rotation" 1 @(2.5, 0, 0)),
    (New-Keyframe "rotation" 2 @(0, 0, 0))
)
Add-Animator $idle "tail" @(
    (New-Keyframe "rotation" 0 @(0, -6, 0)),
    (New-Keyframe "rotation" 1 @(0, 7, 0)),
    (New-Keyframe "rotation" 2 @(0, -6, 0))
)

$walk = [ordered]@{}
foreach ($spec in @(
    @{ name = "leg_front_left"; values = @(24, 0, -24, 0, 24) },
    @{ name = "leg_back_right"; values = @(24, 0, -24, 0, 24) },
    @{ name = "leg_front_right"; values = @(-24, 0, 24, 0, -24) },
    @{ name = "leg_back_left"; values = @(-24, 0, 24, 0, -24) }
)) {
    Add-Animator $walk $spec.name @(
        (New-Keyframe "rotation" 0 @($spec.values[0], 0, 0)),
        (New-Keyframe "rotation" 0.3 @($spec.values[1], 0, 0)),
        (New-Keyframe "rotation" 0.6 @($spec.values[2], 0, 0)),
        (New-Keyframe "rotation" 0.9 @($spec.values[3], 0, 0)),
        (New-Keyframe "rotation" 1.2 @($spec.values[4], 0, 0))
    )
}
Add-Animator $walk "body" @(
    (New-Keyframe "position" 0 @(0, 0, 0)),
    (New-Keyframe "position" 0.3 @(0, 0.35, 0)),
    (New-Keyframe "position" 0.6 @(0, 0, 0)),
    (New-Keyframe "position" 0.9 @(0, 0.35, 0)),
    (New-Keyframe "position" 1.2 @(0, 0, 0))
)
Add-Animator $walk "head" @(
    (New-Keyframe "rotation" 0 @(1, 0, 0)),
    (New-Keyframe "rotation" 0.3 @(-3, 0, 0)),
    (New-Keyframe "rotation" 0.6 @(1, 0, 0)),
    (New-Keyframe "rotation" 0.9 @(-3, 0, 0)),
    (New-Keyframe "rotation" 1.2 @(1, 0, 0))
)

$attack = [ordered]@{}
Add-Animator $attack "body" @(
    (New-Keyframe "position" 0 @(0, 0, 0) "linear"),
    (New-Keyframe "position" 0.25 @(0, -0.5, 1) "linear"),
    (New-Keyframe "position" 0.48 @(0, 0, -3) "linear"),
    (New-Keyframe "position" 0.9 @(0, 0, 0) "catmullrom")
)
Add-Animator $attack "head" @(
    (New-Keyframe "rotation" 0 @(0, 0, 0) "linear"),
    (New-Keyframe "rotation" 0.25 @(18, 0, 0) "linear"),
    (New-Keyframe "rotation" 0.48 @(-14, 0, 0) "linear"),
    (New-Keyframe "rotation" 0.9 @(0, 0, 0) "catmullrom")
)
Add-Animator $attack "jaw" @(
    (New-Keyframe "rotation" 0 @(0, 0, 0) "linear"),
    (New-Keyframe "rotation" 0.35 @(20, 0, 0) "linear"),
    (New-Keyframe "rotation" 0.62 @(5, 0, 0) "linear"),
    (New-Keyframe "rotation" 0.9 @(0, 0, 0) "linear")
)

$hurt = [ordered]@{}
Add-Animator $hurt "body" @(
    (New-Keyframe "rotation" 0 @(0, 0, 0) "linear"),
    (New-Keyframe "rotation" 0.12 @(0, 0, 8) "linear"),
    (New-Keyframe "rotation" 0.28 @(0, 0, -4) "linear"),
    (New-Keyframe "rotation" 0.5 @(0, 0, 0) "catmullrom")
)
Add-Animator $hurt "head" @(
    (New-Keyframe "rotation" 0 @(0, 0, 0) "linear"),
    (New-Keyframe "rotation" 0.12 @(-8, 12, 0) "linear"),
    (New-Keyframe "rotation" 0.5 @(0, 0, 0) "catmullrom")
)

$animations = @(
    (New-Animation "animation.armored_beast.idle" "loop" 2 $idle),
    (New-Animation "animation.armored_beast.walk" "loop" 1.2 $walk),
    (New-Animation "animation.armored_beast.attack" "once" 0.9 $attack),
    (New-Animation "animation.armored_beast.hurt" "once" 0.5 $hurt)
)

$outliner = @((New-OutlinerNode $root))
$model = [ordered]@{
    meta = [ordered]@{
        format_version = "5.0"
        model_format = "free"
        box_uv = $false
    }
    name = "armored_beast"
    geometry_name = "armored_beast"
    visible_box = @(3, 3, 0)
    variable_placeholders = ""
    elements = $elements.ToArray()
    groups = $groups.ToArray()
    outliner = $outliner
    textures = @([ordered]@{
        name = "armored_beast.png"
        folder = ""
        namespace = ""
        id = "0"
        particle = $false
        render_mode = "default"
        render_sides = "auto"
        frame_time = 1
        frame_order_type = "loop"
        frame_interpolate = $false
        visible = $true
        internal = $false
        saved = $true
        uuid = New-ModelId
        relative_path = "armored_beast.png"
        uv_width = 256
        uv_height = 256
    })
    animations = $animations
    animation_variable_placeholders = ""
}

$resolvedSource = (Resolve-Path -LiteralPath $SourceTexture).Path
$resolvedOutput = [System.IO.Path]::GetFullPath((Join-Path (Get-Location) $OutputDirectory))
[System.IO.Directory]::CreateDirectory($resolvedOutput) | Out-Null

Add-Type -AssemblyName System.Drawing
$sourceImage = [System.Drawing.Image]::FromFile($resolvedSource)
try {
    $texture = [System.Drawing.Bitmap]::new(256, 256, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
    try {
        $graphics = [System.Drawing.Graphics]::FromImage($texture)
        try {
            $graphics.CompositingMode = [System.Drawing.Drawing2D.CompositingMode]::SourceCopy
            $graphics.CompositingQuality = [System.Drawing.Drawing2D.CompositingQuality]::HighQuality
            $graphics.InterpolationMode = [System.Drawing.Drawing2D.InterpolationMode]::NearestNeighbor
            $graphics.PixelOffsetMode = [System.Drawing.Drawing2D.PixelOffsetMode]::Half
            $graphics.DrawImage($sourceImage, 0, 0, 256, 256)
        }
        finally {
            $graphics.Dispose()
        }
        $texture.Save((Join-Path $resolvedOutput "armored_beast.png"), [System.Drawing.Imaging.ImageFormat]::Png)
    }
    finally {
        $texture.Dispose()
    }
}
finally {
    $sourceImage.Dispose()
}

$json = $model | ConvertTo-Json -Depth 100
[System.IO.File]::WriteAllText(
    (Join-Path $resolvedOutput "armored_beast.bbmodel"),
    $json + [Environment]::NewLine,
    [System.Text.UTF8Encoding]::new($false)
)

Write-Output "Generated $($elements.Count) elements, $($groups.Count) groups, and $($animations.Count) animations in $resolvedOutput"
