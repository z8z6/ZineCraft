import os
from PIL import Image
from pathlib import Path

def make_square_png(input_path: str, output_path: str):
    """
    将 PNG 图片调整为正方形，透明填充，保持原图比例
    """
    try:
        # 打开图片并确保是 RGBA 透明模式
        with Image.open(input_path).convert("RGBA") as img:
            width, height = img.size
            # 取最大边作为正方形边长
            square_size = max(width, height)

            # 创建正方形透明底图
            square_img = Image.new("RGBA", (square_size, square_size), (0, 0, 0, 0))

            # 计算居中位置
            paste_x = (square_size - width) // 2
            paste_y = (square_size - height) // 2

            # 将原图贴到正方形中心
            square_img.paste(img, (paste_x, paste_y), mask=img)

            # 保存
            square_img.save(output_path, "PNG")
            print(f"✅ 处理完成: {os.path.basename(input_path)}")

    except Exception as e:
        print(f"❌ 处理失败 {input_path}: {str(e)}")

def process_all_pngs(input_dir: str, output_dir: str):
    """
    批量处理目录下所有 PNG
    """
    # 确保输出目录存在
    os.makedirs(output_dir, exist_ok=True)

    # 遍历所有 PNG 文件
    input_path = Path(input_dir)
    for img_path in input_path.glob("*.png"):
        output_file = os.path.join(output_dir, img_path.name)
        make_square_png(str(img_path), output_file)

if __name__ == "__main__":
    # ====================== 你只需要改这里 ======================
    INPUT_FOLDER = "./tool"   # 你的原图目录
    OUTPUT_FOLDER = "./output" # 输出目录
    # ==========================================================

    print("开始批量处理 PNG 正方形化（透明填充）...\n")
    process_all_pngs(INPUT_FOLDER, OUTPUT_FOLDER)
    print("\n🎉 全部处理完成！")