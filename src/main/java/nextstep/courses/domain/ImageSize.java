package nextstep.courses.domain;

import java.util.Objects;

public class ImageSize {
    private final int width;
    private final int height;

    public ImageSize(int width, int height) {
        if (width < 300 || height < 200) {
            throw new IllegalArgumentException();
        }
        checkRate(width, height);
        this.width = width;
        this.height = height;
    }

    private void checkRate(int width, int height) {
        if (!((double)width / (double)height == 1.5)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ImageSize))
            return false;
        ImageSize imageSize = (ImageSize)o;
        return width == imageSize.width && height == imageSize.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
