package nextstep.courses.domain.image;

import java.util.Objects;

public class ImagePixel {
    private static final double RATIO = 3.0 / 2.0;
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;

    private Integer width;
    private Integer height;

    public ImagePixel(Integer width, Integer height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new IllegalArgumentException("invalid width or height(width must be greater than 300, height must be greater than 200)");
        }
        if (((double) width / (double) height) != RATIO) {
            throw new IllegalArgumentException("width:height ratio must be 3:2");
        }

        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
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

    @Override
    public String toString() {
        return "ImagePixel{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
