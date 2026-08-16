# 十九国居民贴图

十九国居民使用原版宽臂 `PlayerModel` 的 64×64 RGBA 皮肤格式。贴图是依据 PRTS
国家地理、社会、民族、气候与产业资料制作的原创像素化适配，不直接复制或重新分发官方立绘；《明日方舟》名称、设定与视觉识别归鹰角网络所有。

## 资料与设计映射

统一导航与国家范围以 [PRTS 泰拉地理一览](https://prts.wiki/w/泰拉大典:地理) 为准。

| 贴图                 | PRTS 资料                                   | 落地要素                         |
|--------------------|-------------------------------------------|------------------------------|
| `aegir.png`        | [阿戈尔](https://prts.wiki/w/泰拉大典:地理/其他#阿戈尔) | 深海、科学执政与封闭技术社会；白、海军蓝和青色科技服。  |
| `bolivar.png`      | [玻利瓦尔](https://prts.wiki/w/泰拉大典:地理/玻利瓦尔)  | 多索雷斯滨海城市、旅游与长期内战；青绿色轻便工作服。   |
| `higashi.png`      | [东](https://prts.wiki/w/泰拉大典:地理/东)        | 南北政权、神社与僧院文化；深色交领日常服。        |
| `durin.png`        | [杜林](https://prts.wiki/w/泰拉大典:生物#杜林)      | 地下城邦与先进科技；白灰工程服和护目镜。         |
| `columbia.png`     | [哥伦比亚](https://prts.wiki/w/泰拉大典:地理/哥伦比亚)  | 开拓、移民与多民族工业社会；耐磨拓荒夹克。        |
| `kazimierz.png`    | [卡西米尔](https://prts.wiki/w/泰拉大典:地理/卡西米尔)  | 骑士传统与商业城市；酒红色城市外套和金色饰边。      |
| `kazdel.png`       | [卡兹戴尔](https://prts.wiki/w/泰拉大典:地理/卡兹戴尔)  | 萨卡兹聚居地与佣兵传统；旧皮革、围巾与角部第二层。    |
| `laterano.png`     | [拉特兰](https://prts.wiki/w/拉特兰)            | 萨科塔社会、修道院与铳械教育；乳白红色服装和金色光环纹。 |
| `leithanien.png`   | [莱塔尼亚](https://prts.wiki/w/泰拉大典:地理/莱塔尼亚)  | 音乐教育、源石技艺和高塔城市；黑紫色工匠礼服。      |
| `rim_billiton.png` | [雷姆必拓](https://prts.wiki/w/泰拉大典:地理/雷姆必拓)  | 矿业与荒地聚落；橙色矿工服和矿灯帽。           |
| `minos.png`        | [米诺斯](https://prts.wiki/w/泰拉大典:地理/米诺斯)    | 丰蹄主体民族与英雄文化；白铜色牧民日常服。        |
| `sargon.png`       | [萨尔贡](https://prts.wiki/w/泰拉大典:地理/萨尔贡)    | 沙漠、绿洲、荒野城镇与商队；赭色遮阳长衣。        |
| `sami.png`         | [萨米](https://prts.wiki/w/泰拉大典:地理/萨米)      | 北方寒地与部族聚落；浅蓝色毛边猎装。           |
| `victoria.png`     | [维多利亚](https://prts.wiki/w/泰拉大典:地理/维多利亚)  | 工业、城市和多元民间文化；深绿色工业夹克与工帽。     |
| `ursus.png`        | [乌萨斯](https://prts.wiki/w/泰拉大典:地理/乌萨斯)    | 寒冷气候、重工业与矿场；红棕色厚冬装。          |
| `kjerag.png`       | [谢拉格](https://prts.wiki/w/泰拉大典:地理/谢拉格)    | 雪山、三族会议与蔓珠院；白青色毛边山地服。        |
| `siracusa.png`     | [叙拉古](https://prts.wiki/w/叙拉古)            | 城镇家族社会与法官体系；炭黑色城市正装。         |
| `yan.png`          | [炎](https://prts.wiki/w/泰拉大典:地理/炎)        | 炎国城市与传统文化；红黑色交领工匠服。          |
| `iberia.png`       | [伊比利亚](https://prts.wiki/w/泰拉大典:地理/伊比利亚)  | 海岸城镇、大静谧后的衰落与审判庭秩序；黑金色耐候长外套。 |

## 生成与接入

- 概念表：[nation_resident_concept_sheet.png](assets/nation_resident_concept_sheet.png)。使用内置 `imagegen`
  生成，提示词要求十九名匿名居民按固定国家顺序排列，并明确不得复制具名干员、不得携带武器或标志。
- 实际皮肤正面预览：[nation_resident_skin_preview.png](assets/nation_resident_skin_preview.png)。该图由生成脚本从最终 UV
  图集确定性拼合，用于快速审核。
- 最终皮肤由 `script/generate_nation_resident_skins.py` 确定性生成；脚本只使用资料映射后的颜色和服装要素，不需要网络。
- 游戏资源位于 `assets/zinecraft/textures/entity/nation_resident/<nation>.png`。
- 通用居民 renderer 根据 `NationAffiliated#getNation()` 选择贴图；拉特兰专用实体固定使用同目录的 `laterano.png`。

重新生成后必须运行资源覆盖测试，确保 19 个 `TerraNation` ID 均有一张 64×64、带 Alpha 通道的贴图。
