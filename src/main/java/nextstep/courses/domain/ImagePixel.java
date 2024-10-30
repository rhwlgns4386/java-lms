package nextstep.courses.domain;

import java.util.Objects;

public class ImagePixel {
    private static final double RATIO = 3.0 / 2.0;
    private static final long MIN_WIDTH = 300;
    private static final long MIN_HEIGHT = 200;

    private Long width;
    private Long height;

    public ImagePixel(Long width, Long height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new IllegalArgumentException("invalid width or height(width must be greater than 300, height must be greater than 200)");
        }
        if (((double) width / (double) height) != RATIO) {
            throw new IllegalArgumentException("width:height ratio must be 3:2");
        }

        this.width = width;
        this.height = height;
    }

    public Long getWidth() {
        return width;
    }

    public Long getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImagePixel)) {
            return false;
        }
        ImagePixel that = (ImagePixel) o;
        return Objects.equals(getWidth(), that.getWidth()) && Objects.equals(getHeight(), that.getHeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWidth(), getHeight());
    }
}
