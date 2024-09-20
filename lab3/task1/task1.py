import sys
from pathlib import Path

import numpy as np
from skimage import io, util
from skimage import transform


def make_transformations(pics, transformation):
    for directory, collection in pics:
        last_index = len(collection) - 1
        for image in collection:
            last_index += 1
            transformed_image = transformation(image)
            transformed_image = (transformed_image * 255).astype(np.uint8)
            io.imsave(f'{directory}/{str(last_index).zfill(4)}.jpg', transformed_image)


def set_transformation(mode):
    def rotation(image):
        return transform.rotate(image, angle=45)

    def flip(image):
        image = (image * 255).astype(np.uint8)
        flipped_image = np.fliplr(image)
        return flipped_image

    def translation(image):
        return transform.warp(image, transform.AffineTransform(translation=(20, 50)))

    def random_crop(image):
        crop_size = (100, 100)
        h, w, _ = image.shape
        top = np.random.randint(0, h - crop_size[0])
        left = np.random.randint(0, w - crop_size[1])
        return image[top:top + crop_size[0], left:left + crop_size[1]]

    def noise_addition(image):
        return util.random_noise(image)

    def complex(image):
        rotated_image = rotation(image)
        noised_image = noise_addition(rotated_image)
        return noised_image

    match mode:
        case 1:
            return rotation
        case 2:
            return flip
        case 3:
            return translation
        case 4:
            return random_crop
        case 5:
            return noise_addition
        case 6:
            return complex


def user_board():
    if len(sys.argv) != 2:
        print("Wrong number of arguments")
        return
    path = Path(sys.argv[1])
    pics = []
    for dir in path.iterdir():
        pics.append([str(dir), io.imread_collection(f"{dir}/*.jpg")])
    print("List of available atomic transformation:\n"
          "1. Rotation\n"
          "2. Flip\n"
          "3. Translation\n"
          "4. Random Cropping\n"
          "5. Noise addition\n"
          "6. Complex (rotation, noise addition)"
          "")
    for i in range(len(pics)):
        collection = pics[i][1]
        if not len(collection):
            del pics[i]
    while True:
        mode = input("Input number of transformation mode: ")
        try:
            mode = int(mode)
            if 1 <= mode <= 6:
                break
        except:
            pass
        print('Wrong mode number, try again.')
    try:
        transformation = set_transformation(mode)
        make_transformations(pics, transformation)
        print("Done!")
    except Exception as e:
        print(e)


if __name__ == '__main__':
    user_board()
