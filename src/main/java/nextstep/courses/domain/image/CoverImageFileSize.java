package nextstep.courses.domain.image;

import java.util.Objects;

public class CoverImageFileSize {
    private static final String RATIO_ERROR = "잘못된 비율입니다: 비율은 반드시 3:2여야 합니다.";
    private static final String FILE_SIZE_MIN_ERROR = "잘못된 크기입니다: 높이는 300픽셀, 넓이는 200픽셀 이상이어야 합니다.";
    private static final double RATIO = 3.0 / 2.0;
    private static final int MIN_HEIGHT = 200;
    private static final int MIN_WIDTH = 300;

    private final int height;
    private final int width;

    public CoverImageFileSize(int width, int height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new IllegalArgumentException(FILE_SIZE_MIN_ERROR);
        }
        if ((double) width / (double) height != RATIO) {
            throw new IllegalArgumentException(RATIO_ERROR);
        }
        this.height = height;
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoverImageFileSize that = (CoverImageFileSize) o;
        return height == that.height && width == that.width;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width);
    }

}
