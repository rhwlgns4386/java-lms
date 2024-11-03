package nextstep.courses.domain.image;

public class ImageSize {
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final int MAX_VOLUME = 1024 * 1024;

    private final int width;
    private final int height;
    private final int volume;

    public ImageSize(int width, int height, int volume) {
        validateImageSize(width, height, volume);
        this.width = width;
        this.height = height;
        this.volume = volume;
    }

    private void validateImageSize(int width, int height, int volume) {
        validateMinWidth(width);
        validateMinHeight(height);
        validateRatio(width, height);
        validateVolume(volume);
    }

    private void validateMinWidth(int width) {
        if (width < MIN_WIDTH) {
            throw new IllegalArgumentException("가로길이는 " + MIN_WIDTH + " 픽셀 이상이어야합니다.");
        }
    }

    private void validateMinHeight(int height) {
        if (height < MIN_HEIGHT) {
            throw new IllegalArgumentException("세로길이는 " + MIN_HEIGHT + "픽셀 이상이어야합니다.");
        }
    }

    private void validateRatio(int width, int height) {
        if (2 * width != 3 * height) {
            throw new IllegalArgumentException("비율이 3:2가 아닙니다.");
        }
    }

    private void validateVolume(int volume) {
        if (volume > MAX_VOLUME) {
            throw new IllegalArgumentException("파일 크기는 1MB를 초과할 수 없습니다.");
        }
    }
}
