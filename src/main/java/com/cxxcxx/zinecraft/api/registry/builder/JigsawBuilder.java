package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.StructureCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
import com.cxxcxx.zinecraft.api.world.structure.JigsawPoolBuilder;
import com.cxxcxx.zinecraft.api.world.structure.JigsawPoolDefinition;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

/**
 * Jigsaw 结构声明构建器，统一保存模板池、结构参数、放置策略、群系范围和动态注册键。
 */
public final class JigsawBuilder {
  public final String path;
  public final String zhCn;
  private final StructureCatalog catalog;
  private final List<JigsawPoolDefinition> mutablePools = new ArrayList<>();
  public final List<JigsawPoolDefinition> pools = Collections.unmodifiableList(mutablePools);
  public String enUs;
  private String startPool = "start";
  private int spacing = 32;
  private int separation = 8;
  private int salt;
  private int size = 3;
  private int maxDistanceFromCenter = 80;
  private float removeVinesChance;
  @Nullable
  private ResourceKey<Biome> biome;
  private List<ResourceKey<Biome>> allowedBiomes = List.of();
  private boolean unique;
  private int ringDistance;
  @Nullable
  private Types heightmap = Types.WORLD_SURFACE_WG;
  private int startHeight;
  private boolean useExpansionHack;
  private boolean fixedOrigin;
  private boolean naturalPlacement = true;
  private int fixedChunkX = -1;
  private int fixedChunkZ = -1;
  private Decoration generationStep = Decoration.SURFACE_STRUCTURES;
  private TerrainAdjustment terrainAdjustment = TerrainAdjustment.BEARD_THIN;
  @Nullable
  private ResourceKey<StructureProcessorList> processorKey;
  private Map<String, ResourceKey<StructureTemplatePool>> poolKeys = Map.of();
  @Nullable
  private ResourceKey<Structure> structureKey;
  @Nullable
  private ResourceKey<StructureSet> setKey;

  /**
   * 创建 Jigsaw 结构声明。
   *
   * @param catalog 接收该结构的目录
   * @param path    结构、结构集和模板池使用的基础路径
   * @param zhCn    结构的简体中文名称
   */
  public JigsawBuilder(StructureCatalog catalog, String path, String zhCn) {
    this.catalog = Objects.requireNonNull(catalog, "结构目录不能为空");
    this.path = Objects.requireNonNull(path, "结构 ID 不能为空");
    this.zhCn = Objects.requireNonNull(zhCn, "结构中文名不能为空：" + path);
    this.enUs = TranslationCatalog.toDisplayName(path);
    this.salt = path.hashCode();
  }

  /**
   * @param enUs 英文名称 @return 当前构建器
   */
  public JigsawBuilder enUs(String enUs) {
    this.enUs = Objects.requireNonNull(enUs, "结构英文名不能为空：" + path);
    return this;
  }

  /**
   * @param startPool 起始模板池名称 @return 当前构建器
   */
  public JigsawBuilder startPool(String startPool) {
    this.startPool = Objects.requireNonNull(startPool, "起始模板池不能为空：" + path);
    return this;
  }

  /**
   * 添加一个模板池。
   *
   * @param name       池名称
   * @param projection 模板投影方式
   * @param configure  模板和权重配置回调
   * @return 当前构建器
   */
  public JigsawBuilder pool(
      String name,
      Projection projection,
      Consumer<? super JigsawPoolBuilder> configure
  ) {
    if (name == null || name.isBlank()) throw new IllegalArgumentException("Jigsaw pool 名称不能为空：" + path);
    if (mutablePools.stream().anyMatch(pool -> pool.name().equals(name))) {
      throw new IllegalArgumentException("Jigsaw pool 重复：" + path + "/" + name);
    }
    JigsawPoolBuilder builder = new JigsawPoolBuilder(name, projection);
    Objects.requireNonNull(configure, "模板池配置不能为空：" + path + "/" + name).accept(builder);
    mutablePools.add(builder.build());
    return this;
  }

  /**
   * @param name 池名称 @param configure 模板配置回调 @return 当前构建器
   */
  public JigsawBuilder pool(String name, Consumer<? super JigsawPoolBuilder> configure) {
    return pool(name, Projection.RIGID, configure);
  }

  /**
   * @param spacing 平均间距 @param separation 最小间距 @param salt 随机盐值 @return 当前构建器
   */
  public JigsawBuilder randomSpread(int spacing, int separation, int salt) {
    this.spacing = spacing;
    this.separation = separation;
    this.salt = salt;
    this.unique = false;
    this.fixedOrigin = false;
    return this;
  }

  /**
   * @param size Jigsaw 展开深度 @param maxDistanceFromCenter 距中心最大距离 @return 当前构建器
   */
  public JigsawBuilder layout(int size, int maxDistanceFromCenter) {
    this.size = size;
    this.maxDistanceFromCenter = maxDistanceFromCenter;
    return this;
  }

  /**
   * @param chance 0 到 1 的藤蔓移除概率 @return 当前构建器
   */
  public JigsawBuilder removeVinesChance(float chance) {
    this.removeVinesChance = chance;
    return this;
  }

  /**
   * @param biome 结构限定群系 @return 当前构建器
   */
  public JigsawBuilder biome(@Nullable ResourceKey<Biome> biome) {
    this.biome = biome;
    this.allowedBiomes = biome == null ? List.of() : List.of(biome);
    return this;
  }

  /**
   * @param allowedBiomes 结构允许生成的群系列表 @return 当前构建器
   */
  public JigsawBuilder allowedBiomes(List<ResourceKey<Biome>> allowedBiomes) {
    this.allowedBiomes = List.copyOf(Objects.requireNonNull(allowedBiomes, "允许群系不能为空：" + path));
    return this;
  }

  /**
   * @param ringDistance 与世界原点的环距离 @return 当前构建器
   */
  public JigsawBuilder unique(int ringDistance) {
    this.unique = true;
    this.fixedOrigin = false;
    this.ringDistance = ringDistance;
    this.spacing = ringDistance + 1;
    this.separation = ringDistance;
    return this;
  }

  /**
   * @return 当前构建器，并将结构固定在世界原点区块
   */
  public JigsawBuilder fixedOrigin() {
    return fixedAt(-1, -1);
  }

  /**
   * @param chunkX 唯一生成区块 X
   * @param chunkZ 唯一生成区块 Z
   * @return 当前构建器，并将结构固定到指定区块
   */
  public JigsawBuilder fixedAt(int chunkX, int chunkZ) {
    this.fixedOrigin = true;
    this.unique = false;
    this.fixedChunkX = chunkX;
    this.fixedChunkZ = chunkZ;
    this.spacing = 2;
    this.separation = 1;
    return this;
  }

  /**
   * 仅注册可被城市 Piece 或 {@code /place structure} 引用的结构，不创建自然生成结构集。
   *
   * @return 当前构建器
   */
  public JigsawBuilder embedded() {
    this.naturalPlacement = false;
    this.unique = false;
    this.fixedOrigin = false;
    return this;
  }

  /**
   * @param heightmap 起始位置采用的高度图；为 {@code null} 时使用固定高度 @param startHeight 起始高度偏移 @return 当前构建器
   */
  public JigsawBuilder height(@Nullable Types heightmap, int startHeight) {
    this.heightmap = heightmap;
    this.startHeight = startHeight;
    return this;
  }

  /**
   * @param useExpansionHack 是否启用原版 Jigsaw 扩展修正 @return 当前构建器
   */
  public JigsawBuilder expansionHack(boolean useExpansionHack) {
    this.useExpansionHack = useExpansionHack;
    return this;
  }

  /**
   * @param generationStep 群系生成阶段 @param terrainAdjustment 地形适配方式 @return 当前构建器
   */
  public JigsawBuilder generation(Decoration generationStep, TerrainAdjustment terrainAdjustment) {
    this.generationStep = Objects.requireNonNull(generationStep, "结构生成阶段不能为空：" + path);
    this.terrainAdjustment = Objects.requireNonNull(terrainAdjustment, "结构地形适配不能为空：" + path);
    return this;
  }

  /**
   * @return 校验并登记后的当前构建器
   */
  public JigsawBuilder build() {
    if (structureKey != null) throw new IllegalStateException("Jigsaw builder 不能重复 build：" + path);
    return catalog.register(this);
  }

  /**
   * 绑定目录分配的动态注册键。
   *
   * @param processorKey 模板使用的处理器列表资源键
   * @param poolKeys     池名称到模板池资源键的映射
   * @param structureKey Jigsaw 结构资源键
   * @param setKey       控制结构放置的结构集资源键
   */
  public void bind(
      ResourceKey<StructureProcessorList> processorKey,
      Map<String, ResourceKey<StructureTemplatePool>> poolKeys,
      ResourceKey<Structure> structureKey,
      ResourceKey<StructureSet> setKey
  ) {
    this.processorKey = Objects.requireNonNull(processorKey, "processorKey");
    this.poolKeys = Collections.unmodifiableMap(new LinkedHashMap<>(poolKeys));
    this.structureKey = Objects.requireNonNull(structureKey, "structureKey");
    this.setKey = Objects.requireNonNull(setKey, "setKey");
  }

  /**
   * @return 起始模板池名称
   */
  public String startPool() {
    return startPool;
  }

  /**
   * @return 随机散布平均区块间距
   */
  public int spacing() {
    return spacing;
  }

  /**
   * @return 随机散布最小区块间距
   */
  public int separation() {
    return separation;
  }

  /**
   * @return 结构放置随机盐值
   */
  public int salt() {
    return salt;
  }

  /**
   * @return Jigsaw 展开深度
   */
  public int size() {
    return size;
  }

  /**
   * @return Jigsaw 距起点的最大方块距离
   */
  public int maxDistanceFromCenter() {
    return maxDistanceFromCenter;
  }

  /**
   * @return 模板中藤蔓被移除的概率
   */
  public float removeVinesChance() {
    return removeVinesChance;
  }

  /**
   * @return 放置偏好群系；未限定时为 {@code null}
   */
  @Nullable
  public ResourceKey<Biome> biome() {
    return biome;
  }

  /**
   * @return 结构本体允许生成和展开的群系
   */
  public List<ResourceKey<Biome>> allowedBiomes() {
    return allowedBiomes;
  }

  /**
   * @return 是否采用同心环唯一放置
   */
  public boolean unique() {
    return unique;
  }

  /**
   * @return 同心环唯一放置距离参数
   */
  public int ringDistance() {
    return ringDistance;
  }

  /**
   * @return 起始高度图；固定高度模式返回 {@code null}
   */
  @Nullable
  public Types heightmap() {
    return heightmap;
  }

  /**
   * @return 起始高度或相对高度图偏移
   */
  public int startHeight() {
    return startHeight;
  }

  /**
   * @return 是否启用原版 Jigsaw 扩展修正
   */
  public boolean useExpansionHack() {
    return useExpansionHack;
  }

  /**
   * @return 是否固定在世界原点区块
   */
  public boolean fixedOriginPlacement() {
    return fixedOrigin;
  }

  public boolean naturalPlacement() {
    return naturalPlacement;
  }

  public int fixedChunkX() {
    return fixedChunkX;
  }

  public int fixedChunkZ() {
    return fixedChunkZ;
  }

  /**
   * @return 结构参与的群系生成阶段
   */
  public Decoration generationStep() {
    return generationStep;
  }

  /**
   * @return 结构采用的地形适配方式
   */
  public TerrainAdjustment terrainAdjustment() {
    return terrainAdjustment;
  }

  /**
   * @return 目录分配的处理器列表资源键
   */
  public ResourceKey<StructureProcessorList> processorKey() {
    return Objects.requireNonNull(processorKey, "结构尚未 build：" + path);
  }

  /**
   * @return 池名称到目录分配模板池资源键的不可变映射
   */
  public Map<String, ResourceKey<StructureTemplatePool>> poolKeys() {
    return poolKeys;
  }

  /**
   * @return 目录分配的结构资源键
   */
  public ResourceKey<Structure> structureKey() {
    return Objects.requireNonNull(structureKey, "结构尚未 build：" + path);
  }

  /**
   * @return 目录分配的结构集资源键
   */
  public ResourceKey<StructureSet> setKey() {
    return Objects.requireNonNull(setKey, "结构尚未 build：" + path);
  }

  /**
   * @param catalog 待比较的结构目录 @return 当前构建器是否归该目录所有
   */
  public boolean belongsTo(StructureCatalog catalog) {
    return this.catalog == catalog;
  }
}
