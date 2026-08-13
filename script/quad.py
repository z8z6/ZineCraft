from PIL import Image, ImageFilter
import os

SRC_DIR = "item"
DST_DIR = "16x16"
TARGET_SIZE = 16

# 可调节参数
N_COLORS = 16          # 颜色数量：越小区域合并越强，边界越清晰；越大保留细节越多
TRANSPARENCY_THRESHOLD = 128   # 透明度阈值：低于此值的像素变全透明

os.makedirs(DST_DIR, exist_ok=True)

for name in os.listdir(SRC_DIR):
    if not name.lower().endswith(".png"):
        continue

    src_path = os.path.join(SRC_DIR, name)
    dst_path = os.path.join(DST_DIR, name)

    # 1. 打开并转为RGBA
    img = Image.open(src_path).convert("RGBA")

    # 2. LANCZOS 高质量缩放（保留平滑过渡）
    img = img.resize((TARGET_SIZE, TARGET_SIZE), Image.Resampling.LANCZOS)

    # 3. 可选轻微锐化（可关闭）
    # img = img.filter(ImageFilter.UnsharpMask(radius=0.5, percent=50, threshold=0))

    # 4. 先对透明度做硬阈值，消除半透明边缘（避免量化时产生黑色噪点）
    data = img.load()
    w, h = img.size
    for y in range(h):
        for x in range(w):
            r, g, b, a = data[x, y]
            if a < TRANSPARENCY_THRESHOLD:
                data[x, y] = (0, 0, 0, 0)   # 完全透明
            else:
                data[x, y] = (r, g, b, 255) # 完全不透明

    # 5. 颜色量化（合并相近颜色，使区域颜色统一，边界变清晰）
    #    FASTOCTREE 支持 RGBA，且速度较快。N_COLORS 控制合并强度。
    #    注意：量化后可能会丢失一些渐变，但能产生清晰的色块边界。
    img = img.quantize(colors=N_COLORS, method=Image.Quantize.FASTOCTREE).convert("RGBA")

    img.save(dst_path)

print(f"完成：LANCZOS缩放 + 透明度硬阈值 + {N_COLORS}色量化")