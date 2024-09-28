from argparse import ArgumentParser
from moviepy.editor import VideoFileClip
from pathlib import Path
from PIL import Image


def extract_frames(input_file, start_time, end_time, output_folder, step=10):
    video = VideoFileClip(input_file)

    clip = video.subclip(start_time, end_time)

    if not Path(output_folder).exists():
        Path(output_folder).mkdir()

    check_frame_width = clip.get_frame(t=1).shape[1]
    width = 250 if check_frame_width > 250 else check_frame_width

    fps = clip.fps
    frame_count = 0

    for i, frame in enumerate(clip.iter_frames(fps=fps)):
        if i % step == 0:
            # хотя у moviepy есть свой resize, он не работает, так как он реализует это через Pillow,
            # используя атрибут 'ANTIALIAS', который был удален из Pillow, поэтому сами ручками реализуем resize
            img = Image.fromarray(frame)
            # мне кажется, что будет лучше, чтоб кадры сохраняли соотношение сторон, поэтому тут так, если это не нужно,
            # то можно раскомментировать эту строчку и закомментировать ту, что за ней
            # img_resized = img.resize((width, img.height))
            img_resized = img.resize((width, int(width * img.height / img.width)))
            frame_filename = Path(output_folder).joinpath(f"{frame_count:04d}.png")
            img_resized.save(frame_filename)

            frame_count += 1

    print(f"Извлечено {frame_count} кадров и сохранено в папку {output_folder}")


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument("--input")
    parser.add_argument("--start")
    parser.add_argument("--end")
    parser.add_argument("--output", default='output_frames')
    parser.add_argument("--step", default=10)

    ns = parser.parse_args()
    input_file = ns.input
    start_time = ns.start
    end_time = ns.end
    output_folder = ns.output
    step = ns.step

    extract_frames(input_file, start_time, end_time, output_folder, step)
