# 泰拉生物模型与素材记录

## 范围

本批次实现四种生物：沙地兽、裂兽、钳兽、驮兽。用户提供的参考目录为
`E:\project\asset\生物`；其中“驮兽”的目录名写作“驼兽”，注册名依据 PRTS 使用“驮兽”。

## 设定依据

- 沙地兽：PRTS「泰拉大典:百科/概念」与「沙地兽」。仅采用“栖息于沙漠和岩漠、胆小、通常无攻击性、夜间觅食”等已公开描述。
- 裂兽：PRTS「泰拉大典:泰拉生态调查报告」。仅采用凶猛肉食、利爪獠牙、裂毛保护柔软部位、群居等已公开描述。
- 钳兽：PRTS「训练用钳兽」。仅采用卡西米尔长河大湖常见感染生物、近战、甲壳防御等条目信息。
- 驮兽：PRTS「放归驮兽」。仅采用野生动物、体型厚重、可被驯养等条目信息。

参考链接：

- https://prts.wiki/w/泰拉大典:百科/概念
- https://prts.wiki/w/沙地兽
- https://prts.wiki/w/泰拉大典:泰拉生态调查报告
- https://prts.wiki/w/训练用钳兽
- https://prts.wiki/w/放归驮兽

## 逐文件来源与权利说明

| 文件                                                      | 来源                                | 处理方式                 | 权利说明                       |
|---------------------------------------------------------|-----------------------------------|----------------------|----------------------------|
| `assets/zinecraft/blockbench/entity/sandbeast.bbmodel`  | 用户提供的 `沙地兽/armored_beast.bbmodel` | 原样保留为 Blockbench 工程源 | 来源与授权范围由素材提供者确认；项目不主张原素材权利 |
| `assets/zinecraft/textures/entity/sandbeast.png`        | 上述工程内嵌贴图                          | 无损提取为运行时贴图           | 同上                         |
| `assets/zinecraft/blockbench/entity/rivenbeast.bbmodel` | 用户提供的 `裂兽/1.png`                  | 依据轮廓、裂毛、爪牙和配色重新制作    | 项目原创模型与贴图；参考图权利归原权利人       |
| `assets/zinecraft/textures/entity/rivenbeast.png`       | 用户提供的 `裂兽/1.png`                  | 原版低分辨率 UV 贴图重新绘制     | 同上                         |
| `assets/zinecraft/blockbench/entity/clampbeast.bbmodel` | 用户提供的 `钳兽/1.png`                  | 依据六足、甲壳和双钳轮廓重新制作     | 项目原创模型与贴图；参考图权利归原权利人       |
| `assets/zinecraft/textures/entity/clampbeast.png`       | 用户提供的 `钳兽/1.png`                  | 原版低分辨率 UV 贴图重新绘制     | 同上                         |
| `assets/zinecraft/blockbench/entity/packbeast.bbmodel`  | 用户提供的 `驼兽/1.png`                  | 依据厚重躯干、背峰和鼻角轮廓重新制作   | 项目原创模型与贴图；参考图权利归原权利人       |
| `assets/zinecraft/textures/entity/packbeast.png`        | 用户提供的 `驼兽/1.png`                  | 原版低分辨率 UV 贴图重新绘制     | 同上                         |

生成脚本 `script/generate_terra_creature_assets.js` 可重建三套原创贴图与 Blockbench
工程，并从用户提供的沙地兽工程中无损提取运行时贴图。运行发布构建前，应确保素材提供者已确认沙地兽工程及参考图的使用范围。
