param(
    [string]$OutputDirectory = "docs/blockbench/sankta_formal_resident",
    [string]$RuntimeDirectory = "src/main/resources/assets/yes_steve_model/builtin/sankta_formal_resident"
)

$ErrorActionPreference = "Stop"
$script:idCounter = 0
$script:bones = [System.Collections.Generic.List[object]]::new()
$script:boneByName = [ordered]@{}
$script:elements = [System.Collections.Generic.List[object]]::new()
$script:groups = [System.Collections.Generic.List[object]]::new()
$script:groupByName = [ordered]@{}
$script:groupChildren = [ordered]@{}
$script:groupParents = [ordered]@{}

function New-StableId {
    $script:idCounter++
    $md5 = [System.Security.Cryptography.MD5]::Create()
    try {
        $bytes = [System.Text.Encoding]::UTF8.GetBytes("sankta_formal_resident:$($script:idCounter)")
        return [guid]::new($md5.ComputeHash($bytes)).ToString()
    }
    finally {
        $md5.Dispose()
    }
}

function New-BedrockUv([int]$ColorIndex) {
    $faces = [ordered]@{}
    foreach ($side in @("north", "east", "south", "west", "up", "down")) {
        $faces[$side] = [ordered]@{
            uv = @($ColorIndex, 0)
            uv_size = @(1, 16)
        }
    }
    return $faces
}

function New-BlockbenchFaces([int]$ColorIndex) {
    $faces = [ordered]@{}
    foreach ($side in @("north", "east", "south", "west", "up", "down")) {
        $faces[$side] = [ordered]@{
            uv = @($ColorIndex, 0, $ColorIndex + 1, 16)
            texture = 0
        }
    }
    return $faces
}

function Add-Bone(
    [string]$Name,
    [double[]]$Pivot,
    [string]$Parent = ""
) {
    $bone = [ordered]@{
        name = $Name
        pivot = $Pivot
        cubes = [System.Collections.Generic.List[object]]::new()
    }
    if ($Parent) {
        $bone.parent = $Parent
    }
    [void]$script:bones.Add($bone)
    $script:boneByName[$Name] = $bone

    $groupId = New-StableId
    [void]$script:groups.Add([ordered]@{
        name = $Name
        origin = $Pivot
        color = $script:groups.Count % 8
        uuid = $groupId
        export = $true
        mirror_uv = $false
        isOpen = $true
    })
    $script:groupByName[$Name] = $groupId
    $script:groupChildren[$groupId] = [System.Collections.Generic.List[object]]::new()
    $script:groupParents[$groupId] = $Parent
}

function Add-Cube(
    [string]$Bone,
    [string]$Name,
    [double[]]$Origin,
    [double[]]$Size,
    [int]$ColorIndex,
    [double[]]$Pivot = $null,
    [double[]]$Rotation = @(0, 0, 0),
    [double]$Inflate = 0
) {
    if ($null -eq $Pivot) {
        $Pivot = @(
            ($Origin[0] + $Size[0] / 2)
            ($Origin[1] + $Size[1] / 2)
            ($Origin[2] + $Size[2] / 2)
        )
    }

    $bedrockCube = [ordered]@{
        origin = $Origin
        size = $Size
        uv = New-BedrockUv $ColorIndex
    }
    if ($Rotation[0] -ne 0 -or $Rotation[1] -ne 0 -or $Rotation[2] -ne 0) {
        $bedrockCube.pivot = $Pivot
        $bedrockCube.rotation = $Rotation
    }
    if ($Inflate -ne 0) {
        $bedrockCube.inflate = $Inflate
    }
    [void]$script:boneByName[$Bone].cubes.Add($bedrockCube)

    $to = @(
        ($Origin[0] + $Size[0])
        ($Origin[1] + $Size[1])
        ($Origin[2] + $Size[2])
    )
    $elementId = New-StableId
    [void]$script:elements.Add([ordered]@{
        name = $Name
        box_uv = $false
        rescale = $false
        locked = $false
        light_emission = $(if ($ColorIndex -eq 11) { 12 } else { 0 })
        render_order = "default"
        allow_mirror_modeling = $true
        from = $Origin
        to = $to
        autouv = 0
        color = $script:elements.Count % 8
        origin = $Pivot
        rotation = $Rotation
        inflate = $Inflate
        faces = New-BlockbenchFaces $ColorIndex
        type = "cube"
        uuid = $elementId
    })
    [void]$script:groupChildren[$script:groupByName[$Bone]].Add($elementId)
}

function New-OutlinerNode([string]$GroupName) {
    $groupId = $script:groupByName[$GroupName]
    $children = [System.Collections.Generic.List[object]]::new()
    foreach ($child in $script:groupChildren[$groupId]) {
        [void]$children.Add($child)
    }
    foreach ($group in $script:groups) {
        if ($script:groupParents[$group.uuid] -eq $GroupName) {
            [void]$children.Add((New-OutlinerNode $group.name))
        }
    }
    return [ordered]@{
        uuid = $groupId
        isOpen = $true
        children = $children.ToArray()
    }
}

function New-Key([double]$Time, [double[]]$Value) {
    return [ordered]@{ time = $Time; value = $Value }
}

function New-Track([string]$Bone, [string]$Channel, [object[]]$Keys) {
    return [ordered]@{ bone = $Bone; channel = $Channel; keys = $Keys }
}

function New-Animation([string]$Name, [string]$Loop, [double]$Length, [object[]]$Tracks) {
    return [ordered]@{ name = $Name; loop = $Loop; length = $Length; tracks = $Tracks }
}

function Convert-ToBedrockAnimation([System.Collections.Specialized.OrderedDictionary]$Animation) {
    $boneTracks = [ordered]@{}
    foreach ($track in $Animation.tracks) {
        if (-not $boneTracks.Contains($track.bone)) {
            $boneTracks[$track.bone] = [ordered]@{}
        }
        $keyframes = [ordered]@{}
        foreach ($key in $track.keys) {
            $keyframes[[string]$key.time] = $key.value
        }
        $boneTracks[$track.bone][$track.channel] = $keyframes
    }
    return [ordered]@{
        loop = ($Animation.loop -eq "loop")
        animation_length = $Animation.length
        bones = $boneTracks
    }
}

function Convert-ToBlockbenchAnimation([System.Collections.Specialized.OrderedDictionary]$Animation) {
    $animators = [ordered]@{}
    foreach ($track in $Animation.tracks) {
        $groupId = $script:groupByName[$track.bone]
        if (-not $animators.Contains($groupId)) {
            $animators[$groupId] = [ordered]@{
                name = $track.bone
                type = "bone"
                rotation_global = $false
                quaternion_interpolation = $false
                keyframes = [System.Collections.Generic.List[object]]::new()
            }
        }
        foreach ($key in $track.keys) {
            [void]$animators[$groupId].keyframes.Add([ordered]@{
                channel = $track.channel
                data_points = @([ordered]@{
                    x = [string]$key.value[0]
                    y = [string]$key.value[1]
                    z = [string]$key.value[2]
                })
                uuid = New-StableId
                time = $key.time
                color = -1
                interpolation = "catmullrom"
            })
        }
    }
    foreach ($animator in $animators.Values) {
        $animator.keyframes = $animator.keyframes.ToArray()
    }
    return [ordered]@{
        uuid = New-StableId
        name = "animation.$($Animation.name)"
        loop = $Animation.loop
        override = $false
        length = $Animation.length
        snapping = 20
        selected = $false
        saved = $true
        path = ""
        scope = 0
        anim_time_update = ""
        blend_weight = ""
        start_delay = ""
        loop_delay = ""
        animators = $animators
    }
}

function Write-Json([string]$Path, [object]$Value) {
    $directory = Split-Path -Parent $Path
    [System.IO.Directory]::CreateDirectory($directory) | Out-Null
    $json = $Value | ConvertTo-Json -Depth 100
    [System.IO.File]::WriteAllText($Path, $json + [Environment]::NewLine, [System.Text.UTF8Encoding]::new($false))
}

function Write-PaletteTexture([string]$Path) {
    Add-Type -AssemblyName System.Drawing
    $colors = @(
        "#E4B7A6", "#91564C", "#593433", "#ECEAE2",
        "#17212E", "#344254", "#9A2937", "#962A34",
        "#4E1B23", "#13161B", "#24262B", "#F5E7A3",
        "#EEE9D8", "#C8C0A5", "#4B3A38", "#A7ABB2"
    )
    $directory = Split-Path -Parent $Path
    [System.IO.Directory]::CreateDirectory($directory) | Out-Null
    $bitmap = [System.Drawing.Bitmap]::new(16, 16, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
    try {
        for ($x = 0; $x -lt $colors.Count; $x++) {
            $color = [System.Drawing.ColorTranslator]::FromHtml($colors[$x])
            for ($y = 0; $y -lt 16; $y++) {
                $bitmap.SetPixel($x, $y, $color)
            }
        }
        $bitmap.Save($Path, [System.Drawing.Imaging.ImageFormat]::Png)
    }
    finally {
        $bitmap.Dispose()
    }
}

# YSM standard humanoid skeleton, with separate halo and wing bones for editing and animation.
Add-Bone "Root" @(0, 0, 0)
Add-Bone "Body" @(0, 14, 0) "Root"
Add-Bone "Head" @(0, 25, 0) "Body"
Add-Bone "Halo" @(0, 35.5, 0) "Head"
Add-Bone "RightArm" @(-5, 24, 0) "Body"
Add-Bone "LeftArm" @(5, 24, 0) "Body"
Add-Bone "RightLeg" @(-2, 14, 0) "Root"
Add-Bone "LeftLeg" @(2, 14, 0) "Root"
Add-Bone "Wings" @(0, 23, 2) "Body"
Add-Bone "RightWing" @(-3.5, 23, 2) "Wings"
Add-Bone "LeftWing" @(3.5, 23, 2) "Wings"
Add-Bone "BackEquipment" @(0, 22, 2.4) "Body"

# Shirt, fitted double-breasted vest, belt, red neck cloth, buttons and waist chain.
Add-Cube "Body" "shirt_torso" @(-4, 14, -2) @(8, 11, 4) 3
Add-Cube "Body" "vest_front" @(-3.75, 15, -2.35) @(7.5, 9.2, 0.5) 4
Add-Cube "Body" "vest_back" @(-3.75, 15, 1.85) @(7.5, 9.2, 0.5) 4
Add-Cube "Body" "vest_left_lapel" @(-2.9, 21.2, -2.55) @(2.6, 3.2, 0.35) 5 @(-1.6, 22.7, -2.35) @(0, 0, -20)
Add-Cube "Body" "vest_right_lapel" @(0.3, 21.2, -2.55) @(2.6, 3.2, 0.35) 5 @(1.6, 22.7, -2.35) @(0, 0, 20)
Add-Cube "Body" "red_neck_cloth" @(-1.15, 23.2, -2.7) @(2.3, 2.4, 0.55) 6
Add-Cube "Body" "red_neck_tail" @(-0.75, 21.1, -2.62) @(1.5, 2.3, 0.4) 6 @(0, 22.2, -2.4) @(0, 0, 12)
Add-Cube "Body" "belt" @(-4.25, 13.6, -2.25) @(8.5, 1.5, 4.5) 9
Add-Cube "Body" "belt_buckle" @(-0.9, 13.75, -2.65) @(1.8, 1.2, 0.45) 15
foreach ($x in @(-1.65, 1.65)) {
    foreach ($y in @(17.2, 19.4, 21.6)) {
        Add-Cube "Body" "vest_button" @(($x - 0.18), $y, -2.7) @(0.36, 0.36, 0.28) 15
    }
}
Add-Cube "Body" "waist_chain_a" @(2.7, 12.4, -2.55) @(0.25, 2.0, 0.25) 15 @(2.8, 13.3, -2.4) @(0, 0, -12)
Add-Cube "Body" "waist_chain_b" @(3.25, 11.8, -2.45) @(0.22, 2.2, 0.22) 15 @(3.35, 12.8, -2.35) @(0, 0, 10)

# Face and layered short brown hair.
Add-Cube "Head" "head" @(-4, 25, -4) @(8, 8, 8) 0
Add-Cube "Head" "hair_cap" @(-4.25, 30.1, -4.25) @(8.5, 3.4, 8.5) 1
Add-Cube "Head" "hair_back" @(-4.15, 27.1, 3.55) @(8.3, 4.8, 0.75) 2
Add-Cube "Head" "hair_left" @(-4.35, 27.1, -4.05) @(1.35, 4.6, 7.8) 1
Add-Cube "Head" "hair_right" @(3.0, 27.1, -4.05) @(1.35, 4.6, 7.8) 1
Add-Cube "Head" "fringe_left" @(-3.65, 28.7, -4.4) @(3.2, 3.4, 0.55) 1 @(-2.0, 30.4, -4.1) @(0, 0, -18)
Add-Cube "Head" "fringe_right" @(-0.35, 28.9, -4.42) @(3.45, 3.2, 0.55) 2 @(1.35, 30.5, -4.1) @(0, 0, 20)
Add-Cube "Head" "left_eye" @(-2.35, 27.75, -4.15) @(1.35, 0.55, 0.25) 14
Add-Cube "Head" "right_eye" @(1.0, 27.75, -4.15) @(1.35, 0.55, 0.25) 14
Add-Cube "Head" "nose_shadow" @(-0.2, 26.8, -4.12) @(0.4, 0.35, 0.2) 0

# Thin luminous octagonal halo above the head.
Add-Cube "Halo" "halo_front" @(-2.8, 35.35, -4.25) @(5.6, 0.35, 0.35) 11
Add-Cube "Halo" "halo_back" @(-2.8, 35.35, 3.9) @(5.6, 0.35, 0.35) 11
Add-Cube "Halo" "halo_left" @(-4.25, 35.35, -2.8) @(0.35, 0.35, 5.6) 11
Add-Cube "Halo" "halo_right" @(3.9, 35.35, -2.8) @(0.35, 0.35, 5.6) 11
Add-Cube "Halo" "halo_front_left" @(-4.0, 35.35, -3.8) @(2.1, 0.35, 0.35) 11 @(-3.0, 35.5, -3.7) @(0, 45, 0)
Add-Cube "Halo" "halo_front_right" @(1.9, 35.35, -3.8) @(2.1, 0.35, 0.35) 11 @(3.0, 35.5, -3.7) @(0, -45, 0)
Add-Cube "Halo" "halo_back_left" @(-4.0, 35.35, 3.45) @(2.1, 0.35, 0.35) 11 @(-3.0, 35.5, 3.6) @(0, -45, 0)
Add-Cube "Halo" "halo_back_right" @(1.9, 35.35, 3.45) @(2.1, 0.35, 0.35) 11 @(3.0, 35.5, 3.6) @(0, 45, 0)

# White sleeves, dark shoulder patch and black gloves.
Add-Cube "RightArm" "right_sleeve" @(-7, 15, -2) @(4, 9, 4) 3
Add-Cube "RightArm" "right_shoulder_patch" @(-7.25, 20.2, -2.15) @(0.5, 3.1, 4.3) 4
Add-Cube "RightArm" "right_glove" @(-7.05, 11.5, -2.05) @(4.1, 3.7, 4.1) 10
Add-Cube "LeftArm" "left_sleeve" @(3, 15, -2) @(4, 9, 4) 3
Add-Cube "LeftArm" "left_shoulder_patch" @(6.75, 20.2, -2.15) @(0.5, 3.1, 4.3) 4
Add-Cube "LeftArm" "left_glove" @(2.95, 11.5, -2.05) @(4.1, 3.7, 4.1) 10

# Asymmetric red-black trousers and polished black boots from the reference silhouette.
Add-Cube "RightLeg" "right_trouser" @(-4, 4.5, -2) @(4, 9.5, 4) 8
Add-Cube "RightLeg" "right_trouser_red_panel" @(-2.0, 7.0, -2.2) @(2.0, 7.0, 0.4) 7
Add-Cube "RightLeg" "right_boot" @(-4.1, 0, -2.35) @(4.2, 5.0, 4.7) 9
Add-Cube "LeftLeg" "left_trouser" @(0, 4.5, -2) @(4, 9.5, 4) 7
Add-Cube "LeftLeg" "left_trouser_shadow" @(2.4, 4.5, -2.2) @(1.6, 9.5, 0.4) 8
Add-Cube "LeftLeg" "left_boot" @(-0.1, 0, -2.35) @(4.2, 5.0, 4.7) 9

# Layered abstract Sankta wings: pale primary plates and warm shadow plates behind the shoulders.
Add-Cube "RightWing" "right_wing_upper" @(-10.2, 22.0, 2.1) @(6.5, 1.1, 0.55) 12 @(-3.8, 22.8, 2.2) @(0, -8, -16)
Add-Cube "RightWing" "right_wing_middle" @(-9.6, 19.8, 2.2) @(5.8, 1.0, 0.5) 12 @(-3.8, 22.0, 2.2) @(0, -10, -32)
Add-Cube "RightWing" "right_wing_lower" @(-8.4, 18.0, 2.3) @(4.6, 0.9, 0.45) 13 @(-3.8, 21.0, 2.3) @(0, -12, -48)
Add-Cube "RightWing" "right_wing_tip" @(-10.9, 24.1, 2.0) @(4.4, 0.75, 0.45) 13 @(-6.7, 24.2, 2.1) @(0, -5, 12)
Add-Cube "LeftWing" "left_wing_upper" @(3.7, 22.0, 2.1) @(6.5, 1.1, 0.55) 12 @(3.8, 22.8, 2.2) @(0, 8, 16)
Add-Cube "LeftWing" "left_wing_middle" @(3.8, 19.8, 2.2) @(5.8, 1.0, 0.5) 12 @(3.8, 22.0, 2.2) @(0, 10, 32)
Add-Cube "LeftWing" "left_wing_lower" @(3.8, 18.0, 2.3) @(4.6, 0.9, 0.45) 13 @(3.8, 21.0, 2.3) @(0, 12, 48)
Add-Cube "LeftWing" "left_wing_tip" @(6.5, 24.1, 2.0) @(4.4, 0.75, 0.45) 13 @(6.7, 24.2, 2.1) @(0, 5, -12)

# Conservative back equipment silhouette visible over the right shoulder; no unsupported markings.
Add-Cube "BackEquipment" "back_case" @(2.4, 20.0, 2.4) @(3.2, 7.0, 1.8) 9 @(4.0, 23.5, 3.3) @(10, 0, -12)
Add-Cube "BackEquipment" "back_rod_left" @(-4.8, 1.5, 2.3) @(0.35, 14.0, 0.35) 9 @(-4.6, 8.5, 2.5) @(0, 0, 8)
Add-Cube "BackEquipment" "back_rod_right" @(-3.8, 1.2, 2.5) @(0.3, 13.5, 0.3) 9 @(-3.6, 8.0, 2.6) @(0, 0, 5)

$idle = New-Animation "idle" "loop" 3 @(
    (New-Track "Body" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 1.5 @(1.2, 0, 0)), (New-Key 3 @(0, 0, 0)))),
    (New-Track "Body" "position" @((New-Key 0 @(0, 0, 0)), (New-Key 1.5 @(0, 0.12, 0)), (New-Key 3 @(0, 0, 0)))),
    (New-Track "Head" "rotation" @((New-Key 0 @(0, -3, 0)), (New-Key 1.5 @(-1, 4, 0)), (New-Key 3 @(0, -3, 0)))),
    (New-Track "RightWing" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 1.5 @(0, -2, -5)), (New-Key 3 @(0, 0, 0)))),
    (New-Track "LeftWing" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 1.5 @(0, 2, 5)), (New-Key 3 @(0, 0, 0))))
)
$forward = New-Animation "forward" "loop" 1 @(
    (New-Track "Body" "position" @((New-Key 0 @(0, 0, 0)), (New-Key 0.5 @(0, 0.25, 0)), (New-Key 1 @(0, 0, 0)))),
    (New-Track "RightArm" "rotation" @((New-Key 0 @(28, 0, 0)), (New-Key 0.5 @(-28, 0, 0)), (New-Key 1 @(28, 0, 0)))),
    (New-Track "LeftArm" "rotation" @((New-Key 0 @(-28, 0, 0)), (New-Key 0.5 @(28, 0, 0)), (New-Key 1 @(-28, 0, 0)))),
    (New-Track "RightLeg" "rotation" @((New-Key 0 @(-30, 0, 0)), (New-Key 0.5 @(30, 0, 0)), (New-Key 1 @(-30, 0, 0)))),
    (New-Track "LeftLeg" "rotation" @((New-Key 0 @(30, 0, 0)), (New-Key 0.5 @(-30, 0, 0)), (New-Key 1 @(30, 0, 0)))),
    (New-Track "RightWing" "rotation" @((New-Key 0 @(0, 0, -4)), (New-Key 0.5 @(0, 0, 2)), (New-Key 1 @(0, 0, -4)))),
    (New-Track "LeftWing" "rotation" @((New-Key 0 @(0, 0, 4)), (New-Key 0.5 @(0, 0, -2)), (New-Key 1 @(0, 0, 4))))
)
$attack = New-Animation "attack" "once" 0.8 @(
    (New-Track "Body" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 0.2 @(0, 18, -3)), (New-Key 0.4 @(3, -24, 5)), (New-Key 0.8 @(0, 0, 0)))),
    (New-Track "RightArm" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 0.2 @(35, 25, -55)), (New-Key 0.4 @(-90, -25, 25)), (New-Key 0.8 @(0, 0, 0)))),
    (New-Track "LeftArm" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 0.2 @(-25, -8, 24)), (New-Key 0.4 @(-40, 12, 30)), (New-Key 0.8 @(0, 0, 0)))),
    (New-Track "RightWing" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 0.3 @(0, -8, -18)), (New-Key 0.8 @(0, 0, 0)))),
    (New-Track "LeftWing" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 0.3 @(0, 8, 18)), (New-Key 0.8 @(0, 0, 0))))
)
$extra = New-Animation "extra0" "once" 2 @(
    (New-Track "Body" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 0.45 @(-4, 0, 0)), (New-Key 1.65 @(-4, 0, 0)), (New-Key 2 @(0, 0, 0)))),
    (New-Track "Head" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 0.45 @(-5, -8, -2)), (New-Key 1.65 @(-5, -8, -2)), (New-Key 2 @(0, 0, 0)))),
    (New-Track "RightArm" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 0.45 @(68, 12, -42)), (New-Key 1.65 @(68, 12, -42)), (New-Key 2 @(0, 0, 0)))),
    (New-Track "RightWing" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 0.45 @(0, -12, -24)), (New-Key 1.65 @(0, -12, -24)), (New-Key 2 @(0, 0, 0)))),
    (New-Track "LeftWing" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 0.45 @(0, 12, 24)), (New-Key 1.65 @(0, 12, 24)), (New-Key 2 @(0, 0, 0)))),
    (New-Track "Halo" "rotation" @((New-Key 0 @(0, 0, 0)), (New-Key 1 @(0, 18, 0)), (New-Key 2 @(0, 0, 0))))
)

$geometryBones = foreach ($bone in $script:bones) {
    $value = [ordered]@{ name = $bone.name; pivot = $bone.pivot }
    if ($bone.Contains("parent")) { $value.parent = $bone.parent }
    if ($bone.cubes.Count -gt 0) { $value.cubes = $bone.cubes.ToArray() }
    $value
}
$geometry = [ordered]@{
    format_version = "1.12.0"
    "minecraft:geometry" = @([ordered]@{
        description = [ordered]@{
            identifier = "geometry.sankta_formal_resident"
            texture_width = 16
            texture_height = 16
            visible_bounds_width = 3
            visible_bounds_height = 3.5
            visible_bounds_offset = @(0, 1.35, 0)
        }
        bones = @($geometryBones)
    })
}

$allAnimations = @($idle, $forward, $attack, $extra)
$runtimeAnimations = [ordered]@{}
foreach ($animation in $allAnimations) {
    $runtimeAnimations[$animation.name] = Convert-ToBedrockAnimation $animation
}
$mainAnimations = [ordered]@{ format_version = "1.8.0"; animations = [ordered]@{} }
foreach ($animation in @($idle, $forward, $attack)) {
    $mainAnimations.animations[$animation.name] = Convert-ToBedrockAnimation $animation
}
$extraAnimations = [ordered]@{ format_version = "1.8.0"; animations = [ordered]@{ extra0 = (Convert-ToBedrockAnimation $extra) } }
$runtimeAnimationFile = [ordered]@{ format_version = "1.8.0"; animations = $runtimeAnimations }

$blockbenchAnimations = foreach ($animation in $allAnimations) {
    Convert-ToBlockbenchAnimation $animation
}
$blockbench = [ordered]@{
    meta = [ordered]@{
        format_version = "5.0"
        model_format = "bedrock"
        box_uv = $false
    }
    name = "sankta_formal_resident"
    model_identifier = "sankta_formal_resident"
    visible_box = @(3, 3.5, 0)
    variable_placeholders = ""
    elements = $script:elements.ToArray()
    groups = $script:groups.ToArray()
    outliner = @((New-OutlinerNode "Root"))
    textures = @([ordered]@{
        name = "default.png"
        folder = "textures"
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
        uuid = New-StableId
        relative_path = "textures/default.png"
        uv_width = 16
        uv_height = 16
    })
    animations = @($blockbenchAnimations)
    animation_variable_placeholders = ""
}

$ysm = [ordered]@{
    spec = 2
    metadata = [ordered]@{
        name = "萨科塔礼服居民"
        tips = "带独立光环与双翼骨骼的红黑礼服萨科塔人物模型"
        license = [ordered]@{ type = "All Rights Reserved" }
        authors = @([ordered]@{ name = "Zinecraft Project"; role = "模型制作" })
    }
    properties = [ordered]@{
        height_scale = 1.0
        width_scale = 1.0
        extra_animation = [ordered]@{ extra0 = "" }
        extra_animation_buttons = @()
        extra_animation_classify = @()
        preview_animation = "idle"
        disable_preview_rotation = $false
        gui_no_lighting = $false
        default_texture = "default"
        render_layers_first = $false
        free = $true
    }
    files = [ordered]@{
        player = [ordered]@{
            model = [ordered]@{ main = "models/main.json"; arm = "" }
            animation = [ordered]@{
                main = "animations/main.animation.json"
                arm = ""
                extra = "animations/extra.animation.json"
                tac = ""
                carryon = ""
                parcool = ""
                swem = ""
                slashblade = ""
                tlm = ""
                fp_arm = ""
            }
            animation_controllers = @()
            texture = @("textures/default.png")
        }
        projectiles = @()
        vehicles = @()
        sound_path = "sounds"
        function_path = "functions"
    }
}
$runtimeYsm = $ysm | ConvertTo-Json -Depth 100 | ConvertFrom-Json
$runtimeYsm.files.player.animation.main = "animations/entity.animation.json"
$runtimeYsm.files.player.animation.extra = ""

$outputRoot = [System.IO.Path]::GetFullPath((Join-Path (Get-Location) $OutputDirectory))
$runtimeRoot = [System.IO.Path]::GetFullPath((Join-Path (Get-Location) $RuntimeDirectory))

Write-Json (Join-Path $outputRoot "sankta_formal_resident.bbmodel") $blockbench
Write-Json (Join-Path $outputRoot "models/main.json") $geometry
Write-Json (Join-Path $outputRoot "animations/main.animation.json") $mainAnimations
Write-Json (Join-Path $outputRoot "animations/extra.animation.json") $extraAnimations
Write-Json (Join-Path $outputRoot "ysm.json") $ysm
Write-PaletteTexture (Join-Path $outputRoot "textures/default.png")

Write-Json (Join-Path $runtimeRoot "models/main.json") $geometry
Write-Json (Join-Path $runtimeRoot "animations/entity.animation.json") $runtimeAnimationFile
Write-Json (Join-Path $runtimeRoot "ysm.json") $runtimeYsm
Write-PaletteTexture (Join-Path $runtimeRoot "textures/default.png")

Write-Output "Generated $($script:elements.Count) cubes, $($script:groups.Count) bones, and $($allAnimations.Count) animations."
