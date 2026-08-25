# 菲林维多利亚居民 YSM 模型

这是按用户提供的服饰效果图制作的原创低多边形 YSM/Bedrock 模型，重点保留猫耳、盘发、长尾、奶油色泡袖、深色束腰、腰带和分层长裙等视觉特征。

## 内容

- `feline_victorian_resident.bbmodel`：可在 Blockbench 中继续编辑的源工程。
- `models/main.json`：YSM 使用的 Bedrock 1.12 几何模型。
- `textures/default.png`：16×16 整数 UV 调色板纹理。
- `animations/main.animation.json`：`idle`、`walk` 与 `attack` 动画。
- `animations/extra.animation.json`：`extra0` 抚胸致意动作。
- `ysm.json`：YSM Utils spec 2 模型清单。

模型由 13 个骨骼和 50 个方块组成。面部采用大比例眼白与瞳孔，包含独立眼睛高光、眉毛和粉色腮红，不设置鼻子与嘴部；尾巴拆为
`Tail` 至 `Tail3` 四段，使待机、行走与攻击时能形成连续摆动。

## 使用

模型已随模组发布到 `assets/yes_steve_model/builtin/feline_victorian_resident`，并绑定实体
`zinecraft:feline_victorian_resident`；该实体会自然生成于 `zinecraft:victoria_misty_highlands`。

以下手动安装方式仅用于脱离 Zinecraft 单独预览模型：

将整个 `feline_victorian_resident` 文件夹复制到：

```text
config/yes_steve_model/custom/feline_victorian_resident
```

进入游戏后刷新或重新进入模型选择界面。若需修改，直接用 Blockbench 打开 `.bbmodel`；安装并重启 YSM Utils 后，可继续用其 YSM
工作流导出。

## 动画

- `idle`：呼吸、轻微转头、裙摆与四段尾巴缓动。
- `walk`：交替摆臂迈步、身体起伏、裙摆与尾巴随步态摆动。
- `attack`：0.8 秒非循环徒手挥击，包含蓄力、命中与收招，身体、双臂、腿部、裙摆和尾巴联动。
- `extra0`：右手抬至胸前，头部轻偏并停留后复位，对应参考图姿态。
