package nextstep.sessions.Image;

public class ImageSize {
    private static final int MAX_MB_SIZE = 1;
    private final int size;

    public ImageSize(int size) {
        validate(size);
        this.size = size;
    }

    private void validate(int size) {
        if (size > MAX_MB_SIZE) {
            throw new IllegalArgumentException("이미지의 크기는 " + MAX_MB_SIZE + "보다 클 수 없습니다.");
        }
    }

    public int getSize() {
        return size;
    }
}
