from PIL import Image
import argparse
from pathlib import Path

parser = argparse.ArgumentParser()
parser.add_argument("-ftype", "--ftype", type=str, default='')
namespace = parser.parse_args()
ftype = namespace.ftype.upper()

path = Path('.')
# есть отдельный метод thumbnails, который сохраняет соотношение сторон,
# но для его корректной работы следует использовать save(), вместо show(),
# в задании сказано показывать, а не сохранять, поэтому оставим resize()

for item in path.iterdir():
    try:
        with Image.open(item) as img:
            thumbnail = img.resize((50, 50))
            # img.thumbnail((50, 50))
            if ftype:
                if img.format == ftype:
                    thumbnail.show()
                    # img.show()
            else:
                thumbnail.show()
                # img.show()
    except:
        pass
