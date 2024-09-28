from moviepy.editor import VideoFileClip
from argparse import ArgumentParser


def extract_video_fragment(input_file, start_time, end_time, output_file):
    video = VideoFileClip(input_file)

    clip = video.subclip(start_time, end_time)

    clip.write_videofile(output_file, codec='libx264')

    video.close()
    clip.close()


if __name__ == "__main__":
    parser = ArgumentParser()
    parser.add_argument("--input")
    parser.add_argument("--start", default=0)
    parser.add_argument("--end", default=-1)
    parser.add_argument("--output", default='output_video.mp4')

    ns = parser.parse_args()
    input_file = ns.input
    start_time = ns.start
    end_time = ns.end
    output_file = ns.output

    extract_video_fragment(input_file, start_time, end_time, output_file)
