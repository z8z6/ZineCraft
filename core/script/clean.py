from PIL import Image, ImageFilter
import os

SRC_DIR = "item"
DST_DIR = "16x16"
TARGET_SIZE = 16

os.makedirs(DST_DIR, exist_ok=True)

for name in os.listdir(SRC_DIR):
    if not name.lower().endswith(".png"):
        continue

    src_path = os.path.join(SRC_DIR, name)
    dst_path = os.path.join(DST_DIR, name)

    img = Image.open(src_path).convert("RGBA")

    # 步骤1：LANCZOS 下采样（保留内部纹理细节）
    img = img.resize((TARGET_SIZE, TARGET_SIZE), Image.Resampling.LANCZOS)

    # 步骤2：可选轻微锐化（增强内部清晰度，但可能加重边缘噪点，可关闭）
    # img = img.filter(ImageFilter.UnsharpMask(radius=0.5, percent=50, threshold=0))

    # 步骤3：强制硬边缘 —— 清除半透明像素
    data = img.load()
    w, h = img.size
    for y in range(h):
        for x in range(w):
            r, g, b, a = data[x, y]
            # 阈值设为 128：低于此值的像素变全透明，否则变完全不透明
            if a < 128:
                data[x, y] = (0, 0, 0, 0)
            else:
                # 也可以保留原始 RGB，或者做一次简单去浅色处理（见下文）
                data[x, y] = (r, g, b, 255)

    # 可选：移除边缘残留的浅色像素（例如背景色是白色时）
    # 可以通过判断 RGB 是否接近背景色来清理，但会引入额外复杂度
    # 简单方法：对完全不透明的像素，如果其颜色非常浅（如 R+G+B > 700），可设为透明或替换
    # 但谨慎使用，可能会误伤内部亮色

    img.save(dst_path)

print("批量转换完成：LANCZOS 缩放 + 透明度硬阈值，边缘清晰且内部细节丰富。")