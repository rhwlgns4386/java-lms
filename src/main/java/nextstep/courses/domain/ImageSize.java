package nextstep.courses.domain;

import static nextstep.util.NullValidator.validateNull;

public class ImageSize {
    private final ImageResolution imageResolution;
    private final ImageByte imageByte;

    public ImageSize(int width, int height, int byteSize) {
        this(new ImageResolution(width, height), new ImageByte(byteSize));
    }

    public ImageSize(Pixel width, Pixel height, int byteSize) {
        this(new ImageResolution(width, height), new ImageByte(byteSize));
    }

    public ImageSize(ImageResolution imageResolution, ImageByte imageByte) {
        validateNull(imageResolution, imageByte);
        this.imageResolution = imageResolution;
        this.imageByte = imageByte;
    }

    public int width() {
        return imageResolution.width();
    }

    public int height() {
        return imageResolution.height();
    }

    public int bytes() {
        return imageByte.bytes();
    }
}
