package nextstep.courses.domain;

import nextstep.courses.ImageSizeMissException;

public class ImageResolution {
    private static final Pixel MIN_WIDTH = new Pixel(300);
    private static final Pixel MIN_HEIGHT = new Pixel(200);

    public ImageResolution(int width, int height) {
        this(new Pixel(width), new Pixel(height));
    }

    public ImageResolution(Pixel width, Pixel height) {
        validateImageResolution(width, height);
    }

    private static void validateImageResolution(Pixel width, Pixel height) {
        if (!MIN_WIDTH.isLessThanOrEqualTo(width) || !MIN_HEIGHT.isLessThanOrEqualTo(height)) {
            throw new ImageSizeMissException();
        }
    }
}
