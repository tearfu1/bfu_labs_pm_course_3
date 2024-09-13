from PIL import Image, ImageDraw, ImageFont


def create_card(number):
    w, h = 100, 100
    card = Image.new('RGB', (w, h), 'white')
    draw = ImageDraw.Draw(card)

    draw.rectangle([(0, 0), (99, 99)], outline='blue', width=5)

    font = ImageFont.load_default(40)

    _, _, text_width, text_height = draw.textbbox((0, 0), str(number), font)
    text_position = ((w - text_width) // 2, (h - text_height) // 2)

    draw.text(text_position, str(number), font=font, fill='red')

    return card


def main():
    for i in range(1, 4):
        card = create_card(i)
        card.show()
        card.save(f'card{i}.png', 'PNG')


main()
