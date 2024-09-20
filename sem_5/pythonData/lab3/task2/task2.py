from PIL import Image
import matplotlib.pyplot as plt
import sys


def report():
    if len(sys.argv) != 2:
        print('Wrong number of arguments')
        return
    img = Image.open(sys.argv[1])
    r, g, b = img.split()
    img_gray = img.convert('L')
    img_histogram = img_gray.histogram()
    r_histogram = r.histogram()
    g_histogram = g.histogram()
    b_histogram = b.histogram()

    fig = plt.figure(figsize=(12, 11))
    gs = fig.add_gridspec(4, 2, width_ratios=[1, 1], height_ratios=[1, 1, 1, 1])

    ax_image = fig.add_subplot(gs[:, 0])
    ax_image.imshow(img)
    ax_image.set_title('Original Image')
    ax_image.axis('off')

    ax_hist_intensity = fig.add_subplot(gs[0, 1])
    ax_hist_intensity.plot(range(256), img_histogram, color='black')
    ax_hist_intensity.set_title('Intensity Histogram')

    ax_hist_red = fig.add_subplot(gs[1, 1])
    ax_hist_red.plot(range(256), r_histogram, color='red')
    ax_hist_red.set_title('Red Channel Histogram')

    ax_hist_green = fig.add_subplot(gs[2, 1])
    ax_hist_green.plot(range(256), g_histogram, color='green')
    ax_hist_green.set_title('Green Channel Histogram')

    ax_hist_blue = fig.add_subplot(gs[3, 1])
    ax_hist_blue.plot(range(256), b_histogram, color='blue')
    ax_hist_blue.set_title('Blue Channel Histogram')

    for ax in [ax_hist_intensity, ax_hist_red, ax_hist_green, ax_hist_blue]:
        ax.set(xlabel='Pixel Value', ylabel='Frequency')

    # Adjust layout
    plt.tight_layout()
    plt.show()


if __name__ == '__main__':
    report()
