package nextstep.courses.domain;

import static nextstep.util.NullValidator.validateNull;

import java.util.Objects;
import nextstep.courses.ImageSizeMissException;

public class ImageResolution {
    private static final Pixel MIN_WIDTH = new Pixel(300);
    private static final Pixel MIN_HEIGHT = new Pixel(200);

    private final Pixel width;
    private final Pixel height;

    public ImageResolution(int width, int height) {
        this(new Pixel(width), new Pixel(height));
    }

    public ImageResolution(Pixel width, Pixel height) {
        validateNull(width, height);
        validateImageResolution(width, height);
        validateImageProportion(width, height);
        this.width = width;
        this.height = height;
    }

    private static void validateImageResolution(Pixel width, Pixel height) {
        if (!MIN_WIDTH.isLessThanOrEqualTo(width) || !MIN_HEIGHT.isLessThanOrEqualTo(height)) {
            throw new ImageSizeMissException();
        }
    }

    private void validateImageProportion(Pixel width, Pixel height) {
        if (!width.multiply(MIN_HEIGHT).equals(height.multiply(MIN_WIDTH))) {
            throw new ImageSizeMissException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageResolution that = (ImageResolution) o;
        return Objects.equals(width, that.width) && Objects.equals(height, that.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
