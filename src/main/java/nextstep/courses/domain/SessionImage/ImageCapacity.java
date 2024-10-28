package nextstep.courses.domain.SessionImage;

public class ImageCapacity {

    public static final int MINIMUM_SIZE = 0;
    public static final int MAXIMUM_SIZE = 1;
    private final int imageSize;

    public ImageCapacity(int imageSize) {
        validateSize(imageSize);
        this.imageSize = imageSize;
    }

    private void validateSize(int imageSize) {
        if (imageSize <= MINIMUM_SIZE || imageSize > MAXIMUM_SIZE) {
            throw new IllegalArgumentException("이미지 사이즈는 1미만이어야 합니다.");
        }
    }
}
