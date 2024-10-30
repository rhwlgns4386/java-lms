package nextstep.sessions.Image;

public class ImageWidth {
    private static final int MIN_WIDTH = 300;
    private final int width;

    public ImageWidth(int width) {
        validate(width);
        this.width = width;
    }

    private void validate(int width) {
        if (width < MIN_WIDTH) {
            throw new IllegalArgumentException("이미지의 너비는 " + MIN_WIDTH + "보다 작을 수 없습니다.");
        }
    }

    public int getWidth() {
        return width;
    }
}
