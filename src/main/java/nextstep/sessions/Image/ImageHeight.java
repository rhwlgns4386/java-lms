package nextstep.sessions.Image;

public class ImageHeight {
    private static final int MIN_HEIGHT = 200;
    private final int height;

    public ImageHeight(int height) {
        validate(height);
        this.height = height;
    }

    private void validate(int height) {
        if (height < MIN_HEIGHT) {
            throw new IllegalArgumentException("이미지의 높이는 " + MIN_HEIGHT + "보다 작을 수 없습니다.");
        }
    }

    public int getHeight() {
        return height;
    }
}
