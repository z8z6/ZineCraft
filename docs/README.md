# Zinecraft 开发文档站

本目录是基于 Vite + React 的项目文档站。站点只读取 `docs/guides/` 中面向开发者的教程，不读取仓库 `prompt/` 内的归档 Markdown。

```powershell
npm install
npm run dev
npm run build
```

图鉴索引保存在 `src/data/catalog.json`。模组注册内容变化后，先运行数据生成，再刷新索引：

```powershell
..\gradlew.bat runData
npm run catalog
```

图鉴的物品、方块和生物图片直接从 `src/main/resources/assets/zinecraft/textures/` 打包，不维护重复贴图副本。

生产构建输出到 `docs/dist/`。
