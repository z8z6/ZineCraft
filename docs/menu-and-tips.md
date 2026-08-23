# 主界面与加载提示

## 明日方舟台词提示

Zinecraft 的自定义 Tips 位于：

```text
src/client/resources/assets/zinecraft/tips/
```

每条语录使用一个 Tips 21.1.3 的 `tipsmod:simple` JSON，标题为角色名，正文为游戏中文语音原文，默认展示 10 秒。

当前语录均核对自 PRTS Wiki 的角色语音记录：

- [阿米娅](https://prts.wiki/w/阿米娅/语音记录)
- [凯尔希](https://prts.wiki/w/凯尔希/语音记录)
- [银灰](https://prts.wiki/w/银灰/语音记录)
- [陈](https://prts.wiki/w/陈/语音记录)
- [能天使](https://prts.wiki/w/能天使/语音记录)
- [德克萨斯](https://prts.wiki/w/德克萨斯/语音记录)
- [史尔特尔](https://prts.wiki/w/史尔特尔/语音记录)
- [斯卡蒂](https://prts.wiki/w/斯卡蒂/语音记录)
- [塞雷娅](https://prts.wiki/w/塞雷娅/语音记录)

新增语录时复制任意 JSON，修改文件名、角色名和正文即可。文件名只能使用小写英文字母、数字和下划线。

## FancyMenu 多图背景

FancyMenu 3.9.7 支持多张图片轮播。其编辑器中的背景类型名为 `Slideshow`，可以先创建包含多张图片的轮播资源，再将该轮播设置为主菜单的全屏背景。

当前主界面使用 `zinecraft_main` 轮播：每 5 秒随机切换 5 张 16:9 背景，并以 2.5 倍淡入淡出速度过渡。源码配置位于：

```text
src/client-pack/config/fancymenu/
```

`installClientPackConfig` 会在准备开发客户端时将其安装到 `run/config/fancymenu/`；PCL 打包也会直接收集源码配置。主界面配置文件为 `customization/zinecraft_title_screen.txt`，轮播定义和图片位于 `slideshows/zinecraft_main/`。

五张图片来自用户提供的项目素材：罗德岛、泰拉主视图、莱塔尼亚双塔、炎国百灶和萨米星门。原图统一为 16:9，使用 JPEG 质量 84 压缩；其中泰拉主视图与双塔图仅进行居中横向裁切，不拉伸画面。
