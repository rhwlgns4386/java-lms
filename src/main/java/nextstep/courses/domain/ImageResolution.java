package nextstep.courses.domain;

import java.util.Objects;
import nextstep.courses.ImageSizeMissException;

public class ImageResolution {
    private static final Pixel MIN_WIDTH = new Pixel(300);
    private static final Pixel MIN_HEIGHT = new Pixel(200);

    public ImageResolution(int width, int height) {
        this(new Pixel(width), new Pixel(height));
    }

    public ImageResolution(Pixel width, Pixel height) {
        validateNull(width, height);
        validateImageResolution(width, height);
        validateImageProportion(width, height);
    }

    private void validateNull(Pixel width, Pixel height) {
        Objects.requireNonNull(width);
        Objects.requireNonNull(height);
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
}
