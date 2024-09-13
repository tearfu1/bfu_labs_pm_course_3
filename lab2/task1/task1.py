from PIL import Image

filename = 'porsche718.jpg'
with Image.open(filename) as img:
    img.load()
    r, g, b = img.split()

    result_img_height = img.height * 2
    result_img_width = img.width * 4
    result_img = Image.new('RGB', (result_img_width, result_img_height))

    result_img.paste(img)
    result_img.paste(r.convert('RGB'), (img.width, 0))
    result_img.paste(g.convert('RGB'), (img.width * 2, 0))
    result_img.paste(b.convert('RGB'), (img.width * 3, 0))

    r_colored = Image.merge('RGB', (r, Image.new('L', r.size, 0), Image.new('L', r.size, 0)))
    g_colored = Image.merge('RGB', (Image.new('L', g.size, 0), g, Image.new('L', g.size, 0)))
    b_colored = Image.merge('RGB', (Image.new('L', b.size, 0), Image.new('L', b.size, 0), b))

    result_img.paste(img, (0, img.height))
    result_img.paste(r_colored, (img.width, img.height))
    result_img.paste(g_colored, (img.width * 2, img.height))
    result_img.paste(b_colored, (img.width * 3, img.height))

    result_filename = f'{filename.split('.')[0]}-channels.png'
    result_img.save(result_filename, 'PNG')
