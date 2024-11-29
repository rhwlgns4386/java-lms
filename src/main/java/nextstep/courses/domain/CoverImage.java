package nextstep.courses.domain;

import nextstep.util.NullValidator;

public class CoverImage {

    private final String fileName;
    private final ImageSize imageSize;
    private final ImageType imageType;

    public CoverImage(String fileName, int width, int height, int size, ImageType imageType) {
        this(fileName, new ImageSize(width, height, size), imageType);
    }

    public CoverImage(String fileName, ImageSize imageSize, ImageType imageType) {
        NullValidator.validateNull(fileName, imageSize, imageType);
        this.fileName = fileName;
        this.imageSize = imageSize;
        this.imageType = imageType;
    }
}
