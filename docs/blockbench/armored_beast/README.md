# 岩甲兽 Blockbench 资产

以用户提供的参考图为轮廓和配色依据制作的低多边形四足生物。模型前方为 `-Z`，脚底位于 `Y=0`。

## 文件

- `armored_beast.bbmodel`：Blockbench 5.0 通用模型工程，包含骨骼、UV 和动作。
- `armored_beast.png`：256×256 像素材质，包含暗色皮肤、棕色长毛、砂岩甲片、浅色晶体、口鼻、爪与眼睛区域。
- `SOURCE.md`：参考图和生成过程记录。

## 动作

- `animation.armored_beast.idle`：2 秒循环呼吸与摆尾。
- `animation.armored_beast.walk`：1.2 秒循环四足对角步态。
- `animation.armored_beast.attack`：0.9 秒一次性蓄力撞击/顶击。
- `animation.armored_beast.hurt`：0.5 秒一次性侧向受击回弹。

## 重新生成

在仓库根目录运行：

```powershell
./script/blockbench/generate_armored_beast.ps1 -SourceTexture <材质母板 PNG>
```

脚本只重建本目录中的 `.bbmodel` 与 256×256 PNG，不接触实体注册代码。
