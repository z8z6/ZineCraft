# 萨科塔礼服居民 YSM 模型

这是依据用户提供立绘制作的原创低多边形 YSM/Bedrock 人物模型。模型重点保留短棕发、白衬衫、深蓝双排扣马甲、红领巾、红黑长裤、黑色手套与长靴，并将萨科塔光环和背后双翼做成独立骨骼。

## 内容

- `sankta_formal_resident.bbmodel`：可直接用 Blockbench 5.x 打开的源工程。
- `models/main.json`：YSM 使用的 Bedrock 1.12 几何模型。
- `textures/default.png`：16×16 整数 UV 调色板纹理。
- `animations/main.animation.json`：`idle`、`forward` 与 `attack` 动画。
- `animations/extra.animation.json`：`extra0` 抚胸致意、展开双翼并转动光环的展示动作。
- `ysm.json`：YSM Utils spec 2 模型清单。

模型包含 12 个骨骼和 58 个方块。基础人体使用 `Root`、`Body`、`Head`、双臂和双腿标准骨骼；光环使用独立 `Halo` 骨骼，双翼使用
`Wings`、`RightWing`、`LeftWing` 三级结构。光环由八段浅金色细方块围成水平八边形，双翼由左右各四片浅色板状羽片组成，在待机、行走、攻击与展示动作中均有轻微联动。

参考图只提供正面轮廓。背面采用无纹章的对称礼服结构，背部装备仅保留图中可见的黑色盒体与细杆剪影，没有补写未知标识或设定。

## 使用

模型已随模组发布到：

```text
assets/yes_steve_model/builtin/sankta_formal_resident
```

模型绑定实体 `zinecraft:sankta_formal_resident`。该和平生物会以 10–20 只的群体自然生成于
`zinecraft:laterano_holy_fields`，生成表面限于拉特兰冲积白垩或草方块；也可使用“萨科塔礼服居民刷怪蛋”生成。

若需脱离 Zinecraft 单独预览，可将本目录复制到：

```text
config/yes_steve_model/custom/sankta_formal_resident
```

使用 Blockbench 打开 `.bbmodel` 后，可以直接调整 `Halo`、`RightWing` 和 `LeftWing` 骨骼。运行以下命令可从脚本确定性重建源工程和内置
YSM 包：

```powershell
./script/blockbench/generate_sankta_formal_resident.ps1
```

## 动画

- `idle`：三秒呼吸循环，头部轻转，双翼小幅张合。
- `forward`：一秒循环步态，四肢交替摆动，双翼随步态稳定摆动。
- `attack`：0.8 秒挥击动作，身体转动并短暂展开双翼。
- `extra0`：两秒抚胸致意动作，同时展开双翼并使光环轻微转动。
