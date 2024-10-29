package nextstep.courses.domain.cover;

public class CoverImageSize {
    private static final int MINIMUM_WIDTH = 300;
    private static final int MINIMUM_HEIGHT = 200;
    private static final int RATIO_HEIGHT = 2;
    private static final int RATIDO_WIDTH = 3;
    private final int width;
    private final int height;

    public CoverImageSize(int width, int height) {
        if (width < MINIMUM_WIDTH || height < MINIMUM_HEIGHT) {
            throw new IllegalArgumentException("width는 300픽셀, height는 200픽셀 이상입니다.");
        }

        if (width * RATIO_HEIGHT != height * RATIDO_WIDTH) {
            throw new IllegalArgumentException("이미지의 비율은 3:2여야 합니다.");
        }
        this.width = width;
        this.height = height;
    }
}
