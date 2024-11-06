package nextstep.courses.domain;


public class SessionImage {
    public static final int MAX_SIZE_BYTE  = 1 * 1024 * 1024;
    public static final int MIN_WIDTH = 300;
    public static final int MIN_HEIGHT = 200;
    public static final int EXPECTED_RATIO = 3 / 2;
    private int size;

    private ImageType imageType;

    private int width;

    private int height;

    public SessionImage(int size, String imageType, int width, int height) {
        validSize(size);
        validDimensions(width, height);

        this.size = size;
        this.imageType = ImageType.fromString(imageType);
        this.width = width;
        this.height = height;
    }

    private void validDimensions(int width, int height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT || width / height != EXPECTED_RATIO) {
            throw new IllegalArgumentException("유효하지 않은 이미지 사이즈입니다.");
        }
    }

    private void validSize(int size) {
        if (size > MAX_SIZE_BYTE) {
            throw new IllegalArgumentException("이미지 크기를 초과했습니다.");
        }
    }
}
