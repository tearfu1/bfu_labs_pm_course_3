from PIL import Image, ImageDraw, ImageFont

filename = 'porscheCarreraGt.jpg'
wmfilename = 'Statue-of-Liberty-Island-New-York-Bay.jpg'
text = 'Maxim Vayda'
output_path = 'output_image.jpg'

with Image.open(filename) as img, Image.open(wmfilename) as wmimg:
    img = img.convert('RGBA')
    wmimg = wmimg.convert('RGBA')
    wmimg = wmimg.resize((200, 100))
    text_image = Image.new('RGBA', wmimg.size, (255, 255, 255, 0))
    draw = ImageDraw.Draw(text_image)
    font = ImageFont.load_default(30)
    _, _, text_width, text_height = draw.textbbox((0, 0), text, font)
    text_position = ((wmimg.width - text_width) // 2, (wmimg.height - text_height) // 2)
    draw.text(text_position, text, font=font, fill=(126, 240, 0, 255))
    wmres = Image.alpha_composite(wmimg, text_image)
    wmpos = (img.width - wmimg.width - 10, img.height - wmimg.height - 10)
    res_img = Image.new('RGB', img.size)
    res_img.paste(img)
    res_img.paste(wmres, wmpos)
    res_img.save(output_path, 'JPEG')
    res_img.show()
