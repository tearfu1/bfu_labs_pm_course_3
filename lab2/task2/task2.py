from PIL import Image
import sys



if len(sys.argv) != 2:
    print("usage: python script.py <path_to_image>")
    exit()

filename = sys.argv[1]
with Image.open(filename) as img:
    if img.mode != 'RGB':
        img = img.convert('RGB')

    pixels = img.load()
    total_r = 0
    total_g = 0
    total_b = 0

    for y in range(img.height):
        for x in range(img.width):
            r, g, b = pixels[x, y]
            total_r += r
            total_g += g
            total_b += b

    max_channel = max(total_r, total_g, total_b)

    if max_channel == total_r:
        dominant_color = 'Red'
    elif max_channel == total_g:
        dominant_color = 'Green'
    else:
        dominant_color = 'Blue'

    print(f"Dominant color: {dominant_color}")
