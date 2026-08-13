from PIL import Image
import os
from collections import Counter

SRC_DIR = "item"
DST_DIR = "16x16"
TARGET_SIZE = 32

# 可调节参数
N_COLORS = 32               # 量化颜色数（越小边缘越统一，但可能损失内部细节）
TRANSPARENCY_THRESHOLD = 128   # 透明度硬阈值

def edge_color_normalize(img):
    """
    将边缘像素（与透明相邻的不透明像素）的颜色归一化为邻域内最常见的颜色。
    只修改颜色，不改变透明度。
    """
    data = img.load()
    w, h = img.size

    # 1. 标记边缘像素
    is_edge = [[False] * w for _ in range(h)]
    for y in range(h):
        for x in range(w):
            r, g, b, a = data[x, y]
            if a == 0:
                continue
            # 检查四邻域是否有透明像素
            for dx, dy in [(-1,0), (1,0), (0,-1), (0,1)]:
                nx, ny = x + dx, y + dy
                if 0 <= nx < w and 0 <= ny < h and data[nx, ny][3] == 0:
                    is_edge[y][x] = True
                    break

    # 2. 备份原始颜色（避免读取被修改后的值）
    original = [[data[x, y] for x in range(w)] for y in range(h)]

    # 3. 对每个边缘像素，用邻域不透明像素的众数颜色替换
    for y in range(h):
        for x in range(w):
            if not is_edge[y][x]:
                continue
            neighbor_colors = []
            for dy in range(-1, 2):
                for dx in range(-1, 2):
                    if dx == 0 and dy == 0:
                        continue
                    nx, ny = x + dx, y + dy
                    if 0 <= nx < w and 0 <= ny < h:
                        cr, cg, cb, ca = original[ny][nx]
                        if ca != 0:   # 只取不透明像素
                            neighbor_colors.append((cr, cg, cb))
            if neighbor_colors:
                # 取众数（出现次数最多的颜色）
                most_common = Counter(neighbor_colors).most_common(1)[0][0]
                data[x, y] = (most_common[0], most_common[1], most_common[2], 255)
    return img

os.makedirs(DST_DIR, exist_ok=True)

for name in os.listdir(SRC_DIR):
    if not name.lower().endswith(".png"):
        continue

    src_path = os.path.join(SRC_DIR, name)
    dst_path = os.path.join(DST_DIR, name)

    # 1. 打开并缩放（LANCZOS 保持内部平滑）
    img = Image.open(src_path).convert("RGBA")
    img = img.resize((TARGET_SIZE, TARGET_SIZE), Image.Resampling.LANCZOS)

    # 2. 透明度硬阈值（去除半透明边缘，但保留不透明/透明二值状态）
    data = img.load()
    w, h = img.size
    for y in range(h):
        for x in range(w):
            r, g, b, a = data[x, y]
            if a < TRANSPARENCY_THRESHOLD:
                data[x, y] = (0, 0, 0, 0)
            else:
                data[x, y] = (r, g, b, 255)

    # 3. 颜色量化（合并相近颜色，使区域内部统一）
    img = img.quantize(colors=N_COLORS, method=Image.Quantize.FASTOCTREE).convert("RGBA")

    # 4. 边缘颜色归一化（统一边缘杂色，强化轮廓）
    img = edge_color_normalize(img)

    img.save(dst_path)

print(f"完成！量化色数={N_COLORS}，边缘颜色已归一化，轮廓更清晰。")