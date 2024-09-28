import cv2
import sys


def play_video_with_info(video_path):
    cap = cv2.VideoCapture(video_path)

    if not cap.isOpened():
        print(f"Не удалось открыть видеофайл: {video_path}")
        return

    fps = cap.get(cv2.CAP_PROP_FPS)

    font = cv2.FONT_HERSHEY_DUPLEX
    text = f"File: {video_path.split('/')[-1]}, FPS: {fps}"
    position = (10, 30)
    font_scale = 1
    font_color = (255, 255, 255)
    font_thickness = 1

    while True:
        ret, frame = cap.read()

        if not ret:
            print("Конец видео.")
            break

        cv2.putText(frame, text, position, font, font_scale, font_color, font_thickness)

        cv2.imshow('Video', frame)

        # cv2.waitKey(int(1000 / fps)) рассчитывает, сколько миллисекунд нужно подождать
        # перед отображением следующего кадра, а так же вернет код нажатой клавиши, чтоб гарантировать, что это
        # 8-битное число используем & 0xFF и сравниваем с q в 8-битном представлении
        if cv2.waitKey(int(1000 / fps)) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()


if __name__ == '__main__':
    if len(sys.argv) != 2:
        print('Wrong input')
        exit()
    video_path = sys.argv[1]
    play_video_with_info(video_path)
